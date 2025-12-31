package com.astro.storm.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.currentLanguage
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.VarshaphalaCalculator
import com.astro.storm.ephemeris.VarshaphalaCalculator.*
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

/**
 * VarshaphalaScreen - Annual Horoscope (Tajika System)
 *
 * This screen uses VarshaphalaCalculator for all astronomical calculations
 * and displays comprehensive annual horoscope with:
 * - Solar Return Chart
 * - Year Lord & Muntha
 * - Tajika Aspects & Yogas
 * - Sahams (Arabic Parts)
 * - Mudda Dasha (Annual Periods)
 * - House-wise Predictions
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VarshaphalaScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val calculator = remember { VarshaphalaCalculator(context) }

    val currentYear = LocalDate.now().year
    val birthYear = chart?.birthData?.dateTime?.year ?: currentYear
    var selectedYear by remember { mutableIntStateOf(currentYear) }
    var varshaphalaResult by remember { mutableStateOf<VarshaphalaResult?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var selectedTab by remember { mutableIntStateOf(0) }

    val tabs = listOf(
        stringResource(StringKeyMatch.TAB_OVERVIEW),
        stringResource(StringKeyMatch.TAB_TAJIKA),
        stringResource(StringKeyMatch.TAB_SAHAMS),
        stringResource(StringKeyMatch.TAB_DASHA),
        stringResource(StringKeyMatch.TAB_HOUSES)
    )

    val lang = currentLanguage()
    LaunchedEffect(chart, selectedYear, lang) {
        if (chart != null && selectedYear >= birthYear) {
            isLoading = true
            error = null
            withContext(Dispatchers.IO) {
                try {
                    varshaphalaResult = calculator.calculateVarshaphala(chart, selectedYear, lang)
                } catch (e: Exception) {
                    error = if (lang == com.astro.storm.data.localization.Language.NEPALI) "गणना त्रुटि: ${e.message ?: "अज्ञात त्रुटि"}" else "Calculation error: ${e.message ?: "Unknown error"}"
                }
            }
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(StringKeyMatch.VARSHAPHALA_TITLE),
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        varshaphalaResult?.let {
                            Text(
                                stringResource(StringKey.VARSHAPHALA_AGE, it.age),
                                style = MaterialTheme.typography.labelSmall,
                                color = AppTheme.TextMuted
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(StringKeyMatch.NAV_BACK),
                            tint = AppTheme.TextPrimary
                        )
                    }
                },
                actions = {
                    varshaphalaResult?.let { result ->
                        YearRatingBadge(result.yearRating)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppTheme.ScreenBackground
                )
            )
        },
        containerColor = AppTheme.ScreenBackground
    ) { paddingValues ->
        if (chart == null) {
            EmptyState()
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            YearSelector(
                currentYear = selectedYear,
                birthYear = birthYear,
                onYearChange = { selectedYear = it }
            )

            if (isLoading) {
                LoadingState()
                return@Scaffold
            }

            error?.let { errorMsg ->
                ErrorState(errorMsg) { selectedYear = currentYear }
                return@Scaffold
            }

            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.Transparent,
                contentColor = AppTheme.AccentPrimary,
                divider = { HorizontalDivider(color = AppTheme.DividerColor.copy(alpha = 0.3f)) },
                edgePadding = 8.dp,
                indicator = @Composable { tabPositions ->
                    if (selectedTab < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.BottomStart)
                                .offset(x = tabPositions[selectedTab].left)
                                .width(tabPositions[selectedTab].width),
                            color = AppTheme.AccentPrimary,
                            height = 3.dp
                        )
                    }
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                title,
                                fontWeight = if (selectedTab == index) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (selectedTab == index) AppTheme.AccentPrimary else AppTheme.TextMuted
                            )
                        }
                    )
                }
            }

            varshaphalaResult?.let { result ->
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = {
                        val direction = if (targetState > initialState) 1 else -1
                        slideInHorizontally { direction * it / 4 } + fadeIn() togetherWith
                                slideOutHorizontally { -direction * it / 4 } + fadeOut()
                    },
                    label = "tab_transition"
                ) { tab ->
                    when (tab) {
                        0 -> OverviewTab(result)
                        1 -> TajikaAspectsTab(result)
                        2 -> SahamsTab(result)
                        3 -> DashaTab(result)
                        4 -> HousesTab(result)
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// COMMON UI COMPONENTS
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun YearRatingBadge(rating: Float) {
    val color = when {
        rating >= 4.0f -> AppTheme.SuccessColor
        rating >= 3.0f -> AppTheme.AccentGold
        rating >= 2.0f -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }

    Surface(
        color = color.copy(alpha = 0.15f),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                String.format("%.1f", rating),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
    }
}

@Composable
private fun YearSelector(
    currentYear: Int,
    birthYear: Int,
    onYearChange: (Int) -> Unit
) {
    val maxYear = LocalDate.now().year + 10
    val years = (birthYear..maxYear).toList()
    val scrollState = rememberScrollState()
    val currentYearIndex = years.indexOf(currentYear)

    LaunchedEffect(currentYearIndex) {
        if (currentYearIndex >= 0) {
            scrollState.animateScrollTo(currentYearIndex * 80)
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (currentYear > birthYear) onYearChange(currentYear - 1) },
                    enabled = currentYear > birthYear
                ) {
                    Icon(
                        Icons.Filled.ChevronLeft,
                        contentDescription = stringResource(StringKeyMatch.NAV_PREVIOUS_YEAR),
                        tint = if (currentYear > birthYear) AppTheme.TextPrimary else AppTheme.TextSubtle
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "$currentYear - ${currentYear + 1}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        stringResource(StringKeyMatch.VARSHAPHALA_YEAR_OF_LIFE, currentYear - birthYear + 1),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }

                IconButton(
                    onClick = { if (currentYear < maxYear) onYearChange(currentYear + 1) },
                    enabled = currentYear < maxYear
                ) {
                    Icon(
                        Icons.Filled.ChevronRight,
                        contentDescription = stringResource(StringKeyMatch.NAV_NEXT_YEAR),
                        tint = if (currentYear < maxYear) AppTheme.TextPrimary else AppTheme.TextSubtle
                    )
                }
            }

            Row(
                modifier = Modifier
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                years.forEach { year ->
                    val isSelected = year == currentYear
                    val isFuture = year > LocalDate.now().year

                    FilterChip(
                        selected = isSelected,
                        onClick = { onYearChange(year) },
                        label = {
                            Text(
                                year.toString(),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AppTheme.AccentPrimary.copy(alpha = 0.2f),
                            selectedLabelColor = AppTheme.AccentPrimary
                        ),
                        border = if (isFuture) FilterChipDefaults.filterChipBorder(
                            borderColor = AppTheme.AccentGold.copy(alpha = 0.5f),
                            enabled = true,
                            selected = isSelected
                        ) else null
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                Icons.Outlined.WbSunny,
                contentDescription = null,
                tint = AppTheme.TextMuted,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                stringResource(StringKeyAnalysis.VARSHAPHALA_NO_CHART),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(StringKeyAnalysis.VARSHAPHALA_NO_CHART_DESC),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(
                color = AppTheme.AccentPrimary,
                modifier = Modifier.size(48.dp),
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                stringResource(StringKey.VARSHAPHALA_COMPUTING),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(StringKey.VARSHAPHALA_COMPUTING_DESC),
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun ErrorState(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                Icons.Filled.ErrorOutline,
                contentDescription = null,
                tint = AppTheme.ErrorColor,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                stringResource(StringKey.VARSHAPHALA_ERROR),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.ErrorColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                message,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedButton(
                onClick = onRetry,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AppTheme.AccentPrimary)
            ) {
                Icon(Icons.Filled.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(StringKey.VARSHAPHALA_RESET_YEAR))
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// OVERVIEW TAB
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun OverviewTab(result: VarshaphalaResult) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item { SolarReturnCard(result) }
        item { YearLordMunthaCard(result) }
        item { AnnualChartVisualization(result) }
        item { PanchaVargiyaBalaCard(result) }
        item { TriPatakiChakraCard(result) }
        item { MajorThemesCard(result) }
        item { MonthsCard(result) }
        item { KeyDatesCard(result) }
        item { OverallPredictionCard(result) }
    }
}

@Composable
private fun SolarReturnCard(result: VarshaphalaResult) {
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("h:mm:ss a")

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
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    AppTheme.PlanetSun,
                                    AppTheme.PlanetSun.copy(alpha = 0.3f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.WbSunny,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(StringKey.VARSHAPHALA_SOLAR_RETURN),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        stringResource(StringKeyAnalysis.VARSHAPHALA_SUN_RETURNS),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        stringResource(StringKeyAnalysis.VARSHAPHALA_AGE_FORMAT, result.age),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.AccentPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = AppTheme.DividerColor.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(StringKeyAnalysis.VARSHAPHALA_RETURN_DATE),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        result.solarReturnChart.solarReturnTime.format(dateFormatter),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        result.solarReturnChart.solarReturnTime.format(timeFormatter),
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                InfoChip(
                    label = stringResource(StringKey.CHART_ASCENDANT),
                    value = "${result.solarReturnChart.ascendant.getLocalizedName(currentLanguage())} ${String.format("%.1f", result.solarReturnChart.ascendantDegree)}°",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                InfoChip(
                    label = stringResource(StringKey.PLANET_MOON),
                    value = result.solarReturnChart.moonSign.getLocalizedName(currentLanguage()),
                    subValue = result.solarReturnChart.moonNakshatra,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun InfoChip(
    label: String,
    value: String,
    subValue: String? = null,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = AppTheme.ChipBackground,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.TextMuted
            )
            Text(
                value,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            subValue?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextSecondary
                )
            }
        }
    }
}

@Composable
private fun YearLordMunthaCard(result: VarshaphalaResult) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Year Lord Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(getPlanetColor(result.yearLord).copy(alpha = 0.15f))
                            .border(
                                width = 3.dp,
                                color = getPlanetColor(result.yearLord),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            result.yearLord.symbol,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = getPlanetColor(result.yearLord)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            stringResource(StringKey.VARSHAPHALA_YEAR_LORD),
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextMuted
                        )
                        Text(
                            result.yearLord.getLocalizedName(currentLanguage()),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = AppTheme.TextPrimary
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            StrengthBadge(result.yearLordStrength, currentLanguage())
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                stringResource(StringKeyAnalysis.VARSHAPHALA_IN_HOUSE, result.yearLordHouse),
                                style = MaterialTheme.typography.labelMedium,
                                color = AppTheme.TextSecondary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                result.yearLordDignity,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
            HorizontalDivider(color = AppTheme.DividerColor.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(20.dp))

            // Muntha Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    AppTheme.AccentGold,
                                    AppTheme.AccentGold.copy(alpha = 0.6f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Adjust,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                stringResource(StringKey.VARSHAPHALA_MUNTHA),
                                style = MaterialTheme.typography.labelSmall,
                                color = AppTheme.TextMuted
                            )
                            Text(
                                result.muntha.sign.getLocalizedName(currentLanguage()),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = AppTheme.TextPrimary
                            )
                            Text(
                                "${stringResource(StringKey.VARSHAPHALA_HOUSE)} ${result.muntha.house} • ${String.format("%.1f", result.muntha.degree)}°",
                                style = MaterialTheme.typography.labelMedium,
                                color = AppTheme.TextSecondary
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                stringResource(StringKeyAnalysis.VARSHAPHALA_LORD_PREFIX, result.muntha.lord.getLocalizedName(currentLanguage())),
                                style = MaterialTheme.typography.labelMedium,
                                color = getPlanetColor(result.muntha.lord),
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                stringResource(StringKeyAnalysis.VARSHAPHALA_IN_HOUSE, result.muntha.lordHouse),
                                style = MaterialTheme.typography.labelSmall,
                                color = AppTheme.TextMuted
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(result.muntha.themes) { theme ->
                            Surface(
                                color = AppTheme.AccentGold.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    theme,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppTheme.AccentGold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        result.muntha.interpretation,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun StrengthBadge(strength: String, language: com.astro.storm.data.localization.Language) {
    Surface(
        color = getStrengthColor(strength, language).copy(alpha = 0.15f),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            strength,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            color = getStrengthColor(strength, language),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
private fun AnnualChartVisualization(result: VarshaphalaResult) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { isExpanded = !isExpanded },
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
                        Icons.Outlined.GridView,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        stringResource(StringKey.VARSHAPHALA_TAJIKA_CHART),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }

                Icon(
                    if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    SouthIndianChart(
                        planetPositions = result.solarReturnChart.planetPositions,
                        ascendantSign = result.solarReturnChart.ascendant,
                        munthaSign = result.muntha.sign,
                        title = stringResource(StringKeyMatch.VARSHAPHALA_TITLE),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ChartLegendItem(stringResource(StringKeyAnalysis.VARSHAPHALA_CHART_LEGEND_ASC), AppTheme.AccentPrimary)
                        ChartLegendItem(stringResource(StringKeyAnalysis.VARSHAPHALA_CHART_LEGEND_MUNTHA), AppTheme.AccentGold)
                        ChartLegendItem(stringResource(StringKeyAnalysis.VARSHAPHALA_CHART_LEGEND_BENEFIC), AppTheme.SuccessColor)
                        ChartLegendItem(stringResource(StringKeyAnalysis.VARSHAPHALA_CHART_LEGEND_MALEFIC), AppTheme.ErrorColor)
                    }
                }
            }
        }
    }
}

@Composable
private fun SouthIndianChart(
    planetPositions: Map<Planet, SolarReturnPlanetPosition>,
    ascendantSign: ZodiacSign,
    munthaSign: ZodiacSign,
    title: String,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()

    // Read colors outside Canvas
    val cardBackgroundElevated = AppTheme.CardBackgroundElevated
    val dividerColor = AppTheme.DividerColor
    val textMuted = AppTheme.TextMuted
    val accentPrimary = AppTheme.AccentPrimary
    val accentGold = AppTheme.AccentGold
    val warningColor = AppTheme.WarningColor
    val cardBackground = AppTheme.CardBackground
    val textPrimary = AppTheme.TextPrimary

    // Precompute planet colors
    val planetColors = planetPositions.keys.associateWith { planet -> getPlanetColor(planet) }

    Canvas(modifier = modifier) {
        val cellSize = size.width / 4
        val strokeWidth = 2.dp.toPx()

        drawRect(
            color = cardBackgroundElevated,
            size = size
        )

        // Draw grid
        for (i in 0..4) {
            drawLine(
                color = dividerColor,
                start = Offset(i * cellSize, 0f),
                end = Offset(i * cellSize, size.height),
                strokeWidth = strokeWidth
            )
            drawLine(
                color = dividerColor,
                start = Offset(0f, i * cellSize),
                end = Offset(size.width, i * cellSize),
                strokeWidth = strokeWidth
            )
        }

        // House positions for South Indian chart
        val housePositions = listOf(
            Pair(1, Offset(1.5f * cellSize, 0.5f * cellSize)),
            Pair(2, Offset(0.5f * cellSize, 0.5f * cellSize)),
            Pair(3, Offset(0.5f * cellSize, 1.5f * cellSize)),
            Pair(4, Offset(0.5f * cellSize, 2.5f * cellSize)),
            Pair(5, Offset(0.5f * cellSize, 3.5f * cellSize)),
            Pair(6, Offset(1.5f * cellSize, 3.5f * cellSize)),
            Pair(7, Offset(2.5f * cellSize, 3.5f * cellSize)),
            Pair(8, Offset(3.5f * cellSize, 3.5f * cellSize)),
            Pair(9, Offset(3.5f * cellSize, 2.5f * cellSize)),
            Pair(10, Offset(3.5f * cellSize, 1.5f * cellSize)),
            Pair(11, Offset(3.5f * cellSize, 0.5f * cellSize)),
            Pair(12, Offset(2.5f * cellSize, 0.5f * cellSize))
        )

        val signOrder = ZodiacSign.entries
        val ascIndex = signOrder.indexOf(ascendantSign)

        // Draw house signs and highlights
        housePositions.forEach { (house, position) ->
            val signIndex = (ascIndex + house - 1) % 12
            val sign = signOrder[signIndex]

            val textLayout = textMeasurer.measure(
                text = getZodiacSymbol(sign),
                style = TextStyle(
                    fontSize = 10.sp,
                    color = textMuted
                )
            )

            val textX = position.x - cellSize / 2 + 8.dp.toPx()
            val textY = position.y - cellSize / 2 + 8.dp.toPx()

            drawText(
                textLayoutResult = textLayout,
                topLeft = Offset(textX, textY)
            )

            if (sign == ascendantSign) {
                drawCircle(
                    color = accentPrimary.copy(alpha = 0.2f),
                    radius = cellSize / 3,
                    center = position
                )
            }

            if (sign == munthaSign) {
                drawCircle(
                    color = accentGold,
                    radius = 6.dp.toPx(),
                    center = Offset(position.x + cellSize / 3, position.y - cellSize / 3),
                    style = Stroke(width = 2.dp.toPx())
                )
            }
        }

        // Draw planets
        planetPositions.forEach { (planet, pos) ->
            val houseIndex = pos.house - 1
            if (houseIndex in 0..11) {
                val basePosition = housePositions[houseIndex].second
                val planetIndex = planetPositions.filter { it.value.house == pos.house }
                    .keys.toList().indexOf(planet)

                val offsetX = (planetIndex % 2) * 24.dp.toPx() - 12.dp.toPx()
                val offsetY = (planetIndex / 2) * 18.dp.toPx()

                val planetPos = Offset(
                    basePosition.x + offsetX,
                    basePosition.y + offsetY
                )

                val textLayout = textMeasurer.measure(
                    text = planet.symbol,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = planetColors[planet] ?: textPrimary
                    )
                )

                if (pos.isRetrograde) {
                    drawCircle(
                        color = warningColor.copy(alpha = 0.3f),
                        radius = 12.dp.toPx(),
                        center = planetPos
                    )
                }

                drawText(
                    textLayoutResult = textLayout,
                    topLeft = Offset(
                        planetPos.x - textLayout.size.width / 2,
                        planetPos.y - textLayout.size.height / 2
                    )
                )
            }
        }

        // Draw center box
        drawRect(
            color = cardBackground,
            topLeft = Offset(cellSize, cellSize),
            size = Size(cellSize * 2, cellSize * 2)
        )

        val titleLayout = textMeasurer.measure(
            text = title,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = textPrimary
            )
        )
        drawText(
            textLayoutResult = titleLayout,
            topLeft = Offset(
                (size.width - titleLayout.size.width) / 2,
                size.height / 2 - titleLayout.size.height
            )
        )
    }
}

@Composable
private fun ChartLegendItem(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.TextMuted
        )
    }
}

@Composable
private fun PanchaVargiyaBalaCard(result: VarshaphalaResult) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { isExpanded = !isExpanded },
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
                        Icons.Outlined.Assessment,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            stringResource(StringKey.VARSHAPHALA_PANCHA_VARGIYA),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            stringResource(StringKeyAnalysis.VARSHAPHALA_FIVEFOLD_STRENGTH),
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Icon(
                    if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    result.panchaVargiyaBala.forEach { bala ->
                        PlanetBalaRow(bala)
                    }
                }
            }
        }
    }
}

@Composable
private fun PlanetBalaRow(bala: PanchaVargiyaBala) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            bala.planet.symbol,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = getPlanetColor(bala.planet),
            modifier = Modifier.width(32.dp)
        )

        Text(
            bala.planet.getLocalizedName(currentLanguage()),
            style = MaterialTheme.typography.bodyMedium,
            color = AppTheme.TextPrimary,
            modifier = Modifier.width(80.dp)
        )

        LinearProgressIndicator(
            progress = { (bala.total.toFloat() / 20f).coerceIn(0f, 1f) },
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp)),
            color = getStrengthColor(bala.category, currentLanguage()),
            trackColor = AppTheme.DividerColor
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            String.format("%.1f", bala.total),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.TextPrimary,
            modifier = Modifier.width(36.dp)
        )

        Surface(
            color = getStrengthColor(bala.category, currentLanguage()).copy(alpha = 0.15f),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                bala.category.take(3),
                style = MaterialTheme.typography.labelSmall,
                color = getStrengthColor(bala.category, currentLanguage()),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
private fun TriPatakiChakraCard(result: VarshaphalaResult) {
    var isExpanded by remember { mutableStateOf(false) }
    val chakra = result.triPatakiChakra

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { isExpanded = !isExpanded },
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
                        Icons.Outlined.Hub,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            stringResource(StringKey.VARSHAPHALA_TRI_PATAKI),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            stringResource(StringKeyAnalysis.VARSHAPHALA_THREE_FLAG),
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Icon(
                    if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Surface(
                color = AppTheme.AccentPrimary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    chakra.dominantInfluence,
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.AccentPrimary,
                    modifier = Modifier.padding(12.dp)
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))

                    chakra.sectors.forEach { sector ->
                        TriPatakiSectorRow(sector)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        chakra.interpretation,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun TriPatakiSectorRow(sector: TriPatakiSector) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.ChipBackground, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Text(
            sector.name,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.TextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))

        if (sector.planets.isNotEmpty()) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                sector.planets.forEach { planet ->
                    Text(
                        planet.symbol,
                        style = MaterialTheme.typography.bodyMedium,
                        color = getPlanetColor(planet),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        } else {
            Text(
                stringResource(StringKeyAnalysis.VARSHAPHALA_NO_PLANETS),
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.TextMuted
            )
        }

        Text(
            sector.influence,
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.TextSecondary
        )
    }
}

@Composable
private fun MajorThemesCard(result: VarshaphalaResult) {
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
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    stringResource(StringKey.VARSHAPHALA_MAJOR_THEMES),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            result.majorThemes.forEachIndexed { index, theme ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Surface(
                        color = AppTheme.AccentGold.copy(alpha = 0.15f),
                        shape = CircleShape,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text(
                                "${index + 1}",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = AppTheme.AccentGold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        theme,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextPrimary,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun MonthsCard(result: VarshaphalaResult) {
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
                    Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    stringResource(StringKey.VARSHAPHALA_MONTHLY_OUTLOOK),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(StringKey.VARSHAPHALA_FAVORABLE),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.SuccessColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(result.favorableMonths) { month ->
                            Surface(
                                color = AppTheme.SuccessColor.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    getMonthName(month),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppTheme.SuccessColor,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(StringKeyAnalysis.VARSHAPHALA_CHALLENGING),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.WarningColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        items(result.challengingMonths) { month ->
                            Surface(
                                color = AppTheme.WarningColor.copy(alpha = 0.15f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    getMonthName(month),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppTheme.WarningColor,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
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
private fun KeyDatesCard(result: VarshaphalaResult) {
    var isExpanded by remember { mutableStateOf(false) }
    val dateFormatter = DateTimeFormatter.ofPattern("MMM d, yyyy")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { isExpanded = !isExpanded },
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
                        Icons.Outlined.Event,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            stringResource(StringKey.VARSHAPHALA_KEY_DATES),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            stringResource(StringKeyAnalysis.VARSHAPHALA_IMPORTANT_DATES, result.keyDates.size),
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                Icon(
                    if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(16.dp))
                    result.keyDates.take(10).forEach { keyDate ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            val color = when (keyDate.type) {
                                KeyDateType.FAVORABLE -> AppTheme.SuccessColor
                                KeyDateType.CHALLENGING -> AppTheme.WarningColor
                                KeyDateType.IMPORTANT -> AppTheme.AccentPrimary
                                KeyDateType.TRANSIT -> AppTheme.AccentGold
                            }

                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(color, CircleShape)
                                    .align(Alignment.CenterVertically)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    keyDate.event,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = AppTheme.TextPrimary
                                )
                                Text(
                                    keyDate.description,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppTheme.TextSecondary
                                )
                            }
                            Text(
                                keyDate.date.format(dateFormatter),
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

@Composable
private fun OverallPredictionCard(result: VarshaphalaResult) {
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
                    Icons.Outlined.Insights,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    stringResource(StringKey.VARSHAPHALA_OVERALL_PREDICTION),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                result.overallPrediction,
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextSecondary,
                lineHeight = 22.sp
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// TAJIKA ASPECTS TAB
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun TajikaAspectsTab(result: VarshaphalaResult) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            TajikaAspectsHeader(result.tajikaAspects)
        }

        items(result.tajikaAspects) { aspect ->
            TajikaAspectCard(aspect)
        }
    }
}

@Composable
private fun TajikaAspectsHeader(aspects: List<TajikaAspectResult>) {
    val positive = aspects.count { it.type.isPositive }
    val challenging = aspects.size - positive

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                stringResource(StringKey.VARSHAPHALA_TAJIKA_SUMMARY),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "$positive",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.SuccessColor
                    )
                    Text(
                        stringResource(StringKey.VARSHAPHALA_FAVORABLE),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "$challenging",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.WarningColor
                    )
                    Text(
                        stringResource(StringKeyAnalysis.VARSHAPHALA_CHALLENGING),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "${aspects.size}",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.AccentPrimary
                    )
                    Text(
                        stringResource(StringKeyAnalysis.VARSHAPHALA_TOTAL),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }
            }
        }
    }
}

@Composable
private fun TajikaAspectCard(aspect: TajikaAspectResult) {
    val language = currentLanguage()
    val color = if (aspect.type.isPositive) AppTheme.SuccessColor else AppTheme.WarningColor

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
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
                    Text(
                        aspect.planet1.symbol,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = getPlanetColor(aspect.planet1)
                    )
                    Text(
                        " ↔ ",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextMuted
                    )
                    Text(
                        aspect.planet2.symbol,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = getPlanetColor(aspect.planet2)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Surface(
                        color = color.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            aspect.type.getDisplayName(language),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = color,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Text(
                    "${aspect.aspectAngle}° (${String.format("%.1f", aspect.orb)}°)",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextMuted
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                aspect.effectDescription,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(StringKeyAnalysis.VARSHAPHALA_HOUSES_PREFIX) + aspect.relatedHouses.joinToString(", "),
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextMuted
                )
                Text(
                    aspect.strength.getDisplayName(language),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Medium,
                    color = getAspectStrengthColor(aspect.strength)
                )
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// SAHAMS TAB
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun SahamsTab(result: VarshaphalaResult) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            SahamsHeader()
        }

        items(result.sahams) { saham ->
            SahamCard(saham)
        }
    }
}

@Composable
private fun SahamsHeader() {
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
                    Icons.Outlined.Stars,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    stringResource(StringKey.VARSHAPHALA_SAHAMS_TITLE),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                stringResource(StringKey.VARSHAPHALA_SAHAMS_DESC),
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun SahamCard(saham: SahamResult) {
    val isActive = saham.isActive

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) AppTheme.CardBackground else AppTheme.CardBackground.copy(alpha = 0.7f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        saham.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        saham.sanskritName,
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }

                if (isActive) {
                    Surface(
                        color = AppTheme.SuccessColor.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            stringResource(StringKeyAnalysis.VARSHAPHALA_ACTIVE),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.SuccessColor,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                InfoChip(
                    label = stringResource(StringKey.VARSHAPHALA_POSITION),
                    value = "${String.format("%.1f", saham.degree)}° ${saham.sign.displayName}",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                InfoChip(
                    label = stringResource(StringKey.VARSHAPHALA_HOUSE),
                    value = "${saham.house}",
                    subValue = stringResource(StringKeyAnalysis.VARSHAPHALA_LORD_PREFIX, saham.lord.displayName),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                saham.interpretation,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary,
                lineHeight = 18.sp
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// DASHA TAB
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun DashaTab(result: VarshaphalaResult) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        item {
            DashaHeader()
        }

        items(result.muddaDasha) { period ->
            MuddaDashaPeriodCard(period)
        }
    }
}

@Composable
private fun DashaHeader() {
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
                    Icons.Outlined.Timeline,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    stringResource(StringKey.VARSHAPHALA_MUDDA_DASHA),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                stringResource(StringKey.VARSHAPHALA_MUDDA_DASHA_DESC),
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun MuddaDashaPeriodCard(period: MuddaDashaPeriod) {
    val dateFormatter = DateTimeFormatter.ofPattern("MMM d")
    var isExpanded by remember { mutableStateOf(period.isCurrent) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(
            containerColor = if (period.isCurrent)
                AppTheme.AccentPrimary.copy(alpha = 0.1f)
            else AppTheme.CardBackground
        ),
        shape = RoundedCornerShape(12.dp),
        border = if (period.isCurrent) androidx.compose.foundation.BorderStroke(
            1.dp, AppTheme.AccentPrimary.copy(alpha = 0.5f)
        ) else null
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(getPlanetColor(period.planet).copy(alpha = 0.15f))
                        .border(2.dp, getPlanetColor(period.planet), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        period.planet.symbol,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = getPlanetColor(period.planet)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            period.planet.getLocalizedName(currentLanguage()),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        if (period.isCurrent) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                color = AppTheme.AccentPrimary,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    stringResource(StringKeyAnalysis.VARSHAPHALA_CURRENT),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                    Text(
                        "${period.startDate.format(dateFormatter)} - ${period.endDate.format(dateFormatter)}",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        stringResource(StringKeyAnalysis.VARSHAPHALA_DAYS_FORMAT, period.days),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.TextPrimary
                    )
                    StrengthBadge(period.planetStrength, currentLanguage())
                }
            }

            if (period.isCurrent) {
                Spacer(modifier = Modifier.height(12.dp))
                LinearProgressIndicator(
                    progress = { period.progressPercent },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = AppTheme.AccentPrimary,
                    trackColor = AppTheme.DividerColor
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = AppTheme.DividerColor.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(period.keywords) { keyword ->
                            Surface(
                                color = getPlanetColor(period.planet).copy(alpha = 0.15f),
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    keyword,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = getPlanetColor(period.planet),
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        period.prediction,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp
                    )

                    if (period.houseRuled.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            stringResource(StringKeyAnalysis.VARSHAPHALA_RULES_HOUSES, period.houseRuled.joinToString(", ")),
                            style = MaterialTheme.typography.labelSmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }
            }
        }
    }
}

// ═══════════════════════════════════════════════════════════════════════════════
// HOUSES TAB
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun HousesTab(result: VarshaphalaResult) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        items(result.housePredictions) { prediction ->
            HousePredictionCard(prediction)
        }
    }
}

@Composable
private fun HousePredictionCard(prediction: HousePrediction) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clickable { isExpanded = !isExpanded },
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
                        .clip(CircleShape)
                        .background(AppTheme.AccentPrimary.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "${prediction.house}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.AccentPrimary
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        stringResource(StringKeyAnalysis.VARSHAPHALA_HOUSE_SIGN, prediction.house, prediction.signOnCusp.getLocalizedName(currentLanguage())),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        stringResource(StringKeyAnalysis.VARSHAPHALA_LORD_IN_HOUSE, prediction.houseLord.getLocalizedName(currentLanguage()), prediction.lordPosition),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        repeat(5) { index ->
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (index < prediction.rating)
                                    AppTheme.AccentGold
                                else AppTheme.DividerColor,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                    StrengthBadge(prediction.strength, currentLanguage())
                }
            }

            if (prediction.planetsInHouse.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        stringResource(StringKeyAnalysis.VARSHAPHALA_PLANETS),
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                    prediction.planetsInHouse.forEach { planet ->
                        Text(
                            planet.symbol,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = getPlanetColor(planet)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = AppTheme.DividerColor.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        items(prediction.keywords) { keyword ->
                            Surface(
                                color = AppTheme.ChipBackground,
                                shape = RoundedCornerShape(6.dp)
                            ) {
                                Text(
                                    keyword,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = AppTheme.TextSecondary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        prediction.prediction,
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary,
                        lineHeight = 18.sp
                    )

                    if (prediction.specificEvents.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            stringResource(StringKeyAnalysis.VARSHAPHALA_SPECIFIC_INDICATIONS),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        prediction.specificEvents.forEach { event ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    "• ",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = AppTheme.AccentPrimary
                                )
                                Text(
                                    event,
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

// ═══════════════════════════════════════════════════════════════════════════════
// HELPER FUNCTIONS
// ═══════════════════════════════════════════════════════════════════════════════

@Composable
private fun getPlanetColor(planet: Planet): Color {
    return when (planet) {
        Planet.SUN -> AppTheme.PlanetSun
        Planet.MOON -> AppTheme.PlanetMoon
        Planet.MARS -> AppTheme.PlanetMars
        Planet.MERCURY -> AppTheme.PlanetMercury
        Planet.JUPITER -> AppTheme.PlanetJupiter
        Planet.VENUS -> AppTheme.PlanetVenus
        Planet.SATURN -> AppTheme.PlanetSaturn
        Planet.RAHU -> AppTheme.PlanetRahu
        Planet.KETU -> AppTheme.PlanetKetu
        else -> AppTheme.TextPrimary
    }
}

@Composable
private fun getStrengthColor(strength: String, language: Language): Color {
    val excellent = stringResource(StringKeyAnalysis.VARSHA_STRENGTH_EXCELLENT)
    val strong = stringResource(StringKeyAnalysis.VARSHA_STRENGTH_STRONG)
    val good = stringResource(StringKeyAnalysis.PANCHA_GOOD)
    val average = stringResource(StringKeyAnalysis.PANCHA_AVERAGE)
    val moderate = stringResource(StringKeyAnalysis.VARSHA_STRENGTH_MODERATE)
    val weak = stringResource(StringKeyAnalysis.VARSHA_STRENGTH_WEAK)
    val challenged = stringResource(StringKeyAnalysis.VARSHA_STRENGTH_CHALLENGED)
    val debilitated = stringResource(StringKeyAnalysis.VARSHA_STRENGTH_DEBILITATED)

    return when {
        strength.contains(excellent, ignoreCase = true) -> AppTheme.SuccessColor
        strength.contains(strong, ignoreCase = true) -> AppTheme.SuccessColor
        strength.contains(good, ignoreCase = true) -> AppTheme.SuccessColor
        strength.contains(moderate, ignoreCase = true) -> AppTheme.AccentGold
        strength.contains(average, ignoreCase = true) -> AppTheme.AccentGold
        strength.contains(weak, ignoreCase = true) -> AppTheme.WarningColor
        strength.contains(challenged, ignoreCase = true) -> AppTheme.ErrorColor
        strength.contains(debilitated, ignoreCase = true) -> AppTheme.ErrorColor
        // Fallback to English for safety
        strength.contains("Excellent", ignoreCase = true) -> AppTheme.SuccessColor
        strength.contains("Strong", ignoreCase = true) -> AppTheme.SuccessColor
        strength.contains("Moderate", ignoreCase = true) -> AppTheme.AccentGold
        strength.contains("Weak", ignoreCase = true) -> AppTheme.WarningColor
        strength.contains("Challenged", ignoreCase = true) -> AppTheme.ErrorColor
        strength.contains("Debilitated", ignoreCase = true) -> AppTheme.ErrorColor
        else -> AppTheme.TextMuted
    }
}

@Composable
private fun getAspectStrengthColor(strength: AspectStrength): Color {
    return when (strength) {
        AspectStrength.VERY_STRONG -> AppTheme.SuccessColor
        AspectStrength.STRONG -> AppTheme.SuccessColor
        AspectStrength.MODERATE -> AppTheme.AccentGold
        AspectStrength.WEAK -> AppTheme.WarningColor
        AspectStrength.VERY_WEAK -> AppTheme.ErrorColor
    }
}

private fun getZodiacSymbol(sign: ZodiacSign): String {
    return when (sign) {
        ZodiacSign.ARIES -> "♈"
        ZodiacSign.TAURUS -> "♉"
        ZodiacSign.GEMINI -> "♊"
        ZodiacSign.CANCER -> "♋"
        ZodiacSign.LEO -> "♌"
        ZodiacSign.VIRGO -> "♍"
        ZodiacSign.LIBRA -> "♎"
        ZodiacSign.SCORPIO -> "♏"
        ZodiacSign.SAGITTARIUS -> "♐"
        ZodiacSign.CAPRICORN -> "♑"
        ZodiacSign.AQUARIUS -> "♒"
        ZodiacSign.PISCES -> "♓"
    }
}

@Composable
private fun getMonthName(month: Int): String {
    return when (month) {
        1 -> stringResource(StringKeyAnalysis.MONTH_JAN)
        2 -> stringResource(StringKeyAnalysis.MONTH_FEB)
        3 -> stringResource(StringKeyAnalysis.MONTH_MAR)
        4 -> stringResource(StringKeyAnalysis.MONTH_APR)
        5 -> stringResource(StringKeyAnalysis.MONTH_MAY)
        6 -> stringResource(StringKeyAnalysis.MONTH_JUN)
        7 -> stringResource(StringKeyAnalysis.MONTH_JUL)
        8 -> stringResource(StringKeyAnalysis.MONTH_AUG)
        9 -> stringResource(StringKeyAnalysis.MONTH_SEP)
        10 -> stringResource(StringKeyAnalysis.MONTH_OCT)
        11 -> stringResource(StringKeyAnalysis.MONTH_NOV)
        12 -> stringResource(StringKeyAnalysis.MONTH_DEC)
        else -> ""
    }
}

