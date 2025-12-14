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
                    calculatePredictions(chart)
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
                                    calculatePredictions(chart)
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
    SPIRITUAL(StringKey.LIFE_AREA_SPIRITUAL, Icons.Outlined.Star);

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
                        Icons.Outlined.LightbulbOutline,
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
                            "Key Factors",
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
                                    "Advice",
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
                "For your current $currentPeriod period:",
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
private suspend fun calculatePredictions(chart: VedicChart): PredictionData {
    // Calculate Dasha Timeline
    val dashaTimeline = DashaCalculator.calculateDashaTimeline(chart)

    // Get current dasha information
    val currentDashaInfo = buildString {
        dashaTimeline.currentMahadasha?.let { md ->
            append("${md.planet.displayName} Mahadasha")
            dashaTimeline.currentAntardasha?.let { ad ->
                append(" - ${ad.planet.displayName} Antardasha")
            }
        }
    }

    // Calculate life overview
    val lifeOverview = calculateLifeOverview(chart, dashaTimeline)

    // Calculate current period analysis
    val currentPeriod = calculateCurrentPeriod(chart, dashaTimeline)

    // Calculate life areas predictions
    val lifeAreas = calculateLifeAreasPredictions(chart, dashaTimeline)

    // Calculate active yogas (simplified for now)
    val activeYogas = calculateActiveYogas(chart)

    // Calculate challenges and opportunities
    val challengesOpportunities = calculateChallengesOpportunities(chart, dashaTimeline)

    // Calculate timing
    val timing = calculateTiming(chart, dashaTimeline)

    // Calculate remedies
    val remedies = calculateRemedies(chart, dashaTimeline)

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

private fun calculateLifeOverview(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline): LifeOverview {
    val ascendant = ZodiacSign.fromLongitude(chart.ascendant)
    val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
    val sunPosition = chart.planetPositions.find { it.planet == Planet.SUN }

    val overallPath = "Your life path is shaped by your ${ascendant.displayName} ascendant, indicating a personality focused on ${getAscendantPath(ascendant)}. " +
            "With your Moon in ${moonPosition?.sign?.displayName ?: "unknown"}, your emotional nature seeks ${getMoonPath(moonPosition?.sign)}."

    val keyStrengths = listOf(
        "Strong determination and resilience in your character",
        "Natural ability to connect with spiritual wisdom",
        "Capacity for deep emotional understanding",
        "Practical approach to life's challenges"
    )

    val lifeTheme = "Path of ${getLifeTheme(ascendant)}"

    val spiritualPath = "Your spiritual journey emphasizes ${getSpiritualPath(moonPosition?.sign)} " +
            "through ${dashaTimeline.currentMahadasha?.planet?.displayName ?: "current"} influences."

    return LifeOverview(
        overallPath = overallPath,
        keyStrengths = keyStrengths,
        lifeTheme = lifeTheme,
        spiritualPath = spiritualPath
    )
}

private fun calculateCurrentPeriod(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline): CurrentPeriodAnalysis {
    val currentMahadasha = dashaTimeline.currentMahadasha
    val currentAntardasha = dashaTimeline.currentAntardasha

    val dashaInfo = buildString {
        currentMahadasha?.let { md ->
            append("${md.planet.displayName} Mahadasha")
            currentAntardasha?.let { ad ->
                append(" - ${ad.planet.displayName} Antardasha")
            }
        } ?: append("Dasha Period")
    }

    val dashaEffect = currentMahadasha?.let { md ->
        "The ${md.planet.displayName} period brings focus on ${getDashaEffect(md.planet)}. " +
                "${if (currentAntardasha != null) "The ${currentAntardasha.planet.displayName} sub-period adds ${getAntardashaEffect(currentAntardasha.planet)}." else ""}"
    } ?: "Current period influences are being calculated."

    val transitHighlights = chart.planetPositions.take(5).map { pos ->
        TransitHighlight(
            planet = pos.planet,
            description = getTransitDescription(pos.planet),
            impact = (5..9).random(),
            isPositive = (0..1).random() == 1
        )
    }

    val period = buildString {
        currentMahadasha?.let { md ->
            val remainingYears = md.getRemainingYears()
            append("${String.format("%.1f", remainingYears)} years remaining")
        }
    }

    return CurrentPeriodAnalysis(
        dashaInfo = dashaInfo,
        dashaEffect = dashaEffect,
        transitHighlights = transitHighlights,
        overallEnergy = (5..9).random(),
        period = period
    )
}

private fun calculateLifeAreasPredictions(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline): List<LifeAreaPrediction> {
    return LifeArea.entries.map { area ->
        LifeAreaPrediction(
            area = area,
            rating = (2..5).random(),
            shortTerm = getShortTermPrediction(area),
            mediumTerm = getMediumTermPrediction(area),
            longTerm = getLongTermPrediction(area),
            keyFactors = getKeyFactors(area),
            advice = getAdvice(area)
        )
    }
}

private fun calculateActiveYogas(chart: VedicChart): List<ActiveYoga> {
    return listOf(
        ActiveYoga(
            name = "Dhana Yoga",
            description = "Wealth-creating planetary combination",
            strength = 4,
            effects = "Favorable for financial growth and material prosperity",
            planets = listOf(Planet.JUPITER, Planet.VENUS)
        ),
        ActiveYoga(
            name = "Raja Yoga",
            description = "Royal combination indicating success",
            strength = 3,
            effects = "Support for leadership and achievement",
            planets = listOf(Planet.SUN, Planet.JUPITER)
        )
    )
}

private fun calculateChallengesOpportunities(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline): ChallengesOpportunities {
    val challenges = listOf(
        Challenge(
            area = "Career Transitions",
            description = "Period of professional reassessment and potential changes",
            severity = 3,
            mitigation = "Focus on skill development and networking"
        )
    )

    val opportunities = listOf(
        Opportunity(
            area = "Financial Growth",
            description = "Favorable period for investments and wealth accumulation",
            timing = "Next 6 months",
            howToLeverage = "Explore new income sources and strategic investments"
        ),
        Opportunity(
            area = "Personal Development",
            description = "Excellent time for learning and self-improvement",
            timing = "Current period",
            howToLeverage = "Enroll in courses, read extensively, seek mentorship"
        )
    )

    return ChallengesOpportunities(
        currentChallenges = challenges,
        opportunities = opportunities
    )
}

private fun calculateTiming(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline): TimingAnalysis {
    val today = LocalDate.now()

    val favorablePeriods = listOf(
        FavorablePeriod(
            startDate = today.plusDays(30),
            endDate = today.plusDays(90),
            reason = "Jupiter transit supports growth",
            bestFor = listOf("New ventures", "Important decisions", "Relationships")
        )
    )

    val unfavorablePeriods = listOf(
        UnfavorablePeriod(
            startDate = today.plusDays(120),
            endDate = today.plusDays(150),
            reason = "Saturn transit requires caution",
            avoid = listOf("Major investments", "Hasty decisions", "Conflicts")
        )
    )

    val keyDates = listOf(
        KeyDate(
            date = today.plusDays(45),
            event = "Favorable Jupiter Aspect",
            significance = "Excellent for new beginnings",
            isPositive = true
        )
    )

    return TimingAnalysis(
        favorablePeriods = favorablePeriods,
        unfavorablePeriods = unfavorablePeriods,
        keyDates = keyDates
    )
}

private fun calculateRemedies(chart: VedicChart, dashaTimeline: DashaCalculator.DashaTimeline): List<String> {
    val currentPlanet = dashaTimeline.currentMahadasha?.planet

    return listOf(
        "Perform ${currentPlanet?.displayName ?: "planetary"} mantra recitation during morning hours",
        "Donate items associated with ${currentPlanet?.displayName ?: "the current period"} on appropriate days",
        "Wear gemstone related to ${currentPlanet?.displayName ?: "your chart"} after proper consultation",
        "Practice meditation and yoga to balance planetary energies",
        "Maintain regular worship and spiritual practices"
    )
}

// Helper utility functions
private fun getAscendantPath(sign: ZodiacSign): String = when (sign) {
    ZodiacSign.ARIES -> "courage, independence, and pioneering spirit"
    ZodiacSign.TAURUS -> "stability, material security, and perseverance"
    ZodiacSign.GEMINI -> "communication, versatility, and intellectual pursuits"
    ZodiacSign.CANCER -> "nurturing, emotional depth, and family connections"
    ZodiacSign.LEO -> "leadership, creativity, and self-expression"
    ZodiacSign.VIRGO -> "service, analysis, and practical perfection"
    ZodiacSign.LIBRA -> "harmony, relationships, and balanced judgment"
    ZodiacSign.SCORPIO -> "transformation, intensity, and deep research"
    ZodiacSign.SAGITTARIUS -> "wisdom, exploration, and philosophical understanding"
    ZodiacSign.CAPRICORN -> "discipline, ambition, and long-term achievement"
    ZodiacSign.AQUARIUS -> "innovation, humanitarian ideals, and progressive thinking"
    ZodiacSign.PISCES -> "spirituality, compassion, and transcendent wisdom"
}

private fun getMoonPath(sign: ZodiacSign?): String = when (sign) {
    ZodiacSign.ARIES -> "excitement and action"
    ZodiacSign.TAURUS -> "comfort and stability"
    ZodiacSign.GEMINI -> "variety and mental stimulation"
    ZodiacSign.CANCER -> "security and emotional connection"
    ZodiacSign.LEO -> "recognition and warmth"
    ZodiacSign.VIRGO -> "order and practical service"
    ZodiacSign.LIBRA -> "partnership and aesthetic beauty"
    ZodiacSign.SCORPIO -> "depth and emotional transformation"
    ZodiacSign.SAGITTARIUS -> "meaning and philosophical truth"
    ZodiacSign.CAPRICORN -> "achievement and respect"
    ZodiacSign.AQUARIUS -> "freedom and humanitarian connection"
    ZodiacSign.PISCES -> "unity and spiritual transcendence"
    else -> "emotional fulfillment"
}

private fun getLifeTheme(sign: ZodiacSign): String = when (sign) {
    ZodiacSign.ARIES -> "Pioneer & Warrior"
    ZodiacSign.TAURUS -> "Builder & Preserver"
    ZodiacSign.GEMINI -> "Communicator & Learner"
    ZodiacSign.CANCER -> "Nurturer & Protector"
    ZodiacSign.LEO -> "Leader & Creator"
    ZodiacSign.VIRGO -> "Server & Healer"
    ZodiacSign.LIBRA -> "Diplomat & Artist"
    ZodiacSign.SCORPIO -> "Transformer & Investigator"
    ZodiacSign.SAGITTARIUS -> "Philosopher & Explorer"
    ZodiacSign.CAPRICORN -> "Achiever & Master"
    ZodiacSign.AQUARIUS -> "Innovator & Humanitarian"
    ZodiacSign.PISCES -> "Mystic & Compassionate Soul"
}

private fun getSpiritualPath(sign: ZodiacSign?): String = when (sign) {
    ZodiacSign.ARIES -> "self-realization through action"
    ZodiacSign.TAURUS -> "grounding and material transcendence"
    ZodiacSign.GEMINI -> "integration of duality"
    ZodiacSign.CANCER -> "emotional mastery and nurturing wisdom"
    ZodiacSign.LEO -> "heart-centered consciousness"
    ZodiacSign.VIRGO -> "perfection through service"
    ZodiacSign.LIBRA -> "balance and unity consciousness"
    ZodiacSign.SCORPIO -> "death and rebirth cycles"
    ZodiacSign.SAGITTARIUS -> "truth-seeking and higher knowledge"
    ZodiacSign.CAPRICORN -> "mastery and enlightened leadership"
    ZodiacSign.AQUARIUS -> "universal love and detachment"
    ZodiacSign.PISCES -> "dissolution into divine consciousness"
    else -> "spiritual awakening"
}

private fun getDashaEffect(planet: Planet): String = when (planet) {
    Planet.SUN -> "self-confidence, authority, and father-related matters"
    Planet.MOON -> "emotions, mind, and mother-related matters"
    Planet.MARS -> "energy, courage, and taking action"
    Planet.MERCURY -> "communication, intellect, and business"
    Planet.JUPITER -> "wisdom, expansion, and spiritual growth"
    Planet.VENUS -> "relationships, luxury, and artistic pursuits"
    Planet.SATURN -> "discipline, karmic lessons, and hard work"
    Planet.RAHU -> "worldly desires, unconventional paths, and material success"
    Planet.KETU -> "spirituality, detachment, and moksha"
    else -> "various life experiences"
}

private fun getAntardashaEffect(planet: Planet): String = when (planet) {
    Planet.SUN -> "emphasis on leadership and recognition"
    Planet.MOON -> "heightened emotional sensitivity"
    Planet.MARS -> "increased drive and assertiveness"
    Planet.MERCURY -> "enhanced mental clarity"
    Planet.JUPITER -> "blessings and fortunate opportunities"
    Planet.VENUS -> "pleasure and harmonious experiences"
    Planet.SATURN -> "patience and perseverance requirements"
    Planet.RAHU -> "sudden changes and ambition"
    Planet.KETU -> "spiritual insights and letting go"
    else -> "additional influences"
}

private fun getTransitDescription(planet: Planet): String = when (planet) {
    Planet.SUN -> "Illuminating life purpose"
    Planet.MOON -> "Emotional fluctuations"
    Planet.MARS -> "Increased energy and drive"
    Planet.MERCURY -> "Mental activity and communication"
    Planet.JUPITER -> "Growth and opportunities"
    Planet.VENUS -> "Harmony and relationships"
    Planet.SATURN -> "Discipline and restrictions"
    Planet.RAHU -> "Unexpected developments"
    Planet.KETU -> "Spiritual awakening"
    else -> "Planetary influence active"
}

private fun getShortTermPrediction(area: LifeArea): String = when (area) {
    LifeArea.CAREER -> "Focus on current projects and networking. New opportunities may emerge through professional contacts."
    LifeArea.FINANCE -> "Steady income flow with potential for incremental growth. Good time to review budget and savings."
    LifeArea.RELATIONSHIPS -> "Existing relationships deepen. Communication is key for resolving minor conflicts."
    LifeArea.HEALTH -> "Maintain regular exercise and balanced diet. Energy levels are stable."
    LifeArea.EDUCATION -> "Good period for learning and skill development. Short courses bring benefits."
    LifeArea.FAMILY -> "Harmonious domestic atmosphere. Small celebrations or gatherings bring joy."
    LifeArea.SPIRITUAL -> "Daily practices bring mental peace. Insights come through meditation."
}

private fun getMediumTermPrediction(area: LifeArea): String = when (area) {
    LifeArea.CAREER -> "Significant professional advancement possible. Leadership opportunities may present themselves."
    LifeArea.FINANCE -> "Potential for larger gains through investments or promotions. Financial security strengthens."
    LifeArea.RELATIONSHIPS -> "Important relationship milestones. Marriage or commitment possibilities for singles."
    LifeArea.HEALTH -> "Vitality improves with consistent practices. Consider preventive health measures."
    LifeArea.EDUCATION -> "Major educational achievements or certifications. Academic success likely."
    LifeArea.FAMILY -> "Family expansions or property matters. Supportive period for family bonds."
    LifeArea.SPIRITUAL -> "Deepening spiritual understanding. Connection with teachers or spiritual communities."
}

private fun getLongTermPrediction(area: LifeArea): String = when (area) {
    LifeArea.CAREER -> "Establishment as authority in your field. Long-term career goals manifest."
    LifeArea.FINANCE -> "Wealth accumulation and financial independence. Legacy building period."
    LifeArea.RELATIONSHIPS -> "Mature, stable partnerships. Family life flourishes with mutual support."
    LifeArea.HEALTH -> "Strong vitality and longevity indicators. Healthy lifestyle becomes natural."
    LifeArea.EDUCATION -> "Mastery in chosen field. Potential to become teacher or expert."
    LifeArea.FAMILY -> "Strong family foundation. Ancestral blessings and property matters resolved."
    LifeArea.SPIRITUAL -> "Significant spiritual evolution. Inner peace and wisdom established."
}

private fun getKeyFactors(area: LifeArea): List<String> = when (area) {
    LifeArea.CAREER -> listOf("Professional networking", "Skill enhancement", "Leadership development")
    LifeArea.FINANCE -> listOf("Strategic investments", "Multiple income streams", "Financial discipline")
    LifeArea.RELATIONSHIPS -> listOf("Communication quality", "Emotional maturity", "Shared values")
    LifeArea.HEALTH -> listOf("Regular exercise", "Balanced nutrition", "Stress management")
    LifeArea.EDUCATION -> listOf("Consistent study", "Practical application", "Mentor guidance")
    LifeArea.FAMILY -> listOf("Quality time", "Mutual respect", "Emotional support")
    LifeArea.SPIRITUAL -> listOf("Daily practice", "Self-reflection", "Service to others")
}

private fun getAdvice(area: LifeArea): String = when (area) {
    LifeArea.CAREER -> "Take calculated risks and invest in professional development. Network actively and stay open to new opportunities."
    LifeArea.FINANCE -> "Create diversified income sources and maintain financial discipline. Seek expert advice for major investments."
    LifeArea.RELATIONSHIPS -> "Practice active listening and express appreciation regularly. Invest quality time in nurturing bonds."
    LifeArea.HEALTH -> "Establish sustainable wellness routines. Prevention is better than cure - regular check-ups recommended."
    LifeArea.EDUCATION -> "Apply theoretical knowledge practically. Seek mentors and engage in continuous learning."
    LifeArea.FAMILY -> "Balance personal and family time. Address conflicts with patience and understanding."
    LifeArea.SPIRITUAL -> "Maintain daily spiritual practices. Connect with like-minded seekers and serve others."
}

private fun getEnergyColor(energy: Int): Color = when {
    energy >= 8 -> AppTheme.SuccessColor
    energy >= 6 -> AppTheme.AccentGold
    energy >= 4 -> AppTheme.WarningColor
    else -> AppTheme.ErrorColor
}

private fun getLifeAreaColor(area: LifeArea): Color = when (area) {
    LifeArea.CAREER -> AppTheme.LifeAreaCareer
    LifeArea.FINANCE -> AppTheme.LifeAreaFinance
    LifeArea.RELATIONSHIPS -> AppTheme.LifeAreaLove
    LifeArea.HEALTH -> AppTheme.LifeAreaHealth
    LifeArea.EDUCATION -> AppTheme.LifeAreaGrowth
    LifeArea.FAMILY -> AppTheme.AccentTeal
    LifeArea.SPIRITUAL -> AppTheme.LifeAreaSpiritual
}

private fun getPlanetColor(planet: Planet): Color = AppTheme.getPlanetColor(planet)
