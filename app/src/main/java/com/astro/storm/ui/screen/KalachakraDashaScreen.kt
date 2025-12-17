package com.astro.storm.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PersonOff
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.astro.storm.data.localization.BikramSambatConverter
import com.astro.storm.data.localization.DateFormat
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.localization.formatLocalized
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.KalachakraDashaCalculator
import com.astro.storm.ui.theme.AppTheme
import com.astro.storm.ui.viewmodel.KalachakraDashaUiState
import com.astro.storm.ui.viewmodel.KalachakraDashaViewModel
import java.time.LocalDate

/**
 * Kalachakra Dasha Screen - Production-grade UI
 *
 * Displays the Kalachakra Dasha analysis including:
 * - Current Mahadasha and Antardasha
 * - Deha (Body) and Jeeva (Soul) analysis
 * - Complete timeline of all Mahadashas
 * - Health and spiritual predictions
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KalachakraDashaScreen(
    chart: VedicChart?,
    onBack: () -> Unit,
    viewModel: KalachakraDashaViewModel = viewModel()
) {
    val chartKey = remember(chart) {
        chart?.let {
            buildString {
                append(it.birthData.dateTime.toString())
                append("|")
                append(String.format("%.6f", it.birthData.latitude))
                append("|")
                append(String.format("%.6f", it.birthData.longitude))
            }
        }
    }

    LaunchedEffect(chartKey) {
        viewModel.loadKalachakraDasha(chart)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val language = LocalLanguage.current

    val currentPeriodInfo = remember(uiState, language) {
        extractCurrentPeriodInfo(uiState, language)
    }

    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            KalachakraDashaTopBar(
                chartName = chart?.birthData?.name ?: stringResource(StringKeyMatch.MISC_UNKNOWN),
                currentPeriodInfo = currentPeriodInfo,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppTheme.ScreenBackground)
        ) {
            AnimatedContent(
                targetState = uiState,
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith
                            fadeOut(animationSpec = tween(200))
                },
                label = "KalachakraDashaStateTransition",
                contentKey = { state -> state::class.simpleName }
            ) { state ->
                when (state) {
                    is KalachakraDashaUiState.Loading -> {
                        LoadingContent()
                    }
                    is KalachakraDashaUiState.Success -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            KalachakraTabRow(
                                selectedTab = selectedTab,
                                onTabSelected = { selectedTab = it }
                            )
                            when (selectedTab) {
                                0 -> CurrentPeriodTab(result = state.result)
                                1 -> DehaJeevaTab(result = state.result)
                                2 -> TimelineTab(result = state.result)
                            }
                        }
                    }
                    is KalachakraDashaUiState.Error -> {
                        ErrorContent(
                            message = state.message,
                            onRetry = { viewModel.loadKalachakraDasha(chart) }
                        )
                    }
                    is KalachakraDashaUiState.Idle -> {
                        EmptyContent(onBack = onBack)
                    }
                }
            }
        }
    }
}

private data class CurrentPeriodInfo(
    val mahadasha: String?,
    val antardasha: String?,
    val isLoading: Boolean,
    val hasError: Boolean
)

private fun extractCurrentPeriodInfo(
    uiState: KalachakraDashaUiState,
    language: Language
): CurrentPeriodInfo {
    return when (uiState) {
        is KalachakraDashaUiState.Success -> {
            val md = uiState.result.currentMahadasha
            val ad = uiState.result.currentAntardasha
            CurrentPeriodInfo(
                mahadasha = md?.sign?.getLocalizedName(language),
                antardasha = ad?.sign?.getLocalizedName(language),
                isLoading = false,
                hasError = false
            )
        }
        is KalachakraDashaUiState.Loading -> CurrentPeriodInfo(null, null, true, false)
        is KalachakraDashaUiState.Error -> CurrentPeriodInfo(null, null, false, true)
        is KalachakraDashaUiState.Idle -> CurrentPeriodInfo(null, null, false, false)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun KalachakraDashaTopBar(
    chartName: String,
    currentPeriodInfo: CurrentPeriodInfo,
    onBack: () -> Unit
) {
    Surface(
        color = AppTheme.ScreenBackground,
        shadowElevation = 2.dp
    ) {
        TopAppBar(
            title = {
                Column(modifier = Modifier.fillMaxWidth(0.85f)) {
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_DASHA_TITLE),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    TopBarSubtitleContent(
                        chartName = chartName,
                        periodInfo = currentPeriodInfo
                    )
                }
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(StringKey.BTN_BACK),
                        tint = AppTheme.TextPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppTheme.ScreenBackground,
                scrolledContainerColor = AppTheme.ScreenBackground
            )
        )
    }
}

@Composable
private fun TopBarSubtitleContent(
    chartName: String,
    periodInfo: CurrentPeriodInfo
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        when {
            periodInfo.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.size(12.dp),
                    strokeWidth = 1.5.dp,
                    color = AppTheme.AccentPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = stringResource(StringKey.DASHA_CALCULATING),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted,
                    fontSize = 12.sp
                )
            }
            periodInfo.hasError -> {
                Text(
                    text = "${stringResource(StringKey.DASHA_ERROR)} • $chartName",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            periodInfo.mahadasha != null -> {
                Text(
                    text = buildString {
                        append(periodInfo.mahadasha)
                        periodInfo.antardasha?.let { append(" → $it") }
                        append(" • $chartName")
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            else -> {
                Text(
                    text = chartName,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun KalachakraTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(
        stringResource(StringKeyDosha.KALACHAKRA_CURRENT),
        stringResource(StringKeyDosha.KALACHAKRA_DEHA_JEEVA),
        stringResource(StringKeyDosha.KALACHAKRA_TIMELINE)
    )

    TabRow(
        selectedTabIndex = selectedTab,
        containerColor = AppTheme.CardBackground,
        contentColor = AppTheme.AccentPrimary,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                color = AppTheme.AccentPrimary
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = title,
                        fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                },
                selectedContentColor = AppTheme.AccentPrimary,
                unselectedContentColor = AppTheme.TextMuted
            )
        }
    }
}

// ============================================
// CURRENT PERIOD TAB
// ============================================

@Composable
private fun CurrentPeriodTab(result: KalachakraDashaCalculator.KalachakraDashaResult) {
    val language = LocalLanguage.current
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "current_period") {
            CurrentPeriodCard(result = result, language = language)
        }

        item(key = "nakshatra_info") {
            NakshatraGroupCard(result = result, language = language)
        }

        item(key = "health_indicator") {
            HealthIndicatorCard(result = result, language = language)
        }

        item(key = "interpretation") {
            InterpretationCard(result = result, language = language)
        }

        item(key = "about_kalachakra") {
            AboutKalachakraCard()
        }

        item(key = "bottom_spacer") {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun CurrentPeriodCard(
    result: KalachakraDashaCalculator.KalachakraDashaResult,
    language: Language
) {
    val currentMahadasha = result.currentMahadasha
    val currentAntardasha = result.currentAntardasha

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = AppTheme.AccentGold.copy(alpha = 0.1f),
                spotColor = AppTheme.AccentGold.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(20.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    AppTheme.AccentGold.copy(alpha = 0.2f),
                                    AppTheme.AccentGold.copy(alpha = 0.1f)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = AppTheme.AccentGold,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_CURRENT),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_DASHA_SUBTITLE),
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (currentMahadasha != null) {
                SignPeriodRow(
                    label = stringResource(StringKey.DASHA_MAHADASHA),
                    sign = currentMahadasha.sign,
                    startDate = currentMahadasha.startDate,
                    endDate = currentMahadasha.endDate,
                    progress = currentMahadasha.getProgressPercent().toFloat() / 100f,
                    remainingText = formatRemainingYearsLocalized(
                        currentMahadasha.getRemainingDays() / 365.25,
                        language
                    ),
                    isLarge = true,
                    language = language
                )

                currentAntardasha?.let { ad ->
                    Spacer(modifier = Modifier.height(14.dp))
                    SignPeriodRow(
                        label = stringResource(StringKey.DASHA_ANTARDASHA),
                        sign = ad.sign,
                        startDate = ad.startDate,
                        endDate = ad.endDate,
                        progress = ad.getProgressPercent().toFloat() / 100f,
                        remainingText = formatRemainingDaysLocalized(
                            java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), ad.endDate).coerceAtLeast(0),
                            language
                        ),
                        isLarge = false,
                        language = language,
                        isDeha = ad.isDehaSign,
                        isJeeva = ad.isJeevaSign
                    )
                }

                HorizontalDivider(
                    color = AppTheme.DividerColor,
                    modifier = Modifier.padding(vertical = 18.dp)
                )

                CurrentEffectsSection(
                    mahadasha = currentMahadasha,
                    language = language
                )
            } else {
                EmptyPeriodState()
            }
        }
    }
}

@Composable
private fun SignPeriodRow(
    label: String,
    sign: ZodiacSign,
    startDate: LocalDate,
    endDate: LocalDate,
    progress: Float,
    remainingText: String,
    isLarge: Boolean,
    language: Language,
    isDeha: Boolean = false,
    isJeeva: Boolean = false
) {
    val signColor = AppTheme.getSignColor(sign)
    val circleSize = if (isLarge) 44.dp else 36.dp
    val mainFontSize = if (isLarge) 16.sp else 14.sp
    val subFontSize = if (isLarge) 12.sp else 11.sp
    val progressHeight = if (isLarge) 6.dp else 5.dp

    val startDateFormatted = startDate.formatLocalized(DateFormat.FULL)
    val endDateFormatted = endDate.formatLocalized(DateFormat.FULL)
    val percentComplete = formatNumber((progress * 100).toInt(), language)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .background(signColor, CircleShape)
                    .border(2.dp, signColor.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = sign.symbol,
                    fontSize = if (isLarge) 17.sp else 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = label,
                        fontSize = subFontSize,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = sign.getLocalizedName(language),
                        fontSize = mainFontSize,
                        fontWeight = if (isLarge) FontWeight.Bold else FontWeight.SemiBold,
                        color = signColor
                    )
                }

                // Show Deha/Jeeva indicators
                if (isDeha || isJeeva) {
                    Row(
                        modifier = Modifier.padding(top = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (isDeha) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = AppTheme.SuccessColor.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = stringResource(StringKeyDosha.KALACHAKRA_DEHA),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppTheme.SuccessColor,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                        if (isJeeva) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = AppTheme.InfoColor.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = stringResource(StringKeyDosha.KALACHAKRA_JEEVA),
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppTheme.InfoColor,
                                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "$startDateFormatted – $endDateFormatted",
                    fontSize = (subFontSize.value - 1).sp,
                    color = AppTheme.TextMuted,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                if (remainingText.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = remainingText,
                        fontSize = (subFontSize.value - 1).sp,
                        color = AppTheme.AccentTeal,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.width(70.dp)
        ) {
            Text(
                text = "$percentComplete%",
                fontSize = subFontSize,
                fontWeight = FontWeight.Bold,
                color = signColor
            )
            Spacer(modifier = Modifier.height(5.dp))
            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(progressHeight)
                    .clip(RoundedCornerShape(progressHeight / 2)),
                color = signColor,
                trackColor = AppTheme.DividerColor
            )
        }
    }
}

@Composable
private fun CurrentEffectsSection(
    mahadasha: KalachakraDashaCalculator.KalachakraMahadasha,
    language: Language
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = AppTheme.CardBackgroundElevated
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Psychology,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(StringKeyDosha.KALACHAKRA_EFFECTS),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.AccentGold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = mahadasha.interpretation.generalEffects,
                fontSize = 13.sp,
                color = AppTheme.TextPrimary,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun NakshatraGroupCard(
    result: KalachakraDashaCalculator.KalachakraDashaResult,
    language: Language
) {
    val groupColor = when (result.nakshatraGroup) {
        KalachakraDashaCalculator.NakshatraGroup.SAVYA -> AppTheme.SuccessColor
        KalachakraDashaCalculator.NakshatraGroup.APSAVYA -> AppTheme.WarningColor
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = groupColor.copy(alpha = 0.08f)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(groupColor.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Timeline,
                        contentDescription = null,
                        tint = groupColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_NAKSHATRA_GROUP),
                        fontSize = 14.sp,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = getGroupLocalizedName(result.nakshatraGroup, language),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = groupColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Birth nakshatra info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(
                    label = stringResource(StringKeyDosha.KALACHAKRA_BIRTH_NAKSHATRA),
                    value = result.birthNakshatra.getLocalizedName(language),
                    color = AppTheme.TextPrimary
                )
                InfoItem(
                    label = stringResource(StringKeyDosha.KALACHAKRA_PADA),
                    value = formatNumber(result.birthNakshatraPada, language),
                    color = AppTheme.TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = result.nakshatraGroup.description,
                fontSize = 12.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Applicability score
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(StringKeyDosha.KALACHAKRA_APPLICABILITY),
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted
                )
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = getApplicabilityColor(result.applicabilityScore).copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "${result.applicabilityScore}%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = getApplicabilityColor(result.applicabilityScore),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HealthIndicatorCard(
    result: KalachakraDashaCalculator.KalachakraDashaResult,
    language: Language
) {
    val currentMahadasha = result.currentMahadasha ?: return
    val healthIndicator = currentMahadasha.healthIndicator
    val healthColor = getHealthColor(healthIndicator)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(healthColor.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.HealthAndSafety,
                        contentDescription = null,
                        tint = healthColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_HEALTH_INDICATOR),
                        fontSize = 14.sp,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = getHealthLocalizedName(healthIndicator, language),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = healthColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = healthIndicator.description,
                fontSize = 13.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 19.sp
            )

            if (currentMahadasha.isParamaAyushSign) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = AppTheme.SuccessColor.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Outlined.Favorite,
                            contentDescription = null,
                            tint = AppTheme.SuccessColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(StringKeyDosha.KALACHAKRA_PARAMA_AYUSH),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.SuccessColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun InterpretationCard(
    result: KalachakraDashaCalculator.KalachakraDashaResult,
    language: Language
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 250),
        label = "interpretation_rotation"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { isExpanded = !isExpanded },
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
                .animateContentSize(animationSpec = tween(durationMillis = 250))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(AppTheme.AccentPrimary.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.SelfImprovement,
                            contentDescription = null,
                            tint = AppTheme.AccentPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_INTERPRETATION),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                Icon(
                    Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotation)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(tween(250)) + fadeIn(tween(250)),
                exit = shrinkVertically(tween(200)) + fadeOut(tween(150))
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor, modifier = Modifier.padding(bottom = 14.dp))

                    Text(
                        text = result.interpretation.systemOverview,
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_GUIDANCE),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.AccentGold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    result.interpretation.generalGuidance.forEach { guidance ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("• ", fontSize = 12.sp, color = AppTheme.TextSecondary)
                            Text(
                                text = guidance,
                                fontSize = 12.sp,
                                color = AppTheme.TextSecondary,
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AboutKalachakraCard() {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 250),
        label = "about_rotation"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { isExpanded = !isExpanded },
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
                .animateContentSize(animationSpec = tween(durationMillis = 250))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(AppTheme.InfoColor.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = null,
                            tint = AppTheme.InfoColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_DASHA_ABOUT),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                Icon(
                    Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotation)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(tween(250)) + fadeIn(tween(250)),
                exit = shrinkVertically(tween(200)) + fadeOut(tween(150))
            ) {
                Column(modifier = Modifier.padding(top = 18.dp)) {
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_DASHA_ABOUT_DESC),
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

// ============================================
// DEHA-JEEVA TAB
// ============================================

@Composable
private fun DehaJeevaTab(result: KalachakraDashaCalculator.KalachakraDashaResult) {
    val language = LocalLanguage.current
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "deha_jeeva_overview") {
            DehaJeevaOverviewCard(result = result, language = language)
        }

        item(key = "deha_analysis") {
            DehaAnalysisCard(result = result, language = language)
        }

        item(key = "jeeva_analysis") {
            JeevaAnalysisCard(result = result, language = language)
        }

        item(key = "relationship") {
            RelationshipCard(result = result, language = language)
        }

        item(key = "recommendations") {
            RecommendationsCard(result = result, language = language)
        }

        item(key = "bottom_spacer") {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DehaJeevaOverviewCard(
    result: KalachakraDashaCalculator.KalachakraDashaResult,
    language: Language
) {
    val dehaColor = AppTheme.getSignColor(result.dehaRashi)
    val jeevaColor = AppTheme.getSignColor(result.jeevaRashi)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = AppTheme.AccentPrimary.copy(alpha = 0.1f),
                spotColor = AppTheme.AccentPrimary.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(20.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = stringResource(StringKeyDosha.KALACHAKRA_DEHA_JEEVA_TITLE),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(StringKeyDosha.KALACHAKRA_DEHA_JEEVA_SUBTITLE),
                fontSize = 12.sp,
                color = AppTheme.TextMuted
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Deha column
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(dehaColor, CircleShape)
                            .border(3.dp, dehaColor.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = result.dehaRashi.symbol,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_DEHA),
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = result.dehaRashi.getLocalizedName(language),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = dehaColor
                    )
                    Text(
                        text = "(${stringResource(StringKeyDosha.KALACHAKRA_BODY)})",
                        fontSize = 11.sp,
                        color = AppTheme.TextMuted
                    )
                }

                // Connector
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    Icon(
                        Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Jeeva column
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(jeevaColor, CircleShape)
                            .border(3.dp, jeevaColor.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = result.jeevaRashi.symbol,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_JEEVA),
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = result.jeevaRashi.getLocalizedName(language),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = jeevaColor
                    )
                    Text(
                        text = "(${stringResource(StringKeyDosha.KALACHAKRA_SOUL)})",
                        fontSize = 11.sp,
                        color = AppTheme.TextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun DehaAnalysisCard(
    result: KalachakraDashaCalculator.KalachakraDashaResult,
    language: Language
) {
    val analysis = result.dehaJeevaAnalysis

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.SuccessColor.copy(alpha = 0.08f)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.HealthAndSafety,
                    contentDescription = null,
                    tint = AppTheme.SuccessColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(StringKeyDosha.KALACHAKRA_DEHA_ANALYSIS),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(
                    label = stringResource(StringKeyDosha.KALACHAKRA_DEHA_LORD),
                    value = analysis.dehaLord.getLocalizedName(language),
                    color = AppTheme.TextPrimary
                )
                InfoItem(
                    label = stringResource(StringKeyDosha.KALACHAKRA_STRENGTH),
                    value = analysis.dehaLordStrength.split(" - ").firstOrNull() ?: analysis.dehaLordStrength,
                    color = AppTheme.TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = analysis.healthPrediction,
                fontSize = 13.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
private fun JeevaAnalysisCard(
    result: KalachakraDashaCalculator.KalachakraDashaResult,
    language: Language
) {
    val analysis = result.dehaJeevaAnalysis

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.InfoColor.copy(alpha = 0.08f)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.SelfImprovement,
                    contentDescription = null,
                    tint = AppTheme.InfoColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(StringKeyDosha.KALACHAKRA_JEEVA_ANALYSIS),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(
                    label = stringResource(StringKeyDosha.KALACHAKRA_JEEVA_LORD),
                    value = analysis.jeevaLord.getLocalizedName(language),
                    color = AppTheme.TextPrimary
                )
                InfoItem(
                    label = stringResource(StringKeyDosha.KALACHAKRA_STRENGTH),
                    value = analysis.jeevaLordStrength.split(" - ").firstOrNull() ?: analysis.jeevaLordStrength,
                    color = AppTheme.TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = analysis.spiritualPrediction,
                fontSize = 13.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
private fun RelationshipCard(
    result: KalachakraDashaCalculator.KalachakraDashaResult,
    language: Language
) {
    val relationship = result.dehaJeevaAnalysis.dehaJeevaRelationship
    val relationshipColor = getRelationshipColor(relationship)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(relationshipColor.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Favorite,
                        contentDescription = null,
                        tint = relationshipColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_RELATIONSHIP),
                        fontSize = 14.sp,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = getRelationshipLocalizedName(relationship, language),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = relationshipColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = relationship.description,
                fontSize = 13.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 19.sp
            )
        }
    }
}

@Composable
private fun RecommendationsCard(
    result: KalachakraDashaCalculator.KalachakraDashaResult,
    language: Language
) {
    val recommendations = result.dehaJeevaAnalysis.recommendations

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = stringResource(StringKeyMatch.DASHA_RECOMMENDATIONS),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.AccentGold
            )

            Spacer(modifier = Modifier.height(14.dp))

            recommendations.forEach { recommendation ->
                Row(
                    modifier = Modifier.padding(vertical = 5.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "•",
                        fontSize = 13.sp,
                        color = AppTheme.AccentGold,
                        modifier = Modifier.padding(end = 10.dp, top = 2.dp)
                    )
                    Text(
                        text = recommendation,
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 19.sp
                    )
                }
            }
        }
    }
}

// ============================================
// TIMELINE TAB
// ============================================

@Composable
private fun TimelineTab(result: KalachakraDashaCalculator.KalachakraDashaResult) {
    val language = LocalLanguage.current
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "timeline_header") {
            TimelineHeaderCard(result = result, language = language)
        }

        items(
            items = result.mahadashas,
            key = { mahadasha -> "${mahadasha.sign.ordinal}_${mahadasha.startDate.toEpochDay()}" }
        ) { mahadasha ->
            MahadashaCard(
                mahadasha = mahadasha,
                isCurrent = mahadasha == result.currentMahadasha,
                currentAntardasha = if (mahadasha == result.currentMahadasha) result.currentAntardasha else null,
                dehaRashi = result.dehaRashi,
                jeevaRashi = result.jeevaRashi,
                language = language
            )
        }

        item(key = "bottom_spacer") {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun TimelineHeaderCard(
    result: KalachakraDashaCalculator.KalachakraDashaResult,
    language: Language
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(AppTheme.AccentGold.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Timeline,
                        contentDescription = null,
                        tint = AppTheme.AccentGold,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = stringResource(StringKeyDosha.KALACHAKRA_TIMELINE),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "${result.mahadashas.size} ${stringResource(StringKey.DASHA_PERIOD)}",
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun MahadashaCard(
    mahadasha: KalachakraDashaCalculator.KalachakraMahadasha,
    isCurrent: Boolean,
    currentAntardasha: KalachakraDashaCalculator.KalachakraAntardasha?,
    dehaRashi: ZodiacSign,
    jeevaRashi: ZodiacSign,
    language: Language
) {
    var isExpanded by rememberSaveable { mutableStateOf(isCurrent) }
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 250),
        label = "expand_rotation"
    )

    val signColor = AppTheme.getSignColor(mahadasha.sign)
    val healthColor = getHealthColor(mahadasha.healthIndicator)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = signColor.copy(alpha = 0.3f))
            ) { isExpanded = !isExpanded },
        shape = RoundedCornerShape(18.dp),
        color = if (isCurrent) {
            signColor.copy(alpha = 0.08f)
        } else {
            AppTheme.CardBackground
        },
        tonalElevation = if (isCurrent) 3.dp else 1.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize(animationSpec = tween(durationMillis = 250))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(signColor, CircleShape)
                            .then(
                                if (isCurrent) {
                                    Modifier.border(2.5.dp, signColor.copy(alpha = 0.4f), CircleShape)
                                } else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = mahadasha.sign.symbol,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = mahadasha.sign.getLocalizedName(language),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                            if (isCurrent) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(5.dp),
                                    color = signColor.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = stringResource(StringKey.DASHA_ACTIVE),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = signColor,
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "${formatYearsLocalized(mahadasha.durationYears, language)} • ${mahadasha.startDate.formatLocalized(DateFormat.YEAR_ONLY)} – ${mahadasha.endDate.formatLocalized(DateFormat.YEAR_ONLY)}",
                            fontSize = 11.sp,
                            color = AppTheme.TextMuted,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (isCurrent) {
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = "${String.format("%.1f", mahadasha.getProgressPercent())}% • ${formatRemainingYearsLocalized(mahadasha.getRemainingDays() / 365.25, language)}",
                                fontSize = 10.sp,
                                color = AppTheme.AccentTeal,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        // Health indicator badge
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = healthColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = getHealthLocalizedName(mahadasha.healthIndicator, language),
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Medium,
                                color = healthColor,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }
                    }
                }

                Icon(
                    Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted,
                    modifier = Modifier
                        .size(26.dp)
                        .rotate(rotation)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(tween(250)) + fadeIn(tween(250)),
                exit = shrinkVertically(tween(200)) + fadeOut(tween(150))
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor, modifier = Modifier.padding(bottom = 14.dp))

                    // Interpretation
                    Text(
                        text = mahadasha.interpretation.generalEffects,
                        fontSize = 12.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = stringResource(StringKey.DASHA_ANTARDASHA),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextSecondary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Surface(
                            shape = RoundedCornerShape(5.dp),
                            color = AppTheme.CardBackgroundElevated
                        ) {
                            Text(
                                text = "${mahadasha.antardashas.size} ${stringResource(StringKey.DASHA_PERIOD)}",
                                fontSize = 10.sp,
                                color = AppTheme.TextMuted,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    mahadasha.antardashas.forEach { antardasha ->
                        AntardashaRow(
                            antardasha = antardasha,
                            isCurrent = antardasha == currentAntardasha,
                            dehaRashi = dehaRashi,
                            jeevaRashi = jeevaRashi,
                            language = language
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun AntardashaRow(
    antardasha: KalachakraDashaCalculator.KalachakraAntardasha,
    isCurrent: Boolean,
    dehaRashi: ZodiacSign,
    jeevaRashi: ZodiacSign,
    language: Language
) {
    val signColor = AppTheme.getSignColor(antardasha.sign)
    val today = LocalDate.now()
    val isPast = antardasha.endDate.isBefore(today)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isCurrent) signColor.copy(alpha = 0.12f) else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = if (isPast) signColor.copy(alpha = 0.4f) else signColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = antardasha.sign.symbol,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = antardasha.sign.getLocalizedName(language),
                        fontSize = 13.sp,
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                        color = when {
                            isCurrent -> signColor
                            isPast -> AppTheme.TextMuted
                            else -> AppTheme.TextPrimary
                        }
                    )
                    if (isCurrent) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${String.format("%.0f", antardasha.getProgressPercent())}%",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = signColor.copy(alpha = 0.9f)
                        )
                    }
                }

                // Deha/Jeeva indicators
                if (antardasha.isDehaSign || antardasha.isJeevaSign) {
                    Row(
                        modifier = Modifier.padding(top = 2.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (antardasha.isDehaSign) {
                            Surface(
                                shape = RoundedCornerShape(3.dp),
                                color = AppTheme.SuccessColor.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = stringResource(StringKeyDosha.KALACHAKRA_DEHA),
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppTheme.SuccessColor,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                        if (antardasha.isJeevaSign) {
                            Surface(
                                shape = RoundedCornerShape(3.dp),
                                color = AppTheme.InfoColor.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    text = stringResource(StringKeyDosha.KALACHAKRA_JEEVA),
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = AppTheme.InfoColor,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${antardasha.startDate.formatLocalized(DateFormat.MONTH_YEAR)} – ${antardasha.endDate.formatLocalized(DateFormat.MONTH_YEAR)}",
                fontSize = 11.sp,
                color = AppTheme.TextMuted
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = String.format("%.1f", antardasha.durationMonths) + (if (language == Language.ENGLISH) " months" else " महिना"),
                fontSize = 10.sp,
                color = AppTheme.TextMuted.copy(alpha = 0.8f)
            )
        }
    }
}

// ============================================
// COMMON COMPONENTS
// ============================================

@Composable
private fun InfoItem(label: String, value: String, color: Color) {
    Column {
        Text(
            text = label,
            fontSize = 11.sp,
            color = AppTheme.TextMuted,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

@Composable
private fun EmptyPeriodState() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = AppTheme.CardBackgroundElevated
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                Icons.Outlined.Info,
                contentDescription = null,
                tint = AppTheme.TextMuted,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = stringResource(StringKey.DASHA_NO_ACTIVE_PERIOD),
                fontSize = 14.sp,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                AppTheme.AccentPrimary.copy(alpha = 0.15f),
                                AppTheme.AccentPrimary.copy(alpha = 0.05f)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(40.dp),
                    color = AppTheme.AccentPrimary,
                    strokeWidth = 3.dp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(StringKey.DASHA_CALCULATING_TIMELINE),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(StringKey.DASHA_CALCULATING_DESC),
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun ErrorContent(
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
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(StringKey.DASHA_CALCULATION_FAILED),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.AccentPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(StringKey.BTN_TRY_AGAIN),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun EmptyContent(onBack: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(AppTheme.CardBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.PersonOff,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    tint = AppTheme.TextMuted
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(StringKey.DASHA_NO_CHART_SELECTED),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(StringKey.DASHA_NO_CHART_MESSAGE),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedButton(
                onClick = onBack,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = stringResource(StringKey.BTN_GO_BACK),
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}

// ============================================
// HELPER FUNCTIONS
// ============================================

private fun getGroupLocalizedName(group: KalachakraDashaCalculator.NakshatraGroup, language: Language): String {
    return when (group) {
        KalachakraDashaCalculator.NakshatraGroup.SAVYA -> when (language) {
            Language.ENGLISH -> "Savya (Direct)"
            Language.NEPALI -> "सव्य (सीधो)"
        }
        KalachakraDashaCalculator.NakshatraGroup.APSAVYA -> when (language) {
            Language.ENGLISH -> "Apsavya (Retrograde)"
            Language.NEPALI -> "अपसव्य (उल्टो)"
        }
    }
}

private fun getHealthLocalizedName(health: KalachakraDashaCalculator.HealthIndicator, language: Language): String {
    return when (health) {
        KalachakraDashaCalculator.HealthIndicator.EXCELLENT -> when (language) {
            Language.ENGLISH -> "Excellent"
            Language.NEPALI -> "उत्कृष्ट"
        }
        KalachakraDashaCalculator.HealthIndicator.GOOD -> when (language) {
            Language.ENGLISH -> "Good"
            Language.NEPALI -> "राम्रो"
        }
        KalachakraDashaCalculator.HealthIndicator.MODERATE -> when (language) {
            Language.ENGLISH -> "Moderate"
            Language.NEPALI -> "मध्यम"
        }
        KalachakraDashaCalculator.HealthIndicator.CHALLENGING -> when (language) {
            Language.ENGLISH -> "Challenging"
            Language.NEPALI -> "चुनौतीपूर्ण"
        }
        KalachakraDashaCalculator.HealthIndicator.CRITICAL -> when (language) {
            Language.ENGLISH -> "Critical"
            Language.NEPALI -> "गम्भीर"
        }
    }
}

private fun getRelationshipLocalizedName(
    relationship: KalachakraDashaCalculator.DehaJeevaRelationship,
    language: Language
): String {
    return when (relationship) {
        KalachakraDashaCalculator.DehaJeevaRelationship.HARMONIOUS -> when (language) {
            Language.ENGLISH -> "Harmonious"
            Language.NEPALI -> "सामञ्जस्यपूर्ण"
        }
        KalachakraDashaCalculator.DehaJeevaRelationship.SUPPORTIVE -> when (language) {
            Language.ENGLISH -> "Supportive"
            Language.NEPALI -> "सहयोगी"
        }
        KalachakraDashaCalculator.DehaJeevaRelationship.NEUTRAL -> when (language) {
            Language.ENGLISH -> "Neutral"
            Language.NEPALI -> "तटस्थ"
        }
        KalachakraDashaCalculator.DehaJeevaRelationship.CHALLENGING -> when (language) {
            Language.ENGLISH -> "Challenging"
            Language.NEPALI -> "चुनौतीपूर्ण"
        }
        KalachakraDashaCalculator.DehaJeevaRelationship.TRANSFORMATIVE -> when (language) {
            Language.ENGLISH -> "Transformative"
            Language.NEPALI -> "परिवर्तनकारी"
        }
    }
}

@Composable
private fun getHealthColor(health: KalachakraDashaCalculator.HealthIndicator): Color {
    return when (health) {
        KalachakraDashaCalculator.HealthIndicator.EXCELLENT -> AppTheme.SuccessColor
        KalachakraDashaCalculator.HealthIndicator.GOOD -> Color(0xFF81C784)
        KalachakraDashaCalculator.HealthIndicator.MODERATE -> AppTheme.InfoColor
        KalachakraDashaCalculator.HealthIndicator.CHALLENGING -> AppTheme.WarningColor
        KalachakraDashaCalculator.HealthIndicator.CRITICAL -> AppTheme.ErrorColor
    }
}

@Composable
private fun getRelationshipColor(relationship: KalachakraDashaCalculator.DehaJeevaRelationship): Color {
    return when (relationship) {
        KalachakraDashaCalculator.DehaJeevaRelationship.HARMONIOUS -> AppTheme.SuccessColor
        KalachakraDashaCalculator.DehaJeevaRelationship.SUPPORTIVE -> Color(0xFF81C784)
        KalachakraDashaCalculator.DehaJeevaRelationship.NEUTRAL -> AppTheme.InfoColor
        KalachakraDashaCalculator.DehaJeevaRelationship.CHALLENGING -> AppTheme.WarningColor
        KalachakraDashaCalculator.DehaJeevaRelationship.TRANSFORMATIVE -> AppTheme.AccentPrimary
    }
}

@Composable
private fun getApplicabilityColor(score: Int): Color {
    return when {
        score >= 70 -> AppTheme.SuccessColor
        score >= 50 -> AppTheme.InfoColor
        score >= 30 -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }
}

private fun formatNumber(number: Int, language: Language): String {
    return when (language) {
        Language.ENGLISH -> number.toString()
        Language.NEPALI -> BikramSambatConverter.toNepaliNumerals(number)
    }
}

private fun formatYearsLocalized(years: Int, language: Language): String {
    return when (language) {
        Language.ENGLISH -> if (years == 1) "1 year" else "$years years"
        Language.NEPALI -> "${BikramSambatConverter.toNepaliNumerals(years)} वर्ष"
    }
}

private fun formatRemainingYearsLocalized(years: Double, language: Language): String {
    if (years <= 0) return ""
    val wholeYears = years.toInt()
    val remainingMonths = ((years - wholeYears) * 12).toInt()

    return when (language) {
        Language.ENGLISH -> when {
            wholeYears > 0 && remainingMonths > 0 -> "${wholeYears}y ${remainingMonths}m remaining"
            wholeYears > 0 -> "${wholeYears}y remaining"
            remainingMonths > 0 -> "${remainingMonths}m remaining"
            else -> ""
        }
        Language.NEPALI -> when {
            wholeYears > 0 && remainingMonths > 0 -> "${BikramSambatConverter.toNepaliNumerals(wholeYears)} वर्ष ${BikramSambatConverter.toNepaliNumerals(remainingMonths)} महिना बाँकी"
            wholeYears > 0 -> "${BikramSambatConverter.toNepaliNumerals(wholeYears)} वर्ष बाँकी"
            remainingMonths > 0 -> "${BikramSambatConverter.toNepaliNumerals(remainingMonths)} महिना बाँकी"
            else -> ""
        }
    }
}

private fun formatRemainingDaysLocalized(days: Long, language: Language): String {
    if (days <= 0) return ""
    val months = days / 30
    val remainingDays = days % 30

    return when (language) {
        Language.ENGLISH -> when {
            months > 0 && remainingDays > 0 -> "${months}m ${remainingDays}d remaining"
            months > 0 -> "${months}m remaining"
            else -> "${remainingDays}d remaining"
        }
        Language.NEPALI -> when {
            months > 0 && remainingDays > 0 -> "${BikramSambatConverter.toNepaliNumerals(months.toInt())} महिना ${BikramSambatConverter.toNepaliNumerals(remainingDays.toInt())} दिन बाँकी"
            months > 0 -> "${BikramSambatConverter.toNepaliNumerals(months.toInt())} महिना बाँकी"
            else -> "${BikramSambatConverter.toNepaliNumerals(remainingDays.toInt())} दिन बाँकी"
        }
    }
}
