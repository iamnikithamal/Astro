package com.astro.storm.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.ZodiacSign

/**
 * Unified App Theme Colors with Dark/Light Mode Support
 *
 * These colors provide a cohesive look throughout the app.
 * Dark mode uses a warm brown theme, light mode uses cream/beige tones.
 */
data class AppThemeColors(
    // Primary Background Colors
    val ScreenBackground: Color,
    val CardBackground: Color,
    val CardBackgroundElevated: Color,
    val SurfaceColor: Color,

    // Accent Colors
    val AccentPrimary: Color,
    val AccentSecondary: Color,
    val AccentGold: Color,
    val AccentTeal: Color,

    // Text Colors
    val TextPrimary: Color,
    val TextSecondary: Color,
    val TextMuted: Color,
    val TextSubtle: Color,

    // Border and Divider Colors
    val BorderColor: Color,
    val DividerColor: Color,

    // Interactive Element Colors
    val ChipBackground: Color,
    val ChipBackgroundSelected: Color,
    val ButtonBackground: Color,
    val ButtonText: Color,

    // Status Colors
    val SuccessColor: Color,
    val WarningColor: Color,
    val ErrorColor: Color,
    val InfoColor: Color,

    // Chart-Specific Colors
    val ChartBackground: Color,
    val ChartBorder: Color,

    // Planet Colors
    val PlanetSun: Color,
    val PlanetMoon: Color,
    val PlanetMars: Color,
    val PlanetMercury: Color,
    val PlanetJupiter: Color,
    val PlanetVenus: Color,
    val PlanetSaturn: Color,
    val PlanetRahu: Color,
    val PlanetKetu: Color,

    // Navigation Colors
    val NavBarBackground: Color,
    val NavItemSelected: Color,
    val NavItemUnselected: Color,
    val NavIndicator: Color,

    // Bottom Sheet Colors
    val BottomSheetBackground: Color,
    val BottomSheetHandle: Color,

    // Prediction Card Colors
    val PredictionCardToday: Color,
    val PredictionCardTomorrow: Color,
    val PredictionCardWeekly: Color,

    // Life Area Colors
    val LifeAreaCareer: Color,
    val LifeAreaLove: Color,
    val LifeAreaHealth: Color,
    val LifeAreaGrowth: Color,
    val LifeAreaFinance: Color,
    val LifeAreaSpiritual: Color,

    // Additional Colors for Light Theme
    val InputBackground: Color,
    val DialogBackground: Color,
    val ScrimColor: Color,

    // Is dark theme flag
    val isDark: Boolean
)

/**
 * Dark Theme Colors - Warm brown tones for nighttime viewing
 */
val DarkAppThemeColors = AppThemeColors(
    // Primary Background Colors
    ScreenBackground = Color(0xFF1C1410),
    CardBackground = Color(0xFF2A201A),
    CardBackgroundElevated = Color(0xFF352A22),
    SurfaceColor = Color(0xFF241C16),

    // Accent Colors
    AccentPrimary = Color(0xFFB8A99A),
    AccentSecondary = Color(0xFF8B7355),
    AccentGold = Color(0xFFD4AF37),
    AccentTeal = Color(0xFF4DB6AC),

    // Text Colors (WCAG AA compliant on ScreenBackground #1C1410)
    TextPrimary = Color(0xFFE8DFD6),
    TextSecondary = Color(0xFFB8A99A),
    TextMuted = Color(0xFF9A8A7A),      // Improved from 0xFF8A7A6A for better contrast (4.5:1+)
    TextSubtle = Color(0xFF8A7A6A),     // Improved from 0xFF6A5A4A for better contrast (3:1+)

    // Border and Divider Colors
    BorderColor = Color(0xFF5A4F48),    // Improved from 0xFF4A3F38 for better visibility
    DividerColor = Color(0xFF4A403A),   // Improved from 0xFF3A302A for better visibility

    // Interactive Element Colors
    ChipBackground = Color(0xFF3D322B),
    ChipBackgroundSelected = Color(0xFF4A3F38),
    ButtonBackground = Color(0xFFB8A99A),
    ButtonText = Color(0xFF1C1410),

    // Status Colors
    SuccessColor = Color(0xFF81C784),
    WarningColor = Color(0xFFFFB74D),
    ErrorColor = Color(0xFFCF6679),
    InfoColor = Color(0xFF64B5F6),

    // Chart-Specific Colors
    ChartBackground = Color(0xFF1A1512),
    ChartBorder = Color(0xFFB8A99A),

    // Planet Colors
    PlanetSun = Color(0xFFD2691E),
    PlanetMoon = Color(0xFFDC143C),
    PlanetMars = Color(0xFFDC143C),
    PlanetMercury = Color(0xFF228B22),
    PlanetJupiter = Color(0xFFDAA520),
    PlanetVenus = Color(0xFF9370DB),
    PlanetSaturn = Color(0xFF4169E1),
    PlanetRahu = Color(0xFF8B0000),
    PlanetKetu = Color(0xFF8B0000),

    // Navigation Colors
    NavBarBackground = Color(0xFF241C16),
    NavItemSelected = Color(0xFFB8A99A),
    NavItemUnselected = Color(0xFF6A5A4A),
    NavIndicator = Color(0xFF3D322B),

    // Bottom Sheet Colors
    BottomSheetBackground = Color(0xFF2A201A),
    BottomSheetHandle = Color(0xFF4A3F38),

    // Prediction Card Colors
    PredictionCardToday = Color(0xFF2D2520),
    PredictionCardTomorrow = Color(0xFF2A2520),
    PredictionCardWeekly = Color(0xFF282520),

    // Life Area Colors
    LifeAreaCareer = Color(0xFFFFB74D),
    LifeAreaLove = Color(0xFFE57373),
    LifeAreaHealth = Color(0xFF81C784),
    LifeAreaGrowth = Color(0xFF64B5F6),
    LifeAreaFinance = Color(0xFFFFD54F),
    LifeAreaSpiritual = Color(0xFFBA68C8),

    // Additional Colors
    InputBackground = Color(0xFF2A201A),
    DialogBackground = Color(0xFF2A201A),
    ScrimColor = Color(0x80000000),

    // Is dark theme flag
    isDark = true
)

/**
 * Light Theme Colors - Cream/beige tones for daytime viewing
 */
val LightAppThemeColors = AppThemeColors(
    // Primary Background Colors
    ScreenBackground = Color(0xFFF5F2ED),
    CardBackground = Color(0xFFFFFFFF),
    CardBackgroundElevated = Color(0xFFFAF8F5),
    SurfaceColor = Color(0xFFFEFCF9),

    // Accent Colors
    AccentPrimary = Color(0xFF6B5D4D),
    AccentSecondary = Color(0xFF8B7355),
    AccentGold = Color(0xFFB8860B),
    AccentTeal = Color(0xFF008B8B),

    // Text Colors
    TextPrimary = Color(0xFF2C2418),
    TextSecondary = Color(0xFF5A4D3D),
    TextMuted = Color(0xFF7A6D5D),
    TextSubtle = Color(0xFFA99D8D),

    // Border and Divider Colors
    BorderColor = Color(0xFFD4C8B8),
    DividerColor = Color(0xFFE8DFD6),

    // Interactive Element Colors
    ChipBackground = Color(0xFFEDE7DF),
    ChipBackgroundSelected = Color(0xFFD4C8B8),
    ButtonBackground = Color(0xFF6B5D4D),
    ButtonText = Color(0xFFFFFFFF),

    // Status Colors
    SuccessColor = Color(0xFF2E7D32),
    WarningColor = Color(0xFFED6C02),
    ErrorColor = Color(0xFFD32F2F),
    InfoColor = Color(0xFF0288D1),

    // Chart-Specific Colors
    ChartBackground = Color(0xFFFAF8F5),
    ChartBorder = Color(0xFF6B5D4D),

    // Planet Colors (slightly adjusted for light mode visibility)
    PlanetSun = Color(0xFFCD6600),
    PlanetMoon = Color(0xFFB22222),
    PlanetMars = Color(0xFFB22222),
    PlanetMercury = Color(0xFF006400),
    PlanetJupiter = Color(0xFFB8860B),
    PlanetVenus = Color(0xFF7B68EE),
    PlanetSaturn = Color(0xFF3A5FCD),
    PlanetRahu = Color(0xFF8B0000),
    PlanetKetu = Color(0xFF8B0000),

    // Navigation Colors
    NavBarBackground = Color(0xFFFFFFFF),
    NavItemSelected = Color(0xFF6B5D4D),
    NavItemUnselected = Color(0xFFA99D8D),
    NavIndicator = Color(0xFFEDE7DF),

    // Bottom Sheet Colors
    BottomSheetBackground = Color(0xFFFFFFFF),
    BottomSheetHandle = Color(0xFFD4C8B8),

    // Prediction Card Colors
    PredictionCardToday = Color(0xFFFFFFFF),
    PredictionCardTomorrow = Color(0xFFFAF8F5),
    PredictionCardWeekly = Color(0xFFF5F2ED),

    // Life Area Colors (adjusted for light mode)
    LifeAreaCareer = Color(0xFFED6C02),
    LifeAreaLove = Color(0xFFC62828),
    LifeAreaHealth = Color(0xFF2E7D32),
    LifeAreaGrowth = Color(0xFF0277BD),
    LifeAreaFinance = Color(0xFFF9A825),
    LifeAreaSpiritual = Color(0xFF7B1FA2),

    // Additional Colors
    InputBackground = Color(0xFFFAF8F5),
    DialogBackground = Color(0xFFFFFFFF),
    ScrimColor = Color(0x40000000),

    // Is dark theme flag
    isDark = false
)

/**
 * CompositionLocal for accessing theme colors
 */
val LocalAppThemeColors = staticCompositionLocalOf { DarkAppThemeColors }

/**
 * App Theme object for accessing current theme colors
 *
 * IMPORTANT: In Composable functions, use LocalAppThemeColors.current for theme-aware colors.
 * The static getters on this object are DEPRECATED and only kept for backward compatibility.
 * They always return dark theme colors regardless of the actual theme setting.
 *
 * For new code, always use:
 *   val colors = LocalAppThemeColors.current
 *   colors.ScreenBackground // etc.
 */
object AppTheme {
    // Current theme accessor for Composables - USE THIS FOR THEME-AWARE COLORS
    val current: AppThemeColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current

    // COMPOSABLE THEME-AWARE ACCESSORS
    // These are the preferred way to access colors in Composable functions
    val ScreenBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.ScreenBackground

    val CardBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.CardBackground

    val CardBackgroundElevated: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.CardBackgroundElevated

    val SurfaceColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.SurfaceColor

    val AccentPrimary: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.AccentPrimary

    val AccentSecondary: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.AccentSecondary

    val AccentGold: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.AccentGold

    val AccentTeal: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.AccentTeal

    val TextPrimary: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.TextPrimary

    val TextSecondary: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.TextSecondary

    val TextMuted: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.TextMuted

    val TextSubtle: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.TextSubtle

    val BorderColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.BorderColor

    val DividerColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.DividerColor

    val ChipBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.ChipBackground

    val ChipBackgroundSelected: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.ChipBackgroundSelected

    val ButtonBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.ButtonBackground

    val ButtonText: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.ButtonText

    val SuccessColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.SuccessColor

    val WarningColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.WarningColor

    val ErrorColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.ErrorColor

    val InfoColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.InfoColor

    val ChartBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.ChartBackground

    val ChartBorder: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.ChartBorder

    val PlanetSun: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PlanetSun

    val PlanetMoon: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PlanetMoon

    val PlanetMars: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PlanetMars

    val PlanetMercury: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PlanetMercury

    val PlanetJupiter: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PlanetJupiter

    val PlanetVenus: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PlanetVenus

    val PlanetSaturn: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PlanetSaturn

    val PlanetRahu: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PlanetRahu

    val PlanetKetu: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PlanetKetu

    val NavBarBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.NavBarBackground

    val NavItemSelected: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.NavItemSelected

    val NavItemUnselected: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.NavItemUnselected

    val NavIndicator: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.NavIndicator

    val BottomSheetBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.BottomSheetBackground

    val BottomSheetHandle: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.BottomSheetHandle

    val PredictionCardToday: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PredictionCardToday

    val PredictionCardTomorrow: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PredictionCardTomorrow

    val PredictionCardWeekly: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.PredictionCardWeekly

    val LifeAreaCareer: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.LifeAreaCareer

    val LifeAreaLove: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.LifeAreaLove

    val LifeAreaHealth: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.LifeAreaHealth

    val LifeAreaGrowth: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.LifeAreaGrowth

    val LifeAreaFinance: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.LifeAreaFinance

    val LifeAreaSpiritual: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.LifeAreaSpiritual

    // Alias for backward compatibility
    val CardElevated: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.CardBackgroundElevated

    /**
     * Get color for a specific planet - Theme-aware composable function
     */
    @Composable
    @ReadOnlyComposable
    fun getPlanetColor(planet: Planet): Color = when (planet) {
        Planet.SUN -> LocalAppThemeColors.current.PlanetSun
        Planet.MOON -> LocalAppThemeColors.current.PlanetMoon
        Planet.MARS -> LocalAppThemeColors.current.PlanetMars
        Planet.MERCURY -> LocalAppThemeColors.current.PlanetMercury
        Planet.JUPITER -> LocalAppThemeColors.current.PlanetJupiter
        Planet.VENUS -> LocalAppThemeColors.current.PlanetVenus
        Planet.SATURN -> LocalAppThemeColors.current.PlanetSaturn
        Planet.RAHU -> LocalAppThemeColors.current.PlanetRahu
        Planet.KETU -> LocalAppThemeColors.current.PlanetKetu
        else -> LocalAppThemeColors.current.AccentGold
    }

    /**
     * Get color for a specific zodiac sign based on its element
     * - Fire signs (Aries, Leo, Sagittarius): Orange/Red tones
     * - Earth signs (Taurus, Virgo, Capricorn): Brown/Green tones
     * - Air signs (Gemini, Libra, Aquarius): Blue/Cyan tones
     * - Water signs (Cancer, Scorpio, Pisces): Purple/Blue tones
     */
    fun getSignColor(sign: ZodiacSign): Color = when (sign) {
        // Fire signs - energetic, warm tones
        ZodiacSign.ARIES -> Color(0xFFE53935)      // Red
        ZodiacSign.LEO -> Color(0xFFF57C00)        // Orange
        ZodiacSign.SAGITTARIUS -> Color(0xFFFF7043) // Deep orange

        // Earth signs - stable, grounded tones
        ZodiacSign.TAURUS -> Color(0xFF43A047)     // Green
        ZodiacSign.VIRGO -> Color(0xFF8BC34A)      // Light green
        ZodiacSign.CAPRICORN -> Color(0xFF795548)  // Brown

        // Air signs - intellectual, light tones
        ZodiacSign.GEMINI -> Color(0xFF29B6F6)     // Light blue
        ZodiacSign.LIBRA -> Color(0xFF26C6DA)      // Cyan
        ZodiacSign.AQUARIUS -> Color(0xFF5C6BC0)   // Indigo

        // Water signs - emotional, deep tones
        ZodiacSign.CANCER -> Color(0xFF9575CD)     // Light purple
        ZodiacSign.SCORPIO -> Color(0xFF7E57C2)    // Purple
        ZodiacSign.PISCES -> Color(0xFF42A5F5)     // Blue
    }
}

/**
 * Provider composable for theme colors
 */
@Composable
fun ProvideAppThemeColors(
    colors: AppThemeColors,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalAppThemeColors provides colors) {
        content()
    }
}
