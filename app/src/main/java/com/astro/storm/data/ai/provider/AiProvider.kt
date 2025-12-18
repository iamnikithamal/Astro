package com.astro.storm.data.ai.provider

import kotlinx.coroutines.flow.Flow

/**
 * Base interface for all AI providers.
 *
 * Implementations handle communication with various free AI APIs
 * (DeepInfra, PollinationsAI, etc.) following patterns from gpt4free.
 */
interface AiProvider {
    /**
     * Unique identifier for this provider
     */
    val providerId: String

    /**
     * Human-readable name for display
     */
    val displayName: String

    /**
     * Base URL for the provider's API
     */
    val baseUrl: String

    /**
     * Whether this provider is currently working/operational
     */
    val isWorking: Boolean

    /**
     * Whether this provider requires authentication
     */
    val requiresAuth: Boolean
        get() = false

    /**
     * Whether this provider supports streaming responses
     */
    val supportsStreaming: Boolean
        get() = true

    /**
     * Whether this provider supports system messages
     */
    val supportsSystemMessage: Boolean
        get() = true

    /**
     * Whether this provider supports message history
     */
    val supportsMessageHistory: Boolean
        get() = true

    /**
     * Default model to use if none specified
     */
    val defaultModel: String

    /**
     * List of available models for this provider
     */
    suspend fun getModels(): List<AiModel>

    /**
     * Send a chat completion request and receive streaming response
     *
     * @param messages The conversation messages
     * @param model The model to use (or null for default)
     * @param temperature Sampling temperature (0.0 - 2.0)
     * @param maxTokens Maximum tokens in response
     * @param stream Whether to stream the response
     * @return Flow of response chunks
     */
    fun chat(
        messages: List<ChatMessage>,
        model: String? = null,
        temperature: Float? = null,
        maxTokens: Int? = null,
        stream: Boolean = true
    ): Flow<ChatResponse>

    /**
     * Refresh the available models from the provider
     * @return true if refresh was successful
     */
    suspend fun refreshModels(): Boolean
}

/**
 * Represents an AI model from a provider
 */
data class AiModel(
    val id: String,
    val name: String,
    val providerId: String,
    val displayName: String = name,
    val description: String? = null,
    val maxTokens: Int? = null,
    val supportsVision: Boolean = false,
    val supportsTools: Boolean = false,
    val supportsReasoning: Boolean = false,
    val enabled: Boolean = true,
    val aliasName: String? = null
) {
    /**
     * Get the name to display to users
     */
    fun getDisplayNameOrAlias(): String = aliasName ?: displayName
}

/**
 * A message in a chat conversation
 */
data class ChatMessage(
    val role: MessageRole,
    val content: String,
    val name: String? = null,
    val toolCalls: List<ToolCall>? = null,
    val toolCallId: String? = null
)

/**
 * Role of a message sender
 */
enum class MessageRole {
    SYSTEM,
    USER,
    ASSISTANT,
    TOOL;

    fun toApiString(): String = name.lowercase()

    companion object {
        fun fromString(value: String): MessageRole {
            return entries.find { it.name.equals(value, ignoreCase = true) } ?: USER
        }
    }
}

/**
 * A tool call requested by the AI
 */
data class ToolCall(
    val id: String,
    val type: String = "function",
    val function: FunctionCall
)

/**
 * Function call details
 */
data class FunctionCall(
    val name: String,
    val arguments: String
)

/**
 * Response chunk from AI chat
 */
sealed class ChatResponse {
    /**
     * A text content chunk
     */
    data class Content(
        val text: String,
        val isComplete: Boolean = false
    ) : ChatResponse()

    /**
     * Reasoning/thinking content (for models that support it)
     */
    data class Reasoning(
        val text: String,
        val isComplete: Boolean = false
    ) : ChatResponse()

    /**
     * Tool call request from the AI
     */
    data class ToolCallRequest(
        val toolCalls: List<ToolCall>
    ) : ChatResponse()

    /**
     * Usage statistics
     */
    data class Usage(
        val promptTokens: Int,
        val completionTokens: Int,
        val totalTokens: Int
    ) : ChatResponse()

    /**
     * Error response
     */
    data class Error(
        val message: String,
        val code: String? = null,
        val isRetryable: Boolean = false
    ) : ChatResponse()

    /**
     * Completion signal
     */
    data class Done(
        val finishReason: String = "stop"
    ) : ChatResponse()

    /**
     * Provider information
     */
    data class ProviderInfo(
        val providerId: String,
        val model: String
    ) : ChatResponse()

    /**
     * Retry notification (when auto-retrying due to rate limits or errors)
     */
    data class RetryNotification(
        val attempt: Int,
        val maxAttempts: Int,
        val delayMs: Long,
        val reason: String
    ) : ChatResponse()
}

/**
 * Exception thrown by AI providers
 */
open class AiProviderException(
    message: String,
    val code: String? = null,
    open val isRetryable: Boolean = false,
    cause: Throwable? = null
) : Exception(message, cause)

/**
 * Rate limit exception
 */
class RateLimitException(
    message: String = "Rate limit exceeded",
    val retryAfterMs: Long? = null
) : AiProviderException(message, "rate_limit", isRetryable = true)

/**
 * Authentication exception
 */
class AuthenticationException(
    message: String = "Authentication required or failed"
) : AiProviderException(message, "auth_error", isRetryable = false)
