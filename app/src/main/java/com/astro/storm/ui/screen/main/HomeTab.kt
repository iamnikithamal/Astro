package com.astro.storm.ui.screen.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Cake
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.CompareArrows
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.PersonAddAlt
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ui.theme.AppTheme
import kotlinx.coroutines.delay

// ============================================================================
// DESIGN TOKENS
// ============================================================================

private object HomeTabDimens {
    val ScreenPaddingHorizontal = 20.dp
    val ScreenPaddingVertical = 16.dp
    val CardSpacing = 14.dp
    val RowSpacing = 14.dp
    val CardCornerRadius = 16.dp
    val IconContainerSize = 44.dp
    val IconSize = 24.dp
    val CardPadding = 18.dp
    val SectionTitlePadding = PaddingValues(
        start = 20.dp,
        end = 20.dp,
        top = 24.dp,
        bottom = 14.dp
    )
    val BottomContentPadding = 120.dp
    val EmptyStateIconSize = 80.dp
}

private object HomeTabAnimations {
    const val StaggerDelayMs = 50
    const val CardEntryDurationMs = 400
    const val ShimmerDurationMs = 1200
    const val PressScaleFactor = 0.97f
}

// ============================================================================
// MAIN COMPOSABLE
// ============================================================================

/**
 * Home Tab - Chart Analysis & Tools
 *
 * Displays a grid of astrological analysis features with:
 * - Staggered entry animations
 * - Haptic feedback on interactions
 * - Accessibility support
 * - Press state animations
 */
@Composable
fun HomeTab(
    chart: VedicChart?,
    onFeatureClick: (InsightFeature) -> Unit,
    onCreateProfileClick: () -> Unit = {},
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState()
) {
    when {
        isLoading -> HomeLoadingState(modifier = modifier)
        chart == null -> EmptyHomeState(
            onCreateProfileClick = onCreateProfileClick,
            modifier = modifier
        )
        else -> HomeContent(
            onFeatureClick = onFeatureClick,
            listState = listState,
            modifier = modifier
        )
    }
}

@Composable
private fun HomeContent(
    onFeatureClick: (InsightFeature) -> Unit,
    listState: LazyListState,
    modifier: Modifier = Modifier
) {
    // Pre-calculate feature grid rows to avoid recalculation during recomposition
    val implementedFeatureRows by remember {
        derivedStateOf { InsightFeature.implementedFeatures.chunked(2) }
    }
    val comingSoonFeatureRows by remember {
        derivedStateOf { InsightFeature.comingSoonFeatures.chunked(2) }
    }

    // Animation state for staggered entry
    var animationTriggered by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animationTriggered = true
    }

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.ScreenBackground),
        contentPadding = PaddingValues(bottom = HomeTabDimens.BottomContentPadding)
    ) {
        // Quick Actions Header
        item(key = "header_chart_analysis") {
            SectionHeader(
                title = "Chart Analysis",
                subtitle = "Explore your cosmic blueprint",
                animationDelay = 0
            )
        }

        // Implemented Features Grid
        implementedFeatureRows.forEachIndexed { rowIndex, rowFeatures ->
            item(key = "implemented_row_$rowIndex") {
                FeatureRow(
                    features = rowFeatures,
                    onFeatureClick = onFeatureClick,
                    isDisabled = false,
                    animationDelay = (rowIndex + 1) * HomeTabAnimations.StaggerDelayMs,
                    rowIndex = rowIndex
                )
            }
        }

        // Coming Soon Section Header
        item(key = "header_coming_soon") {
            SectionHeader(
                title = "Coming Soon",
                subtitle = "Features in development",
                isMuted = true,
                animationDelay = (implementedFeatureRows.size + 1) * HomeTabAnimations.StaggerDelayMs
            )
        }

        // Coming Soon Features Grid
        comingSoonFeatureRows.forEachIndexed { rowIndex, rowFeatures ->
            item(key = "coming_soon_row_$rowIndex") {
                FeatureRow(
                    features = rowFeatures,
                    onFeatureClick = { /* Coming soon - no action */ },
                    isDisabled = true,
                    animationDelay = (implementedFeatureRows.size + rowIndex + 2) * HomeTabAnimations.StaggerDelayMs,
                    rowIndex = rowIndex
                )
            }
        }
    }
}

// ============================================================================
// SECTION HEADER
// ============================================================================

@Composable
private fun SectionHeader(
    title: String,
    subtitle: String,
    isMuted: Boolean = false,
    animationDelay: Int = 0
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(animationDelay.toLong())
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = HomeTabAnimations.CardEntryDurationMs,
                easing = FastOutSlowInEasing
            )
        ) + slideInVertically(
            initialOffsetY = { it / 3 },
            animationSpec = tween(
                durationMillis = HomeTabAnimations.CardEntryDurationMs,
                easing = FastOutSlowInEasing
            )
        )
    ) {
        Column(
            modifier = Modifier.padding(HomeTabDimens.SectionTitlePadding)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = if (isMuted) AppTheme.TextMuted else AppTheme.TextPrimary,
                letterSpacing = (-0.5).sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isMuted) AppTheme.TextSubtle else AppTheme.TextMuted
            )
        }
    }
}

// ============================================================================
// FEATURE GRID
// ============================================================================

@Composable
private fun FeatureRow(
    features: List<InsightFeature>,
    onFeatureClick: (InsightFeature) -> Unit,
    isDisabled: Boolean,
    animationDelay: Int,
    rowIndex: Int
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(animationDelay.toLong())
        isVisible = true
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = HomeTabAnimations.CardEntryDurationMs,
                easing = FastOutSlowInEasing
            )
        ) + slideInVertically(
            initialOffsetY = { it / 2 },
            animationSpec = tween(
                durationMillis = HomeTabAnimations.CardEntryDurationMs,
                easing = FastOutSlowInEasing
            )
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = HomeTabDimens.ScreenPaddingHorizontal),
            horizontalArrangement = Arrangement.spacedBy(HomeTabDimens.CardSpacing)
        ) {
            features.forEachIndexed { index, feature ->
                FeatureCard(
                    feature = feature,
                    onClick = { onFeatureClick(feature) },
                    isDisabled = isDisabled,
                    modifier = Modifier.weight(1f),
                    cardIndex = rowIndex * 2 + index
                )
            }
            // Fill empty space for odd number of features
            if (features.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(HomeTabDimens.RowSpacing))
    }
}

@Composable
private fun FeatureCard(
    feature: InsightFeature,
    onClick: () -> Unit,
    isDisabled: Boolean,
    modifier: Modifier = Modifier,
    cardIndex: Int = 0
) {
    val hapticFeedback = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Press animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed && !isDisabled) HomeTabAnimations.PressScaleFactor else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "card_scale"
    )

    // Gradient background for enabled cards
    val gradientBrush = remember(feature.color) {
        Brush.linearGradient(
            colors = listOf(
                feature.color.copy(alpha = 0.08f),
                feature.color.copy(alpha = 0.02f)
            ),
            start = Offset(0f, 0f),
            end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
        )
    }

    Card(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .semantics(mergeDescendants = true) {
                contentDescription = if (isDisabled) {
                    "${feature.title}. ${feature.description}. Coming soon."
                } else {
                    "${feature.title}. ${feature.description}. Tap to open."
                }
                role = Role.Button
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null, // Custom animation instead
                enabled = !isDisabled,
                onClick = {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick()
                }
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isDisabled) {
                AppTheme.CardBackground.copy(alpha = 0.5f)
            } else {
                AppTheme.CardBackground
            }
        ),
        shape = RoundedCornerShape(HomeTabDimens.CardCornerRadius),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isDisabled) 0.dp else 2.dp,
            pressedElevation = 0.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(
                    if (!isDisabled) {
                        Modifier.background(gradientBrush)
                    } else {
                        Modifier
                    }
                )
        ) {
            Column(
                modifier = Modifier.padding(HomeTabDimens.CardPadding)
            ) {
                // Header Row: Icon + Badge/Arrow
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FeatureIcon(
                        icon = feature.icon,
                        color = feature.color,
                        isDisabled = isDisabled
                    )

                    if (isDisabled) {
                        ComingSoonBadge()
                    } else {
                        NavigationIndicator(color = feature.color)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Title
                Text(
                    text = feature.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDisabled) AppTheme.TextSubtle else AppTheme.TextPrimary,
                    letterSpacing = (-0.2).sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Description
                Text(
                    text = feature.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isDisabled) {
                        AppTheme.TextSubtle.copy(alpha = 0.7f)
                    } else {
                        AppTheme.TextMuted
                    },
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
            }

            // Decorative accent for enabled cards
            if (!isDisabled) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 20.dp, y = (-20).dp)
                        .size(60.dp)
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    feature.color.copy(alpha = 0.1f),
                                    Color.Transparent
                                )
                            ),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

@Composable
private fun FeatureIcon(
    icon: ImageVector,
    color: Color,
    isDisabled: Boolean
) {
    Box(
        modifier = Modifier
            .size(HomeTabDimens.IconContainerSize)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isDisabled) {
                    AppTheme.TextSubtle.copy(alpha = 0.08f)
                } else {
                    color.copy(alpha = 0.12f)
                }
            )
            .then(
                if (!isDisabled) {
                    Modifier.border(
                        width = 1.dp,
                        color = color.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isDisabled) AppTheme.TextSubtle else color,
            modifier = Modifier.size(HomeTabDimens.IconSize)
        )
    }
}

@Composable
private fun ComingSoonBadge() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier
            .background(
                color = AppTheme.TextSubtle.copy(alpha = 0.08f),
                shape = RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Rounded.Lock,
            contentDescription = null,
            tint = AppTheme.TextSubtle,
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = "Soon",
            style = MaterialTheme.typography.labelSmall,
            color = AppTheme.TextSubtle,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun NavigationIndicator(color: Color) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .background(
                color = color.copy(alpha = 0.1f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.ChevronRight,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(18.dp)
        )
    }
}

// ============================================================================
// LOADING STATE
// ============================================================================

@Composable
private fun HomeLoadingState(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        AppTheme.CardBackground.copy(alpha = 0.6f),
        AppTheme.CardBackground.copy(alpha = 0.9f),
        AppTheme.CardBackground.copy(alpha = 0.6f)
    )

    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = HomeTabAnimations.ShimmerDurationMs,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    val shimmerBrush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(shimmerTranslate - 500f, shimmerTranslate - 500f),
        end = Offset(shimmerTranslate, shimmerTranslate)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.ScreenBackground)
            .padding(HomeTabDimens.ScreenPaddingHorizontal)
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        // Header shimmer
        ShimmerBox(
            width = 160.dp,
            height = 24.dp,
            brush = shimmerBrush
        )
        Spacer(modifier = Modifier.height(8.dp))
        ShimmerBox(
            width = 200.dp,
            height = 16.dp,
            brush = shimmerBrush
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Feature grid shimmer
        repeat(4) { rowIndex ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(HomeTabDimens.CardSpacing)
            ) {
                repeat(2) {
                    ShimmerFeatureCard(
                        brush = shimmerBrush,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(HomeTabDimens.RowSpacing))
        }
    }
}

@Composable
private fun ShimmerBox(
    width: androidx.compose.ui.unit.Dp,
    height: androidx.compose.ui.unit.Dp,
    brush: Brush,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .clip(RoundedCornerShape(8.dp))
            .background(brush)
    )
}

@Composable
private fun ShimmerFeatureCard(
    brush: Brush,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        shape = RoundedCornerShape(HomeTabDimens.CardCornerRadius)
    ) {
        Column(
            modifier = Modifier
                .background(brush)
                .padding(HomeTabDimens.CardPadding)
        ) {
            ShimmerBox(width = 44.dp, height = 44.dp, brush = brush)
            Spacer(modifier = Modifier.height(14.dp))
            ShimmerBox(width = 80.dp, height = 18.dp, brush = brush)
            Spacer(modifier = Modifier.height(6.dp))
            ShimmerBox(width = 120.dp, height = 14.dp, brush = brush)
            Spacer(modifier = Modifier.height(4.dp))
            ShimmerBox(width = 100.dp, height = 14.dp, brush = brush)
        }
    }
}

// ============================================================================
// EMPTY STATE
// ============================================================================

@Composable
private fun EmptyHomeState(
    onCreateProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        isVisible = true
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.ScreenBackground),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(
                animationSpec = tween(500, easing = FastOutSlowInEasing)
            ) + scaleIn(
                initialScale = 0.9f,
                animationSpec = tween(500, easing = FastOutSlowInEasing)
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(48.dp)
            ) {
                // Animated icon container
                EmptyStateIcon()

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "No Profile Selected",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = AppTheme.TextPrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Create or select a profile to unlock your personalized astrological insights and cosmic guidance.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AppTheme.TextMuted,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )

                Spacer(modifier = Modifier.height(32.dp))

                FilledTonalButton(
                    onClick = onCreateProfileClick,
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PersonAddAlt,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Create Profile",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Rounded.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyStateIcon() {
    val infiniteTransition = rememberInfiniteTransition(label = "empty_state_anim")

    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_scale"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow_alpha"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.scale(pulseScale)
    ) {
        // Outer glow ring
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            AppTheme.AccentPrimary.copy(alpha = glowAlpha * 0.3f),
                            Color.Transparent
                        )
                    ),
                    shape = CircleShape
                )
        )

        // Main icon container
        Box(
            modifier = Modifier
                .size(HomeTabDimens.EmptyStateIconSize)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            AppTheme.AccentPrimary.copy(alpha = 0.15f),
                            AppTheme.AccentSecondary.copy(alpha = 0.1f)
                        )
                    ),
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            AppTheme.AccentPrimary.copy(alpha = 0.3f),
                            AppTheme.AccentSecondary.copy(alpha = 0.2f)
                        )
                    ),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.PersonAddAlt,
                contentDescription = null,
                tint = AppTheme.AccentPrimary,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

// ============================================================================
// INSIGHT FEATURE ENUM
// ============================================================================

/**
 * Enumeration of all available insight features in the app.
 *
 * Features are categorized as either implemented or coming soon,
 * with associated metadata for display purposes.
 */
@Stable
enum class InsightFeature(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val color: Color,
    val isImplemented: Boolean
) {
    // ===== IMPLEMENTED FEATURES =====

    FULL_CHART(
        title = "Birth Chart",
        description = "Complete Vedic birth chart analysis",
        icon = Icons.Outlined.GridView,
        color = AppTheme.AccentPrimary,
        isImplemented = true
    ),
    PLANETS(
        title = "Planets",
        description = "Detailed planetary positions & aspects",
        icon = Icons.Outlined.Public,
        color = AppTheme.LifeAreaCareer,
        isImplemented = true
    ),
    YOGAS(
        title = "Yogas",
        description = "Powerful planetary combinations",
        icon = Icons.Outlined.AutoAwesome,
        color = AppTheme.AccentGold,
        isImplemented = true
    ),
    DASHAS(
        title = "Dashas",
        description = "Planetary period predictions",
        icon = Icons.Outlined.Timeline,
        color = AppTheme.LifeAreaSpiritual,
        isImplemented = true
    ),
    TRANSITS(
        title = "Transits",
        description = "Current cosmic influences",
        icon = Icons.Outlined.Sync,
        color = AppTheme.AccentTeal,
        isImplemented = true
    ),
    ASHTAKAVARGA(
        title = "Ashtakavarga",
        description = "House strength analysis",
        icon = Icons.Outlined.BarChart,
        color = AppTheme.SuccessColor,
        isImplemented = true
    ),
    PANCHANGA(
        title = "Panchanga",
        description = "Vedic calendar essentials",
        icon = Icons.Outlined.CalendarMonth,
        color = AppTheme.LifeAreaFinance,
        isImplemented = true
    ),
    MATCHMAKING(
        title = "Matchmaking",
        description = "Kundli Milan compatibility",
        icon = Icons.Outlined.Favorite,
        color = AppTheme.LifeAreaLove,
        isImplemented = true
    ),
    MUHURTA(
        title = "Muhurta",
        description = "Find auspicious timings",
        icon = Icons.Outlined.AccessTime,
        color = AppTheme.WarningColor,
        isImplemented = true
    ),
    REMEDIES(
        title = "Remedies",
        description = "Personalized cosmic remedies",
        icon = Icons.Outlined.Spa,
        color = AppTheme.LifeAreaHealth,
        isImplemented = true
    ),
    VARSHAPHALA(
        title = "Varshaphala",
        description = "Annual solar predictions",
        icon = Icons.Outlined.Cake,
        color = AppTheme.LifeAreaCareer,
        isImplemented = true
    ),

    // ===== COMING SOON FEATURES =====

    PRASHNA(
        title = "Prashna",
        description = "Horary astrology queries",
        icon = Icons.AutoMirrored.Outlined.HelpOutline,
        color = AppTheme.AccentTeal,
        isImplemented = false
    ),
    CHART_COMPARISON(
        title = "Synastry",
        description = "Compare two birth charts",
        icon = Icons.Outlined.CompareArrows,
        color = AppTheme.LifeAreaFinance,
        isImplemented = false
    ),
    NAKSHATRA_ANALYSIS(
        title = "Nakshatras",
        description = "Lunar mansion insights",
        icon = Icons.Outlined.Stars,
        color = AppTheme.AccentGold,
        isImplemented = false
    ),
    SHADBALA(
        title = "Shadbala",
        description = "Sixfold planetary strength",
        icon = Icons.Outlined.Speed,
        color = AppTheme.SuccessColor,
        isImplemented = false
    );

    companion object {
        /**
         * Cached list of implemented features to avoid repeated filtering.
         */
        val implementedFeatures: List<InsightFeature> by lazy {
            entries.filter { it.isImplemented }
        }

        /**
         * Cached list of coming soon features to avoid repeated filtering.
         */
        val comingSoonFeatures: List<InsightFeature> by lazy {
            entries.filter { !it.isImplemented }
        }
    }
}