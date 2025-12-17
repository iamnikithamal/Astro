package com.astro.storm.ephemeris

import android.content.Context
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Comprehensive Gochara Vedha (Transit Obstruction) Calculator
 *
 * Vedha is a critical concept in Vedic transit analysis where the beneficial effects
 * of a planet transiting a favorable house are obstructed (nullified) by another planet
 * positioned in a specific "Vedha point".
 *
 * Key Principles:
 *
 * 1. **VEDHA MECHANISM**
 *    - Only applies when a planet is in a normally favorable transit house
 *    - Specific pairs of houses create mutual obstruction
 *    - The obstructing planet "punctures" or nullifies the good effects
 *
 * 2. **CLASSICAL VEDHA PAIRS (from Moon)**
 *    House 1 ↔ 5 (except Sun-Saturn)
 *    House 2 ↔ 12
 *    House 3 ↔ 9
 *    House 4 ↔ 10
 *    House 6 ↔ 12
 *    House 7 ↔ 11
 *
 * 3. **EXCEPTIONS**
 *    - Sun and Saturn do NOT cause Vedha to each other
 *    - Some traditions exclude Moon's Vedha effects
 *    - Rahu-Ketu Vedha is debated among authorities
 *
 * 4. **PRACTICAL APPLICATION**
 *    - Used to refine transit predictions
 *    - Essential for accurate Gochara Phala (transit results)
 *    - Helps identify why expected transits may not deliver results
 *
 * Classical References:
 * - Phaladeepika (Chapter on Gochara)
 * - Saravali (Transit sections)
 * - Brihat Samhita (Gochara rules)
 *
 * @author AstroStorm - Ultra-Precision Vedic Astrology
 */
object GocharaVedhaCalculator {

    // ============================================================================
    // VEDHA PAIR DEFINITIONS
    // ============================================================================

    /**
     * Classical Vedha pairs from Moon position.
     * Format: If planet is in key house and another planet is in value house, Vedha occurs.
     * These are bidirectional - 3↔9 means 3 and 9 obstruct each other.
     */
    private val VEDHA_PAIRS = mapOf(
        1 to 5,
        2 to 12,
        3 to 9,
        4 to 10,
        5 to 1,
        6 to 12,
        7 to 11,
        9 to 3,
        10 to 4,
        11 to 7,
        12 to 2  // Also 12 ↔ 6 for some
    )

    /**
     * Extended Vedha pairs including the 6-12 bidirectional relationship
     */
    private val EXTENDED_VEDHA_PAIRS = mapOf(
        1 to setOf(5),
        2 to setOf(12),
        3 to setOf(9),
        4 to setOf(10),
        5 to setOf(1),
        6 to setOf(12),
        7 to setOf(11),
        9 to setOf(3),
        10 to setOf(4),
        11 to setOf(7),
        12 to setOf(2, 6)
    )

    /**
     * Favorable transit houses for each planet (from Moon sign)
     * Based on classical Gochara rules
     */
    private val FAVORABLE_HOUSES = mapOf(
        Planet.SUN to setOf(3, 6, 10, 11),
        Planet.MOON to setOf(1, 3, 6, 7, 10, 11),
        Planet.MARS to setOf(3, 6, 11),
        Planet.MERCURY to setOf(2, 4, 6, 8, 10, 11),
        Planet.JUPITER to setOf(2, 5, 7, 9, 11),
        Planet.VENUS to setOf(1, 2, 3, 4, 5, 8, 9, 11, 12),
        Planet.SATURN to setOf(3, 6, 11),
        Planet.RAHU to setOf(3, 6, 10, 11),
        Planet.KETU to setOf(3, 6, 11)
    )

    /**
     * Unfavorable transit houses for each planet
     */
    private val UNFAVORABLE_HOUSES = mapOf(
        Planet.SUN to setOf(1, 2, 4, 5, 7, 8, 9, 12),
        Planet.MOON to setOf(2, 4, 5, 8, 9, 12),
        Planet.MARS to setOf(1, 2, 4, 5, 7, 8, 9, 10, 12),
        Planet.MERCURY to setOf(1, 3, 5, 7, 9, 12),
        Planet.JUPITER to setOf(1, 3, 4, 6, 8, 10, 12),
        Planet.VENUS to setOf(6, 7, 10),
        Planet.SATURN to setOf(1, 2, 4, 5, 7, 8, 9, 10, 12),
        Planet.RAHU to setOf(1, 2, 4, 5, 7, 8, 9, 12),
        Planet.KETU to setOf(1, 2, 4, 5, 7, 8, 9, 10, 12)
    )

    // ============================================================================
    // VEDHA SEVERITY LEVELS
    // ============================================================================

    /**
     * Vedha effect severity
     */
    enum class VedhaSeverity(
        val displayName: String,
        val reductionPercent: Int
    ) {
        COMPLETE("Complete Obstruction", 100),
        STRONG("Strong Obstruction", 75),
        MODERATE("Moderate Obstruction", 50),
        PARTIAL("Partial Obstruction", 25),
        NONE("No Obstruction", 0);

        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                COMPLETE -> StringKeyAnalysis.VEDHA_COMPLETE
                STRONG -> StringKeyAnalysis.VEDHA_STRONG
                MODERATE -> StringKeyAnalysis.VEDHA_MODERATE
                PARTIAL -> StringKeyAnalysis.VEDHA_PARTIAL
                NONE -> StringKeyAnalysis.VEDHA_NONE
            }
            return StringResources.get(key, language)
        }
    }

    /**
     * Transit effect after Vedha consideration
     */
    enum class TransitEffectiveness(
        val displayName: String,
        val score: Int
    ) {
        EXCELLENT("Excellent", 5),
        GOOD("Good", 4),
        MODERATE("Moderate", 3),
        WEAK("Weak", 2),
        NULLIFIED("Nullified by Vedha", 1),
        UNFAVORABLE("Unfavorable Transit", 0);

        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                EXCELLENT -> StringKeyAnalysis.TRANSIT_EXCELLENT
                GOOD -> StringKeyAnalysis.TRANSIT_GOOD
                MODERATE -> StringKeyAnalysis.TRANSIT_MODERATE
                WEAK -> StringKeyAnalysis.TRANSIT_WEAK
                NULLIFIED -> StringKeyAnalysis.TRANSIT_NULLIFIED
                UNFAVORABLE -> StringKeyAnalysis.TRANSIT_UNFAVORABLE
            }
            return StringResources.get(key, language)
        }
    }

    // ============================================================================
    // DATA CLASSES
    // ============================================================================

    /**
     * Single planet transit analysis with Vedha
     */
    data class PlanetTransitVedha(
        val planet: Planet,
        val transitSign: ZodiacSign,
        val houseFromMoon: Int,
        val isNaturallyFavorable: Boolean,
        val hasVedha: Boolean,
        val vedhaSeverity: VedhaSeverity,
        val vedhaSourcePlanets: List<Planet>,
        val vedhaSourceHouse: Int?,
        val effectiveStrength: TransitEffectiveness,
        val significations: List<String>,
        val interpretation: String
    )

    /**
     * Complete Vedha analysis for all transiting planets
     */
    data class CompleteVedhaAnalysis(
        val natalMoonSign: ZodiacSign,
        val analysisDate: LocalDate,
        val planetTransits: List<PlanetTransitVedha>,
        val activeVedhas: List<VedhaInteraction>,
        val overallTransitScore: Int, // 0-100
        val favorableAspects: Int,
        val obstructedAspects: Int,
        val keyInsights: List<String>,
        val recommendations: List<String>,
        val timestamp: Long = System.currentTimeMillis()
    )

    /**
     * Specific Vedha interaction between planets
     */
    data class VedhaInteraction(
        val obstructedPlanet: Planet,
        val obstructedHouse: Int,
        val obstructingPlanet: Planet,
        val obstructingHouse: Int,
        val severity: VedhaSeverity,
        val lostBenefits: String,
        val interpretation: String
    )

    /**
     * Daily Vedha forecast
     */
    data class DailyVedhaForecast(
        val date: LocalDate,
        val moonSign: ZodiacSign,
        val vedhaStatus: List<PlanetTransitVedha>,
        val overallEffectiveness: Int,
        val bestTransits: List<Planet>,
        val obstructedTransits: List<Planet>,
        val dailyAdvice: String
    )

    /**
     * Weekly Vedha overview
     */
    data class WeeklyVedhaOverview(
        val startDate: LocalDate,
        val dailyForecasts: List<DailyVedhaForecast>,
        val keyVedhaEvents: List<VedhaEvent>,
        val weeklyGuidance: String
    )

    /**
     * Vedha event (start/end)
     */
    data class VedhaEvent(
        val date: LocalDate,
        val eventType: VedhaEventType,
        val involvedPlanets: List<Planet>,
        val description: String
    )

    enum class VedhaEventType { VEDHA_BEGINS, VEDHA_ENDS, VEDHA_PEAKS }

    // ============================================================================
    // CALCULATION METHODS
    // ============================================================================

    /**
     * Calculate complete Vedha analysis for current transits
     *
     * @param chart Birth chart with natal Moon position
     * @param transitPositions Current transit planet positions
     * @param analysisDate Date of analysis
     * @return Complete Vedha analysis
     */
    fun calculateVedhaAnalysis(
        chart: VedicChart,
        transitPositions: List<PlanetPosition>,
        analysisDate: LocalDate = LocalDate.now()
    ): CompleteVedhaAnalysis? {
        // Get natal Moon sign
        val natalMoon = chart.planetPositions.find { it.planet == Planet.MOON }
            ?: return null

        val natalMoonSign = natalMoon.sign

        // Analyze each transiting planet
        val planetTransits = mutableListOf<PlanetTransitVedha>()
        val activeVedhas = mutableListOf<VedhaInteraction>()

        // Main planets for Gochara analysis (excluding outer planets)
        val gocharaPlanets = listOf(
            Planet.SUN, Planet.MOON, Planet.MARS, Planet.MERCURY,
            Planet.JUPITER, Planet.VENUS, Planet.SATURN, Planet.RAHU, Planet.KETU
        )

        for (planet in gocharaPlanets) {
            val transitPos = transitPositions.find { it.planet == planet } ?: continue

            val analysis = analyzePlanetTransit(
                planet = planet,
                transitSign = transitPos.sign,
                natalMoonSign = natalMoonSign,
                allTransitPositions = transitPositions
            )

            planetTransits.add(analysis)

            // Collect active Vedhas
            if (analysis.hasVedha) {
                analysis.vedhaSourcePlanets.forEach { obstructor ->
                    activeVedhas.add(createVedhaInteraction(
                        obstructedPlanet = planet,
                        obstructedHouse = analysis.houseFromMoon,
                        obstructingPlanet = obstructor,
                        obstructingHouse = analysis.vedhaSourceHouse ?: 0,
                        severity = analysis.vedhaSeverity
                    ))
                }
            }
        }

        // Calculate overall score
        val favorableCount = planetTransits.count {
            it.effectiveStrength in listOf(TransitEffectiveness.EXCELLENT, TransitEffectiveness.GOOD, TransitEffectiveness.MODERATE)
        }
        val obstructedCount = activeVedhas.size
        val overallScore = calculateOverallScore(planetTransits)

        // Generate insights and recommendations
        val insights = generateKeyInsights(planetTransits, activeVedhas)
        val recommendations = generateRecommendations(planetTransits, activeVedhas)

        return CompleteVedhaAnalysis(
            natalMoonSign = natalMoonSign,
            analysisDate = analysisDate,
            planetTransits = planetTransits,
            activeVedhas = activeVedhas,
            overallTransitScore = overallScore,
            favorableAspects = favorableCount,
            obstructedAspects = obstructedCount,
            keyInsights = insights,
            recommendations = recommendations
        )
    }

    /**
     * Analyze single planet transit with Vedha check
     */
    fun analyzePlanetTransit(
        planet: Planet,
        transitSign: ZodiacSign,
        natalMoonSign: ZodiacSign,
        allTransitPositions: List<PlanetPosition>
    ): PlanetTransitVedha {
        // Calculate house from Moon
        var houseFromMoon = transitSign.number - natalMoonSign.number + 1
        if (houseFromMoon <= 0) houseFromMoon += 12

        // Check if naturally favorable
        val favorableHouses = FAVORABLE_HOUSES[planet] ?: emptySet()
        val isNaturallyFavorable = houseFromMoon in favorableHouses

        // Check for Vedha
        val (hasVedha, severity, sources, vedhaHouse) = checkVedha(
            planet = planet,
            houseFromMoon = houseFromMoon,
            natalMoonSign = natalMoonSign,
            allTransitPositions = allTransitPositions
        )

        // Determine effective strength
        val effectiveStrength = calculateEffectiveStrength(
            planet = planet,
            houseFromMoon = houseFromMoon,
            isNaturallyFavorable = isNaturallyFavorable,
            hasVedha = hasVedha,
            severity = severity
        )

        // Get significations
        val significations = getTransitSignifications(planet, houseFromMoon)

        // Generate interpretation
        val interpretation = generateTransitInterpretation(
            planet = planet,
            houseFromMoon = houseFromMoon,
            isNaturallyFavorable = isNaturallyFavorable,
            hasVedha = hasVedha,
            severity = severity,
            sources = sources
        )

        return PlanetTransitVedha(
            planet = planet,
            transitSign = transitSign,
            houseFromMoon = houseFromMoon,
            isNaturallyFavorable = isNaturallyFavorable,
            hasVedha = hasVedha,
            vedhaSeverity = severity,
            vedhaSourcePlanets = sources,
            vedhaSourceHouse = vedhaHouse,
            effectiveStrength = effectiveStrength,
            significations = significations,
            interpretation = interpretation
        )
    }

    /**
     * Check Vedha for a planet transit
     * Returns: (hasVedha, severity, sourcePlanets, vedhaHouse)
     */
    private fun checkVedha(
        planet: Planet,
        houseFromMoon: Int,
        natalMoonSign: ZodiacSign,
        allTransitPositions: List<PlanetPosition>
    ): VedhaCheckResult {
        // Only check Vedha for favorable positions
        val favorableHouses = FAVORABLE_HOUSES[planet] ?: emptySet()
        if (houseFromMoon !in favorableHouses) {
            return VedhaCheckResult(false, VedhaSeverity.NONE, emptyList(), null)
        }

        // Get Vedha house(s)
        val vedhaHouses = EXTENDED_VEDHA_PAIRS[houseFromMoon] ?: return VedhaCheckResult(false, VedhaSeverity.NONE, emptyList(), null)

        val vedhaSources = mutableListOf<Planet>()
        var maxSeverity = VedhaSeverity.NONE
        var vedhaHouse: Int? = null

        for (vHouse in vedhaHouses) {
            // Find which planets are in the Vedha house
            for (transitPos in allTransitPositions) {
                val otherPlanet = transitPos.planet
                if (otherPlanet == planet) continue

                // Calculate house from Moon for this planet
                var otherHouse = transitPos.sign.number - natalMoonSign.number + 1
                if (otherHouse <= 0) otherHouse += 12

                if (otherHouse == vHouse) {
                    // Check Sun-Saturn exception
                    if (isSunSaturnException(planet, otherPlanet)) {
                        continue
                    }

                    vedhaSources.add(otherPlanet)
                    vedhaHouse = vHouse

                    // Calculate severity based on obstructing planet
                    val severity = calculateVedhaSeverity(planet, otherPlanet)
                    if (severity.reductionPercent > maxSeverity.reductionPercent) {
                        maxSeverity = severity
                    }
                }
            }
        }

        return VedhaCheckResult(
            hasVedha = vedhaSources.isNotEmpty(),
            severity = if (vedhaSources.isEmpty()) VedhaSeverity.NONE else maxSeverity,
            sources = vedhaSources,
            vedhaHouse = vedhaHouse
        )
    }

    private data class VedhaCheckResult(
        val hasVedha: Boolean,
        val severity: VedhaSeverity,
        val sources: List<Planet>,
        val vedhaHouse: Int?
    )

    /**
     * Sun and Saturn exception - they don't cause Vedha to each other
     */
    private fun isSunSaturnException(planet1: Planet, planet2: Planet): Boolean {
        return (planet1 == Planet.SUN && planet2 == Planet.SATURN) ||
               (planet1 == Planet.SATURN && planet2 == Planet.SUN)
    }

    /**
     * Calculate Vedha severity based on obstructing planet
     */
    private fun calculateVedhaSeverity(obstructedPlanet: Planet, obstructingPlanet: Planet): VedhaSeverity {
        // Natural malefics cause stronger Vedha
        val malefics = listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU)

        return when {
            // Natural malefic obstructing a benefic
            obstructingPlanet in malefics &&
            obstructedPlanet in listOf(Planet.JUPITER, Planet.VENUS, Planet.MOON) ->
                VedhaSeverity.COMPLETE

            // Any planet obstructing Jupiter (most important for fortune)
            obstructedPlanet == Planet.JUPITER ->
                VedhaSeverity.STRONG

            // Malefic obstructing any planet
            obstructingPlanet in malefics ->
                VedhaSeverity.STRONG

            // Benefic obstructing
            obstructingPlanet in listOf(Planet.JUPITER, Planet.VENUS) ->
                VedhaSeverity.PARTIAL

            // Neutral situations
            else -> VedhaSeverity.MODERATE
        }
    }

    /**
     * Calculate effective transit strength after Vedha
     */
    private fun calculateEffectiveStrength(
        planet: Planet,
        houseFromMoon: Int,
        isNaturallyFavorable: Boolean,
        hasVedha: Boolean,
        severity: VedhaSeverity
    ): TransitEffectiveness {
        if (!isNaturallyFavorable) {
            return TransitEffectiveness.UNFAVORABLE
        }

        if (!hasVedha) {
            // No Vedha - check house quality
            val upachayaHouses = setOf(3, 6, 10, 11)
            val excellentHouses = setOf(2, 5, 9, 11)

            return when {
                houseFromMoon in upachayaHouses && houseFromMoon in excellentHouses ->
                    TransitEffectiveness.EXCELLENT

                houseFromMoon in upachayaHouses || houseFromMoon in excellentHouses ->
                    TransitEffectiveness.GOOD

                else -> TransitEffectiveness.MODERATE
            }
        }

        // Has Vedha - reduce effectiveness
        return when (severity) {
            VedhaSeverity.COMPLETE -> TransitEffectiveness.NULLIFIED
            VedhaSeverity.STRONG -> TransitEffectiveness.WEAK
            VedhaSeverity.MODERATE -> TransitEffectiveness.MODERATE
            VedhaSeverity.PARTIAL -> TransitEffectiveness.MODERATE
            VedhaSeverity.NONE -> TransitEffectiveness.GOOD
        }
    }

    /**
     * Get significations for transit house
     */
    private fun getTransitSignifications(planet: Planet, house: Int): List<String> {
        val houseSignifications = when (house) {
            1 -> listOf("Self", "Personality", "Physical body")
            2 -> listOf("Wealth", "Family", "Speech")
            3 -> listOf("Courage", "Siblings", "Short journeys")
            4 -> listOf("Home", "Mother", "Comfort")
            5 -> listOf("Children", "Intelligence", "Romance")
            6 -> listOf("Enemies", "Health issues", "Debts")
            7 -> listOf("Marriage", "Partnership", "Business")
            8 -> listOf("Obstacles", "Longevity", "Inheritance")
            9 -> listOf("Fortune", "Father", "Religion")
            10 -> listOf("Career", "Status", "Authority")
            11 -> listOf("Gains", "Friends", "Aspirations")
            12 -> listOf("Losses", "Expenses", "Liberation")
            else -> emptyList()
        }

        val planetSignifications = when (planet) {
            Planet.SUN -> listOf("Authority", "Government", "Vitality")
            Planet.MOON -> listOf("Mind", "Emotions", "Public")
            Planet.MARS -> listOf("Energy", "Competition", "Property")
            Planet.MERCURY -> listOf("Communication", "Trade", "Skills")
            Planet.JUPITER -> listOf("Wisdom", "Expansion", "Fortune")
            Planet.VENUS -> listOf("Love", "Luxury", "Arts")
            Planet.SATURN -> listOf("Discipline", "Karma", "Delays")
            Planet.RAHU -> listOf("Ambition", "Innovation", "Foreign")
            Planet.KETU -> listOf("Spirituality", "Detachment", "Past")
            else -> emptyList()
        }

        return houseSignifications + planetSignifications
    }

    /**
     * Generate interpretation for transit
     */
    private fun generateTransitInterpretation(
        planet: Planet,
        houseFromMoon: Int,
        isNaturallyFavorable: Boolean,
        hasVedha: Boolean,
        severity: VedhaSeverity,
        sources: List<Planet>
    ): String {
        val planetName = planet.displayName

        return buildString {
            append("$planetName transiting ${houseFromMoon.toOrdinal()} house from Moon: ")

            if (!isNaturallyFavorable) {
                append("This is a naturally challenging transit. ")
                append(getUnfavorableTransitEffect(planet, houseFromMoon))
                return@buildString
            }

            append("Naturally favorable for ${getFavorableAreas(planet, houseFromMoon)}. ")

            if (hasVedha) {
                val sourceNames = sources.joinToString(", ") { it.displayName }
                append("However, Vedha from $sourceNames causes ${severity.displayName.lowercase()}. ")

                when (severity) {
                    VedhaSeverity.COMPLETE ->
                        append("The positive effects are completely blocked. ")
                    VedhaSeverity.STRONG ->
                        append("Most benefits will not materialize. ")
                    VedhaSeverity.MODERATE ->
                        append("Results will be mixed and delayed. ")
                    VedhaSeverity.PARTIAL ->
                        append("Some benefits will still manifest. ")
                    else -> {}
                }

                append("Wait for Vedha to clear for full results.")
            } else {
                append("No Vedha obstruction - full positive effects expected.")
            }
        }
    }

    private fun Int.toOrdinal(): String {
        return when (this) {
            1 -> "1st"
            2 -> "2nd"
            3 -> "3rd"
            else -> "${this}th"
        }
    }

    private fun getFavorableAreas(planet: Planet, house: Int): String {
        return when (planet) {
            Planet.SUN -> when (house) {
                3 -> "courage and initiative"
                6 -> "victory over enemies"
                10 -> "career advancement"
                11 -> "gains and recognition"
                else -> "general progress"
            }
            Planet.JUPITER -> when (house) {
                2 -> "wealth and family prosperity"
                5 -> "children, education, and investments"
                7 -> "marriage and partnerships"
                9 -> "fortune and spiritual growth"
                11 -> "gains and wish fulfillment"
                else -> "expansion and blessings"
            }
            Planet.VENUS -> when (house) {
                1, 4 -> "comfort and pleasures"
                2, 11 -> "financial gains"
                5 -> "romance and creativity"
                else -> "happiness and harmony"
            }
            Planet.SATURN -> when (house) {
                3 -> "determination and resilience"
                6 -> "victory over obstacles"
                11 -> "steady gains through hard work"
                else -> "discipline and structure"
            }
            else -> "related matters"
        }
    }

    private fun getUnfavorableTransitEffect(planet: Planet, house: Int): String {
        return when {
            planet == Planet.SATURN && house == 8 ->
                "Challenges and obstacles in ${house.toOrdinal()} house matters. Health may need attention."
            planet == Planet.MARS && house == 8 ->
                "Potential for conflicts, accidents, or sudden events. Exercise caution."
            planet == Planet.RAHU && house == 8 ->
                "Confusion and unexpected developments. Avoid speculation."
            house == 12 ->
                "Increased expenses and potential losses. Focus on spiritual matters."
            house == 4 && planet == Planet.SATURN ->
                "Domestic pressures and emotional heaviness. Be patient with family."
            else ->
                "Navigate carefully through challenges in ${house.toOrdinal()} house matters."
        }
    }

    /**
     * Create Vedha interaction record
     */
    private fun createVedhaInteraction(
        obstructedPlanet: Planet,
        obstructedHouse: Int,
        obstructingPlanet: Planet,
        obstructingHouse: Int,
        severity: VedhaSeverity
    ): VedhaInteraction {
        val lostBenefits = when (obstructedPlanet) {
            Planet.JUPITER -> "Fortune, wisdom, expansion opportunities blocked"
            Planet.VENUS -> "Harmony, romance, financial gains reduced"
            Planet.MERCURY -> "Business success, communication benefits limited"
            Planet.SUN -> "Authority recognition, career advancement delayed"
            Planet.MOON -> "Peace of mind, public support diminished"
            Planet.MARS -> "Victory, courage benefits obstructed"
            Planet.SATURN -> "Long-term gains, stability compromised"
            else -> "Positive effects reduced"
        }

        val interpretation =
            "${obstructedPlanet.displayName}'s favorable ${obstructedHouse.toOrdinal()} house transit " +
            "is being obstructed by ${obstructingPlanet.displayName} in ${obstructingHouse.toOrdinal()} house. " +
            "This ${severity.displayName.lowercase()} reduces the expected benefits."

        return VedhaInteraction(
            obstructedPlanet = obstructedPlanet,
            obstructedHouse = obstructedHouse,
            obstructingPlanet = obstructingPlanet,
            obstructingHouse = obstructingHouse,
            severity = severity,
            lostBenefits = lostBenefits,
            interpretation = interpretation
        )
    }

    /**
     * Calculate overall transit score
     */
    private fun calculateOverallScore(transits: List<PlanetTransitVedha>): Int {
        if (transits.isEmpty()) return 50

        val weights = mapOf(
            Planet.JUPITER to 3.0,  // Most important for fortune
            Planet.SATURN to 2.5,   // Important for karma
            Planet.SUN to 2.0,
            Planet.MOON to 2.0,
            Planet.VENUS to 1.5,
            Planet.MARS to 1.5,
            Planet.MERCURY to 1.5,
            Planet.RAHU to 1.0,
            Planet.KETU to 1.0
        )

        var totalWeightedScore = 0.0
        var totalWeight = 0.0

        for (transit in transits) {
            val weight = weights[transit.planet] ?: 1.0
            val score = transit.effectiveStrength.score * 20 // Convert to 0-100

            totalWeightedScore += score * weight
            totalWeight += weight
        }

        return (totalWeightedScore / totalWeight).toInt().coerceIn(0, 100)
    }

    /**
     * Generate key insights
     */
    private fun generateKeyInsights(
        transits: List<PlanetTransitVedha>,
        vedhas: List<VedhaInteraction>
    ): List<String> {
        val insights = mutableListOf<String>()

        // Check Jupiter status (most important)
        val jupiterTransit = transits.find { it.planet == Planet.JUPITER }
        jupiterTransit?.let {
            if (it.hasVedha) {
                insights.add("Jupiter's blessings are currently obstructed - major fortune delayed")
            } else if (it.isNaturallyFavorable) {
                insights.add("Jupiter favorably placed - expansion and wisdom available")
            } else {
                // Jupiter neutral
            }
        }

        // Check Saturn status
        val saturnTransit = transits.find { it.planet == Planet.SATURN }
        saturnTransit?.let {
            if (!it.isNaturallyFavorable) {
                insights.add("Saturn's challenging transit requires patience and discipline")
            } else if (!it.hasVedha) {
                insights.add("Saturn supporting through steady effort - rewards for hard work")
            } else {
                // Saturn obstructed
            }
        }

        // Count Vedhas
        val vedhaCount = vedhas.size
        when {
            vedhaCount >= 4 -> insights.add("Multiple Vedhas active - many expected results may not materialize")
            vedhaCount >= 2 -> insights.add("Some planetary benefits obstructed - manage expectations")
            vedhaCount == 0 -> insights.add("No active Vedhas - transits can deliver their full potential")
        }

        // Check benefic-malefic balance
        val favorableCount = transits.count { it.effectiveStrength.score >= 3 }
        val unfavorableCount = transits.count { it.effectiveStrength.score <= 1 }

        when {
            favorableCount > unfavorableCount + 2 ->
                insights.add("Overall transit picture is favorable - positive period ahead")
            unfavorableCount > favorableCount + 2 ->
                insights.add("Challenging transit period - focus on essential matters")
            else ->
                insights.add("Mixed transit influences - selective approach recommended")
        }

        return insights
    }

    /**
     * Generate recommendations
     */
    private fun generateRecommendations(
        transits: List<PlanetTransitVedha>,
        vedhas: List<VedhaInteraction>
    ): List<String> {
        val recommendations = mutableListOf<String>()

        // Recommendations based on obstructed planets
        for (vedha in vedhas) {
            when (vedha.obstructedPlanet) {
                Planet.JUPITER ->
                    recommendations.add("Recite Guru mantra to mitigate Jupiter Vedha effects")
                Planet.SATURN ->
                    recommendations.add("Saturn Vedha: Hanuman Chalisa on Saturdays recommended")
                Planet.VENUS ->
                    recommendations.add("Venus Vedha: White items donation on Fridays beneficial")
                else -> {}
            }
        }

        // General recommendations based on overall situation
        val vedhaCount = vedhas.size
        when {
            vedhaCount >= 3 -> {
                recommendations.add("Perform Navagraha Shanti puja to harmonize planetary energies")
                recommendations.add("Avoid starting major new ventures during intense Vedha periods")
            }
            vedhaCount > 0 -> {
                recommendations.add("Be patient with delays in ${vedhas.map { it.obstructedPlanet.displayName }.joinToString(", ")} related matters")
            }
            else -> {
                recommendations.add("Favorable time - make the most of unobstructed planetary transits")
            }
        }

        // Activity timing recommendation
        val bestTransit = transits.maxByOrNull { it.effectiveStrength.score }
        bestTransit?.let {
            if (it.effectiveStrength.score >= 4) {
                recommendations.add("Best time for ${it.planet.displayName} related activities - ${getFavorableAreas(it.planet, it.houseFromMoon)}")
            }
        }

        return recommendations.distinct()
    }

    /**
     * Calculate Vedha for current moment using Swiss Ephemeris
     */
    fun calculateCurrentVedha(
        context: Context,
        chart: VedicChart,
        dateTime: LocalDateTime = LocalDateTime.now()
    ): CompleteVedhaAnalysis? {
        // Calculate current transit positions
        val engineInstance = SwissEphemerisEngine.getInstance(context) ?: return null

        val transitPositions = mutableListOf<PlanetPosition>()
        val gocharaPlanets = listOf(
            Planet.SUN, Planet.MOON, Planet.MARS, Planet.MERCURY,
            Planet.JUPITER, Planet.VENUS, Planet.SATURN, Planet.RAHU, Planet.KETU
        )

        for (planet in gocharaPlanets) {
            try {
                val planetPos = engineInstance.calculatePlanetPosition(
                    planet, dateTime, chart.birthData.timezone,
                    chart.birthData.latitude, chart.birthData.longitude
                )
                transitPositions.add(planetPos)
            } catch (e: Exception) {
                // Skip if calculation fails
            }
        }

        return calculateVedhaAnalysis(chart, transitPositions, dateTime.toLocalDate())
    }

    /**
     * Generate plain text report
     */
    fun generateReport(analysis: CompleteVedhaAnalysis, language: Language = Language.ENGLISH): String {
        return buildString {
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine("           GOCHARA VEDHA ANALYSIS REPORT")
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine()
            appendLine("Natal Moon Sign: ${analysis.natalMoonSign.displayName}")
            appendLine("Analysis Date: ${analysis.analysisDate}")
            appendLine()
            appendLine("Overall Transit Score: ${analysis.overallTransitScore}%")
            appendLine("Favorable Transits: ${analysis.favorableAspects}")
            appendLine("Obstructed Transits: ${analysis.obstructedAspects}")
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("PLANETARY TRANSITS")
            appendLine("─────────────────────────────────────────────────────────")

            analysis.planetTransits.forEach { transit ->
                appendLine()
                val vedhaIndicator = if (transit.hasVedha) " [VEDHA]" else ""
                appendLine("${transit.planet.displayName}$vedhaIndicator")
                appendLine("  Sign: ${transit.transitSign.displayName}")
                appendLine("  House from Moon: ${transit.houseFromMoon}")
                appendLine("  Natural Status: ${if (transit.isNaturallyFavorable) "Favorable" else "Unfavorable"}")
                appendLine("  Effective Strength: ${transit.effectiveStrength.displayName}")
                if (transit.hasVedha) {
                    appendLine("  Vedha Severity: ${transit.vedhaSeverity.displayName}")
                    appendLine("  Obstructed by: ${transit.vedhaSourcePlanets.joinToString { it.displayName }}")
                }
                appendLine("  Interpretation: ${transit.interpretation}")
            }

            if (analysis.activeVedhas.isNotEmpty()) {
                appendLine()
                appendLine("─────────────────────────────────────────────────────────")
                appendLine("ACTIVE VEDHA INTERACTIONS")
                appendLine("─────────────────────────────────────────────────────────")
                analysis.activeVedhas.forEach { vedha ->
                    appendLine()
                    appendLine("${vedha.obstructedPlanet.displayName} (${vedha.obstructedHouse}) ← ${vedha.obstructingPlanet.displayName} (${vedha.obstructingHouse})")
                    appendLine("  Severity: ${vedha.severity.displayName}")
                    appendLine("  Lost Benefits: ${vedha.lostBenefits}")
                }
            }

            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("KEY INSIGHTS")
            appendLine("─────────────────────────────────────────────────────────")
            analysis.keyInsights.forEach { insight ->
                appendLine("• $insight")
            }

            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("RECOMMENDATIONS")
            appendLine("─────────────────────────────────────────────────────────")
            analysis.recommendations.forEach { rec ->
                appendLine("• $rec")
            }
        }
    }
}
