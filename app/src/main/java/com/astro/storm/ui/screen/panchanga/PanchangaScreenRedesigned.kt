package com.astro.storm.ui.screen.panchanga

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Today
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.LocalLanguage
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.PanchangaCalculator
import com.astro.storm.ephemeris.PanchangaData
import com.astro.storm.ui.components.common.ModernPillTabRow
import com.astro.storm.ui.components.common.TabItem
import com.astro.storm.ui.theme.AppTheme
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Redesigned Panchanga Screen
 *
 * A modern, clean UI for displaying Panchanga (Hindu Calendar) with:
 * - Summary card with all five limbs
 * - Tab navigation (Today, Birth Day, Elements)
 * - Visual representations of each element
 * - Auspicious/inauspicious timing
 * - Detailed element breakdowns
 * - Smooth animations throughout
 */

enum class PanchangaViewType(val titleKey: StringKey) {
    TODAY(StringKey.TAB_TODAY),
    BIRTH(StringKey.TAB_BIRTH_DAY),
    ELEMENTS(StringKey.TAB_ELEMENTS)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanchangaScreenRedesigned(
    chart: VedicChart?,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val language = LocalLanguage.current
    var selectedTabIndex by rememberSaveable { mutableIntStateOf(0) }
    var showInfoDialog by remember { mutableStateOf(false) }

    // Calculate Panchanga for today
    val todayPanchanga = remember(chart) {
        chart?.let {
            try {
                val calculator = PanchangaCalculator(context)
                calculator.use { calc ->
                    calc.calculatePanchanga(
                        dateTime = LocalDateTime.now(),
                        latitude = it.birthData.latitude,
                        longitude = it.birthData.longitude,
                        timezone = it.birthData.timezone
                    )
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    // Calculate Panchanga for birth
    val birthPanchanga = remember(chart) {
        chart?.let {
            try {
                val calculator = PanchangaCalculator(context)
                calculator.use { calc ->
                    calc.calculatePanchanga(
                        dateTime = it.birthData.dateTime,
                        latitude = it.birthData.latitude,
                        longitude = it.birthData.longitude,
                        timezone = it.birthData.timezone
                    )
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    // Capture composable colors outside remember
    val accentPrimary = AppTheme.AccentPrimary
    val accentGold = AppTheme.AccentGold
    val accentTeal = AppTheme.AccentTeal

    // Get tab titles outside remember to avoid Composable calls inside remember
    val todayTitle = stringResource(PanchangaViewType.TODAY.titleKey, language)
    val birthTitle = stringResource(PanchangaViewType.BIRTH.titleKey, language)
    val elementsTitle = stringResource(PanchangaViewType.ELEMENTS.titleKey, language)

    val tabs = remember(accentPrimary, accentGold, accentTeal, todayTitle, birthTitle, elementsTitle) {
        listOf(
            TabItem(title = todayTitle, accentColor = accentPrimary),
            TabItem(title = birthTitle, accentColor = accentGold),
            TabItem(title = elementsTitle, accentColor = accentTeal)
        )
    }

    if (showInfoDialog) {
        PanchangaInfoDialog(onDismiss = { showInfoDialog = false })
    }

    Scaffold(
        containerColor = AppTheme.ScreenBackground,
        topBar = {
            PanchangaTopBar(
                chartName = chart?.birthData?.name ?: "",
                onBack = onBack,
                onInfoClick = { showInfoDialog = true }
            )
        }
    ) { paddingValues ->
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
                label = "PanchangaTabContent"
            ) { tabIndex ->
                when (PanchangaViewType.entries[tabIndex]) {
                    PanchangaViewType.TODAY -> {
                        TodayPanchangaContent(panchanga = todayPanchanga)
                    }
                    PanchangaViewType.BIRTH -> {
                        BirthPanchangaContent(
                            panchanga = birthPanchanga,
                            chart = chart
                        )
                    }
                    PanchangaViewType.ELEMENTS -> {
                        PanchangaElementsContent(
                            todayPanchanga = todayPanchanga,
                            birthPanchanga = birthPanchanga
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PanchangaTopBar(
    chartName: String,
    onBack: () -> Unit,
    onInfoClick: () -> Unit
) {
    Surface(
        color = AppTheme.ScreenBackground,
        shadowElevation = 2.dp
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = stringResource(StringKey.FEATURE_PANCHANGA),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = stringResource(StringKeyDosha.PANCHANGA_LIMBS_TITLE),
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextMuted
                    )
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
            actions = {
                IconButton(onClick = onInfoClick) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Panchanga info",
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
private fun TodayPanchangaContent(
    panchanga: PanchangaData?
) {
    if (panchanga == null) {
        EmptyPanchangaContent()
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Summary card
        item(key = "today_summary") {
            TodaySummaryCard(panchanga = panchanga)
        }

        // Five elements
        item(key = "five_elements") {
            FiveLimbsCard(panchanga = panchanga)
        }

        // Auspicious timing
        item(key = "auspicious_timing") {
            AuspiciousTimingCard(panchanga = panchanga)
        }

        // Sun/Moon info
        item(key = "sun_moon") {
            SunMoonCard(panchanga = panchanga)
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun TodaySummaryCard(
    panchanga: PanchangaData
) {
    val today = LocalDate.now()
    val dayOfWeek = today.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }

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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = today.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "$dayOfWeek - ${panchanga.vara}",
                        fontSize = 14.sp,
                        color = AppTheme.AccentPrimary,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Day quality indicator
                Box(
                    modifier = Modifier
                        .size(56.dp)
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
                        Icons.Outlined.Today,
                        contentDescription = null,
                        tint = AppTheme.AccentPrimary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Quick summary row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PanchangaQuickItem(
                    label = stringResource(StringKeyDosha.PANCHANGA_TITHI_LABEL),
                    value = panchanga.tithi.tithi.displayName,
                    icon = Icons.Outlined.DarkMode,
                    color = AppTheme.LifeAreaLove,
                    modifier = Modifier.weight(1f)
                )
                PanchangaQuickItem(
                    label = stringResource(StringKeyDosha.PANCHANGA_NAKSHATRA_LABEL),
                    value = panchanga.nakshatra.nakshatra.displayName,
                    icon = Icons.Outlined.Star,
                    color = AppTheme.AccentGold,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                PanchangaQuickItem(
                    label = stringResource(StringKeyDosha.PANCHANGA_YOGA_LABEL),
                    value = panchanga.yoga.yoga.displayName,
                    icon = Icons.Outlined.LightMode,
                    color = AppTheme.AccentTeal,
                    modifier = Modifier.weight(1f)
                )
                PanchangaQuickItem(
                    label = stringResource(StringKeyDosha.PANCHANGA_KARANA_LABEL),
                    value = panchanga.karana.karana.displayName,
                    icon = Icons.Outlined.Schedule,
                    color = AppTheme.LifeAreaSpiritual,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PanchangaQuickItem(
    label: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.1f)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = label.uppercase(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextMuted,
                    letterSpacing = 0.5.sp
                )
                Text(
                    text = value,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = color,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun FiveLimbsCard(
    panchanga: PanchangaData
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = stringResource(StringKeyDosha.PANCHANGA_FIVE_LIMBS),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 14.dp)
            )

            // Tithi
            PanchangaLimbRow(
                number = 1,
                name = stringResource(StringKeyDosha.PANCHANGA_TITHI_LABEL),
                sanskrit = "तिथि",
                value = panchanga.tithi.tithi.displayName,
                description = stringResource(StringKeyDosha.PANCHANGA_LUNAR_DAY_FMT, panchanga.paksha.displayName),
                color = AppTheme.LifeAreaLove
            )

            HorizontalDivider(color = AppTheme.DividerColor, modifier = Modifier.padding(vertical = 12.dp))

            // Vara
            PanchangaLimbRow(
                number = 2,
                name = stringResource(StringKeyDosha.PANCHANGA_VARA_LABEL),
                sanskrit = "वार",
                value = panchanga.vara.displayName,
                description = stringResource(StringKeyDosha.PANCHANGA_VARA_DESC),
                color = AppTheme.AccentPrimary
            )

            HorizontalDivider(color = AppTheme.DividerColor, modifier = Modifier.padding(vertical = 12.dp))

            // Nakshatra
            PanchangaLimbRow(
                number = 3,
                name = stringResource(StringKeyDosha.PANCHANGA_NAKSHATRA_LABEL),
                sanskrit = "नक्षत्र",
                value = panchanga.nakshatra.nakshatra.displayName,
                description = stringResource(StringKeyDosha.PANCHANGA_NAKSHATRA_DESC_FMT, panchanga.nakshatra.pada),
                color = AppTheme.AccentGold
            )

            HorizontalDivider(color = AppTheme.DividerColor, modifier = Modifier.padding(vertical = 12.dp))

            // Yoga
            PanchangaLimbRow(
                number = 4,
                name = stringResource(StringKeyDosha.PANCHANGA_YOGA_LABEL),
                sanskrit = "योग",
                value = panchanga.yoga.yoga.displayName,
                description = stringResource(StringKeyDosha.PANCHANGA_YOGA_DESC),
                color = AppTheme.AccentTeal
            )

            HorizontalDivider(color = AppTheme.DividerColor, modifier = Modifier.padding(vertical = 12.dp))

            // Karana
            PanchangaLimbRow(
                number = 5,
                name = stringResource(StringKeyDosha.PANCHANGA_KARANA_LABEL),
                sanskrit = "करण",
                value = panchanga.karana.karana.displayName,
                description = stringResource(StringKeyDosha.PANCHANGA_KARANA_DESC),
                color = AppTheme.LifeAreaSpiritual
            )
        }
    }
}

@Composable
private fun PanchangaLimbRow(
    number: Int,
    name: String,
    sanskrit: String,
    value: String,
    description: String,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Number badge
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(color.copy(alpha = 0.15f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = color
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = sanskrit,
                    fontSize = 12.sp,
                    color = AppTheme.TextMuted,
                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                )
            }
            Text(
                text = description,
                fontSize = 11.sp,
                color = AppTheme.TextMuted
            )
        }

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = color.copy(alpha = 0.1f)
        ) {
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = color,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun AuspiciousTimingCard(
    panchanga: PanchangaData
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = stringResource(StringKeyDosha.PANCHANGA_TIMING_AUSPICIOUSNESS),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 14.dp)
            )

            // Rahu Kaal - calculated from sunrise/sunset
            TimingRow(
                label = stringResource(StringKeyDosha.PANCHANGA_RAHU),
                value = stringResource(StringKeyDosha.PANCHANGA_CALCULATE_DAY),
                isInauspicious = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Yamagandam
            TimingRow(
                label = stringResource(StringKeyDosha.PANCHANGA_YAMAGANDAM),
                value = stringResource(StringKeyDosha.PANCHANGA_CALCULATE_DAY),
                isInauspicious = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Gulika Kaal
            TimingRow(
                label = stringResource(StringKeyDosha.PANCHANGA_GULIKA),
                value = stringResource(StringKeyDosha.PANCHANGA_CALCULATE_DAY),
                isInauspicious = true
            )

            HorizontalDivider(color = AppTheme.DividerColor, modifier = Modifier.padding(vertical = 14.dp))

            // Abhijit Muhurta
            TimingRow(
                label = stringResource(StringKeyDosha.PANCHANGA_ABHIJIT),
                value = stringResource(StringKeyDosha.PANCHANGA_MIDDAY_HOUR),
                isInauspicious = false
            )
        }
    }
}

@Composable
private fun TimingRow(
    label: String,
    value: String,
    isInauspicious: Boolean
) {
    val color = if (isInauspicious) AppTheme.WarningColor else AppTheme.SuccessColor

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color, CircleShape)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = label,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = AppTheme.TextPrimary
                )
                Text(
                    text = if (isInauspicious) "Avoid important activities" else "Auspicious timing",
                    fontSize = 10.sp,
                    color = AppTheme.TextMuted
                )
            }
        }

        Surface(
            shape = RoundedCornerShape(8.dp),
            color = color.copy(alpha = 0.1f)
        ) {
            Text(
                text = value,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = color,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
    }
}

@Composable
private fun SunMoonCard(
    panchanga: PanchangaData
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = stringResource(StringKeyDosha.PANCHANGA_SUN_MOON),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary,
                modifier = Modifier.padding(bottom = 14.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Sun times
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = AppTheme.PlanetSun.copy(alpha = 0.1f)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Outlined.LightMode,
                            contentDescription = null,
                            tint = AppTheme.PlanetSun,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(StringKeyDosha.PANCHANGA_SUN),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.PlanetSun
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        SunMoonTimeRow(label = stringResource(StringKeyDosha.PANCHANGA_RISE), time = panchanga.sunrise)
                        Spacer(modifier = Modifier.height(4.dp))
                        SunMoonTimeRow(label = stringResource(StringKeyDosha.PANCHANGA_SET), time = panchanga.sunset)
                    }
                }

                // Moon times
                Surface(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    color = AppTheme.LifeAreaLove.copy(alpha = 0.1f)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Outlined.DarkMode,
                            contentDescription = null,
                            tint = AppTheme.LifeAreaLove,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(StringKeyDosha.PANCHANGA_MOON),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.LifeAreaLove
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        SunMoonTimeRow(label = stringResource(StringKeyDosha.PANCHANGA_RISE), time = "N/A")
                        Spacer(modifier = Modifier.height(4.dp))
                        SunMoonTimeRow(label = stringResource(StringKeyDosha.PANCHANGA_SET), time = "N/A")
                    }
                }
            }
        }
    }
}

@Composable
private fun SunMoonTimeRow(label: String, time: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            color = AppTheme.TextMuted
        )
        Text(
            text = time,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = AppTheme.TextPrimary
        )
    }
}

@Composable
private fun BirthPanchangaContent(
    panchanga: PanchangaData?,
    chart: VedicChart?
) {
    if (panchanga == null || chart == null) {
        EmptyBirthPanchangaContent()
        return
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Birth summary
        item(key = "birth_summary") {
            BirthSummaryCard(panchanga = panchanga, chart = chart)
        }

        // Five elements
        item(key = "birth_elements") {
            FiveLimbsCard(panchanga = panchanga)
        }

        // Birth day interpretation
        item(key = "birth_interpretation") {
            BirthDayInterpretationCard(panchanga = panchanga)
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun BirthSummaryCard(
    panchanga: PanchangaData,
    chart: VedicChart
) {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Birth Panchanga",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = chart.birthData.name,
                        fontSize = 14.sp,
                        color = AppTheme.AccentGold,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .size(56.dp)
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
                        Icons.Outlined.CalendarMonth,
                        contentDescription = null,
                        tint = AppTheme.AccentGold,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Birth date info
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = AppTheme.CardBackgroundElevated
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "DATE",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = AppTheme.TextMuted,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = chart.birthData.dateTime.format(
                                    DateTimeFormatter.ofPattern("MMM dd, yyyy")
                                ),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "TIME",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Medium,
                                color = AppTheme.TextMuted,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = chart.birthData.dateTime.format(
                                    DateTimeFormatter.ofPattern("hh:mm a")
                                ),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = AppTheme.TextPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${panchanga.vara.displayName} - ${panchanga.tithi.tithi.displayName}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = AppTheme.AccentGold
                    )
                }
            }
        }
    }
}

@Composable
private fun BirthDayInterpretationCard(
    panchanga: PanchangaData
) {
    val language = LocalLanguage.current
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Star,
                    contentDescription = null,
                    tint = AppTheme.AccentGold,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Birth Day Significance",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                                        text = getBirthDayInterpretation(panchanga, language),                fontSize = 13.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun PanchangaElementsContent(
    todayPanchanga: PanchangaData?,
    birthPanchanga: PanchangaData?
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item(key = "elements_intro") {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                color = AppTheme.CardBackground
            ) {
                Text(
                    text = stringResource(StringKeyDosha.PANCHANGA_INTRO),
                    fontSize = 13.sp,
                    color = AppTheme.TextMuted,
                    lineHeight = 19.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        // Tithi explanation
        item(key = "tithi_element") {
            ElementDetailCard(
                name = stringResource(StringKeyDosha.PANCHANGA_TITHI_LABEL),
                sanskrit = "तिथि",
                description = stringResource(StringKeyDosha.PANCHANGA_TITHI_DESC_LONG),
                significance = stringResource(StringKeyDosha.PANCHANGA_TITHI_SIG),
                color = AppTheme.LifeAreaLove,
                currentValue = todayPanchanga?.tithi?.tithi?.displayName,
                birthValue = birthPanchanga?.tithi?.tithi?.displayName
            )
        }

        // Vara explanation
        item(key = "vara_element") {
            ElementDetailCard(
                name = stringResource(StringKeyDosha.PANCHANGA_VARA_LABEL),
                sanskrit = "वार",
                description = stringResource(StringKeyDosha.PANCHANGA_VARA_DESC_LONG),
                significance = stringResource(StringKeyDosha.PANCHANGA_VARA_SIG),
                color = AppTheme.AccentPrimary,
                currentValue = todayPanchanga?.vara?.displayName,
                birthValue = birthPanchanga?.vara?.displayName
            )
        }

        // Nakshatra explanation
        item(key = "nakshatra_element") {
            ElementDetailCard(
                name = stringResource(StringKeyDosha.PANCHANGA_NAKSHATRA_LABEL),
                sanskrit = "नक्षत्र",
                description = stringResource(StringKeyDosha.PANCHANGA_NAKSHATRA_DESC_LONG),
                significance = stringResource(StringKeyDosha.PANCHANGA_NAKSHATRA_SIG),
                color = AppTheme.AccentGold,
                currentValue = todayPanchanga?.nakshatra?.nakshatra?.displayName,
                birthValue = birthPanchanga?.nakshatra?.nakshatra?.displayName
            )
        }

        // Yoga explanation
        item(key = "yoga_element") {
            ElementDetailCard(
                name = stringResource(StringKeyDosha.PANCHANGA_YOGA_LABEL),
                sanskrit = "योग",
                description = stringResource(StringKeyDosha.PANCHANGA_YOGA_DESC_LONG),
                significance = stringResource(StringKeyDosha.PANCHANGA_YOGA_SIG),
                color = AppTheme.AccentTeal,
                currentValue = todayPanchanga?.yoga?.yoga?.displayName,
                birthValue = birthPanchanga?.yoga?.yoga?.displayName
            )
        }

        // Karana explanation
        item(key = "karana_element") {
            ElementDetailCard(
                name = stringResource(StringKeyDosha.PANCHANGA_KARANA_LABEL),
                sanskrit = "करण",
                description = stringResource(StringKeyDosha.PANCHANGA_KARANA_DESC_LONG),
                significance = stringResource(StringKeyDosha.PANCHANGA_KARANA_SIG),
                color = AppTheme.LifeAreaSpiritual,
                currentValue = todayPanchanga?.karana?.karana?.displayName,
                birthValue = birthPanchanga?.karana?.karana?.displayName
            )
        }

        item { Spacer(modifier = Modifier.height(32.dp)) }
    }
}

@Composable
private fun ElementDetailCard(
    name: String,
    sanskrit: String,
    description: String,
    significance: String,
    color: Color,
    currentValue: String?,
    birthValue: String?
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = AppTheme.CardBackground
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = name.first().toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = color
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = sanskrit,
                        fontSize = 12.sp,
                        color = AppTheme.TextMuted,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }

            // Description
            Text(
                text = description,
                fontSize = 13.sp,
                color = AppTheme.TextSecondary,
                lineHeight = 19.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Significance
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                color = color.copy(alpha = 0.08f)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = stringResource(StringKeyDosha.PANCHANGA_SIGNIFICANCE),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = color,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = significance,
                        fontSize = 12.sp,
                        color = AppTheme.TextPrimary,
                        lineHeight = 17.sp
                    )
                }
            }

            // Current values
            if (currentValue != null || birthValue != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    currentValue?.let {
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            color = AppTheme.CardBackgroundElevated
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "TODAY",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = AppTheme.TextMuted,
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = it,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppTheme.AccentPrimary,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                    birthValue?.let {
                        Surface(
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            color = AppTheme.CardBackgroundElevated
                        ) {
                            Column(
                                modifier = Modifier.padding(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "BIRTH",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = AppTheme.TextMuted,
                                    letterSpacing = 0.5.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = it,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AppTheme.AccentGold,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
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
private fun EmptyPanchangaContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = null,
                tint = AppTheme.TextMuted,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Panchanga Unavailable",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Unable to calculate Panchanga for today",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun EmptyBirthPanchangaContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.CalendarMonth,
                contentDescription = null,
                tint = AppTheme.TextMuted,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No Birth Chart",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please select a birth chart to view birth Panchanga",
                style = MaterialTheme.typography.bodyMedium,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PanchangaInfoDialog(onDismiss: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(StringKeyDosha.PANCHANGA_ABOUT_TITLE),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )
        },
        text = {
            Column {
                Text(
                    text = stringResource(StringKeyDosha.PANCHANGA_ABOUT_DESC_1),
                    fontSize = 13.sp,
                    color = AppTheme.TextSecondary,
                    lineHeight = 19.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(StringKeyDosha.PANCHANGA_ABOUT_DESC_2),
                    fontSize = 13.sp,
                    color = AppTheme.TextSecondary,
                    lineHeight = 19.sp
                )
            }
        },
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text(stringResource(StringKey.BTN_BACK), color = AppTheme.AccentPrimary)
            }
        },
        containerColor = AppTheme.CardBackground,
        shape = RoundedCornerShape(16.dp)
    )
}

private fun getBirthDayInterpretation(panchanga: PanchangaData, language: Language): String {
    return buildString {
        append(StringResources.get(
            StringKeyAnalysis.PANCHANGA_BIRTH_INTERP_MAIN,
            language,
            panchanga.vara.getLocalizedName(language),
            panchanga.nakshatra.nakshatra.getLocalizedName(language),
            panchanga.tithi.tithi.getLocalizedName(language)
        ))
        append(" ")
        append(StringResources.get(
            StringKeyAnalysis.PANCHANGA_BIRTH_INTERP_YOGA,
            language,
            panchanga.yoga.yoga.getLocalizedName(language)
        ))
        
        when (panchanga.yoga.yoga.displayName.lowercase()) {
            "vishkumbh", "atiganda", "shool", "vyaghata", "vajra", "paridh" ->
                append(StringResources.get(StringKeyAnalysis.PANCHANGA_BIRTH_INTERP_YOGA_CHALLENGE, language))
            "siddha", "shubha", "amrita", "sukarma" ->
                append(StringResources.get(StringKeyAnalysis.PANCHANGA_BIRTH_INTERP_YOGA_AUSPICIOUS, language))
            else ->
                append(StringResources.get(StringKeyAnalysis.PANCHANGA_BIRTH_INTERP_YOGA_MODERATE, language))
        }
        
        append(StringResources.get(
            StringKeyAnalysis.PANCHANGA_BIRTH_INTERP_KARANA,
            language,
            panchanga.karana.karana.getLocalizedName(language)
        ))
    }
}
