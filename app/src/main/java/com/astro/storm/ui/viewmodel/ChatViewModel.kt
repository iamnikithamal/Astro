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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

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

    // Current streaming message ID
    private var currentMessageId: Long? = null
    private var streamingJob: Job? = null

    // Raw content accumulators for proper cleaning
    private var rawContentAccumulator = StringBuilder()
    private var rawReasoningAccumulator = StringBuilder()

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

                // Reset accumulators
                rawContentAccumulator.clear()
                rawReasoningAccumulator.clear()

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

                                // Clean and update display content
                                val cleanedContent = ContentCleaner.cleanForDisplay(rawContentAccumulator.toString())
                                _streamingContent.value = cleanedContent

                                // Update AI status
                                if (!hasReceivedContent && cleanedContent.isNotEmpty()) {
                                    hasReceivedContent = true
                                    _aiStatus.value = AiStatus.Typing
                                }

                                // Update database periodically with cleaned content
                                currentMessageId?.let { msgId ->
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
                            is AgentResponse.ReasoningChunk -> {
                                // Accumulate raw reasoning
                                rawReasoningAccumulator.append(response.text)

                                // Clean and update display reasoning
                                val cleanedReasoning = ContentCleaner.cleanReasoning(rawReasoningAccumulator.toString())
                                if (cleanedReasoning.isNotEmpty()) {
                                    _streamingReasoning.value = cleanedReasoning

                                    // Update AI status to show reasoning
                                    if (_aiStatus.value == AiStatus.Thinking) {
                                        _aiStatus.value = AiStatus.Reasoning
                                    }

                                    // Update database with reasoning
                                    currentMessageId?.let { msgId ->
                                        val cleanedContent = ContentCleaner.cleanForDisplay(rawContentAccumulator.toString())
                                        chatRepository.updateAssistantMessageContent(
                                            messageId = msgId,
                                            content = cleanedContent,
                                            reasoningContent = cleanedReasoning,
                                            isStreaming = true
                                        )
                                    }
                                }
                            }
                            is AgentResponse.ToolCallsStarted -> {
                                _toolsInProgress.value = response.toolNames
                                toolsUsed.addAll(response.toolNames)
                                _aiStatus.value = AiStatus.ExecutingTools(response.toolNames)
                            }
                            is AgentResponse.ToolExecuting -> {
                                if (!_toolsInProgress.value.contains(response.toolName)) {
                                    _toolsInProgress.value = _toolsInProgress.value + response.toolName
                                }
                                if (!toolsUsed.contains(response.toolName)) {
                                    toolsUsed.add(response.toolName)
                                }
                                _aiStatus.value = AiStatus.CallingTool(response.toolName)
                            }
                            is AgentResponse.ToolResult -> {
                                _toolsInProgress.value = _toolsInProgress.value - response.toolName
                                if (_toolsInProgress.value.isEmpty()) {
                                    // Tools done, back to thinking for next iteration
                                    _aiStatus.value = AiStatus.Thinking
                                }
                            }
                            is AgentResponse.Complete -> {
                                // Clean final content
                                finalContent = ContentCleaner.cleanForDisplay(response.content)
                                finalReasoning = response.reasoning?.let {
                                    ContentCleaner.cleanReasoning(it)
                                }?.takeIf { it.isNotBlank() }
                                _aiStatus.value = AiStatus.Complete
                            }
                            is AgentResponse.Error -> {
                                _uiState.value = ChatUiState.Error(response.message)
                                _aiStatus.value = AiStatus.Idle
                                currentMessageId?.let { msgId ->
                                    chatRepository.setMessageError(msgId, response.message)
                                }
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
                currentMessageId = null

            } catch (e: Exception) {
                _isStreaming.value = false
                _toolsInProgress.value = emptyList()
                _aiStatus.value = AiStatus.Idle
                _uiState.value = ChatUiState.Error(e.message ?: "Unknown error occurred")

                currentMessageId?.let { msgId ->
                    chatRepository.setMessageError(msgId, e.message ?: "Unknown error")
                }
                currentMessageId = null
            }
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
