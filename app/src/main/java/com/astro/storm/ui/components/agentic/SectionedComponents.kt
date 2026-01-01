package com.astro.storm.ui.components.agentic

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.ui.components.MarkdownText
import com.astro.storm.ui.theme.AppTheme
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.stringResource

/**
 * Sectioned UI Components for Dynamic Agentic Message Layout
 *
 * These components render individual sections of an AI response dynamically.
 * Inspired by professional agentic IDEs (Cursor, Windsurf, Google Antigravity),
 * adapted for Stormy - the Vedic Astrology AI Assistant.
 *
 * Key Features:
 * - Each section type has its own dedicated component
 * - Sections animate in as they stream
 * - Interactive sections (AskUser, Todo) support user input
 * - Clean, professional design without bubbles
 * - Full theme support with AppTheme colors
 */

// ============================================
// TASK BOUNDARY SECTION
// ============================================

/**
 * Task Boundary Section - Visual marker for task start/end
 *
 * Shows a thin line with task name to indicate workflow boundaries.
 * Makes the agentic workflow visible and understandable.
 */
@Composable
fun TaskBoundarySection(
    section: AgentSection.TaskBoundary,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Left line
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(
                        if (section.isStart)
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    colors.AccentPrimary.copy(alpha = 0.3f)
                                )
                            )
                        else
                            Brush.horizontalGradient(
                                colors = listOf(
                                    colors.SuccessColor.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            )
                    )
            )

            // Task badge
            Surface(
                color = if (section.isStart)
                    colors.AccentPrimary.copy(alpha = 0.1f)
                else
                    colors.SuccessColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = if (section.isStart) Icons.Outlined.PlayArrow else Icons.Outlined.CheckCircle,
                        contentDescription = null,
                        tint = if (section.isStart) colors.AccentPrimary else colors.SuccessColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = if (section.isStart)
                            stringResource(StringKeyDosha.SECTION_TASK_STARTED, section.taskName)
                        else
                            stringResource(StringKeyDosha.SECTION_TASK_COMPLETED, section.taskName),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = if (section.isStart) colors.AccentPrimary else colors.SuccessColor
                    )
                }
            }

            // Right line
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(1.dp)
                    .background(
                        if (section.isStart)
                            Brush.horizontalGradient(
                                colors = listOf(
                                    colors.AccentPrimary.copy(alpha = 0.3f),
                                    Color.Transparent
                                )
                            )
                        else
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    colors.SuccessColor.copy(alpha = 0.3f)
                                )
                            )
                    )
            )
        }

        // Summary text for completed tasks
        if (!section.isStart && !section.summary.isNullOrBlank()) {
            Text(
                text = section.summary,
                style = MaterialTheme.typography.bodySmall,
                color = colors.TextMuted,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

// ============================================
// REASONING SECTION
// ============================================

/**
 * Reasoning Section - Displays AI thinking process
 *
 * Shows the AI's internal reasoning with duration tracking.
 * Collapsible by default to focus on the response.
 */
@Composable
fun ReasoningSection(
    section: AgentSection.Reasoning,
    onToggleExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.current
    val isActive = !section.isComplete

    Surface(
        onClick = onToggleExpand,
        color = colors.AccentPrimary.copy(alpha = 0.06f),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header with duration
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
                                text = stringResource(StringKeyDosha.SECTION_REASONING),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = colors.AccentPrimary
                            )
                            if (isActive) {
                                ThinkingDotsIndicator()
                            }
                        }

                        // Duration or status
                        Text(
                            text = if (section.isComplete && section.durationMs > 0)
                                stringResource(StringKeyDosha.SECTION_REASONED_FOR, section.durationDisplay)
                            else if (isActive)
                                stringResource(StringKeyDosha.SECTION_ANALYZING)
                            else
                                stringResource(StringKeyDosha.SECTION_TAP_TO_VIEW),
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.TextMuted
                        )
                    }
                }

                Icon(
                    imageVector = if (section.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (section.isExpanded)
                        stringResource(StringKeyDosha.SECTION_COLLAPSE)
                    else
                        stringResource(StringKeyDosha.SECTION_EXPAND),
                    tint = colors.AccentPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Expandable reasoning content
            AnimatedVisibility(
                visible = section.isExpanded,
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
                        text = section.content,
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
 * Animated thinking dots indicator
 */
@Composable
private fun ThinkingDotsIndicator() {
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

// ============================================
// TOOL GROUP SECTION
// ============================================

/**
 * Tool Group Section - Shows tool execution status
 *
 * With chronological sections, each ToolGroup represents a batch of tools
 * that were called together. When tools are interleaved with reasoning,
 * multiple separate ToolGroup sections will be created.
 *
 * This matches the Codex-style layout where each tool operation appears
 * as a distinct, chronologically-ordered section.
 *
 * IMPORTANT: Each tool has its own independent expanded state to allow
 * users to expand/collapse tools individually.
 */
@Composable
fun ToolGroupSection(
    section: AgentSection.ToolGroup,
    onToggleExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Each tool maintains its own independent expanded state
    // This allows users to expand/collapse individual tools
    val expandedStates = remember(section.id) {
        mutableStateMapOf<String, Boolean>().apply {
            // Initialize all tools with the group's default expanded state
            section.tools.forEach { tool ->
                this[tool.id] = section.isExpanded
            }
        }
    }

    // Render each tool as its own visual card for Codex-style appearance
    // Using key() for efficient recomposition when tools list changes
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        section.tools.forEach { tool ->
            key(tool.id) {
                // Ensure new tools get added to the state map
                if (!expandedStates.containsKey(tool.id)) {
                    expandedStates[tool.id] = section.isExpanded
                }

                IndividualToolCard(
                    tool = tool,
                    isExpanded = expandedStates[tool.id] ?: false,
                    onToggleExpand = {
                        // Toggle only this specific tool's expanded state
                        expandedStates[tool.id] = !(expandedStates[tool.id] ?: false)
                    }
                )
            }
        }
    }
}

/**
 * Individual Tool Card - Codex-style tool display
 *
 * Each tool is displayed as its own collapsible card with:
 * - Tool name as the header (e.g., "Calculate Planetary Positions")
 * - Status indicator (running spinner, check mark, error)
 * - Expandable result/error details
 */
@Composable
private fun IndividualToolCard(
    tool: ToolExecution,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    val colors = AppTheme.current

    val (statusIcon, statusColor) = when (tool.status) {
        ToolExecutionStatus.PENDING -> Icons.Outlined.Schedule to colors.TextMuted
        ToolExecutionStatus.EXECUTING -> Icons.Outlined.Sync to colors.AccentTeal
        ToolExecutionStatus.COMPLETED -> Icons.Filled.CheckCircle to colors.SuccessColor
        ToolExecutionStatus.FAILED -> Icons.Outlined.ErrorOutline to colors.ErrorColor
    }

    Surface(
        onClick = onToggleExpand,
        color = statusColor.copy(alpha = 0.06f),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header row with tool name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    // Status icon
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(statusColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (tool.status == ToolExecutionStatus.EXECUTING) {
                            val infiniteTransition = rememberInfiniteTransition(label = "tool_spin_${tool.id}")
                            val rotation by infiniteTransition.animateFloat(
                                initialValue = 0f,
                                targetValue = 360f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(1000, easing = LinearEasing),
                                    repeatMode = RepeatMode.Restart
                                ),
                                label = "tool_rotation_${tool.id}"
                            )
                            Icon(
                                imageVector = statusIcon,
                                contentDescription = null,
                                tint = statusColor,
                                modifier = Modifier
                                    .size(16.dp)
                                    .rotate(rotation)
                            )
                        } else {
                            Icon(
                                imageVector = statusIcon,
                                contentDescription = null,
                                tint = statusColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        // Tool display name as the primary title
                        Text(
                            text = tool.displayName,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.TextPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        // Status text
                        Text(
                            text = when (tool.status) {
                                ToolExecutionStatus.PENDING -> stringResource(StringKeyDosha.TOOL_STATUS_PENDING)
                                ToolExecutionStatus.EXECUTING -> stringResource(StringKeyDosha.TOOL_STATUS_RUNNING)
                                ToolExecutionStatus.COMPLETED -> stringResource(StringKeyDosha.TOOL_STATUS_COMPLETED_IN, tool.durationDisplay)
                                ToolExecutionStatus.FAILED -> stringResource(StringKeyDosha.TOOL_STATUS_FAILED)
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = statusColor
                        )
                    }
                }

                // Expand/collapse indicator (only show if there's content to expand)
                if (tool.result != null || tool.error != null) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded)
                            stringResource(StringKeyDosha.SECTION_COLLAPSE)
                        else
                            stringResource(StringKeyDosha.SECTION_EXPAND),
                        tint = colors.TextMuted,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Expandable result/error content
            AnimatedVisibility(
                visible = isExpanded && (tool.result != null || tool.error != null),
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
                    Column(modifier = Modifier.padding(12.dp)) {
                        tool.result?.let { result ->
                            if (result.isNotBlank()) {
                                Text(
                                    text = result,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = colors.TextSecondary,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                        tool.error?.let { error ->
                            Text(
                                text = error,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.ErrorColor,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Legacy Tool Group Section - Grouped display
 *
 * Use this for a more compact grouped display when multiple tools
 * should appear as a single "Astrological Tools" section.
 */
@Composable
fun ToolGroupSectionGrouped(
    section: AgentSection.ToolGroup,
    onToggleExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.current

    // Determine overall status color
    val statusColor = when {
        section.failedCount > 0 -> colors.ErrorColor
        section.runningCount > 0 -> colors.AccentTeal
        section.completedCount == section.totalCount && section.totalCount > 0 -> colors.SuccessColor
        else -> colors.TextMuted
    }

    Surface(
        onClick = onToggleExpand,
        color = statusColor.copy(alpha = 0.06f),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header row
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
                        if (section.runningCount > 0) {
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
                                imageVector = if (section.failedCount > 0) Icons.Outlined.Warning else Icons.Outlined.Build,
                                contentDescription = null,
                                tint = statusColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Column {
                        Text(
                            text = stringResource(StringKeyDosha.SECTION_ASTROLOGICAL_TOOLS),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.TextPrimary
                        )
                        Text(
                            text = section.statusSummary,
                            style = MaterialTheme.typography.labelSmall,
                            color = statusColor
                        )
                    }
                }

                Icon(
                    imageVector = if (section.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (section.isExpanded)
                        stringResource(StringKeyDosha.SECTION_COLLAPSE)
                    else
                        stringResource(StringKeyDosha.SECTION_EXPAND),
                    tint = colors.TextMuted,
                    modifier = Modifier.size(22.dp)
                )
            }

            // Expandable tool list
            AnimatedVisibility(
                visible = section.isExpanded,
                enter = expandVertically(animationSpec = tween(200)) + fadeIn(animationSpec = tween(200)),
                exit = shrinkVertically(animationSpec = tween(200)) + fadeOut(animationSpec = tween(200))
            ) {
                Column(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    section.tools.forEach { tool ->
                        ToolExecutionRow(tool = tool)
                    }
                }
            }
        }
    }
}

/**
 * Individual tool execution row
 */
@Composable
private fun ToolExecutionRow(tool: ToolExecution) {
    val colors = AppTheme.current

    val (icon, iconColor, bgColor) = when (tool.status) {
        ToolExecutionStatus.PENDING -> Triple(
            Icons.Outlined.Schedule,
            colors.TextMuted,
            colors.ChipBackground
        )
        ToolExecutionStatus.EXECUTING -> Triple(
            Icons.Outlined.Sync,
            colors.AccentTeal,
            colors.AccentTeal.copy(alpha = 0.12f)
        )
        ToolExecutionStatus.COMPLETED -> Triple(
            Icons.Filled.CheckCircle,
            colors.SuccessColor,
            colors.SuccessColor.copy(alpha = 0.12f)
        )
        ToolExecutionStatus.FAILED -> Triple(
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
        // Status icon
        Box(
            modifier = Modifier.size(18.dp),
            contentAlignment = Alignment.Center
        ) {
            if (tool.status == ToolExecutionStatus.EXECUTING) {
                val infiniteTransition = rememberInfiniteTransition(label = "tool_spin_${tool.id}")
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "tool_rotation_${tool.id}"
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
                text = tool.displayName,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = colors.TextPrimary
            )
            tool.result?.let { result ->
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
            tool.error?.let { error ->
                Text(
                    text = error,
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.ErrorColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Duration badge
        if (tool.status == ToolExecutionStatus.COMPLETED && tool.endTime != null) {
            Surface(
                color = colors.ChipBackground,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = tool.durationDisplay,
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.TextSubtle,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

// ============================================
// CONTENT SECTION
// ============================================

/**
 * Content Section - Main response text
 *
 * Displays the AI's response content with Markdown rendering
 * and an optional typing indicator.
 */
@Composable
fun ContentSection(
    section: AgentSection.Content,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (section.text.isNotEmpty()) {
            MarkdownText(
                markdown = section.text,
                modifier = Modifier.fillMaxWidth(),
                textColor = colors.TextPrimary,
                linkColor = colors.AccentPrimary,
                textSize = 15f,
                cleanContent = false
            )

            // Typing indicator
            if (section.isTyping && !section.isComplete) {
                Spacer(modifier = Modifier.height(4.dp))
                TypingIndicator()
            }
        } else if (section.isTyping && !section.isComplete) {
            // Show typing indicator even without content
            TypingIndicator()
        }
    }
}

/**
 * Animated typing indicator
 */
@Composable
private fun TypingIndicator() {
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

// ============================================
// ASK USER SECTION
// ============================================

/**
 * Ask User Section - Interactive clarifying question
 *
 * Displays a question from the AI with optional choices
 * and custom input support. Handles user responses.
 */
@Composable
fun AskUserSection(
    section: AgentSection.AskUser,
    onSelectOption: (AskUserOption) -> Unit,
    onSubmitCustomResponse: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.current
    var customInput by remember { mutableStateOf("") }

    Surface(
        color = colors.AccentGold.copy(alpha = 0.08f),
        shape = RoundedCornerShape(12.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header with question mark icon
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(colors.AccentGold.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.HelpOutline,
                        contentDescription = null,
                        tint = colors.AccentGold,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(StringKeyDosha.STORMY_NEEDS_INPUT),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = colors.AccentGold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = section.question,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.TextPrimary,
                        lineHeight = 22.sp
                    )
                }
            }

            // Options (if any)
            if (section.hasOptions && !section.isAnswered) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    section.options.forEach { option ->
                        AskUserOptionButton(
                            option = option,
                            isSelected = section.selectedOptionId == option.id,
                            onClick = { onSelectOption(option) }
                        )
                    }
                }
            }

            // Custom input field
            if (section.allowCustomInput && !section.isAnswered) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    BasicTextField(
                        value = customInput,
                        onValueChange = { customInput = it },
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(colors.ChipBackground)
                            .padding(12.dp),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = colors.TextPrimary
                        ),
                        cursorBrush = SolidColor(colors.AccentPrimary),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (customInput.isNotBlank()) {
                                    onSubmitCustomResponse(customInput.trim())
                                }
                            }
                        ),
                        decorationBox = { innerTextField ->
                            Box {
                                if (customInput.isEmpty()) {
                                    Text(
                                        text = stringResource(StringKeyDosha.PLACEHOLDER_TYPE_RESPONSE),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = colors.TextMuted
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )

                    IconButton(
                        onClick = {
                            if (customInput.isNotBlank()) {
                                onSubmitCustomResponse(customInput.trim())
                            }
                        },
                        enabled = customInput.isNotBlank()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = stringResource(StringKeyDosha.CHAT_SEND),
                            tint = if (customInput.isNotBlank())
                                colors.AccentPrimary
                            else
                                colors.TextMuted
                        )
                    }
                }
            }

            // Show selected answer
            if (section.isAnswered) {
                Surface(
                    color = colors.SuccessColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = colors.SuccessColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = section.customResponse
                                ?: section.options.find { it.id == section.selectedOptionId }?.label
                                ?: stringResource(StringKeyDosha.RESPONSE_SUBMITTED),
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.TextPrimary
                        )
                    }
                }
            }
        }
    }
}

/**
 * Individual option button for AskUser
 */
@Composable
private fun AskUserOptionButton(
    option: AskUserOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val colors = AppTheme.current

    Surface(
        onClick = onClick,
        color = if (isSelected)
            colors.AccentPrimary.copy(alpha = 0.15f)
        else
            colors.ChipBackground,
        shape = RoundedCornerShape(8.dp),
        border = if (isSelected)
            androidx.compose.foundation.BorderStroke(1.dp, colors.AccentPrimary)
        else
            null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Radio-style indicator
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        color = if (isSelected) colors.AccentPrimary else colors.TextMuted,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(colors.AccentPrimary)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = option.label,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                    color = colors.TextPrimary
                )
                option.description?.let { desc ->
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.TextMuted
                    )
                }
            }
        }
    }
}

// ============================================
// TODO LIST SECTION
// ============================================

/**
 * Todo List Section - Task progress tracker
 *
 * Shows a list of tasks with completion status.
 * Items can be marked as completed, in-progress, or pending.
 */
@Composable
fun TodoListSection(
    section: AgentSection.TodoList,
    onToggleExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.current

    // Progress color based on completion
    val progressColor = when {
        section.completedCount == section.totalCount && section.totalCount > 0 -> colors.SuccessColor
        section.completedCount > 0 -> colors.AccentTeal
        else -> colors.TextMuted
    }

    Surface(
        onClick = onToggleExpand,
        color = progressColor.copy(alpha = 0.06f),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
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
                    // Checklist icon
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(progressColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Checklist,
                            contentDescription = null,
                            tint = progressColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Column {
                        Text(
                            text = section.title,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.TextPrimary
                        )
                        Text(
                            text = section.progressText,
                            style = MaterialTheme.typography.labelSmall,
                            color = progressColor
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Progress indicator
                    LinearProgressIndicator(
                        progress = { section.progress },
                        modifier = Modifier
                            .width(48.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = progressColor,
                        trackColor = colors.ChipBackground
                    )

                    Icon(
                        imageVector = if (section.isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (section.isExpanded)
                            stringResource(StringKeyDosha.SECTION_COLLAPSE)
                        else
                            stringResource(StringKeyDosha.SECTION_EXPAND),
                        tint = colors.TextMuted,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Expandable item list
            AnimatedVisibility(
                visible = section.isExpanded,
                enter = expandVertically(animationSpec = tween(200)) + fadeIn(animationSpec = tween(200)),
                exit = shrinkVertically(animationSpec = tween(200)) + fadeOut(animationSpec = tween(200))
            ) {
                Column(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    section.items.forEach { item ->
                        TodoItemRow(item = item)
                    }
                }
            }
        }
    }
}

/**
 * Individual todo item row
 */
@Composable
private fun TodoItemRow(item: TodoItem) {
    val colors = AppTheme.current

    val (icon, iconColor, textColor) = when {
        item.isCompleted -> Triple(
            Icons.Filled.CheckCircle,
            colors.SuccessColor,
            colors.TextMuted
        )
        item.isInProgress -> Triple(
            Icons.Outlined.HourglassTop,
            colors.AccentTeal,
            colors.TextPrimary
        )
        else -> Triple(
            Icons.Outlined.Circle,
            colors.TextMuted,
            colors.TextPrimary
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Status icon with animation for in-progress
        Box(
            modifier = Modifier.size(18.dp),
            contentAlignment = Alignment.Center
        ) {
            if (item.isInProgress) {
                val infiniteTransition = rememberInfiniteTransition(label = "todo_progress_${item.id}")
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "todo_rotation_${item.id}"
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

        Text(
            text = item.text,
            style = MaterialTheme.typography.bodySmall,
            color = textColor,
            modifier = Modifier.weight(1f)
        )

        // In-progress badge
        if (item.isInProgress) {
            Surface(
                color = colors.AccentTeal.copy(alpha = 0.15f),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = stringResource(StringKeyDosha.TODO_IN_PROGRESS),
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.AccentTeal,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }
    }
}

// ============================================
// PROFILE OPERATION SECTION
// ============================================

/**
 * Profile Operation Section - Shows profile create/update status
 *
 * Displays the status of profile operations with success/error states.
 */
@Composable
fun ProfileOperationSection(
    section: AgentSection.ProfileOperation,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.current

    val (icon, statusColor) = when (section.status) {
        ProfileOperationStatus.PENDING -> Icons.Outlined.Schedule to colors.TextMuted
        ProfileOperationStatus.IN_PROGRESS -> Icons.Outlined.Sync to colors.AccentTeal
        ProfileOperationStatus.SUCCESS -> Icons.Filled.CheckCircle to colors.SuccessColor
        ProfileOperationStatus.FAILED -> Icons.Outlined.ErrorOutline to colors.ErrorColor
    }

    val statusText = when (section.status) {
        ProfileOperationStatus.PENDING -> stringResource(StringKeyDosha.PROFILE_STATUS_PENDING)
        ProfileOperationStatus.IN_PROGRESS -> stringResource(StringKeyDosha.PROFILE_STATUS_IN_PROGRESS)
        ProfileOperationStatus.SUCCESS -> stringResource(StringKeyDosha.PROFILE_STATUS_SUCCESS)
        ProfileOperationStatus.FAILED -> stringResource(StringKeyDosha.PROFILE_STATUS_FAILED)
    }

    val operationText = when (section.operation) {
        ProfileOperationType.CREATE -> stringResource(StringKeyDosha.PROFILE_OP_CREATING)
        ProfileOperationType.UPDATE -> stringResource(StringKeyDosha.PROFILE_OP_UPDATING)
        ProfileOperationType.DELETE -> stringResource(StringKeyDosha.PROFILE_OP_DELETING)
        ProfileOperationType.VIEW -> stringResource(StringKeyDosha.PROFILE_OP_VIEWING)
    }

    Surface(
        color = statusColor.copy(alpha = 0.08f),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Status icon
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(statusColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                if (section.status == ProfileOperationStatus.IN_PROGRESS) {
                    val infiniteTransition = rememberInfiniteTransition(label = "profile_spin")
                    val rotation by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1500, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        ),
                        label = "profile_rotation"
                    )
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier
                            .size(20.dp)
                            .rotate(rotation)
                    )
                } else {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = operationText,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.TextPrimary
                )
                Text(
                    text = if (section.profileName.isNotBlank())
                        "${section.profileName} - $statusText"
                    else
                        statusText,
                    style = MaterialTheme.typography.labelSmall,
                    color = statusColor
                )
                section.errorMessage?.let { error ->
                    Text(
                        text = error,
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.ErrorColor
                    )
                }
            }

            // Profile ID badge for successful operations
            if (section.status == ProfileOperationStatus.SUCCESS && section.profileId != null) {
                Surface(
                    color = colors.ChipBackground,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = stringResource(StringKeyDosha.PROFILE_ID_LABEL, section.profileId),
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.TextSubtle,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}
