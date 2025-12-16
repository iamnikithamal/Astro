package com.astro.storm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.astro.storm.data.localization.LocalizationProvider
import com.astro.storm.data.preferences.OnboardingManager
import com.astro.storm.data.preferences.ThemeManager
import com.astro.storm.data.preferences.ThemeMode
import com.astro.storm.ui.navigation.AstroStormNavigation
import com.astro.storm.ui.screen.OnboardingScreen
import com.astro.storm.ui.theme.AstroStormTheme
import com.astro.storm.ui.theme.LocalAppThemeColors
import com.astro.storm.ui.viewmodel.ChartViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Get managers
            val themeManager = remember { ThemeManager.getInstance(this) }
            val onboardingManager = remember { OnboardingManager.getInstance(this) }

            // Observe theme mode
            val themeMode by themeManager.themeMode.collectAsState()
            val isSystemDarkTheme = isSystemInDarkTheme()

            // Determine if dark theme should be used
            val useDarkTheme = when (themeMode) {
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
                ThemeMode.SYSTEM -> isSystemDarkTheme
            }

            // Observe onboarding completion
            val hasCompletedOnboarding by onboardingManager.hasCompletedOnboarding.collectAsState()
            var showOnboarding by remember { mutableStateOf(!hasCompletedOnboarding) }

            // Track if we should navigate to chart input after onboarding
            var navigateToChartInputAfterOnboarding by remember { mutableStateOf(false) }

            LocalizationProvider {
                AstroStormTheme(darkTheme = useDarkTheme) {
                    val colors = LocalAppThemeColors.current
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = colors.ScreenBackground
                    ) {
                        if (showOnboarding) {
                            OnboardingScreen(
                                onComplete = { shouldNavigateToChartInput ->
                                    navigateToChartInputAfterOnboarding = shouldNavigateToChartInput
                                    showOnboarding = false
                                }
                            )
                        } else {
                            val navController = rememberNavController()
                            val viewModel: ChartViewModel = viewModel()

                            // Navigate to chart input if coming from onboarding
                            LaunchedEffect(navigateToChartInputAfterOnboarding) {
                                if (navigateToChartInputAfterOnboarding) {
                                    navController.navigate("chart_input") {
                                        launchSingleTop = true
                                    }
                                    navigateToChartInputAfterOnboarding = false
                                }
                            }

                            AstroStormNavigation(
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
        }
    }
}
