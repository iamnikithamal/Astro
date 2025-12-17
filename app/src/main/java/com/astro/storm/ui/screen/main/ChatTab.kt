package com.astro.storm.ui.screen.main

import androidx.compose.animation.*
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.astro.storm.data.ai.provider.AiModel
import com.astro.storm.data.ai.provider.MessageRole
import com.astro.storm.data.local.chat.ChatConversation
import com.astro.storm.data.local.chat.ChatMessageModel
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.repository.SavedChart
import com.astro.storm.ui.theme.AppTheme
import com.astro.storm.ui.viewmodel.ChatUiState
import com.astro.storm.ui.viewmodel.ChatViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * Chat Tab - Main entry point for AI chat feature
 *
 * Shows either:
 * - Conversations list (when no conversation is open)
 * - Chat screen (when a conversation is open)
 */
@Composable
fun ChatTab(
    viewModel: ChatViewModel,
    currentChart: VedicChart?,
    savedCharts: List<SavedChart>,
    selectedChartId: Long?,
    onNavigateToModels: () -> Unit
) {
    val conversations by viewModel.conversations.collectAsState()
    val currentConversationId by viewModel.currentConversationId.collectAsState()
    val currentMessages by viewModel.currentMessages.collectAsState()
    val availableModels by viewModel.availableModels.collectAsState()
    val selectedModel by viewModel.selectedModel.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val isStreaming by viewModel.isStreaming.collectAsState()
    val streamingContent by viewModel.streamingContent.collectAsState()
    val streamingReasoning by viewModel.streamingReasoning.collectAsState()
    val toolsInProgress by viewModel.toolsInProgress.collectAsState()

    val colors = AppTheme.current

    // Show chat screen if conversation is open
    if (currentConversationId != null) {
        ChatScreen(
            messages = currentMessages,
            streamingContent = streamingContent,
            streamingReasoning = streamingReasoning,
            isStreaming = isStreaming,
            toolsInProgress = toolsInProgress,
            uiState = uiState,
            selectedModel = selectedModel,
            availableModels = availableModels,
            onSendMessage = { message ->
                viewModel.sendMessage(message, currentChart, savedCharts, selectedChartId)
            },
            onCancelStreaming = { viewModel.cancelStreaming() },
            onRegenerateResponse = {
                viewModel.regenerateResponse(currentChart, savedCharts, selectedChartId)
            },
            onSelectModel = { viewModel.selectModel(it) },
            onBack = { viewModel.closeConversation() },
            onClearChat = { viewModel.clearConversation() },
            onNavigateToModels = onNavigateToModels
        )
    } else {
        // Show conversations list
        ConversationsListScreen(
            conversations = conversations,
            onConversationClick = { conversation ->
                viewModel.openConversation(
                    conversationId = conversation.id,
                    currentChart = currentChart,
                    savedCharts = savedCharts,
                    selectedChartId = selectedChartId
                )
            },
            onNewChat = {
                viewModel.createConversation(currentChart, savedCharts, selectedChartId)
            },
            onDeleteConversation = { viewModel.deleteConversation(it.id) },
            onArchiveConversation = { viewModel.archiveConversation(it.id) },
            selectedModel = selectedModel,
            availableModels = availableModels,
            onSelectModel = { viewModel.selectModel(it) },
            onNavigateToModels = onNavigateToModels,
            hasModels = availableModels.isNotEmpty()
        )
    }
}

// ============================================
// CONVERSATIONS LIST SCREEN
// ============================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConversationsListScreen(
    conversations: List<ChatConversation>,
    onConversationClick: (ChatConversation) -> Unit,
    onNewChat: () -> Unit,
    onDeleteConversation: (ChatConversation) -> Unit,
    onArchiveConversation: (ChatConversation) -> Unit,
    selectedModel: AiModel?,
    availableModels: List<AiModel>,
    onSelectModel: (AiModel) -> Unit,
    onNavigateToModels: () -> Unit,
    hasModels: Boolean
) {
    val colors = AppTheme.current
    val language = LocalLanguage.current
    var showModelSelector by remember { mutableStateOf(false) }
    var conversationToDelete by remember { mutableStateOf<ChatConversation?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.ScreenBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Model selector row
            ModelSelectorRow(
                selectedModel = selectedModel,
                onClick = { showModelSelector = true },
                onNavigateToModels = onNavigateToModels
            )

            if (conversations.isEmpty()) {
                // Empty state
                EmptyChatState(
                    hasModels = hasModels,
                    onNewChat = onNewChat,
                    onNavigateToModels = onNavigateToModels
                )
            } else {
                // Conversations list
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = conversations,
                        key = { it.id }
                    ) { conversation ->
                        ConversationCard(
                            conversation = conversation,
                            onClick = { onConversationClick(conversation) },
                            onDelete = { conversationToDelete = conversation },
                            onArchive = { onArchiveConversation(conversation) }
                        )
                    }
                }
            }
        }

        // FAB for new chat
        if (hasModels && conversations.isNotEmpty()) {
            FloatingActionButton(
                onClick = onNewChat,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = colors.AccentPrimary,
                contentColor = colors.ScreenBackground
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "New Chat"
                )
            }
        }
    }

    // Model selector bottom sheet
    if (showModelSelector) {
        ModelSelectorBottomSheet(
            models = availableModels,
            selectedModel = selectedModel,
            onSelectModel = {
                onSelectModel(it)
                showModelSelector = false
            },
            onDismiss = { showModelSelector = false },
            onNavigateToModels = {
                showModelSelector = false
                onNavigateToModels()
            }
        )
    }

    // Delete confirmation dialog
    conversationToDelete?.let { conversation ->
        AlertDialog(
            onDismissRequest = { conversationToDelete = null },
            title = { Text("Delete Chat") },
            text = { Text("Are you sure you want to delete \"${conversation.title}\"? This cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteConversation(conversation)
                        conversationToDelete = null
                    }
                ) {
                    Text("Delete", color = colors.ErrorColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { conversationToDelete = null }) {
                    Text("Cancel")
                }
            },
            containerColor = colors.CardBackground
        )
    }
}

@Composable
private fun EmptyChatState(
    hasModels: Boolean,
    onNewChat: () -> Unit,
    onNavigateToModels: () -> Unit
) {
    val colors = AppTheme.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Stormy icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            colors.AccentGold.copy(alpha = 0.3f),
                            colors.AccentPrimary.copy(alpha = 0.1f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = colors.AccentGold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Meet Stormy",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = colors.TextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your Vedic astrology AI assistant",
            style = MaterialTheme.typography.bodyLarge,
            color = colors.TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ask about your birth chart, planetary periods, transits, compatibility, remedies, and more.",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.TextMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (hasModels) {
            Button(
                onClick = onNewChat,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.AccentPrimary,
                    contentColor = colors.ScreenBackground
                ),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Start New Chat")
            }
        } else {
            OutlinedButton(
                onClick = onNavigateToModels,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colors.AccentPrimary
                ),
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Configure AI Models")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Enable AI models to start chatting",
                style = MaterialTheme.typography.bodySmall,
                color = colors.TextMuted
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConversationCard(
    conversation: ChatConversation,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onArchive: () -> Unit
) {
    val colors = AppTheme.current
    var showMenu by remember { mutableStateOf(false) }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = colors.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chat icon
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(colors.ChipBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Chat,
                    contentDescription = null,
                    tint = colors.AccentPrimary,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = conversation.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Medium,
                    color = colors.TextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                if (conversation.lastMessagePreview != null) {
                    Text(
                        text = conversation.lastMessagePreview,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.TextMuted,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = formatTimestamp(conversation.updatedAt),
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.TextSubtle
                    )

                    Text(
                        text = "•",
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.TextSubtle
                    )

                    Text(
                        text = "${conversation.messageCount} messages",
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.TextSubtle
                    )
                }
            }

            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = colors.TextMuted
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Archive") },
                        onClick = {
                            showMenu = false
                            onArchive()
                        },
                        leadingIcon = {
                            Icon(Icons.Outlined.Archive, contentDescription = null)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete", color = colors.ErrorColor) },
                        onClick = {
                            showMenu = false
                            onDelete()
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Delete,
                                contentDescription = null,
                                tint = colors.ErrorColor
                            )
                        }
                    )
                }
            }
        }
    }
}

// ============================================
// CHAT SCREEN
// ============================================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChatScreen(
    messages: List<ChatMessageModel>,
    streamingContent: String,
    streamingReasoning: String,
    isStreaming: Boolean,
    toolsInProgress: List<String>,
    uiState: ChatUiState,
    selectedModel: AiModel?,
    availableModels: List<AiModel>,
    onSendMessage: (String) -> Unit,
    onCancelStreaming: () -> Unit,
    onRegenerateResponse: () -> Unit,
    onSelectModel: (AiModel) -> Unit,
    onBack: () -> Unit,
    onClearChat: () -> Unit,
    onNavigateToModels: () -> Unit
) {
    val colors = AppTheme.current
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var messageText by remember { mutableStateOf("") }
    var showModelSelector by remember { mutableStateOf(false) }
    var showClearConfirm by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    // Auto-scroll to bottom on new messages
    LaunchedEffect(messages.size, streamingContent) {
        if (messages.isNotEmpty() || streamingContent.isNotEmpty()) {
            listState.animateScrollToItem(
                (messages.size + if (isStreaming) 1 else 0).coerceAtLeast(0)
            )
        }
    }

    Scaffold(
        containerColor = colors.ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Stormy",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = colors.TextPrimary
                        )
                        selectedModel?.let {
                            Text(
                                text = it.displayName,
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.TextMuted
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = colors.TextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showModelSelector = true }) {
                        Icon(
                            imageVector = Icons.Outlined.Psychology,
                            contentDescription = "Change model",
                            tint = colors.TextSecondary
                        )
                    }
                    IconButton(onClick = { showClearConfirm = true }) {
                        Icon(
                            imageVector = Icons.Outlined.DeleteSweep,
                            contentDescription = "Clear chat",
                            tint = colors.TextSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.ScreenBackground
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Messages list
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Welcome message if empty
                if (messages.isEmpty() && !isStreaming) {
                    item {
                        WelcomeMessage()
                    }
                }

                // Messages
                items(
                    items = messages,
                    key = { it.id }
                ) { message ->
                    MessageBubble(
                        message = message,
                        onRegenerate = if (message.role == MessageRole.ASSISTANT && message == messages.lastOrNull()) {
                            onRegenerateResponse
                        } else null
                    )
                }

                // Streaming message
                if (isStreaming && streamingContent.isNotEmpty()) {
                    item {
                        StreamingMessageBubble(
                            content = streamingContent,
                            reasoningContent = streamingReasoning,
                            toolsInProgress = toolsInProgress
                        )
                    }
                }

                // Tools in progress indicator
                if (isStreaming && toolsInProgress.isNotEmpty() && streamingContent.isEmpty()) {
                    item {
                        ToolsProgressIndicator(tools = toolsInProgress)
                    }
                }

                // Loading indicator
                if (uiState == ChatUiState.Sending) {
                    item {
                        LoadingIndicator()
                    }
                }
            }

            // Error banner
            AnimatedVisibility(
                visible = uiState is ChatUiState.Error,
                enter = slideInVertically() + fadeIn(),
                exit = slideOutVertically() + fadeOut()
            ) {
                (uiState as? ChatUiState.Error)?.let { error ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        color = colors.ErrorColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ErrorOutline,
                                contentDescription = null,
                                tint = colors.ErrorColor,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = error.message,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.ErrorColor,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }

            // Input area
            ChatInputArea(
                messageText = messageText,
                onMessageTextChange = { messageText = it },
                onSend = {
                    if (messageText.isNotBlank()) {
                        onSendMessage(messageText.trim())
                        messageText = ""
                        focusManager.clearFocus()
                    }
                },
                onCancel = onCancelStreaming,
                isStreaming = isStreaming,
                isSending = uiState == ChatUiState.Sending,
                enabled = selectedModel != null
            )
        }
    }

    // Model selector
    if (showModelSelector) {
        ModelSelectorBottomSheet(
            models = availableModels,
            selectedModel = selectedModel,
            onSelectModel = {
                onSelectModel(it)
                showModelSelector = false
            },
            onDismiss = { showModelSelector = false },
            onNavigateToModels = {
                showModelSelector = false
                onNavigateToModels()
            }
        )
    }

    // Clear confirmation
    if (showClearConfirm) {
        AlertDialog(
            onDismissRequest = { showClearConfirm = false },
            title = { Text("Clear Chat") },
            text = { Text("Are you sure you want to clear all messages in this conversation?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearChat()
                        showClearConfirm = false
                    }
                ) {
                    Text("Clear", color = colors.ErrorColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirm = false }) {
                    Text("Cancel")
                }
            },
            containerColor = colors.CardBackground
        )
    }
}

@Composable
private fun WelcomeMessage() {
    val colors = AppTheme.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            colors.AccentGold.copy(alpha = 0.3f),
                            colors.AccentPrimary.copy(alpha = 0.1f)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = colors.AccentGold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Hello! I'm Stormy",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = colors.TextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Your Vedic astrology assistant. Ask me anything about your birth chart, planetary periods, transits, compatibility, or remedies.",
            style = MaterialTheme.typography.bodyMedium,
            color = colors.TextMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Suggestion chips
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SuggestionChip(text = "What's my current dasha period?")
            SuggestionChip(text = "Analyze my birth chart")
            SuggestionChip(text = "What yogas are present in my chart?")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuggestionChip(text: String) {
    val colors = AppTheme.current

    SuggestionChip(
        onClick = { /* TODO: Implement suggestion click */ },
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodySmall
            )
        },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = colors.ChipBackground,
            labelColor = colors.TextSecondary
        ),
        border = null
    )
}

@Composable
private fun MessageBubble(
    message: ChatMessageModel,
    onRegenerate: (() -> Unit)?
) {
    val colors = AppTheme.current
    val isUser = message.role == MessageRole.USER
    var showReasoning by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Surface(
            color = if (isUser) colors.AccentPrimary else colors.CardBackground,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = if (isUser) 16.dp else 4.dp,
                bottomEnd = if (isUser) 4.dp else 16.dp
            ),
            modifier = Modifier.widthIn(max = 320.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // Error state
                if (message.errorMessage != null) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.ErrorOutline,
                            contentDescription = null,
                            tint = colors.ErrorColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = message.errorMessage,
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.ErrorColor
                        )
                    }
                } else {
                    Text(
                        text = message.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isUser) colors.ScreenBackground else colors.TextPrimary
                    )
                }

                // Reasoning toggle for assistant messages
                if (!isUser && message.reasoningContent != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.clickable { showReasoning = !showReasoning },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (showReasoning) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = colors.TextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (showReasoning) "Hide reasoning" else "Show reasoning",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.TextMuted
                        )
                    }

                    AnimatedVisibility(visible = showReasoning) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            color = colors.ChipBackground,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = message.reasoningContent,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.TextMuted,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }

                // Tools used
                if (!isUser && message.toolsUsed?.isNotEmpty() == true) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Build,
                            contentDescription = null,
                            tint = colors.TextSubtle,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = message.toolsUsed.joinToString(", "),
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.TextSubtle
                        )
                    }
                }
            }
        }

        // Regenerate button for last assistant message
        if (onRegenerate != null && !isUser) {
            TextButton(
                onClick = onRegenerate,
                modifier = Modifier.padding(start = 8.dp)
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

@Composable
private fun StreamingMessageBubble(
    content: String,
    reasoningContent: String,
    toolsInProgress: List<String>
) {
    val colors = AppTheme.current
    var showReasoning by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Surface(
            color = colors.CardBackground,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 4.dp,
                bottomEnd = 16.dp
            ),
            modifier = Modifier.widthIn(max = 320.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.TextPrimary
                )

                // Typing indicator
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    repeat(3) { index ->
                        val infiniteTransition = rememberInfiniteTransition(label = "typing_animation_$index")
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
                            label = "typing_dot_$index"
                        )
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(colors.TextMuted.copy(alpha = alpha))
                        )
                    }
                }

                // Reasoning toggle
                if (reasoningContent.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.clickable { showReasoning = !showReasoning },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (showReasoning) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = null,
                            tint = colors.TextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (showReasoning) "Hide reasoning" else "Show reasoning",
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.TextMuted
                        )
                    }

                    AnimatedVisibility(visible = showReasoning) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            color = colors.ChipBackground,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = reasoningContent,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.TextMuted,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ToolsProgressIndicator(tools: List<String>) {
    val colors = AppTheme.current

    Surface(
        color = colors.CardBackground,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.widthIn(max = 280.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(16.dp),
                strokeWidth = 2.dp,
                color = colors.AccentPrimary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Using tools...",
                    style = MaterialTheme.typography.labelMedium,
                    color = colors.TextPrimary
                )
                Text(
                    text = tools.joinToString(", "),
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.TextMuted
                )
            }
        }
    }
}

@Composable
private fun LoadingIndicator() {
    val colors = AppTheme.current

    Surface(
        color = colors.CardBackground,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.widthIn(max = 100.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            repeat(3) { index ->
                val infiniteTransition = rememberInfiniteTransition(label = "loading_animation_$index")
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
                    label = "loading_dot_$index"
                )
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(colors.TextMuted.copy(alpha = alpha))
                )
            }
        }
    }
}

@Composable
private fun ChatInputArea(
    messageText: String,
    onMessageTextChange: (String) -> Unit,
    onSend: () -> Unit,
    onCancel: () -> Unit,
    isStreaming: Boolean,
    isSending: Boolean,
    enabled: Boolean
) {
    val colors = AppTheme.current
    val focusRequester = remember { FocusRequester() }

    Surface(
        color = colors.CardBackground,
        tonalElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = onMessageTextChange,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(focusRequester),
                placeholder = {
                    Text(
                        text = "Ask Stormy...",
                        color = colors.TextSubtle
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.AccentPrimary,
                    unfocusedBorderColor = colors.BorderColor,
                    focusedContainerColor = colors.InputBackground,
                    unfocusedContainerColor = colors.InputBackground,
                    cursorColor = colors.AccentPrimary
                ),
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Send
                ),
                keyboardActions = KeyboardActions(
                    onSend = { if (messageText.isNotBlank() && enabled && !isStreaming) onSend() }
                ),
                maxLines = 4,
                enabled = enabled && !isSending
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (isStreaming) {
                // Cancel button
                IconButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(colors.ErrorColor)
                ) {
                    Icon(
                        imageVector = Icons.Default.Stop,
                        contentDescription = "Stop",
                        tint = Color.White
                    )
                }
            } else {
                // Send button
                IconButton(
                    onClick = onSend,
                    enabled = messageText.isNotBlank() && enabled && !isSending,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            if (messageText.isNotBlank() && enabled) colors.AccentPrimary
                            else colors.ChipBackground
                        )
                ) {
                    if (isSending) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = colors.TextMuted
                        )
                    } else {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = if (messageText.isNotBlank() && enabled) colors.ScreenBackground
                            else colors.TextMuted
                        )
                    }
                }
            }
        }
    }
}

// ============================================
// MODEL SELECTOR COMPONENTS
// ============================================

@Composable
private fun ModelSelectorRow(
    selectedModel: AiModel?,
    onClick: () -> Unit,
    onNavigateToModels: () -> Unit
) {
    val colors = AppTheme.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        color = colors.CardBackground,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Psychology,
                contentDescription = null,
                tint = colors.AccentPrimary,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "AI Model",
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.TextMuted
                )
                Text(
                    text = selectedModel?.displayName ?: "Select a model",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = colors.TextPrimary
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = colors.TextMuted
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModelSelectorBottomSheet(
    models: List<AiModel>,
    selectedModel: AiModel?,
    onSelectModel: (AiModel) -> Unit,
    onDismiss: () -> Unit,
    onNavigateToModels: () -> Unit
) {
    val colors = AppTheme.current
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = colors.BottomSheetBackground,
        dragHandle = {
            Surface(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(32.dp)
                    .height(4.dp),
                color = colors.BottomSheetHandle,
                shape = RoundedCornerShape(2.dp)
            ) {}
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "Select AI Model",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = colors.TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (models.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Psychology,
                        contentDescription = null,
                        tint = colors.TextMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No models available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.TextMuted
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = onNavigateToModels) {
                        Text("Configure Models")
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Group by provider
                    val groupedModels = models.groupBy { it.providerId }

                    groupedModels.forEach { (providerId, providerModels) ->
                        item {
                            Text(
                                text = providerId.replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.labelMedium,
                                color = colors.TextMuted,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        items(providerModels) { model ->
                            val isSelected = model.id == selectedModel?.id &&
                                    model.providerId == selectedModel?.providerId

                            Surface(
                                onClick = { onSelectModel(model) },
                                color = if (isSelected) colors.ChipBackgroundSelected else colors.CardBackground,
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = model.displayName,
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                                            color = colors.TextPrimary
                                        )
                                        model.description?.let { desc ->
                                            Text(
                                                text = desc,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = colors.TextMuted,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }

                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = colors.AccentPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = onNavigateToModels,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Manage AI Models")
                }
            }
        }
    }
}

// ============================================
// UTILITIES
// ============================================

private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp

    return when {
        diff < 60_000 -> "Just now"
        diff < 3600_000 -> "${diff / 60_000}m ago"
        diff < 86400_000 -> "${diff / 3600_000}h ago"
        diff < 604800_000 -> "${diff / 86400_000}d ago"
        else -> SimpleDateFormat("MMM d", Locale.getDefault()).format(Date(timestamp))
    }
}
