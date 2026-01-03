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
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Timeline
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Nakshatra
import com.astro.storm.data.model.Planet
import com.astro.storm.ephemeris.AshtottariAntardasha
import com.astro.storm.ephemeris.AshtottariMahadasha
import com.astro.storm.ephemeris.AshtottariTimeline
import com.astro.storm.ui.screen.chartdetail.ChartDetailColors
import com.astro.storm.ui.theme.AppTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

/**
 * Ashtottari Dasha Tab Content
 *
 * Displays the 108-year Ashtottari Dasha cycle with:
 * - Current period card with all active levels
 * - Complete timeline visualization
 * - Expandable mahadasha periods with antardashas
 * - Information about the Ashtottari system
 */
@Composable
fun AshtottariDashaTabContent(
    timeline: AshtottariTimeline
) {
    val language = LocalLanguage.current
    var expandedMahadashaKeys by rememberSaveable { mutableStateOf(setOf<String>()) }
    var showInfoExpanded by rememberSaveable { mutableStateOf(false) }

    val currentMahadashaIndex = remember(timeline) {
        timeline.mahadashas.indexOfFirst { it == timeline.currentMahadasha }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Current Period Card
        item(key = "current_period") {
            AshtottariCurrentPeriodCard(timeline = timeline)
        }

        // System Info Card
        item(key = "system_info") {
            AshtottariInfoCard(
                isExpanded = showInfoExpanded,
                onToggleExpand = { showInfoExpanded = it }
            )
        }

        // Timeline Overview
        item(key = "timeline_overview") {
            AshtottariTimelineCard(timeline = timeline)
        }

        // Mahadasha List
        items(
            items = timeline.mahadashas,
            key = { md: AshtottariMahadasha -> "ashtottari_md_${md.planet.symbol}_${md.startDate}" }
        ) { mahadasha ->
            val mdKey = "${mahadasha.planet.symbol}_${mahadasha.startDate}"
            val isCurrent = mahadasha == timeline.currentMahadasha
            val isExpanded = mdKey in expandedMahadashaKeys

            AshtottariMahadashaCard(
                mahadasha = mahadasha,
                currentAntardasha = if (isCurrent) timeline.currentAntardasha else null,
                isCurrent = isCurrent,
                isExpanded = isExpanded,
                onToggleExpand = { expanded ->
                    expandedMahadashaKeys = if (expanded) {
                        expandedMahadashaKeys + mdKey
                    } else {
                        expandedMahadashaKeys - mdKey
                    }
                }
            )
        }

        // Bottom spacer
        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun AshtottariCurrentPeriodCard(
    timeline: AshtottariTimeline
) {
    val language = LocalLanguage.current
    val currentMD = timeline.currentMahadasha
    val currentAD = timeline.currentAntardasha

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
                        Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = AppTheme.AccentGold,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column {
                    Text(
                        text = stringResource(StringKeyDosha.ASHTOTTARI_CURRENT_TITLE),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary,
                        letterSpacing = (-0.3).sp
                    )
                    if (currentMD != null && currentAD != null) {
                        Text(
                            text = "${currentMD.planet.getLocalizedName(language)} - ${currentAD.antardashaLord.getLocalizedName(language)}",
                            fontSize = 12.sp,
                            color = AppTheme.TextMuted,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Birth Nakshatra Info
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = AppTheme.CardBackgroundElevated
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val nakshatraColor = AppTheme.AccentGold

                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(nakshatraColor.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = timeline.birthNakshatraLord.symbol,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = nakshatraColor
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(StringKeyDosha.ASHTOTTARI_BIRTH_NAKSHATRA_HEADER),
                            fontSize = 11.sp,
                            color = AppTheme.TextMuted,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.5.sp
                        )
                        Text(
                            text = "${timeline.birthNakshatra.getLocalizedName(language)} (Pada ${timeline.birthNakshatraPada})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = stringResource(StringKeyDosha.ASHTOTTARI_LORD_HEADER),
                            fontSize = 11.sp,
                            color = AppTheme.TextMuted,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = timeline.birthNakshatraLord.getLocalizedName(language),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = nakshatraColor
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Current periods
            if (currentMD != null) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    val now = LocalDateTime.now()
                    val mdTotalDuration = java.time.Duration.between(currentMD.startDate, currentMD.endDate).toMillis().toDouble()
                    val mdElapsedDuration = java.time.Duration.between(currentMD.startDate, now).toMillis().toDouble()
                    val mdProgressPercent = if (mdTotalDuration > 0) (mdElapsedDuration / mdTotalDuration * 100).coerceIn(0.0, 100.0) else 0.0

                    AshtottariPeriodRow(
                        label = stringResource(StringKeyDosha.ASHTOTTARI_MAHADASHA_LABEL),
                        planet = currentMD.planet,
                        startDate = currentMD.startDate,
                        endDate = currentMD.endDate,
                        progress = mdProgressPercent.toFloat() / 100f,
                        isMain = true
                    )

                    currentAD?.let { ad ->
                        val now = LocalDateTime.now()
                        val totalDuration = java.time.Duration.between(ad.startDate, ad.endDate).toMillis().toDouble()
                        val elapsedDuration = java.time.Duration.between(ad.startDate, now).toMillis().toDouble()
                        val progressPercent = if (totalDuration > 0) (elapsedDuration / totalDuration * 100).coerceIn(0.0, 100.0) else 0.0

                        AshtottariPeriodRow(
                            label = stringResource(StringKeyDosha.ASHTOTTARI_ANTARDASHA_LABEL),
                            planet = ad.antardashaLord,
                            startDate = ad.startDate,
                            endDate = ad.endDate,
                            progress = progressPercent.toFloat() / 100f,
                            isMain = false
                        )
                    }
                }

                HorizontalDivider(
                    color = AppTheme.DividerColor,
                    modifier = Modifier.padding(vertical = 18.dp)
                )

                // Period interpretation
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    color = AppTheme.CardBackgroundElevated
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Outlined.Star,
                                contentDescription = null,
                                tint = AppTheme.AccentGold,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(StringKeyDosha.ASHTOTTARI_PERIOD_INSIGHTS),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.AccentGold
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = getAshtottariInterpretation(currentMD.planet, currentAD?.planet, language),
                            fontSize = 13.sp,
                            color = AppTheme.TextPrimary,
                            lineHeight = 20.sp
                        )
                    }
                }
            } else {
                Text(
                    text = stringResource(StringKeyDosha.ASHTOTTARI_UNABLE_CALC),
                    fontSize = 14.sp,
                    color = AppTheme.TextMuted
                )
            }
        }
    }
}

@Composable
private fun AshtottariPeriodRow(
    label: String,
    planet: Planet,
    startDate: LocalDateTime,
    endDate: LocalDateTime,
    progress: Float,
    isMain: Boolean
) {
    val language = LocalLanguage.current
    val planetColor = ChartDetailColors.getPlanetColor(planet)
    val circleSize = if (isMain) 44.dp else 36.dp
    val fontSize = if (isMain) 16.sp else 14.sp

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(circleSize)
                .background(planetColor, CircleShape)
                .border(2.dp, planetColor.copy(alpha = 0.3f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = planet.symbol,
                fontSize = if (isMain) 17.sp else 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = planet.getLocalizedName(language),
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    color = planetColor
                )
            }
            Text(
                text = "${formatDate(startDate)} - ${formatDate(endDate)}",
                fontSize = 11.sp,
                color = AppTheme.TextMuted
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.width(70.dp)
        ) {
            Text(
                text = "${(progress * 100).toInt()}%",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = planetColor
            )
            Spacer(modifier = Modifier.height(5.dp))
            LinearProgressIndicator(
                progress = { progress.coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isMain) 6.dp else 5.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = planetColor,
                trackColor = AppTheme.DividerColor
            )
        }
    }
}

@Composable
private fun AshtottariInfoCard(
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
                            .background(AppTheme.AccentGold.copy(alpha = 0.15f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = null,
                            tint = AppTheme.AccentGold,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(StringKeyDosha.ASHTOTTARI_ABOUT_TITLE),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = if (isExpanded) stringResource(StringKey.ACC_COLLAPSE) else stringResource(StringKey.ACC_EXPAND),
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
                        text = stringResource(StringKeyDosha.ASHTOTTARI_ABOUT_DESC_FULL),
                        fontSize = 13.sp,
                        color = AppTheme.TextSecondary,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Period durations
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = AppTheme.CardBackgroundElevated
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = stringResource(StringKeyDosha.ASHTOTTARI_PERIOD_LIST_HEADER),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextSecondary
                            )
                            Spacer(modifier = Modifier.height(12.dp))

                            val periods = listOf(
                                Planet.SUN to 6,
                                Planet.MOON to 15,
                                Planet.MARS to 8,
                                Planet.MERCURY to 17,
                                Planet.SATURN to 10,
                                Planet.JUPITER to 19,
                                Planet.RAHU to 12,
                                Planet.VENUS to 21
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    periods.take(4).forEach { (planet, years) ->
                                        PeriodDurationRow(planet = planet, years = years)
                                    }
                                }
                                Spacer(modifier = Modifier.width(20.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    periods.drop(4).forEach { (planet, years) ->
                                        PeriodDurationRow(planet = planet, years = years)
                                    }
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
private fun PeriodDurationRow(planet: Planet, years: Int) {
    val language = LocalLanguage.current
    val planetColor = ChartDetailColors.getPlanetColor(planet)

    Row(
        modifier = Modifier.padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .background(planetColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = planet.symbol,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = planet.getLocalizedName(language),
            fontSize = 12.sp,
            color = AppTheme.TextPrimary,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "${years}y",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = AppTheme.TextMuted
        )
    }
}

@Composable
private fun AshtottariTimelineCard(
    timeline: AshtottariTimeline
) {
    val language = LocalLanguage.current
    val today = LocalDateTime.now()

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 18.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .background(AppTheme.AccentTeal.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Timeline,
                        contentDescription = null,
                        tint = AppTheme.AccentTeal,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = stringResource(StringKeyDosha.ASHTOTTARI_TIMELINE_TITLE),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = stringResource(StringKeyDosha.ASHTOTTARI_TIMELINE_DESC),
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted
                    )
                }
            }

            timeline.mahadashas.forEachIndexed { index, dasha ->
                val isPast = dasha.endDate.isBefore(today)
                val isCurrent = !today.isBefore(dasha.startDate) && today.isBefore(dasha.endDate)
                val planetColor = ChartDetailColors.getPlanetColor(dasha.planet)
                val isLast = index == timeline.mahadashas.lastIndex

                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.width(36.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(if (isCurrent) 32.dp else 26.dp)
                                .background(
                                    color = when {
                                        isCurrent -> planetColor
                                        isPast -> planetColor.copy(alpha = 0.35f)
                                        else -> planetColor.copy(alpha = 0.6f)
                                    },
                                    shape = CircleShape
                                )
                                .then(
                                    if (isCurrent) {
                                        Modifier.border(2.dp, planetColor.copy(alpha = 0.4f), CircleShape)
                                    } else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dasha.planet.symbol,
                                fontSize = if (isCurrent) 12.sp else 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        if (!isLast) {
                            Box(
                                modifier = Modifier
                                    .width(2.dp)
                                    .height(22.dp)
                                    .background(
                                        if (isPast || isCurrent) AppTheme.DividerColor
                                        else AppTheme.DividerColor.copy(alpha = 0.4f)
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = if (isLast) 0.dp else 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = dasha.planet.getLocalizedName(language),
                            fontSize = 13.sp,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                            color = when {
                                isCurrent -> planetColor
                                isPast -> AppTheme.TextMuted
                                else -> AppTheme.TextSecondary
                            },
                            modifier = Modifier.width(72.dp)
                        )

                        Text(
                            text = "${dasha.startDate.year} - ${dasha.endDate.year}",
                            fontSize = 12.sp,
                            color = if (isCurrent) AppTheme.TextPrimary else AppTheme.TextMuted,
                            modifier = Modifier.weight(1f)
                        )

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (isCurrent) planetColor.copy(alpha = 0.15f) else Color.Transparent
                        ) {
                            Text(
                                text = "${dasha.durationYears.toInt()}y",
                                fontSize = 11.sp,
                                fontWeight = if (isCurrent) FontWeight.SemiBold else FontWeight.Normal,
                                color = if (isCurrent) planetColor else AppTheme.TextMuted,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AshtottariMahadashaCard(
    mahadasha: AshtottariMahadasha,
    currentAntardasha: AshtottariAntardasha?,
    isCurrent: Boolean,
    isExpanded: Boolean,
    onToggleExpand: (Boolean) -> Unit
) {
    val rotation by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        animationSpec = tween(250),
        label = "expandRotation"
    )
    val planetColor = ChartDetailColors.getPlanetColor(mahadasha.planet)
    val language = LocalLanguage.current

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = planetColor.copy(alpha = 0.3f))
            ) { onToggleExpand(!isExpanded) },
        shape = RoundedCornerShape(18.dp),
        color = if (isCurrent) planetColor.copy(alpha = 0.08f) else AppTheme.CardBackground
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
                            .size(48.dp)
                            .background(planetColor, CircleShape)
                            .then(
                                if (isCurrent) Modifier.border(2.5.dp, planetColor.copy(alpha = 0.4f), CircleShape)
                                else Modifier
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = mahadasha.planet.symbol,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val mahadashaLabel = stringResource(StringKeyDosha.ASHTOTTARI_MAHADASHA_LABEL)
                            Text(
                                text = "${mahadasha.planet.getLocalizedName(language)} $mahadashaLabel",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                            if (isCurrent) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(5.dp),
                                    color = planetColor.copy(alpha = 0.2f)
                                ) {
                                    Text(
                                        text = stringResource(StringKey.DASHA_ACTIVE),
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = planetColor,
                                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
                                    )
                                }
                            }
                        }
                        Text(
                            text = "${mahadasha.durationYears.toInt()}y - ${formatDate(mahadasha.startDate)} to ${formatDate(mahadasha.endDate)}",
                            fontSize = 11.sp,
                            color = AppTheme.TextMuted,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) stringResource(StringKey.ACC_COLLAPSE) else stringResource(StringKey.ACC_EXPAND),
                    tint = AppTheme.TextMuted,
                    modifier = Modifier
                        .size(26.dp)
                        .rotate(rotation)
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(tween(250)) + fadeIn(tween(250)),
                exit = shrinkVertically(tween(200)) + fadeOut(tween(150))
            ) {
                Column(modifier = Modifier.padding(top = 16.dp)) {
                    HorizontalDivider(color = AppTheme.DividerColor, modifier = Modifier.padding(bottom = 14.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = stringResource(StringKeyDosha.ASHTOTTARI_ANTARDASHAS_TITLE),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextSecondary
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Surface(
                            shape = RoundedCornerShape(5.dp),
                            color = AppTheme.CardBackgroundElevated
                        ) {
                            Text(
                                text = StringResources.get(StringKeyDosha.ASHTOTTARI_SUB_PERIODS_FMT, language, mahadasha.antardashas.size),
                                fontSize = 10.sp,
                                color = AppTheme.TextMuted,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    mahadasha.antardashas.forEachIndexed { index, ad ->
                        val isCurrentAD = ad == currentAntardasha
                        AshtottariAntardashaRow(
                            antardasha = ad,
                            mahadashaPlanet = mahadasha.planet,
                            isCurrent = isCurrentAD,
                            modifier = Modifier.padding(top = if (index == 0) 0.dp else 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AshtottariAntardashaRow(
    antardasha: AshtottariAntardasha,
    mahadashaPlanet: Planet,
    isCurrent: Boolean,
    modifier: Modifier = Modifier
) {
    val planetColor = ChartDetailColors.getPlanetColor(antardasha.antardashaLord)
    val today = LocalDateTime.now()
    val isPast = antardasha.endDate.isBefore(today)
    val language = LocalLanguage.current

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = when {
                    isCurrent -> planetColor.copy(alpha = 0.12f)
                    else -> Color.Transparent
                },
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 10.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = if (isPast) planetColor.copy(alpha = 0.4f) else planetColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = antardasha.antardashaLord.symbol,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${mahadashaPlanet.symbol}-${antardasha.antardashaLord.getLocalizedName(language)}",
                        fontSize = 13.sp,
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                        color = when {
                            isCurrent -> planetColor
                            isPast -> AppTheme.TextMuted
                            else -> AppTheme.TextPrimary
                        }
                    )
                    if (isCurrent) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${antardasha.getProgressPercent().toInt()}%",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = planetColor.copy(alpha = 0.9f)
                        )
                    }
                }
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${formatShortDate(antardasha.startDate)} - ${formatShortDate(antardasha.endDate)}",
                fontSize = 11.sp,
                color = AppTheme.TextMuted
            )
        }
    }
}

private fun formatDate(date: LocalDateTime): String {
    return date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
}

private fun formatShortDate(date: LocalDateTime): String {
    return date.format(DateTimeFormatter.ofPattern("MMM yyyy"))
}

private fun getAshtottariInterpretation(mahadashaPlanet: Planet, antardashaPlanet: Planet?, language: com.astro.storm.data.localization.Language): String {
    val baseInterpretation = when (mahadashaPlanet) {
        Planet.SUN -> StringResources.get(StringKeyAnalysis.DASHA_INTERP_MAHADASHA_SUN, language)
        Planet.MOON -> StringResources.get(StringKeyAnalysis.DASHA_INTERP_MAHADASHA_MOON, language)
        Planet.MARS -> StringResources.get(StringKeyAnalysis.DASHA_INTERP_MAHADASHA_MARS, language)
        Planet.MERCURY -> StringResources.get(StringKeyAnalysis.DASHA_INTERP_MAHADASHA_MERCURY, language)
        Planet.JUPITER -> StringResources.get(StringKeyAnalysis.DASHA_INTERP_MAHADASHA_JUPITER, language)
        Planet.VENUS -> StringResources.get(StringKeyAnalysis.DASHA_INTERP_MAHADASHA_VENUS, language)
        Planet.SATURN -> StringResources.get(StringKeyAnalysis.DASHA_INTERP_MAHADASHA_SATURN, language)
        Planet.RAHU -> StringResources.get(StringKeyAnalysis.DASHA_INTERP_MAHADASHA_RAHU, language)
        else -> StringResources.get(StringKeyAnalysis.DASHA_INTERP_MAHADASHA_DEFAULT, language)
    }

    return if (antardashaPlanet != null && antardashaPlanet != mahadashaPlanet) {
        val subEffect = StringResources.get(StringKeyAnalysis.DASHA_SUB_PERIOD_MODIFIER, language, antardashaPlanet.getLocalizedName(language))
        "$baseInterpretation $subEffect"
    } else {
        baseInterpretation
    }
}
