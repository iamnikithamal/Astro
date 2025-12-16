package com.astro.storm.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.LocalizationManager
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.stringResource
import com.astro.storm.data.preferences.OnboardingManager
import com.astro.storm.data.preferences.ThemeManager
import com.astro.storm.data.preferences.ThemeMode
import com.astro.storm.ui.theme.AppTheme
import com.astro.storm.ui.theme.LocalAppThemeColors

/**
 * Onboarding pages enumeration
 */
private enum class OnboardingPage {
    WELCOME,
    FEATURES,
    LANGUAGE,
    THEME,
    READY
}

/**
 * Main Onboarding Screen Composable
 *
 * A responsive, multi-page onboarding experience with:
 * - Welcome page (compact, no lengthy descriptions)
 * - Features overview page (key app capabilities)
 * - Language selection page
 * - Theme selection page
 * - Ready/Get Started page
 *
 * @param onComplete Callback when onboarding is completed
 * @param navigateToChartInput If true, signals intent to navigate to chart input after completion
 */
@Composable
fun OnboardingScreen(
    onComplete: (navigateToChartInput: Boolean) -> Unit = { _ -> }
) {
    val context = LocalContext.current
    val themeManager = remember { ThemeManager.getInstance(context) }
    val localizationManager = remember { LocalizationManager.getInstance(context) }
    val onboardingManager = remember { OnboardingManager.getInstance(context) }

    val colors = LocalAppThemeColors.current
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    // Determine if we're on a small screen
    val isSmallScreen = screenHeight < 700.dp

    var currentPage by remember { mutableStateOf(OnboardingPage.WELCOME) }

    // Current selections
    val currentThemeMode by themeManager.themeMode.collectAsState()
    val currentLanguage by localizationManager.language.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.ScreenBackground)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = if (isSmallScreen) 16.dp else 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Page indicator
            Spacer(modifier = Modifier.height(if (isSmallScreen) 12.dp else 24.dp))
            PageIndicator(
                currentPage = currentPage,
                totalPages = OnboardingPage.entries.size,
                colors = colors
            )

            Spacer(modifier = Modifier.height(if (isSmallScreen) 16.dp else 32.dp))

            // Page content - scrollable for responsiveness
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when (currentPage) {
                    OnboardingPage.WELCOME -> WelcomePage(
                        colors = colors,
                        isSmallScreen = isSmallScreen
                    )
                    OnboardingPage.FEATURES -> FeaturesPage(
                        colors = colors,
                        isSmallScreen = isSmallScreen
                    )
                    OnboardingPage.LANGUAGE -> LanguagePage(
                        currentLanguage = currentLanguage,
                        onLanguageSelected = { localizationManager.setLanguage(it) },
                        colors = colors,
                        isSmallScreen = isSmallScreen
                    )
                    OnboardingPage.THEME -> ThemePage(
                        currentThemeMode = currentThemeMode,
                        onThemeModeSelected = { themeManager.setThemeMode(it) },
                        colors = colors,
                        isSmallScreen = isSmallScreen
                    )
                    OnboardingPage.READY -> ReadyPage(
                        colors = colors,
                        isSmallScreen = isSmallScreen
                    )
                }
            }

            // Navigation buttons
            Spacer(modifier = Modifier.height(if (isSmallScreen) 12.dp else 24.dp))
            NavigationButtons(
                currentPage = currentPage,
                onBack = {
                    val currentIndex = OnboardingPage.entries.indexOf(currentPage)
                    if (currentIndex > 0) {
                        currentPage = OnboardingPage.entries[currentIndex - 1]
                    }
                },
                onNext = {
                    val currentIndex = OnboardingPage.entries.indexOf(currentPage)
                    if (currentIndex < OnboardingPage.entries.size - 1) {
                        currentPage = OnboardingPage.entries[currentIndex + 1]
                    }
                },
                onComplete = {
                    onboardingManager.completeOnboarding()
                    onComplete(true) // Navigate to chart input
                },
                colors = colors,
                isSmallScreen = isSmallScreen
            )

            Spacer(modifier = Modifier.height(if (isSmallScreen) 16.dp else 32.dp))
        }
    }
}

/**
 * Page indicator dots
 */
@Composable
private fun PageIndicator(
    currentPage: OnboardingPage,
    totalPages: Int,
    colors: com.astro.storm.ui.theme.AppThemeColors
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OnboardingPage.entries.forEachIndexed { index, page ->
            val isActive = page == currentPage
            Box(
                modifier = Modifier
                    .size(if (isActive) 10.dp else 6.dp)
                    .clip(CircleShape)
                    .background(
                        if (isActive) colors.AccentGold
                        else colors.BorderColor
                    )
            )
        }
    }
}

/**
 * Welcome page - Clean and compact
 */
@Composable
private fun WelcomePage(
    colors: com.astro.storm.ui.theme.AppThemeColors,
    isSmallScreen: Boolean
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // App Icon
        Box(
            modifier = Modifier
                .size(if (isSmallScreen) 80.dp else 100.dp)
                .clip(CircleShape)
                .background(colors.AccentGold.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = colors.AccentGold,
                modifier = Modifier.size(if (isSmallScreen) 40.dp else 48.dp)
            )
        }

        Spacer(modifier = Modifier.height(if (isSmallScreen) 20.dp else 32.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_WELCOME_TITLE),
            fontSize = if (isSmallScreen) 24.sp else 28.sp,
            fontWeight = FontWeight.Bold,
            color = colors.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isSmallScreen) 12.dp else 20.dp))

        // Brief tagline only - no lengthy description
        Text(
            text = "Precision Vedic Astrology",
            fontSize = if (isSmallScreen) 14.sp else 16.sp,
            color = colors.AccentGold,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isSmallScreen) 24.dp else 40.dp))

        // Compact feature icons grid
        CompactFeatureGrid(colors = colors, isSmallScreen = isSmallScreen)
    }
}

/**
 * Compact feature grid for welcome page
 */
@Composable
private fun CompactFeatureGrid(
    colors: com.astro.storm.ui.theme.AppThemeColors,
    isSmallScreen: Boolean
) {
    val features = listOf(
        Icons.Outlined.AutoGraph to "Birth Charts",
        Icons.Outlined.Timeline to "Dashas",
        Icons.Outlined.Sync to "Transits",
        Icons.Outlined.Stars to "Yogas"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        features.forEach { (icon, label) ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(if (isSmallScreen) 44.dp else 52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(colors.ChipBackground),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = colors.AccentGold,
                        modifier = Modifier.size(if (isSmallScreen) 22.dp else 26.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = label,
                    fontSize = if (isSmallScreen) 10.sp else 11.sp,
                    color = colors.TextMuted,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Features overview page - Key capabilities
 */
@Composable
private fun FeaturesPage(
    colors: com.astro.storm.ui.theme.AppThemeColors,
    isSmallScreen: Boolean
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "What AstroStorm Offers",
            fontSize = if (isSmallScreen) 20.sp else 24.sp,
            fontWeight = FontWeight.Bold,
            color = colors.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isSmallScreen) 16.dp else 24.dp))

        // Feature categories
        FeatureCategory(
            icon = Icons.Outlined.GridView,
            title = "Complete Chart Analysis",
            items = listOf("Rashi & Divisional Charts", "Shadbala & Ashtakavarga", "All 16 Vargas"),
            colors = colors,
            isSmallScreen = isSmallScreen
        )

        Spacer(modifier = Modifier.height(if (isSmallScreen) 12.dp else 16.dp))

        FeatureCategory(
            icon = Icons.Outlined.Timeline,
            title = "Dasha Systems",
            items = listOf("Vimshottari & Yogini", "Ashtottari & Chara", "Kalachakra Dasha"),
            colors = colors,
            isSmallScreen = isSmallScreen
        )

        Spacer(modifier = Modifier.height(if (isSmallScreen) 12.dp else 16.dp))

        FeatureCategory(
            icon = Icons.Outlined.AutoAwesome,
            title = "Predictions & Yogas",
            items = listOf("200+ Yoga Calculations", "Transit Analysis", "Matchmaking (Kundali Milan)"),
            colors = colors,
            isSmallScreen = isSmallScreen
        )

        Spacer(modifier = Modifier.height(if (isSmallScreen) 12.dp else 16.dp))

        FeatureCategory(
            icon = Icons.Outlined.Spa,
            title = "Remedies & Muhurta",
            items = listOf("Vedic & Lal Kitab Remedies", "Auspicious Timing", "Panchanga"),
            colors = colors,
            isSmallScreen = isSmallScreen
        )
    }
}

/**
 * Feature category card
 */
@Composable
private fun FeatureCategory(
    icon: ImageVector,
    title: String,
    items: List<String>,
    colors: com.astro.storm.ui.theme.AppThemeColors,
    isSmallScreen: Boolean
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = colors.CardBackground,
        border = androidx.compose.foundation.BorderStroke(1.dp, colors.BorderColor.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(if (isSmallScreen) 12.dp else 16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(if (isSmallScreen) 36.dp else 40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colors.AccentGold.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = colors.AccentGold,
                    modifier = Modifier.size(if (isSmallScreen) 18.dp else 22.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = if (isSmallScreen) 13.sp else 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.TextPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                items.forEach { item ->
                    Text(
                        text = "• $item",
                        fontSize = if (isSmallScreen) 11.sp else 12.sp,
                        color = colors.TextMuted,
                        lineHeight = if (isSmallScreen) 14.sp else 16.sp
                    )
                }
            }
        }
    }
}

/**
 * Language selection page - Compact
 */
@Composable
private fun LanguagePage(
    currentLanguage: Language,
    onLanguageSelected: (Language) -> Unit,
    colors: com.astro.storm.ui.theme.AppThemeColors,
    isSmallScreen: Boolean
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(if (isSmallScreen) 64.dp else 80.dp)
                .clip(CircleShape)
                .background(colors.AccentGold.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Language,
                contentDescription = null,
                tint = colors.AccentGold,
                modifier = Modifier.size(if (isSmallScreen) 32.dp else 40.dp)
            )
        }

        Spacer(modifier = Modifier.height(if (isSmallScreen) 16.dp else 24.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_LANGUAGE_TITLE),
            fontSize = if (isSmallScreen) 20.sp else 24.sp,
            fontWeight = FontWeight.Bold,
            color = colors.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isSmallScreen) 20.dp else 32.dp))

        // Language options - compact cards
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Language.entries.forEach { language ->
                LanguageOptionCardCompact(
                    language = language,
                    isSelected = currentLanguage == language,
                    onClick = { onLanguageSelected(language) },
                    colors = colors,
                    isSmallScreen = isSmallScreen
                )
            }
        }
    }
}

/**
 * Compact language option card
 */
@Composable
private fun LanguageOptionCardCompact(
    language: Language,
    isSelected: Boolean,
    onClick: () -> Unit,
    colors: com.astro.storm.ui.theme.AppThemeColors,
    isSmallScreen: Boolean
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics { contentDescription = language.englishName },
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) colors.AccentGold.copy(alpha = 0.15f) else colors.CardBackground,
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, colors.AccentGold)
        } else {
            androidx.compose.foundation.BorderStroke(1.dp, colors.BorderColor)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = if (isSmallScreen) 12.dp else 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = language.nativeName,
                    fontSize = if (isSmallScreen) 15.sp else 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) colors.AccentGold else colors.TextPrimary
                )
                Text(
                    text = language.englishName,
                    fontSize = if (isSmallScreen) 12.sp else 13.sp,
                    color = colors.TextMuted
                )
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(colors.AccentGold),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = null,
                        tint = if (colors.isDark) colors.ScreenBackground else colors.CardBackground,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

/**
 * Theme selection page - Compact
 */
@Composable
private fun ThemePage(
    currentThemeMode: ThemeMode,
    onThemeModeSelected: (ThemeMode) -> Unit,
    colors: com.astro.storm.ui.theme.AppThemeColors,
    isSmallScreen: Boolean
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(if (isSmallScreen) 64.dp else 80.dp)
                .clip(CircleShape)
                .background(colors.AccentGold.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (colors.isDark) Icons.Outlined.DarkMode else Icons.Outlined.LightMode,
                contentDescription = null,
                tint = colors.AccentGold,
                modifier = Modifier.size(if (isSmallScreen) 32.dp else 40.dp)
            )
        }

        Spacer(modifier = Modifier.height(if (isSmallScreen) 16.dp else 24.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_THEME_TITLE),
            fontSize = if (isSmallScreen) 20.sp else 24.sp,
            fontWeight = FontWeight.Bold,
            color = colors.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isSmallScreen) 20.dp else 32.dp))

        // Theme options - horizontal layout for compactness
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ThemeOptionCardCompact(
                themeMode = ThemeMode.LIGHT,
                icon = Icons.Outlined.LightMode,
                isSelected = currentThemeMode == ThemeMode.LIGHT,
                onClick = { onThemeModeSelected(ThemeMode.LIGHT) },
                colors = colors,
                modifier = Modifier.weight(1f),
                isSmallScreen = isSmallScreen
            )
            ThemeOptionCardCompact(
                themeMode = ThemeMode.DARK,
                icon = Icons.Outlined.DarkMode,
                isSelected = currentThemeMode == ThemeMode.DARK,
                onClick = { onThemeModeSelected(ThemeMode.DARK) },
                colors = colors,
                modifier = Modifier.weight(1f),
                isSmallScreen = isSmallScreen
            )
            ThemeOptionCardCompact(
                themeMode = ThemeMode.SYSTEM,
                icon = Icons.Outlined.Brightness6,
                isSelected = currentThemeMode == ThemeMode.SYSTEM,
                onClick = { onThemeModeSelected(ThemeMode.SYSTEM) },
                colors = colors,
                modifier = Modifier.weight(1f),
                isSmallScreen = isSmallScreen
            )
        }

        Spacer(modifier = Modifier.height(if (isSmallScreen) 16.dp else 24.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_THEME_DESC),
            fontSize = if (isSmallScreen) 11.sp else 12.sp,
            color = colors.TextMuted,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Compact theme option card
 */
@Composable
private fun ThemeOptionCardCompact(
    themeMode: ThemeMode,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    colors: com.astro.storm.ui.theme.AppThemeColors,
    modifier: Modifier = Modifier,
    isSmallScreen: Boolean
) {
    val displayName = when (themeMode) {
        ThemeMode.LIGHT -> stringResource(StringKey.ONBOARDING_THEME_LIGHT)
        ThemeMode.DARK -> stringResource(StringKey.ONBOARDING_THEME_DARK)
        ThemeMode.SYSTEM -> stringResource(StringKey.ONBOARDING_THEME_SYSTEM)
    }

    Surface(
        modifier = modifier
            .clickable(onClick = onClick)
            .semantics { contentDescription = displayName },
        shape = RoundedCornerShape(12.dp),
        color = if (isSelected) colors.AccentGold.copy(alpha = 0.15f) else colors.CardBackground,
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(2.dp, colors.AccentGold)
        } else {
            androidx.compose.foundation.BorderStroke(1.dp, colors.BorderColor)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = if (isSmallScreen) 12.dp else 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(if (isSmallScreen) 36.dp else 44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isSelected) colors.AccentGold.copy(alpha = 0.2f) else colors.ChipBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) colors.AccentGold else colors.TextSecondary,
                    modifier = Modifier.size(if (isSmallScreen) 20.dp else 24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = displayName,
                fontSize = if (isSmallScreen) 12.sp else 13.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                color = if (isSelected) colors.AccentGold else colors.TextPrimary,
                textAlign = TextAlign.Center
            )

            if (isSelected) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(colors.AccentGold)
                )
            }
        }
    }
}

/**
 * Ready/Get Started page - Compact
 */
@Composable
private fun ReadyPage(
    colors: com.astro.storm.ui.theme.AppThemeColors,
    isSmallScreen: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Success icon
        Box(
            modifier = Modifier
                .size(if (isSmallScreen) 80.dp else 100.dp)
                .clip(CircleShape)
                .background(colors.SuccessColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = colors.SuccessColor,
                modifier = Modifier.size(if (isSmallScreen) 44.dp else 56.dp)
            )
        }

        Spacer(modifier = Modifier.height(if (isSmallScreen) 24.dp else 32.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_READY_TITLE),
            fontSize = if (isSmallScreen) 22.sp else 26.sp,
            fontWeight = FontWeight.Bold,
            color = colors.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isSmallScreen) 8.dp else 12.dp))

        Text(
            text = "Create Your First Chart",
            fontSize = if (isSmallScreen) 14.sp else 16.sp,
            color = colors.AccentGold,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(if (isSmallScreen) 16.dp else 24.dp))

        // Quick start info
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = colors.CardBackground,
            border = androidx.compose.foundation.BorderStroke(1.dp, colors.BorderColor.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(if (isSmallScreen) 16.dp else 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = colors.AccentTeal,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "You'll need:",
                        fontSize = if (isSmallScreen) 13.sp else 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = colors.TextPrimary
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    QuickStartItem("Birth date & time", colors, isSmallScreen)
                    QuickStartItem("Birth location (city)", colors, isSmallScreen)
                    QuickStartItem("That's all!", colors, isSmallScreen)
                }
            }
        }
    }
}

@Composable
private fun QuickStartItem(
    text: String,
    colors: com.astro.storm.ui.theme.AppThemeColors,
    isSmallScreen: Boolean
) {
    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(colors.AccentGold)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            fontSize = if (isSmallScreen) 12.sp else 13.sp,
            color = colors.TextSecondary
        )
    }
}

/**
 * Navigation buttons (Back/Next/Get Started)
 */
@Composable
private fun NavigationButtons(
    currentPage: OnboardingPage,
    onBack: () -> Unit,
    onNext: () -> Unit,
    onComplete: () -> Unit,
    colors: com.astro.storm.ui.theme.AppThemeColors,
    isSmallScreen: Boolean
) {
    val isFirstPage = currentPage == OnboardingPage.WELCOME
    val isLastPage = currentPage == OnboardingPage.READY

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Back button (hidden on first page)
        if (!isFirstPage) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .weight(1f)
                    .height(if (isSmallScreen) 44.dp else 52.dp),
                shape = RoundedCornerShape(if (isSmallScreen) 22.dp else 26.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, colors.BorderColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colors.TextPrimary
                )
            ) {
                Text(
                    text = stringResource(StringKey.ONBOARDING_BTN_BACK),
                    fontSize = if (isSmallScreen) 14.sp else 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        // Next/Create Chart button
        Button(
            onClick = if (isLastPage) onComplete else onNext,
            modifier = Modifier
                .weight(1f)
                .height(if (isSmallScreen) 44.dp else 52.dp),
            shape = RoundedCornerShape(if (isSmallScreen) 22.dp else 26.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colors.AccentGold,
                contentColor = if (colors.isDark) colors.ScreenBackground else colors.CardBackground
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = if (isLastPage) {
                        "Create Chart"
                    } else {
                        stringResource(StringKey.ONBOARDING_BTN_NEXT)
                    },
                    fontSize = if (isSmallScreen) 14.sp else 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                if (!isLastPage) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(if (isSmallScreen) 16.dp else 18.dp)
                    )
                }
            }
        }
    }
}
