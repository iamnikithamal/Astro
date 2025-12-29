package com.astro.storm.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
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
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.ArudhaPadaCalculator
import com.astro.storm.ephemeris.ArudhaPadaCalculator.ArudhaPadaAnalysis
import com.astro.storm.ephemeris.ArudhaPadaCalculator.ArudhaPada
import com.astro.storm.ephemeris.ArudhaPadaCalculator.ArudhaPadaDetail
import com.astro.storm.ephemeris.ArudhaPadaCalculator.ArudhaYoga
import com.astro.storm.ephemeris.ArudhaPadaCalculator.ArudhaRelationship
import com.astro.storm.ephemeris.ArudhaPadaCalculator.ArudhaStrength
import com.astro.storm.ephemeris.ArudhaPadaCalculator.YogaStrength
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Arudha Pada Analysis Screen
 *
 * Comprehensive Jaimini Arudha Pada analysis showing:
 * - All 12 Arudha Padas and their positions
 * - Special Arudhas (AL, UL, A7, A10, A11) with detailed analysis
 * - Arudha Yogas (Raja Yoga, Dhana Yoga from Arudhas)
 * - Arudha relationships and their effects
 * - Transit and Dasha activation
 *
 * References: Jaimini Sutras, BPHS, Sanjay Rath's works
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArudhaPadaScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = "Arudha Pada",
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = currentLanguage()
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedArudha by remember { mutableStateOf<ArudhaPada?>(null) }
    var isCalculating by remember { mutableStateOf(true) }
    var arudhaAnalysis by remember { mutableStateOf<ArudhaPadaAnalysis?>(null) }

    val tabs = listOf(
        "Overview",
        "All Arudhas",
        "Yogas",
        "Relationships"
    )

    // Calculate Arudha Padas
    LaunchedEffect(chart) {
        isCalculating = true
        delay(300)
        try {
            arudhaAnalysis = withContext(Dispatchers.Default) {
                ArudhaPadaCalculator.analyzeArudhaPadas(chart)
            }
        } catch (e: Exception) {
            // Handle error silently
        }
        isCalculating = false
    }

    if (showInfoDialog) {
        ArudhaPadaInfoDialog(onDismiss = { showInfoDialog = false })
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Arudha Pada",
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
                            contentDescription = "About Arudha Pada",
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
        } else if (arudhaAnalysis != null) {
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
                    0 -> OverviewTab(arudhaAnalysis!!)
                    1 -> AllArudhasTab(
                        arudhaAnalysis!!,
                        selectedArudha = selectedArudha,
                        onArudhaSelected = { selectedArudha = it }
                    )
                    2 -> YogasTab(arudhaAnalysis!!)
                    3 -> RelationshipsTab(arudhaAnalysis!!)
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
                text = "Calculating Arudha Padas...",
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
private fun OverviewTab(analysis: ArudhaPadaAnalysis) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Summary card
        item {
            SummaryCard(analysis)
        }

        // Key Arudhas highlight
        item {
            KeyArudhasCard(analysis)
        }

        // Overall Assessment
        item {
            AssessmentCard(analysis.overallAssessment)
        }

        // Key Yogas preview
        if (analysis.arudhaYogas.isNotEmpty()) {
            item {
                KeyYogasPreview(analysis.arudhaYogas.take(3))
            }
        }

        // Recommendations
        item {
            RecommendationsCard(analysis.interpretation.recommendations)
        }
    }
}

@Composable
private fun SummaryCard(analysis: ArudhaPadaAnalysis) {
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
                    text = "Arudha Pada Analysis",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Text(
                text = analysis.interpretation.summary,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
private fun KeyArudhasCard(analysis: ArudhaPadaAnalysis) {
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
                text = "Key Arudha Positions",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            // Key Arudhas in a grid-like layout
            val keyArudhas = listOf(
                analysis.specialArudhas.arudhaLagna to Icons.Outlined.Person,
                analysis.specialArudhas.rajyaPada to Icons.Outlined.Work,
                analysis.specialArudhas.labhaPada to Icons.Outlined.TrendingUp,
                analysis.specialArudhas.darapada to Icons.Outlined.Handshake,
                analysis.specialArudhas.upapada to Icons.Outlined.Favorite
            )

            keyArudhas.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    row.forEach { (detail, icon) ->
                        KeyArudhaChip(
                            detail = detail,
                            icon = icon,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    // Fill empty space if odd number
                    if (row.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
private fun KeyArudhaChip(
    detail: ArudhaPadaDetail,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    val strengthColor = when (detail.arudha.strength) {
        ArudhaStrength.VERY_STRONG -> AppTheme.SuccessColor
        ArudhaStrength.STRONG -> Color(0xFF4CAF50)
        ArudhaStrength.MODERATE -> AppTheme.AccentGold
        ArudhaStrength.WEAK -> AppTheme.WarningColor
        ArudhaStrength.VERY_WEAK -> AppTheme.ErrorColor
    }

    Surface(
        modifier = modifier,
        color = AppTheme.ChipBackground,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(strengthColor.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = strengthColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            Column {
                Text(
                    text = detail.arudha.name,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Text(
                    text = detail.arudha.sign.displayName,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted
                )
            }
        }
    }
}

@Composable
private fun AssessmentCard(assessment: ArudhaPadaCalculator.ArudhaOverallAssessment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Manifestation Strength",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            // Strength bars
            StrengthBar(
                label = "Public Image",
                value = assessment.publicImageStrength,
                icon = Icons.Outlined.Person
            )
            StrengthBar(
                label = "Career",
                value = assessment.careerManifestationStrength,
                icon = Icons.Outlined.Work
            )
            StrengthBar(
                label = "Gains",
                value = assessment.gainsAndFulfillment,
                icon = Icons.Outlined.TrendingUp
            )
            StrengthBar(
                label = "Relationships",
                value = assessment.relationshipIndicator,
                icon = Icons.Outlined.Favorite
            )

            Divider(color = AppTheme.BorderColor, modifier = Modifier.padding(vertical = 4.dp))

            // Key themes
            if (assessment.keyThemes.isNotEmpty()) {
                Text(
                    text = "Key Themes",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(assessment.keyThemes) { theme ->
                        SuggestionChip(
                            onClick = {},
                            label = { Text(theme, style = MaterialTheme.typography.labelSmall) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = AppTheme.AccentPrimary.copy(alpha = 0.1f),
                                labelColor = AppTheme.AccentPrimary
                            ),
                            border = null
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StrengthBar(
    label: String,
    value: Int,
    icon: ImageVector
) {
    val color = when {
        value >= 70 -> AppTheme.SuccessColor
        value >= 50 -> AppTheme.AccentGold
        value >= 30 -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = AppTheme.TextMuted,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.TextSecondary,
            modifier = Modifier.width(80.dp)
        )
        LinearProgressIndicator(
            progress = { value / 100f },
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = color,
            trackColor = AppTheme.ChipBackground
        )
        Text(
            text = "$value%",
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun KeyYogasPreview(yogas: List<ArudhaYoga>) {
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
                    imageVector = Icons.Outlined.Stars,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Arudha Yogas",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            yogas.forEach { yoga ->
                YogaPreviewItem(yoga)
            }
        }
    }
}

@Composable
private fun YogaPreviewItem(yoga: ArudhaYoga) {
    val strengthColor = when (yoga.strength) {
        YogaStrength.EXCEPTIONAL -> AppTheme.AccentGold
        YogaStrength.STRONG -> AppTheme.SuccessColor
        YogaStrength.MODERATE -> AppTheme.AccentPrimary
        YogaStrength.MILD -> AppTheme.TextMuted
        YogaStrength.WEAK -> AppTheme.TextSubtle
    }

    Surface(
        color = AppTheme.ChipBackground,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(strengthColor)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = yoga.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
                Text(
                    text = yoga.effects,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
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
                    text = "Recommendations",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            recommendations.take(5).forEach { rec ->
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
// ALL ARUDHAS TAB
// ============================================

@Composable
private fun AllArudhasTab(
    analysis: ArudhaPadaAnalysis,
    selectedArudha: ArudhaPada?,
    onArudhaSelected: (ArudhaPada?) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(analysis.arudhaPadas) { arudha ->
            ArudhaPadaCard(
                arudha = arudha,
                isExpanded = selectedArudha == arudha,
                onClick = {
                    onArudhaSelected(if (selectedArudha == arudha) null else arudha)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArudhaPadaCard(
    arudha: ArudhaPada,
    isExpanded: Boolean,
    onClick: () -> Unit
) {
    val strengthColor = when (arudha.strength) {
        ArudhaStrength.VERY_STRONG -> AppTheme.SuccessColor
        ArudhaStrength.STRONG -> Color(0xFF4CAF50)
        ArudhaStrength.MODERATE -> AppTheme.AccentGold
        ArudhaStrength.WEAK -> AppTheme.WarningColor
        ArudhaStrength.VERY_WEAK -> AppTheme.ErrorColor
    }

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Arudha badge
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        strengthColor.copy(alpha = 0.3f),
                                        strengthColor.copy(alpha = 0.1f)
                                    )
                                )
                            )
                            .border(1.dp, strengthColor.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = arudha.name,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = strengthColor
                        )
                    }

                    Column {
                        Text(
                            text = arudha.fullName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.TextPrimary
                        )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = arudha.sign.symbol,
                                fontSize = 14.sp
                            )
                            Text(
                                text = arudha.sign.displayName,
                                style = MaterialTheme.typography.bodySmall,
                                color = AppTheme.TextMuted
                            )
                        }
                    }
                }

                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted
                )
            }

            // Strength indicator
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Strength:",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextMuted
                )
                Surface(
                    color = strengthColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = arudha.strength.name.replace("_", " "),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = strengthColor,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = "• Lord: ${arudha.houseLord.displayName} in ${arudha.houseLordSign.displayName}",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextMuted
                )
            }

            // Expanded content
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier.padding(top = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Divider(color = AppTheme.BorderColor)

                    // Interpretation
                    Text(
                        text = arudha.interpretation,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary,
                        lineHeight = 20.sp
                    )

                    // Planets in Arudha
                    if (arudha.planetsInArudha.isNotEmpty()) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Circle,
                                contentDescription = null,
                                tint = AppTheme.AccentPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Planets in ${arudha.name}: ",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium,
                                color = AppTheme.TextPrimary
                            )
                            Text(
                                text = arudha.planetsInArudha.joinToString { it.planet.displayName },
                                style = MaterialTheme.typography.bodySmall,
                                color = AppTheme.TextSecondary
                            )
                        }
                    }

                    // Significations
                    Text(
                        text = "Significations:",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.TextPrimary
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(arudha.significations) { signification ->
                            SuggestionChip(
                                onClick = {},
                                label = {
                                    Text(
                                        signification,
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = AppTheme.ChipBackground,
                                    labelColor = AppTheme.TextSecondary
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

// ============================================
// YOGAS TAB
// ============================================

@Composable
private fun YogasTab(analysis: ArudhaPadaAnalysis) {
    if (analysis.arudhaYogas.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Stars,
                    contentDescription = null,
                    tint = AppTheme.TextMuted,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = "No significant Arudha Yogas found",
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
            items(analysis.arudhaYogas) { yoga ->
                YogaCard(yoga)
            }
        }
    }
}

@Composable
private fun YogaCard(yoga: ArudhaYoga) {
    val strengthColor = when (yoga.strength) {
        YogaStrength.EXCEPTIONAL -> AppTheme.AccentGold
        YogaStrength.STRONG -> AppTheme.SuccessColor
        YogaStrength.MODERATE -> AppTheme.AccentPrimary
        YogaStrength.MILD -> AppTheme.TextMuted
        YogaStrength.WEAK -> AppTheme.TextSubtle
    }

    val typeIcon = when (yoga.type) {
        ArudhaPadaCalculator.ArudhaYogaType.RAJA_YOGA -> Icons.Outlined.MilitaryTech
        ArudhaPadaCalculator.ArudhaYogaType.DHANA_YOGA -> Icons.Outlined.AttachMoney
        ArudhaPadaCalculator.ArudhaYogaType.PARIVARTANA -> Icons.Outlined.SwapHoriz
        ArudhaPadaCalculator.ArudhaYogaType.ARGALA_YOGA -> Icons.Outlined.Shield
        ArudhaPadaCalculator.ArudhaYogaType.BHAVA_YOGA -> Icons.Outlined.Home
        ArudhaPadaCalculator.ArudhaYogaType.GRAHA_YOGA -> Icons.Outlined.Stars
    }

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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(strengthColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = typeIcon,
                            contentDescription = null,
                            tint = strengthColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column {
                        Text(
                            text = yoga.name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            text = yoga.type.name.replace("_", " "),
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Surface(
                    color = strengthColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = yoga.strength.name,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = strengthColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Involved Arudhas
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                yoga.involvedArudhas.forEach { arudha ->
                    Surface(
                        color = AppTheme.AccentPrimary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = arudha,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = AppTheme.AccentPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Text(
                    text = "in",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                yoga.involvedSigns.forEach { sign ->
                    Text(
                        text = "${sign.symbol} ${sign.displayName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }

            // Effects
            Text(
                text = yoga.effects,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary,
                lineHeight = 20.sp
            )

            // Timing
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
                    text = yoga.timing,
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextMuted
                )
            }

            // Recommendations
            if (yoga.recommendations.isNotEmpty()) {
                Divider(color = AppTheme.BorderColor)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    yoga.recommendations.forEach { rec ->
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text("•", color = AppTheme.AccentPrimary, fontSize = 12.sp)
                            Text(
                                text = rec,
                                style = MaterialTheme.typography.labelSmall,
                                color = AppTheme.TextMuted
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================
// RELATIONSHIPS TAB
// ============================================

@Composable
private fun RelationshipsTab(analysis: ArudhaPadaAnalysis) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(analysis.arudhaRelationships) { relationship ->
            RelationshipCard(relationship)
        }
    }
}

@Composable
private fun RelationshipCard(relationship: ArudhaRelationship) {
    val isPositiveColor = if (relationship.isPositive) AppTheme.SuccessColor else AppTheme.WarningColor

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Arudha 1
            Surface(
                color = AppTheme.AccentPrimary.copy(alpha = 0.1f),
                shape = CircleShape
            ) {
                Text(
                    text = relationship.arudha1,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.AccentPrimary
                )
            }

            // Relationship indicator
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Divider(
                        modifier = Modifier.width(20.dp),
                        color = isPositiveColor
                    )
                    Icon(
                        imageVector = if (relationship.isPositive)
                            Icons.Outlined.CheckCircle else Icons.Outlined.Warning,
                        contentDescription = null,
                        tint = isPositiveColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Divider(
                        modifier = Modifier.width(20.dp),
                        color = isPositiveColor
                    )
                }
                Text(
                    text = relationship.relationship.name.replace("_", " "),
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextMuted
                )
                Text(
                    text = "${relationship.distanceInSigns} signs",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextSubtle
                )
            }

            // Arudha 2
            Surface(
                color = AppTheme.AccentPrimary.copy(alpha = 0.1f),
                shape = CircleShape
            ) {
                Text(
                    text = relationship.arudha2,
                    modifier = Modifier.padding(12.dp),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.AccentPrimary
                )
            }
        }

        // Effect description
        Text(
            text = relationship.effect,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.TextSecondary
        )
    }
}

// ============================================
// INFO DIALOG
// ============================================

@Composable
private fun ArudhaPadaInfoDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "About Arudha Pada",
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "What is Arudha Pada?",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "Arudha means 'mount' or 'image'. In Jaimini astrology, Arudha Padas show how the matters of each house manifest and are perceived in the material world. They reveal the maya (illusion) or external image of various life areas.",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary
                    )
                }

                item {
                    Text(
                        text = "Key Arudhas",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf(
                            "AL (A1)" to "Arudha Lagna - Your public image and status",
                            "A7" to "Darapada - Business and partnerships",
                            "A10" to "Rajya Pada - Career manifestation",
                            "A11" to "Labha Pada - Gains and desires",
                            "UL (A12)" to "Upapada - Spouse characteristics"
                        ).forEach { (name, desc) ->
                            Row {
                                Text(
                                    text = "$name: ",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = AppTheme.AccentPrimary
                                )
                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AppTheme.TextSecondary
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Calculation Method",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "1. Find the lord of the house\n2. Count from house to lord's position\n3. Count same distance from lord\n4. That sign is the Arudha Pada\n\nException: If Arudha falls in same or 7th house, move 10 signs forward.",
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
                        text = "• Jaimini Sutras (Chapter 1, Pada 1)\n• BPHS Chapters 29-30\n• Sanjay Rath's 'Crux of Vedic Astrology'",
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
