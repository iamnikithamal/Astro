package com.astro.storm.ephemeris

import android.content.Context
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.model.Nakshatra
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Comprehensive Tarabala and Chandrabala Calculator
 *
 * This calculator implements two essential daily strength systems from Vedic astrology:
 *
 * 1. **TARABALA (Star Strength)**
 *    - Based on the 9-fold nakshatra cycle from birth nakshatra
 *    - Determines favorable and unfavorable days for activities
 *    - Traditional 9 Taras: Janma, Sampat, Vipat, Kshema, Pratyari, Sadhaka, Vadha, Mitra, Parama Mitra
 *
 * 2. **CHANDRABALA (Moon Strength)**
 *    - Based on transit Moon's position from natal Moon sign
 *    - Houses 3, 6, 10, 11 are favorable (Upachaya)
 *    - Houses 1, 2, 5, 7, 9 are neutral to good
 *    - Houses 4, 8, 12 are challenging
 *
 * Combined Analysis:
 * - Both Tarabala and Chandrabala should be favorable for important activities
 * - Used extensively in Muhurta (electional astrology) and daily predictions
 *
 * Classical References:
 * - Muhurta Chintamani
 * - Muhurta Martanda
 * - Phaladeepika (Transit chapters)
 *
 * @author AstroStorm - Ultra-Precision Vedic Astrology
 */
object TarabalaCalculator {

    // ============================================================================
    // TARA (NAKSHATRA) TYPES - 9-fold cycle
    // ============================================================================

    /**
     * The nine Taras in the nakshatra cycle.
     * Each represents a specific strength/weakness pattern.
     */
    enum class Tara(
        val number: Int,
        val sanskritName: String,
        val englishMeaning: String,
        val isFavorable: Boolean,
        val strength: Int // 1-5 scale, 5 being most favorable
    ) {
        JANMA(1, "Janma", "Birth Star", false, 2),
        SAMPAT(2, "Sampat", "Wealth", true, 4),
        VIPAT(3, "Vipat", "Danger", false, 1),
        KSHEMA(4, "Kshema", "Well-being", true, 4),
        PRATYARI(5, "Pratyari", "Obstacle", false, 2),
        SADHAKA(6, "Sadhaka", "Achievement", true, 3),
        VADHA(7, "Vadha", "Death", false, 1),
        MITRA(8, "Mitra", "Friend", true, 4),
        PARAMA_MITRA(9, "Parama Mitra", "Great Friend", true, 5);

        /**
         * Get localized name
         */
        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                JANMA -> StringKeyAnalysis.TARA_JANMA
                SAMPAT -> StringKeyAnalysis.TARA_SAMPAT
                VIPAT -> StringKeyAnalysis.TARA_VIPAT
                KSHEMA -> StringKeyAnalysis.TARA_KSHEMA
                PRATYARI -> StringKeyAnalysis.TARA_PRATYARI
                SADHAKA -> StringKeyAnalysis.TARA_SADHAKA
                VADHA -> StringKeyAnalysis.TARA_VADHA
                MITRA -> StringKeyAnalysis.TARA_MITRA
                PARAMA_MITRA -> StringKeyAnalysis.TARA_PARAMA_MITRA
            }
            return StringResources.get(key, language)
        }

        /**
         * Get localized description
         */
        fun getLocalizedDescription(language: Language): String {
            val key = when (this) {
                JANMA -> StringKeyAnalysis.TARA_JANMA_DESC
                SAMPAT -> StringKeyAnalysis.TARA_SAMPAT_DESC
                VIPAT -> StringKeyAnalysis.TARA_VIPAT_DESC
                KSHEMA -> StringKeyAnalysis.TARA_KSHEMA_DESC
                PRATYARI -> StringKeyAnalysis.TARA_PRATYARI_DESC
                SADHAKA -> StringKeyAnalysis.TARA_SADHAKA_DESC
                VADHA -> StringKeyAnalysis.TARA_VADHA_DESC
                MITRA -> StringKeyAnalysis.TARA_MITRA_DESC
                PARAMA_MITRA -> StringKeyAnalysis.TARA_PARAMA_MITRA_DESC
            }
            return StringResources.get(key, language)
        }

        companion object {
            fun fromNumber(num: Int): Tara = entries.find { it.number == num } ?: JANMA
        }
    }

    // ============================================================================
    // CHANDRABALA - Moon position strength
    // ============================================================================

    /**
     * Chandrabala strength levels based on Moon's transit house from natal Moon.
     */
    enum class ChandrabalaStrength(
        val displayName: String,
        val strength: Int, // 1-5 scale
        val isFavorable: Boolean
    ) {
        EXCELLENT("Excellent", 5, true),
        GOOD("Good", 4, true),
        NEUTRAL("Neutral", 3, true),
        WEAK("Weak", 2, false),
        UNFAVORABLE("Unfavorable", 1, false);

        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                EXCELLENT -> StringKeyAnalysis.CHANDRABALA_EXCELLENT
                GOOD -> StringKeyAnalysis.CHANDRABALA_GOOD
                NEUTRAL -> StringKeyAnalysis.CHANDRABALA_NEUTRAL
                WEAK -> StringKeyAnalysis.CHANDRABALA_WEAK
                UNFAVORABLE -> StringKeyAnalysis.CHANDRABALA_UNFAVORABLE
            }
            return StringResources.get(key, language)
        }
    }

    /**
     * Chandrabala house significations - each house from Moon has specific effects
     */
    private val CHANDRABALA_HOUSE_STRENGTH = mapOf(
        1 to ChandrabalaStrength.NEUTRAL,      // Self - neutral
        2 to ChandrabalaStrength.GOOD,         // Wealth - generally good
        3 to ChandrabalaStrength.EXCELLENT,    // Courage (Upachaya) - excellent
        4 to ChandrabalaStrength.WEAK,         // Home - can be restrictive
        5 to ChandrabalaStrength.GOOD,         // Intelligence - good
        6 to ChandrabalaStrength.EXCELLENT,    // Victory (Upachaya) - excellent
        7 to ChandrabalaStrength.GOOD,         // Partnership - good
        8 to ChandrabalaStrength.UNFAVORABLE,  // Obstacles - unfavorable
        9 to ChandrabalaStrength.GOOD,         // Fortune - good
        10 to ChandrabalaStrength.EXCELLENT,   // Career (Upachaya) - excellent
        11 to ChandrabalaStrength.EXCELLENT,   // Gains (Upachaya) - excellent
        12 to ChandrabalaStrength.UNFAVORABLE  // Losses - unfavorable
    )

    // ============================================================================
    // DATA CLASSES - Results
    // ============================================================================

    /**
     * Single Tarabala result for a specific nakshatra
     */
    data class TarabalaResult(
        val targetNakshatra: Nakshatra,
        val tara: Tara,
        val cycle: Int, // Which 9-nakshatra cycle (1-3)
        val overallStrength: Int,
        val recommendations: List<String>
    ) {
        val isFavorable: Boolean get() = tara.isFavorable
    }

    /**
     * Single Chandrabala result
     */
    data class ChandrabalaResult(
        val transitMoonSign: ZodiacSign,
        val natalMoonSign: ZodiacSign,
        val houseFromMoon: Int,
        val strength: ChandrabalaStrength,
        val significations: String,
        val recommendations: List<String>
    ) {
        val isFavorable: Boolean get() = strength.isFavorable
    }

    /**
     * Combined daily strength result
     */
    data class DailyStrengthResult(
        val date: LocalDate,
        val tarabala: TarabalaResult,
        val chandrabala: ChandrabalaResult,
        val combinedStrength: CombinedStrength,
        val overallScore: Int, // 0-100
        val favorableActivities: List<ActivityRecommendation>,
        val avoidActivities: List<ActivityRecommendation>,
        val generalAdvice: String
    )

    /**
     * Combined strength assessment
     */
    enum class CombinedStrength(
        val displayName: String,
        val score: Int
    ) {
        HIGHLY_FAVORABLE("Highly Favorable", 5),
        FAVORABLE("Favorable", 4),
        MIXED("Mixed Results", 3),
        CHALLENGING("Challenging", 2),
        UNFAVORABLE("Unfavorable", 1);

        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                HIGHLY_FAVORABLE -> StringKeyAnalysis.COMBINED_HIGHLY_FAVORABLE
                FAVORABLE -> StringKeyAnalysis.COMBINED_FAVORABLE
                MIXED -> StringKeyAnalysis.COMBINED_MIXED
                CHALLENGING -> StringKeyAnalysis.COMBINED_CHALLENGING
                UNFAVORABLE -> StringKeyAnalysis.COMBINED_UNFAVORABLE
            }
            return StringResources.get(key, language)
        }
    }

    /**
     * Activity recommendation with suitability rating
     */
    data class ActivityRecommendation(
        val activity: String,
        val suitability: ActivitySuitability,
        val reason: String
    )

    enum class ActivitySuitability {
        HIGHLY_RECOMMENDED,
        RECOMMENDED,
        ACCEPTABLE,
        CAUTION,
        AVOID
    }

    /**
     * Weekly forecast with daily strengths
     */
    data class WeeklyForecast(
        val startDate: LocalDate,
        val dailyStrengths: List<DailyStrengthResult>,
        val bestDay: DailyStrengthResult,
        val worstDay: DailyStrengthResult,
        val weeklyAdvice: String
    )

    /**
     * Complete Tarabala/Chandrabala analysis
     */
    data class TarabalaChandrabalaAnalysis(
        val birthNakshatra: Nakshatra,
        val natalMoonSign: ZodiacSign,
        val currentDate: LocalDate,
        val currentTarabala: TarabalaResult,
        val currentChandrabala: ChandrabalaResult,
        val todayStrength: DailyStrengthResult,
        val weeklyForecast: WeeklyForecast,
        val allTaras: List<TarabalaResult>, // All 27 nakshatras analyzed
        val timestamp: Long = System.currentTimeMillis()
    )

    // ============================================================================
    // CALCULATION METHODS
    // ============================================================================

    /**
     * Calculate complete Tarabala and Chandrabala analysis
     *
     * @param context Android context for ephemeris engine
     * @param chart The birth chart
     * @param currentDateTime Current date/time for transit calculations
     * @return Complete analysis with daily and weekly forecasts
     */
    fun calculateAnalysis(
        context: Context,
        chart: VedicChart,
        currentDateTime: LocalDateTime = LocalDateTime.now()
    ): TarabalaChandrabalaAnalysis? {
        // Get birth nakshatra from Moon position
        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
            ?: return null

        val birthNakshatra = moonPosition.nakshatra
        val natalMoonSign = moonPosition.sign
        val currentDate = currentDateTime.toLocalDate()

        // Calculate current transit Moon position
        val transitMoonSign = calculateTransitMoonSign(context, currentDateTime, chart)
            ?: return null

        val transitNakshatra = calculateTransitNakshatra(context, currentDateTime, chart)
            ?: return null

        // Calculate Tarabala for current nakshatra
        val currentTarabala = calculateTarabala(birthNakshatra, transitNakshatra)

        // Calculate Chandrabala
        val currentChandrabala = calculateChandrabala(natalMoonSign, transitMoonSign)

        // Calculate today's combined strength
        val todayStrength = calculateDailyStrength(
            currentDate,
            currentTarabala,
            currentChandrabala
        )

        // Calculate weekly forecast
        val weeklyForecast = calculateWeeklyForecast(
            context,
            chart,
            currentDate
        )

        // Calculate all 27 Tarabala results
        val allTaras = Nakshatra.entries.map { targetNak ->
            calculateTarabala(birthNakshatra, targetNak)
        }

        return TarabalaChandrabalaAnalysis(
            birthNakshatra = birthNakshatra,
            natalMoonSign = natalMoonSign,
            currentDate = currentDate,
            currentTarabala = currentTarabala,
            currentChandrabala = currentChandrabala,
            todayStrength = todayStrength,
            weeklyForecast = weeklyForecast,
            allTaras = allTaras
        )
    }

    /**
     * Calculate Tarabala for a specific nakshatra from birth nakshatra
     */
    fun calculateTarabala(birthNakshatra: Nakshatra, targetNakshatra: Nakshatra): TarabalaResult {
        val birthNum = birthNakshatra.number
        val targetNum = targetNakshatra.number

        // Calculate position in the 27-nakshatra cycle
        var diff = targetNum - birthNum
        if (diff < 0) diff += 27
        if (diff == 0) diff = 0 // Same nakshatra

        // Determine which cycle (1-3) and tara number (1-9)
        val cycle = (diff / 9) + 1
        val taraNumber = if (diff == 0) 1 else (diff % 9) + 1

        val tara = Tara.fromNumber(if (taraNumber == 0) 9 else taraNumber)

        // Calculate strength with cycle consideration
        // First cycle is strongest, third cycle is weakest
        val cycleModifier = when (cycle) {
            1 -> 1.0
            2 -> 0.8
            3 -> 0.6
            else -> 1.0
        }

        val overallStrength = (tara.strength * cycleModifier * 20).toInt()

        val recommendations = generateTarabalaRecommendations(tara)

        return TarabalaResult(
            targetNakshatra = targetNakshatra,
            tara = tara,
            cycle = cycle.coerceIn(1, 3),
            overallStrength = overallStrength,
            recommendations = recommendations
        )
    }

    /**
     * Calculate Chandrabala based on transit Moon position from natal Moon
     */
    fun calculateChandrabala(natalMoonSign: ZodiacSign, transitMoonSign: ZodiacSign): ChandrabalaResult {
        // Calculate house position of transit Moon from natal Moon
        var houseFromMoon = transitMoonSign.number - natalMoonSign.number + 1
        if (houseFromMoon <= 0) houseFromMoon += 12

        val strength = CHANDRABALA_HOUSE_STRENGTH[houseFromMoon] ?: ChandrabalaStrength.NEUTRAL

        val significations = getHouseSignifications(houseFromMoon)
        val recommendations = generateChandrabalaRecommendations(houseFromMoon, strength)

        return ChandrabalaResult(
            transitMoonSign = transitMoonSign,
            natalMoonSign = natalMoonSign,
            houseFromMoon = houseFromMoon,
            strength = strength,
            significations = significations,
            recommendations = recommendations
        )
    }

    /**
     * Calculate combined daily strength from Tarabala and Chandrabala
     */
    fun calculateDailyStrength(
        date: LocalDate,
        tarabala: TarabalaResult,
        chandrabala: ChandrabalaResult
    ): DailyStrengthResult {
        // Calculate combined strength
        val taraScore = tarabala.overallStrength
        val chandraScore = chandrabala.strength.strength * 20

        val overallScore = ((taraScore + chandraScore) / 2).coerceIn(0, 100)

        val combinedStrength = when {
            tarabala.isFavorable && chandrabala.isFavorable && overallScore >= 70 -> CombinedStrength.HIGHLY_FAVORABLE
            tarabala.isFavorable && chandrabala.isFavorable -> CombinedStrength.FAVORABLE
            tarabala.isFavorable || chandrabala.isFavorable -> CombinedStrength.MIXED
            !tarabala.isFavorable && !chandrabala.isFavorable && overallScore <= 30 -> CombinedStrength.UNFAVORABLE
            else -> CombinedStrength.CHALLENGING
        }

        val (favorableActivities, avoidActivities) = generateActivityRecommendations(
            tarabala,
            chandrabala,
            combinedStrength
        )

        val generalAdvice = generateGeneralAdvice(tarabala, chandrabala, combinedStrength)

        return DailyStrengthResult(
            date = date,
            tarabala = tarabala,
            chandrabala = chandrabala,
            combinedStrength = combinedStrength,
            overallScore = overallScore,
            favorableActivities = favorableActivities,
            avoidActivities = avoidActivities,
            generalAdvice = generalAdvice
        )
    }

    /**
     * Calculate weekly forecast
     */
    fun calculateWeeklyForecast(
        context: Context,
        chart: VedicChart,
        startDate: LocalDate
    ): WeeklyForecast {
        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
        if (moonPosition == null) {
            // Return empty forecast if no Moon position
            val emptyResult = createEmptyDailyResult(startDate)
            return WeeklyForecast(
                startDate = startDate,
                dailyStrengths = (0..6).map { createEmptyDailyResult(startDate.plusDays(it.toLong())) },
                bestDay = emptyResult,
                worstDay = emptyResult,
                weeklyAdvice = "Unable to calculate - Moon position unavailable"
            )
        }

        val birthNakshatra = moonPosition.nakshatra
        val natalMoonSign = moonPosition.sign

        val dailyStrengths = (0..6).map { dayOffset ->
            val date = startDate.plusDays(dayOffset.toLong())
            val dateTime = date.atStartOfDay()

            // Calculate transit positions for this date
            val transitMoonSign = calculateTransitMoonSignForDate(context, date, chart) ?: natalMoonSign
            val transitNakshatra = calculateTransitNakshatraForDate(context, date, chart) ?: birthNakshatra

            val tarabala = calculateTarabala(birthNakshatra, transitNakshatra)
            val chandrabala = calculateChandrabala(natalMoonSign, transitMoonSign)

            calculateDailyStrength(date, tarabala, chandrabala)
        }

        val bestDay = dailyStrengths.maxByOrNull { it.overallScore } ?: dailyStrengths.first()
        val worstDay = dailyStrengths.minByOrNull { it.overallScore } ?: dailyStrengths.first()

        val weeklyAdvice = generateWeeklyAdvice(dailyStrengths, bestDay, worstDay)

        return WeeklyForecast(
            startDate = startDate,
            dailyStrengths = dailyStrengths,
            bestDay = bestDay,
            worstDay = worstDay,
            weeklyAdvice = weeklyAdvice
        )
    }

    // ============================================================================
    // TRANSIT MOON CALCULATIONS
    // ============================================================================

    /**
     * Calculate current transit Moon sign using ephemeris
     */
    private fun calculateTransitMoonSign(context: Context, dateTime: LocalDateTime, chart: VedicChart): ZodiacSign? {
        return try {
            val engineInstance = SwissEphemerisEngine.getInstance(context) ?: return null
            val moonPos = engineInstance.calculatePlanetPosition(
                Planet.MOON, dateTime, chart.birthData.timezone,
                chart.birthData.latitude, chart.birthData.longitude
            )

            // Position is already in sidereal
            ZodiacSign.fromLongitude(moonPos.longitude)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Calculate transit Moon nakshatra
     */
    private fun calculateTransitNakshatra(context: Context, dateTime: LocalDateTime, chart: VedicChart): Nakshatra? {
        return try {
            val engineInstance = SwissEphemerisEngine.getInstance(context) ?: return null
            val moonPos = engineInstance.calculatePlanetPosition(
                Planet.MOON, dateTime, chart.birthData.timezone,
                chart.birthData.latitude, chart.birthData.longitude
            )

            // Position is already in sidereal
            val nakshatraInfo = Nakshatra.fromLongitude(moonPos.longitude)
            nakshatraInfo.first
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Calculate transit Moon sign for a specific date
     */
    private fun calculateTransitMoonSignForDate(context: Context, date: LocalDate, chart: VedicChart): ZodiacSign? {
        return calculateTransitMoonSign(context, date.atTime(12, 0), chart)
    }

    /**
     * Calculate transit nakshatra for a specific date
     */
    private fun calculateTransitNakshatraForDate(context: Context, date: LocalDate, chart: VedicChart): Nakshatra? {
        return calculateTransitNakshatra(context, date.atTime(12, 0), chart)
    }

    // ============================================================================
    // RECOMMENDATION GENERATORS
    // ============================================================================

    private fun generateTarabalaRecommendations(tara: Tara): List<String> {
        return when (tara) {
            Tara.JANMA -> listOf(
                "Birth star day - rest and introspection recommended",
                "Avoid starting new ventures",
                "Good for spiritual practices and meditation",
                "Health requires extra attention"
            )
            Tara.SAMPAT -> listOf(
                "Excellent for financial matters",
                "Start wealth-generating activities",
                "Good for investments and purchases",
                "Favorable for business negotiations"
            )
            Tara.VIPAT -> listOf(
                "Avoid important decisions",
                "Be cautious in travels",
                "Not suitable for new beginnings",
                "Focus on routine activities only"
            )
            Tara.KSHEMA -> listOf(
                "Good for health-related activities",
                "Favorable for medical treatments",
                "Support well-being initiatives",
                "Safe for moderate new undertakings"
            )
            Tara.PRATYARI -> listOf(
                "Expect minor obstacles",
                "Stay patient with challenges",
                "Avoid confrontations",
                "Not ideal for important meetings"
            )
            Tara.SADHAKA -> listOf(
                "Good for achieving goals",
                "Favorable for completing tasks",
                "Support ongoing projects",
                "Moderate success in new ventures"
            )
            Tara.VADHA -> listOf(
                "Most inauspicious tara - maximum caution",
                "Avoid all major activities",
                "Not suitable for travel or surgery",
                "Focus on prayers and spiritual protection"
            )
            Tara.MITRA -> listOf(
                "Favorable for relationships",
                "Good for meetings and collaborations",
                "Support partnerships and friendships",
                "Suitable for social activities"
            )
            Tara.PARAMA_MITRA -> listOf(
                "Most auspicious tara - excellent for everything",
                "Ideal for starting new ventures",
                "Best time for important decisions",
                "Maximum success probability"
            )
        }
    }

    private fun generateChandrabalaRecommendations(house: Int, strength: ChandrabalaStrength): List<String> {
        return when (house) {
            1 -> listOf("Personal matters are neutral", "Self-reflection day", "Health awareness needed")
            2 -> listOf("Good for family matters", "Favorable for financial discussions", "Speech brings benefits")
            3 -> listOf("Excellent courage and initiative", "Best for bold actions", "Travel is favorable", "Communications succeed")
            4 -> listOf("Focus on home comforts", "Mother may need attention", "Avoid property decisions")
            5 -> listOf("Intelligence enhanced", "Good for education", "Children bring joy", "Romance may blossom")
            6 -> listOf("Victory over enemies", "Competitions favored", "Health improves", "Service brings rewards")
            7 -> listOf("Partnerships favorable", "Business dealings good", "Marriage matters positive")
            8 -> listOf("Avoid risky activities", "Hidden matters surface", "Transformation possible", "Caution advised")
            9 -> listOf("Fortune smiles", "Long journeys favorable", "Spiritual growth", "Father may help")
            10 -> listOf("Career advancement", "Professional success", "Authority recognition", "Best for work matters")
            11 -> listOf("Maximum gains possible", "Friends bring opportunities", "Wishes get fulfilled", "Income increases")
            12 -> listOf("Expenses may increase", "Rest and recuperation needed", "Spiritual practices beneficial", "Foreign matters need care")
            else -> listOf("Neutral influences")
        }
    }

    private fun generateActivityRecommendations(
        tarabala: TarabalaResult,
        chandrabala: ChandrabalaResult,
        combined: CombinedStrength
    ): Pair<List<ActivityRecommendation>, List<ActivityRecommendation>> {
        val favorable = mutableListOf<ActivityRecommendation>()
        val avoid = mutableListOf<ActivityRecommendation>()

        when (combined) {
            CombinedStrength.HIGHLY_FAVORABLE -> {
                favorable.addAll(listOf(
                    ActivityRecommendation("Starting new business", ActivitySuitability.HIGHLY_RECOMMENDED, "Both Tara and Chandra support new beginnings"),
                    ActivityRecommendation("Important meetings", ActivitySuitability.HIGHLY_RECOMMENDED, "Communication enhanced"),
                    ActivityRecommendation("Financial investments", ActivitySuitability.HIGHLY_RECOMMENDED, "Wealth matters favored"),
                    ActivityRecommendation("Medical treatments", ActivitySuitability.RECOMMENDED, "Health matters supported"),
                    ActivityRecommendation("Travel", ActivitySuitability.HIGHLY_RECOMMENDED, "Journeys will be successful"),
                    ActivityRecommendation("Marriage ceremonies", ActivitySuitability.HIGHLY_RECOMMENDED, "Auspicious for unions")
                ))
            }
            CombinedStrength.FAVORABLE -> {
                favorable.addAll(listOf(
                    ActivityRecommendation("Business activities", ActivitySuitability.RECOMMENDED, "Generally supportive"),
                    ActivityRecommendation("Routine work", ActivitySuitability.HIGHLY_RECOMMENDED, "Productivity enhanced"),
                    ActivityRecommendation("Short travels", ActivitySuitability.RECOMMENDED, "Safe for journeys"),
                    ActivityRecommendation("Social events", ActivitySuitability.RECOMMENDED, "Relationships favored")
                ))
                avoid.addAll(listOf(
                    ActivityRecommendation("High-risk investments", ActivitySuitability.CAUTION, "Not the best time for speculation")
                ))
            }
            CombinedStrength.MIXED -> {
                favorable.addAll(listOf(
                    ActivityRecommendation("Routine activities", ActivitySuitability.ACCEPTABLE, "Normal productivity expected"),
                    ActivityRecommendation("Ongoing projects", ActivitySuitability.RECOMMENDED, "Continue existing work")
                ))
                avoid.addAll(listOf(
                    ActivityRecommendation("New ventures", ActivitySuitability.CAUTION, "Wait for better timing"),
                    ActivityRecommendation("Major purchases", ActivitySuitability.CAUTION, "Delays possible"),
                    ActivityRecommendation("Important negotiations", ActivitySuitability.CAUTION, "Mixed results likely")
                ))
            }
            CombinedStrength.CHALLENGING -> {
                favorable.addAll(listOf(
                    ActivityRecommendation("Spiritual practices", ActivitySuitability.RECOMMENDED, "Inner work beneficial"),
                    ActivityRecommendation("Rest and recovery", ActivitySuitability.RECOMMENDED, "Rejuvenation favored")
                ))
                avoid.addAll(listOf(
                    ActivityRecommendation("New beginnings", ActivitySuitability.AVOID, "Obstacles likely"),
                    ActivityRecommendation("Travel", ActivitySuitability.CAUTION, "Delays and problems possible"),
                    ActivityRecommendation("Important decisions", ActivitySuitability.AVOID, "Judgment may be clouded"),
                    ActivityRecommendation("Medical procedures", ActivitySuitability.CAUTION, "Unless emergency")
                ))
            }
            CombinedStrength.UNFAVORABLE -> {
                favorable.addAll(listOf(
                    ActivityRecommendation("Prayers and meditation", ActivitySuitability.HIGHLY_RECOMMENDED, "Spiritual protection needed"),
                    ActivityRecommendation("Charity", ActivitySuitability.RECOMMENDED, "Reduces negative karma")
                ))
                avoid.addAll(listOf(
                    ActivityRecommendation("All major activities", ActivitySuitability.AVOID, "Day is inauspicious"),
                    ActivityRecommendation("Travel", ActivitySuitability.AVOID, "Accidents/problems likely"),
                    ActivityRecommendation("Contracts and agreements", ActivitySuitability.AVOID, "Will not be beneficial"),
                    ActivityRecommendation("Starting anything new", ActivitySuitability.AVOID, "Will face destruction"),
                    ActivityRecommendation("Surgeries", ActivitySuitability.AVOID, "Unless life-threatening emergency")
                ))
            }
        }

        return Pair(favorable, avoid)
    }

    private fun generateGeneralAdvice(
        tarabala: TarabalaResult,
        chandrabala: ChandrabalaResult,
        combined: CombinedStrength
    ): String {
        return when (combined) {
            CombinedStrength.HIGHLY_FAVORABLE ->
                "Today is highly auspicious with both Tarabala (${tarabala.tara.sanskritName}) and Chandrabala supporting your activities. " +
                "This is an excellent day for important decisions, new beginnings, and significant undertakings. " +
                "Make the most of this favorable alignment."

            CombinedStrength.FAVORABLE ->
                "Today offers good support from the cosmic energies. Tarabala is ${if (tarabala.isFavorable) "positive" else "neutral"} " +
                "and Moon's position in the ${chandrabala.houseFromMoon}th house provides ${chandrabala.strength.displayName.lowercase()} results. " +
                "Proceed with confidence in normal activities."

            CombinedStrength.MIXED ->
                "Mixed influences today require balanced approach. While ${tarabala.tara.sanskritName} Tara brings " +
                "${if (tarabala.isFavorable) "support" else "challenges"}, Chandrabala shows ${chandrabala.strength.displayName.lowercase()} strength. " +
                "Focus on routine matters and avoid extremes."

            CombinedStrength.CHALLENGING ->
                "Today presents challenges with ${tarabala.tara.sanskritName} Tara and Moon in ${chandrabala.houseFromMoon}th house. " +
                "Exercise caution in all matters. Postpone important decisions if possible. " +
                "Focus on spiritual practices and inner strength."

            CombinedStrength.UNFAVORABLE ->
                "Caution advised today. The ${tarabala.tara.sanskritName} Tara combined with Moon's position creates unfavorable conditions. " +
                "This is not suitable for any important activity. Focus on prayers, charity, and rest. " +
                "Wait for better cosmic alignment before proceeding with significant matters."
        }
    }

    private fun generateWeeklyAdvice(
        dailyStrengths: List<DailyStrengthResult>,
        bestDay: DailyStrengthResult,
        worstDay: DailyStrengthResult
    ): String {
        val avgScore = dailyStrengths.map { it.overallScore }.average().toInt()
        val favorableDays = dailyStrengths.count { it.combinedStrength in listOf(CombinedStrength.HIGHLY_FAVORABLE, CombinedStrength.FAVORABLE) }

        return buildString {
            append("This week shows an average strength score of $avgScore%. ")
            append("$favorableDays out of 7 days are favorable for important activities. ")
            append("Best day is ${bestDay.date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }} with ${bestDay.overallScore}% strength. ")
            append("Avoid major decisions on ${worstDay.date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }} (${worstDay.overallScore}% strength). ")
            append("Plan your important activities accordingly.")
        }
    }

    private fun getHouseSignifications(house: Int): String {
        return when (house) {
            1 -> "Self, personality, physical body"
            2 -> "Wealth, family, speech, food"
            3 -> "Courage, siblings, short journeys, communications"
            4 -> "Home, mother, comfort, vehicles"
            5 -> "Children, intelligence, romance, creativity"
            6 -> "Enemies, diseases, debts, service"
            7 -> "Marriage, partnership, business, travel"
            8 -> "Longevity, obstacles, inheritance, transformation"
            9 -> "Fortune, father, religion, long journeys"
            10 -> "Career, status, authority, profession"
            11 -> "Gains, friends, aspirations, income"
            12 -> "Losses, expenses, liberation, foreign"
            else -> "Unknown"
        }
    }

    private fun createEmptyDailyResult(date: LocalDate): DailyStrengthResult {
        return DailyStrengthResult(
            date = date,
            tarabala = TarabalaResult(Nakshatra.ASHWINI, Tara.JANMA, 1, 50, emptyList()),
            chandrabala = ChandrabalaResult(ZodiacSign.ARIES, ZodiacSign.ARIES, 1, ChandrabalaStrength.NEUTRAL, "", emptyList()),
            combinedStrength = CombinedStrength.MIXED,
            overallScore = 50,
            favorableActivities = emptyList(),
            avoidActivities = emptyList(),
            generalAdvice = "Unable to calculate precise strength"
        )
    }

    /**
     * Generate plain text report
     */
    fun generateReport(analysis: TarabalaChandrabalaAnalysis, language: Language = Language.ENGLISH): String {
        return buildString {
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine("           TARABALA & CHANDRABALA ANALYSIS")
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine()
            appendLine("Birth Nakshatra: ${analysis.birthNakshatra.displayName}")
            appendLine("Natal Moon Sign: ${analysis.natalMoonSign.displayName}")
            appendLine("Analysis Date: ${analysis.currentDate}")
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("TODAY'S TARABALA")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("Transit Nakshatra: ${analysis.currentTarabala.targetNakshatra.displayName}")
            appendLine("Tara: ${analysis.currentTarabala.tara.sanskritName} (${analysis.currentTarabala.tara.englishMeaning})")
            appendLine("Cycle: ${analysis.currentTarabala.cycle} of 3")
            appendLine("Favorable: ${if (analysis.currentTarabala.isFavorable) "Yes" else "No"}")
            appendLine("Strength: ${analysis.currentTarabala.overallStrength}%")
            appendLine()
            appendLine("Recommendations:")
            analysis.currentTarabala.recommendations.forEach {
                appendLine("  • $it")
            }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("TODAY'S CHANDRABALA")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("Transit Moon: ${analysis.currentChandrabala.transitMoonSign.displayName}")
            appendLine("House from Natal Moon: ${analysis.currentChandrabala.houseFromMoon}")
            appendLine("Strength: ${analysis.currentChandrabala.strength.displayName}")
            appendLine("Significations: ${analysis.currentChandrabala.significations}")
            appendLine()
            appendLine("Recommendations:")
            analysis.currentChandrabala.recommendations.forEach {
                appendLine("  • $it")
            }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("COMBINED DAILY STRENGTH")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("Overall Score: ${analysis.todayStrength.overallScore}%")
            appendLine("Combined Strength: ${analysis.todayStrength.combinedStrength.displayName}")
            appendLine()
            appendLine("General Advice:")
            appendLine(analysis.todayStrength.generalAdvice)
            appendLine()
            if (analysis.todayStrength.favorableActivities.isNotEmpty()) {
                appendLine("Favorable Activities:")
                analysis.todayStrength.favorableActivities.forEach {
                    appendLine("  ✓ ${it.activity} - ${it.reason}")
                }
                appendLine()
            }
            if (analysis.todayStrength.avoidActivities.isNotEmpty()) {
                appendLine("Activities to Avoid:")
                analysis.todayStrength.avoidActivities.forEach {
                    appendLine("  ✗ ${it.activity} - ${it.reason}")
                }
            }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("WEEKLY FORECAST")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("Best Day: ${analysis.weeklyForecast.bestDay.date.dayOfWeek} (${analysis.weeklyForecast.bestDay.overallScore}%)")
            appendLine("Avoid: ${analysis.weeklyForecast.worstDay.date.dayOfWeek} (${analysis.weeklyForecast.worstDay.overallScore}%)")
            appendLine()
            appendLine("Daily Forecast:")
            analysis.weeklyForecast.dailyStrengths.forEach { day ->
                val indicator = when {
                    day.overallScore >= 70 -> "★★★"
                    day.overallScore >= 50 -> "★★"
                    day.overallScore >= 30 -> "★"
                    else -> "○"
                }
                appendLine("  ${day.date.dayOfWeek.name.take(3)}: ${day.overallScore}% $indicator ${day.combinedStrength.displayName}")
            }
            appendLine()
            appendLine("Weekly Advice:")
            appendLine(analysis.weeklyForecast.weeklyAdvice)
        }
    }
}
