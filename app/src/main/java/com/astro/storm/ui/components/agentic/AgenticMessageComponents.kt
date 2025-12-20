package com.astro.storm.ui.components.agentic

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.ui.components.ContentCleaner
import com.astro.storm.ui.components.MarkdownText
import com.astro.storm.ui.theme.AppTheme
import com.astro.storm.ui.viewmodel.AiStatus
import com.astro.storm.ui.viewmodel.StreamingMessageState
import com.astro.storm.ui.viewmodel.ToolExecutionStep
import com.astro.storm.ui.viewmodel.ToolStepStatus

/**
 * Modern Agentic Message Layout - Professional IDE-style AI Message Display
 *
 * This component renders AI messages in a modern, professional layout inspired by
 * agentic coding IDEs like Cursor, Windsurf, and Google's Antigravity. It is designed
 * specifically for Stormy, the Vedic Astrology AI Assistant.
 *
 * Key Design Principles:
 * - NO message bubbles for AI responses (clean, professional look)
 * - Structured content blocks that parse and organize message content
 * - Collapsible tool execution panel with real-time status indicators
 * - Reasoning/thinking display with progressive disclosure
 * - Full-width layout that respects the content hierarchy
 * - Smooth animations and visual feedback for agentic operations
 *
 * The layout uses a vertical stack of components:
 * 1. Agent Identity Header - Shows Stormy with active status
 * 2. Tool Execution Panel - Collapsible list of tool calls with status
 * 3. Reasoning Panel - Collapsible thinking/reasoning content
 * 4. Response Content - Main message content with Markdown rendering
 *
 * This design prevents message duplication by:
 * - Using a single source of truth (StreamingMessageState)
 * - Separating streaming UI from persisted message rendering
 * - Cleaning tool call artifacts before display
 */
@Composable
fun AgenticMessageCard(
    streamingState: StreamingMessageState?,
    streamingContent: String,
    streamingReasoning: String,
    aiStatus: AiStatus,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.current

    // Use streamingState if available, otherwise fall back to individual streams
    val content = streamingState?.content?.ifEmpty { streamingContent } ?: streamingContent
    val reasoning = streamingState?.reasoning?.ifEmpty { streamingReasoning } ?: streamingReasoning
    val toolSteps = streamingState?.toolSteps ?: emptyList()

    // Clean content from tool call artifacts
    val cleanedContent = remember(content) {
        if (content.isNotEmpty()) ContentCleaner.cleanForDisplay(content) else ""
    }
    val cleanedReasoning = remember(reasoning) {
        if (reasoning.isNotBlank()) ContentCleaner.cleanReasoning(reasoning) else ""
    }

    // State for collapsible sections - reasoning collapsed by default to focus on response
    var showReasoning by remember { mutableStateOf(false) }
    var showToolSteps by remember { mutableStateOf(true) }

    // Determine if we're actively processing
    val isProcessing = aiStatus !is AiStatus.Idle && aiStatus !is AiStatus.Complete

    // Main container - NO bubble, full width professional layout
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Agent Identity Header with status indicator
        AgentIdentityHeader(
            isProcessing = isProcessing,
            aiStatus = aiStatus
        )

        // Tool Execution Panel - Shows when tools are being used
        AnimatedVisibility(
            visible = toolSteps.isNotEmpty(),
            enter = expandVertically(animationSpec = tween(200)) + fadeIn(),
            exit = shrinkVertically(animationSpec = tween(200)) + fadeOut()
        ) {
            ToolExecutionPanel(
                toolSteps = toolSteps,
                isExpanded = showToolSteps,
                onToggle = { showToolSteps = !showToolSteps }
            )
        }

        // Reasoning Panel - Collapsible thinking/reasoning display
        AnimatedVisibility(
            visible = cleanedReasoning.isNotBlank(),
            enter = expandVertically(animationSpec = tween(200)) + fadeIn(),
            exit = shrinkVertically(animationSpec = tween(200)) + fadeOut()
        ) {
            ReasoningPanel(
                reasoning = cleanedReasoning,
                isExpanded = showReasoning,
                onToggle = { showReasoning = !showReasoning },
                isActive = aiStatus is AiStatus.Reasoning
            )
        }

        // Main Content Panel - Response content with status
        ContentPanel(
            content = cleanedContent,
            aiStatus = aiStatus,
            toolsUsed = toolSteps.filter { it.status == ToolStepStatus.COMPLETED }.map { it.toolName },
            isProcessing = isProcessing
        )
    }
}

/**
 * Agent Identity Header - Shows Stormy avatar and current status
 *
 * Displays the agent name with a pulsing avatar during active processing
 * and a status description based on the current AI operation.
 */
@Composable
private fun AgentIdentityHeader(
    isProcessing: Boolean,
    aiStatus: AiStatus
) {
    val colors = AppTheme.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Animated avatar with pulsing effect during processing
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            colors.AccentGold.copy(alpha = 0.4f),
                            colors.AccentPrimary.copy(alpha = 0.2f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isProcessing) {
                // Pulsing animation when active
                val infiniteTransition = rememberInfiniteTransition(label = "avatar_pulse")
                val alpha by infiniteTransition.animateFloat(
                    initialValue = 0.6f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "avatar_alpha"
                )
                val scale by infiniteTransition.animateFloat(
                    initialValue = 0.95f,
                    targetValue = 1.05f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(800, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "avatar_scale"
                )
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = colors.AccentGold.copy(alpha = alpha),
                    modifier = Modifier.size((20 * scale).dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = colors.AccentGold,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Stormy",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.TextPrimary
                )

                if (isProcessing) {
                    ActiveIndicatorDot()
                }
            }

            // Status description
            val statusText = when (aiStatus) {
                is AiStatus.Idle -> null
                is AiStatus.Complete -> null
                is AiStatus.Thinking -> "Analyzing your question..."
                is AiStatus.Reasoning -> "Reasoning through Vedic principles..."
                is AiStatus.CallingTool -> "Using ${formatToolNameDisplay(aiStatus.toolName)}..."
                is AiStatus.ExecutingTools -> "Gathering astrological data..."
                is AiStatus.Typing -> "Composing response..."
            }

            statusText?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.TextMuted,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

/**
 * Small pulsing dot to indicate active processing
 */
@Composable
private fun ActiveIndicatorDot() {
    val colors = AppTheme.current
    val infiniteTransition = rememberInfiniteTransition(label = "active_dot")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(600),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dot_scale"
    )

    Box(
        modifier = Modifier
            .size((6 * scale).dp)
            .clip(CircleShape)
            .background(colors.SuccessColor)
    )
}

/**
 * Tool Execution Panel - IDE-style tool call display
 *
 * Shows all tool calls with their execution status in a collapsible panel.
 * Displays a summary header with completion counts and expandable details.
 */
@Composable
fun ToolExecutionPanel(
    toolSteps: List<ToolExecutionStep>,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    val colors = AppTheme.current
    val completedCount = toolSteps.count { it.status == ToolStepStatus.COMPLETED }
    val failedCount = toolSteps.count { it.status == ToolStepStatus.FAILED }
    val executingCount = toolSteps.count { it.status == ToolStepStatus.EXECUTING }
    val totalCount = toolSteps.size

    // Determine overall status color based on tool execution states
    val statusColor = when {
        failedCount > 0 -> colors.ErrorColor
        executingCount > 0 -> colors.AccentTeal
        completedCount == totalCount -> colors.SuccessColor
        else -> colors.TextMuted
    }

    // Tool panel container with subtle background
    Surface(
        onClick = onToggle,
        color = statusColor.copy(alpha = 0.06f),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header row with status summary
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Tool icon with status-based background
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(statusColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        // Animated icon for executing state
                        if (executingCount > 0) {
                            val infiniteTransition = rememberInfiniteTransition(label = "tool_icon_spin")
                            val rotation by infiniteTransition.animateFloat(
                                initialValue = 0f,
                                targetValue = 360f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(1500, easing = LinearEasing),
                                    repeatMode = RepeatMode.Restart
                                ),
                                label = "tool_icon_rotation"
                            )
                            Icon(
                                imageVector = Icons.Outlined.Sync,
                                contentDescription = null,
                                tint = statusColor,
                                modifier = Modifier
                                    .size(16.dp)
                                    .rotate(rotation)
                            )
                        } else {
                            Icon(
                                imageVector = if (failedCount > 0) Icons.Outlined.Warning else Icons.Outlined.Build,
                                contentDescription = null,
                                tint = statusColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Column {
                        Text(
                            text = "Astrological Tools",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.TextPrimary
                        )
                        Text(
                            text = buildStatusSummary(completedCount, executingCount, failedCount, totalCount),
                            style = MaterialTheme.typography.labelSmall,
                            color = statusColor
                        )
                    }
                }

                // Expand/collapse chevron
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = colors.TextMuted,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Expandable tool list
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(200)) + fadeIn(animationSpec = tween(200)),
                exit = shrinkVertically(animationSpec = tween(200)) + fadeOut(animationSpec = tween(200))
            ) {
                Column(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    toolSteps.forEach { step ->
                        ToolStepRow(step = step)
                    }
                }
            }
        }
    }
}

/**
 * Build status summary text based on tool execution states
 */
private fun buildStatusSummary(completed: Int, executing: Int, failed: Int, total: Int): String {
    return when {
        executing > 0 && completed > 0 -> "$completed/$total completed, $executing running"
        executing > 0 -> "$executing tool${if (executing > 1) "s" else ""} running..."
        failed > 0 && completed > 0 -> "$completed completed, $failed failed"
        failed > 0 -> "$failed tool${if (failed > 1) "s" else ""} failed"
        completed == total && total > 0 -> "$completed tool${if (completed > 1) "s" else ""} completed"
        else -> "$completed/$total completed"
    }
}

/**
 * Individual tool step row with detailed status
 */
@Composable
private fun ToolStepRow(step: ToolExecutionStep) {
    val colors = AppTheme.current

    val (icon, iconColor, bgColor) = when (step.status) {
        ToolStepStatus.PENDING -> Triple(
            Icons.Outlined.Schedule,
            colors.TextMuted,
            colors.ChipBackground
        )
        ToolStepStatus.EXECUTING -> Triple(
            Icons.Outlined.Sync,
            colors.AccentTeal,
            colors.AccentTeal.copy(alpha = 0.12f)
        )
        ToolStepStatus.COMPLETED -> Triple(
            Icons.Filled.CheckCircle,
            colors.SuccessColor,
            colors.SuccessColor.copy(alpha = 0.12f)
        )
        ToolStepStatus.FAILED -> Triple(
            Icons.Outlined.ErrorOutline,
            colors.ErrorColor,
            colors.ErrorColor.copy(alpha = 0.12f)
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Status icon with animation for executing state
        Box(
            modifier = Modifier.size(18.dp),
            contentAlignment = Alignment.Center
        ) {
            if (step.status == ToolStepStatus.EXECUTING) {
                val infiniteTransition = rememberInfiniteTransition(label = "tool_spin_${step.toolName}")
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "tool_rotation_${step.toolName}"
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier
                        .size(16.dp)
                        .rotate(rotation)
                )
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Tool name and result
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = step.displayName,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = colors.TextPrimary
            )
            step.result?.let { result ->
                if (result.isNotBlank()) {
                    Text(
                        text = result,
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.TextMuted,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        // Duration badge for completed tools
        if (step.status == ToolStepStatus.COMPLETED && step.endTime != null) {
            val duration = step.endTime - step.startTime
            Surface(
                color = colors.ChipBackground,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = formatDuration(duration),
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.TextSubtle,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

/**
 * Format duration for display
 */
private fun formatDuration(durationMs: Long): String {
    return when {
        durationMs < 1000 -> "${durationMs}ms"
        durationMs < 60000 -> "${durationMs / 1000}.${(durationMs % 1000) / 100}s"
        else -> "${durationMs / 60000}m ${(durationMs % 60000) / 1000}s"
    }
}

/**
 * Reasoning Panel - Collapsible thinking/reasoning display
 *
 * Shows the AI's reasoning process in a collapsible section.
 * Displays an active indicator when reasoning is in progress.
 */
@Composable
fun ReasoningPanel(
    reasoning: String,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    isActive: Boolean
) {
    val colors = AppTheme.current

    Surface(
        onClick = onToggle,
        color = colors.AccentPrimary.copy(alpha = 0.06f),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header with thinking indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Brain icon
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(colors.AccentPrimary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Psychology,
                            contentDescription = null,
                            tint = colors.AccentPrimary,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "Reasoning",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.AccentPrimary
                            )
                            if (isActive) {
                                ThinkingIndicator()
                            }
                        }
                        Text(
                            text = if (isExpanded) "Vedic analysis process" else "Tap to view reasoning",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.TextMuted
                        )
                    }
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = colors.AccentPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Expandable reasoning content
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(animationSpec = tween(200)) + fadeIn(animationSpec = tween(200)),
                exit = shrinkVertically(animationSpec = tween(200)) + fadeOut(animationSpec = tween(200))
            ) {
                Surface(
                    color = colors.ChipBackground,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Text(
                        text = reasoning,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.TextSecondary,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}

/**
 * Animated thinking indicator - three pulsing dots
 */
@Composable
private fun ThinkingIndicator() {
    val colors = AppTheme.current

    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        repeat(3) { index ->
            val infiniteTransition = rememberInfiniteTransition(label = "think_dot_$index")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(600),
                    repeatMode = RepeatMode.Reverse,
                    initialStartOffset = StartOffset(index * 150)
                ),
                label = "think_alpha_$index"
            )
            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(colors.AccentPrimary.copy(alpha = alpha))
            )
        }
    }
}

/**
 * Main Content Panel - Response content with status indicators
 *
 * Displays the main response content with Markdown rendering.
 * Shows appropriate status indicators during processing phases.
 */
@Composable
fun ContentPanel(
    content: String,
    aiStatus: AiStatus,
    toolsUsed: List<String>,
    isProcessing: Boolean = false
) {
    val colors = AppTheme.current
    val isTyping = aiStatus is AiStatus.Typing ||
        (aiStatus !is AiStatus.Idle && aiStatus !is AiStatus.Complete && content.isNotEmpty())

    // Content container - clean, no bubble for AI messages
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Main content area
        if (content.isNotEmpty()) {
            // Render content with Markdown support
            MarkdownText(
                markdown = content,
                modifier = Modifier.fillMaxWidth(),
                textColor = colors.TextPrimary,
                linkColor = colors.AccentPrimary,
                textSize = 15f,
                cleanContent = false // Already cleaned above
            )

            // Typing indicator at end when actively typing
            if (isTyping) {
                Spacer(modifier = Modifier.height(4.dp))
                TypingDots()
            }
        } else if (aiStatus !is AiStatus.Idle && aiStatus !is AiStatus.Complete) {
            // Show status indicator when no content yet
            StatusIndicatorInline(aiStatus = aiStatus)
        }

        // Tools used badge - show after completion
        if (toolsUsed.isNotEmpty() && !isProcessing && aiStatus is AiStatus.Complete) {
            Spacer(modifier = Modifier.height(4.dp))
            ToolsUsedBadge(tools = toolsUsed)
        }
    }
}

/**
 * Inline status indicator for pre-content phases
 */
@Composable
private fun StatusIndicatorInline(aiStatus: AiStatus) {
    val colors = AppTheme.current

    val (statusText, statusIcon) = when (aiStatus) {
        is AiStatus.Idle, is AiStatus.Complete -> return
        is AiStatus.Thinking -> "Analyzing your question..." to Icons.Outlined.Psychology
        is AiStatus.Reasoning -> "Applying Vedic principles..." to Icons.Outlined.Lightbulb
        is AiStatus.CallingTool -> "Using ${formatToolNameDisplay(aiStatus.toolName)}..." to Icons.Outlined.Build
        is AiStatus.ExecutingTools -> "Gathering astrological data..." to Icons.Outlined.Build
        is AiStatus.Typing -> "Composing response..." to Icons.Outlined.Edit
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(16.dp),
            strokeWidth = 2.dp,
            color = colors.AccentPrimary
        )
        Icon(
            imageVector = statusIcon,
            contentDescription = null,
            tint = colors.TextMuted,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = statusText,
            style = MaterialTheme.typography.bodySmall,
            color = colors.TextMuted,
            fontStyle = FontStyle.Italic
        )
    }
}

/**
 * Animated typing dots indicator
 */
@Composable
fun TypingDots() {
    val colors = AppTheme.current

    Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
        repeat(3) { index ->
            val infiniteTransition = rememberInfiniteTransition(label = "type_dot_$index")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1000
                        0.3f at 0
                        1f at 300
                        0.3f at 600
                    },
                    repeatMode = RepeatMode.Restart,
                    initialStartOffset = StartOffset(index * 150)
                ),
                label = "type_alpha_$index"
            )
            Box(
                modifier = Modifier
                    .size(5.dp)
                    .clip(CircleShape)
                    .background(colors.TextMuted.copy(alpha = alpha))
            )
        }
    }
}

/**
 * Badge showing tools that were used in the response
 */
@Composable
private fun ToolsUsedBadge(tools: List<String>) {
    val colors = AppTheme.current

    Surface(
        color = colors.ChipBackground,
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Build,
                contentDescription = null,
                tint = colors.TextSubtle,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = "Used: " + tools.take(3).joinToString(", ") { formatToolNameDisplay(it) } +
                    if (tools.size > 3) " +${tools.size - 3} more" else "",
                style = MaterialTheme.typography.labelSmall,
                color = colors.TextSubtle
            )
        }
    }
}

/**
 * Format tool name for user-friendly display
 *
 * Converts snake_case tool names to Title Case display names.
 * Examples:
 * - get_planet_positions -> Planet Positions
 * - get_current_dasha -> Current Dasha
 * - calculate_muhurta -> Calculate Muhurta
 */
private fun formatToolNameDisplay(toolName: String): String {
    return toolName
        .removePrefix("get_")
        .removePrefix("calculate_")
        .replace("_", " ")
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
}

/**
 * Completed Message Card - For rendering finalized AI messages (not streaming)
 *
 * This component is used to display AI messages that have been saved to the database.
 * It uses a similar layout to AgenticMessageCard but without streaming-specific features.
 */
@Composable
fun CompletedAiMessageCard(
    content: String,
    reasoning: String?,
    toolsUsed: List<String>?,
    errorMessage: String?,
    onRegenerate: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.current

    // Clean content
    val cleanedContent = remember(content) {
        ContentCleaner.cleanForDisplay(content)
    }
    val cleanedReasoning = remember(reasoning) {
        reasoning?.let { ContentCleaner.cleanReasoning(it) }
    }

    // State for collapsible reasoning
    var showReasoning by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Agent header (static, not processing)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                colors.AccentGold.copy(alpha = 0.3f),
                                colors.AccentPrimary.copy(alpha = 0.15f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = colors.AccentGold,
                    modifier = Modifier.size(18.dp)
                )
            }

            Text(
                text = "Stormy",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = colors.TextPrimary
            )
        }

        // Reasoning panel if present
        if (!cleanedReasoning.isNullOrBlank()) {
            Surface(
                onClick = { showReasoning = !showReasoning },
                color = colors.AccentPrimary.copy(alpha = 0.06f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Psychology,
                                contentDescription = null,
                                tint = colors.AccentPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Reasoning",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium,
                                color = colors.AccentPrimary
                            )
                        }
                        Icon(
                            imageVector = if (showReasoning) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = colors.AccentPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    AnimatedVisibility(visible = showReasoning) {
                        Text(
                            text = cleanedReasoning,
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.TextSecondary,
                            lineHeight = 20.sp,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }

        // Error state
        if (errorMessage != null) {
            Surface(
                color = colors.ErrorColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ErrorOutline,
                        contentDescription = null,
                        tint = colors.ErrorColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.ErrorColor
                    )
                }
            }
        } else if (cleanedContent.isNotEmpty()) {
            // Main content with Markdown
            MarkdownText(
                markdown = cleanedContent,
                modifier = Modifier.fillMaxWidth(),
                textColor = colors.TextPrimary,
                linkColor = colors.AccentPrimary,
                textSize = 15f,
                cleanContent = false
            )
        }

        // Tools used badge
        if (!toolsUsed.isNullOrEmpty()) {
            ToolsUsedBadge(tools = toolsUsed)
        }

        // Regenerate button
        if (onRegenerate != null) {
            TextButton(
                onClick = onRegenerate,
                modifier = Modifier.align(Alignment.Start)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Regenerate",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
