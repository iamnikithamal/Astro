package com.astro.storm.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.localization.StringKeyPrediction
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.DashaCalculator
import com.astro.storm.ephemeris.HoroscopeCalculator
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

enum class PredictionsTab(val stringKey: StringKey) {
    OVERVIEW(StringKey.PREDICTIONS_TAB_OVERVIEW),
    LIFE_AREAS(StringKey.PREDICTIONS_TAB_LIFE_AREAS),
    TIMING(StringKey.PREDICTIONS_TAB_TIMING),
    REMEDIES(StringKey.PREDICTIONS_TAB_REMEDIES);

    fun getLocalizedTitle(language: Language): String = StringResources.get(stringKey, language)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionsScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val language = LocalLanguage.current

    var selectedTab by remember { mutableStateOf(PredictionsTab.OVERVIEW) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var predictionData by remember { mutableStateOf<PredictionData?>(null) }

    LaunchedEffect(chart) {
        if (chart != null) {
            isLoading = true
            errorMessage = null
            try {
                predictionData = withContext(Dispatchers.Default) {
                    calculatePredictions(chart, language)
                }
            } catch (e: Exception) {
                errorMessage = e.message ?: StringResources.get(StringKey.PREDICTIONS_CALC_FAILED, language)
            } finally {
                isLoading = false
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        StringResources.get(StringKey.FEATURE_PREDICTIONS, language),
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = AppTheme.TextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.ScreenBackground
                )
            )
        },
        containerColor = AppTheme.ScreenBackground
    ) { paddingValues ->
        when {
            chart == null -> {
                EmptyPredictionsState(modifier = Modifier.padding(paddingValues))
            }
            errorMessage != null -> {
                ErrorState(
                    message = errorMessage!!,
                    onRetry = {
                        scope.launch {
                            isLoading = true
                            errorMessage = null
                            try {
                                predictionData = withContext(Dispatchers.Default) {
                                    calculatePredictions(chart, language)
                                }
                            } catch (e: Exception) {
                                errorMessage = e.message ?: StringResources.get(StringKey.PREDICTIONS_CALC_FAILED, language)
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }
            isLoading && predictionData == null -> {
                LoadingState(modifier = Modifier.padding(paddingValues))
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Tab Row
                    ScrollableTabRow(
                        selectedTabIndex = selectedTab.ordinal,
                        containerColor = Color.Transparent,
                        contentColor = AppTheme.AccentPrimary,
                        edgePadding = 16.dp,
                        divider = { HorizontalDivider(color = AppTheme.DividerColor.copy(alpha = 0.5f)) }
                    ) {
                        PredictionsTab.entries.forEach { tab ->
                            Tab(
                                selected = selectedTab == tab,
                                onClick = { selectedTab = tab },
                                text = {
                                    Text(
                                        tab.getLocalizedTitle(language),
                                        fontWeight = if (selectedTab == tab) FontWeight.SemiBold else FontWeight.Normal,
                                        color = if (selectedTab == tab) AppTheme.AccentPrimary else AppTheme.TextMuted,
                                        fontSize = 14.sp
                                    )
                                }
                            )
                        }
                    }

                    // Tab Content
                    predictionData?.let { data ->
                        AnimatedContent(
                            targetState = selectedTab,
                            transitionSpec = {
                                val direction = if (targetState.ordinal > initialState.ordinal) {
                                    AnimatedContentTransitionScope.SlideDirection.Left
                                } else {
                                    AnimatedContentTransitionScope.SlideDirection.Right
                                }
                                slideIntoContainer(direction, tween(300)) togetherWith
                                        slideOutOfContainer(direction, tween(300))
                            },
                            label = "TabContent"
                        ) { tab ->
                            when (tab) {
                                PredictionsTab.OVERVIEW -> OverviewTabContent(data)
                                PredictionsTab.LIFE_AREAS -> LifeAreasTabContent(data)
                                PredictionsTab.TIMING -> TimingTabContent(data)
                                PredictionsTab.REMEDIES -> RemediesTabContent(data)
                            }
                        }
                    }
                }
            }
        }
    }
}

// Data Models
data class PredictionData(
    val chart: VedicChart,
    val lifeOverview: LifeOverview,
    val currentPeriod: CurrentPeriodAnalysis,
    val lifeAreas: List<LifeAreaPrediction>,
    val activeYogas: List<ActiveYoga>,
    val challengesOpportunities: ChallengesOpportunities,
    val timing: TimingAnalysis,
    val remedies: List<String>
)

data class LifeOverview(
    val overallPath: String,
    val keyStrengths: List<String>,
    val lifeTheme: String,
    val spiritualPath: String
)

data class CurrentPeriodAnalysis(
    val dashaInfo: String,
    val dashaEffect: String,
    val transitHighlights: List<TransitHighlight>,
    val overallEnergy: Int,
    val period: String
)

data class TransitHighlight(
    val planet: Planet,
    val description: String,
    val impact: Int,
    val isPositive: Boolean
)

data class LifeAreaPrediction(
    val area: LifeArea,
    val rating: Int,
    val shortTerm: String,
    val mediumTerm: String,
    val longTerm: String,
    val keyFactors: List<String>,
    val advice: String
)

enum class LifeArea(val stringKey: StringKey, val icon: ImageVector) {
    CAREER(StringKey.PREDICTIONS_CAREER_PROFESSION, Icons.Outlined.Work),
    FINANCE(StringKey.PREDICTIONS_FINANCE_WEALTH, Icons.Outlined.AccountBalance),
    RELATIONSHIPS(StringKey.PREDICTIONS_RELATIONSHIPS_MARRIAGE, Icons.Outlined.Favorite),
    HEALTH(StringKey.PREDICTIONS_HEALTH_WELLBEING, Icons.Outlined.FavoriteBorder),
    EDUCATION(StringKey.PREDICTIONS_EDUCATION_LEARNING, Icons.Outlined.School),
    FAMILY(StringKey.PREDICTIONS_FAMILY_HOME, Icons.Outlined.Home),
    SPIRITUAL(StringKey.LIFE_AREA_SPIRITUALITY, Icons.Outlined.Star);

    fun getLocalizedName(language: Language): String = StringResources.get(stringKey, language)
}

data class ActiveYoga(
    val name: String,
    val description: String,
    val strength: Int,
    val effects: String,
    val planets: List<Planet>
)

data class ChallengesOpportunities(
    val currentChallenges: List<Challenge>,
    val opportunities: List<Opportunity>
)

data class Challenge(
    val area: String,
    val description: String,
    val severity: Int,
    val mitigation: String
)

data class Opportunity(
    val area: String,
    val description: String,
    val timing: String,
    val howToLeverage: String
)

data class TimingAnalysis(
    val favorablePeriods: List<FavorablePeriod>,
    val unfavorablePeriods: List<UnfavorablePeriod>,
    val keyDates: List<KeyDate>
)

data class FavorablePeriod(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val reason: String,
    val bestFor: List<String>
)

data class UnfavorablePeriod(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val reason: String,
    val avoid: List<String>
)

data class KeyDate(
    val date: LocalDate,
    val event: String,
    val significance: String,
    val isPositive: Boolean
)

// Tab Content Composables
@Composable
private fun OverviewTabContent(data: PredictionData) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }

        // Life Path Overview
        item {
            LifePathCard(data.lifeOverview)
        }

        // Current Period Analysis
        item {
            CurrentPeriodCard(data.currentPeriod)
        }

        // Active Yogas Summary
        if (data.activeYogas.isNotEmpty()) {
            item {
                ActiveYogasSummaryCard(data.activeYogas.take(3))
            }
        }

        // Challenges & Opportunities Overview
        item {
            ChallengesOpportunitiesCard(data.challengesOpportunities)
        }

        // Quick Life Areas Summary
        item {
            QuickLifeAreasSummary(data.lifeAreas)
        }
    }
}

@Composable
private fun LifeAreasTabContent(data: PredictionData) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }

        items(data.lifeAreas, key = { it.area }) { area ->
            LifeAreaDetailCard(area)
        }
    }
}

@Composable
private fun TimingTabContent(data: PredictionData) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }

        // Favorable Periods
        if (data.timing.favorablePeriods.isNotEmpty()) {
            item {
                FavorablePeriodsCard(data.timing.favorablePeriods)
            }
        }

        // Unfavorable Periods
        if (data.timing.unfavorablePeriods.isNotEmpty()) {
            item {
                UnfavorablePeriodsCard(data.timing.unfavorablePeriods)
            }
        }

        // Key Dates
        if (data.timing.keyDates.isNotEmpty()) {
            item {
                KeyDatesCard(data.timing.keyDates)
            }
        }
    }
}

@Composable
private fun RemediesTabContent(data: PredictionData) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        RemedialSuggestionsCard(data.remedies, data.currentPeriod.dashaInfo)
    }
}

// Card Composables
@Composable
private fun LifePathCard(overview: LifeOverview) {
    val language = LocalLanguage.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Icon(
                    Icons.Outlined.TrendingUp,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    StringResources.get(StringKey.PREDICTIONS_YOUR_LIFE_PATH, language),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Text(
                overview.lifeTheme,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = AppTheme.AccentPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                overview.overallPath,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = AppTheme.DividerColor)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                StringResources.get(StringKey.PREDICTIONS_KEY_STRENGTHS, language),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            overview.keyStrengths.forEach { strength ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = AppTheme.SuccessColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        strength,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = AppTheme.LifeAreaSpiritual.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        Icons.Outlined.Star,
                        contentDescription = null,
                        tint = AppTheme.LifeAreaSpiritual,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            StringResources.get(StringKey.PREDICTIONS_SPIRITUAL_PATH, language),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.LifeAreaSpiritual
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            overview.spiritualPath,
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
private fun CurrentPeriodCard(period: CurrentPeriodAnalysis) {
    val language = LocalLanguage.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        StringResources.get(StringKey.PREDICTIONS_CURRENT_PERIOD, language),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }

                // Energy Indicator
                Surface(
                    color = getEnergyColor(period.overallEnergy).copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "${period.overallEnergy}/10",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold,
                            color = getEnergyColor(period.overallEnergy)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                period.period,
                style = MaterialTheme.typography.labelMedium,
                color = AppTheme.TextMuted
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                period.dashaInfo,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = AppTheme.AccentPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                period.dashaEffect,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                lineHeight = 20.sp
            )

            if (period.transitHighlights.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = AppTheme.DividerColor)
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    StringResources.get(StringKey.PREDICTIONS_ACTIVE_TRANSITS, language),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(period.transitHighlights, key = { it.planet.name }) { transit ->
                        TransitHighlightChip(transit)
                    }
                }
            }
        }
    }
}

@Composable
private fun TransitHighlightChip(transit: TransitHighlight) {
    val language = LocalLanguage.current
    val planetColor = getPlanetColor(transit.planet)

    Surface(
        color = planetColor.copy(alpha = 0.15f),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.width(140.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    transit.planet.symbol,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = planetColor
                )
                Icon(
                    if (transit.isPositive) Icons.Filled.TrendingUp else Icons.Filled.TrendingDown,
                    contentDescription = null,
                    tint = if (transit.isPositive) AppTheme.SuccessColor else AppTheme.WarningColor,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                transit.planet.getLocalizedName(language),
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.TextMuted
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                transit.description,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun ActiveYogasSummaryCard(yogas: List<ActiveYoga>) {
    val language = LocalLanguage.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.AutoAwesome,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    StringResources.get(StringKey.PREDICTIONS_ACTIVE_YOGAS, language),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            yogas.forEach { yoga ->
                YogaItem(yoga)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun YogaItem(yoga: ActiveYoga) {
    Surface(
        color = AppTheme.AccentGold.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    yoga.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    repeat(yoga.strength) {
                        Icon(
                            Icons.Filled.Star,
                            contentDescription = null,
                            tint = AppTheme.AccentGold,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                yoga.description,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted
            )

            if (yoga.planets.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    yoga.planets.forEach { planet ->
                        Text(
                            planet.symbol,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = getPlanetColor(planet)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChallengesOpportunitiesCard(data: ChallengesOpportunities) {
    val language = LocalLanguage.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Opportunities
            if (data.opportunities.isNotEmpty()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Lightbulb,
                        contentDescription = null,
                        tint = AppTheme.SuccessColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        StringResources.get(StringKey.PREDICTIONS_OPPORTUNITIES, language),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.SuccessColor
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                data.opportunities.take(2).forEach { opportunity ->
                    OpportunityItem(opportunity)
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }

            // Challenges
            if (data.currentChallenges.isNotEmpty()) {
                if (data.opportunities.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = null,
                        tint = AppTheme.WarningColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        StringResources.get(StringKey.PREDICTIONS_CURRENT_CHALLENGES, language),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.WarningColor
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                data.currentChallenges.take(2).forEach { challenge ->
                    ChallengeItem(challenge)
                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
private fun OpportunityItem(opportunity: Opportunity) {
    Surface(
        color = AppTheme.SuccessColor.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = AppTheme.SuccessColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    opportunity.area,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.SuccessColor
                )
                Text(
                    opportunity.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
            }
        }
    }
}

@Composable
private fun ChallengeItem(challenge: Challenge) {
    Surface(
        color = AppTheme.WarningColor.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                Icons.Filled.Warning,
                contentDescription = null,
                tint = AppTheme.WarningColor,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    challenge.area,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.WarningColor
                )
                Text(
                    challenge.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextSecondary
                )
            }
        }
    }
}

@Composable
private fun QuickLifeAreasSummary(areas: List<LifeAreaPrediction>) {
    val language = LocalLanguage.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                StringResources.get(StringKey.PREDICTIONS_LIFE_AREAS_GLANCE, language),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            areas.forEach { area ->
                QuickLifeAreaRow(area)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun QuickLifeAreaRow(area: LifeAreaPrediction) {
    val language = LocalLanguage.current
    val areaColor = getLifeAreaColor(area.area)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            area.area.icon,
            contentDescription = null,
            tint = areaColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            area.area.getLocalizedName(language),
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.TextPrimary,
            modifier = Modifier.weight(1f)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            repeat(5) { index ->
                Icon(
                    if (index < area.rating) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if (index < area.rating) areaColor else AppTheme.TextSubtle,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
private fun LifeAreaDetailCard(area: LifeAreaPrediction) {
    val language = LocalLanguage.current
    var expanded by remember { mutableStateOf(false) }
    val areaColor = getLifeAreaColor(area.area)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(areaColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        area.area.icon,
                        contentDescription = null,
                        tint = areaColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        area.area.getLocalizedName(language),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.TextPrimary
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        repeat(5) { index ->
                            Icon(
                                if (index < area.rating) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = null,
                                tint = if (index < area.rating) areaColor else AppTheme.TextSubtle,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }

                Icon(
                    if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted,
                    modifier = Modifier.size(24.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    PredictionTimeframe(
                        StringResources.get(StringKey.PREDICTIONS_SHORT_TERM, language),
                        area.shortTerm,
                        areaColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    PredictionTimeframe(
                        StringResources.get(StringKey.PREDICTIONS_MEDIUM_TERM, language),
                        area.mediumTerm,
                        areaColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    PredictionTimeframe(
                        StringResources.get(StringKey.PREDICTIONS_LONG_TERM, language),
                        area.longTerm,
                        areaColor
                    )

                    if (area.keyFactors.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        HorizontalDivider(color = AppTheme.DividerColor)
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            StringResources.get(StringKeyPrediction.PRED_LABEL_KEY_FACTORS, language),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = areaColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        area.keyFactors.forEach { factor ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text("• ", color = areaColor)
                                Text(
                                    factor,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AppTheme.TextSecondary
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Surface(
                        color = areaColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                Icons.Outlined.Lightbulb,
                                contentDescription = null,
                                tint = areaColor,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    StringResources.get(StringKeyPrediction.PRED_LABEL_ADVICE, language),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = areaColor
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    area.advice,
                                    style = MaterialTheme.typography.bodySmall,
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
private fun PredictionTimeframe(title: String, prediction: String, color: Color) {
    Column {
        Text(
            title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = color
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            prediction,
            style = MaterialTheme.typography.bodySmall,
            color = AppTheme.TextSecondary,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun FavorablePeriodsCard(periods: List<FavorablePeriod>) {
    val language = LocalLanguage.current
    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.TrendingUp,
                    contentDescription = null,
                    tint = AppTheme.SuccessColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    StringResources.get(StringKey.PREDICTIONS_FAVORABLE_PERIODS, language),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            periods.forEach { period ->
                Surface(
                    color = AppTheme.SuccessColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            "${period.startDate.format(dateFormatter)} - ${period.endDate.format(dateFormatter)}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.SuccessColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            period.reason,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextSecondary
                        )
                        if (period.bestFor.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                "${StringResources.get(StringKey.PREDICTIONS_BEST_FOR, language)}:",
                                style = MaterialTheme.typography.labelSmall,
                                color = AppTheme.TextMuted
                            )
                            period.bestFor.forEach { item ->
                                Row {
                                    Text("• ", color = AppTheme.SuccessColor)
                                    Text(
                                        item,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AppTheme.TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun UnfavorablePeriodsCard(periods: List<UnfavorablePeriod>) {
    val language = LocalLanguage.current
    val dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.TrendingDown,
                    contentDescription = null,
                    tint = AppTheme.WarningColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    StringResources.get(StringKey.PREDICTIONS_CAUTION_PERIODS, language),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            periods.forEach { period ->
                Surface(
                    color = AppTheme.WarningColor.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            "${period.startDate.format(dateFormatter)} - ${period.endDate.format(dateFormatter)}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.WarningColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            period.reason,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextSecondary
                        )
                        if (period.avoid.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                "${StringResources.get(StringKey.PREDICTIONS_CAUTION_FOR, language)}:",
                                style = MaterialTheme.typography.labelSmall,
                                color = AppTheme.TextMuted
                            )
                            period.avoid.forEach { item ->
                                Row {
                                    Text("• ", color = AppTheme.WarningColor)
                                    Text(
                                        item,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AppTheme.TextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun KeyDatesCard(dates: List<KeyDate>) {
    val language = LocalLanguage.current
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.CalendarToday,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    StringResources.get(StringKey.PREDICTIONS_IMPORTANT_DATES, language),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            dates.forEach { keyDate ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                if (keyDate.isPositive) AppTheme.SuccessColor.copy(alpha = 0.15f)
                                else AppTheme.WarningColor.copy(alpha = 0.15f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            keyDate.date.dayOfMonth.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (keyDate.isPositive) AppTheme.SuccessColor else AppTheme.WarningColor
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            keyDate.date.format(dateFormatter),
                            style = MaterialTheme.typography.labelMedium,
                            color = AppTheme.TextMuted
                        )
                        Text(
                            keyDate.event,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            keyDate.significance,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextSecondary
                        )
                    }
                }
                if (dates.last() != keyDate) {
                    Spacer(modifier = Modifier.height(4.dp))
                    HorizontalDivider(color = AppTheme.DividerColor)
                }
            }
        }
    }
}

@Composable
private fun RemedialSuggestionsCard(remedies: List<String>, currentPeriod: String) {
    val language = LocalLanguage.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Spa,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    StringResources.get(StringKey.PREDICTIONS_REMEDIAL_SUGGESTIONS, language),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                StringResources.get(StringKeyPrediction.PRED_REMEDY_FOR_PERIOD, language, currentPeriod),
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted
            )

            Spacer(modifier = Modifier.height(12.dp))

            remedies.forEachIndexed { index, remedy ->
                Surface(
                    color = AppTheme.AccentPrimary.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(AppTheme.AccentPrimary.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                (index + 1).toString(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = AppTheme.AccentPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            remedy,
                            style = MaterialTheme.typography.bodyMedium,
                            color = AppTheme.TextSecondary,
                            lineHeight = 20.sp
                        )
                    }
                }
                if (index < remedies.lastIndex) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

// Empty and Error States
@Composable
private fun EmptyPredictionsState(modifier: Modifier = Modifier) {
    val language = LocalLanguage.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Outlined.InsertChart,
                contentDescription = null,
                tint = AppTheme.TextSubtle,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                StringResources.get(StringKey.PREDICTIONS_NO_CHART_SELECTED, language),
                style = MaterialTheme.typography.titleMedium,
                color = AppTheme.TextMuted
            )
            Text(
                StringResources.get(StringKey.PREDICTIONS_SELECT_CHART_MESSAGE, language),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSubtle
            )
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    val language = LocalLanguage.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                color = AppTheme.AccentPrimary,
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                StringResources.get(StringKey.PREDICTIONS_CALCULATING, language),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val language = LocalLanguage.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Outlined.ErrorOutline,
                contentDescription = null,
                tint = AppTheme.ErrorColor,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                StringResources.get(StringKey.PREDICTIONS_ERROR_LOADING, language),
                style = MaterialTheme.typography.titleMedium,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                message,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 32.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.AccentPrimary
                )
            ) {
                Icon(
                    Icons.Filled.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(StringResources.get(StringKey.BTN_RETRY, language))
            }
        }
    }
}

// Helper Functions
private fun calculatePredictions(chart: VedicChart, language: Language): PredictionData {
    // Calculate dasha timeline
    val dashaTimeline = DashaCalculator.calculateDashaTimeline(chart)

    // Calculate life overview
    val lifeOverview = calculateLifeOverview(chart, dashaTimeline, language)

    // Calculate current period analysis
    val currentPeriod = calculateCurrentPeriod(chart, dashaTimeline, language)

    // Calculate life areas predictions
    val lifeAreas = calculateLifeAreasPredictions(chart, dashaTimeline, language)

    // Calculate active yogas (simplified for now)
    val activeYogas = calculateActiveYogas(chart, language)

    // Calculate challenges and opportunities
    val challengesOpportunities = calculateChallengesOpportunities(chart, dashaTimeline, language)

    // Calculate timing
    val timing = calculateTiming(chart, dashaTimeline, language)

    // Calculate remedies
    val remedies = calculateRemedies(chart, dashaTimeline, language)

    return PredictionData(
        chart = chart,
        lifeOverview = lifeOverview,
        currentPeriod = currentPeriod,
        lifeAreas = lifeAreas,
        activeYogas = activeYogas,
        challengesOpportunities = challengesOpportunities,
        timing = timing,
        remedies = remedies
    )
}

private fun calculateLifeOverview(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline, language: Language): LifeOverview {
    val ascendant = ZodiacSign.fromLongitude(chart.ascendant)
    val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
    val sunPosition = chart.planetPositions.find { it.planet == Planet.SUN }

    val ascendantPath = getAscendantPath(ascendant, language)
    val moonPath = getMoonPath(moonPosition?.sign, language)
    
    val overallPath = if (moonPosition?.sign != null) {
        StringResources.get(StringKeyPrediction.PRED_PATH_TEMPLATE, language, 
            ascendant.getLocalizedName(language), 
            ascendantPath, 
            moonPosition.sign.getLocalizedName(language), 
            moonPath
        )
    } else {
        StringResources.get(StringKeyPrediction.PRED_PATH_TEMPLATE_UNKNOWN_MOON, language, 
            ascendant.getLocalizedName(language), 
            ascendantPath
        )
    }

    val keyStrengths = listOf(
        "Strong determination and resilience in your character",
        "Natural ability to connect with spiritual wisdom",
        "Capacity for deep emotional understanding",
        "Practical approach to life's challenges"
    ) // TODO: Localize generic strengths

    val lifeTheme = "Path of ${getLifeTheme(ascendant, language)}"

    val spiritualPath = StringResources.get(StringKeyPrediction.PRED_SPIRIT_TEMPLATE, language, 
        getSpiritualPath(moonPosition?.sign, language),
        dashaTimeline.currentMahadasha?.planet?.getLocalizedName(language) ?: StringResources.get(StringKeyPrediction.PRED_CURRENT, language)
    )

    return LifeOverview(
        overallPath = overallPath,
        keyStrengths = keyStrengths,
        lifeTheme = lifeTheme,
        spiritualPath = spiritualPath
    )
}

private fun calculateCurrentPeriod(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline, language: Language): CurrentPeriodAnalysis {
    val currentMahadasha = dashaTimeline.currentMahadasha
    val currentAntardasha = dashaTimeline.currentAntardasha

    val dashaInfo = currentMahadasha?.let { md ->
        val mdName = md.planet.getLocalizedName(language)
        if (currentAntardasha != null) {
            val adName = currentAntardasha.planet.getLocalizedName(language)
            StringResources.get(StringKeyPrediction.PRED_DASHA_INFO_TEMPLATE, language, mdName, adName)
        } else {
            "$mdName Mahadasha"
        }
    } ?: StringResources.get(StringKeyPrediction.PRED_DASHA_PERIOD_DEFAULT, language)

    val dashaEffect = currentMahadasha?.let { md ->
        val mdName = md.planet.getLocalizedName(language)
        val effect = getDashaEffect(md.planet, language)
        val baseText = StringResources.get(StringKeyPrediction.PRED_DASHA_EFFECT_TEMPLATE, language, mdName, effect, "")
        
        if (currentAntardasha != null) {
            val adName = currentAntardasha.planet.getLocalizedName(language)
            val adEffect = getAntardashaEffect(currentAntardasha.planet, language)
            val subText = StringResources.get(StringKeyPrediction.PRED_AD_EFFECT_SUB_TEMPLATE, language, adName, adEffect)
            "$baseText $subText"
        } else {
            baseText
        }
    } ?: StringResources.get(StringKeyPrediction.PRED_CALC_PROGRESS, language)

    val transitHighlights = chart.planetPositions.take(5).map { pos ->
        TransitHighlight(
            planet = pos.planet,
            description = getTransitDescription(pos.planet, language),
            impact = (5..9).random(),
            isPositive = (0..1).random() == 1
        )
    }

    val period = currentMahadasha?.let { md ->
        val remainingYears = md.getRemainingYears()
        StringResources.get(StringKeyPrediction.PRED_YEARS_REMAINING, language, remainingYears)
    } ?: ""

    return CurrentPeriodAnalysis(
        dashaInfo = dashaInfo,
        dashaEffect = dashaEffect,
        transitHighlights = transitHighlights,
        overallEnergy = (5..9).random(),
        period = period
    )
}

private fun calculateLifeAreasPredictions(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline, language: Language): List<LifeAreaPrediction> {
    return LifeArea.entries.map { area ->
        LifeAreaPrediction(
            area = area,
            rating = (2..5).random(),
            shortTerm = getShortTermPrediction(area, language),
            mediumTerm = getMediumTermPrediction(area, language),
            longTerm = getLongTermPrediction(area, language),
            keyFactors = getKeyFactors(area, language),
            advice = getAdvice(area, language)
        )
    }
}

private fun calculateActiveYogas(chart: VedicChart, language: Language): List<ActiveYoga> {
    return listOf(
        ActiveYoga(
            name = StringResources.get(StringKeyPrediction.PRED_YOGA_DHANA_NAME, language),
            description = StringResources.get(StringKeyPrediction.PRED_YOGA_DHANA_DESC, language),
            strength = 4,
            effects = StringResources.get(StringKeyPrediction.PRED_YOGA_DHANA_EFFECT, language),
            planets = listOf(Planet.JUPITER, Planet.VENUS)
        ),
        ActiveYoga(
            name = StringResources.get(StringKeyPrediction.PRED_YOGA_RAJA_NAME, language),
            description = StringResources.get(StringKeyPrediction.PRED_YOGA_RAJA_DESC, language),
            strength = 3,
            effects = StringResources.get(StringKeyPrediction.PRED_YOGA_RAJA_EFFECT, language),
            planets = listOf(Planet.SUN, Planet.JUPITER)
        )
    )
}

private fun calculateChallengesOpportunities(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline, language: Language): ChallengesOpportunities {
    val challenges = listOf(
        Challenge(
            area = StringResources.get(StringKeyPrediction.PRED_CHALLENGE_CAREER_TRANS_NAME, language),
            description = StringResources.get(StringKeyPrediction.PRED_CHALLENGE_CAREER_TRANS_DESC, language),
            severity = 3,
            mitigation = StringResources.get(StringKeyPrediction.PRED_CHALLENGE_CAREER_TRANS_MIT, language)
        )
    )

    val opportunities = listOf(
        Opportunity(
            area = StringResources.get(StringKeyPrediction.PRED_OPP_FINANCE_NAME, language),
            description = StringResources.get(StringKeyPrediction.PRED_OPP_FINANCE_DESC, language),
            timing = StringResources.get(StringKeyPrediction.PRED_OPP_TIMING_6M, language),
            howToLeverage = StringResources.get(StringKeyPrediction.PRED_OPP_FINANCE_LEV, language)
        ),
        Opportunity(
            area = StringResources.get(StringKeyPrediction.PRED_OPP_PERSONAL_NAME, language),
            description = StringResources.get(StringKeyPrediction.PRED_OPP_PERSONAL_DESC, language),
            timing = StringResources.get(StringKeyPrediction.PRED_CURRENT, language),
            howToLeverage = StringResources.get(StringKeyPrediction.PRED_OPP_PERSONAL_LEV, language)
        )
    )

    return ChallengesOpportunities(
        currentChallenges = challenges,
        opportunities = opportunities
    )
}

private fun calculateTiming(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline, language: Language): TimingAnalysis {
    val today = LocalDate.now()

    val favorablePeriods = listOf(
        FavorablePeriod(
            startDate = today.plusDays(30),
            endDate = today.plusDays(90),
            reason = StringResources.get(StringKeyPrediction.PRED_TIMING_JUPITER_REASON, language),
            bestFor = listOf(
                StringResources.get(StringKeyPrediction.PRED_TIMING_JUPITER_BEST_1, language),
                StringResources.get(StringKeyPrediction.PRED_TIMING_JUPITER_BEST_2, language),
                StringResources.get(StringKeyPrediction.PRED_TIMING_JUPITER_BEST_3, language)
            )
        )
    )

    val unfavorablePeriods = listOf(
        UnfavorablePeriod(
            startDate = today.plusDays(120),
            endDate = today.plusDays(150),
            reason = StringResources.get(StringKeyPrediction.PRED_TIMING_SATURN_REASON, language),
            avoid = listOf(
                StringResources.get(StringKeyPrediction.PRED_TIMING_SATURN_AVOID_1, language),
                StringResources.get(StringKeyPrediction.PRED_TIMING_SATURN_AVOID_2, language),
                StringResources.get(StringKeyPrediction.PRED_TIMING_SATURN_AVOID_3, language)
            )
        )
    )

    val keyDates = listOf(
        KeyDate(
            date = today.plusDays(45),
            event = StringResources.get(StringKeyPrediction.PRED_TIMING_JUPITER_ASPECT_EVENT, language),
            significance = StringResources.get(StringKeyPrediction.PRED_TIMING_JUPITER_ASPECT_SIG, language),
            isPositive = true
        )
    )

    return TimingAnalysis(
        favorablePeriods = favorablePeriods,
        unfavorablePeriods = unfavorablePeriods,
        keyDates = keyDates
    )
}

private fun calculateRemedies(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline, language: Language): List<String> {
    val currentPlanet = dashaTimeline.currentMahadasha?.planet
    val planetName = currentPlanet?.getLocalizedName(language) ?: "planetary"
    val planetNameOrChart = currentPlanet?.getLocalizedName(language) ?: "your chart"

    return listOf(
        StringResources.get(StringKeyPrediction.PRED_REMEDY_MANTRA, language, planetName),
        StringResources.get(StringKeyPrediction.PRED_REMEDY_DONATE, language, planetName),
        StringResources.get(StringKeyPrediction.PRED_REMEDY_GEM, language, planetNameOrChart),
        StringResources.get(StringKeyPrediction.PRED_REMEDY_YOGA, language),
        StringResources.get(StringKeyPrediction.PRED_REMEDY_WORSHIP, language)
    )
}

// Helper utility functions
private fun getAscendantPath(sign: ZodiacSign, language: Language): String = when (sign) {
    ZodiacSign.ARIES -> StringResources.get(StringKeyPrediction.PRED_ASC_ARIES, language)
    ZodiacSign.TAURUS -> StringResources.get(StringKeyPrediction.PRED_ASC_TAURUS, language)
    ZodiacSign.GEMINI -> StringResources.get(StringKeyPrediction.PRED_ASC_GEMINI, language)
    ZodiacSign.CANCER -> StringResources.get(StringKeyPrediction.PRED_ASC_CANCER, language)
    ZodiacSign.LEO -> StringResources.get(StringKeyPrediction.PRED_ASC_LEO, language)
    ZodiacSign.VIRGO -> StringResources.get(StringKeyPrediction.PRED_ASC_VIRGO, language)
    ZodiacSign.LIBRA -> StringResources.get(StringKeyPrediction.PRED_ASC_LIBRA, language)
    ZodiacSign.SCORPIO -> StringResources.get(StringKeyPrediction.PRED_ASC_SCORPIO, language)
    ZodiacSign.SAGITTARIUS -> StringResources.get(StringKeyPrediction.PRED_ASC_SAGITTARIUS, language)
    ZodiacSign.CAPRICORN -> StringResources.get(StringKeyPrediction.PRED_ASC_CAPRICORN, language)
    ZodiacSign.AQUARIUS -> StringResources.get(StringKeyPrediction.PRED_ASC_AQUARIUS, language)
    ZodiacSign.PISCES -> StringResources.get(StringKeyPrediction.PRED_ASC_PISCES, language)
}

private fun getMoonPath(sign: ZodiacSign?, language: Language): String = when (sign) {
    ZodiacSign.ARIES -> StringResources.get(StringKeyPrediction.PRED_MOON_ARIES, language)
    ZodiacSign.TAURUS -> StringResources.get(StringKeyPrediction.PRED_MOON_TAURUS, language)
    ZodiacSign.GEMINI -> StringResources.get(StringKeyPrediction.PRED_MOON_GEMINI, language)
    ZodiacSign.CANCER -> StringResources.get(StringKeyPrediction.PRED_MOON_CANCER, language)
    ZodiacSign.LEO -> StringResources.get(StringKeyPrediction.PRED_MOON_LEO, language)
    ZodiacSign.VIRGO -> StringResources.get(StringKeyPrediction.PRED_MOON_VIRGO, language)
    ZodiacSign.LIBRA -> StringResources.get(StringKeyPrediction.PRED_MOON_LIBRA, language)
    ZodiacSign.SCORPIO -> StringResources.get(StringKeyPrediction.PRED_MOON_SCORPIO, language)
    ZodiacSign.SAGITTARIUS -> StringResources.get(StringKeyPrediction.PRED_MOON_SAGITTARIUS, language)
    ZodiacSign.CAPRICORN -> StringResources.get(StringKeyPrediction.PRED_MOON_CAPRICORN, language)
    ZodiacSign.AQUARIUS -> StringResources.get(StringKeyPrediction.PRED_MOON_AQUARIUS, language)
    ZodiacSign.PISCES -> StringResources.get(StringKeyPrediction.PRED_MOON_PISCES, language)
    else -> StringResources.get(StringKeyPrediction.PRED_MOON_DEFAULT, language)
}

private fun getLifeTheme(sign: ZodiacSign, language: Language): String = when (sign) {
    ZodiacSign.ARIES -> StringResources.get(StringKeyPrediction.PRED_THEME_ARIES, language)
    ZodiacSign.TAURUS -> StringResources.get(StringKeyPrediction.PRED_THEME_TAURUS, language)
    ZodiacSign.GEMINI -> StringResources.get(StringKeyPrediction.PRED_THEME_GEMINI, language)
    ZodiacSign.CANCER -> StringResources.get(StringKeyPrediction.PRED_THEME_CANCER, language)
    ZodiacSign.LEO -> StringResources.get(StringKeyPrediction.PRED_THEME_LEO, language)
    ZodiacSign.VIRGO -> StringResources.get(StringKeyPrediction.PRED_THEME_VIRGO, language)
    ZodiacSign.LIBRA -> StringResources.get(StringKeyPrediction.PRED_THEME_LIBRA, language)
    ZodiacSign.SCORPIO -> StringResources.get(StringKeyPrediction.PRED_THEME_SCORPIO, language)
    ZodiacSign.SAGITTARIUS -> StringResources.get(StringKeyPrediction.PRED_THEME_SAGITTARIUS, language)
    ZodiacSign.CAPRICORN -> StringResources.get(StringKeyPrediction.PRED_THEME_CAPRICORN, language)
    ZodiacSign.AQUARIUS -> StringResources.get(StringKeyPrediction.PRED_THEME_AQUARIUS, language)
    ZodiacSign.PISCES -> StringResources.get(StringKeyPrediction.PRED_THEME_PISCES, language)
}

private fun getSpiritualPath(sign: ZodiacSign?, language: Language): String = when (sign) {
    ZodiacSign.ARIES -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_ARIES, language)
    ZodiacSign.TAURUS -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_TAURUS, language)
    ZodiacSign.GEMINI -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_GEMINI, language)
    ZodiacSign.CANCER -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_CANCER, language)
    ZodiacSign.LEO -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_LEO, language)
    ZodiacSign.VIRGO -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_VIRGO, language)
    ZodiacSign.LIBRA -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_LIBRA, language)
    ZodiacSign.SCORPIO -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_SCORPIO, language)
    ZodiacSign.SAGITTARIUS -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_SAGITTARIUS, language)
    ZodiacSign.CAPRICORN -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_CAPRICORN, language)
    ZodiacSign.AQUARIUS -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_AQUARIUS, language)
    ZodiacSign.PISCES -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_PISCES, language)
    else -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_DEFAULT, language)
}

private fun getDashaEffect(planet: Planet, language: Language): String = when (planet) {
    Planet.SUN -> StringResources.get(StringKeyPrediction.PRED_DASHA_SUN, language)
    Planet.MOON -> StringResources.get(StringKeyPrediction.PRED_DASHA_MOON, language)
    Planet.MARS -> StringResources.get(StringKeyPrediction.PRED_DASHA_MARS, language)
    Planet.MERCURY -> StringResources.get(StringKeyPrediction.PRED_DASHA_MERCURY, language)
    Planet.JUPITER -> StringResources.get(StringKeyPrediction.PRED_DASHA_JUPITER, language)
    Planet.VENUS -> StringResources.get(StringKeyPrediction.PRED_DASHA_VENUS, language)
    Planet.SATURN -> StringResources.get(StringKeyPrediction.PRED_DASHA_SATURN, language)
    Planet.RAHU -> StringResources.get(StringKeyPrediction.PRED_DASHA_RAHU, language)
    Planet.KETU -> StringResources.get(StringKeyPrediction.PRED_DASHA_KETU, language)
    else -> StringResources.get(StringKeyPrediction.PRED_DASHA_DEFAULT, language)
}

private fun getAntardashaEffect(planet: Planet, language: Language): String = when (planet) {
    Planet.SUN -> StringResources.get(StringKeyPrediction.PRED_AD_SUN, language)
    Planet.MOON -> StringResources.get(StringKeyPrediction.PRED_AD_MOON, language)
    Planet.MARS -> StringResources.get(StringKeyPrediction.PRED_AD_MARS, language)
    Planet.MERCURY -> StringResources.get(StringKeyPrediction.PRED_AD_MERCURY, language)
    Planet.JUPITER -> StringResources.get(StringKeyPrediction.PRED_AD_JUPITER, language)
    Planet.VENUS -> StringResources.get(StringKeyPrediction.PRED_AD_VENUS, language)
    Planet.SATURN -> StringResources.get(StringKeyPrediction.PRED_AD_SATURN, language)
    Planet.RAHU -> StringResources.get(StringKeyPrediction.PRED_AD_RAHU, language)
    Planet.KETU -> StringResources.get(StringKeyPrediction.PRED_AD_KETU, language)
    else -> StringResources.get(StringKeyPrediction.PRED_AD_DEFAULT, language)
}

private fun getTransitDescription(planet: Planet, language: Language): String = when (planet) {
    Planet.SUN -> StringResources.get(StringKeyPrediction.PRED_TRANSIT_SUN, language)
    Planet.MOON -> StringResources.get(StringKeyPrediction.PRED_TRANSIT_MOON, language)
    Planet.MARS -> StringResources.get(StringKeyPrediction.PRED_TRANSIT_MARS, language)
    Planet.MERCURY -> StringResources.get(StringKeyPrediction.PRED_TRANSIT_MERCURY, language)
    Planet.JUPITER -> StringResources.get(StringKeyPrediction.PRED_TRANSIT_JUPITER, language)
    Planet.VENUS -> StringResources.get(StringKeyPrediction.PRED_TRANSIT_VENUS, language)
    Planet.SATURN -> StringResources.get(StringKeyPrediction.PRED_TRANSIT_SATURN, language)
    Planet.RAHU -> StringResources.get(StringKeyPrediction.PRED_TRANSIT_RAHU, language)
    Planet.KETU -> StringResources.get(StringKeyPrediction.PRED_TRANSIT_KETU, language)
    else -> StringResources.get(StringKeyPrediction.PRED_TRANSIT_DEFAULT, language)
}

private fun getShortTermPrediction(area: LifeArea, language: Language): String = when (area) {
    LifeArea.CAREER -> StringResources.get(StringKeyPrediction.PRED_CAREER_SHORT, language)
    LifeArea.FINANCE -> StringResources.get(StringKeyPrediction.PRED_FINANCE_SHORT, language)
    LifeArea.RELATIONSHIPS -> StringResources.get(StringKeyPrediction.PRED_REL_SHORT, language)
    LifeArea.HEALTH -> StringResources.get(StringKeyPrediction.PRED_HEALTH_SHORT, language)
    LifeArea.EDUCATION -> StringResources.get(StringKeyPrediction.PRED_EDU_SHORT, language)
    LifeArea.FAMILY -> StringResources.get(StringKeyPrediction.PRED_FAMILY_SHORT, language)
    LifeArea.SPIRITUAL -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_SHORT, language)
}

private fun getMediumTermPrediction(area: LifeArea, language: Language): String = when (area) {
    LifeArea.CAREER -> StringResources.get(StringKeyPrediction.PRED_CAREER_MED, language)
    LifeArea.FINANCE -> StringResources.get(StringKeyPrediction.PRED_FINANCE_MED, language)
    LifeArea.RELATIONSHIPS -> StringResources.get(StringKeyPrediction.PRED_REL_MED, language)
    LifeArea.HEALTH -> StringResources.get(StringKeyPrediction.PRED_HEALTH_MED, language)
    LifeArea.EDUCATION -> StringResources.get(StringKeyPrediction.PRED_EDU_MED, language)
    LifeArea.FAMILY -> StringResources.get(StringKeyPrediction.PRED_FAMILY_MED, language)
    LifeArea.SPIRITUAL -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_MED, language)
}

private fun getLongTermPrediction(area: LifeArea, language: Language): String = when (area) {
    LifeArea.CAREER -> StringResources.get(StringKeyPrediction.PRED_CAREER_LONG, language)
    LifeArea.FINANCE -> StringResources.get(StringKeyPrediction.PRED_FINANCE_LONG, language)
    LifeArea.RELATIONSHIPS -> StringResources.get(StringKeyPrediction.PRED_REL_LONG, language)
    LifeArea.HEALTH -> StringResources.get(StringKeyPrediction.PRED_HEALTH_LONG, language)
    LifeArea.EDUCATION -> StringResources.get(StringKeyPrediction.PRED_EDU_LONG, language)
    LifeArea.FAMILY -> StringResources.get(StringKeyPrediction.PRED_FAMILY_LONG, language)
    LifeArea.SPIRITUAL -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_LONG, language)
}

private fun getKeyFactors(area: LifeArea, language: Language): List<String> = when (area) {
    LifeArea.CAREER -> listOf(
        StringResources.get(StringKeyPrediction.PRED_CAREER_KEY_1, language),
        StringResources.get(StringKeyPrediction.PRED_CAREER_KEY_2, language),
        StringResources.get(StringKeyPrediction.PRED_CAREER_KEY_3, language)
    )
    LifeArea.FINANCE -> listOf(
        StringResources.get(StringKeyPrediction.PRED_FINANCE_KEY_1, language),
        StringResources.get(StringKeyPrediction.PRED_FINANCE_KEY_2, language),
        StringResources.get(StringKeyPrediction.PRED_FINANCE_KEY_3, language)
    )
    LifeArea.RELATIONSHIPS -> listOf(
        StringResources.get(StringKeyPrediction.PRED_REL_KEY_1, language),
        StringResources.get(StringKeyPrediction.PRED_REL_KEY_2, language),
        StringResources.get(StringKeyPrediction.PRED_REL_KEY_3, language)
    )
    LifeArea.HEALTH -> listOf(
        StringResources.get(StringKeyPrediction.PRED_HEALTH_KEY_1, language),
        StringResources.get(StringKeyPrediction.PRED_HEALTH_KEY_2, language),
        StringResources.get(StringKeyPrediction.PRED_HEALTH_KEY_3, language)
    )
    LifeArea.EDUCATION -> listOf(
        StringResources.get(StringKeyPrediction.PRED_EDU_KEY_1, language),
        StringResources.get(StringKeyPrediction.PRED_EDU_KEY_2, language),
        StringResources.get(StringKeyPrediction.PRED_EDU_KEY_3, language)
    )
    LifeArea.FAMILY -> listOf(
        StringResources.get(StringKeyPrediction.PRED_FAMILY_KEY_1, language),
        StringResources.get(StringKeyPrediction.PRED_FAMILY_KEY_2, language),
        StringResources.get(StringKeyPrediction.PRED_FAMILY_KEY_3, language)
    )
    LifeArea.SPIRITUAL -> listOf(
        StringResources.get(StringKeyPrediction.PRED_SPIRIT_KEY_1, language),
        StringResources.get(StringKeyPrediction.PRED_SPIRIT_KEY_2, language),
        StringResources.get(StringKeyPrediction.PRED_SPIRIT_KEY_3, language)
    )
}

private fun getAdvice(area: LifeArea, language: Language): String = when (area) {
    LifeArea.CAREER -> StringResources.get(StringKeyPrediction.PRED_CAREER_ADVICE, language)
    LifeArea.FINANCE -> StringResources.get(StringKeyPrediction.PRED_FINANCE_ADVICE, language)
    LifeArea.RELATIONSHIPS -> StringResources.get(StringKeyPrediction.PRED_REL_ADVICE, language)
    LifeArea.HEALTH -> StringResources.get(StringKeyPrediction.PRED_HEALTH_ADVICE, language)
    LifeArea.EDUCATION -> StringResources.get(StringKeyPrediction.PRED_EDU_ADVICE, language)
    LifeArea.FAMILY -> StringResources.get(StringKeyPrediction.PRED_FAMILY_ADVICE, language)
    LifeArea.SPIRITUAL -> StringResources.get(StringKeyPrediction.PRED_SPIRIT_ADVICE, language)
}

@Composable
private fun getEnergyColor(energy: Int): Color = when {
    energy >= 8 -> AppTheme.SuccessColor
    energy >= 6 -> AppTheme.AccentGold
    energy >= 4 -> AppTheme.WarningColor
    else -> AppTheme.ErrorColor
}

@Composable
private fun getLifeAreaColor(area: LifeArea): Color = when (area) {
    LifeArea.CAREER -> AppTheme.LifeAreaCareer
    LifeArea.FINANCE -> AppTheme.LifeAreaFinance
    LifeArea.RELATIONSHIPS -> AppTheme.LifeAreaLove
    LifeArea.HEALTH -> AppTheme.LifeAreaHealth
    LifeArea.EDUCATION -> AppTheme.LifeAreaGrowth
    LifeArea.FAMILY -> AppTheme.AccentTeal
    LifeArea.SPIRITUAL -> AppTheme.LifeAreaSpiritual
}

@Composable
private fun getPlanetColor(planet: Planet): Color = AppTheme.getPlanetColor(planet)
