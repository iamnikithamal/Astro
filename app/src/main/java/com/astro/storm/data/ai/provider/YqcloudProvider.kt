package com.astro.storm.data.ai.provider

import kotlinx.coroutines.Dispatchers
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
 * Yqcloud AI Provider
 *
 * Free provider based on gpt4free's Yqcloud implementation.
 * Provides access to GPT-4 class models without authentication.
 *
 * API Endpoint: https://api.binjie.fun/api/generateStream
 * Website: https://chat9.yqcloud.top
 *
 * Features:
 * - Streaming responses
 * - System message support
 * - Message history support
 * - No authentication required
 */
class YqcloudProvider : AiProvider {

    override val providerId: String = "yqcloud"
    override val displayName: String = "Yqcloud"
    override val baseUrl: String = "https://chat9.yqcloud.top"
    override val isWorking: Boolean = true
    override val requiresAuth: Boolean = false
    override val supportsStreaming: Boolean = true
    override val supportsSystemMessage: Boolean = true
    override val supportsMessageHistory: Boolean = true

    override val defaultModel: String = "gpt-4"

    private val apiEndpoint = "https://api.binjie.fun/api/generateStream"

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
            displayName = "GPT-4 (Yqcloud)",
            description = "Free GPT-4 class model via Yqcloud",
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
        try {
            emit(ChatResponse.ProviderInfo(providerId, model ?: defaultModel))

            // Extract system message if present
            var systemMessage = ""
            val processedMessages = messages.toMutableList()
            if (processedMessages.isNotEmpty() && processedMessages[0].role == MessageRole.SYSTEM) {
                systemMessage = processedMessages[0].content
                processedMessages.removeAt(0)
            }

            // Format messages into prompt
            val prompt = formatPrompt(processedMessages)

            // Generate unique user ID based on timestamp
            val userId = "#/chat/${System.currentTimeMillis()}"

            // Build request body
            val requestBody = JSONObject().apply {
                put("prompt", prompt)
                put("userId", userId)
                put("network", true)
                put("system", systemMessage)
                put("withoutContext", false)
                put("stream", stream)
            }

            val connection = createConnection()
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.outputStream.use { os ->
                os.write(requestBody.toString().toByteArray(Charsets.UTF_8))
            }

            val responseCode = connection.responseCode
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

            // Read streaming response
            val reader = BufferedReader(InputStreamReader(connection.inputStream, Charsets.UTF_8))
            val contentBuilder = StringBuilder()

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val text = line ?: continue
                if (text.isNotEmpty()) {
                    contentBuilder.append(text)
                    emit(ChatResponse.Content(text))
                }
            }

            reader.close()
            connection.disconnect()

            // Emit final content
            if (contentBuilder.isNotEmpty()) {
                emit(ChatResponse.Content(contentBuilder.toString(), isComplete = true))
            }
            emit(ChatResponse.Done())

        } catch (e: Exception) {
            emit(ChatResponse.Error(e.message ?: "Unknown error", "error", isRetryable = true))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Format messages into a single prompt string
     */
    private fun formatPrompt(messages: List<ChatMessage>): String {
        return messages.joinToString("\n\n") { message ->
            when (message.role) {
                MessageRole.USER -> "User: ${message.content}"
                MessageRole.ASSISTANT -> "Assistant: ${message.content}"
                MessageRole.SYSTEM -> "System: ${message.content}"
                MessageRole.TOOL -> "Tool Result: ${message.content}"
            }
        }
    }

    private fun createConnection(): HttpURLConnection {
        val url = URL(apiEndpoint)
        val connection = url.openConnection() as HttpsURLConnection

        connection.connectTimeout = 30000
        connection.readTimeout = 120000

        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accept", "application/json, text/plain, */*")
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9")
        connection.setRequestProperty("Origin", baseUrl)
        connection.setRequestProperty("Referer", "$baseUrl/")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")

        return connection
    }
}
