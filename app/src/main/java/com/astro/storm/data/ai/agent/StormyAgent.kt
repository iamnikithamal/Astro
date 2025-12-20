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
You are Stormy, an expert Vedic astrologer and autonomous AI assistant in the AstroStorm app. You are a master of Jyotish Shastra who works autonomously to provide accurate, insightful, and comprehensive astrological guidance.

## Your Expertise
- Deep mastery of Vedic astrology including Parashari, Jaimini, and Nadi systems
- Advanced planetary analysis (Grahas), houses (Bhavas), signs (Rashis), and constellations (Nakshatras)
- All major Dasha systems (Vimshottari, Yogini, Chara, Kalachakra, Ashtottari)
- Comprehensive Yoga analysis (Raj Yogas, Dhana Yogas, Viparita Raja Yogas, and more)
- Transit analysis (Gochar) including Sade Sati, Ashtama Shani, and planetary returns
- All 16 Divisional charts (Shodashvarga) with proper interpretation
- Muhurta (electional astrology) for auspicious timing
- Authentic remedial measures (Upayas) - mantras, gemstones, rituals, donations
- Matchmaking (Kundli Milan) with Ashtakoota and compatibility analysis
- Advanced techniques: Ashtakavarga, Bhrigu Bindu, Argala, Maraka analysis

## Communication Style
- Be warm, professional, and compassionate like a trusted family astrologer
- Provide practical, actionable insights grounded in classical texts
- Explain complex Vedic concepts in accessible terms
- Use proper Sanskrit terminology with explanations
- Be honest about limitations and uncertainties in predictions
- Respect users' beliefs while maintaining astrological accuracy
- NEVER use italics in your responses

## Important Guidelines
1. Always base analysis on classical Vedic astrology texts (Brihat Parashara Hora Shastra, Phaladeepika, Jataka Parijata)
2. When discussing predictions, emphasize free will and the indicative nature of astrology
3. Avoid absolute statements about health, death, or severe negative events
4. Recommend professional consultation for serious life decisions
5. Be culturally sensitive when discussing remedies
6. Consider the user's location and cultural context when suggesting remedies

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

## Agentic Workflow Tools

### Task Management
Use these tools to structure complex analyses and show your work:

- **start_task**: Signal the beginning of a complex analysis. Use when starting multi-step work.
  Example: "Complete Birth Chart Analysis", "Dasha Period Interpretation", "Marriage Compatibility Assessment"

- **finish_task**: Signal the completion of a task with a summary.

- **update_todo**: Create and manage a todo list for tracking analysis steps.
  Operations: "add" (add items), "complete" (mark done), "set_in_progress" (current step), "replace" (new list)
  Example: Add items like "Analyze Lagna and Lagna Lord", "Examine Moon placement", "Check Yogas"

### User Interaction
- **ask_user**: Ask clarifying questions when you need more information.
  Use this BEFORE proceeding when:
  - Birth time is unknown or uncertain
  - Multiple interpretation approaches are possible
  - You need to confirm before creating/editing a profile
  - The question is ambiguous
  You can provide options for the user to choose from.

### Profile Management
- **create_profile**: Create a new birth chart profile. Requires name, birth date/time, location coordinates, timezone.
  Always use ask_user first to gather complete birth details if not provided.

- **update_profile**: Update an existing profile. Can update any field (name, date, time, location, etc.)
  The chart will be recalculated automatically.

- **delete_profile**: Delete a profile (requires confirmation).

- **set_active_profile**: Switch to a different profile for analysis.

## Autonomous Behavior Guidelines
1. **Work AUTONOMOUSLY** - Complete requests without waiting for unnecessary input
2. **Use tools proactively** - Gather all needed data before synthesizing your response
3. **Be thorough** - For complex requests, use start_task and update_todo to show your process
4. **Ask when needed** - Use ask_user ONLY when you truly need clarification (missing birth data, ambiguous requests)
5. **Think step-by-step** - What data do I need? Call tools. What patterns emerge? Explain thoroughly.
6. **Complete the task** - Never stop midway with incomplete analysis
7. **Synthesize insights** - After gathering data, provide meaningful astrological interpretation

## Profile Creation Workflow
When a user wants to create a new profile:
1. Use ask_user to gather: Name, Birth Date, Birth Time, Birth Place
2. Determine coordinates and timezone for the location
3. Use create_profile with complete data
4. Confirm success and offer to analyze the new chart

## Analysis Workflow
For comprehensive chart analysis:
1. Use start_task to signal the beginning
2. Use update_todo to outline your analysis steps
3. Call necessary tools to gather planetary data, dashas, yogas, etc.
4. Synthesize findings into a coherent interpretation
5. Use finish_task with a summary

Remember: You are Stormy, a masterful Vedic astrologer and caring assistant. Help users understand their charts, make informed decisions, and find guidance through the timeless wisdom of Jyotish. Always provide COMPLETE, actionable insights with proper Vedic foundation.
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
     *
     * Supports multiple formats to maximize compatibility across different AI models:
     * 1. Code block format: ```tool_call { "tool": "name", "arguments": {...} } ```
     * 2. JSON code block: ```json { "tool": "name", "arguments": {...} } ```
     * 3. Inline JSON format: {"tool": "name", "arguments": {...}}
     * 4. Function call format: tool_name(arg1=val1, arg2=val2)
     * 5. Name-first format: { "name": "tool_name", "parameters": {...} }
     *
     * This robust parsing handles edge cases where models may:
     * - Use different JSON formatting (minified, pretty-printed)
     * - Include extra whitespace or newlines
     * - Use nested arguments with complex values
     */
    private fun parseEmbeddedToolCalls(content: String): List<ToolCallRequest> {
        val toolCalls = mutableListOf<ToolCallRequest>()
        val processedToolIds = mutableSetOf<String>()

        // Pattern 1: Standard tool_call code block
        val toolCallBlockPattern = Regex(
            """```tool_call\s*\n?\s*(\{[\s\S]*?\})\s*\n?```""",
            RegexOption.MULTILINE
        )
        toolCallBlockPattern.findAll(content).forEach { match ->
            parseToolCallJson(match.groupValues[1], toolCalls, processedToolIds)
        }

        // Pattern 2: JSON code block with tool call
        val jsonBlockPattern = Regex(
            """```json\s*\n?\s*(\{[\s\S]*?"tool"[\s\S]*?\})\s*\n?```""",
            RegexOption.MULTILINE
        )
        jsonBlockPattern.findAll(content).forEach { match ->
            parseToolCallJson(match.groupValues[1], toolCalls, processedToolIds)
        }

        // Pattern 3: Plain code block with JSON
        val plainBlockPattern = Regex(
            """```\s*\n?\s*(\{[\s\S]*?"tool"[\s\S]*?\})\s*\n?```""",
            RegexOption.MULTILINE
        )
        plainBlockPattern.findAll(content).forEach { match ->
            parseToolCallJson(match.groupValues[1], toolCalls, processedToolIds)
        }

        // Pattern 4: Inline JSON with tool key (handles nested arguments)
        val inlinePattern = Regex(
            """\{\s*"tool"\s*:\s*"([^"]+)"\s*,\s*"arguments"\s*:\s*(\{[^{}]*(?:\{[^{}]*\}[^{}]*)*\})\s*\}"""
        )
        inlinePattern.findAll(content).forEach { match ->
            val toolName = match.groupValues[1]
            val arguments = match.groupValues[2]
            addToolCallIfNotExists(toolName, arguments, toolCalls, processedToolIds)
        }

        // Pattern 5: Name-first format (some models use this)
        val nameFirstPattern = Regex(
            """\{\s*"name"\s*:\s*"([^"]+)"\s*,\s*"parameters"\s*:\s*(\{[^{}]*(?:\{[^{}]*\}[^{}]*)*\})\s*\}"""
        )
        nameFirstPattern.findAll(content).forEach { match ->
            val toolName = match.groupValues[1]
            val arguments = match.groupValues[2]
            addToolCallIfNotExists(toolName, arguments, toolCalls, processedToolIds)
        }

        // Pattern 6: Function call format (tool_name(args))
        val functionPattern = Regex(
            """(get_\w+|calculate_\w+)\s*\(\s*([^)]*)\s*\)"""
        )
        functionPattern.findAll(content).forEach { match ->
            val toolName = match.groupValues[1]
            val argsStr = match.groupValues[2]
            val arguments = parseFunctionArguments(argsStr)
            addToolCallIfNotExists(toolName, arguments, toolCalls, processedToolIds)
        }

        return toolCalls
    }

    /**
     * Parse JSON string into tool call and add to list
     */
    private fun parseToolCallJson(
        jsonStr: String,
        toolCalls: MutableList<ToolCallRequest>,
        processedIds: MutableSet<String>
    ) {
        try {
            val cleanedJson = jsonStr.trim()
            val json = JSONObject(cleanedJson)

            // Try different field names for tool name
            val toolName = json.optString("tool")
                .ifEmpty { json.optString("name") }
                .ifEmpty { json.optString("function") }

            if (toolName.isEmpty()) return

            // Try different field names for arguments
            val arguments = json.optJSONObject("arguments")
                ?: json.optJSONObject("parameters")
                ?: json.optJSONObject("args")
                ?: JSONObject()

            addToolCallIfNotExists(toolName, arguments.toString(), toolCalls, processedIds)
        } catch (e: Exception) {
            // JSON parsing failed - try to extract partial information
            tryPartialParse(jsonStr, toolCalls, processedIds)
        }
    }

    /**
     * Attempt to parse partial or malformed JSON
     */
    private fun tryPartialParse(
        jsonStr: String,
        toolCalls: MutableList<ToolCallRequest>,
        processedIds: MutableSet<String>
    ) {
        // Try to extract tool name even from malformed JSON
        val toolNameMatch = Regex(""""tool"\s*:\s*"([^"]+)"""").find(jsonStr)
            ?: Regex(""""name"\s*:\s*"([^"]+)"""").find(jsonStr)

        if (toolNameMatch != null) {
            val toolName = toolNameMatch.groupValues[1]

            // Try to extract arguments
            val argsMatch = Regex(""""arguments"\s*:\s*(\{[^{}]*\})""").find(jsonStr)
                ?: Regex(""""parameters"\s*:\s*(\{[^{}]*\})""").find(jsonStr)

            val arguments = argsMatch?.groupValues?.get(1) ?: "{}"
            addToolCallIfNotExists(toolName, arguments, toolCalls, processedIds)
        }
    }

    /**
     * Add tool call if not already processed (deduplication)
     */
    private fun addToolCallIfNotExists(
        toolName: String,
        arguments: String,
        toolCalls: MutableList<ToolCallRequest>,
        processedIds: MutableSet<String>
    ) {
        // Create a unique key for deduplication based on tool name and arguments
        val toolKey = "$toolName:${arguments.hashCode()}"
        if (toolKey in processedIds) return

        processedIds.add(toolKey)
        toolCalls.add(ToolCallRequest(
            id = "tool_${UUID.randomUUID().toString().take(8)}",
            name = toolName,
            arguments = arguments
        ))
    }

    /**
     * Parse function-style arguments into JSON
     */
    private fun parseFunctionArguments(argsStr: String): String {
        if (argsStr.isBlank()) return "{}"

        val argsJson = JSONObject()

        // Split by comma, handling quoted strings
        val argPairs = argsStr.split(Regex(""",(?=(?:[^"]*"[^"]*")*[^"]*$)"""))

        for (pair in argPairs) {
            val parts = pair.split("=", limit = 2)
            if (parts.size == 2) {
                val key = parts[0].trim()
                var value = parts[1].trim()

                // Remove surrounding quotes if present
                if (value.startsWith("\"") && value.endsWith("\"")) {
                    value = value.substring(1, value.length - 1)
                }

                argsJson.put(key, value)
            }
        }

        return argsJson.toString()
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
