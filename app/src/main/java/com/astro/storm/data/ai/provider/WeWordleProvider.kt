package com.astro.storm.data.ai.provider

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * WeWordle AI Provider
 *
 * Free provider based on gpt4free's WeWordle implementation.
 * Provides access to GPT-4 class models without authentication.
 *
 * API Endpoint: https://wewordle.org/gptapi/v1/web/turbo
 * Website: https://chat-gpt.com
 *
 * Features:
 * - OpenAI-compatible API format
 * - System message support
 * - Message history support
 * - Automatic retry with backoff
 * - No authentication required
 */
class WeWordleProvider : AiProvider {

    override val providerId: String = "wewordle"
    override val displayName: String = "WeWordle"
    override val baseUrl: String = "https://chat-gpt.com"
    override val isWorking: Boolean = true
    override val requiresAuth: Boolean = false
    override val supportsStreaming: Boolean = true
    override val supportsSystemMessage: Boolean = true
    override val supportsMessageHistory: Boolean = true

    override val defaultModel: String = "gpt-4"

    private val apiEndpoint = "https://wewordle.org/gptapi/v1/web/turbo"

    private val maxRetries = 3
    private val initialRetryDelayMs = 5000L
    private val maxRetryDelayMs = 60000L
    private val postRequestDelayMs = 1000L

    private var cachedModels: List<AiModel>? = null

    override suspend fun getModels(): List<AiModel> {
        return cachedModels ?: getDefaultModels().also { cachedModels = it }
    }

    override suspend fun refreshModels(): Boolean {
        cachedModels = getDefaultModels()
        return true
    }

    private fun getDefaultModels(): List<AiModel> = listOf(
        AiModel(
            id = "gpt-4",
            name = "GPT-4",
            providerId = providerId,
            displayName = "GPT-4 (WeWordle)",
            description = "Free GPT-4 class model via WeWordle - reliable and fast",
            supportsTools = false,
            supportsVision = false,
            supportsReasoning = false
        )
    )

    override fun chat(
        messages: List<ChatMessage>,
        model: String?,
        temperature: Float?,
        maxTokens: Int?,
        stream: Boolean
    ): Flow<ChatResponse> = flow {
        var retries = 0
        var currentDelay = initialRetryDelayMs

        while (retries <= maxRetries) {
            try {
                emit(ChatResponse.ProviderInfo(providerId, model ?: defaultModel))

                // Build messages array in OpenAI format
                val messagesArray = JSONArray()
                for (message in messages) {
                    messagesArray.put(JSONObject().apply {
                        put("role", message.role.toApiString())
                        put("content", message.content)
                    })
                }

                // Build request body
                val requestBody = JSONObject().apply {
                    put("messages", messagesArray)
                    put("model", model ?: defaultModel)
                }

                val connection = createConnection()
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.outputStream.use { os ->
                    os.write(requestBody.toString().toByteArray(Charsets.UTF_8))
                }

                val responseCode = connection.responseCode

                // Handle rate limiting
                if (responseCode == 429) {
                    if (retries < maxRetries) {
                        emit(ChatResponse.RetryNotification(
                            attempt = retries + 1,
                            maxAttempts = maxRetries,
                            delayMs = currentDelay,
                            reason = "Rate limit exceeded"
                        ))
                        delay(currentDelay)
                        retries++
                        currentDelay = (currentDelay * 2).coerceAtMost(maxRetryDelayMs)
                        connection.disconnect()
                        continue
                    }
                }

                if (responseCode != HttpURLConnection.HTTP_OK) {
                    val errorBody = try {
                        connection.errorStream?.bufferedReader()?.readText() ?: "Unknown error"
                    } catch (e: Exception) {
                        "Error reading response: ${e.message}"
                    }
                    connection.disconnect()
                    emit(ChatResponse.Error("Request failed with HTTP $responseCode: $errorBody", "http_error"))
                    return@flow
                }

                // Read response
                val contentBuilder = StringBuilder()
                val transferEncoding = connection.getHeaderField("Transfer-Encoding")
                val contentType = connection.contentType

                if (transferEncoding == "chunked" || contentType?.contains("event-stream") == true) {
                    // Stream response
                    val reader = BufferedReader(InputStreamReader(connection.inputStream, Charsets.UTF_8))
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        val text = line ?: continue
                        if (text.isNotEmpty()) {
                            // Try to parse as JSON first
                            try {
                                val json = JSONObject(text)
                                val content = extractContent(json)
                                if (content.isNotEmpty()) {
                                    contentBuilder.append(content)
                                    emit(ChatResponse.Content(content))
                                }
                            } catch (e: Exception) {
                                // If not JSON, emit raw text
                                contentBuilder.append(text)
                                emit(ChatResponse.Content(text))
                            }
                        }
                    }
                    reader.close()
                } else {
                    // Non-streaming response
                    val responseBody = connection.inputStream.bufferedReader().readText()
                    try {
                        val json = JSONObject(responseBody)
                        val content = extractContent(json)
                        if (content.isNotEmpty()) {
                            contentBuilder.append(content)
                            emit(ChatResponse.Content(content, isComplete = true))
                        }
                    } catch (e: Exception) {
                        // Emit raw response
                        contentBuilder.append(responseBody)
                        emit(ChatResponse.Content(responseBody, isComplete = true))
                    }
                }

                connection.disconnect()

                // Post-request delay to avoid rate limiting
                delay(postRequestDelayMs)

                // Emit done
                emit(ChatResponse.Done())
                return@flow

            } catch (e: java.net.SocketTimeoutException) {
                if (retries < maxRetries) {
                    emit(ChatResponse.RetryNotification(
                        attempt = retries + 1,
                        maxAttempts = maxRetries,
                        delayMs = currentDelay,
                        reason = "Connection timeout"
                    ))
                    delay(currentDelay)
                    retries++
                    currentDelay = (currentDelay * 2).coerceAtMost(maxRetryDelayMs)
                    continue
                }
                emit(ChatResponse.Error("Connection timeout after $maxRetries retries", "timeout"))
                return@flow
            } catch (e: java.net.ConnectException) {
                if (retries < maxRetries) {
                    emit(ChatResponse.RetryNotification(
                        attempt = retries + 1,
                        maxAttempts = maxRetries,
                        delayMs = currentDelay,
                        reason = "Connection failed"
                    ))
                    delay(currentDelay)
                    retries++
                    currentDelay = (currentDelay * 2).coerceAtMost(maxRetryDelayMs)
                    continue
                }
                emit(ChatResponse.Error("Connection failed after $maxRetries retries", "connection_error"))
                return@flow
            } catch (e: Exception) {
                emit(ChatResponse.Error(e.message ?: "Unknown error", "error", isRetryable = true))
                return@flow
            }
        }

        emit(ChatResponse.Error("Failed after $maxRetries retries", "max_retries"))
    }.flowOn(Dispatchers.IO)

    /**
     * Extract content from JSON response (handles multiple formats)
     */
    private fun extractContent(json: JSONObject): String {
        // Format 1: {"message": {"content": "..."}}
        json.optJSONObject("message")?.let { message ->
            message.optString("content", null)?.let { return it }
        }

        // Format 2: {"choices": [{"message": {"content": "..."}}]}
        json.optJSONArray("choices")?.let { choices ->
            if (choices.length() > 0) {
                val firstChoice = choices.optJSONObject(0)
                firstChoice?.optJSONObject("message")?.let { message ->
                    message.optString("content", null)?.let { return it }
                }
            }
        }

        // Format 3: Check for error with limit
        if (json.optInt("limit", -1) == 0) {
            json.optJSONObject("error")?.let { error ->
                val errorMessage = error.optString("message", "Rate limit exceeded")
                throw RuntimeException("API error: $errorMessage")
            }
        }

        return ""
    }

    private fun createConnection(): HttpURLConnection {
        val url = URL(apiEndpoint)
        val connection = url.openConnection() as HttpsURLConnection

        connection.connectTimeout = 30000
        connection.readTimeout = 60000

        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accept", "*/*")
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9")
        connection.setRequestProperty("Cache-Control", "no-cache")
        connection.setRequestProperty("DNT", "1")
        connection.setRequestProperty("Origin", "https://chat-gpt.com")
        connection.setRequestProperty("Pragma", "no-cache")
        connection.setRequestProperty("Priority", "u=1, i")
        connection.setRequestProperty("Referer", "https://chat-gpt.com/")
        connection.setRequestProperty("Sec-CH-UA", "\"Not.A/Brand\";v=\"99\", \"Chromium\";v=\"136\"")
        connection.setRequestProperty("Sec-CH-UA-Mobile", "?0")
        connection.setRequestProperty("Sec-CH-UA-Platform", "\"Linux\"")
        connection.setRequestProperty("Sec-Fetch-Dest", "empty")
        connection.setRequestProperty("Sec-Fetch-Mode", "cors")
        connection.setRequestProperty("Sec-Fetch-Site", "cross-site")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/136.0.0.0 Safari/537.36")

        return connection
    }
}
