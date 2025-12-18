package com.astro.storm.data.ai.agent

import android.content.Context
import com.astro.storm.data.ai.provider.AiModel
import com.astro.storm.data.ai.provider.AiProvider
import com.astro.storm.data.ai.provider.AiProviderRegistry
import com.astro.storm.data.ai.provider.ChatMessage
import com.astro.storm.data.ai.provider.ChatResponse
import com.astro.storm.data.ai.provider.FunctionCall
import com.astro.storm.data.ai.provider.MessageRole
import com.astro.storm.data.ai.provider.ToolCall
import com.astro.storm.data.ai.agent.tools.AstrologyToolRegistry
import com.astro.storm.data.ai.agent.tools.ToolExecutionResult
import com.astro.storm.data.local.ChartDatabase
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.repository.SavedChart
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

/**
 * Stormy - The Vedic Astrology AI Assistant
 *
 * Stormy is an intelligent agent capable of:
 * - Providing Vedic astrology insights and guidance
 * - Executing tools to fetch chart data and perform calculations
 * - Supporting multiple AI models (model-agnostic)
 * - Handling tool calls through JSON parsing (works even with models without native tool support)
 */
class StormyAgent private constructor(
    private val context: Context,
    private val providerRegistry: AiProviderRegistry,
    private val toolRegistry: AstrologyToolRegistry
) {

    companion object {
        const val AGENT_NAME = "Stormy"
        const val AGENT_DESCRIPTION = "Your Vedic Astrology AI Assistant"

        /**
         * Maximum number of autonomous tool execution iterations.
         * The agent can call tools up to this many times in a single request
         * to accomplish the user's task autonomously.
         */
        private const val MAX_TOOL_ITERATIONS = 15

        /**
         * Maximum total iterations (including non-tool responses)
         * to prevent infinite loops in edge cases
         */
        private const val MAX_TOTAL_ITERATIONS = 20

        @Volatile
        private var INSTANCE: StormyAgent? = null

        fun getInstance(context: Context): StormyAgent {
            return INSTANCE ?: synchronized(this) {
                val appContext = context.applicationContext
                val registry = AiProviderRegistry.getInstance(appContext)
                val toolRegistry = AstrologyToolRegistry.getInstance(appContext)
                StormyAgent(appContext, registry, toolRegistry).also {
                    INSTANCE = it
                }
            }
        }
    }

    /**
     * Generate the system prompt for Stormy
     */
    fun generateSystemPrompt(
        currentProfile: SavedChart?,
        allProfiles: List<SavedChart>,
        currentChart: VedicChart?
    ): String {
        val profileContext = buildProfileContext(currentProfile, allProfiles, currentChart)
        val toolsDescription = toolRegistry.getToolsDescription()

        return """
You are Stormy, an expert Vedic astrologer and AI assistant in the AstroStorm app. You provide accurate, insightful, and helpful astrological guidance based on authentic Vedic astrology principles (Jyotish Shastra).

## Your Expertise
- Deep knowledge of Vedic astrology including Parashari, Jaimini, and Nadi systems
- Planetary analysis (Grahas), houses (Bhavas), signs (Rashis), and constellations (Nakshatras)
- Dasha systems (Vimshottari, Yogini, Chara, Kalachakra, Ashtottari)
- Yogas (planetary combinations) and their effects
- Transits (Gochar) and their impacts
- Divisional charts (Vargas/Shodashvarga)
- Muhurta (electional astrology) and auspicious timing
- Remedial measures (Upayas) - mantras, gemstones, rituals
- Matchmaking (Kundli Milan) and compatibility analysis

## Communication Style
- Be warm, professional, and compassionate
- Provide practical, actionable insights
- Explain complex concepts in accessible terms
- Be honest about limitations and uncertainties
- Respect users' beliefs while maintaining astrological accuracy

## Important Guidelines
1. Always base your analysis on classical Vedic astrology texts and principles
2. When discussing predictions, emphasize free will and the indicative nature of astrology
3. Avoid making absolute statements about health, death, or severe negative events
4. Recommend professional consultation for serious life decisions
5. Be culturally sensitive when discussing remedies

$profileContext

## Available Tools
You can call the following tools to get information from the app. To call a tool, respond with a JSON block in this exact format:

```tool_call
{
  "tool": "tool_name",
  "arguments": {
    "arg1": "value1",
    "arg2": "value2"
  }
}
```

$toolsDescription

## Tool Usage Guidelines
1. Call tools when you need specific chart data or calculations
2. You can call multiple tools by including multiple tool_call blocks
3. After receiving tool results, synthesize the information into a helpful response
4. If a tool returns an error, explain the issue and suggest alternatives
5. Always verify you have the necessary profile/chart before calling chart-specific tools

## Autonomous Behavior Guidelines
- Work AUTONOMOUSLY to accomplish the user's request without waiting for further input
- If you need data, call the appropriate tools immediately
- Continue analyzing and calling tools until you can provide a COMPLETE response
- DO NOT stop midway with incomplete analysis - gather all necessary information first
- If you need to clarify something with the user, do so AFTER providing what you can
- Provide comprehensive, detailed responses that fully address the user's query
- Only ask clarifying questions if absolutely necessary for the core request
- Think step-by-step: what data do I need? Call tools. What does this mean? Explain thoroughly.

Remember: You are Stormy, a knowledgeable and caring astrology assistant. Help users understand their charts, make informed decisions, and find guidance through the wisdom of Vedic astrology. Always strive to provide COMPLETE, actionable insights.
        """.trimIndent()
    }

    /**
     * Build context about available profiles and current chart
     */
    private fun buildProfileContext(
        currentProfile: SavedChart?,
        allProfiles: List<SavedChart>,
        currentChart: VedicChart?
    ): String {
        val sb = StringBuilder()
        sb.appendLine("## Current Context")
        sb.appendLine()

        if (currentProfile != null) {
            sb.appendLine("**Active Profile:** ${currentProfile.name}")
            sb.appendLine("- Birth Location: ${currentProfile.location}")
            sb.appendLine("- Birth Date/Time: ${currentProfile.dateTime}")
            sb.appendLine("- Profile ID: ${currentProfile.id}")

            if (currentChart != null) {
                sb.appendLine("- Ascendant (Lagna): ${currentChart.planetPositions.find { it.planet.displayName == "Ascendant" }?.sign?.displayName ?: "Available"}")
                sb.appendLine("- Moon Sign (Rashi): ${currentChart.planetPositions.find { it.planet.displayName == "Moon" }?.sign?.displayName ?: "Available"}")
            }
        } else {
            sb.appendLine("**No profile is currently selected.** Ask the user to create or select a birth chart profile to provide personalized readings.")
        }

        sb.appendLine()

        if (allProfiles.isNotEmpty()) {
            sb.appendLine("**Available Profiles:** (${allProfiles.size} total)")
            allProfiles.take(5).forEach { profile ->
                val marker = if (profile.id == currentProfile?.id) " [ACTIVE]" else ""
                sb.appendLine("- ${profile.name}$marker (ID: ${profile.id})")
            }
            if (allProfiles.size > 5) {
                sb.appendLine("- ... and ${allProfiles.size - 5} more")
            }
        } else {
            sb.appendLine("**No profiles available.** Encourage the user to create their first birth chart.")
        }

        sb.appendLine()
        sb.appendLine("**Current Date:** ${SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.getDefault()).format(Date())}")

        return sb.toString()
    }

    /**
     * Process a user message and generate a response with tool calling support
     */
    fun processMessage(
        messages: List<ChatMessage>,
        model: AiModel,
        currentProfile: SavedChart?,
        allProfiles: List<SavedChart>,
        currentChart: VedicChart?,
        temperature: Float? = null,
        maxTokens: Int? = null
    ): Flow<AgentResponse> = flow {
        val provider = providerRegistry.getProvider(model.providerId)
            ?: throw IllegalStateException("Provider not found: ${model.providerId}")

        val systemPrompt = generateSystemPrompt(currentProfile, allProfiles, currentChart)

        // Build messages with system prompt
        val fullMessages = mutableListOf<ChatMessage>()
        fullMessages.add(ChatMessage(role = MessageRole.SYSTEM, content = systemPrompt))
        fullMessages.addAll(messages)

        var iteration = 0
        var toolIterations = 0
        var continueProcessing = true
        val toolsUsed = mutableListOf<String>()

        // Track content from each iteration separately to detect duplicates
        val contentByIteration = mutableListOf<String>()
        val allReasoning = StringBuilder()

        // Keep track of what we've already emitted to avoid duplicates
        var totalEmittedContent = StringBuilder()

        while (continueProcessing && iteration < MAX_TOTAL_ITERATIONS && toolIterations < MAX_TOOL_ITERATIONS) {
            iteration++
            var currentContent = StringBuilder()
            var currentReasoning = StringBuilder()
            var pendingToolCalls = mutableListOf<ToolCallRequest>()
            var hasError = false
            var errorMessage: String? = null
            var receivedDone = false

            // Call the AI model
            provider.chat(
                messages = fullMessages,
                model = model.id,
                temperature = temperature,
                maxTokens = maxTokens,
                stream = true
            ).collect { response ->
                when (response) {
                    is ChatResponse.Content -> {
                        currentContent.append(response.text)
                        emit(AgentResponse.ContentChunk(response.text))
                    }
                    is ChatResponse.Reasoning -> {
                        // Skip empty reasoning markers
                        if (response.text.isNotEmpty()) {
                            currentReasoning.append(response.text)
                            emit(AgentResponse.ReasoningChunk(response.text))
                        }
                    }
                    is ChatResponse.ToolCallRequest -> {
                        response.toolCalls.forEach { call ->
                            pendingToolCalls.add(
                                ToolCallRequest(
                                    id = call.id.ifEmpty { "tool_${UUID.randomUUID().toString().take(8)}" },
                                    name = call.function.name,
                                    arguments = call.function.arguments
                                )
                            )
                        }
                    }
                    is ChatResponse.Error -> {
                        hasError = true
                        errorMessage = response.message
                        emit(AgentResponse.Error(response.message, response.isRetryable))
                    }
                    is ChatResponse.Usage -> {
                        emit(AgentResponse.TokenUsage(
                            response.promptTokens,
                            response.completionTokens,
                            response.totalTokens
                        ))
                    }
                    is ChatResponse.Done -> {
                        receivedDone = true
                        // Check for embedded tool calls in content
                        if (pendingToolCalls.isEmpty()) {
                            pendingToolCalls.addAll(parseEmbeddedToolCalls(currentContent.toString()))
                        }
                    }
                    is ChatResponse.ProviderInfo -> {
                        emit(AgentResponse.ModelInfo(response.providerId, response.model))
                    }
                    is ChatResponse.RetryNotification -> {
                        // Emit retry notification so UI can show it
                        emit(AgentResponse.RetryInfo(
                            attempt = response.attempt,
                            maxAttempts = response.maxAttempts,
                            delayMs = response.delayMs,
                            reason = response.reason
                        ))
                    }
                }
            }

            // Clean current content of tool call blocks
            val cleanedCurrentContent = currentContent.toString().cleanToolCallBlocks().trim()

            // Only add this iteration's content if it's not a duplicate of previous content
            // This prevents the model from repeating the same response multiple times
            if (cleanedCurrentContent.isNotEmpty()) {
                val isDuplicate = contentByIteration.any { previousContent ->
                    // Check if this content is substantially similar to any previous iteration
                    // Either identical, or one contains the other (common in multi-turn)
                    previousContent.trim() == cleanedCurrentContent ||
                    previousContent.contains(cleanedCurrentContent) ||
                    cleanedCurrentContent.contains(previousContent.trim())
                }

                if (!isDuplicate) {
                    contentByIteration.add(cleanedCurrentContent)
                }
            }

            // Accumulate reasoning (reasoning can be additive across iterations)
            if (currentReasoning.isNotEmpty()) {
                if (allReasoning.isNotEmpty()) {
                    allReasoning.append("\n\n")
                }
                allReasoning.append(currentReasoning)
            }

            if (hasError) {
                continueProcessing = false
                continue
            }

            // Process tool calls if any
            if (pendingToolCalls.isNotEmpty()) {
                toolIterations++
                emit(AgentResponse.ToolCallsStarted(pendingToolCalls.map { it.name }))

                // Add assistant message with tool calls
                val assistantContent = currentContent.toString().cleanToolCallBlocks()
                fullMessages.add(ChatMessage(
                    role = MessageRole.ASSISTANT,
                    content = assistantContent.ifEmpty { "I'll use some tools to help answer your question." },
                    toolCalls = pendingToolCalls.map { call ->
                        ToolCall(
                            id = call.id,
                            function = FunctionCall(call.name, call.arguments)
                        )
                    }
                ))

                // Execute each tool and add results
                for (toolCall in pendingToolCalls) {
                    emit(AgentResponse.ToolExecuting(toolCall.name))
                    if (!toolsUsed.contains(toolCall.name)) {
                        toolsUsed.add(toolCall.name)
                    }

                    val result = executeToolCall(toolCall, currentProfile, allProfiles, currentChart)

                    emit(AgentResponse.ToolResult(toolCall.name, result.success, result.summary))

                    // Add tool result message
                    fullMessages.add(ChatMessage(
                        role = MessageRole.TOOL,
                        content = result.toJson(),
                        toolCallId = toolCall.id,
                        name = toolCall.name
                    ))
                }

                // Continue processing to let AI respond to tool results
                // The agent will autonomously continue until it has enough info
            } else {
                // No tool calls - combine unique content from all iterations
                // Use only the last (most complete) response if there are multiple iterations with content
                // This handles the case where models repeat content after tool calls
                val finalContent = if (contentByIteration.isNotEmpty()) {
                    // Take the last non-empty content as it's typically the most complete response
                    // after all tool results have been processed
                    contentByIteration.last()
                } else {
                    ""
                }
                val finalReasoning = allReasoning.toString().trim()

                // Check if we only have reasoning but no content
                // This can happen with thinking models like Kimi K2, DeepSeek R1, QwQ
                // They sometimes emit only reasoning_content without content field
                if (finalContent.isEmpty() && finalReasoning.isNotEmpty()) {
                    // For reasoning-only responses, check if we used tools and might need continuation
                    if (toolsUsed.isNotEmpty() && iteration < MAX_TOTAL_ITERATIONS - 1) {
                        // We used tools but got no final answer - prompt for continuation
                        // Add a hint message to encourage the model to provide the final answer
                        fullMessages.add(ChatMessage(
                            role = MessageRole.ASSISTANT,
                            content = finalReasoning
                        ))

                        fullMessages.add(ChatMessage(
                            role = MessageRole.USER,
                            content = "Please provide your analysis and answer based on the tool results above."
                        ))

                        // Clear content for the continuation
                        contentByIteration.clear()
                        allReasoning.clear()

                        // Continue for one more iteration
                        continue
                    }
                    // If no tools were used, or we've exhausted iterations,
                    // treat reasoning as the content (some models only output in reasoning_content)
                    // This ensures the user sees something
                }

                // We're done - emit the complete response
                // If we have no content but have reasoning, use reasoning as a fallback
                // This handles cases where models emit only in reasoning_content
                val contentToEmit = if (finalContent.isEmpty() && finalReasoning.isNotEmpty()) {
                    // Use reasoning as the actual response when there's no content
                    // Clear reasoning since we're using it as content
                    finalReasoning
                } else {
                    finalContent
                }

                // Only keep reasoning separate if we have both content AND reasoning
                val reasoningToEmit = if (finalContent.isNotEmpty() && finalReasoning.isNotEmpty()) {
                    finalReasoning
                } else {
                    null
                }

                continueProcessing = false
                emit(AgentResponse.Complete(
                    content = contentToEmit,
                    reasoning = reasoningToEmit,
                    toolsUsed = toolsUsed.distinct().toList()
                ))
            }
        }

        if (iteration >= MAX_TOTAL_ITERATIONS || toolIterations >= MAX_TOOL_ITERATIONS) {
            // Still emit what we have - use the last content iteration
            val finalContent = if (contentByIteration.isNotEmpty()) {
                contentByIteration.last()
            } else {
                ""
            }
            val finalReasoning = allReasoning.toString().trim()

            if (finalContent.isNotEmpty() || finalReasoning.isNotEmpty()) {
                // If we have no content but have reasoning, use reasoning as content
                val contentToEmit = if (finalContent.isEmpty() && finalReasoning.isNotEmpty()) {
                    finalReasoning
                } else {
                    finalContent.ifEmpty { "I apologize, but I wasn't able to complete my analysis within the allowed iterations. Here's what I was able to determine..." }
                }

                // Only include reasoning separately if we have both
                val reasoningToEmit = if (finalContent.isNotEmpty() && finalReasoning.isNotEmpty()) {
                    finalReasoning
                } else {
                    null
                }

                emit(AgentResponse.Complete(
                    content = contentToEmit,
                    reasoning = reasoningToEmit,
                    toolsUsed = toolsUsed.distinct().toList()
                ))
            }

            emit(AgentResponse.Error(
                "Maximum iterations reached ($iteration total, $toolIterations tool calls). The analysis may be incomplete.",
                isRetryable = false
            ))
        }
    }

    /**
     * Parse embedded tool calls from AI response content
     */
    private fun parseEmbeddedToolCalls(content: String): List<ToolCallRequest> {
        val toolCalls = mutableListOf<ToolCallRequest>()

        // Look for tool_call JSON blocks
        val pattern = Regex("""```tool_call\s*\n?\s*(\{[\s\S]*?\})\s*\n?```""", RegexOption.MULTILINE)
        val matches = pattern.findAll(content)

        for (match in matches) {
            try {
                val jsonStr = match.groupValues[1].trim()
                val json = JSONObject(jsonStr)
                val toolName = json.getString("tool")
                val arguments = json.optJSONObject("arguments")?.toString() ?: "{}"

                toolCalls.add(ToolCallRequest(
                    id = "tool_${UUID.randomUUID().toString().take(8)}",
                    name = toolName,
                    arguments = arguments
                ))
            } catch (e: Exception) {
                // Invalid JSON, skip
            }
        }

        // Also check for inline JSON tool calls (for models that don't use code blocks)
        val inlinePattern = Regex("""\{"tool"\s*:\s*"([^"]+)"\s*,\s*"arguments"\s*:\s*(\{[^}]*\})\s*\}""")
        val inlineMatches = inlinePattern.findAll(content)

        for (match in inlineMatches) {
            try {
                val toolName = match.groupValues[1]
                val arguments = match.groupValues[2]

                // Avoid duplicates
                if (toolCalls.none { it.name == toolName }) {
                    toolCalls.add(ToolCallRequest(
                        id = "tool_${UUID.randomUUID().toString().take(8)}",
                        name = toolName,
                        arguments = arguments
                    ))
                }
            } catch (e: Exception) {
                // Invalid format, skip
            }
        }

        return toolCalls
    }

    /**
     * Execute a tool call
     */
    private suspend fun executeToolCall(
        toolCall: ToolCallRequest,
        currentProfile: SavedChart?,
        allProfiles: List<SavedChart>,
        currentChart: VedicChart?
    ): ToolExecutionResult {
        return try {
            val arguments = try {
                JSONObject(toolCall.arguments)
            } catch (e: Exception) {
                JSONObject()
            }

            toolRegistry.executeTool(
                toolName = toolCall.name,
                arguments = arguments,
                currentProfile = currentProfile,
                allProfiles = allProfiles,
                currentChart = currentChart
            )
        } catch (e: Exception) {
            ToolExecutionResult(
                success = false,
                data = null,
                error = e.message ?: "Unknown error executing tool",
                summary = "Failed to execute ${toolCall.name}"
            )
        }
    }

    /**
     * Clean tool call blocks from content for display
     */
    private fun String.cleanToolCallBlocks(): String {
        return this
            .replace(Regex("""```tool_call\s*\n?\s*\{[\s\S]*?\}\s*\n?```"""), "")
            .replace(Regex("""\{"tool"\s*:\s*"[^"]+"\s*,\s*"arguments"\s*:\s*\{[^}]*\}\s*\}"""), "")
            .trim()
    }
}

/**
 * Tool call request data
 */
data class ToolCallRequest(
    val id: String,
    val name: String,
    val arguments: String
)

/**
 * Sealed class for agent responses
 */
sealed class AgentResponse {
    /**
     * Content chunk during streaming
     */
    data class ContentChunk(val text: String) : AgentResponse()

    /**
     * Reasoning/thinking chunk
     */
    data class ReasoningChunk(val text: String) : AgentResponse()

    /**
     * Model information
     */
    data class ModelInfo(val providerId: String, val model: String) : AgentResponse()

    /**
     * Tool calls are starting
     */
    data class ToolCallsStarted(val toolNames: List<String>) : AgentResponse()

    /**
     * A tool is being executed
     */
    data class ToolExecuting(val toolName: String) : AgentResponse()

    /**
     * Tool execution result
     */
    data class ToolResult(
        val toolName: String,
        val success: Boolean,
        val summary: String
    ) : AgentResponse()

    /**
     * Token usage information
     */
    data class TokenUsage(
        val promptTokens: Int,
        val completionTokens: Int,
        val totalTokens: Int
    ) : AgentResponse()

    /**
     * Error occurred
     */
    data class Error(
        val message: String,
        val isRetryable: Boolean = false
    ) : AgentResponse()

    /**
     * Retry notification (rate limit or transient error)
     */
    data class RetryInfo(
        val attempt: Int,
        val maxAttempts: Int,
        val delayMs: Long,
        val reason: String
    ) : AgentResponse()

    /**
     * Response complete
     */
    data class Complete(
        val content: String,
        val reasoning: String?,
        val toolsUsed: List<String>
    ) : AgentResponse()
}
