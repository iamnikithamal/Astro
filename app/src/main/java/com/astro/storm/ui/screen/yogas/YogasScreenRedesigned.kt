package com.astro.storm.ui.screen.yogas

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
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Diamond
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.TipsAndUpdates
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.YogaCalculator
import com.astro.storm.ui.screen.chartdetail.ChartDetailColors
import com.astro.storm.ui.theme.AppTheme

/**
 * Redesigned Yogas Screen
 *
 * A modern, clean, and professional UI for displaying Vedic yogas with:
 * - Summary statistics card
 * - Category filter chips
 * - Expandable yoga cards with full details
 * - Visual strength indicators
 * - Smooth animations throughout
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YogasScreenRedesigned(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    val language = LocalLanguage.current
    var selectedCategory by rememberSaveable { mutableStateOf<YogaCalculator.YogaCategory?>(null) }
    var expandedYogaKeys by rememberSaveable { mutableStateOf(setOf<String>()) }

    // Calculate yogas
    val yogaAnalysis = remember(chart) {
        chart?.let {
            try {
                YogaCalculator.calculateYogas(it)
            } catch (e: Exception) {
                null
            }
        }
    }

    val filteredYogas = remember(yogaAnalysis, selectedCategory) {
        if (yogaAnalysis == null) return@remember emptyList()
        if (selectedCategory == null) {
            yogaAnalysis.allYogas.sortedByDescending { it.strengthPercentage }
        } else {
            yogaAnalysis.allYogas
                .filter { it.category == selectedCategory }
                .sortedByDescending { it.strengthPercentage }
        }
    }

    val categoryStats = remember(yogaAnalysis) {
        if (yogaAnalysis == null) return@remember emptyMap()
        yogaAnalysis.allYogas.groupBy { it.category }.mapValues { it.value.size }
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            YogasTopBar(
                chartName = chart?.birthData?.name ?: "",
                totalYogas = yogaAnalysis?.allYogas?.size ?: 0,
                onBack = onBack
            )
        }
    ) { paddingValues ->
        if (chart == null || yogaAnalysis == null) {
            EmptyYogasContent(modifier = Modifier.padding(paddingValues))
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(AppTheme.ScreenBackground),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Summary Card
                item(key = "summary") {
                    YogasSummaryCard(analysis = yogaAnalysis)
                }

                // Category Filter
                item(key = "category_filter") {
                    YogaCategoryFilter(
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it },
                        categoryStats = categoryStats
                    )
                }

                // Category header when filtered
                if (selectedCategory != null) {
                    item(key = "category_header") {
                        CategoryHeader(
                            category = selectedCategory!!,
                            count = filteredYogas.size
                        )
                    }
                }

                // Yoga cards
                itemsIndexed(
                    items = filteredYogas,
                    key = { index, yoga -> "yoga_${index}_${yoga.name}_${yoga.category}" }
                ) { index, yoga ->
                    val yogaKey = "${index}_${yoga.name}_${yoga.category}"
                    val isExpanded = yogaKey in expandedYogaKeys

                    YogaCard(
                        yoga = yoga,
                        isExpanded = isExpanded,
                        onToggleExpand = { expanded ->
                            val key = yogaKey
                            expandedYogaKeys = if (expanded) {
                                expandedYogaKeys + key
                            } else {
                                expandedYogaKeys - key
                            }
                        }
                    )
                }

                // Bottom spacer
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun YogasTopBar(
    chartName: String,
    totalYogas: Int,
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
                        text = stringResource(StringKey.FEATURE_YOGAS),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    if (chartName.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "$totalYogas yogas found - $chartName",
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
private fun YogasSummaryCard(
    analysis: YogaCalculator.YogaAnalysis
) {
    val language = LocalLanguage.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = AppTheme.AccentGold.copy(alpha = 0.1f),
                spotColor = AppTheme.AccentGold.copy(alpha = 0.1f)
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
                                    AppTheme.AccentGold.copy(alpha = 0.2f),
                                    AppTheme.AccentGold.copy(alpha = 0.1f)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.AutoAwesome,
                        contentDescription = null,
                        tint = AppTheme.AccentGold,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = stringResource(StringKey.YOGA_ANALYSIS_SUMMARY),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary,
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = stringResource(StringKey.YOGA_SUBTITLE),
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                YogaStatItem(
                    value = analysis.allYogas.size.toString(),
                    label = stringResource(StringKey.YOGA_TOTAL),
                    color = AppTheme.AccentPrimary,
                    modifier = Modifier.weight(1f)
                )
                YogaStatItem(
                    value = analysis.allYogas.count { it.isAuspicious }.toString(),
                    label = stringResource(StringKey.YOGA_AUSPICIOUS),
                    color = AppTheme.SuccessColor,
                    modifier = Modifier.weight(1f)
                )
                YogaStatItem(
                    value = "${analysis.overallYogaStrength.toInt()}%",
                    label = stringResource(StringKey.YOGA_STRENGTH),
                    color = AppTheme.AccentGold,
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
                            text = stringResource(StringKey.YOGA_OVERALL_STRENGTH),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = AppTheme.TextMuted
                        )
                        Text(
                            text = "${analysis.overallYogaStrength.toInt()}%",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = getStrengthColor(analysis.overallYogaStrength)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    LinearProgressIndicator(
                        progress = { (analysis.overallYogaStrength / 100.0).coerceIn(0.0, 1.0).toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = getStrengthColor(analysis.overallYogaStrength),
                        trackColor = AppTheme.DividerColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Dominant category
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(StringKey.YOGA_DOMINANT_CATEGORY),
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted
                )
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = getCategoryColor(analysis.dominantYogaCategory).copy(alpha = 0.15f)
                ) {
                    Text(
                        text = analysis.dominantYogaCategory.getLocalizedName(language),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = getCategoryColor(analysis.dominantYogaCategory),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun YogaStatItem(
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
                fontSize = 22.sp,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun YogaCategoryFilter(
    selectedCategory: YogaCalculator.YogaCategory?,
    onCategorySelected: (YogaCalculator.YogaCategory?) -> Unit,
    categoryStats: Map<YogaCalculator.YogaCategory, Int>
) {
    val language = LocalLanguage.current

    Column {
        Text(
            text = stringResource(StringKey.YOGA_FILTER_BY_CATEGORY),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.TextSecondary,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // All filter
            item {
                val isSelected = selectedCategory == null
                val chipColor by animateColorAsState(
                    targetValue = if (isSelected) AppTheme.AccentPrimary else Color.Transparent,
                    animationSpec = tween(200),
                    label = "allChipColor"
                )

                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onCategorySelected(null) },
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) AppTheme.AccentPrimary.copy(alpha = 0.15f) else AppTheme.CardBackground,
                    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, AppTheme.BorderColor)
                ) {
                    Text(
                        text = "All (${categoryStats.values.sum()})",
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        color = if (isSelected) AppTheme.AccentPrimary else AppTheme.TextSecondary,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }

            // Category filters
            items(YogaCalculator.YogaCategory.entries.filter { categoryStats.containsKey(it) }) { category ->
                val isSelected = selectedCategory == category
                val categoryColor = getCategoryColor(category)
                val count = categoryStats[category] ?: 0

                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onCategorySelected(if (isSelected) null else category) },
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) categoryColor.copy(alpha = 0.15f) else AppTheme.CardBackground,
                    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, AppTheme.BorderColor)
                ) {
                    Text(
                        text = "${category.getLocalizedName(language)} ($count)",
                        fontSize = 13.sp,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                        color = if (isSelected) categoryColor else AppTheme.TextSecondary,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CategoryHeader(
    category: YogaCalculator.YogaCategory,
    count: Int
) {
    val language = LocalLanguage.current
    val categoryColor = getCategoryColor(category)
    val yogasSuffix = stringResource(StringKey.YOGA_COUNT_SUFFIX)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(categoryColor, CircleShape)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = category.getLocalizedName(language),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = AppTheme.TextPrimary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Surface(
            shape = RoundedCornerShape(6.dp),
            color = AppTheme.CardBackgroundElevated
        ) {
            Text(
                text = "$count $yogasSuffix",
                fontSize = 11.sp,
                color = AppTheme.TextMuted,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun YogaCard(
    yoga: YogaCalculator.Yoga,
    isExpanded: Boolean,
    onToggleExpand: (Boolean) -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(250),
        label = "yogaRotation"
    )

    val language = LocalLanguage.current
    val categoryColor = getCategoryColor(yoga.category)
    val strengthColor = getStrengthColor(yoga.strengthPercentage)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = categoryColor.copy(alpha = 0.3f))
            ) { onToggleExpand(!isExpanded) },
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
                verticalAlignment = Alignment.Top
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.weight(1f)
                ) {
                    // Yoga icon with auspicious indicator
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(categoryColor.copy(alpha = 0.15f), RoundedCornerShape(12.dp))
                            .border(
                                width = if (yoga.isAuspicious) 1.5.dp else 0.dp,
                                color = if (yoga.isAuspicious) AppTheme.SuccessColor.copy(alpha = 0.5f) else Color.Transparent,
                                shape = RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = getCategoryIcon(yoga.category),
                            contentDescription = null,
                            tint = categoryColor,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = yoga.name,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (yoga.isAuspicious) {
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Outlined.Star,
                                    contentDescription = "Auspicious",
                                    tint = AppTheme.SuccessColor,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Text(
                            text = yoga.sanskritName,
                            fontSize = 12.sp,
                            color = AppTheme.TextMuted,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = categoryColor.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = yoga.category.getLocalizedName(language),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = categoryColor,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = strengthColor.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = "${yoga.strengthPercentage.toInt()}%",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = strengthColor,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }

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

                    // Planets involved
                    if (yoga.planets.isNotEmpty()) {
                        Text(
                            text = stringResource(StringKey.YOGA_PLANETS_INVOLVED),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextSecondary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            yoga.planets.forEach { planet ->
                                val planetColor = ChartDetailColors.getPlanetColor(planet)
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = planetColor.copy(alpha = 0.15f)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .background(planetColor, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = planet.symbol,
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = planet.getLocalizedName(language),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = planetColor
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    // Houses
                    if (yoga.houses.isNotEmpty()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(bottom = 10.dp)
                        ) {
                            Text(
                                text = stringResource(StringKey.YOGA_HOUSES_LABEL),
                                fontSize = 12.sp,
                                color = AppTheme.TextMuted
                            )
                            Text(
                                text = yoga.houses.joinToString(", ") { "${it}H" },
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                        }
                    }

                    // Strength indicator
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = AppTheme.CardBackgroundElevated
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(StringKey.YOGA_STRENGTH_LABEL),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = AppTheme.TextMuted
                                )
                                Text(
                                    text = yoga.strength.getLocalizedName(language),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = strengthColor
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { (yoga.strengthPercentage / 100.0).coerceIn(0.0, 1.0).toFloat() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = strengthColor,
                                trackColor = AppTheme.DividerColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Effects
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        color = AppTheme.CardBackgroundElevated
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Outlined.TipsAndUpdates,
                                    contentDescription = null,
                                    tint = categoryColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = stringResource(StringKey.YOGA_EFFECTS),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = categoryColor
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = yoga.effects,
                                fontSize = 13.sp,
                                color = AppTheme.TextPrimary,
                                lineHeight = 19.sp
                            )
                        }
                    }

                    // Activation period
                    if (yoga.activationPeriod.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = stringResource(StringKey.YOGA_ACTIVATION_LABEL),
                                fontSize = 12.sp,
                                color = AppTheme.TextMuted
                            )
                            Text(
                                text = yoga.activationPeriod,
                                fontSize = 12.sp,
                                color = AppTheme.TextPrimary
                            )
                        }
                    }

                    // Cancellation factors
                    if (yoga.cancellationFactors.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = stringResource(StringKey.YOGA_CANCELLATION_FACTORS),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.WarningColor,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        yoga.cancellationFactors.forEach { factor ->
                            Row(
                                modifier = Modifier.padding(vertical = 2.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "•",
                                    fontSize = 11.sp,
                                    color = AppTheme.WarningColor
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = factor,
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

@Composable
private fun EmptyYogasContent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.AutoAwesome,
                contentDescription = null,
                tint = AppTheme.TextMuted,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(StringKey.YOGA_NO_YOGAS_FOUND),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(StringKey.YOGA_NO_CHART_MESSAGE),
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun getCategoryColor(category: YogaCalculator.YogaCategory): Color {
    return when (category) {
        YogaCalculator.YogaCategory.RAJA_YOGA -> AppTheme.AccentGold
        YogaCalculator.YogaCategory.DHANA_YOGA -> AppTheme.LifeAreaFinance
        YogaCalculator.YogaCategory.MAHAPURUSHA_YOGA -> AppTheme.LifeAreaSpiritual
        YogaCalculator.YogaCategory.NABHASA_YOGA -> AppTheme.AccentTeal
        YogaCalculator.YogaCategory.CHANDRA_YOGA -> AppTheme.LifeAreaLove
        YogaCalculator.YogaCategory.SOLAR_YOGA -> AppTheme.PlanetSun
        YogaCalculator.YogaCategory.NEGATIVE_YOGA -> AppTheme.ErrorColor
        YogaCalculator.YogaCategory.SPECIAL_YOGA -> AppTheme.AccentPrimary
    }
}

private fun getCategoryIcon(category: YogaCalculator.YogaCategory): ImageVector {
    return when (category) {
        YogaCalculator.YogaCategory.RAJA_YOGA -> Icons.Outlined.WorkspacePremium
        YogaCalculator.YogaCategory.DHANA_YOGA -> Icons.Outlined.Diamond
        YogaCalculator.YogaCategory.MAHAPURUSHA_YOGA -> Icons.Outlined.Star
        YogaCalculator.YogaCategory.NABHASA_YOGA -> Icons.Outlined.AutoAwesome
        YogaCalculator.YogaCategory.CHANDRA_YOGA -> Icons.Outlined.StarOutline
        YogaCalculator.YogaCategory.SOLAR_YOGA -> Icons.Outlined.Star
        YogaCalculator.YogaCategory.NEGATIVE_YOGA -> Icons.Outlined.StarOutline
        YogaCalculator.YogaCategory.SPECIAL_YOGA -> Icons.Outlined.TipsAndUpdates
    }
}

@Composable
private fun getStrengthColor(strength: Double): Color {
    return when {
        strength >= 80 -> AppTheme.SuccessColor
        strength >= 60 -> AppTheme.AccentTeal
        strength >= 40 -> AppTheme.AccentGold
        strength >= 20 -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }
}
