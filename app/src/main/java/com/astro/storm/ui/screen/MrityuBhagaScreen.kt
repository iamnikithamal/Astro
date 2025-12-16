package com.astro.storm.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.currentLanguage
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.MrityuBhagaCalculator
import com.astro.storm.ephemeris.SensitiveDegreesAnalysis
import com.astro.storm.ephemeris.MrityuBhagaResult
import com.astro.storm.ephemeris.MrityuBhagaSeverity
import com.astro.storm.ephemeris.GandantaResult
import com.astro.storm.ephemeris.GandantaSeverity
import com.astro.storm.ephemeris.GandantaType
import com.astro.storm.ephemeris.PushkaraNavamsaResult
import com.astro.storm.ephemeris.PushkaraBhagaResult
import com.astro.storm.ephemeris.AssessmentLevel
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Mrityu Bhaga Analysis Screen
 *
 * Comprehensive sensitive degrees analysis showing:
 * - Mrityu Bhaga (death portion) degrees where planets are vulnerable
 * - Gandanta (karmic knot) junctions at water-fire sign boundaries
 * - Pushkara Navamsa (auspicious degrees) for protection
 * - Pushkara Bhaga (nourishing degrees) benefits
 *
 * Based on classical texts: Phaladeepika, BPHS, Saravali
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MrityuBhagaScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = stringResource(StringKeyDosha.MRITYU_BHAGA_SCREEN_TITLE),
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = currentLanguage()
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var isCalculating by remember { mutableStateOf(true) }
    var analysisResult by remember { mutableStateOf<SensitiveDegreesAnalysis?>(null) }

    val tabs = listOf(
        stringResource(StringKeyDosha.SCREEN_OVERVIEW),
        stringResource(StringKeyDosha.MRITYU_BHAGA_SIGN_DEGREES),
        stringResource(StringKeyDosha.MRITYU_BHAGA_REMEDIES_SECTION)
    )

    // Calculate analysis
    LaunchedEffect(chart) {
        isCalculating = true
        delay(300)
        withContext(Dispatchers.Default) {
            analysisResult = MrityuBhagaCalculator.analyzeSensitiveDegrees(chart)
        }
        isCalculating = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(StringKeyDosha.MRITYU_BHAGA_SCREEN_TITLE),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            stringResource(StringKeyDosha.MRITYU_BHAGA_SCREEN_SUBTITLE),
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
                        1 -> SignDegreesTab(analysisResult!!, language)
                        2 -> RemediesTab(analysisResult!!, language)
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
                    stringResource(StringKeyDosha.MRITYU_BHAGA_SCREEN_ABOUT),
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.TextPrimary
                )
            },
            text = {
                Text(
                    stringResource(StringKeyDosha.MRITYU_BHAGA_SCREEN_ABOUT_DESC),
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
private fun OverviewTab(analysis: SensitiveDegreesAnalysis, language: String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Overall Assessment Card
        item {
            OverallAssessmentCard(analysis)
        }

        // Critical Placements (Mrityu Bhaga)
        val criticalMrityu = analysis.mrityuBhagaAnalysis.filter { it.isInMrityuBhaga }
        if (criticalMrityu.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(StringKeyDosha.MRITYU_BHAGA_PLANETS_AFFECTED),
                    icon = Icons.Filled.Warning,
                    tint = AppTheme.WarningColor
                )
            }
            items(criticalMrityu) { result ->
                MrityuBhagaPlanetCard(result, language)
            }
        }

        // Gandanta Placements
        val gandantaPlanets = analysis.gandantaAnalysis.filter { it.isInGandanta }
        if (gandantaPlanets.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Gandanta Placements",
                    icon = Icons.Filled.Waves,
                    tint = AppTheme.ErrorColor
                )
            }
            items(gandantaPlanets) { result ->
                GandantaPlanetCard(result, language)
            }
        }

        // Auspicious Placements (Pushkara)
        val pushkaraNavamsa = analysis.pushkaraNavamsaAnalysis.filter { it.isInPushkaraNavamsa }
        val pushkaraBhaga = analysis.pushkaraBhagaAnalysis.filter { it.isInPushkaraBhaga }

        if (pushkaraNavamsa.isNotEmpty() || pushkaraBhaga.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Auspicious Placements",
                    icon = Icons.Filled.Star,
                    tint = AppTheme.SuccessColor
                )
            }
            items(pushkaraNavamsa) { result ->
                PushkaraNavamsaCard(result, language)
            }
            items(pushkaraBhaga) { result ->
                PushkaraBhagaCard(result, language)
            }
        }

        // No Critical Placements Message
        if (criticalMrityu.isEmpty() && gandantaPlanets.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = AppTheme.SuccessColor.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = AppTheme.SuccessColor,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                stringResource(StringKeyDosha.MRITYU_BHAGA_NO_PLANETS),
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                            Text(
                                "No planets are in critical sensitive degrees",
                                fontSize = 13.sp,
                                color = AppTheme.TextMuted
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun OverallAssessmentCard(analysis: SensitiveDegreesAnalysis) {
    val assessment = analysis.overallAssessment

    val (backgroundColor, iconColor, icon) = when (assessment.level) {
        AssessmentLevel.NEEDS_ATTENTION -> Triple(
            AppTheme.ErrorColor.copy(alpha = 0.1f),
            AppTheme.ErrorColor,
            Icons.Filled.Warning
        )
        AssessmentLevel.MODERATE_CONCERN -> Triple(
            AppTheme.WarningColor.copy(alpha = 0.1f),
            AppTheme.WarningColor,
            Icons.Outlined.Info
        )
        AssessmentLevel.BALANCED -> Triple(
            AppTheme.AccentGold.copy(alpha = 0.1f),
            AppTheme.AccentGold,
            Icons.Filled.Balance
        )
        AssessmentLevel.GENERALLY_POSITIVE -> Triple(
            AppTheme.SuccessColor.copy(alpha = 0.1f),
            AppTheme.SuccessColor,
            Icons.Filled.CheckCircle
        )
        AssessmentLevel.HIGHLY_AUSPICIOUS -> Triple(
            AppTheme.SuccessColor.copy(alpha = 0.15f),
            AppTheme.SuccessColor,
            Icons.Filled.Star
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "Overall Assessment",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        assessment.level.name.replace("_", " "),
                        fontSize = 13.sp,
                        color = iconColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                assessment.summary,
                fontSize = 14.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(
                    label = "Critical",
                    value = assessment.criticalPlacementCount.toString(),
                    color = if (assessment.criticalPlacementCount > 0) AppTheme.ErrorColor else AppTheme.TextMuted
                )
                StatItem(
                    label = "Auspicious",
                    value = assessment.auspiciousPlacementCount.toString(),
                    color = if (assessment.auspiciousPlacementCount > 0) AppTheme.SuccessColor else AppTheme.TextMuted
                )
            }
        }
    }
}

@Composable
private fun StatItem(label: String, value: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            value,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = color
        )
        Text(
            label,
            fontSize = 12.sp,
            color = AppTheme.TextMuted
        )
    }
}

@Composable
private fun SectionHeader(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, tint: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            color = AppTheme.TextPrimary
        )
    }
}

@Composable
private fun MrityuBhagaPlanetCard(result: MrityuBhagaResult, language: String) {
    var expanded by remember { mutableStateOf(false) }

    val severityColor = when (result.severity) {
        MrityuBhagaSeverity.EXACT -> AppTheme.ErrorColor
        MrityuBhagaSeverity.VERY_CLOSE -> AppTheme.ErrorColor.copy(alpha = 0.8f)
        MrityuBhagaSeverity.WITHIN_ORB -> AppTheme.WarningColor
        MrityuBhagaSeverity.APPROACHING -> AppTheme.WarningColor.copy(alpha = 0.7f)
        MrityuBhagaSeverity.SAFE -> AppTheme.SuccessColor
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
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
                            .background(AppTheme.getPlanetColor(result.planet).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            result.planet.displayName.take(2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = AppTheme.getPlanetColor(result.planet)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            result.planet.getLocalizedName(language),
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            "${result.sign.getLocalizedName(language)} ${String.format("%.1f", result.actualDegree)}°",
                            fontSize = 12.sp,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                // Severity Badge
                Surface(
                    color = severityColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        result.severity.name.replace("_", " "),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = severityColor
                    )
                }
            }

            // Mrityu Bhaga degree info
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(StringKeyDosha.MRITYU_BHAGA_SENSITIVE_DEGREE),
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted
                )
                Text(
                    "${String.format("%.1f", result.mrityuBhagaDegree)}°",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(StringKeyDosha.MRITYU_BHAGA_ORB),
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted
                )
                Text(
                    "${String.format("%.2f", result.distanceFromMrityuBhaga)}°",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = severityColor
                )
            }

            // Expandable content
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))

                    if (result.effects.isNotEmpty()) {
                        Text(
                            stringResource(StringKeyDosha.EFFECTS_LABEL),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        result.effects.forEach { effect ->
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

                    if (result.vulnerabilityAreas.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            stringResource(StringKeyDosha.MRITYU_BHAGA_LIFE_AREAS),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(result.vulnerabilityAreas) { area ->
                                Surface(
                                    color = AppTheme.ChipBackground,
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text(
                                        area,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
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
    }
}

@Composable
private fun GandantaPlanetCard(result: GandantaResult, language: String) {
    var expanded by remember { mutableStateOf(false) }

    val severityColor = when (result.severity) {
        GandantaSeverity.EXACT_JUNCTION -> AppTheme.ErrorColor
        GandantaSeverity.CRITICAL -> AppTheme.ErrorColor.copy(alpha = 0.8f)
        GandantaSeverity.SEVERE -> AppTheme.WarningColor
        GandantaSeverity.MODERATE -> AppTheme.WarningColor.copy(alpha = 0.7f)
        GandantaSeverity.MILD -> AppTheme.AccentGold
    }

    val gandantaTypeName = when (result.gandantaType) {
        GandantaType.BRAHMA_GANDANTA -> "Brahma Gandanta"
        GandantaType.VISHNU_GANDANTA -> "Vishnu Gandanta"
        GandantaType.SHIVA_GANDANTA -> "Shiva Gandanta"
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
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
                            .background(AppTheme.getPlanetColor(result.planet).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.Waves,
                            contentDescription = null,
                            tint = AppTheme.getPlanetColor(result.planet),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            result.planet.getLocalizedName(language),
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            gandantaTypeName,
                            fontSize = 12.sp,
                            color = severityColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Surface(
                    color = severityColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        result.severity.name.replace("_", " "),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = severityColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "${result.waterSign.getLocalizedName(language)} ↔ ${result.fireSign.getLocalizedName(language)} junction",
                fontSize = 12.sp,
                color = AppTheme.TextMuted
            )
            Text(
                "Distance from junction: ${String.format("%.2f", result.distanceFromJunction)}°",
                fontSize = 12.sp,
                color = severityColor
            )

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))

                    if (result.effects.isNotEmpty()) {
                        Text(
                            stringResource(StringKeyDosha.EFFECTS_LABEL),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        result.effects.forEach { effect ->
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
                }
            }
        }
    }
}

@Composable
private fun PushkaraNavamsaCard(result: PushkaraNavamsaResult, language: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.SuccessColor.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = AppTheme.SuccessColor,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "${result.planet.getLocalizedName(language)} in Pushkara Navamsa",
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Text(
                    "${result.sign.getLocalizedName(language)} ${String.format("%.1f", result.degree)}°",
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted
                )
                if (result.benefits.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        result.benefits.first(),
                        fontSize = 11.sp,
                        color = AppTheme.SuccessColor
                    )
                }
            }
        }
    }
}

@Composable
private fun PushkaraBhagaCard(result: PushkaraBhagaResult, language: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.AccentGold.copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Spa,
                contentDescription = null,
                tint = AppTheme.AccentGold,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "${result.planet.getLocalizedName(language)} in Pushkara Bhaga",
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Text(
                    "Nourishing degree: ${String.format("%.1f", result.pushkaraBhagaDegree)}° (Orb: ${String.format("%.2f", result.distance)}°)",
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted
                )
            }
        }
    }
}

@Composable
private fun SignDegreesTab(analysis: SensitiveDegreesAnalysis, language: String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                stringResource(StringKeyDosha.MRITYU_BHAGA_ALL_SIGNS),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Group by planet
        val planetGroups = analysis.mrityuBhagaAnalysis.groupBy { it.planet }

        items(planetGroups.entries.toList()) { (planet, results) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(AppTheme.getPlanetColor(planet).copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                planet.displayName.take(2),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp,
                                color = AppTheme.getPlanetColor(planet)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            planet.getLocalizedName(language),
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    results.forEach { result ->
                        val isInMrityu = result.isInMrityuBhaga
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                result.sign.getLocalizedName(language),
                                fontSize = 13.sp,
                                color = if (isInMrityu) AppTheme.WarningColor else AppTheme.TextSecondary
                            )
                            Row {
                                Text(
                                    "MB: ${String.format("%.0f", result.mrityuBhagaDegree)}°",
                                    fontSize = 12.sp,
                                    color = AppTheme.TextMuted
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    "Actual: ${String.format("%.1f", result.actualDegree)}°",
                                    fontSize = 12.sp,
                                    color = if (isInMrityu) AppTheme.WarningColor else AppTheme.TextSecondary,
                                    fontWeight = if (isInMrityu) FontWeight.Medium else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RemediesTab(analysis: SensitiveDegreesAnalysis, language: String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // General Recommendations
        item {
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

                    analysis.recommendations.forEach { recommendation ->
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                Icons.Filled.Check,
                                contentDescription = null,
                                tint = AppTheme.SuccessColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                recommendation,
                                fontSize = 14.sp,
                                color = AppTheme.TextSecondary,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
        }

        // Planet-specific remedies
        val criticalPlanets = analysis.mrityuBhagaAnalysis.filter { it.isInMrityuBhaga }

        if (criticalPlanets.isNotEmpty()) {
            item {
                Text(
                    "Planet-Specific Remedies",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(criticalPlanets) { result ->
                if (result.remedies.isNotEmpty()) {
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
                                        .background(AppTheme.getPlanetColor(result.planet).copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        result.planet.displayName.take(2),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = AppTheme.getPlanetColor(result.planet)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    "${result.planet.getLocalizedName(language)} Remedies",
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppTheme.TextPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            result.remedies.forEach { remedy ->
                                Row(
                                    modifier = Modifier.padding(vertical = 3.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text("•", color = AppTheme.AccentPrimary, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        remedy,
                                        fontSize = 13.sp,
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

        // Gandanta remedies
        val gandantaPlanets = analysis.gandantaAnalysis.filter { it.isInGandanta }

        if (gandantaPlanets.isNotEmpty()) {
            item {
                Text(
                    "Gandanta Remedies",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            items(gandantaPlanets) { result ->
                if (result.remedies.isNotEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Filled.Waves,
                                    contentDescription = null,
                                    tint = AppTheme.AccentTeal,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    "${result.planet.getLocalizedName(language)} - ${result.gandantaType.name.replace("_", " ")}",
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppTheme.TextPrimary
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            result.remedies.forEach { remedy ->
                                Row(
                                    modifier = Modifier.padding(vertical = 3.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text("•", color = AppTheme.AccentTeal, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        remedy,
                                        fontSize = 13.sp,
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

        // Precautionary Measures
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.InfoColor.copy(alpha = 0.08f)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Shield,
                            contentDescription = null,
                            tint = AppTheme.InfoColor,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            stringResource(StringKeyDosha.MRITYU_BHAGA_PRECAUTIONS),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = AppTheme.TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    val precautions = listOf(
                        "Be extra cautious during Dasha/Antardasha periods of planets in Mrityu Bhaga",
                        "Avoid major decisions during critical transit periods",
                        "Regular spiritual practices provide natural protection",
                        "Maintain awareness without excessive worry - these are tendencies, not certainties"
                    )

                    precautions.forEach { precaution ->
                        Row(
                            modifier = Modifier.padding(vertical = 4.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                Icons.Outlined.Info,
                                contentDescription = null,
                                tint = AppTheme.InfoColor,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                precaution,
                                fontSize = 13.sp,
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
