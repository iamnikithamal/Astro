package com.astro.storm.data.ai.provider

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection

/**
 * Pollinations AI Provider
 *
 * Pollinations provides free access to various AI models without requiring API keys.
 * It aggregates multiple model providers and offers an OpenAI-compatible API.
 *
 * Features:
 * - No API key required for free tier models
 * - Multiple model options
 * - Supports streaming
 * - Tool calling support on some models
 *
 * IMPORTANT: Only models available on the free/anonymous tier are included.
 * Premium models require the "seed" tier and are excluded to prevent HTTP 402 errors.
 *
 * Implementation mirrors gpt4free's PollinationsAI provider.
 */
class PollinationsProvider : BaseOpenAiCompatibleProvider() {

    override val providerId: String = "pollinations"
    override val displayName: String = "Pollinations AI"
    override val apiBase: String = "https://text.pollinations.ai"
    override val isWorking: Boolean = true
    override val supportsStreaming: Boolean = true
    override val supportsSystemMessage: Boolean = true
    override val supportsMessageHistory: Boolean = true

    override val chatEndpoint: String
        get() = "$apiBase/openai"

    override val defaultModel: String = "openai"

    override val defaultHeaders: Map<String, String>
        get() = mapOf(
            "Content-Type" to "application/json",
            "Accept" to "text/event-stream",
            "User-Agent" to "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36",
            "Referer" to "https://pollinations.ai/",
            "Origin" to "https://pollinations.ai"
        )

    /**
     * Model aliases for user-friendly names
     * Only includes free tier models
     */
    override val modelAliases: Map<String, String> = mapOf(
        "gpt-4.1-nano" to "openai-fast",
        "llama-4-scout" to "llamascout",
        "qwen" to "qwen-coder",
        "mistral" to "mistral"
    )

    /**
     * Models with reasoning/thinking capabilities (available on free tier)
     * Note: DeepSeek reasoning models are premium only
     */
    private val reasoningModels = setOf<String>()

    /**
     * Models with vision capabilities (available on free tier)
     */
    private val visionModels = setOf(
        "openai"
    )

    /**
     * Models that are KNOWN to be free (anonymous tier)
     * These have been verified to work without API key
     */
    private val freeModels = setOf(
        "openai",          // GPT-4o-mini equivalent - VERIFIED FREE
        "openai-fast",     // GPT-4.1-nano equivalent - VERIFIED FREE
        "mistral",         // Mistral free tier - VERIFIED FREE
        "llamascout",      // Llama 4 Scout - VERIFIED FREE
        "qwen-coder",      // Qwen Coder free - VERIFIED FREE
        "searchgpt"        // Search capability - VERIFIED FREE
    )

    /**
     * Models that require premium (seed tier) - EXCLUDED
     * HTTP 402: "Model not found or tier not high enough. Your tier: anonymous, required tier: seed"
     */
    private val premiumModels = setOf(
        "openai-large",
        "deepseek",
        "deepseek-reasoning",
        "deepseek-r1",
        "gemini-flash",
        "claude-hybridspace",
        "claude"
    )

    private val textModelsEndpoint = "https://text.pollinations.ai/models"
    private val g4fModelsEndpoint = "https://g4f.dev/api/pollinations/models"

    override suspend fun fetchModels(): List<AiModel> = withContext(Dispatchers.IO) {
        try {
            // Try the g4f endpoint first as it has more info
            var models = fetchFromEndpoint(g4fModelsEndpoint)
            if (models.isEmpty()) {
                // Fallback to direct Pollinations endpoint
                models = fetchFromEndpoint(textModelsEndpoint)
            }
            // Filter to only include free models
            val filteredModels = models.filter { model ->
                (model.id in freeModels || model.name in freeModels) &&
                model.id !in premiumModels && model.name !in premiumModels
            }
            filteredModels.ifEmpty { getDefaultModels() }
        } catch (e: Exception) {
            getDefaultModels()
        }
    }

    private fun fetchFromEndpoint(endpoint: String): List<AiModel> {
        val models = mutableListOf<AiModel>()
        try {
            val url = java.net.URL(endpoint)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 15000
            connection.setRequestProperty("User-Agent", "AstroStorm/1.0")

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().readText()
                val jsonArray = JSONArray(response)

                for (i in 0 until jsonArray.length()) {
                    val modelJson = jsonArray.getJSONObject(i)
                    val modelName = modelJson.optString("name", "")
                    val modelId = modelJson.optString("id", modelName)
                    val aliases = modelJson.optJSONArray("aliases")

                    // Skip premium models
                    if (modelName in premiumModels || modelId in premiumModels) {
                        continue
                    }

                    if (modelName.isNotEmpty()) {
                        val displayName = if (aliases != null && aliases.length() > 0) {
                            aliases.getString(0)
                        } else {
                            modelName
                        }

                        models.add(
                            AiModel(
                                id = modelId.ifEmpty { modelName },
                                name = modelName,
                                providerId = providerId,
                                displayName = formatDisplayName(displayName),
                                supportsVision = modelJson.optBoolean("vision", modelName in visionModels),
                                supportsReasoning = modelName in reasoningModels,
                                supportsTools = true
                            )
                        )
                    }
                }
            }
            connection.disconnect()
        } catch (e: Exception) {
            // Ignore and return empty
        }
        return models
    }

    private fun formatDisplayName(name: String): String {
        return name
            .replace("-instruct", "")
            .replace("qwen-", "Qwen ")
            .replace("openai", "OpenAI")
            .replace("deepseek", "DeepSeek")
            .replace("mistral", "Mistral")
            .replace("llama", "Llama")
            .replace("gemini", "Gemini")
            .replace("claude", "Claude")
            .split("-", "_")
            .joinToString(" ") { word ->
                word.replaceFirstChar { it.uppercase() }
            }
    }

    override fun getDefaultModels(): List<AiModel> = listOf(
        // Only truly FREE models (anonymous tier) - VERIFIED WORKING
        AiModel(
            id = "openai",
            name = "OpenAI",
            providerId = providerId,
            displayName = "OpenAI GPT",
            description = "GPT-4o-mini equivalent via Pollinations (Free)",
            supportsVision = true,
            supportsTools = true
        ),
        AiModel(
            id = "openai-fast",
            name = "OpenAI Fast",
            providerId = providerId,
            displayName = "OpenAI Fast",
            description = "Fast GPT model (GPT-4.1-nano equivalent)",
            supportsTools = true
        ),
        AiModel(
            id = "mistral",
            name = "Mistral",
            providerId = providerId,
            displayName = "Mistral",
            description = "Mistral AI model (Free tier)",
            supportsTools = true
        ),
        AiModel(
            id = "llamascout",
            name = "Llama Scout",
            providerId = providerId,
            displayName = "Llama 4 Scout",
            description = "Meta's Llama 4 Scout model (Free)",
            supportsTools = true
        ),
        AiModel(
            id = "qwen-coder",
            name = "Qwen Coder",
            providerId = providerId,
            displayName = "Qwen Coder",
            description = "Alibaba's Qwen Coder (Free tier)",
            supportsTools = true
        ),
        AiModel(
            id = "searchgpt",
            name = "SearchGPT",
            providerId = providerId,
            displayName = "SearchGPT",
            description = "GPT with web search capabilities"
        )
    )

    override fun buildRequestBody(
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
            put("referrer", "https://astrostorm.app")
            temperature?.let { put("temperature", it.toDouble()) }
            maxTokens?.let { put("max_tokens", it) }
            // Add seed for consistent results when not using tools
            put("seed", System.currentTimeMillis() % Int.MAX_VALUE)
        }
        return json.toString()
    }

    override fun formatModelDisplayName(modelId: String): String {
        return formatDisplayName(modelId)
    }
}
