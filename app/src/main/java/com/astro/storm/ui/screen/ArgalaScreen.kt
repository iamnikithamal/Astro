package com.astro.storm.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.currentLanguage
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.ArgalaCalculator
import com.astro.storm.ephemeris.ArgalaCalculator.ArgalaAnalysis
import com.astro.storm.ephemeris.ArgalaCalculator.ArgalaNature
import com.astro.storm.ephemeris.ArgalaCalculator.ArgalaStrength
import com.astro.storm.ephemeris.ArgalaCalculator.ArgalaType
import com.astro.storm.ephemeris.ArgalaCalculator.HouseArgalaResult
import com.astro.storm.ephemeris.ArgalaCalculator.PlanetArgalaResult
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Argala Analysis Screen
 *
 * Comprehensive Jaimini Argala (intervention) analysis based on classical texts:
 * - Argala: Planetary intervention from specific houses (2nd, 4th, 11th, 5th)
 * - Virodha Argala: Obstruction of Argala (12th, 10th, 3rd, 9th)
 * - Shubha vs Ashubha: Benefic vs Malefic interventions
 * - Net Effect: Final result after considering obstructions
 *
 * References: Jaimini Sutras, Commentary by Raghunatha Bhatta
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArgalaScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = stringResource(StringKeyDosha.ARGALA_TITLE),
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = currentLanguage()
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedHouse by remember { mutableIntStateOf(1) }
    var selectedPlanet by remember { mutableStateOf<Planet?>(null) }
    var isCalculating by remember { mutableStateOf(true) }
    var argalaAnalysis by remember { mutableStateOf<ArgalaAnalysis?>(null) }

    val tabs = listOf(
        stringResource(StringKeyDosha.ARGALA_ABOUT),
        stringResource(StringKey.FEATURE_HOUSES),
        stringResource(StringKey.FEATURE_PLANETS)
    )

    // Calculate Argala
    LaunchedEffect(chart) {
        isCalculating = true
        delay(300)
        try {
            argalaAnalysis = withContext(Dispatchers.Default) {
                ArgalaCalculator.analyzeArgala(chart)
            }
        } catch (e: Exception) {
            // Handle error silently
        }
        isCalculating = false
    }

    if (showInfoDialog) {
        ArgalaInfoDialog(onDismiss = { showInfoDialog = false })
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(StringKeyDosha.ARGALA_TITLE),
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
                            contentDescription = stringResource(StringKeyDosha.ARGALA_ABOUT),
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
                        "Calculating Argala...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextMuted
                    )
                }
            }
        } else if (argalaAnalysis == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Failed to calculate Argala",
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
                    ArgalaTabSelector(
                        tabs = tabs,
                        selectedTab = selectedTab,
                        onTabSelected = { selectedTab = it }
                    )
                }

                // Tab content
                when (selectedTab) {
                    0 -> item {
                        ArgalaOverviewTab(
                            analysis = argalaAnalysis!!,
                            language = language
                        )
                    }
                    1 -> item {
                        ArgalaHousesTab(
                            analysis = argalaAnalysis!!,
                            selectedHouse = selectedHouse,
                            onSelectHouse = { selectedHouse = it },
                            language = language
                        )
                    }
                    2 -> item {
                        ArgalaPlanetsTab(
                            analysis = argalaAnalysis!!,
                            selectedPlanet = selectedPlanet,
                            onSelectPlanet = { selectedPlanet = if (selectedPlanet == it) null else it },
                            language = language,
                            chart = chart
                        )
                    }
                }
            }
        }
    }
}

// ============================================
// UI Components
// ============================================

@Composable
private fun ArgalaTabSelector(
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
private fun ArgalaOverviewTab(
    analysis: ArgalaAnalysis,
    language: Language
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // About Argala Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(AppTheme.AccentGold.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.Bolt,
                            contentDescription = null,
                            tint = AppTheme.AccentGold,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            stringResource(StringKeyDosha.ARGALA_TITLE),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            stringResource(StringKeyDosha.ARGALA_SUBTITLE),
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    stringResource(StringKeyDosha.ARGALA_ABOUT_DESC),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary,
                    lineHeight = 22.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Argala Types Explanation
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Argala Types",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                ArgalaTypeItem(
                    icon = Icons.Filled.Star,
                    title = stringResource(StringKeyDosha.ARGALA_PRIMARY),
                    description = "4th & 11th houses create primary interventions",
                    color = AppTheme.AccentGold
                )

                Spacer(modifier = Modifier.height(10.dp))

                ArgalaTypeItem(
                    icon = Icons.Filled.StarHalf,
                    title = stringResource(StringKeyDosha.ARGALA_SECONDARY),
                    description = "2nd house creates secondary interventions",
                    color = AppTheme.AccentTeal
                )

                Spacer(modifier = Modifier.height(10.dp))

                ArgalaTypeItem(
                    icon = Icons.Filled.AutoAwesome,
                    title = stringResource(StringKeyDosha.ARGALA_FIFTH_HOUSE),
                    description = "5th house creates special interventions",
                    color = AppTheme.InfoColor
                )

                Spacer(modifier = Modifier.height(10.dp))

                ArgalaTypeItem(
                    icon = Icons.Filled.Block,
                    title = stringResource(StringKeyDosha.ARGALA_VIRODHA),
                    description = "12th, 10th, 3rd, 9th houses obstruct Argala",
                    color = AppTheme.WarningColor
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Overall Assessment
        OverallArgalaCard(analysis = analysis, language = language)

        Spacer(modifier = Modifier.height(16.dp))

        // Karma Pattern Analysis
        if (analysis.overallAssessment.karmaPatterns.isNotEmpty()) {
            KarmaPatternCard(analysis = analysis)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Significant Argalas
        if (analysis.significantArgalas.isNotEmpty()) {
            SignificantArgalasCard(analysis = analysis, language = language)
        }
    }
}

@Composable
private fun ArgalaTypeItem(
    icon: ImageVector,
    title: String,
    description: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(color.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun OverallArgalaCard(
    analysis: ArgalaAnalysis,
    language: Language
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "Chart-Wide Argala Patterns",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Strongest Benefic Argala
            analysis.overallAssessment.strongestBeneficArgala?.let { house ->
                ArgalaHighlightRow(
                    label = "Strongest Support",
                    value = "House $house",
                    icon = Icons.Filled.TrendingUp,
                    color = AppTheme.SuccessColor
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Strongest Malefic Argala
            analysis.overallAssessment.strongestMaleficArgala?.let { house ->
                ArgalaHighlightRow(
                    label = "Greatest Challenge",
                    value = "House $house",
                    icon = Icons.Filled.TrendingDown,
                    color = AppTheme.WarningColor
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Most Obstructed
            analysis.overallAssessment.mostObstructedHouse?.let { house ->
                ArgalaHighlightRow(
                    label = "Most Obstructed",
                    value = "House $house",
                    icon = Icons.Filled.Block,
                    color = AppTheme.ErrorColor
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Least Obstructed
            analysis.overallAssessment.leastObstructedHouse?.let { house ->
                ArgalaHighlightRow(
                    label = "Least Obstructed",
                    value = "House $house",
                    icon = Icons.Filled.CheckCircle,
                    color = AppTheme.InfoColor
                )
            }

            // Recommendations
            if (analysis.overallAssessment.generalRecommendations.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = AppTheme.BorderColor.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Recommendations",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                analysis.overallAssessment.generalRecommendations.take(3).forEach { recommendation ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            Icons.Filled.LightbulbCircle,
                            contentDescription = null,
                            tint = AppTheme.AccentGold,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            recommendation,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextSecondary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ArgalaHighlightRow(
    label: String,
    value: String,
    icon: ImageVector,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary
            )
        }
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
    }
}

@Composable
private fun KarmaPatternCard(analysis: ArgalaAnalysis) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.AccountTree,
                    contentDescription = null,
                    tint = AppTheme.LifeAreaSpiritual,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Karma Patterns",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            analysis.overallAssessment.karmaPatterns.forEach { pattern ->
                Row(
                    modifier = Modifier.padding(vertical = 6.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(AppTheme.LifeAreaSpiritual)
                            .padding(top = 8.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        pattern,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun SignificantArgalasCard(
    analysis: ArgalaAnalysis,
    language: Language
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Significant Argalas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(12.dp))

            analysis.significantArgalas.take(5).forEach { argala ->
                SignificantArgalaItem(argala = argala, language = language)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
private fun SignificantArgalaItem(
    argala: ArgalaCalculator.SignificantArgala,
    language: Language
) {
    val natureColor = when (argala.nature) {
        ArgalaNature.SHUBHA -> AppTheme.SuccessColor
        ArgalaNature.ASHUBHA -> AppTheme.WarningColor
        ArgalaNature.MIXED -> AppTheme.InfoColor
    }

    Surface(
        color = natureColor.copy(alpha = 0.08f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    argala.targetDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                ArgalaStrengthBadge(strength = argala.strength, color = natureColor)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                argala.lifeAreaEffect,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary
            )

            if (argala.involvedPlanets.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(argala.involvedPlanets) { planet ->
                        PlanetChip(planet = planet, language = language)
                    }
                }
            }
        }
    }
}

@Composable
private fun ArgalaStrengthBadge(strength: ArgalaStrength, color: Color) {
    val (label, bgColor) = when (strength) {
        ArgalaStrength.VERY_STRONG -> "Very Strong" to color
        ArgalaStrength.STRONG -> "Strong" to color
        ArgalaStrength.MODERATE -> "Moderate" to color.copy(alpha = 0.7f)
        ArgalaStrength.WEAK -> "Weak" to AppTheme.TextMuted
        ArgalaStrength.OBSTRUCTED -> "Obstructed" to AppTheme.ErrorColor
    }

    Surface(
        color = bgColor.copy(alpha = 0.15f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = bgColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun PlanetChip(planet: Planet, language: Language) {
    Surface(
        color = AppTheme.getPlanetColor(planet).copy(alpha = 0.15f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                planet.symbol,
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.getPlanetColor(planet)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                planet.getLocalizedName(language),
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.getPlanetColor(planet)
            )
        }
    }
}

@Composable
private fun ArgalaHousesTab(
    analysis: ArgalaAnalysis,
    selectedHouse: Int,
    onSelectHouse: (Int) -> Unit,
    language: Language
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // House selector
        Text(
            "Select House to Analyze",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // House grid selector
        HouseGridSelector(
            selectedHouse = selectedHouse,
            onSelectHouse = onSelectHouse,
            houseArgalas = analysis.houseArgalas
        )

        Spacer(modifier = Modifier.height(16.dp))

        // House detail
        analysis.houseArgalas[selectedHouse]?.let { houseResult ->
            HouseArgalaDetailCard(
                house = selectedHouse,
                result = houseResult,
                language = language
            )
        }
    }
}

@Composable
private fun HouseGridSelector(
    selectedHouse: Int,
    onSelectHouse: (Int) -> Unit,
    houseArgalas: Map<Int, HouseArgalaResult>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (row in 0..2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (col in 0..3) {
                    val house = row * 4 + col + 1
                    val isSelected = house == selectedHouse
                    val result = houseArgalas[house]

                    val backgroundColor = when {
                        isSelected -> AppTheme.AccentPrimary.copy(alpha = 0.2f)
                        result?.effectiveArgala?.dominantNature == ArgalaNature.SHUBHA ->
                            AppTheme.SuccessColor.copy(alpha = 0.1f)
                        result?.effectiveArgala?.dominantNature == ArgalaNature.ASHUBHA ->
                            AppTheme.WarningColor.copy(alpha = 0.1f)
                        else -> AppTheme.ChipBackground
                    }

                    Surface(
                        onClick = { onSelectHouse(house) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        color = backgroundColor
                    ) {
                        Box(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                house.toString(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) AppTheme.AccentPrimary else AppTheme.TextPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HouseArgalaDetailCard(
    house: Int,
    result: HouseArgalaResult,
    language: Language
) {
    Column {
        // Header card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = when (result.effectiveArgala.dominantNature) {
                    ArgalaNature.SHUBHA -> AppTheme.SuccessColor.copy(alpha = 0.08f)
                    ArgalaNature.ASHUBHA -> AppTheme.WarningColor.copy(alpha = 0.08f)
                    ArgalaNature.MIXED -> AppTheme.InfoColor.copy(alpha = 0.08f)
                }
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            stringResource(StringKeyDosha.ARGALA_TO_HOUSE, house),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            getHouseName(house),
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppTheme.TextMuted
                        )
                    }

                    NetEffectIndicator(
                        netStrength = result.netArgalaStrength,
                        nature = result.effectiveArgala.dominantNature
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    result.interpretation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Primary Argalas
        if (result.primaryArgalas.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.CallReceived,
                            contentDescription = null,
                            tint = AppTheme.AccentGold,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Argala Influences",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    result.primaryArgalas.forEach { argala ->
                        ArgalaInfluenceItem(argala = argala, language = language)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Virodha Argalas (Obstructions)
        if (result.virodhaArgalas.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Block,
                            contentDescription = null,
                            tint = AppTheme.WarningColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            stringResource(StringKeyDosha.ARGALA_VIRODHA),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    result.virodhaArgalas.forEach { virodha ->
                        VirodhaArgalaItem(virodha = virodha, language = language)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Net Effect Summary
        NetEffectCard(effectiveArgala = result.effectiveArgala)
    }
}

@Composable
private fun NetEffectIndicator(
    netStrength: Double,
    nature: ArgalaNature
) {
    val (color, icon) = when {
        netStrength > 1.0 -> AppTheme.SuccessColor to Icons.Filled.TrendingUp
        netStrength > 0 -> AppTheme.InfoColor to Icons.Filled.TrendingUp
        netStrength > -1.0 -> AppTheme.WarningColor to Icons.Filled.TrendingDown
        else -> AppTheme.ErrorColor to Icons.Filled.TrendingDown
    }

    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                String.format("%.2f", netStrength),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
private fun ArgalaInfluenceItem(
    argala: ArgalaCalculator.ArgalaInfluence,
    language: Language
) {
    val natureColor = when (argala.nature) {
        ArgalaNature.SHUBHA -> AppTheme.SuccessColor
        ArgalaNature.ASHUBHA -> AppTheme.WarningColor
        ArgalaNature.MIXED -> AppTheme.InfoColor
    }

    Surface(
        color = natureColor.copy(alpha = 0.08f),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    argala.argalaType.displayName,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Surface(
                    color = natureColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        when (argala.nature) {
                            ArgalaNature.SHUBHA -> stringResource(StringKeyDosha.ARGALA_SHUBHA)
                            ArgalaNature.ASHUBHA -> stringResource(StringKeyDosha.ARGALA_ASHUBHA)
                            ArgalaNature.MIXED -> stringResource(StringKeyDosha.ARGALA_MIXED)
                        },
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Medium,
                        color = natureColor,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                stringResource(StringKeyDosha.ARGALA_FROM_HOUSE, argala.argalaHouse),
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted
            )

            if (argala.planets.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(argala.planets) { planet ->
                        PlanetChip(planet = planet, language = language)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Strength:",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted
                )
                Text(
                    String.format("%.2f", argala.strength),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = natureColor
                )
            }
        }
    }
}

@Composable
private fun VirodhaArgalaItem(
    virodha: ArgalaCalculator.VirodhaArgala,
    language: Language
) {
    Surface(
        color = if (virodha.isEffective)
            AppTheme.WarningColor.copy(alpha = 0.08f)
        else
            AppTheme.ChipBackground,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "House ${virodha.obstructingHouse}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                if (virodha.isEffective) {
                    Surface(
                        color = AppTheme.WarningColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            "Effective",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.WarningColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                virodha.description,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary
            )

            if (virodha.obstructingPlanets.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(virodha.obstructingPlanets) { planet ->
                        PlanetChip(planet = planet, language = language)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Obstruction:",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted
                )
                Text(
                    "${(virodha.obstructionStrength * 100).toInt()}%",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = if (virodha.isEffective) AppTheme.WarningColor else AppTheme.TextMuted
                )
            }
        }
    }
}

@Composable
private fun NetEffectCard(
    effectiveArgala: ArgalaCalculator.EffectiveArgala
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Balance,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyDosha.ARGALA_NET_EFFECT),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Benefic strength
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = null,
                        tint = AppTheme.SuccessColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Benefic Strength",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextSecondary
                    )
                }
                Text(
                    String.format("%.2f", effectiveArgala.netBeneficStrength),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.SuccessColor
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Malefic strength
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Remove,
                        contentDescription = null,
                        tint = AppTheme.WarningColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "Malefic Strength",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextSecondary
                    )
                }
                Text(
                    String.format("%.2f", effectiveArgala.netMaleficStrength),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.WarningColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = AppTheme.BorderColor.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                effectiveArgala.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun ArgalaPlanetsTab(
    analysis: ArgalaAnalysis,
    selectedPlanet: Planet?,
    onSelectPlanet: (Planet) -> Unit,
    language: Language,
    chart: VedicChart
) {
    Column(modifier = Modifier.padding(16.dp)) {
        // Planet selector
        Text(
            stringResource(StringKeyDosha.ARGALA_PLANET_CAUSES),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.TextPrimary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(analysis.planetArgalas.keys.toList()) { planet ->
                val isSelected = selectedPlanet == planet
                val result = analysis.planetArgalas[planet]

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
                    leadingIcon = if (result?.netStrength ?: 0.0 > 0) {
                        {
                            Icon(
                                Icons.Filled.TrendingUp,
                                contentDescription = null,
                                tint = AppTheme.SuccessColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    } else {
                        {
                            Icon(
                                Icons.Filled.TrendingDown,
                                contentDescription = null,
                                tint = AppTheme.WarningColor,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    },
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

        // Planet detail
        val planet = selectedPlanet ?: analysis.planetArgalas.keys.firstOrNull()
        planet?.let { p ->
            analysis.planetArgalas[p]?.let { result ->
                PlanetArgalaDetailCard(
                    planet = p,
                    result = result,
                    language = language,
                    chart = chart
                )
            }
        }
    }
}

@Composable
private fun PlanetArgalaDetailCard(
    planet: Planet,
    result: PlanetArgalaResult,
    language: Language,
    chart: VedicChart
) {
    val planetColor = AppTheme.getPlanetColor(planet)
    val planetHouse = chart.planetPositions.find { it.planet == planet }?.house ?: 1

    Column {
        // Header card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = planetColor.copy(alpha = 0.08f)
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
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
                            "In House $planetHouse",
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppTheme.TextMuted
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                            .background(planetColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            planet.symbol,
                            style = MaterialTheme.typography.headlineMedium,
                            color = planetColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    result.interpretation,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    color = if (result.netStrength > 0)
                        AppTheme.SuccessColor.copy(alpha = 0.15f)
                    else
                        AppTheme.WarningColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            if (result.netStrength > 0) Icons.Filled.TrendingUp else Icons.Filled.TrendingDown,
                            contentDescription = null,
                            tint = if (result.netStrength > 0) AppTheme.SuccessColor else AppTheme.WarningColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Net Strength: ${String.format("%.2f", result.netStrength)}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = if (result.netStrength > 0) AppTheme.SuccessColor else AppTheme.WarningColor
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Argalas received
        if (result.argalasReceived.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Argala Influences Received",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    result.argalasReceived.forEach { argala ->
                        ArgalaInfluenceItem(argala = argala, language = language)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Virodhas received
        if (result.virodhasReceived.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardElevated),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Obstructions Received",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    result.virodhasReceived.forEach { virodha ->
                        VirodhaArgalaItem(virodha = virodha, language = language)
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun ArgalaInfoDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(StringKeyDosha.ARGALA_ABOUT),
                fontWeight = FontWeight.Bold,
                color = AppTheme.TextPrimary
            )
        },
        text = {
            Column {
                Text(
                    stringResource(StringKeyDosha.ARGALA_ABOUT_DESC),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Key Concepts:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                listOf(
                    "• 2nd, 4th, 11th, 5th houses create Argala",
                    "• 12th, 10th, 3rd, 9th houses obstruct Argala",
                    "• Benefic planets = Shubha (auspicious) Argala",
                    "• Malefic planets = Ashubha (inauspicious) Argala",
                    "• More planets = stronger intervention",
                    "• Net effect = Primary Argala - Virodha"
                ).forEach { item ->
                    Text(
                        item,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextMuted,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Source: Jaimini Sutras (Chapter 1, Pada 1, Sutras 5-8)",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextMuted,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
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

// ============================================
// Helper Functions
// ============================================

private fun getHouseName(house: Int): String {
    return when (house) {
        1 -> "Self, Personality"
        2 -> "Wealth, Family"
        3 -> "Courage, Siblings"
        4 -> "Home, Mother"
        5 -> "Children, Intelligence"
        6 -> "Enemies, Service"
        7 -> "Marriage, Partnership"
        8 -> "Transformation, Longevity"
        9 -> "Fortune, Dharma"
        10 -> "Career, Status"
        11 -> "Gains, Aspirations"
        12 -> "Loss, Liberation"
        else -> "House $house"
    }
}
