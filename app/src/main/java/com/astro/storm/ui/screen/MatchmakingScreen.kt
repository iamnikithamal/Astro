package com.astro.storm.ui.screen

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.local.ChartEntity
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ephemeris.MatchmakingCalculator
import com.astro.storm.ui.theme.AppTheme
import com.astro.storm.ui.viewmodel.ChartViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Production-Grade Matchmaking Screen for Vedic Astrology
 *
 * Features:
 * - Profile selection for bride and groom
 * - Complete Ashtakoota (8 Guna) analysis display
 * - Visual compatibility score with circular progress
 * - Detailed breakdown of each Guna
 * - Manglik Dosha analysis
 * - Remedies and recommendations
 *
 * @author AstroStorm - Ultra-Precision Vedic Astrology
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchmakingScreen(
    savedCharts: List<ChartEntity>,
    viewModel: ChartViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // State for selected profiles
    var selectedBrideId by remember { mutableStateOf<Long?>(null) }
    var selectedGroomId by remember { mutableStateOf<Long?>(null) }
    var brideChart by remember { mutableStateOf<VedicChart?>(null) }
    var groomChart by remember { mutableStateOf<VedicChart?>(null) }

    // State for matching result
    var matchingResult by remember { mutableStateOf<MatchmakingCalculator.MatchmakingResult?>(null) }
    var isCalculating by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Profile selection dialogs
    var showBrideSelector by remember { mutableStateOf(false) }
    var showGroomSelector by remember { mutableStateOf(false) }

    // Tab state for result sections
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Overview", "Gunas", "Manglik", "Remedies")

    // Load charts when selected
    LaunchedEffect(selectedBrideId) {
        selectedBrideId?.let { id ->
            withContext(Dispatchers.IO) {
                brideChart = viewModel.getChartById(id)
            }
        }
    }

    LaunchedEffect(selectedGroomId) {
        selectedGroomId?.let { id ->
            withContext(Dispatchers.IO) {
                groomChart = viewModel.getChartById(id)
            }
        }
    }

    // Calculate matching when both charts are available
    LaunchedEffect(brideChart, groomChart) {
        if (brideChart != null && groomChart != null) {
            isCalculating = true
            errorMessage = null
            withContext(Dispatchers.Default) {
                try {
                    matchingResult = MatchmakingCalculator.calculateMatchmaking(brideChart!!, groomChart!!)
                } catch (e: Exception) {
                    errorMessage = e.message
                }
            }
            isCalculating = false
        } else {
            matchingResult = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Kundli Milan",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Profile Selection Section
            item {
                ProfileSelectionSection(
                    savedCharts = savedCharts,
                    selectedBrideId = selectedBrideId,
                    selectedGroomId = selectedGroomId,
                    brideChart = brideChart,
                    groomChart = groomChart,
                    onSelectBride = { showBrideSelector = true },
                    onSelectGroom = { showGroomSelector = true }
                )
            }

            // Loading state
            if (isCalculating) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(
                                color = AppTheme.AccentPrimary,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                "Calculating compatibility...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = AppTheme.TextMuted
                            )
                        }
                    }
                }
            }

            // Error state
            errorMessage?.let { error ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = AppTheme.ErrorColor.copy(alpha = 0.1f)
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Error,
                                contentDescription = null,
                                tint = AppTheme.ErrorColor
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                error,
                                style = MaterialTheme.typography.bodyMedium,
                                color = AppTheme.ErrorColor
                            )
                        }
                    }
                }
            }

            // Results Section
            matchingResult?.let { result ->
                // Score Card
                item {
                    CompatibilityScoreCard(result)
                }

                // Tab selector
                item {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTab,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        containerColor = Color.Transparent,
                        edgePadding = 0.dp,
                        divider = {}
                    ) {
                        tabs.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = {
                                    Text(
                                        title,
                                        color = if (selectedTab == index) AppTheme.AccentPrimary else AppTheme.TextMuted
                                    )
                                }
                            )
                        }
                    }
                }

                // Tab content
                when (selectedTab) {
                    0 -> {
                        // Overview
                        item { OverviewSection(result) }
                    }
                    1 -> {
                        // Gunas breakdown
                        items(result.gunaAnalyses) { guna ->
                            GunaCard(guna)
                        }
                    }
                    2 -> {
                        // Manglik analysis
                        item { ManglikSection(result) }
                    }
                    3 -> {
                        // Remedies
                        item { RemediesSection(result) }
                    }
                }
            }

            // Empty state - waiting for selection
            if (matchingResult == null && !isCalculating && errorMessage == null) {
                item {
                    EmptyMatchingState(
                        hasBride = brideChart != null,
                        hasGroom = groomChart != null
                    )
                }
            }
        }
    }

    // Profile selection dialogs
    if (showBrideSelector) {
        ProfileSelectorDialog(
            title = "Select Bride's Chart",
            charts = savedCharts,
            selectedId = selectedBrideId,
            excludeId = selectedGroomId,
            onSelect = { id ->
                selectedBrideId = id
                showBrideSelector = false
            },
            onDismiss = { showBrideSelector = false }
        )
    }

    if (showGroomSelector) {
        ProfileSelectorDialog(
            title = "Select Groom's Chart",
            charts = savedCharts,
            selectedId = selectedGroomId,
            excludeId = selectedBrideId,
            onSelect = { id ->
                selectedGroomId = id
                showGroomSelector = false
            },
            onDismiss = { showGroomSelector = false }
        )
    }
}

@Composable
private fun ProfileSelectionSection(
    savedCharts: List<ChartEntity>,
    selectedBrideId: Long?,
    selectedGroomId: Long?,
    brideChart: VedicChart?,
    groomChart: VedicChart?,
    onSelectBride: () -> Unit,
    onSelectGroom: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                "Select Profiles",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = AppTheme.TextPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Bride selector
                ProfileCard(
                    label = "Bride",
                    name = brideChart?.birthData?.name,
                    icon = Icons.Filled.Female,
                    color = AppTheme.LifeAreaLove,
                    onClick = onSelectBride,
                    modifier = Modifier.weight(1f)
                )

                // Connection indicator
                Box(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        contentDescription = null,
                        tint = if (brideChart != null && groomChart != null)
                            AppTheme.LifeAreaLove else AppTheme.TextSubtle,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Groom selector
                ProfileCard(
                    label = "Groom",
                    name = groomChart?.birthData?.name,
                    icon = Icons.Filled.Male,
                    color = AppTheme.AccentTeal,
                    onClick = onSelectGroom,
                    modifier = Modifier.weight(1f)
                )
            }

            if (savedCharts.isEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Create birth charts first to use matchmaking",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun ProfileCard(
    label: String,
    name: String?,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = if (name != null) color.copy(alpha = 0.1f) else AppTheme.ChipBackground
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(if (name != null) color.copy(alpha = 0.2f) else AppTheme.ChipBackground)
                    .border(
                        width = 2.dp,
                        color = if (name != null) color else AppTheme.BorderColor,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = if (name != null) color else AppTheme.TextMuted,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                label,
                style = MaterialTheme.typography.labelSmall,
                color = AppTheme.TextMuted
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                name ?: "Tap to select",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (name != null) FontWeight.Medium else FontWeight.Normal,
                color = if (name != null) AppTheme.TextPrimary else AppTheme.TextSubtle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun CompatibilityScoreCard(result: MatchmakingCalculator.MatchmakingResult) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Circular score indicator
            Box(
                modifier = Modifier.size(140.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { (result.totalPoints / result.maxPoints).toFloat() },
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = 12.dp,
                    color = getRatingColor(result.rating),
                    trackColor = AppTheme.ChipBackground
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = String.format("%.1f", result.totalPoints),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = AppTheme.TextPrimary
                    )
                    Text(
                        text = "/ ${result.maxPoints.toInt()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Rating badge
            Surface(
                color = getRatingColor(result.rating).copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        getRatingIcon(result.rating),
                        contentDescription = null,
                        tint = getRatingColor(result.rating),
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        result.rating.displayName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = getRatingColor(result.rating)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = String.format("%.1f%% Compatibility", result.percentage),
                style = MaterialTheme.typography.bodyLarge,
                color = AppTheme.TextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = result.rating.description,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
private fun OverviewSection(result: MatchmakingCalculator.MatchmakingResult) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        // Quick score summary
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Guna Scores",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Horizontal score bars
                result.gunaAnalyses.forEach { guna ->
                    GunaScoreBar(guna)
                    if (guna != result.gunaAnalyses.last()) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }

        // Key insights
        if (result.specialConsiderations.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = null,
                            tint = AppTheme.InfoColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Key Considerations",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    result.specialConsiderations.forEach { consideration ->
                        Row(modifier = Modifier.padding(vertical = 4.dp)) {
                            Text("•", color = AppTheme.TextMuted)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                consideration,
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

@Composable
private fun GunaScoreBar(guna: MatchmakingCalculator.GunaAnalysis) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                guna.name,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextPrimary
            )
            Text(
                "${guna.obtainedPoints.toInt()}/${guna.maxPoints.toInt()}",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium,
                color = if (guna.isPositive) AppTheme.SuccessColor else AppTheme.WarningColor
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(AppTheme.ChipBackground)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth((guna.obtainedPoints / guna.maxPoints).toFloat())
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(3.dp))
                    .background(if (guna.isPositive) AppTheme.SuccessColor else AppTheme.WarningColor)
            )
        }
    }
}

@Composable
private fun GunaCard(guna: MatchmakingCalculator.GunaAnalysis) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
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
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                if (guna.isPositive) AppTheme.SuccessColor.copy(alpha = 0.15f)
                                else AppTheme.WarningColor.copy(alpha = 0.15f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            if (guna.isPositive) Icons.Filled.CheckCircle else Icons.Filled.Warning,
                            contentDescription = null,
                            tint = if (guna.isPositive) AppTheme.SuccessColor else AppTheme.WarningColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            guna.name,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.SemiBold,
                            color = AppTheme.TextPrimary
                        )
                        Text(
                            guna.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = AppTheme.TextMuted
                        )
                    }
                }

                // Score
                Surface(
                    color = if (guna.isPositive) AppTheme.SuccessColor.copy(alpha = 0.15f)
                    else AppTheme.WarningColor.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "${guna.obtainedPoints.toInt()}/${guna.maxPoints.toInt()}",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (guna.isPositive) AppTheme.SuccessColor else AppTheme.WarningColor,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = AppTheme.DividerColor)
            Spacer(modifier = Modifier.height(12.dp))

            // Bride and Groom values
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Bride",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                    Text(
                        guna.brideValue,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.LifeAreaLove
                    )
                }
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Text(
                        "Groom",
                        style = MaterialTheme.typography.labelSmall,
                        color = AppTheme.TextMuted
                    )
                    Text(
                        guna.groomValue,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.AccentTeal
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Analysis
            Text(
                guna.analysis,
                style = MaterialTheme.typography.bodySmall,
                color = AppTheme.TextSecondary,
                lineHeight = 18.sp
            )
        }
    }
}

@Composable
private fun ManglikSection(result: MatchmakingCalculator.MatchmakingResult) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        // Manglik summary card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Shield,
                        contentDescription = null,
                        tint = AppTheme.PlanetMars,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Manglik Dosha Analysis",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Compatibility status
                Surface(
                    color = getManglikStatusColor(result.manglikCompatibility).copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        result.manglikCompatibility,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = getManglikStatusColor(result.manglikCompatibility),
                        modifier = Modifier.padding(12.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Individual Manglik analysis
        ManglikPersonCard(result.brideManglik, AppTheme.LifeAreaLove)
        ManglikPersonCard(result.groomManglik, AppTheme.AccentTeal)
    }
}

@Composable
private fun ManglikPersonCard(
    analysis: MatchmakingCalculator.ManglikAnalysis,
    accentColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    analysis.person,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = accentColor
                )

                Surface(
                    color = getManglikSeverityColor(analysis.effectiveDosha).copy(alpha = 0.15f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        analysis.effectiveDosha.displayName,
                        style = MaterialTheme.typography.labelMedium,
                        color = getManglikSeverityColor(analysis.effectiveDosha),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            if (analysis.marsHouse > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Mars in House ${analysis.marsHouse}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.TextMuted
                )
            }

            if (analysis.factors.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Factors:",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.TextMuted
                )
                analysis.factors.forEach { factor ->
                    Text(
                        "• $factor",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.TextSecondary
                    )
                }
            }

            if (analysis.cancellations.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Cancellation Factors:",
                    style = MaterialTheme.typography.labelSmall,
                    color = AppTheme.SuccessColor
                )
                analysis.cancellations.forEach { cancellation ->
                    Text(
                        "✓ $cancellation",
                        style = MaterialTheme.typography.bodySmall,
                        color = AppTheme.SuccessColor
                    )
                }
            }
        }
    }
}

@Composable
private fun RemediesSection(result: MatchmakingCalculator.MatchmakingResult) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = AppTheme.CardBackground),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Spa,
                        contentDescription = null,
                        tint = AppTheme.LifeAreaHealth,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "Suggested Remedies",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = AppTheme.TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                result.remedies.forEachIndexed { index, remedy ->
                    Row(modifier = Modifier.padding(vertical = 6.dp)) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(AppTheme.AccentPrimary.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "${index + 1}",
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
            }
        }

        // Disclaimer
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = AppTheme.InfoColor.copy(alpha = 0.1f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = null,
                    tint = AppTheme.InfoColor,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "Remedies are traditional suggestions based on Vedic astrology. For specific guidance, please consult a learned astrologer.",
                    style = MaterialTheme.typography.bodySmall,
                    color = AppTheme.InfoColor
                )
            }
        }
    }
}

@Composable
private fun EmptyMatchingState(hasBride: Boolean, hasGroom: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Outlined.FavoriteBorder,
                contentDescription = null,
                tint = AppTheme.TextSubtle,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = when {
                    !hasBride && !hasGroom -> "Select both profiles to begin"
                    !hasBride -> "Select bride's profile"
                    !hasGroom -> "Select groom's profile"
                    else -> "Calculating..."
                },
                style = MaterialTheme.typography.bodyLarge,
                color = AppTheme.TextMuted,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileSelectorDialog(
    title: String,
    charts: List<ChartEntity>,
    selectedId: Long?,
    excludeId: Long?,
    onSelect: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val availableCharts = charts.filter { it.id != excludeId }

    AlertDialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            color = AppTheme.CardBackground,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = AppTheme.TextPrimary
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (availableCharts.isEmpty()) {
                    Text(
                        "No charts available",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppTheme.TextMuted
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.heightIn(max = 400.dp)
                    ) {
                        items(availableCharts) { chart ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onSelect(chart.id) }
                                    .background(
                                        if (chart.id == selectedId) AppTheme.AccentPrimary.copy(alpha = 0.1f)
                                        else Color.Transparent
                                    )
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = chart.id == selectedId,
                                    onClick = { onSelect(chart.id) },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = AppTheme.AccentPrimary
                                    )
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        chart.name,
                                        style = MaterialTheme.typography.bodyLarge,
                                        fontWeight = FontWeight.Medium,
                                        color = AppTheme.TextPrimary
                                    )
                                    Text(
                                        chart.location,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = AppTheme.TextMuted
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Cancel", color = AppTheme.TextMuted)
                }
            }
        }
    }
}

// Helper functions
private fun getRatingColor(rating: MatchmakingCalculator.CompatibilityRating): Color {
    return when (rating) {
        MatchmakingCalculator.CompatibilityRating.EXCELLENT -> Color(0xFF4CAF50)
        MatchmakingCalculator.CompatibilityRating.GOOD -> Color(0xFF8BC34A)
        MatchmakingCalculator.CompatibilityRating.AVERAGE -> Color(0xFFFFC107)
        MatchmakingCalculator.CompatibilityRating.BELOW_AVERAGE -> Color(0xFFFF9800)
        MatchmakingCalculator.CompatibilityRating.POOR -> Color(0xFFF44336)
    }
}

private fun getRatingIcon(rating: MatchmakingCalculator.CompatibilityRating): androidx.compose.ui.graphics.vector.ImageVector {
    return when (rating) {
        MatchmakingCalculator.CompatibilityRating.EXCELLENT -> Icons.Filled.Stars
        MatchmakingCalculator.CompatibilityRating.GOOD -> Icons.Filled.ThumbUp
        MatchmakingCalculator.CompatibilityRating.AVERAGE -> Icons.Filled.Balance
        MatchmakingCalculator.CompatibilityRating.BELOW_AVERAGE -> Icons.Filled.Warning
        MatchmakingCalculator.CompatibilityRating.POOR -> Icons.Filled.Cancel
    }
}

private fun getManglikStatusColor(status: String): Color {
    return when {
        status.contains("No concerns") || status.contains("cancel") -> AppTheme.SuccessColor
        status.contains("Manageable") -> AppTheme.WarningColor
        else -> AppTheme.ErrorColor
    }
}

private fun getManglikSeverityColor(dosha: MatchmakingCalculator.ManglikDosha): Color {
    return when (dosha) {
        MatchmakingCalculator.ManglikDosha.NONE -> AppTheme.SuccessColor
        MatchmakingCalculator.ManglikDosha.PARTIAL -> AppTheme.WarningColor
        MatchmakingCalculator.ManglikDosha.FULL -> AppTheme.ErrorColor
        MatchmakingCalculator.ManglikDosha.DOUBLE -> Color(0xFF8B0000)
    }
}
