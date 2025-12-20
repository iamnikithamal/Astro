package com.astro.storm.data.ai.agent.tools

import android.content.Context
import com.astro.storm.data.local.ChartDatabase
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.repository.SavedChart
import org.json.JSONObject

/**
 * Registry for all astrology tools available to the Stormy agent.
 *
 * Manages tool registration, discovery, and execution.
 * Tools provide access to chart data, calculations, and app features.
 */
class AstrologyToolRegistry private constructor(
    private val context: Context
) {
    private val tools = mutableMapOf<String, AstrologyTool>()

    init {
        // Register all available tools

        // === AGENTIC WORKFLOW TOOLS ===
        // Task management and user interaction
        registerTool(StartTaskTool())
        registerTool(FinishTaskTool())
        registerTool(UpdateTodoTool())
        registerTool(AskUserTool())

        // === PROFILE MANAGEMENT TOOLS ===
        // Create, update, delete, and manage profiles
        registerTool(GetProfileInfoTool())
        registerTool(GetAllProfilesTool())
        registerTool(CreateProfileTool())
        registerTool(UpdateProfileTool())
        registerTool(DeleteProfileTool())
        registerTool(SetActiveProfileTool())

        // === CHART DATA TOOLS ===
        // Planetary positions, houses, nakshatras
        registerTool(GetPlanetPositionsTool())
        registerTool(GetHousePositionsTool())
        registerTool(GetNakshatraInfoTool())

        // === DASHA & TIMING TOOLS ===
        // Dasha periods and timing
        registerTool(GetDashaInfoTool())
        registerTool(GetCurrentDashaTool())
        registerTool(CalculateMuhurtaTool())
        registerTool(GetPanchangaTool())

        // === YOGA & STRENGTH TOOLS ===
        // Yogas, strengths, and special combinations
        registerTool(GetYogasTool())
        registerTool(GetAshtakavargaTool())
        registerTool(GetStrengthAnalysisTool())
        registerTool(GetVipareetaRajaYogaTool())

        // === TRANSIT & COMPATIBILITY TOOLS ===
        // Transits and relationship analysis
        registerTool(GetTransitsTool())
        registerTool(GetCompatibilityTool())
        registerTool(GetCompatibilityDeepDiveTool())

        // === ADVANCED ANALYSIS TOOLS ===
        // Divisional charts, special points, and advanced techniques
        registerTool(GetDivisionalChartTool())
        registerTool(GetBhriguBinduTool())
        registerTool(GetArgalaTool())
        registerTool(GetPrashnaAnalysisTool())
        registerTool(GetMarakaAnalysisTool())
        registerTool(GetBadhakaAnalysisTool())

        // === REMEDIES ===
        registerTool(GetRemediesTool())
    }

    /**
     * Register a tool
     */
    fun registerTool(tool: AstrologyTool) {
        tools[tool.name] = tool
    }

    /**
     * Get a tool by name
     */
    fun getTool(name: String): AstrologyTool? = tools[name]

    /**
     * Get all registered tools
     */
    fun getAllTools(): List<AstrologyTool> = tools.values.toList()

    /**
     * Generate tools description for the system prompt
     */
    fun getToolsDescription(): String {
        val sb = StringBuilder()
        sb.appendLine("### Available Tools:")
        sb.appendLine()

        tools.values.sortedBy { it.name }.forEach { tool ->
            sb.appendLine("**${tool.name}**")
            sb.appendLine("${tool.description}")
            sb.appendLine()
            sb.appendLine("Arguments:")
            tool.parameters.forEach { param ->
                val required = if (param.required) " (required)" else " (optional)"
                sb.appendLine("- `${param.name}`: ${param.description}$required")
            }
            sb.appendLine()
        }

        return sb.toString()
    }

    /**
     * Execute a tool with comprehensive error handling and parameter validation
     *
     * This method handles:
     * - Tool lookup with fuzzy matching for typos
     * - Parameter validation and type coercion
     * - Graceful error recovery with helpful messages
     * - Timeout protection for long-running operations
     */
    suspend fun executeTool(
        toolName: String,
        arguments: JSONObject,
        currentProfile: SavedChart?,
        allProfiles: List<SavedChart>,
        currentChart: VedicChart?
    ): ToolExecutionResult {
        // Normalize tool name (handle common variations)
        val normalizedName = toolName.lowercase()
            .replace("-", "_")
            .replace(" ", "_")
            .trim()

        // Try exact match first, then fuzzy match
        val tool = tools[normalizedName]
            ?: tools[toolName]
            ?: findToolByFuzzyMatch(normalizedName)
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "Tool not found: $toolName. Available tools: ${tools.keys.sorted().joinToString(", ")}",
                summary = "Unknown tool '$toolName'"
            )

        // Validate required parameters
        val validationError = validateToolParameters(tool, arguments)
        if (validationError != null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = validationError,
                summary = "Invalid parameters"
            )
        }

        // Check for required context (profile/chart)
        val contextError = validateToolContext(tool.name, currentProfile, currentChart)
        if (contextError != null) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = contextError,
                summary = "Missing context"
            )
        }

        return try {
            val toolContext = ToolContext(
                context = context,
                currentProfile = currentProfile,
                allProfiles = allProfiles,
                currentChart = currentChart,
                database = ChartDatabase.getInstance(context)
            )

            // Execute with timeout protection (30 seconds max)
            kotlinx.coroutines.withTimeout(30000L) {
                tool.execute(arguments, toolContext)
            }
        } catch (e: kotlinx.coroutines.TimeoutCancellationException) {
            ToolExecutionResult(
                success = false,
                data = null,
                error = "Tool execution timed out after 30 seconds",
                summary = "Timeout"
            )
        } catch (e: org.json.JSONException) {
            ToolExecutionResult(
                success = false,
                data = null,
                error = "Invalid JSON in tool arguments: ${e.message}",
                summary = "JSON error"
            )
        } catch (e: IllegalArgumentException) {
            ToolExecutionResult(
                success = false,
                data = null,
                error = "Invalid argument: ${e.message}",
                summary = "Invalid argument"
            )
        } catch (e: NullPointerException) {
            ToolExecutionResult(
                success = false,
                data = null,
                error = "Missing required data: ${e.message ?: "null reference"}",
                summary = "Missing data"
            )
        } catch (e: Exception) {
            ToolExecutionResult(
                success = false,
                data = null,
                error = "${e.javaClass.simpleName}: ${e.message ?: "Unknown error"}",
                summary = "Execution failed"
            )
        }
    }

    /**
     * Find tool by fuzzy matching (handles typos and similar names)
     */
    private fun findToolByFuzzyMatch(searchName: String): AstrologyTool? {
        // Try to find tool with similar name
        val searchTerms = searchName.split("_")

        // Find tools that contain all search terms
        val candidates = tools.values.filter { tool ->
            val toolTerms = tool.name.split("_")
            searchTerms.all { term ->
                toolTerms.any { it.contains(term) || term.contains(it) }
            }
        }

        // Return best match (prefer exact term matches)
        return candidates.maxByOrNull { tool ->
            val toolTerms = tool.name.split("_")
            searchTerms.count { term -> toolTerms.contains(term) }
        }
    }

    /**
     * Validate tool parameters against tool definition
     */
    private fun validateToolParameters(tool: AstrologyTool, arguments: JSONObject): String? {
        val missingRequired = mutableListOf<String>()

        tool.parameters.forEach { param ->
            if (param.required && !arguments.has(param.name)) {
                // Check for common alternative names
                val alternativeNames = getAlternativeParamNames(param.name)
                val hasAlternative = alternativeNames.any { arguments.has(it) }
                if (!hasAlternative) {
                    missingRequired.add(param.name)
                }
            }
        }

        return if (missingRequired.isEmpty()) {
            null
        } else {
            "Missing required parameter${if (missingRequired.size > 1) "s" else ""}: ${missingRequired.joinToString(", ")}"
        }
    }

    /**
     * Get alternative parameter names for fuzzy matching
     */
    private fun getAlternativeParamNames(paramName: String): List<String> {
        return when (paramName) {
            "profile_id" -> listOf("profileId", "profile", "id", "chart_id", "chartId")
            "planet_name" -> listOf("planetName", "planet", "graha")
            "house_number" -> listOf("houseNumber", "house", "bhava")
            "divisional_chart" -> listOf("divisionalChart", "varga", "division", "chart_type")
            "target_profile_id" -> listOf("targetProfileId", "target_id", "partner_id", "partnerId")
            "start_date" -> listOf("startDate", "from_date", "fromDate", "date")
            "end_date" -> listOf("endDate", "to_date", "toDate")
            else -> listOf(
                paramName.replace("_", ""),  // remove underscores
                paramName.split("_").joinToString("") { it.replaceFirstChar { c -> c.uppercase() } }.replaceFirstChar { it.lowercase() }  // camelCase
            )
        }
    }

    /**
     * Validate that required context is available for the tool
     */
    private fun validateToolContext(toolName: String, currentProfile: SavedChart?, currentChart: VedicChart?): String? {
        // Tools that require a profile/chart
        val profileRequiredTools = setOf(
            "get_planet_positions", "get_house_positions", "get_nakshatra_info",
            "get_dasha_info", "get_current_dasha", "get_yogas", "get_ashtakavarga",
            "get_transits", "get_remedies", "get_strength_analysis",
            "get_divisional_chart", "get_bhrigu_bindu", "get_argala",
            "get_maraka_analysis", "get_badhaka_analysis", "get_vipareeta_raja_yoga"
        )

        if (toolName in profileRequiredTools && currentProfile == null && currentChart == null) {
            return "This tool requires an active profile. Please select or create a birth chart first."
        }

        // Tools that require two profiles for comparison
        val comparisonTools = setOf("get_compatibility", "get_compatibility_deep_dive")
        if (toolName in comparisonTools && currentProfile == null) {
            return "Compatibility analysis requires a selected profile. Please select a birth chart first."
        }

        return null
    }

    companion object {
        @Volatile
        private var INSTANCE: AstrologyToolRegistry? = null

        fun getInstance(context: Context): AstrologyToolRegistry {
            return INSTANCE ?: synchronized(this) {
                AstrologyToolRegistry(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }
}

/**
 * Base interface for all astrology tools
 */
interface AstrologyTool {
    /**
     * Unique name of the tool
     */
    val name: String

    /**
     * Description of what the tool does
     */
    val description: String

    /**
     * Parameters the tool accepts
     */
    val parameters: List<ToolParameter>

    /**
     * Execute the tool with given arguments
     */
    suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult
}

/**
 * Tool parameter definition
 */
data class ToolParameter(
    val name: String,
    val description: String,
    val type: ParameterType,
    val required: Boolean = false,
    val defaultValue: Any? = null
)

/**
 * Parameter types
 */
enum class ParameterType {
    STRING,
    INTEGER,
    BOOLEAN,
    NUMBER,
    ARRAY
}

/**
 * Context provided to tool execution
 */
data class ToolContext(
    val context: Context,
    val currentProfile: SavedChart?,
    val allProfiles: List<SavedChart>,
    val currentChart: VedicChart?,
    val database: ChartDatabase
)

/**
 * Result of tool execution
 */
data class ToolExecutionResult(
    val success: Boolean,
    val data: JSONObject?,
    val error: String? = null,
    val summary: String
) {
    fun toJson(): String {
        return JSONObject().apply {
            put("success", success)
            data?.let { put("data", it) }
            error?.let { put("error", it) }
            put("summary", summary)
        }.toString()
    }
}
