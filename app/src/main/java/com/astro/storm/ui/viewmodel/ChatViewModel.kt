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
import com.astro.storm.ui.components.agentic.AgentSection
import com.astro.storm.ui.components.agentic.AskUserOption
import com.astro.storm.ui.components.agentic.ProfileOperationStatus
import com.astro.storm.ui.components.agentic.ProfileOperationType
import com.astro.storm.ui.components.agentic.SectionedMessageSerializer
import com.astro.storm.ui.components.agentic.SectionedMessageState
import com.astro.storm.ui.components.agentic.TodoItem
import com.astro.storm.ui.components.agentic.ToolDisplayUtils
import com.astro.storm.ui.components.agentic.ToolExecution
import com.astro.storm.ui.components.agentic.ToolExecutionStatus
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.json.JSONArray
import org.json.JSONObject

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

    // Sectioned message state for dynamic agentic UI layout
    private val _sectionedMessageState = MutableStateFlow<SectionedMessageState?>(null)
    val sectionedMessageState: StateFlow<SectionedMessageState?> = _sectionedMessageState.asStateFlow()

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

    // Section tracking for dynamic agentic layout
    private var currentSections = mutableListOf<AgentSection>()
    private var currentReasoningSection: AgentSection.Reasoning? = null
    private var currentContentSection: AgentSection.Content? = null
    private var currentToolGroup: AgentSection.ToolGroup? = null
    private var currentTodoList: AgentSection.TodoList? = null
    private var activeTaskId: String? = null
    private var reasoningStartTime: Long = 0L

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

                // Reset section tracking for new message
                currentSections.clear()
                currentReasoningSection = null
                currentContentSection = null
                currentToolGroup = null
                currentTodoList = null
                activeTaskId = null
                reasoningStartTime = 0L

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
                    // Initialize sectioned message state
                    _sectionedMessageState.value = SectionedMessageState(
                        messageId = msgId,
                        sections = emptyList(),
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

                                // Mark reasoning as complete when content starts
                                if (currentReasoningSection != null && !currentReasoningSection!!.isComplete) {
                                    finalizeReasoningSection()
                                }

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

                                    // Update or create content section
                                    updateContentSection(cleanedContent, isComplete = false)

                                    // Update streaming message state (throttled)
                                    updateStreamingMessageState()
                                    updateSectionedMessageState()
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

                                // Track reasoning section start time
                                if (reasoningStartTime == 0L) {
                                    reasoningStartTime = System.currentTimeMillis()
                                }

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

                                        // Update or create reasoning section
                                        updateReasoningSection(cleanedReasoning, isComplete = false)

                                        // Update streaming message state
                                        updateStreamingMessageState()
                                        updateSectionedMessageState()
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
                                        displayName = ToolDisplayUtils.formatToolName(toolName),
                                        status = ToolStepStatus.PENDING
                                    )
                                    if (currentToolSteps.none { it.toolName == toolName }) {
                                        currentToolSteps.add(step)
                                    }
                                }

                                // Create or update tool group section
                                updateToolGroupSection(response.toolNames)
                                updateStreamingMessageState()
                                updateSectionedMessageState()
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
                                        displayName = ToolDisplayUtils.formatToolName(response.toolName),
                                        status = ToolStepStatus.EXECUTING
                                    ))
                                }

                                // Update sectioned UI
                                updateToolExecutionStatus(response.toolName, ToolExecutionStatus.EXECUTING)
                                updateStreamingMessageState()
                                updateSectionedMessageState()
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

                                // Update sectioned UI with tool result
                                updateToolExecutionStatus(
                                    toolName = response.toolName,
                                    status = if (response.success) ToolExecutionStatus.COMPLETED else ToolExecutionStatus.FAILED,
                                    result = response.summary
                                )

                                // Check for agentic tools and process their results
                                if (response.success && isAgenticTool(response.toolName)) {
                                    try {
                                        val resultJson = parseToolResultJson(response.summary)
                                        processAgenticToolResult(response.toolName, resultJson)
                                    } catch (e: Exception) {
                                        // Non-JSON result, ignore
                                    }
                                }

                                updateStreamingMessageState()
                                updateSectionedMessageState()
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

                                // Update final content section
                                if (finalContent.isNotEmpty()) {
                                    updateContentSection(finalContent, isComplete = true)
                                }

                                // Finalize all sections
                                finalizeSections()

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

                                // Update sectioned state with error
                                _sectionedMessageState.value = _sectionedMessageState.value?.copy(
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

                    // Serialize the sectioned state for persistence
                    val sectionsJsonToSave = _sectionedMessageState.value?.let { state ->
                        try {
                            SectionedMessageSerializer.serialize(state)
                        } catch (e: Exception) {
                            null
                        }
                    }

                    chatRepository.finalizeAssistantMessage(
                        messageId = msgId,
                        content = contentToSave,
                        reasoningContent = reasoningToSave,
                        toolsUsed = toolsUsed.distinct().takeIf { it.isNotEmpty() },
                        sectionsJson = sectionsJsonToSave
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
                _sectionedMessageState.value = null
                clearSectionTracking()
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
                _sectionedMessageState.value = null
                clearSectionTracking()
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

    // Tool name formatting now uses centralized ToolDisplayUtils.formatToolName()

    // ============================================
    // SECTION MANAGEMENT HELPERS
    // ============================================

    /**
     * Update or create reasoning section during streaming.
     * Called when reasoning content is received from the AI.
     */
    private fun updateReasoningSection(content: String, isComplete: Boolean) {
        val durationMs = if (reasoningStartTime > 0) {
            System.currentTimeMillis() - reasoningStartTime
        } else 0L

        if (currentReasoningSection == null) {
            // Create new reasoning section
            currentReasoningSection = AgentSection.Reasoning(
                content = content,
                isComplete = isComplete,
                isExpanded = false,
                durationMs = durationMs
            )
            currentSections.add(currentReasoningSection!!)
        } else {
            // Update existing reasoning section
            val index = currentSections.indexOfFirst { it.id == currentReasoningSection!!.id }
            if (index >= 0) {
                currentReasoningSection = currentReasoningSection!!.copy(
                    content = content,
                    isComplete = isComplete,
                    durationMs = durationMs
                )
                currentSections[index] = currentReasoningSection!!
            }
        }
    }

    /**
     * Finalize reasoning section when content starts streaming.
     * Marks reasoning as complete and calculates final duration.
     */
    private fun finalizeReasoningSection() {
        if (currentReasoningSection != null && !currentReasoningSection!!.isComplete) {
            val durationMs = if (reasoningStartTime > 0) {
                System.currentTimeMillis() - reasoningStartTime
            } else 0L

            val index = currentSections.indexOfFirst { it.id == currentReasoningSection!!.id }
            if (index >= 0) {
                currentReasoningSection = currentReasoningSection!!.copy(
                    isComplete = true,
                    durationMs = durationMs
                )
                currentSections[index] = currentReasoningSection!!
            }
        }
    }

    /**
     * Update or create content section during streaming.
     * Called when content text is received from the AI.
     */
    private fun updateContentSection(text: String, isComplete: Boolean) {
        if (currentContentSection == null) {
            // Create new content section
            currentContentSection = AgentSection.Content(
                text = text,
                isComplete = isComplete,
                isTyping = !isComplete && text.isNotEmpty()
            )
            currentSections.add(currentContentSection!!)
        } else {
            // Update existing content section
            val index = currentSections.indexOfFirst { it.id == currentContentSection!!.id }
            if (index >= 0) {
                currentContentSection = currentContentSection!!.copy(
                    text = text,
                    isComplete = isComplete,
                    isTyping = !isComplete && text.isNotEmpty()
                )
                currentSections[index] = currentContentSection!!
            }
        }
    }

    /**
     * Update or create tool group section when tools are called.
     * Creates a collapsible section showing all tool executions.
     */
    private fun updateToolGroupSection(toolNames: List<String>) {
        // Finalize any active reasoning before tools
        if (currentReasoningSection != null && !currentReasoningSection!!.isComplete) {
            finalizeReasoningSection()
        }

        // Build tool execution list from current tool steps
        val toolExecutions = toolNames.map { toolName ->
            val existingStep = currentToolSteps.find { it.toolName == toolName }
            ToolExecution(
                toolName = toolName,
                displayName = ToolDisplayUtils.formatToolName(toolName),
                status = when (existingStep?.status) {
                    ToolStepStatus.PENDING -> ToolExecutionStatus.PENDING
                    ToolStepStatus.EXECUTING -> ToolExecutionStatus.EXECUTING
                    ToolStepStatus.COMPLETED -> ToolExecutionStatus.COMPLETED
                    ToolStepStatus.FAILED -> ToolExecutionStatus.FAILED
                    null -> ToolExecutionStatus.PENDING
                },
                startTime = existingStep?.startTime ?: System.currentTimeMillis(),
                endTime = existingStep?.endTime,
                result = existingStep?.result
            )
        }

        if (currentToolGroup == null) {
            // Create new tool group section
            currentToolGroup = AgentSection.ToolGroup(
                tools = toolExecutions,
                isComplete = false,
                isExpanded = true
            )
            currentSections.add(currentToolGroup!!)
        } else {
            // Update existing tool group with new tools
            val index = currentSections.indexOfFirst { it.id == currentToolGroup!!.id }
            if (index >= 0) {
                // Merge existing tools with new ones
                val existingTools = currentToolGroup!!.tools.toMutableList()
                toolExecutions.forEach { newTool ->
                    val existingIndex = existingTools.indexOfFirst { it.toolName == newTool.toolName }
                    if (existingIndex >= 0) {
                        existingTools[existingIndex] = newTool
                    } else {
                        existingTools.add(newTool)
                    }
                }
                currentToolGroup = currentToolGroup!!.copy(tools = existingTools)
                currentSections[index] = currentToolGroup!!
            }
        }
    }

    /**
     * Update tool execution status within the tool group.
     * Called when a tool starts executing or completes.
     */
    private fun updateToolExecutionStatus(
        toolName: String,
        status: ToolExecutionStatus,
        result: String? = null,
        error: String? = null
    ) {
        if (currentToolGroup == null) return

        val index = currentSections.indexOfFirst { it.id == currentToolGroup!!.id }
        if (index >= 0) {
            val updatedTools = currentToolGroup!!.tools.map { tool ->
                if (tool.toolName == toolName) {
                    tool.copy(
                        status = status,
                        endTime = if (status == ToolExecutionStatus.COMPLETED || status == ToolExecutionStatus.FAILED) {
                            System.currentTimeMillis()
                        } else tool.endTime,
                        result = result ?: tool.result,
                        error = error ?: tool.error
                    )
                } else tool
            }

            // Check if all tools are complete
            val allComplete = updatedTools.all {
                it.status == ToolExecutionStatus.COMPLETED || it.status == ToolExecutionStatus.FAILED
            }

            currentToolGroup = currentToolGroup!!.copy(
                tools = updatedTools,
                isComplete = allComplete,
                isExpanded = !allComplete
            )
            currentSections[index] = currentToolGroup!!
        }
    }

    /**
     * Process agentic tool results from tool execution.
     * Handles special tools like start_task, finish_task, ask_user, update_todo.
     */
    private fun processAgenticToolResult(toolName: String, resultJson: JSONObject?) {
        if (resultJson == null) return

        when (toolName) {
            "start_task" -> {
                val taskName = resultJson.optString("task_name", "Analysis")
                activeTaskId = java.util.UUID.randomUUID().toString()

                val taskSection = AgentSection.TaskBoundary(
                    id = activeTaskId!!,
                    taskName = taskName,
                    isStart = true
                )
                currentSections.add(taskSection)

                // Reset content section for new task
                currentContentSection = null
            }

            "finish_task" -> {
                val taskName = resultJson.optString("task_name", "Analysis")
                val summary = resultJson.optString("summary", "")

                val taskSection = AgentSection.TaskBoundary(
                    taskName = taskName,
                    isStart = false,
                    summary = summary
                )
                currentSections.add(taskSection)

                activeTaskId = null
            }

            "ask_user" -> {
                val question = resultJson.optString("question", "")
                val optionsArray = resultJson.optJSONArray("options")
                val allowCustomInput = resultJson.optBoolean("allow_custom_input", true)

                val options = mutableListOf<AskUserOption>()
                optionsArray?.let { array ->
                    for (i in 0 until array.length()) {
                        val opt = array.optJSONObject(i)
                        if (opt != null) {
                            options.add(
                                AskUserOption(
                                    label = opt.optString("label", "Option ${i + 1}"),
                                    description = opt.optString("description", null),
                                    value = opt.optString("value", opt.optString("label", ""))
                                )
                            )
                        }
                    }
                }

                val askSection = AgentSection.AskUser(
                    question = question,
                    options = options,
                    allowCustomInput = allowCustomInput,
                    isAnswered = false
                )
                currentSections.add(askSection)
            }

            "update_todo" -> {
                val operation = resultJson.optString("operation", "replace")
                val title = resultJson.optString("title", "Analysis Steps")
                val itemsArray = resultJson.optJSONArray("items")

                val items = mutableListOf<String>()
                itemsArray?.let { array ->
                    for (i in 0 until array.length()) {
                        items.add(array.optString(i, ""))
                    }
                }

                when (operation) {
                    "add", "replace" -> {
                        val todoItems = items.map { text ->
                            TodoItem(
                                text = text,
                                isCompleted = false,
                                isInProgress = false
                            )
                        }

                        if (currentTodoList == null || operation == "replace") {
                            currentTodoList = AgentSection.TodoList(
                                title = title,
                                items = todoItems,
                                isExpanded = true
                            )
                            // Remove old todo list if replacing
                            if (operation == "replace") {
                                currentSections.removeAll { it is AgentSection.TodoList }
                            }
                            currentSections.add(currentTodoList!!)
                        } else {
                            // Add items to existing list
                            val index = currentSections.indexOfFirst { it.id == currentTodoList!!.id }
                            if (index >= 0) {
                                currentTodoList = currentTodoList!!.copy(
                                    items = currentTodoList!!.items + todoItems
                                )
                                currentSections[index] = currentTodoList!!
                            }
                        }
                    }

                    "complete" -> {
                        if (currentTodoList != null) {
                            val index = currentSections.indexOfFirst { it.id == currentTodoList!!.id }
                            if (index >= 0) {
                                val updatedItems = currentTodoList!!.items.mapIndexed { idx, item ->
                                    if (items.contains(idx.toString()) || items.contains(item.text)) {
                                        item.copy(isCompleted = true, isInProgress = false)
                                    } else item
                                }
                                currentTodoList = currentTodoList!!.copy(items = updatedItems)
                                currentSections[index] = currentTodoList!!
                            }
                        }
                    }

                    "set_in_progress" -> {
                        if (currentTodoList != null) {
                            val index = currentSections.indexOfFirst { it.id == currentTodoList!!.id }
                            if (index >= 0) {
                                val updatedItems = currentTodoList!!.items.mapIndexed { idx, item ->
                                    val shouldBeInProgress = items.contains(idx.toString()) || items.contains(item.text)
                                    item.copy(isInProgress = shouldBeInProgress && !item.isCompleted)
                                }
                                currentTodoList = currentTodoList!!.copy(items = updatedItems)
                                currentSections[index] = currentTodoList!!
                            }
                        }
                    }
                }
            }

            "create_profile", "update_profile" -> {
                val profileName = resultJson.optString("name", "Profile")
                val profileId = resultJson.optLong("profile_id", -1L)
                val status = resultJson.optString("status", "")

                val opType = if (toolName == "create_profile") {
                    ProfileOperationType.CREATE
                } else {
                    ProfileOperationType.UPDATE
                }

                val opStatus = when (status) {
                    "created", "updated" -> ProfileOperationStatus.SUCCESS
                    else -> ProfileOperationStatus.FAILED
                }

                val profileSection = AgentSection.ProfileOperation(
                    operation = opType,
                    profileName = profileName,
                    profileId = if (profileId > 0) profileId else null,
                    status = opStatus
                )
                currentSections.add(profileSection)
            }

            "delete_profile" -> {
                val profileName = resultJson.optString("name", "Profile")
                val status = resultJson.optString("status", "")

                val profileSection = AgentSection.ProfileOperation(
                    operation = ProfileOperationType.DELETE,
                    profileName = profileName,
                    status = if (status == "deleted") ProfileOperationStatus.SUCCESS else ProfileOperationStatus.FAILED
                )
                currentSections.add(profileSection)
            }

            "set_active_profile" -> {
                val profileName = resultJson.optString("name", "Profile")
                val profileId = resultJson.optLong("profile_id", -1L)

                val profileSection = AgentSection.ProfileOperation(
                    operation = ProfileOperationType.VIEW,
                    profileName = profileName,
                    profileId = if (profileId > 0) profileId else null,
                    status = ProfileOperationStatus.SUCCESS
                )
                currentSections.add(profileSection)
            }
        }
    }

    /**
     * Update the sectioned message state from current sections.
     * Called after any section modification to sync UI state.
     */
    private fun updateSectionedMessageState() {
        currentMessageId?.let { msgId ->
            _sectionedMessageState.value = SectionedMessageState(
                messageId = msgId,
                sections = currentSections.toList(),
                isComplete = false
            )
        }
    }

    /**
     * Finalize all sections when message is complete.
     * Marks content as complete and closes any open sections.
     */
    private fun finalizeSections() {
        // Finalize reasoning if still open
        if (currentReasoningSection != null && !currentReasoningSection!!.isComplete) {
            finalizeReasoningSection()
        }

        // Finalize content section
        if (currentContentSection != null) {
            val index = currentSections.indexOfFirst { it.id == currentContentSection!!.id }
            if (index >= 0) {
                currentContentSection = currentContentSection!!.copy(
                    isComplete = true,
                    isTyping = false
                )
                currentSections[index] = currentContentSection!!
            }
        }

        // Mark tool group as complete
        if (currentToolGroup != null) {
            val index = currentSections.indexOfFirst { it.id == currentToolGroup!!.id }
            if (index >= 0) {
                currentToolGroup = currentToolGroup!!.copy(
                    isComplete = true,
                    isExpanded = false
                )
                currentSections[index] = currentToolGroup!!
            }
        }

        // Update final state
        currentMessageId?.let { msgId ->
            _sectionedMessageState.value = SectionedMessageState(
                messageId = msgId,
                sections = currentSections.toList(),
                isComplete = true
            )
        }
    }

    /**
     * Clear all section tracking state.
     * Called when cancelling or resetting the conversation.
     */
    private fun clearSectionTracking() {
        currentSections.clear()
        currentReasoningSection = null
        currentContentSection = null
        currentToolGroup = null
        currentTodoList = null
        activeTaskId = null
        reasoningStartTime = 0L
        _sectionedMessageState.value = null
    }

    /**
     * Handle user response to an AskUser section.
     * Updates the section state and can trigger continuation.
     */
    fun handleAskUserResponse(sectionId: String, response: String) {
        val index = currentSections.indexOfFirst { it.id == sectionId }
        if (index >= 0) {
            val section = currentSections[index]
            if (section is AgentSection.AskUser) {
                currentSections[index] = section.copy(
                    isAnswered = true,
                    customResponse = response
                )
                updateSectionedMessageState()
            }
        }
    }

    /**
     * Handle user selection of an option in AskUser section.
     */
    fun handleAskUserOptionSelect(sectionId: String, option: AskUserOption) {
        val index = currentSections.indexOfFirst { it.id == sectionId }
        if (index >= 0) {
            val section = currentSections[index]
            if (section is AgentSection.AskUser) {
                currentSections[index] = section.copy(
                    isAnswered = true,
                    selectedOptionId = option.id
                )
                updateSectionedMessageState()
            }
        }
    }

    /**
     * Toggle expansion state of a section (for reasoning, tools, todo).
     */
    fun toggleSectionExpanded(sectionId: String) {
        val index = currentSections.indexOfFirst { it.id == sectionId }
        if (index >= 0) {
            val section = currentSections[index]
            currentSections[index] = when (section) {
                is AgentSection.Reasoning -> section.copy(isExpanded = !section.isExpanded)
                is AgentSection.ToolGroup -> section.copy(isExpanded = !section.isExpanded)
                is AgentSection.TodoList -> section.copy(isExpanded = !section.isExpanded)
                else -> section
            }
            updateSectionedMessageState()
        }
    }

    /**
     * Check if a tool is an agentic workflow tool that needs special handling.
     */
    private fun isAgenticTool(toolName: String): Boolean {
        return toolName in listOf(
            "start_task",
            "finish_task",
            "ask_user",
            "update_todo",
            "create_profile",
            "update_profile",
            "delete_profile",
            "set_active_profile"
        )
    }

    /**
     * Parse tool result summary to extract JSON data.
     * Tool results often contain JSON that we need to parse for agentic tools.
     */
    private fun parseToolResultJson(summary: String): JSONObject? {
        return try {
            // Try to parse the summary directly as JSON
            if (summary.trim().startsWith("{")) {
                JSONObject(summary)
            } else {
                // Look for JSON embedded in the summary
                val jsonMatch = Regex("""\{[^{}]*\}""").find(summary)
                jsonMatch?.let { JSONObject(it.value) }
            }
        } catch (e: Exception) {
            null
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
        _sectionedMessageState.value = null
        clearSectionTracking()

        // Mark current message as incomplete (no sectionsJson for cancelled messages)
        viewModelScope.launch {
            currentMessageId?.let { msgId ->
                val content = _streamingContent.value.ifEmpty { "Response cancelled" }
                chatRepository.finalizeAssistantMessage(
                    messageId = msgId,
                    content = content,
                    reasoningContent = _streamingReasoning.value.takeIf { it.isNotEmpty() },
                    sectionsJson = null // Cancelled messages don't persist section data
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
