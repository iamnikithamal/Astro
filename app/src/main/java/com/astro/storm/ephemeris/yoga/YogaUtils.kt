package com.astro.storm.ephemeris.yoga

import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.VedicAstrologyUtils
import kotlin.math.abs

/**
 * Shared utility functions for yoga calculations.
 * These functions are used by all yoga calculator modules.
 *
 * All calculations follow traditional Vedic (Parashari) astrology principles.
 */
object YogaUtils {

    // ==================== YOGA DATA CLASSES ====================

    /**
     * Yoga category enumeration
     */
    enum class YogaCategory(val displayName: String, val description: String) {
        RAJA_YOGA("Raja Yoga", "Power, authority, and leadership combinations"),
        DHANA_YOGA("Dhana Yoga", "Wealth and prosperity combinations"),
        MAHAPURUSHA_YOGA("Pancha Mahapurusha Yoga", "Five great person combinations"),
        NABHASA_YOGA("Nabhasa Yoga", "Pattern-based planetary combinations"),
        CHANDRA_YOGA("Chandra Yoga", "Moon-based combinations"),
        SOLAR_YOGA("Solar Yoga", "Sun-based combinations"),
        NEGATIVE_YOGA("Negative Yoga", "Challenging combinations"),
        SPECIAL_YOGA("Special Yoga", "Other significant combinations");

        /**
         * Get localized display name
         */
        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                RAJA_YOGA -> StringKeyMatch.YOGA_CAT_RAJA
                DHANA_YOGA -> StringKeyMatch.YOGA_CAT_DHANA
                MAHAPURUSHA_YOGA -> StringKeyMatch.YOGA_CAT_PANCHA_MAHAPURUSHA
                NABHASA_YOGA -> StringKeyMatch.YOGA_CAT_NABHASA
                CHANDRA_YOGA -> StringKeyMatch.YOGA_CAT_CHANDRA
                SOLAR_YOGA -> StringKeyMatch.YOGA_CAT_SOLAR
                NEGATIVE_YOGA -> StringKeyMatch.YOGA_CAT_NEGATIVE
                SPECIAL_YOGA -> StringKeyMatch.YOGA_CAT_SPECIAL
            }
            return StringResources.get(key, language)
        }

        /**
         * Get localized description
         */
        fun getLocalizedDescription(language: Language): String {
            val key = when (this) {
                RAJA_YOGA -> StringKeyMatch.YOGA_CAT_RAJA_DESC
                DHANA_YOGA -> StringKeyMatch.YOGA_CAT_DHANA_DESC
                MAHAPURUSHA_YOGA -> StringKeyMatch.YOGA_CAT_PANCHA_MAHAPURUSHA_DESC
                NABHASA_YOGA -> StringKeyMatch.YOGA_CAT_NABHASA_DESC
                CHANDRA_YOGA -> StringKeyMatch.YOGA_CAT_CHANDRA_DESC
                SOLAR_YOGA -> StringKeyMatch.YOGA_CAT_SOLAR_DESC
                NEGATIVE_YOGA -> StringKeyMatch.YOGA_CAT_NEGATIVE_DESC
                SPECIAL_YOGA -> StringKeyMatch.YOGA_CAT_SPECIAL_DESC
            }
            return StringResources.get(key, language)
        }
    }

    /**
     * Yoga strength level
     */
    enum class YogaStrength(val displayName: String, val value: Int) {
        EXTREMELY_STRONG("Extremely Strong", 5),
        STRONG("Strong", 4),
        MODERATE("Moderate", 3),
        WEAK("Weak", 2),
        VERY_WEAK("Very Weak", 1);

        /**
         * Get localized display name
         */
        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                EXTREMELY_STRONG -> StringKeyMatch.YOGA_STRENGTH_EXTREMELY_STRONG
                STRONG -> StringKeyMatch.YOGA_STRENGTH_STRONG
                MODERATE -> StringKeyMatch.YOGA_STRENGTH_MODERATE
                WEAK -> StringKeyMatch.YOGA_STRENGTH_WEAK
                VERY_WEAK -> StringKeyMatch.YOGA_STRENGTH_VERY_WEAK
            }
            return StringResources.get(key, language)
        }

        companion object {
            /**
             * Get strength enum from percentage value
             */
            fun fromPercentage(percentage: Double): YogaStrength = when {
                percentage >= 85 -> EXTREMELY_STRONG
                percentage >= 70 -> STRONG
                percentage >= 50 -> MODERATE
                percentage >= 30 -> WEAK
                else -> VERY_WEAK
            }
        }
    }

    /**
     * Complete Yoga data class
     */
    data class Yoga(
        val name: String,
        val sanskritName: String,
        val category: YogaCategory,
        val planets: List<Planet>,
        val houses: List<Int>,
        val description: String,
        val effects: String,
        val strength: YogaStrength,
        val strengthPercentage: Double,
        val isAuspicious: Boolean,
        val activationPeriod: String,
        val cancellationFactors: List<String>
    )

    // ==================== HOUSE CLASSIFICATION CONSTANTS ====================

    /** Kendra houses (Angular/Quadrant) - 1, 4, 7, 10 */
    val KENDRA_HOUSES = setOf(1, 4, 7, 10)

    /** Trikona houses (Trine) - 1, 5, 9 */
    val TRIKONA_HOUSES = setOf(1, 5, 9)

    /** Dusthana houses (Malefic) - 6, 8, 12 */
    val DUSTHANA_HOUSES = setOf(6, 8, 12)

    /** Upachaya houses (Growth) - 3, 6, 10, 11 */
    val UPACHAYA_HOUSES = setOf(3, 6, 10, 11)

    /** Wealth houses - 2, 11 */
    val WEALTH_HOUSES = setOf(2, 11)

    /** Trishadaya houses - 3, 6, 11 */
    val TRISHADAYA_HOUSES = setOf(3, 6, 11)

    // ==================== PLANETARY CLASSIFICATION ====================

    /** Natural benefics */
    val NATURAL_BENEFICS = setOf(Planet.JUPITER, Planet.VENUS, Planet.MERCURY, Planet.MOON)

    /** Natural malefics */
    val NATURAL_MALEFICS = setOf(Planet.SUN, Planet.MARS, Planet.SATURN, Planet.RAHU, Planet.KETU)

    // ==================== CONJUNCTION AND ASPECT HELPERS ====================

    /**
     * Check if two planets are in conjunction (same sign or within orb).
     * Default orb is 8° as per Vedic astrology standards.
     */
    fun areConjunct(pos1: PlanetPosition, pos2: PlanetPosition, customOrb: Double = 8.0): Boolean {
        val distance = abs(pos1.longitude - pos2.longitude)
        val normalizedDistance = if (distance > 180) 360 - distance else distance
        return normalizedDistance <= customOrb
    }

    /**
     * Check if two planets are in mutual aspect (opposition).
     */
    fun areMutuallyAspecting(pos1: PlanetPosition, pos2: PlanetPosition): Boolean {
        val angle = abs(pos1.longitude - pos2.longitude)
        val normalizedAngle = if (angle > 180) 360 - angle else angle
        return normalizedAngle in 170.0..190.0
    }

    /**
     * Check if two planets are in Parivartana (exchange of signs).
     */
    fun areInExchange(pos1: PlanetPosition, pos2: PlanetPosition): Boolean {
        return pos1.sign.ruler == pos2.planet && pos2.sign.ruler == pos1.planet
    }

    /**
     * Check if a planet is in Kendra from reference position.
     */
    fun isInKendraFrom(pos: PlanetPosition, reference: PlanetPosition): Boolean {
        val house = getHouseFrom(pos.sign, reference.sign)
        return house in KENDRA_HOUSES
    }

    /**
     * Calculate house number from one sign to another.
     * @param targetSign The sign to calculate house for
     * @param referenceSign The reference sign (e.g., ascendant sign)
     * @return House number (1-12)
     */
    fun getHouseFrom(targetSign: ZodiacSign, referenceSign: ZodiacSign): Int {
        val diff = targetSign.number - referenceSign.number
        return if (diff >= 0) diff + 1 else diff + 13
    }

    /**
     * Get house lords for a given ascendant sign.
     */
    fun getHouseLords(ascendantSign: ZodiacSign): Map<Int, Planet> {
        val lords = mutableMapOf<Int, Planet>()
        for (house in 1..12) {
            val signIndex = (ascendantSign.ordinal + house - 1) % 12
            val sign = ZodiacSign.entries[signIndex]
            lords[house] = sign.ruler
        }
        return lords
    }

    // ==================== DIGNITY HELPERS (delegate to VedicAstrologyUtils) ====================

    /**
     * Check if planet is in own sign.
     */
    fun isInOwnSign(pos: PlanetPosition): Boolean = VedicAstrologyUtils.isInOwnSign(pos)

    /**
     * Check if planet is exalted.
     */
    fun isExalted(pos: PlanetPosition): Boolean = VedicAstrologyUtils.isExalted(pos)

    /**
     * Check if planet is debilitated.
     */
    fun isDebilitated(pos: PlanetPosition): Boolean = VedicAstrologyUtils.isDebilitated(pos)

    /**
     * Check if planet is in Moolatrikona.
     */
    fun isInMoolatrikona(pos: PlanetPosition): Boolean = VedicAstrologyUtils.isInMoolatrikona(pos)

    /**
     * Check if planet is in friendly sign.
     */
    fun isInFriendSign(pos: PlanetPosition): Boolean = VedicAstrologyUtils.isInFriendSign(pos)

    /**
     * Check if planet is in enemy sign.
     */
    fun isInEnemySign(pos: PlanetPosition): Boolean = VedicAstrologyUtils.isInEnemySign(pos)

    /**
     * Check if planet has Dig Bala (directional strength).
     */
    fun hasDigBala(pos: PlanetPosition): Boolean = VedicAstrologyUtils.hasDigBala(pos)

    /**
     * Check if planet is naturally benefic.
     */
    fun isNaturalBenefic(planet: Planet): Boolean = VedicAstrologyUtils.isNaturalBenefic(planet)

    /**
     * Check if planet is naturally malefic.
     */
    fun isNaturalMalefic(planet: Planet): Boolean = VedicAstrologyUtils.isNaturalMalefic(planet)

    // ==================== NEECHA BHANGA (DEBILITATION CANCELLATION) ====================

    /**
     * Check if a debilitated planet has Neecha Bhanga (debilitation cancellation).
     *
     * Neecha Bhanga conditions per BPHS:
     * 1. Lord of debilitation sign aspects the debilitated planet
     * 2. Lord of exaltation sign aspects the debilitated planet
     * 3. Debilitated planet is in Kendra from Lagna or Moon
     * 4. Lord of the sign where planet is debilitated is in Kendra from Lagna or Moon
     */
    fun hasNeechaBhanga(pos: PlanetPosition, chart: VedicChart): Boolean {
        if (!isDebilitated(pos)) return false

        // Condition 3: Debilitated planet in Kendra from Lagna
        if (pos.house in KENDRA_HOUSES) return true

        // Condition 4: Lord of debilitation sign in Kendra
        val debilitatedSignLord = pos.sign.ruler
        val lordPos = chart.planetPositions.find { it.planet == debilitatedSignLord }
        if (lordPos != null && lordPos.house in KENDRA_HOUSES) return true

        // Check Moon position for Kendra check
        val moonPos = chart.planetPositions.find { it.planet == Planet.MOON }
        if (moonPos != null) {
            val houseFromMoon = getHouseFrom(pos.sign, moonPos.sign)
            if (houseFromMoon in KENDRA_HOUSES) return true
        }

        return false
    }

    // ==================== COMBUSTION CHECK ====================

    /**
     * Combustion orbs for each planet (degrees from Sun).
     */
    private val combustionOrbs = mapOf(
        Planet.MOON to 12.0,
        Planet.MARS to 17.0,
        Planet.MERCURY to Pair(14.0, 12.0),  // direct, retrograde
        Planet.JUPITER to 11.0,
        Planet.VENUS to Pair(10.0, 8.0),     // direct, retrograde
        Planet.SATURN to 15.0
    )

    /**
     * Get combustion orb for a planet.
     */
    fun getCombustionOrb(planet: Planet, isRetrograde: Boolean): Double {
        return when (val orb = combustionOrbs[planet]) {
            is Double -> orb
            is Pair<*, *> -> if (isRetrograde) (orb.second as Double) else (orb.first as Double)
            else -> 0.0
        }
    }

    /**
     * Check if a planet is combust.
     */
    fun isCombust(pos: PlanetPosition, chart: VedicChart): Boolean {
        if (pos.planet == Planet.SUN || pos.planet == Planet.RAHU || pos.planet == Planet.KETU) {
            return false
        }

        val sunPos = chart.planetPositions.find { it.planet == Planet.SUN } ?: return false
        val orb = getCombustionOrb(pos.planet, pos.isRetrograde)
        if (orb == 0.0) return false

        val distance = abs(pos.longitude - sunPos.longitude)
        val normalizedDistance = if (distance > 180) 360 - distance else distance
        return normalizedDistance <= orb
    }

    /**
     * Calculate combustion factor (0.0 = fully combust, 1.0 = not combust).
     */
    fun getCombustionFactor(pos: PlanetPosition, chart: VedicChart): Double {
        if (pos.planet == Planet.SUN || pos.planet == Planet.RAHU || pos.planet == Planet.KETU) {
            return 1.0
        }

        val sunPos = chart.planetPositions.find { it.planet == Planet.SUN } ?: return 1.0
        val orb = getCombustionOrb(pos.planet, pos.isRetrograde)
        if (orb == 0.0) return 1.0

        val distance = abs(pos.longitude - sunPos.longitude)
        val normalizedDistance = if (distance > 180) 360 - distance else distance

        return when {
            normalizedDistance > orb -> 1.0
            normalizedDistance <= 3.0 -> 0.3  // Deep combustion
            else -> 0.3 + (normalizedDistance / orb) * 0.7
        }
    }

    // ==================== PAPAKARTARI / SHUBHAKARTARI ====================

    /**
     * Check if planet is hemmed between malefics (Papakartari Yoga).
     */
    fun isPapakartari(pos: PlanetPosition, chart: VedicChart): Boolean {
        val prevHouse = if (pos.house == 1) 12 else pos.house - 1
        val nextHouse = if (pos.house == 12) 1 else pos.house + 1

        val hasMaleficBefore = chart.planetPositions.any {
            it.house == prevHouse && isNaturalMalefic(it.planet)
        }
        val hasMaleficAfter = chart.planetPositions.any {
            it.house == nextHouse && isNaturalMalefic(it.planet)
        }

        return hasMaleficBefore && hasMaleficAfter
    }

    /**
     * Check if planet is hemmed between benefics (Shubhakartari Yoga).
     */
    fun isShubhakartari(pos: PlanetPosition, chart: VedicChart): Boolean {
        val prevHouse = if (pos.house == 1) 12 else pos.house - 1
        val nextHouse = if (pos.house == 12) 1 else pos.house + 1

        val hasBeneficBefore = chart.planetPositions.any {
            it.house == prevHouse && isNaturalBenefic(it.planet)
        }
        val hasBeneficAfter = chart.planetPositions.any {
            it.house == nextHouse && isNaturalBenefic(it.planet)
        }

        return hasBeneficBefore && hasBeneficAfter
    }

    // ==================== YOGA STRENGTH CALCULATION ====================

    /**
     * Calculate yoga strength based on planetary positions.
     *
     * Base strength factors:
     * - Exaltation: +15%
     * - Own sign (Swakshetra): +12%
     * - Friend's sign (Mitra Kshetra): +6%
     * - Kendra/Trikona placement: +8%
     * - Debilitation: -15% (before cancellation check)
     * - Dusthana placement (6,8,12): -10%
     * - Retrograde benefics: +5% (considered stronger)
     * - Dig Bala: +7%
     */
    fun calculateYogaStrength(
        chart: VedicChart,
        positions: List<PlanetPosition>
    ): Pair<Double, List<String>> {
        var baseStrength = 50.0
        val cancellationReasons = mutableListOf<String>()

        positions.forEach { pos ->
            // Add strength for exaltation
            if (isExalted(pos)) baseStrength += 15.0

            // Add strength for own sign
            if (isInOwnSign(pos)) baseStrength += 12.0

            // Add strength for friend's sign
            if (isInFriendSign(pos)) baseStrength += 6.0

            // Add strength for good houses (Kendra/Trikona)
            if (pos.house in KENDRA_HOUSES || pos.house in TRIKONA_HOUSES) baseStrength += 8.0

            // Add for 2nd and 11th (wealth houses)
            if (pos.house in WEALTH_HOUSES) baseStrength += 4.0

            // Reduce for debilitation (check for cancellation)
            if (isDebilitated(pos)) {
                if (hasNeechaBhanga(pos, chart)) {
                    baseStrength -= 5.0  // Reduced penalty due to cancellation
                    cancellationReasons.add("${pos.planet.displayName} debilitation cancelled (Neecha Bhanga)")
                } else {
                    baseStrength -= 15.0
                    cancellationReasons.add("${pos.planet.displayName} debilitated in ${pos.sign.displayName}")
                }
            }

            // Reduce for 6, 8, 12 placement (Dusthanas)
            if (pos.house in DUSTHANA_HOUSES) {
                baseStrength -= 10.0
                cancellationReasons.add("${pos.planet.displayName} in dusthana house ${pos.house}")
            }

            // Check for retrograde effects
            if (pos.isRetrograde) {
                when (pos.planet) {
                    Planet.JUPITER, Planet.VENUS, Planet.MERCURY -> baseStrength += 5.0
                    Planet.SATURN -> baseStrength += 3.0
                    Planet.MARS -> baseStrength -= 2.0
                    else -> {}
                }
            }

            // Dig Bala (directional strength)
            if (hasDigBala(pos)) baseStrength += 7.0

            // Combustion check
            val combustionFactor = getCombustionFactor(pos, chart)
            if (combustionFactor < 1.0) {
                val penalty = (1.0 - combustionFactor) * 15.0
                baseStrength -= penalty
                cancellationReasons.add("${pos.planet.displayName} combust (${String.format("%.0f", combustionFactor * 100)}% strength)")
            }

            // Papakartari check
            if (isPapakartari(pos, chart)) {
                baseStrength -= 8.0
                cancellationReasons.add("${pos.planet.displayName} hemmed by malefics (Papakartari)")
            }
        }

        return Pair(baseStrength.coerceIn(10.0, 100.0), cancellationReasons)
    }

    // ==================== HOUSE SIGNIFICATIONS ====================

    /**
     * Get house significations (English).
     */
    fun getHouseSignifications(house: Int): String = when (house) {
        1 -> "self, personality, health, appearance"
        2 -> "wealth, family, speech, food"
        3 -> "courage, siblings, communication, short journeys"
        4 -> "mother, home, property, vehicles, education"
        5 -> "intelligence, children, creativity, romance, speculation"
        6 -> "enemies, diseases, debts, service, daily work"
        7 -> "marriage, partnerships, business, foreign travel"
        8 -> "longevity, obstacles, inheritance, occult, transformation"
        9 -> "fortune, father, dharma, higher learning, long journeys"
        10 -> "career, fame, government, authority, public image"
        11 -> "gains, income, elder siblings, fulfillment of desires"
        12 -> "losses, expenses, foreign lands, moksha, sleep"
        else -> ""
    }
}
