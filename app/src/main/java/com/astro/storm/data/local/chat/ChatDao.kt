package com.astro.storm.data.local.chat

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for chat-related database operations
 */
@Dao
interface ChatDao {

    // ============================================
    // CONVERSATION OPERATIONS
    // ============================================

    /**
     * Get all conversations ordered by update time (newest first)
     * Excludes archived conversations
     */
    @Query("""
        SELECT * FROM chat_conversations
        WHERE isArchived = 0
        ORDER BY isPinned DESC, updatedAt DESC
    """)
    fun getAllConversations(): Flow<List<ConversationEntity>>

    /**
     * Get all conversations including archived
     */
    @Query("""
        SELECT * FROM chat_conversations
        ORDER BY isPinned DESC, updatedAt DESC
    """)
    fun getAllConversationsIncludingArchived(): Flow<List<ConversationEntity>>

    /**
     * Get archived conversations
     */
    @Query("""
        SELECT * FROM chat_conversations
        WHERE isArchived = 1
        ORDER BY updatedAt DESC
    """)
    fun getArchivedConversations(): Flow<List<ConversationEntity>>

    /**
     * Get conversation by ID
     */
    @Query("SELECT * FROM chat_conversations WHERE id = :conversationId")
    suspend fun getConversationById(conversationId: Long): ConversationEntity?

    /**
     * Get conversation by ID as Flow
     */
    @Query("SELECT * FROM chat_conversations WHERE id = :conversationId")
    fun getConversationByIdFlow(conversationId: Long): Flow<ConversationEntity?>

    /**
     * Get conversations for a specific profile
     */
    @Query("""
        SELECT * FROM chat_conversations
        WHERE profileId = :profileId AND isArchived = 0
        ORDER BY isPinned DESC, updatedAt DESC
    """)
    fun getConversationsForProfile(profileId: Long): Flow<List<ConversationEntity>>

    /**
     * Search conversations by title
     */
    @Query("""
        SELECT * FROM chat_conversations
        WHERE title LIKE '%' || :query || '%' AND isArchived = 0
        ORDER BY isPinned DESC, updatedAt DESC
    """)
    fun searchConversations(query: String): Flow<List<ConversationEntity>>

    /**
     * Insert a new conversation
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: ConversationEntity): Long

    /**
     * Update an existing conversation
     */
    @Update
    suspend fun updateConversation(conversation: ConversationEntity)

    /**
     * Delete a conversation (messages will cascade delete)
     */
    @Delete
    suspend fun deleteConversation(conversation: ConversationEntity)

    /**
     * Delete conversation by ID
     */
    @Query("DELETE FROM chat_conversations WHERE id = :conversationId")
    suspend fun deleteConversationById(conversationId: Long)

    /**
     * Update conversation title
     */
    @Query("UPDATE chat_conversations SET title = :title, updatedAt = :updatedAt WHERE id = :conversationId")
    suspend fun updateConversationTitle(conversationId: Long, title: String, updatedAt: Long = System.currentTimeMillis())

    /**
     * Update conversation pin state
     */
    @Query("UPDATE chat_conversations SET isPinned = :isPinned, updatedAt = :updatedAt WHERE id = :conversationId")
    suspend fun updateConversationPinned(conversationId: Long, isPinned: Boolean, updatedAt: Long = System.currentTimeMillis())

    /**
     * Update conversation archive state
     */
    @Query("UPDATE chat_conversations SET isArchived = :isArchived, updatedAt = :updatedAt WHERE id = :conversationId")
    suspend fun updateConversationArchived(conversationId: Long, isArchived: Boolean, updatedAt: Long = System.currentTimeMillis())

    /**
     * Update conversation message count
     */
    @Query("UPDATE chat_conversations SET messageCount = :count, updatedAt = :updatedAt WHERE id = :conversationId")
    suspend fun updateConversationMessageCount(conversationId: Long, count: Int, updatedAt: Long = System.currentTimeMillis())

    /**
     * Update conversation model
     */
    @Query("UPDATE chat_conversations SET modelId = :modelId, providerId = :providerId, updatedAt = :updatedAt WHERE id = :conversationId")
    suspend fun updateConversationModel(conversationId: Long, modelId: String, providerId: String, updatedAt: Long = System.currentTimeMillis())

    /**
     * Get conversation count
     */
    @Query("SELECT COUNT(*) FROM chat_conversations WHERE isArchived = 0")
    suspend fun getConversationCount(): Int

    // ============================================
    // MESSAGE OPERATIONS
    // ============================================

    /**
     * Get all messages for a conversation
     */
    @Query("SELECT * FROM chat_messages WHERE conversationId = :conversationId ORDER BY createdAt ASC")
    fun getMessagesForConversation(conversationId: Long): Flow<List<MessageEntity>>

    /**
     * Get all messages for a conversation (non-flow)
     */
    @Query("SELECT * FROM chat_messages WHERE conversationId = :conversationId ORDER BY createdAt ASC")
    suspend fun getMessagesForConversationSync(conversationId: Long): List<MessageEntity>

    /**
     * Get last N messages for a conversation
     */
    @Query("""
        SELECT * FROM chat_messages
        WHERE conversationId = :conversationId
        ORDER BY createdAt DESC
        LIMIT :limit
    """)
    suspend fun getLastMessages(conversationId: Long, limit: Int): List<MessageEntity>

    /**
     * Get last message in a conversation
     */
    @Query("""
        SELECT * FROM chat_messages
        WHERE conversationId = :conversationId
        ORDER BY createdAt DESC
        LIMIT 1
    """)
    suspend fun getLastMessage(conversationId: Long): MessageEntity?

    /**
     * Get message by ID
     */
    @Query("SELECT * FROM chat_messages WHERE id = :messageId")
    suspend fun getMessageById(messageId: Long): MessageEntity?

    /**
     * Insert a new message
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity): Long

    /**
     * Insert multiple messages
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>): List<Long>

    /**
     * Update a message
     */
    @Update
    suspend fun updateMessage(message: MessageEntity)

    /**
     * Delete a message
     */
    @Delete
    suspend fun deleteMessage(message: MessageEntity)

    /**
     * Delete message by ID
     */
    @Query("DELETE FROM chat_messages WHERE id = :messageId")
    suspend fun deleteMessageById(messageId: Long)

    /**
     * Delete all messages in a conversation
     */
    @Query("DELETE FROM chat_messages WHERE conversationId = :conversationId")
    suspend fun deleteMessagesForConversation(conversationId: Long)

    /**
     * Update message content (for streaming updates)
     */
    @Query("""
        UPDATE chat_messages
        SET content = :content, isStreaming = :isStreaming
        WHERE id = :messageId
    """)
    suspend fun updateMessageContent(
        messageId: Long,
        content: String,
        isStreaming: Boolean
    )

    /**
     * Update message with reasoning content
     */
    @Query("""
        UPDATE chat_messages
        SET content = :content, reasoningContent = :reasoningContent, isStreaming = :isStreaming
        WHERE id = :messageId
    """)
    suspend fun updateMessageWithReasoning(
        messageId: Long,
        content: String,
        reasoningContent: String?,
        isStreaming: Boolean
    )

    /**
     * Update message error state
     */
    @Query("UPDATE chat_messages SET errorMessage = :error, isStreaming = 0 WHERE id = :messageId")
    suspend fun updateMessageError(messageId: Long, error: String)

    /**
     * Update message tools used
     */
    @Query("UPDATE chat_messages SET toolsUsedJson = :toolsJson WHERE id = :messageId")
    suspend fun updateMessageToolsUsed(messageId: Long, toolsJson: String)

    /**
     * Update message token usage
     */
    @Query("""
        UPDATE chat_messages
        SET promptTokens = :promptTokens, completionTokens = :completionTokens, totalTokens = :totalTokens
        WHERE id = :messageId
    """)
    suspend fun updateMessageTokenUsage(
        messageId: Long,
        promptTokens: Int,
        completionTokens: Int,
        totalTokens: Int
    )

    /**
     * Get message count for a conversation
     */
    @Query("SELECT COUNT(*) FROM chat_messages WHERE conversationId = :conversationId")
    suspend fun getMessageCountForConversation(conversationId: Long): Int

    /**
     * Search messages by content
     */
    @Query("""
        SELECT * FROM chat_messages
        WHERE content LIKE '%' || :query || '%'
        ORDER BY createdAt DESC
    """)
    fun searchMessages(query: String): Flow<List<MessageEntity>>

    // ============================================
    // COMBINED OPERATIONS
    // ============================================

    /**
     * Get conversations with their last message preview
     */
    @Transaction
    @Query("""
        SELECT c.*,
               m.id as last_id,
               m.content as last_content,
               m.role as last_role,
               m.createdAt as last_createdAt
        FROM chat_conversations c
        LEFT JOIN (
            SELECT conversationId, MAX(createdAt) as maxCreatedAt
            FROM chat_messages
            GROUP BY conversationId
        ) latest ON c.id = latest.conversationId
        LEFT JOIN chat_messages m ON c.id = m.conversationId AND m.createdAt = latest.maxCreatedAt
        WHERE c.isArchived = 0
        ORDER BY c.isPinned DESC, c.updatedAt DESC
    """)
    fun getConversationsWithPreview(): Flow<List<ConversationWithLastMessage>>

    /**
     * Delete all conversations and messages
     */
    @Query("DELETE FROM chat_conversations")
    suspend fun deleteAllConversations()
}

/**
 * Data class for conversation with last message query result
 */
data class ConversationWithLastMessage(
    @Embedded
    val conversation: ConversationEntity,

    @Embedded(prefix = "last_")
    val lastMessage: LastMessagePreview?
)

/**
 * Preview of last message for display
 */
data class LastMessagePreview(
    val id: Long?,
    val content: String?,
    val role: String?,
    val createdAt: Long?
)
