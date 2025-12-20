package com.astro.storm.data.local.chat

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.astro.storm.data.ai.provider.MessageRole
import org.json.JSONArray
import org.json.JSONObject

/**
 * Room Entity representing a chat conversation
 */
@Entity(tableName = "chat_conversations")
data class ConversationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /**
     * Title of the conversation (auto-generated or user-set)
     */
    val title: String,

    /**
     * Model ID used for this conversation
     */
    val modelId: String,

    /**
     * Provider ID for the model
     */
    val providerId: String,

    /**
     * ID of the profile associated with this conversation (if any)
     */
    val profileId: Long? = null,

    /**
     * Creation timestamp
     */
    val createdAt: Long = System.currentTimeMillis(),

    /**
     * Last updated timestamp
     */
    val updatedAt: Long = System.currentTimeMillis(),

    /**
     * Whether the conversation is pinned
     */
    val isPinned: Boolean = false,

    /**
     * Whether the conversation is archived
     */
    val isArchived: Boolean = false,

    /**
     * Custom system prompt override (if any)
     */
    val systemPromptOverride: String? = null,

    /**
     * Message count for quick display
     */
    val messageCount: Int = 0
)

/**
 * Room Entity representing a message in a conversation
 */
@Entity(
    tableName = "chat_messages",
    foreignKeys = [
        ForeignKey(
            entity = ConversationEntity::class,
            parentColumns = ["id"],
            childColumns = ["conversationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["conversationId"]),
        Index(value = ["createdAt"])
    ]
)
data class MessageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    /**
     * ID of the parent conversation
     */
    val conversationId: Long,

    /**
     * Message role (SYSTEM, USER, ASSISTANT, TOOL)
     */
    val role: String,

    /**
     * Message content
     */
    val content: String,

    /**
     * Reasoning/thinking content (for models that support it)
     */
    val reasoningContent: String? = null,

    /**
     * Tool calls JSON (for assistant messages requesting tools)
     */
    val toolCallsJson: String? = null,

    /**
     * Tool call ID (for tool response messages)
     */
    val toolCallId: String? = null,

    /**
     * Tools used in this message (for display)
     */
    val toolsUsedJson: String? = null,

    /**
     * Model ID that generated this message (for assistant messages)
     */
    val modelId: String? = null,

    /**
     * Creation timestamp
     */
    val createdAt: Long = System.currentTimeMillis(),

    /**
     * Whether this message is still being generated
     */
    val isStreaming: Boolean = false,

    /**
     * Error message if generation failed
     */
    val errorMessage: String? = null,

    /**
     * Token usage for this message
     */
    val promptTokens: Int? = null,
    val completionTokens: Int? = null,
    val totalTokens: Int? = null,

    /**
     * Serialized sections JSON for agentic message layout
     * Contains the dynamic section data (reasoning, tools, content, ask_user, etc.)
     */
    val sectionsJson: String? = null
)

/**
 * Domain model for a conversation with its last message
 */
data class ConversationWithPreview(
    val conversation: ConversationEntity,
    val lastMessage: MessageEntity?
)

/**
 * Domain model for UI display
 */
data class ChatConversation(
    val id: Long,
    val title: String,
    val modelId: String,
    val providerId: String,
    val profileId: Long?,
    val createdAt: Long,
    val updatedAt: Long,
    val isPinned: Boolean,
    val isArchived: Boolean,
    val messageCount: Int,
    val lastMessagePreview: String?
) {
    companion object {
        fun fromEntity(entity: ConversationEntity, lastMessage: MessageEntity?): ChatConversation {
            return ChatConversation(
                id = entity.id,
                title = entity.title,
                modelId = entity.modelId,
                providerId = entity.providerId,
                profileId = entity.profileId,
                createdAt = entity.createdAt,
                updatedAt = entity.updatedAt,
                isPinned = entity.isPinned,
                isArchived = entity.isArchived,
                messageCount = entity.messageCount,
                lastMessagePreview = lastMessage?.content?.take(100)
            )
        }
    }
}

/**
 * Domain model for a chat message
 */
data class ChatMessageModel(
    val id: Long,
    val conversationId: Long,
    val role: MessageRole,
    val content: String,
    val reasoningContent: String?,
    val toolCalls: List<ToolCallData>?,
    val toolCallId: String?,
    val toolsUsed: List<String>?,
    val modelId: String?,
    val createdAt: Long,
    val isStreaming: Boolean,
    val errorMessage: String?,
    val promptTokens: Int?,
    val completionTokens: Int?,
    val totalTokens: Int?,
    val sectionsJson: String?
) {
    companion object {
        fun fromEntity(entity: MessageEntity): ChatMessageModel {
            return ChatMessageModel(
                id = entity.id,
                conversationId = entity.conversationId,
                role = MessageRole.fromString(entity.role),
                content = entity.content,
                reasoningContent = entity.reasoningContent,
                toolCalls = parseToolCalls(entity.toolCallsJson),
                toolCallId = entity.toolCallId,
                toolsUsed = parseToolsUsed(entity.toolsUsedJson),
                modelId = entity.modelId,
                createdAt = entity.createdAt,
                isStreaming = entity.isStreaming,
                errorMessage = entity.errorMessage,
                promptTokens = entity.promptTokens,
                completionTokens = entity.completionTokens,
                totalTokens = entity.totalTokens,
                sectionsJson = entity.sectionsJson
            )
        }

        private fun parseToolCalls(json: String?): List<ToolCallData>? {
            if (json == null) return null
            return try {
                val array = JSONArray(json)
                List(array.length()) { i ->
                    val obj = array.getJSONObject(i)
                    val funcObj = obj.getJSONObject("function")
                    ToolCallData(
                        id = obj.getString("id"),
                        name = funcObj.getString("name"),
                        arguments = funcObj.getString("arguments")
                    )
                }
            } catch (e: Exception) {
                null
            }
        }

        private fun parseToolsUsed(json: String?): List<String>? {
            if (json == null) return null
            return try {
                val array = JSONArray(json)
                List(array.length()) { array.getString(it) }
            } catch (e: Exception) {
                null
            }
        }
    }

    fun toEntity(): MessageEntity {
        return MessageEntity(
            id = id,
            conversationId = conversationId,
            role = role.name,
            content = content,
            reasoningContent = reasoningContent,
            toolCallsJson = toolCalls?.let {
                JSONArray().apply {
                    it.forEach { call ->
                        put(JSONObject().apply {
                            put("id", call.id)
                            put("function", JSONObject().apply {
                                put("name", call.name)
                                put("arguments", call.arguments)
                            })
                        })
                    }
                }.toString()
            },
            toolCallId = toolCallId,
            toolsUsedJson = toolsUsed?.let { JSONArray(it).toString() },
            modelId = modelId,
            createdAt = createdAt,
            isStreaming = isStreaming,
            errorMessage = errorMessage,
            promptTokens = promptTokens,
            completionTokens = completionTokens,
            totalTokens = totalTokens,
            sectionsJson = sectionsJson
        )
    }
}

/**
 * Tool call data
 */
data class ToolCallData(
    val id: String,
    val name: String,
    val arguments: String
)
