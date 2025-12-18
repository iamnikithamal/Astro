package com.astro.storm.data.ai.provider

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

/**
 * TeachAnything AI Provider
 *
 * Free provider based on gpt4free's TeachAnything implementation.
 * Uses Google's Gemma model without authentication.
 *
 * API Endpoint: https://www.teach-anything.com/api/generate
 * Website: https://www.teach-anything.com
 *
 * Features:
 * - Simple prompt-based API
 * - Powered by Google Gemma
 * - No authentication required
 * - Good for educational and knowledge queries
 */
class TeachAnythingProvider : AiProvider {

    override val providerId: String = "teachanything"
    override val displayName: String = "Teach Anything"
    override val baseUrl: String = "https://www.teach-anything.com"
    override val isWorking: Boolean = true
    override val requiresAuth: Boolean = false
    override val supportsStreaming: Boolean = true
    override val supportsSystemMessage: Boolean = true
    override val supportsMessageHistory: Boolean = true

    override val defaultModel: String = "gemma"

    private val apiEndpoint = "$baseUrl/api/generate"

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
            id = "gemma",
            name = "Gemma",
            providerId = providerId,
            displayName = "Gemma (Teach Anything)",
            description = "Google Gemma model - excellent for educational queries and explanations",
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

            // Format messages into a single prompt
            val prompt = formatPrompt(messages)

            // Build request body
            val requestBody = JSONObject().apply {
                put("prompt", prompt)
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
            val buffer = ByteArray(1024)
            var byteBuffer = ByteArray(0)

            // Stream the response content
            val inputStream = connection.inputStream
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                // Append to buffer
                val newBuffer = ByteArray(byteBuffer.size + bytesRead)
                System.arraycopy(byteBuffer, 0, newBuffer, 0, byteBuffer.size)
                System.arraycopy(buffer, 0, newBuffer, byteBuffer.size, bytesRead)
                byteBuffer = newBuffer

                // Try to decode and emit
                try {
                    val decoded = byteBuffer.toString(Charsets.UTF_8)
                    if (decoded.isNotEmpty()) {
                        contentBuilder.append(decoded)
                        emit(ChatResponse.Content(decoded))
                        byteBuffer = ByteArray(0)
                    }
                } catch (e: Exception) {
                    // Wait for more data if we can't decode yet
                }
            }

            // Handle any remaining data in buffer
            if (byteBuffer.isNotEmpty()) {
                try {
                    val remaining = byteBuffer.toString(Charsets.UTF_8)
                    if (remaining.isNotEmpty()) {
                        emit(ChatResponse.Content(remaining))
                    }
                } catch (e: Exception) {
                    // Ignore final decode errors
                }
            }

            inputStream.close()
            connection.disconnect()

            // Emit done
            emit(ChatResponse.Done())

        } catch (e: Exception) {
            emit(ChatResponse.Error(e.message ?: "Unknown error", "error", isRetryable = true))
        }
    }.flowOn(Dispatchers.IO)

    /**
     * Format messages into a single prompt string
     */
    private fun formatPrompt(messages: List<ChatMessage>): String {
        val sb = StringBuilder()

        // Handle system message
        val systemMessage = messages.find { it.role == MessageRole.SYSTEM }?.content
        if (!systemMessage.isNullOrBlank()) {
            sb.append("Instructions: $systemMessage\n\n")
        }

        // Format conversation history
        val conversationMessages = messages.filter { it.role != MessageRole.SYSTEM }
        if (conversationMessages.isNotEmpty()) {
            for (message in conversationMessages.dropLast(1)) {
                when (message.role) {
                    MessageRole.USER -> sb.append("Human: ${message.content}\n")
                    MessageRole.ASSISTANT -> sb.append("Assistant: ${message.content}\n")
                    else -> {}
                }
            }

            // Add the last (current) user message
            val lastMessage = conversationMessages.lastOrNull()
            if (lastMessage?.role == MessageRole.USER) {
                sb.append("\nQuestion: ${lastMessage.content}")
            } else if (lastMessage != null) {
                sb.append("\n${lastMessage.content}")
            }
        }

        return sb.toString().trim()
    }

    private fun createConnection(): HttpURLConnection {
        val url = URL(apiEndpoint)
        val connection = url.openConnection() as HttpsURLConnection

        connection.connectTimeout = 60000
        connection.readTimeout = 60000

        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("Accept", "*/*")
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.9")
        connection.setRequestProperty("Cache-Control", "no-cache")
        connection.setRequestProperty("DNT", "1")
        connection.setRequestProperty("Origin", baseUrl)
        connection.setRequestProperty("Pragma", "no-cache")
        connection.setRequestProperty("Priority", "u=1, i")
        connection.setRequestProperty("Referer", "$baseUrl/")
        connection.setRequestProperty("Sec-CH-UA", "\"Not?A_Brand\";v=\"99\", \"Chromium\";v=\"130\"")
        connection.setRequestProperty("Sec-CH-UA-Mobile", "?0")
        connection.setRequestProperty("Sec-CH-UA-Platform", "\"Linux\"")
        connection.setRequestProperty("Sec-Fetch-Dest", "empty")
        connection.setRequestProperty("Sec-Fetch-Mode", "cors")
        connection.setRequestProperty("Sec-Fetch-Site", "same-origin")
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36")

        return connection
    }
}
