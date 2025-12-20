package com.astro.storm.data.repository

import android.content.Context
import com.astro.storm.data.ai.provider.AiModel
import com.astro.storm.data.ai.provider.AiProviderRegistry
import com.astro.storm.data.ai.provider.ChatMessage
import com.astro.storm.data.ai.provider.ChatResponse
import com.astro.storm.data.ai.provider.MessageRole
import com.astro.storm.data.local.ChartDatabase
import com.astro.storm.data.local.chat.ChatConversation
import com.astro.storm.data.local.chat.ChatDao
import com.astro.storm.data.local.chat.ChatMessageModel
import com.astro.storm.data.local.chat.ConversationEntity
import com.astro.storm.data.local.chat.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONArray

/**
 * Repository for chat-related data operations.
 *
 * Handles:
 * - Conversation CRUD operations
 * - Message management
 * - Integration with AI providers
 */
class ChatRepository private constructor(
    private val chatDao: ChatDao,
    private val providerRegistry: AiProviderRegistry
) {

    // ============================================
    // CONVERSATION OPERATIONS
    // ============================================

    /**
     * Get all active conversations
     */
    fun getAllConversations(): Flow<List<ChatConversation>> {
        return chatDao.getAllConversations().map { entities ->
            entities.map { entity ->
                val lastMessage = chatDao.getLastMessage(entity.id)
                ChatConversation.fromEntity(entity, lastMessage)
            }
        }
    }

    /**
     * Get conversation by ID
     */
    suspend fun getConversationById(conversationId: Long): ChatConversation? {
        val entity = chatDao.getConversationById(conversationId) ?: return null
        val lastMessage = chatDao.getLastMessage(conversationId)
        return ChatConversation.fromEntity(entity, lastMessage)
    }

    /**
     * Get conversation entity by ID
     */
    suspend fun getConversationEntityById(conversationId: Long): ConversationEntity? {
        return chatDao.getConversationById(conversationId)
    }

    /**
     * Create a new conversation
     */
    suspend fun createConversation(
        title: String = "New Chat",
        modelId: String,
        providerId: String,
        profileId: Long? = null,
        systemPromptOverride: String? = null
    ): Long {
        val entity = ConversationEntity(
            title = title,
            modelId = modelId,
            providerId = providerId,
            profileId = profileId,
            systemPromptOverride = systemPromptOverride,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        return chatDao.insertConversation(entity)
    }

    /**
     * Update conversation title
     */
    suspend fun updateConversationTitle(conversationId: Long, title: String) {
        chatDao.updateConversationTitle(conversationId, title)
    }

    /**
     * Update conversation model
     */
    suspend fun updateConversationModel(conversationId: Long, modelId: String, providerId: String) {
        chatDao.updateConversationModel(conversationId, modelId, providerId)
    }

    /**
     * Toggle conversation pinned state
     */
    suspend fun toggleConversationPinned(conversationId: Long) {
        val conversation = chatDao.getConversationById(conversationId) ?: return
        chatDao.updateConversationPinned(conversationId, !conversation.isPinned)
    }

    /**
     * Archive a conversation
     */
    suspend fun archiveConversation(conversationId: Long) {
        chatDao.updateConversationArchived(conversationId, true)
    }

    /**
     * Unarchive a conversation
     */
    suspend fun unarchiveConversation(conversationId: Long) {
        chatDao.updateConversationArchived(conversationId, false)
    }

    /**
     * Delete a conversation
     */
    suspend fun deleteConversation(conversationId: Long) {
        chatDao.deleteConversationById(conversationId)
    }

    /**
     * Search conversations
     */
    fun searchConversations(query: String): Flow<List<ChatConversation>> {
        return chatDao.searchConversations(query).map { entities ->
            entities.map { entity ->
                val lastMessage = chatDao.getLastMessage(entity.id)
                ChatConversation.fromEntity(entity, lastMessage)
            }
        }
    }

    // ============================================
    // MESSAGE OPERATIONS
    // ============================================

    /**
     * Get messages for a conversation
     */
    fun getMessagesForConversation(conversationId: Long): Flow<List<ChatMessageModel>> {
        return chatDao.getMessagesForConversation(conversationId).map { entities ->
            entities.map { ChatMessageModel.fromEntity(it) }
        }
    }

    /**
     * Get messages for a conversation (sync)
     */
    suspend fun getMessagesForConversationSync(conversationId: Long): List<ChatMessageModel> {
        return chatDao.getMessagesForConversationSync(conversationId).map {
            ChatMessageModel.fromEntity(it)
        }
    }

    /**
     * Add a user message to a conversation
     */
    suspend fun addUserMessage(
        conversationId: Long,
        content: String
    ): Long {
        val message = MessageEntity(
            conversationId = conversationId,
            role = MessageRole.USER.name,
            content = content,
            createdAt = System.currentTimeMillis()
        )
        val messageId = chatDao.insertMessage(message)
        updateConversationMessageCount(conversationId)
        return messageId
    }

    /**
     * Add an assistant message (placeholder for streaming)
     */
    suspend fun addAssistantMessagePlaceholder(
        conversationId: Long,
        modelId: String
    ): Long {
        val message = MessageEntity(
            conversationId = conversationId,
            role = MessageRole.ASSISTANT.name,
            content = "",
            modelId = modelId,
            isStreaming = true,
            createdAt = System.currentTimeMillis()
        )
        return chatDao.insertMessage(message)
    }

    /**
     * Update assistant message content during streaming
     */
    suspend fun updateAssistantMessageContent(
        messageId: Long,
        content: String,
        reasoningContent: String? = null,
        isStreaming: Boolean = true
    ) {
        if (reasoningContent != null) {
            chatDao.updateMessageWithReasoning(messageId, content, reasoningContent, isStreaming)
        } else {
            chatDao.updateMessageContent(messageId, content, isStreaming)
        }
    }

    /**
     * Finalize assistant message after streaming completes
     */
    suspend fun finalizeAssistantMessage(
        messageId: Long,
        content: String,
        reasoningContent: String? = null,
        toolsUsed: List<String>? = null,
        promptTokens: Int? = null,
        completionTokens: Int? = null,
        totalTokens: Int? = null,
        sectionsJson: String? = null
    ) {
        val message = chatDao.getMessageById(messageId) ?: return

        chatDao.updateMessage(
            message.copy(
                content = content,
                reasoningContent = reasoningContent,
                isStreaming = false,
                toolsUsedJson = toolsUsed?.let { JSONArray(it).toString() },
                promptTokens = promptTokens,
                completionTokens = completionTokens,
                totalTokens = totalTokens,
                sectionsJson = sectionsJson
            )
        )

        // Update conversation message count
        updateConversationMessageCount(message.conversationId)
    }

    /**
     * Set message error state
     */
    suspend fun setMessageError(messageId: Long, error: String) {
        chatDao.updateMessageError(messageId, error)
    }

    /**
     * Add a tool response message
     */
    suspend fun addToolResponseMessage(
        conversationId: Long,
        toolCallId: String,
        content: String
    ): Long {
        val message = MessageEntity(
            conversationId = conversationId,
            role = MessageRole.TOOL.name,
            content = content,
            toolCallId = toolCallId,
            createdAt = System.currentTimeMillis()
        )
        return chatDao.insertMessage(message)
    }

    /**
     * Delete a message
     */
    suspend fun deleteMessage(messageId: Long) {
        val message = chatDao.getMessageById(messageId) ?: return
        chatDao.deleteMessageById(messageId)
        updateConversationMessageCount(message.conversationId)
    }

    /**
     * Clear all messages in a conversation
     */
    suspend fun clearConversationMessages(conversationId: Long) {
        chatDao.deleteMessagesForConversation(conversationId)
        chatDao.updateConversationMessageCount(conversationId, 0)
    }

    private suspend fun updateConversationMessageCount(conversationId: Long) {
        val count = chatDao.getMessageCountForConversation(conversationId)
        chatDao.updateConversationMessageCount(conversationId, count)
    }

    // ============================================
    // AI PROVIDER OPERATIONS
    // ============================================

    /**
     * Get all enabled AI models
     */
    fun getEnabledModels(): Flow<List<AiModel>> = providerRegistry.enabledModels

    /**
     * Get default model
     */
    fun getDefaultModel(): AiModel? = providerRegistry.getDefaultModel()

    /**
     * Send a chat message and get streaming response
     */
    fun sendMessage(
        messages: List<ChatMessage>,
        model: String,
        providerId: String,
        temperature: Float? = null,
        maxTokens: Int? = null
    ): kotlinx.coroutines.flow.Flow<ChatResponse> {
        val provider = providerRegistry.getProvider(providerId)
            ?: throw IllegalArgumentException("Provider not found: $providerId")

        return provider.chat(
            messages = messages,
            model = model,
            temperature = temperature,
            maxTokens = maxTokens,
            stream = true
        )
    }

    /**
     * Convert conversation messages to ChatMessage format for API
     */
    suspend fun getConversationMessagesForApi(
        conversationId: Long,
        systemPrompt: String
    ): List<ChatMessage> {
        val messages = mutableListOf<ChatMessage>()

        // Add system prompt
        messages.add(ChatMessage(role = MessageRole.SYSTEM, content = systemPrompt))

        // Add conversation history
        val conversationMessages = getMessagesForConversationSync(conversationId)
        conversationMessages.forEach { msg ->
            messages.add(
                ChatMessage(
                    role = msg.role,
                    content = msg.content,
                    toolCallId = msg.toolCallId,
                    toolCalls = msg.toolCalls?.map { call ->
                        com.astro.storm.data.ai.provider.ToolCall(
                            id = call.id,
                            function = com.astro.storm.data.ai.provider.FunctionCall(
                                name = call.name,
                                arguments = call.arguments
                            )
                        )
                    }
                )
            )
        }

        return messages
    }

    /**
     * Generate a title for a conversation based on the first message
     */
    suspend fun generateConversationTitle(conversationId: Long): String {
        val messages = chatDao.getMessagesForConversationSync(conversationId)
        val firstUserMessage = messages.firstOrNull { it.role == MessageRole.USER.name }
            ?: return "New Chat"

        // Generate a short title from the first user message
        val content = firstUserMessage.content.trim()
        return when {
            content.length <= 40 -> content
            else -> {
                // Find a natural break point
                val truncated = content.take(40)
                val lastSpace = truncated.lastIndexOf(' ')
                if (lastSpace > 20) {
                    "${truncated.substring(0, lastSpace)}..."
                } else {
                    "${truncated}..."
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ChatRepository? = null

        fun getInstance(context: Context): ChatRepository {
            return INSTANCE ?: synchronized(this) {
                val database = ChartDatabase.getInstance(context)
                val registry = AiProviderRegistry.getInstance(context)
                ChatRepository(database.chatDao(), registry).also {
                    INSTANCE = it
                }
            }
        }
    }
}
