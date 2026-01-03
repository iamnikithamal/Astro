package com.astro.storm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringResources
import com.astro.storm.ephemeris.YoginiDashaCalculator
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicReference

sealed class YoginiDashaUiState {
    data object Loading : YoginiDashaUiState()
    data class Success(val result: YoginiDashaCalculator.YoginiDashaResult) : YoginiDashaUiState()
    data class Error(val message: String) : YoginiDashaUiState()
    data object Idle : YoginiDashaUiState()
}

class YoginiDashaViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<YoginiDashaUiState>(YoginiDashaUiState.Idle)
    val uiState: StateFlow<YoginiDashaUiState> = _uiState.asStateFlow()

    private var calculationJob: Job? = null

    private val cache = AtomicReference<CachedYoginiResult?>(null)

    private data class CachedYoginiResult(
        val chartKey: String,
        val result: YoginiDashaCalculator.YoginiDashaResult
    )

    fun loadYoginiDasha(chart: VedicChart?, language: Language = Language.ENGLISH) {
        if (chart == null) {
            _uiState.value = YoginiDashaUiState.Idle
            return
        }

        val chartKey = generateChartKey(chart, language)

        // Check cache first
        cache.get()?.let { cached ->
            if (cached.chartKey == chartKey) {
                _uiState.value = YoginiDashaUiState.Success(cached.result)
                return
            }
        }

        // Don't start a new calculation if one is already in progress
        val currentState = _uiState.value
        if (currentState is YoginiDashaUiState.Loading) {
            return
        }

        // Cancel any existing calculation and start a new one
        calculationJob?.cancel()
        _uiState.value = YoginiDashaUiState.Loading

        calculationJob = viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.Default) {
                    YoginiDashaCalculator.calculateYoginiDasha(
                        chart = chart,
                        numberOfCycles = 3, // Calculate 3 cycles = 108 years
                        language = language
                    )
                }

                cache.set(CachedYoginiResult(chartKey, result))
                _uiState.value = YoginiDashaUiState.Success(result)

            } catch (e: CancellationException) {
                // Don't update state on cancellation
                throw e
            } catch (e: Exception) {
                val errorMessage = when {
                    e.message?.contains("Moon", ignoreCase = true) == true ->
                        StringResources.get(StringKeyDosha.YOGINI_ERROR_MOON, language)
                    e.message?.contains("birth", ignoreCase = true) == true ->
                        StringResources.get(StringKeyDosha.YOGINI_ERROR_BIRTH, language)
                    e.message?.contains("nakshatra", ignoreCase = true) == true ->
                        StringResources.get(StringKeyDosha.YOGINI_ERROR_NAKSHATRA, language)
                    else ->
                        e.message ?: StringResources.get(StringKeyDosha.YOGINI_ERROR_GENERIC, language)
                }
                _uiState.value = YoginiDashaUiState.Error(errorMessage)
            }
        }
    }

    fun clearCache() {
        cache.set(null)
    }

    private fun generateChartKey(chart: VedicChart, language: Language): String {
        val birthData = chart.birthData
        return buildString {
            append(birthData.dateTime.toEpochSecond(java.time.ZoneOffset.UTC))
            append('|')
            append((birthData.latitude * 1_000_000).toLong())
            append('|')
            append((birthData.longitude * 1_000_000).toLong())
            append('|')
            append(chart.ayanamsaName)
            append("|yogini|")
            append(language.name)
        }
    }

    override fun onCleared() {
        super.onCleared()
        calculationJob?.cancel()
    }
}
