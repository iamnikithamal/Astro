package com.astro.storm.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.astro.storm.data.model.VedicChart
import com.astro.storm.ui.screen.ArgalaScreen
import com.astro.storm.ui.screen.AshtakavargaScreen
import com.astro.storm.ui.screen.BhriguBinduScreen
import com.astro.storm.ui.screen.BirthChartScreen
import com.astro.storm.ui.screen.CharaDashaScreen
import com.astro.storm.ui.screen.ChartAnalysisScreen
import com.astro.storm.ui.screen.ChartInputScreen
import com.astro.storm.ui.screen.DashasScreen
import com.astro.storm.ui.screen.matchmaking.MatchmakingScreen
import com.astro.storm.ui.screen.MuhurtaScreen
import com.astro.storm.ui.screen.NakshatraScreen
import com.astro.storm.ui.screen.PanchangaScreen
import com.astro.storm.ui.screen.PlanetsScreen
import com.astro.storm.ui.screen.PrashnaScreen
import com.astro.storm.ui.screen.PredictionsScreen
import com.astro.storm.ui.screen.ProfileEditScreen
import com.astro.storm.ui.screen.RemediesScreen
import com.astro.storm.ui.screen.ShadbalaScreen
import com.astro.storm.ui.screen.ShodashvargaScreen
import com.astro.storm.ui.screen.SynastryScreen
import com.astro.storm.ui.screen.TransitsScreen
import com.astro.storm.ui.screen.VarshaphalaScreen
import com.astro.storm.ui.screen.YogasScreen
import com.astro.storm.ui.screen.YoginiDashaScreen
import com.astro.storm.ui.screen.AshtottariDashaScreen
import com.astro.storm.ui.screen.SudarshanaChakraScreen
import com.astro.storm.ui.screen.MrityuBhagaScreen
import com.astro.storm.ui.screen.LalKitabRemediesScreen
import com.astro.storm.ui.screen.DivisionalChartsScreen
import com.astro.storm.ui.screen.UpachayaTransitScreen
import com.astro.storm.ui.screen.KalachakraDashaScreen
import com.astro.storm.ui.screen.tarabala.TarabalaScreen
import com.astro.storm.ui.screen.AiModelsScreen
import com.astro.storm.ui.screen.main.ExportFormat
import com.astro.storm.ui.screen.main.InsightFeature
import com.astro.storm.ui.screen.main.MainScreen
import com.astro.storm.ui.theme.AppTheme
import com.astro.storm.ui.viewmodel.ChartViewModel
import com.astro.storm.ui.viewmodel.ChatViewModel
import com.astro.storm.data.ai.provider.AiProviderRegistry

/**
 * Navigation routes
 */
sealed class Screen(val route: String) {
    object Main : Screen("main")
    object ChartInput : Screen("chart_input")
    object ChartAnalysis : Screen("chart_analysis/{chartId}/{feature}") {
        fun createRoute(chartId: Long, feature: InsightFeature = InsightFeature.FULL_CHART) =
            "chart_analysis/$chartId/${feature.name}"
    }

    // New feature screens
    object Matchmaking : Screen("matchmaking")
    object Muhurta : Screen("muhurta")
    object Remedies : Screen("remedies/{chartId}") {
        fun createRoute(chartId: Long) = "remedies/$chartId"
    }
    object Varshaphala : Screen("varshaphala/{chartId}") {
        fun createRoute(chartId: Long) = "varshaphala/$chartId"
    }
    object Prashna : Screen("prashna")

    // New advanced feature screens
    object Synastry : Screen("synastry")
    object Nakshatra : Screen("nakshatra/{chartId}") {
        fun createRoute(chartId: Long) = "nakshatra/$chartId"
    }
    object Shadbala : Screen("shadbala/{chartId}") {
        fun createRoute(chartId: Long) = "shadbala/$chartId"
    }

    // Individual chart analysis screens
    object BirthChart : Screen("birth_chart/{chartId}") {
        fun createRoute(chartId: Long) = "birth_chart/$chartId"
    }
    object Planets : Screen("planets/{chartId}") {
        fun createRoute(chartId: Long) = "planets/$chartId"
    }
    object Yogas : Screen("yogas/{chartId}") {
        fun createRoute(chartId: Long) = "yogas/$chartId"
    }
    object Dashas : Screen("dashas/{chartId}") {
        fun createRoute(chartId: Long) = "dashas/$chartId"
    }
    object Transits : Screen("transits/{chartId}") {
        fun createRoute(chartId: Long) = "transits/$chartId"
    }
    object Ashtakavarga : Screen("ashtakavarga/{chartId}") {
        fun createRoute(chartId: Long) = "ashtakavarga/$chartId"
    }
    object Panchanga : Screen("panchanga/{chartId}") {
        fun createRoute(chartId: Long) = "panchanga/$chartId"
    }
    object ProfileEdit : Screen("profile_edit/{chartId}") {
        fun createRoute(chartId: Long) = "profile_edit/$chartId"
    }

    // Advanced Calculator Screens
    object Shodashvarga : Screen("shodashvarga/{chartId}") {
        fun createRoute(chartId: Long) = "shodashvarga/$chartId"
    }
    object YoginiDasha : Screen("yogini_dasha/{chartId}") {
        fun createRoute(chartId: Long) = "yogini_dasha/$chartId"
    }
    object Argala : Screen("argala/{chartId}") {
        fun createRoute(chartId: Long) = "argala/$chartId"
    }
    object CharaDasha : Screen("chara_dasha/{chartId}") {
        fun createRoute(chartId: Long) = "chara_dasha/$chartId"
    }
    object BhriguBindu : Screen("bhrigu_bindu/{chartId}") {
        fun createRoute(chartId: Long) = "bhrigu_bindu/$chartId"
    }
    object Predictions : Screen("predictions/{chartId}") {
        fun createRoute(chartId: Long) = "predictions/$chartId"
    }

    // New Advanced Feature Screens
    object AshtottariDasha : Screen("ashtottari_dasha/{chartId}") {
        fun createRoute(chartId: Long) = "ashtottari_dasha/$chartId"
    }
    object SudarshanaChakra : Screen("sudarshana_chakra/{chartId}") {
        fun createRoute(chartId: Long) = "sudarshana_chakra/$chartId"
    }
    object MrityuBhaga : Screen("mrityu_bhaga/{chartId}") {
        fun createRoute(chartId: Long) = "mrityu_bhaga/$chartId"
    }
    object LalKitabRemedies : Screen("lal_kitab/{chartId}") {
        fun createRoute(chartId: Long) = "lal_kitab/$chartId"
    }
    object DivisionalCharts : Screen("divisional_charts/{chartId}") {
        fun createRoute(chartId: Long) = "divisional_charts/$chartId"
    }
    object UpachayaTransit : Screen("upachaya_transit/{chartId}") {
        fun createRoute(chartId: Long) = "upachaya_transit/$chartId"
    }
    object KalachakraDasha : Screen("kalachakra_dasha/{chartId}") {
        fun createRoute(chartId: Long) = "kalachakra_dasha/$chartId"
    }
    object Tarabala : Screen("tarabala/{chartId}") {
        fun createRoute(chartId: Long) = "tarabala/$chartId"
    }

    // AI Models configuration screen
    object AiModels : Screen("ai_models")
}

/**
 * Main navigation graph - Redesigned
 *
 * The new navigation structure:
 * - Main: Primary screen with Home, Insights, Settings tabs
 * - ChartInput: Birth data input (unchanged)
 * - ChartAnalysis: Detailed chart analysis with horizontal tabs
 */
@Composable
fun AstroStormNavigation(
    navController: NavHostController,
    viewModel: ChartViewModel = viewModel(),
    chatViewModel: ChatViewModel = viewModel()
) {
    val savedCharts by viewModel.savedCharts.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val density = LocalDensity.current
    val context = LocalContext.current
    val selectedChartId by viewModel.selectedChartId.collectAsState()

    // Get AI Provider Registry for AI Models screen
    val providerRegistry = remember { AiProviderRegistry.getInstance(context) }

    var currentChart by remember { mutableStateOf<VedicChart?>(null) }

    // Update current chart from UI state
    LaunchedEffect(uiState) {
        if (uiState is com.astro.storm.ui.viewmodel.ChartUiState.Success) {
            currentChart = (uiState as com.astro.storm.ui.viewmodel.ChartUiState.Success).chart
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        // Main screen with bottom navigation
        composable(Screen.Main.route) {
            MainScreen(
                viewModel = viewModel,
                chatViewModel = chatViewModel,
                savedCharts = savedCharts,
                currentChart = currentChart,
                selectedChartId = selectedChartId,
                onChartSelected = viewModel::loadChart,
                onAddNewChart = {
                    navController.navigate(Screen.ChartInput.route)
                },
                onNavigateToChartAnalysis = { feature ->
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.ChartAnalysis.createRoute(chartId, feature))
                    }
                },
                onNavigateToMatchmaking = {
                    navController.navigate(Screen.Matchmaking.route)
                },
                onNavigateToMuhurta = {
                    navController.navigate(Screen.Muhurta.route)
                },
                onNavigateToRemedies = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Remedies.createRoute(chartId))
                    }
                },
                onNavigateToVarshaphala = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Varshaphala.createRoute(chartId))
                    }
                },
                onNavigateToPrashna = {
                    navController.navigate(Screen.Prashna.route)
                },
                onNavigateToBirthChart = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.BirthChart.createRoute(chartId))
                    }
                },
                onNavigateToPlanets = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Planets.createRoute(chartId))
                    }
                },
                onNavigateToYogas = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Yogas.createRoute(chartId))
                    }
                },
                onNavigateToDashas = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Dashas.createRoute(chartId))
                    }
                },
                onNavigateToTransits = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Transits.createRoute(chartId))
                    }
                },
                onNavigateToAshtakavarga = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Ashtakavarga.createRoute(chartId))
                    }
                },
                onNavigateToPanchanga = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Panchanga.createRoute(chartId))
                    }
                },
                onNavigateToProfileEdit = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.ProfileEdit.createRoute(chartId))
                    }
                },
                onNavigateToSynastry = {
                    navController.navigate(Screen.Synastry.route)
                },
                onNavigateToNakshatra = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Nakshatra.createRoute(chartId))
                    }
                },
                onNavigateToShadbala = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Shadbala.createRoute(chartId))
                    }
                },
                onNavigateToShodashvarga = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Shodashvarga.createRoute(chartId))
                    }
                },
                onNavigateToYoginiDasha = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.YoginiDasha.createRoute(chartId))
                    }
                },
                onNavigateToArgala = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Argala.createRoute(chartId))
                    }
                },
                onNavigateToCharaDasha = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.CharaDasha.createRoute(chartId))
                    }
                },
                onNavigateToBhriguBindu = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.BhriguBindu.createRoute(chartId))
                    }
                },
                onNavigateToPredictions = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Predictions.createRoute(chartId))
                    }
                },
                onNavigateToAshtottariDasha = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.AshtottariDasha.createRoute(chartId))
                    }
                },
                onNavigateToSudarshanaChakra = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.SudarshanaChakra.createRoute(chartId))
                    }
                },
                onNavigateToMrityuBhaga = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.MrityuBhaga.createRoute(chartId))
                    }
                },
                onNavigateToLalKitab = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.LalKitabRemedies.createRoute(chartId))
                    }
                },
                onNavigateToDivisionalCharts = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.DivisionalCharts.createRoute(chartId))
                    }
                },
                onNavigateToUpachayaTransit = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.UpachayaTransit.createRoute(chartId))
                    }
                },
                onNavigateToKalachakraDasha = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.KalachakraDasha.createRoute(chartId))
                    }
                },
                onNavigateToTarabala = {
                    selectedChartId?.let { chartId ->
                        navController.navigate(Screen.Tarabala.createRoute(chartId))
                    }
                },
                onNavigateToAiModels = {
                    navController.navigate(Screen.AiModels.route)
                },
                onExportChart = { format ->
                    currentChart?.let { chart ->
                        when (format) {
                            ExportFormat.PDF -> viewModel.exportChartToPdf(chart, density)
                            ExportFormat.IMAGE -> viewModel.exportChartToImage(chart, density)
                            ExportFormat.JSON -> viewModel.exportChartToJson(chart)
                            ExportFormat.CSV -> viewModel.exportChartToCsv(chart)
                            ExportFormat.CLIPBOARD -> viewModel.copyChartToClipboard(chart)
                        }
                    }
                }
            )
        }

        // Chart input screen (unchanged functionality, same screen)
        composable(Screen.ChartInput.route) {
            ChartInputScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onChartCalculated = {
                    // After chart is saved, navigate back and it will auto-select
                    navController.popBackStack()
                }
            )
        }

        // Chart analysis screen with feature parameter
        composable(
            route = Screen.ChartAnalysis.route,
            arguments = listOf(
                navArgument("chartId") { type = NavType.LongType },
                navArgument("feature") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable
            val featureName = backStackEntry.arguments?.getString("feature") ?: InsightFeature.FULL_CHART.name
            val feature = try {
                InsightFeature.valueOf(featureName)
            } catch (e: Exception) {
                InsightFeature.FULL_CHART
            }

            // Load chart if not already loaded
            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            currentChart?.let { chart ->
                ChartAnalysisScreen(
                    chart = chart,
                    initialFeature = feature,
                    viewModel = viewModel,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }

        // Matchmaking screen
        composable(Screen.Matchmaking.route) {
            MatchmakingScreen(
                savedCharts = savedCharts,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // Muhurta screen
        composable(Screen.Muhurta.route) {
            MuhurtaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Remedies screen
        composable(
            route = Screen.Remedies.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            RemediesScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Varshaphala screen
        composable(
            route = Screen.Varshaphala.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            VarshaphalaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Prashna (Horary) screen
        composable(Screen.Prashna.route) {
            PrashnaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Birth Chart screen
        composable(
            route = Screen.BirthChart.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            // Use theme-aware chart renderer
            val isDarkTheme = AppTheme.current.isDark
            val themeAwareChartRenderer = remember(isDarkTheme) {
                viewModel.getChartRenderer(isDarkTheme)
            }

            BirthChartScreen(
                chart = currentChart,
                chartRenderer = themeAwareChartRenderer,
                onBack = { navController.popBackStack() },
                onCopyToClipboard = {
                    currentChart?.let {
                        viewModel.copyChartToClipboard(it)
                    }
                }
            )
        }

        // Planets screen
        composable(
            route = Screen.Planets.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            PlanetsScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Yogas screen
        composable(
            route = Screen.Yogas.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            YogasScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Dashas screen
        composable(
            route = Screen.Dashas.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            DashasScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() },
                onNavigateToYoginiDasha = {
                    chartId?.let { navController.navigate(Screen.YoginiDasha.createRoute(it)) }
                },
                onNavigateToCharaDasha = {
                    chartId?.let { navController.navigate(Screen.CharaDasha.createRoute(it)) }
                }
            )
        }

        // Transits screen
        composable(
            route = Screen.Transits.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            TransitsScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Ashtakavarga screen
        composable(
            route = Screen.Ashtakavarga.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            AshtakavargaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Panchanga screen
        composable(
            route = Screen.Panchanga.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            PanchangaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Profile Edit screen
        composable(
            route = Screen.ProfileEdit.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            ProfileEditScreen(
                chart = currentChart,
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onSaveComplete = { navController.popBackStack() }
            )
        }

        // Synastry (Chart Comparison) screen
        composable(Screen.Synastry.route) {
            SynastryScreen(
                savedCharts = savedCharts,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // Nakshatra Analysis screen
        composable(
            route = Screen.Nakshatra.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            NakshatraScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Shadbala Analysis screen
        composable(
            route = Screen.Shadbala.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            ShadbalaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Shodashvarga (16-divisional charts) screen
        composable(
            route = Screen.Shodashvarga.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            ShodashvargaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Yogini Dasha screen
        composable(
            route = Screen.YoginiDasha.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            YoginiDashaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Argala (Jaimini Intervention) screen
        composable(
            route = Screen.Argala.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            ArgalaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Chara Dasha (Jaimini Sign-based) screen
        composable(
            route = Screen.CharaDasha.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            CharaDashaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Bhrigu Bindu (Karmic Destiny Point) screen
        composable(
            route = Screen.BhriguBindu.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            BhriguBinduScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Predictions (Comprehensive Life Analysis) screen
        composable(
            route = Screen.Predictions.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            PredictionsScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Ashtottari Dasha screen
        composable(
            route = Screen.AshtottariDasha.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            AshtottariDashaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Sudarshana Chakra Dasha screen
        composable(
            route = Screen.SudarshanaChakra.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            SudarshanaChakraScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Mrityu Bhaga Analysis screen
        composable(
            route = Screen.MrityuBhaga.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            MrityuBhagaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Lal Kitab Remedies screen
        composable(
            route = Screen.LalKitabRemedies.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            LalKitabRemediesScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Divisional Charts Analysis screen
        composable(
            route = Screen.DivisionalCharts.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            DivisionalChartsScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Upachaya Transit screen
        composable(
            route = Screen.UpachayaTransit.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            UpachayaTransitScreen(
                chart = currentChart,
                transitPositions = currentChart?.planetPositions ?: emptyList(),
                onBack = { navController.popBackStack() }
            )
        }

        // Kalachakra Dasha screen
        composable(
            route = Screen.KalachakraDasha.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            KalachakraDashaScreen(
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // Tarabala & Chandrabala Analysis screen
        composable(
            route = Screen.Tarabala.route,
            arguments = listOf(navArgument("chartId") { type = NavType.LongType })
        ) { backStackEntry ->
            val chartId = backStackEntry.arguments?.getLong("chartId") ?: return@composable

            LaunchedEffect(chartId) {
                if (selectedChartId != chartId) {
                    viewModel.loadChart(chartId)
                }
            }

            TarabalaScreen(
                context = context,
                chart = currentChart,
                onBack = { navController.popBackStack() }
            )
        }

        // AI Models configuration screen
        composable(Screen.AiModels.route) {
            AiModelsScreen(
                providerRegistry = providerRegistry,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
