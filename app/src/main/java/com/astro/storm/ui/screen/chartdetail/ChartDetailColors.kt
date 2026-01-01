package com.astro.storm.ui.screen.chartdetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import com.astro.storm.data.model.Planet
import com.astro.storm.ui.theme.LocalAppThemeColors

/**
 * Theme-aware color palette for ChartDetail screens and components.
 *
 * All properties automatically adapt to light/dark mode using LocalAppThemeColors,
 * ensuring proper display in both themes.
 */
object ChartDetailColors {
    // Screen backgrounds - theme-aware
    val ScreenBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.ScreenBackground

    val SurfaceColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.SurfaceColor

    val CardBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.CardBackground

    val CardBackgroundElevated: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.CardBackgroundElevated

    val ChartBackground: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.ChartBackground

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

    // Divider and utility - theme-aware
    val DividerColor: Color
        @Composable
        @ReadOnlyComposable
        get() = LocalAppThemeColors.current.DividerColor

    // Status colors - theme-aware
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

    /**
     * Returns the appropriate color for a planet - theme-aware.
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
     * Returns color based on strength percentage - theme-aware.
     */
    @Composable
    @ReadOnlyComposable
    fun getStrengthColor(percentage: Double): Color = when {
        percentage >= 100 -> LocalAppThemeColors.current.SuccessColor
        percentage >= 85 -> LocalAppThemeColors.current.WarningColor
        else -> LocalAppThemeColors.current.ErrorColor
    }

    /**
     * Returns color based on bindu score - theme-aware.
     */
    @Composable
    @ReadOnlyComposable
    fun getBinduColor(bindus: Int): Color = when {
        bindus >= 5 -> LocalAppThemeColors.current.SuccessColor
        bindus >= 4 -> LocalAppThemeColors.current.AccentTeal
        bindus <= 2 -> LocalAppThemeColors.current.ErrorColor
        else -> LocalAppThemeColors.current.TextPrimary
    }

    /**
     * Returns color for SAV transit favorability - theme-aware.
     */
    @Composable
    @ReadOnlyComposable
    fun getSavFavorableColor(isFavorable: Boolean): Color =
        if (isFavorable) LocalAppThemeColors.current.SuccessColor
        else LocalAppThemeColors.current.WarningColor
}
