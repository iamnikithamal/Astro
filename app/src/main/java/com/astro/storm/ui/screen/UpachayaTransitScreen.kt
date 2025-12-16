package com.astro.storm.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.currentLanguage
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.UpachayaTransitTracker
import com.astro.storm.ephemeris.UpachayaTransitAnalysis
import com.astro.storm.ephemeris.UpachayaTransit
import com.astro.storm.ephemeris.UpachayaLevel
import com.astro.storm.ephemeris.HouseStrength
import com.astro.storm.ephemeris.TransitQuality
import com.astro.storm.ephemeris.AlertType
import com.astro.storm.ephemeris.AlertPriority
import com.astro.storm.ephemeris.TransitReference
import com.astro.storm.ephemeris.HouseTransitAnalysis
import com.astro.storm.ephemeris.UpachayaAlert
import com.astro.storm.ephemeris.UpcomingUpachayaTransit
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Upachaya Transit Screen
 *
 * Tracks beneficial transits through Upachaya houses (3, 6, 10, 11) where
 * natural malefics give especially good results. Provides:
 * - Current transit positions relative to Moon and Lagna
 * - House-wise analysis of growth opportunities
 * - Alerts for significant planetary transits
 * - Recommendations for maximizing transit benefits
 *
 * Based on: Phaladeepika, BPHS transit rules
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpachayaTransitScreen(
    chart: VedicChart?,
    transitPositions: List<PlanetPosition>,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = stringResource(StringKeyDosha.UPACHAYA_SCREEN_TITLE),
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = currentLanguage()
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var isCalculating by remember { mutableStateOf(true) }
    var analysisResult by remember { mutableStateOf<UpachayaTransitAnalysis?>(null) }
    var upcomingTransits by remember { mutableStateOf<List<UpcomingUpachayaTransit>>(emptyList()) }

    val tabs = listOf(
        stringResource(StringKeyDosha.SCREEN_OVERVIEW),
        stringResource(StringKeyDosha.UPACHAYA_HOUSE_ANALYSIS),
        stringResource(StringKeyDosha.UPACHAYA_TRANSIT_DETAILS),
        stringResource(StringKeyDosha.UPACHAYA_UPCOMING_TRANSITS)
    )

    // Calculate analysis
    LaunchedEffect(chart, transitPositions) {
        isCalculating = true
        delay(300)
        withContext(Dispatchers.Default) {
            analysisResult = UpachayaTransitTracker.analyzeUpachayaTransits(chart, transitPositions)
            upcomingTransits = UpachayaTransitTracker.getUpcomingTransits(chart)
        }
        isCalculating = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(StringKeyDosha.UPACHAYA_SCREEN_TITLE),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            stringResource(StringKeyDosha.UPACHAYA_SCREEN_SUBTITLE),
                            fontSize = 12.sp,
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
                            contentDescription = stringResource(StringKey.ABOUT),
                            tint = AppTheme.TextSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.ScreenBackground,
                    titleContentColor = AppTheme.TextPrimary
                )
            )
        },
        containerColor = AppTheme.ScreenBackground
    ) { paddingValues ->
        when {
            isCalculating -> LoadingContent(paddingValues)
            analysisResult == null -> ErrorContent(
                paddingValues = paddingValues,
                message = stringResource(StringKeyDosha.SCREEN_ERROR_CALCULATION)
            )
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Tab Row
                    TabSelector(
                        tabs = tabs,
                        selectedIndex = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )

                    // Content based on selected tab
                    when (selectedTab) {
                        0 -> OverviewTab(analysisResult!!, language)
                        1 -> HouseAnalysisTab(analysisResult!!, language)
                        2 -> TransitDetailsTab(analysisResult!!, language)
                        3 -> UpcomingTransitsTab(upcomingTransits, language)
                    }
                }
            }
        }
    }

    // Info Dialog
    if (showInfoDialog) {
        AlertDialog(
            onDismissRequest = { showInfoDialog = false },
            title = {
                Text(
                    stringResource(StringKeyDosha.UPACHAYA_SCREEN_ABOUT),
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.TextPrimary
                )
            },
            text = {
                Column {
                    Text(
                        stringResource(StringKeyDosha.UPACHAYA_SCREEN_ABOUT_DESC),
                        color = AppTheme.TextSecondary,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = AppTheme.InfoColor.copy(alpha = 0.1f)
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "Upachaya Houses:",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                color = AppTheme.TextPrimary
                            )
                            Text(
                                "3rd (Courage), 6th (Enemies), 10th (Career), 11th (Gains)",
                                fontSize = 12.sp,
                                color = AppTheme.TextSecondary
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showInfoDialog = false }) {
                    Text(
                        stringResource(StringKey.BTN_CLOSE),
                        color = AppTheme.AccentPrimary
                    )
                }
            },
            containerColor = AppTheme.CardBackground,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun LoadingContent(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = AppTheme.AccentPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                stringResource(StringKeyDosha.SCREEN_CALCULATING),
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun ErrorContent(paddingValues: PaddingValues, message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Outlined.ErrorOutline,
                contentDescription = null,
                tint = AppTheme.ErrorColor,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(message, color = AppTheme.TextMuted)
        }
    }
}

@Composable
private fun TabSelector(
    tabs: List<String>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tabs.size) { index ->
            FilterChip(
                selected = selectedIndex == index,
                onClick = { onTabSelected(index) },
                label = {
                    Text(
                        tabs[index],
                        fontSize = 13.sp,
                        fontWeight = if (selectedIndex == index) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AppTheme.AccentPrimary.copy(alpha = 0.2f),
                    selectedLabelColor = AppTheme.AccentPrimary,
                    containerColor = AppTheme.CardBackground,
                    labelColor = AppTheme.TextSecondary
                )
            )
        }
    }
}

@Composable
private fun SectionHeader(title: String, icon: ImageVector, tint: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp, color = AppTheme.TextPrimary)
    }
}

// ============================================
// OVERVIEW TAB
// ============================================
@Composable
private fun OverviewTab(analysis: UpachayaTransitAnalysis, language: String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Overall Assessment Card
        item {
            OverallAssessmentCard(analysis)
        }

        // Reference Points
        item {
            ReferencePointsCard(analysis, language)
        }

        // Active Alerts
        if (analysis.alerts.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(StringKeyDosha.UPACHAYA_ACTIVE_ALERTS),
                    icon = Icons.Filled.Notifications,
                    tint = AppTheme.AccentGold
                )
            }
            items(analysis.alerts) { alert ->
                AlertCard(alert, language)
            }
        }

        // Most Significant Transits
        if (analysis.mostSignificantTransits.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(StringKeyDosha.UPACHAYA_SIGNIFICANT_TRANSITS),
                    icon = Icons.Filled.Star,
                    tint = AppTheme.AccentPrimary
                )
            }
            items(analysis.mostSignificantTransits) { transit ->
                SignificantTransitCard(transit, language)
            }
        }

        // Recommendations
        if (analysis.recommendations.isNotEmpty()) {
            item {
                RecommendationsCard(analysis.recommendations)
            }
        }
    }
}

@Composable
private fun OverallAssessmentCard(analysis: UpachayaTransitAnalysis) {
    val assessment = analysis.overallAssessment

    val (backgroundColor, iconColor, icon) = when (assessment.level) {
        UpachayaLevel.EXCEPTIONAL -> Triple(
            AppTheme.SuccessColor.copy(alpha = 0.15f),
            AppTheme.SuccessColor,
            Icons.Filled.Star
        )
        UpachayaLevel.HIGH -> Triple(
            AppTheme.SuccessColor.copy(alpha = 0.1f),
            AppTheme.SuccessColor,
            Icons.Filled.TrendingUp
        )
        UpachayaLevel.MODERATE -> Triple(
            AppTheme.AccentGold.copy(alpha = 0.1f),
            AppTheme.AccentGold,
            Icons.Filled.TrendingFlat
        )
        UpachayaLevel.LOW -> Triple(
            AppTheme.TextMuted.copy(alpha = 0.1f),
            AppTheme.TextMuted,
            Icons.Filled.HourglassEmpty
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(iconColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        stringResource(StringKeyDosha.UPACHAYA_TRANSIT_ASSESSMENT),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        "Level: ${assessment.level.name}",
                        fontSize = 14.sp,
                        color = iconColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Progress Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Transit Strength",
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "${assessment.score.toInt()}%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = iconColor
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { (assessment.score / 100f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = iconColor,
                trackColor = AppTheme.ChipBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                assessment.summary,
                fontSize = 14.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                color = AppTheme.ChipBackground,
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = null,
                        tint = AppTheme.InfoColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        assessment.keyPeriod,
                        fontSize = 12.sp,
                        color = AppTheme.TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun ReferencePointsCard(analysis: UpachayaTransitAnalysis, language: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                stringResource(StringKeyDosha.UPACHAYA_REFERENCE_POINTS),
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(AppTheme.PlanetMoon.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.NightsStay,
                            contentDescription = null,
                            tint = AppTheme.PlanetMoon,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Moon Sign", fontSize = 11.sp, color = AppTheme.TextMuted)
                    Text(
                        analysis.moonSign.getLocalizedName(language),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.TextPrimary
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(AppTheme.AccentPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.Home,
                            contentDescription = null,
                            tint = AppTheme.AccentPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Lagna", fontSize = 11.sp, color = AppTheme.TextMuted)
                    Text(
                        analysis.lagnaSign.getLocalizedName(language),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.TextPrimary
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(AppTheme.SuccessColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "${analysis.activeUpachayaTransits.size}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = AppTheme.SuccessColor
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Active", fontSize = 11.sp, color = AppTheme.TextMuted)
                    Text(
                        "Upachaya",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.TextPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun AlertCard(alert: UpachayaAlert, language: String) {
    val (backgroundColor, iconColor, icon) = when (alert.type) {
        AlertType.OPPORTUNITY -> Triple(
            AppTheme.SuccessColor.copy(alpha = 0.1f),
            AppTheme.SuccessColor,
            Icons.Filled.TrendingUp
        )
        AlertType.MAJOR_TRANSIT -> Triple(
            AppTheme.AccentPrimary.copy(alpha = 0.1f),
            AppTheme.AccentPrimary,
            Icons.Filled.Star
        )
        AlertType.FORTUNE -> Triple(
            AppTheme.AccentGold.copy(alpha = 0.1f),
            AppTheme.AccentGold,
            Icons.Filled.AutoAwesome
        )
        AlertType.CAUTION -> Triple(
            AppTheme.WarningColor.copy(alpha = 0.1f),
            AppTheme.WarningColor,
            Icons.Outlined.Warning
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    alert.message,
                    fontSize = 13.sp,
                    color = AppTheme.TextPrimary,
                    lineHeight = 18.sp
                )
            }
            if (alert.priority == AlertPriority.HIGH) {
                Surface(
                    color = iconColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        "HIGH",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = iconColor
                    )
                }
            }
        }
    }
}

@Composable
private fun SignificantTransitCard(transit: UpachayaTransit, language: String) {
    val qualityColor = when (transit.transitQuality) {
        TransitQuality.EXCELLENT -> AppTheme.SuccessColor
        TransitQuality.GOOD -> AppTheme.SuccessColor.copy(alpha = 0.8f)
        TransitQuality.FAVORABLE -> AppTheme.AccentGold
        TransitQuality.NEUTRAL -> AppTheme.TextMuted
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(AppTheme.getPlanetColor(transit.planet).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    transit.planet.displayName.take(2),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = AppTheme.getPlanetColor(transit.planet)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        transit.planet.getLocalizedName(language),
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Surface(
                        color = qualityColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            transit.transitQuality.name,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = qualityColor
                        )
                    }
                }
                Text(
                    "${transit.transitSign.getLocalizedName(language)} • ${getHouseName(transit.houseFromReference)} from ${transit.reference.name}",
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted
                )
                Text(
                    "Significance: ${transit.significance.toInt()}%",
                    fontSize = 12.sp,
                    color = qualityColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun RecommendationsCard(recommendations: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Lightbulb,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    stringResource(StringKeyDosha.SCREEN_RECOMMENDATIONS),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            recommendations.forEach { rec ->
                Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.Top) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = null,
                        tint = AppTheme.SuccessColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(rec, fontSize = 13.sp, color = AppTheme.TextSecondary, lineHeight = 18.sp)
                }
            }
        }
    }
}

// ============================================
// HOUSE ANALYSIS TAB
// ============================================
@Composable
private fun HouseAnalysisTab(analysis: UpachayaTransitAnalysis, language: String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Introduction Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.AccentPrimary.copy(alpha = 0.08f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        stringResource(StringKeyDosha.UPACHAYA_ABOUT),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Upachaya houses (3, 6, 10, 11) are 'growth houses' where planets, especially malefics, give increasingly positive results over time. Transits through these houses bring opportunities for growth and success.",
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        // House-wise Analysis Cards
        items(analysis.houseWiseAnalysis.entries.toList().sortedBy { it.key }) { (house, houseAnalysis) ->
            HouseAnalysisCard(houseAnalysis, language)
        }
    }
}

@Composable
private fun HouseAnalysisCard(analysis: HouseTransitAnalysis, language: String) {
    val strengthColor = when (analysis.strength) {
        HouseStrength.VERY_STRONG -> AppTheme.SuccessColor
        HouseStrength.STRONG -> AppTheme.SuccessColor.copy(alpha = 0.8f)
        HouseStrength.MODERATE -> AppTheme.AccentGold
        HouseStrength.MILD -> AppTheme.TextMuted
        HouseStrength.INACTIVE -> AppTheme.TextMuted.copy(alpha = 0.5f)
    }

    val houseIcon = when (analysis.house) {
        3 -> Icons.Filled.Shield
        6 -> Icons.Filled.Security
        10 -> Icons.Filled.Work
        11 -> Icons.Filled.Paid
        else -> Icons.Filled.Home
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (analysis.strength != HouseStrength.INACTIVE)
                strengthColor.copy(alpha = 0.05f)
            else AppTheme.CardBackground
        ),
        shape = RoundedCornerShape(12.dp)
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
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(strengthColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            houseIcon,
                            contentDescription = null,
                            tint = strengthColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            "${analysis.houseName} House",
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            analysis.houseTheme,
                            fontSize = 12.sp,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Surface(
                    color = strengthColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        analysis.strength.name.replace("_", " "),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = strengthColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Transiting Planets
            if (analysis.transitingPlanets.isNotEmpty()) {
                Text(
                    "Transiting Planets:",
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted
                )
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(analysis.transitingPlanets) { planet ->
                        Surface(
                            color = AppTheme.getPlanetColor(planet).copy(alpha = 0.2f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                planet.getLocalizedName(language),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = AppTheme.getPlanetColor(planet)
                            )
                        }
                    }
                }
            }

            // Effects
            if (analysis.effects.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                analysis.effects.forEach { effect ->
                    Row(
                        modifier = Modifier.padding(vertical = 2.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text("•", color = strengthColor, fontSize = 12.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            effect,
                            fontSize = 12.sp,
                            color = AppTheme.TextSecondary,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            // Timing
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                analysis.timing,
                fontSize = 11.sp,
                color = AppTheme.TextMuted,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }
    }
}

// ============================================
// TRANSIT DETAILS TAB
// ============================================
@Composable
private fun TransitDetailsTab(analysis: UpachayaTransitAnalysis, language: String) {
    var selectedReference by remember { mutableStateOf(TransitReference.MOON) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Reference Toggle
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                FilterChip(
                    selected = selectedReference == TransitReference.MOON,
                    onClick = { selectedReference = TransitReference.MOON },
                    label = { Text("From Moon") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AppTheme.PlanetMoon.copy(alpha = 0.2f),
                        selectedLabelColor = AppTheme.PlanetMoon
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                FilterChip(
                    selected = selectedReference == TransitReference.LAGNA,
                    onClick = { selectedReference = TransitReference.LAGNA },
                    label = { Text("From Lagna") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AppTheme.AccentPrimary.copy(alpha = 0.2f),
                        selectedLabelColor = AppTheme.AccentPrimary
                    )
                )
            }
        }

        val transits = if (selectedReference == TransitReference.MOON)
            analysis.transitsFromMoon
        else
            analysis.transitsFromLagna

        items(transits.sortedByDescending { it.significance }) { transit ->
            TransitDetailCard(transit, language)
        }
    }
}

@Composable
private fun TransitDetailCard(transit: UpachayaTransit, language: String) {
    var expanded by remember { mutableStateOf(false) }

    val cardColor = if (transit.isInUpachaya)
        AppTheme.SuccessColor.copy(alpha = 0.05f)
    else
        AppTheme.CardBackground

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(12.dp)
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
                            .background(AppTheme.getPlanetColor(transit.planet).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            transit.planet.displayName.take(2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = AppTheme.getPlanetColor(transit.planet)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            transit.planet.getLocalizedName(language),
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            "${transit.transitSign.getLocalizedName(language)} ${String.format("%.1f", transit.transitDegree)}°",
                            fontSize = 12.sp,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "${getHouseName(transit.houseFromReference)} House",
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = if (transit.isInUpachaya) AppTheme.SuccessColor else AppTheme.TextSecondary
                    )
                    if (transit.isInUpachaya) {
                        Text(
                            "UPACHAYA",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.SuccessColor
                        )
                    }
                }
            }

            AnimatedVisibility(visible = expanded && transit.isInUpachaya) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))

                    // Duration
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Schedule,
                            contentDescription = null,
                            tint = AppTheme.TextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            transit.approximateDuration,
                            fontSize = 12.sp,
                            color = AppTheme.TextMuted
                        )
                    }

                    // Effects
                    if (transit.effects.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            stringResource(StringKeyDosha.EFFECTS_LABEL),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        transit.effects.forEach { effect ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text("•", color = AppTheme.TextMuted, fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    effect,
                                    fontSize = 12.sp,
                                    color = AppTheme.TextSecondary,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }

                    // Recommendations
                    if (transit.recommendations.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            stringResource(StringKeyDosha.SCREEN_RECOMMENDATIONS),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.SuccessColor
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        transit.recommendations.forEach { rec ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = AppTheme.SuccessColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    rec,
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
}

// ============================================
// UPCOMING TRANSITS TAB
// ============================================
@Composable
private fun UpcomingTransitsTab(upcomingTransits: List<UpcomingUpachayaTransit>, language: String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                stringResource(StringKeyDosha.UPACHAYA_UPCOMING_TRANSITS),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                "Key planetary transits to watch for Upachaya house activation",
                fontSize = 13.sp,
                color = AppTheme.TextMuted,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Group by house
        val groupedByHouse = upcomingTransits.groupBy { it.targetHouse }

        groupedByHouse.forEach { (house, transits) ->
            item {
                Text(
                    "${getHouseName(house)} House Transits",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    color = AppTheme.TextPrimary,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }

            items(transits) { transit ->
                UpcomingTransitCard(transit, language)
            }
        }
    }
}

@Composable
private fun UpcomingTransitCard(transit: UpcomingUpachayaTransit, language: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(AppTheme.getPlanetColor(transit.planet).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    transit.planet.displayName.take(2),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = AppTheme.getPlanetColor(transit.planet)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "${transit.planet.getLocalizedName(language)} → ${transit.targetSign.getLocalizedName(language)}",
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Text(
                    transit.significance,
                    fontSize = 12.sp,
                    color = AppTheme.TextSecondary,
                    lineHeight = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Surface(
                    color = AppTheme.SuccessColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        transit.recommendation,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        color = AppTheme.SuccessColor
                    )
                }
            }
        }
    }
}

// Helper function
private fun getHouseName(house: Int): String {
    return when (house) {
        1 -> "1st"
        2 -> "2nd"
        3 -> "3rd"
        else -> "${house}th"
    }
}
