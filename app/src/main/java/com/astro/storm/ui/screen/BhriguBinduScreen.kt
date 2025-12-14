package com.astro.storm.ui.screen

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.currentLanguage
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.BhriguBinduCalculator
import com.astro.storm.ephemeris.BhriguBinduCalculator.AspectType
import com.astro.storm.ephemeris.BhriguBinduCalculator.AreaInfluence
import com.astro.storm.ephemeris.BhriguBinduCalculator.FactorInfluence
import com.astro.storm.ephemeris.BhriguBinduCalculator.LifeArea
import com.astro.storm.ephemeris.BhriguBinduCalculator.OverallStrength
import com.astro.storm.ephemeris.BhriguBinduCalculator.RemedyCategory
import com.astro.storm.ephemeris.BhriguBinduCalculator.RemedyPriority
import com.astro.storm.ui.theme.AppTheme
import java.time.LocalDate

/**
 * Bhrigu Bindu Analysis Screen
 *
 * Comprehensive Vedic astrology analysis of the Bhrigu Bindu (BB) point featuring:
 * - Bhrigu Bindu calculation formula and position
 * - BB longitude, sign, nakshatra, pada
 * - House placement and its significance
 * - Sign lord and nakshatra lord
 * - Strength assessment (Excellent/Good/Moderate/Challenging/Difficult)
 * - Aspecting planets and their effects
 * - Conjunct planets
 * - Transit analysis (when transits activate BB)
 * - Karmic significance and life events timing
 * - Recommended remedies
 *
 * Based on Bhrigu Nandi Nadi traditions for timing events and understanding karmic patterns.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BhriguBinduScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = stringResource(StringKeyDosha.BHRIGU_BINDU_TITLE),
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = currentLanguage()
    val clipboardManager = LocalClipboardManager.current
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var expandedFactor by remember { mutableStateOf<Int?>(null) }
    var expandedLifeArea by remember { mutableStateOf<LifeArea?>(null) }

    val tabs = listOf(
        stringResource(StringKeyAnalysis.TAB_OVERVIEW),
        stringResource(StringKeyAnalysis.TAB_ANALYSIS),
        stringResource(StringKeyDosha.BHRIGU_BINDU_TRANSITS),
        stringResource(StringKeyDosha.BHRIGU_BINDU_REMEDIES)
    )

    // Calculate Bhrigu Bindu analysis
    val bbAnalysis = remember(chart) {
        try {
            BhriguBinduCalculator.analyzeBhriguBindu(chart, LocalDate.now())
        } catch (e: Exception) {
            null
        }
    }

    if (bbAnalysis == null) {
        EmptyChartScreen(
            title = stringResource(StringKeyDosha.BHRIGU_BINDU_TITLE),
            message = "Unable to calculate Bhrigu Bindu. Please check chart data.",
            onBack = onBack
        )
        return
    }

    if (showInfoDialog) {
        BhriguBinduInfoDialog(onDismiss = { showInfoDialog = false })
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(StringKeyDosha.BHRIGU_BINDU_TITLE),
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
                            contentDescription = stringResource(StringKeyDosha.BHRIGU_BINDU_ABOUT),
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(AppTheme.ScreenBackground),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Tab selector
            item {
                BhriguBinduTabSelector(
                    tabs = tabs,
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }

            // Tab content
            when (selectedTab) {
                0 -> item {
                    BhriguBinduOverviewTab(
                        analysis = bbAnalysis,
                        chart = chart,
                        language = language
                    )
                }
                1 -> item {
                    BhriguBinduAnalysisTab(
                        analysis = bbAnalysis,
                        chart = chart,
                        language = language,
                        expandedFactor = expandedFactor,
                        onExpandFactor = { expandedFactor = if (expandedFactor == it) null else it },
                        expandedLifeArea = expandedLifeArea,
                        onExpandLifeArea = { expandedLifeArea = if (expandedLifeArea == it) null else it }
                    )
                }
                2 -> item {
                    BhriguBinduTransitsTab(
                        analysis = bbAnalysis,
                        language = language
                    )
                }
                3 -> item {
                    BhriguBinduRemediesTab(
                        analysis = bbAnalysis,
                        language = language,
                        onCopyMantra = { mantra -> clipboardManager.setText(AnnotatedString(mantra)) }
                    )
                }
            }
        }
    }
}

// ============================================
// UI Components
// ============================================

@Composable
private fun BhriguBinduTabSelector(
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

@Composable
private fun BhriguBinduOverviewTab(
    analysis: BhriguBinduCalculator.BhriguBinduAnalysis,
    chart: VedicChart,
    language: Language
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Main BB Position Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Title
                Text(
                    stringResource(StringKeyDosha.BHRIGU_BINDU_SUBTITLE),
                    style = MaterialTheme.typography.titleSmall,
                    color = AppTheme.TextMuted
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Longitude display
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    AppTheme.AccentGold.copy(alpha = 0.2f),
                                    AppTheme.AccentGold.copy(alpha = 0.05f)
                                )
                            )
                        )
                        .border(2.dp, AppTheme.AccentGold.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            String.format("%.2f°", analysis.bhriguBindu),
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.AccentGold
                        )
                        Text(
                            stringResource(StringKeyDosha.BHRIGU_BINDU_LONGITUDE),
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Calculation formula
                Surface(
                    color = AppTheme.AccentTeal.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(StringKeyDosha.BHRIGU_BINDU_CALCULATION),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.AccentTeal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "(${String.format("%.2f°", analysis.rahuLongitude)} + ${String.format("%.2f°", analysis.moonLongitude)}) / 2",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(color = AppTheme.BorderColor.copy(alpha = 0.5f))

                Spacer(modifier = Modifier.height(16.dp))

                // Position details
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    BBInfoChip(
                        label = stringResource(StringKeyDosha.BHRIGU_BINDU_SIGN),
                        value = analysis.bhriguBinduSign.getLocalizedName(language),
                        icon = Icons.Filled.Star
                    )
                    BBInfoChip(
                        label = stringResource(StringKeyDosha.BHRIGU_BINDU_HOUSE),
                        value = analysis.bhriguBinduHouse.toString(),
                        icon = Icons.Filled.Home
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nakshatra details card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    stringResource(StringKeyDosha.BHRIGU_BINDU_NAKSHATRA),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))

                BBDetailRow(
                    label = stringResource(StringKeyDosha.BHRIGU_BINDU_NAKSHATRA),
                    value = analysis.bhriguBinduNakshatra.getLocalizedName(language)
                )
                Spacer(modifier = Modifier.height(8.dp))
                BBDetailRow(
                    label = stringResource(StringKeyDosha.BHRIGU_BINDU_PADA),
                    value = analysis.bhriguBinduPada.toString()
                )
                Spacer(modifier = Modifier.height(8.dp))
                BBDetailRow(
                    label = stringResource(StringKeyDosha.BHRIGU_BINDU_NAKSHATRA_LORD),
                    value = analysis.nakshatraLord.getLocalizedName(language)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Strength assessment card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = getStrengthBackgroundColor(analysis.strengthAssessment.overallStrength)
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            stringResource(StringKeyDosha.BHRIGU_BINDU_STRENGTH),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            getStrengthText(analysis.strengthAssessment.overallStrength),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = getStrengthColor(analysis.strengthAssessment.overallStrength)
                        )
                    }

                    Icon(
                        getStrengthIcon(analysis.strengthAssessment.overallStrength),
                        contentDescription = null,
                        tint = getStrengthColor(analysis.strengthAssessment.overallStrength),
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Benefic/Malefic influence
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfluenceIndicator(
                        label = "Benefic",
                        value = analysis.strengthAssessment.beneficInfluence,
                        color = AppTheme.SuccessColor
                    )
                    InfluenceIndicator(
                        label = "Malefic",
                        value = analysis.strengthAssessment.maleficInfluence,
                        color = AppTheme.WarningColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lords card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Lords",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))

                PlanetLordRow(
                    label = stringResource(StringKeyDosha.BHRIGU_BINDU_LORD),
                    planet = analysis.bhriguBinduLord,
                    language = language
                )
                Spacer(modifier = Modifier.height(8.dp))
                PlanetLordRow(
                    label = stringResource(StringKeyDosha.BHRIGU_BINDU_NAKSHATRA_LORD),
                    planet = analysis.nakshatraLord,
                    language = language
                )
            }
        }

        // Conjunct planets (if any)
        if (analysis.conjunctPlanets.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Adjust,
                            contentDescription = null,
                            tint = AppTheme.AccentPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            stringResource(StringKeyDosha.BHRIGU_BINDU_CONJUNCTIONS),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    analysis.conjunctPlanets.forEach { planet ->
                        ConjunctPlanetChip(planet = planet, language = language)
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun BhriguBinduAnalysisTab(
    analysis: BhriguBinduCalculator.BhriguBinduAnalysis,
    chart: VedicChart,
    language: Language,
    expandedFactor: Int?,
    onExpandFactor: (Int) -> Unit,
    expandedLifeArea: LifeArea?,
    onExpandLifeArea: (LifeArea) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Karmic Significance
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.AccentGold.copy(alpha = 0.08f)),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.AutoAwesome,
                        contentDescription = null,
                        tint = AppTheme.AccentGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        stringResource(StringKeyDosha.BHRIGU_BINDU_KARMIC_SIGNIFICANCE),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    analysis.interpretation.karmicSignificance,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary,
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // General Meaning
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Position Interpretation",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    analysis.interpretation.generalMeaning,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Strength Factors
        Text(
            "Strength Factors",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        analysis.strengthAssessment.factors.forEachIndexed { index, factor ->
            StrengthFactorCard(
                factor = factor,
                isExpanded = expandedFactor == index,
                onToggle = { onExpandFactor(index) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Life Area Influences
        Text(
            stringResource(StringKeyDosha.BHRIGU_BINDU_LIFE_AREAS),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        analysis.interpretation.lifeAreas.forEach { lifeAreaInfluence ->
            LifeAreaCard(
                lifeAreaInfluence = lifeAreaInfluence,
                isExpanded = expandedLifeArea == lifeAreaInfluence.area,
                onToggle = { onExpandLifeArea(lifeAreaInfluence.area) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Aspecting Planets
        if (analysis.aspectingPlanets.isNotEmpty()) {
            Text(
                stringResource(StringKeyDosha.BHRIGU_BINDU_ASPECTS),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            analysis.aspectingPlanets.take(5).forEach { aspectingPlanet ->
                AspectingPlanetCard(aspectingPlanet = aspectingPlanet, language = language)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun BhriguBinduTransitsTab(
    analysis: BhriguBinduCalculator.BhriguBinduAnalysis,
    language: Language
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (analysis.transitAnalysis == null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = null,
                        tint = AppTheme.TextMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Transit data not available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }
            return
        }

        // Current Transits
        Text(
            "Current Planetary Positions",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        analysis.transitAnalysis.currentTransits.take(6).forEach { transit ->
            TransitCard(transit = transit, language = language)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Upcoming Transits
        if (analysis.transitAnalysis.upcomingTransits.isNotEmpty()) {
            Text(
                "Upcoming Significant Transits",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            analysis.transitAnalysis.upcomingTransits.forEach { upcomingTransit ->
                UpcomingTransitCard(upcomingTransit = upcomingTransit, language = language)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Transit Interpretation
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.AccentTeal.copy(alpha = 0.08f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Lightbulb,
                        contentDescription = null,
                        tint = AppTheme.AccentTeal,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Transit Timing",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "When slow-moving planets (Saturn, Jupiter, Rahu, Ketu) transit over your Bhrigu Bindu, " +
                    "significant karmic events tend to manifest. Pay special attention during these periods for life-changing opportunities and challenges.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun BhriguBinduRemediesTab(
    analysis: BhriguBinduCalculator.BhriguBinduAnalysis,
    language: Language,
    onCopyMantra: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Recommendations
        Text(
            stringResource(StringKeyDosha.BHRIGU_BINDU_RECOMMENDATIONS),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        analysis.interpretation.recommendations.forEach { recommendation ->
            RecommendationCard(recommendation = recommendation)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Remedial Measures
        Text(
            stringResource(StringKeyDosha.BHRIGU_BINDU_REMEDIES),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        analysis.interpretation.remedialMeasures.forEach { remedy ->
            RemedyCard(remedy = remedy, onCopyMantra = onCopyMantra)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Auspicious Days
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.AccentGold.copy(alpha = 0.08f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.CalendarToday,
                        contentDescription = null,
                        tint = AppTheme.AccentGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        stringResource(StringKeyDosha.BHRIGU_BINDU_AUSPICIOUS_DAYS),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                analysis.interpretation.auspiciousDays.forEach { day ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = AppTheme.SuccessColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            day,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppTheme.TextSecondary
                        )
                    }
                }
            }
        }
    }
}

// ============================================
// Reusable UI Components
// ============================================

@Composable
private fun BBInfoChip(
    label: String,
    value: String,
    icon: ImageVector
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(AppTheme.AccentPrimary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = AppTheme.AccentPrimary,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.TextSubtle
        )
        Text(
            value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = AppTheme.TextPrimary
        )
    }
}

@Composable
private fun BBDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.TextMuted
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = AppTheme.TextPrimary
        )
    }
}

@Composable
private fun PlanetLordRow(label: String, planet: Planet, language: Language) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.TextMuted
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(AppTheme.getPlanetColor(planet).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    planet.symbol,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.getPlanetColor(planet)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                planet.getLocalizedName(language),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextPrimary
            )
        }
    }
}

@Composable
private fun ConjunctPlanetChip(planet: Planet, language: Language) {
    Surface(
        color = AppTheme.getPlanetColor(planet).copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                planet.symbol,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.getPlanetColor(planet)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                planet.getLocalizedName(language),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextPrimary
            )
        }
    }
}

@Composable
private fun InfluenceIndicator(label: String, value: Double, color: Color) {
    Column {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.TextMuted
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                String.format("%.1f", value),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                Icons.Filled.Circle,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(8.dp)
            )
        }
    }
}

@Composable
private fun StrengthFactorCard(
    factor: BhriguBinduCalculator.StrengthFactor,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(300),
        label = "rotation"
    )

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
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(getInfluenceColor(factor.influence))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        factor.factor,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.TextPrimary
                    )
                }

                Icon(
                    Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotationAngle)
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = AppTheme.BorderColor.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        factor.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun LifeAreaCard(
    lifeAreaInfluence: BhriguBinduCalculator.LifeAreaInfluence,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    val rotationAngle by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(300),
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onToggle
            ),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        getLifeAreaIcon(lifeAreaInfluence.area),
                        contentDescription = null,
                        tint = getAreaInfluenceColor(lifeAreaInfluence.influence),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        getLifeAreaName(lifeAreaInfluence.area),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }

                Icon(
                    Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotationAngle)
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = AppTheme.BorderColor.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(12.dp))

                    Surface(
                        color = getAreaInfluenceColor(lifeAreaInfluence.influence).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            getAreaInfluenceName(lifeAreaInfluence.influence),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            color = getAreaInfluenceColor(lifeAreaInfluence.influence),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        lifeAreaInfluence.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun AspectingPlanetCard(
    aspectingPlanet: BhriguBinduCalculator.AspectingPlanet,
    language: Language
) {
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
                    .background(AppTheme.getPlanetColor(aspectingPlanet.planet).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    aspectingPlanet.planet.symbol,
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppTheme.getPlanetColor(aspectingPlanet.planet)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    aspectingPlanet.planet.getLocalizedName(language),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Text(
                    getAspectTypeName(aspectingPlanet.aspectType),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "${(aspectingPlanet.aspectStrength * 100).toInt()}%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = getAspectTypeColor(aspectingPlanet.aspectType)
                )
                if (aspectingPlanet.isApplying) {
                    Text(
                        "Applying",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.AccentTeal
                    )
                }
            }
        }
    }
}

@Composable
private fun TransitCard(
    transit: BhriguBinduCalculator.TransitingPlanet,
    language: Language
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (transit.isConjunct)
                AppTheme.AccentGold.copy(alpha = 0.08f)
            else
                AppTheme.CardBackground
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
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(AppTheme.getPlanetColor(transit.planet).copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            transit.planet.symbol,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppTheme.getPlanetColor(transit.planet)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            transit.planet.getLocalizedName(language),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            String.format("%.2f° from BB", transit.distanceFromBB),
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                if (transit.isConjunct) {
                    Surface(
                        color = AppTheme.AccentGold.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            "Conjunct",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.AccentGold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            if (transit.effectDescription.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    transit.effectDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
private fun UpcomingTransitCard(
    upcomingTransit: BhriguBinduCalculator.UpcomingTransit,
    language: Language
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        upcomingTransit.planet.getLocalizedName(language),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        getTransitTypeName(upcomingTransit.transitType),
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextMuted
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    upcomingTransit.estimatedDate.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
            }

            Icon(
                Icons.Filled.Event,
                contentDescription = null,
                tint = getTransitSignificanceColor(upcomingTransit.significance),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun RecommendationCard(recommendation: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.AccentTeal.copy(alpha = 0.08f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                Icons.Filled.Lightbulb,
                contentDescription = null,
                tint = AppTheme.AccentTeal,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                recommendation,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun RemedyCard(
    remedy: BhriguBinduCalculator.RemedialMeasure,
    onCopyMantra: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = when (remedy.priority) {
                RemedyPriority.ESSENTIAL -> AppTheme.WarningColor.copy(alpha = 0.08f)
                RemedyPriority.RECOMMENDED -> AppTheme.AccentGold.copy(alpha = 0.08f)
                RemedyPriority.OPTIONAL -> AppTheme.CardBackground
            }
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
                    Icon(
                        getRemedyCategoryIcon(remedy.category),
                        contentDescription = null,
                        tint = getRemedyPriorityColor(remedy.priority),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        getRemedyCategoryName(remedy.category),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = getRemedyPriorityColor(remedy.priority)
                    )
                }

                if (remedy.category == RemedyCategory.MANTRA) {
                    IconButton(
                        onClick = { onCopyMantra(remedy.remedy) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Filled.ContentCopy,
                            contentDescription = "Copy mantra",
                            tint = AppTheme.AccentGold,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                remedy.remedy,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextPrimary,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                remedy.timing,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun BhriguBinduInfoDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(StringKeyDosha.BHRIGU_BINDU_ABOUT),
                fontWeight = FontWeight.Bold,
                color = AppTheme.TextPrimary
            )
        },
        text = {
            Column {
                Text(
                    stringResource(StringKeyDosha.BHRIGU_BINDU_ABOUT_DESC),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    color = AppTheme.AccentTeal.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            stringResource(StringKeyDosha.BHRIGU_BINDU_CALCULATION),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.AccentTeal
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "The Bhrigu Bindu is calculated as the midpoint between Rahu and Moon positions, " +
                            "representing a sensitive karmic point in the chart.",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextSecondary,
                            lineHeight = 16.sp
                        )
                    }
                }
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

// ============================================
// Helper Functions
// ============================================

private fun getStrengthText(strength: OverallStrength): String = when (strength) {
    OverallStrength.EXCELLENT -> "Excellent"
    OverallStrength.GOOD -> "Good"
    OverallStrength.MODERATE -> "Moderate"
    OverallStrength.CHALLENGING -> "Challenging"
    OverallStrength.DIFFICULT -> "Difficult"
}

private fun getStrengthColor(strength: OverallStrength): Color = when (strength) {
    OverallStrength.EXCELLENT -> AppTheme.SuccessColor
    OverallStrength.GOOD -> Color(0xFF4CAF50)
    OverallStrength.MODERATE -> AppTheme.AccentTeal
    OverallStrength.CHALLENGING -> AppTheme.WarningColor
    OverallStrength.DIFFICULT -> AppTheme.ErrorColor
}

private fun getStrengthBackgroundColor(strength: OverallStrength): Color = when (strength) {
    OverallStrength.EXCELLENT -> AppTheme.SuccessColor.copy(alpha = 0.08f)
    OverallStrength.GOOD -> Color(0xFF4CAF50).copy(alpha = 0.08f)
    OverallStrength.MODERATE -> AppTheme.CardBackground
    OverallStrength.CHALLENGING -> AppTheme.WarningColor.copy(alpha = 0.08f)
    OverallStrength.DIFFICULT -> AppTheme.ErrorColor.copy(alpha = 0.08f)
}

private fun getStrengthIcon(strength: OverallStrength): ImageVector = when (strength) {
    OverallStrength.EXCELLENT -> Icons.Filled.Star
    OverallStrength.GOOD -> Icons.Filled.ThumbUp
    OverallStrength.MODERATE -> Icons.Filled.HorizontalRule
    OverallStrength.CHALLENGING -> Icons.Filled.Warning
    OverallStrength.DIFFICULT -> Icons.Filled.Error
}

private fun getInfluenceColor(influence: FactorInfluence): Color = when (influence) {
    FactorInfluence.HIGHLY_POSITIVE -> AppTheme.SuccessColor
    FactorInfluence.POSITIVE -> Color(0xFF4CAF50)
    FactorInfluence.NEUTRAL -> AppTheme.TextMuted
    FactorInfluence.CHALLENGING -> AppTheme.WarningColor
    FactorInfluence.NEGATIVE -> Color(0xFFFF9800)
    FactorInfluence.HIGHLY_NEGATIVE -> AppTheme.ErrorColor
}

private fun getAreaInfluenceColor(influence: AreaInfluence): Color = when (influence) {
    AreaInfluence.VERY_FAVORABLE -> AppTheme.SuccessColor
    AreaInfluence.FAVORABLE -> Color(0xFF4CAF50)
    AreaInfluence.NEUTRAL -> AppTheme.AccentTeal
    AreaInfluence.CHALLENGING -> AppTheme.WarningColor
    AreaInfluence.NEEDS_ATTENTION -> AppTheme.ErrorColor
}

private fun getAreaInfluenceName(influence: AreaInfluence): String = when (influence) {
    AreaInfluence.VERY_FAVORABLE -> "Very Favorable"
    AreaInfluence.FAVORABLE -> "Favorable"
    AreaInfluence.NEUTRAL -> "Neutral"
    AreaInfluence.CHALLENGING -> "Challenging"
    AreaInfluence.NEEDS_ATTENTION -> "Needs Attention"
}

private fun getLifeAreaIcon(area: LifeArea): ImageVector = when (area) {
    LifeArea.CAREER -> Icons.Filled.Work
    LifeArea.RELATIONSHIPS -> Icons.Filled.Favorite
    LifeArea.HEALTH -> Icons.Filled.LocalHospital
    LifeArea.SPIRITUALITY -> Icons.Filled.SelfImprovement
    LifeArea.WEALTH -> Icons.Filled.AttachMoney
    LifeArea.FAMILY -> Icons.Filled.FamilyRestroom
    LifeArea.EDUCATION -> Icons.Filled.School
    LifeArea.FOREIGN_CONNECTIONS -> Icons.Filled.Flight
}

private fun getLifeAreaName(area: LifeArea): String = when (area) {
    LifeArea.CAREER -> "Career"
    LifeArea.RELATIONSHIPS -> "Relationships"
    LifeArea.HEALTH -> "Health"
    LifeArea.SPIRITUALITY -> "Spirituality"
    LifeArea.WEALTH -> "Wealth"
    LifeArea.FAMILY -> "Family"
    LifeArea.EDUCATION -> "Education"
    LifeArea.FOREIGN_CONNECTIONS -> "Foreign Connections"
}

private fun getAspectTypeName(aspectType: AspectType): String = when (aspectType) {
    AspectType.CONJUNCTION -> "Conjunction"
    AspectType.OPPOSITION -> "Opposition"
    AspectType.TRINE -> "Trine"
    AspectType.SQUARE -> "Square"
    AspectType.SEXTILE -> "Sextile"
    AspectType.SPECIAL_ASPECT -> "Special Aspect"
}

private fun getAspectTypeColor(aspectType: AspectType): Color = when (aspectType) {
    AspectType.CONJUNCTION -> AppTheme.AccentGold
    AspectType.OPPOSITION -> AppTheme.ErrorColor
    AspectType.TRINE -> AppTheme.SuccessColor
    AspectType.SQUARE -> AppTheme.WarningColor
    AspectType.SEXTILE -> AppTheme.AccentTeal
    AspectType.SPECIAL_ASPECT -> AppTheme.AccentPrimary
}

private fun getTransitTypeName(transitType: BhriguBinduCalculator.TransitType): String = when (transitType) {
    BhriguBinduCalculator.TransitType.CONJUNCTION -> "Conjunction"
    BhriguBinduCalculator.TransitType.OPPOSITION -> "Opposition"
    BhriguBinduCalculator.TransitType.TRINE -> "Trine"
    BhriguBinduCalculator.TransitType.SQUARE -> "Square"
    BhriguBinduCalculator.TransitType.ENTERING_SIGN -> "Entering Sign"
}

private fun getTransitSignificanceColor(significance: BhriguBinduCalculator.TransitSignificance): Color = when (significance) {
    BhriguBinduCalculator.TransitSignificance.HIGHLY_SIGNIFICANT -> AppTheme.AccentGold
    BhriguBinduCalculator.TransitSignificance.SIGNIFICANT -> AppTheme.AccentPrimary
    BhriguBinduCalculator.TransitSignificance.MODERATE -> AppTheme.AccentTeal
    BhriguBinduCalculator.TransitSignificance.MINOR -> AppTheme.TextMuted
}

private fun getRemedyCategoryIcon(category: RemedyCategory): ImageVector = when (category) {
    RemedyCategory.MANTRA -> Icons.Filled.RecordVoiceOver
    RemedyCategory.CHARITY -> Icons.Filled.VolunteerActivism
    RemedyCategory.GEMSTONE -> Icons.Filled.Diamond
    RemedyCategory.FASTING -> Icons.Filled.Restaurant
    RemedyCategory.PILGRIMAGE -> Icons.Filled.Place
    RemedyCategory.LIFESTYLE -> Icons.Filled.Spa
}

private fun getRemedyCategoryName(category: RemedyCategory): String = when (category) {
    RemedyCategory.MANTRA -> "Mantra"
    RemedyCategory.CHARITY -> "Charity"
    RemedyCategory.GEMSTONE -> "Gemstone"
    RemedyCategory.FASTING -> "Fasting"
    RemedyCategory.PILGRIMAGE -> "Pilgrimage"
    RemedyCategory.LIFESTYLE -> "Lifestyle"
}

private fun getRemedyPriorityColor(priority: RemedyPriority): Color = when (priority) {
    RemedyPriority.ESSENTIAL -> AppTheme.ErrorColor
    RemedyPriority.RECOMMENDED -> AppTheme.AccentGold
    RemedyPriority.OPTIONAL -> AppTheme.AccentTeal
}
