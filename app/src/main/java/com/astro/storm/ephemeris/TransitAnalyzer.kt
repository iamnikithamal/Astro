package com.astro.storm.ephemeris

import android.content.Context
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.data.model.BirthData
import com.astro.storm.data.model.HouseSystem
import com.astro.storm.data.localization.StringKeyHoroscope
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.localization.StringKeyTransit
import com.astro.storm.data.localization.StringKeyInterface
import com.astro.storm.data.localization.getLocalizedName
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.LocalizationManager

/**
 * Comprehensive Transit Analysis System
 *
 * This class provides complete transit analysis including:
 * 1. Current planetary positions (real-time)
 * 2. Transit-to-natal aspect calculations
 * 3. Gochara (transit) analysis with Vedha points
 * 4. Ashtakavarga-based transit scoring
 * 5. Transit timeline for significant periods
 *
 * Gochara Rules (Transit from Moon Sign):
 * Based on classical Vedic texts including Phaladeepika and Brihat Samhita
 *
 * @author AstroStorm - Ultra-Precision Vedic Astrology
 */
class TransitAnalyzer(private val context: Context) {

    private val ephemerisEngine = SwissEphemerisEngine.getInstance(context)
    private val localizationManager = LocalizationManager.getInstance(context)

    private fun getString(key: StringKeyInterface): String = localizationManager.getString(key)
    private fun getString(key: StringKeyInterface, vararg args: Any): String = localizationManager.getString(key, *args)

    companion object {
        /**
         * Vedha (Obstruction) Points
         * When a planet transiting a favorable house has another planet in its Vedha point,
         * the favorable effects are obstructed/nullified.
         *
         * Format: Map<FavorableHouse, VedhaHouse>
         * Note: Some combinations have mutual Vedha
         */
        private val SUN_VEDHA = mapOf(
            3 to 9, 9 to 3,  // Sun in 3rd blocked by planet in 9th and vice versa
            6 to 12, 12 to 6,
            10 to 4, 4 to 10,
            11 to 5, 5 to 11
        )

        private val MOON_VEDHA = mapOf(
            1 to 5, 5 to 1,
            3 to 9, 9 to 3,
            6 to 12, 12 to 6,
            7 to 2, 2 to 7,
            10 to 4, 4 to 10,
            11 to 8, 8 to 11
        )

        private val MARS_VEDHA = mapOf(
            3 to 12, 12 to 3,
            6 to 9, 9 to 6,
            11 to 5, 5 to 11
        )

        private val MERCURY_VEDHA = mapOf(
            2 to 5, 5 to 2,
            4 to 3, 3 to 4,
            6 to 9, 9 to 6,
            8 to 1, 1 to 8,
            10 to 8, // 10 blocked by 8
            11 to 12, 12 to 11
        )

        private val JUPITER_VEDHA = mapOf(
            2 to 12, 12 to 2,
            5 to 4, 4 to 5,
            7 to 3, 3 to 7,
            9 to 10, 10 to 9,
            11 to 8, 8 to 11
        )

        private val VENUS_VEDHA = mapOf(
            1 to 8, 8 to 1,
            2 to 7, 7 to 2,
            3 to 1, 1 to 3,
            4 to 10, 10 to 4,
            5 to 9, 9 to 5,
            8 to 5, 5 to 8,
            9 to 11, 11 to 9,
            11 to 6, 6 to 11,
            12 to 3, 3 to 12
        )

        private val SATURN_VEDHA = mapOf(
            3 to 12, 12 to 3,
            6 to 9, 9 to 6,
            11 to 5, 5 to 11
        )

        /**
         * Favorable transit houses from Moon (without Vedha consideration)
         * Based on classical Gochara rules
         */
        private val FAVORABLE_TRANSITS = mapOf(
            Planet.SUN to listOf(3, 6, 10, 11),
            Planet.MOON to listOf(1, 3, 6, 7, 10, 11),
            Planet.MARS to listOf(3, 6, 11),
            Planet.MERCURY to listOf(2, 4, 6, 8, 10, 11),
            Planet.JUPITER to listOf(2, 5, 7, 9, 11),
            Planet.VENUS to listOf(1, 2, 3, 4, 5, 8, 9, 11, 12),
            Planet.SATURN to listOf(3, 6, 11),
            Planet.RAHU to listOf(3, 6, 10, 11),
            Planet.KETU to listOf(3, 6, 10, 11)
        )

        /**
         * Gochara interpretation keys mapping
         */
        private val GOCHARA_FAVORABLE_KEYS = mapOf(
            Planet.SUN to mapOf(3 to StringKeyHoroscope.GOCHARA_SUN_3, 6 to StringKeyHoroscope.GOCHARA_SUN_6, 10 to StringKeyHoroscope.GOCHARA_SUN_10, 11 to StringKeyHoroscope.GOCHARA_SUN_11),
            Planet.MOON to mapOf(1 to StringKeyHoroscope.GOCHARA_MOON_1, 3 to StringKeyHoroscope.GOCHARA_MOON_3, 6 to StringKeyHoroscope.GOCHARA_MOON_6, 7 to StringKeyHoroscope.GOCHARA_MOON_7, 10 to StringKeyHoroscope.GOCHARA_MOON_10, 11 to StringKeyHoroscope.GOCHARA_MOON_11),
            Planet.MARS to mapOf(3 to StringKeyHoroscope.GOCHARA_MARS_3, 6 to StringKeyHoroscope.GOCHARA_MARS_6, 11 to StringKeyHoroscope.GOCHARA_MARS_11),
            Planet.MERCURY to mapOf(2 to StringKeyHoroscope.GOCHARA_MERCURY_2, 4 to StringKeyHoroscope.GOCHARA_MERCURY_4, 6 to StringKeyHoroscope.GOCHARA_MERCURY_6, 8 to StringKeyHoroscope.GOCHARA_MERCURY_8, 10 to StringKeyHoroscope.GOCHARA_MERCURY_10, 11 to StringKeyHoroscope.GOCHARA_MERCURY_11),
            Planet.JUPITER to mapOf(2 to StringKeyHoroscope.GOCHARA_JUPITER_2, 5 to StringKeyHoroscope.GOCHARA_JUPITER_5, 7 to StringKeyHoroscope.GOCHARA_JUPITER_7, 9 to StringKeyHoroscope.GOCHARA_JUPITER_9, 11 to StringKeyHoroscope.GOCHARA_JUPITER_11),
            Planet.VENUS to mapOf(1 to StringKeyHoroscope.GOCHARA_VENUS_1, 2 to StringKeyHoroscope.GOCHARA_VENUS_2, 3 to StringKeyHoroscope.GOCHARA_VENUS_3, 4 to StringKeyHoroscope.GOCHARA_VENUS_4, 5 to StringKeyHoroscope.GOCHARA_VENUS_5, 8 to StringKeyHoroscope.GOCHARA_VENUS_8, 9 to StringKeyHoroscope.GOCHARA_VENUS_9, 11 to StringKeyHoroscope.GOCHARA_VENUS_11, 12 to StringKeyHoroscope.GOCHARA_VENUS_12),
            Planet.SATURN to mapOf(3 to StringKeyHoroscope.GOCHARA_SATURN_3, 6 to StringKeyHoroscope.GOCHARA_SATURN_6, 11 to StringKeyHoroscope.GOCHARA_SATURN_11),
            Planet.RAHU to mapOf(3 to StringKeyHoroscope.GOCHARA_RAHU_3, 6 to StringKeyHoroscope.GOCHARA_RAHU_6, 10 to StringKeyHoroscope.GOCHARA_RAHU_10, 11 to StringKeyHoroscope.GOCHARA_RAHU_11),
            Planet.KETU to mapOf(3 to StringKeyHoroscope.GOCHARA_KETU_3, 6 to StringKeyHoroscope.GOCHARA_KETU_6, 9 to StringKeyHoroscope.GOCHARA_KETU_9, 11 to StringKeyHoroscope.GOCHARA_KETU_11)
        )

        private val GOCHARA_UNFAVORABLE_KEYS = mapOf(
            Planet.SUN to mapOf(1 to StringKeyHoroscope.GOCHARA_SUN_1, 2 to StringKeyHoroscope.GOCHARA_SUN_2, 4 to StringKeyHoroscope.GOCHARA_SUN_4, 5 to StringKeyHoroscope.GOCHARA_SUN_5, 7 to StringKeyHoroscope.GOCHARA_SUN_7, 8 to StringKeyHoroscope.GOCHARA_SUN_8, 9 to StringKeyHoroscope.GOCHARA_SUN_9, 12 to StringKeyHoroscope.GOCHARA_SUN_12),
            Planet.MOON to mapOf(2 to StringKeyHoroscope.GOCHARA_MOON_2, 4 to StringKeyHoroscope.GOCHARA_MOON_4, 5 to StringKeyHoroscope.GOCHARA_MOON_5, 8 to StringKeyHoroscope.GOCHARA_MOON_8, 9 to StringKeyHoroscope.GOCHARA_MOON_9, 12 to StringKeyHoroscope.GOCHARA_MOON_12),
            Planet.MARS to mapOf(1 to StringKeyHoroscope.GOCHARA_MARS_1, 2 to StringKeyHoroscope.GOCHARA_MARS_2, 4 to StringKeyHoroscope.GOCHARA_MARS_4, 5 to StringKeyHoroscope.GOCHARA_MARS_5, 7 to StringKeyHoroscope.GOCHARA_MARS_7, 8 to StringKeyHoroscope.GOCHARA_MARS_8, 9 to StringKeyHoroscope.GOCHARA_MARS_9, 10 to StringKeyHoroscope.GOCHARA_MARS_10, 12 to StringKeyHoroscope.GOCHARA_MARS_12),
            Planet.MERCURY to mapOf(1 to StringKeyHoroscope.GOCHARA_MERCURY_1, 3 to StringKeyHoroscope.GOCHARA_MERCURY_3, 5 to StringKeyHoroscope.GOCHARA_MERCURY_5, 7 to StringKeyHoroscope.GOCHARA_MERCURY_7, 9 to StringKeyHoroscope.GOCHARA_MERCURY_9, 12 to StringKeyHoroscope.GOCHARA_MERCURY_12),
            Planet.JUPITER to mapOf(1 to StringKeyHoroscope.GOCHARA_JUPITER_1, 3 to StringKeyHoroscope.GOCHARA_JUPITER_3, 4 to StringKeyHoroscope.GOCHARA_JUPITER_4, 6 to StringKeyHoroscope.GOCHARA_JUPITER_6, 8 to StringKeyHoroscope.GOCHARA_JUPITER_8, 10 to StringKeyHoroscope.GOCHARA_JUPITER_10, 12 to StringKeyHoroscope.GOCHARA_JUPITER_12),
            Planet.SATURN to mapOf(1 to StringKeyHoroscope.GOCHARA_SATURN_1, 2 to StringKeyHoroscope.GOCHARA_SATURN_2, 4 to StringKeyHoroscope.GOCHARA_SATURN_4, 5 to StringKeyHoroscope.GOCHARA_SATURN_5, 7 to StringKeyHoroscope.GOCHARA_SATURN_7, 8 to StringKeyHoroscope.GOCHARA_SATURN_8, 9 to StringKeyHoroscope.GOCHARA_SATURN_9, 10 to StringKeyHoroscope.GOCHARA_SATURN_10, 12 to StringKeyHoroscope.GOCHARA_SATURN_12),
            Planet.RAHU to mapOf(1 to StringKeyHoroscope.GOCHARA_RAHU_1, 2 to StringKeyHoroscope.GOCHARA_RAHU_2, 4 to StringKeyHoroscope.GOCHARA_RAHU_4, 5 to StringKeyHoroscope.GOCHARA_RAHU_5, 7 to StringKeyHoroscope.GOCHARA_RAHU_7, 8 to StringKeyHoroscope.GOCHARA_RAHU_8, 9 to StringKeyHoroscope.GOCHARA_RAHU_9, 12 to StringKeyHoroscope.GOCHARA_RAHU_12),
            Planet.KETU to mapOf(1 to StringKeyHoroscope.GOCHARA_KETU_1, 2 to StringKeyHoroscope.GOCHARA_KETU_2, 4 to StringKeyHoroscope.GOCHARA_KETU_4, 5 to StringKeyHoroscope.GOCHARA_KETU_5, 7 to StringKeyHoroscope.GOCHARA_KETU_7, 8 to StringKeyHoroscope.GOCHARA_KETU_8, 10 to StringKeyHoroscope.GOCHARA_KETU_10, 12 to StringKeyHoroscope.GOCHARA_KETU_12)
        )

        /**
         * Neutral transit houses from Moon
         */
        private val NEUTRAL_TRANSITS = mapOf(
            Planet.SUN to listOf(1, 2, 5),
            Planet.MOON to listOf(2, 5),
            Planet.MARS to listOf(1, 10),
            Planet.MERCURY to listOf(1, 3, 5),
            Planet.JUPITER to listOf(1, 4, 6, 8, 10),
            Planet.VENUS to listOf(6, 7, 10),
            Planet.SATURN to listOf(1, 2, 10),
            Planet.RAHU to listOf(1, 2, 5),
            Planet.KETU to listOf(1, 2, 5)
        )

        /**
         * Difficult transit houses from Moon
         */
        private val DIFFICULT_TRANSITS = mapOf(
            Planet.SUN to listOf(4, 7, 8, 9, 12),
            Planet.MOON to listOf(4, 8, 9, 12),
            Planet.MARS to listOf(2, 4, 5, 7, 8, 9, 12),
            Planet.MERCURY to listOf(7, 9, 12),
            Planet.JUPITER to listOf(3, 12),
            Planet.VENUS to listOf(6, 7, 10),
            Planet.SATURN to listOf(4, 5, 7, 8, 9, 12),
            Planet.RAHU to listOf(4, 7, 8, 9, 12),
            Planet.KETU to listOf(4, 7, 8, 9, 12)
        )

        /**
         * Aspect angles and their names
         */
        private val ASPECT_ANGLES = mapOf(
            0.0 to StringKeyTransit.ASPECT_CONJUNCTION,
            60.0 to StringKeyTransit.ASPECT_SEXTILE,
            90.0 to StringKeyTransit.ASPECT_SQUARE,
            120.0 to StringKeyTransit.ASPECT_TRINE,
            180.0 to StringKeyTransit.ASPECT_OPPOSITION
        )

        /**
         * Orb allowances for transit aspects
         */
        private val TRANSIT_ORBS = mapOf(
            Planet.SUN to 8.0,
            Planet.MOON to 8.0,
            Planet.MERCURY to 6.0,
            Planet.VENUS to 6.0,
            Planet.MARS to 6.0,
            Planet.JUPITER to 8.0,
            Planet.SATURN to 8.0,
            Planet.RAHU to 5.0,
            Planet.KETU to 5.0
        )
    }

    /**
     * Complete transit analysis result
     */
    data class TransitAnalysis(
        val natalChart: VedicChart,
        val transitDateTime: LocalDateTime,
        val transitPositions: List<PlanetPosition>,
        val gocharaResults: List<GocharaResult>,
        val transitAspects: List<TransitAspect>,
        val ashtakavargaScores: Map<Planet, AshtakavargaCalculator.TransitScore>,
        val overallAssessment: OverallTransitAssessment,
        val significantPeriods: List<SignificantPeriod>,
        val language: Language
    ) {
        fun toPlainText(): String = buildString {
            val title = StringResources.get(StringKeyTransit.REPORT_TITLE_LINE1, language)
            val line = "═".repeat(title.length + 10)
            
            appendLine(line)
            appendLine("     $title     ")
            appendLine(line)
            appendLine()
            appendLine(StringResources.get(StringKeyTransit.REPORT_DATE_LABEL, language, transitDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))))
            appendLine()

            appendLine(StringResources.get(StringKeyTransit.REPORT_SECTION_POSITIONS, language))
            appendLine("─────────────────────────────────────────────────────────")
            transitPositions.forEach { pos ->
                val retro = if (pos.isRetrograde) " (R)" else ""
                appendLine("${pos.planet.getLocalizedName(language).padEnd(10)}: ${pos.sign.getLocalizedName(language).padEnd(12)} ${formatDegree(pos.longitude)}$retro")
            }
            appendLine()

            appendLine(StringResources.get(StringKeyTransit.REPORT_SECTION_GOCHARA, language))
            appendLine("─────────────────────────────────────────────────────────")
            gocharaResults.forEach { result ->
                val vedhaStr = if (result.isVedhaAffected) " [VEDHA]" else ""
                val effectName = StringResources.get(result.effect.key, language)
                appendLine("${result.planet.getLocalizedName(language).padEnd(10)}: House ${result.houseFromMoon.toString().padStart(2)} - $effectName$vedhaStr")
                if (result.isVedhaAffected && result.vedhaSource != null) {
                    appendLine(StringResources.get(StringKeyTransit.REPORT_VEDHA_FROM, language, result.vedhaSource.getLocalizedName(language)))
                }
            }
            appendLine()

            appendLine(StringResources.get(StringKeyTransit.REPORT_SECTION_ASPECTS, language))
            appendLine("─────────────────────────────────────────────────────────")
            if (transitAspects.isEmpty()) {
                appendLine(StringResources.get(StringKeyTransit.REPORT_NO_ASPECTS, language))
            } else {
                transitAspects.sortedByDescending { it.strength }.take(10).forEach { aspect ->
                    val applying = if (aspect.isApplying) StringResources.get(StringKeyTransit.ASPECT_APPLYING, language) else StringResources.get(StringKeyTransit.ASPECT_SEPARATING, language)
                    val aspectName = StringResources.get(aspect.aspectKey, language)
                    appendLine(StringResources.get(StringKeyTransit.REPORT_ASPECT_LINE, language, aspect.transitingPlanet.getLocalizedName(language), aspectName, aspect.natalPlanet.getLocalizedName(language)))
                    appendLine(StringResources.get(StringKeyTransit.REPORT_ASPECT_DETAILS, language, String.format("%.2f", aspect.orb), applying, String.format("%.0f", aspect.strength * 100)))
                }
            }
            appendLine()

            appendLine(StringResources.get(StringKeyTransit.REPORT_SECTION_ASHTAKAVARGA, language))
            appendLine("─────────────────────────────────────────────────────────")
            ashtakavargaScores.forEach { (planet, score) ->
                appendLine(StringResources.get(StringKeyTransit.REPORT_BAV_SAV, language, planet.getLocalizedName(language), score.binduScore, score.savScore, score.interpretation))
            }
            appendLine()

            appendLine(StringResources.get(StringKeyTransit.REPORT_SECTION_ASSESSMENT, language))
            appendLine("─────────────────────────────────────────────────────────")
            appendLine(StringResources.get(StringKeyTransit.REPORT_PERIOD_QUALITY, language, StringResources.get(overallAssessment.quality.key, language)))
            appendLine(StringResources.get(StringKeyTransit.REPORT_SCORE, language, String.format("%.1f", overallAssessment.score)))
            appendLine()
            appendLine(StringResources.get(StringKeyTransit.REPORT_SUMMARY, language, overallAssessment.summary))
            appendLine()
            appendLine(StringResources.get(StringKeyTransit.REPORT_FOCUS_AREAS, language))
            overallAssessment.focusAreas.forEachIndexed { index, area ->
                appendLine("${index + 1}. $area")
            }
        }

        private fun formatDegree(longitude: Double): String {
            val degInSign = longitude % 30.0
            val deg = degInSign.toInt()
            val min = ((degInSign - deg) * 60).toInt()
            return "${deg}° ${min}'"
        }
    }

    /**
     * Gochara (transit) result for a single planet
     */
    data class GocharaResult(
        val planet: Planet,
        val transitSign: ZodiacSign,
        val houseFromMoon: Int,
        val effect: TransitEffect,
        val isVedhaAffected: Boolean,
        val vedhaSource: Planet?,
        val interpretation: String
    )

    /**
     * Transit aspect to natal planet
     */
    data class TransitAspect(
        val transitingPlanet: Planet,
        val natalPlanet: Planet,
        val aspectType: String, // Kept for legacy compatibility if needed
        val aspectKey: StringKeyTransit, // Localized key
        val exactAngle: Double,
        val orb: Double,
        val isApplying: Boolean,
        val strength: Double,
        val interpretation: String
    )

    /**
     * Transit effect enumeration
     */
    enum class TransitEffect(val key: StringKeyTransit, val score: Int) {
        EXCELLENT(StringKeyTransit.EFFECT_EXCELLENT, 5),
        GOOD(StringKeyTransit.EFFECT_GOOD, 4),
        NEUTRAL(StringKeyTransit.EFFECT_NEUTRAL, 3),
        CHALLENGING(StringKeyTransit.EFFECT_CHALLENGING, 2),
        DIFFICULT(StringKeyTransit.EFFECT_DIFFICULT, 1);
        
        // Helper to get displayName in code that hasn't been updated to use keys directly
        val displayName: String get() = StringResources.get(key, Language.ENGLISH)
    }

    /**
     * Overall transit assessment
     */
    data class OverallTransitAssessment(
        val quality: TransitQuality,
        val score: Double,
        val summary: String,
        val focusAreas: List<String>
    )

    /**
     * Transit quality
     */
    enum class TransitQuality(val key: StringKeyTransit) {
        EXCELLENT(StringKeyTransit.QUALITY_EXCELLENT),
        GOOD(StringKeyTransit.QUALITY_GOOD),
        MIXED(StringKeyTransit.QUALITY_MIXED),
        CHALLENGING(StringKeyTransit.QUALITY_CHALLENGING),
        DIFFICULT(StringKeyTransit.QUALITY_DIFFICULT)
    }

    /**
     * Significant period in transit timeline
     */
    data class SignificantPeriod(
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
        val description: String,
        val planets: List<Planet>,
        val intensity: Int // 1-5
    )

    /**
     * Calculate current transit positions
     */
    fun getCurrentTransitPositions(
        timezone: String = "UTC"
    ): List<PlanetPosition> {
        val now = LocalDateTime.now(ZoneId.of(timezone))
        return getTransitPositionsForDateTime(now, timezone)
    }

    /**
     * Get transit positions for a specific date/time
     */
    fun getTransitPositionsForDateTime(
        dateTime: LocalDateTime,
        timezone: String = "UTC"
    ): List<PlanetPosition> {
        val transitBirthData = BirthData(
            name = "Transit",
            dateTime = dateTime,
            latitude = 0.0,
            longitude = 0.0,
            timezone = timezone,
            location = "Transit Chart"
        )

        val transitChart = ephemerisEngine.calculateVedicChart(transitBirthData, HouseSystem.DEFAULT)
        return transitChart.planetPositions
    }

    /**
     * Perform complete transit analysis
     */
    fun analyzeTransits(
        natalChart: VedicChart,
        transitDateTime: LocalDateTime = LocalDateTime.now(),
        language: Language = Language.ENGLISH
    ): TransitAnalysis {
        // Get transit positions
        val transitPositions = getTransitPositionsForDateTime(
            transitDateTime,
            natalChart.birthData.timezone
        )

        // Get natal Moon position for Gochara
        val natalMoon = natalChart.planetPositions.find { it.planet == Planet.MOON }
            ?: throw IllegalStateException("Natal Moon position not found")

        // Calculate Gochara results
        val gocharaResults = calculateGochara(natalMoon, transitPositions, language)

        // Calculate transit aspects
        val transitAspects = calculateTransitAspects(natalChart, transitPositions, language)

        // Calculate Ashtakavarga scores
        val ashtakavargaAnalysis = AshtakavargaCalculator.calculateAshtakavarga(natalChart)
        val ashtakavargaScores = mutableMapOf<Planet, AshtakavargaCalculator.TransitScore>()

        transitPositions.filter { it.planet in Planet.MAIN_PLANETS && it.planet != Planet.RAHU && it.planet != Planet.KETU }
            .forEach { transitPos ->
                ashtakavargaScores[transitPos.planet] = ashtakavargaAnalysis.getTransitScore(
                    transitPos.planet,
                    transitPos.sign
                )
            }

        // Calculate overall assessment
        val overallAssessment = calculateOverallAssessment(gocharaResults, transitAspects, ashtakavargaScores, language)

        // Find significant periods
        val significantPeriods = findSignificantPeriods(natalChart, transitDateTime, language)

        return TransitAnalysis(
            natalChart = natalChart,
            transitDateTime = transitDateTime,
            transitPositions = transitPositions,
            gocharaResults = gocharaResults,
            transitAspects = transitAspects,
            ashtakavargaScores = ashtakavargaScores,
            overallAssessment = overallAssessment,
            significantPeriods = significantPeriods,
            language = language
        )
    }

    /**
     * Calculate Gochara (transit from Moon) for all planets
     */
    private fun calculateGochara(
        natalMoon: PlanetPosition,
        transitPositions: List<PlanetPosition>,
        language: Language
    ): List<GocharaResult> {
        val results = mutableListOf<GocharaResult>()
        val natalMoonSign = natalMoon.sign

        transitPositions.forEach { transitPos ->
            val planet = transitPos.planet
            if (planet !in Planet.MAIN_PLANETS) return@forEach

            // Calculate house from Moon
            val houseFromMoon = calculateHouseFromSign(transitPos.sign, natalMoonSign)

            // Determine effect based on classical rules
            val baseEffect = when (planet) {
                in FAVORABLE_TRANSITS.keys -> {
                    when (houseFromMoon) {
                        in (FAVORABLE_TRANSITS[planet] ?: emptyList()) -> TransitEffect.GOOD
                        in (NEUTRAL_TRANSITS[planet] ?: emptyList()) -> TransitEffect.NEUTRAL
                        else -> TransitEffect.CHALLENGING
                    }
                }
                else -> TransitEffect.NEUTRAL
            }

            // Check for Vedha
            val (isVedhaAffected, vedhaSource) = checkVedha(planet, houseFromMoon, transitPositions, natalMoonSign)

            // Adjust effect if Vedha is present
            val finalEffect = if (isVedhaAffected && baseEffect == TransitEffect.GOOD) {
                TransitEffect.NEUTRAL
            } else {
                baseEffect
            }

            val interpretation = generateGocharaInterpretation(planet, houseFromMoon, finalEffect, isVedhaAffected, language)

            results.add(
                GocharaResult(
                    planet = planet,
                    transitSign = transitPos.sign,
                    houseFromMoon = houseFromMoon,
                    effect = finalEffect,
                    isVedhaAffected = isVedhaAffected,
                    vedhaSource = vedhaSource,
                    interpretation = interpretation
                )
            )
        }

        return results
    }

    /**
     * Check if a transit has Vedha (obstruction)
     */
    private fun checkVedha(
        planet: Planet,
        houseFromMoon: Int,
        transitPositions: List<PlanetPosition>,
        natalMoonSign: ZodiacSign
    ): Pair<Boolean, Planet?> {
        val vedhaMap = when (planet) {
            Planet.SUN -> SUN_VEDHA
            Planet.MOON -> MOON_VEDHA
            Planet.MARS -> MARS_VEDHA
            Planet.MERCURY -> MERCURY_VEDHA
            Planet.JUPITER -> JUPITER_VEDHA
            Planet.VENUS -> VENUS_VEDHA
            Planet.SATURN -> SATURN_VEDHA
            else -> emptyMap()
        }

        val vedhaHouse = vedhaMap[houseFromMoon] ?: return Pair(false, null)

        // Check if any planet is in the Vedha house
        transitPositions.forEach { otherTransit ->
            if (otherTransit.planet != planet && otherTransit.planet in Planet.MAIN_PLANETS) {
                val otherHouseFromMoon = calculateHouseFromSign(otherTransit.sign, natalMoonSign)
                if (otherHouseFromMoon == vedhaHouse) {
                    return Pair(true, otherTransit.planet)
                }
            }
        }

        return Pair(false, null)
    }

    /**
     * Calculate transit aspects to natal planets
     */
    private fun calculateTransitAspects(
        natalChart: VedicChart,
        transitPositions: List<PlanetPosition>,
        language: Language
    ): List<TransitAspect> {
        val aspects = mutableListOf<TransitAspect>()

        transitPositions.forEach { transitPos ->
            natalChart.planetPositions.forEach { natalPos ->
                // Calculate angular separation
                val angularSeparation = calculateAngularSeparation(
                    transitPos.longitude,
                    natalPos.longitude
                )

                // Check each aspect angle
                ASPECT_ANGLES.forEach { (aspectAngle, aspectKey) ->
                    val orb = calculateOrb(angularSeparation, aspectAngle)
                    val maxOrb = TRANSIT_ORBS[transitPos.planet] ?: 6.0

                    if (orb <= maxOrb) {
                        val strength = 1.0 - (orb / maxOrb)
                        val isApplying = isAspectApplying(transitPos, natalPos, aspectAngle)
                        val aspectName = StringResources.get(aspectKey, language)

                        val interpretation = generateAspectInterpretation(
                            transitPos.planet,
                            natalPos.planet,
                            aspectKey,
                            isApplying,
                            language
                        )

                        aspects.add(
                            TransitAspect(
                                transitingPlanet = transitPos.planet,
                                natalPlanet = natalPos.planet,
                                aspectType = aspectName,
                                aspectKey = aspectKey,
                                exactAngle = angularSeparation,
                                orb = orb,
                                isApplying = isApplying,
                                strength = strength,
                                interpretation = interpretation
                            )
                        )
                    }
                }
            }
        }

        return aspects.sortedByDescending { it.strength }
    }

    /**
     * Calculate overall transit assessment
     */
    private fun calculateOverallAssessment(
        gocharaResults: List<GocharaResult>,
        transitAspects: List<TransitAspect>,
        ashtakavargaScores: Map<Planet, AshtakavargaCalculator.TransitScore>,
        language: Language
    ): OverallTransitAssessment {
        // Score from Gochara (handle empty list case to avoid NaN)
        val gocharaScore = if (gocharaResults.isNotEmpty()) {
            gocharaResults.map { it.effect.score }.average() * 20
        } else 50.0

        // Score from strong aspects
        val aspectScore = if (transitAspects.isNotEmpty()) {
            val beneficAspects = transitAspects.count { aspect ->
                val type = StringResources.get(aspect.aspectKey, Language.ENGLISH) // Check conceptual type
                (type in listOf("Trine", "Sextile") ||
                        (type == "Conjunction" && aspect.transitingPlanet in listOf(Planet.JUPITER, Planet.VENUS)))
            }
            val maleficAspects = transitAspects.count { aspect ->
                val type = StringResources.get(aspect.aspectKey, Language.ENGLISH)
                type in listOf("Square", "Opposition") &&
                        aspect.transitingPlanet in listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU)
            }
            ((beneficAspects * 10) - (maleficAspects * 5) + 50).coerceIn(0, 100).toDouble()
        } else 50.0

        // Score from Ashtakavarga
        val ashtakavargaScore = if (ashtakavargaScores.isNotEmpty()) {
            ashtakavargaScores.values.map { it.overallRating }.average() * 100
        } else 50.0

        // Combined score
        val combinedScore = (gocharaScore * 0.4 + aspectScore * 0.3 + ashtakavargaScore * 0.3)

        val quality = when {
            combinedScore >= 75 -> TransitQuality.EXCELLENT
            combinedScore >= 60 -> TransitQuality.GOOD
            combinedScore >= 45 -> TransitQuality.MIXED
            combinedScore >= 30 -> TransitQuality.CHALLENGING
            else -> TransitQuality.DIFFICULT
        }

        val summary = generateOverallSummary(quality, gocharaResults, transitAspects, language)
        val focusAreas = generateFocusAreas(gocharaResults, transitAspects, language)

        return OverallTransitAssessment(
            quality = quality,
            score = combinedScore,
            summary = summary,
            focusAreas = focusAreas
        )
    }

    /**
     * Find significant transit periods in the next 30 days
     */
    private fun findSignificantPeriods(
        natalChart: VedicChart,
        startDate: LocalDateTime,
        language: Language
    ): List<SignificantPeriod> {
        val periods = mutableListOf<SignificantPeriod>()

        // Check for significant Saturn, Jupiter, and Rahu/Ketu transits
        val significantPlanets = listOf(Planet.SATURN, Planet.JUPITER, Planet.RAHU, Planet.KETU)

        val natalMoon = natalChart.planetPositions.find { it.planet == Planet.MOON } ?: return periods

        // Analyze transit for next 30 days (sample every 7 days)
        for (dayOffset in listOf(0, 7, 14, 21, 28)) {
            val checkDate = startDate.plusDays(dayOffset.toLong())
            val transitPositions = getTransitPositionsForDateTime(checkDate, natalChart.birthData.timezone)

            significantPlanets.forEach { planet ->
                val transitPos = transitPositions.find { it.planet == planet } ?: return@forEach
                val houseFromMoon = calculateHouseFromSign(transitPos.sign, natalMoon.sign)

                // Check for significant house transits
                val isSignificant = when (planet) {
                    Planet.SATURN -> houseFromMoon in listOf(1, 4, 7, 8, 10, 12) // Sade Sati, Ashtama Shani, etc.
                    Planet.JUPITER -> houseFromMoon in listOf(1, 5, 9) // Guru in Kendra/Trikona
                    Planet.RAHU, Planet.KETU -> houseFromMoon in listOf(1, 7) // Node on axis
                    else -> false
                }

                if (isSignificant) {
                    val description = generatePeriodDescription(planet, houseFromMoon, language)
                    val intensity = when {
                        planet == Planet.SATURN && houseFromMoon == 8 -> 5 // Ashtama Shani
                        planet == Planet.SATURN && houseFromMoon in listOf(1, 12) -> 4 // Sade Sati peak/end
                        planet == Planet.JUPITER && houseFromMoon in listOf(1, 5, 9) -> 4
                        else -> 3
                    }

                    // Find period duration (simplified - actual implementation would track sign changes)
                    val endDate = checkDate.plusDays(7)

                    periods.add(
                        SignificantPeriod(
                            startDate = checkDate,
                            endDate = endDate,
                            description = description,
                            planets = listOf(planet),
                            intensity = intensity
                        )
                    )
                }
            }
        }

        return periods.distinctBy { it.description }
    }

    /**
     * Calculate house from one sign to another
     */
    private fun calculateHouseFromSign(targetSign: ZodiacSign, referenceSign: ZodiacSign): Int {
        return VedicAstrologyUtils.getHouseFromSigns(targetSign, referenceSign)
    }

    /**
     * Calculate angular separation
     */
    private fun calculateAngularSeparation(long1: Double, long2: Double): Double {
        return VedicAstrologyUtils.angularDistance(long1, long2)
    }

    /**
     * Calculate orb from exact aspect
     */
    private fun calculateOrb(actualAngle: Double, aspectAngle: Double): Double {
        val diff = abs(actualAngle - aspectAngle)
        return minOf(diff, 360.0 - diff)
    }

    /**
     * Determine if aspect is applying
     */
    private fun isAspectApplying(
        transitPos: PlanetPosition,
        natalPos: PlanetPosition,
        aspectAngle: Double
    ): Boolean {
        // If transit planet is moving faster than natal (always true for transits to natal),
        // check if the orb is decreasing
        val currentOrb = calculateOrb(
            VedicAstrologyUtils.angularDistance(transitPos.longitude, natalPos.longitude),
            aspectAngle
        )

        // Estimate future position based on speed
        val futureLong = VedicAstrologyUtils.normalizeLongitude(transitPos.longitude + transitPos.speed)
        val futureOrb = calculateOrb(
            VedicAstrologyUtils.angularDistance(futureLong, natalPos.longitude),
            aspectAngle
        )

        return futureOrb < currentOrb
    }

    /**
     * Generate Gochara interpretation
     */
    private fun generateGocharaInterpretation(
        planet: Planet,
        houseFromMoon: Int,
        effect: TransitEffect,
        isVedhaAffected: Boolean,
        language: Language
    ): String {
        val planetName = planet.getLocalizedName(language)
        val isFavorable = effect in listOf(TransitEffect.EXCELLENT, TransitEffect.GOOD)
        
        val effectKey = if (isFavorable) {
            GOCHARA_FAVORABLE_KEYS[planet]?.get(houseFromMoon)
        } else {
            GOCHARA_UNFAVORABLE_KEYS[planet]?.get(houseFromMoon)
        }

        val baseInterpretation = effectKey?.let { getString(it) } ?: run {
            // Fallback if key missing
            val houseMattersKey = when (houseFromMoon) {
                1 -> StringKeyTransit.GOCHARA_MATTERS_1
                2 -> StringKeyTransit.GOCHARA_MATTERS_2
                3 -> StringKeyTransit.GOCHARA_MATTERS_3
                4 -> StringKeyTransit.GOCHARA_MATTERS_4
                5 -> StringKeyTransit.GOCHARA_MATTERS_5
                6 -> StringKeyTransit.GOCHARA_MATTERS_6
                7 -> StringKeyTransit.GOCHARA_MATTERS_7
                8 -> StringKeyTransit.GOCHARA_MATTERS_8
                9 -> StringKeyTransit.GOCHARA_MATTERS_9
                10 -> StringKeyTransit.GOCHARA_MATTERS_10
                11 -> StringKeyTransit.GOCHARA_MATTERS_11
                12 -> StringKeyTransit.GOCHARA_MATTERS_12
                else -> StringKeyTransit.GOCHARA_MATTERS_GENERAL
            }
            val houseMatters = StringResources.get(houseMattersKey, language)
            val templateKey = when (effect) {
                TransitEffect.EXCELLENT -> StringKeyTransit.GOCHARA_EFFECT_EXCELLENT
                TransitEffect.GOOD -> StringKeyTransit.GOCHARA_EFFECT_GOOD
                TransitEffect.NEUTRAL -> StringKeyTransit.GOCHARA_EFFECT_NEUTRAL
                TransitEffect.CHALLENGING -> StringKeyTransit.GOCHARA_EFFECT_CHALLENGING
                TransitEffect.DIFFICULT -> StringKeyTransit.GOCHARA_EFFECT_DIFFICULT
            }
            StringResources.get(templateKey, language, planetName, houseFromMoon, houseMatters, "")
        }

        return if (isVedhaAffected) {
            baseInterpretation + getString(StringKeyHoroscope.VEDHA_OBSTRUCTION, "...") // Analyzer doesn't always have source here
        } else {
            baseInterpretation
        }
    }

    /**
     * Generate aspect interpretation
     */
    private fun generateAspectInterpretation(
        transitingPlanet: Planet,
        natalPlanet: Planet,
        aspectKey: StringKeyTransit,
        isApplying: Boolean,
        language: Language
    ): String {
        val applyingStr = if (isApplying) StringResources.get(StringKeyTransit.ASPECT_APPLYING, language) else StringResources.get(StringKeyTransit.ASPECT_SEPARATING, language)
        val beneficTransit = transitingPlanet in listOf(Planet.JUPITER, Planet.VENUS)
        
        // We use the English name conceptually for logic, but display localized string
        val aspectName = StringResources.get(aspectKey, Language.ENGLISH)
        val harmonicAspect = aspectName in listOf("Trine", "Sextile")
        
        val transitingName = transitingPlanet.getLocalizedName(language)
        val natalName = natalPlanet.getLocalizedName(language)
        val localizedAspectName = StringResources.get(aspectKey, language)

        return when {
            beneficTransit && harmonicAspect -> StringResources.get(StringKeyTransit.ASPECT_INTERP_BENEFIC_HARMONIC, language, transitingName, localizedAspectName, natalName, applyingStr)
            beneficTransit -> StringResources.get(StringKeyTransit.ASPECT_INTERP_BENEFIC, language, transitingName, localizedAspectName, natalName, applyingStr)
            harmonicAspect -> StringResources.get(StringKeyTransit.ASPECT_INTERP_HARMONIC, language, transitingName, localizedAspectName, natalName, applyingStr)
            else -> StringResources.get(StringKeyTransit.ASPECT_INTERP_CHALLENGING, language, transitingName, localizedAspectName, natalName, applyingStr)
        }
    }

    /**
     * Generate overall summary
     */
    private fun generateOverallSummary(
        quality: TransitQuality,
        gocharaResults: List<GocharaResult>,
        transitAspects: List<TransitAspect>,
        language: Language
    ): String {
        val favorablePlanets = gocharaResults.filter { it.effect in listOf(TransitEffect.EXCELLENT, TransitEffect.GOOD) }
            .map { it.planet.getLocalizedName(language) }
        val challengingPlanets = gocharaResults.filter { it.effect in listOf(TransitEffect.CHALLENGING, TransitEffect.DIFFICULT) }
            .map { it.planet.getLocalizedName(language) }

        val favStr = favorablePlanets.joinToString(", ")
        val chalStr = challengingPlanets.joinToString(", ")

        return when (quality) {
            TransitQuality.EXCELLENT -> getString(StringKeyTransit.SUMMARY_EXCELLENT, favStr)
            TransitQuality.GOOD -> getString(StringKeyTransit.SUMMARY_GOOD, favStr)
            TransitQuality.MIXED -> getString(StringKeyTransit.SUMMARY_MIXED, favStr, chalStr)
            TransitQuality.CHALLENGING -> getString(StringKeyTransit.SUMMARY_CHALLENGING, chalStr)
            TransitQuality.DIFFICULT -> getString(StringKeyTransit.SUMMARY_DIFFICULT, chalStr)
        }
    }

    /**
     * Generate focus areas based on transit analysis
     */
    private fun generateFocusAreas(
        gocharaResults: List<GocharaResult>,
        transitAspects: List<TransitAspect>,
        language: Language
    ): List<String> {
        val areas = mutableListOf<String>()

        // Check Saturn transit
        gocharaResults.find { it.planet == Planet.SATURN }?.let { saturnResult ->
            val focusKey = when (saturnResult.houseFromMoon) {
                1, 12 -> StringKeyTransit.FOCUS_SADE_SATI
                8 -> StringKeyTransit.FOCUS_ASHTAMA_SHANI
                4 -> StringKeyTransit.FOCUS_SATURN_4
                10 -> StringKeyTransit.FOCUS_SATURN_10
                else -> null
            }
            focusKey?.let { areas.add(getString(it)) }
        }

        // Check Jupiter transit
        gocharaResults.find { it.planet == Planet.JUPITER }?.let { jupiterResult ->
            val focusKey = when (jupiterResult.houseFromMoon) {
                1, 5, 9 -> StringKeyTransit.FOCUS_JUPITER_TRINE
                2 -> StringKeyTransit.FOCUS_JUPITER_2
                11 -> StringKeyTransit.FOCUS_JUPITER_11
                else -> null
            }
            focusKey?.let { areas.add(getString(it)) }
        }

        // Check strong aspects
        transitAspects.filter { it.strength > 0.8 }.take(3).forEach { aspect ->
            val aspectName = getString(aspect.aspectKey)
            val transitName = aspect.transitingPlanet.getLocalizedName(language)
            val natalName = aspect.natalPlanet.getLocalizedName(language)
            areas.add(getString(StringKeyTransit.FOCUS_STRONG_ASPECT, aspectName, transitName, natalName))
        }

        return areas.take(5)
    }

    /**
     * Generate period description
     */
    private fun generatePeriodDescription(planet: Planet, houseFromMoon: Int, language: Language): String {
        return when (planet) {
            Planet.SATURN -> when (houseFromMoon) {
                12 -> getString(StringKeyTransit.PERIOD_SADE_SATI_BEGIN)
                1 -> getString(StringKeyTransit.PERIOD_SADE_SATI_PEAK)
                2 -> getString(StringKeyTransit.PERIOD_SADE_SATI_END)
                8 -> getString(StringKeyTransit.PERIOD_ASHTAMA_SHANI)
                4 -> getString(StringKeyTransit.PERIOD_KANTAK_SHANI)
                7 -> getString(StringKeyTransit.PERIOD_SATURN_7)
                10 -> getString(StringKeyTransit.PERIOD_SATURN_10)
                else -> getString(StringKeyTransit.PERIOD_SATURN_GENERIC, houseFromMoon)
            }
            Planet.JUPITER -> when (houseFromMoon) {
                1 -> getString(StringKeyTransit.PERIOD_JUPITER_1)
                5 -> getString(StringKeyTransit.PERIOD_JUPITER_5)
                9 -> getString(StringKeyTransit.PERIOD_JUPITER_9)
                else -> getString(StringKeyTransit.PERIOD_JUPITER_GENERIC, houseFromMoon)
            }
            Planet.RAHU -> getString(StringKeyTransit.PERIOD_RAHU_GENERIC, houseFromMoon)
            Planet.KETU -> getString(StringKeyTransit.PERIOD_KETU_GENERIC, houseFromMoon)
            else -> getString(StringKeyTransit.PERIOD_GENERIC, planet.getLocalizedName(language), houseFromMoon)
        }
    }

    /**
     * Clean up resources
     */
    fun close() {
        ephemerisEngine.close()
    }
}
