package com.astro.storm.util

import com.astro.storm.data.model.VedicChart
import java.time.ZoneOffset

/**
 * Utility functions for Vedic chart operations.
 *
 * This centralizes common chart-related operations to avoid code duplication
 * across ViewModels and ensures consistent behavior.
 */
object ChartUtils {

    /**
     * Generates a unique cache key for a VedicChart.
     *
     * The key incorporates:
     * - Birth timestamp (epoch seconds)
     * - Latitude (with 6 decimal places precision)
     * - Longitude (with 6 decimal places precision)
     * - Ayanamsa system used
     * - Timezone (for disambiguation)
     *
     * This key is used for caching chart calculations across ViewModels
     * (DashaViewModel, InsightsViewModel, etc.) to avoid redundant computations.
     *
     * @param chart The VedicChart to generate a key for
     * @return A unique string key representing this chart configuration
     */
    fun generateChartKey(chart: VedicChart): String {
        val birthData = chart.birthData
        return buildString {
            // Epoch timestamp provides unique time identification
            append(birthData.dateTime.toEpochSecond(ZoneOffset.UTC))
            append('|')
            // High precision lat/long for exact location (6 decimal places)
            append((birthData.latitude * 1_000_000).toLong())
            append('|')
            append((birthData.longitude * 1_000_000).toLong())
            append('|')
            // Ayanamsa affects all calculations significantly
            append(chart.ayanamsaName)
            append('|')
            // Timezone for any timezone-specific caching needs
            append(birthData.timezone)
        }
    }

    /**
     * Generates a shorter identifier for a VedicChart, suitable for logging or display.
     *
     * @param chart The VedicChart to generate an ID for
     * @return A human-readable short identifier
     */
    fun generateShortId(chart: VedicChart): String {
        val birthData = chart.birthData
        val name = birthData.name.take(8).replace(" ", "_")
        val dateStr = birthData.dateTime.toLocalDate().toString()
        return "${name}_$dateStr"
    }

    /**
     * Checks if two charts represent the same birth data configuration.
     * Useful for determining if cached data is still valid.
     *
     * @param chart1 First chart to compare
     * @param chart2 Second chart to compare
     * @return true if charts have identical birth configurations
     */
    fun chartsMatch(chart1: VedicChart?, chart2: VedicChart?): Boolean {
        if (chart1 == null || chart2 == null) return chart1 == chart2
        return generateChartKey(chart1) == generateChartKey(chart2)
    }
}
