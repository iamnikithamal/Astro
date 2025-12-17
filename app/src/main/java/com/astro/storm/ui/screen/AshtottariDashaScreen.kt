package com.astro.storm.ui.screen

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.localization.currentLanguage
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.AshtottariDashaCalculator
import com.astro.storm.ephemeris.AshtottariDashaResult
import com.astro.storm.ephemeris.AshtottariMahadasha
import com.astro.storm.ephemeris.AshtottariAntardasha
import com.astro.storm.ephemeris.PlanetRelationship
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

// Helper extension for planet abbreviation
private val Planet.abbreviation: String
    get() = when (this) {
        Planet.SUN -> "Su"
        Planet.MOON -> "Mo"
        Planet.MARS -> "Ma"
        Planet.MERCURY -> "Me"
        Planet.JUPITER -> "Ju"
        Planet.VENUS -> "Ve"
        Planet.SATURN -> "Sa"
        Planet.RAHU -> "Ra"
        Planet.KETU -> "Ke"
        else -> name.take(2)
    }

/**
 * Ashtottari Dasha Screen
 *
 * Comprehensive display of the 108-year Ashtottari Dasha system.
 * Shows applicability check, timeline, current periods, and interpretations.
 *
 * Key Features:
 * - Applicability check (Rahu in Kendra/Trikona from Lagna Lord)
 * - Complete timeline with all Mahadashas
 * - Current period details with Antardashas
 * - Comparison with Vimshottari when both are applicable
 *
 * Reference: BPHS Chapter 46, Uttara Kalamrita
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AshtottariDashaScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = stringResource(StringKeyDosha.ASHTOTTARI_TITLE),
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = currentLanguage()
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var isCalculating by remember { mutableStateOf(true) }
    var dashaResult by remember { mutableStateOf<AshtottariDashaResult?>(null) }
    var selectedMahadasha by remember { mutableStateOf<AshtottariMahadasha?>(null) }
    var expandedMahadashaIndex by remember { mutableIntStateOf(-1) }

    val tabs = listOf(
        stringResource(StringKeyDosha.ASHTOTTARI_ABOUT),
        stringResource(StringKeyDosha.ASHTOTTARI_TIMELINE),
        stringResource(StringKeyDosha.SCREEN_INTERPRETATION)
    )

    // Calculate Ashtottari Dasha
    LaunchedEffect(chart) {
        isCalculating = true
        delay(300)
        try {
            dashaResult = withContext(Dispatchers.Default) {
                AshtottariDashaCalculator.calculateAshtottariDasha(chart)
            }
        } catch (e: Exception) {
            // Handle error silently
        }
        isCalculating = false
    }

    if (showInfoDialog) {
        AshtottariInfoDialog(onDismiss = { showInfoDialog = false })
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(StringKeyDosha.ASHTOTTARI_TITLE),
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
                            contentDescription = stringResource(StringKeyDosha.ASHTOTTARI_ABOUT),
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
        } else if (dashaResult != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Tab selector
                TabSelector(
                    tabs = tabs,
                    selectedIndex = selectedTab,
                    onTabSelected = { selectedTab = it }
                )

                // Content based on selected tab
                when (selectedTab) {
                    0 -> ApplicabilityContent(dashaResult!!, chart)
                    1 -> TimelineContent(
                        result = dashaResult!!,
                        expandedIndex = expandedMahadashaIndex,
                        onExpandChange = { expandedMahadashaIndex = if (expandedMahadashaIndex == it) -1 else it }
                    )
                    2 -> InterpretationContent(dashaResult!!)
                }
            }
        } else {
            ErrorContent(paddingValues, stringResource(StringKeyDosha.SCREEN_ERROR_CALCULATION))
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
    val language = currentLanguage()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(tabs) { index, tab ->
            val isSelected = index == selectedIndex
            FilterChip(
                selected = isSelected,
                onClick = { onTabSelected(index) },
                label = {
                    Text(
                        tab,
                        fontSize = 13.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
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
private fun ApplicabilityContent(result: AshtottariDashaResult, chart: VedicChart) {
    val language = currentLanguage()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Applicability Status Card
        item {
            ApplicabilityStatusCard(result)
        }

        // Condition Details Card
        item {
            ConditionDetailsCard(result)
        }

        // Current Period Card if applicable
        if (result.currentMahadasha != null) {
            item {
                CurrentPeriodCard(result)
            }
        }

        // System Info Card
        item {
            SystemInfoCard()
        }
    }
}

@Composable
private fun ApplicabilityStatusCard(result: AshtottariDashaResult) {
    val language = currentLanguage()
    val isApplicable = result.applicability.isApplicable

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isApplicable)
                AppTheme.SuccessColor.copy(alpha = 0.1f)
            else
                AppTheme.WarningColor.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isApplicable) Icons.Filled.CheckCircle else Icons.Outlined.Info,
                    contentDescription = null,
                    tint = if (isApplicable) AppTheme.SuccessColor else AppTheme.WarningColor,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        stringResource(StringKeyDosha.ASHTOTTARI_APPLICABILITY),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        if (isApplicable)
                            stringResource(StringKeyDosha.ASHTOTTARI_APPLICABLE)
                        else
                            stringResource(StringKeyDosha.ASHTOTTARI_NOT_APPLICABLE),
                        fontSize = 14.sp,
                        color = AppTheme.TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                result.applicability.reason,
                fontSize = 14.sp,
                color = AppTheme.TextMuted,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun ConditionDetailsCard(result: AshtottariDashaResult) {
    val language = currentLanguage()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                stringResource(StringKeyDosha.ASHTOTTARI_IDEAL_CONDITION),
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = AppTheme.TextPrimary,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Birth Nakshatra Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(StringKeyDosha.BIRTH_NAKSHATRA),
                    color = AppTheme.TextMuted,
                    fontSize = 13.sp
                )
                Text(
                    result.moonNakshatra.getLocalizedName(language),
                    color = AppTheme.TextPrimary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Starting Lord
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(StringKeyMatch.DASHA_LORD),
                    color = AppTheme.TextMuted,
                    fontSize = 13.sp
                )
                Text(
                    result.startingLord.getLocalizedName(language),
                    color = AppTheme.TextPrimary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Balance at Birth
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(StringKeyDosha.BALANCE_LABEL),
                    color = AppTheme.TextMuted,
                    fontSize = 13.sp
                )
                Text(
                    String.format("%.2f %s", result.balanceAtBirth, stringResource(StringKey.YEARS)),
                    color = AppTheme.TextPrimary,
                    fontWeight = FontWeight.Medium,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun CurrentPeriodCard(result: AshtottariDashaResult) {
    val language = currentLanguage()
    val mahadasha = result.currentMahadasha ?: return
    val antardasha = result.currentAntardasha
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.AccentPrimary.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Schedule,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyDosha.ASHTOTTARI_CURRENT_PERIOD),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Current Mahadasha
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        AppTheme.AccentPrimary.copy(alpha = 0.1f),
                        RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        stringResource(StringKey.DASHA_MAHADASHA),
                        color = AppTheme.TextMuted,
                        fontSize = 12.sp
                    )
                    Text(
                        mahadasha.planet.getLocalizedName(language),
                        color = AppTheme.TextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "${mahadasha.startDate.format(dateFormatter)}",
                        color = AppTheme.TextMuted,
                        fontSize = 11.sp
                    )
                    Text(
                        "- ${mahadasha.endDate.format(dateFormatter)}",
                        color = AppTheme.TextMuted,
                        fontSize = 11.sp
                    )
                }
            }

            // Current Antardasha
            if (antardasha != null) {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            AppTheme.AccentSecondary.copy(alpha = 0.1f),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            stringResource(StringKey.DASHA_ANTARDASHA),
                            color = AppTheme.TextMuted,
                            fontSize = 12.sp
                        )
                        Text(
                            antardasha.antardashaLord.getLocalizedName(language),
                            color = AppTheme.TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                    RelationshipChip(antardasha.relationship)
                }
            }

            // Progress Bar
            Spacer(modifier = Modifier.height(16.dp))

            val progress = calculateMahadashaProgress(mahadasha)
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(StringKey.DASHA_PROGRESS),
                        color = AppTheme.TextMuted,
                        fontSize = 12.sp
                    )
                    Text(
                        "${(progress * 100).toInt()}%",
                        color = AppTheme.AccentPrimary,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                LinearProgressIndicator(
                    progress = { progress.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = AppTheme.AccentPrimary,
                    trackColor = AppTheme.AccentPrimary.copy(alpha = 0.2f)
                )
            }
        }
    }
}

@Composable
private fun RelationshipChip(relationship: PlanetRelationship) {
    val language = currentLanguage()
    val (color, text) = when (relationship) {
        PlanetRelationship.FRIEND -> AppTheme.SuccessColor to stringResource(StringKeyDosha.RELATIONSHIP_FRIEND)
        PlanetRelationship.ENEMY -> AppTheme.ErrorColor to stringResource(StringKeyDosha.RELATIONSHIP_ENEMY)
        PlanetRelationship.NEUTRAL -> AppTheme.WarningColor to stringResource(StringKeyDosha.RELATIONSHIP_NEUTRAL)
        PlanetRelationship.SAME -> AppTheme.AccentPrimary to stringResource(StringKeyDosha.RELATIONSHIP_SAME)
    }

    Surface(
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = color,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun SystemInfoCard() {
    val language = currentLanguage()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                stringResource(StringKeyDosha.ASHTOTTARI_SUBTITLE),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Planet periods
            val periods = listOf(
                StringKeyDosha.ASHTOTTARI_SUN_DURATION,
                StringKeyDosha.ASHTOTTARI_MOON_DURATION,
                StringKeyDosha.ASHTOTTARI_MARS_DURATION,
                StringKeyDosha.ASHTOTTARI_MERCURY_DURATION,
                StringKeyDosha.ASHTOTTARI_SATURN_DURATION,
                StringKeyDosha.ASHTOTTARI_JUPITER_DURATION,
                StringKeyDosha.ASHTOTTARI_RAHU_DURATION,
                StringKeyDosha.ASHTOTTARI_VENUS_DURATION
            )

            periods.chunked(2).forEach { row ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    row.forEach { period ->
                        Text(
                            stringResource(period),
                            color = AppTheme.TextSecondary,
                            fontSize = 12.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    if (row.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            HorizontalDivider(color = AppTheme.DividerColor)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                stringResource(StringKeyDosha.ASHTOTTARI_TOTAL_YEARS),
                color = AppTheme.AccentPrimary,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                stringResource(StringKeyDosha.ASHTOTTARI_PLANETS_USED),
                color = AppTheme.TextMuted,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun TimelineContent(
    result: AshtottariDashaResult,
    expandedIndex: Int,
    onExpandChange: (Int) -> Unit
) {
    val language = currentLanguage()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(result.mahadashas) { index, mahadasha ->
            MahadashaTimelineCard(
                mahadasha = mahadasha,
                isExpanded = expandedIndex == index,
                onExpandChange = { onExpandChange(index) },
                isCurrent = mahadasha.isCurrentlyRunning
            )
        }
    }
}

@Composable
private fun MahadashaTimelineCard(
    mahadasha: AshtottariMahadasha,
    isExpanded: Boolean,
    onExpandChange: () -> Unit,
    isCurrent: Boolean
) {
    val language = currentLanguage()
    val dateFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
    val antardashas = remember(mahadasha) {
        AshtottariDashaCalculator.calculateAntardashas(mahadasha)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpandChange() },
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent)
                AppTheme.AccentPrimary.copy(alpha = 0.15f)
            else
                AppTheme.CardBackground
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Planet indicator
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                getPlanetColor(mahadasha.planet).copy(alpha = 0.2f),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            mahadasha.planet.abbreviation,
                            color = getPlanetColor(mahadasha.planet),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                mahadasha.planet.getLocalizedName(language),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                                color = AppTheme.TextPrimary
                            )
                            if (isCurrent) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    color = AppTheme.SuccessColor.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        stringResource(StringKeyDosha.CURRENT_LABEL),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        color = AppTheme.SuccessColor,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                        Text(
                            "${String.format("%.1f", mahadasha.actualYears)} ${stringResource(StringKey.YEARS)}",
                            color = AppTheme.TextMuted,
                            fontSize = 12.sp
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            mahadasha.startDate.format(dateFormatter),
                            color = AppTheme.TextMuted,
                            fontSize = 11.sp
                        )
                        Text(
                            mahadasha.endDate.format(dateFormatter),
                            color = AppTheme.TextMuted,
                            fontSize = 11.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                        contentDescription = null,
                        tint = AppTheme.TextMuted
                    )
                }
            }

            // Progress if current
            if (isCurrent) {
                Spacer(modifier = Modifier.height(8.dp))
                val progress = calculateMahadashaProgress(mahadasha)
                LinearProgressIndicator(
                    progress = { progress.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp)),
                    color = AppTheme.AccentPrimary,
                    trackColor = AppTheme.AccentPrimary.copy(alpha = 0.2f)
                )
            }

            // Expanded content - Antardashas
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        stringResource(StringKey.DASHA_ANTARDASHA),
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    antardashas.forEach { antardasha ->
                        AntardashaRow(antardasha)
                    }
                }
            }
        }
    }
}

@Composable
private fun AntardashaRow(antardasha: AshtottariAntardasha) {
    val language = currentLanguage()
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yy")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(
                if (antardasha.isCurrentlyRunning)
                    AppTheme.AccentPrimary.copy(alpha = 0.1f)
                else
                    Color.Transparent,
                RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                antardasha.antardashaLord.getLocalizedName(language),
                color = if (antardasha.isCurrentlyRunning) AppTheme.AccentPrimary else AppTheme.TextSecondary,
                fontWeight = if (antardasha.isCurrentlyRunning) FontWeight.Medium else FontWeight.Normal,
                fontSize = 13.sp
            )
            if (antardasha.isCurrentlyRunning) {
                Spacer(modifier = Modifier.width(6.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(AppTheme.SuccessColor, CircleShape)
                )
            }
        }
        Text(
            "${antardasha.startDate.format(dateFormatter)} - ${antardasha.endDate.format(dateFormatter)}",
            color = AppTheme.TextMuted,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun InterpretationContent(result: AshtottariDashaResult) {
    val language = currentLanguage()
    val interpretation = result.interpretation

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Key Themes
        if (interpretation.keyThemes.isNotEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            stringResource(StringKeyDosha.KEY_THEMES),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = AppTheme.TextPrimary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(interpretation.keyThemes) { theme ->
                                Surface(
                                    color = AppTheme.AccentPrimary.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        theme,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        color = AppTheme.AccentPrimary,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Combined Effects
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        stringResource(StringKeyDosha.SUDARSHANA_COMBINED_ANALYSIS),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        interpretation.combinedEffects,
                        color = AppTheme.TextSecondary,
                        fontSize = 14.sp,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Mahadasha Effects
        if (interpretation.mahadashaEffects.isNotEmpty()) {
            item {
                EffectsCard(
                    title = stringResource(StringKey.DASHA_MAHADASHA) + " " + stringResource(StringKeyDosha.EFFECTS_LABEL),
                    effects = interpretation.mahadashaEffects,
                    icon = Icons.Filled.Star
                )
            }
        }

        // Antardasha Effects
        if (interpretation.antardashaEffects.isNotEmpty()) {
            item {
                EffectsCard(
                    title = stringResource(StringKey.DASHA_ANTARDASHA) + " " + stringResource(StringKeyDosha.EFFECTS_LABEL),
                    effects = interpretation.antardashaEffects,
                    icon = Icons.Outlined.StarBorder
                )
            }
        }

        // Recommendations
        if (interpretation.recommendations.isNotEmpty()) {
            item {
                RecommendationsCard(interpretation.recommendations)
            }
        }
    }
}

@Composable
private fun EffectsCard(
    title: String,
    effects: List<String>,
    icon: ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            effects.forEach { effect ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text("•", color = AppTheme.AccentPrimary, modifier = Modifier.padding(end = 8.dp))
                    Text(
                        effect,
                        color = AppTheme.TextSecondary,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun RecommendationsCard(recommendations: List<String>) {
    val language = currentLanguage()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.SuccessColor.copy(alpha = 0.1f)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Lightbulb,
                    contentDescription = null,
                    tint = AppTheme.SuccessColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyDosha.SCREEN_RECOMMENDATIONS),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            recommendations.forEachIndexed { index, rec ->
                Row(
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        "${index + 1}.",
                        color = AppTheme.SuccessColor,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        rec,
                        color = AppTheme.TextSecondary,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun AshtottariInfoDialog(onDismiss: () -> Unit) {
    val language = currentLanguage()

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppTheme.CardBackground,
        title = {
            Text(
                stringResource(StringKeyDosha.ASHTOTTARI_ABOUT),
                fontWeight = FontWeight.Bold,
                color = AppTheme.TextPrimary
            )
        },
        text = {
            Text(
                stringResource(StringKeyDosha.ASHTOTTARI_ABOUT_DESC),
                color = AppTheme.TextSecondary,
                lineHeight = 22.sp
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(StringKey.BTN_CLOSE), color = AppTheme.AccentPrimary)
            }
        }
    )
}

// Helper functions
private fun calculateMahadashaProgress(mahadasha: AshtottariMahadasha): Double {
    val now = LocalDateTime.now()
    if (now.isBefore(mahadasha.startDate)) return 0.0
    if (now.isAfter(mahadasha.endDate)) return 1.0

    val totalDays = ChronoUnit.DAYS.between(mahadasha.startDate, mahadasha.endDate).toDouble()
    val elapsedDays = ChronoUnit.DAYS.between(mahadasha.startDate, now).toDouble()
    return (elapsedDays / totalDays).coerceIn(0.0, 1.0)
}

@Composable
private fun getPlanetColor(planet: Planet): Color {
    return when (planet) {
        Planet.SUN -> Color(0xFFFF6B35)
        Planet.MOON -> Color(0xFF4ECDC4)
        Planet.MARS -> Color(0xFFE63946)
        Planet.MERCURY -> Color(0xFF45B7D1)
        Planet.JUPITER -> Color(0xFFF9C74F)
        Planet.VENUS -> Color(0xFFFF69B4)
        Planet.SATURN -> Color(0xFF6C757D)
        Planet.RAHU -> Color(0xFF6366F1)
        Planet.KETU -> Color(0xFF8B5CF6)
        else -> AppTheme.AccentPrimary
    }
}
