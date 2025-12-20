package com.astro.storm.ui.components.agentic

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Centralized Tool Display Utilities
 *
 * This module provides a single source of truth for tool name formatting
 * and icon mapping throughout the agentic UI components.
 *
 * Key Features:
 * - Consistent tool name formatting across all UI components
 * - Semantic icon mapping for Vedic astrology tools
 * - Duration formatting utilities
 * - Eliminates code duplication across ChatTab, ChatViewModel,
 *   AgenticMessageComponents, SectionedMessageCard, and SectionedComponents
 */
object ToolDisplayUtils {

    /**
     * Format tool name for user-friendly display
     *
     * Converts snake_case tool names to Title Case display names.
     * Removes common prefixes like "get_" and "calculate_" for cleaner display.
     *
     * Examples:
     * - get_planet_positions -> Planet Positions
     * - get_current_dasha -> Current Dasha
     * - calculate_muhurta -> Muhurta
     * - get_birth_chart_summary -> Birth Chart Summary
     * - get_yogas -> Yogas
     * - get_transits -> Transits
     *
     * @param toolName The raw tool name from the agent
     * @return Formatted display name for the UI
     */
    fun formatToolName(toolName: String): String {
        return toolName
            .removePrefix("get_")
            .removePrefix("calculate_")
            .removePrefix("search_")
            .removePrefix("analyze_")
            .removePrefix("fetch_")
            .replace("_", " ")
            .split(" ")
            .joinToString(" ") { word ->
                word.replaceFirstChar { it.uppercase() }
            }
    }

    /**
     * Get the appropriate icon for a tool based on its name
     *
     * Maps Vedic astrology tools to semantic Material icons
     * for better visual identification in the UI.
     *
     * @param toolName The raw tool name
     * @return ImageVector icon appropriate for the tool
     */
    fun getToolIcon(toolName: String): ImageVector {
        return when {
            // Planet and celestial body tools
            toolName.contains("planet") -> Icons.Outlined.Public
            toolName.contains("sun") -> Icons.Outlined.WbSunny
            toolName.contains("moon") -> Icons.Outlined.NightsStay

            // House and chart tools
            toolName.contains("house") -> Icons.Outlined.Home
            toolName.contains("chart") -> Icons.Outlined.Analytics
            toolName.contains("ascendant") -> Icons.Outlined.Navigation

            // Time-based tools
            toolName.contains("dasha") -> Icons.Outlined.Timeline
            toolName.contains("transit") -> Icons.Outlined.TrendingUp
            toolName.contains("muhurta") -> Icons.Outlined.Schedule
            toolName.contains("time") -> Icons.Outlined.AccessTime

            // Yoga and combination tools
            toolName.contains("yoga") -> Icons.Outlined.SelfImprovement
            toolName.contains("combination") -> Icons.Outlined.Merge

            // Compatibility tools
            toolName.contains("compatibility") -> Icons.Outlined.Favorite
            toolName.contains("match") -> Icons.Outlined.CompareArrows

            // Remedy and recommendation tools
            toolName.contains("remedy") -> Icons.Outlined.Healing
            toolName.contains("gemstone") -> Icons.Outlined.Diamond
            toolName.contains("mantra") -> Icons.Outlined.MusicNote

            // Profile tools
            toolName.contains("profile") -> Icons.Outlined.Person
            toolName.contains("birth") -> Icons.Outlined.Cake

            // Prediction tools
            toolName.contains("predict") -> Icons.Outlined.Visibility
            toolName.contains("forecast") -> Icons.Outlined.CloudQueue

            // Generic calculation tools
            toolName.contains("calculate") -> Icons.Outlined.Calculate
            toolName.contains("analyze") -> Icons.Outlined.Insights

            // Default tool icon
            else -> Icons.Outlined.Build
        }
    }

    /**
     * Format duration for display
     *
     * Converts milliseconds to a human-readable duration string.
     * Uses appropriate units based on the duration magnitude.
     *
     * @param durationMs Duration in milliseconds
     * @return Formatted duration string (e.g., "250ms", "2.3s", "1m 15s")
     */
    fun formatDuration(durationMs: Long): String {
        return when {
            durationMs < 1000 -> "${durationMs}ms"
            durationMs < 60000 -> "${durationMs / 1000}.${(durationMs % 1000) / 100}s"
            else -> "${durationMs / 60000}m ${(durationMs % 60000) / 1000}s"
        }
    }

    /**
     * Format reasoning duration for display in section headers
     *
     * Provides a more verbose format for reasoning duration display.
     *
     * @param durationMs Duration in milliseconds
     * @return Formatted duration string (e.g., "for 250ms", "for 2s", "for 1m 15s")
     */
    fun formatReasoningDuration(durationMs: Long): String {
        return when {
            durationMs < 1000 -> "for ${durationMs}ms"
            durationMs < 60000 -> "for ${durationMs / 1000}s"
            else -> "for ${durationMs / 60000}m ${(durationMs % 60000) / 1000}s"
        }
    }

    /**
     * Build status summary text for tool execution groups
     *
     * @param completed Number of completed tools
     * @param executing Number of currently executing tools
     * @param failed Number of failed tools
     * @param total Total number of tools
     * @return Status summary string for display
     */
    fun buildToolStatusSummary(
        completed: Int,
        executing: Int,
        failed: Int,
        total: Int
    ): String {
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
     * Check if a tool name represents an internal/agentic tool
     * that should be filtered from user-facing displays
     *
     * @param toolName The raw tool name
     * @return True if this is an internal tool that should be hidden
     */
    fun isInternalTool(toolName: String): Boolean {
        return toolName in listOf(
            "start_task",
            "finish_task",
            "update_todo",
            "set_status"
        )
    }

    /**
     * Check if a tool name represents a profile management tool
     *
     * @param toolName The raw tool name
     * @return True if this is a profile-related tool
     */
    fun isProfileTool(toolName: String): Boolean {
        return toolName in listOf(
            "create_profile",
            "update_profile",
            "delete_profile",
            "set_active_profile",
            "get_profile",
            "list_profiles"
        )
    }

    /**
     * Check if a tool name represents an interactive tool
     * that requires user input
     *
     * @param toolName The raw tool name
     * @return True if this is an interactive tool
     */
    fun isInteractiveTool(toolName: String): Boolean {
        return toolName in listOf(
            "ask_user",
            "confirm_action",
            "select_option"
        )
    }
}
