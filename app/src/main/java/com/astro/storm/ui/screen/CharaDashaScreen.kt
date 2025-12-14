package com.astro.storm.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import com.astro.storm.data.localization.DateFormat
import com.astro.storm.data.localization.DateSystem
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.LocalDateSystem
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.localization.formatDate
import com.astro.storm.data.localization.formatDateRange
import com.astro.storm.data.localization.formatDurationYearsMonths
import com.astro.storm.data.localization.formatRemainingDuration
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.CharaDashaCalculator
import com.astro.storm.ui.theme.AppTheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Chara Dasha Screen - Jaimini Sign-Based Dasha System
 *
 * Displays:
 * - Current Chara Dasha (sign-based) with dates
 * - Chara Karakas (8 Jaimini significators)
 * - Karakamsha (AK's Navamsa position)
 * - Complete dasha sequence
 * - Sign-wise dasha periods with interpretations
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharaDashaScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    val language = LocalLanguage.current
    val dateSystem = LocalDateSystem.current

    var isCalculating by remember { mutableStateOf(false) }
    var calculationError by remember { mutableStateOf<String?>(null) }
    var charaDashaResult by remember { mutableStateOf<CharaDashaCalculator.CharaDashaResult?>(null) }

    // Calculate Chara Dasha on chart change
    LaunchedEffect(chart) {
        if (chart != null) {
            isCalculating = true
            calculationError = null
            try {
                charaDashaResult = CharaDashaCalculator.calculateCharaDasha(chart, numberOfCycles = 3)
                isCalculating = false
            } catch (e: Exception) {
                calculationError = e.message ?: "Unknown error calculating Chara Dasha"
                isCalculating = false
            }
        }
    }

    // Tab state
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            CharaDashaTopBar(
                chartName = chart?.birthData?.name ?: stringResource(StringKeyMatch.MISC_UNKNOWN),
                currentSign = charaDashaResult?.currentMahadasha?.sign,
                language = language,
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
                targetState = Triple(isCalculating, calculationError, charaDashaResult),
                transitionSpec = {
                    fadeIn(animationSpec = tween(300)) togetherWith
                            fadeOut(animationSpec = tween(200))
                },
                label = "CharaDashaStateTransition",
                contentKey = { (calculating, error, result) ->
                    when {
                        calculating -> "loading"
                        error != null -> "error"
                        result != null -> "success"
                        else -> "idle"
                    }
                }
            ) { (calculating, error, result) ->
                when {
                    calculating -> CharaDashaLoadingContent()
                    error != null -> CharaDashaErrorContent(
                        message = error,
                        onRetry = {
                            if (chart != null) {
                                isCalculating = true
                                calculationError = null
                                try {
                                    charaDashaResult = CharaDashaCalculator.calculateCharaDasha(chart, numberOfCycles = 3)
                                    isCalculating = false
                                } catch (e: Exception) {
                                    calculationError = e.message ?: "Unknown error"
                                    isCalculating = false
                                }
                            }
                        }
                    )
                    result != null -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Tabs
                            ScrollableTabRow(
                                selectedTabIndex = selectedTabIndex,
                                containerColor = AppTheme.CardBackground,
                                contentColor = AppTheme.TextPrimary,
                                edgePadding = 16.dp,
                                indicator = {},
                                divider = {}
                            ) {
                                Tab(
                                    selected = selectedTabIndex == 0,
                                    onClick = { selectedTabIndex = 0 },
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 8.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (selectedTabIndex == 0) AppTheme.AccentPrimary.copy(alpha = 0.15f)
                                            else Color.Transparent
                                        )
                                ) {
                                    Text(
                                        text = stringResource(StringKeyDosha.CHARA_DASHA_CURRENT),
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                        color = if (selectedTabIndex == 0) AppTheme.AccentPrimary
                                        else AppTheme.TextMuted,
                                        fontWeight = if (selectedTabIndex == 0) FontWeight.SemiBold
                                        else FontWeight.Normal,
                                        fontSize = 14.sp
                                    )
                                }
                                Tab(
                                    selected = selectedTabIndex == 1,
                                    onClick = { selectedTabIndex = 1 },
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 8.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (selectedTabIndex == 1) AppTheme.AccentPrimary.copy(alpha = 0.15f)
                                            else Color.Transparent
                                        )
                                ) {
                                    Text(
                                        text = stringResource(StringKeyDosha.KARAKA_TITLE),
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                        color = if (selectedTabIndex == 1) AppTheme.AccentPrimary
                                        else AppTheme.TextMuted,
                                        fontWeight = if (selectedTabIndex == 1) FontWeight.SemiBold
                                        else FontWeight.Normal,
                                        fontSize = 14.sp
                                    )
                                }
                                Tab(
                                    selected = selectedTabIndex == 2,
                                    onClick = { selectedTabIndex = 2 },
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp, vertical = 8.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (selectedTabIndex == 2) AppTheme.AccentPrimary.copy(alpha = 0.15f)
                                            else Color.Transparent
                                        )
                                ) {
                                    Text(
                                        text = stringResource(StringKeyDosha.CHARA_DASHA_TIMELINE),
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                        color = if (selectedTabIndex == 2) AppTheme.AccentPrimary
                                        else AppTheme.TextMuted,
                                        fontWeight = if (selectedTabIndex == 2) FontWeight.SemiBold
                                        else FontWeight.Normal,
                                        fontSize = 14.sp
                                    )
                                }
                            }

                            // Tab content
                            when (selectedTabIndex) {
                                0 -> CurrentCharaDashaTab(result, language, dateSystem)
                                1 -> CharaKarakasTab(result, language)
                                2 -> CharaDashaTimelineTab(result, language, dateSystem)
                            }
                        }
                    }
                    chart == null -> CharaDashaEmptyContent(onBack = onBack)
                    else -> CharaDashaEmptyContent(onBack = onBack)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharaDashaTopBar(
    chartName: String,
    currentSign: ZodiacSign?,
    language: Language,
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
                        text = stringResource(StringKeyDosha.CHARA_DASHA_TITLE),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = if (currentSign != null) {
                            "${currentSign.getLocalizedName(language)} • $chartName"
                        } else {
                            chartName
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextMuted,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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

// ============================================
// TAB CONTENTS
// ============================================

@Composable
private fun CurrentCharaDashaTab(
    result: CharaDashaCalculator.CharaDashaResult,
    language: Language,
    dateSystem: DateSystem
) {
    val currentMaha = result.currentMahadasha
    val currentAntar = result.currentAntardasha

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Current Period Card
        item(key = "current_period") {
            CurrentCharaDashaPeriodCard(
                currentMaha = currentMaha,
                currentAntar = currentAntar,
                result = result,
                language = language,
                dateSystem = dateSystem
            )
        }

        // System Info Card
        item(key = "system_info") {
            CharaDashaSystemInfoCard(result = result, language = language)
        }

        // Interpretation Card
        if (currentMaha != null) {
            item(key = "interpretation") {
                CharaDashaInterpretationCard(
                    mahadasha = currentMaha,
                    language = language
                )
            }
        }

        // Karakamsha Card
        item(key = "karakamsha") {
            KarakamshaCard(result = result, language = language)
        }
    }
}

@Composable
private fun CharaKarakasTab(
    result: CharaDashaCalculator.CharaDashaResult,
    language: Language
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // About Karakas Card
        item(key = "about_karakas") {
            AboutKarakasCard(language = language)
        }

        // All 8 Karakas
        items(
            items = result.charaKarakas.toList(),
            key = { karaka -> karaka.karakaType.name }
        ) { karaka ->
            KarakaCard(karaka = karaka, language = language)
        }
    }
}

@Composable
private fun CharaDashaTimelineTab(
    result: CharaDashaCalculator.CharaDashaResult,
    language: Language,
    dateSystem: DateSystem
) {
    var expandedMahadashaIds by rememberSaveable { mutableStateOf(setOf<String>()) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Timeline Overview
        item(key = "timeline_overview") {
            CharaDashaTimelineOverviewCard(result = result, language = language)
        }

        // All Mahadashas
        itemsIndexed(
            items = result.mahadashas,
            key = { index, maha -> "${maha.sign.name}_${maha.startDate.toEpochDay()}_$index" }
        ) { index, mahadasha ->
            val mahadashaId = "${mahadasha.sign.name}_${mahadasha.startDate.toEpochDay()}"
            val isExpanded = mahadashaId in expandedMahadashaIds
            val isCurrent = mahadasha == result.currentMahadasha

            CharaMahadashaCard(
                mahadasha = mahadasha,
                isCurrent = isCurrent,
                isExpanded = isExpanded,
                language = language,
                dateSystem = dateSystem,
                onToggleExpand = { expanded ->
                    expandedMahadashaIds = if (expanded) {
                        expandedMahadashaIds + mahadashaId
                    } else {
                        expandedMahadashaIds - mahadashaId
                    }
                }
            )
        }
    }
}

// ============================================
// CARD COMPONENTS
// ============================================

@Composable
private fun CurrentCharaDashaPeriodCard(
    currentMaha: CharaDashaCalculator.CharaMahadasha?,
    currentAntar: CharaDashaCalculator.CharaAntardasha?,
    result: CharaDashaCalculator.CharaDashaResult,
    language: Language,
    dateSystem: DateSystem
) {
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
            // Header
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
                        text = stringResource(StringKeyDosha.CHARA_DASHA_CURRENT),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary,
                        letterSpacing = (-0.3).sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(StringKeyDosha.CHARA_DASHA_SUBTITLE),
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (currentMaha != null) {
                // Mahadasha
                SignDashaRow(
                    label = stringResource(StringKeyMatch.DASHA_LEVEL_MAHADASHA),
                    sign = currentMaha.sign,
                    lord = currentMaha.signLord,
                    startDate = currentMaha.startDate,
                    endDate = currentMaha.endDate,
                    progress = currentMaha.getProgressPercent().toFloat() / 100f,
                    remainingText = "${currentMaha.getRemainingDays()} ${stringResource(StringKeyMatch.MISC_DAYS_LEFT)}",
                    language = language,
                    dateSystem = dateSystem,
                    isLarger = true
                )

                currentAntar?.let { antar ->
                    Spacer(modifier = Modifier.height(12.dp))
                    SignDashaRow(
                        label = stringResource(StringKeyMatch.DASHA_LEVEL_ANTARDASHA),
                        sign = antar.sign,
                        lord = antar.signLord,
                        startDate = antar.startDate,
                        endDate = antar.endDate,
                        progress = antar.getProgressPercent().toFloat() / 100f,
                        remainingText = "${antar.durationMonths.toInt()} ${stringResource(StringKeyMatch.MISC_MONTHS)}",
                        language = language,
                        dateSystem = dateSystem,
                        isLarger = false
                    )
                }
            } else {
                Text(
                    text = stringResource(StringKeyMatch.DASHA_NO_CURRENT_PERIOD),
                    color = AppTheme.TextMuted,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun SignDashaRow(
    label: String,
    sign: ZodiacSign,
    lord: com.astro.storm.data.model.Planet,
    startDate: LocalDate,
    endDate: LocalDate,
    progress: Float,
    remainingText: String,
    language: Language,
    dateSystem: DateSystem,
    isLarger: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = label,
                    fontSize = if (isLarger) 13.sp else 12.sp,
                    color = AppTheme.TextMuted,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = sign.getLocalizedName(language),
                        fontSize = if (isLarger) 16.sp else 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "(${lord.getLocalizedName(language)})",
                        fontSize = if (isLarger) 13.sp else 12.sp,
                        color = AppTheme.TextSecondary
                    )
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = remainingText,
                    fontSize = if (isLarger) 13.sp else 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.AccentGold
                )
                Text(
                    text = formatDateRange(startDate, endDate),
                    fontSize = 11.sp,
                    color = AppTheme.TextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(if (isLarger) 6.dp else 5.dp)
                .clip(RoundedCornerShape(50)),
            color = AppTheme.AccentGold,
            trackColor = AppTheme.AccentGold.copy(alpha = 0.15f)
        )
    }
}

@Composable
private fun CharaDashaSystemInfoCard(
    result: CharaDashaCalculator.CharaDashaResult,
    language: Language
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = null,
                    tint = AppTheme.AccentTeal,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(StringKeyDosha.CHARA_DASHA_ABOUT),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            InfoRow(
                label = stringResource(StringKeyAnalysis.CHART_LAGNA),
                value = result.lagnaSign.getLocalizedName(language)
            )
            InfoRow(
                label = stringResource(StringKeyDosha.CHARA_DASHA_DIRECTION),
                value = if (result.isOddLagna) {
                    stringResource(StringKeyDosha.CHARA_DASHA_DIRECT)
                } else {
                    stringResource(StringKeyDosha.CHARA_DASHA_REVERSE)
                }
            )
            InfoRow(
                label = "Type",
                value = if (result.isOddLagna) {
                    stringResource(StringKeyDosha.CHARA_DASHA_ODD_LAGNA)
                } else {
                    stringResource(StringKeyDosha.CHARA_DASHA_EVEN_LAGNA)
                }
            )
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = AppTheme.TextMuted
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = AppTheme.TextPrimary
        )
    }
}

@Composable
private fun CharaDashaInterpretationCard(
    mahadasha: CharaDashaCalculator.CharaMahadasha,
    language: Language
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "rotation"
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = AppTheme.CardBackground
    ) {
        Column(
            modifier = Modifier
                .animateContentSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple()
                    ) { isExpanded = !isExpanded },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Star,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(StringKeyMatch.INTERPRETATION_TITLE),
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
                        .rotate(rotationAngle)
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(
                        color = AppTheme.DividerColor,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    InterpretationSection(
                        title = stringResource(StringKeyMatch.INTERPRETATION_GENERAL),
                        content = mahadasha.interpretation.generalEffects
                    )

                    InterpretationSection(
                        title = stringResource(StringKeyMatch.INTERPRETATION_LORD_EFFECTS),
                        content = mahadasha.interpretation.signLordEffects
                    )

                    if (mahadasha.interpretation.karakaActivation.isNotEmpty()) {
                        InterpretationSection(
                            title = "Karaka Activation",
                            items = mahadasha.interpretation.karakaActivation
                        )
                    }

                    InterpretationSection(
                        title = stringResource(StringKeyMatch.INTERPRETATION_FAVORABLE),
                        items = mahadasha.interpretation.favorableAreas
                    )

                    InterpretationSection(
                        title = stringResource(StringKeyMatch.INTERPRETATION_CHALLENGES),
                        items = mahadasha.interpretation.challengingAreas
                    )

                    InterpretationSection(
                        title = stringResource(StringKeyMatch.INTERPRETATION_RECOMMENDATIONS),
                        items = mahadasha.interpretation.recommendations
                    )
                }
            }
        }
    }
}

@Composable
private fun InterpretationSection(
    title: String,
    content: String? = null,
    items: List<String>? = null
) {
    Column(modifier = Modifier.padding(bottom = 12.dp)) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.AccentSecondary
        )
        Spacer(modifier = Modifier.height(6.dp))

        content?.let {
            Text(
                text = it,
                fontSize = 13.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 18.sp
            )
        }

        items?.forEach { item ->
            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                Text(
                    text = "• ",
                    fontSize = 13.sp,
                    color = AppTheme.AccentGold
                )
                Text(
                    text = item,
                    fontSize = 13.sp,
                    color = AppTheme.TextSecondary,
                    modifier = Modifier.weight(1f),
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun KarakamshaCard(
    result: CharaDashaCalculator.CharaDashaResult,
    language: Language
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            AppTheme.AccentGold.copy(alpha = 0.15f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = result.karakamsha.symbol,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.AccentGold
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = stringResource(StringKeyDosha.KARAKAMSHA_TITLE),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = result.karakamsha.getLocalizedName(language),
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(StringKeyDosha.KARAKAMSHA_DESC),
                fontSize = 12.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = result.interpretation.karakamshaAnalysis,
                fontSize = 13.sp,
                color = AppTheme.TextPrimary,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun AboutKarakasCard(language: Language) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = null,
                    tint = AppTheme.AccentTeal,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(StringKeyDosha.KARAKA_ABOUT),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(StringKeyDosha.KARAKA_ABOUT_DESC),
                fontSize = 13.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun KarakaCard(
    karaka: CharaDashaCalculator.KarakaInfo,
    language: Language
) {
    val (karakaKey, descKey) = when (karaka.karakaType) {
        CharaDashaCalculator.KarakaType.ATMAKARAKA -> StringKeyDosha.KARAKA_AK to StringKeyDosha.KARAKA_AK_DESC
        CharaDashaCalculator.KarakaType.AMATYAKARAKA -> StringKeyDosha.KARAKA_AMK to StringKeyDosha.KARAKA_AMK_DESC
        CharaDashaCalculator.KarakaType.BHRATRIKARAKA -> StringKeyDosha.KARAKA_BK to StringKeyDosha.KARAKA_BK_DESC
        CharaDashaCalculator.KarakaType.MATRIKARAKA -> StringKeyDosha.KARAKA_MK to StringKeyDosha.KARAKA_MK_DESC
        CharaDashaCalculator.KarakaType.PITRIKARAKA -> StringKeyDosha.KARAKA_PIK to StringKeyDosha.KARAKA_PIK_DESC
        CharaDashaCalculator.KarakaType.PUTRAKARAKA -> StringKeyDosha.KARAKA_PUK to StringKeyDosha.KARAKA_PUK_DESC
        CharaDashaCalculator.KarakaType.GNATIKARAKA -> StringKeyDosha.KARAKA_GK to StringKeyDosha.KARAKA_GK_DESC
        CharaDashaCalculator.KarakaType.DARAKARAKA -> StringKeyDosha.KARAKA_DK to StringKeyDosha.KARAKA_DK_DESC
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                AppTheme.getPlanetColor(karaka.planet).copy(alpha = 0.15f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = karaka.planet.symbol,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.getPlanetColor(karaka.planet)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = stringResource(karakaKey),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            text = karaka.planet.getLocalizedName(language),
                            fontSize = 12.sp,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = String.format("%.2f°", karaka.degreeInSign),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.AccentGold
                    )
                    Text(
                        text = karaka.sign.getLocalizedName(language),
                        fontSize = 11.sp,
                        color = AppTheme.TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(descKey),
                fontSize = 13.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        AppTheme.CardBackgroundElevated,
                        RoundedCornerShape(8.dp)
                    )
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Navamsa:",
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted
                )
                Text(
                    text = karaka.navamsaSign.getLocalizedName(language),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
            }
        }
    }
}

@Composable
private fun CharaDashaTimelineOverviewCard(
    result: CharaDashaCalculator.CharaDashaResult,
    language: Language
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Timeline,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(StringKeyDosha.CHARA_DASHA_TIMELINE),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = result.interpretation.systemOverview,
                fontSize = 13.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun CharaMahadashaCard(
    mahadasha: CharaDashaCalculator.CharaMahadasha,
    isCurrent: Boolean,
    isExpanded: Boolean,
    language: Language,
    dateSystem: DateSystem,
    onToggleExpand: (Boolean) -> Unit
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "rotation"
    )

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = if (isCurrent) {
            AppTheme.AccentGold.copy(alpha = 0.08f)
        } else {
            AppTheme.CardBackground
        },
        border = if (isCurrent) {
            androidx.compose.foundation.BorderStroke(1.dp, AppTheme.AccentGold.copy(alpha = 0.3f))
        } else null
    ) {
        Column(
            modifier = Modifier
                .animateContentSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple()
                    ) { onToggleExpand(!isExpanded) },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                if (isCurrent) AppTheme.AccentGold.copy(alpha = 0.2f)
                                else AppTheme.CardBackgroundElevated,
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = mahadasha.sign.symbol,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCurrent) AppTheme.AccentGold else AppTheme.TextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = mahadasha.sign.getLocalizedName(language),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = AppTheme.TextPrimary
                            )
                            if (isCurrent) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(
                                            AppTheme.AccentGold,
                                            RoundedCornerShape(4.dp)
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = stringResource(StringKeyMatch.MISC_CURRENT),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AppTheme.ScreenBackground
                                    )
                                }
                            }
                        }
                        Text(
                            text = "${mahadasha.signLord.getLocalizedName(language)} • ${mahadasha.durationYears} ${stringResource(StringKeyMatch.MISC_YEARS)}",
                            fontSize = 12.sp,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Icon(
                    Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotationAngle)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = formatDateRange(mahadasha.startDate, mahadasha.endDate),
                fontSize = 12.sp,
                color = AppTheme.TextMuted
            )

            if (isCurrent) {
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { mahadasha.getProgressPercent().toFloat() / 100f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(5.dp)
                        .clip(RoundedCornerShape(50)),
                    color = AppTheme.AccentGold,
                    trackColor = AppTheme.AccentGold.copy(alpha = 0.15f)
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(
                        color = AppTheme.DividerColor,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Special significance
                    if (mahadasha.specialSignificance.isNotEmpty()) {
                        Text(
                            text = mahadasha.specialSignificance,
                            fontSize = 12.sp,
                            color = AppTheme.TextMuted,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                    // General effects
                    Text(
                        text = mahadasha.interpretation.generalEffects,
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Antardashas
                    if (mahadasha.antardashas.isNotEmpty()) {
                        Text(
                            text = stringResource(StringKeyMatch.DASHA_LEVEL_ANTARDASHAS),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.AccentSecondary,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        mahadasha.antardashas.take(12).forEach { antar ->
                            AntardashaRow(
                                antardasha = antar,
                                language = language,
                                dateSystem = dateSystem
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AntardashaRow(
    antardasha: CharaDashaCalculator.CharaAntardasha,
    language: Language,
    dateSystem: DateSystem
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                AppTheme.CardBackgroundElevated,
                RoundedCornerShape(8.dp)
            )
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        AppTheme.AccentPrimary.copy(alpha = 0.15f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = antardasha.sign.symbol,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.AccentPrimary
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = antardasha.sign.getLocalizedName(language),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
                Text(
                    text = formatDateRange(antardasha.startDate, antardasha.endDate),
                    fontSize = 10.sp,
                    color = AppTheme.TextMuted
                )
            }
        }

        Text(
            text = "${antardasha.durationMonths.toInt()}m",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = AppTheme.TextSecondary
        )
    }
    Spacer(modifier = Modifier.height(6.dp))
}

// ============================================
// LOADING & ERROR STATES
// ============================================

@Composable
private fun CharaDashaLoadingContent() {
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
                text = stringResource(StringKey.DASHA_CALCULATING),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Calculating Chara Dasha periods...",
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun CharaDashaErrorContent(
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
private fun CharaDashaEmptyContent(onBack: () -> Unit) {
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
