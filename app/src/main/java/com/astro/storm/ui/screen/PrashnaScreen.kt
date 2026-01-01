package com.astro.storm.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.ChildFriendly
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Flight
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.LocalHospital
import androidx.compose.material.icons.outlined.MonetizationOn
import androidx.compose.material.icons.outlined.MyLocation
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.currentLanguage
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.ai.agent.AgentResponse
import com.astro.storm.data.ai.agent.StormyAgent
import com.astro.storm.data.ai.provider.AiModel
import com.astro.storm.data.ai.provider.AiProviderRegistry
import com.astro.storm.data.ai.provider.ChatMessage
import com.astro.storm.data.ai.provider.MessageRole
import com.astro.storm.data.repository.SavedChart
import com.astro.storm.ephemeris.PrashnaCalculator
import com.astro.storm.ui.components.ContentCleaner
import com.astro.storm.ui.components.MarkdownText
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.Locale
import com.astro.storm.data.localization.DateFormat
import com.astro.storm.data.localization.formatLocalized
import com.astro.storm.data.localization.localized
import com.astro.storm.data.localization.localizedName

/**
 * UI State for Prashna Screen
 */
private sealed interface PrashnaUiState {
    data object Initial : PrashnaUiState
    data object Loading : PrashnaUiState
    data class Success(val result: PrashnaCalculator.PrashnaResult) : PrashnaUiState
    data class Error(val message: String) : PrashnaUiState
}

// PrashnaFormatters removed in favor of localized extensions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrashnaScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    val focusManager = LocalFocusManager.current
    val currentLang = currentLanguage()

    var question by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(PrashnaCalculator.PrashnaCategory.YES_NO) }
    var uiState by remember { mutableStateOf<PrashnaUiState>(PrashnaUiState.Initial) }

    // Location from chart or defaults
    val latitude = remember(chart) { chart?.birthData?.latitude ?: 28.6139 }
    val longitude = remember(chart) { chart?.birthData?.longitude ?: 77.2090 }
    val timezone = remember(chart) { chart?.birthData?.timezone ?: "Asia/Kolkata" }
    val locationName = remember(chart) { chart?.birthData?.location ?: "New Delhi, India" }

    val calculator = remember(context) { PrashnaCalculator(context) }
    val analyzeErrorMessage = stringResource(StringKeyAnalysis.PRASHNA_ANALYZE_ERROR)

    DisposableEffect(calculator) {
        onDispose { calculator.close() }
    }

    val performAnalysis: () -> Unit = remember(calculator, question, selectedCategory, latitude, longitude, timezone, analyzeErrorMessage, currentLang) {
        {
            if (question.isNotBlank()) {
                scope.launch {
                    uiState = PrashnaUiState.Loading
                    try {
                        withContext(Dispatchers.IO) {
                            val result = calculator.generatePrashnaChart(
                                question = question.trim(),
                                category = selectedCategory,
                                latitude = latitude,
                                longitude = longitude,
                                timezone = timezone,
                                language = currentLang
                            )
                            uiState = PrashnaUiState.Success(result)
                        }
                    } catch (e: CancellationException) {
                        throw e
                    } catch (e: Exception) {
                        uiState = PrashnaUiState.Error(e.message ?: analyzeErrorMessage)
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(StringKeyAnalysis.PRASHNA_KUNDALI),
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onBack()
                        }
                    ) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(StringKeyMatch.NAV_BACK),
                            tint = AppTheme.TextPrimary
                        )
                    }
                },
                actions = {
                    if (uiState is PrashnaUiState.Success) {
                        IconButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                question = ""
                                uiState = PrashnaUiState.Initial
                            }
                        ) {
                            Icon(
                                Icons.Filled.Refresh,
                                contentDescription = stringResource(StringKeyAnalysis.PRASHNA_NEW_QUESTION),
                                tint = AppTheme.TextPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.ScreenBackground
                )
            )
        },
        containerColor = AppTheme.ScreenBackground
    ) { paddingValues ->
        AnimatedContent(
            targetState = uiState,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith fadeOut(animationSpec = tween(300))
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            label = "prashna_state"
        ) { state ->
            when (state) {
                is PrashnaUiState.Initial -> {
                    PrashnaInputContent(
                        question = question,
                        onQuestionChange = { question = it },
                        selectedCategory = selectedCategory,
                        onCategoryChange = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            selectedCategory = it
                        },
                        locationName = locationName,
                        onAnalyze = {
                            focusManager.clearFocus()
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            performAnalysis()
                        }
                    )
                }
                is PrashnaUiState.Loading -> {
                    PrashnaLoadingContent()
                }
                is PrashnaUiState.Success -> {
                    PrashnaResultContent(result = state.result)
                }
                is PrashnaUiState.Error -> {
                    PrashnaErrorContent(
                        message = state.message,
                        onRetry = performAnalysis
                    )
                }
            }
        }
    }
}

@Composable
private fun PrashnaInputContent(
    question: String,
    onQuestionChange: (String) -> Unit,
    selectedCategory: PrashnaCalculator.PrashnaCategory,
    onCategoryChange: (PrashnaCalculator.PrashnaCategory) -> Unit,
    locationName: String,
    onAnalyze: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item(key = "header") {
            PrashnaHeaderCard()
        }

        item(key = "question_input") {
            QuestionInputCard(
                question = question,
                onQuestionChange = onQuestionChange,
                onSubmit = onAnalyze
            )
        }

        item(key = "category_selector") {
            CategorySelectorCard(
                selectedCategory = selectedCategory,
                onCategoryChange = onCategoryChange
            )
        }

        item(key = "location_info") {
            LocationInfoCard(locationName = locationName)
        }

        item(key = "analyze_button") {
            AnalyzeButton(
                enabled = question.isNotBlank(),
                onClick = onAnalyze
            )
        }

        item(key = "instructions") {
            PrashnaInstructionsCard()
        }
    }
}

@Composable
private fun PrashnaHeaderCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.AccentTeal.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                AppTheme.AccentTeal.copy(alpha = 0.3f),
                                AppTheme.AccentTeal.copy(alpha = 0.1f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.HelpOutline,
                    contentDescription = null,
                    tint = AppTheme.AccentTeal,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                stringResource(StringKeyAnalysis.PRASHNA_KUNDALI),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                stringResource(StringKeyAnalysis.PRASHNA_HORARY),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.AccentTeal
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                stringResource(StringKeyAnalysis.PRASHNA_INTRO),
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun QuestionInputCard(
    question: String,
    onQuestionChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.QuestionAnswer,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyAnalysis.PRASHNA_YOUR_QUESTION),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = question,
                onValueChange = onQuestionChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        stringResource(StringKeyAnalysis.PRASHNA_QUESTION_HINT),
                        color = AppTheme.TextSubtle
                    )
                },
                minLines = 3,
                maxLines = 5,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { if (question.isNotBlank()) onSubmit() }
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AppTheme.AccentPrimary,
                    unfocusedBorderColor = AppTheme.DividerColor,
                    cursorColor = AppTheme.AccentPrimary,
                    focusedTextColor = AppTheme.TextPrimary,
                    unfocusedTextColor = AppTheme.TextPrimary,
                    focusedContainerColor = AppTheme.CardBackgroundElevated,
                    unfocusedContainerColor = AppTheme.CardBackgroundElevated
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                stringResource(StringKeyAnalysis.PRASHNA_QUESTION_HELP),
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategorySelectorCard(
    selectedCategory: PrashnaCalculator.PrashnaCategory,
    onCategoryChange: (PrashnaCalculator.PrashnaCategory) -> Unit
) {
    val categories = remember { PrashnaCalculator.PrashnaCategory.entries.toList() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Star,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyAnalysis.PRASHNA_CATEGORY),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 2.dp)
            ) {
                items(categories, key = { it.name }) { category ->
                    val icon = getCategoryIcon(category)
                    FilterChip(
                        selected = category == selectedCategory,
                        onClick = { onCategoryChange(category) },
                        label = {
                            Text(
                                category.getLocalizedName(currentLanguage()),
                                style = MaterialTheme.typography.labelMedium
                            )
                        },
                        leadingIcon = {
                            Icon(
                                icon,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AppTheme.AccentTeal.copy(alpha = 0.2f),
                            selectedLabelColor = AppTheme.AccentTeal,
                            selectedLeadingIconColor = AppTheme.AccentTeal,
                            containerColor = AppTheme.ChipBackground,
                            labelColor = AppTheme.TextSecondary,
                            iconColor = AppTheme.TextMuted
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = category == selectedCategory,
                            borderColor = Color.Transparent,
                            selectedBorderColor = AppTheme.AccentTeal.copy(alpha = 0.5f)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                color = AppTheme.CardBackgroundElevated,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        getCategoryIcon(selectedCategory),
                        contentDescription = null,
                        tint = AppTheme.AccentTeal,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            selectedCategory.getLocalizedName(currentLanguage()),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            selectedCategory.getLocalizedDescription(currentLanguage()),
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationInfoCard(locationName: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackgroundElevated),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(AppTheme.InfoColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.MyLocation,
                    contentDescription = null,
                    tint = AppTheme.InfoColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stringResource(StringKeyAnalysis.PRASHNA_LOCATION),
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextMuted
                )
                Text(
                    locationName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
            }
            Surface(
                color = AppTheme.InfoColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = AppTheme.InfoColor
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        stringResource(StringKeyAnalysis.PRASHNA_TIME_NOW),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.InfoColor
                    )
                }
            }
        }
    }
}

@Composable
private fun AnalyzeButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.AccentTeal,
            disabledContainerColor = AppTheme.AccentTeal.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(12.dp),
        enabled = enabled
    ) {
        Icon(
            Icons.Outlined.Psychology,
            contentDescription = null,
            modifier = Modifier.size(22.dp),
            tint = if (enabled) AppTheme.ButtonText else AppTheme.ButtonText.copy(alpha = 0.5f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            stringResource(StringKeyAnalysis.PRASHNA_ANALYZE),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            color = if (enabled) AppTheme.ButtonText else AppTheme.ButtonText.copy(alpha = 0.5f)
        )
    }
}

@Composable
private fun PrashnaInstructionsCard() {
    var isExpanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(300),
        label = "expand_rotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = null,
                        tint = AppTheme.InfoColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        stringResource(StringKeyAnalysis.PRASHNA_ABOUT),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                Icon(
                    Icons.Filled.ExpandMore,
                    contentDescription = if (isExpanded) stringResource(StringKeyMatch.MISC_COLLAPSE) else stringResource(StringKeyMatch.MISC_EXPAND),
                    tint = AppTheme.TextMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotationAngle)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    val instructions = listOf(
                        stringResource(StringKeyAnalysis.PRASHNA_INST_1),
                        stringResource(StringKeyAnalysis.PRASHNA_INST_2),
                        stringResource(StringKeyAnalysis.PRASHNA_INST_3),
                        stringResource(StringKeyAnalysis.PRASHNA_INST_4),
                        stringResource(StringKeyAnalysis.PRASHNA_INST_5)
                    )

                    instructions.forEachIndexed { index, instruction ->
                        Row(
                            modifier = Modifier.padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Surface(
                                color = AppTheme.InfoColor.copy(alpha = 0.2f),
                                shape = CircleShape,
                                modifier = Modifier.size(20.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        (index + 1).localized(),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = AppTheme.InfoColor
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                instruction,
                                style = MaterialTheme.typography.bodySmall,
                                color = AppTheme.TextSecondary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PrashnaLoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            CircularProgressIndicator(
                color = AppTheme.AccentTeal,
                strokeWidth = 4.dp,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                stringResource(StringKeyAnalysis.PRASHNA_ANALYZING),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(StringKeyAnalysis.PRASHNA_CALCULATING),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PrashnaErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                Icons.Outlined.ErrorOutline,
                contentDescription = null,
                tint = AppTheme.ErrorColor,
                modifier = Modifier.size(72.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                stringResource(StringKeyAnalysis.PRASHNA_ANALYSIS_FAILED),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                message,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = AppTheme.AccentTeal)
            ) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(StringKey.BTN_TRY_AGAIN), color = AppTheme.ButtonText)
            }
        }
    }
}

@Composable
private fun PrashnaResultContent(result: PrashnaCalculator.PrashnaResult) {
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        item(key = "verdict") {
            VerdictCard(result = result)
        }

        item(key = "question_summary") {
            QuestionSummaryCard(result = result)
        }

        item(key = "moon_analysis") {
            MoonAnalysisCard(moonAnalysis = result.moonAnalysis)
        }

        item(key = "lagna_analysis") {
            LagnaAnalysisCard(lagnaAnalysis = result.lagnaAnalysis)
        }

        if (result.timingPrediction.willEventOccur) {
            item(key = "timing") {
                TimingPredictionCard(timing = result.timingPrediction)
            }
        }

        if (result.specialYogas.isNotEmpty()) {
            item(key = "yogas") {
                SpecialYogasCard(yogas = result.specialYogas)
            }
        }

        item(key = "factors") {
            FactorsCard(judgment = result.judgment)
        }

        if (result.recommendations.isNotEmpty()) {
            item(key = "recommendations") {
                RecommendationsCard(recommendations = result.recommendations)
            }
        }

        // AI Insight Card - Ask Stormy for deeper interpretation
        item(key = "ai_insight") {
            AiInsightCard(result = result)
        }
    }
}

@Composable
private fun VerdictCard(result: PrashnaCalculator.PrashnaResult) {
    val verdictColor = getVerdictColor(result.judgment.verdict)
    val verdictIcon = getVerdictIcon(result.judgment.verdict)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            verdictColor.copy(alpha = 0.15f),
                            Color.Transparent
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(verdictColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        verdictIcon,
                        contentDescription = null,
                        tint = verdictColor,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    result.judgment.verdict.getLocalizedName(currentLanguage()),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = verdictColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ConfidenceBadge(confidence = result.confidence)
                    Spacer(modifier = Modifier.width(12.dp))
                    CertaintyBadge(certainty = result.judgment.certaintyLevel)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    result.judgment.primaryReason,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Score indicator
                ScoreIndicator(score = result.judgment.overallScore)
            }
        }
    }
}

@Composable
private fun ConfidenceBadge(confidence: Int) {
    val color = when {
        confidence >= 70 -> AppTheme.SuccessColor
        confidence >= 50 -> AppTheme.WarningColor
        else -> AppTheme.TextMuted
    }

    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            stringResource(StringKeyAnalysis.PRASHNA_CONFIDENCE, confidence),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = color,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun CertaintyBadge(certainty: PrashnaCalculator.CertaintyLevel) {
    Surface(
        color = AppTheme.InfoColor.copy(alpha = 0.15f),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            certainty.getLocalizedName(currentLanguage()),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = AppTheme.InfoColor,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun ScoreIndicator(score: Int) {
    val normalizedScore = ((score + 100) / 2f) / 100f // Convert -100 to +100 to 0 to 1
    val scoreColor = when {
        score >= 50 -> AppTheme.SuccessColor
        score >= 20 -> Color(0xFF8BC34A)
        score >= -20 -> AppTheme.WarningColor
        score >= -50 -> Color(0xFFFF9800)
        else -> AppTheme.ErrorColor
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(AppTheme.ChipBackground)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(normalizedScore.coerceIn(0.05f, 1f))
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                scoreColor.copy(alpha = 0.7f),
                                scoreColor
                            )
                        )
                    )
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(StringKeyAnalysis.PRASHNA_UNFAVORABLE),
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.TextMuted
            )
            Text(
                stringResource(StringKeyAnalysis.PRASHNA_SCORE, score.localized()),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = scoreColor
            )
            Text(
                stringResource(StringKeyAnalysis.PRASHNA_FAVORABLE),
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun QuestionSummaryCard(result: PrashnaCalculator.PrashnaResult) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.QuestionAnswer,
                    contentDescription = null,
                    tint = AppTheme.AccentTeal,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyAnalysis.PRASHNA_QUESTION_DETAILS),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                color = AppTheme.CardBackgroundElevated,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "\"${result.question}\"",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoChip(
                    icon = Icons.Outlined.Star,
                    label = result.category.getLocalizedName(currentLanguage()),
                    modifier = Modifier.weight(1f)
                )
                InfoChip(
                    icon = Icons.Outlined.AccessTime,
                    label = result.questionTime.formatLocalized(DateFormat.TIME_ONLY),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun InfoChip(
    icon: ImageVector,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = AppTheme.ChipBackground,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = AppTheme.TextMuted,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                label,
                style = MaterialTheme.typography.labelMedium,
                color = AppTheme.TextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun MoonAnalysisCard(moonAnalysis: PrashnaCalculator.MoonAnalysis) {
    val strengthColor = getMoonStrengthColor(moonAnalysis.moonStrength)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.NightsStay,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyAnalysis.PRASHNA_MOON_ANALYSIS),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    color = strengthColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        moonAnalysis.moonStrength.getLocalizedName(currentLanguage()),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = strengthColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            val signLabel = stringResource(StringKeyAnalysis.PRASHNA_SIGN)
            val houseLabel = stringResource(StringKey.VARSHAPHALA_HOUSE)
            val nakshatraLabel = stringResource(StringKey.CHART_NAKSHATRA)
            val phaseLabel = stringResource(StringKeyAnalysis.PRASHNA_PHASE)
            val tithiLabel = stringResource(StringKeyAnalysis.PRASHNA_TITHI)
            val nakshatraLordLabel = stringResource(StringKeyAnalysis.PRASHNA_NAKSHATRA_LORD)
            val waxingLabel = stringResource(StringKeyAnalysis.PRASHNA_MOON_PHASE_WAXING)
            val waningLabel = stringResource(StringKeyAnalysis.PRASHNA_MOON_PHASE_WANING)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MoonDetailItem(
                    label = signLabel,
                    value = moonAnalysis.moonSign.getLocalizedName(currentLanguage()),
                    modifier = Modifier.weight(1f)
                )
                MoonDetailItem(
                    label = houseLabel,
                    value = moonAnalysis.moonHouse.localized(),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MoonDetailItem(
                    label = nakshatraLabel,
                    value = stringResource(StringKey.NAKSHATRA_PADA_ACCESSIBILITY, moonAnalysis.nakshatra.localizedName(), moonAnalysis.nakshatraPada.localized()),
                    modifier = Modifier.weight(1f)
                )
                MoonDetailItem(
                    label = phaseLabel,
                    value = if (moonAnalysis.isWaxing) waxingLabel else waningLabel,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MoonDetailItem(
                    label = tithiLabel,
                    value = moonAnalysis.tithiName,
                    modifier = Modifier.weight(1f)
                )
                MoonDetailItem(
                    label = nakshatraLordLabel,
                    value = moonAnalysis.nakshatraLord.getLocalizedName(currentLanguage()),
                    modifier = Modifier.weight(1f)
                )
            }

            if (moonAnalysis.isVoidOfCourse) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = AppTheme.WarningColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.Warning,
                            contentDescription = null,
                            tint = AppTheme.WarningColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            stringResource(StringKeyAnalysis.PRASHNA_MOON_VOID),
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.WarningColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                moonAnalysis.interpretation,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun MoonDetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = AppTheme.CardBackgroundElevated,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.TextMuted
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                value,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun LagnaAnalysisCard(lagnaAnalysis: PrashnaCalculator.LagnaAnalysis) {
    // Hoist language call to Composable scope
    val language = currentLanguage()
    val risingSignLabel = stringResource(StringKeyAnalysis.PRASHNA_RISING_SIGN)
    val lagnaLordLabel = stringResource(StringKeyAnalysis.PRASHNA_LAGNA_LORD)
    val lordPositionLabel = stringResource(StringKeyAnalysis.PRASHNA_LORD_POSITION)
    val conditionLabel = stringResource(StringKeyAnalysis.PRASHNA_CONDITION)
    val houseLabel = stringResource(StringKey.VARSHAPHALA_HOUSE)
    val planetsInLagnaLabel = stringResource(StringKeyAnalysis.PRASHNA_PLANETS_IN_LAGNA)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.TrendingUp,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyAnalysis.PRASHNA_LAGNA_ANALYSIS),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MoonDetailItem(
                    label = risingSignLabel,
                    value = lagnaAnalysis.lagnaSign.getLocalizedName(language),
                    modifier = Modifier.weight(1f)
                )
                MoonDetailItem(
                    label = lagnaLordLabel,
                    value = lagnaAnalysis.lagnaLord.getLocalizedName(language),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MoonDetailItem(
                    label = lordPositionLabel,
                    value = "${stringResource(StringKey.CHART_HOUSE)} ${lagnaAnalysis.lagnaLordPosition.house.localized()}",
                    modifier = Modifier.weight(1f)
                )
                MoonDetailItem(
                    label = conditionLabel,
                    value = lagnaAnalysis.lagnaCondition.getLocalizedName(language),
                    modifier = Modifier.weight(1f)
                )
            }

            if (lagnaAnalysis.planetsInLagna.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = AppTheme.InfoColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = null,
                            tint = AppTheme.InfoColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        val language = currentLanguage()
                        Text(
                            "$planetsInLagnaLabel: ${lagnaAnalysis.planetsInLagna.joinToString { it.planet.getLocalizedName(language) }}",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.InfoColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                lagnaAnalysis.interpretation,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun TimingPredictionCard(timing: PrashnaCalculator.TimingPrediction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.AccentTeal.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = AppTheme.AccentTeal,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyAnalysis.PRASHNA_TIMING),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = AppTheme.AccentTeal.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        timing.estimatedTime,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.AccentTeal
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        stringResource(StringKeyAnalysis.PRASHNA_ESTIMATED_TIMEFRAME),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoChip(
                    icon = Icons.Outlined.Visibility,
                    label = timing.timingMethod.getLocalizedName(currentLanguage()),
                    modifier = Modifier.weight(1f)
                )
                InfoChip(
                    icon = Icons.Outlined.TrendingUp,
                    label = stringResource(StringKeyAnalysis.PRASHNA_CONFIDENCE, timing.confidence.localized()),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                timing.explanation,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun SpecialYogasCard(yogas: List<PrashnaCalculator.PrashnaYoga>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyAnalysis.PRASHNA_SPECIAL_YOGAS),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                yogas.forEach { yoga ->
                    YogaItem(yoga = yoga)
                }
            }
        }
    }
}

@Composable
private fun YogaItem(yoga: PrashnaCalculator.PrashnaYoga) {
    val color = if (yoga.isPositive) AppTheme.SuccessColor else AppTheme.ErrorColor
    val icon = if (yoga.isPositive) Icons.Filled.CheckCircle else Icons.Filled.Warning

    Surface(
        color = color.copy(alpha = 0.08f),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        yoga.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    repeat(yoga.strength) {
                        Icon(
                            Icons.Outlined.Star,
                            contentDescription = null,
                            tint = AppTheme.AccentGold,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    yoga.description,
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextMuted
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    yoga.interpretation,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
            }
        }
    }
}

@Composable
private fun FactorsCard(judgment: PrashnaCalculator.PrashnaJudgment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Supporting Factors
            if (judgment.supportingFactors.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.ThumbUp,
                        contentDescription = null,
                        tint = AppTheme.SuccessColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        stringResource(StringKeyAnalysis.PRASHNA_SUPPORTING_FACTORS),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    judgment.supportingFactors.take(5).forEach { factor ->
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = null,
                                tint = AppTheme.SuccessColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                factor,
                                style = MaterialTheme.typography.bodySmall,
                                color = AppTheme.TextSecondary
                            )
                        }
                    }
                }
            }

            // Opposing Factors
            if (judgment.opposingFactors.isNotEmpty()) {
                if (judgment.supportingFactors.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = AppTheme.DividerColor.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.ThumbDown,
                        contentDescription = null,
                        tint = AppTheme.ErrorColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        stringResource(StringKeyAnalysis.PRASHNA_CHALLENGES),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    judgment.opposingFactors.take(5).forEach { factor ->
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = null,
                                tint = AppTheme.ErrorColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                factor,
                                style = MaterialTheme.typography.bodySmall,
                                color = AppTheme.TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecommendationsCard(recommendations: List<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.InfoColor.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Lightbulb,
                    contentDescription = null,
                    tint = AppTheme.InfoColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyAnalysis.PRASHNA_RECOMMENDATIONS),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                recommendations.forEachIndexed { index, rec ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            color = AppTheme.InfoColor.copy(alpha = 0.2f),
                            shape = CircleShape,
                            modifier = Modifier.size(22.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    "${index + 1}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = AppTheme.InfoColor
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            rec,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextSecondary,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

/**
 * AI Insight Card - Allows users to get AI-powered interpretation of Prashna results
 */
@Composable
private fun AiInsightCard(result: PrashnaCalculator.PrashnaResult) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // AI state management
    var aiState by remember { mutableStateOf<AiInsightState>(AiInsightState.Initial) }
    var streamingContent by remember { mutableStateOf("") }
    var displayContent by remember { mutableStateOf("") }
    var streamingJob by remember { mutableStateOf<Job?>(null) }
    var lastUpdateTime by remember { mutableStateOf(0L) }

    // Get AI dependencies
    val agent = remember(context) { StormyAgent.getInstance(context) }
    val providerRegistry = remember(context) { AiProviderRegistry.getInstance(context) }

    // Generate Prashna context for the AI
    val prashnaContext = remember(result) { formatPrashnaForAi(result) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.AccentPrimary.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AppTheme.AccentPrimary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.AutoAwesome,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(StringKeyAnalysis.PRASHNA_AI_INSIGHT),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        stringResource(StringKeyAnalysis.PRASHNA_AI_INSIGHT_SUBTITLE),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content based on state
            AnimatedContent(
                targetState = aiState,
                label = "ai_insight_state"
            ) { state ->
                when (state) {
                    is AiInsightState.Initial -> {
                        // Show generate button
                        Button(
                            onClick = {
                                scope.launch {
                                    val model = providerRegistry.getDefaultModel()
                                    if (model == null) {
                                        aiState = AiInsightState.Error("No AI model configured. Please set up an AI provider in settings.")
                                        return@launch
                                    }

                                    aiState = AiInsightState.Loading
                                    streamingContent = ""

                                    streamingJob = launch {
                                        try {
                                            val messages = listOf(
                                                ChatMessage(
                                                    role = MessageRole.USER,
                                                    content = prashnaContext
                                                )
                                            )

                                            agent.processMessage(
                                                messages = messages,
                                                model = model,
                                                currentProfile = null,
                                                allProfiles = emptyList(),
                                                currentChart = result.chart
                                            ).collect { response ->
                                                when (response) {
                                                    is AgentResponse.ContentChunk -> {
                                                        streamingContent += response.text
                                                        // Throttle UI updates to reduce recompositions
                                                        val currentTime = System.currentTimeMillis()
                                                        if (currentTime - lastUpdateTime > 100) { // Max 10 updates/second
                                                            lastUpdateTime = currentTime
                                                            // Use raw content during streaming (skip expensive cleaning)
                                                            displayContent = streamingContent.trim()
                                                            aiState = AiInsightState.Streaming(displayContent)
                                                        }
                                                    }
                                                    is AgentResponse.Complete -> {
                                                        // Only apply full cleaning when content is complete
                                                        val finalContent = withContext(Dispatchers.Default) {
                                                            ContentCleaner.cleanForDisplay(response.content)
                                                        }
                                                        aiState = AiInsightState.Success(finalContent)
                                                    }
                                                    is AgentResponse.Error -> {
                                                        aiState = AiInsightState.Error(response.message)
                                                    }
                                                    else -> { /* Ignore other responses */ }
                                                }
                                            }
                                        } catch (e: CancellationException) {
                                            throw e
                                        } catch (e: Exception) {
                                            aiState = AiInsightState.Error(e.message ?: "Failed to generate insight")
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.AccentPrimary
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(
                                Icons.Outlined.AutoAwesome,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = AppTheme.ButtonText
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                stringResource(StringKeyAnalysis.PRASHNA_GENERATE_AI_INSIGHT),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Medium,
                                color = AppTheme.ButtonText
                            )
                        }
                    }

                    is AiInsightState.Loading -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(
                                color = AppTheme.AccentPrimary,
                                strokeWidth = 3.dp,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                stringResource(StringKeyDosha.STORMY_ANALYZING),
                                style = MaterialTheme.typography.bodySmall,
                                color = AppTheme.TextMuted
                            )
                        }
                    }

                    is AiInsightState.Streaming -> {
                        Column {
                            Surface(
                                color = AppTheme.CardBackgroundElevated,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    MarkdownText(
                                        markdown = state.content,
                                        textColor = AppTheme.TextSecondary,
                                        linkColor = AppTheme.AccentPrimary,
                                        textSize = 13f
                                    )
                                    // Typing indicator
                                    Row(
                                        modifier = Modifier.padding(top = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        CircularProgressIndicator(
                                            color = AppTheme.AccentPrimary,
                                            strokeWidth = 2.dp,
                                            modifier = Modifier.size(14.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            stringResource(StringKeyDosha.AI_GENERATING),
                                            style = MaterialTheme.typography.labelSmall,
                                            color = AppTheme.TextMuted
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is AiInsightState.Success -> {
                        Column {
                            Surface(
                                color = AppTheme.CardBackgroundElevated,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                MarkdownText(
                                    markdown = state.content,
                                    modifier = Modifier.padding(12.dp),
                                    textColor = AppTheme.TextSecondary,
                                    linkColor = AppTheme.AccentPrimary,
                                    textSize = 13f
                                )
                            }
                            // Regenerate button
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Surface(
                                    onClick = {
                                        aiState = AiInsightState.Initial
                                    },
                                    color = AppTheme.AccentPrimary.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Filled.Refresh,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp),
                                            tint = AppTheme.AccentPrimary
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            stringResource(StringKey.BTN_REGENERATE),
                                            style = MaterialTheme.typography.labelMedium,
                                            color = AppTheme.AccentPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is AiInsightState.Error -> {
                        Column {
                            Surface(
                                color = AppTheme.ErrorColor.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Outlined.ErrorOutline,
                                        contentDescription = null,
                                        tint = AppTheme.ErrorColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        state.message,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AppTheme.ErrorColor,
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Button(
                                onClick = { aiState = AiInsightState.Initial },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AppTheme.AccentPrimary
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(
                                    Icons.Filled.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp),
                                    tint = AppTheme.ButtonText
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    stringResource(StringKey.BTN_TRY_AGAIN),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = AppTheme.ButtonText
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Cleanup on disposal
    DisposableEffect(Unit) {
        onDispose {
            streamingJob?.cancel()
        }
    }
}

/**
 * State for AI insight generation
 */
private sealed interface AiInsightState {
    data object Initial : AiInsightState
    data object Loading : AiInsightState
    data class Streaming(val content: String) : AiInsightState
    data class Success(val content: String) : AiInsightState
    data class Error(val message: String) : AiInsightState
}

/**
 * Format Prashna result as context for AI interpretation
 */
private fun formatPrashnaForAi(result: PrashnaCalculator.PrashnaResult): String {
    return buildString {
        appendLine("Please provide a deeper Vedic astrology interpretation of this Prashna (horary) chart analysis. Be insightful, practical, and compassionate in your guidance.")
        appendLine()
        appendLine("## Question")
        appendLine("\"${result.question}\"")
        appendLine("Category: ${result.category.displayName}")
        appendLine("Time: ${result.questionTime}")
        appendLine()
        appendLine("## Initial Verdict")
        appendLine("Verdict: ${result.judgment.verdict.displayName}")
        appendLine("Confidence: ${result.confidence}%")
        appendLine("Certainty: ${result.judgment.certaintyLevel.displayName}")
        appendLine()
        appendLine("## Moon Analysis")
        appendLine("Moon Sign: ${result.moonAnalysis.moonSign.displayName}")
        appendLine("Moon House: ${result.moonAnalysis.moonHouse}")
        appendLine("Nakshatra: ${result.moonAnalysis.nakshatra.displayName} (Pada ${result.moonAnalysis.nakshatraPada})")
        appendLine("Moon Strength: ${result.moonAnalysis.moonStrength.displayName}")
        appendLine("Phase: ${if (result.moonAnalysis.isWaxing) "Waxing" else "Waning"}")
        appendLine("Void of Course: ${if (result.moonAnalysis.isVoidOfCourse) "Yes" else "No"}")
        appendLine()
        appendLine("## Lagna Analysis")
        appendLine("Rising Sign: ${result.lagnaAnalysis.lagnaSign.displayName}")
        appendLine("Lagna Lord: ${result.lagnaAnalysis.lagnaLord.displayName}")
        appendLine("Lagna Lord Position: House ${result.lagnaAnalysis.lagnaLordPosition.house}")
        appendLine("Condition: ${result.lagnaAnalysis.lagnaCondition.displayName}")
        if (result.lagnaAnalysis.planetsInLagna.isNotEmpty()) {
            appendLine("Planets in Lagna: ${result.lagnaAnalysis.planetsInLagna.joinToString { it.planet.displayName }}")
        }
        appendLine()
        if (result.timingPrediction.willEventOccur) {
            appendLine("## Timing Prediction")
            appendLine("Estimated Time: ${result.timingPrediction.estimatedTime}")
            appendLine("Method: ${result.timingPrediction.timingMethod.displayName}")
            appendLine("Confidence: ${result.timingPrediction.confidence}%")
            appendLine()
        }
        if (result.specialYogas.isNotEmpty()) {
            appendLine("## Special Prashna Yogas")
            result.specialYogas.forEach { yoga ->
                val sign = if (yoga.isPositive) "+" else "-"
                appendLine("$sign ${yoga.name}: ${yoga.description}")
            }
            appendLine()
        }
        appendLine("## Supporting Factors")
        result.judgment.supportingFactors.forEach { appendLine("- $it") }
        appendLine()
        if (result.judgment.opposingFactors.isNotEmpty()) {
            appendLine("## Challenges/Opposing Factors")
            result.judgment.opposingFactors.forEach { appendLine("- $it") }
            appendLine()
        }
        appendLine("Please provide:")
        appendLine("1. Your overall interpretation of this Prashna chart")
        appendLine("2. Key insights the querent should know")
        appendLine("3. What the Moon's position reveals about their mindset")
        appendLine("4. Practical advice based on the astrological factors")
        appendLine("5. Any additional considerations or cautions")
    }
}

// Helper functions
private fun getCategoryIcon(category: PrashnaCalculator.PrashnaCategory): ImageVector {
    return when (category) {
        PrashnaCalculator.PrashnaCategory.YES_NO -> Icons.Outlined.HelpOutline
        PrashnaCalculator.PrashnaCategory.CAREER -> Icons.Outlined.Work
        PrashnaCalculator.PrashnaCategory.MARRIAGE -> Icons.Outlined.Favorite
        PrashnaCalculator.PrashnaCategory.CHILDREN -> Icons.Outlined.ChildFriendly
        PrashnaCalculator.PrashnaCategory.HEALTH -> Icons.Outlined.LocalHospital
        PrashnaCalculator.PrashnaCategory.WEALTH -> Icons.Outlined.MonetizationOn
        PrashnaCalculator.PrashnaCategory.PROPERTY -> Icons.Outlined.Home
        PrashnaCalculator.PrashnaCategory.TRAVEL -> Icons.Outlined.Flight
        PrashnaCalculator.PrashnaCategory.EDUCATION -> Icons.Outlined.School
        PrashnaCalculator.PrashnaCategory.LEGAL -> Icons.Outlined.Gavel
        PrashnaCalculator.PrashnaCategory.LOST_OBJECT -> Icons.Outlined.Search
        PrashnaCalculator.PrashnaCategory.RELATIONSHIP -> Icons.Outlined.Favorite
        PrashnaCalculator.PrashnaCategory.BUSINESS -> Icons.Outlined.Business
        PrashnaCalculator.PrashnaCategory.SPIRITUAL -> Icons.Outlined.SelfImprovement
        PrashnaCalculator.PrashnaCategory.GENERAL -> Icons.Outlined.Star
    }
}

private fun getVerdictColor(verdict: PrashnaCalculator.PrashnaVerdict): Color {
    return when (verdict) {
        PrashnaCalculator.PrashnaVerdict.STRONGLY_YES -> Color(0xFF2E7D32)
        PrashnaCalculator.PrashnaVerdict.YES -> Color(0xFF4CAF50)
        PrashnaCalculator.PrashnaVerdict.LIKELY_YES -> Color(0xFF8BC34A)
        PrashnaCalculator.PrashnaVerdict.UNCERTAIN -> Color(0xFFFFC107)
        PrashnaCalculator.PrashnaVerdict.TIMING_DEPENDENT -> Color(0xFFFF9800)
        PrashnaCalculator.PrashnaVerdict.LIKELY_NO -> Color(0xFFFF9800)
        PrashnaCalculator.PrashnaVerdict.NO -> Color(0xFFFF5722)
        PrashnaCalculator.PrashnaVerdict.STRONGLY_NO -> Color(0xFFE53935)
    }
}

private fun getVerdictIcon(verdict: PrashnaCalculator.PrashnaVerdict): ImageVector {
    return when (verdict) {
        PrashnaCalculator.PrashnaVerdict.STRONGLY_YES,
        PrashnaCalculator.PrashnaVerdict.YES,
        PrashnaCalculator.PrashnaVerdict.LIKELY_YES -> Icons.Outlined.ThumbUp

        PrashnaCalculator.PrashnaVerdict.UNCERTAIN,
        PrashnaCalculator.PrashnaVerdict.TIMING_DEPENDENT -> Icons.Outlined.HelpOutline

        PrashnaCalculator.PrashnaVerdict.LIKELY_NO,
        PrashnaCalculator.PrashnaVerdict.NO,
        PrashnaCalculator.PrashnaVerdict.STRONGLY_NO -> Icons.Outlined.ThumbDown
    }
}

private fun getMoonStrengthColor(strength: PrashnaCalculator.MoonStrength): Color {
    return when (strength) {
        PrashnaCalculator.MoonStrength.EXCELLENT -> Color(0xFF2E7D32)
        PrashnaCalculator.MoonStrength.GOOD -> Color(0xFF4CAF50)
        PrashnaCalculator.MoonStrength.AVERAGE -> Color(0xFFFFC107)
        PrashnaCalculator.MoonStrength.WEAK -> Color(0xFFFF9800)
        PrashnaCalculator.MoonStrength.VERY_WEAK -> Color(0xFFFF5722)
        PrashnaCalculator.MoonStrength.AFFLICTED -> Color(0xFFE53935)
    }
}
