package com.astro.storm.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.currentLanguage
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.GrahaYuddhaCalculator
import com.astro.storm.ephemeris.GrahaYuddhaCalculator.GrahaYuddhaAnalysis
import com.astro.storm.ephemeris.GrahaYuddhaCalculator.GrahaYuddhaResult
import com.astro.storm.ephemeris.GrahaYuddhaCalculator.DashaWarEffect
import com.astro.storm.ephemeris.GrahaYuddhaCalculator.WarRemedy
import com.astro.storm.ephemeris.GrahaYuddhaCalculator.WarIntensity
import com.astro.storm.ephemeris.GrahaYuddhaCalculator.WarImpactLevel
import com.astro.storm.ephemeris.GrahaYuddhaCalculator.WarStatus
import com.astro.storm.ephemeris.GrahaYuddhaCalculator.RemedyType
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Graha Yuddha (Planetary War) Analysis Screen
 *
 * Comprehensive analysis of planetary wars showing:
 * - Active wars with winner/loser determination
 * - War intensity and effects
 * - Dasha period implications
 * - Remedial measures
 *
 * References: BPHS, Surya Siddhanta, Phaladeepika
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GrahaYuddhaScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = "Graha Yuddha",
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = currentLanguage()
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var isCalculating by remember { mutableStateOf(true) }
    var yuddhaAnalysis by remember { mutableStateOf<GrahaYuddhaAnalysis?>(null) }

    val tabs = listOf(
        "Overview",
        "Active Wars",
        "Dasha Effects",
        "Remedies"
    )

    // Calculate Graha Yuddha
    LaunchedEffect(chart) {
        isCalculating = true
        delay(300)
        try {
            yuddhaAnalysis = withContext(Dispatchers.Default) {
                GrahaYuddhaCalculator.analyzeGrahaYuddha(chart)
            }
        } catch (e: Exception) {
            // Handle error silently
        }
        isCalculating = false
    }

    if (showInfoDialog) {
        GrahaYuddhaInfoDialog(onDismiss = { showInfoDialog = false })
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Graha Yuddha",
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
                            contentDescription = "About Graha Yuddha",
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
        if (isCalculating) {
            LoadingContent(paddingValues)
        } else if (yuddhaAnalysis != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Tab row
                ScrollableTabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = AppTheme.ScreenBackground,
                    contentColor = AppTheme.AccentPrimary,
                    edgePadding = 16.dp
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    color = if (selectedTab == index)
                                        AppTheme.AccentPrimary else AppTheme.TextMuted
                                )
                            }
                        )
                    }
                }

                // Content based on selected tab
                when (selectedTab) {
                    0 -> OverviewTab(yuddhaAnalysis!!)
                    1 -> ActiveWarsTab(yuddhaAnalysis!!)
                    2 -> DashaEffectsTab(yuddhaAnalysis!!)
                    3 -> RemediesTab(yuddhaAnalysis!!)
                }
            }
        }
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator(
                color = AppTheme.AccentPrimary,
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "Analyzing Planetary Wars...",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted
            )
        }
    }
}

// ============================================
// OVERVIEW TAB
// ============================================

@Composable
private fun OverviewTab(analysis: GrahaYuddhaAnalysis) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // War Status Card
        item {
            WarStatusCard(analysis)
        }

        // Summary Card
        item {
            SummaryCard(analysis.interpretation.summary)
        }

        // Impact Assessment
        item {
            ImpactAssessmentCard(analysis)
        }

        // Key Insights
        if (analysis.interpretation.keyInsights.isNotEmpty()) {
            item {
                KeyInsightsCard(analysis.interpretation.keyInsights)
            }
        }

        // Affected Life Areas
        if (analysis.interpretation.affectedLifeAreas.isNotEmpty()) {
            item {
                AffectedAreasCard(analysis.interpretation.affectedLifeAreas)
            }
        }

        // Recommendations
        item {
            RecommendationsCard(analysis.interpretation.recommendations)
        }
    }
}

@Composable
private fun WarStatusCard(analysis: GrahaYuddhaAnalysis) {
    val hasWar = analysis.hasActiveWar
    val warCount = analysis.activeWars.size

    val (statusColor, statusIcon, statusText) = if (hasWar) {
        Triple(
            AppTheme.WarningColor,
            Icons.Outlined.Warning,
            "$warCount Planetary War${if (warCount > 1) "s" else ""} Detected"
        )
    } else {
        Triple(
            AppTheme.SuccessColor,
            Icons.Outlined.CheckCircle,
            "No Planetary Wars Present"
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(statusColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = statusIcon,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(28.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = statusText,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Text(
                    text = if (hasWar)
                        "Wars affect planetary significations significantly"
                    else
                        "Planets operate without war-related obstructions",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted
                )
            }
        }
    }
}

@Composable
private fun SummaryCard(summary: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Analysis Summary",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Text(
                text = summary,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
private fun ImpactAssessmentCard(analysis: GrahaYuddhaAnalysis) {
    val impactLevel = analysis.interpretation.overallWarImpact
    val impactColor = when (impactLevel) {
        WarImpactLevel.NONE -> AppTheme.SuccessColor
        WarImpactLevel.MILD -> Color(0xFF4CAF50)
        WarImpactLevel.MODERATE -> AppTheme.AccentGold
        WarImpactLevel.SIGNIFICANT -> AppTheme.WarningColor
        WarImpactLevel.SEVERE -> AppTheme.ErrorColor
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Overall War Impact",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LinearProgressIndicator(
                    progress = { when (impactLevel) {
                        WarImpactLevel.NONE -> 0f
                        WarImpactLevel.MILD -> 0.25f
                        WarImpactLevel.MODERATE -> 0.5f
                        WarImpactLevel.SIGNIFICANT -> 0.75f
                        WarImpactLevel.SEVERE -> 1f
                    } },
                    modifier = Modifier
                        .weight(1f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(5.dp)),
                    color = impactColor,
                    trackColor = AppTheme.ChipBackground
                )
                Surface(
                    color = impactColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = impactLevel.displayName,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = impactColor
                    )
                }
            }

            // Winner/Loser summary
            if (analysis.activeWars.isNotEmpty()) {
                Divider(color = AppTheme.BorderColor, modifier = Modifier.padding(vertical = 4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val winners = analysis.activeWars.map { it.winner }.distinct()
                    val losers = analysis.activeWars.map { it.loser }.distinct()

                    // Winners
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Victorious",
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextMuted
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            winners.forEach { planet ->
                                PlanetChip(planet = planet, isWinner = true)
                            }
                        }
                    }

                    // Losers
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Defeated",
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextMuted
                        )
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            losers.forEach { planet ->
                                PlanetChip(planet = planet, isWinner = false)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlanetChip(planet: Planet, isWinner: Boolean) {
    val color = if (isWinner) AppTheme.SuccessColor else AppTheme.ErrorColor

    Surface(
        color = color.copy(alpha = 0.1f),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text = planet.symbol,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
    }
}

@Composable
private fun KeyInsightsCard(insights: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Lightbulb,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Key Insights",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            insights.forEach { insight ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowRight,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = insight,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun AffectedAreasCard(areas: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Affected Life Areas",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(areas) { area ->
                    SuggestionChip(
                        onClick = {},
                        label = { Text(area, style = MaterialTheme.typography.labelSmall) },
                        colors = SuggestionChipDefaults.suggestionChipColors(
                            containerColor = AppTheme.WarningColor.copy(alpha = 0.1f),
                            labelColor = AppTheme.WarningColor
                        ),
                        border = null
                    )
                }
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
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                    tint = AppTheme.SuccessColor,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Recommendations",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            recommendations.forEach { rec ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "•",
                        color = AppTheme.AccentPrimary
                    )
                    Text(
                        text = rec,
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
// ACTIVE WARS TAB
// ============================================

@Composable
private fun ActiveWarsTab(analysis: GrahaYuddhaAnalysis) {
    if (analysis.activeWars.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null,
                    tint = AppTheme.SuccessColor,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "No Active Planetary Wars",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextMuted
                )
                Text(
                    text = "All planets are operating peacefully",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSubtle
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(analysis.activeWars) { war ->
                WarCard(war = war)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WarCard(war: GrahaYuddhaResult) {
    var isExpanded by remember { mutableStateOf(false) }

    val intensityColor = when (war.intensityLevel) {
        WarIntensity.SEVERE -> AppTheme.ErrorColor
        WarIntensity.INTENSE -> AppTheme.WarningColor
        WarIntensity.MODERATE -> AppTheme.AccentGold
        WarIntensity.MILD -> AppTheme.TextMuted
    }

    Card(
        onClick = { isExpanded = !isExpanded },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header - Winner vs Loser
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Winner
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(AppTheme.SuccessColor.copy(alpha = 0.15f))
                            .border(2.dp, AppTheme.SuccessColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = war.winner.symbol,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.SuccessColor
                        )
                    }

                    // VS indicator
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CompareArrows,
                            contentDescription = null,
                            tint = intensityColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "vs",
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextMuted
                        )
                    }

                    // Loser
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(AppTheme.ErrorColor.copy(alpha = 0.15f))
                            .border(2.dp, AppTheme.ErrorColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = war.loser.symbol,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.ErrorColor
                        )
                    }
                }

                // Intensity badge
                Surface(
                    color = intensityColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = war.intensityLevel.displayName,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = intensityColor
                    )
                }
            }

            // War details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "${war.winner.displayName} defeats ${war.loser.displayName}",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "in ${war.warSign.displayName} (House ${war.warHouse})",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextMuted
                    )
                }
            }

            // Separation and advantage
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DetailChip(
                    label = "Separation",
                    value = "${String.format("%.2f", war.separation)}°"
                )
                DetailChip(
                    label = "Advantage",
                    value = war.winnerAdvantage.displayName
                )
            }

            // Expand button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted
                )
            }

            // Expanded content
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Divider(color = AppTheme.BorderColor)

                    // Interpretation
                    Text(
                        text = war.interpretation,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary,
                        lineHeight = 20.sp
                    )

                    // Winner effects
                    EffectsSection(
                        title = "${war.winner.displayName} (Winner)",
                        color = AppTheme.SuccessColor,
                        content = war.winnerEffects.overallBenefit
                    )

                    // Loser effects
                    EffectsSection(
                        title = "${war.loser.displayName} (Loser)",
                        color = AppTheme.ErrorColor,
                        content = war.loserEffects.overallDeficit
                    )

                    // Weakness areas
                    Text(
                        text = "Weakness Areas:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.TextPrimary
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(war.loserEffects.weaknessAreas) { area ->
                            SuggestionChip(
                                onClick = {},
                                label = { Text(area, style = MaterialTheme.typography.labelSmall) },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = AppTheme.ChipBackground,
                                    labelColor = AppTheme.TextMuted
                                ),
                                border = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailChip(label: String, value: String) {
    Surface(
        color = AppTheme.ChipBackground,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.TextMuted
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextPrimary
            )
        }
    }
}

@Composable
private fun EffectsSection(title: String, color: Color, content: String) {
    Surface(
        color = color.copy(alpha = 0.05f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary,
                lineHeight = 18.sp
            )
        }
    }
}

// ============================================
// DASHA EFFECTS TAB
// ============================================

@Composable
private fun DashaEffectsTab(analysis: GrahaYuddhaAnalysis) {
    if (analysis.dashaWarEffects.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Timeline,
                    contentDescription = null,
                    tint = AppTheme.TextMuted,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "No War-Related Dasha Effects",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextMuted
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(analysis.dashaWarEffects) { effect ->
                DashaEffectCard(effect = effect)
            }
        }
    }
}

@Composable
private fun DashaEffectCard(effect: DashaWarEffect) {
    val color = if (effect.isWinner) AppTheme.SuccessColor else AppTheme.ErrorColor

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(color.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = effect.planet.symbol,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                }
                Column {
                    Text(
                        text = "${effect.planet.displayName} Periods",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = if (effect.isWinner) "War Victor" else "War Defeated",
                        style = MaterialTheme.typography.labelSmall,
                        color = color
                    )
                }
            }

            // Mahadasha
            DashaEffectRow(
                icon = Icons.Outlined.Timeline,
                title = "Mahadasha",
                content = effect.mahadashaEffect
            )

            // Antardasha
            DashaEffectRow(
                icon = Icons.Outlined.Schedule,
                title = "Antardasha",
                content = effect.antardashaEffect
            )

            // Transit
            DashaEffectRow(
                icon = Icons.Outlined.Sync,
                title = "Transit",
                content = effect.transitEffect
            )
        }
    }
}

@Composable
private fun DashaEffectRow(
    icon: ImageVector,
    title: String,
    content: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AppTheme.AccentPrimary,
            modifier = Modifier.size(16.dp)
        )
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextPrimary
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted,
                lineHeight = 18.sp
            )
        }
    }
}

// ============================================
// REMEDIES TAB
// ============================================

@Composable
private fun RemediesTab(analysis: GrahaYuddhaAnalysis) {
    if (analysis.remedies.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Spa,
                    contentDescription = null,
                    tint = AppTheme.SuccessColor,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "No Specific Remedies Needed",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextMuted
                )
                Text(
                    text = "No active wars to remediate",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSubtle
                )
            }
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Group by planet
            val groupedRemedies = analysis.remedies.groupBy { it.forPlanet }

            groupedRemedies.forEach { (planet, remedies) ->
                item {
                    Text(
                        text = planet?.displayName ?: "General Remedies",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                items(remedies) { remedy ->
                    RemedyCard(remedy = remedy)
                }
            }
        }
    }
}

@Composable
private fun RemedyCard(remedy: WarRemedy) {
    val typeIcon = when (remedy.type) {
        RemedyType.MANTRA -> Icons.Outlined.RecordVoiceOver
        RemedyType.CHARITY -> Icons.Outlined.VolunteerActivism
        RemedyType.WORSHIP -> Icons.Outlined.SelfImprovement
        RemedyType.GEMSTONE -> Icons.Outlined.Diamond
        RemedyType.GENERAL -> Icons.Outlined.AutoAwesome
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(AppTheme.AccentGold.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = typeIcon,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = remedy.type.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.AccentGold
                )
                Text(
                    text = remedy.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextPrimary
                )

                remedy.mantra?.let { mantra ->
                    Surface(
                        color = AppTheme.ChipBackground,
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = mantra,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.AccentPrimary
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = AppTheme.TextMuted,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = remedy.timing,
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }

                Text(
                    text = remedy.expectedBenefit,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
            }
        }
    }
}

// ============================================
// INFO DIALOG
// ============================================

@Composable
private fun GrahaYuddhaInfoDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "About Graha Yuddha",
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "What is Graha Yuddha?",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "Graha Yuddha (Planetary War) occurs when two planets are within one degree of each other. The planets engage in combat, significantly affecting both planets' ability to deliver results.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary
                    )
                }

                item {
                    Text(
                        text = "Who Can Fight?",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "Only Mars, Mercury, Jupiter, Venus, and Saturn can engage in planetary war. Sun and Moon are luminaries and don't participate. Rahu and Ketu are shadow planets and also don't participate.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary
                    )
                }

                item {
                    Text(
                        text = "Determining the Winner",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf(
                            "1. Northern latitude (higher declination) wins",
                            "2. If equal, brighter planet wins",
                            "3. Brightness order: Venus > Jupiter > Mars > Mercury > Saturn"
                        ).forEach { rule ->
                            Text(
                                text = rule,
                                style = MaterialTheme.typography.bodySmall,
                                color = AppTheme.TextSecondary
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = "Effects of War",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "The winner gains strength and dominance, often delivering results more forcefully. The loser's significations are weakened, obstructed, or manifest with difficulty. Effects intensify during dasha/antardasha of involved planets.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary
                    )
                }

                item {
                    Text(
                        text = "References",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "• Brihat Parashara Hora Shastra\n• Surya Siddhanta\n• Phaladeepika\n• Jataka Parijata",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(StringKeyDosha.BTN_GOT_IT), color = AppTheme.AccentPrimary)
            }
        },
        containerColor = AppTheme.CardBackground
    )
}
