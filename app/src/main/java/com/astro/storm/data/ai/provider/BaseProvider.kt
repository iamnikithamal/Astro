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
import javax.net.ssl.HttpsURLConnection

/**
 * Base implementation for OpenAI-compatible API providers.
 *
 * Many free AI providers (DeepInfra, PollinationsAI, etc.) use
 * OpenAI-compatible APIs, so this base class handles the common patterns.
 *
 * Features:
 * - Automatic retry with exponential backoff for rate limits
 * - Proper error handling and categorization
 * - Streaming and non-streaming support
 * - Tool calling support
 */
abstract class BaseOpenAiCompatibleProvider : AiProvider {

    protected abstract val apiBase: String

    protected open val chatEndpoint: String
        get() = "$apiBase/chat/completions"

    protected open val modelsEndpoint: String
        get() = "$apiBase/models"

    protected open val defaultHeaders: Map<String, String>
        get() = mapOf(
            "Content-Type" to "application/json",
            "Accept" to "text/event-stream",
            "User-Agent" to "AstroStorm/1.0"
        )

    protected open val connectTimeoutMs: Int = 30000
    protected open val readTimeoutMs: Int = 120000

    /**
     * Maximum number of retry attempts for rate-limited requests
     */
    protected open val maxRetries: Int = 3

    /**
     * Initial delay in milliseconds before first retry (exponential backoff)
     */
    protected open val initialRetryDelayMs: Long = 2000L

    /**
     * Maximum delay between retries in milliseconds
     */
    protected open val maxRetryDelayMs: Long = 30000L

    protected var cachedModels: List<AiModel>? = null

    override val baseUrl: String
        get() = apiBase

    /**
     * Model aliases mapping friendly names to full model IDs
     */
    protected open val modelAliases: Map<String, String> = emptyMap()

    /**
     * Resolve model alias to actual model ID
     */
    protected fun resolveModel(model: String?): String {
        if (model == null) return defaultModel
        return modelAliases[model] ?: model
    }

    /**
     * Get additional headers for requests (e.g., API keys)
     */
    protected open fun getAuthHeaders(): Map<String, String> = emptyMap()

    override suspend fun getModels(): List<AiModel> {
        return cachedModels ?: fetchModels().also { cachedModels = it }
    }

    override suspend fun refreshModels(): Boolean {
        return try {
            cachedModels = fetchModels()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Fetch models from the provider's API
     */
    protected open suspend fun fetchModels(): List<AiModel> = withContext(Dispatchers.IO) {
        try {
            val connection = createConnection(modelsEndpoint)
            connection.requestMethod = "GET"

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().readText()
                parseModelsResponse(response)
            } else {
                getDefaultModels()
            }
        } catch (e: Exception) {
            getDefaultModels()
        }
    }

    /**
     * Parse models response from API
     */
    protected open fun parseModelsResponse(response: String): List<AiModel> {
        val models = mutableListOf<AiModel>()
        try {
            val json = JSONObject(response)
            val data = json.optJSONArray("data") ?: json.optJSONArray("models")
            if (data != null) {
                for (i in 0 until data.length()) {
                    val modelJson = data.getJSONObject(i)
                    val id = modelJson.optString("id", modelJson.optString("name", ""))
                    if (id.isNotEmpty()) {
                        models.add(
                            AiModel(
                                id = id,
                                name = id,
                                providerId = providerId,
                                displayName = formatModelDisplayName(id),
                                supportsVision = modelJson.optBoolean("vision", false),
                                supportsTools = modelJson.optBoolean("tools", false)
                            )
                        )
                    }
                }
            }
        } catch (e: Exception) {
            // Fall back to default models
        }
        return models.ifEmpty { getDefaultModels() }
    }

    /**
     * Get default/fallback models for this provider
     */
    protected abstract fun getDefaultModels(): List<AiModel>

    /**
     * Format model ID into a user-friendly display name
     */
    protected open fun formatModelDisplayName(modelId: String): String {
        return modelId
            .substringAfterLast("/")
            .replace("-", " ")
            .replace("_", " ")
            .split(" ")
            .joinToString(" ") { word ->
                word.replaceFirstChar { it.uppercase() }
            }
    }

    override fun chat(
        messages: List<ChatMessage>,
        model: String?,
        temperature: Float?,
        maxTokens: Int?,
        stream: Boolean
    ): Flow<ChatResponse> = flow {
        var lastException: Exception? = null
        var retryCount = 0

        while (retryCount <= maxRetries) {
            try {
                emit(ChatResponse.ProviderInfo(providerId, resolveModel(model)))

                val requestBody = buildRequestBody(messages, model, temperature, maxTokens, stream)
                val connection = createConnection(chatEndpoint)

                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.outputStream.use { os ->
                    os.write(requestBody.toByteArray(Charsets.UTF_8))
                }

                val responseCode = connection.responseCode
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    val errorBody = try {
                        connection.errorStream?.bufferedReader()?.readText() ?: "Unknown error"
                    } catch (e: Exception) {
                        "Error reading response: ${e.message}"
                    }
                    connection.disconnect()

                    // Check if retryable
                    if (responseCode == 429 && retryCount < maxRetries) {
                        // Rate limit - retry with exponential backoff
                        val delayMs = calculateRetryDelay(retryCount)
                        emit(ChatResponse.RetryNotification(
                            attempt = retryCount + 1,
                            maxAttempts = maxRetries,
                            delayMs = delayMs,
                            reason = "Rate limit exceeded"
                        ))
                        delay(delayMs)
                        retryCount++
                        continue
                    } else if ((responseCode == 500 || responseCode == 502 || responseCode == 503 || responseCode == 504) && retryCount < maxRetries) {
                        // Server error - retry with exponential backoff
                        val delayMs = calculateRetryDelay(retryCount)
                        emit(ChatResponse.RetryNotification(
                            attempt = retryCount + 1,
                            maxAttempts = maxRetries,
                            delayMs = delayMs,
                            reason = "Server error (HTTP $responseCode)"
                        ))
                        delay(delayMs)
                        retryCount++
                        continue
                    } else if (responseCode == 402) {
                        // Payment required - this model requires premium access
                        throw AiProviderException(
                            "This model requires premium access. Please select a different model.",
                            "payment_required",
                            isRetryable = false
                        )
                    }

                    handleErrorResponse(responseCode, errorBody)
                    return@flow
                }

                if (stream) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream, Charsets.UTF_8))
                    var line: String?
                    val contentBuilder = StringBuilder()
                    val reasoningBuilder = StringBuilder()

                    while (reader.readLine().also { line = it } != null) {
                        val trimmedLine = line?.trim() ?: continue
                        if (trimmedLine.isEmpty() || trimmedLine == "data: [DONE]") {
                            if (trimmedLine == "data: [DONE]") {
                                if (contentBuilder.isNotEmpty()) {
                                    emit(ChatResponse.Content(contentBuilder.toString(), isComplete = true))
                                }
                                emit(ChatResponse.Done())
                            }
                            continue
                        }

                        if (trimmedLine.startsWith("data: ")) {
                            val jsonStr = trimmedLine.removePrefix("data: ")
                            try {
                                val chunk = parseStreamChunk(jsonStr)
                                chunk?.let { response ->
                                    when (response) {
                                        is ChatResponse.Content -> {
                                            contentBuilder.append(response.text)
                                            emit(response)
                                        }
                                        is ChatResponse.Reasoning -> {
                                            reasoningBuilder.append(response.text)
                                            emit(response)
                                        }
                                        else -> emit(response)
                                    }
                                }
                            } catch (e: Exception) {
                                // Skip malformed chunks
                            }
                        }
                    }
                    reader.close()
                } else {
                    // Non-streaming response
                    val responseBody = connection.inputStream.bufferedReader().readText()
                    val responses = parseNonStreamResponse(responseBody)
                    responses.forEach { emit(it) }
                }

                connection.disconnect()
                return@flow // Success, exit the loop

            } catch (e: RateLimitException) {
                lastException = e
                if (retryCount < maxRetries) {
                    val delayMs = calculateRetryDelay(retryCount)
                    emit(ChatResponse.RetryNotification(
                        attempt = retryCount + 1,
                        maxAttempts = maxRetries,
                        delayMs = delayMs,
                        reason = "Rate limit exceeded"
                    ))
                    delay(delayMs)
                    retryCount++
                } else {
                    emit(ChatResponse.Error(
                        "Rate limit exceeded after $maxRetries retries. Please try again later.",
                        "rate_limit",
                        isRetryable = false
                    ))
                    return@flow
                }
            } catch (e: AuthenticationException) {
                emit(ChatResponse.Error(e.message ?: "Authentication failed", "auth_error", isRetryable = false))
                return@flow
            } catch (e: AiProviderException) {
                if (e.isRetryable && retryCount < maxRetries) {
                    lastException = e
                    val delayMs = calculateRetryDelay(retryCount)
                    emit(ChatResponse.RetryNotification(
                        attempt = retryCount + 1,
                        maxAttempts = maxRetries,
                        delayMs = delayMs,
                        reason = e.message ?: "Provider error"
                    ))
                    delay(delayMs)
                    retryCount++
                } else {
                    emit(ChatResponse.Error(e.message ?: "Provider error", e.code, e.isRetryable))
                    return@flow
                }
            } catch (e: Exception) {
                // Network or other transient errors may be retryable
                lastException = e
                if (retryCount < maxRetries && isRetryableException(e)) {
                    val delayMs = calculateRetryDelay(retryCount)
                    emit(ChatResponse.RetryNotification(
                        attempt = retryCount + 1,
                        maxAttempts = maxRetries,
                        delayMs = delayMs,
                        reason = e.message ?: "Connection error"
                    ))
                    delay(delayMs)
                    retryCount++
                } else {
                    emit(ChatResponse.Error(e.message ?: "Unknown error", "unknown", isRetryable = false))
                    return@flow
                }
            }
        }

        // If we exit the loop without returning, all retries failed
        emit(ChatResponse.Error(
            lastException?.message ?: "Request failed after $maxRetries retries",
            "max_retries_exceeded",
            isRetryable = false
        ))
    }.flowOn(Dispatchers.IO)

    /**
     * Calculate retry delay with exponential backoff and jitter
     */
    protected fun calculateRetryDelay(retryCount: Int): Long {
        val exponentialDelay = initialRetryDelayMs * (1L shl retryCount) // 2^retryCount
        val withJitter = exponentialDelay + (Math.random() * exponentialDelay * 0.1).toLong()
        return withJitter.coerceAtMost(maxRetryDelayMs)
    }

    /**
     * Check if an exception is retryable (network errors, timeouts, etc.)
     */
    protected fun isRetryableException(e: Exception): Boolean {
        return e is java.net.SocketTimeoutException ||
               e is java.net.ConnectException ||
               e is java.net.UnknownHostException ||
               e is java.io.EOFException ||
               e.message?.contains("Connection reset", ignoreCase = true) == true ||
               e.message?.contains("timeout", ignoreCase = true) == true
    }

    /**
     * Build the JSON request body
     */
    protected open fun buildRequestBody(
        messages: List<ChatMessage>,
        model: String?,
        temperature: Float?,
        maxTokens: Int?,
        stream: Boolean
    ): String {
        val json = JSONObject().apply {
            put("model", resolveModel(model))
            put("messages", buildMessagesArray(messages))
            put("stream", stream)
            temperature?.let { put("temperature", it.toDouble()) }
            maxTokens?.let { put("max_tokens", it) }
        }
        return json.toString()
    }

    /**
     * Build the messages array for the request
     */
    protected open fun buildMessagesArray(messages: List<ChatMessage>): JSONArray {
        return JSONArray().apply {
            messages.forEach { message ->
                put(JSONObject().apply {
                    put("role", message.role.toApiString())
                    put("content", message.content)
                    message.name?.let { put("name", it) }
                    message.toolCallId?.let { put("tool_call_id", it) }
                    message.toolCalls?.let { calls ->
                        put("tool_calls", JSONArray().apply {
                            calls.forEach { call ->
                                put(JSONObject().apply {
                                    put("id", call.id)
                                    put("type", call.type)
                                    put("function", JSONObject().apply {
                                        put("name", call.function.name)
                                        put("arguments", call.function.arguments)
                                    })
                                })
                            }
                        })
                    }
                })
            }
        }
    }

    /**
     * Parse a streaming response chunk
     */
    protected open fun parseStreamChunk(jsonStr: String): ChatResponse? {
        try {
            val json = JSONObject(jsonStr)

            // Check for error
            json.optJSONObject("error")?.let { error ->
                val message = error.optString("message", "Unknown error")
                val code = error.optString("code", "error")
                return ChatResponse.Error(message, code)
            }

            // Parse choices
            val choices = json.optJSONArray("choices")
            if (choices != null && choices.length() > 0) {
                val choice = choices.getJSONObject(0)
                val delta = choice.optJSONObject("delta")
                val finishReason = choice.optString("finish_reason", null)

                if (finishReason != null && finishReason != "null") {
                    return ChatResponse.Done(finishReason)
                }

                if (delta != null) {
                    // Check for reasoning content (for models like DeepSeek R1)
                    val reasoningContent = delta.optString("reasoning_content", null)
                        ?: delta.optString("reasoning", null)
                    if (!reasoningContent.isNullOrEmpty()) {
                        return ChatResponse.Reasoning(reasoningContent)
                    }

                    // Regular content
                    val content = delta.optString("content", null)
                    if (!content.isNullOrEmpty()) {
                        return ChatResponse.Content(content)
                    }

                    // Tool calls
                    val toolCalls = delta.optJSONArray("tool_calls")
                    if (toolCalls != null && toolCalls.length() > 0) {
                        val calls = mutableListOf<ToolCall>()
                        for (i in 0 until toolCalls.length()) {
                            val callJson = toolCalls.getJSONObject(i)
                            val funcJson = callJson.optJSONObject("function")
                            if (funcJson != null) {
                                calls.add(
                                    ToolCall(
                                        id = callJson.optString("id", ""),
                                        type = callJson.optString("type", "function"),
                                        function = FunctionCall(
                                            name = funcJson.optString("name", ""),
                                            arguments = funcJson.optString("arguments", "")
                                        )
                                    )
                                )
                            }
                        }
                        if (calls.isNotEmpty()) {
                            return ChatResponse.ToolCallRequest(calls)
                        }
                    }
                }
            }

            // Parse usage
            val usage = json.optJSONObject("usage")
            if (usage != null) {
                return ChatResponse.Usage(
                    promptTokens = usage.optInt("prompt_tokens", 0),
                    completionTokens = usage.optInt("completion_tokens", 0),
                    totalTokens = usage.optInt("total_tokens", 0)
                )
            }
        } catch (e: Exception) {
            // Malformed JSON, skip
        }
        return null
    }

    /**
     * Parse a non-streaming response
     */
    protected open fun parseNonStreamResponse(responseBody: String): List<ChatResponse> {
        val responses = mutableListOf<ChatResponse>()
        try {
            val json = JSONObject(responseBody)

            // Check for error
            json.optJSONObject("error")?.let { error ->
                val message = error.optString("message", "Unknown error")
                val code = error.optString("code", "error")
                responses.add(ChatResponse.Error(message, code))
                return responses
            }

            // Parse choices
            val choices = json.optJSONArray("choices")
            if (choices != null && choices.length() > 0) {
                val choice = choices.getJSONObject(0)
                val message = choice.optJSONObject("message")

                if (message != null) {
                    // Check for reasoning content
                    val reasoningContent = message.optString("reasoning_content", null)
                        ?: message.optString("reasoning", null)
                    if (!reasoningContent.isNullOrEmpty()) {
                        responses.add(ChatResponse.Reasoning(reasoningContent, isComplete = true))
                    }

                    // Regular content
                    val content = message.optString("content", null)
                    if (!content.isNullOrEmpty()) {
                        responses.add(ChatResponse.Content(content, isComplete = true))
                    }

                    // Tool calls
                    val toolCalls = message.optJSONArray("tool_calls")
                    if (toolCalls != null && toolCalls.length() > 0) {
                        val calls = mutableListOf<ToolCall>()
                        for (i in 0 until toolCalls.length()) {
                            val callJson = toolCalls.getJSONObject(i)
                            val funcJson = callJson.optJSONObject("function")
                            if (funcJson != null) {
                                calls.add(
                                    ToolCall(
                                        id = callJson.optString("id", ""),
                                        type = callJson.optString("type", "function"),
                                        function = FunctionCall(
                                            name = funcJson.optString("name", ""),
                                            arguments = funcJson.optString("arguments", "")
                                        )
                                    )
                                )
                            }
                        }
                        if (calls.isNotEmpty()) {
                            responses.add(ChatResponse.ToolCallRequest(calls))
                        }
                    }
                }

                val finishReason = choice.optString("finish_reason", "stop")
                responses.add(ChatResponse.Done(finishReason))
            }

            // Parse usage
            val usage = json.optJSONObject("usage")
            if (usage != null) {
                responses.add(
                    ChatResponse.Usage(
                        promptTokens = usage.optInt("prompt_tokens", 0),
                        completionTokens = usage.optInt("completion_tokens", 0),
                        totalTokens = usage.optInt("total_tokens", 0)
                    )
                )
            }
        } catch (e: Exception) {
            responses.add(ChatResponse.Error(e.message ?: "Failed to parse response", "parse_error"))
        }
        return responses
    }

    /**
     * Handle error HTTP responses
     */
    protected open fun handleErrorResponse(responseCode: Int, errorBody: String) {
        when (responseCode) {
            429 -> throw RateLimitException("Rate limit exceeded. Please try again later.")
            401, 403 -> throw AuthenticationException("Authentication failed or access denied.")
            400 -> {
                try {
                    val json = JSONObject(errorBody)
                    val error = json.optJSONObject("error")
                    val message = error?.optString("message") ?: errorBody
                    throw AiProviderException(message, "bad_request", isRetryable = false)
                } catch (e: Exception) {
                    throw AiProviderException("Bad request: $errorBody", "bad_request", isRetryable = false)
                }
            }
            500, 502, 503, 504 -> throw AiProviderException(
                "Server error (HTTP $responseCode). Please try again.",
                "server_error",
                isRetryable = true
            )
            else -> throw AiProviderException(
                "Request failed with HTTP $responseCode: $errorBody",
                "http_error",
                isRetryable = responseCode >= 500
            )
        }
    }

    /**
     * Create an HTTP connection with proper settings
     */
    protected open fun createConnection(endpoint: String): HttpURLConnection {
        val url = URL(endpoint)
        val connection = if (url.protocol == "https") {
            url.openConnection() as HttpsURLConnection
        } else {
            url.openConnection() as HttpURLConnection
        }

        connection.connectTimeout = connectTimeoutMs
        connection.readTimeout = readTimeoutMs

        // Apply default headers
        defaultHeaders.forEach { (key, value) ->
            connection.setRequestProperty(key, value)
        }

        // Apply auth headers
        getAuthHeaders().forEach { (key, value) ->
            connection.setRequestProperty(key, value)
        }

        return connection
    }
}
