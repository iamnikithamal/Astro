package com.astro.storm.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.ShodashvargaCalculator
import com.astro.storm.ephemeris.ShodashvargaCalculator.PlanetShodashvargaStrength
import com.astro.storm.ephemeris.ShodashvargaCalculator.ShodashvargaAnalysis
import com.astro.storm.ephemeris.ShodashvargaCalculator.StrengthGrade
import com.astro.storm.ephemeris.ShodashvargaCalculator.VargaDignity
import com.astro.storm.ephemeris.ShodashvargaCalculator.VargaType
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Shodashvarga Strength Analysis Screen
 *
 * Comprehensive 16-divisional chart strength analysis displaying:
 * - Overall planetary strength summary
 * - Individual planet strength across all 16 vargas (D1 to D60)
 * - Vimsopaka Bala calculations (Poorva, Madhya, Para schemes)
 * - Vargottama planets identification
 * - Strength grades and detailed interpretations
 *
 * Based on classical Vedic astrology texts including:
 * - Brihat Parashara Hora Shastra (BPHS)
 * - Hora Ratna
 * - Phaladeepika
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShodashvargaScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = stringResource(StringKeyDosha.SHODASHVARGA_TITLE),
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = LocalLanguage.current
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedPlanet by remember { mutableStateOf<Planet?>(null) }
    var isCalculating by remember { mutableStateOf(true) }
    var shodashvargaAnalysis by remember { mutableStateOf<ShodashvargaAnalysis?>(null) }

    val tabs = listOf(
        stringResource(StringKeyDosha.SHADBALA_OVERVIEW),
        stringResource(StringKeyDosha.VARGA_POSITIONS),
        stringResource(StringKeyDosha.VIMSOPAKA_BALA),
        stringResource(StringKeyDosha.VARGOTTAMA_TITLE)
    )

    // Calculate Shodashvarga
    LaunchedEffect(chart) {
        isCalculating = true
        delay(300) // Brief delay for smooth UI
        try {
            shodashvargaAnalysis = withContext(Dispatchers.Default) {
                ShodashvargaCalculator.analyzeShodashvarga(chart)
            }
        } catch (e: Exception) {
            // Handle error silently, analysis will be null
        }
        isCalculating = false
    }

    if (showInfoDialog) {
        ShodashvargaInfoDialog(onDismiss = { showInfoDialog = false })
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(StringKeyDosha.SHODASHVARGA_TITLE),
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
                            contentDescription = stringResource(StringKeyDosha.SHODASHVARGA_ABOUT),
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = AppTheme.AccentPrimary,
                        strokeWidth = 4.dp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        stringResource(StringKey.DASHA_CALCULATING),
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextMuted
                    )
                }
            }
        } else if (shodashvargaAnalysis == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Failed to calculate Shodashvarga analysis",
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.ErrorColor
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(AppTheme.ScreenBackground),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // Tab selector
                item {
                    ShodashvargaTabSelector(
                        tabs = tabs,
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )
                }

                // Tab content
                when (selectedTab) {
                    0 -> item {
                        OverviewTab(
                            analysis = shodashvargaAnalysis!!,
                            language = language
                        )
                    }
                    1 -> item {
                        PlanetStrengthsTab(
                            analysis = shodashvargaAnalysis!!,
                            selectedPlanet = selectedPlanet,
                            onSelectPlanet = { selectedPlanet = if (selectedPlanet == it) null else it },
                            language = language
                        )
                    }
                    2 -> item {
                        VimsopakaTab(
                            analysis = shodashvargaAnalysis!!,
                            language = language
                        )
                    }
                    3 -> item {
                        VargottamaTab(
                            analysis = shodashvargaAnalysis!!,
                            language = language
                        )
                    }
                }
            }
        }
    }
}

// ============================================
// TAB SELECTOR
// ============================================

@Composable
private fun ShodashvargaTabSelector(
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
// OVERVIEW TAB
// ============================================

@Composable
private fun OverviewTab(
    analysis: ShodashvargaAnalysis,
    language: Language
) {
    val averageStrength = analysis.overallAssessment.averageStrength
    val overallProgress by animateFloatAsState(
        targetValue = (averageStrength / 16.0).coerceIn(0.0, 1.0).toFloat(),
        animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing),
        label = "overall_progress"
    )

    Column(modifier = Modifier.padding(16.dp)) {
        // Overall Strength Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(StringKeyDosha.AVERAGE_STRENGTH),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )

                Spacer(modifier = Modifier.height(20.dp))

                Box(
                    modifier = Modifier.size(160.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        progress = { 1f },
                        modifier = Modifier.fillMaxSize(),
                        strokeWidth = 14.dp,
                        color = AppTheme.ChipBackground,
                        strokeCap = StrokeCap.Round
                    )
                    CircularProgressIndicator(
                        progress = { overallProgress },
                        modifier = Modifier.fillMaxSize(),
                        strokeWidth = 14.dp,
                        color = getStrengthColorFromBala(averageStrength),
                        strokeCap = StrokeCap.Round
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = String.format("%.1f", averageStrength),
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            text = "/16",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Strength distribution
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    val excellent = analysis.overallAssessment.strengthDistribution.count { it.value == StrengthGrade.EXCELLENT }
                    val good = analysis.overallAssessment.strengthDistribution.count { it.value == StrengthGrade.GOOD }
                    val weak = analysis.overallAssessment.strengthDistribution.count {
                        it.value == StrengthGrade.WEAK || it.value == StrengthGrade.VERY_WEAK
                    }

                    StrengthCountBadge(
                        label = stringResource(StringKeyDosha.STRENGTH_EXCELLENT),
                        count = excellent,
                        color = AppTheme.SuccessColor
                    )
                    StrengthCountBadge(
                        label = stringResource(StringKeyDosha.STRENGTH_GOOD),
                        count = good,
                        color = AppTheme.AccentPrimary
                    )
                    StrengthCountBadge(
                        label = stringResource(StringKeyDosha.STRENGTH_WEAK),
                        count = weak,
                        color = AppTheme.WarningColor
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Strongest and Weakest Planet
        Row(modifier = Modifier.fillMaxWidth()) {
            analysis.overallAssessment.strongestPlanet?.let { strongest ->
                StrongestWeakestCard(
                    title = stringResource(StringKeyDosha.STRONGEST_PLANET),
                    planet = strongest,
                    strength = analysis.planetStrengths[strongest],
                    isStrong = true,
                    modifier = Modifier.weight(1f),
                    language = language
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            analysis.overallAssessment.weakestPlanet?.let { weakest ->
                StrongestWeakestCard(
                    title = stringResource(StringKeyDosha.WEAKEST_PLANET),
                    planet = weakest,
                    strength = analysis.planetStrengths[weakest],
                    isStrong = false,
                    modifier = Modifier.weight(1f),
                    language = language
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Key Insights
        if (analysis.overallAssessment.keyInsights.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Lightbulb,
                            contentDescription = null,
                            tint = AppTheme.AccentGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            stringResource(StringKeyDosha.KEY_INSIGHTS),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    analysis.overallAssessment.keyInsights.forEach { insight ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text(
                                "•",
                                style = MaterialTheme.typography.bodyMedium,
                                color = AppTheme.AccentPrimary,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                insight,
                                style = MaterialTheme.typography.bodyMedium,
                                color = AppTheme.TextSecondary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Planet Summary List
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    stringResource(StringKeyDosha.SHODASHVARGA_BALA),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                analysis.planetStrengths.entries
                    .sortedByDescending { it.value.shodashvargaBala }
                    .forEach { (planet, strength) ->
                        PlanetStrengthRow(
                            planet = planet,
                            strength = strength,
                            language = language
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
            }
        }
    }
}

@Composable
private fun StrengthCountBadge(
    label: String,
    count: Int,
    color: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                count.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.TextMuted
        )
    }
}

@Composable
private fun StrongestWeakestCard(
    title: String,
    planet: Planet,
    strength: PlanetShodashvargaStrength?,
    isStrong: Boolean,
    modifier: Modifier = Modifier,
    language: Language
) {
    val accentColor = if (isStrong) AppTheme.SuccessColor else AppTheme.WarningColor

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = accentColor.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                title,
                style = MaterialTheme.typography.labelMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    planet.symbol,
                    style = MaterialTheme.typography.headlineSmall,
                    color = accentColor
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                planet.getLocalizedName(language),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            strength?.let {
                Text(
                    String.format("%.2f/16", it.shodashvargaBala),
                    style = MaterialTheme.typography.bodySmall,
                    color = accentColor
                )
            }
        }
    }
}

@Composable
private fun PlanetStrengthRow(
    planet: Planet,
    strength: PlanetShodashvargaStrength,
    language: Language
) {
    val progress by animateFloatAsState(
        targetValue = (strength.shodashvargaBala / 16.0).coerceIn(0.0, 1.0).toFloat(),
        animationSpec = tween(800),
        label = "progress"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(AppTheme.getPlanetColor(planet).copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                planet.symbol,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.getPlanetColor(planet)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    planet.getLocalizedName(language),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
                Text(
                    String.format("%.2f/16", strength.shodashvargaBala),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = getStrengthColorFromBala(strength.shodashvargaBala)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = getStrengthColorFromBala(strength.shodashvargaBala),
                trackColor = AppTheme.ChipBackground
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Icon(
            getStrengthIcon(strength.strengthGrade),
            contentDescription = null,
            tint = getStrengthGradeColor(strength.strengthGrade),
            modifier = Modifier.size(18.dp)
        )
    }
}

// ============================================
// PLANET STRENGTHS TAB
// ============================================

@Composable
private fun PlanetStrengthsTab(
    analysis: ShodashvargaAnalysis,
    selectedPlanet: Planet?,
    onSelectPlanet: (Planet) -> Unit,
    language: Language
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Planet selector chips
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(analysis.planetStrengths.keys.toList()) { planet ->
                val isSelected = selectedPlanet == planet
                val strength = analysis.planetStrengths[planet]

                FilterChip(
                    selected = isSelected,
                    onClick = { onSelectPlanet(planet) },
                    label = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(planet.symbol)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(planet.getLocalizedName(language))
                        }
                    },
                    leadingIcon = if (isSelected) {
                        {
                            Icon(
                                getStrengthIcon(strength?.strengthGrade ?: StrengthGrade.AVERAGE),
                                contentDescription = null,
                                tint = getStrengthGradeColor(strength?.strengthGrade ?: StrengthGrade.AVERAGE),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    } else null,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = AppTheme.getPlanetColor(planet).copy(alpha = 0.15f),
                        selectedLabelColor = AppTheme.getPlanetColor(planet),
                        containerColor = AppTheme.ChipBackground,
                        labelColor = AppTheme.TextSecondary
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Planet details
        val strength = if (selectedPlanet != null) {
            analysis.planetStrengths[selectedPlanet]
        } else {
            analysis.planetStrengths.entries.maxByOrNull { it.value.shodashvargaBala }?.value
        }

        strength?.let {
            PlanetDetailCard(strength = it, language = language)
        }
    }
}

@Composable
private fun PlanetDetailCard(
    strength: PlanetShodashvargaStrength,
    language: Language
) {
    val planet = strength.planet
    val accentColor = AppTheme.getPlanetColor(planet)

    Column {
        // Header Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = accentColor.copy(alpha = 0.08f)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            planet.getLocalizedName(language),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            stringResource(StringKeyDosha.SHODASHVARGA_SUBTITLE),
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(accentColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            planet.symbol,
                            style = MaterialTheme.typography.headlineMedium,
                            color = accentColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Strength summary
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    StrengthMetric(
                        label = stringResource(StringKeyDosha.SHADVARGA_BALA),
                        value = strength.shadvargaBala,
                        maxValue = 6.0
                    )
                    VerticalDivider(
                        modifier = Modifier.height(50.dp),
                        color = AppTheme.BorderColor
                    )
                    StrengthMetric(
                        label = stringResource(StringKeyDosha.DASHAVARGA_BALA),
                        value = strength.dashavargaBala,
                        maxValue = 10.0
                    )
                    VerticalDivider(
                        modifier = Modifier.height(50.dp),
                        color = AppTheme.BorderColor
                    )
                    StrengthMetric(
                        label = stringResource(StringKeyDosha.SHODASHVARGA_BALA),
                        value = strength.shodashvargaBala,
                        maxValue = 16.0,
                        isPrimary = true
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Strength grade chip
                Surface(
                    color = getStrengthGradeColor(strength.strengthGrade).copy(alpha = 0.15f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            getStrengthIcon(strength.strengthGrade),
                            contentDescription = null,
                            tint = getStrengthGradeColor(strength.strengthGrade),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            getLocalizedStrengthGrade(strength.strengthGrade, language),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = getStrengthGradeColor(strength.strengthGrade)
                        )
                    }
                }

                if (strength.vargottamaCount > 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = AppTheme.AccentGold.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = AppTheme.AccentGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                "Vargottama in ${strength.vargottamaCount} chart(s)",
                                style = MaterialTheme.typography.bodySmall,
                                color = AppTheme.AccentGold
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Varga Positions Table
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    stringResource(StringKeyDosha.VARGA_POSITIONS),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                VargaType.entries.forEach { vargaType ->
                    val position = strength.vargaPositions[vargaType]
                    position?.let {
                        VargaPositionRow(
                            vargaType = vargaType,
                            position = it,
                            language = language
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Interpretation Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Description,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Interpretation",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    strength.interpretation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun StrengthMetric(
    label: String,
    value: Double,
    maxValue: Double,
    isPrimary: Boolean = false
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            String.format("%.2f", value),
            style = if (isPrimary) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = if (isPrimary) AppTheme.AccentPrimary else AppTheme.TextPrimary
        )
        Text(
            "/${"%.0f".format(maxValue)}",
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.TextMuted
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.TextMuted,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun VargaPositionRow(
    vargaType: VargaType,
    position: ShodashvargaCalculator.VargaPosition,
    language: Language
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (position.isVargottama) AppTheme.AccentGold.copy(alpha = 0.05f)
                else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                vargaType.name,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextPrimary,
                modifier = Modifier.width(60.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                position.sign.getLocalizedName(language),
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary,
                modifier = Modifier.weight(1f)
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                color = getDignityColor(position.dignity).copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    getLocalizedDignity(position.dignity, language),
                    style = MaterialTheme.typography.labelSmall,
                    color = getDignityColor(position.dignity),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            if (position.isVargottama) {
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Filled.Star,
                    contentDescription = "Vargottama",
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

// ============================================
// VIMSOPAKA TAB
// ============================================

@Composable
private fun VimsopakaTab(
    analysis: ShodashvargaAnalysis,
    language: Language
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        stringResource(StringKeyDosha.VIMSOPAKA_BALA),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "Vimsopaka Bala assigns different weights to the 16 vargas based on their importance. Three classical schemes (Poorva, Madhya, Para) are used.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Vimsopaka comparison for all planets
        analysis.planetStrengths.entries
            .sortedByDescending { it.value.vimsopakaBalaPara }
            .forEach { (planet, strength) ->
                VimsopakaCard(
                    planet = planet,
                    strength = strength,
                    language = language
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
    }
}

@Composable
private fun VimsopakaCard(
    planet: Planet,
    strength: PlanetShodashvargaStrength,
    language: Language
) {
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
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(AppTheme.getPlanetColor(planet).copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            planet.symbol,
                            style = MaterialTheme.typography.bodyLarge,
                            color = AppTheme.getPlanetColor(planet)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        planet.getLocalizedName(language),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }

                Surface(
                    color = getStrengthGradeColor(strength.strengthGrade).copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        getLocalizedStrengthGrade(strength.strengthGrade, language),
                        style = MaterialTheme.typography.labelSmall,
                        color = getStrengthGradeColor(strength.strengthGrade),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Three schemes
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                VimsopakaSchemeColumn(
                    label = stringResource(StringKeyDosha.VIMSOPAKA_POORVA),
                    value = strength.vimsopakaBalaPoorva,
                    modifier = Modifier.weight(1f)
                )
                VimsopakaSchemeColumn(
                    label = stringResource(StringKeyDosha.VIMSOPAKA_MADHYA),
                    value = strength.vimsopakaBalaMadhya,
                    modifier = Modifier.weight(1f)
                )
                VimsopakaSchemeColumn(
                    label = stringResource(StringKeyDosha.VIMSOPAKA_PARA),
                    value = strength.vimsopakaBalaPara,
                    modifier = Modifier.weight(1f),
                    isPrimary = true
                )
            }
        }
    }
}

@Composable
private fun VimsopakaSchemeColumn(
    label: String,
    value: Double,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.TextMuted
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            String.format("%.2f", value),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = if (isPrimary) FontWeight.Bold else FontWeight.SemiBold,
            color = if (isPrimary) AppTheme.AccentPrimary else AppTheme.TextPrimary
        )
        Text(
            "/20",
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.TextMuted
        )

        Spacer(modifier = Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = { (value / 20.0).coerceIn(0.0, 1.0).toFloat() },
            modifier = Modifier
                .width(60.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = if (isPrimary) AppTheme.AccentPrimary else AppTheme.TextSecondary,
            trackColor = AppTheme.ChipBackground
        )
    }
}

// ============================================
// VARGOTTAMA TAB
// ============================================

@Composable
private fun VargottamaTab(
    analysis: ShodashvargaAnalysis,
    language: Language
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Info Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = null,
                        tint = AppTheme.AccentGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        stringResource(StringKeyDosha.VARGOTTAMA_TITLE),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    stringResource(StringKeyDosha.VARGOTTAMA_DESC),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (analysis.vargottamaPlanets.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Outlined.StarBorder,
                            contentDescription = null,
                            tint = AppTheme.TextMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "No Vargottama planets found",
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppTheme.TextMuted
                        )
                    }
                }
            }
        } else {
            analysis.vargottamaPlanets.groupBy { it.planet }.forEach { (planet, vargottamas) ->
                VargottamaPlanetCard(
                    planet = planet,
                    vargottamas = vargottamas,
                    language = language
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun VargottamaPlanetCard(
    planet: Planet,
    vargottamas: List<ShodashvargaCalculator.VargottamaInfo>,
    language: Language
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.AccentGold.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(AppTheme.getPlanetColor(planet).copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        planet.symbol,
                        style = MaterialTheme.typography.headlineSmall,
                        color = AppTheme.getPlanetColor(planet)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        planet.getLocalizedName(language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = AppTheme.AccentGold,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Vargottama in ${vargottamas.size} chart(s)",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.AccentGold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            vargottamas.forEach { vargottama ->
                Surface(
                    color = AppTheme.CardBackground,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                vargottama.vargaType.name,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                            Text(
                                vargottama.sign.getLocalizedName(language),
                                style = MaterialTheme.typography.bodyMedium,
                                color = AppTheme.AccentPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            vargottama.significance,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted,
                            lineHeight = 16.sp
                        )
                    }
                }
                if (vargottama != vargottamas.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

// ============================================
// INFO DIALOG
// ============================================

@Composable
private fun ShodashvargaInfoDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(StringKeyDosha.SHODASHVARGA_ABOUT),
                fontWeight = FontWeight.Bold,
                color = AppTheme.TextPrimary
            )
        },
        text = {
            LazyColumn {
                item {
                    Text(
                        stringResource(StringKeyDosha.SHODASHVARGA_ABOUT_DESC),
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextSecondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "The 16 Divisional Charts:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                items(VargaType.entries.toList()) { varga ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            varga.name.padEnd(12),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.AccentPrimary,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                        )
                        Text(
                            "- ${varga.domain}",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
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
// HELPER FUNCTIONS
// ============================================

private fun getStrengthColorFromBala(bala: Double): Color {
    return when {
        bala >= 15.0 -> AppTheme.SuccessColor
        bala >= 12.0 -> Color(0xFF8BC34A)
        bala >= 9.0 -> AppTheme.AccentPrimary
        bala >= 6.0 -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }
}

private fun getStrengthGradeColor(grade: StrengthGrade): Color {
    return when (grade) {
        StrengthGrade.EXCELLENT -> AppTheme.SuccessColor
        StrengthGrade.GOOD -> Color(0xFF8BC34A)
        StrengthGrade.AVERAGE -> AppTheme.AccentPrimary
        StrengthGrade.WEAK -> AppTheme.WarningColor
        StrengthGrade.VERY_WEAK -> AppTheme.ErrorColor
    }
}

private fun getStrengthIcon(grade: StrengthGrade): ImageVector {
    return when (grade) {
        StrengthGrade.EXCELLENT -> Icons.Filled.CheckCircle
        StrengthGrade.GOOD -> Icons.Filled.ThumbUp
        StrengthGrade.AVERAGE -> Icons.Outlined.RemoveCircle
        StrengthGrade.WEAK -> Icons.Outlined.Warning
        StrengthGrade.VERY_WEAK -> Icons.Filled.Error
    }
}

private fun getDignityColor(dignity: VargaDignity): Color {
    return when (dignity) {
        VargaDignity.EXALTED -> AppTheme.SuccessColor
        VargaDignity.MOOLATRIKONA -> Color(0xFF8BC34A)
        VargaDignity.OWN_SIGN -> AppTheme.AccentPrimary
        VargaDignity.GREAT_FRIEND -> Color(0xFF4CAF50)
        VargaDignity.FRIEND -> Color(0xFF81C784)
        VargaDignity.NEUTRAL -> AppTheme.TextMuted
        VargaDignity.ENEMY -> AppTheme.WarningColor
        VargaDignity.GREAT_ENEMY -> Color(0xFFFF7043)
        VargaDignity.DEBILITATED -> AppTheme.ErrorColor
    }
}

private fun getLocalizedStrengthGrade(grade: StrengthGrade, language: Language): String {
    return when (grade) {
        StrengthGrade.EXCELLENT -> StringResources.get(StringKeyDosha.STRENGTH_EXCELLENT, language)
        StrengthGrade.GOOD -> StringResources.get(StringKeyDosha.STRENGTH_GOOD, language)
        StrengthGrade.AVERAGE -> StringResources.get(StringKeyDosha.STRENGTH_AVERAGE, language)
        StrengthGrade.WEAK -> StringResources.get(StringKeyDosha.STRENGTH_WEAK, language)
        StrengthGrade.VERY_WEAK -> StringResources.get(StringKeyDosha.STRENGTH_VERY_WEAK, language)
    }
}

private fun getLocalizedDignity(dignity: VargaDignity, language: Language): String {
    return when (dignity) {
        VargaDignity.EXALTED -> StringResources.get(StringKeyDosha.DIGNITY_EXALTED, language)
        VargaDignity.MOOLATRIKONA -> StringResources.get(StringKeyDosha.DIGNITY_MOOLATRIKONA, language)
        VargaDignity.OWN_SIGN -> StringResources.get(StringKeyDosha.DIGNITY_OWN_SIGN, language)
        VargaDignity.GREAT_FRIEND -> StringResources.get(StringKeyDosha.DIGNITY_GREAT_FRIEND, language)
        VargaDignity.FRIEND -> StringResources.get(StringKeyDosha.DIGNITY_FRIEND, language)
        VargaDignity.NEUTRAL -> StringResources.get(StringKeyDosha.DIGNITY_NEUTRAL, language)
        VargaDignity.ENEMY -> StringResources.get(StringKeyDosha.DIGNITY_ENEMY, language)
        VargaDignity.GREAT_ENEMY -> StringResources.get(StringKeyDosha.DIGNITY_GREAT_ENEMY, language)
        VargaDignity.DEBILITATED -> StringResources.get(StringKeyDosha.DIGNITY_DEBILITATED, language)
    }
}
