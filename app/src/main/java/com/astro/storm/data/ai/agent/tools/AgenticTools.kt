package com.astro.storm.data.ai.agent.tools

import com.astro.storm.data.local.ChartDatabase
import com.astro.storm.data.local.ChartEntity
import com.astro.storm.data.model.BirthData
import com.astro.storm.data.model.Gender
import com.astro.storm.data.model.HouseSystem
import com.astro.storm.ephemeris.SwissEphemerisEngine
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Agentic Tools for Stormy AI Assistant
 *
 * These tools enable Stormy to work autonomously like professional agentic AIs
 * (Cursor, Windsurf, Google Antigravity). They provide:
 *
 * 1. Task Management: start_task, finish_task, update_todo
 * 2. User Interaction: ask_user (clarifying questions mid-task)
 * 3. Profile Management: create_profile, update_profile
 *
 * These tools are designed for Vedic astrology context while maintaining
 * the agentic workflow patterns of modern AI assistants.
 */

// ============================================
// TASK MANAGEMENT TOOLS
// ============================================

/**
 * Tool to signal the start of a complex task.
 * Helps visualize the agent's work in a sectioned layout.
 */
class StartTaskTool : AstrologyTool {
    override val name = "start_task"
    override val description = """
        Signal the start of a complex astrological analysis or task.
        Use this when beginning multi-step work to help the user understand your process.
        The task name should be concise and descriptive.
    """.trimIndent()
    override val parameters = listOf(
        ToolParameter(
            name = "task_name",
            description = "A concise name for the task (e.g., 'Complete Birth Chart Analysis', 'Dasha Period Interpretation')",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "description",
            description = "Optional brief description of what this task will accomplish",
            type = ParameterType.STRING,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val taskName = arguments.getString("task_name")
        val description = arguments.optString("description", "")

        val data = JSONObject().apply {
            put("task_name", taskName)
            put("description", description)
            put("status", "started")
            put("timestamp", System.currentTimeMillis())
        }

        return ToolExecutionResult(
            success = true,
            data = data,
            summary = "Started task: $taskName"
        )
    }
}

/**
 * Tool to signal the completion of a task.
 * Helps create clean boundaries in the sectioned layout.
 */
class FinishTaskTool : AstrologyTool {
    override val name = "finish_task"
    override val description = """
        Signal the completion of a task.
        Use this after completing a complex analysis to provide closure.
        Include a brief summary of what was accomplished.
    """.trimIndent()
    override val parameters = listOf(
        ToolParameter(
            name = "task_name",
            description = "The name of the task being completed (should match the start_task call)",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "summary",
            description = "Brief summary of what was accomplished in this task",
            type = ParameterType.STRING,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val taskName = arguments.getString("task_name")
        val summary = arguments.optString("summary", "Task completed successfully")

        val data = JSONObject().apply {
            put("task_name", taskName)
            put("summary", summary)
            put("status", "completed")
            put("timestamp", System.currentTimeMillis())
        }

        return ToolExecutionResult(
            success = true,
            data = data,
            summary = "Completed task: $taskName"
        )
    }
}

/**
 * Tool to manage a todo list for complex tasks.
 * Shows progress in the sectioned layout.
 */
class UpdateTodoTool : AstrologyTool {
    override val name = "update_todo"
    override val description = """
        Manage a todo list for tracking progress on complex tasks.
        Use this to show the user what steps you're working through.

        Operations:
        - "add": Add new items to the todo list
        - "complete": Mark items as completed
        - "set_in_progress": Mark an item as currently being worked on
        - "replace": Replace the entire todo list with new items
    """.trimIndent()
    override val parameters = listOf(
        ToolParameter(
            name = "operation",
            description = "Operation to perform: 'add', 'complete', 'set_in_progress', or 'replace'",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "items",
            description = "Array of todo items. For 'add'/'replace': strings to add. For 'complete'/'set_in_progress': item indices (0-based) or item text",
            type = ParameterType.ARRAY,
            required = true
        ),
        ToolParameter(
            name = "title",
            description = "Optional title for the todo list (default: 'Analysis Steps')",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "Analysis Steps"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val operation = arguments.getString("operation").lowercase()
        val itemsArray = arguments.getJSONArray("items")
        val title = arguments.optString("title", "Analysis Steps")

        val items = mutableListOf<String>()
        for (i in 0 until itemsArray.length()) {
            items.add(itemsArray.getString(i))
        }

        val data = JSONObject().apply {
            put("operation", operation)
            put("title", title)
            put("items", JSONArray(items))
            put("timestamp", System.currentTimeMillis())
        }

        val summary = when (operation) {
            "add" -> "Added ${items.size} todo item${if (items.size > 1) "s" else ""}"
            "complete" -> "Marked ${items.size} item${if (items.size > 1) "s" else ""} as completed"
            "set_in_progress" -> "Set ${items.size} item${if (items.size > 1) "s" else ""} as in progress"
            "replace" -> "Updated todo list with ${items.size} item${if (items.size > 1) "s" else ""}"
            else -> "Updated todo list"
        }

        return ToolExecutionResult(
            success = true,
            data = data,
            summary = summary
        )
    }
}

// ============================================
// USER INTERACTION TOOLS
// ============================================

/**
 * Tool to ask the user clarifying questions mid-task.
 * Enables interactive agentic behavior.
 */
class AskUserTool : AstrologyTool {
    override val name = "ask_user"
    override val description = """
        Ask the user a clarifying question when you need more information to proceed.
        Use this when:
        - The user's request is ambiguous
        - You need specific details (birth time, location, etc.)
        - Multiple interpretation approaches are possible
        - You want to confirm before making changes (like creating/editing a profile)

        You can provide optional choices for the user to select from.
        The execution will pause until the user responds.
    """.trimIndent()
    override val parameters = listOf(
        ToolParameter(
            name = "question",
            description = "The question to ask the user. Be clear and specific.",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "options",
            description = "Optional array of choice options for the user. Each option should be an object with 'label' and optional 'description' fields.",
            type = ParameterType.ARRAY,
            required = false
        ),
        ToolParameter(
            name = "allow_custom_input",
            description = "Whether to allow the user to provide custom text input in addition to options. Default: true",
            type = ParameterType.BOOLEAN,
            required = false,
            defaultValue = true
        ),
        ToolParameter(
            name = "context",
            description = "Optional context about why you're asking this question",
            type = ParameterType.STRING,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val question = arguments.getString("question")
        val optionsArray = arguments.optJSONArray("options")
        val allowCustomInput = arguments.optBoolean("allow_custom_input", true)
        val questionContext = arguments.optString("context", "")

        val options = mutableListOf<JSONObject>()
        optionsArray?.let { array ->
            for (i in 0 until array.length()) {
                val opt = array.get(i)
                when (opt) {
                    is JSONObject -> options.add(opt)
                    is String -> options.add(JSONObject().apply {
                        put("label", opt)
                        put("value", opt)
                    })
                }
            }
        }

        val data = JSONObject().apply {
            put("question", question)
            put("options", JSONArray(options))
            put("allow_custom_input", allowCustomInput)
            put("context", questionContext)
            put("status", "awaiting_response")
            put("timestamp", System.currentTimeMillis())
        }

        return ToolExecutionResult(
            success = true,
            data = data,
            summary = "Asking user: ${question.take(50)}${if (question.length > 50) "..." else ""}"
        )
    }
}

// ============================================
// PROFILE MANAGEMENT TOOLS
// ============================================

/**
 * Tool to create a new birth chart profile.
 * Enables autonomous profile creation with full calculation.
 */
class CreateProfileTool : AstrologyTool {
    override val name = "create_profile"
    override val description = """
        Create a new birth chart profile with complete Vedic chart calculation.
        Use this when the user wants to add a new person's chart.

        Required information:
        - Name of the person
        - Birth date and time (as precise as possible)
        - Birth location with coordinates
        - Timezone

        The tool will calculate the complete Vedic chart including:
        - Planetary positions (all 9 grahas + Rahu/Ketu)
        - House cusps
        - Nakshatras and padas
        - Ascendant (Lagna)

        If any required information is missing, use ask_user to get it first.
    """.trimIndent()
    override val parameters = listOf(
        ToolParameter(
            name = "name",
            description = "Name of the person (e.g., 'John Doe', 'Baby Ram')",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "birth_date",
            description = "Birth date in YYYY-MM-DD format (e.g., '1990-05-15')",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "birth_time",
            description = "Birth time in HH:MM format (24-hour) (e.g., '14:30' for 2:30 PM). Use '12:00' if unknown.",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "latitude",
            description = "Birth location latitude in decimal degrees (e.g., 28.6139 for Delhi)",
            type = ParameterType.NUMBER,
            required = true
        ),
        ToolParameter(
            name = "longitude",
            description = "Birth location longitude in decimal degrees (e.g., 77.2090 for Delhi)",
            type = ParameterType.NUMBER,
            required = true
        ),
        ToolParameter(
            name = "location_name",
            description = "Human-readable location name (e.g., 'New Delhi, India')",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "timezone",
            description = "Timezone ID (e.g., 'Asia/Kolkata', 'America/New_York'). Use 'UTC' if unknown.",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "gender",
            description = "Gender: 'male', 'female', or 'other'. Default: 'other'",
            type = ParameterType.STRING,
            required = false,
            defaultValue = "other"
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        try {
            // Parse all required parameters
            val name = arguments.getString("name").trim()
            val birthDateStr = arguments.getString("birth_date")
            val birthTimeStr = arguments.getString("birth_time")
            val latitude = arguments.getDouble("latitude")
            val longitude = arguments.getDouble("longitude")
            val locationName = arguments.getString("location_name")
            val timezone = arguments.getString("timezone")
            val genderStr = arguments.optString("gender", "other")

            // Validate name
            if (name.isBlank()) {
                return ToolExecutionResult(
                    success = false,
                    data = null,
                    error = "Name cannot be empty",
                    summary = "Invalid name"
                )
            }

            // Parse date and time
            val dateTime = try {
                val datePart = LocalDateTime.parse("${birthDateStr}T${birthTimeStr}:00")
                datePart
            } catch (e: DateTimeParseException) {
                return ToolExecutionResult(
                    success = false,
                    data = null,
                    error = "Invalid date/time format. Use YYYY-MM-DD for date and HH:MM for time. Error: ${e.message}",
                    summary = "Invalid date/time format"
                )
            }

            // Validate coordinates
            if (latitude < -90 || latitude > 90) {
                return ToolExecutionResult(
                    success = false,
                    data = null,
                    error = "Latitude must be between -90 and 90 degrees",
                    summary = "Invalid latitude"
                )
            }
            if (longitude < -180 || longitude > 180) {
                return ToolExecutionResult(
                    success = false,
                    data = null,
                    error = "Longitude must be between -180 and 180 degrees",
                    summary = "Invalid longitude"
                )
            }

            // Parse gender
            val gender = Gender.fromString(genderStr)

            // Create birth data
            val birthData = BirthData(
                name = name,
                dateTime = dateTime,
                latitude = latitude,
                longitude = longitude,
                timezone = timezone,
                location = locationName,
                gender = gender
            )

            // Calculate the chart using Swiss Ephemeris
            val ephemerisEngine = SwissEphemerisEngine.getInstance(context.context)
            val chart = ephemerisEngine.calculateVedicChart(birthData, HouseSystem.DEFAULT)

            // Save to database
            val repository = com.astro.storm.data.repository.ChartRepository(context.database.chartDao())
            val chartId = repository.saveChart(chart)

            // Build success response
            val moonSign = chart.planetPositions.find { it.planet.displayName == "Moon" }?.sign?.displayName
            val ascendant = chart.planetPositions.find { it.planet.displayName == "Ascendant" }?.sign?.displayName

            val data = JSONObject().apply {
                put("profile_id", chartId)
                put("name", name)
                put("birth_date", birthDateStr)
                put("birth_time", birthTimeStr)
                put("location", locationName)
                put("latitude", latitude)
                put("longitude", longitude)
                put("timezone", timezone)
                put("gender", gender.displayName)
                put("ascendant", ascendant ?: "Calculated")
                put("moon_sign", moonSign ?: "Calculated")
                put("ayanamsa", chart.ayanamsaName)
                put("status", "created")
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Created profile for $name (ID: $chartId) - Ascendant: $ascendant, Moon: $moonSign"
            )

        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "Failed to create profile: ${e.message}",
                summary = "Profile creation failed"
            )
        }
    }
}

/**
 * Tool to update an existing birth chart profile.
 * Enables autonomous profile editing with re-calculation.
 */
class UpdateProfileTool : AstrologyTool {
    override val name = "update_profile"
    override val description = """
        Update an existing birth chart profile.
        Use this when the user wants to correct or modify chart data.

        You can update any of the following:
        - Name
        - Birth date/time
        - Birth location (coordinates and name)
        - Timezone
        - Gender

        The chart will be re-calculated with the updated information.
        Only provide the fields you want to change.
    """.trimIndent()
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to update. Use 'current' for the active profile.",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "name",
            description = "New name for the person",
            type = ParameterType.STRING,
            required = false
        ),
        ToolParameter(
            name = "birth_date",
            description = "New birth date in YYYY-MM-DD format",
            type = ParameterType.STRING,
            required = false
        ),
        ToolParameter(
            name = "birth_time",
            description = "New birth time in HH:MM format (24-hour)",
            type = ParameterType.STRING,
            required = false
        ),
        ToolParameter(
            name = "latitude",
            description = "New birth location latitude in decimal degrees",
            type = ParameterType.NUMBER,
            required = false
        ),
        ToolParameter(
            name = "longitude",
            description = "New birth location longitude in decimal degrees",
            type = ParameterType.NUMBER,
            required = false
        ),
        ToolParameter(
            name = "location_name",
            description = "New human-readable location name",
            type = ParameterType.STRING,
            required = false
        ),
        ToolParameter(
            name = "timezone",
            description = "New timezone ID",
            type = ParameterType.STRING,
            required = false
        ),
        ToolParameter(
            name = "gender",
            description = "New gender: 'male', 'female', or 'other'",
            type = ParameterType.STRING,
            required = false
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        try {
            val profileIdStr = arguments.getString("profile_id")

            // Get the existing profile
            val existingProfile = if (profileIdStr == "current") {
                context.currentProfile
            } else {
                context.allProfiles.find { it.id.toString() == profileIdStr }
            }

            if (existingProfile == null) {
                return ToolExecutionResult(
                    success = false,
                    data = null,
                    error = "Profile not found with ID: $profileIdStr",
                    summary = "Profile not found"
                )
            }

            // Get current values
            var name = existingProfile.name
            var dateTime = existingProfile.dateTime
            var latitude = existingProfile.latitude
            var longitude = existingProfile.longitude
            var locationName = existingProfile.location
            var timezone = existingProfile.timezone
            var gender = existingProfile.gender

            // Track what's being updated
            val updates = mutableListOf<String>()

            // Apply updates
            if (arguments.has("name")) {
                val newName = arguments.getString("name").trim()
                if (newName.isNotBlank()) {
                    name = newName
                    updates.add("name")
                }
            }

            if (arguments.has("birth_date") || arguments.has("birth_time")) {
                val dateStr = arguments.optString("birth_date", dateTime.toLocalDate().toString())
                val timeStr = arguments.optString("birth_time", dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))

                dateTime = try {
                    LocalDateTime.parse("${dateStr}T${timeStr}:00")
                } catch (e: DateTimeParseException) {
                    return ToolExecutionResult(
                        success = false,
                        data = null,
                        error = "Invalid date/time format: ${e.message}",
                        summary = "Invalid date/time"
                    )
                }
                if (arguments.has("birth_date")) updates.add("birth date")
                if (arguments.has("birth_time")) updates.add("birth time")
            }

            if (arguments.has("latitude")) {
                latitude = arguments.getDouble("latitude")
                if (latitude < -90 || latitude > 90) {
                    return ToolExecutionResult(
                        success = false,
                        data = null,
                        error = "Latitude must be between -90 and 90 degrees",
                        summary = "Invalid latitude"
                    )
                }
                updates.add("latitude")
            }

            if (arguments.has("longitude")) {
                longitude = arguments.getDouble("longitude")
                if (longitude < -180 || longitude > 180) {
                    return ToolExecutionResult(
                        success = false,
                        data = null,
                        error = "Longitude must be between -180 and 180 degrees",
                        summary = "Invalid longitude"
                    )
                }
                updates.add("longitude")
            }

            if (arguments.has("location_name")) {
                locationName = arguments.getString("location_name")
                updates.add("location")
            }

            if (arguments.has("timezone")) {
                timezone = arguments.getString("timezone")
                updates.add("timezone")
            }

            if (arguments.has("gender")) {
                gender = Gender.fromString(arguments.getString("gender"))
                updates.add("gender")
            }

            if (updates.isEmpty()) {
                return ToolExecutionResult(
                    success = false,
                    data = null,
                    error = "No updates provided. Please specify at least one field to update.",
                    summary = "No updates provided"
                )
            }

            // Create updated birth data
            val birthData = BirthData(
                name = name,
                dateTime = dateTime,
                latitude = latitude,
                longitude = longitude,
                timezone = timezone,
                location = locationName,
                gender = gender
            )

            // Re-calculate the chart
            val ephemerisEngine = SwissEphemerisEngine.getInstance(context.context)
            val chart = ephemerisEngine.calculateVedicChart(birthData, HouseSystem.DEFAULT)

            // Update in database
            val repository = com.astro.storm.data.repository.ChartRepository(context.database.chartDao())
            repository.updateChart(existingProfile.id, chart)

            // Build success response
            val moonSign = chart.planetPositions.find { it.planet.displayName == "Moon" }?.sign?.displayName
            val ascendant = chart.planetPositions.find { it.planet.displayName == "Ascendant" }?.sign?.displayName

            val data = JSONObject().apply {
                put("profile_id", existingProfile.id)
                put("name", name)
                put("birth_date", dateTime.toLocalDate().toString())
                put("birth_time", dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                put("location", locationName)
                put("latitude", latitude)
                put("longitude", longitude)
                put("timezone", timezone)
                put("gender", gender.displayName)
                put("ascendant", ascendant ?: "Calculated")
                put("moon_sign", moonSign ?: "Calculated")
                put("updates_applied", JSONArray(updates))
                put("status", "updated")
            }

            return ToolExecutionResult(
                success = true,
                data = data,
                summary = "Updated profile $name: ${updates.joinToString(", ")}"
            )

        } catch (e: Exception) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "Failed to update profile: ${e.message}",
                summary = "Profile update failed"
            )
        }
    }
}

/**
 * Tool to delete a birth chart profile.
 * Use with caution - this permanently removes the profile.
 */
class DeleteProfileTool : AstrologyTool {
    override val name = "delete_profile"
    override val description = """
        Delete an existing birth chart profile.
        This action is irreversible - the profile and all its data will be permanently removed.
        Always confirm with the user before deleting using ask_user tool.
    """.trimIndent()
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to delete",
            type = ParameterType.STRING,
            required = true
        ),
        ToolParameter(
            name = "confirm",
            description = "Must be set to 'true' to confirm deletion",
            type = ParameterType.BOOLEAN,
            required = true
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileIdStr = arguments.getString("profile_id")
        val confirm = arguments.optBoolean("confirm", false)

        if (!confirm) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "Deletion not confirmed. Set 'confirm' to true to proceed.",
                summary = "Deletion not confirmed"
            )
        }

        val profile = context.allProfiles.find { it.id.toString() == profileIdStr }
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "Profile not found with ID: $profileIdStr",
                summary = "Profile not found"
            )

        // Cannot delete the current profile if it's the only one
        if (profile.id == context.currentProfile?.id && context.allProfiles.size == 1) {
            return ToolExecutionResult(
                success = false,
                data = null,
                error = "Cannot delete the only profile. Create another profile first.",
                summary = "Cannot delete only profile"
            )
        }

        // Delete from database
        val repository = com.astro.storm.data.repository.ChartRepository(context.database.chartDao())
        repository.deleteChart(profile.id)

        val data = JSONObject().apply {
            put("profile_id", profile.id)
            put("name", profile.name)
            put("status", "deleted")
        }

        return ToolExecutionResult(
            success = true,
            data = data,
            summary = "Deleted profile: ${profile.name}"
        )
    }
}

/**
 * Tool to set the active/current profile for the conversation.
 * This helps when working with multiple profiles.
 */
class SetActiveProfileTool : AstrologyTool {
    override val name = "set_active_profile"
    override val description = """
        Set which profile should be the active/current one for this conversation.
        Use this when the user wants to switch to a different person's chart.
        All subsequent chart-related tools will use this profile unless specified otherwise.
    """.trimIndent()
    override val parameters = listOf(
        ToolParameter(
            name = "profile_id",
            description = "ID of the profile to set as active",
            type = ParameterType.STRING,
            required = true
        )
    )

    override suspend fun execute(arguments: JSONObject, context: ToolContext): ToolExecutionResult {
        val profileIdStr = arguments.getString("profile_id")

        val profile = context.allProfiles.find { it.id.toString() == profileIdStr }
            ?: return ToolExecutionResult(
                success = false,
                data = null,
                error = "Profile not found with ID: $profileIdStr. Available profiles: ${context.allProfiles.map { "${it.name} (ID: ${it.id})" }.joinToString(", ")}",
                summary = "Profile not found"
            )

        val data = JSONObject().apply {
            put("profile_id", profile.id)
            put("name", profile.name)
            put("location", profile.location)
            put("status", "activated")
        }

        return ToolExecutionResult(
            success = true,
            data = data,
            summary = "Activated profile: ${profile.name}"
        )
    }
}
