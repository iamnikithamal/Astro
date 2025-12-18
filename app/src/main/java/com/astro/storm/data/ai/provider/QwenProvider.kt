package com.astro.storm.data.ai.provider

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.UUID
import javax.net.ssl.HttpsURLConnection

/**
 * Qwen AI Provider
 *
 * Provides free access to Alibaba's Qwen models through chat.qwen.ai.
 * This implementation mirrors gpt4free's Qwen provider.
 *
 * Features:
 * - Multiple Qwen models (Qwen3, QwQ, etc.)
 * - Streaming support
 * - Thinking/reasoning capability
 * - No API key required (uses browser-like auth)
 *
 * Note: This provider has rate limits and may require retries.
 */
class QwenProvider : AiProvider {

    override val providerId: String = "qwen"
    override val displayName: String = "Qwen"
    override val baseUrl: String = "https://chat.qwen.ai"
    override val isWorking: Boolean = true
    override val supportsStreaming: Boolean = true
    override val supportsSystemMessage: Boolean = true
    override val supportsMessageHistory: Boolean = false // Qwen uses single-message context

    override val defaultModel: String = "qwen3-235b-a22b"

    private val apiEndpoint = "https://chat.qwen.ai/api/v2/chat/completions"
    private val newChatEndpoint = "https://chat.qwen.ai/api/v2/chats/new"
    private val midTokenEndpoint = "https://sg-wum.alibaba.com/w/wu.json"

    // Cached authentication token
    @Volatile
    private var midToken: String? = null
    @Volatile
    private var midTokenUses: Int = 0
    private val maxMidTokenUses = 5 // Refresh after this many uses

    // Cached models
    private var cachedModels: List<AiModel>? = null

    /**
     * Models available on Qwen chat
     */
    private val availableModels = listOf(
        "qwen3-235b-a22b",       // Qwen 3 235B - Most capable
        "qwen3-max-preview",     // Qwen 3 Max Preview
        "qwen-max-latest",       // Qwen Max Latest
        "qwen-plus-2025-01-25",  // Qwen Plus
        "qwq-32b",               // QwQ 32B - Reasoning model
        "qwen-turbo-2025-02-11", // Qwen Turbo - Fast
        "qwen3-30b-a3b",         // Qwen 3 30B
        "qwen3-coder-plus",      // Qwen 3 Coder Plus
        "qwen2.5-72b-instruct",  // Qwen 2.5 72B
        "qwen2.5-coder-32b-instruct" // Qwen 2.5 Coder
    )

    /**
     * Reasoning models (support thinking)
     */
    private val reasoningModels = setOf(
        "qwq-32b",
        "qvq-72b-preview-0310"
    )

    /**
     * Vision models
     */
    private val visionModels = setOf(
        "qwen3-235b-a22b",
        "qwen3-max-preview",
        "qwen-max-latest",
        "qwen-plus-2025-01-25",
        "qwen2.5-vl-32b-instruct"
    )

    override suspend fun getModels(): List<AiModel> {
        return cachedModels ?: getDefaultModels().also { cachedModels = it }
    }

    override suspend fun refreshModels(): Boolean {
        cachedModels = getDefaultModels()
        return true
    }

    private fun getDefaultModels(): List<AiModel> = availableModels.map { modelId ->
        AiModel(
            id = modelId,
            name = formatDisplayName(modelId),
            providerId = providerId,
            displayName = formatDisplayName(modelId),
            description = getModelDescription(modelId),
            supportsVision = modelId in visionModels,
            supportsReasoning = modelId in reasoningModels,
            supportsTools = true
        )
    }

    private fun formatDisplayName(modelId: String): String {
        return modelId
            .replace("-", " ")
            .replace("qwen", "Qwen")
            .replace("qwq", "QwQ")
            .split(" ")
            .joinToString(" ") { word ->
                word.replaceFirstChar { it.uppercase() }
            }
    }

    private fun getModelDescription(modelId: String): String {
        return when {
            modelId.contains("235b") -> "Alibaba's largest Qwen 3 model (235B parameters)"
            modelId.contains("max") -> "Qwen Max - High capability model"
            modelId.contains("qwq") -> "QwQ - Reasoning model with thinking capability"
            modelId.contains("turbo") -> "Fast Qwen model for quick responses"
            modelId.contains("coder") -> "Qwen model optimized for coding"
            modelId.contains("plus") -> "Qwen Plus - Enhanced capability"
            else -> "Alibaba's Qwen AI model"
        }
    }

    override fun chat(
        messages: List<ChatMessage>,
        model: String?,
        temperature: Float?,
        maxTokens: Int?,
        stream: Boolean
    ): Flow<ChatResponse> = flow {
        val modelId = model ?: defaultModel

        emit(ChatResponse.ProviderInfo(providerId, modelId))

        var lastException: Exception? = null
        val maxRetries = 3

        for (attempt in 0 until maxRetries) {
            try {
                // Get or refresh mid token
                val token = getMidToken()
                if (token == null) {
                    emit(ChatResponse.Error("Failed to obtain authentication token", "auth_error"))
                    return@flow
                }

                // Get the last user message content
                val userContent = messages.lastOrNull { it.role == MessageRole.USER }?.content
                    ?: messages.lastOrNull()?.content
                    ?: ""

                // Create a new chat session
                val chatId = createChatSession(token, modelId) ?: run {
                    emit(ChatResponse.Error("Failed to create chat session", "session_error"))
                    return@flow
                }

                // Send the message and stream response
                streamChatResponse(
                    chatId = chatId,
                    modelId = modelId,
                    content = userContent,
                    token = token,
                    stream = stream,
                    enableThinking = modelId in reasoningModels
                ).collect { response ->
                    emit(response)
                }

                return@flow // Success

            } catch (e: RateLimitException) {
                lastException = e
                // Invalidate token and retry
                midToken = null
                midTokenUses = 0

                if (attempt < maxRetries - 1) {
                    val delayMs = (2000L * (1 shl attempt)).coerceAtMost(10000L)
                    emit(ChatResponse.RetryNotification(
                        attempt = attempt + 1,
                        maxAttempts = maxRetries,
                        delayMs = delayMs,
                        reason = "Rate limit exceeded"
                    ))
                    delay(delayMs)
                }
            } catch (e: Exception) {
                lastException = e
                if (attempt < maxRetries - 1 && isRetryableError(e)) {
                    val delayMs = (2000L * (1 shl attempt)).coerceAtMost(10000L)
                    emit(ChatResponse.RetryNotification(
                        attempt = attempt + 1,
                        maxAttempts = maxRetries,
                        delayMs = delayMs,
                        reason = e.message ?: "Connection error"
                    ))
                    delay(delayMs)
                } else {
                    break
                }
            }
        }

        // All retries failed
        emit(ChatResponse.Error(
            lastException?.message ?: "Request failed after $maxRetries attempts",
            "max_retries_exceeded",
            isRetryable = false
        ))
    }.flowOn(Dispatchers.IO)

    private fun isRetryableError(e: Exception): Boolean {
        return e is java.net.SocketTimeoutException ||
               e is java.net.ConnectException ||
               e.message?.contains("Connection reset", ignoreCase = true) == true
    }

    private suspend fun getMidToken(): String? = withContext(Dispatchers.IO) {
        // Check if we have a valid cached token
        val currentToken = midToken
        if (currentToken != null && midTokenUses < maxMidTokenUses) {
            midTokenUses++
            return@withContext currentToken
        }

        // Fetch new token
        try {
            val url = URL(midTokenEndpoint)
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().readText()

                // Extract token from response using regex
                // Pattern: umx.wu('token') or __fycb('token')
                val regex = Regex("""(?:umx\.wu|__fycb)\('([^']+)'\)""")
                val match = regex.find(response)
                if (match != null) {
                    val token = match.groupValues[1]
                    midToken = token
                    midTokenUses = 1
                    return@withContext token
                }
            }
            connection.disconnect()
        } catch (e: Exception) {
            // Failed to get token
        }

        null
    }

    private suspend fun createChatSession(token: String, modelId: String): String? = withContext(Dispatchers.IO) {
        try {
            val url = URL(newChatEndpoint)
            val connection = url.openConnection() as HttpsURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.connectTimeout = 15000
            connection.readTimeout = 15000

            applyHeaders(connection, token)

            val payload = JSONObject().apply {
                put("title", "New Chat")
                put("models", JSONArray().put(modelId))
                put("chat_mode", "normal")
                put("chat_type", "t2t")
                put("timestamp", System.currentTimeMillis())
            }

            connection.outputStream.use { os ->
                os.write(payload.toString().toByteArray(Charsets.UTF_8))
            }

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().readText()
                val json = JSONObject(response)
                if (json.optBoolean("success", false)) {
                    return@withContext json.optJSONObject("data")?.optString("id")
                }
            } else if (connection.responseCode == 429) {
                throw RateLimitException("Rate limit exceeded")
            }
            connection.disconnect()
        } catch (e: Exception) {
            if (e is RateLimitException) throw e
        }

        null
    }

    private fun streamChatResponse(
        chatId: String,
        modelId: String,
        content: String,
        token: String,
        stream: Boolean,
        enableThinking: Boolean
    ): Flow<ChatResponse> = flow {
        val messageId = UUID.randomUUID().toString()
        val url = URL("$apiEndpoint?chat_id=$chatId")
        val connection = url.openConnection() as HttpsURLConnection

        try {
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.connectTimeout = 30000
            connection.readTimeout = 120000

            applyHeaders(connection, token)

            val payload = JSONObject().apply {
                put("stream", stream)
                put("incremental_output", stream)
                put("chat_id", chatId)
                put("chat_mode", "normal")
                put("model", modelId)
                put("messages", JSONArray().put(JSONObject().apply {
                    put("fid", messageId)
                    put("role", "user")
                    put("content", content)
                    put("user_action", "chat")
                    put("files", JSONArray())
                    put("models", JSONArray().put(modelId))
                    put("chat_type", "t2t")
                    put("feature_config", JSONObject().apply {
                        put("thinking_enabled", enableThinking)
                        put("output_schema", "phase")
                        put("thinking_budget", 81920)
                    })
                }))
            }

            connection.outputStream.use { os ->
                os.write(payload.toString().toByteArray(Charsets.UTF_8))
            }

            val responseCode = connection.responseCode
            if (responseCode == 429) {
                throw RateLimitException("Rate limit exceeded")
            }

            if (responseCode != HttpURLConnection.HTTP_OK) {
                val errorBody = try {
                    connection.errorStream?.bufferedReader()?.readText() ?: "Unknown error"
                } catch (e: Exception) {
                    "Unknown error"
                }
                emit(ChatResponse.Error("Request failed: HTTP $responseCode - $errorBody", "http_error"))
                return@flow
            }

            val reader = BufferedReader(InputStreamReader(connection.inputStream, Charsets.UTF_8))
            val contentBuilder = StringBuilder()
            val reasoningBuilder = StringBuilder()
            var isThinking = false

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val trimmedLine = line?.trim() ?: continue
                if (trimmedLine.isEmpty()) continue

                // Skip non-data lines
                if (!trimmedLine.startsWith("data:")) continue

                val jsonStr = trimmedLine.removePrefix("data:").trim()
                if (jsonStr.isEmpty() || jsonStr == "[DONE]") {
                    if (jsonStr == "[DONE]") {
                        emit(ChatResponse.Done())
                    }
                    continue
                }

                try {
                    val chunk = JSONObject(jsonStr)

                    // Check for error
                    chunk.optJSONObject("error")?.let { error ->
                        val errorCode = error.optString("code", "")
                        val errorDetails = error.optString("details", "Unknown error")
                        if (errorCode == "RateLimited") {
                            throw RateLimitException(errorDetails)
                        }
                        emit(ChatResponse.Error("$errorCode: $errorDetails", errorCode))
                        return@flow
                    }

                    // Parse choices
                    val choices = chunk.optJSONArray("choices")
                    if (choices != null && choices.length() > 0) {
                        val delta = choices.getJSONObject(0).optJSONObject("delta")
                        if (delta != null) {
                            val phase = delta.optString("phase", "")
                            val deltaContent = delta.optString("content", "")

                            when (phase) {
                                "think" -> {
                                    if (!isThinking) {
                                        isThinking = true
                                    }
                                    if (deltaContent.isNotEmpty()) {
                                        reasoningBuilder.append(deltaContent)
                                        emit(ChatResponse.Reasoning(deltaContent))
                                    }
                                }
                                "answer" -> {
                                    if (isThinking) {
                                        isThinking = false
                                        // Emit final reasoning
                                        if (reasoningBuilder.isNotEmpty()) {
                                            emit(ChatResponse.Reasoning(reasoningBuilder.toString(), isComplete = true))
                                        }
                                    }
                                    if (deltaContent.isNotEmpty()) {
                                        contentBuilder.append(deltaContent)
                                        emit(ChatResponse.Content(deltaContent))
                                    }
                                }
                                else -> {
                                    // Regular content without phase
                                    if (deltaContent.isNotEmpty()) {
                                        contentBuilder.append(deltaContent)
                                        emit(ChatResponse.Content(deltaContent))
                                    }
                                }
                            }
                        }
                    }

                    // Parse usage
                    chunk.optJSONObject("usage")?.let { usage ->
                        emit(ChatResponse.Usage(
                            promptTokens = usage.optInt("prompt_tokens", 0),
                            completionTokens = usage.optInt("completion_tokens", 0),
                            totalTokens = usage.optInt("total_tokens", 0)
                        ))
                    }
                } catch (e: Exception) {
                    // Skip malformed chunks
                }
            }

            reader.close()

            // Final content emission
            if (contentBuilder.isNotEmpty()) {
                emit(ChatResponse.Content(contentBuilder.toString(), isComplete = true))
            }
            emit(ChatResponse.Done())

        } finally {
            connection.disconnect()
        }
    }.flowOn(Dispatchers.IO)

    private fun applyHeaders(connection: HttpURLConnection, token: String) {
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accept", "*/*")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/138.0.0.0 Safari/537.36")
        connection.setRequestProperty("Origin", baseUrl)
        connection.setRequestProperty("Referer", "$baseUrl/")
        connection.setRequestProperty("Source", "web")
        connection.setRequestProperty("bx-umidtoken", token)
        connection.setRequestProperty("bx-v", "2.5.31")
    }
}
