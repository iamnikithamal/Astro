package com.astro.storm.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.DashaSandhiAnalyzer
import com.astro.storm.ephemeris.DashaCalculator
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Dasha Sandhi Screen - Displays planetary period transition analysis
 *
 * Shows detailed analysis of Dasha Sandhi (junction points between planetary periods)
 * including current sandhi, upcoming transitions, and calendar view.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashaSandhiScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    val language = LocalLanguage.current
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var isCalculating by remember { mutableStateOf(true) }
    var sandhiAnalysis by remember { mutableStateOf<DashaSandhiAnalyzer.CompleteSandhiAnalysis?>(null) }
    var showInfoDialog by remember { mutableStateOf(false) }

    val tabs = listOf("Overview", "Current", "Upcoming", "Calendar")

    // Calculate Dasha Sandhi analysis
    LaunchedEffect(chart) {
        if (chart == null) {
            isCalculating = false
            return@LaunchedEffect
        }
        isCalculating = true
        delay(300) // Brief delay for smooth transition
        try {
            sandhiAnalysis = withContext(Dispatchers.Default) {
                val dashaTimeline = DashaCalculator.calculateDashaTimeline(chart)
                DashaSandhiAnalyzer.analyzeCompleteSandhis(
                    chart = chart,
                    dashaTimeline = dashaTimeline,
                    analysisDate = LocalDate.now(),
                    lookAheadMonths = 24
                )
            }
        } catch (e: Exception) {
            // Handle calculation error
        }
        isCalculating = false
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Dasha Sandhi",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            text = "Period Transitions",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AppTheme.TextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showInfoDialog = true }) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = "Info",
                            tint = AppTheme.TextSecondary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.ScreenBackground
                )
            )
        }
    ) { paddingValues ->
        when {
            isCalculating -> {
                LoadingContent(modifier = Modifier.padding(paddingValues))
            }
            chart == null || sandhiAnalysis == null -> {
                EmptyContent(modifier = Modifier.padding(paddingValues))
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .background(AppTheme.ScreenBackground),
                    contentPadding = PaddingValues(bottom = 32.dp)
                ) {
                    // Tab selector
                    item {
                        TabSelector(
                            tabs = tabs,
                            selectedTab = selectedTab,
                            onTabSelected = { selectedTab = it }
                        )
                    }

                    // Content based on selected tab
                    when (selectedTab) {
                        0 -> {
                            item { OverviewSection(sandhiAnalysis!!) }
                        }
                        1 -> {
                            item { CurrentSandhiSection(sandhiAnalysis!!) }
                        }
                        2 -> {
                            item { UpcomingSandhiSection(sandhiAnalysis!!) }
                        }
                        3 -> {
                            item { CalendarSection(sandhiAnalysis!!) }
                        }
                    }
                }
            }
        }
    }

    // Info Dialog
    if (showInfoDialog) {
        InfoDialog(
            title = "About Dasha Sandhi",
            content = """
                Dasha Sandhi refers to the junction or transition point between two planetary periods (Dashas) in Vedic astrology.

                These transition periods are considered sensitive times when:
                • The energy shifts from one planetary influence to another
                • Both planets' effects may be felt simultaneously
                • Major life changes often occur
                • Careful planning is advised

                The intensity of a Sandhi depends on:
                • The nature of the transitioning planets
                • Their relationship in your chart
                • Current transits and aspects

                Understanding your Sandhi periods helps you prepare for and navigate these significant life transitions.
            """.trimIndent(),
            onDismiss = { showInfoDialog = false }
        )
    }
}

@Composable
private fun TabSelector(
    tabs: List<String>,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tabs.size) { index ->
            FilterChip(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                label = {
                    Text(
                        text = tabs[index],
                        fontSize = 13.sp,
                        fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal
                    )
                },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = AppTheme.AccentPrimary.copy(alpha = 0.15f),
                    selectedLabelColor = AppTheme.AccentPrimary,
                    containerColor = AppTheme.CardBackground,
                    labelColor = AppTheme.TextSecondary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = AppTheme.BorderColor,
                    selectedBorderColor = AppTheme.AccentPrimary.copy(alpha = 0.3f),
                    enabled = true,
                    selected = selectedTab == index
                )
            )
        }
    }
}

@Composable
private fun OverviewSection(analysis: DashaSandhiAnalyzer.CompleteSandhiAnalysis) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Volatility Score Card
        VolatilityScoreCard(score = analysis.overallVolatilityScore)

        // Quick Stats
        QuickStatsRow(analysis)

        // General Guidance
        GuidanceCard(guidance = analysis.generalGuidance)

        // Current Status Summary
        analysis.currentSandhi?.let { currentSandhi ->
            CurrentStatusCard(sandhi = currentSandhi)
        }
    }
}

@Composable
private fun VolatilityScoreCard(score: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Overall Volatility",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Circular progress indicator
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                val progress by animateFloatAsState(
                    targetValue = score / 100f,
                    animationSpec = tween(1000),
                    label = "volatilityProgress"
                )
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxSize(),
                    color = getVolatilityColor(score),
                    trackColor = AppTheme.DividerColor,
                    strokeWidth = 10.dp,
                    strokeCap = StrokeCap.Round
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "$score%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = getVolatilityColor(score)
                    )
                    Text(
                        text = getVolatilityLabel(score),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Based on current and upcoming period transitions",
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun QuickStatsRow(analysis: DashaSandhiAnalyzer.CompleteSandhiAnalysis) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            title = "Active",
            value = if (analysis.currentSandhi != null) "Yes" else "No",
            color = if (analysis.currentSandhi != null) AppTheme.WarningColor else AppTheme.SuccessColor,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = "Upcoming",
            value = "${analysis.upcomingSandhis.size}",
            color = AppTheme.AccentPrimary,
            modifier = Modifier.weight(1f)
        )
        StatCard(
            title = "Recent",
            value = "${analysis.recentPastSandhis.size}",
            color = AppTheme.TextMuted,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun GuidanceCard(guidance: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.TipsAndUpdates,
                contentDescription = null,
                tint = AppTheme.AccentGold,
                modifier = Modifier.size(24.dp)
            )
            Column {
                Text(
                    text = "General Guidance",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = guidance,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppTheme.TextSecondary,
                    lineHeight = 22.sp
                )
            }
        }
    }
}

@Composable
private fun CurrentStatusCard(sandhi: DashaSandhiAnalyzer.SandhiAnalysis) {
    val language = LocalLanguage.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getSandhiIntensityColor(sandhi.intensity).copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Warning,
                    contentDescription = null,
                    tint = getSandhiIntensityColor(sandhi.intensity),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Currently in Sandhi Period",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = getSandhiIntensityColor(sandhi.intensity)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${sandhi.sandhi.fromPlanet.getLocalizedName(language)} → ${sandhi.sandhi.toPlanet.getLocalizedName(language)}",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Intensity: ${sandhi.intensity.displayName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
                Text(
                    text = "Ends: ${sandhi.sandhi.sandhiEndDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
            }
        }
    }
}

@Composable
private fun CurrentSandhiSection(analysis: DashaSandhiAnalyzer.CompleteSandhiAnalysis) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (analysis.currentSandhi != null) {
            SandhiDetailCard(
                sandhi = analysis.currentSandhi,
                isExpanded = true
            )
        } else {
            // No current sandhi
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = AppTheme.SuccessColor,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No Active Sandhi",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "You are not currently in a Dasha Sandhi period. Check the Upcoming tab for future transitions.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SandhiDetailCard(
    sandhi: DashaSandhiAnalyzer.SandhiAnalysis,
    isExpanded: Boolean = false
) {
    val language = LocalLanguage.current
    var expanded by remember { mutableStateOf(isExpanded) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Intensity indicator
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(getSandhiIntensityColor(sandhi.intensity))
                    )
                    Column {
                        Text(
                            text = "${sandhi.sandhi.fromPlanet.getLocalizedName(language)} → ${sandhi.sandhi.toPlanet.getLocalizedName(language)}",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            text = sandhi.transitionType.name.replace("_", " "),
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = getSandhiIntensityColor(sandhi.intensity).copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = sandhi.intensity.displayName,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = getSandhiIntensityColor(sandhi.intensity),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = AppTheme.TextMuted
                    )
                }
            }

            // Date range
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Start: ${sandhi.sandhi.sandhiStartDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
                Text(
                    text = "End: ${sandhi.sandhi.sandhiEndDate.format(DateTimeFormatter.ofPattern("MMM d, yyyy"))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
            }

            // Expanded content
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Predictions
                    Text(
                        text = "Predictions",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Key dates
                    sandhi.predictions.keyDates.forEach { keyDate ->
                        KeyDateItem(keyDate = keyDate)
                    }

                    // Life area impacts
                    if (sandhi.affectedLifeAreas.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Life Area Impacts",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            sandhi.affectedLifeAreas.forEach { impact ->
                                LifeAreaChip(impact = impact)
                            }
                        }
                    }

                    // Remedies
                    if (sandhi.remedies.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Recommended Remedies",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        sandhi.remedies.take(3).forEach { remedy ->
                            RemedyItem(remedy = remedy)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun KeyDateItem(keyDate: DashaSandhiAnalyzer.KeyDatePrediction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = keyDate.date.format(DateTimeFormatter.ofPattern("MMM d")),
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            color = AppTheme.AccentPrimary,
            modifier = Modifier.width(50.dp)
        )
        Column {
            Text(
                text = keyDate.event,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextPrimary
            )
            Text(
                text = keyDate.significance,
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun LifeAreaChip(impact: DashaSandhiAnalyzer.LifeAreaImpact) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = getImpactColor(impact.impactLevel).copy(alpha = 0.15f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(getImpactColor(impact.impactLevel))
            )
            Text(
                text = impact.area,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextPrimary
            )
        }
    }
}

@Composable
private fun RemedyItem(remedy: DashaSandhiAnalyzer.SandhiRemedy) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        color = AppTheme.CardBackgroundElevated
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = remedy.type.name.replace("_", " "),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.AccentPrimary
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = remedy.description,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun UpcomingSandhiSection(analysis: DashaSandhiAnalyzer.CompleteSandhiAnalysis) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (analysis.upcomingSandhis.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No upcoming Sandhi periods in the analysis window.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            analysis.upcomingSandhis.forEach { sandhi ->
                SandhiDetailCard(sandhi = sandhi)
            }
        }
    }
}

@Composable
private fun CalendarSection(analysis: DashaSandhiAnalyzer.CompleteSandhiAnalysis) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (analysis.sandhiCalendar.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = AppTheme.TextMuted,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No calendar entries available.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            // Group by month
            val groupedByMonth = analysis.sandhiCalendar.groupBy { entry ->
                entry.date.format(DateTimeFormatter.ofPattern("MMMM yyyy"))
            }

            groupedByMonth.forEach { (month, entries) ->
                Text(
                    text = month,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                entries.forEach { entry ->
                    CalendarEntryCard(entry = entry)
                }
            }
        }
    }
}

@Composable
private fun CalendarEntryCard(entry: DashaSandhiAnalyzer.SandhiCalendarEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getSandhiIntensityColor(entry.intensity).copy(alpha = 0.08f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Date badge
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = AppTheme.CardBackground
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = entry.date.format(DateTimeFormatter.ofPattern("d")),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = entry.date.format(DateTimeFormatter.ofPattern("EEE")),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }
            }

            // Event details
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entry.sandhiType,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
                Text(
                    text = "${entry.fromPlanet.displayName} → ${entry.toPlanet.displayName}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Intensity badge
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = getSandhiIntensityColor(entry.intensity).copy(alpha = 0.2f)
            ) {
                Text(
                    text = entry.intensity.displayName,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Medium,
                    color = getSandhiIntensityColor(entry.intensity),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun LoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = AppTheme.AccentPrimary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Analyzing Dasha Sandhis...",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun EmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = null,
                tint = AppTheme.TextMuted,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No Chart Data",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Create or select a birth chart to analyze Dasha Sandhi periods.",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun InfoDialog(
    title: String,
    content: String,
    onDismiss: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                lineHeight = 22.sp
            )
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text(stringResource(StringKeyDosha.BTN_GOT_IT), color = AppTheme.AccentPrimary)
            }
        },
        containerColor = AppTheme.CardBackground
    )
}

// Helper functions
@Composable
private fun getVolatilityColor(score: Int): Color {
    return when {
        score >= 80 -> AppTheme.ErrorColor
        score >= 60 -> AppTheme.WarningColor
        score >= 40 -> AppTheme.AccentGold
        score >= 20 -> AppTheme.AccentTeal
        else -> AppTheme.SuccessColor
    }
}

private fun getVolatilityLabel(score: Int): String {
    return when {
        score >= 80 -> "Very High"
        score >= 60 -> "High"
        score >= 40 -> "Moderate"
        score >= 20 -> "Low"
        else -> "Minimal"
    }
}

@Composable
private fun getSandhiIntensityColor(intensity: DashaSandhiAnalyzer.SandhiIntensity): Color {
    return when (intensity) {
        DashaSandhiAnalyzer.SandhiIntensity.CRITICAL -> AppTheme.ErrorColor
        DashaSandhiAnalyzer.SandhiIntensity.HIGH -> Color(0xFFFF9800)
        DashaSandhiAnalyzer.SandhiIntensity.MODERATE -> AppTheme.WarningColor
        DashaSandhiAnalyzer.SandhiIntensity.MILD -> AppTheme.SuccessColor
        DashaSandhiAnalyzer.SandhiIntensity.MINIMAL -> AppTheme.AccentTeal
    }
}

@Composable
private fun getImpactColor(level: Int): Color {
    return when {
        level >= 4 -> AppTheme.ErrorColor
        level >= 3 -> AppTheme.WarningColor
        level >= 2 -> AppTheme.AccentGold
        else -> AppTheme.SuccessColor
    }
}
