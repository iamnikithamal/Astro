package com.astro.storm.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Brightness4
import androidx.compose.material.icons.outlined.Brightness7
import androidx.compose.material.icons.outlined.EmojiEmotions
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.TrendingDown
import androidx.compose.material.icons.outlined.TrendingUp
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.AvasthaCalculator
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

/**
 * Avastha Screen
 *
 * Displays the various planetary states (Avasthas) that indicate how
 * effectively each planet can deliver its results. Shows Baladi, Jagradadi,
 * Deeptadi, and Lajjitadi Avasthas for each planet.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvasthaScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    val language = LocalLanguage.current
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    var isCalculating by remember { mutableStateOf(true) }
    var analysis by remember { mutableStateOf<AvasthaCalculator.AvasthaAnalysis?>(null) }
    var showInfoDialog by remember { mutableStateOf(false) }

    val tabs = listOf("Overview", "Planets", "Baladi", "Jagradadi", "Deeptadi", "Lajjitadi")

    // Calculate analysis
    LaunchedEffect(chart) {
        if (chart == null) {
            isCalculating = false
            return@LaunchedEffect
        }
        isCalculating = true
        delay(300)
        try {
            analysis = withContext(Dispatchers.Default) {
                AvasthaCalculator.analyzeAvasthas(chart)
            }
        } catch (e: Exception) {
            // Handle error
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
                            text = "Planetary Avasthas",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            text = "Planetary States & Conditions",
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
                AvasthaLoadingContent(modifier = Modifier.padding(paddingValues))
            }
            chart == null || analysis == null -> {
                AvasthaEmptyContent(modifier = Modifier.padding(paddingValues))
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
                        AvasthaTabSelector(
                            tabs = tabs,
                            selectedTab = selectedTab,
                            onTabSelected = { selectedTab = it }
                        )
                    }

                    // Content based on tab
                    when (selectedTab) {
                        0 -> item { AvasthaOverviewSection(analysis!!) }
                        1 -> item { AvasthaPlanetsSection(analysis!!) }
                        2 -> item { BaladiSection(analysis!!) }
                        3 -> item { JagradadiSection(analysis!!) }
                        4 -> item { DeeptadiSection(analysis!!) }
                        5 -> item { LajjitadiSection(analysis!!) }
                    }
                }
            }
        }
    }

    if (showInfoDialog) {
        AvasthaInfoDialog(onDismiss = { showInfoDialog = false })
    }
}

@Composable
private fun AvasthaTabSelector(
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
                    selectedContainerColor = AppTheme.AccentGold.copy(alpha = 0.15f),
                    selectedLabelColor = AppTheme.AccentGold,
                    containerColor = AppTheme.CardBackground,
                    labelColor = AppTheme.TextSecondary
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = AppTheme.BorderColor,
                    selectedBorderColor = AppTheme.AccentGold.copy(alpha = 0.3f),
                    enabled = true,
                    selected = selectedTab == index
                )
            )
        }
    }
}

@Composable
private fun AvasthaOverviewSection(analysis: AvasthaCalculator.AvasthaAnalysis) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Overall Strength Card
        OverallStrengthCard(analysis = analysis)

        // Strongest and Weakest Planets
        StrengthExtremeCards(analysis)

        // Quick Stats
        AvasthaQuickStatsRow(analysis)

        // Interpretation
        AvasthaInterpretationCard(analysis)

        // Recommendations
        if (analysis.recommendations.isNotEmpty()) {
            AvasthaRecommendationsCard(analysis.recommendations)
        }
    }
}

@Composable
private fun OverallStrengthCard(analysis: AvasthaCalculator.AvasthaAnalysis) {
    val strengthColor = getStrengthColor(analysis.overallStrength)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = strengthColor.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Strength icon
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(strengthColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Psychology,
                    contentDescription = null,
                    tint = strengthColor,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Overall Planetary Strength",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${analysis.overallStrength}%",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = strengthColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            LinearProgressIndicator(
                progress = { analysis.overallStrength / 100f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = strengthColor,
                trackColor = strengthColor.copy(alpha = 0.2f),
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when {
                    analysis.overallStrength >= 70 -> "Strong overall planetary configuration"
                    analysis.overallStrength >= 50 -> "Moderate overall planetary strength"
                    else -> "Planets need strengthening measures"
                },
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun StrengthExtremeCards(analysis: AvasthaCalculator.AvasthaAnalysis) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Strongest Planet
        analysis.strongestPlanet?.let { strongest ->
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = AppTheme.SuccessColor.copy(alpha = 0.08f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.TrendingUp,
                        contentDescription = null,
                        tint = AppTheme.SuccessColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = strongest.planet.symbol,
                        fontSize = 24.sp,
                        color = getPlanetColor(strongest.planet)
                    )
                    Text(
                        text = strongest.planet.displayName,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "${strongest.effectiveStrength}%",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.SuccessColor
                    )
                    Text(
                        text = "Strongest",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }
            }
        }

        // Weakest Planet
        analysis.weakestPlanet?.let { weakest ->
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = AppTheme.WarningColor.copy(alpha = 0.08f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.TrendingDown,
                        contentDescription = null,
                        tint = AppTheme.WarningColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = weakest.planet.symbol,
                        fontSize = 24.sp,
                        color = getPlanetColor(weakest.planet)
                    )
                    Text(
                        text = weakest.planet.displayName,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "${weakest.effectiveStrength}%",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.WarningColor
                    )
                    Text(
                        text = "Needs Attention",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun AvasthaQuickStatsRow(analysis: AvasthaCalculator.AvasthaAnalysis) {
    val strongCount = analysis.planetaryAvasthas.count { it.effectiveStrength >= 60 }
    val moderateCount = analysis.planetaryAvasthas.count { it.effectiveStrength in 40..59 }
    val weakCount = analysis.planetaryAvasthas.count { it.effectiveStrength < 40 }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AvasthaStatCard(
            title = "Strong",
            value = "$strongCount",
            color = AppTheme.SuccessColor,
            modifier = Modifier.weight(1f)
        )
        AvasthaStatCard(
            title = "Moderate",
            value = "$moderateCount",
            color = AppTheme.AccentGold,
            modifier = Modifier.weight(1f)
        )
        AvasthaStatCard(
            title = "Weak",
            value = "$weakCount",
            color = AppTheme.WarningColor,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun AvasthaStatCard(
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
private fun AvasthaInterpretationCard(analysis: AvasthaCalculator.AvasthaAnalysis) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "Interpretation",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = analysis.interpretation,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
private fun AvasthaRecommendationsCard(recommendations: List<AvasthaCalculator.AvasthaRecommendation>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Recommendations",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(12.dp))

            recommendations.forEach { rec ->
                val priorityColor = when (rec.priority) {
                    AvasthaCalculator.RemedyPriority.HIGH -> AppTheme.ErrorColor
                    AvasthaCalculator.RemedyPriority.MEDIUM -> AppTheme.WarningColor
                    AvasthaCalculator.RemedyPriority.LOW -> AppTheme.AccentTeal
                }

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = AppTheme.CardBackgroundElevated
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = rec.planet.symbol,
                                fontSize = 20.sp,
                                color = getPlanetColor(rec.planet)
                            )
                            Text(
                                text = rec.planet.displayName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = priorityColor.copy(alpha = 0.15f)
                            ) {
                                Text(
                                    text = rec.priority.displayName,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = priorityColor,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = rec.issue,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                        Text(
                            text = rec.remedy,
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
private fun AvasthaPlanetsSection(analysis: AvasthaCalculator.AvasthaAnalysis) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        analysis.planetaryAvasthas.forEach { avastha ->
            PlanetAvasthaCard(avastha = avastha)
        }
    }
}

@Composable
private fun PlanetAvasthaCard(avastha: AvasthaCalculator.PlanetaryAvastha) {
    var expanded by remember { mutableStateOf(false) }
    val strengthColor = getStrengthColor(avastha.effectiveStrength)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(16.dp)
        ) {
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
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(getPlanetColor(avastha.planet).copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = avastha.planet.symbol,
                            fontSize = 24.sp,
                            color = getPlanetColor(avastha.planet)
                        )
                    }
                    Column {
                        Text(
                            text = avastha.planet.displayName,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            text = "${avastha.sign.displayName} at ${String.format("%.1f", avastha.degree)}°",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${avastha.effectiveStrength}%",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = strengthColor
                        )
                    }
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = AppTheme.TextMuted
                    )
                }
            }

            // Avastha badges
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AvasthaBadge(
                    label = avastha.baladiAvastha.displayName.take(10),
                    color = getBaladiColor(avastha.baladiAvastha)
                )
                AvasthaBadge(
                    label = avastha.jagradadiAvastha.displayName.take(10),
                    color = getJagradadiColor(avastha.jagradadiAvastha)
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))

                    // All four avasthas with descriptions
                    AvasthaDetailRow(
                        title = "Age State (Baladi)",
                        value = avastha.baladiAvastha.displayName,
                        description = avastha.baladiAvastha.description,
                        color = getBaladiColor(avastha.baladiAvastha)
                    )
                    AvasthaDetailRow(
                        title = "Alertness (Jagradadi)",
                        value = avastha.jagradadiAvastha.displayName,
                        description = avastha.jagradadiAvastha.description,
                        color = getJagradadiColor(avastha.jagradadiAvastha)
                    )
                    AvasthaDetailRow(
                        title = "Dignity (Deeptadi)",
                        value = avastha.deeptadiAvastha.displayName,
                        description = avastha.deeptadiAvastha.description,
                        color = getDeeptadiColor(avastha.deeptadiAvastha)
                    )
                    AvasthaDetailRow(
                        title = "Emotional (Lajjitadi)",
                        value = avastha.lajjitadiAvastha.displayName,
                        description = avastha.lajjitadiAvastha.description,
                        color = getLajjitadiColor(avastha.lajjitadiAvastha)
                    )
                }
            }
        }
    }
}

@Composable
private fun AvasthaBadge(label: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(6.dp),
        color = color.copy(alpha = 0.15f)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun AvasthaDetailRow(
    title: String,
    value: String,
    description: String,
    color: Color
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextMuted
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.TextSecondary,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun BaladiSection(analysis: AvasthaCalculator.AvasthaAnalysis) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Info card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.AccentPrimary.copy(alpha = 0.08f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.SelfImprovement,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Baladi Avastha (Age States)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Based on the planet's degree within a sign. Determines the maturity and capability of the planet to deliver results.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
            }
        }

        // Planet list
        analysis.planetaryAvasthas.forEach { avastha ->
            BaladiPlanetCard(avastha = avastha)
        }
    }
}

@Composable
private fun BaladiPlanetCard(avastha: AvasthaCalculator.PlanetaryAvastha) {
    val color = getBaladiColor(avastha.baladiAvastha)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = avastha.planet.symbol,
                    fontSize = 24.sp,
                    color = getPlanetColor(avastha.planet)
                )
                Column {
                    Text(
                        text = avastha.planet.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "${avastha.baladiAvastha.resultPercentage}% result capacity",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = color.copy(alpha = 0.15f)
            ) {
                Text(
                    text = avastha.baladiAvastha.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = color,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
private fun JagradadiSection(analysis: AvasthaCalculator.AvasthaAnalysis) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Info card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.AccentGold.copy(alpha = 0.08f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Brightness7,
                        contentDescription = null,
                        tint = AppTheme.AccentGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Jagradadi Avastha (Alertness States)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Based on planet's relationship with sign lord. Determines how alert and active the planet is.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
            }
        }

        // Planet list
        analysis.planetaryAvasthas.forEach { avastha ->
            JagradadiPlanetCard(avastha = avastha)
        }
    }
}

@Composable
private fun JagradadiPlanetCard(avastha: AvasthaCalculator.PlanetaryAvastha) {
    val color = getJagradadiColor(avastha.jagradadiAvastha)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = avastha.planet.symbol,
                    fontSize = 24.sp,
                    color = getPlanetColor(avastha.planet)
                )
                Column {
                    Text(
                        text = avastha.planet.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "${avastha.jagradadiAvastha.resultPercentage}% result capacity",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }
            }
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = color.copy(alpha = 0.15f)
            ) {
                Text(
                    text = avastha.jagradadiAvastha.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = color,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
private fun DeeptadiSection(analysis: AvasthaCalculator.AvasthaAnalysis) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Info card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.AccentTeal.copy(alpha = 0.08f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Star,
                        contentDescription = null,
                        tint = AppTheme.AccentTeal,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Deeptadi Avastha (Dignity States)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Nine states based on exaltation, own sign, friends/enemies, and combustion. Determines overall dignity.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
            }
        }

        // Planet list
        analysis.planetaryAvasthas.forEach { avastha ->
            DeeptadiPlanetCard(avastha = avastha)
        }
    }
}

@Composable
private fun DeeptadiPlanetCard(avastha: AvasthaCalculator.PlanetaryAvastha) {
    val color = getDeeptadiColor(avastha.deeptadiAvastha)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = avastha.planet.symbol,
                        fontSize = 24.sp,
                        color = getPlanetColor(avastha.planet)
                    )
                    Text(
                        text = avastha.planet.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = color.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = avastha.deeptadiAvastha.displayName,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = color,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = avastha.deeptadiAvastha.description,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun LajjitadiSection(analysis: AvasthaCalculator.AvasthaAnalysis) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Info card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppTheme.PlanetVenus.copy(alpha = 0.08f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.EmojiEmotions,
                        contentDescription = null,
                        tint = AppTheme.PlanetVenus,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Lajjitadi Avastha (Emotional States)",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Based on conjunctions and aspects. Determines the emotional state and how the planet feels in its position.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
            }
        }

        // Planet list
        analysis.planetaryAvasthas.forEach { avastha ->
            LajjitadiPlanetCard(avastha = avastha)
        }
    }
}

@Composable
private fun LajjitadiPlanetCard(avastha: AvasthaCalculator.PlanetaryAvastha) {
    val color = getLajjitadiColor(avastha.lajjitadiAvastha)

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = avastha.planet.symbol,
                        fontSize = 24.sp,
                        color = getPlanetColor(avastha.planet)
                    )
                    Text(
                        text = avastha.planet.displayName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = color.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = avastha.lajjitadiAvastha.displayName,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = color,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = avastha.lajjitadiAvastha.description,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun AvasthaLoadingContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = AppTheme.AccentGold)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Analyzing Planetary States...",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun AvasthaEmptyContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Psychology,
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
                text = "Create or select a birth chart to analyze planetary Avasthas.",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AvasthaInfoDialog(onDismiss: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "About Planetary Avasthas",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Text(
                text = """
                    Avasthas are planetary states that indicate how effectively a planet can deliver its results.

                    Four Types of Avasthas:

                    1. Baladi (Age-based):
                    Bala (Infant), Kumara (Youth), Yuva (Adult), Vriddha (Old), Mrita (Dead)

                    2. Jagradadi (Alertness):
                    Jagrat (Awake), Swapna (Dreaming), Sushupti (Deep Sleep)

                    3. Deeptadi (Dignity):
                    Deepta, Swastha, Mudita, Shanta, Dina, Vikala, Khala, Kopa, Bhita

                    4. Lajjitadi (Emotional):
                    Lajjita, Garvita, Kshudita, Trushita, Mudita, Kshobhita

                    A planet in good avasthas gives its full results, while one in poor avasthas struggles to manifest its significations.
                """.trimIndent(),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                lineHeight = 22.sp
            )
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text(stringResource(StringKeyDosha.BTN_GOT_IT), color = AppTheme.AccentGold)
            }
        },
        containerColor = AppTheme.CardBackground
    )
}

// Helper functions for colors
@Composable
private fun getStrengthColor(strength: Int): Color {
    return when {
        strength >= 70 -> AppTheme.SuccessColor
        strength >= 50 -> AppTheme.AccentGold
        strength >= 30 -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }
}

@Composable
private fun getPlanetColor(planet: com.astro.storm.data.model.Planet): Color {
    return when (planet) {
        com.astro.storm.data.model.Planet.SUN -> AppTheme.PlanetSun
        com.astro.storm.data.model.Planet.MOON -> AppTheme.PlanetMoon
        com.astro.storm.data.model.Planet.MARS -> AppTheme.PlanetMars
        com.astro.storm.data.model.Planet.MERCURY -> AppTheme.PlanetMercury
        com.astro.storm.data.model.Planet.JUPITER -> AppTheme.PlanetJupiter
        com.astro.storm.data.model.Planet.VENUS -> AppTheme.PlanetVenus
        com.astro.storm.data.model.Planet.SATURN -> AppTheme.PlanetSaturn
        com.astro.storm.data.model.Planet.RAHU -> AppTheme.PlanetRahu
        com.astro.storm.data.model.Planet.KETU -> AppTheme.PlanetKetu
        com.astro.storm.data.model.Planet.URANUS -> AppTheme.PlanetSaturn
        com.astro.storm.data.model.Planet.NEPTUNE -> AppTheme.PlanetMoon
        com.astro.storm.data.model.Planet.PLUTO -> AppTheme.PlanetRahu
    }
}

@Composable
private fun getBaladiColor(avastha: AvasthaCalculator.BaladiAvastha): Color {
    return when (avastha) {
        AvasthaCalculator.BaladiAvastha.YUVA -> AppTheme.SuccessColor
        AvasthaCalculator.BaladiAvastha.KUMARA -> AppTheme.AccentTeal
        AvasthaCalculator.BaladiAvastha.BALA -> AppTheme.AccentGold
        AvasthaCalculator.BaladiAvastha.VRIDDHA -> AppTheme.WarningColor
        AvasthaCalculator.BaladiAvastha.MRITA -> AppTheme.ErrorColor
    }
}

@Composable
private fun getJagradadiColor(avastha: AvasthaCalculator.JagradadiAvastha): Color {
    return when (avastha) {
        AvasthaCalculator.JagradadiAvastha.JAGRAT -> AppTheme.SuccessColor
        AvasthaCalculator.JagradadiAvastha.SWAPNA -> AppTheme.AccentGold
        AvasthaCalculator.JagradadiAvastha.SUSHUPTI -> AppTheme.ErrorColor
    }
}

@Composable
private fun getDeeptadiColor(avastha: AvasthaCalculator.DeeptadiAvastha): Color {
    return when (avastha) {
        AvasthaCalculator.DeeptadiAvastha.DEEPTA -> AppTheme.SuccessColor
        AvasthaCalculator.DeeptadiAvastha.SWASTHA -> AppTheme.AccentTeal
        AvasthaCalculator.DeeptadiAvastha.MUDITA -> AppTheme.AccentGold
        AvasthaCalculator.DeeptadiAvastha.SHANTA -> AppTheme.AccentPrimary
        AvasthaCalculator.DeeptadiAvastha.DINA -> AppTheme.TextMuted
        AvasthaCalculator.DeeptadiAvastha.VIKALA -> AppTheme.WarningColor
        AvasthaCalculator.DeeptadiAvastha.KHALA -> AppTheme.ErrorColor
        AvasthaCalculator.DeeptadiAvastha.KOPA -> AppTheme.PlanetMars
        AvasthaCalculator.DeeptadiAvastha.BHITA -> AppTheme.ErrorColor
    }
}

@Composable
private fun getLajjitadiColor(avastha: AvasthaCalculator.LajjitadiAvastha): Color {
    return when (avastha) {
        AvasthaCalculator.LajjitadiAvastha.GARVITA -> AppTheme.SuccessColor
        AvasthaCalculator.LajjitadiAvastha.MUDITA -> AppTheme.AccentTeal
        AvasthaCalculator.LajjitadiAvastha.LAJJITA -> AppTheme.WarningColor
        AvasthaCalculator.LajjitadiAvastha.KSHUDITA -> AppTheme.PlanetMars
        AvasthaCalculator.LajjitadiAvastha.TRUSHITA -> AppTheme.AccentPrimary
        AvasthaCalculator.LajjitadiAvastha.KSHOBHITA -> AppTheme.ErrorColor
    }
}
