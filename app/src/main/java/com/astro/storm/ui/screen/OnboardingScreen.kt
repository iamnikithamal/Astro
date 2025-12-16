package com.astro.storm.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
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
    LANGUAGE,
    THEME,
    READY
}

/**
 * Main Onboarding Screen Composable
 *
 * A multi-page onboarding experience with:
 * - Welcome page
 * - Language selection page
 * - Theme selection page
 * - Ready/Get Started page
 *
 * @param onComplete Callback when onboarding is completed
 */
@Composable
fun OnboardingScreen(
    onComplete: () -> Unit
) {
    val context = LocalContext.current
    val themeManager = remember { ThemeManager.getInstance(context) }
    val localizationManager = remember { LocalizationManager.getInstance(context) }
    val onboardingManager = remember { OnboardingManager.getInstance(context) }

    val colors = LocalAppThemeColors.current

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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Page indicator
            Spacer(modifier = Modifier.height(24.dp))
            PageIndicator(
                currentPage = currentPage,
                totalPages = OnboardingPage.entries.size,
                colors = colors
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Page content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when (currentPage) {
                    OnboardingPage.WELCOME -> WelcomePage(colors = colors)
                    OnboardingPage.LANGUAGE -> LanguagePage(
                        currentLanguage = currentLanguage,
                        onLanguageSelected = { localizationManager.setLanguage(it) },
                        colors = colors
                    )
                    OnboardingPage.THEME -> ThemePage(
                        currentThemeMode = currentThemeMode,
                        onThemeModeSelected = { themeManager.setThemeMode(it) },
                        colors = colors
                    )
                    OnboardingPage.READY -> ReadyPage(colors = colors)
                }
            }

            // Navigation buttons
            Spacer(modifier = Modifier.height(24.dp))
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
                    onComplete()
                },
                colors = colors
            )

            Spacer(modifier = Modifier.height(32.dp))
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
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OnboardingPage.entries.forEachIndexed { index, page ->
            val isActive = page == currentPage
            Box(
                modifier = Modifier
                    .size(if (isActive) 10.dp else 8.dp)
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
 * Welcome page content
 */
@Composable
private fun WelcomePage(colors: com.astro.storm.ui.theme.AppThemeColors) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(colors.AccentGold.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Star,
                contentDescription = null,
                tint = colors.AccentGold,
                modifier = Modifier.size(56.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_WELCOME_TITLE),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colors.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_WELCOME_SUBTITLE),
            fontSize = 16.sp,
            color = colors.TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_WELCOME_DESC),
            fontSize = 14.sp,
            color = colors.TextMuted,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Feature highlights
        FeatureHighlights(colors = colors)
    }
}

/**
 * Feature highlights on welcome page
 */
@Composable
private fun FeatureHighlights(colors: com.astro.storm.ui.theme.AppThemeColors) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FeatureItem(
            icon = Icons.Outlined.AutoGraph,
            title = stringResource(StringKey.ONBOARDING_FEATURE_CHARTS),
            description = stringResource(StringKey.ONBOARDING_FEATURE_CHARTS_DESC),
            colors = colors
        )
        FeatureItem(
            icon = Icons.Outlined.Schedule,
            title = stringResource(StringKey.ONBOARDING_FEATURE_DASHAS),
            description = stringResource(StringKey.ONBOARDING_FEATURE_DASHAS_DESC),
            colors = colors
        )
        FeatureItem(
            icon = Icons.Outlined.TrendingUp,
            title = stringResource(StringKey.ONBOARDING_FEATURE_TRANSITS),
            description = stringResource(StringKey.ONBOARDING_FEATURE_TRANSITS_DESC),
            colors = colors
        )
    }
}

/**
 * Single feature item
 */
@Composable
private fun FeatureItem(
    icon: ImageVector,
    title: String,
    description: String,
    colors: com.astro.storm.ui.theme.AppThemeColors
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colors.ChipBackground),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.AccentGold,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = colors.TextPrimary
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = colors.TextMuted
            )
        }
    }
}

/**
 * Language selection page
 */
@Composable
private fun LanguagePage(
    currentLanguage: Language,
    onLanguageSelected: (Language) -> Unit,
    colors: com.astro.storm.ui.theme.AppThemeColors
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(colors.AccentGold.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Language,
                contentDescription = null,
                tint = colors.AccentGold,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_LANGUAGE_TITLE),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colors.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_LANGUAGE_SUBTITLE),
            fontSize = 14.sp,
            color = colors.TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Language options
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Language.entries.forEach { language ->
                LanguageOptionCard(
                    language = language,
                    isSelected = currentLanguage == language,
                    onClick = { onLanguageSelected(language) },
                    colors = colors
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_LANGUAGE_DESC),
            fontSize = 12.sp,
            color = colors.TextMuted,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Language option card
 */
@Composable
private fun LanguageOptionCard(
    language: Language,
    isSelected: Boolean,
    onClick: () -> Unit,
    colors: com.astro.storm.ui.theme.AppThemeColors
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics { contentDescription = language.englishName },
        shape = RoundedCornerShape(16.dp),
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
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = language.nativeName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isSelected) colors.AccentGold else colors.TextPrimary
                )
                Text(
                    text = language.englishName,
                    fontSize = 14.sp,
                    color = colors.TextMuted
                )
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(colors.AccentGold),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = null,
                        tint = if (colors.isDark) colors.ScreenBackground else colors.CardBackground,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

/**
 * Theme selection page
 */
@Composable
private fun ThemePage(
    currentThemeMode: ThemeMode,
    onThemeModeSelected: (ThemeMode) -> Unit,
    colors: com.astro.storm.ui.theme.AppThemeColors
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(colors.AccentGold.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (colors.isDark) Icons.Outlined.DarkMode else Icons.Outlined.LightMode,
                contentDescription = null,
                tint = colors.AccentGold,
                modifier = Modifier.size(48.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_THEME_TITLE),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colors.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_THEME_SUBTITLE),
            fontSize = 14.sp,
            color = colors.TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Theme options
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ThemeOptionCard(
                themeMode = ThemeMode.LIGHT,
                icon = Icons.Outlined.LightMode,
                isSelected = currentThemeMode == ThemeMode.LIGHT,
                onClick = { onThemeModeSelected(ThemeMode.LIGHT) },
                colors = colors
            )
            ThemeOptionCard(
                themeMode = ThemeMode.DARK,
                icon = Icons.Outlined.DarkMode,
                isSelected = currentThemeMode == ThemeMode.DARK,
                onClick = { onThemeModeSelected(ThemeMode.DARK) },
                colors = colors
            )
            ThemeOptionCard(
                themeMode = ThemeMode.SYSTEM,
                icon = Icons.Outlined.Brightness6,
                isSelected = currentThemeMode == ThemeMode.SYSTEM,
                onClick = { onThemeModeSelected(ThemeMode.SYSTEM) },
                colors = colors
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_THEME_DESC),
            fontSize = 12.sp,
            color = colors.TextMuted,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Theme option card
 */
@Composable
private fun ThemeOptionCard(
    themeMode: ThemeMode,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    colors: com.astro.storm.ui.theme.AppThemeColors
) {
    val displayName = when (themeMode) {
        ThemeMode.LIGHT -> stringResource(StringKey.ONBOARDING_THEME_LIGHT)
        ThemeMode.DARK -> stringResource(StringKey.ONBOARDING_THEME_DARK)
        ThemeMode.SYSTEM -> stringResource(StringKey.ONBOARDING_THEME_SYSTEM)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .semantics { contentDescription = displayName },
        shape = RoundedCornerShape(16.dp),
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
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (isSelected) colors.AccentGold.copy(alpha = 0.2f) else colors.ChipBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isSelected) colors.AccentGold else colors.TextSecondary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = displayName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isSelected) colors.AccentGold else colors.TextPrimary
                )
                Text(
                    text = themeMode.description,
                    fontSize = 12.sp,
                    color = colors.TextMuted
                )
            }

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(colors.AccentGold),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Check,
                        contentDescription = null,
                        tint = if (colors.isDark) colors.ScreenBackground else colors.CardBackground,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

/**
 * Ready/Get Started page
 */
@Composable
private fun ReadyPage(colors: com.astro.storm.ui.theme.AppThemeColors) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Success icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(colors.SuccessColor.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = null,
                tint = colors.SuccessColor,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_READY_TITLE),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colors.TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_READY_SUBTITLE),
            fontSize = 16.sp,
            color = colors.TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(StringKey.ONBOARDING_READY_DESC),
            fontSize = 14.sp,
            color = colors.TextMuted,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
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
    colors: com.astro.storm.ui.theme.AppThemeColors
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
                modifier = Modifier.weight(1f).height(52.dp),
                shape = RoundedCornerShape(26.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, colors.BorderColor),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colors.TextPrimary
                )
            ) {
                Text(
                    text = stringResource(StringKey.ONBOARDING_BTN_BACK),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        // Next/Get Started button
        Button(
            onClick = if (isLastPage) onComplete else onNext,
            modifier = Modifier.weight(1f).height(52.dp),
            shape = RoundedCornerShape(26.dp),
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
                        stringResource(StringKey.ONBOARDING_BTN_GET_STARTED)
                    } else {
                        stringResource(StringKey.ONBOARDING_BTN_NEXT)
                    },
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
                if (!isLastPage) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
