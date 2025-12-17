package com.astro.storm.ui.screen.dasha

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.FilterCenterFocus
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.SudarshanaTimeline
import com.astro.storm.ephemeris.YearlyAnalysis
import com.astro.storm.ui.screen.chartdetail.ChartDetailColors
import com.astro.storm.ui.theme.AppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Sudarshana Chakra Dasha Tab Content
 *
 * Displays the triple-reference Sudarshana Chakra system with:
 * - Simultaneous view from Lagna, Moon, and Sun perspectives
 * - Current year analysis
 * - Combined interpretation and predictions
 * - Yearly progression display
 */
@Composable
fun SudarshanaChakraTabContent(
    timeline: SudarshanaTimeline
) {
    var showInfoExpanded by rememberSaveable { mutableStateOf(false) }
    var expandedYears by rememberSaveable { mutableStateOf(setOf<Int>()) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Current Year Card
        item(key = "current_year") {
            SudarshanaCurrentYearCard(timeline = timeline)
        }

        // Triple Reference Overview
        item(key = "triple_reference") {
            SudarshanaTripleReferenceCard(timeline = timeline)
        }

        // System Info Card
        item(key = "system_info") {
            SudarshanaInfoCard(
                isExpanded = showInfoExpanded,
                onToggleExpand = { showInfoExpanded = it }
            )
        }

        // Yearly Analysis Header
        item(key = "yearly_header") {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Yearly Progression",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Text(
                    text = "${timeline.yearlyAnalysis.size} years",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted
                )
            }
        }

        // Yearly Analysis Cards
        items(
            items = timeline.yearlyAnalysis.take(24),
            key = { "sudarshana_year_${it.year}" }
        ) { yearData ->
            val isExpanded = yearData.year in expandedYears
            val isCurrent = yearData.year == LocalDate.now().year

            SudarshanaYearCard(
                yearData = yearData,
                isCurrent = isCurrent,
                isExpanded = isExpanded,
                onToggleExpand = { expanded ->
                    expandedYears = if (expanded) {
                        expandedYears + yearData.year
                    } else {
                        expandedYears - yearData.year
                    }
                }
            )
        }

        // Bottom spacer
        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun SudarshanaCurrentYearCard(
    timeline: SudarshanaTimeline
) {
    val currentYear = LocalDate.now().year
    val currentYearData = timeline.yearlyAnalysis.find { it.year == currentYear }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = AppTheme.AccentTeal.copy(alpha = 0.1f),
                spotColor = AppTheme.AccentTeal.copy(alpha = 0.1f)
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
                                    AppTheme.AccentTeal.copy(alpha = 0.2f),
                                    AppTheme.AccentTeal.copy(alpha = 0.1f)
                                )
                            ),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.FilterCenterFocus,
                        contentDescription = null,
                        tint = AppTheme.AccentTeal,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = "Sudarshana Chakra - $currentYear",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary,
                        letterSpacing = (-0.3).sp
                    )
                    Text(
                        text = "Triple-Reference Annual Analysis",
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Current year analysis
            if (currentYearData != null) {
                // Three reference points
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    ReferencePointCard(
                        title = "Lagna",
                        sign = currentYearData.lagnaSign,
                        color = AppTheme.AccentPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    ReferencePointCard(
                        title = "Moon",
                        sign = currentYearData.moonSign,
                        color = AppTheme.LifeAreaLove,
                        modifier = Modifier.weight(1f)
                    )
                    ReferencePointCard(
                        title = "Sun",
                        sign = currentYearData.sunSign,
                        color = AppTheme.AccentGold,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Year score
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = AppTheme.CardBackgroundElevated
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "OVERALL YEAR STRENGTH",
                                fontSize = 11.sp,
                                color = AppTheme.TextMuted,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.5.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            LinearProgressIndicator(
                                progress = { (currentYearData.combinedStrength / 100.0).toFloat().coerceIn(0f, 1f) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = getStrengthColor(currentYearData.combinedStrength),
                                trackColor = AppTheme.DividerColor
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${currentYearData.combinedStrength.toInt()}%",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = getStrengthColor(currentYearData.combinedStrength)
                            )
                            Text(
                                text = getStrengthLabel(currentYearData.combinedStrength),
                                fontSize = 11.sp,
                                color = AppTheme.TextMuted
                            )
                        }
                    }
                }

                HorizontalDivider(
                    color = AppTheme.DividerColor,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Combined interpretation
                if (currentYearData.combinedEffects.isNotEmpty()) {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        color = AppTheme.CardBackgroundElevated
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Outlined.AutoAwesome,
                                    contentDescription = null,
                                    tint = AppTheme.AccentTeal,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Year Themes",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppTheme.AccentTeal
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            currentYearData.combinedEffects.take(4).forEach { effect ->
                                Row(
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "•",
                                        fontSize = 13.sp,
                                        color = AppTheme.AccentTeal,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = effect,
                                        fontSize = 13.sp,
                                        color = AppTheme.TextPrimary,
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "Current year analysis not available",
                    fontSize = 14.sp,
                    color = AppTheme.TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ReferencePointCard(
    title: String,
    sign: ZodiacSign,
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
                text = title.uppercase(),
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = color,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = sign.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = sign.displayName,
                fontSize = 11.sp,
                color = AppTheme.TextSecondary,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun SudarshanaTripleReferenceCard(
    timeline: SudarshanaTimeline
) {
    val language = LocalLanguage.current

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
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(AppTheme.LifeAreaSpiritual.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Star,
                        contentDescription = null,
                        tint = AppTheme.LifeAreaSpiritual,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Birth Reference Points",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "Starting positions for chakra progression",
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted
                    )
                }
            }

            // Birth reference data
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BirthReferenceItem(
                    label = "Lagna",
                    sign = timeline.lagnaSign,
                    color = AppTheme.AccentPrimary,
                    modifier = Modifier.weight(1f)
                )
                BirthReferenceItem(
                    label = "Moon",
                    sign = timeline.moonSign,
                    color = AppTheme.LifeAreaLove,
                    modifier = Modifier.weight(1f)
                )
                BirthReferenceItem(
                    label = "Sun",
                    sign = timeline.sunSign,
                    color = AppTheme.AccentGold,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun BirthReferenceItem(
    label: String,
    sign: ZodiacSign,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = AppTheme.CardBackgroundElevated
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(color.copy(alpha = 0.15f), CircleShape)
                    .border(1.5.dp, color.copy(alpha = 0.3f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = sign.symbol,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = AppTheme.TextMuted,
                letterSpacing = 0.5.sp
            )
            Text(
                text = sign.displayName,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun SudarshanaInfoCard(
    isExpanded: Boolean,
    onToggleExpand: (Boolean) -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(250),
        label = "infoRotation"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple()
            ) { onToggleExpand(!isExpanded) },
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
                .animateContentSize(animationSpec = tween(250))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(AppTheme.AccentTeal.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = null,
                            tint = AppTheme.AccentTeal,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "About Sudarshana Chakra",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
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
                Column(modifier = Modifier.padding(top = 18.dp)) {
                    Text(
                        text = "Sudarshana Chakra Dasha is a unique annual timing system that analyzes each year from three simultaneous reference points: Lagna (Ascendant), Moon, and Sun. The signs progress one year at a time from each reference point, creating a comprehensive view of yearly influences.",
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = AppTheme.CardBackgroundElevated
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Three Perspectives",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextSecondary
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            PerspectiveInfoRow(
                                title = "Lagna Chakra",
                                description = "Physical body, appearance, overall life direction",
                                color = AppTheme.AccentPrimary
                            )
                            PerspectiveInfoRow(
                                title = "Moon Chakra",
                                description = "Mind, emotions, mother, public image",
                                color = AppTheme.LifeAreaLove
                            )
                            PerspectiveInfoRow(
                                title = "Sun Chakra",
                                description = "Soul, authority, father, career recognition",
                                color = AppTheme.AccentGold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "The combined analysis of all three chakras gives the most accurate prediction of annual themes and events.",
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted,
                        lineHeight = 18.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun PerspectiveInfoRow(
    title: String,
    description: String,
    color: Color
) {
    Row(
        modifier = Modifier.padding(vertical = 6.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(color, CircleShape)
                .padding(top = 4.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = color
            )
            Text(
                text = description,
                fontSize = 11.sp,
                color = AppTheme.TextMuted
            )
        }
    }
}

@Composable
private fun SudarshanaYearCard(
    yearData: YearlyAnalysis,
    isCurrent: Boolean,
    isExpanded: Boolean,
    onToggleExpand: (Boolean) -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(250),
        label = "yearRotation"
    )

    val strengthColor = getStrengthColor(yearData.combinedStrength)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = strengthColor.copy(alpha = 0.3f))
            ) { onToggleExpand(!isExpanded) },
        shape = RoundedCornerShape(16.dp),
        color = if (isCurrent) strengthColor.copy(alpha = 0.08f) else AppTheme.CardBackground
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
                    // Year badge
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isCurrent) strengthColor else AppTheme.CardBackgroundElevated
                    ) {
                        Text(
                            text = yearData.year.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCurrent) Color.White else AppTheme.TextPrimary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Age ${yearData.age}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                            if (isCurrent) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(4.dp),
                                    color = strengthColor.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = "CURRENT",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = strengthColor,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = "${yearData.lagnaSign.displayName} / ${yearData.moonSign.displayName} / ${yearData.sunSign.displayName}",
                            fontSize = 11.sp,
                            color = AppTheme.TextMuted,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${yearData.combinedStrength.toInt()}%",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = strengthColor
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        tint = AppTheme.TextMuted,
                        modifier = Modifier
                            .size(22.dp)
                            .rotate(rotation)
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(tween(250)) + fadeIn(tween(250)),
                exit = shrinkVertically(tween(200)) + fadeOut(tween(150))
            ) {
                Column(modifier = Modifier.padding(top = 14.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor, modifier = Modifier.padding(bottom = 14.dp))

                    // Sign details
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        YearSignDetail(
                            label = "Lagna",
                            sign = yearData.lagnaSign,
                            color = AppTheme.AccentPrimary,
                            modifier = Modifier.weight(1f)
                        )
                        YearSignDetail(
                            label = "Moon",
                            sign = yearData.moonSign,
                            color = AppTheme.LifeAreaLove,
                            modifier = Modifier.weight(1f)
                        )
                        YearSignDetail(
                            label = "Sun",
                            sign = yearData.sunSign,
                            color = AppTheme.AccentGold,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Effects
                    if (yearData.combinedEffects.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Year Themes",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextSecondary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        yearData.combinedEffects.take(3).forEach { effect ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "•",
                                    fontSize = 12.sp,
                                    color = strengthColor,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = effect,
                                    fontSize = 12.sp,
                                    color = AppTheme.TextPrimary,
                                    lineHeight = 17.sp
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
private fun YearSignDetail(
    label: String,
    sign: ZodiacSign,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        color = AppTheme.CardBackgroundElevated
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = color,
                letterSpacing = 0.3.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = sign.symbol,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = sign.displayName,
                fontSize = 10.sp,
                color = AppTheme.TextMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
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

private fun getStrengthLabel(strength: Double): String {
    return when {
        strength >= 70 -> "Excellent"
        strength >= 50 -> "Good"
        strength >= 30 -> "Moderate"
        else -> "Challenging"
    }
}
