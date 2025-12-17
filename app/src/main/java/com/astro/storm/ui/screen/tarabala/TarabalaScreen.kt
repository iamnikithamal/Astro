package com.astro.storm.ui.screen.tarabala

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.currentLanguage
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.TarabalaCalculator
import com.astro.storm.ui.screen.EmptyChartScreen
import com.astro.storm.ui.theme.AppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Tarabala & Chandrabala Analysis Screen
 *
 * Comprehensive daily strength analysis featuring:
 * - Today's Tarabala (9-fold nakshatra cycle)
 * - Today's Chandrabala (Moon transit strength)
 * - Combined daily strength score
 * - Weekly forecast with best/worst days
 * - Activity recommendations
 * - All 27 nakshatra Tarabala mappings
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarabalaScreen(
    context: Context,
    chart: VedicChart?,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = stringResource(StringKeyAnalysis.TARABALA_TITLE),
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = currentLanguage()
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var expandedDays by remember { mutableStateOf(setOf<String>()) }

    val tabs = listOf(
        stringResource(StringKeyAnalysis.TARABALA_TODAY),
        stringResource(StringKeyAnalysis.TARABALA_WEEKLY),
        stringResource(StringKeyAnalysis.TARABALA_ALL_NAKSHATRAS)
    )

    // Calculate Tarabala analysis
    val tarabalaAnalysis = remember(chart) {
        TarabalaCalculator.calculateAnalysis(context, chart, LocalDateTime.now())
    }

    if (showInfoDialog) {
        TarabalaInfoDialog(onDismiss = { showInfoDialog = false })
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(StringKeyAnalysis.TARABALA_TITLE),
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary,
                            fontSize = 18.sp
                        )
                        Text(
                            chart.birthData.name,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(StringKey.BTN_BACK),
                            tint = AppTheme.TextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showInfoDialog = true }) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = stringResource(StringKeyAnalysis.TARABALA_WHAT_IS),
                            tint = AppTheme.TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.ScreenBackground
                )
            )
        }
    ) { paddingValues ->
        if (tarabalaAnalysis == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    stringResource(StringKeyAnalysis.TARABALA_UNABLE_CALCULATE),
                    color = AppTheme.TextMuted
                )
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppTheme.ScreenBackground),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Tab selector
            item {
                TarabalaTabSelector(
                    tabs = tabs,
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }

            // Tab content
            when (selectedTab) {
                0 -> item {
                    TarabalaTodayTab(
                        analysis = tarabalaAnalysis,
                        language = language
                    )
                }
                1 -> item {
                    TarabalaWeeklyTab(
                        analysis = tarabalaAnalysis,
                        expandedDays = expandedDays,
                        onToggleDay = { day ->
                            expandedDays = if (day in expandedDays) {
                                expandedDays - day
                            } else {
                                expandedDays + day
                            }
                        },
                        language = language
                    )
                }
                2 -> item {
                    TarabalaAllNakshatrasTab(
                        analysis = tarabalaAnalysis,
                        language = language
                    )
                }
            }
        }
    }
}

// ============================================
// Tab Selector
// ============================================

@Composable
private fun TarabalaTabSelector(
    tabs: List<String>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tabs.size) { index ->
            val isSelected = selectedTab == index
            FilterChip(
                selected = isSelected,
                onClick = { onTabSelected(index) },
                label = {
                    Text(
                        tabs[index],
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AppTheme.AccentPrimary.copy(alpha = 0.15f),
                    selectedLabelColor = AppTheme.AccentPrimary,
                    containerColor = AppTheme.ChipBackground,
                    labelColor = AppTheme.TextSecondary
                )
            )
        }
    }
}

// ============================================
// Today Tab - Main Daily Strength View
// ============================================

@Composable
private fun TarabalaTodayTab(
    analysis: TarabalaCalculator.TarabalaChandrabalaAnalysis,
    language: Language
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Overall Score Card
        DailyStrengthScoreCard(
            todayStrength = analysis.todayStrength,
            language = language
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tarabala Card
        TarabalaDetailCard(
            tarabala = analysis.currentTarabala,
            birthNakshatra = analysis.birthNakshatra,
            language = language
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Chandrabala Card
        ChandrabalaDetailCard(
            chandrabala = analysis.currentChandrabala,
            language = language
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Activity Recommendations
        ActivityRecommendationsCard(
            todayStrength = analysis.todayStrength,
            language = language
        )
    }
}

@Composable
private fun DailyStrengthScoreCard(
    todayStrength: TarabalaCalculator.DailyStrengthResult,
    language: Language
) {
    val scoreColor = when {
        todayStrength.overallScore >= 70 -> AppTheme.SuccessColor
        todayStrength.overallScore >= 50 -> AppTheme.AccentTeal
        todayStrength.overallScore >= 30 -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = scoreColor.copy(alpha = 0.15f),
                spotColor = scoreColor.copy(alpha = 0.15f)
            ),
        shape = RoundedCornerShape(20.dp),
        color = AppTheme.CardBackground
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(StringKeyAnalysis.TARABALA_TODAY_STRENGTH),
                style = MaterialTheme.typography.titleSmall,
                color = AppTheme.TextMuted
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Circular score indicator
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                scoreColor.copy(alpha = 0.2f),
                                scoreColor.copy(alpha = 0.05f)
                            )
                        ),
                        shape = CircleShape
                    )
                    .border(3.dp, scoreColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "${todayStrength.overallScore}%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = scoreColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = scoreColor.copy(alpha = 0.15f)
            ) {
                Text(
                    todayStrength.combinedStrength.getLocalizedName(language),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = scoreColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = AppTheme.DividerColor)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                todayStrength.generalAdvice,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
private fun TarabalaDetailCard(
    tarabala: TarabalaCalculator.TarabalaResult,
    birthNakshatra: com.astro.storm.data.model.Nakshatra,
    language: Language
) {
    val taraColor = if (tarabala.isFavorable) AppTheme.SuccessColor else AppTheme.WarningColor

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
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
                            .clip(CircleShape)
                            .background(AppTheme.AccentGold.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = AppTheme.AccentGold,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            stringResource(StringKeyAnalysis.TARABALA_LABEL),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            stringResource(StringKeyAnalysis.TARABALA_STAR_STRENGTH),
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = taraColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        if (tarabala.isFavorable)
                            stringResource(StringKeyAnalysis.TARABALA_FAVORABLE)
                        else
                            stringResource(StringKeyAnalysis.TARABALA_CHALLENGING),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = taraColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Tara details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TaraInfoChip(
                    label = stringResource(StringKeyAnalysis.TARABALA_BIRTH_STAR),
                    value = birthNakshatra.getLocalizedName(language)
                )
                TaraInfoChip(
                    label = stringResource(StringKeyAnalysis.TARABALA_TRANSIT_STAR),
                    value = tarabala.targetNakshatra.getLocalizedName(language)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = AppTheme.CardBackgroundElevated
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(StringKeyAnalysis.TARABALA_TARA_TYPE),
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                        Text(
                            "${tarabala.tara.getLocalizedName(language)} (${tarabala.tara.number}/9)",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = taraColor
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            stringResource(StringKeyAnalysis.TARABALA_CYCLE),
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                        Text(
                            "${tarabala.cycle}/3",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Recommendations
            tarabala.recommendations.take(2).forEach { rec ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        if (tarabala.isFavorable) Icons.Filled.CheckCircle else Icons.Filled.Warning,
                        contentDescription = null,
                        tint = taraColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        rec,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun TaraInfoChip(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.TextSubtle
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.TextPrimary
        )
    }
}

@Composable
private fun ChandrabalaDetailCard(
    chandrabala: TarabalaCalculator.ChandrabalaResult,
    language: Language
) {
    val strengthColor = when (chandrabala.strength) {
        TarabalaCalculator.ChandrabalaStrength.EXCELLENT -> AppTheme.SuccessColor
        TarabalaCalculator.ChandrabalaStrength.GOOD -> AppTheme.AccentTeal
        TarabalaCalculator.ChandrabalaStrength.NEUTRAL -> AppTheme.AccentGold
        TarabalaCalculator.ChandrabalaStrength.WEAK -> AppTheme.WarningColor
        TarabalaCalculator.ChandrabalaStrength.UNFAVORABLE -> AppTheme.ErrorColor
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
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
                            .clip(CircleShape)
                            .background(AppTheme.PlanetMoon.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.Nightlight,
                            contentDescription = null,
                            tint = AppTheme.PlanetMoon,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            stringResource(StringKeyAnalysis.CHANDRABALA_LABEL),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            stringResource(StringKeyAnalysis.CHANDRABALA_MOON_STRENGTH),
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = strengthColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        chandrabala.strength.getLocalizedName(language),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = strengthColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Moon transit details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TaraInfoChip(
                    label = stringResource(StringKeyAnalysis.CHANDRABALA_NATAL_MOON),
                    value = chandrabala.natalMoonSign.getLocalizedName(language)
                )
                TaraInfoChip(
                    label = stringResource(StringKeyAnalysis.CHANDRABALA_TRANSIT_MOON),
                    value = chandrabala.transitMoonSign.getLocalizedName(language)
                )
                TaraInfoChip(
                    label = stringResource(StringKeyAnalysis.CHANDRABALA_HOUSE),
                    value = "${chandrabala.houseFromMoon}H"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // House significations
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = AppTheme.CardBackgroundElevated
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        stringResource(StringKeyAnalysis.CHANDRABALA_SIGNIFICATIONS),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextSubtle
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        chandrabala.significations,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Recommendations
            chandrabala.recommendations.take(2).forEach { rec ->
                Row(
                    modifier = Modifier.padding(vertical = 3.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Filled.LightMode,
                        contentDescription = null,
                        tint = strengthColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        rec,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityRecommendationsCard(
    todayStrength: TarabalaCalculator.DailyStrengthResult,
    language: Language
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                stringResource(StringKeyAnalysis.TARABALA_ACTIVITIES),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Favorable activities
            if (todayStrength.favorableActivities.isNotEmpty()) {
                Text(
                    stringResource(StringKeyAnalysis.TARABALA_FAVORABLE_ACTIVITIES),
                    style = MaterialTheme.typography.labelMedium,
                    color = AppTheme.SuccessColor,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                todayStrength.favorableActivities.take(3).forEach { activity ->
                    ActivityItem(
                        activity = activity,
                        isPositive = true
                    )
                }
            }

            // Activities to avoid
            if (todayStrength.avoidActivities.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    stringResource(StringKeyAnalysis.TARABALA_AVOID_ACTIVITIES),
                    style = MaterialTheme.typography.labelMedium,
                    color = AppTheme.WarningColor,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                todayStrength.avoidActivities.take(3).forEach { activity ->
                    ActivityItem(
                        activity = activity,
                        isPositive = false
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityItem(
    activity: TarabalaCalculator.ActivityRecommendation,
    isPositive: Boolean
) {
    val color = if (isPositive) AppTheme.SuccessColor else AppTheme.WarningColor

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(10.dp),
        color = color.copy(alpha = 0.08f)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                if (isPositive) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    activity.activity,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
                Text(
                    activity.reason,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted
                )
            }
        }
    }
}

// ============================================
// Weekly Tab
// ============================================

@Composable
private fun TarabalaWeeklyTab(
    analysis: TarabalaCalculator.TarabalaChandrabalaAnalysis,
    expandedDays: Set<String>,
    onToggleDay: (String) -> Unit,
    language: Language
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Weekly Summary Card
        WeeklySummaryCard(
            weeklyForecast = analysis.weeklyForecast,
            language = language
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Daily breakdown
        Text(
            stringResource(StringKeyAnalysis.TARABALA_DAILY_BREAKDOWN),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        analysis.weeklyForecast.dailyStrengths.forEach { daily ->
            val dayKey = daily.date.toString()
            val isExpanded = dayKey in expandedDays

            DailyStrengthCard(
                dailyStrength = daily,
                isExpanded = isExpanded,
                onToggle = { onToggleDay(dayKey) },
                isBestDay = daily.date == analysis.weeklyForecast.bestDay.date,
                isWorstDay = daily.date == analysis.weeklyForecast.worstDay.date,
                language = language
            )

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun WeeklySummaryCard(
    weeklyForecast: TarabalaCalculator.WeeklyForecast,
    language: Language
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                stringResource(StringKeyAnalysis.TARABALA_WEEKLY_OVERVIEW),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Best day
                DayHighlightChip(
                    label = stringResource(StringKeyAnalysis.TARABALA_BEST_DAY),
                    dayName = weeklyForecast.bestDay.date.dayOfWeek.name.take(3),
                    score = weeklyForecast.bestDay.overallScore,
                    color = AppTheme.SuccessColor
                )

                // Worst day
                DayHighlightChip(
                    label = stringResource(StringKeyAnalysis.TARABALA_AVOID_DAY),
                    dayName = weeklyForecast.worstDay.date.dayOfWeek.name.take(3),
                    score = weeklyForecast.worstDay.overallScore,
                    color = AppTheme.WarningColor
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(color = AppTheme.DividerColor)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                weeklyForecast.weeklyAdvice,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun DayHighlightChip(
    label: String,
    dayName: String,
    score: Int,
    color: Color
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.12f)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = color.copy(alpha = 0.8f)
            )
            Text(
                dayName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                "$score%",
                style = MaterialTheme.typography.bodySmall,
                color = color.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
private fun DailyStrengthCard(
    dailyStrength: TarabalaCalculator.DailyStrengthResult,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    isBestDay: Boolean,
    isWorstDay: Boolean,
    language: Language
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(300),
        label = "rotation"
    )

    val scoreColor = when {
        dailyStrength.overallScore >= 70 -> AppTheme.SuccessColor
        dailyStrength.overallScore >= 50 -> AppTheme.AccentTeal
        dailyStrength.overallScore >= 30 -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onToggle
            ),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Day indicator
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                color = if (isBestDay) AppTheme.SuccessColor.copy(alpha = 0.15f)
                                else if (isWorstDay) AppTheme.WarningColor.copy(alpha = 0.15f)
                                else AppTheme.CardBackgroundElevated,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                dailyStrength.date.dayOfWeek.name.take(3),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isBestDay) AppTheme.SuccessColor
                                else if (isWorstDay) AppTheme.WarningColor
                                else AppTheme.TextPrimary
                            )
                            Text(
                                "${dailyStrength.date.dayOfMonth}",
                                style = MaterialTheme.typography.bodySmall,
                                color = AppTheme.TextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                dailyStrength.combinedStrength.getLocalizedName(language),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                            if (isBestDay) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = AppTheme.SuccessColor.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        stringResource(StringKeyAnalysis.TARABALA_BEST_LABEL),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = AppTheme.SuccessColor
                                    )
                                }
                            }
                        }
                        Text(
                            "${dailyStrength.tarabala.tara.sanskritName} + ${dailyStrength.chandrabala.houseFromMoon}H",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Score badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = scoreColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            "${dailyStrength.overallScore}%",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = scoreColor
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Icon(
                        Icons.Filled.ExpandMore,
                        contentDescription = null,
                        tint = AppTheme.TextMuted,
                        modifier = Modifier
                            .size(24.dp)
                            .rotate(rotationAngle)
                    )
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        dailyStrength.generalAdvice,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

// ============================================
// All Nakshatras Tab
// ============================================

@Composable
private fun TarabalaAllNakshatrasTab(
    analysis: TarabalaCalculator.TarabalaChandrabalaAnalysis,
    language: Language
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            stringResource(StringKeyAnalysis.TARABALA_ALL_27_DESC),
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.TextMuted,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Group by Tara type
        val groupedByTara = analysis.allTaras.groupBy { it.tara }

        TarabalaCalculator.Tara.entries.forEach { tara ->
            val nakshatras = groupedByTara[tara] ?: emptyList()
            if (nakshatras.isNotEmpty()) {
                TaraGroupCard(
                    tara = tara,
                    nakshatras = nakshatras,
                    language = language
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun TaraGroupCard(
    tara: TarabalaCalculator.Tara,
    nakshatras: List<TarabalaCalculator.TarabalaResult>,
    language: Language
) {
    val taraColor = if (tara.isFavorable) AppTheme.SuccessColor else AppTheme.WarningColor

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(taraColor.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "${tara.number}",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = taraColor
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            tara.getLocalizedName(language),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            tara.englishMeaning,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                // Strength stars
                Row {
                    repeat(tara.strength) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = taraColor,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Nakshatra chips
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                items(nakshatras) { nak ->
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = AppTheme.CardBackgroundElevated
                    ) {
                        Text(
                            nak.targetNakshatra.getLocalizedName(language),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextSecondary
                        )
                    }
                }
            }
        }
    }
}

// ============================================
// Info Dialog
// ============================================

@Composable
private fun TarabalaInfoDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(StringKeyAnalysis.TARABALA_WHAT_IS),
                fontWeight = FontWeight.Bold,
                color = AppTheme.TextPrimary
            )
        },
        text = {
            Column {
                Text(
                    stringResource(StringKeyAnalysis.TARABALA_EXPLANATION),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary,
                    lineHeight = 22.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    stringResource(StringKeyAnalysis.CHANDRABALA_EXPLANATION),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary,
                    lineHeight = 22.sp
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(StringKey.BTN_CLOSE), color = AppTheme.AccentGold)
            }
        },
        containerColor = AppTheme.CardBackground
    )
}
