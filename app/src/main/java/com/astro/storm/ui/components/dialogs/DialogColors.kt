package com.astro.storm.ui.components.dialogs

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.astro.storm.data.model.Planet
import com.astro.storm.ui.theme.LocalAppThemeColors

/**
 * Theme-aware dialog colors that automatically adapt to light/dark mode.
 *
 * All properties use the current theme from LocalAppThemeColors, ensuring
 * dialogs display correctly in both light and dark themes.
 */
object DialogColors {
    // Dialog backgrounds - theme-aware
    val DialogBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.DialogBackground

    val DialogSurface: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.CardBackground

    val DialogSurfaceElevated: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.CardBackgroundElevated

    // Accent colors - theme-aware
    val AccentGold: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.AccentGold

    val AccentTeal: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.AccentTeal

    val AccentPurple: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.LifeAreaSpiritual

    val AccentRose: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.LifeAreaLove

    val AccentBlue: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.InfoColor

    val AccentGreen: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.SuccessColor

    val AccentOrange: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.WarningColor

    // Text colors - theme-aware
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

    // Divider - theme-aware
    val DividerColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.DividerColor

    /**
     * Gets the color for a specific planet - theme-aware.
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
        Planet.URANUS -> LocalAppThemeColors.current.AccentTeal
        Planet.NEPTUNE -> LocalAppThemeColors.current.InfoColor
        Planet.PLUTO -> LocalAppThemeColors.current.LifeAreaSpiritual
    }

    /**
     * Gets the color for strength-based indicators - theme-aware.
     */
    @Composable
    @ReadOnlyComposable
    fun getStrengthColor(percentage: Double): Color = when {
        percentage >= 100 -> LocalAppThemeColors.current.SuccessColor
        percentage >= 85 -> LocalAppThemeColors.current.WarningColor
        else -> LocalAppThemeColors.current.ErrorColor
    }
}
