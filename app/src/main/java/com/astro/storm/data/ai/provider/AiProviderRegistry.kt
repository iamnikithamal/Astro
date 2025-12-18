package com.astro.storm.data.ai.provider

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

/**
 * Registry and manager for all AI providers.
 *
 * Handles:
 * - Provider registration and discovery
 * - Model management (enabling, disabling, aliasing)
 * - Configuration persistence
 * - Model refresh operations
 */
class AiProviderRegistry private constructor(context: Context) {

    private val appContext = context.applicationContext
    private val prefs: SharedPreferences = appContext.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )

    // Registered providers
    private val providers = mutableMapOf<String, AiProvider>()

    // Model configuration state
    private val _modelsState = MutableStateFlow<ModelsState>(ModelsState.Loading)
    val modelsState: StateFlow<ModelsState> = _modelsState.asStateFlow()

    // All available models
    private val _allModels = MutableStateFlow<List<AiModel>>(emptyList())
    val allModels: StateFlow<List<AiModel>> = _allModels.asStateFlow()

    // Enabled models only
    private val _enabledModels = MutableStateFlow<List<AiModel>>(emptyList())
    val enabledModels: StateFlow<List<AiModel>> = _enabledModels.asStateFlow()

    // User model configurations (enabled state, alias names)
    private val modelConfigs = mutableMapOf<String, ModelConfig>()

    // Custom user-added models
    private val customModels = mutableListOf<AiModel>()

    private val mutex = Mutex()

    init {
        // Register default providers
        registerProvider(DeepInfraProvider())
        registerProvider(PollinationsProvider())
        registerProvider(QwenProvider())

        // Load saved configurations
        loadConfigurations()
    }

    /**
     * Register an AI provider
     */
    fun registerProvider(provider: AiProvider) {
        providers[provider.providerId] = provider
    }

    /**
     * Get a provider by ID
     */
    fun getProvider(providerId: String): AiProvider? = providers[providerId]

    /**
     * Get all registered providers
     */
    fun getAllProviders(): List<AiProvider> = providers.values.toList()

    /**
     * Get all working providers
     */
    fun getWorkingProviders(): List<AiProvider> = providers.values.filter { it.isWorking }

    /**
     * Initialize and load all models from providers
     */
    suspend fun initialize() = withContext(Dispatchers.IO) {
        mutex.withLock {
            _modelsState.value = ModelsState.Loading

            try {
                val allModelsFromProviders = mutableListOf<AiModel>()

                // Fetch models from each provider
                for ((_, provider) in providers) {
                    if (provider.isWorking) {
                        try {
                            val models = provider.getModels()
                            allModelsFromProviders.addAll(models)
                        } catch (e: Exception) {
                            // Log and continue with other providers
                        }
                    }
                }

                // Add custom models
                allModelsFromProviders.addAll(customModels)

                // Apply configurations
                val configuredModels = allModelsFromProviders.map { model ->
                    val config = modelConfigs[model.id]
                    if (config != null) {
                        model.copy(
                            enabled = config.enabled,
                            aliasName = config.aliasName
                        )
                    } else {
                        model
                    }
                }

                _allModels.value = configuredModels
                _enabledModels.value = configuredModels.filter { it.enabled }
                _modelsState.value = ModelsState.Loaded(configuredModels.size)

            } catch (e: Exception) {
                _modelsState.value = ModelsState.Error(e.message ?: "Failed to load models")
            }
        }
    }

    /**
     * Refresh models from all providers
     */
    suspend fun refreshModels(): Boolean = withContext(Dispatchers.IO) {
        mutex.withLock {
            _modelsState.value = ModelsState.Refreshing

            var success = true
            for ((_, provider) in providers) {
                if (provider.isWorking) {
                    if (!provider.refreshModels()) {
                        success = false
                    }
                }
            }

            // Re-initialize after refresh
            _modelsState.value = ModelsState.Loading
        }

        initialize()
        return@withContext _modelsState.value is ModelsState.Loaded
    }

    /**
     * Enable or disable a model
     */
    suspend fun setModelEnabled(modelId: String, enabled: Boolean) {
        mutex.withLock {
            val config = modelConfigs.getOrPut(modelId) { ModelConfig(modelId) }
            modelConfigs[modelId] = config.copy(enabled = enabled)
            saveConfigurations()
            updateModelStates()
        }
    }

    /**
     * Set alias name for a model
     */
    suspend fun setModelAlias(modelId: String, aliasName: String?) {
        mutex.withLock {
            val config = modelConfigs.getOrPut(modelId) { ModelConfig(modelId) }
            modelConfigs[modelId] = config.copy(aliasName = aliasName)
            saveConfigurations()
            updateModelStates()
        }
    }

    /**
     * Add a custom model
     */
    suspend fun addCustomModel(model: AiModel) {
        mutex.withLock {
            // Ensure it has a unique ID
            val finalModel = if (customModels.any { it.id == model.id } ||
                _allModels.value.any { it.id == model.id }
            ) {
                model.copy(id = "${model.id}_custom_${System.currentTimeMillis()}")
            } else {
                model
            }

            customModels.add(finalModel)
            modelConfigs[finalModel.id] = ModelConfig(finalModel.id, enabled = true)
            saveConfigurations()
            updateModelStates()
        }
    }

    /**
     * Remove a custom model
     */
    suspend fun removeCustomModel(modelId: String) {
        mutex.withLock {
            customModels.removeAll { it.id == modelId }
            modelConfigs.remove(modelId)
            saveConfigurations()
            updateModelStates()
        }
    }

    /**
     * Get model by ID
     */
    fun getModel(modelId: String): AiModel? {
        return _allModels.value.find { it.id == modelId }
    }

    /**
     * Get default model (first enabled model)
     */
    fun getDefaultModel(): AiModel? {
        return _enabledModels.value.firstOrNull()
    }

    /**
     * Get models grouped by provider
     */
    fun getModelsGroupedByProvider(): Map<String, List<AiModel>> {
        return _allModels.value.groupBy { it.providerId }
    }

    /**
     * Get enabled models grouped by provider
     */
    fun getEnabledModelsGroupedByProvider(): Map<String, List<AiModel>> {
        return _enabledModels.value.groupBy { it.providerId }
    }

    /**
     * Enable all models (globally or for a specific provider)
     * @param providerId If specified, only enable models from this provider
     */
    suspend fun enableAllModels(providerId: String? = null) {
        mutex.withLock {
            val modelsToEnable = if (providerId != null) {
                _allModels.value.filter { it.providerId == providerId }
            } else {
                _allModels.value
            }

            modelsToEnable.forEach { model ->
                val config = modelConfigs.getOrPut(model.id) { ModelConfig(model.id) }
                modelConfigs[model.id] = config.copy(enabled = true)
            }

            saveConfigurations()
            updateModelStates()
        }
    }

    /**
     * Disable all models (globally or for a specific provider)
     * @param providerId If specified, only disable models from this provider
     */
    suspend fun disableAllModels(providerId: String? = null) {
        mutex.withLock {
            val modelsToDisable = if (providerId != null) {
                _allModels.value.filter { it.providerId == providerId }
            } else {
                _allModels.value
            }

            modelsToDisable.forEach { model ->
                val config = modelConfigs.getOrPut(model.id) { ModelConfig(model.id) }
                modelConfigs[model.id] = config.copy(enabled = false)
            }

            saveConfigurations()
            updateModelStates()
        }
    }

    /**
     * Get the count of enabled models for a provider
     */
    fun getEnabledCountForProvider(providerId: String): Int {
        return _enabledModels.value.count { it.providerId == providerId }
    }

    /**
     * Get the total count of models for a provider
     */
    fun getTotalCountForProvider(providerId: String): Int {
        return _allModels.value.count { it.providerId == providerId }
    }

    /**
     * Check if all models are enabled for a provider
     */
    fun areAllModelsEnabled(providerId: String): Boolean {
        val total = getTotalCountForProvider(providerId)
        val enabled = getEnabledCountForProvider(providerId)
        return total > 0 && total == enabled
    }

    private fun updateModelStates() {
        val allModelsFromProviders = mutableListOf<AiModel>()

        // Get cached models from providers
        for ((_, provider) in providers) {
            try {
                // Use cached models (don't fetch again) - filter from current state
                val models = _allModels.value.filter { model -> model.providerId == provider.providerId }
                allModelsFromProviders.addAll(models)
            } catch (e: Exception) {
                // Continue
            }
        }

        // Add custom models
        allModelsFromProviders.addAll(customModels)

        // Apply configurations
        val configuredModels = allModelsFromProviders.map { model ->
            val config = modelConfigs[model.id]
            if (config != null) {
                model.copy(
                    enabled = config.enabled,
                    aliasName = config.aliasName
                )
            } else {
                model
            }
        }

        _allModels.value = configuredModels
        _enabledModels.value = configuredModels.filter { it.enabled }
    }

    private fun loadConfigurations() {
        try {
            // Load model configs
            val configsJson = prefs.getString(KEY_MODEL_CONFIGS, null)
            if (configsJson != null) {
                val jsonArray = JSONArray(configsJson)
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val config = ModelConfig(
                        modelId = obj.getString("modelId"),
                        enabled = obj.optBoolean("enabled", true),
                        aliasName = obj.optString("aliasName", null).takeIf { it.isNotEmpty() }
                    )
                    modelConfigs[config.modelId] = config
                }
            }

            // Load custom models
            val customModelsJson = prefs.getString(KEY_CUSTOM_MODELS, null)
            if (customModelsJson != null) {
                val jsonArray = JSONArray(customModelsJson)
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val model = AiModel(
                        id = obj.getString("id"),
                        name = obj.getString("name"),
                        providerId = obj.getString("providerId"),
                        displayName = obj.optString("displayName", obj.getString("name")),
                        description = obj.optString("description", null).takeIf { it.isNotEmpty() },
                        maxTokens = obj.optInt("maxTokens", 0).takeIf { it > 0 },
                        supportsVision = obj.optBoolean("supportsVision", false),
                        supportsTools = obj.optBoolean("supportsTools", false),
                        supportsReasoning = obj.optBoolean("supportsReasoning", false),
                        enabled = obj.optBoolean("enabled", true),
                        aliasName = obj.optString("aliasName", null).takeIf { it.isNotEmpty() }
                    )
                    customModels.add(model)
                }
            }
        } catch (e: Exception) {
            // Start fresh if configs are corrupted
            modelConfigs.clear()
            customModels.clear()
        }
    }

    private fun saveConfigurations() {
        try {
            // Save model configs
            val configsArray = JSONArray()
            for ((_, config) in modelConfigs) {
                configsArray.put(JSONObject().apply {
                    put("modelId", config.modelId)
                    put("enabled", config.enabled)
                    config.aliasName?.let { put("aliasName", it) }
                })
            }
            prefs.edit().putString(KEY_MODEL_CONFIGS, configsArray.toString()).apply()

            // Save custom models
            val customModelsArray = JSONArray()
            for (model in customModels) {
                customModelsArray.put(JSONObject().apply {
                    put("id", model.id)
                    put("name", model.name)
                    put("providerId", model.providerId)
                    put("displayName", model.displayName)
                    model.description?.let { put("description", it) }
                    model.maxTokens?.let { put("maxTokens", it) }
                    put("supportsVision", model.supportsVision)
                    put("supportsTools", model.supportsTools)
                    put("supportsReasoning", model.supportsReasoning)
                    put("enabled", model.enabled)
                    model.aliasName?.let { put("aliasName", it) }
                })
            }
            prefs.edit().putString(KEY_CUSTOM_MODELS, customModelsArray.toString()).apply()
        } catch (e: Exception) {
            // Log error
        }
    }

    companion object {
        private const val PREFS_NAME = "astrostorm_ai_models"
        private const val KEY_MODEL_CONFIGS = "model_configs"
        private const val KEY_CUSTOM_MODELS = "custom_models"

        @Volatile
        private var INSTANCE: AiProviderRegistry? = null

        fun getInstance(context: Context): AiProviderRegistry {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AiProviderRegistry(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }
}

/**
 * Configuration for a model (user preferences)
 */
data class ModelConfig(
    val modelId: String,
    val enabled: Boolean = true,
    val aliasName: String? = null
)

/**
 * State of models loading
 */
sealed class ModelsState {
    object Loading : ModelsState()
    object Refreshing : ModelsState()
    data class Loaded(val count: Int) : ModelsState()
    data class Error(val message: String) : ModelsState()
}
