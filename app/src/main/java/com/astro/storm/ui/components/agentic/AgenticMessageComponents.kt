package com.astro.storm.ui.components.agentic

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
 * Agentic Message Layout - IDE-style streaming message display
 *
 * Inspired by modern AI coding agents like Cursor, Windsurf, and similar tools.
 * Displays tool executions, reasoning, and content in a clean, structured layout
 * with progressive disclosure and smooth animations.
 *
 * Key features:
 * - Collapsible tool execution section with status indicators
 * - Progressive content streaming with typing indicators
 * - Reasoning/thinking mode display with toggle
 * - Clean separation of concerns to prevent duplicate messages
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

    // State for collapsible sections
    var showReasoning by remember { mutableStateOf(false) }
    var showToolSteps by remember { mutableStateOf(true) }

    // Determine if we're actively processing
    val isProcessing = aiStatus !is AiStatus.Idle && aiStatus !is AiStatus.Complete

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Agent Header - Shows Stormy identity
        AgentIdentityHeader(isProcessing = isProcessing)

        // Tool Execution Panel - Only show if tools are being used
        if (toolSteps.isNotEmpty()) {
            ToolExecutionPanel(
                toolSteps = toolSteps,
                isExpanded = showToolSteps,
                onToggle = { showToolSteps = !showToolSteps }
            )
        }

        // Reasoning Panel - Collapsible thinking display
        if (cleanedReasoning.isNotBlank()) {
            ReasoningPanel(
                reasoning = cleanedReasoning,
                isExpanded = showReasoning,
                onToggle = { showReasoning = !showReasoning },
                isActive = aiStatus is AiStatus.Reasoning
            )
        }

        // Main Content Panel
        ContentPanel(
            content = cleanedContent,
            aiStatus = aiStatus,
            toolsUsed = toolSteps.filter { it.status == ToolStepStatus.COMPLETED }.map { it.toolName }
        )
    }
}

/**
 * Agent Identity Header - Shows Stormy avatar and status
 */
@Composable
private fun AgentIdentityHeader(
    isProcessing: Boolean
) {
    val colors = AppTheme.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Animated avatar
        Box(
            modifier = Modifier
                .size(32.dp)
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
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = colors.AccentGold.copy(alpha = alpha),
                    modifier = Modifier.size(18.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = colors.AccentGold,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Text(
            text = "Stormy",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = colors.TextPrimary
        )

        if (isProcessing) {
            ActiveIndicatorDot()
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
 * Tool Execution Panel - Shows tool calls with status indicators
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

    // Determine overall status color
    val statusColor = when {
        failedCount > 0 -> colors.ErrorColor
        executingCount > 0 -> colors.AccentTeal
        completedCount == totalCount -> colors.SuccessColor
        else -> colors.TextMuted
    }

    Surface(
        onClick = onToggle,
        color = colors.CardBackground,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header row with status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Tool icon with status color
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(statusColor.copy(alpha = 0.12f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Build,
                            contentDescription = null,
                            tint = statusColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Tool Execution",
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

                // Chevron
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
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
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
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
 * Build status summary text
 */
private fun buildStatusSummary(completed: Int, executing: Int, failed: Int, total: Int): String {
    return when {
        executing > 0 -> "$completed/$total completed, $executing running"
        failed > 0 -> "$completed/$total completed, $failed failed"
        completed == total -> "$completed tools completed"
        else -> "$completed/$total completed"
    }
}

/**
 * Individual tool step with detailed status
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
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Status icon (animated if executing)
        Box(
            modifier = Modifier.size(20.dp),
            contentAlignment = Alignment.Center
        ) {
            if (step.status == ToolStepStatus.EXECUTING) {
                val infiniteTransition = rememberInfiniteTransition(label = "tool_spin")
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "tool_rotation"
                )
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier
                        .size(18.dp)
                        .rotate(rotation)
                )
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = step.displayName,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = colors.TextPrimary
            )
            step.result?.let { result ->
                Text(
                    text = result,
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.TextMuted,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Duration badge if completed
        if (step.status == ToolStepStatus.COMPLETED && step.endTime != null) {
            val duration = step.endTime - step.startTime
            Surface(
                color = colors.ChipBackground,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = "${duration}ms",
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.TextSubtle,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

/**
 * Reasoning Panel - Shows AI thinking process
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
        color = colors.AccentPrimary.copy(alpha = 0.08f),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Header
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
                                text = "Thinking",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.AccentPrimary
                            )
                            if (isActive) {
                                ThinkingIndicator()
                            }
                        }
                        Text(
                            text = if (isExpanded) "Tap to collapse" else "Tap to expand",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.TextMuted
                        )
                    }
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = colors.AccentPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Expandable content
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
                        .padding(top = 12.dp)
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
 * Animated thinking indicator
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
 * Main Content Panel - Shows the response text
 */
@Composable
fun ContentPanel(
    content: String,
    aiStatus: AiStatus,
    toolsUsed: List<String>
) {
    val colors = AppTheme.current
    val isTyping = aiStatus is AiStatus.Typing ||
        (aiStatus !is AiStatus.Idle && aiStatus !is AiStatus.Complete && content.isNotEmpty())

    Surface(
        color = colors.CardBackground,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Content or status
            if (content.isNotEmpty()) {
                MarkdownText(
                    markdown = content,
                    modifier = Modifier.fillMaxWidth(),
                    textColor = colors.TextPrimary,
                    linkColor = colors.AccentPrimary,
                    textSize = 14f,
                    cleanContent = false
                )

                // Typing indicator at end
                if (isTyping) {
                    Spacer(modifier = Modifier.height(8.dp))
                    TypingDots()
                }
            } else if (aiStatus !is AiStatus.Idle && aiStatus !is AiStatus.Complete) {
                // Show status when no content yet
                StatusIndicatorInline(aiStatus = aiStatus)
            }

            // Tools used badge
            if (toolsUsed.isNotEmpty() && !isTyping && aiStatus is AiStatus.Complete) {
                Spacer(modifier = Modifier.height(10.dp))
                ToolsUsedBadge(tools = toolsUsed)
            }
        }
    }
}

/**
 * Inline status indicator
 */
@Composable
private fun StatusIndicatorInline(aiStatus: AiStatus) {
    val colors = AppTheme.current

    val (statusText, statusIcon) = when (aiStatus) {
        is AiStatus.Idle, is AiStatus.Complete -> return
        is AiStatus.Thinking -> "Analyzing your question..." to Icons.Outlined.Psychology
        is AiStatus.Reasoning -> "Reasoning through the details..." to Icons.Outlined.Lightbulb
        is AiStatus.CallingTool -> "Using ${formatToolNameDisplay(aiStatus.toolName)}..." to Icons.Outlined.Build
        is AiStatus.ExecutingTools -> "Gathering astrological data..." to Icons.Outlined.Build
        is AiStatus.Typing -> "Composing response..." to Icons.Outlined.Edit
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
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
            color = colors.TextMuted
        )
    }
}

/**
 * Animated typing dots
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
 * Badge showing tools that were used
 */
@Composable
private fun ToolsUsedBadge(tools: List<String>) {
    val colors = AppTheme.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Build,
            contentDescription = null,
            tint = colors.TextSubtle,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = tools.take(3).joinToString(", ") { formatToolNameDisplay(it) } +
                if (tools.size > 3) " +${tools.size - 3} more" else "",
            style = MaterialTheme.typography.labelSmall,
            color = colors.TextSubtle
        )
    }
}

/**
 * Format tool name for display
 */
private fun formatToolNameDisplay(toolName: String): String {
    return toolName
        .removePrefix("get_")
        .replace("_", " ")
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }
}
