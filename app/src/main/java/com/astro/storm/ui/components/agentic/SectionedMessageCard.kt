package com.astro.storm.ui.components.agentic

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.astro.storm.ui.components.ContentCleaner
import com.astro.storm.ui.components.MarkdownText
import com.astro.storm.ui.theme.AppTheme
import com.astro.storm.ui.viewmodel.AiStatus

/**
 * Sectioned Agentic Message Card
 *
 * This component renders AI messages with a dynamic sectioned layout,
 * inspired by professional agentic IDEs (Cursor, Windsurf, Google Antigravity).
 *
 * Key Features:
 * - Dynamic section rendering based on SectionedMessageState
 * - Each section type has its own dedicated component
 * - Sections animate in as they stream
 * - Interactive sections support user input
 * - Clean, professional design without bubbles
 *
 * The sections are rendered in order as they appear in the state,
 * allowing for flexible layouts like:
 * - Reasoning -> Content
 * - Tools -> Content
 * - Reasoning -> Tools -> Content -> Ask User -> More Tools -> Final Content
 * - Task Start -> Analysis -> Task End
 */
@Composable
fun SectionedMessageCard(
    sectionedState: SectionedMessageState,
    aiStatus: AiStatus,
    onAskUserResponse: (sectionId: String, response: String) -> Unit = { _, _ -> },
    onAskUserOptionSelect: (sectionId: String, option: AskUserOption) -> Unit = { _, _ -> },
    onToggleSection: (sectionId: String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.current
    val isProcessing = aiStatus !is AiStatus.Idle && aiStatus !is AiStatus.Complete

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Agent Identity Header
        SectionedAgentHeader(
            isProcessing = isProcessing,
            aiStatus = aiStatus
        )

        // Dynamic Section Rendering
        sectionedState.sections.forEach { section ->
            key(section.id) {
                AnimatedVisibility(
                    visible = true,
                    enter = expandVertically(
                        animationSpec = tween(200),
                        expandFrom = Alignment.Top
                    ) + fadeIn(animationSpec = tween(200)),
                    exit = shrinkVertically(animationSpec = tween(200)) + fadeOut(animationSpec = tween(200))
                ) {
                    when (section) {
                        is AgentSection.TaskBoundary -> {
                            TaskBoundarySection(section = section)
                        }

                        is AgentSection.Reasoning -> {
                            ReasoningSection(
                                section = section,
                                onToggleExpand = { onToggleSection(section.id) }
                            )
                        }

                        is AgentSection.ToolGroup -> {
                            ToolGroupSection(
                                section = section,
                                onToggleExpand = { onToggleSection(section.id) }
                            )
                        }

                        is AgentSection.Content -> {
                            ContentSection(section = section)
                        }

                        is AgentSection.AskUser -> {
                            AskUserSection(
                                section = section,
                                onSelectOption = { option ->
                                    onAskUserOptionSelect(section.id, option)
                                },
                                onSubmitCustomResponse = { response ->
                                    onAskUserResponse(section.id, response)
                                }
                            )
                        }

                        is AgentSection.TodoList -> {
                            TodoListSection(
                                section = section,
                                onToggleExpand = { onToggleSection(section.id) }
                            )
                        }

                        is AgentSection.FileEdit -> {
                            // File edits not applicable for astrology app
                            // Could be used for chart exports in future
                        }

                        is AgentSection.ProfileOperation -> {
                            ProfileOperationSection(section = section)
                        }
                    }
                }
            }
        }

        // Error state
        if (sectionedState.hasError && sectionedState.errorMessage != null) {
            ErrorBanner(message = sectionedState.errorMessage)
        }

        // Processing indicator when no sections yet
        if (sectionedState.sections.isEmpty() && isProcessing) {
            ProcessingIndicator(aiStatus = aiStatus)
        }
    }
}

/**
 * Agent Header for Sectioned Layout
 *
 * Shows Stormy avatar with current processing status.
 */
@Composable
private fun SectionedAgentHeader(
    isProcessing: Boolean,
    aiStatus: AiStatus
) {
    val colors = AppTheme.current

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Animated avatar
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
                    ActiveDot()
                }
            }

            // Status text
            val statusText = getStatusText(aiStatus)
            statusText?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.TextMuted
                )
            }
        }
    }
}

/**
 * Small pulsing active indicator dot
 */
@Composable
private fun ActiveDot() {
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
 * Error banner for failed operations
 */
@Composable
private fun ErrorBanner(message: String) {
    val colors = AppTheme.current

    Surface(
        color = colors.ErrorColor.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.ErrorOutline,
                contentDescription = null,
                tint = colors.ErrorColor,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
                color = colors.ErrorColor,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Processing indicator when waiting for first section
 */
@Composable
private fun ProcessingIndicator(aiStatus: AiStatus) {
    val colors = AppTheme.current
    val statusText = getStatusText(aiStatus) ?: "Processing..."

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
        Text(
            text = statusText,
            style = MaterialTheme.typography.bodySmall,
            color = colors.TextMuted
        )
    }
}

/**
 * Get status text for current AI status
 */
private fun getStatusText(aiStatus: AiStatus): String? {
    return when (aiStatus) {
        is AiStatus.Idle -> null
        is AiStatus.Complete -> null
        is AiStatus.Thinking -> "Analyzing your question..."
        is AiStatus.Reasoning -> "Reasoning through Vedic principles..."
        is AiStatus.CallingTool -> "Using ${formatToolName(aiStatus.toolName)}..."
        is AiStatus.ExecutingTools -> "Gathering astrological data..."
        is AiStatus.Typing -> "Composing response..."
    }
}

/**
 * Format tool name for display
 */
private fun formatToolName(toolName: String): String {
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
 * Completed Sectioned Message Card
 *
 * For displaying finalized AI messages from the database
 * using the sectioned layout approach.
 */
@Composable
fun CompletedSectionedMessageCard(
    content: String,
    reasoning: String?,
    toolsUsed: List<String>?,
    sectionsJson: String?,
    errorMessage: String?,
    onRegenerate: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    val colors = AppTheme.current

    // Try to deserialize sections, fall back to legacy rendering
    val sectionedState = remember(sectionsJson) {
        sectionsJson?.let { SectionedMessageSerializer.deserialize(it) }
    }

    // Clean content
    val cleanedContent = remember(content) {
        ContentCleaner.cleanForDisplay(content)
    }
    val cleanedReasoning = remember(reasoning) {
        reasoning?.let { ContentCleaner.cleanReasoning(it) }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Static agent header
        CompletedAgentHeader()

        // If we have sectioned state, render sections
        if (sectionedState != null && sectionedState.sections.isNotEmpty()) {
            // Expandable state for each section
            val expandedStates = remember {
                mutableStateMapOf<String, Boolean>().apply {
                    // Default: reasoning collapsed, others expanded
                    sectionedState.sections.forEach { section ->
                        this[section.id] = when (section) {
                            is AgentSection.Reasoning -> false
                            is AgentSection.ToolGroup -> false
                            is AgentSection.TodoList -> true
                            else -> true
                        }
                    }
                }
            }

            sectionedState.sections.forEach { section ->
                when (section) {
                    is AgentSection.TaskBoundary -> {
                        TaskBoundarySection(section = section)
                    }

                    is AgentSection.Reasoning -> {
                        ReasoningSection(
                            section = section.copy(isExpanded = expandedStates[section.id] ?: false),
                            onToggleExpand = {
                                expandedStates[section.id] = !(expandedStates[section.id] ?: false)
                            }
                        )
                    }

                    is AgentSection.ToolGroup -> {
                        ToolGroupSection(
                            section = section.copy(isExpanded = expandedStates[section.id] ?: false),
                            onToggleExpand = {
                                expandedStates[section.id] = !(expandedStates[section.id] ?: false)
                            }
                        )
                    }

                    is AgentSection.Content -> {
                        ContentSection(section = section.copy(isComplete = true, isTyping = false))
                    }

                    is AgentSection.AskUser -> {
                        // Show answered state for completed messages
                        AskUserSection(
                            section = section.copy(isAnswered = true),
                            onSelectOption = {},
                            onSubmitCustomResponse = {}
                        )
                    }

                    is AgentSection.TodoList -> {
                        TodoListSection(
                            section = section.copy(isExpanded = expandedStates[section.id] ?: true),
                            onToggleExpand = {
                                expandedStates[section.id] = !(expandedStates[section.id] ?: true)
                            }
                        )
                    }

                    is AgentSection.FileEdit -> {
                        // Not used
                    }

                    is AgentSection.ProfileOperation -> {
                        ProfileOperationSection(section = section)
                    }
                }
            }
        } else {
            // Legacy rendering for messages without sectioned data
            var showReasoning by remember { mutableStateOf(false) }

            // Reasoning panel if present
            if (!cleanedReasoning.isNullOrBlank()) {
                val reasoningSection = AgentSection.Reasoning(
                    content = cleanedReasoning,
                    isComplete = true,
                    isExpanded = showReasoning
                )
                ReasoningSection(
                    section = reasoningSection,
                    onToggleExpand = { showReasoning = !showReasoning }
                )
            }

            // Tools used
            if (!toolsUsed.isNullOrEmpty()) {
                val toolGroup = AgentSection.ToolGroup(
                    tools = toolsUsed.map { toolName ->
                        ToolExecution(
                            toolName = toolName,
                            displayName = formatToolName(toolName),
                            status = ToolExecutionStatus.COMPLETED
                        )
                    },
                    isComplete = true,
                    isExpanded = false
                )
                var showTools by remember { mutableStateOf(false) }
                ToolGroupSection(
                    section = toolGroup.copy(isExpanded = showTools),
                    onToggleExpand = { showTools = !showTools }
                )
            }

            // Error state
            if (errorMessage != null) {
                ErrorBanner(message = errorMessage)
            } else if (cleanedContent.isNotEmpty()) {
                // Main content
                MarkdownText(
                    markdown = cleanedContent,
                    modifier = Modifier.fillMaxWidth(),
                    textColor = colors.TextPrimary,
                    linkColor = colors.AccentPrimary,
                    textSize = 15f,
                    cleanContent = false
                )
            }
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

/**
 * Static agent header for completed messages
 */
@Composable
private fun CompletedAgentHeader() {
    val colors = AppTheme.current

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
}
