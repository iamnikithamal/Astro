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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.astro.storm.ephemeris.LalKitabRemediesCalculator
import com.astro.storm.ephemeris.LalKitabAnalysis
import com.astro.storm.ephemeris.PlanetaryAffliction
import com.astro.storm.ephemeris.AfflictionType
import com.astro.storm.ephemeris.AfflictionSeverity
import com.astro.storm.ephemeris.KarmicDebt
import com.astro.storm.ephemeris.DebtType
import com.astro.storm.ephemeris.LalKitabRemedy
import com.astro.storm.ephemeris.RemedyCategory
import com.astro.storm.ephemeris.ColorRemedy
import com.astro.storm.ephemeris.DirectionRemedy
import com.astro.storm.ephemeris.AnnualRemedyEntry
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Lal Kitab Remedies Screen
 *
 * Comprehensive Lal Kitab remedial system display showing:
 * - Planetary afflictions per Lal Kitab principles
 * - Karmic debts (Rin) analysis
 * - Practical, everyday remedies
 * - Color therapy recommendations
 * - Direction guidance
 * - Weekly remedy schedule
 *
 * Based on: Original Lal Kitab texts, Pandit Roop Chand Joshi's interpretations
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LalKitabRemediesScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = stringResource(StringKeyDosha.LAL_KITAB_SCREEN_TITLE),
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = currentLanguage()
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var isCalculating by remember { mutableStateOf(true) }
    var analysisResult by remember { mutableStateOf<LalKitabAnalysis?>(null) }

    val tabs = listOf(
        stringResource(StringKeyDosha.SCREEN_OVERVIEW),
        stringResource(StringKeyDosha.LAL_KITAB_SCREEN_KARMIC_DEBTS),
        stringResource(StringKeyDosha.LAL_KITAB_WEEKLY_SCHEDULE),
        stringResource(StringKeyDosha.LAL_KITAB_COLOR_THERAPY)
    )

    // Calculate analysis
    LaunchedEffect(chart) {
        isCalculating = true
        delay(300)
        withContext(Dispatchers.Default) {
            analysisResult = LalKitabRemediesCalculator.analyzeLalKitab(chart)
        }
        isCalculating = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(StringKeyDosha.LAL_KITAB_SCREEN_TITLE),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            stringResource(StringKeyDosha.LAL_KITAB_SCREEN_SUBTITLE),
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
                        1 -> KarmicDebtsTab(analysisResult!!, language)
                        2 -> WeeklyScheduleTab(analysisResult!!, language)
                        3 -> ColorDirectionTab(analysisResult!!, language)
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
                    stringResource(StringKeyDosha.LAL_KITAB_SCREEN_ABOUT),
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.TextPrimary
                )
            },
            text = {
                Column {
                    Text(
                        stringResource(StringKeyDosha.LAL_KITAB_SCREEN_ABOUT_DESC),
                        color = AppTheme.TextSecondary,
                        lineHeight = 22.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Surface(
                        color = AppTheme.InfoColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                Icons.Outlined.Info,
                                contentDescription = null,
                                tint = AppTheme.InfoColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Note: Lal Kitab is a distinct system from classical Vedic astrology.",
                                fontSize = 12.sp,
                                color = AppTheme.InfoColor,
                                lineHeight = 16.sp
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
private fun OverviewTab(analysis: LalKitabAnalysis, language: String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // System Note Banner
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.InfoColor.copy(alpha = 0.1f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Outlined.MenuBook,
                        contentDescription = null,
                        tint = AppTheme.InfoColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        analysis.systemNote,
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Planetary Afflictions Summary
        if (analysis.planetaryAfflictions.isNotEmpty()) {
            item {
                SectionHeader(
                    title = stringResource(StringKeyDosha.LAL_KITAB_SCREEN_PLANETARY_AFFLICTIONS),
                    icon = Icons.Filled.Warning,
                    tint = AppTheme.WarningColor
                )
            }

            items(analysis.planetaryAfflictions.take(5)) { affliction ->
                PlanetaryAfflictionCard(affliction, language)
            }
        }

        // Top Remedies
        if (analysis.remedies.isNotEmpty()) {
            item {
                SectionHeader(
                    title = "Recommended Remedies",
                    icon = Icons.Filled.Healing,
                    tint = AppTheme.SuccessColor
                )
            }

            items(analysis.remedies.take(5)) { remedy ->
                RemedyCard(remedy, language)
            }
        }

        // General Recommendations
        item {
            GeneralRecommendationsCard(analysis.generalRecommendations)
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
private fun PlanetaryAfflictionCard(affliction: PlanetaryAffliction, language: String) {
    var expanded by remember { mutableStateOf(false) }

    val severityColor = when (affliction.severity) {
        AfflictionSeverity.SEVERE -> AppTheme.ErrorColor
        AfflictionSeverity.MODERATE -> AppTheme.WarningColor
        AfflictionSeverity.MILD -> AppTheme.AccentGold
        AfflictionSeverity.MINIMAL -> AppTheme.TextMuted
    }

    val afflictionTypeName = when (affliction.afflictionType) {
        AfflictionType.PITRU_DOSH -> stringResource(StringKeyDosha.LAL_KITAB_DEBT_PITRU)
        AfflictionType.MATRU_RIN -> stringResource(StringKeyDosha.LAL_KITAB_DEBT_MATRU)
        AfflictionType.STRI_RIN -> stringResource(StringKeyDosha.LAL_KITAB_DEBT_STRI)
        AfflictionType.KANYA_RIN -> "Kanya Rin"
        AfflictionType.GRAHAN_DOSH -> "Grahan Dosh"
        AfflictionType.SHANI_PEEDA -> "Shani Peeda"
        AfflictionType.NONE -> "General"
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
                            .background(AppTheme.getPlanetColor(affliction.planet).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            affliction.planet.displayName.take(2),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = AppTheme.getPlanetColor(affliction.planet)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            affliction.planet.getLocalizedName(language),
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            "House ${affliction.house} • $afflictionTypeName",
                            fontSize = 12.sp,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Surface(
                    color = severityColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        affliction.severity.name,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = severityColor
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))

                    if (affliction.effects.isNotEmpty()) {
                        Text(
                            stringResource(StringKeyDosha.EFFECTS_LABEL),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        affliction.effects.forEach { effect ->
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

                    if (affliction.remedies.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Remedies",
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.SuccessColor
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        affliction.remedies.forEach { remedy ->
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
                                    remedy,
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
private fun RemedyCard(remedy: LalKitabRemedy, language: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.SuccessColor.copy(alpha = 0.05f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Spa,
                    contentDescription = null,
                    tint = AppTheme.SuccessColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    remedy.remedy,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (remedy.method.isNotEmpty()) {
                Text(
                    remedy.method,
                    fontSize = 13.sp,
                    color = AppTheme.TextSecondary,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (remedy.frequency.isNotEmpty()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Schedule,
                            contentDescription = null,
                            tint = AppTheme.TextMuted,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            remedy.frequency,
                            fontSize = 11.sp,
                            color = AppTheme.TextMuted
                        )
                    }
                }
                if (remedy.effectiveness.isNotEmpty()) {
                    Surface(
                        color = AppTheme.SuccessColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            remedy.effectiveness,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            fontSize = 10.sp,
                            color = AppTheme.SuccessColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GeneralRecommendationsCard(recommendations: List<String>) {
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
                    "General Principles",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            recommendations.forEach { rec ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Text("•", color = AppTheme.AccentGold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        rec,
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun KarmicDebtsTab(analysis: LalKitabAnalysis, language: String) {
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
                        "Understanding Karmic Debts (Rin)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "In Lal Kitab, karmic debts represent obligations carried from past lives. These manifest as specific challenges in current life until properly addressed through remedies and right actions.",
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        // Karmic Debts List
        if (analysis.karmicDebts.isNotEmpty()) {
            items(analysis.karmicDebts) { debt ->
                KarmicDebtCard(debt, language)
            }
        } else {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = AppTheme.SuccessColor.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(12.dp)
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
                                "No Major Karmic Debts",
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                            Text(
                                "No significant karmic debt indicators found in your chart",
                                fontSize = 13.sp,
                                color = AppTheme.TextMuted
                            )
                        }
                    }
                }
            }
        }

        // Debt Types Reference
        item {
            DebtTypesReferenceCard()
        }
    }
}

@Composable
private fun KarmicDebtCard(debt: KarmicDebt, language: String) {
    var expanded by remember { mutableStateOf(false) }

    val (icon, color, title) = when (debt.type) {
        DebtType.PITRU_RIN -> Triple(Icons.Filled.Elderly, AppTheme.AccentGold, stringResource(StringKeyDosha.LAL_KITAB_DEBT_PITRU))
        DebtType.MATRU_RIN -> Triple(Icons.Filled.Face, AppTheme.AccentTeal, stringResource(StringKeyDosha.LAL_KITAB_DEBT_MATRU))
        DebtType.STRI_RIN -> Triple(Icons.Filled.Favorite, AppTheme.ErrorColor.copy(alpha = 0.8f), stringResource(StringKeyDosha.LAL_KITAB_DEBT_STRI))
        DebtType.KANYA_RIN -> Triple(Icons.Filled.ChildCare, AppTheme.AccentPrimary, stringResource(StringKeyDosha.LAL_KITAB_DEBT_SELF))
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
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(color.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            icon,
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            title,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            debt.description,
                            fontSize = 12.sp,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Icon(
                    if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))

                    if (debt.indicators.isNotEmpty()) {
                        Text(
                            "Indicators",
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        debt.indicators.forEach { indicator ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    Icons.Outlined.Info,
                                    contentDescription = null,
                                    tint = AppTheme.InfoColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    indicator,
                                    fontSize = 12.sp,
                                    color = AppTheme.TextSecondary
                                )
                            }
                        }
                    }

                    if (debt.effects.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            stringResource(StringKeyDosha.EFFECTS_LABEL),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.WarningColor
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        debt.effects.forEach { effect ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text("•", color = AppTheme.WarningColor, fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    effect,
                                    fontSize = 12.sp,
                                    color = AppTheme.TextSecondary
                                )
                            }
                        }
                    }

                    if (debt.remedies.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Remedies",
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.SuccessColor
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        debt.remedies.forEach { remedy ->
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
                                    remedy,
                                    fontSize = 12.sp,
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

@Composable
private fun DebtTypesReferenceCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "Types of Karmic Debts",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            val debtTypes = listOf(
                Triple(stringResource(StringKeyDosha.LAL_KITAB_DEBT_PITRU), "Debt towards father and ancestors", AppTheme.AccentGold),
                Triple(stringResource(StringKeyDosha.LAL_KITAB_DEBT_MATRU), "Debt towards mother and maternal lineage", AppTheme.AccentTeal),
                Triple(stringResource(StringKeyDosha.LAL_KITAB_DEBT_STRI), "Debt towards wife/women", AppTheme.ErrorColor.copy(alpha = 0.8f)),
                Triple(stringResource(StringKeyDosha.LAL_KITAB_DEBT_SELF), "Debt towards self and karma", AppTheme.AccentPrimary)
            )

            debtTypes.forEach { (name, desc, color) ->
                Row(
                    modifier = Modifier.padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            name,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            desc,
                            fontSize = 11.sp,
                            color = AppTheme.TextMuted
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeeklyScheduleTab(analysis: LalKitabAnalysis, language: String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                stringResource(StringKeyDosha.LAL_KITAB_WEEKLY_SCHEDULE),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                "Daily remedies for each planetary day as per Lal Kitab tradition",
                fontSize = 13.sp,
                color = AppTheme.TextMuted,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(analysis.annualCalendar) { entry ->
            WeeklyRemedyCard(entry, language)
        }
    }
}

@Composable
private fun WeeklyRemedyCard(entry: AnnualRemedyEntry, language: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AppTheme.getPlanetColor(entry.planet).copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    entry.day.take(3),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = AppTheme.getPlanetColor(entry.planet)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        entry.day,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Surface(
                        color = AppTheme.getPlanetColor(entry.planet).copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            entry.planet.getLocalizedName(language),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.getPlanetColor(entry.planet)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                entry.remedies.forEach { remedy ->
                    Row(
                        modifier = Modifier.padding(vertical = 2.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            Icons.Filled.Circle,
                            contentDescription = null,
                            tint = AppTheme.getPlanetColor(entry.planet),
                            modifier = Modifier.size(6.dp).padding(top = 6.dp)
                        )
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

@Composable
private fun ColorDirectionTab(analysis: LalKitabAnalysis, language: String) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Color Remedies Section
        item {
            SectionHeader(
                title = stringResource(StringKeyDosha.LAL_KITAB_COLOR_THERAPY),
                icon = Icons.Filled.Palette,
                tint = AppTheme.AccentPrimary
            )
        }

        items(analysis.colorRemedies) { colorRemedy ->
            ColorRemedyCard(colorRemedy, language)
        }

        // Direction Remedies Section
        item {
            Spacer(modifier = Modifier.height(8.dp))
            SectionHeader(
                title = stringResource(StringKeyDosha.LAL_KITAB_DIRECTION_GUIDANCE),
                icon = Icons.Filled.Explore,
                tint = AppTheme.AccentTeal
            )
        }

        items(analysis.directionRemedies) { dirRemedy ->
            DirectionRemedyCard(dirRemedy, language)
        }
    }
}

@Composable
private fun ColorRemedyCard(remedy: ColorRemedy, language: String) {
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
                        .background(AppTheme.getPlanetColor(remedy.planet).copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Palette,
                        contentDescription = null,
                        tint = AppTheme.getPlanetColor(remedy.planet),
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    remedy.planet.getLocalizedName(language),
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        "Favorable",
                        fontSize = 11.sp,
                        color = AppTheme.SuccessColor,
                        fontWeight = FontWeight.Medium
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(remedy.favorableColors) { color ->
                            Surface(
                                color = AppTheme.SuccessColor.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    color,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    fontSize = 11.sp,
                                    color = AppTheme.TextSecondary
                                )
                            }
                        }
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "Avoid",
                        fontSize = 11.sp,
                        color = AppTheme.ErrorColor,
                        fontWeight = FontWeight.Medium
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(remedy.avoidColors) { color ->
                            Surface(
                                color = AppTheme.ErrorColor.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    color,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    fontSize = 11.sp,
                                    color = AppTheme.TextSecondary
                                )
                            }
                        }
                    }
                }
            }

            if (remedy.application.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    remedy.application,
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
        }
    }
}

@Composable
private fun DirectionRemedyCard(remedy: DirectionRemedy, language: String) {
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
                        .background(AppTheme.AccentTeal.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Explore,
                        contentDescription = null,
                        tint = AppTheme.AccentTeal,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    remedy.planet.getLocalizedName(language),
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Check,
                        contentDescription = null,
                        tint = AppTheme.SuccessColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        remedy.favorableDirection,
                        fontSize = 13.sp,
                        color = AppTheme.SuccessColor,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = null,
                        tint = AppTheme.ErrorColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        remedy.avoidDirection,
                        fontSize = 13.sp,
                        color = AppTheme.ErrorColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            if (remedy.application.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    remedy.application,
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted
                )
            }
        }
    }
}
