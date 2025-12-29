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
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.localization.currentLanguage
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.DivisionalChartAnalyzer
import com.astro.storm.ephemeris.HoraAnalysis
import com.astro.storm.ephemeris.DrekkanaAnalysis
import com.astro.storm.ephemeris.NavamsaMarriageAnalysis
import com.astro.storm.ephemeris.DashamsaAnalysis
import com.astro.storm.ephemeris.DwadasamsaAnalysis
import com.astro.storm.ephemeris.WealthPotential
import com.astro.storm.ephemeris.WealthType
import com.astro.storm.ephemeris.CourageLevel
import com.astro.storm.ephemeris.RelationshipQuality
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Divisional Charts Analysis Screen (D-2, D-3, D-9, D-10, D-12)
 *
 * Comprehensive analysis of the key divisional charts:
 * - D-2 Hora: Wealth and Financial Prospects
 * - D-3 Drekkana: Siblings, Courage, Communication
 * - D-9 Navamsa: Marriage and Spouse
 * - D-10 Dashamsa: Career and Professional Life
 * - D-12 Dwadasamsa: Parents and Ancestry
 *
 * Based on: BPHS (Brihat Parashara Hora Shastra), Phaladeepika
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DivisionalChartsScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = stringResource(StringKeyDosha.DIVISIONAL_CHARTS_TITLE),
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = currentLanguage()
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var isCalculating by remember { mutableStateOf(true) }

    // Analysis results for each chart
    var horaAnalysis by remember { mutableStateOf<HoraAnalysis?>(null) }
    var drekkanaAnalysis by remember { mutableStateOf<DrekkanaAnalysis?>(null) }
    var navamsaAnalysis by remember { mutableStateOf<NavamsaMarriageAnalysis?>(null) }
    var dashamsaAnalysis by remember { mutableStateOf<DashamsaAnalysis?>(null) }
    var dwadasamsaAnalysis by remember { mutableStateOf<DwadasamsaAnalysis?>(null) }

    val tabs = listOf(
        stringResource(StringKeyDosha.DIVISIONAL_HORA_TAB),
        stringResource(StringKeyDosha.DIVISIONAL_DREKKANA_TAB),
        stringResource(StringKeyDosha.DIVISIONAL_NAVAMSA_TAB),
        stringResource(StringKeyDosha.DIVISIONAL_DASHAMSA_TAB),
        stringResource(StringKeyDosha.DIVISIONAL_DWADASAMSA_TAB)
    )

    // Calculate all analyses
    LaunchedEffect(chart) {
        isCalculating = true
        delay(300)
        withContext(Dispatchers.Default) {
            horaAnalysis = DivisionalChartAnalyzer.analyzeHora(chart)
            drekkanaAnalysis = DivisionalChartAnalyzer.analyzeDrekkana(chart)
            navamsaAnalysis = DivisionalChartAnalyzer.analyzeNavamsaForMarriage(chart)
            dashamsaAnalysis = DivisionalChartAnalyzer.analyzeDashamsa(chart)
            dwadasamsaAnalysis = DivisionalChartAnalyzer.analyzeDwadasamsa(chart)
        }
        isCalculating = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(StringKeyDosha.DIVISIONAL_CHARTS_TITLE),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            stringResource(StringKeyDosha.DIVISIONAL_CHARTS_SUBTITLE),
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
                            contentDescription = stringResource(StringKeyDosha.DIVISIONAL_CHARTS_ABOUT),
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
                        0 -> horaAnalysis?.let { HoraTab(it, language) }
                        1 -> drekkanaAnalysis?.let { DrekkanaTab(it, language) }
                        2 -> navamsaAnalysis?.let { NavamsaTab(it, language) }
                        3 -> dashamsaAnalysis?.let { DashamsaTab(it, language) }
                        4 -> dwadasamsaAnalysis?.let { DwadasamsaTab(it, language) }
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
                    stringResource(StringKeyDosha.DIVISIONAL_CHARTS_ABOUT),
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.TextPrimary
                )
            },
            text = {
                Text(
                    stringResource(StringKeyDosha.DIVISIONAL_CHARTS_ABOUT_DESC),
                    color = AppTheme.TextSecondary,
                    lineHeight = 22.sp
                )
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
// D-2 HORA TAB - WEALTH ANALYSIS
// ============================================
@Composable
private fun HoraTab(analysis: HoraAnalysis, language: Language) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Wealth Potential Overview
        item {
            WealthPotentialCard(analysis)
        }

        // Sun Hora Planets (Self-Earned)
        if (analysis.sunHoraPlanets.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(StringKeyDosha.HORA_SUN_TITLE),
                    icon = Icons.Filled.WbSunny,
                    tint = AppTheme.PlanetSun
                )
            }
            item {
                PlanetChipsCard(
                    planets = analysis.sunHoraPlanets,
                    description = stringResource(StringKeyDosha.HORA_SUN_DESC),
                    language = language
                )
            }
        }

        // Moon Hora Planets (Inherited/Liquid)
        if (analysis.moonHoraPlanets.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(StringKeyDosha.HORA_MOON_TITLE),
                    icon = Icons.Filled.NightsStay,
                    tint = AppTheme.PlanetMoon
                )
            }
            item {
                PlanetChipsCard(
                    planets = analysis.moonHoraPlanets,
                    description = stringResource(StringKeyDosha.HORA_MOON_DESC),
                    language = language
                )
            }
        }

        // Wealth Indicators Detail
        if (analysis.wealthIndicators.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(StringKeyDosha.HORA_WEALTH_SOURCES),
                    icon = Icons.Filled.AccountBalance,
                    tint = AppTheme.AccentGold
                )
            }
            items(analysis.wealthIndicators) { indicator ->
                WealthIndicatorCard(indicator, language)
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
private fun WealthPotentialCard(analysis: HoraAnalysis) {
    val (color, icon, labelKey) = when (analysis.overallWealthPotential) {
        WealthPotential.EXCEPTIONAL -> Triple(AppTheme.SuccessColor, Icons.Filled.Star, StringKeyDosha.HORA_POTENTIAL_EXCEPTIONAL)
        WealthPotential.HIGH -> Triple(AppTheme.SuccessColor.copy(alpha = 0.8f), Icons.Filled.TrendingUp, StringKeyDosha.HORA_POTENTIAL_HIGH)
        WealthPotential.MODERATE -> Triple(AppTheme.AccentGold, Icons.Filled.TrendingFlat, StringKeyDosha.HORA_POTENTIAL_MODERATE)
        WealthPotential.AVERAGE -> Triple(AppTheme.TextMuted, Icons.Filled.HorizontalRule, StringKeyDosha.HORA_POTENTIAL_AVERAGE)
        WealthPotential.LOW -> Triple(AppTheme.WarningColor, Icons.Filled.TrendingDown, StringKeyDosha.HORA_POTENTIAL_NEEDS_EFFORT)
    }
    val label = stringResource(labelKey)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    stringResource(StringKeyDosha.DIVISIONAL_WEALTH_ANALYSIS),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "${stringResource(StringKeyDosha.HORA_POTENTIAL)}: $label",
                    fontSize = 14.sp,
                    color = color,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun PlanetChipsCard(planets: List<Planet>, description: String, language: Language) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(planets) { planet ->
                    Surface(
                        color = AppTheme.getPlanetColor(planet).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            planet.getLocalizedName(language),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.getPlanetColor(planet)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(description, fontSize = 12.sp, color = AppTheme.TextMuted)
        }
    }
}

@Composable
private fun WealthIndicatorCard(indicator: com.astro.storm.ephemeris.WealthIndicator, language: Language) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(AppTheme.getPlanetColor(indicator.planet).copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        indicator.planet.displayName.take(2),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = AppTheme.getPlanetColor(indicator.planet)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        indicator.planet.getLocalizedName(language),
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        stringResource(if (indicator.type == WealthType.SELF_EARNED) StringKeyDosha.HORA_SELF_EARNED else StringKeyDosha.HORA_INHERITED),
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "${indicator.strength.toInt()}%",
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.AccentGold
                )
            }
            if (indicator.sources.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                indicator.sources.forEach { source ->
                    Text("• $source", fontSize = 12.sp, color = AppTheme.TextSecondary)
                }
            }
        }
    }
}

// ============================================
// D-3 DREKKANA TAB - SIBLINGS & COURAGE
// ============================================
@Composable
private fun DrekkanaTab(analysis: DrekkanaAnalysis, language: Language) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Courage Analysis
        item {
            CourageAnalysisCard(analysis.courageAnalysis)
        }

        // Sibling Indicators
        item {
            SiblingIndicatorsCard(analysis.siblingIndicators, language)
        }

        // Communication Skills
        item {
            CommunicationCard(analysis.communicationSkills)
        }

        // Short Journeys
        if (analysis.shortJourneyIndicators.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.DirectionsCar,
                                contentDescription = null,
                                tint = AppTheme.AccentTeal,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                stringResource(StringKeyDosha.DREKKANA_SHORT_JOURNEYS),
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        analysis.shortJourneyIndicators.forEach { indicator ->
                            Text("• $indicator", fontSize = 13.sp, color = AppTheme.TextSecondary)
                        }
                    }
                }
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
private fun CourageAnalysisCard(analysis: com.astro.storm.ephemeris.CourageAnalysis) {
    val (color, labelKey) = when (analysis.overallCourageLevel) {
        CourageLevel.EXCEPTIONAL -> AppTheme.SuccessColor to StringKeyDosha.COURAGE_EXCEPTIONAL
        CourageLevel.HIGH -> AppTheme.SuccessColor.copy(alpha = 0.8f) to StringKeyDosha.COURAGE_HIGH
        CourageLevel.MODERATE -> AppTheme.AccentGold to StringKeyDosha.COURAGE_MODERATE
        CourageLevel.LOW -> AppTheme.WarningColor to StringKeyDosha.COURAGE_DEVELOPING
        CourageLevel.VERY_LOW -> AppTheme.ErrorColor to StringKeyDosha.COURAGE_NEEDS_WORK
    }
    val label = stringResource(labelKey)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Shield,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        stringResource(StringKeyDosha.DREKKANA_COURAGE_TITLE),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = AppTheme.TextPrimary
                    )
                    Text("${stringResource(StringKeyDosha.COURAGE_LEVEL)}: $label", fontSize = 14.sp, color = color, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(StringKeyDosha.COURAGE_PHYSICAL), fontSize = 11.sp, color = AppTheme.TextMuted)
                    Text(analysis.physicalCourage, fontSize = 13.sp, color = AppTheme.TextPrimary)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(StringKeyDosha.COURAGE_MENTAL), fontSize = 11.sp, color = AppTheme.TextMuted)
                    Text(analysis.mentalCourage, fontSize = 13.sp, color = AppTheme.TextPrimary)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "${stringResource(StringKeyDosha.COURAGE_INITIATIVE)}: ${analysis.initiativeAbility}",
                fontSize = 12.sp,
                color = AppTheme.TextSecondary
            )
        }
    }
}

@Composable
private fun SiblingIndicatorsCard(indicators: com.astro.storm.ephemeris.SiblingIndicators, language: Language) {
    val relationshipColor = when (indicators.relationshipQuality) {
        RelationshipQuality.EXCELLENT -> AppTheme.SuccessColor
        RelationshipQuality.GOOD -> AppTheme.SuccessColor.copy(alpha = 0.7f)
        RelationshipQuality.NEUTRAL -> AppTheme.AccentGold
        RelationshipQuality.CHALLENGING -> AppTheme.WarningColor
        RelationshipQuality.DIFFICULT -> AppTheme.ErrorColor
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.People,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyDosha.DIVISIONAL_SIBLING_ANALYSIS),
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(stringResource(StringKeyDosha.DREKKANA_YOUNGER), fontSize = 11.sp, color = AppTheme.TextMuted)
                    Text(
                        "${indicators.estimatedYoungerSiblings.first}-${indicators.estimatedYoungerSiblings.last}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                }
                Column {
                    Text(stringResource(StringKeyDosha.DREKKANA_ELDER), fontSize = 11.sp, color = AppTheme.TextMuted)
                    Text(
                        "${indicators.estimatedElderSiblings.first}-${indicators.estimatedElderSiblings.last}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                }
                Column {
                    Text(stringResource(StringKeyDosha.DREKKANA_RELATIONSHIP), fontSize = 11.sp, color = AppTheme.TextMuted)
                    Text(
                        indicators.relationshipQuality.name,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = relationshipColor
                    )
                }
            }

            if (indicators.siblingWelfareIndicators.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = AppTheme.DividerColor)
                Spacer(modifier = Modifier.height(12.dp))
                indicators.siblingWelfareIndicators.forEach { welfare ->
                    Text("• $welfare", fontSize = 12.sp, color = AppTheme.TextSecondary)
                }
            }
        }
    }
}

@Composable
private fun CommunicationCard(analysis: com.astro.storm.ephemeris.CommunicationAnalysis) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.RecordVoiceOver,
                    contentDescription = null,
                    tint = AppTheme.InfoColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(StringKeyDosha.DREKKANA_COMMUNICATION_TITLE), fontWeight = FontWeight.SemiBold, color = AppTheme.TextPrimary)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("${stringResource(StringKeyDosha.DREKKANA_OVERALL)}: ${analysis.overallSkillLevel}", fontSize = 13.sp, color = AppTheme.TextSecondary)
            Text("${stringResource(StringKeyDosha.DREKKANA_WRITING)}: ${analysis.writingAbility}", fontSize = 13.sp, color = AppTheme.TextSecondary)
            Text("${stringResource(StringKeyDosha.DREKKANA_SPEAKING)}: ${analysis.speakingAbility}", fontSize = 13.sp, color = AppTheme.TextSecondary)

            if (analysis.artisticTalents.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("${stringResource(StringKeyDosha.DREKKANA_ARTISTIC_TALENTS)}:", fontSize = 12.sp, fontWeight = FontWeight.Medium, color = AppTheme.TextPrimary)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(analysis.artisticTalents) { talent ->
                        Surface(
                            color = AppTheme.ChipBackground,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                talent,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                color = AppTheme.TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================
// D-9 NAVAMSA TAB - MARRIAGE
// ============================================
@Composable
private fun NavamsaTab(analysis: NavamsaMarriageAnalysis, language: Language) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Spouse Characteristics
        item {
            SpouseCharacteristicsCard(analysis.spouseCharacteristics, analysis.spouseDirection)
        }

        // Marriage Timing Factors
        item {
            MarriageTimingCard(analysis.marriageTimingFactors, language)
        }

        // Key Planets Positions
        item {
            NavamsaKeyPlanetsCard(analysis, language)
        }

        // Multiple Marriage Indicators
        item {
            MultipleMarriageCard(analysis.multipleMarriageIndicators)
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
private fun SpouseCharacteristicsCard(
    characteristics: com.astro.storm.ephemeris.SpouseCharacteristics,
    direction: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.AccentPrimary.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = null,
                    tint = AppTheme.ErrorColor.copy(alpha = 0.8f),
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    stringResource(StringKeyDosha.NAVAMSA_SPOUSE_TITLE),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            InfoRow(stringResource(StringKeyDosha.NAVAMSA_NATURE), characteristics.generalNature)
            InfoRow(stringResource(StringKeyDosha.NAVAMSA_PHYSICAL_TRAITS), characteristics.physicalTraits)
            InfoRow(stringResource(StringKeyDosha.NAVAMSA_FAMILY_BACKGROUND), characteristics.familyBackground)
            InfoRow(stringResource(StringKeyDosha.NAVAMSA_DIRECTION), direction)

            if (characteristics.probableProfessions.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("${stringResource(StringKeyDosha.NAVAMSA_PROBABLE_PROFESSIONS)}:", fontSize = 12.sp, color = AppTheme.TextMuted)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(characteristics.probableProfessions) { profession ->
                        Surface(color = AppTheme.ChipBackground, shape = RoundedCornerShape(12.dp)) {
                            Text(
                                profession,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                fontSize = 11.sp,
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
private fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, color = AppTheme.TextMuted)
        Text(value, fontSize = 13.sp, color = AppTheme.TextPrimary, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun MarriageTimingCard(factors: com.astro.storm.ephemeris.MarriageTimingFactors, language: Language) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(StringKeyDosha.NAVAMSA_TIMING_TITLE), fontWeight = FontWeight.SemiBold, color = AppTheme.TextPrimary)

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatColumn(stringResource(StringKeyDosha.NAVAMSA_VENUS), "${factors.venusNavamsaStrength.toInt()}%", AppTheme.PlanetVenus)
                StatColumn(stringResource(StringKeyDosha.NAVAMSA_7TH_LORD), "${factors.seventhLordStrength.toInt()}%", AppTheme.AccentPrimary)
                StatColumn(stringResource(StringKeyDosha.NAVAMSA_DARAKARAKA), "${factors.darakarakaStrength.toInt()}%", AppTheme.AccentGold)
            }

            if (factors.favorableDashaPlanets.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("${stringResource(StringKeyDosha.NAVAMSA_FAVORABLE_DASHA)}:", fontSize = 12.sp, color = AppTheme.TextMuted)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(factors.favorableDashaPlanets) { planet ->
                        Surface(
                            color = AppTheme.SuccessColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                planet.getLocalizedName(language),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                color = AppTheme.SuccessColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatColumn(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = color)
        Text(label, fontSize = 11.sp, color = AppTheme.TextMuted)
    }
}

@Composable
private fun NavamsaKeyPlanetsCard(analysis: NavamsaMarriageAnalysis, language: Language) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(StringKeyDosha.NAVAMSA_KEY_PLANETS_TITLE), fontWeight = FontWeight.SemiBold, color = AppTheme.TextPrimary)
            Spacer(modifier = Modifier.height(12.dp))

            analysis.venusInNavamsa?.let {
                PlanetPositionRow(stringResource(StringKeyDosha.NAVAMSA_VENUS), it.sign.getLocalizedName(language), AppTheme.PlanetVenus)
            }
            analysis.jupiterInNavamsa?.let {
                PlanetPositionRow(stringResource(StringKeyDosha.NAVAMSA_JUPITER), it.sign.getLocalizedName(language), AppTheme.PlanetJupiter)
            }
            analysis.seventhLordNavamsa?.let {
                PlanetPositionRow(stringResource(StringKeyDosha.NAVAMSA_7TH_LORD), it.sign.getLocalizedName(language), AppTheme.AccentPrimary)
            }
            analysis.darakarakaNavamsa?.let {
                PlanetPositionRow("${stringResource(StringKeyDosha.NAVAMSA_DARAKARAKA)} (${analysis.darakaraka.getLocalizedName(language)})", it.sign.getLocalizedName(language), AppTheme.AccentGold)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("${stringResource(StringKeyDosha.NAVAMSA_UPAPADA)}: ${analysis.upapadaSign.getLocalizedName(language)}", fontSize = 13.sp, color = AppTheme.TextSecondary)
        }
    }
}

@Composable
private fun PlanetPositionRow(label: String, sign: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontSize = 13.sp, color = AppTheme.TextSecondary)
        Surface(color = color.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp)) {
            Text(
                sign,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = color
            )
        }
    }
}

@Composable
private fun MultipleMarriageCard(indicators: com.astro.storm.ephemeris.MultipleMarriageIndicators) {
    if (!indicators.hasStrongIndicators && indicators.riskFactors.isEmpty()) return

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (indicators.hasStrongIndicators) AppTheme.WarningColor.copy(alpha = 0.1f)
            else AppTheme.CardBackground
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    if (indicators.hasStrongIndicators) Icons.Outlined.Warning else Icons.Outlined.Info,
                    contentDescription = null,
                    tint = if (indicators.hasStrongIndicators) AppTheme.WarningColor else AppTheme.InfoColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(StringKeyDosha.NAVAMSA_RELATIONSHIP_STABILITY), fontWeight = FontWeight.SemiBold, color = AppTheme.TextPrimary)
            }

            if (indicators.riskFactors.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("${stringResource(StringKeyDosha.NAVAMSA_AREAS_ATTENTION)}:", fontSize = 12.sp, color = AppTheme.WarningColor)
                indicators.riskFactors.forEach { factor ->
                    Text("• $factor", fontSize = 12.sp, color = AppTheme.TextSecondary)
                }
            }

            if (indicators.mitigatingFactors.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("${stringResource(StringKeyDosha.NAVAMSA_PROTECTIVE_FACTORS)}:", fontSize = 12.sp, color = AppTheme.SuccessColor)
                indicators.mitigatingFactors.forEach { factor ->
                    Text("• $factor", fontSize = 12.sp, color = AppTheme.TextSecondary)
                }
            }
        }
    }
}

// ============================================
// D-10 DASHAMSA TAB - CAREER
// ============================================
@Composable
private fun DashamsaTab(analysis: DashamsaAnalysis, language: Language) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Career Types
        if (analysis.careerTypes.isNotEmpty()) {
            item {
                CareerTypesCard(analysis.careerTypes)
            }
        }

        // Business vs Service
        item {
            BusinessVsServiceCard(analysis.businessVsServiceAptitude)
        }

        // Government Service Potential
        item {
            GovernmentServiceCard(analysis.governmentServicePotential)
        }

        // Professional Strengths
        if (analysis.professionalStrengths.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(StringKeyDosha.DASHAMSA_PROFESSIONAL_STRENGTHS), fontWeight = FontWeight.SemiBold, color = AppTheme.TextPrimary)
                        Spacer(modifier = Modifier.height(8.dp))
                        analysis.professionalStrengths.forEach { strength ->
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Icon(
                                    Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = AppTheme.SuccessColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(strength, fontSize = 13.sp, color = AppTheme.TextSecondary)
                            }
                        }
                    }
                }
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
private fun CareerTypesCard(careerTypes: List<com.astro.storm.ephemeris.CareerType>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.AccentPrimary.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Work,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    stringResource(StringKeyDosha.DIVISIONAL_CAREER_ANALYSIS),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            careerTypes.forEach { career ->
                Column(modifier = Modifier.padding(vertical = 6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(career.name, fontWeight = FontWeight.Medium, color = AppTheme.TextPrimary)
                        Text(career.suitability, fontSize = 12.sp, color = AppTheme.SuccessColor)
                    }
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(career.industries.take(3)) { industry ->
                            Text(industry, fontSize = 11.sp, color = AppTheme.TextMuted)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BusinessVsServiceCard(analysis: com.astro.storm.ephemeris.BusinessVsServiceAnalysis) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(StringKeyDosha.DASHAMSA_BUSINESS_VS_SERVICE), fontWeight = FontWeight.SemiBold, color = AppTheme.TextPrimary)

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(StringKeyDosha.DASHAMSA_BUSINESS), fontSize = 12.sp, color = AppTheme.TextMuted)
                    LinearProgressIndicator(
                        progress = { analysis.businessAptitude / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = AppTheme.AccentGold,
                        trackColor = AppTheme.ChipBackground
                    )
                    Text("${analysis.businessAptitude}%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AppTheme.AccentGold)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(stringResource(StringKeyDosha.DASHAMSA_SERVICE), fontSize = 12.sp, color = AppTheme.TextMuted)
                    LinearProgressIndicator(
                        progress = { analysis.serviceAptitude / 100f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = AppTheme.AccentTeal,
                        trackColor = AppTheme.ChipBackground
                    )
                    Text("${analysis.serviceAptitude}%", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AppTheme.AccentTeal)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(analysis.recommendation, fontSize = 13.sp, color = AppTheme.TextSecondary)
        }
    }
}

@Composable
private fun GovernmentServiceCard(analysis: com.astro.storm.ephemeris.GovernmentServiceAnalysis) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.AccountBalance,
                    contentDescription = null,
                    tint = AppTheme.PlanetSun,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(StringKeyDosha.DASHAMSA_GOVT_SERVICE_TITLE), fontWeight = FontWeight.SemiBold, color = AppTheme.TextPrimary)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("${stringResource(StringKeyDosha.DASHAMSA_POTENTIAL)}: ${analysis.potential}", fontSize = 14.sp, color = AppTheme.TextSecondary)

            if (analysis.favorableFactors.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                analysis.favorableFactors.forEach { factor ->
                    Text("• $factor", fontSize = 12.sp, color = AppTheme.SuccessColor)
                }
            }

            if (analysis.recommendedDepartments.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("${stringResource(StringKeyDosha.DASHAMSA_RECOMMENDED_AREAS)}:", fontSize = 12.sp, color = AppTheme.TextMuted)
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(analysis.recommendedDepartments) { dept ->
                        Surface(color = AppTheme.ChipBackground, shape = RoundedCornerShape(12.dp)) {
                            Text(
                                dept,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                color = AppTheme.TextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================
// D-12 DWADASAMSA TAB - PARENTS
// ============================================
@Composable
private fun DwadasamsaTab(analysis: DwadasamsaAnalysis, language: Language) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Father Analysis
        item {
            ParentAnalysisCard(analysis.fatherAnalysis, stringResource(StringKeyDosha.DWADASAMSA_FATHER), Icons.Filled.Man, AppTheme.PlanetSun)
        }

        // Mother Analysis
        item {
            ParentAnalysisCard(analysis.motherAnalysis, stringResource(StringKeyDosha.DWADASAMSA_MOTHER), Icons.Filled.Woman, AppTheme.PlanetMoon)
        }

        // Inheritance Analysis
        item {
            InheritanceCard(analysis.inheritanceIndicators)
        }

        // Ancestral Property
        if (analysis.ancestralPropertyIndicators.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Filled.Home,
                                contentDescription = null,
                                tint = AppTheme.AccentGold,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(StringKeyDosha.DWADASAMSA_ANCESTRAL_PROPERTY), fontWeight = FontWeight.SemiBold, color = AppTheme.TextPrimary)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        analysis.ancestralPropertyIndicators.forEach { indicator ->
                            Text("• $indicator", fontSize = 13.sp, color = AppTheme.TextSecondary)
                        }
                    }
                }
            }
        }

        // Longevity Indicators
        item {
            LongevityCard(analysis.parentalLongevityIndicators)
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
private fun ParentAnalysisCard(
    analysis: com.astro.storm.ephemeris.ParentAnalysis,
    title: String,
    icon: ImageVector,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.08f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(28.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = AppTheme.TextPrimary)
                    Text(analysis.overallWellbeing, fontSize = 13.sp, color = color, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatColumn(stringResource(StringKeyDosha.DWADASAMSA_SIGNIFICATOR), "${analysis.significatorStrength.toInt()}%", color)
                StatColumn(stringResource(StringKeyDosha.DWADASAMSA_HOUSE_LORD), "${analysis.houseLordStrength.toInt()}%", AppTheme.AccentPrimary)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("${stringResource(StringKeyDosha.DWADASAMSA_CHARACTERISTICS)}: ${analysis.characteristics}", fontSize = 13.sp, color = AppTheme.TextSecondary)
            Text("${stringResource(StringKeyDosha.DWADASAMSA_RELATIONSHIP)}: ${analysis.relationship}", fontSize = 13.sp, color = AppTheme.TextSecondary)
        }
    }
}

@Composable
private fun InheritanceCard(analysis: com.astro.storm.ephemeris.InheritanceAnalysis) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.CardGiftcard,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(StringKeyDosha.DWADASAMSA_INHERITANCE_TITLE), fontWeight = FontWeight.SemiBold, color = AppTheme.TextPrimary)
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("${stringResource(StringKeyDosha.DWADASAMSA_POTENTIAL)}: ${analysis.potential}", fontSize = 14.sp, color = AppTheme.TextSecondary)
            Text("${stringResource(StringKeyDosha.DWADASAMSA_TIMING)}: ${analysis.timing}", fontSize = 13.sp, color = AppTheme.TextMuted)

            if (analysis.sources.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text("${stringResource(StringKeyDosha.DWADASAMSA_SOURCES)}:", fontSize = 12.sp, color = AppTheme.TextMuted)
                analysis.sources.forEach { source ->
                    Text("• $source", fontSize = 12.sp, color = AppTheme.TextSecondary)
                }
            }
        }
    }
}

@Composable
private fun LongevityCard(indicators: com.astro.storm.ephemeris.ParentalLongevityIndicators) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(stringResource(StringKeyDosha.DWADASAMSA_LONGEVITY_TITLE), fontWeight = FontWeight.SemiBold, color = AppTheme.TextPrimary)

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(stringResource(StringKeyDosha.DWADASAMSA_FATHER), fontSize = 12.sp, color = AppTheme.TextMuted)
                    Text(indicators.fatherLongevity, fontSize = 13.sp, color = AppTheme.TextPrimary)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(stringResource(StringKeyDosha.DWADASAMSA_MOTHER), fontSize = 12.sp, color = AppTheme.TextMuted)
                    Text(indicators.motherLongevity, fontSize = 13.sp, color = AppTheme.TextPrimary)
                }
            }

            if (indicators.healthConcerns.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = AppTheme.DividerColor)
                Spacer(modifier = Modifier.height(8.dp))
                indicators.healthConcerns.forEach { concern ->
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = null,
                            tint = AppTheme.InfoColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(concern, fontSize = 12.sp, color = AppTheme.TextSecondary)
                    }
                }
            }
        }
    }
}

// ============================================
// COMMON COMPONENTS
// ============================================
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
