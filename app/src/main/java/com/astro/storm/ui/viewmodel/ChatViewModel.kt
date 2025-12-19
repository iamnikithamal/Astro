package com.astro.storm.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.astro.storm.data.ai.agent.AgentResponse
import com.astro.storm.data.ai.agent.StormyAgent
import com.astro.storm.data.ai.provider.AiModel
import com.astro.storm.data.ai.provider.AiProviderRegistry
import com.astro.storm.data.ai.provider.ChatMessage
import com.astro.storm.data.ai.provider.MessageRole
import com.astro.storm.data.local.ChartDatabase
import com.astro.storm.data.local.chat.ChatConversation
import com.astro.storm.data.local.chat.ChatMessageModel
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.repository.ChatRepository
import com.astro.storm.data.repository.SavedChart
import com.astro.storm.ui.components.ContentCleaner
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * AI processing status for detailed UI feedback
 */
sealed class AiStatus {
    object Idle : AiStatus()
    object Thinking : AiStatus()
    object Reasoning : AiStatus()
    data class CallingTool(val toolName: String) : AiStatus()
    data class ExecutingTools(val tools: List<String>) : AiStatus()
    object Typing : AiStatus()
    object Complete : AiStatus()
}

/**
 * Represents a tool execution step for the agentic UI
 */
data class ToolExecutionStep(
    val toolName: String,
    val displayName: String,
    val status: ToolStepStatus,
    val startTime: Long = System.currentTimeMillis(),
    val endTime: Long? = null,
    val result: String? = null
)

enum class ToolStepStatus {
    PENDING, EXECUTING, COMPLETED, FAILED
}

/**
 * Streaming message state for the agentic UI
 * This tracks the current streaming response with all its components
 */
data class StreamingMessageState(
    val messageId: Long,
    val content: String = "",
    val reasoning: String = "",
    val toolSteps: List<ToolExecutionStep> = emptyList(),
    val isComplete: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null
)

/**
 * ViewModel for Chat feature
 *
 * Manages:
 * - Conversations list
 * - Current conversation messages
 * - AI model selection
 * - Message streaming
 * - Agent tool calling
 */
class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val chatRepository = ChatRepository.getInstance(application)
    private val providerRegistry = AiProviderRegistry.getInstance(application)
    private val database = ChartDatabase.getInstance(application)

    // Agent instance (lazy init per conversation)
    private var stormyAgent: StormyAgent? = null

    // ============================================
    // UI STATE
    // ============================================

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Idle)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    private val _currentConversationId = MutableStateFlow<Long?>(null)
    val currentConversationId: StateFlow<Long?> = _currentConversationId.asStateFlow()

    private val _streamingContent = MutableStateFlow("")
    val streamingContent: StateFlow<String> = _streamingContent.asStateFlow()

    private val _streamingReasoning = MutableStateFlow("")
    val streamingReasoning: StateFlow<String> = _streamingReasoning.asStateFlow()

    private val _isStreaming = MutableStateFlow(false)
    val isStreaming: StateFlow<Boolean> = _isStreaming.asStateFlow()

    private val _selectedModel = MutableStateFlow<AiModel?>(null)
    val selectedModel: StateFlow<AiModel?> = _selectedModel.asStateFlow()

    private val _toolsInProgress = MutableStateFlow<List<String>>(emptyList())
    val toolsInProgress: StateFlow<List<String>> = _toolsInProgress.asStateFlow()

    // Detailed AI processing status for UI feedback
    private val _aiStatus = MutableStateFlow<AiStatus>(AiStatus.Idle)
    val aiStatus: StateFlow<AiStatus> = _aiStatus.asStateFlow()

    // Model options - thinking and web search toggles
    private val _thinkingEnabled = MutableStateFlow(true)
    val thinkingEnabled: StateFlow<Boolean> = _thinkingEnabled.asStateFlow()

    private val _webSearchEnabled = MutableStateFlow(false)
    val webSearchEnabled: StateFlow<Boolean> = _webSearchEnabled.asStateFlow()

    // Streaming message state for agentic UI - replaces separate content/reasoning flows
    private val _streamingMessageState = MutableStateFlow<StreamingMessageState?>(null)
    val streamingMessageState: StateFlow<StreamingMessageState?> = _streamingMessageState.asStateFlow()

    // Track the ID of the message being streamed to exclude from regular message list
    private val _streamingMessageId = MutableStateFlow<Long?>(null)
    val streamingMessageId: StateFlow<Long?> = _streamingMessageId.asStateFlow()

    // Current streaming message ID
    private var currentMessageId: Long? = null
    private var streamingJob: Job? = null

    // Raw content accumulators for proper cleaning
    private var rawContentAccumulator = StringBuilder()
    private var rawReasoningAccumulator = StringBuilder()

    // Tool steps accumulator for agentic UI
    private var currentToolSteps = mutableListOf<ToolExecutionStep>()

    // Performance optimization: Throttling for UI updates
    private var lastUiUpdateTime = 0L
    private var lastDbUpdateTime = 0L
    private var pendingUiUpdate = false
    private val updateMutex = Mutex()

    // Throttle intervals (in milliseconds)
    private companion object {
        const val UI_UPDATE_THROTTLE_MS = 50L  // ~20 FPS for smooth typing
        const val DB_UPDATE_THROTTLE_MS = 500L // Reduced DB writes
        const val MIN_CONTENT_CHANGE_LENGTH = 5 // Min chars to trigger update
    }

    // ============================================
    // DATA FLOWS
    // ============================================

    /**
     * All conversations
     */
    val conversations: StateFlow<List<ChatConversation>> = chatRepository
        .getAllConversations()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * Messages for current conversation
     */
    val currentMessages: StateFlow<List<ChatMessageModel>> = _currentConversationId
        .flatMapLatest { id ->
            if (id != null) {
                chatRepository.getMessagesForConversation(id)
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * Available AI models
     */
    val availableModels: StateFlow<List<AiModel>> = providerRegistry.enabledModels
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * Current conversation details
     */
    val currentConversation: StateFlow<ChatConversation?> = _currentConversationId
        .flatMapLatest { id ->
            if (id != null) {
                flow<ChatConversation?> { emit(chatRepository.getConversationById(id)) }
            } else {
                flowOf<ChatConversation?>(null)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {
        // Initialize provider registry and set default model
        viewModelScope.launch {
            // First, ensure models are loaded from providers
            providerRegistry.initialize()

            // Then set default model
            val defaultModel = providerRegistry.getDefaultModel()
            _selectedModel.value = defaultModel
        }
    }

    // ============================================
    // CONVERSATION MANAGEMENT
    // ============================================

    // Track pending conversation context for lazy creation
    private var pendingConversationContext: PendingConversationContext? = null

    private data class PendingConversationContext(
        val currentChart: VedicChart?,
        val savedCharts: List<SavedChart>,
        val selectedChartId: Long?
    )

    /**
     * Prepare a new conversation (lazy creation - actually creates when first message is sent)
     */
    fun createConversation(
        currentChart: VedicChart?,
        savedCharts: List<SavedChart>,
        selectedChartId: Long?
    ) {
        viewModelScope.launch {
            val model = _selectedModel.value ?: providerRegistry.getDefaultModel()
            if (model == null) {
                _uiState.value = ChatUiState.Error("No AI model available. Please configure models in settings.")
                return@launch
            }

            // Store context for lazy creation - don't create conversation in database yet
            pendingConversationContext = PendingConversationContext(
                currentChart = currentChart,
                savedCharts = savedCharts,
                selectedChartId = selectedChartId
            )

            // Clear current conversation ID (signals we're in "new chat" mode)
            _currentConversationId.value = null

            // Initialize agent with context
            initializeAgent(currentChart, savedCharts, selectedChartId)
        }
    }

    /**
     * Open an existing conversation
     */
    fun openConversation(
        conversationId: Long,
        currentChart: VedicChart?,
        savedCharts: List<SavedChart>,
        selectedChartId: Long?
    ) {
        viewModelScope.launch {
            _currentConversationId.value = conversationId

            // Get conversation model
            val conversation = chatRepository.getConversationEntityById(conversationId)
            if (conversation != null) {
                val model = availableModels.value.find {
                    it.id == conversation.modelId && it.providerId == conversation.providerId
                }
                if (model != null) {
                    _selectedModel.value = model
                }
            }

            // Initialize agent with context
            initializeAgent(currentChart, savedCharts, selectedChartId)
        }
    }

    /**
     * Close current conversation
     */
    fun closeConversation() {
        cancelStreaming()
        _currentConversationId.value = null
        pendingConversationContext = null
        stormyAgent = null
    }

    /**
     * Delete a conversation
     */
    fun deleteConversation(conversationId: Long) {
        viewModelScope.launch {
            if (_currentConversationId.value == conversationId) {
                closeConversation()
            }
            chatRepository.deleteConversation(conversationId)
        }
    }

    /**
     * Update conversation title
     */
    fun updateConversationTitle(conversationId: Long, title: String) {
        viewModelScope.launch {
            chatRepository.updateConversationTitle(conversationId, title)
        }
    }

    /**
     * Archive conversation
     */
    fun archiveConversation(conversationId: Long) {
        viewModelScope.launch {
            chatRepository.archiveConversation(conversationId)
        }
    }

    // ============================================
    // MODEL SELECTION
    // ============================================

    /**
     * Select AI model for current/new conversation
     */
    fun selectModel(model: AiModel) {
        _selectedModel.value = model

        // Update current conversation if exists
        viewModelScope.launch {
            val conversationId = _currentConversationId.value ?: return@launch
            chatRepository.updateConversationModel(conversationId, model.id, model.providerId)
        }
    }

    /**
     * Toggle thinking mode (for supported models like Qwen)
     */
    fun setThinkingEnabled(enabled: Boolean) {
        _thinkingEnabled.value = enabled
    }

    /**
     * Toggle web search (for supported models like Qwen)
     */
    fun setWebSearchEnabled(enabled: Boolean) {
        _webSearchEnabled.value = enabled
    }

    // ============================================
    // MESSAGE HANDLING
    // ============================================

    /**
     * Send a message and get AI response
     */
    fun sendMessage(
        content: String,
        currentChart: VedicChart?,
        savedCharts: List<SavedChart>,
        selectedChartId: Long?
    ) {
        val model = _selectedModel.value ?: return

        // Cancel any existing streaming
        cancelStreaming()

        viewModelScope.launch {
            try {
                _isStreaming.value = true
                _streamingContent.value = ""
                _streamingReasoning.value = ""
                _toolsInProgress.value = emptyList()
                _aiStatus.value = AiStatus.Thinking
                _uiState.value = ChatUiState.Sending

                // Reset accumulators and throttle timers
                rawContentAccumulator.clear()
                rawReasoningAccumulator.clear()
                currentToolSteps.clear()
                lastUiUpdateTime = 0L
                lastDbUpdateTime = 0L
                pendingUiUpdate = false

                // Get or create conversation ID
                // If we're in "new chat" mode, create the conversation now (lazy creation)
                val conversationId = _currentConversationId.value ?: run {
                    val context = pendingConversationContext
                    if (context == null) {
                        _uiState.value = ChatUiState.Error("No conversation context")
                        _isStreaming.value = false
                        _aiStatus.value = AiStatus.Idle
                        return@launch
                    }

                    // Create conversation in database now that we have a message
                    val newConversationId = chatRepository.createConversation(
                        title = "New Chat",
                        modelId = model.id,
                        providerId = model.providerId,
                        profileId = context.selectedChartId
                    )
                    _currentConversationId.value = newConversationId
                    pendingConversationContext = null
                    newConversationId
                }

                // Add user message
                chatRepository.addUserMessage(conversationId, content)

                // Create placeholder for assistant message
                currentMessageId = chatRepository.addAssistantMessagePlaceholder(
                    conversationId,
                    model.id
                )

                // Set the streaming message ID to exclude from regular message list
                _streamingMessageId.value = currentMessageId

                // Initialize streaming message state for agentic UI
                currentMessageId?.let { msgId ->
                    _streamingMessageState.value = StreamingMessageState(
                        messageId = msgId,
                        content = "",
                        reasoning = "",
                        toolSteps = emptyList(),
                        isComplete = false
                    )
                }

                // Ensure agent is initialized
                if (stormyAgent == null) {
                    initializeAgent(currentChart, savedCharts, selectedChartId)
                }

                val agent = stormyAgent ?: run {
                    _uiState.value = ChatUiState.Error("Failed to initialize AI agent")
                    _aiStatus.value = AiStatus.Idle
                    return@launch
                }

                // Get conversation history and convert to ChatMessage format
                val dbMessages = chatRepository.getMessagesForConversationSync(conversationId)
                    .dropLast(1) // Exclude the placeholder
                val chatMessages = dbMessages.map { msg ->
                    ChatMessage(
                        role = msg.role,
                        content = msg.content
                    )
                }

                // Get current profile
                val currentProfile = savedCharts.find { it.id == selectedChartId }

                // Process with agent
                _uiState.value = ChatUiState.Streaming

                var finalContent = ""
                var finalReasoning: String? = null
                val toolsUsed = mutableListOf<String>()
                var hasReceivedContent = false

                streamingJob = launch {
                    agent.processMessage(
                        messages = chatMessages,
                        model = model,
                        currentProfile = currentProfile,
                        allProfiles = savedCharts,
                        currentChart = currentChart
                    ).collect { response ->
                        when (response) {
                            is AgentResponse.ContentChunk -> {
                                // Accumulate raw content
                                rawContentAccumulator.append(response.text)

                                // Throttled UI update for smooth performance
                                val currentTime = System.currentTimeMillis()
                                if (currentTime - lastUiUpdateTime >= UI_UPDATE_THROTTLE_MS) {
                                    lastUiUpdateTime = currentTime

                                    // Clean and update display content
                                    val cleanedContent = ContentCleaner.cleanForDisplay(rawContentAccumulator.toString())
                                    _streamingContent.value = cleanedContent

                                    // Update AI status
                                    if (!hasReceivedContent && cleanedContent.isNotEmpty()) {
                                        hasReceivedContent = true
                                        _aiStatus.value = AiStatus.Typing
                                    }

                                    // Update streaming message state (throttled)
                                    updateStreamingMessageState()
                                } else {
                                    pendingUiUpdate = true
                                }

                                // Throttled database update (less frequent to reduce I/O)
                                if (currentTime - lastDbUpdateTime >= DB_UPDATE_THROTTLE_MS) {
                                    lastDbUpdateTime = currentTime
                                    currentMessageId?.let { msgId ->
                                        val cleanedContent = ContentCleaner.cleanForDisplay(rawContentAccumulator.toString())
                                        val cleanedReasoning = if (rawReasoningAccumulator.isNotEmpty()) {
                                            ContentCleaner.cleanReasoning(rawReasoningAccumulator.toString())
                                        } else null

                                        chatRepository.updateAssistantMessageContent(
                                            messageId = msgId,
                                            content = cleanedContent,
                                            reasoningContent = cleanedReasoning?.takeIf { it.isNotEmpty() },
                                            isStreaming = true
                                        )
                                    }
                                }
                            }
                            is AgentResponse.ReasoningChunk -> {
                                // Accumulate raw reasoning
                                rawReasoningAccumulator.append(response.text)

                                // Throttled UI update for reasoning
                                val currentTime = System.currentTimeMillis()
                                if (currentTime - lastUiUpdateTime >= UI_UPDATE_THROTTLE_MS) {
                                    lastUiUpdateTime = currentTime

                                    val cleanedReasoning = ContentCleaner.cleanReasoning(rawReasoningAccumulator.toString())
                                    if (cleanedReasoning.isNotEmpty()) {
                                        _streamingReasoning.value = cleanedReasoning

                                        // Update AI status to show reasoning
                                        if (_aiStatus.value == AiStatus.Thinking) {
                                            _aiStatus.value = AiStatus.Reasoning
                                        }

                                        // Update streaming message state
                                        updateStreamingMessageState()
                                    }
                                }

                                // Throttled database update (reasoning less frequent)
                                if (currentTime - lastDbUpdateTime >= DB_UPDATE_THROTTLE_MS) {
                                    lastDbUpdateTime = currentTime
                                    currentMessageId?.let { msgId ->
                                        val cleanedContent = ContentCleaner.cleanForDisplay(rawContentAccumulator.toString())
                                        val cleanedReasoning = ContentCleaner.cleanReasoning(rawReasoningAccumulator.toString())
                                        chatRepository.updateAssistantMessageContent(
                                            messageId = msgId,
                                            content = cleanedContent,
                                            reasoningContent = cleanedReasoning.takeIf { it.isNotEmpty() },
                                            isStreaming = true
                                        )
                                    }
                                }
                            }
                            is AgentResponse.ToolCallsStarted -> {
                                _toolsInProgress.value = response.toolNames
                                toolsUsed.addAll(response.toolNames)
                                _aiStatus.value = AiStatus.ExecutingTools(response.toolNames)

                                // Add tool steps to streaming state for agentic UI
                                response.toolNames.forEach { toolName ->
                                    val step = ToolExecutionStep(
                                        toolName = toolName,
                                        displayName = formatToolNameForDisplay(toolName),
                                        status = ToolStepStatus.PENDING
                                    )
                                    if (currentToolSteps.none { it.toolName == toolName }) {
                                        currentToolSteps.add(step)
                                    }
                                }
                                updateStreamingMessageState()
                            }
                            is AgentResponse.ToolExecuting -> {
                                if (!_toolsInProgress.value.contains(response.toolName)) {
                                    _toolsInProgress.value = _toolsInProgress.value + response.toolName
                                }
                                if (!toolsUsed.contains(response.toolName)) {
                                    toolsUsed.add(response.toolName)
                                }
                                _aiStatus.value = AiStatus.CallingTool(response.toolName)

                                // Update tool step status to executing
                                val stepIndex = currentToolSteps.indexOfFirst { it.toolName == response.toolName }
                                if (stepIndex >= 0) {
                                    currentToolSteps[stepIndex] = currentToolSteps[stepIndex].copy(
                                        status = ToolStepStatus.EXECUTING
                                    )
                                } else {
                                    currentToolSteps.add(ToolExecutionStep(
                                        toolName = response.toolName,
                                        displayName = formatToolNameForDisplay(response.toolName),
                                        status = ToolStepStatus.EXECUTING
                                    ))
                                }
                                updateStreamingMessageState()
                            }
                            is AgentResponse.ToolResult -> {
                                _toolsInProgress.value = _toolsInProgress.value - response.toolName
                                if (_toolsInProgress.value.isEmpty()) {
                                    // Tools done, back to thinking for next iteration
                                    _aiStatus.value = AiStatus.Thinking
                                }

                                // Update tool step status to completed/failed
                                val stepIndex = currentToolSteps.indexOfFirst { it.toolName == response.toolName }
                                if (stepIndex >= 0) {
                                    currentToolSteps[stepIndex] = currentToolSteps[stepIndex].copy(
                                        status = if (response.success) ToolStepStatus.COMPLETED else ToolStepStatus.FAILED,
                                        endTime = System.currentTimeMillis(),
                                        result = response.summary
                                    )
                                }
                                updateStreamingMessageState()
                            }
                            is AgentResponse.Complete -> {
                                // Clean final content
                                finalContent = ContentCleaner.cleanForDisplay(response.content)
                                finalReasoning = response.reasoning?.let {
                                    ContentCleaner.cleanReasoning(it)
                                }?.takeIf { it.isNotBlank() }
                                _aiStatus.value = AiStatus.Complete

                                // Flush any pending UI updates before completion
                                if (pendingUiUpdate || _streamingContent.value != finalContent) {
                                    _streamingContent.value = finalContent
                                    finalReasoning?.let { _streamingReasoning.value = it }
                                }

                                // Update streaming state to complete
                                _streamingMessageState.value = _streamingMessageState.value?.copy(
                                    content = finalContent,
                                    reasoning = finalReasoning ?: "",
                                    isComplete = true
                                )
                            }
                            is AgentResponse.Error -> {
                                _uiState.value = ChatUiState.Error(response.message)
                                _aiStatus.value = AiStatus.Idle
                                currentMessageId?.let { msgId ->
                                    chatRepository.setMessageError(msgId, response.message)
                                }

                                // Update streaming state with error
                                _streamingMessageState.value = _streamingMessageState.value?.copy(
                                    hasError = true,
                                    errorMessage = response.message
                                )
                            }
                            is AgentResponse.TokenUsage -> {
                                // Token usage info - could log or track if needed
                            }
                            is AgentResponse.ModelInfo -> {
                                // Model info - could log if needed
                            }
                            is AgentResponse.RetryInfo -> {
                                // Don't add retry info to content - just update status
                                // The UI can show this via aiStatus if needed
                            }
                        }
                    }
                }

                streamingJob?.join()

                // Finalize message with cleaned content
                currentMessageId?.let { msgId ->
                    // Use the final cleaned content from agent, but fall back to streaming content if empty
                    val contentToSave = when {
                        finalContent.isNotEmpty() -> finalContent
                        _streamingContent.value.isNotEmpty() -> _streamingContent.value
                        _streamingReasoning.value.isNotEmpty() -> _streamingReasoning.value
                        else -> ""
                    }

                    // Determine reasoning - only include if we have both content AND separate reasoning
                    val reasoningToSave = when {
                        finalReasoning != null && finalContent.isNotEmpty() -> finalReasoning
                        _streamingReasoning.value.isNotEmpty() && _streamingContent.value.isNotEmpty() -> _streamingReasoning.value
                        else -> null
                    }

                    chatRepository.finalizeAssistantMessage(
                        messageId = msgId,
                        content = contentToSave,
                        reasoningContent = reasoningToSave,
                        toolsUsed = toolsUsed.distinct().takeIf { it.isNotEmpty() }
                    )
                }

                // Generate title for new conversation
                val messageCount = chatRepository.getMessagesForConversationSync(conversationId).size
                if (messageCount == 2) { // First user + first assistant message
                    val title = chatRepository.generateConversationTitle(conversationId)
                    chatRepository.updateConversationTitle(conversationId, title)
                }

                _isStreaming.value = false
                _toolsInProgress.value = emptyList()
                _aiStatus.value = AiStatus.Idle
                _uiState.value = ChatUiState.Idle

                // Clear streaming message state and ID - this allows the message to appear in regular list
                _streamingMessageId.value = null
                _streamingMessageState.value = null
                currentMessageId = null

            } catch (e: Exception) {
                _isStreaming.value = false
                _toolsInProgress.value = emptyList()
                _aiStatus.value = AiStatus.Idle
                _uiState.value = ChatUiState.Error(e.message ?: "Unknown error occurred")

                currentMessageId?.let { msgId ->
                    chatRepository.setMessageError(msgId, e.message ?: "Unknown error")
                }

                // Clear streaming state on error
                _streamingMessageId.value = null
                _streamingMessageState.value = null
                currentMessageId = null
            }
        }
    }

    /**
     * Helper to update the streaming message state with current values
     */
    private fun updateStreamingMessageState() {
        currentMessageId?.let { msgId ->
            _streamingMessageState.value = StreamingMessageState(
                messageId = msgId,
                content = ContentCleaner.cleanForDisplay(rawContentAccumulator.toString()),
                reasoning = if (rawReasoningAccumulator.isNotEmpty()) {
                    ContentCleaner.cleanReasoning(rawReasoningAccumulator.toString())
                } else "",
                toolSteps = currentToolSteps.toList(),
                isComplete = false
            )
        }
    }

    /**
     * Format tool name for display (e.g., "get_planet_positions" -> "Planet Positions")
     */
    private fun formatToolNameForDisplay(toolName: String): String {
        return toolName
            .removePrefix("get_")
            .replace("_", " ")
            .split(" ")
            .joinToString(" ") { word ->
                word.replaceFirstChar { it.uppercase() }
            }
    }

    /**
     * Cancel current streaming
     */
    fun cancelStreaming() {
        streamingJob?.cancel()
        streamingJob = null
        _isStreaming.value = false
        _streamingContent.value = ""
        _streamingReasoning.value = ""
        _toolsInProgress.value = emptyList()
        _aiStatus.value = AiStatus.Idle

        // Clear accumulators
        rawContentAccumulator.clear()
        rawReasoningAccumulator.clear()
        currentToolSteps.clear()

        // Reset throttle timers
        lastUiUpdateTime = 0L
        lastDbUpdateTime = 0L
        pendingUiUpdate = false

        // Clear streaming state
        _streamingMessageId.value = null
        _streamingMessageState.value = null

        // Mark current message as incomplete
        viewModelScope.launch {
            currentMessageId?.let { msgId ->
                val content = _streamingContent.value.ifEmpty { "Response cancelled" }
                chatRepository.finalizeAssistantMessage(
                    messageId = msgId,
                    content = content,
                    reasoningContent = _streamingReasoning.value.takeIf { it.isNotEmpty() }
                )
            }
            currentMessageId = null
        }
    }

    /**
     * Regenerate last response
     */
    fun regenerateResponse(
        currentChart: VedicChart?,
        savedCharts: List<SavedChart>,
        selectedChartId: Long?
    ) {
        viewModelScope.launch {
            val conversationId = _currentConversationId.value ?: return@launch
            val messages = chatRepository.getMessagesForConversationSync(conversationId)

            // Find last user message
            val lastUserMessage = messages.lastOrNull { it.role.name == "USER" }
            if (lastUserMessage != null) {
                // Delete last assistant message if exists
                messages.lastOrNull { it.role.name == "ASSISTANT" }?.let {
                    chatRepository.deleteMessage(it.id)
                }

                // Resend
                sendMessage(lastUserMessage.content, currentChart, savedCharts, selectedChartId)
            }
        }
    }

    /**
     * Clear conversation messages
     */
    fun clearConversation() {
        viewModelScope.launch {
            val conversationId = _currentConversationId.value ?: return@launch
            chatRepository.clearConversationMessages(conversationId)
        }
    }

    // ============================================
    // AGENT INITIALIZATION
    // ============================================

    private fun initializeAgent(
        currentChart: VedicChart?,
        savedCharts: List<SavedChart>,
        selectedChartId: Long?
    ) {
        stormyAgent = StormyAgent.getInstance(getApplication())
        // Context data (currentChart, savedCharts, selectedChartId) is passed
        // directly to processMessage() when sending messages
    }

    /**
     * Update agent context when profile changes
     */
    fun updateAgentContext(
        currentChart: VedicChart?,
        savedCharts: List<SavedChart>,
        selectedChartId: Long?
    ) {
        if (stormyAgent != null) {
            initializeAgent(currentChart, savedCharts, selectedChartId)
        }
    }

    // ============================================
    // ERROR HANDLING
    // ============================================

    fun clearError() {
        if (_uiState.value is ChatUiState.Error) {
            _uiState.value = ChatUiState.Idle
        }
    }
}

/**
 * Chat UI State
 */
sealed class ChatUiState {
    object Idle : ChatUiState()
    object Sending : ChatUiState()
    object Streaming : ChatUiState()
    data class Error(val message: String) : ChatUiState()
}
