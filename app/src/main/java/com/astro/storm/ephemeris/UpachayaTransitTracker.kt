package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringKeyDosha
import java.time.LocalDateTime

/**
 * Upachaya House Transit Tracker
 *
 * Tracks transits through Upachaya houses (3, 6, 10, 11) where natural malefics
 * give good results. These houses are "growth houses" where planets gain strength
 * over time and produce increasingly positive results.
 *
 * Reference: Phaladeepika, BPHS transit rules
 */
object UpachayaTransitTracker {

    /** Upachaya houses: 3 (courage), 6 (enemies), 10 (career), 11 (gains) */
    val UPACHAYA_HOUSES = setOf(3, 6, 10, 11)

    /** Malefic planets that give especially good results in Upachaya houses */
    val BENEFICIAL_MALEFICS_IN_UPACHAYA = setOf(
        Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU, Planet.SUN
    )

    /**
     * Analyze current transit positions relative to natal chart
     */
    fun analyzeUpachayaTransits(
        natalChart: VedicChart,
        transitPositions: List<PlanetPosition>,
        language: Language
    ): UpachayaTransitAnalysis {
        val moonSign = natalChart.planetPositions.find { it.planet == Planet.MOON }?.sign
            ?: throw IllegalArgumentException("Moon position required")

        val lagnaSign = ZodiacSign.fromLongitude(natalChart.ascendant)

        val upachayaTransitsFromMoon = analyzeTransitsFromReference(
            transitPositions, moonSign, TransitReference.MOON, language
        )
        val upachayaTransitsFromLagna = analyzeTransitsFromReference(
            transitPositions, lagnaSign, TransitReference.LAGNA, language
        )

        val activeUpachayaTransits = (upachayaTransitsFromMoon + upachayaTransitsFromLagna)
            .filter { it.isInUpachaya }

        val mostSignificantTransits = activeUpachayaTransits
            .sortedByDescending { it.significance }
            .take(5)

        val overallAssessment = calculateOverallAssessment(activeUpachayaTransits, language)
        val recommendations = generateRecommendations(activeUpachayaTransits, language)
        val alerts = generateAlerts(activeUpachayaTransits, language)

        return UpachayaTransitAnalysis(
            analysisDateTime = LocalDateTime.now(),
            moonSign = moonSign,
            lagnaSign = lagnaSign,
            transitsFromMoon = upachayaTransitsFromMoon,
            transitsFromLagna = upachayaTransitsFromLagna,
            activeUpachayaTransits = activeUpachayaTransits,
            mostSignificantTransits = mostSignificantTransits,
            overallAssessment = overallAssessment,
            houseWiseAnalysis = analyzeByHouse(activeUpachayaTransits, language),
            recommendations = recommendations,
            alerts = alerts
        )
    }

    /**
     * Analyze transits from a specific reference point
     */
    private fun analyzeTransitsFromReference(
        transitPositions: List<PlanetPosition>,
        referenceSign: ZodiacSign,
        reference: TransitReference,
        language: Language
    ): List<UpachayaTransit> {
        return transitPositions.map { transit ->
            val houseFromReference = ((transit.sign.ordinal - referenceSign.ordinal + 12) % 12) + 1
            val isInUpachaya = houseFromReference in UPACHAYA_HOUSES
            val isBeneficialMalefic = transit.planet in BENEFICIAL_MALEFICS_IN_UPACHAYA

            val transitQuality = when {
                isInUpachaya && isBeneficialMalefic -> TransitQuality.EXCELLENT
                isInUpachaya && transit.planet in AstrologicalConstants.NATURAL_BENEFICS ->
                    TransitQuality.GOOD
                isInUpachaya -> TransitQuality.FAVORABLE
                else -> TransitQuality.NEUTRAL
            }

            val significance = calculateTransitSignificance(
                transit.planet, houseFromReference, isInUpachaya, isBeneficialMalefic
            )

            val effects = if (isInUpachaya) {
                getUpachayaTransitEffects(transit.planet, houseFromReference, reference, language)
            } else emptyList()

            val duration = getTransitDuration(transit.planet, language)

            UpachayaTransit(
                planet = transit.planet,
                transitSign = transit.sign,
                transitDegree = transit.longitude % 30.0,
                reference = reference,
                referenceSign = referenceSign,
                houseFromReference = houseFromReference,
                isInUpachaya = isInUpachaya,
                isBeneficialMaleficInUpachaya = isInUpachaya && isBeneficialMalefic,
                transitQuality = transitQuality,
                significance = significance,
                effects = effects,
                approximateDuration = duration,
                recommendations = if (isInUpachaya) {
                    getTransitRecommendations(transit.planet, houseFromReference, language)
                } else emptyList()
            )
        }
    }

    private fun calculateTransitSignificance(
        planet: Planet,
        house: Int,
        isUpachaya: Boolean,
        isBeneficialMalefic: Boolean
    ): Double {
        if (!isUpachaya) return 30.0

        var score = 50.0

        // Slow-moving planets have more significant transits
        score += when (planet) {
            Planet.SATURN -> 30.0
            Planet.JUPITER -> 25.0
            Planet.RAHU, Planet.KETU -> 20.0
            Planet.MARS -> 15.0
            Planet.SUN -> 10.0
            Planet.VENUS -> 8.0
            Planet.MERCURY -> 5.0
            Planet.MOON -> 3.0
            else -> 0.0
        }

        // Malefics in Upachaya get bonus
        if (isBeneficialMalefic) score += 15.0

        // 10th and 11th house transits are most significant
        when (house) {
            10 -> score += 10.0
            11 -> score += 12.0
            6 -> score += 5.0
            3 -> score += 3.0
        }

        return score.coerceIn(0.0, 100.0)
    }

    private fun getUpachayaTransitEffects(
        planet: Planet,
        house: Int,
        reference: TransitReference,
        language: Language
    ): List<String> {
        val houseEffects = when (house) {
            3 -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_COURAGE, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_TRAVELS, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_SIBLINGS, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_SKILLS, language)
            )
            6 -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_ENEMIES, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_HEALTH_IMPROVE, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_SERVICE, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_DEBTS, language)
            )
            10 -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_CAREER_ADVANCE, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_PROFESSIONAL, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_STATUS, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_GOVT_FAVORED, language)
            )
            11 -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_GAINS, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_DESIRES, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_FRIENDSHIP, language),
                StringResources.get(StringKeyDosha.UPACHAYA_EFFECT_ELDER_SIBLINGS, language)
            )
            else -> emptyList()
        }

        val planetEffects = when (planet) {
            Planet.SATURN -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_SATURN_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_SATURN_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_SATURN_3, language)
            )
            Planet.MARS -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_MARS_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_MARS_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_MARS_3, language)
            )
            Planet.RAHU -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_RAHU_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_RAHU_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_RAHU_3, language)
            )
            Planet.KETU -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_KETU_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_KETU_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_KETU_3, language)
            )
            Planet.JUPITER -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_JUPITER_1, language, getHouseName(house, language)),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_JUPITER_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_JUPITER_3, language)
            )
            Planet.SUN -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_SUN_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_SUN_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_PLANET_SUN_3, language)
            )
            else -> listOf(StringResources.get(StringKeyDosha.UPACHAYA_PLANET_GENERIC, language, planet.displayName, getHouseName(house, language)))
        }

        return houseEffects.take(2) + planetEffects.take(2)
    }

    private fun getTransitDuration(planet: Planet, language: Language): String {
        return when (planet) {
            Planet.MOON -> StringResources.get(StringKeyDosha.UPACHAYA_DURATION_MOON, language)
            Planet.SUN -> StringResources.get(StringKeyDosha.UPACHAYA_DURATION_SUN, language)
            Planet.MERCURY -> StringResources.get(StringKeyDosha.UPACHAYA_DURATION_MERCURY, language)
            Planet.VENUS -> StringResources.get(StringKeyDosha.UPACHAYA_DURATION_VENUS, language)
            Planet.MARS -> StringResources.get(StringKeyDosha.UPACHAYA_DURATION_MARS, language)
            Planet.JUPITER -> StringResources.get(StringKeyDosha.UPACHAYA_DURATION_JUPITER, language)
            Planet.SATURN -> StringResources.get(StringKeyDosha.UPACHAYA_DURATION_SATURN, language)
            Planet.RAHU -> StringResources.get(StringKeyDosha.UPACHAYA_DURATION_RAHU, language)
            Planet.KETU -> StringResources.get(StringKeyDosha.UPACHAYA_DURATION_RAHU, language) // Ketu same as Rahu
            else -> "Variable"
        }
    }

    private fun getTransitRecommendations(planet: Planet, house: Int, language: Language): List<String> {
        val houseRecs = when (house) {
            3 -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_3_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_3_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_3_3, language)
            )
            6 -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_6_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_6_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_6_3, language)
            )
            10 -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_10_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_10_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_10_3, language)
            )
            11 -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_11_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_11_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_HOUSE_11_3, language)
            )
            else -> emptyList()
        }

        val planetRecs = when (planet) {
            Planet.SATURN -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_REC_SATURN_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_SATURN_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_SATURN_3, language)
            )
            Planet.MARS -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_REC_MARS_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_MARS_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_MARS_3, language)
            )
            Planet.RAHU -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_REC_RAHU_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_RAHU_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_RAHU_3, language)
            )
            Planet.JUPITER -> listOf(
                StringResources.get(StringKeyDosha.UPACHAYA_REC_JUPITER_1, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_JUPITER_2, language),
                StringResources.get(StringKeyDosha.UPACHAYA_REC_JUPITER_3, language)
            )
            else -> emptyList()
        }

        return (houseRecs + planetRecs).take(4)
    }

    private fun analyzeByHouse(transits: List<UpachayaTransit>, language: Language): Map<Int, HouseTransitAnalysis> {
        return UPACHAYA_HOUSES.associateWith { house ->
            val houseTransits = transits.filter { it.houseFromReference == house && it.isInUpachaya }
            val planets = houseTransits.map { it.planet }.distinct()

            HouseTransitAnalysis(
                house = house,
                houseName = getHouseName(house, language),
                houseTheme = getHouseTheme(house, language),
                transitingPlanets = planets,
                strength = calculateHouseTransitStrength(houseTransits),
                effects = getHouseTransitSummary(house, planets, language),
                timing = if (houseTransits.isNotEmpty()) {
                    val activeLabel = StringResources.get(StringKeyAnalysis.VARSHAPHALA_ACTIVE, language)
                    "$activeLabel: ${planets.joinToString { it.getLocalizedName(language) }}"
                } else StringResources.get(StringKeyDosha.UPACHAYA_HOUSE_INACTIVE, language)
            )
        }
    }

    private fun getHouseTheme(house: Int, language: Language): String {
        return when (house) {
            3 -> StringResources.get(StringKeyDosha.UPACHAYA_SCREEN_HOUSE_3, language).split(" - ").getOrElse(1) { "Courage, Siblings" }
            6 -> StringResources.get(StringKeyDosha.UPACHAYA_SCREEN_HOUSE_6, language).split(" - ").getOrElse(1) { "Enemies, Health" }
            10 -> StringResources.get(StringKeyDosha.UPACHAYA_SCREEN_HOUSE_10, language).split(" - ").getOrElse(1) { "Career, Status" }
            11 -> StringResources.get(StringKeyDosha.UPACHAYA_SCREEN_HOUSE_11, language).split(" - ").getOrElse(1) { "Gains, Aspirations" }
            else -> "General"
        }
    }

    private fun getHouseTransitSummary(house: Int, planets: List<Planet>, language: Language): List<String> {
        if (planets.isEmpty()) return listOf(StringResources.get(StringKeyDosha.UPACHAYA_HOUSE_NO_TRANSIT, language, getHouseName(house, language)))

        val summary = mutableListOf<String>()
        summary.add(StringResources.get(StringKeyDosha.UPACHAYA_HOUSE_PLANETS_COUNT, language, planets.size, getHouseName(house, language)))

        if (planets.any { it == Planet.SATURN }) {
            summary.add(StringResources.get(StringKeyDosha.UPACHAYA_SATURN_PROGRESS, language, getHouseTheme(house, language).lowercase()))
        }
        if (planets.any { it == Planet.MARS }) {
            summary.add(StringResources.get(StringKeyDosha.UPACHAYA_MARS_PROGRESS, language, getHouseTheme(house, language).lowercase()))
        }
        if (planets.any { it == Planet.JUPITER }) {
            summary.add(StringResources.get(StringKeyDosha.UPACHAYA_JUPITER_PROGRESS, language, getHouseTheme(house, language).lowercase()))
        }

        return summary
    }

    private fun getHouseName(house: Int, language: Language): String {
        return if (language == Language.NEPALI) {
            "$house भाव"
        } else {
            when (house) {
                1 -> "1st"
                2 -> "2nd"
                3 -> "3rd"
                else -> "${house}th"
            }
        }
    }

    private fun calculateOverallAssessment(transits: List<UpachayaTransit>, language: Language): OverallUpachayaAssessment {
        if (transits.isEmpty()) {
            return OverallUpachayaAssessment(
                level = UpachayaLevel.LOW,
                score = 30.0,
                summary = StringResources.get(StringKeyDosha.UPACHAYA_ASSESS_NO_ACTIVE, language),
                keyPeriod = StringResources.get(StringKeyDosha.UPACHAYA_ASSESS_WAIT, language)
            )
        }

        val avgSignificance = transits.map { it.significance }.average()
        val excellentCount = transits.count { it.transitQuality == TransitQuality.EXCELLENT }

        val level = when {
            avgSignificance >= 70 && excellentCount >= 2 -> UpachayaLevel.EXCEPTIONAL
            avgSignificance >= 60 || excellentCount >= 1 -> UpachayaLevel.HIGH
            avgSignificance >= 45 -> UpachayaLevel.MODERATE
            else -> UpachayaLevel.LOW
        }

        val summary = when (level) {
            UpachayaLevel.EXCEPTIONAL -> StringResources.get(StringKeyDosha.UPACHAYA_ASSESS_EXCEPTIONAL, language)
            UpachayaLevel.HIGH -> StringResources.get(StringKeyDosha.UPACHAYA_ASSESS_HIGH, language)
            UpachayaLevel.MODERATE -> StringResources.get(StringKeyDosha.UPACHAYA_ASSESS_MODERATE, language)
            UpachayaLevel.LOW -> StringResources.get(StringKeyDosha.UPACHAYA_ASSESS_LOW, language)
        }

        val keyPeriod = transits
            .filter { it.planet in listOf(Planet.SATURN, Planet.JUPITER, Planet.RAHU) }
            .maxByOrNull { it.significance }
            ?.let { 
                StringResources.get(StringKeyDosha.UPACHAYA_ASSESS_KEY_TRANSIT, language, it.planet.displayName, getHouseName(it.houseFromReference, language))
            } ?: StringResources.get(StringKeyDosha.UPACHAYA_ASSESS_DAILY_ACTION, language)

        return OverallUpachayaAssessment(
            level = level,
            score = avgSignificance,
            summary = summary,
            keyPeriod = keyPeriod
        )
    }

    private fun generateRecommendations(transits: List<UpachayaTransit>, language: Language): List<String> {
        val recommendations = mutableListOf<String>()
        val activeHouses = transits.filter { it.isInUpachaya }.map { it.houseFromReference }.distinct()

        if (3 in activeHouses) recommendations.add(StringResources.get(StringKeyDosha.UPACHAYA_REC_BOLD_INITIATIVE, language))
        if (6 in activeHouses) recommendations.add(StringResources.get(StringKeyDosha.UPACHAYA_REC_HEALTH_CONFLICTS, language))
        if (10 in activeHouses) recommendations.add(StringResources.get(StringKeyDosha.UPACHAYA_REC_CAREER_FOCUS, language))
        if (11 in activeHouses) recommendations.add(StringResources.get(StringKeyDosha.UPACHAYA_REC_FINANCIAL_GOALS, language))

        if (transits.any { it.isInUpachaya && it.planet in listOf(Planet.SATURN, Planet.RAHU) }) {
            recommendations.add(StringResources.get(StringKeyDosha.UPACHAYA_REC_SLOW_PLANET, language))
        }

        if (recommendations.isEmpty()) {
            recommendations.add(StringResources.get(StringKeyDosha.UPACHAYA_REC_PREPARE_UPCOMING, language))
        }

        return recommendations
    }

    private fun generateAlerts(transits: List<UpachayaTransit>, language: Language): List<UpachayaAlert> {
        val alerts = mutableListOf<UpachayaAlert>()

        transits.filter { it.transitQuality == TransitQuality.EXCELLENT }.forEach { transit ->
            alerts.add(UpachayaAlert(
                type = AlertType.OPPORTUNITY,
                planet = transit.planet,
                house = transit.houseFromReference,
                message = StringResources.get(StringKeyDosha.UPACHAYA_ALERT_EXCELLENT, language, transit.planet.displayName, getHouseName(transit.houseFromReference, language)),
                priority = AlertPriority.HIGH
            ))
        }

        if (transits.any { it.planet == Planet.SATURN && it.houseFromReference == 10 && it.isInUpachaya }) {
            alerts.add(UpachayaAlert(
                type = AlertType.MAJOR_TRANSIT,
                planet = Planet.SATURN,
                house = 10,
                message = StringResources.get(StringKeyDosha.UPACHAYA_ALERT_SATURN_10, language),
                priority = AlertPriority.HIGH
            ))
        }

        if (transits.any { it.planet == Planet.JUPITER && it.houseFromReference == 11 && it.isInUpachaya }) {
            alerts.add(UpachayaAlert(
                type = AlertType.FORTUNE,
                planet = Planet.JUPITER,
                house = 11,
                message = StringResources.get(StringKeyDosha.UPACHAYA_ALERT_JUPITER_11, language),
                priority = AlertPriority.HIGH
            ))
        }

        return alerts.sortedByDescending { it.priority }
    }

    fun getUpcomingTransits(natalChart: VedicChart, language: Language): List<UpcomingUpachayaTransit> {
        val moonSign = natalChart.planetPositions.find { it.planet == Planet.MOON }?.sign
            ?: return emptyList()

        val upcomingTransits = mutableListOf<UpcomingUpachayaTransit>()

        UPACHAYA_HOUSES.forEach { house ->
            val targetSign = ZodiacSign.entries[(moonSign.ordinal + house - 1) % 12]

            listOf(Planet.SATURN, Planet.JUPITER, Planet.MARS).forEach { planet ->
                upcomingTransits.add(UpcomingUpachayaTransit(
                    planet = planet,
                    targetHouse = house,
                    targetSign = targetSign,
                    significance = StringResources.get(StringKeyDosha.UPACHAYA_UPCOMING_SIGNIFICANCE, language, planet.displayName, targetSign.displayName, getHouseTheme(house, language)),
                    recommendation = StringResources.get(StringKeyDosha.UPACHAYA_UPCOMING_REC, language, getHouseTheme(house, language))
                ))
            }
        }

        return upcomingTransits.sortedBy { it.targetHouse }
    }
    private fun calculateHouseTransitStrength(transits: List<UpachayaTransit>): HouseStrength {
        if (transits.isEmpty()) return HouseStrength.INACTIVE
        var score = 0
        for (transit in transits) {
            score += when (transit.planet) {
                Planet.SUN, Planet.MARS, Planet.SATURN, Planet.RAHU -> 20
                Planet.JUPITER -> 15
                else -> 10
            }
        }
        val normalizedScore = (score / transits.size).coerceAtMost(100)
        return when {
            normalizedScore >= 80 -> HouseStrength.VERY_STRONG
            normalizedScore >= 60 -> HouseStrength.STRONG
            normalizedScore >= 40 -> HouseStrength.MODERATE
            normalizedScore >= 20 -> HouseStrength.MILD
            else -> HouseStrength.INACTIVE
        }
    }
}

// Data classes
data class UpachayaTransitAnalysis(
    val analysisDateTime: LocalDateTime,
    val moonSign: ZodiacSign,
    val lagnaSign: ZodiacSign,
    val transitsFromMoon: List<UpachayaTransit>,
    val transitsFromLagna: List<UpachayaTransit>,
    val activeUpachayaTransits: List<UpachayaTransit>,
    val mostSignificantTransits: List<UpachayaTransit>,
    val overallAssessment: OverallUpachayaAssessment,
    val houseWiseAnalysis: Map<Int, HouseTransitAnalysis>,
    val recommendations: List<String>,
    val alerts: List<UpachayaAlert>
)

enum class TransitReference {
    MOON, LAGNA
}

data class UpachayaTransit(
    val planet: Planet,
    val transitSign: ZodiacSign,
    val transitDegree: Double,
    val reference: TransitReference,
    val referenceSign: ZodiacSign,
    val houseFromReference: Int,
    val isInUpachaya: Boolean,
    val isBeneficialMaleficInUpachaya: Boolean,
    val transitQuality: TransitQuality,
    val significance: Double,
    val effects: List<String>,
    val approximateDuration: String,
    val recommendations: List<String>
)

enum class TransitQuality {
    EXCELLENT, GOOD, FAVORABLE, NEUTRAL
}

data class HouseTransitAnalysis(
    val house: Int,
    val houseName: String,
    val houseTheme: String,
    val transitingPlanets: List<Planet>,
    val strength: HouseStrength,
    val effects: List<String>,
    val timing: String
)

enum class HouseStrength {
    INACTIVE, MILD, MODERATE, STRONG, VERY_STRONG
}

data class OverallUpachayaAssessment(
    val level: UpachayaLevel,
    val score: Double,
    val summary: String,
    val keyPeriod: String
)

enum class UpachayaLevel {
    LOW, MODERATE, HIGH, EXCEPTIONAL
}

data class UpachayaAlert(
    val type: AlertType,
    val planet: Planet,
    val house: Int,
    val message: String,
    val priority: AlertPriority
)

enum class AlertType {
    OPPORTUNITY, MAJOR_TRANSIT, FORTUNE, CAUTION
}

enum class AlertPriority {
    LOW, MEDIUM, HIGH
}

data class UpcomingUpachayaTransit(
    val planet: Planet,
    val targetHouse: Int,
    val targetSign: ZodiacSign,
    val significance: String,
    val recommendation: String
)
