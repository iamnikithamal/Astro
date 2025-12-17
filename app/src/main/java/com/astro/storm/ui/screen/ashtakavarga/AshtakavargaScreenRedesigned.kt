package com.astro.storm.ui.screen.ashtakavarga

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.TrendingUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.AshtakavargaCalculator
import com.astro.storm.ui.components.common.ModernPillTabRow
import com.astro.storm.ui.components.common.TabItem
import com.astro.storm.ui.screen.chartdetail.ChartDetailColors
import com.astro.storm.ui.theme.AppTheme

/**
 * Redesigned Ashtakavarga Screen
 *
 * A modern, clean UI for displaying Ashtakavarga analysis with:
 * - Summary overview with total bindus
 * - Tab navigation (Overview, Sarvashtakavarga, By Planet, By House)
 * - Visual grid displays
 * - Detailed planet-wise and house-wise analysis
 * - Strength interpretations
 * - Smooth animations throughout
 */

enum class AshtakavargaViewType(val title: String) {
    OVERVIEW("Overview"),
    SARVASHTAKAVARGA("Sarvashtakavarga"),
    BY_PLANET("By Planet"),
    BY_HOUSE("By House")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AshtakavargaScreenRedesigned(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    val language = LocalLanguage.current
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    var expandedPlanets by rememberSaveable { mutableStateOf(setOf<String>()) }
    var showInfoDialog by remember { mutableStateOf(false) }

    // Calculate Ashtakavarga
    val ashtakavarga = remember(chart) {
        chart?.let {
            try {
                AshtakavargaCalculator.calculateAshtakavarga(it)
            } catch (e: Exception) {
                null
            }
        }
    }

    // Read colors outside remember
    val accentPrimary = AppTheme.AccentPrimary
    val successColor = AppTheme.SuccessColor
    val accentGold = AppTheme.AccentGold
    val accentTeal = AppTheme.AccentTeal

    val tabs = remember(accentPrimary, successColor, accentGold, accentTeal) {
        AshtakavargaViewType.entries.map { type ->
            TabItem(
                title = type.title,
                accentColor = when (type) {
                    AshtakavargaViewType.OVERVIEW -> accentPrimary
                    AshtakavargaViewType.SARVASHTAKAVARGA -> successColor
                    AshtakavargaViewType.BY_PLANET -> accentGold
                    AshtakavargaViewType.BY_HOUSE -> accentTeal
                }
            )
        }
    }

    if (showInfoDialog) {
        AshtakavargaInfoDialog(onDismiss = { showInfoDialog = false })
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            AshtakavargaTopBar(
                chartName = chart?.birthData?.name ?: "",
                onBack = onBack,
                onInfoClick = { showInfoDialog = true }
            )
        }
    ) { paddingValues ->
        if (chart == null || ashtakavarga == null) {
            EmptyAshtakavargaContent(modifier = Modifier.padding(paddingValues))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(AppTheme.ScreenBackground)
            ) {
                // Tab row
                ModernPillTabRow(
                    tabs = tabs,
                    selectedIndex = selectedTabIndex,
                    onTabSelected = { selectedTabIndex = it },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )

                // Content
                AnimatedContent(
                    targetState = selectedTabIndex,
                    transitionSpec = {
                        fadeIn(tween(300)) togetherWith fadeOut(tween(200))
                    },
                    label = "AshtakavargaTabContent"
                ) { tabIndex ->
                    when (AshtakavargaViewType.entries[tabIndex]) {
                        AshtakavargaViewType.OVERVIEW -> {
                            AshtakavargaOverviewContent(
                                ashtakavarga = ashtakavarga,
                                chart = chart
                            )
                        }
                        AshtakavargaViewType.SARVASHTAKAVARGA -> {
                            SarvashtakavargaContent(ashtakavarga = ashtakavarga)
                        }
                        AshtakavargaViewType.BY_PLANET -> {
                            AshtakavargaByPlanetContent(
                                ashtakavarga = ashtakavarga,
                                expandedPlanets = expandedPlanets,
                                onTogglePlanet = { planet ->
                                    expandedPlanets = if (planet in expandedPlanets) {
                                        expandedPlanets - planet
                                    } else {
                                        expandedPlanets + planet
                                    }
                                }
                            )
                        }
                        AshtakavargaViewType.BY_HOUSE -> {
                            AshtakavargaByHouseContent(ashtakavarga = ashtakavarga)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AshtakavargaTopBar(
    chartName: String,
    onBack: () -> Unit,
    onInfoClick: () -> Unit
) {
    Surface(
        color = AppTheme.ScreenBackground,
        shadowElevation = 2.dp
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = stringResource(StringKey.FEATURE_ASHTAKAVARGA),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    if (chartName.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Bindu strength analysis - $chartName",
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(StringKey.BTN_BACK),
                        tint = AppTheme.TextPrimary
                    )
                }
            },
            actions = {
                IconButton(onClick = onInfoClick) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Ashtakavarga info",
                        tint = AppTheme.TextPrimary
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppTheme.ScreenBackground
            )
        )
    }
}

@Composable
private fun AshtakavargaOverviewContent(
    ashtakavarga: AshtakavargaCalculator.AshtakavargaAnalysis,
    chart: VedicChart
) {
    val language = LocalLanguage.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Summary card
        item(key = "summary") {
            AshtakavargaSummaryCard(ashtakavarga = ashtakavarga)
        }

        // House strength distribution
        item(key = "house_distribution") {
            HouseStrengthDistribution(ashtakavarga = ashtakavarga)
        }

        // Key insights
        item(key = "insights") {
            AshtakavargaInsightsCard(ashtakavarga = ashtakavarga, chart = chart)
        }

        // Quick planet view
        item(key = "planet_quick") {
            PlanetQuickView(ashtakavarga = ashtakavarga)
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun AshtakavargaSummaryCard(
    ashtakavarga: AshtakavargaCalculator.AshtakavargaAnalysis
) {
    val totalBindus = ashtakavarga.sarvashtakavarga.totalBindus
    val averageBindus = totalBindus / 12.0
    val strengthPercent = (totalBindus / 337.0 * 100).coerceIn(0.0, 100.0)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = AppTheme.SuccessColor.copy(alpha = 0.1f),
                spotColor = AppTheme.SuccessColor.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(20.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    AppTheme.SuccessColor.copy(alpha = 0.2f),
                                    AppTheme.SuccessColor.copy(alpha = 0.1f)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.BarChart,
                        contentDescription = null,
                        tint = AppTheme.SuccessColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = "Ashtakavarga Summary",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary,
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = "Bindu distribution analysis",
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Stats row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AshtakavargaStatItem(
                    value = totalBindus.toString(),
                    label = "Total Bindus",
                    color = AppTheme.SuccessColor,
                    modifier = Modifier.weight(1f)
                )
                AshtakavargaStatItem(
                    value = String.format("%.1f", averageBindus),
                    label = "Avg/House",
                    color = AppTheme.AccentPrimary,
                    modifier = Modifier.weight(1f)
                )
                AshtakavargaStatItem(
                    value = "${strengthPercent.toInt()}%",
                    label = "Strength",
                    color = getBinduStrengthColor(strengthPercent),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Overall strength bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = AppTheme.CardBackgroundElevated
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Overall Chart Strength",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.TextMuted
                        )
                        Text(
                            text = getStrengthLabel(strengthPercent),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = getBinduStrengthColor(strengthPercent)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { (strengthPercent / 100.0).toFloat().coerceIn(0f, 1f) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = getBinduStrengthColor(strengthPercent),
                        trackColor = AppTheme.DividerColor
                    )
                }
            }
        }
    }
}

@Composable
private fun AshtakavargaStatItem(
    value: String,
    label: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun HouseStrengthDistribution(
    ashtakavarga: AshtakavargaCalculator.AshtakavargaAnalysis
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(
                    Icons.Outlined.TrendingUp,
                    contentDescription = null,
                    tint = AppTheme.AccentTeal,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "House Strength Distribution",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            // Horizontal scrollable bar chart
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                ZodiacSign.entries.forEachIndexed { index, sign ->
                    val house = index + 1
                    val bindus = ashtakavarga.sarvashtakavarga.getBindusForSign(sign)
                    val maxBindus = 56 // Maximum possible per house
                    val heightPercent = (bindus.toFloat() / maxBindus).coerceIn(0f, 1f)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(45.dp)
                    ) {
                        // Bindu count
                        Text(
                            text = bindus.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = getBinduColor(bindus)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        // Bar
                        Box(
                            modifier = Modifier
                                .width(28.dp)
                                .height(80.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(AppTheme.DividerColor)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height((80 * heightPercent).dp)
                                    .align(Alignment.BottomCenter)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                getBinduColor(bindus),
                                                getBinduColor(bindus).copy(alpha = 0.7f)
                                            )
                                        ),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        // Sign
                        Text(
                            text = sign.symbol,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            text = "${house}H",
                            fontSize = 10.sp,
                            color = AppTheme.TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LegendItem(color = AppTheme.SuccessColor, label = ">30 Strong")
                LegendItem(color = AppTheme.AccentGold, label = "25-30 Good")
                LegendItem(color = AppTheme.WarningColor, label = "<25 Weak")
            }
        }
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            fontSize = 10.sp,
            color = AppTheme.TextMuted
        )
    }
}

@Composable
private fun AshtakavargaInsightsCard(
    ashtakavarga: AshtakavargaCalculator.AshtakavargaAnalysis,
    chart: VedicChart
) {
    val strongHouses = ashtakavarga.sarvashtakavarga.binduMatrix.entries
        .sortedByDescending { it.value }
        .take(3)
        .map { it.key to it.value }

    val weakHouses = ashtakavarga.sarvashtakavarga.binduMatrix.entries
        .sortedBy { it.value }
        .take(3)
        .map { it.key to it.value }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Key Insights",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 14.dp)
            )

            // Strongest houses
            Text(
                text = "STRONGEST HOUSES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = AppTheme.SuccessColor,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for ((sign, bindus) in strongHouses) {
                    val house = ZodiacSign.entries.indexOf(sign) + 1
                    InsightHouseChip(
                        house = house,
                        sign = sign,
                        bindus = bindus,
                        isStrong = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Weakest houses
            Text(
                text = "WEAKEST HOUSES",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = AppTheme.WarningColor,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for ((sign, bindus) in weakHouses) {
                    val house = ZodiacSign.entries.indexOf(sign) + 1
                    InsightHouseChip(
                        house = house,
                        sign = sign,
                        bindus = bindus,
                        isStrong = false,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun InsightHouseChip(
    house: Int,
    sign: ZodiacSign,
    bindus: Int,
    isStrong: Boolean,
    modifier: Modifier = Modifier
) {
    val color = if (isStrong) AppTheme.SuccessColor else AppTheme.WarningColor

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = sign.symbol,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = "${house}H",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextPrimary
            )
            Text(
                text = "$bindus bindus",
                fontSize = 10.sp,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun PlanetQuickView(
    ashtakavarga: AshtakavargaCalculator.AshtakavargaAnalysis
) {
    val language = LocalLanguage.current

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Planet Bindu Totals",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 14.dp)
            )

            val planets = listOf(
                Planet.SUN, Planet.MOON, Planet.MARS, Planet.MERCURY,
                Planet.JUPITER, Planet.VENUS, Planet.SATURN
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                planets.forEach { planet ->
                    val bhinnashtakavarga = ashtakavarga.bhinnashtakavarga[planet]
                    val totalBindus = bhinnashtakavarga?.totalBindus ?: 0
                    val planetColor = ChartDetailColors.getPlanetColor(planet)

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(planetColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = planet.symbol,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = totalBindus.toString(),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = planetColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SarvashtakavargaContent(
    ashtakavarga: AshtakavargaCalculator.AshtakavargaAnalysis
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item(key = "sarva_header") {
            Text(
                text = "Sarvashtakavarga is the combined bindu strength from all seven planets for each house/sign.",
                fontSize = 13.sp,
                color = AppTheme.TextMuted,
                lineHeight = 19.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Grid of all houses
        items(ZodiacSign.entries.chunked(3)) { rowSigns ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowSigns.forEach { sign ->
                    val house = ZodiacSign.entries.indexOf(sign) + 1
                    val bindus = ashtakavarga.sarvashtakavarga.getBindusForSign(sign)
                    SarvashtakavargaHouseCard(
                        house = house,
                        sign = sign,
                        bindus = bindus,
                        modifier = Modifier.weight(1f)
                    )
                }
                // Fill remaining space if less than 3
                repeat(3 - rowSigns.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun SarvashtakavargaHouseCard(
    house: Int,
    sign: ZodiacSign,
    bindus: Int,
    modifier: Modifier = Modifier
) {
    val color = getBinduColor(bindus)
    val strengthPercent = (bindus.toFloat() / 56f).coerceIn(0f, 1f)

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        color = AppTheme.CardBackground
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // House and sign
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${house}H",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextSecondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = sign.symbol,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bindu count
            Text(
                text = bindus.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Progress bar
            LinearProgressIndicator(
                progress = { strengthPercent },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = color,
                trackColor = AppTheme.DividerColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = getBinduLabel(bindus),
                fontSize = 10.sp,
                color = color,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun AshtakavargaByPlanetContent(
    ashtakavarga: AshtakavargaCalculator.AshtakavargaAnalysis,
    expandedPlanets: Set<String>,
    onTogglePlanet: (String) -> Unit
) {
    val language = LocalLanguage.current

    val planets = listOf(
        Planet.SUN, Planet.MOON, Planet.MARS, Planet.MERCURY,
        Planet.JUPITER, Planet.VENUS, Planet.SATURN
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = planets,
            key = { "planet_${it.symbol}" }
        ) { planet ->
            val isExpanded = planet.symbol in expandedPlanets
            val planetAshtakavarga = ashtakavarga.bhinnashtakavarga[planet]?.binduMatrix ?: emptyMap()

            PlanetAshtakavargaCard(
                planet = planet,
                ashtakavarga = planetAshtakavarga,
                isExpanded = isExpanded,
                onToggleExpand = { onTogglePlanet(planet.symbol) }
            )
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun PlanetAshtakavargaCard(
    planet: Planet,
    ashtakavarga: Map<ZodiacSign, Int>,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(250),
        label = "planetRotation"
    )

    val language = LocalLanguage.current
    val planetColor = ChartDetailColors.getPlanetColor(planet)
    val totalBindus = ashtakavarga.values.sum()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = planetColor.copy(alpha = 0.3f))
            ) { onToggleExpand() },
        shape = RoundedCornerShape(16.dp),
        color = AppTheme.CardBackground
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize(animationSpec = tween(250))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(planetColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = planet.symbol,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = planet.getLocalizedName(language),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            text = "Total: $totalBindus bindus",
                            fontSize = 12.sp,
                            color = planetColor
                        )
                    }
                }

                // Strength indicator
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = getBinduColor(totalBindus / 12).copy(alpha = 0.15f)
                ) {
                    Text(
                        text = "${(totalBindus.toFloat() / 48 * 100).toInt()}%",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = getBinduColor(totalBindus / 12),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Icon(
                    Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = AppTheme.TextMuted,
                    modifier = Modifier
                        .size(24.dp)
                        .rotate(rotation)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(tween(250)) + fadeIn(tween(250)),
                exit = shrinkVertically(tween(200)) + fadeOut(tween(150))
            ) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor, modifier = Modifier.padding(bottom = 14.dp))

                    // Grid of bindus
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        ZodiacSign.entries.forEach { sign ->
                            val bindus = ashtakavarga[sign] ?: 0
                            val house = ZodiacSign.entries.indexOf(sign) + 1

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = sign.symbol,
                                    fontSize = 12.sp,
                                    color = AppTheme.TextMuted
                                )
                                Box(
                                    modifier = Modifier
                                        .size(32.dp)
                                        .background(
                                            getBinduColor(bindus).copy(alpha = 0.15f),
                                            RoundedCornerShape(6.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = bindus.toString(),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = getBinduColor(bindus)
                                    )
                                }
                                Text(
                                    text = "${house}H",
                                    fontSize = 9.sp,
                                    color = AppTheme.TextMuted
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
private fun AshtakavargaByHouseContent(
    ashtakavarga: AshtakavargaCalculator.AshtakavargaAnalysis
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(12) { index ->
            val house = index + 1
            val sign = ZodiacSign.entries[index]
            val totalBindus = ashtakavarga.sarvashtakavarga.getBindusForSign(sign)

            // Get individual planet contributions
            val planetContributions = listOf(
                Planet.SUN, Planet.MOON, Planet.MARS, Planet.MERCURY,
                Planet.JUPITER, Planet.VENUS, Planet.SATURN
            ).associateWith { planet ->
                ashtakavarga.bhinnashtakavarga[planet]?.getBindusForSign(sign) ?: 0
            }

            HouseDetailCard(
                house = house,
                sign = sign,
                totalBindus = totalBindus,
                planetContributions = planetContributions
            )
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun HouseDetailCard(
    house: Int,
    sign: ZodiacSign,
    totalBindus: Int,
    planetContributions: Map<Planet, Int>
) {
    val language = LocalLanguage.current

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = getBinduColor(totalBindus).copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${house}H",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = getBinduColor(totalBindus)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = sign.symbol,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = getBinduColor(totalBindus)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = sign.displayName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            text = getHouseSignification(house),
                            fontSize = 11.sp,
                            color = AppTheme.TextMuted,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = totalBindus.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = getBinduColor(totalBindus)
                    )
                    Text(
                        text = "bindus",
                        fontSize = 10.sp,
                        color = AppTheme.TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Planet contributions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                planetContributions.forEach { (planet, bindus) ->
                    val planetColor = ChartDetailColors.getPlanetColor(planet)

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .background(planetColor.copy(alpha = 0.2f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = planet.symbol,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = planetColor
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = bindus.toString(),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyAshtakavargaContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.BarChart,
                contentDescription = null,
                tint = AppTheme.TextMuted,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No Ashtakavarga Data",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please select a birth chart to view Ashtakavarga",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AshtakavargaInfoDialog(onDismiss: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "About Ashtakavarga",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
        },
        text = {
            Column {
                Text(
                    text = "Ashtakavarga is a unique Vedic system that calculates the strength of each house by analyzing beneficial points (bindus) contributed by seven planets.",
                    fontSize = 13.sp,
                    color = AppTheme.TextSecondary,
                    lineHeight = 19.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Interpretation Guide:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "• 30+ bindus: Very strong, excellent results\n" +
                            "• 25-30 bindus: Good strength, favorable\n" +
                            "• 20-25 bindus: Average, mixed results\n" +
                            "• Below 20: Weak, challenges expected",
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted,
                    lineHeight = 18.sp
                )
            }
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text("Got it", color = AppTheme.AccentPrimary)
            }
        },
        containerColor = AppTheme.CardBackground,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
private fun getBinduColor(bindus: Int): Color {
    return when {
        bindus >= 30 -> AppTheme.SuccessColor
        bindus >= 25 -> AppTheme.AccentGold
        bindus >= 20 -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }
}

@Composable
private fun getBinduStrengthColor(percent: Double): Color {
    return when {
        percent >= 75 -> AppTheme.SuccessColor
        percent >= 55 -> AppTheme.AccentTeal
        percent >= 40 -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }
}

private fun getBinduLabel(bindus: Int): String {
    return when {
        bindus >= 30 -> "Strong"
        bindus >= 25 -> "Good"
        bindus >= 20 -> "Average"
        else -> "Weak"
    }
}

private fun getStrengthLabel(percent: Double): String {
    return when {
        percent >= 75 -> "Excellent"
        percent >= 55 -> "Good"
        percent >= 40 -> "Average"
        else -> "Below Average"
    }
}

private fun getHouseSignification(house: Int): String {
    return when (house) {
        1 -> "Self, Body, Personality"
        2 -> "Wealth, Speech, Family"
        3 -> "Siblings, Courage"
        4 -> "Home, Mother, Property"
        5 -> "Children, Education"
        6 -> "Health, Enemies"
        7 -> "Marriage, Partnership"
        8 -> "Transformation"
        9 -> "Fortune, Father"
        10 -> "Career, Fame"
        11 -> "Gains, Wishes"
        12 -> "Losses, Liberation"
        else -> ""
    }
}
