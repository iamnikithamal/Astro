package com.astro.storm.ui.screen.transits

import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Sync
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
import androidx.compose.ui.platform.LocalContext
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
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.TransitAnalyzer
import com.astro.storm.ui.components.common.ModernPillTabRow
import com.astro.storm.ui.components.common.TabItem
import com.astro.storm.ui.screen.chartdetail.ChartDetailColors
import com.astro.storm.ui.theme.AppTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Redesigned Transits Screen
 *
 * A modern, clean UI for displaying planetary transits with:
 * - Current transit positions overview
 * - Tab navigation for transit types
 * - House-by-house transit analysis
 * - Upcoming significant transits
 * - Transit strength indicators
 * - Smooth animations throughout
 */

enum class TransitViewType(val titleKey: StringKey) {
    CURRENT(StringKey.TAB_CURRENT_POSITIONS),
    BY_HOUSE(StringKey.TAB_BY_HOUSE),
    UPCOMING(StringKey.TAB_UPCOMING),
    ASPECTS(StringKey.TAB_ASPECTS)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransitsScreenRedesigned(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val language = LocalLanguage.current
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    var expandedPlanets by rememberSaveable { mutableStateOf(setOf<String>()) }

    // Calculate transit data
    val transitAnalysis = remember(chart) {
        chart?.let {
            try {
                val analyzer = TransitAnalyzer(context)
                analyzer.analyzeTransits(it, LocalDateTime.now())
            } catch (e: Exception) {
                null
            }
        }
    }

    val currentPositions = remember(transitAnalysis) {
        transitAnalysis?.transitPositions ?: emptyList()
    }

    // Read colors outside remember
    val accentPrimary = AppTheme.AccentPrimary
    val accentTeal = AppTheme.AccentTeal
    val accentGold = AppTheme.AccentGold
    val lifeAreaSpiritual = AppTheme.LifeAreaSpiritual

    val tabs = remember(accentPrimary, accentTeal, accentGold, lifeAreaSpiritual, language) {
        TransitViewType.entries.map { type ->
            TabItem(
                title = stringResource(type.titleKey, language),
                accentColor = when (type) {
                    TransitViewType.CURRENT -> accentPrimary
                    TransitViewType.BY_HOUSE -> accentTeal
                    TransitViewType.UPCOMING -> accentGold
                    TransitViewType.ASPECTS -> lifeAreaSpiritual
                }
            )
        }
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            TransitsTopBar(
                chartName = chart?.birthData?.name ?: "",
                onBack = onBack
            )
        }
    ) { paddingValues ->
        if (chart == null || transitAnalysis == null) {
            EmptyTransitsContent(modifier = Modifier.padding(paddingValues))
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
                    label = "TransitTabContent"
                ) { tabIndex ->
                    when (TransitViewType.entries[tabIndex]) {
                        TransitViewType.CURRENT -> {
                            CurrentTransitsContent(
                                analysis = transitAnalysis,
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
                        TransitViewType.BY_HOUSE -> {
                            TransitsByHouseContent(analysis = transitAnalysis)
                        }
                        TransitViewType.UPCOMING -> {
                            UpcomingTransitsContent(analysis = transitAnalysis)
                        }
                        TransitViewType.ASPECTS -> {
                            TransitAspectsContent(analysis = transitAnalysis)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransitsTopBar(
    chartName: String,
    onBack: () -> Unit
) {
    Surface(
        color = AppTheme.ScreenBackground,
        shadowElevation = 2.dp
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = stringResource(StringKey.FEATURE_TRANSITS),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    if (chartName.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = stringResource(StringKey.TRANSIT_CURRENT_MOVEMENTS, chartName),
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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = AppTheme.ScreenBackground
            )
        )
    }
}

@Composable
private fun CurrentTransitsContent(
    analysis: TransitAnalyzer.TransitAnalysis,
    expandedPlanets: Set<String>,
    onTogglePlanet: (String) -> Unit
) {
    val language = LocalLanguage.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Summary card
        item(key = "summary") {
            TransitSummaryCard(analysis = analysis)
        }

        // Current positions header
        item(key = "positions_header") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(StringKey.TRANSIT_PLANET_POSITIONS),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Text(
                    text = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")),
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted
                )
            }
        }

        // Planet transit cards
        items(
            items = analysis.transitPositions,
            key = { "transit_${it.planet.symbol}" }
        ) { transitPosition ->
            val isExpanded = transitPosition.planet.symbol in expandedPlanets

            TransitPlanetCard(
                transitPosition = transitPosition,
                natalPosition = analysis.natalChart.planetPositions.find { it.planet == transitPosition.planet },
                isExpanded = isExpanded,
                onToggleExpand = { onTogglePlanet(transitPosition.planet.symbol) }
            )
        }

        // Bottom spacer
        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun TransitSummaryCard(
    analysis: TransitAnalyzer.TransitAnalysis
) {
    val majorTransits = analysis.significantPeriods.filter { it.intensity >= 4 }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = AppTheme.AccentPrimary.copy(alpha = 0.1f),
                spotColor = AppTheme.AccentPrimary.copy(alpha = 0.1f)
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
                                    AppTheme.AccentPrimary.copy(alpha = 0.2f),
                                    AppTheme.AccentPrimary.copy(alpha = 0.1f)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Sync,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = stringResource(StringKey.TRANSIT_OVERVIEW),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary,
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = stringResource(StringKey.TRANSIT_CURRENT_INFLUENCES),
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
                TransitStatItem(
                    value = "${analysis.transitPositions.size}",
                    label = stringResource(StringKey.TRANSIT_PLANETS_COUNT),
                    color = AppTheme.AccentPrimary,
                    modifier = Modifier.weight(1f)
                )
                TransitStatItem(
                    value = "${majorTransits.size}",
                    label = stringResource(StringKey.TRANSIT_MAJOR_TRANSITS),
                    color = AppTheme.AccentGold,
                    modifier = Modifier.weight(1f)
                )
                TransitStatItem(
                    value = "${analysis.overallAssessment.score.toInt()}%",
                    label = stringResource(StringKey.TRANSIT_QUALITY_LABEL),
                    color = getStrengthColor(analysis.overallAssessment.score),
                    modifier = Modifier.weight(1f)
                )
            }

            // Overall assessment
            if (analysis.overallAssessment.summary.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    color = AppTheme.CardBackgroundElevated
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Outlined.TrendingUp,
                                contentDescription = null,
                                tint = AppTheme.AccentGold,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(StringKey.TRANSIT_OVERALL_ASSESSMENT),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.AccentGold
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = analysis.overallAssessment.summary,
                            fontSize = 13.sp,
                            color = AppTheme.TextPrimary,
                            lineHeight = 19.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TransitStatItem(
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
private fun TransitPlanetCard(
    transitPosition: PlanetPosition,
    natalPosition: com.astro.storm.data.model.PlanetPosition?,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(250),
        label = "planetRotation"
    )

    val language = LocalLanguage.current
    val planetColor = ChartDetailColors.getPlanetColor(transitPosition.planet)

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
                    // Planet icon
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(planetColor, CircleShape)
                            .then(
                                if (transitPosition.isRetrograde) {
                                    Modifier.border(2.dp, AppTheme.WarningColor, CircleShape)
                                } else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = transitPosition.planet.symbol,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = transitPosition.planet.getLocalizedName(language),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                            if (transitPosition.isRetrograde) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = AppTheme.WarningColor.copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = stringResource(StringKey.TRANSIT_RETROGRADE_SYMBOL),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = AppTheme.WarningColor,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = transitPosition.sign.displayName,
                                fontSize = 13.sp,
                                color = planetColor,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = " ${transitPosition.formattedDegree}",
                                fontSize = 12.sp,
                                color = AppTheme.TextMuted
                            )
                        }
                    }
                }

                // House badge
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = AppTheme.CardBackgroundElevated
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${transitPosition.houseTransit}H",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = planetColor
                        )
                        Text(
                            text = stringResource(StringKey.TRANSIT_HOUSE_LABEL),
                            fontSize = 9.sp,
                            color = AppTheme.TextMuted
                        )
                    }
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

                    // Transit details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Transit position
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(10.dp),
                            color = AppTheme.CardBackgroundElevated
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = stringResource(StringKey.TRANSIT_LABEL),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = AppTheme.TextMuted,
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${transitPosition.sign.symbol} ${transitPosition.formattedDegree}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = planetColor
                                )
                                Text(
                                    text = transitPosition.nakshatra.getLocalizedName(language),
                                    fontSize = 11.sp,
                                    color = AppTheme.TextMuted
                                )
                            }
                        }

                        // Natal position
                        if (natalPosition != null) {
                            Surface(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                color = AppTheme.CardBackgroundElevated
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = stringResource(StringKey.TRANSIT_NATAL_LABEL),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = AppTheme.TextMuted,
                                        letterSpacing = 0.5.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${natalPosition.sign.symbol} ${natalPosition.formattedDegree}",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = AppTheme.TextSecondary
                                    )
                                    Text(
                                        text = natalPosition.nakshatra.getLocalizedName(language),
                                        fontSize = 11.sp,
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
}

@Composable
private fun TransitsByHouseContent(
    analysis: TransitAnalyzer.TransitAnalysis
) {
    val language = LocalLanguage.current

    // Group transits by house
    val transitsByHouse = remember(analysis) {
        analysis.transitPositions.groupBy { it.houseTransit }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(12) { index ->
            val house = index + 1
            val transits = transitsByHouse[house] ?: emptyList()

            HouseTransitCard(
                house = house,
                transits = transits,
                houseSignification = getHouseSignification(house)
            )
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun HouseTransitCard(
    house: Int,
    transits: List<PlanetPosition>,
    houseSignification: String
) {
    val language = LocalLanguage.current
    val hasTransits = transits.isNotEmpty()

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = if (hasTransits) AppTheme.CardBackground else AppTheme.CardBackground.copy(alpha = 0.6f)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // House number
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if (hasTransits) AppTheme.AccentTeal.copy(alpha = 0.15f)
                        else AppTheme.DividerColor,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${house}H",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (hasTransits) AppTheme.AccentTeal else AppTheme.TextMuted
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = houseSignification,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
                if (transits.isEmpty()) {
                    Text(
                        text = stringResource(StringKey.TRANSIT_NO_PLANETS_TRANSITING),
                        fontSize = 11.sp,
                        color = AppTheme.TextMuted
                    )
                }
            }

            // Planet badges
            if (hasTransits) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    transits.forEach { transit ->
                        val planetColor = ChartDetailColors.getPlanetColor(transit.planet)
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(planetColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = transit.planet.symbol,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UpcomingTransitsContent(
    analysis: TransitAnalyzer.TransitAnalysis
) {
    val language = LocalLanguage.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Significant transits header
        item(key = "significant_header") {
            Text(
                text = stringResource(StringKey.TRANSIT_UPCOMING),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
        }

        // Filter to upcoming and significant
        val upcomingTransits = analysis.significantPeriods
            .filter { it.startDate.isAfter(LocalDateTime.now()) }
            .sortedBy { it.startDate }
            .take(15)

        if (upcomingTransits.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(StringKey.TRANSIT_NO_UPCOMING),
                        fontSize = 14.sp,
                        color = AppTheme.TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            items(
                items = upcomingTransits,
                key = { period -> "upcoming_${period.planets.joinToString("_") { it.symbol }}_${period.startDate}" }
            ) { period ->
                UpcomingTransitCard(period = period)
            }
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun UpcomingTransitCard(
    period: TransitAnalyzer.SignificantPeriod
) {
    val language = LocalLanguage.current
    val primaryPlanet = period.planets.firstOrNull() ?: Planet.SUN
    val planetColor = ChartDetailColors.getPlanetColor(primaryPlanet)
    val daysUntil = java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), period.startDate.toLocalDate()).toInt()

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = AppTheme.CardBackground
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Planet icon(s)
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(planetColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = period.planets.joinToString("") { it.symbol }.take(2),
                    fontSize = if (period.planets.size > 1) 12.sp else 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = period.description,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = period.planets.joinToString(", ") { it.getLocalizedName(language) },
                    fontSize = 12.sp,
                    color = planetColor
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = period.startDate.format(DateTimeFormatter.ofPattern("MMM dd")),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Text(
                    text = if (daysUntil == 0) "Today" else if (daysUntil == 1) "Tomorrow" else "In $daysUntil days",
                    fontSize = 11.sp,
                    color = AppTheme.TextMuted
                )
            }
        }
    }
}

@Composable
private fun TransitAspectsContent(
    analysis: TransitAnalyzer.TransitAnalysis
) {
    val language = LocalLanguage.current

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item(key = "aspects_header") {
            Text(
                text = stringResource(StringKey.TRANSIT_TO_NATAL_ASPECTS),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
        }

        if (analysis.transitAspects.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(StringKey.TRANSIT_NO_ASPECTS),
                        fontSize = 14.sp,
                        color = AppTheme.TextMuted,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            items(
                items = analysis.transitAspects,
                key = { "aspect_${it.transitingPlanet.symbol}_${it.natalPlanet.symbol}_${it.aspectType}" }
            ) { aspect ->
                TransitAspectCard(aspect = aspect)
            }
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun TransitAspectCard(
    aspect: TransitAnalyzer.TransitAspect
) {
    val language = LocalLanguage.current
    val transitColor = ChartDetailColors.getPlanetColor(aspect.transitingPlanet)
    val natalColor = ChartDetailColors.getPlanetColor(aspect.natalPlanet)
    val isHarmonious = aspect.aspectType in listOf("Trine", "Sextile", "Conjunction")

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Transit planet
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(transitColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = aspect.transitingPlanet.symbol,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Aspect badge
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isHarmonious) AppTheme.SuccessColor.copy(alpha = 0.15f)
                    else AppTheme.WarningColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = aspect.aspectType,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isHarmonious) AppTheme.SuccessColor else AppTheme.WarningColor,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Natal planet
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .background(natalColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = aspect.natalPlanet.symbol,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Orb
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = AppTheme.CardBackgroundElevated
                ) {
                    Text(
                        text = "${aspect.orb.toInt()}° orb",
                        fontSize = 11.sp,
                        color = AppTheme.TextMuted,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "${aspect.transitingPlanet.getLocalizedName(language)} transit ${aspect.aspectType.lowercase()} natal ${aspect.natalPlanet.getLocalizedName(language)}",
                fontSize = 13.sp,
                color = AppTheme.TextPrimary
            )

            if (aspect.interpretation.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = aspect.interpretation,
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted,
                    lineHeight = 17.sp
                )
            }
        }
    }
}

@Composable
private fun EmptyTransitsContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Sync,
                contentDescription = null,
                tint = AppTheme.TextMuted,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(StringKey.TRANSIT_NO_DATA),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(StringKey.TRANSIT_SELECT_CHART),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun getHouseSignification(house: Int): String {
    return when (house) {
        1 -> "Self, Body, Personality"
        2 -> "Wealth, Speech, Family"
        3 -> "Siblings, Courage, Communication"
        4 -> "Home, Mother, Property"
        5 -> "Children, Creativity, Education"
        6 -> "Health, Enemies, Service"
        7 -> "Marriage, Partnerships, Business"
        8 -> "Transformation, Inheritance"
        9 -> "Fortune, Higher Learning, Father"
        10 -> "Career, Fame, Authority"
        11 -> "Gains, Friends, Wishes"
        12 -> "Losses, Spirituality, Liberation"
        else -> ""
    }
}

@Composable
private fun getStrengthColor(strength: Double): Color {
    return when {
        strength >= 70 -> AppTheme.SuccessColor
        strength >= 50 -> AppTheme.AccentTeal
        strength >= 30 -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }
}