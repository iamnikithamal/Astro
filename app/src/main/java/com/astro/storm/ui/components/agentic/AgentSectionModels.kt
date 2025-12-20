package com.astro.storm.ui.components.agentic

import org.json.JSONArray
import org.json.JSONObject
import java.util.UUID

/**
 * Dynamic Sectioned Layout Models for Agentic AI Messages
 *
 * Inspired by professional agentic IDEs (Cursor, Windsurf, Google Antigravity),
 * this model system represents AI responses as a sequence of discrete sections
 * that are rendered dynamically as they stream in.
 *
 * Key Design Principles:
 * 1. Each section is a self-contained unit (reasoning, content, tool, ask_user, todo)
 * 2. Sections are rendered in order as they arrive during streaming
 * 3. Multiple sections of the same type can exist (e.g., multiple reasoning blocks)
 * 4. Each section maintains its own state (collapsed, expanded, completed)
 * 5. The layout is fully dynamic - no static assumptions about section order
 */

/**
 * Represents a single section in an AI agent response.
 * Each section has a unique ID, type, content, and display state.
 */
sealed class AgentSection {
    abstract val id: String
    abstract val timestamp: Long
    abstract val isComplete: Boolean

    /**
     * Reasoning/Thinking Section
     * Displays the AI's internal reasoning process with duration tracking.
     */
    data class Reasoning(
        override val id: String = UUID.randomUUID().toString(),
        override val timestamp: Long = System.currentTimeMillis(),
        override val isComplete: Boolean = false,
        val content: String = "",
        val durationMs: Long = 0,
        val isExpanded: Boolean = false
    ) : AgentSection() {
        val durationDisplay: String
            get() = when {
                durationMs < 1000 -> "${durationMs}ms"
                durationMs < 60000 -> "${durationMs / 1000}s"
                else -> "${durationMs / 60000}m ${(durationMs % 60000) / 1000}s"
            }
    }

    /**
     * Text Content Section
     * Main response content with Markdown support.
     */
    data class Content(
        override val id: String = UUID.randomUUID().toString(),
        override val timestamp: Long = System.currentTimeMillis(),
        override val isComplete: Boolean = false,
        val text: String = "",
        val isTyping: Boolean = false
    ) : AgentSection()

    /**
     * Tool Execution Section
     * Groups related tool calls with status tracking.
     */
    data class ToolGroup(
        override val id: String = UUID.randomUUID().toString(),
        override val timestamp: Long = System.currentTimeMillis(),
        override val isComplete: Boolean = false,
        val tools: List<ToolExecution> = emptyList(),
        val isExpanded: Boolean = true
    ) : AgentSection() {
        val completedCount: Int
            get() = tools.count { it.status == ToolExecutionStatus.COMPLETED }
        val failedCount: Int
            get() = tools.count { it.status == ToolExecutionStatus.FAILED }
        val runningCount: Int
            get() = tools.count { it.status == ToolExecutionStatus.EXECUTING }
        val pendingCount: Int
            get() = tools.count { it.status == ToolExecutionStatus.PENDING }
        val totalCount: Int
            get() = tools.size

        val statusSummary: String
            get() = when {
                runningCount > 0 && completedCount > 0 -> "$completedCount/$totalCount completed, $runningCount running"
                runningCount > 0 -> "$runningCount tool${if (runningCount > 1) "s" else ""} running"
                failedCount > 0 && completedCount > 0 -> "$completedCount completed, $failedCount failed"
                failedCount > 0 -> "$failedCount tool${if (failedCount > 1) "s" else ""} failed"
                completedCount == totalCount && totalCount > 0 -> "$completedCount tool${if (completedCount > 1) "s" else ""} completed"
                else -> "$completedCount/$totalCount completed"
            }
    }

    /**
     * Ask User Section
     * Allows the agent to ask clarifying questions mid-task.
     */
    data class AskUser(
        override val id: String = UUID.randomUUID().toString(),
        override val timestamp: Long = System.currentTimeMillis(),
        override val isComplete: Boolean = false,
        val question: String = "",
        val options: List<AskUserOption> = emptyList(),
        val allowCustomInput: Boolean = true,
        val selectedOptionId: String? = null,
        val customResponse: String? = null,
        val isAnswered: Boolean = false
    ) : AgentSection() {
        val hasOptions: Boolean
            get() = options.isNotEmpty()
    }

    /**
     * Todo List Section
     * Shows task progress with checkable items.
     */
    data class TodoList(
        override val id: String = UUID.randomUUID().toString(),
        override val timestamp: Long = System.currentTimeMillis(),
        override val isComplete: Boolean = false,
        val title: String = "Tasks",
        val items: List<TodoItem> = emptyList(),
        val isExpanded: Boolean = true
    ) : AgentSection() {
        val completedCount: Int
            get() = items.count { it.isCompleted }
        val totalCount: Int
            get() = items.size
        val progress: Float
            get() = if (totalCount > 0) completedCount.toFloat() / totalCount else 0f
        val progressText: String
            get() = "$completedCount/$totalCount completed"
    }

    /**
     * Task Boundary Section
     * Marks the start or end of a task for visual grouping.
     */
    data class TaskBoundary(
        override val id: String = UUID.randomUUID().toString(),
        override val timestamp: Long = System.currentTimeMillis(),
        override val isComplete: Boolean = true,
        val taskName: String = "",
        val isStart: Boolean = true,
        val taskId: String = UUID.randomUUID().toString(),
        val summary: String? = null
    ) : AgentSection()

    /**
     * File Edit Section
     * Shows file modifications made by the agent.
     */
    data class FileEdit(
        override val id: String = UUID.randomUUID().toString(),
        override val timestamp: Long = System.currentTimeMillis(),
        override val isComplete: Boolean = false,
        val fileName: String = "",
        val fileType: String = "",
        val operation: FileOperation = FileOperation.EDIT,
        val additions: Int = 0,
        val deletions: Int = 0,
        val isExpanded: Boolean = false,
        val preview: String? = null
    ) : AgentSection()

    /**
     * Profile Operation Section
     * Shows profile creation or modification.
     */
    data class ProfileOperation(
        override val id: String = UUID.randomUUID().toString(),
        override val timestamp: Long = System.currentTimeMillis(),
        override val isComplete: Boolean = false,
        val operation: ProfileOperationType = ProfileOperationType.VIEW,
        val profileId: Long? = null,
        val profileName: String = "",
        val status: ProfileOperationStatus = ProfileOperationStatus.PENDING,
        val errorMessage: String? = null
    ) : AgentSection()
}

/**
 * Individual tool execution within a ToolGroup
 */
data class ToolExecution(
    val id: String = UUID.randomUUID().toString(),
    val toolName: String,
    val displayName: String,
    val arguments: Map<String, Any?> = emptyMap(),
    val status: ToolExecutionStatus = ToolExecutionStatus.PENDING,
    val startTime: Long = System.currentTimeMillis(),
    val endTime: Long? = null,
    val result: String? = null,
    val error: String? = null
) {
    val duration: Long
        get() = (endTime ?: System.currentTimeMillis()) - startTime

    val durationDisplay: String
        get() = when {
            duration < 1000 -> "${duration}ms"
            duration < 60000 -> "${duration / 1000}.${(duration % 1000) / 100}s"
            else -> "${duration / 60000}m ${(duration % 60000) / 1000}s"
        }
}

enum class ToolExecutionStatus {
    PENDING,
    EXECUTING,
    COMPLETED,
    FAILED
}

/**
 * Option for AskUser section
 */
data class AskUserOption(
    val id: String = UUID.randomUUID().toString(),
    val label: String,
    val description: String? = null,
    val value: String = label
)

/**
 * Todo item for TodoList section
 */
data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val isCompleted: Boolean = false,
    val isInProgress: Boolean = false
)

/**
 * File operation types
 */
enum class FileOperation {
    CREATE,
    EDIT,
    DELETE,
    VIEW
}

/**
 * Profile operation types
 */
enum class ProfileOperationType {
    CREATE,
    UPDATE,
    DELETE,
    VIEW
}

/**
 * Profile operation status
 */
enum class ProfileOperationStatus {
    PENDING,
    IN_PROGRESS,
    SUCCESS,
    FAILED
}

/**
 * Complete streaming state for a message using the sectioned model.
 * This replaces the flat StreamingMessageState with a dynamic section list.
 */
data class SectionedMessageState(
    val messageId: Long,
    val sections: List<AgentSection> = emptyList(),
    val isComplete: Boolean = false,
    val hasError: Boolean = false,
    val errorMessage: String? = null,
    val activeTaskId: String? = null
) {
    /**
     * Get all reasoning sections
     */
    val reasoningSections: List<AgentSection.Reasoning>
        get() = sections.filterIsInstance<AgentSection.Reasoning>()

    /**
     * Get all content sections
     */
    val contentSections: List<AgentSection.Content>
        get() = sections.filterIsInstance<AgentSection.Content>()

    /**
     * Get all tool groups
     */
    val toolGroups: List<AgentSection.ToolGroup>
        get() = sections.filterIsInstance<AgentSection.ToolGroup>()

    /**
     * Get the current ask user section (if any, unanswered)
     */
    val pendingAskUser: AgentSection.AskUser?
        get() = sections.filterIsInstance<AgentSection.AskUser>()
            .firstOrNull { !it.isAnswered }

    /**
     * Get all todo lists
     */
    val todoLists: List<AgentSection.TodoList>
        get() = sections.filterIsInstance<AgentSection.TodoList>()

    /**
     * Get all tools used across all tool groups
     */
    val allToolsUsed: List<String>
        get() = toolGroups.flatMap { group ->
            group.tools
                .filter { it.status == ToolExecutionStatus.COMPLETED }
                .map { it.toolName }
        }.distinct()

    /**
     * Total reasoning duration across all reasoning sections
     */
    val totalReasoningDuration: Long
        get() = reasoningSections.sumOf { it.durationMs }

    /**
     * Combined reasoning content
     */
    val combinedReasoning: String
        get() = reasoningSections.joinToString("\n\n") { it.content }

    /**
     * Combined content text
     */
    val combinedContent: String
        get() = contentSections.joinToString("\n\n") { it.text }

    /**
     * Check if there's an active task
     */
    val hasActiveTask: Boolean
        get() = activeTaskId != null
}

/**
 * Extension function to convert legacy StreamingMessageState to SectionedMessageState
 */
fun com.astro.storm.ui.viewmodel.StreamingMessageState.toSectionedState(): SectionedMessageState {
    val sections = mutableListOf<AgentSection>()

    // Add reasoning section if present
    if (reasoning.isNotBlank()) {
        sections.add(
            AgentSection.Reasoning(
                content = reasoning,
                isComplete = isComplete,
                durationMs = 0
            )
        )
    }

    // Add tool group if there are tool steps
    if (toolSteps.isNotEmpty()) {
        sections.add(
            AgentSection.ToolGroup(
                tools = toolSteps.map { step ->
                    ToolExecution(
                        toolName = step.toolName,
                        displayName = step.displayName,
                        status = when (step.status) {
                            com.astro.storm.ui.viewmodel.ToolStepStatus.PENDING -> ToolExecutionStatus.PENDING
                            com.astro.storm.ui.viewmodel.ToolStepStatus.EXECUTING -> ToolExecutionStatus.EXECUTING
                            com.astro.storm.ui.viewmodel.ToolStepStatus.COMPLETED -> ToolExecutionStatus.COMPLETED
                            com.astro.storm.ui.viewmodel.ToolStepStatus.FAILED -> ToolExecutionStatus.FAILED
                        },
                        startTime = step.startTime,
                        endTime = step.endTime,
                        result = step.result
                    )
                },
                isComplete = toolSteps.all { it.status == com.astro.storm.ui.viewmodel.ToolStepStatus.COMPLETED || it.status == com.astro.storm.ui.viewmodel.ToolStepStatus.FAILED }
            )
        )
    }

    // Add content section if present
    if (content.isNotBlank()) {
        sections.add(
            AgentSection.Content(
                text = content,
                isComplete = isComplete,
                isTyping = !isComplete
            )
        )
    }

    return SectionedMessageState(
        messageId = messageId,
        sections = sections,
        isComplete = isComplete,
        hasError = hasError,
        errorMessage = errorMessage
    )
}

/**
 * Serialization helpers for persisting sectioned message state
 */
object SectionedMessageSerializer {

    fun serialize(state: SectionedMessageState): String {
        val json = JSONObject().apply {
            put("messageId", state.messageId)
            put("isComplete", state.isComplete)
            put("hasError", state.hasError)
            state.errorMessage?.let { put("errorMessage", it) }
            state.activeTaskId?.let { put("activeTaskId", it) }
            put("sections", JSONArray().apply {
                state.sections.forEach { section ->
                    put(serializeSection(section))
                }
            })
        }
        return json.toString()
    }

    private fun serializeSection(section: AgentSection): JSONObject {
        return JSONObject().apply {
            put("id", section.id)
            put("timestamp", section.timestamp)
            put("isComplete", section.isComplete)
            when (section) {
                is AgentSection.Reasoning -> {
                    put("type", "reasoning")
                    put("content", section.content)
                    put("durationMs", section.durationMs)
                    put("isExpanded", section.isExpanded)
                }
                is AgentSection.Content -> {
                    put("type", "content")
                    put("text", section.text)
                    put("isTyping", section.isTyping)
                }
                is AgentSection.ToolGroup -> {
                    put("type", "tool_group")
                    put("isExpanded", section.isExpanded)
                    put("tools", JSONArray().apply {
                        section.tools.forEach { tool ->
                            put(JSONObject().apply {
                                put("id", tool.id)
                                put("toolName", tool.toolName)
                                put("displayName", tool.displayName)
                                put("status", tool.status.name)
                                put("startTime", tool.startTime)
                                tool.endTime?.let { put("endTime", it) }
                                tool.result?.let { put("result", it) }
                                tool.error?.let { put("error", it) }
                            })
                        }
                    })
                }
                is AgentSection.AskUser -> {
                    put("type", "ask_user")
                    put("question", section.question)
                    put("allowCustomInput", section.allowCustomInput)
                    put("isAnswered", section.isAnswered)
                    section.selectedOptionId?.let { put("selectedOptionId", it) }
                    section.customResponse?.let { put("customResponse", it) }
                    put("options", JSONArray().apply {
                        section.options.forEach { option ->
                            put(JSONObject().apply {
                                put("id", option.id)
                                put("label", option.label)
                                option.description?.let { put("description", it) }
                                put("value", option.value)
                            })
                        }
                    })
                }
                is AgentSection.TodoList -> {
                    put("type", "todo_list")
                    put("title", section.title)
                    put("isExpanded", section.isExpanded)
                    put("items", JSONArray().apply {
                        section.items.forEach { item ->
                            put(JSONObject().apply {
                                put("id", item.id)
                                put("text", item.text)
                                put("isCompleted", item.isCompleted)
                                put("isInProgress", item.isInProgress)
                            })
                        }
                    })
                }
                is AgentSection.TaskBoundary -> {
                    put("type", "task_boundary")
                    put("taskName", section.taskName)
                    put("isStart", section.isStart)
                    put("taskId", section.taskId)
                    section.summary?.let { put("summary", it) }
                }
                is AgentSection.FileEdit -> {
                    put("type", "file_edit")
                    put("fileName", section.fileName)
                    put("fileType", section.fileType)
                    put("operation", section.operation.name)
                    put("additions", section.additions)
                    put("deletions", section.deletions)
                    put("isExpanded", section.isExpanded)
                    section.preview?.let { put("preview", it) }
                }
                is AgentSection.ProfileOperation -> {
                    put("type", "profile_operation")
                    put("operation", section.operation.name)
                    section.profileId?.let { put("profileId", it) }
                    put("profileName", section.profileName)
                    put("status", section.status.name)
                    section.errorMessage?.let { put("errorMessage", it) }
                }
            }
        }
    }

    fun deserialize(json: String): SectionedMessageState? {
        return try {
            val obj = JSONObject(json)
            SectionedMessageState(
                messageId = obj.getLong("messageId"),
                isComplete = obj.getBoolean("isComplete"),
                hasError = obj.getBoolean("hasError"),
                errorMessage = obj.optString("errorMessage").takeIf { it.isNotEmpty() },
                activeTaskId = obj.optString("activeTaskId").takeIf { it.isNotEmpty() },
                sections = obj.getJSONArray("sections").let { array ->
                    List(array.length()) { i ->
                        deserializeSection(array.getJSONObject(i))
                    }
                }.filterNotNull()
            )
        } catch (e: Exception) {
            null
        }
    }

    private fun deserializeSection(json: JSONObject): AgentSection? {
        return try {
            val id = json.getString("id")
            val timestamp = json.getLong("timestamp")
            val isComplete = json.getBoolean("isComplete")

            when (json.getString("type")) {
                "reasoning" -> AgentSection.Reasoning(
                    id = id,
                    timestamp = timestamp,
                    isComplete = isComplete,
                    content = json.getString("content"),
                    durationMs = json.getLong("durationMs"),
                    isExpanded = json.optBoolean("isExpanded", false)
                )
                "content" -> AgentSection.Content(
                    id = id,
                    timestamp = timestamp,
                    isComplete = isComplete,
                    text = json.getString("text"),
                    isTyping = json.optBoolean("isTyping", false)
                )
                "tool_group" -> AgentSection.ToolGroup(
                    id = id,
                    timestamp = timestamp,
                    isComplete = isComplete,
                    isExpanded = json.optBoolean("isExpanded", true),
                    tools = json.getJSONArray("tools").let { array ->
                        List(array.length()) { i ->
                            val toolJson = array.getJSONObject(i)
                            ToolExecution(
                                id = toolJson.getString("id"),
                                toolName = toolJson.getString("toolName"),
                                displayName = toolJson.getString("displayName"),
                                status = ToolExecutionStatus.valueOf(toolJson.getString("status")),
                                startTime = toolJson.getLong("startTime"),
                                endTime = toolJson.optLong("endTime").takeIf { it > 0 },
                                result = toolJson.optString("result").takeIf { it.isNotEmpty() },
                                error = toolJson.optString("error").takeIf { it.isNotEmpty() }
                            )
                        }
                    }
                )
                "ask_user" -> AgentSection.AskUser(
                    id = id,
                    timestamp = timestamp,
                    isComplete = isComplete,
                    question = json.getString("question"),
                    allowCustomInput = json.optBoolean("allowCustomInput", true),
                    isAnswered = json.optBoolean("isAnswered", false),
                    selectedOptionId = json.optString("selectedOptionId").takeIf { it.isNotEmpty() },
                    customResponse = json.optString("customResponse").takeIf { it.isNotEmpty() },
                    options = json.optJSONArray("options")?.let { array ->
                        List(array.length()) { i ->
                            val optJson = array.getJSONObject(i)
                            AskUserOption(
                                id = optJson.getString("id"),
                                label = optJson.getString("label"),
                                description = optJson.optString("description").takeIf { it.isNotEmpty() },
                                value = optJson.getString("value")
                            )
                        }
                    } ?: emptyList()
                )
                "todo_list" -> AgentSection.TodoList(
                    id = id,
                    timestamp = timestamp,
                    isComplete = isComplete,
                    title = json.getString("title"),
                    isExpanded = json.optBoolean("isExpanded", true),
                    items = json.getJSONArray("items").let { array ->
                        List(array.length()) { i ->
                            val itemJson = array.getJSONObject(i)
                            TodoItem(
                                id = itemJson.getString("id"),
                                text = itemJson.getString("text"),
                                isCompleted = itemJson.getBoolean("isCompleted"),
                                isInProgress = itemJson.optBoolean("isInProgress", false)
                            )
                        }
                    }
                )
                "task_boundary" -> AgentSection.TaskBoundary(
                    id = id,
                    timestamp = timestamp,
                    isComplete = isComplete,
                    taskName = json.getString("taskName"),
                    isStart = json.getBoolean("isStart"),
                    taskId = json.getString("taskId"),
                    summary = json.optString("summary").takeIf { it.isNotEmpty() }
                )
                "file_edit" -> AgentSection.FileEdit(
                    id = id,
                    timestamp = timestamp,
                    isComplete = isComplete,
                    fileName = json.getString("fileName"),
                    fileType = json.optString("fileType", ""),
                    operation = FileOperation.valueOf(json.getString("operation")),
                    additions = json.optInt("additions", 0),
                    deletions = json.optInt("deletions", 0),
                    isExpanded = json.optBoolean("isExpanded", false),
                    preview = json.optString("preview").takeIf { it.isNotEmpty() }
                )
                "profile_operation" -> AgentSection.ProfileOperation(
                    id = id,
                    timestamp = timestamp,
                    isComplete = isComplete,
                    operation = ProfileOperationType.valueOf(json.getString("operation")),
                    profileId = json.optLong("profileId").takeIf { it > 0 },
                    profileName = json.getString("profileName"),
                    status = ProfileOperationStatus.valueOf(json.getString("status")),
                    errorMessage = json.optString("errorMessage").takeIf { it.isNotEmpty() }
                )
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }
}
