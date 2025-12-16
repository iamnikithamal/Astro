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
import com.astro.storm.data.localization.currentLanguage
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.SudarshanaChakraDashaCalculator
import com.astro.storm.ephemeris.SudarshanaChakraResult
import com.astro.storm.ephemeris.ChakraAnalysis
import com.astro.storm.ephemeris.SudarshanaReference
import com.astro.storm.ephemeris.StrengthLevel
import com.astro.storm.ephemeris.InfluenceNature
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.Period

/**
 * Sudarshana Chakra Dasha Screen
 *
 * Comprehensive triple-reference timing system display showing:
 * - Lagna Chakra: Physical body, self, and initiative
 * - Chandra Chakra: Mind, emotions, and public image
 * - Surya Chakra: Soul, authority, and recognition
 *
 * Each reference progresses one house per year, creating a synchronized
 * triple view of life events based on classical Sudarshana Chakra principles.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SudarshanaChakraScreen(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    if (chart == null) {
        EmptyChartScreen(
            title = stringResource(StringKeyDosha.SUDARSHANA_TITLE),
            message = stringResource(StringKey.NO_PROFILE_MESSAGE),
            onBack = onBack
        )
        return
    }

    val language = currentLanguage()
    var showInfoDialog by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }
    var isCalculating by remember { mutableStateOf(true) }
    var chakraResult by remember { mutableStateOf<SudarshanaChakraResult?>(null) }
    var selectedAge by remember { mutableIntStateOf(0) }

    // Calculate current age
    val currentAge = remember(chart) {
        Period.between(chart.birthData.dateTime.toLocalDate(), LocalDate.now()).years
    }

    // Initialize selectedAge with current age
    LaunchedEffect(currentAge) {
        selectedAge = currentAge
    }

    val tabs = listOf(
        stringResource(StringKeyDosha.SUDARSHANA_TRIPLE_VIEW),
        stringResource(StringKeyDosha.SCREEN_TIMELINE),
        stringResource(StringKeyDosha.SCREEN_INTERPRETATION)
    )

    // Calculate Sudarshana Chakra
    LaunchedEffect(chart, selectedAge) {
        isCalculating = true
        delay(200)
        try {
            chakraResult = withContext(Dispatchers.Default) {
                SudarshanaChakraDashaCalculator.calculateSudarshanaChakra(chart, selectedAge)
            }
        } catch (e: Exception) {
            // Handle error silently
        }
        isCalculating = false
    }

    if (showInfoDialog) {
        SudarshanaInfoDialog(onDismiss = { showInfoDialog = false })
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(StringKeyDosha.SUDARSHANA_TITLE),
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
                            contentDescription = stringResource(StringKeyDosha.SUDARSHANA_ABOUT),
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
        } else if (chakraResult != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Age Selector
                AgeSelector(
                    currentAge = currentAge,
                    selectedAge = selectedAge,
                    onAgeSelected = { selectedAge = it }
                )

                // Tab selector
                TabSelector(
                    tabs = tabs,
                    selectedIndex = selectedTab,
                    onTabSelected = { selectedTab = it }
                )

                // Content based on selected tab
                when (selectedTab) {
                    0 -> TripleViewContent(chakraResult!!)
                    1 -> TimelineContent(chart, currentAge)
                    2 -> SynthesisContent(chakraResult!!)
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
private fun AgeSelector(
    currentAge: Int,
    selectedAge: Int,
    onAgeSelected: (Int) -> Unit
) {
    val language = currentLanguage()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
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
                Text(
                    stringResource(StringKeyDosha.SUDARSHANA_AGE),
                    color = AppTheme.TextMuted,
                    fontSize = 12.sp
                )
                Text(
                    String.format(stringResource(StringKeyDosha.SUDARSHANA_YEAR_ANALYSIS), selectedAge),
                    color = AppTheme.TextPrimary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Age slider
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { if (selectedAge > 1) onAgeSelected(selectedAge - 1) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Filled.Remove,
                        contentDescription = null,
                        tint = AppTheme.TextSecondary
                    )
                }

                Slider(
                    value = selectedAge.toFloat(),
                    onValueChange = { onAgeSelected(it.toInt()) },
                    valueRange = 1f..100f,
                    modifier = Modifier.weight(1f),
                    colors = SliderDefaults.colors(
                        thumbColor = AppTheme.AccentPrimary,
                        activeTrackColor = AppTheme.AccentPrimary,
                        inactiveTrackColor = AppTheme.AccentPrimary.copy(alpha = 0.2f)
                    )
                )

                IconButton(
                    onClick = { if (selectedAge < 100) onAgeSelected(selectedAge + 1) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = null,
                        tint = AppTheme.TextSecondary
                    )
                }
            }

            // Current age quick button
            if (selectedAge != currentAge) {
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = { onAgeSelected(currentAge) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "${stringResource(StringKeyDosha.CURRENT_LABEL)}: ${currentAge} ${stringResource(StringKey.YEARS)}",
                        color = AppTheme.AccentPrimary,
                        fontSize = 12.sp
                    )
                }
            }
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
            .padding(horizontal = 16.dp, vertical = 4.dp),
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
private fun TripleViewContent(result: SudarshanaChakraResult) {
    val language = currentLanguage()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Current Signs Overview
        item {
            CurrentSignsCard(result)
        }

        // Lagna Chakra
        item {
            ChakraCard(
                chakra = result.lagnaChakra,
                title = stringResource(StringKeyDosha.SUDARSHANA_LAGNA_CHAKRA),
                subtitle = stringResource(StringKeyDosha.SUDARSHANA_LAGNA_INFLUENCE),
                color = Color(0xFF6366F1)
            )
        }

        // Chandra Chakra
        item {
            ChakraCard(
                chakra = result.chandraChakra,
                title = stringResource(StringKeyDosha.SUDARSHANA_CHANDRA_CHAKRA),
                subtitle = stringResource(StringKeyDosha.SUDARSHANA_CHANDRA_INFLUENCE),
                color = Color(0xFF4ECDC4)
            )
        }

        // Surya Chakra
        item {
            ChakraCard(
                chakra = result.suryaChakra,
                title = stringResource(StringKeyDosha.SUDARSHANA_SURYA_CHAKRA),
                subtitle = stringResource(StringKeyDosha.SUDARSHANA_SURYA_INFLUENCE),
                color = Color(0xFFFF6B35)
            )
        }

        // Convergence Analysis
        item {
            ConvergenceCard(result)
        }
    }
}

@Composable
private fun CurrentSignsCard(result: SudarshanaChakraResult) {
    val language = currentLanguage()

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
                    Icons.Filled.Visibility,
                    contentDescription = null,
                    tint = AppTheme.AccentPrimary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyDosha.SUDARSHANA_CURRENT_SIGNS),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SignIndicator(
                    label = stringResource(StringKeyDosha.LAGNA_LABEL),
                    sign = result.lagnaChakra.activeSign,
                    house = result.lagnaChakra.activeHouse,
                    color = Color(0xFF6366F1)
                )
                SignIndicator(
                    label = stringResource(StringKeyDosha.MOON_LABEL),
                    sign = result.chandraChakra.activeSign,
                    house = result.chandraChakra.activeHouse,
                    color = Color(0xFF4ECDC4)
                )
                SignIndicator(
                    label = stringResource(StringKeyDosha.SUN_LABEL),
                    sign = result.suryaChakra.activeSign,
                    house = result.suryaChakra.activeHouse,
                    color = Color(0xFFFF6B35)
                )
            }
        }
    }
}

@Composable
private fun SignIndicator(
    label: String,
    sign: ZodiacSign,
    house: Int,
    color: Color
) {
    val language = currentLanguage()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(color.copy(alpha = 0.2f), CircleShape)
                .border(2.dp, color, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                sign.symbol,
                fontSize = 24.sp,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            label,
            fontSize = 11.sp,
            color = AppTheme.TextMuted
        )
        Text(
            sign.getLocalizedName(language),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            color = AppTheme.TextPrimary
        )
        Text(
            "H${house}",
            fontSize = 11.sp,
            color = color
        )
    }
}

@Composable
private fun ChakraCard(
    chakra: ChakraAnalysis,
    title: String,
    subtitle: String,
    color: Color
) {
    val language = currentLanguage()
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(color, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            title,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            subtitle,
                            fontSize = 11.sp,
                            color = AppTheme.TextMuted,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                StrengthBadge(chakra.strength.level, color)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sign and Lord Info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoColumn(
                    label = stringResource(StringKeyDosha.SIGN_LABEL),
                    value = chakra.activeSign.getLocalizedName(language)
                )
                InfoColumn(
                    label = stringResource(StringKeyDosha.SUDARSHANA_SIGN_LORD),
                    value = chakra.signLord.getLocalizedName(language)
                )
                InfoColumn(
                    label = stringResource(StringKeyDosha.HOUSE_LABEL),
                    value = "H${chakra.activeHouse}"
                )
            }

            // Strength Progress
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(StringKeyDosha.STRENGTH_LABEL),
                    color = AppTheme.TextMuted,
                    fontSize = 12.sp
                )
                Text(
                    "${chakra.strength.score.toInt()}%",
                    color = color,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { (chakra.strength.score / 100).toFloat() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = color,
                trackColor = color.copy(alpha = 0.2f)
            )

            // Expanded content
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor)
                    Spacer(modifier = Modifier.height(12.dp))

                    // House Significance
                    Text(
                        stringResource(StringKeyDosha.SUDARSHANA_HOUSE_SIGNIFICATIONS),
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        chakra.houseSignificance.description,
                        fontSize = 13.sp,
                        color = AppTheme.TextMuted,
                        lineHeight = 20.sp
                    )

                    // Planets in Sign
                    if (chakra.planetsInSign.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            stringResource(StringKeyDosha.SUDARSHANA_PLANETS_IN_SIGN),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.TextSecondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(chakra.planetsInSign) { position ->
                                PlanetChip(position.planet)
                            }
                        }
                    }

                    // Aspecting Planets
                    if (chakra.aspectingPlanets.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            stringResource(StringKeyDosha.SUDARSHANA_ASPECTS_RECEIVED),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.TextSecondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        chakra.aspectingPlanets.forEach { aspect ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp)
                            ) {
                                Text(
                                    "• ${aspect.planet.getLocalizedName(language)} ",
                                    color = AppTheme.TextMuted,
                                    fontSize = 12.sp
                                )
                                Text(
                                    "from ${aspect.fromSign.getLocalizedName(language)}",
                                    color = AppTheme.TextMuted,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    // Sign Effects
                    if (chakra.signEffects.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            stringResource(StringKeyDosha.EFFECTS_LABEL),
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = AppTheme.TextSecondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        chakra.signEffects.forEach { effect ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp)
                            ) {
                                Text("• ", color = color)
                                Text(
                                    effect,
                                    color = AppTheme.TextMuted,
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }

            // Expand/Collapse indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    if (isExpanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = null,
                    tint = AppTheme.TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun InfoColumn(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            label,
            fontSize = 11.sp,
            color = AppTheme.TextMuted
        )
        Text(
            value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = AppTheme.TextPrimary
        )
    }
}

@Composable
private fun StrengthBadge(level: StrengthLevel, color: Color) {
    val (text, badgeColor) = when (level) {
        StrengthLevel.EXCELLENT -> stringResource(StringKeyDosha.PANCHA_EXCELLENT) to AppTheme.SuccessColor
        StrengthLevel.GOOD -> stringResource(StringKeyDosha.PANCHA_GOOD) to AppTheme.SuccessColor.copy(alpha = 0.8f)
        StrengthLevel.MODERATE -> stringResource(StringKeyDosha.PANCHA_AVERAGE) to AppTheme.WarningColor
        StrengthLevel.WEAK -> stringResource(StringKeyDosha.PANCHA_BELOW_AVERAGE) to AppTheme.WarningColor.copy(alpha = 0.8f)
        StrengthLevel.VERY_WEAK -> stringResource(StringKeyDosha.PANCHA_WEAK) to AppTheme.ErrorColor
    }

    Surface(
        color = badgeColor.copy(alpha = 0.15f),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            text,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = badgeColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun PlanetChip(planet: Planet) {
    val language = currentLanguage()

    Surface(
        color = getPlanetColor(planet).copy(alpha = 0.15f),
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(
            planet.getLocalizedName(language),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = getPlanetColor(planet),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ConvergenceCard(result: SudarshanaChakraResult) {
    val language = currentLanguage()
    val synthesis = result.synthesis

    val convergenceStrength = when {
        synthesis.combinedStrengthScore >= 65 -> stringResource(StringKeyDosha.SUDARSHANA_STRONG_CONVERGENCE)
        else -> stringResource(StringKeyDosha.SUDARSHANA_WEAK_CONVERGENCE)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (synthesis.combinedStrengthScore >= 60)
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
                    Icons.Filled.Hub,
                    contentDescription = null,
                    tint = if (synthesis.combinedStrengthScore >= 60) AppTheme.SuccessColor else AppTheme.WarningColor,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    stringResource(StringKeyDosha.SUDARSHANA_CONVERGENCE),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Convergence status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    convergenceStrength,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
                Text(
                    "${synthesis.combinedStrengthScore.toInt()}%",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (synthesis.combinedStrengthScore >= 60) AppTheme.SuccessColor else AppTheme.WarningColor
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Common themes
            if (synthesis.commonThemes.isNotEmpty()) {
                synthesis.commonThemes.forEach { theme ->
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text("✓ ", color = AppTheme.SuccessColor)
                        Text(
                            theme,
                            color = AppTheme.TextSecondary,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // Conflicting themes
            if (synthesis.conflictingThemes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                synthesis.conflictingThemes.forEach { theme ->
                    Row(modifier = Modifier.padding(vertical = 2.dp)) {
                        Text("⚠ ", color = AppTheme.WarningColor)
                        Text(
                            theme,
                            color = AppTheme.TextSecondary,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TimelineContent(chart: VedicChart, currentAge: Int) {
    val language = currentLanguage()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Show a range of years around current age
        val startAge = (currentAge - 5).coerceAtLeast(1)
        val endAge = (currentAge + 10).coerceAtMost(100)

        items(endAge - startAge + 1) { index ->
            val age = startAge + index
            val isCurrent = age == currentAge

            TimelineYearCard(
                chart = chart,
                age = age,
                isCurrent = isCurrent
            )
        }
    }
}

@Composable
private fun TimelineYearCard(
    chart: VedicChart,
    age: Int,
    isCurrent: Boolean
) {
    val language = currentLanguage()
    var result by remember { mutableStateOf<SudarshanaChakraResult?>(null) }

    LaunchedEffect(age) {
        result = try {
            SudarshanaChakraDashaCalculator.calculateSudarshanaChakra(chart, age)
        } catch (e: Exception) {
            null
        }
    }

    result?.let { chakraResult ->
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (isCurrent)
                    AppTheme.AccentPrimary.copy(alpha = 0.15f)
                else
                    AppTheme.CardBackground
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Age indicator
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.width(48.dp)
                ) {
                    Text(
                        "$age",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = if (isCurrent) AppTheme.AccentPrimary else AppTheme.TextPrimary
                    )
                    Text(
                        stringResource(StringKey.YEARS),
                        fontSize = 10.sp,
                        color = AppTheme.TextMuted
                    )
                    if (isCurrent) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(AppTheme.SuccessColor, CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Signs
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        SignSmall("L", chakraResult.lagnaChakra.activeSign, Color(0xFF6366F1))
                        SignSmall("M", chakraResult.chandraChakra.activeSign, Color(0xFF4ECDC4))
                        SignSmall("S", chakraResult.suryaChakra.activeSign, Color(0xFFFF6B35))
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    // Strength bar
                    LinearProgressIndicator(
                        progress = { (chakraResult.synthesis.combinedStrengthScore / 100).toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(4.dp)
                            .clip(RoundedCornerShape(2.dp)),
                        color = getStrengthColor(chakraResult.synthesis.combinedStrengthScore),
                        trackColor = AppTheme.DividerColor
                    )
                }
            }
        }
    }
}

@Composable
private fun SignSmall(prefix: String, sign: ZodiacSign, color: Color) {
    val language = currentLanguage()

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            prefix,
            fontSize = 10.sp,
            color = color,
            fontWeight = FontWeight.Bold
        )
        Text(
            ":",
            fontSize = 10.sp,
            color = AppTheme.TextMuted
        )
        Text(
            sign.abbreviation,
            fontSize = 12.sp,
            color = AppTheme.TextSecondary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun SynthesisContent(result: SudarshanaChakraResult) {
    val language = currentLanguage()
    val synthesis = result.synthesis

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Overall Assessment
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
                        synthesis.overallAssessment,
                        fontSize = 14.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 22.sp
                    )
                }
            }
        }

        // Three Chakra Contributions
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        stringResource(StringKeyDosha.SUDARSHANA_TRIPLE_VIEW),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    ContributionRow(
                        color = Color(0xFF6366F1),
                        label = stringResource(StringKeyDosha.SUDARSHANA_LAGNA_CHAKRA),
                        contribution = synthesis.lagnaContribution
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ContributionRow(
                        color = Color(0xFF4ECDC4),
                        label = stringResource(StringKeyDosha.SUDARSHANA_CHANDRA_CHAKRA),
                        contribution = synthesis.chandraContribution
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ContributionRow(
                        color = Color(0xFFFF6B35),
                        label = stringResource(StringKeyDosha.SUDARSHANA_SURYA_CHAKRA),
                        contribution = synthesis.suryaContribution
                    )
                }
            }
        }

        // Primary and Secondary Focus
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppTheme.AccentPrimary.copy(alpha = 0.1f)
                ),
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

                    Row(modifier = Modifier.padding(vertical = 4.dp)) {
                        Text(
                            "${stringResource(StringKeyDosha.PRIMARY_LABEL)}: ",
                            color = AppTheme.AccentPrimary,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp
                        )
                        Text(
                            synthesis.primaryFocus,
                            color = AppTheme.TextSecondary,
                            fontSize = 13.sp
                        )
                    }

                    if (synthesis.secondaryFocus.isNotBlank()) {
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text(
                                "${stringResource(StringKeyDosha.SECONDARY_LABEL)}: ",
                                color = AppTheme.AccentSecondary,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp
                            )
                            Text(
                                synthesis.secondaryFocus,
                                color = AppTheme.TextSecondary,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        // Recommendations
        if (result.recommendations.isNotEmpty()) {
            item {
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
                        result.recommendations.forEachIndexed { index, rec ->
                            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                                Text(
                                    "${index + 1}. ",
                                    color = AppTheme.SuccessColor,
                                    fontWeight = FontWeight.Medium
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
        }

        // Yearly Progression
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        stringResource(StringKeyDosha.SCREEN_TIMELINE),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    result.yearlyProgression.previousYearSummary?.let { prev ->
                        ProgressionRow(
                            label = stringResource(StringKeyDosha.PREVIOUS_LABEL),
                            summary = prev,
                            isPast = true
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    ProgressionRow(
                        label = stringResource(StringKeyDosha.CURRENT_LABEL),
                        summary = result.yearlyProgression.currentYearSummary,
                        isCurrent = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProgressionRow(
                        label = stringResource(StringKeyDosha.NEXT_LABEL),
                        summary = result.yearlyProgression.nextYearSummary,
                        isFuture = true
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        result.yearlyProgression.trend,
                        fontSize = 13.sp,
                        color = AppTheme.TextMuted,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }
    }
}

@Composable
private fun ContributionRow(color: Color, label: String, contribution: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .offset(y = 6.dp)
                .background(color, CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                label,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                color = color
            )
            Text(
                contribution,
                fontSize = 12.sp,
                color = AppTheme.TextMuted,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun ProgressionRow(
    label: String,
    summary: String,
    isPast: Boolean = false,
    isCurrent: Boolean = false,
    isFuture: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (isCurrent) AppTheme.AccentPrimary.copy(alpha = 0.1f) else Color.Transparent,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            label,
            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
            fontSize = 12.sp,
            color = when {
                isCurrent -> AppTheme.AccentPrimary
                isPast -> AppTheme.TextMuted
                else -> AppTheme.TextSecondary
            },
            modifier = Modifier.width(60.dp)
        )
        Text(
            summary,
            fontSize = 12.sp,
            color = if (isCurrent) AppTheme.TextPrimary else AppTheme.TextMuted,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun SudarshanaInfoDialog(onDismiss: () -> Unit) {
    val language = currentLanguage()

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = AppTheme.CardBackground,
        title = {
            Text(
                stringResource(StringKeyDosha.SUDARSHANA_ABOUT),
                fontWeight = FontWeight.Bold,
                color = AppTheme.TextPrimary
            )
        },
        text = {
            Text(
                stringResource(StringKeyDosha.SUDARSHANA_ABOUT_DESC),
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
        else -> Color(0xFF6B7280)
    }
}

private fun getStrengthColor(score: Double): Color {
    return when {
        score >= 70 -> Color(0xFF4ECDC4)
        score >= 50 -> Color(0xFFF9C74F)
        else -> Color(0xFFE63946)
    }
}
