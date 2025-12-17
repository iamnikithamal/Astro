package com.astro.storm.data.ai.provider

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection

/**
 * DeepInfra AI Provider
 *
 * DeepInfra provides free access to various open-source LLMs including:
 * - Meta Llama models
 * - DeepSeek models (including R1 with reasoning)
 * - Qwen models
 * - Google Gemma models
 * - And more
 *
 * Implementation mirrors gpt4free's DeepInfra provider.
 * Uses OpenAI-compatible API at api.deepinfra.com/v1/openai
 */
class DeepInfraProvider : BaseOpenAiCompatibleProvider() {

    override val providerId: String = "deepinfra"
    override val displayName: String = "DeepInfra"
    override val apiBase: String = "https://api.deepinfra.com/v1/openai"
    override val isWorking: Boolean = true
    override val supportsStreaming: Boolean = true
    override val supportsSystemMessage: Boolean = true
    override val supportsMessageHistory: Boolean = true

    override val defaultModel: String = "meta-llama/Llama-3.3-70B-Instruct"

    /**
     * Model aliases for user-friendly names
     */
    override val modelAliases: Map<String, String> = mapOf(
        // DeepSeek models
        "deepseek-r1" to "deepseek-ai/DeepSeek-R1",
        "deepseek-r1-0528" to "deepseek-ai/DeepSeek-R1-0528",
        "deepseek-r1-turbo" to "deepseek-ai/DeepSeek-R1-Turbo",
        "deepseek-r1-distill-llama-70b" to "deepseek-ai/DeepSeek-R1-Distill-Llama-70B",
        "deepseek-r1-distill-qwen-32b" to "deepseek-ai/DeepSeek-R1-Distill-Qwen-32B",
        "deepseek-v3" to "deepseek-ai/DeepSeek-V3",
        "deepseek-v3-0324" to "deepseek-ai/DeepSeek-V3-0324",
        "deepseek-prover-v2" to "deepseek-ai/DeepSeek-Prover-V2-671B",

        // Meta Llama models
        "llama-3.1-8b" to "meta-llama/Meta-Llama-3.1-8B-Instruct",
        "llama-3.2-90b" to "meta-llama/Llama-3.2-90B-Vision-Instruct",
        "llama-3.3-70b" to "meta-llama/Llama-3.3-70B-Instruct",
        "llama-4-maverick" to "meta-llama/Llama-4-Maverick-17B-128E-Instruct-FP8",
        "llama-4-scout" to "meta-llama/Llama-4-Scout-17B-16E-Instruct",

        // Google Gemma models
        "gemma-2-27b" to "google/gemma-2-27b-it",
        "gemma-2-9b" to "google/gemma-2-9b-it",
        "gemma-3-4b" to "google/gemma-3-4b-it",
        "gemma-3-12b" to "google/gemma-3-12b-it",
        "gemma-3-27b" to "google/gemma-3-27b-it",

        // Qwen models
        "qwen-3-14b" to "Qwen/Qwen3-14B",
        "qwen-3-30b" to "Qwen/Qwen3-30B-A3B",
        "qwen-3-32b" to "Qwen/Qwen3-32B",
        "qwen-3-235b" to "Qwen/Qwen3-235B-A22B",
        "qwq-32b" to "Qwen/QwQ-32B",

        // Microsoft models
        "phi-4" to "microsoft/phi-4",
        "phi-4-multimodal" to "microsoft/Phi-4-multimodal-instruct",
        "phi-4-reasoning-plus" to "microsoft/phi-4-reasoning-plus",
        "wizardlm-2-7b" to "microsoft/WizardLM-2-7B",
        "wizardlm-2-8x22b" to "microsoft/WizardLM-2-8x22B",

        // Mistral models
        "mistral-small-3.1-24b" to "mistralai/Mistral-Small-3.1-24B-Instruct-2503",

        // Other models
        "dolphin-2.6" to "cognitivecomputations/dolphin-2.6-mixtral-8x7b",
        "dolphin-2.9" to "cognitivecomputations/dolphin-2.9.1-llama-3-70b",
        "airoboros-70b" to "deepinfra/airoboros-70b",
        "lzlv-70b" to "lizpreciatior/lzlv_70b_fp16_hf",
        "kimi-k2" to "moonshotai/Kimi-K2-Instruct-0905"
    )

    /**
     * Vision-capable models
     */
    private val visionModels = setOf(
        "meta-llama/Llama-3.2-90B-Vision-Instruct",
        "openai/gpt-oss-120b",
        "openai/gpt-oss-20b",
        "microsoft/Phi-4-multimodal-instruct"
    )

    /**
     * Models with reasoning/thinking capabilities
     */
    private val reasoningModels = setOf(
        "deepseek-ai/DeepSeek-R1",
        "deepseek-ai/DeepSeek-R1-0528",
        "deepseek-ai/DeepSeek-R1-Turbo",
        "deepseek-ai/DeepSeek-R1-Distill-Llama-70B",
        "deepseek-ai/DeepSeek-R1-Distill-Qwen-32B",
        "Qwen/QwQ-32B",
        "microsoft/phi-4-reasoning-plus"
    )

    override suspend fun fetchModels(): List<AiModel> = withContext(Dispatchers.IO) {
        try {
            val url = java.net.URL("https://api.deepinfra.com/models/featured")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 15000
            connection.readTimeout = 15000

            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().readText()
                parseDeepInfraModels(response)
            } else {
                getDefaultModels()
            }
        } catch (e: Exception) {
            getDefaultModels()
        }
    }

    private fun parseDeepInfraModels(response: String): List<AiModel> {
        val models = mutableListOf<AiModel>()
        try {
            val jsonArray = org.json.JSONArray(response)
            for (i in 0 until jsonArray.length()) {
                val modelJson = jsonArray.getJSONObject(i)
                val modelName = modelJson.optString("model_name", "")
                val modelType = modelJson.optString("type", "")

                // Only include text-generation models
                if (modelType == "text-generation" && modelName.isNotEmpty()) {
                    models.add(
                        AiModel(
                            id = modelName,
                            name = modelName,
                            providerId = providerId,
                            displayName = formatModelDisplayName(modelName),
                            supportsVision = modelName in visionModels,
                            supportsReasoning = modelName in reasoningModels,
                            supportsTools = true
                        )
                    )
                }
            }
        } catch (e: Exception) {
            // Fall back to defaults
        }
        return models.ifEmpty { getDefaultModels() }
    }

    override fun getDefaultModels(): List<AiModel> = listOf(
        // DeepSeek R1 - Top reasoning model
        AiModel(
            id = "deepseek-ai/DeepSeek-R1",
            name = "DeepSeek R1",
            providerId = providerId,
            displayName = "DeepSeek R1",
            description = "Advanced reasoning model with chain-of-thought capabilities",
            supportsReasoning = true,
            supportsTools = true
        ),
        AiModel(
            id = "deepseek-ai/DeepSeek-R1-Turbo",
            name = "DeepSeek R1 Turbo",
            providerId = providerId,
            displayName = "DeepSeek R1 Turbo",
            description = "Faster version of DeepSeek R1",
            supportsReasoning = true,
            supportsTools = true
        ),
        AiModel(
            id = "deepseek-ai/DeepSeek-V3",
            name = "DeepSeek V3",
            providerId = providerId,
            displayName = "DeepSeek V3",
            description = "Latest DeepSeek generation model",
            supportsTools = true
        ),
        // Meta Llama models
        AiModel(
            id = "meta-llama/Llama-3.3-70B-Instruct",
            name = "Llama 3.3 70B",
            providerId = providerId,
            displayName = "Llama 3.3 70B",
            description = "Meta's latest Llama model, excellent for general tasks",
            supportsTools = true
        ),
        AiModel(
            id = "meta-llama/Llama-3.2-90B-Vision-Instruct",
            name = "Llama 3.2 90B Vision",
            providerId = providerId,
            displayName = "Llama 3.2 90B Vision",
            description = "Vision-capable Llama model",
            supportsVision = true,
            supportsTools = true
        ),
        AiModel(
            id = "meta-llama/Meta-Llama-3.1-8B-Instruct",
            name = "Llama 3.1 8B",
            providerId = providerId,
            displayName = "Llama 3.1 8B",
            description = "Efficient smaller Llama model",
            supportsTools = true
        ),
        // Qwen models
        AiModel(
            id = "Qwen/Qwen3-235B-A22B",
            name = "Qwen 3 235B",
            providerId = providerId,
            displayName = "Qwen 3 235B",
            description = "Alibaba's largest Qwen model",
            supportsTools = true
        ),
        AiModel(
            id = "Qwen/QwQ-32B",
            name = "QwQ 32B",
            providerId = providerId,
            displayName = "QwQ 32B",
            description = "Qwen reasoning model",
            supportsReasoning = true,
            supportsTools = true
        ),
        AiModel(
            id = "Qwen/Qwen3-32B",
            name = "Qwen 3 32B",
            providerId = providerId,
            displayName = "Qwen 3 32B",
            description = "Mid-size Qwen model",
            supportsTools = true
        ),
        // Google Gemma
        AiModel(
            id = "google/gemma-3-27b-it",
            name = "Gemma 3 27B",
            providerId = providerId,
            displayName = "Gemma 3 27B",
            description = "Google's open Gemma model",
            supportsTools = true
        ),
        AiModel(
            id = "google/gemma-2-9b-it",
            name = "Gemma 2 9B",
            providerId = providerId,
            displayName = "Gemma 2 9B",
            description = "Efficient Gemma model",
            supportsTools = true
        ),
        // Microsoft
        AiModel(
            id = "microsoft/phi-4",
            name = "Phi 4",
            providerId = providerId,
            displayName = "Phi 4",
            description = "Microsoft's efficient Phi model",
            supportsTools = true
        ),
        AiModel(
            id = "microsoft/phi-4-reasoning-plus",
            name = "Phi 4 Reasoning Plus",
            providerId = providerId,
            displayName = "Phi 4 Reasoning+",
            description = "Phi model with enhanced reasoning",
            supportsReasoning = true,
            supportsTools = true
        ),
        // Mistral
        AiModel(
            id = "mistralai/Mistral-Small-3.1-24B-Instruct-2503",
            name = "Mistral Small 3.1",
            providerId = providerId,
            displayName = "Mistral Small 3.1",
            description = "Mistral's efficient small model",
            supportsTools = true
        )
    )

    override fun formatModelDisplayName(modelId: String): String {
        return modelId
            .substringAfterLast("/")
            .replace("-Instruct", "")
            .replace("-instruct", "")
            .replace("Meta-", "")
            .replace("-it", "")
            .replace("-", " ")
            .replace("_", " ")
    }
}
