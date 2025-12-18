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
        registerTool(GetProfileInfoTool())
        registerTool(GetAllProfilesTool())
        registerTool(GetPlanetPositionsTool())
        registerTool(GetHousePositionsTool())
        registerTool(GetNakshatraInfoTool())
        registerTool(GetDashaInfoTool())
        registerTool(GetCurrentDashaTool())
        registerTool(GetYogasTool())
        registerTool(GetAshtakavargaTool())
        registerTool(GetPanchangaTool())
        registerTool(GetTransitsTool())
        registerTool(GetCompatibilityTool())
        registerTool(GetRemediesTool())
        registerTool(GetStrengthAnalysisTool())
        registerTool(GetDivisionalChartTool())
        registerTool(CalculateMuhurtaTool())
        registerTool(GetBhriguBinduTool())
        registerTool(GetArgalaTool())
        registerTool(GetPrashnaAnalysisTool())
        registerTool(GetCompatibilityDeepDiveTool())
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
     * Execute a tool
     */
    suspend fun executeTool(
        toolName: String,
        arguments: JSONObject,
        currentProfile: SavedChart?,
        allProfiles: List<SavedChart>,
        currentChart: VedicChart?
    ): ToolExecutionResult {
        val tool = tools[toolName]
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "Tool not found: $toolName",
                summary = "Unknown tool"
            )

        return try {
            val toolContext = ToolContext(
                context = context,
                currentProfile = currentProfile,
                allProfiles = allProfiles,
                currentChart = currentChart,
                database = ChartDatabase.getInstance(context)
            )

            tool.execute(arguments, toolContext)
        } catch (e: Exception) {
            ToolExecutionResult(
                success = false,
                data = null,
                error = e.message ?: "Unknown error",
                summary = "Tool execution failed"
            )
        }
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
