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
 * Pollinations AI Provider
 *
 * Pollinations.ai is a free, open AI service that provides access to multiple
 * language models without requiring authentication. This implementation mirrors
 * the gpt4free PollinationsAI provider.
 *
 * Features:
 * - Multiple free AI models (OpenAI, DeepSeek, Qwen, Llama, Mistral)
 * - Streaming support
 * - No API key required
 * - High reliability and uptime
 *
 * API Endpoints:
 * - Text: https://text.pollinations.ai/openai
 * - Models: https://text.pollinations.ai/models
 */
class PollinationsProvider : AiProvider {

    override val providerId: String = "pollinations"
    override val displayName: String = "Pollinations AI"
    override val baseUrl: String = "https://pollinations.ai"
    override val isWorking: Boolean = true
    override val supportsStreaming: Boolean = true
    override val supportsSystemMessage: Boolean = true
    override val supportsMessageHistory: Boolean = true

    override val defaultModel: String = "openai"

    private val textApiEndpoint = "https://text.pollinations.ai/openai"
    private val modelsEndpoint = "https://text.pollinations.ai/models"

    // Cached models
    private var cachedModels: List<AiModel>? = null

    /**
     * Core text models available on Pollinations
     */
    private val coreModels = listOf(
        ModelConfig(
            id = "openai",
            name = "OpenAI GPT-4o",
            description = "OpenAI's GPT-4o model - versatile and capable",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "openai-large",
            name = "OpenAI GPT-4o (Large)",
            description = "OpenAI's larger GPT-4o model variant",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "openai-fast",
            name = "OpenAI GPT-4.1 Nano",
            description = "Fast and efficient OpenAI model",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "deepseek",
            name = "DeepSeek V3",
            description = "DeepSeek's V3 model - excellent reasoning",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "deepseek-reasoning",
            name = "DeepSeek R1",
            description = "DeepSeek R1 with extended reasoning capability",
            supportsReasoning = true
        ),
        ModelConfig(
            id = "qwen-72b",
            name = "Qwen 72B",
            description = "Alibaba's Qwen 72B model",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "qwen-32b",
            name = "Qwen 32B",
            description = "Alibaba's Qwen 32B model",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "qwq",
            name = "QwQ (Reasoning)",
            description = "Qwen reasoning model with thinking capability",
            supportsReasoning = true
        ),
        ModelConfig(
            id = "llama-3.3-70b",
            name = "Llama 3.3 70B",
            description = "Meta's powerful Llama 3.3 70B model",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "llama-4-scout",
            name = "Llama 4 Scout",
            description = "Meta's latest Llama 4 Scout model",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "mistral",
            name = "Mistral Large",
            description = "Mistral's large model",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "mistral-small",
            name = "Mistral Small",
            description = "Mistral's efficient small model",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "gemini",
            name = "Gemini Pro",
            description = "Google's Gemini Pro model",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "claude-hybridspace",
            name = "Claude (Hybridspace)",
            description = "Anthropic's Claude via Hybridspace",
            supportsReasoning = false
        ),
        ModelConfig(
            id = "searchgpt",
            name = "SearchGPT",
            description = "GPT with web search capabilities",
            supportsReasoning = false
        )
    )

    /**
     * Model ID mappings (aliases -> internal names)
     */
    private val modelAliases = mapOf(
        "gpt-4o" to "openai",
        "gpt-4o-mini" to "openai-fast",
        "deepseek-r1" to "deepseek-reasoning",
        "qwen-3-235b" to "qwen-72b"
    )

    override suspend fun getModels(): List<AiModel> {
        return cachedModels ?: getDefaultModels().also { cachedModels = it }
    }

    override suspend fun refreshModels(): Boolean {
        cachedModels = getDefaultModels()
        return true
    }

    private fun getDefaultModels(): List<AiModel> = coreModels.map { config ->
        AiModel(
            id = config.id,
            name = config.name,
            providerId = providerId,
            displayName = config.name,
            description = config.description,
            maxTokens = config.maxTokens,
            supportsVision = config.supportsVision,
            supportsReasoning = config.supportsReasoning,
            supportsTools = true,
            supportsThinking = config.supportsReasoning
        )
    }

    override fun chat(
        messages: List<ChatMessage>,
        model: String?,
        temperature: Float?,
        maxTokens: Int?,
        stream: Boolean
    ): Flow<ChatResponse> = flow {
        val modelId = resolveModel(model ?: defaultModel)

        emit(ChatResponse.ProviderInfo(providerId, modelId))

        var lastException: Exception? = null
        val maxRetries = 3

        for (attempt in 0 until maxRetries) {
            try {
                val url = URL(textApiEndpoint)
                val connection = url.openConnection() as HttpsURLConnection

                try {
                    connection.requestMethod = "POST"
                    connection.doOutput = true
                    connection.connectTimeout = 30000
                    connection.readTimeout = 180000 // Long timeout for reasoning models

                    applyHeaders(connection)

                    // Generate seed for reproducibility (or randomness)
                    val seed = Random.nextInt(0, Int.MAX_VALUE)

                    val payload = buildRequestPayload(messages, modelId, temperature, maxTokens, stream, seed)

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
                    val reasoningBuilder = StringBuilder()
                    var isInReasoning = false
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
                                // Final emissions
                                if (reasoningBuilder.isNotEmpty()) {
                                    emit(ChatResponse.Reasoning("", isComplete = true))
                                }
                                if (contentBuilder.isNotEmpty()) {
                                    emit(ChatResponse.Content("", isComplete = true))
                                }
                                emit(ChatResponse.Done(finishReason ?: "stop"))
                            }
                            continue
                        }

                        try {
                            val json = JSONObject(jsonStr)

                            // Check for error
                            json.optJSONObject("error")?.let { error ->
                                val errorMsg = error.optString("message", "Unknown error")
                                emit(ChatResponse.Error(errorMsg, "api_error"))
                                return@flow
                            }

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
                                    // Handle reasoning content (for DeepSeek R1, QwQ, etc.)
                                    val reasoningContent = delta.optString("reasoning_content", "")
                                    if (reasoningContent.isNotEmpty()) {
                                        if (!isInReasoning) {
                                            isInReasoning = true
                                        }
                                        reasoningBuilder.append(reasoningContent)
                                        emit(ChatResponse.Reasoning(reasoningContent))
                                    }

                                    // Handle regular content
                                    val content = delta.optString("content", "")
                                    if (content.isNotEmpty()) {
                                        if (isInReasoning) {
                                            isInReasoning = false
                                            emit(ChatResponse.Reasoning("", isComplete = true))
                                        }
                                        contentBuilder.append(content)
                                        emit(ChatResponse.Content(content))
                                    }
                                }
                            }

                            // Parse usage
                            json.optJSONObject("usage")?.let { usage ->
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

    private fun resolveModel(model: String): String {
        return modelAliases[model] ?: model
    }

    private fun isRetryableError(e: Exception): Boolean {
        return e is java.net.SocketTimeoutException ||
                e is java.net.ConnectException ||
                e is java.net.UnknownHostException ||
                e.message?.contains("Connection reset", ignoreCase = true) == true ||
                e.message?.contains("timeout", ignoreCase = true) == true
    }

    private fun buildRequestPayload(
        messages: List<ChatMessage>,
        modelId: String,
        temperature: Float?,
        maxTokens: Int?,
        stream: Boolean,
        seed: Int
    ): JSONObject {
        val formattedMessages = JSONArray()

        messages.forEach { message ->
            formattedMessages.put(JSONObject().apply {
                put("role", when (message.role) {
                    MessageRole.SYSTEM -> "system"
                    MessageRole.USER -> "user"
                    MessageRole.ASSISTANT -> "assistant"
                    MessageRole.TOOL -> "user" // Map tool responses as user messages
                })
                put("content", message.content)
            })
        }

        return JSONObject().apply {
            put("model", modelId)
            put("messages", formattedMessages)
            put("stream", stream)
            put("seed", seed)
            put("referrer", "https://astrostorm.app")
            temperature?.let { put("temperature", it.toDouble()) }
            maxTokens?.let { put("max_tokens", it) }
        }
    }

    private fun applyHeaders(connection: HttpURLConnection) {
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accept", "text/event-stream")
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36")
        connection.setRequestProperty("Origin", "https://pollinations.ai")
        connection.setRequestProperty("Referer", "https://pollinations.ai/")
    }

    /**
     * Model configuration data class
     */
    private data class ModelConfig(
        val id: String,
        val name: String,
        val description: String,
        val maxTokens: Int = 16384,
        val supportsVision: Boolean = false,
        val supportsReasoning: Boolean = false
    )
}
