package com.astro.storm.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.ui.unit.Density
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.astro.storm.data.local.ChartDatabase
import com.astro.storm.data.model.BirthData
import com.astro.storm.data.model.HouseSystem
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.repository.ChartRepository
import com.astro.storm.data.repository.SavedChart
import com.astro.storm.ephemeris.SwissEphemerisEngine
import com.astro.storm.ui.chart.ChartColorConfig
import com.astro.storm.ui.chart.ChartRenderer
import com.astro.storm.util.ChartExporter
import com.astro.storm.util.ExportUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import android.content.Context
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.util.Objects
import java.util.concurrent.Executors

/**
 * ViewModel for chart operations
 */
class ChartViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ChartRepository
    private val ephemerisEngine: SwissEphemerisEngine
    // Default chart renderer for light theme - for theme-aware rendering, use getChartRenderer(isDark)
    val chartRenderer = ChartRenderer(ChartColorConfig.Light)
    private val prefs = application.getSharedPreferences("chart_prefs", Context.MODE_PRIVATE)
    private val chartExporter: ChartExporter

    // Theme-aware chart renderer cache
    private var darkChartRenderer: ChartRenderer? = null
    private var lightChartRenderer: ChartRenderer? = null

    /**
     * Get a chart renderer configured for the current theme.
     * @param isDarkTheme Whether the app is in dark theme mode
     * @return ChartRenderer with appropriate color configuration
     */
    fun getChartRenderer(isDarkTheme: Boolean): ChartRenderer {
        return if (isDarkTheme) {
            darkChartRenderer ?: ChartRenderer(ChartColorConfig.Dark).also { darkChartRenderer = it }
        } else {
            lightChartRenderer ?: ChartRenderer(ChartColorConfig.Light).also { lightChartRenderer = it }
        }
    }

    // Single-threaded dispatcher for sequential state updates
    private val singleThreadContext = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    private val _uiState = MutableStateFlow<ChartUiState>(ChartUiState.Initial)
    val uiState: StateFlow<ChartUiState> = _uiState.asStateFlow()

    private val _savedCharts = MutableStateFlow<List<SavedChart>>(emptyList())
    val savedCharts: StateFlow<List<SavedChart>> = _savedCharts.asStateFlow()

    private val _selectedChartId = MutableStateFlow<Long?>(null)
    val selectedChartId: StateFlow<Long?> = _selectedChartId.asStateFlow()

    // Guard against duplicate saves of the same chart during a single calculation cycle
    private var lastSavedChartHash: Int? = null

    init {
        val database = ChartDatabase.getInstance(application)
        repository = ChartRepository(database.chartDao())
        ephemerisEngine = SwissEphemerisEngine.getInstance(application)
        chartExporter = ChartExporter(application)

        loadSavedCharts()
    }

    private fun loadSavedCharts() {
        viewModelScope.launch(singleThreadContext) {
            repository.getAllCharts().collect { charts ->
                _savedCharts.value = charts
                // Use compareAndSet pattern to avoid race condition
                val currentSelected = _selectedChartId.value
                if (currentSelected == null) {
                    val lastSelectedId = prefs.getLong("last_selected_chart_id", -1)
                    val targetChartId = when {
                        lastSelectedId != -1L && charts.any { it.id == lastSelectedId } -> lastSelectedId
                        charts.isNotEmpty() -> charts.first().id
                        else -> null
                    }
                    // Only load if still not selected (double-check pattern)
                    if (targetChartId != null && _selectedChartId.value == null) {
                        loadChart(targetChartId)
                    }
                }
            }
        }
    }

    /**
     * Calculate a new Vedic chart
     */
    fun calculateChart(
        birthData: BirthData,
        houseSystem: HouseSystem = HouseSystem.DEFAULT
    ) {
        viewModelScope.launch(singleThreadContext) {
            // Reset the save guard for new calculations
            lastSavedChartHash = null
            _uiState.value = ChartUiState.Calculating

            try {
                val chart = withContext(Dispatchers.Default) {
                    ephemerisEngine.calculateVedicChart(birthData, houseSystem)
                }
                _uiState.value = ChartUiState.Success(chart)
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    /**
     * Load a saved chart
     */
    fun loadChart(chartId: Long) {
        viewModelScope.launch(singleThreadContext) {
            _uiState.value = ChartUiState.Loading

            try {
                val chart = repository.getChartById(chartId)
                if (chart != null) {
                    _uiState.value = ChartUiState.Success(chart)
                    _selectedChartId.value = chartId
                    prefs.edit().putLong("last_selected_chart_id", chartId).apply()
                } else {
                    _uiState.value = ChartUiState.Error("Chart not found")
                }
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error(e.message ?: "Failed to load chart")
            }
        }
    }

    /**
     * Save current chart with duplicate prevention
     * Uses a hash of birth data to prevent saving the same chart multiple times
     * in rapid succession (e.g., due to recomposition or state changes)
     */
    fun saveChart(chart: VedicChart) {
        viewModelScope.launch(singleThreadContext) {
            try {
                // Generate a hash based on birth data to identify unique charts
                val chartHash = generateChartHash(chart)

                // Skip if this exact chart was just saved (prevents duplicates)
                if (lastSavedChartHash == chartHash) {
                    // Already saved this chart, just update state without saving again
                    _uiState.value = ChartUiState.Saved
                    return@launch
                }

                repository.saveChart(chart)
                lastSavedChartHash = chartHash
                _uiState.value = ChartUiState.Saved
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error("Failed to save chart: ${e.message}")
            }
        }
    }

    /**
     * Generate a hash to uniquely identify a chart by its birth data
     * This prevents saving duplicates of the exact same chart
     */
    private fun generateChartHash(chart: VedicChart): Int {
        return Objects.hash(
            chart.birthData.name,
            chart.birthData.dateTime.toString(),
            chart.birthData.latitude,
            chart.birthData.longitude,
            chart.birthData.timezone,
            chart.birthData.location
        )
    }

    /**
     * Delete a saved chart
     */
    fun deleteChart(chartId: Long) {
        viewModelScope.launch(singleThreadContext) {
            try {
                repository.deleteChart(chartId)
                if (_selectedChartId.value == chartId) {
                    prefs.edit().remove("last_selected_chart_id").apply()
                    _selectedChartId.value = null
                    _uiState.value = ChartUiState.Initial
                }
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error("Failed to delete chart: ${e.message}")
            }
        }
    }

    /**
     * Export chart as image
     */
    fun exportChartImage(chart: VedicChart, fileName: String, density: Density) {
        viewModelScope.launch(singleThreadContext) {
            try {
                val bitmap = withContext(Dispatchers.Default) {
                    chartRenderer.createChartBitmap(chart, 2048, 2048, density)
                }

                val result = ExportUtils.saveChartImage(getApplication(), bitmap, fileName)
                result.onSuccess {
                    _uiState.value = ChartUiState.Exported("Image saved successfully")
                }.onFailure {
                    _uiState.value = ChartUiState.Error("Failed to save image: ${it.message}")
                }
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error("Export failed: ${e.message}")
            }
        }
    }

    /**
     * Copy chart plaintext to clipboard
     */
    fun copyChartToClipboard(chart: VedicChart) {
        viewModelScope.launch(singleThreadContext) {
            try {
                // Heavy string formatting moved to a background thread
                val plaintext = withContext(Dispatchers.Default) {
                    ExportUtils.getChartPlaintext(chart)
                }
                ExportUtils.copyToClipboard(getApplication(), plaintext, "Vedic Chart Data")
                _uiState.value = ChartUiState.Exported("Chart data copied to clipboard")
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error("Failed to copy: ${e.message}")
            }
        }
    }

    /**
     * Export chart to PDF with comprehensive report
     */
    fun exportChartToPdf(
        chart: VedicChart,
        density: Density,
        options: ChartExporter.PdfExportOptions = ChartExporter.PdfExportOptions()
    ) {
        viewModelScope.launch(singleThreadContext) {
            try {
                _uiState.value = ChartUiState.Exporting("Generating PDF report...")
                val result = chartExporter.exportToPdf(chart, options, density)
                when (result) {
                    is ChartExporter.ExportResult.Success -> {
                        _uiState.value = ChartUiState.Exported("PDF saved successfully")
                    }
                    is ChartExporter.ExportResult.Error -> {
                        _uiState.value = ChartUiState.Error(result.message)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error("PDF export failed: ${e.message}")
            }
        }
    }

    /**
     * Export chart to JSON
     */
    fun exportChartToJson(chart: VedicChart) {
        viewModelScope.launch(singleThreadContext) {
            try {
                _uiState.value = ChartUiState.Exporting("Generating JSON...")
                val result = chartExporter.exportToJson(chart)
                when (result) {
                    is ChartExporter.ExportResult.Success -> {
                        _uiState.value = ChartUiState.Exported("JSON saved successfully")
                    }
                    is ChartExporter.ExportResult.Error -> {
                        _uiState.value = ChartUiState.Error(result.message)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error("JSON export failed: ${e.message}")
            }
        }
    }

    /**
     * Export chart to CSV
     */
    fun exportChartToCsv(chart: VedicChart) {
        viewModelScope.launch(singleThreadContext) {
            try {
                _uiState.value = ChartUiState.Exporting("Generating CSV...")
                val result = chartExporter.exportToCsv(chart)
                when (result) {
                    is ChartExporter.ExportResult.Success -> {
                        _uiState.value = ChartUiState.Exported("CSV saved successfully")
                    }
                    is ChartExporter.ExportResult.Error -> {
                        _uiState.value = ChartUiState.Error(result.message)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error("CSV export failed: ${e.message}")
            }
        }
    }

    /**
     * Export chart as high-quality image with options
     */
    fun exportChartToImage(
        chart: VedicChart,
        density: Density,
        options: ChartExporter.ImageExportOptions = ChartExporter.ImageExportOptions()
    ) {
        viewModelScope.launch(singleThreadContext) {
            try {
                _uiState.value = ChartUiState.Exporting("Generating image...")
                val result = chartExporter.exportToImage(chart, options, density)
                when (result) {
                    is ChartExporter.ExportResult.Success -> {
                        _uiState.value = ChartUiState.Exported("Image saved successfully")
                    }
                    is ChartExporter.ExportResult.Error -> {
                        _uiState.value = ChartUiState.Error(result.message)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error("Image export failed: ${e.message}")
            }
        }
    }

    /**
     * Export chart as plain text report
     */
    fun exportChartToText(chart: VedicChart) {
        viewModelScope.launch(singleThreadContext) {
            try {
                _uiState.value = ChartUiState.Exporting("Generating text report...")
                val result = chartExporter.exportToText(chart)
                when (result) {
                    is ChartExporter.ExportResult.Success -> {
                        _uiState.value = ChartUiState.Exported("Text report saved successfully")
                    }
                    is ChartExporter.ExportResult.Error -> {
                        _uiState.value = ChartUiState.Error(result.message)
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ChartUiState.Error("Text export failed: ${e.message}")
            }
        }
    }

    /**
     * Get a chart by ID directly (for matchmaking and other features)
     */
    suspend fun getChartById(chartId: Long): VedicChart? {
        return repository.getChartById(chartId)
    }

    /**
     * Reset UI state to previous chart state if available, otherwise Initial
     * This is used after export operations to restore the normal UI state
     */
    fun resetState() {
        // If we had a chart loaded, restore to Success state
        val currentState = _uiState.value
        when (currentState) {
            is ChartUiState.Exported, is ChartUiState.Error, is ChartUiState.Exporting -> {
                // Try to reload the current chart if one was selected
                _selectedChartId.value?.let { chartId ->
                    loadChart(chartId)
                } ?: run {
                    _uiState.value = ChartUiState.Initial
                }
            }
            else -> {
                // Keep current state for Success, Calculating, Loading, etc.
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ephemerisEngine.close()
        singleThreadContext.close()
    }
}

/**
 * UI states for chart operations
 */
sealed class ChartUiState {
    object Initial : ChartUiState()
    object Loading : ChartUiState()
    object Calculating : ChartUiState()
    data class Success(val chart: VedicChart) : ChartUiState()
    data class Error(val message: String) : ChartUiState()
    object Saved : ChartUiState()
    data class Exporting(val message: String) : ChartUiState()
    data class Exported(val message: String) : ChartUiState()
}
