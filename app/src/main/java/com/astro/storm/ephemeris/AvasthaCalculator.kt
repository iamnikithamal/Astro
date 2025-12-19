package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.data.model.Nakshatra
import kotlin.math.abs

/**
 * Avastha Calculator - Planetary States/Conditions
 *
 * Calculates the various Avasthas (states) of planets that indicate how
 * effectively a planet can deliver its results. There are multiple
 * classification systems for Avasthas in Vedic astrology.
 *
 * This calculator implements:
 * 1. Baladi Avastha (Age-based states): Bala, Kumara, Yuva, Vriddha, Mrita
 * 2. Jagradadi Avastha (Alertness states): Jagrat, Swapna, Sushupti
 * 3. Deeptadi Avastha (Dignity-based states): Deepta, Swastha, Mudita, etc.
 * 4. Lajjitadi Avastha (Emotional states): Lajjita, Garvita, Kshudita, etc.
 *
 * References:
 * - Brihat Parashara Hora Shastra (Chapters on Avasthas)
 * - Saravali by Kalyana Varma
 * - Phaladeepika by Mantreswara
 */
object AvasthaCalculator {

    /**
     * Main analysis entry point
     */
    fun analyzeAvasthas(chart: VedicChart): AvasthaAnalysis {
        val planetaryAvasthas = mutableListOf<PlanetaryAvastha>()

        // Analyze each planet (excluding Rahu/Ketu for some avasthas)
        val planetsToAnalyze = listOf(
            Planet.SUN, Planet.MOON, Planet.MARS, Planet.MERCURY,
            Planet.JUPITER, Planet.VENUS, Planet.SATURN
        )

        for (planet in planetsToAnalyze) {
            val position = chart.planetPositions.find { it.planet == planet } ?: continue
            val avastha = analyzePlanetAvastha(planet, position, chart)
            planetaryAvasthas.add(avastha)
        }

        // Optionally analyze nodes
        chart.planetPositions.find { it.planet == Planet.RAHU }?.let { rahuPos ->
            planetaryAvasthas.add(analyzeNodeAvastha(Planet.RAHU, rahuPos, chart))
        }
        chart.planetPositions.find { it.planet == Planet.KETU }?.let { ketuPos ->
            planetaryAvasthas.add(analyzeNodeAvastha(Planet.KETU, ketuPos, chart))
        }

        val overallStrength = calculateOverallStrength(planetaryAvasthas)
        val interpretation = generateOverallInterpretation(planetaryAvasthas)
        val recommendations = generateRecommendations(planetaryAvasthas)

        return AvasthaAnalysis(
            planetaryAvasthas = planetaryAvasthas,
            overallStrength = overallStrength,
            interpretation = interpretation,
            recommendations = recommendations,
            strongestPlanet = planetaryAvasthas.maxByOrNull { it.effectiveStrength },
            weakestPlanet = planetaryAvasthas.minByOrNull { it.effectiveStrength }
        )
    }

    /**
     * Analyze all avasthas for a single planet
     */
    private fun analyzePlanetAvastha(
        planet: Planet,
        position: PlanetPosition,
        chart: VedicChart
    ): PlanetaryAvastha {
        val sign = ZodiacSign.fromLongitude(position.longitude)
        val degree = position.longitude % 30

        // Calculate each type of avastha
        val baladiAvastha = calculateBaladiAvastha(planet, sign, degree)
        val jagradadiAvastha = calculateJagradadiAvastha(planet, sign)
        val deeptadiAvastha = calculateDeeptadiAvastha(planet, position, chart)
        val lajjitadiAvastha = calculateLajjitadiAvastha(planet, position, chart)

        // Calculate effective strength based on all avasthas
        val effectiveStrength = calculateEffectiveStrength(
            baladiAvastha, jagradadiAvastha, deeptadiAvastha, lajjitadiAvastha
        )

        val interpretation = generatePlanetInterpretation(
            planet, baladiAvastha, jagradadiAvastha, deeptadiAvastha, lajjitadiAvastha, effectiveStrength
        )

        return PlanetaryAvastha(
            planet = planet,
            position = position,
            sign = sign,
            degree = degree,
            baladiAvastha = baladiAvastha,
            jagradadiAvastha = jagradadiAvastha,
            deeptadiAvastha = deeptadiAvastha,
            lajjitadiAvastha = lajjitadiAvastha,
            effectiveStrength = effectiveStrength,
            interpretation = interpretation
        )
    }

    /**
     * Analyze nodes (Rahu/Ketu) - simplified analysis
     */
    private fun analyzeNodeAvastha(
        planet: Planet,
        position: PlanetPosition,
        chart: VedicChart
    ): PlanetaryAvastha {
        val sign = ZodiacSign.fromLongitude(position.longitude)
        val degree = position.longitude % 30

        // Nodes use simplified avasthas
        val deeptadiAvastha = calculateNodeDeeptadiAvastha(planet, position, chart)

        val effectiveStrength = when (deeptadiAvastha) {
            DeeptadiAvastha.DEEPTA, DeeptadiAvastha.SWASTHA -> 80
            DeeptadiAvastha.MUDITA, DeeptadiAvastha.SHANTA -> 60
            DeeptadiAvastha.DINA, DeeptadiAvastha.VIKALA -> 40
            DeeptadiAvastha.KHALA, DeeptadiAvastha.KOPA -> 30
            DeeptadiAvastha.BHITA -> 20
        }

        return PlanetaryAvastha(
            planet = planet,
            position = position,
            sign = sign,
            degree = degree,
            baladiAvastha = BaladiAvastha.YUVA, // Default for nodes
            jagradadiAvastha = JagradadiAvastha.JAGRAT, // Default for nodes
            deeptadiAvastha = deeptadiAvastha,
            lajjitadiAvastha = LajjitadiAvastha.GARVITA, // Default for nodes
            effectiveStrength = effectiveStrength,
            interpretation = "Node position in ${sign.displayName}. ${deeptadiAvastha.description}"
        )
    }

    /**
     * Calculate Baladi Avastha (Age-based states)
     *
     * Based on the degree of the planet within the sign:
     * - Odd signs: Bala (0-6°), Kumara (6-12°), Yuva (12-18°), Vriddha (18-24°), Mrita (24-30°)
     * - Even signs: Mrita (0-6°), Vriddha (6-12°), Yuva (12-18°), Kumara (18-24°), Bala (24-30°)
     */
    private fun calculateBaladiAvastha(planet: Planet, sign: ZodiacSign, degree: Double): BaladiAvastha {
        val isOddSign = sign.ordinal % 2 == 0 // Aries=0 is odd (first sign)

        val segment = (degree / 6).toInt().coerceIn(0, 4)

        return if (isOddSign) {
            when (segment) {
                0 -> BaladiAvastha.BALA
                1 -> BaladiAvastha.KUMARA
                2 -> BaladiAvastha.YUVA
                3 -> BaladiAvastha.VRIDDHA
                4 -> BaladiAvastha.MRITA
                else -> BaladiAvastha.YUVA
            }
        } else {
            when (segment) {
                0 -> BaladiAvastha.MRITA
                1 -> BaladiAvastha.VRIDDHA
                2 -> BaladiAvastha.YUVA
                3 -> BaladiAvastha.KUMARA
                4 -> BaladiAvastha.BALA
                else -> BaladiAvastha.YUVA
            }
        }
    }

    /**
     * Calculate Jagradadi Avastha (Alertness states)
     *
     * Based on the relationship between planet and sign lord:
     * - Jagrat (Awake): Planet in own sign or exaltation
     * - Swapna (Dreaming): Planet in friendly sign
     * - Sushupti (Deep Sleep): Planet in enemy sign or debilitation
     */
    private fun calculateJagradadiAvastha(planet: Planet, sign: ZodiacSign): JagradadiAvastha {
        val signLord = sign.ruler

        // Check if in own sign
        if (isOwnSign(planet, sign)) {
            return JagradadiAvastha.JAGRAT
        }

        // Check if exalted
        if (isExalted(planet, sign)) {
            return JagradadiAvastha.JAGRAT
        }

        // Check if debilitated
        if (isDebilitated(planet, sign)) {
            return JagradadiAvastha.SUSHUPTI
        }

        // Check friendship
        return when (getPlanetaryRelationship(planet, signLord)) {
            PlanetaryRelationship.GREAT_FRIEND, PlanetaryRelationship.FRIEND -> JagradadiAvastha.JAGRAT
            PlanetaryRelationship.NEUTRAL -> JagradadiAvastha.SWAPNA
            PlanetaryRelationship.ENEMY, PlanetaryRelationship.GREAT_ENEMY -> JagradadiAvastha.SUSHUPTI
        }
    }

    /**
     * Calculate Deeptadi Avastha (Dignity-based states)
     *
     * Nine states based on various factors:
     * 1. Deepta - Exalted
     * 2. Swastha - Own sign
     * 3. Mudita - Friend's sign
     * 4. Shanta - In benefic vargas
     * 5. Dina - In neutral sign
     * 6. Vikala - Combust
     * 7. Khala - Debilitated
     * 8. Kopa - In enemy sign
     * 9. Bhita - In great enemy sign
     */
    private fun calculateDeeptadiAvastha(
        planet: Planet,
        position: PlanetPosition,
        chart: VedicChart
    ): DeeptadiAvastha {
        val sign = ZodiacSign.fromLongitude(position.longitude)
        val signLord = sign.ruler

        // Check combustion first (overrides other states)
        if (isCombust(planet, position, chart)) {
            return DeeptadiAvastha.VIKALA
        }

        // Check exaltation
        if (isExalted(planet, sign)) {
            return DeeptadiAvastha.DEEPTA
        }

        // Check debilitation
        if (isDebilitated(planet, sign)) {
            return DeeptadiAvastha.KHALA
        }

        // Check own sign
        if (isOwnSign(planet, sign)) {
            return DeeptadiAvastha.SWASTHA
        }

        // Check friendship with sign lord
        return when (getPlanetaryRelationship(planet, signLord)) {
            PlanetaryRelationship.GREAT_FRIEND -> DeeptadiAvastha.MUDITA
            PlanetaryRelationship.FRIEND -> DeeptadiAvastha.SHANTA
            PlanetaryRelationship.NEUTRAL -> DeeptadiAvastha.DINA
            PlanetaryRelationship.ENEMY -> DeeptadiAvastha.KOPA
            PlanetaryRelationship.GREAT_ENEMY -> DeeptadiAvastha.BHITA
        }
    }

    /**
     * Calculate simplified Deeptadi for nodes
     */
    private fun calculateNodeDeeptadiAvastha(
        planet: Planet,
        position: PlanetPosition,
        chart: VedicChart
    ): DeeptadiAvastha {
        val sign = ZodiacSign.fromLongitude(position.longitude)

        // Rahu exalted in Taurus/Gemini, Ketu exalted in Scorpio/Sagittarius (varies by tradition)
        return when (planet) {
            Planet.RAHU -> {
                when (sign) {
                    ZodiacSign.TAURUS, ZodiacSign.GEMINI -> DeeptadiAvastha.DEEPTA
                    ZodiacSign.SCORPIO, ZodiacSign.SAGITTARIUS -> DeeptadiAvastha.KHALA
                    ZodiacSign.VIRGO, ZodiacSign.AQUARIUS -> DeeptadiAvastha.MUDITA
                    else -> DeeptadiAvastha.DINA
                }
            }
            Planet.KETU -> {
                when (sign) {
                    ZodiacSign.SCORPIO, ZodiacSign.SAGITTARIUS -> DeeptadiAvastha.DEEPTA
                    ZodiacSign.TAURUS, ZodiacSign.GEMINI -> DeeptadiAvastha.KHALA
                    ZodiacSign.PISCES -> DeeptadiAvastha.MUDITA
                    else -> DeeptadiAvastha.DINA
                }
            }
            else -> DeeptadiAvastha.DINA
        }
    }

    /**
     * Calculate Lajjitadi Avastha (Emotional states)
     *
     * Based on conjunctions and aspects:
     * 1. Lajjita - Ashamed (conjunct Sun/Saturn in 5th)
     * 2. Garvita - Proud (in exaltation or moolatrikona)
     * 3. Kshudita - Hungry (in enemy sign, aspected by enemy)
     * 4. Trushita - Thirsty (in watery sign, aspected by enemy)
     * 5. Mudita - Delighted (conjunct or aspected by friend)
     * 6. Kshobhita - Agitated (conjunct Sun, aspected by malefic)
     */
    private fun calculateLajjitadiAvastha(
        planet: Planet,
        position: PlanetPosition,
        chart: VedicChart
    ): LajjitadiAvastha {
        val sign = ZodiacSign.fromLongitude(position.longitude)
        val house = position.house

        // Check for Garvita (proud) - in exaltation or moolatrikona
        if (isExalted(planet, sign) || isMoolatrikona(planet, sign, position.longitude)) {
            return LajjitadiAvastha.GARVITA
        }

        // Check for Lajjita (ashamed) - in 5th house with Sun or Saturn
        if (house == 5) {
            val hasSunOrSaturn = chart.planetPositions.any {
                it.planet != planet &&
                (it.planet == Planet.SUN || it.planet == Planet.SATURN) &&
                it.house == 5
            }
            if (hasSunOrSaturn) {
                return LajjitadiAvastha.LAJJITA
            }
        }

        // Check for Kshobhita (agitated) - conjunct Sun and aspected by malefic
        val isWithSun = chart.planetPositions.any {
            it.planet == Planet.SUN &&
            abs(it.longitude - position.longitude) < 10
        }
        if (isWithSun && planet != Planet.SUN) {
            val hasmaleficAspect = hasAspectFrom(position, chart,
                listOf(Planet.MARS, Planet.SATURN, Planet.RAHU, Planet.KETU))
            if (hasmaleficAspect) {
                return LajjitadiAvastha.KSHOBHITA
            }
        }

        // Check for Trushita (thirsty) - in watery sign, aspected by enemy
        val waterySigns = listOf(ZodiacSign.CANCER, ZodiacSign.SCORPIO, ZodiacSign.PISCES)
        if (sign in waterySigns) {
            // Simplified: if in enemy sign and watery
            val signLord = sign.ruler
            if (getPlanetaryRelationship(planet, signLord) in
                listOf(PlanetaryRelationship.ENEMY, PlanetaryRelationship.GREAT_ENEMY)) {
                return LajjitadiAvastha.TRUSHITA
            }
        }

        // Check for Kshudita (hungry) - in enemy sign, aspected by enemy
        val signLord = sign.ruler
        if (getPlanetaryRelationship(planet, signLord) in
            listOf(PlanetaryRelationship.ENEMY, PlanetaryRelationship.GREAT_ENEMY)) {
            return LajjitadiAvastha.KSHUDITA
        }

        // Check for Mudita (delighted) - conjunct or aspected by friend
        val hasFriendlyInfluence = chart.planetPositions.any { other ->
            other.planet != planet &&
            getPlanetaryRelationship(planet, other.planet) in
            listOf(PlanetaryRelationship.FRIEND, PlanetaryRelationship.GREAT_FRIEND) &&
            (abs(other.longitude - position.longitude) < 10 || // Conjunction
             isAspecting(other, position)) // Aspect
        }
        if (hasFriendlyInfluence) {
            return LajjitadiAvastha.MUDITA
        }

        // Default to neutral state
        return LajjitadiAvastha.MUDITA
    }

    /**
     * Calculate effective strength based on all avasthas
     */
    private fun calculateEffectiveStrength(
        baladi: BaladiAvastha,
        jagradadi: JagradadiAvastha,
        deeptadi: DeeptadiAvastha,
        lajjitadi: LajjitadiAvastha
    ): Int {
        var strength = 50 // Base

        // Baladi contribution (20 points max)
        strength += when (baladi) {
            BaladiAvastha.YUVA -> 20
            BaladiAvastha.KUMARA -> 15
            BaladiAvastha.BALA -> 10
            BaladiAvastha.VRIDDHA -> 5
            BaladiAvastha.MRITA -> 0
        }

        // Jagradadi contribution (20 points max)
        strength += when (jagradadi) {
            JagradadiAvastha.JAGRAT -> 20
            JagradadiAvastha.SWAPNA -> 10
            JagradadiAvastha.SUSHUPTI -> 0
        }

        // Deeptadi contribution (30 points max)
        strength += when (deeptadi) {
            DeeptadiAvastha.DEEPTA -> 30
            DeeptadiAvastha.SWASTHA -> 25
            DeeptadiAvastha.MUDITA -> 20
            DeeptadiAvastha.SHANTA -> 15
            DeeptadiAvastha.DINA -> 10
            DeeptadiAvastha.VIKALA -> 5
            DeeptadiAvastha.KHALA -> 3
            DeeptadiAvastha.KOPA -> 2
            DeeptadiAvastha.BHITA -> 0
        }

        // Lajjitadi contribution (20 points max)
        strength += when (lajjitadi) {
            LajjitadiAvastha.GARVITA -> 20
            LajjitadiAvastha.MUDITA -> 15
            LajjitadiAvastha.LAJJITA -> 5
            LajjitadiAvastha.KSHUDITA -> 3
            LajjitadiAvastha.TRUSHITA -> 3
            LajjitadiAvastha.KSHOBHITA -> 0
        }

        return strength.coerceIn(0, 100)
    }

    /**
     * Generate interpretation for a planet
     */
    private fun generatePlanetInterpretation(
        planet: Planet,
        baladi: BaladiAvastha,
        jagradadi: JagradadiAvastha,
        deeptadi: DeeptadiAvastha,
        lajjitadi: LajjitadiAvastha,
        strength: Int
    ): String {
        return buildString {
            append("${planet.displayName} Analysis:\n\n")

            append("• Age State (Baladi): ${baladi.displayName}\n")
            append("  ${baladi.description}\n\n")

            append("• Alertness (Jagradadi): ${jagradadi.displayName}\n")
            append("  ${jagradadi.description}\n\n")

            append("• Dignity (Deeptadi): ${deeptadi.displayName}\n")
            append("  ${deeptadi.description}\n\n")

            append("• Emotional (Lajjitadi): ${lajjitadi.displayName}\n")
            append("  ${lajjitadi.description}\n\n")

            append("Overall Strength: $strength%\n")
            append(when {
                strength >= 80 -> "This planet is very powerful and will deliver excellent results."
                strength >= 60 -> "This planet is strong and will give good results."
                strength >= 40 -> "This planet has moderate strength with mixed results."
                strength >= 20 -> "This planet is weak and may struggle to give full results."
                else -> "This planet is very weak and needs remedial measures."
            })
        }
    }

    /**
     * Calculate overall strength of all planets
     */
    private fun calculateOverallStrength(avasthas: List<PlanetaryAvastha>): Int {
        if (avasthas.isEmpty()) return 0
        return avasthas.sumOf { it.effectiveStrength } / avasthas.size
    }

    /**
     * Generate overall interpretation
     */
    private fun generateOverallInterpretation(avasthas: List<PlanetaryAvastha>): String {
        val strong = avasthas.filter { it.effectiveStrength >= 60 }
        val weak = avasthas.filter { it.effectiveStrength < 40 }

        return buildString {
            if (strong.isNotEmpty()) {
                append("Strong Planets: ${strong.joinToString(", ") { it.planet.displayName }}\n")
                append("These planets are well-placed and will give favorable results.\n\n")
            }
            if (weak.isNotEmpty()) {
                append("Weak Planets: ${weak.joinToString(", ") { it.planet.displayName }}\n")
                append("These planets may need remedial measures to strengthen their significations.\n\n")
            }
            if (strong.isEmpty() && weak.isEmpty()) {
                append("Most planets are in moderate states, giving mixed results based on their periods.\n")
            }
        }
    }

    /**
     * Generate recommendations based on planetary states
     */
    private fun generateRecommendations(avasthas: List<PlanetaryAvastha>): List<AvasthaRecommendation> {
        val recommendations = mutableListOf<AvasthaRecommendation>()

        for (avastha in avasthas.sortedBy { it.effectiveStrength }.take(3)) {
            if (avastha.effectiveStrength < 50) {
                recommendations.add(AvasthaRecommendation(
                    planet = avastha.planet,
                    issue = "Low strength (${avastha.effectiveStrength}%)",
                    remedy = "Strengthen ${avastha.planet.displayName} through gemstones, mantras, or charitable acts",
                    priority = if (avastha.effectiveStrength < 30) RemedyPriority.HIGH else RemedyPriority.MEDIUM
                ))
            }
        }

        return recommendations
    }

    // Helper functions

    private fun isOwnSign(planet: Planet, sign: ZodiacSign): Boolean {
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.LEO
            Planet.MOON -> sign == ZodiacSign.CANCER
            Planet.MARS -> sign in listOf(ZodiacSign.ARIES, ZodiacSign.SCORPIO)
            Planet.MERCURY -> sign in listOf(ZodiacSign.GEMINI, ZodiacSign.VIRGO)
            Planet.JUPITER -> sign in listOf(ZodiacSign.SAGITTARIUS, ZodiacSign.PISCES)
            Planet.VENUS -> sign in listOf(ZodiacSign.TAURUS, ZodiacSign.LIBRA)
            Planet.SATURN -> sign in listOf(ZodiacSign.CAPRICORN, ZodiacSign.AQUARIUS)
            else -> false
        }
    }

    private fun isExalted(planet: Planet, sign: ZodiacSign): Boolean {
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.ARIES
            Planet.MOON -> sign == ZodiacSign.TAURUS
            Planet.MARS -> sign == ZodiacSign.CAPRICORN
            Planet.MERCURY -> sign == ZodiacSign.VIRGO
            Planet.JUPITER -> sign == ZodiacSign.CANCER
            Planet.VENUS -> sign == ZodiacSign.PISCES
            Planet.SATURN -> sign == ZodiacSign.LIBRA
            else -> false
        }
    }

    private fun isDebilitated(planet: Planet, sign: ZodiacSign): Boolean {
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.LIBRA
            Planet.MOON -> sign == ZodiacSign.SCORPIO
            Planet.MARS -> sign == ZodiacSign.CANCER
            Planet.MERCURY -> sign == ZodiacSign.PISCES
            Planet.JUPITER -> sign == ZodiacSign.CAPRICORN
            Planet.VENUS -> sign == ZodiacSign.VIRGO
            Planet.SATURN -> sign == ZodiacSign.ARIES
            else -> false
        }
    }

    private fun isMoolatrikona(planet: Planet, sign: ZodiacSign, longitude: Double): Boolean {
        val degree = longitude % 30
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.LEO && degree in 0.0..20.0
            Planet.MOON -> sign == ZodiacSign.TAURUS && degree in 3.0..30.0
            Planet.MARS -> sign == ZodiacSign.ARIES && degree in 0.0..12.0
            Planet.MERCURY -> sign == ZodiacSign.VIRGO && degree in 15.0..20.0
            Planet.JUPITER -> sign == ZodiacSign.SAGITTARIUS && degree in 0.0..10.0
            Planet.VENUS -> sign == ZodiacSign.LIBRA && degree in 0.0..15.0
            Planet.SATURN -> sign == ZodiacSign.AQUARIUS && degree in 0.0..20.0
            else -> false
        }
    }

    private fun isCombust(planet: Planet, position: PlanetPosition, chart: VedicChart): Boolean {
        if (planet == Planet.SUN) return false

        val sunPosition = chart.planetPositions.find { it.planet == Planet.SUN } ?: return false
        val separation = abs(position.longitude - sunPosition.longitude)
        val normalizedSeparation = if (separation > 180) 360 - separation else separation

        val combustionOrb = when (planet) {
            Planet.MOON -> 12.0
            Planet.MARS -> 17.0
            Planet.MERCURY -> 14.0
            Planet.JUPITER -> 11.0
            Planet.VENUS -> 10.0
            Planet.SATURN -> 15.0
            else -> 10.0
        }

        return normalizedSeparation <= combustionOrb
    }

    private fun getPlanetaryRelationship(planet1: Planet, planet2: Planet): PlanetaryRelationship {
        if (planet1 == planet2) return PlanetaryRelationship.FRIEND

        val friendships = mapOf(
            Planet.SUN to Pair(
                listOf(Planet.MOON, Planet.MARS, Planet.JUPITER),
                listOf(Planet.VENUS, Planet.SATURN)
            ),
            Planet.MOON to Pair(
                listOf(Planet.SUN, Planet.MERCURY),
                emptyList<Planet>()
            ),
            Planet.MARS to Pair(
                listOf(Planet.SUN, Planet.MOON, Planet.JUPITER),
                listOf(Planet.MERCURY)
            ),
            Planet.MERCURY to Pair(
                listOf(Planet.SUN, Planet.VENUS),
                listOf(Planet.MOON)
            ),
            Planet.JUPITER to Pair(
                listOf(Planet.SUN, Planet.MOON, Planet.MARS),
                listOf(Planet.MERCURY, Planet.VENUS)
            ),
            Planet.VENUS to Pair(
                listOf(Planet.MERCURY, Planet.SATURN),
                listOf(Planet.SUN, Planet.MOON)
            ),
            Planet.SATURN to Pair(
                listOf(Planet.MERCURY, Planet.VENUS),
                listOf(Planet.SUN, Planet.MOON, Planet.MARS)
            )
        )

        val (friends, enemies) = friendships[planet1] ?: return PlanetaryRelationship.NEUTRAL

        return when (planet2) {
            in friends -> PlanetaryRelationship.FRIEND
            in enemies -> PlanetaryRelationship.ENEMY
            else -> PlanetaryRelationship.NEUTRAL
        }
    }

    private fun hasAspectFrom(
        position: PlanetPosition,
        chart: VedicChart,
        planets: List<Planet>
    ): Boolean {
        return chart.planetPositions.any { other ->
            other.planet in planets && isAspecting(other, position)
        }
    }

    private fun isAspecting(from: PlanetPosition, to: PlanetPosition): Boolean {
        val houseDiff = (to.house - from.house + 12) % 12

        // All planets aspect 7th house
        if (houseDiff == 6) return true

        // Special aspects
        return when (from.planet) {
            Planet.MARS -> houseDiff in listOf(3, 6, 7)
            Planet.JUPITER -> houseDiff in listOf(4, 6, 8)
            Planet.SATURN -> houseDiff in listOf(2, 6, 9)
            else -> false
        }
    }

    // ============================================
    // DATA CLASSES
    // ============================================

    data class AvasthaAnalysis(
        val planetaryAvasthas: List<PlanetaryAvastha>,
        val overallStrength: Int,
        val interpretation: String,
        val recommendations: List<AvasthaRecommendation>,
        val strongestPlanet: PlanetaryAvastha?,
        val weakestPlanet: PlanetaryAvastha?
    )

    data class PlanetaryAvastha(
        val planet: Planet,
        val position: PlanetPosition,
        val sign: ZodiacSign,
        val degree: Double,
        val baladiAvastha: BaladiAvastha,
        val jagradadiAvastha: JagradadiAvastha,
        val deeptadiAvastha: DeeptadiAvastha,
        val lajjitadiAvastha: LajjitadiAvastha,
        val effectiveStrength: Int,
        val interpretation: String
    )

    data class AvasthaRecommendation(
        val planet: Planet,
        val issue: String,
        val remedy: String,
        val priority: RemedyPriority
    )

    // ============================================
    // ENUMS
    // ============================================

    enum class BaladiAvastha(
        val displayName: String,
        val description: String,
        val resultPercentage: Int
    ) {
        BALA(
            "Bala (Infant)",
            "Planet is in infant state - like a child, it gives results slowly and with help from others. Results manifest in the later part of life.",
            20
        ),
        KUMARA(
            "Kumara (Adolescent)",
            "Planet is in adolescent state - eager and active but not fully mature. Results come with some effort and learning.",
            50
        ),
        YUVA(
            "Yuva (Young Adult)",
            "Planet is in prime youthful state - most powerful and capable of giving full results. Best time for manifestation.",
            100
        ),
        VRIDDHA(
            "Vriddha (Old)",
            "Planet is in aged state - wisdom but declining vitality. Results come through experience and patience.",
            30
        ),
        MRITA(
            "Mrita (Dead)",
            "Planet is in dead state - minimal ability to give results. May need strong remedial measures.",
            10
        )
    }

    enum class JagradadiAvastha(
        val displayName: String,
        val description: String,
        val resultPercentage: Int
    ) {
        JAGRAT(
            "Jagrat (Awake)",
            "Planet is fully awake and alert - gives complete and timely results. Functions at full capacity.",
            100
        ),
        SWAPNA(
            "Swapna (Dreaming)",
            "Planet is in dreaming state - gives partial results, often delayed or through indirect means.",
            50
        ),
        SUSHUPTI(
            "Sushupti (Deep Sleep)",
            "Planet is in deep sleep - struggles to manifest results. May give minimal or no results without remedies.",
            25
        )
    }

    enum class DeeptadiAvastha(
        val displayName: String,
        val description: String
    ) {
        DEEPTA(
            "Deepta (Blazing)",
            "Exalted planet - shines brilliantly and gives exceptional results in all areas it signifies."
        ),
        SWASTHA(
            "Swastha (Healthy)",
            "Planet in own sign - comfortable and gives strong, natural results with ease."
        ),
        MUDITA(
            "Mudita (Delighted)",
            "Planet in great friend's sign - happy and supported, gives good results with cooperation."
        ),
        SHANTA(
            "Shanta (Peaceful)",
            "Planet in friendly sign - calm and stable, gives consistent positive results."
        ),
        DINA(
            "Dina (Ordinary)",
            "Planet in neutral sign - gives average results, neither specially good nor bad."
        ),
        VIKALA(
            "Vikala (Afflicted)",
            "Combust planet - weakened by Sun's rays, struggles to give full results. Hidden or overshadowed."
        ),
        KHALA(
            "Khala (Debilitated)",
            "Planet in debilitation - significantly weakened, gives poor or opposite results."
        ),
        KOPA(
            "Kopa (Angry)",
            "Planet in enemy sign - frustrated and obstructed, gives results with difficulty."
        ),
        BHITA(
            "Bhita (Frightened)",
            "Planet in great enemy's sign - very uncomfortable, gives minimal or adverse results."
        )
    }

    enum class LajjitadiAvastha(
        val displayName: String,
        val description: String
    ) {
        LAJJITA(
            "Lajjita (Ashamed)",
            "Planet feels ashamed due to conjunction with Sun/Saturn in 5th house. Children and creativity matters may suffer."
        ),
        GARVITA(
            "Garvita (Proud)",
            "Planet in exaltation or moolatrikona - proud and confident, gives excellent results with dignity."
        ),
        KSHUDITA(
            "Kshudita (Hungry)",
            "Planet in enemy sign with enemy aspect - constantly craving, never satisfied. Results feel incomplete."
        ),
        TRUSHITA(
            "Trushita (Thirsty)",
            "Planet in watery sign with enemy aspect - emotionally parched, seeking fulfillment. Emotional needs unmet."
        ),
        MUDITA(
            "Mudita (Delighted)",
            "Planet with friendly influences - joyful and supported, gives pleasant and fulfilling results."
        ),
        KSHOBHITA(
            "Kshobhita (Agitated)",
            "Planet conjunct Sun and aspected by malefic - disturbed and unsettled, gives erratic results."
        )
    }

    enum class PlanetaryRelationship {
        GREAT_FRIEND,
        FRIEND,
        NEUTRAL,
        ENEMY,
        GREAT_ENEMY
    }

    enum class RemedyPriority(val displayName: String) {
        HIGH("High Priority"),
        MEDIUM("Medium Priority"),
        LOW("Low Priority")
    }
}
