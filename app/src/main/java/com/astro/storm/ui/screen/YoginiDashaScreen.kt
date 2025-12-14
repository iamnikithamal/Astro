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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.PersonOff
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Star
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
import com.astro.storm.data.localization.LocalDateSystem
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.localization.formatLocalized
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.YoginiDashaCalculator
import com.astro.storm.ui.theme.AppTheme
import com.astro.storm.ui.viewmodel.YoginiDashaUiState
import com.astro.storm.ui.viewmodel.YoginiDashaViewModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YoginiDashaScreen(
    chart: VedicChart?,
    onBack: () -> Unit,
    viewModel: YoginiDashaViewModel = viewModel()
) {
    val chartKey = remember(chart) {
        chart?.generateUniqueKey()
    }

    LaunchedEffect(chartKey) {
        viewModel.loadYoginiDasha(chart)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val language = LocalLanguage.current

    val currentPeriodInfo = remember(uiState, language) {
        extractCurrentYoginiPeriodInfo(uiState, language)
    }

    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            YoginiDashaTopBar(
                chartName = chart?.birthData?.name ?: stringResource(StringKey.MISC_UNKNOWN),
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
                label = "YoginiDashaStateTransition",
                contentKey = { state -> state::class.simpleName }
            ) { state ->
                when (state) {
                    is YoginiDashaUiState.Loading -> {
                        YoginiDashaLoadingContent()
                    }
                    is YoginiDashaUiState.Success -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            YoginiDashaTabRow(
                                selectedTab = selectedTab,
                                onTabSelected = { selectedTab = it }
                            )
                            when (selectedTab) {
                                0 -> CurrentYoginiTab(result = state.result)
                                1 -> TimelineYoginiTab(result = state.result)
                                2 -> DetailsYoginiTab(result = state.result)
                            }
                        }
                    }
                    is YoginiDashaUiState.Error -> {
                        YoginiDashaErrorContent(
                            message = state.message,
                            onRetry = { viewModel.loadYoginiDasha(chart) }
                        )
                    }
                    is YoginiDashaUiState.Idle -> {
                        YoginiDashaEmptyContent(onBack = onBack)
                    }
                }
            }
        }
    }
}

private fun VedicChart.generateUniqueKey(): String {
    return buildString {
        append(birthData.dateTime.toString())
        append("|")
        append(String.format("%.6f", birthData.latitude))
        append("|")
        append(String.format("%.6f", birthData.longitude))
    }
}

private data class CurrentYoginiPeriodInfo(
    val mahadasha: String?,
    val antardasha: String?,
    val isLoading: Boolean,
    val hasError: Boolean
)

private fun extractCurrentYoginiPeriodInfo(
    uiState: YoginiDashaUiState,
    language: Language
): CurrentYoginiPeriodInfo {
    return when (uiState) {
        is YoginiDashaUiState.Success -> {
            val md = uiState.result.currentMahadasha
            val ad = uiState.result.currentAntardasha
            CurrentYoginiPeriodInfo(
                mahadasha = md?.yogini?.let { getYoginiLocalizedName(it, language) },
                antardasha = ad?.yogini?.let { getYoginiLocalizedName(it, language) },
                isLoading = false,
                hasError = false
            )
        }
        is YoginiDashaUiState.Loading -> CurrentYoginiPeriodInfo(null, null, true, false)
        is YoginiDashaUiState.Error -> CurrentYoginiPeriodInfo(null, null, false, true)
        is YoginiDashaUiState.Idle -> CurrentYoginiPeriodInfo(null, null, false, false)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun YoginiDashaTopBar(
    chartName: String,
    currentPeriodInfo: CurrentYoginiPeriodInfo,
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
                        text = stringResource(StringKeyDosha.YOGINI_DASHA_TITLE),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    TopBarSubtitle(
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
private fun TopBarSubtitle(
    chartName: String,
    periodInfo: CurrentYoginiPeriodInfo
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
private fun YoginiDashaTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(
        stringResource(StringKeyDosha.YOGINI_DASHA_CURRENT),
        stringResource(StringKeyDosha.YOGINI_DASHA_TIMELINE),
        stringResource(StringKeyMatch.DETAILS)
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

@Composable
private fun CurrentYoginiTab(result: YoginiDashaCalculator.YoginiDashaResult) {
    val language = LocalLanguage.current
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "current_period") {
            CurrentYoginiPeriodCard(result = result)
        }

        item(key = "birth_balance") {
            BirthBalanceCard(balance = result.balanceAtBirth, language = language)
        }

        item(key = "applicability") {
            ApplicabilityCard(applicability = result.applicability, language = language)
        }

        item(key = "yogini_sequence") {
            YoginiSequenceCard(startingYogini = result.startingYogini, language = language)
        }

        item(key = "about_yogini") {
            AboutYoginiDashaCard()
        }

        item(key = "bottom_spacer") {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun TimelineYoginiTab(result: YoginiDashaCalculator.YoginiDashaResult) {
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
            key = { mahadasha -> "${mahadasha.yogini.ordinal}_${mahadasha.startDate.toEpochDay()}" }
        ) { mahadasha ->
            YoginiMahadashaCard(
                mahadasha = mahadasha,
                isCurrent = mahadasha == result.currentMahadasha,
                currentAntardasha = if (mahadasha == result.currentMahadasha) result.currentAntardasha else null,
                language = language
            )
        }

        item(key = "bottom_spacer") {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DetailsYoginiTab(result: YoginiDashaCalculator.YoginiDashaResult) {
    val language = LocalLanguage.current
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = YoginiDashaCalculator.Yogini.entries,
            key = { it.ordinal }
        ) { yogini ->
            YoginiDetailCard(yogini = yogini, language = language)
        }

        item(key = "bottom_spacer") {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun CurrentYoginiPeriodCard(result: YoginiDashaCalculator.YoginiDashaResult) {
    val language = LocalLanguage.current
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
                        text = stringResource(StringKeyDosha.YOGINI_DASHA_CURRENT),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(StringKeyDosha.YOGINI_DASHA_SUBTITLE),
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (currentMahadasha != null) {
                YoginiPeriodRow(
                    label = stringResource(StringKey.DASHA_MAHADASHA),
                    yogini = currentMahadasha.yogini,
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
                    YoginiPeriodRow(
                        label = stringResource(StringKeyDosha.YOGINI_DASHA_ANTARDASHA),
                        yogini = ad.yogini,
                        startDate = ad.startDate,
                        endDate = ad.endDate,
                        progress = ad.getProgressPercent().toFloat() / 100f,
                        remainingText = formatRemainingDaysLocalized(ad.getRemainingDays(), language),
                        isLarge = false,
                        language = language
                    )
                }

                HorizontalDivider(
                    color = AppTheme.DividerColor,
                    modifier = Modifier.padding(vertical = 18.dp)
                )

                CurrentYoginiEffects(
                    mahadasha = currentMahadasha,
                    antardasha = currentAntardasha
                )
            } else {
                EmptyYoginiState()
            }
        }
    }
}

@Composable
private fun YoginiPeriodRow(
    label: String,
    yogini: YoginiDashaCalculator.Yogini,
    startDate: LocalDate,
    endDate: LocalDate,
    progress: Float,
    remainingText: String,
    isLarge: Boolean,
    language: Language
) {
    val yoginiColor = getYoginiColor(yogini)
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
                    .background(yoginiColor, CircleShape)
                    .border(2.dp, yoginiColor.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = yogini.planet.symbol,
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
                        text = getYoginiLocalizedName(yogini, language),
                        fontSize = mainFontSize,
                        fontWeight = if (isLarge) FontWeight.Bold else FontWeight.SemiBold,
                        color = yoginiColor
                    )
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
                color = yoginiColor
            )
            Spacer(modifier = Modifier.height(5.dp))
            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(progressHeight)
                    .clip(RoundedCornerShape(progressHeight / 2)),
                color = yoginiColor,
                trackColor = AppTheme.DividerColor
            )
        }
    }
}

@Composable
private fun CurrentYoginiEffects(
    mahadasha: YoginiDashaCalculator.YoginiMahadasha,
    antardasha: YoginiDashaCalculator.YoginiAntardasha?
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = AppTheme.CardBackgroundElevated
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Star,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(StringKeyDosha.YOGINI_GENERAL_EFFECTS),
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

            if (antardasha != null) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = AppTheme.DividerColor)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = antardasha.interpretation,
                    fontSize = 12.sp,
                    color = AppTheme.TextSecondary,
                    lineHeight = 18.sp,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }
}

@Composable
private fun BirthBalanceCard(
    balance: YoginiDashaCalculator.BalanceAtBirth,
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
                        .background(AppTheme.AccentTeal.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Timeline,
                        contentDescription = null,
                        tint = AppTheme.AccentTeal,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = stringResource(StringKeyDosha.YOGINI_DASHA_BALANCE),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(
                    label = stringResource(StringKeyDosha.YOGINI_DASHA_CURRENT),
                    value = getYoginiLocalizedName(balance.yogini, language),
                    color = getYoginiColor(balance.yogini)
                )
                InfoItem(
                    label = stringResource(StringKey.DASHA_DURATION),
                    value = formatYearsLocalized(balance.totalYears, language),
                    color = AppTheme.TextSecondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoItem(
                    label = stringResource(StringKeyDosha.YOGINI_DASHA_BALANCE),
                    value = "${formatNumber(balance.balanceDays.toInt(), language)} ${stringResource(StringKey.DAYS)}",
                    color = AppTheme.AccentTeal
                )
                InfoItem(
                    label = stringResource(StringKey.DASHA_PROGRESS),
                    value = String.format("%.1f%%", (balance.elapsed / balance.totalYears) * 100),
                    color = AppTheme.TextMuted
                )
            }
        }
    }
}

@Composable
private fun ApplicabilityCard(
    applicability: YoginiDashaCalculator.Applicability,
    language: Language
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = if (applicability.isRecommended) {
            AppTheme.SuccessColor.copy(alpha = 0.08f)
        } else {
            AppTheme.InfoColor.copy(alpha = 0.08f)
        }
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = null,
                    tint = if (applicability.isRecommended) AppTheme.SuccessColor else AppTheme.InfoColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(StringKeyDosha.YOGINI_DASHA_APPLICABILITY),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (applicability.isRecommended) {
                Text(
                    text = stringResource(StringKeyDosha.YOGINI_DASHA_RECOMMENDED),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.SuccessColor
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            applicability.reasons.forEach { reason ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "•",
                        fontSize = 12.sp,
                        color = AppTheme.TextSecondary,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = reason,
                        fontSize = 12.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${stringResource(StringKey.MUHURTA_SCORE)}: ${String.format("%.0f", applicability.applicabilityScore * 100)}%",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun YoginiSequenceCard(
    startingYogini: YoginiDashaCalculator.Yogini,
    language: Language
) {
    val sequence = YoginiDashaCalculator.getYoginiSequence(startingYogini)

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = stringResource(StringKeyDosha.YOGINI_DASHA_SEQUENCE),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            sequence.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    row.forEach { yogini ->
                        YoginiSequenceItem(yogini = yogini, language = language)
                    }
                    if (row.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun YoginiSequenceItem(
    yogini: YoginiDashaCalculator.Yogini,
    language: Language
) {
    Surface(
        modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        color = AppTheme.CardBackgroundElevated
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(getYoginiColor(yogini).copy(alpha = 0.15f), CircleShape)
                    .border(1.dp, getYoginiColor(yogini).copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = yogini.planet.symbol,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = getYoginiColor(yogini)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = getYoginiLocalizedName(yogini, language),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
                Text(
                    text = formatYearsLocalized(yogini.years, language),
                    fontSize = 10.sp,
                    color = AppTheme.TextMuted
                )
            }
        }
    }
}

@Composable
private fun TimelineHeaderCard(
    result: YoginiDashaCalculator.YoginiDashaResult,
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
                        text = stringResource(StringKeyDosha.YOGINI_DASHA_TIMELINE),
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
private fun YoginiMahadashaCard(
    mahadasha: YoginiDashaCalculator.YoginiMahadasha,
    isCurrent: Boolean,
    currentAntardasha: YoginiDashaCalculator.YoginiAntardasha?,
    language: Language
) {
    var isExpanded by rememberSaveable { mutableStateOf(isCurrent) }
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 250),
        label = "expand_rotation"
    )

    val yoginiColor = getYoginiColor(mahadasha.yogini)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = yoginiColor.copy(alpha = 0.3f))
            ) { isExpanded = !isExpanded },
        shape = RoundedCornerShape(18.dp),
        color = if (isCurrent) {
            yoginiColor.copy(alpha = 0.08f)
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
                            .background(yoginiColor, CircleShape)
                            .then(
                                if (isCurrent) {
                                    Modifier.border(2.5.dp, yoginiColor.copy(alpha = 0.4f), CircleShape)
                                } else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = mahadasha.yogini.planet.symbol,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = getYoginiLocalizedName(mahadasha.yogini, language),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                            if (isCurrent) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(5.dp),
                                    color = yoginiColor.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = stringResource(StringKey.DASHA_ACTIVE),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = yoginiColor,
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
                        YoginiAntardashaRow(
                            antardasha = antardasha,
                            isCurrent = antardasha == currentAntardasha,
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
private fun YoginiAntardashaRow(
    antardasha: YoginiDashaCalculator.YoginiAntardasha,
    isCurrent: Boolean,
    language: Language
) {
    val yoginiColor = getYoginiColor(antardasha.yogini)
    val today = LocalDate.now()
    val isPast = antardasha.endDate.isBefore(today)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isCurrent) yoginiColor.copy(alpha = 0.12f) else Color.Transparent,
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
                        color = if (isPast) yoginiColor.copy(alpha = 0.4f) else yoginiColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = antardasha.yogini.planet.symbol,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = getYoginiLocalizedName(antardasha.yogini, language),
                        fontSize = 13.sp,
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                        color = when {
                            isCurrent -> yoginiColor
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
                            color = yoginiColor.copy(alpha = 0.9f)
                        )
                    }
                }
                if (isCurrent) {
                    val remaining = formatRemainingDaysLocalized(antardasha.getRemainingDays(), language)
                    if (remaining.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = remaining,
                            fontSize = 10.sp,
                            color = AppTheme.AccentTeal,
                            fontWeight = FontWeight.Medium
                        )
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

@Composable
private fun YoginiDetailCard(
    yogini: YoginiDashaCalculator.Yogini,
    language: Language
) {
    var isExpanded by rememberSaveable(key = "yogini_detail_${yogini.ordinal}") { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(durationMillis = 250),
        label = "detail_rotation"
    )

    val yoginiColor = getYoginiColor(yogini)
    val natureColor = getNatureColor(yogini.nature)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = yoginiColor.copy(alpha = 0.3f))
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
                            .size(52.dp)
                            .background(yoginiColor.copy(alpha = 0.15f), CircleShape)
                            .border(2.dp, yoginiColor.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = yogini.planet.symbol,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = yoginiColor
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = getYoginiLocalizedName(yogini, language),
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = yogini.planet.getLocalizedName(language),
                                fontSize = 12.sp,
                                color = AppTheme.TextMuted
                            )
                            Text(
                                text = " • ",
                                fontSize = 12.sp,
                                color = AppTheme.TextMuted
                            )
                            Text(
                                text = formatYearsLocalized(yogini.years, language),
                                fontSize = 12.sp,
                                color = AppTheme.TextMuted
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = natureColor.copy(alpha = 0.15f)
                        ) {
                            Text(
                                text = getNatureLocalizedName(yogini.nature, language),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = natureColor,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
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

                    // Get interpretation (mock data for now - in real implementation this would come from calculator)
                    val interpretation = remember(yogini) {
                        YoginiDashaCalculator.YoginiInterpretation(
                            generalEffects = yogini.description,
                            careerEffects = "Career-related effects for ${yogini.displayName}",
                            relationshipEffects = "Relationship effects for ${yogini.displayName}",
                            healthEffects = "Health considerations for ${yogini.displayName}",
                            spiritualEffects = "Spiritual growth during ${yogini.displayName}",
                            recommendations = listOf(
                                "Honor the presiding deity",
                                "Practice specific remedies",
                                "Wear appropriate gemstone if suitable"
                            ),
                            cautionAreas = listOf(
                                "Be aware of potential challenges",
                                "Maintain balance and moderation"
                            )
                        )
                    }

                    EffectSection(
                        title = stringResource(StringKeyDosha.YOGINI_CAREER_EFFECTS),
                        text = interpretation.careerEffects
                    )

                    EffectSection(
                        title = stringResource(StringKeyDosha.YOGINI_RELATIONSHIP_EFFECTS),
                        text = interpretation.relationshipEffects
                    )

                    EffectSection(
                        title = stringResource(StringKeyDosha.YOGINI_HEALTH_EFFECTS),
                        text = interpretation.healthEffects
                    )

                    EffectSection(
                        title = stringResource(StringKeyDosha.YOGINI_SPIRITUAL_EFFECTS),
                        text = interpretation.spiritualEffects
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(StringKeyMatch.DASHA_RECOMMENDATIONS),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.AccentGold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    interpretation.recommendations.forEach { rec ->
                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                            Text("• ", fontSize = 12.sp, color = AppTheme.TextSecondary)
                            Text(
                                text = rec,
                                fontSize = 12.sp,
                                color = AppTheme.TextSecondary,
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = stringResource(StringKeyDosha.YOGINI_CAUTION_AREAS),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.WarningColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    interpretation.cautionAreas.forEach { caution ->
                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                            Text("• ", fontSize = 12.sp, color = AppTheme.TextSecondary)
                            Text(
                                text = caution,
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
private fun EffectSection(title: String, text: String) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.AccentTeal
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = AppTheme.TextSecondary,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun AboutYoginiDashaCard() {
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
                        text = stringResource(StringKeyDosha.YOGINI_DASHA_ABOUT),
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
                        text = stringResource(StringKeyDosha.YOGINI_DASHA_ABOUT_DESC),
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

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
private fun EmptyYoginiState() {
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
private fun YoginiDashaLoadingContent() {
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
private fun YoginiDashaErrorContent(
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
private fun YoginiDashaEmptyContent(onBack: () -> Unit) {
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

private fun getYoginiLocalizedName(yogini: YoginiDashaCalculator.Yogini, language: Language): String {
    return when (language) {
        Language.ENGLISH -> yogini.displayName
        Language.NEPALI -> yogini.sanskrit
    }
}

private fun getNatureLocalizedName(nature: YoginiDashaCalculator.YoginiNature, language: Language): String {
    return when (nature) {
        YoginiDashaCalculator.YoginiNature.HIGHLY_AUSPICIOUS -> when (language) {
            Language.ENGLISH -> "Highly Auspicious"
            Language.NEPALI -> "अत्यन्त शुभ"
        }
        YoginiDashaCalculator.YoginiNature.AUSPICIOUS -> when (language) {
            Language.ENGLISH -> "Auspicious"
            Language.NEPALI -> "शुभ"
        }
        YoginiDashaCalculator.YoginiNature.MIXED -> when (language) {
            Language.ENGLISH -> "Mixed"
            Language.NEPALI -> "मिश्रित"
        }
        YoginiDashaCalculator.YoginiNature.CHALLENGING -> when (language) {
            Language.ENGLISH -> "Challenging"
            Language.NEPALI -> "चुनौतीपूर्ण"
        }
        YoginiDashaCalculator.YoginiNature.DIFFICULT -> when (language) {
            Language.ENGLISH -> "Difficult"
            Language.NEPALI -> "कठिन"
        }
    }
}

private fun getYoginiColor(yogini: YoginiDashaCalculator.Yogini): Color {
    return AppTheme.getPlanetColor(yogini.planet)
}

private fun getNatureColor(nature: YoginiDashaCalculator.YoginiNature): Color {
    return when (nature) {
        YoginiDashaCalculator.YoginiNature.HIGHLY_AUSPICIOUS -> AppTheme.SuccessColor
        YoginiDashaCalculator.YoginiNature.AUSPICIOUS -> Color(0xFF81C784)
        YoginiDashaCalculator.YoginiNature.MIXED -> AppTheme.InfoColor
        YoginiDashaCalculator.YoginiNature.CHALLENGING -> AppTheme.WarningColor
        YoginiDashaCalculator.YoginiNature.DIFFICULT -> AppTheme.ErrorColor
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
