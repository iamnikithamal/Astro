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
import kotlin.random.Random

/**
 * Chatai Provider
 *
 * Chatai is a free AI chat service that provides access to GPT-4o-mini
 * without requiring authentication. This implementation mirrors the
 * gpt4free Chatai provider.
 *
 * Features:
 * - Free GPT-4o-mini access
 * - Streaming support
 * - No API key required
 * - Simple and reliable
 *
 * API Endpoint: https://chatai.aritek.app/stream
 */
class ChataiProvider : AiProvider {

    override val providerId: String = "chatai"
    override val displayName: String = "Chatai"
    override val baseUrl: String = "https://chatai.aritek.app"
    override val isWorking: Boolean = true
    override val supportsStreaming: Boolean = true
    override val supportsSystemMessage: Boolean = true
    override val supportsMessageHistory: Boolean = true

    override val defaultModel: String = "gpt-4o-mini"

    private val apiEndpoint = "https://chatai.aritek.app/stream"

    // Static token for authentication (from gpt4free implementation)
    private val authToken = "eyJzdWIiOiIyMzQyZmczNHJ0MzR0MzQiLCJuYW1lIjoiSm9objM0NTM0NT"

    // Cached models
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
            id = "gpt-4o-mini",
            name = "GPT-4o Mini",
            providerId = providerId,
            displayName = "GPT-4o Mini",
            description = "OpenAI's efficient GPT-4o Mini model - fast and capable",
            maxTokens = 16384,
            supportsVision = false,
            supportsReasoning = false,
            supportsTools = true
        )
    )

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
                val url = URL(apiEndpoint)
                val connection = url.openConnection() as HttpsURLConnection

                try {
                    connection.requestMethod = "POST"
                    connection.doOutput = true
                    connection.connectTimeout = 30000
                    connection.readTimeout = 120000

                    applyHeaders(connection)

                    val payload = buildRequestPayload(messages)

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
                        throw AiProviderException("HTTP $responseCode: $errorBody", "http_error",
                            isRetryable = responseCode >= 500)
                    }

                    val reader = BufferedReader(InputStreamReader(connection.inputStream, Charsets.UTF_8))
                    val contentBuilder = StringBuilder()
                    var finishReason: String? = null

                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        val text = line?.trim() ?: continue
                        if (text.isEmpty()) continue

                        // SSE format
                        if (!text.startsWith("data:")) continue

                        val jsonStr = text.removePrefix("data:").trim()
                        if (jsonStr.isEmpty() || jsonStr == "[DONE]") {
                            if (jsonStr == "[DONE]") {
                                if (contentBuilder.isNotEmpty()) {
                                    emit(ChatResponse.Content("", isComplete = true))
                                }
                                emit(ChatResponse.Done(finishReason ?: "stop"))
                            }
                            continue
                        }

                        try {
                            val json = JSONObject(jsonStr)

                            // Parse choices
                            val choices = json.optJSONArray("choices")
                            if (choices != null && choices.length() > 0) {
                                val choice = choices.getJSONObject(0)
                                val delta = choice.optJSONObject("delta")

                                // Check finish reason
                                choice.optString("finish_reason", null)?.let { reason ->
                                    if (reason.isNotEmpty() && reason != "null") {
                                        finishReason = reason
                                    }
                                }

                                if (delta != null) {
                                    val content = delta.optString("content", "")
                                    if (content.isNotEmpty()) {
                                        contentBuilder.append(content)
                                        emit(ChatResponse.Content(content))
                                    }
                                }
                            }

                        } catch (e: Exception) {
                            // Skip malformed chunks
                        }
                    }

                    reader.close()
                    return@flow // Success

                } finally {
                    connection.disconnect()
                }

            } catch (e: RateLimitException) {
                lastException = e
                if (attempt < maxRetries - 1) {
                    val delayMs = (3000L * (1 shl attempt)).coerceAtMost(15000L)
                    emit(ChatResponse.RetryNotification(
                        attempt = attempt + 1,
                        maxAttempts = maxRetries,
                        delayMs = delayMs,
                        reason = "Rate limit exceeded"
                    ))
                    delay(delayMs)
                }
            } catch (e: AiProviderException) {
                lastException = e
                if (e.isRetryable && attempt < maxRetries - 1) {
                    val delayMs = (2000L * (1 shl attempt)).coerceAtMost(10000L)
                    emit(ChatResponse.RetryNotification(
                        attempt = attempt + 1,
                        maxAttempts = maxRetries,
                        delayMs = delayMs,
                        reason = e.message ?: "Provider error"
                    ))
                    delay(delayMs)
                } else if (!e.isRetryable) {
                    break
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
                e is java.net.UnknownHostException ||
                e.message?.contains("Connection reset", ignoreCase = true) == true ||
                e.message?.contains("timeout", ignoreCase = true) == true
    }

    /**
     * Generate a random machine ID for Chatai authentication
     */
    private fun generateMachineId(): String {
        val part1 = (1..16).map { Random.nextInt(0, 10) }.joinToString("")
        val part2 = buildString {
            repeat(25) {
                if (Random.nextBoolean() && length > 0 && last() != '.') {
                    append('.')
                } else {
                    append(Random.nextInt(0, 10))
                }
            }
        }
        return "$part1.$part2"
    }

    private fun buildRequestPayload(messages: List<ChatMessage>): JSONObject {
        val formattedMessages = JSONArray()

        messages.forEach { message ->
            formattedMessages.put(JSONObject().apply {
                put("role", when (message.role) {
                    MessageRole.SYSTEM -> "system"
                    MessageRole.USER -> "user"
                    MessageRole.ASSISTANT -> "assistant"
                    MessageRole.TOOL -> "user"
                })
                put("content", message.content)
            })
        }

        return JSONObject().apply {
            put("machineId", generateMachineId())
            put("msg", formattedMessages)
            put("token", authToken)
            put("type", 0)
        }
    }

    private fun applyHeaders(connection: HttpURLConnection) {
        connection.setRequestProperty("Accept", "text/event-stream")
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("User-Agent", "Dalvik/2.1.0 (Linux; U; Android 7.1.2; SM-G935F Build/N2G48H)")
        connection.setRequestProperty("Host", "chatai.aritek.app")
        connection.setRequestProperty("Connection", "Keep-Alive")
    }
}
