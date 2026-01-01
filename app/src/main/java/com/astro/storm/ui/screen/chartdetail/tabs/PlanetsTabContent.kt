package com.astro.storm.ui.screen.chartdetail.tabs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.localized
import com.astro.storm.data.localization.localizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.Nakshatra
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.PlanetaryShadbala
import com.astro.storm.ephemeris.RetrogradeCombustionCalculator
import com.astro.storm.ephemeris.ShadbalaCalculator
import com.astro.storm.ui.screen.chartdetail.ChartDetailColors
import com.astro.storm.ui.screen.chartdetail.ChartDetailUtils
import com.astro.storm.ui.screen.chartdetail.components.ConditionChip
import com.astro.storm.ui.screen.chartdetail.components.StyledDivider
import java.text.DecimalFormat

enum class DignityStatus(val stringKey: com.astro.storm.data.localization.StringKeyInterface) {
    EXALTED(StringKey.DIGNITY_EXALTED_STATUS),
    DEBILITATED(StringKey.DIGNITY_DEBILITATED_STATUS),
    OWN_SIGN(StringKey.DIGNITY_OWN_SIGN_STATUS),
    NEUTRAL(StringKey.DIGNITY_NEUTRAL_STATUS);

    val isSignificant: Boolean get() = this != NEUTRAL
}

/**
 * Get localized display name for dignity status
 */
@Composable
fun DignityStatus.localizedDisplayName(): String = stringResource(stringKey)

private object PlanetaryDignityCalculator {

    private val exaltationSigns: Map<Planet, ZodiacSign> = mapOf(
        Planet.SUN to ZodiacSign.ARIES,
        Planet.MOON to ZodiacSign.TAURUS,
        Planet.MARS to ZodiacSign.CAPRICORN,
        Planet.MERCURY to ZodiacSign.VIRGO,
        Planet.JUPITER to ZodiacSign.CANCER,
        Planet.VENUS to ZodiacSign.PISCES,
        Planet.SATURN to ZodiacSign.LIBRA
    )

    private val debilitationSigns: Map<Planet, ZodiacSign> = mapOf(
        Planet.SUN to ZodiacSign.LIBRA,
        Planet.MOON to ZodiacSign.SCORPIO,
        Planet.MARS to ZodiacSign.CANCER,
        Planet.MERCURY to ZodiacSign.PISCES,
        Planet.JUPITER to ZodiacSign.CAPRICORN,
        Planet.VENUS to ZodiacSign.VIRGO,
        Planet.SATURN to ZodiacSign.ARIES
    )

    fun calculate(planet: Planet, sign: ZodiacSign): DignityStatus = when {
        exaltationSigns[planet] == sign -> DignityStatus.EXALTED
        debilitationSigns[planet] == sign -> DignityStatus.DEBILITATED
        sign.ruler == planet -> DignityStatus.OWN_SIGN
        else -> DignityStatus.NEUTRAL
    }

    fun getColor(status: DignityStatus): Color = when (status) {
        DignityStatus.EXALTED -> ChartDetailColors.SuccessColor
        DignityStatus.DEBILITATED -> ChartDetailColors.ErrorColor
        DignityStatus.OWN_SIGN -> ChartDetailColors.AccentGold
        DignityStatus.NEUTRAL -> ChartDetailColors.TextSecondary
    }
}

private object DecimalFormatters {
    val oneDecimal: DecimalFormat by lazy { DecimalFormat("0.0") }
    val twoDecimals: DecimalFormat by lazy { DecimalFormat("0.00") }
}

@Stable
private data class PlanetCardState(
    val position: PlanetPosition,
    val shadbala: PlanetaryShadbala?,
    val conditions: RetrogradeCombustionCalculator.PlanetCondition?,
    val dignityStatus: DignityStatus
) {
    val hasConditions: Boolean
        get() = shadbala != null ||
                dignityStatus.isSignificant ||
                position.isRetrograde ||
                conditions?.combustionStatus != RetrogradeCombustionCalculator.CombustionStatus.NOT_COMBUST ||
                conditions?.isInPlanetaryWar == true
}

@Composable
fun PlanetsTabContent(
    chart: VedicChart,
    onPlanetClick: (PlanetPosition) -> Unit,
    onNakshatraClick: (Nakshatra, Int) -> Unit = { _, _ -> }
) {
    val planetConditions = remember(chart) {
        RetrogradeCombustionCalculator.analyzePlanetaryConditions(chart)
    }

    val shadbala = remember(chart) {
        ShadbalaCalculator.calculateShadbala(chart)
    }

    val planetCardStates by remember(chart, planetConditions, shadbala) {
        derivedStateOf {
            chart.planetPositions.map { position ->
                PlanetCardState(
                    position = position,
                    shadbala = shadbala.planetaryStrengths[position.planet],
                    conditions = planetConditions.getCondition(position.planet),
                    dignityStatus = PlanetaryDignityCalculator.calculate(
                        position.planet,
                        position.sign
                    )
                )
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "conditions_summary") {
            PlanetaryConditionsSummary(conditions = planetConditions)
        }

        items(
            items = planetCardStates,
            key = { it.position.planet.name }
        ) { cardState ->
            PlanetDetailCard(
                state = cardState,
                onClick = { onPlanetClick(cardState.position) },
                onNakshatraClick = {
                    onNakshatraClick(
                        cardState.position.nakshatra,
                        cardState.position.nakshatraPada
                    )
                }
            )
        }
    }
}

@Composable
private fun PlanetaryConditionsSummary(
    conditions: RetrogradeCombustionCalculator.PlanetaryConditionAnalysis
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = ChartDetailColors.CardBackground
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SectionHeader(
                icon = Icons.Outlined.Info,
                title = stringResource(StringKeyAnalysis.PLANETS_CONDITIONS),
                iconTint = ChartDetailColors.AccentPurple
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ConditionStatBadge(
                    count = conditions.retrogradePlanets.size,
                    label = stringResource(StringKeyAnalysis.PLANETS_RETROGRADE),
                    color = ChartDetailColors.WarningColor
                )
                ConditionStatBadge(
                    count = conditions.combustPlanets.size,
                    label = stringResource(StringKeyAnalysis.PLANETS_COMBUST),
                    color = ChartDetailColors.ErrorColor
                )
                ConditionStatBadge(
                    count = conditions.planetaryWars.size,
                    label = stringResource(StringKeyAnalysis.PLANETS_AT_WAR),
                    color = ChartDetailColors.AccentPurple
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = ChartDetailColors.TextPrimary
        )
    }
}

@Composable
private fun ConditionStatBadge(
    count: Int,
    label: String,
    color: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.semantics(mergeDescendants = true) {
            contentDescription = "$count planets $label"
        }
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = color.copy(alpha = 0.15f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = count.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = ChartDetailColors.TextMuted
        )
    }
}

@Composable
private fun PlanetDetailCard(
    state: PlanetCardState,
    onClick: () -> Unit,
    onNakshatraClick: () -> Unit
) {
    val position = state.position
    val planetColor = ChartDetailColors.getPlanetColor(position.planet)
    val dignityDisplayName = state.dignityStatus.localizedDisplayName()

    // Hoist all localized strings for accessibility (outside non-Composable lambda)
    val planetName = position.planet.localizedName()
    val signName = position.sign.localizedName()
    val nakshatraName = position.nakshatra.localizedName()
    val houseNum = position.house.localized()
    val padaNum = position.nakshatraPada.localized()
    val planetInSignStr = stringResource(StringKey.PLANET_IN_SIGN_ACCESSIBILITY, planetName, signName)
    val houseStr = stringResource(StringKey.CHART_HOUSE)
    val nakshatraPadaStr = stringResource(StringKey.NAKSHATRA_PADA_ACCESSIBILITY, nakshatraName, padaNum)
    val retrogradeStr = stringResource(StringKey.PLANET_RETROGRADE)

    Surface(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .semantics {
                role = Role.Button
                contentDescription = buildString {
                    append(planetInSignStr)
                    append(", $houseStr $houseNum")
                    append(", $nakshatraPadaStr")
                    if (position.isRetrograde) append(", $retrogradeStr")
                    if (state.dignityStatus.isSignificant) append(", $dignityDisplayName")
                }
            },
        shape = RoundedCornerShape(16.dp),
        color = ChartDetailColors.CardBackground
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
        ) {
            PlanetCardHeader(
                position = position,
                planetColor = planetColor,
                onNakshatraClick = onNakshatraClick
            )

            AnimatedVisibility(
                visible = state.hasConditions,
                enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
                exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Top)
            ) {
                Column {
                    StyledDivider(verticalPadding = 12.dp)

                    state.shadbala?.let { shadbala ->
                        ShadbalaProgressSection(shadbala = shadbala)
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    ConditionChipsSection(
                        dignityStatus = state.dignityStatus,
                        isRetrograde = position.isRetrograde,
                        conditions = state.conditions
                    )
                }
            }

            TapForDetailsHint()
        }
    }
}

@Composable
private fun PlanetCardHeader(
    position: PlanetPosition,
    planetColor: Color,
    onNakshatraClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            PlanetSymbolBadge(
                symbol = position.planet.symbol,
                color = planetColor
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = position.planet.localizedName(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ChartDetailColors.TextPrimary
                )
                Text(
                    text = "${position.sign.localizedName()} • ${stringResource(StringKey.CHART_HOUSE)} ${position.house.localized()}",
                    fontSize = 12.sp,
                    color = ChartDetailColors.TextSecondary
                )
            }
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = ChartDetailUtils.formatDegreeInSign(position.longitude),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = ChartDetailColors.AccentTeal
            )
            NakshatraChip(
                nakshatra = position.nakshatra,
                pada = position.nakshatraPada,
                onClick = onNakshatraClick
            )
        }
    }
}

@Composable
private fun PlanetSymbolBadge(
    symbol: String,
    color: Color,
    size: Int = 42
) {
    Box(
        modifier = Modifier
            .size(size.dp)
            .background(color = color, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            fontSize = (size * 0.43).sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun NakshatraChip(
    nakshatra: Nakshatra,
    pada: Int,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        color = ChartDetailColors.CardBackgroundElevated
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${nakshatra.localizedName()} ${pada.localized()}",
                fontSize = 11.sp,
                color = ChartDetailColors.AccentPurple
            )
            Spacer(modifier = Modifier.width(2.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(StringKey.ACC_VIEW_DETAILS),
                tint = ChartDetailColors.AccentPurple,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Composable
private fun ShadbalaProgressSection(shadbala: PlanetaryShadbala) {
    val progress = remember(shadbala.percentageOfRequired) {
        (shadbala.percentageOfRequired / 150.0).coerceIn(0.0, 1.0).toFloat()
    }
    val strengthColor = ChartDetailColors.getStrengthColor(shadbala.percentageOfRequired)

    val formattedTotal = remember(shadbala.totalRupas) {
        DecimalFormatters.twoDecimals.format(shadbala.totalRupas)
    }
    val formattedRequired = remember(shadbala.requiredRupas) {
        DecimalFormatters.twoDecimals.format(shadbala.requiredRupas)
    }
    val formattedPercentage = remember(shadbala.percentageOfRequired) {
        DecimalFormatters.oneDecimal.format(shadbala.percentageOfRequired)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(StringKeyAnalysis.PLANETS_SHADBALA),
                fontSize = 12.sp,
                color = ChartDetailColors.TextSecondary
            )
            Text(
                text = shadbala.strengthRating.localizedName(),
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = strengthColor
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = strengthColor,
            trackColor = ChartDetailColors.DividerColor
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "$formattedTotal / $formattedRequired ${stringResource(StringKey.UNIT_RUPAS)} ($formattedPercentage%)",
            fontSize = 10.sp,
            color = ChartDetailColors.TextMuted
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ConditionChipsSection(
    dignityStatus: DignityStatus,
    isRetrograde: Boolean,
    conditions: RetrogradeCombustionCalculator.PlanetCondition?
) {
    val showDignity = dignityStatus.isSignificant
    val showRetrograde = isRetrograde
    val showCombust = conditions?.combustionStatus != null &&
            conditions.combustionStatus != RetrogradeCombustionCalculator.CombustionStatus.NOT_COMBUST
    val showWar = conditions?.isInPlanetaryWar == true

    val hasAnyChip = showDignity || showRetrograde || showCombust || showWar

    if (!hasAnyChip) return

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (showDignity) {
            ConditionChip(
                label = dignityStatus.localizedDisplayName(),
                color = PlanetaryDignityCalculator.getColor(dignityStatus)
            )
        }

        if (showRetrograde) {
            ConditionChip(
                label = stringResource(StringKeyAnalysis.PLANETS_RETROGRADE),
                color = ChartDetailColors.WarningColor
            )
        }

        if (showCombust && conditions != null) {
            ConditionChip(
                label = conditions.combustionStatus.displayName,
                color = ChartDetailColors.ErrorColor
            )
        }

        if (showWar) {
            ConditionChip(
                label = stringResource(StringKeyAnalysis.PLANETS_PLANETARY_WAR),
                color = ChartDetailColors.AccentPurple
            )
        }
    }
}

@Composable
private fun TapForDetailsHint() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(StringKey.ACC_VIEW_DETAILS),
            fontSize = 11.sp,
            color = ChartDetailColors.TextMuted
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            tint = ChartDetailColors.TextMuted,
            modifier = Modifier.size(16.dp)
        )
    }
}