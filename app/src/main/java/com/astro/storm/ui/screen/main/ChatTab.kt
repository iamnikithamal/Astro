package com.astro.storm.ui.screen.main

import androidx.compose.animation.*
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.StartOffset
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.compose.ui.draw.rotate
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
import androidx.compose.ui.unit.sp
import com.astro.storm.data.ai.provider.AiModel
import com.astro.storm.data.ai.provider.MessageRole
import com.astro.storm.data.local.chat.ChatConversation
import com.astro.storm.data.local.chat.ChatMessageModel
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.repository.SavedChart
import com.astro.storm.ui.components.ContentCleaner
import com.astro.storm.ui.components.MarkdownText
import com.astro.storm.ui.components.agentic.AgenticMessageCard
import com.astro.storm.ui.components.agentic.AskUserOption
import com.astro.storm.ui.components.agentic.CompletedAiMessageCard
import com.astro.storm.ui.components.agentic.CompletedSectionedMessageCard
import com.astro.storm.ui.components.agentic.SectionedMessageCard
import com.astro.storm.ui.components.agentic.SectionedMessageState
import com.astro.storm.ui.components.agentic.ToolDisplayUtils
import com.astro.storm.ui.theme.AppTheme
import com.astro.storm.ui.viewmodel.AiStatus
import com.astro.storm.ui.viewmodel.ChatUiState
import com.astro.storm.ui.viewmodel.ChatViewModel
import com.astro.storm.ui.viewmodel.StreamingMessageState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * Chat Tab - Main entry point for AI chat feature
 *
 * Shows conversations list with option to create new or open existing chats.
 * When a conversation is clicked, navigates to the ChatScreen as a separate destination.
 */
@Composable
fun ChatTab(
    viewModel: ChatViewModel,
    currentChart: VedicChart?,
    savedCharts: List<SavedChart>,
    selectedChartId: Long?,
    onNavigateToModels: () -> Unit,
    onNavigateToChat: (Long?) -> Unit,  // null for new chat, Long for existing
    isFullScreen: Boolean = false
) {
    val conversations by viewModel.conversations.collectAsState()
    val availableModels by viewModel.availableModels.collectAsState()
    val selectedModel by viewModel.selectedModel.collectAsState()

    val colors = AppTheme.current

    // Show conversations list
    ConversationsListScreen(
        conversations = conversations,
        onConversationClick = { conversation ->
            onNavigateToChat(conversation.id)
        },
        onNewChat = {
            onNavigateToChat(null)  // Navigate to new chat
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
    var conversationToDelete by remember { mutableStateOf<ChatConversation?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.ScreenBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Model selector row removed - users can select model in individual chat screens

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
                    contentDescription = stringResource(StringKeyDosha.CHAT_NEW)
                )
            }
        }
    }

    // Delete confirmation dialog
    conversationToDelete?.let { conversation ->
        AlertDialog(
            onDismissRequest = { conversationToDelete = null },
            title = { Text(stringResource(StringKeyDosha.CHAT_DELETE)) },
            text = { Text(stringResource(StringKeyDosha.CHAT_DELETE_CONFIRM, conversation.title)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteConversation(conversation)
                        conversationToDelete = null
                    }
                ) {
                    Text(stringResource(StringKeyDosha.CHAT_DELETE_BTN), color = colors.ErrorColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { conversationToDelete = null }) {
                    Text(stringResource(StringKeyDosha.CHAT_CANCEL_BTN))
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
            text = stringResource(StringKeyDosha.STORMY_MEET),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = colors.TextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(StringKeyDosha.STORMY_SUBTITLE),
            style = MaterialTheme.typography.bodyLarge,
            color = colors.TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(StringKeyDosha.STORMY_INTRO),
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
                Text(stringResource(StringKeyDosha.STORMY_START_CHAT))
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
                Text(
                    text = stringResource(StringKeyDosha.STORMY_CONFIGURE_MODELS),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(StringKeyDosha.STORMY_ENABLE_MODELS),
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
                        text = stringResource(StringKeyDosha.CHAT_MESSAGES_COUNT, conversation.messageCount),
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.TextSubtle
                    )
                }
            }

            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = stringResource(StringKeyDosha.CHAT_MORE_OPTIONS),
                        tint = colors.TextMuted
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(StringKeyDosha.CHAT_ARCHIVE)) },
                        onClick = {
                            showMenu = false
                            onArchive()
                        },
                        leadingIcon = {
                            Icon(Icons.Outlined.Archive, contentDescription = null)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(StringKeyDosha.CHAT_DELETE_BTN), color = colors.ErrorColor) },
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
fun ChatScreen(
    messages: List<ChatMessageModel>,
    streamingContent: String,
    streamingReasoning: String,
    isStreaming: Boolean,
    toolsInProgress: List<String>,
    aiStatus: AiStatus,
    uiState: ChatUiState,
    selectedModel: AiModel?,
    availableModels: List<AiModel>,
    thinkingEnabled: Boolean = true,
    webSearchEnabled: Boolean = false,
    streamingMessageState: StreamingMessageState? = null,
    streamingMessageId: Long? = null,
    sectionedMessageState: SectionedMessageState? = null,
    onSendMessage: (String) -> Unit,
    onCancelStreaming: () -> Unit,
    onRegenerateResponse: () -> Unit,
    onSelectModel: (AiModel) -> Unit,
    onSetThinkingEnabled: (Boolean) -> Unit = {},
    onSetWebSearchEnabled: (Boolean) -> Unit = {},
    onAskUserResponse: (sectionId: String, response: String) -> Unit = { _, _ -> },
    onAskUserOptionSelect: (sectionId: String, option: AskUserOption) -> Unit = { _, _ -> },
    onToggleSection: (sectionId: String) -> Unit = {},
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
    var showModelOptions by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    // Auto-scroll to bottom on new messages or when streaming status changes
    LaunchedEffect(messages.size, streamingContent, aiStatus) {
        if (messages.isNotEmpty() || isStreaming) {
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
                            text = stringResource(StringKeyDosha.STORMY_TITLE),
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
                            contentDescription = stringResource(StringKeyDosha.CHAT_BACK),
                            tint = colors.TextPrimary
                        )
                    }
                },
                actions = {
                    // Model options button (only for models with thinking/websearch support)
                    if (selectedModel?.supportsThinking == true || selectedModel?.supportsWebSearch == true) {
                        IconButton(onClick = { showModelOptions = true }) {
                            Icon(
                                imageVector = Icons.Outlined.Tune,
                                contentDescription = stringResource(StringKeyDosha.CHAT_MODEL_OPTIONS),
                                tint = colors.AccentPrimary
                            )
                        }
                    }
                    IconButton(onClick = { showModelSelector = true }) {
                        Icon(
                            imageVector = Icons.Outlined.Psychology,
                            contentDescription = stringResource(StringKeyDosha.CHAT_CHANGE_MODEL),
                            tint = colors.TextSecondary
                        )
                    }
                    IconButton(onClick = { showClearConfirm = true }) {
                        Icon(
                            imageVector = Icons.Outlined.DeleteSweep,
                            contentDescription = stringResource(StringKeyDosha.CHAT_CLEAR),
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
            // Filter out the streaming message from the database list to avoid duplicates
            // The streaming message is rendered separately below using streamingMessageState
            val displayMessages = remember(messages, streamingMessageId, isStreaming) {
                if (streamingMessageId != null || isStreaming) {
                    // Exclude the streaming message by ID (most reliable) or by isStreaming flag
                    messages.filter { msg ->
                        msg.id != streamingMessageId && !msg.isStreaming
                    }
                } else {
                    messages
                }
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Welcome message if empty
                if (displayMessages.isEmpty() && !isStreaming) {
                    item {
                        WelcomeMessage(
                            onSuggestionClick = { suggestion ->
                                messageText = suggestion
                            }
                        )
                    }
                }

                // Messages (excluding streaming ones to avoid duplicates)
                items(
                    items = displayMessages,
                    key = { it.id }
                ) { message ->
                    MessageBubble(
                        message = message,
                        onRegenerate = if (message.role == MessageRole.ASSISTANT && message == displayMessages.lastOrNull { it.role == MessageRole.ASSISTANT } && !isStreaming) {
                            onRegenerateResponse
                        } else null
                    )
                }

                // Streaming message with dynamic sectioned agentic UI
                // Uses SectionedMessageCard for professional IDE-style layout
                // Falls back to AgenticMessageCard if no sectioned state available
                if (isStreaming || streamingMessageState != null || sectionedMessageState != null) {
                    item(key = "streaming_message") {
                        if (sectionedMessageState != null) {
                            // Use new sectioned layout when available
                            SectionedMessageCard(
                                sectionedState = sectionedMessageState,
                                aiStatus = aiStatus,
                                onAskUserResponse = onAskUserResponse,
                                onAskUserOptionSelect = onAskUserOptionSelect,
                                onToggleSection = onToggleSection
                            )
                        } else {
                            // Fallback to legacy AgenticMessageCard
                            AgenticMessageCard(
                                streamingState = streamingMessageState,
                                streamingContent = streamingContent,
                                streamingReasoning = streamingReasoning,
                                aiStatus = aiStatus
                            )
                        }
                    }
                }

                // Sending indicator (before streaming starts)
                if (uiState == ChatUiState.Sending && !isStreaming && streamingMessageState == null) {
                    item(key = "sending_indicator") {
                        AiStatusIndicator(aiStatus = AiStatus.Thinking)
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
            title = { Text(stringResource(StringKeyDosha.CHAT_CLEAR)) },
            text = { Text(stringResource(StringKeyDosha.CHAT_CLEAR_CONFIRM)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        onClearChat()
                        showClearConfirm = false
                    }
                ) {
                    Text(stringResource(StringKeyDosha.CHAT_CLEAR_BTN), color = colors.ErrorColor)
                }
            },
            dismissButton = {
                TextButton(onClick = { showClearConfirm = false }) {
                    Text(stringResource(StringKeyDosha.CHAT_CANCEL_BTN))
                }
            },
            containerColor = colors.CardBackground
        )
    }

    // Model options dialog
    if (showModelOptions && selectedModel != null) {
        AlertDialog(
            onDismissRequest = { showModelOptions = false },
            title = {
                Text(
                    text = stringResource(StringKeyDosha.MODEL_OPTIONS_TITLE),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = selectedModel!!.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.TextMuted
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Thinking toggle
                    if (selectedModel!!.supportsThinking) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(colors.ChipBackground)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = stringResource(StringKeyDosha.MODEL_THINKING_MODE),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = colors.TextPrimary
                                )
                                Text(
                                    text = stringResource(StringKeyDosha.MODEL_THINKING_DESC),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = colors.TextMuted
                                )
                            }
                            Switch(
                                checked = thinkingEnabled,
                                onCheckedChange = onSetThinkingEnabled,
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = colors.AccentPrimary,
                                    checkedTrackColor = colors.AccentPrimary.copy(alpha = 0.5f)
                                )
                            )
                        }
                    }

                    // Web search toggle
                    if (selectedModel!!.supportsWebSearch) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(colors.ChipBackground)
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = stringResource(StringKeyDosha.MODEL_WEB_SEARCH),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = colors.TextPrimary
                                )
                                Text(
                                    text = stringResource(StringKeyDosha.MODEL_WEB_SEARCH_DESC),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = colors.TextMuted
                                )
                            }
                            Switch(
                                checked = webSearchEnabled,
                                onCheckedChange = onSetWebSearchEnabled,
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = colors.AccentPrimary,
                                    checkedTrackColor = colors.AccentPrimary.copy(alpha = 0.5f)
                                )
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showModelOptions = false }) {
                    Text(stringResource(StringKeyDosha.MODEL_DONE), color = colors.AccentPrimary)
                }
            },
            containerColor = colors.CardBackground
        )
    }
}

@Composable
private fun WelcomeMessage(
    onSuggestionClick: (String) -> Unit = {}
) {
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
            text = stringResource(StringKeyDosha.STORMY_HELLO),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = colors.TextPrimary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(StringKeyDosha.STORMY_HELLO_DESC),
            style = MaterialTheme.typography.bodyMedium,
            color = colors.TextMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Suggestion chips - clickable to populate input field
        val suggestionDasha = stringResource(StringKeyDosha.CHAT_SUGGESTION_DASHA)
        val suggestionChart = stringResource(StringKeyDosha.CHAT_SUGGESTION_CHART)
        val suggestionYogas = stringResource(StringKeyDosha.CHAT_SUGGESTION_YOGAS)

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SuggestionChip(
                text = suggestionDasha,
                onClick = { onSuggestionClick(suggestionDasha) }
            )
            SuggestionChip(
                text = suggestionChart,
                onClick = { onSuggestionClick(suggestionChart) }
            )
            SuggestionChip(
                text = suggestionYogas,
                onClick = { onSuggestionClick(suggestionYogas) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SuggestionChip(
    text: String,
    onClick: () -> Unit = {}
) {
    val colors = AppTheme.current

    SuggestionChip(
        onClick = onClick,
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

/**
 * Message display component - Uses different layouts for user vs AI messages
 *
 * User messages: Traditional bubble style (right-aligned, colored background)
 * AI messages: Modern IDE-style layout (full width, no bubble, professional look)
 *
 * This approach inspired by agentic coding IDEs like Cursor, Windsurf, and Google Antigravity
 * but adapted for Vedic astrology assistant context.
 */
@Composable
private fun MessageBubble(
    message: ChatMessageModel,
    onRegenerate: (() -> Unit)?
) {
    val isUser = message.role == MessageRole.USER

    if (isUser) {
        // User messages keep the bubble style
        UserMessageBubble(message = message)
    } else {
        // AI messages use the new professional layout
        // Use CompletedSectionedMessageCard when sectionsJson is available for full agentic layout
        // Fall back to CompletedAiMessageCard for legacy messages without section data
        if (message.sectionsJson != null) {
            CompletedSectionedMessageCard(
                content = message.content,
                reasoning = message.reasoningContent,
                toolsUsed = message.toolsUsed,
                sectionsJson = message.sectionsJson,
                errorMessage = message.errorMessage,
                onRegenerate = onRegenerate
            )
        } else {
            CompletedAiMessageCard(
                content = message.content,
                reasoning = message.reasoningContent,
                toolsUsed = message.toolsUsed,
                errorMessage = message.errorMessage,
                onRegenerate = onRegenerate
            )
        }
    }
}

/**
 * User message bubble - Maintains the traditional bubble style for user messages
 */
@Composable
private fun UserMessageBubble(message: ChatMessageModel) {
    val colors = AppTheme.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Surface(
            color = colors.AccentPrimary,
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomStart = 16.dp,
                bottomEnd = 4.dp
            ),
            modifier = Modifier.widthIn(max = 320.dp)
        ) {
            Text(
                text = message.content,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.ScreenBackground,
                modifier = Modifier.padding(12.dp)
            )
        }
    }
}

// Legacy StreamingMessageBubble removed - replaced by SectionedMessageCard and AgenticMessageCard
// for professional IDE-style AI message display

/**
 * Displays the current AI processing status with appropriate icons and messages.
 * Shows different states: thinking, reasoning, calling tools, typing, etc.
 */
@Composable
private fun AiStatusIndicator(aiStatus: AiStatus) {
    val colors = AppTheme.current

    // Determine status text and icon based on current AI status
    val (statusText, statusIcon) = when (aiStatus) {
        is AiStatus.Idle -> return // Don't show anything for idle
        is AiStatus.Thinking -> stringResource(StringKeyDosha.STORMY_THINKING) to Icons.Outlined.Psychology
        is AiStatus.Reasoning -> stringResource(StringKeyDosha.STORMY_REASONING) to Icons.Outlined.Lightbulb
        is AiStatus.CallingTool -> stringResource(StringKeyDosha.STORMY_CALLING_TOOL, ToolDisplayUtils.formatToolName(aiStatus.toolName)) to Icons.Outlined.Build
        is AiStatus.ExecutingTools -> stringResource(StringKeyDosha.STORMY_USING_TOOLS, aiStatus.tools.joinToString(", ") { ToolDisplayUtils.formatToolName(it) }) to Icons.Outlined.Build
        is AiStatus.Typing -> stringResource(StringKeyDosha.STORMY_TYPING) to Icons.Outlined.Edit
        is AiStatus.Complete -> return // Don't show anything for complete
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Surface(
            color = colors.CardBackground,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Animated loading indicator
                Box(
                    modifier = Modifier.size(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        strokeWidth = 2.dp,
                        color = colors.AccentPrimary
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                // Status icon
                Icon(
                    imageVector = statusIcon,
                    contentDescription = null,
                    tint = colors.AccentPrimary,
                    modifier = Modifier.size(16.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Status text
                Text(
                    text = statusText,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.TextSecondary
                )
            }
        }
    }
}

// Tool name formatting now uses centralized ToolDisplayUtils.formatToolName()

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
        tonalElevation = 2.dp,
        modifier = Modifier.imePadding() // Handle keyboard overlap
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding() // Padding for navigation bar
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
                        text = stringResource(StringKeyDosha.STORMY_ASK_PLACEHOLDER),
                        color = colors.TextSubtle,
                        fontSize = 14.sp
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    color = colors.TextPrimary
                ),
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
                        contentDescription = stringResource(StringKeyDosha.CHAT_STOP),
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
                            contentDescription = stringResource(StringKeyDosha.CHAT_SEND),
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
                    text = stringResource(StringKeyDosha.MODEL_AI_LABEL),
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.TextMuted
                )
                Text(
                    text = selectedModel?.displayName ?: stringResource(StringKeyDosha.MODEL_SELECT_PROMPT),
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
                text = stringResource(StringKeyDosha.MODEL_SELECT_TITLE),
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
                        text = stringResource(StringKeyDosha.MODEL_NONE_AVAILABLE),
                        style = MaterialTheme.typography.bodyMedium,
                        color = colors.TextMuted
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = onNavigateToModels) {
                        Text(stringResource(StringKeyDosha.MODEL_CONFIGURE))
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
                    Text(stringResource(StringKeyDosha.MODEL_MANAGE))
                }
            }
        }
    }
}

// ============================================
// UTILITIES
// ============================================

@Composable
@androidx.compose.runtime.ReadOnlyComposable
private fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    val language = LocalLanguage.current

    return when {
        diff < 60_000 -> stringResource(StringKeyDosha.CHAT_JUST_NOW)
        diff < 3600_000 -> stringResource(StringKeyDosha.CHAT_MINUTES_AGO, (diff / 60_000).toInt())
        diff < 86400_000 -> stringResource(StringKeyDosha.CHAT_HOURS_AGO, (diff / 3600_000).toInt())
        diff < 604800_000 -> stringResource(StringKeyDosha.CHAT_DAYS_AGO, (diff / 86400_000).toInt())
        else -> SimpleDateFormat("MMM d", Locale.getDefault()).format(Date(timestamp))
    }
}
