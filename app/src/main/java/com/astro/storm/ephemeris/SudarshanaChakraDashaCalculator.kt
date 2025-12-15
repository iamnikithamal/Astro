package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.Quality
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import java.time.LocalDateTime

/**
 * Sudarshana Chakra Dasha Calculator
 *
 * Triple-reference dasha system analyzing charts from Lagna, Moon, and Sun simultaneously.
 * Each house progresses one year at a time, creating a 12-year cycle from each reference.
 *
 * Reference: Traditional Sudarshana Chakra texts
 */
object SudarshanaChakraDashaCalculator {

    /** Each house/sign rules for one year in Sudarshana Chakra */
    const val YEARS_PER_HOUSE = 1

    /** Full cycle is 12 years (one complete round of zodiac) */
    const val FULL_CYCLE_YEARS = 12

    /**
     * Calculate Sudarshana Chakra Dasha for a given age/year
     */
    fun calculateSudarshana(chart: VedicChart, targetAge: Int = 0): SudarshanaChakraResult {
        return calculateSudarshanaChakra(chart, targetAge)
    }

    /**
     * Calculate Sudarshana Chakra Dasha for a given age/year
     */
    fun calculateSudarshanaChakra(chart: VedicChart, targetAge: Int = 0): SudarshanaChakraResult {
        val birthDateTime = chart.birthData.dateTime
        val currentAge = if (targetAge == 0) {
            java.time.Period.between(birthDateTime.toLocalDate(), LocalDateTime.now().toLocalDate()).years
        } else {
            targetAge
        }

        val lagnaSign = ZodiacSign.fromLongitude(chart.ascendant)
        val moonSign = chart.planetPositions.find { it.planet == Planet.MOON }?.sign
            ?: throw IllegalArgumentException("Moon position required")
        val sunSign = chart.planetPositions.find { it.planet == Planet.SUN }?.sign
            ?: throw IllegalArgumentException("Sun position required")

        val houseFromLagna = ((currentAge - 1) % FULL_CYCLE_YEARS) + 1
        val houseFromMoon = ((currentAge - 1) % FULL_CYCLE_YEARS) + 1
        val houseFromSun = ((currentAge - 1) % FULL_CYCLE_YEARS) + 1

        val lagnaChakra = calculateChakraFromReference(
            chart, lagnaSign, currentAge, SudarshanaReference.LAGNA
        )
        val moonChakra = calculateChakraFromReference(
            chart, moonSign, currentAge, SudarshanaReference.CHANDRA
        )
        val sunChakra = calculateChakraFromReference(
            chart, sunSign, currentAge, SudarshanaReference.SURYA
        )

        val synthesis = synthesizeResults(lagnaChakra, moonChakra, sunChakra, chart)

        val yearlyProgression = calculateYearlyProgression(chart, currentAge)

        return SudarshanaChakraResult(
            currentAge = currentAge,
            lagnaReference = lagnaSign,
            moonReference = moonSign,
            sunReference = sunSign,
            lagnaChakra = lagnaChakra,
            chandraChakra = moonChakra,
            suryaChakra = sunChakra,
            synthesis = synthesis,
            yearlyProgression = yearlyProgression,
            recommendations = generateRecommendations(synthesis)
        )
    }

    /**
     * Calculate chakra analysis from a specific reference point
     */
    private fun calculateChakraFromReference(
        chart: VedicChart,
        referenceSign: ZodiacSign,
        age: Int,
        reference: SudarshanaReference
    ): ChakraAnalysis {
        val cycleNumber = ((age - 1) / FULL_CYCLE_YEARS) + 1
        val houseInCycle = ((age - 1) % FULL_CYCLE_YEARS) + 1
        val activeSign = ZodiacSign.entries[(referenceSign.ordinal + houseInCycle - 1) % 12]
        val signLord = activeSign.ruler

        val planetsInActiveSign = chart.planetPositions.filter { it.sign == activeSign }
        val aspectingPlanets = getAspectingPlanets(chart, activeSign)

        val houseSignificance = getHouseSignificance(houseInCycle)
        val signEffects = getSignEffects(activeSign, signLord, chart)
        val planetaryInfluences = analyzePlanetaryInfluences(planetsInActiveSign, aspectingPlanets)

        val strength = calculateChakraStrength(
            signLord, planetsInActiveSign, aspectingPlanets, chart
        )

        return ChakraAnalysis(
            reference = reference,
            cycleNumber = cycleNumber,
            activeHouse = houseInCycle,
            activeSign = activeSign,
            signLord = signLord,
            signLordPosition = chart.planetPositions.find { it.planet == signLord },
            planetsInSign = planetsInActiveSign,
            aspectingPlanets = aspectingPlanets,
            houseSignificance = houseSignificance,
            signEffects = signEffects,
            planetaryInfluences = planetaryInfluences,
            strength = strength
        )
    }

    private fun getAspectingPlanets(chart: VedicChart, targetSign: ZodiacSign): List<AspectingPlanet> {
        val aspectingPlanets = mutableListOf<AspectingPlanet>()

        chart.planetPositions.forEach { position ->
            val houseFromPlanet = ((targetSign.ordinal - position.sign.ordinal + 12) % 12) + 1

            val aspectStrength = when (position.planet) {
                Planet.MARS -> when (houseFromPlanet) {
                    4, 7, 8 -> 1.0
                    else -> 0.0
                }
                Planet.JUPITER -> when (houseFromPlanet) {
                    5, 7, 9 -> 1.0
                    else -> 0.0
                }
                Planet.SATURN -> when (houseFromPlanet) {
                    3, 7, 10 -> 1.0
                    else -> 0.0
                }
                Planet.RAHU, Planet.KETU -> when (houseFromPlanet) {
                    5, 7, 9 -> 0.75
                    else -> 0.0
                }
                else -> when (houseFromPlanet) {
                    7 -> 1.0
                    else -> 0.0
                }
            }

            if (aspectStrength > 0) {
                aspectingPlanets.add(
                    AspectingPlanet(
                        planet = position.planet,
                        fromSign = position.sign,
                        aspectStrength = aspectStrength,
                        isBenefic = position.planet in AstrologicalConstants.NATURAL_BENEFICS
                    )
                )
            }
        }

        return aspectingPlanets
    }

    private fun getHouseSignificance(house: Int): HouseSignificance {
        return when (house) {
            1 -> HouseSignificance(
                house = 1,
                primaryThemes = listOf("Self", "Body", "Personality", "New beginnings"),
                secondaryThemes = listOf("Health", "Appearance", "Character"),
                karakas = listOf(Planet.SUN),
                description = "Year of self-focus, health matters, and personal initiatives"
            )
            2 -> HouseSignificance(
                house = 2,
                primaryThemes = listOf("Wealth", "Family", "Speech", "Values"),
                secondaryThemes = listOf("Food", "Face", "Early education"),
                karakas = listOf(Planet.JUPITER, Planet.VENUS),
                description = "Year of financial matters, family affairs, and resource building"
            )
            3 -> HouseSignificance(
                house = 3,
                primaryThemes = listOf("Siblings", "Courage", "Communication", "Short journeys"),
                secondaryThemes = listOf("Skills", "Neighbors", "Hands"),
                karakas = listOf(Planet.MARS, Planet.MERCURY),
                description = "Year of courage, communication, and sibling matters"
            )
            4 -> HouseSignificance(
                house = 4,
                primaryThemes = listOf("Mother", "Home", "Property", "Emotions"),
                secondaryThemes = listOf("Vehicles", "Education", "Peace of mind"),
                karakas = listOf(Planet.MOON),
                description = "Year of home, mother, property, and emotional well-being"
            )
            5 -> HouseSignificance(
                house = 5,
                primaryThemes = listOf("Children", "Romance", "Creativity", "Intelligence"),
                secondaryThemes = listOf("Speculation", "Past-life merit", "Mantras"),
                karakas = listOf(Planet.JUPITER),
                description = "Year of children, romance, creativity, and spiritual practices"
            )
            6 -> HouseSignificance(
                house = 6,
                primaryThemes = listOf("Health", "Enemies", "Service", "Daily work"),
                secondaryThemes = listOf("Debts", "Litigation", "Pets"),
                karakas = listOf(Planet.MARS, Planet.SATURN),
                description = "Year of health focus, overcoming obstacles, and service"
            )
            7 -> HouseSignificance(
                house = 7,
                primaryThemes = listOf("Marriage", "Partnership", "Business", "Foreign travel"),
                secondaryThemes = listOf("Public life", "Contracts", "Others"),
                karakas = listOf(Planet.VENUS),
                description = "Year of relationships, partnerships, and public dealings"
            )
            8 -> HouseSignificance(
                house = 8,
                primaryThemes = listOf("Transformation", "Longevity", "Occult", "Inheritance"),
                secondaryThemes = listOf("Research", "Insurance", "In-laws"),
                karakas = listOf(Planet.SATURN),
                description = "Year of transformation, research, and handling sudden events"
            )
            9 -> HouseSignificance(
                house = 9,
                primaryThemes = listOf("Fortune", "Father", "Dharma", "Higher education"),
                secondaryThemes = listOf("Long journeys", "Religion", "Guru"),
                karakas = listOf(Planet.JUPITER, Planet.SUN),
                description = "Year of fortune, spiritual growth, and father-related matters"
            )
            10 -> HouseSignificance(
                house = 10,
                primaryThemes = listOf("Career", "Status", "Karma", "Authority"),
                secondaryThemes = listOf("Government", "Fame", "Father's profession"),
                karakas = listOf(Planet.SUN, Planet.SATURN, Planet.MERCURY),
                description = "Year of career growth, recognition, and professional achievements"
            )
            11 -> HouseSignificance(
                house = 11,
                primaryThemes = listOf("Gains", "Income", "Friends", "Aspirations"),
                secondaryThemes = listOf("Elder siblings", "Social networks", "Fulfillment"),
                karakas = listOf(Planet.JUPITER),
                description = "Year of gains, fulfillment of desires, and social expansion"
            )
            12 -> HouseSignificance(
                house = 12,
                primaryThemes = listOf("Losses", "Expenses", "Foreign lands", "Liberation"),
                secondaryThemes = listOf("Sleep", "Bed pleasures", "Hidden matters"),
                karakas = listOf(Planet.SATURN, Planet.KETU),
                description = "Year of spiritual growth, foreign connections, and completion of cycles"
            )
            else -> HouseSignificance(
                house = house,
                primaryThemes = emptyList(),
                secondaryThemes = emptyList(),
                karakas = emptyList(),
                description = ""
            )
        }
    }

    private fun getSignEffects(sign: ZodiacSign, lord: Planet, chart: VedicChart): List<String> {
        val lordPosition = chart.planetPositions.find { it.planet == lord }
        val effects = mutableListOf<String>()

        effects.add("${sign.displayName} activated - ${sign.element.lowercase()} element emphasized")

        when (sign.quality) {
            Quality.CARDINAL ->
                effects.add("Movable sign - new initiatives and changes favored")
            Quality.FIXED ->
                effects.add("Fixed sign - stability and consolidation emphasized")
            Quality.MUTABLE ->
                effects.add("Dual sign - adaptability and flexibility needed")
        }

        lordPosition?.let { pos ->
            val houseFromSign = ((pos.sign.ordinal - sign.ordinal + 12) % 12) + 1
            effects.add("Lord ${lord.displayName} in ${houseFromSign} from active sign")

            if (AstrologicalConstants.isExalted(lord, pos.sign)) {
                effects.add("Sign lord is exalted - strong positive results")
            } else if (AstrologicalConstants.isDebilitated(lord, pos.sign)) {
                effects.add("Sign lord is debilitated - extra efforts needed")
            } else {
                effects.add("Sign lord is in normal dignity")
            }
        }

        return effects
    }

    private fun analyzePlanetaryInfluences(
        planetsInSign: List<PlanetPosition>,
        aspectingPlanets: List<AspectingPlanet>
    ): PlanetaryInfluences {
        val beneficCount = planetsInSign.count { it.planet in AstrologicalConstants.NATURAL_BENEFICS } +
                          aspectingPlanets.count { it.isBenefic }
        val maleficCount = planetsInSign.count { it.planet in AstrologicalConstants.NATURAL_MALEFICS } +
                          aspectingPlanets.count { !it.isBenefic }

        val nature = when {
            beneficCount > maleficCount + 1 -> InfluenceNature.STRONGLY_POSITIVE
            beneficCount > maleficCount -> InfluenceNature.POSITIVE
            beneficCount == maleficCount -> InfluenceNature.MIXED
            maleficCount > beneficCount -> InfluenceNature.CHALLENGING
            else -> InfluenceNature.STRONGLY_CHALLENGING
        }

        val effects = mutableListOf<String>()
        planetsInSign.forEach { pos ->
            effects.add("${pos.planet.displayName} present - direct influence on year's themes")
        }
        aspectingPlanets.forEach { asp ->
            effects.add("${asp.planet.displayName} aspects from ${asp.fromSign.displayName}")
        }

        return PlanetaryInfluences(
            beneficCount = beneficCount,
            maleficCount = maleficCount,
            nature = nature,
            effects = effects
        )
    }

    private fun calculateChakraStrength(
        signLord: Planet,
        planetsInSign: List<PlanetPosition>,
        aspectingPlanets: List<AspectingPlanet>,
        chart: VedicChart
    ): ChakraStrength {
        var score = 50.0

        val lordPosition = chart.planetPositions.find { it.planet == signLord }
        lordPosition?.let { pos ->
            if (AstrologicalConstants.isExalted(signLord, pos.sign)) score += 20.0
            if (AstrologicalConstants.isInOwnSign(signLord, pos.sign)) score += 15.0
            if (AstrologicalConstants.isDebilitated(signLord, pos.sign)) score -= 20.0
            if (pos.house in AstrologicalConstants.KENDRA_HOUSES) score += 10.0
            if (pos.house in AstrologicalConstants.TRIKONA_HOUSES) score += 10.0
            if (pos.house in AstrologicalConstants.DUSTHANA_HOUSES) score -= 10.0
        }

        planetsInSign.forEach { pos ->
            if (pos.planet in AstrologicalConstants.NATURAL_BENEFICS) score += 8.0
            if (pos.planet in AstrologicalConstants.NATURAL_MALEFICS) score -= 5.0
        }

        aspectingPlanets.forEach { asp ->
            if (asp.isBenefic) score += 5.0 * asp.aspectStrength
            else score -= 3.0 * asp.aspectStrength
        }

        score = score.coerceIn(0.0, 100.0)

        val level = when {
            score >= 75 -> StrengthLevel.EXCELLENT
            score >= 60 -> StrengthLevel.GOOD
            score >= 45 -> StrengthLevel.MODERATE
            score >= 30 -> StrengthLevel.WEAK
            else -> StrengthLevel.VERY_WEAK
        }

        return ChakraStrength(
            score = score,
            level = level,
            factors = buildList {
                add("Sign lord ${signLord.displayName} dignity: ${lordPosition?.let { getDignitySummary(signLord, it.sign) } ?: "Unknown"}")
                if (planetsInSign.isNotEmpty()) add("${planetsInSign.size} planets in active sign")
                if (aspectingPlanets.isNotEmpty()) add("${aspectingPlanets.size} planets aspecting")
            }
        )
    }

    private fun getDignitySummary(planet: Planet, sign: ZodiacSign): String {
        return when {
            AstrologicalConstants.isExalted(planet, sign) -> "Exalted"
            AstrologicalConstants.isInOwnSign(planet, sign) -> "Own Sign"
            AstrologicalConstants.isDebilitated(planet, sign) -> "Debilitated"
            else -> "Normal"
        }
    }

    private fun synthesizeResults(
        lagnaChakra: ChakraAnalysis,
        moonChakra: ChakraAnalysis,
        sunChakra: ChakraAnalysis,
        chart: VedicChart
    ): SudarshanasSynthesis {
        val combinedStrength = (lagnaChakra.strength.score +
                               moonChakra.strength.score +
                               sunChakra.strength.score) / 3

        val commonThemes = findCommonThemes(lagnaChakra, moonChakra, sunChakra)
        val conflictingThemes = findConflictingThemes(lagnaChakra, moonChakra, sunChakra)

        val overallAssessment = when {
            combinedStrength >= 65 && conflictingThemes.isEmpty() ->
                "Highly favorable year with aligned energies from all three references"
            combinedStrength >= 55 ->
                "Generally positive year with good support from chart references"
            combinedStrength >= 45 ->
                "Mixed year requiring balanced approach to different life areas"
            else ->
                "Challenging year requiring extra effort and remedial measures"
        }

        val primaryFocus = determinePrimaryFocus(lagnaChakra, moonChakra, sunChakra)
        val secondaryFocus = determineSecondaryFocus(lagnaChakra, moonChakra, sunChakra, primaryFocus)

        return SudarshanasSynthesis(
            combinedStrengthScore = combinedStrength,
            overallAssessment = overallAssessment,
            commonThemes = commonThemes,
            conflictingThemes = conflictingThemes,
            primaryFocus = primaryFocus,
            secondaryFocus = secondaryFocus,
            lagnaContribution = "Physical matters, personality, and health - ${lagnaChakra.strength.level.name}",
            chandraContribution = "Mind, emotions, and public life - ${moonChakra.strength.level.name}",
            suryaContribution = "Soul, authority, and career - ${sunChakra.strength.level.name}"
        )
    }

    private fun findCommonThemes(
        lagna: ChakraAnalysis,
        moon: ChakraAnalysis,
        sun: ChakraAnalysis
    ): List<String> {
        val allThemes = mutableListOf<String>()

        if (lagna.activeHouse == moon.activeHouse || lagna.activeHouse == sun.activeHouse ||
            moon.activeHouse == sun.activeHouse) {
            allThemes.add("Multiple references point to ${getHouseName(lagna.activeHouse)} house themes")
        }

        val allKarakas = (lagna.houseSignificance.karakas +
                        moon.houseSignificance.karakas +
                        sun.houseSignificance.karakas)
        val commonKarakas = allKarakas.groupingBy { it }.eachCount().filter { it.value > 1 }
        commonKarakas.forEach { (planet, _) ->
            allThemes.add("${planet.displayName} activated from multiple references")
        }

        return allThemes
    }

    private fun findConflictingThemes(
        lagna: ChakraAnalysis,
        moon: ChakraAnalysis,
        sun: ChakraAnalysis
    ): List<String> {
        val conflicts = mutableListOf<String>()

        val strengthDiff = maxOf(
            kotlin.math.abs(lagna.strength.score - moon.strength.score),
            kotlin.math.abs(moon.strength.score - sun.strength.score),
            kotlin.math.abs(lagna.strength.score - sun.strength.score)
        )

        if (strengthDiff > 30) {
            conflicts.add("Significant variation in strength across references - uneven year expected")
        }

        return conflicts
    }

    private fun determinePrimaryFocus(
        lagna: ChakraAnalysis,
        moon: ChakraAnalysis,
        sun: ChakraAnalysis
    ): String {
        val strongest = listOf(lagna, moon, sun).maxByOrNull { it.strength.score }
        return strongest?.let {
            "${it.houseSignificance.primaryThemes.firstOrNull() ?: "General matters"} from ${it.reference.name} reference"
        } ?: "Balanced focus across all areas"
    }

    private fun determineSecondaryFocus(
        lagna: ChakraAnalysis,
        moon: ChakraAnalysis,
        sun: ChakraAnalysis,
        primaryFocus: String
    ): String {
        val allThemes = (lagna.houseSignificance.primaryThemes +
                        moon.houseSignificance.primaryThemes +
                        sun.houseSignificance.primaryThemes).distinct()
        return allThemes.filterNot { primaryFocus.contains(it) }.take(2).joinToString(", ")
    }

    private fun getHouseName(house: Int): String {
        return when (house) {
            1 -> "1st"
            2 -> "2nd"
            3 -> "3rd"
            else -> "${house}th"
        }
    }

    private fun calculateYearlyProgression(chart: VedicChart, currentAge: Int): YearlyProgression {
        val previousYear = if (currentAge > 1) {
            calculateSudarshanaChakraInternal(chart, currentAge - 1)
        } else null

        val currentYear = calculateSudarshanaChakraInternal(chart, currentAge)
        val nextYear = calculateSudarshanaChakraInternal(chart, currentAge + 1)

        return YearlyProgression(
            previousYearSummary = previousYear?.let { summarizeYear(it) },
            currentYearSummary = summarizeYear(currentYear),
            nextYearSummary = summarizeYear(nextYear),
            trend = determineTrend(previousYear, currentYear, nextYear)
        )
    }

    private fun calculateSudarshanaChakraInternal(chart: VedicChart, age: Int): InternalChakraData {
        val lagnaSign = ZodiacSign.fromLongitude(chart.ascendant)
        val moonSign = chart.planetPositions.find { it.planet == Planet.MOON }?.sign ?: lagnaSign
        val sunSign = chart.planetPositions.find { it.planet == Planet.SUN }?.sign ?: lagnaSign

        val houseFromLagna = ((age - 1) % FULL_CYCLE_YEARS) + 1
        val activeLagnaSign = ZodiacSign.entries[(lagnaSign.ordinal + houseFromLagna - 1) % 12]
        val activeMoonSign = ZodiacSign.entries[(moonSign.ordinal + houseFromLagna - 1) % 12]
        val activeSunSign = ZodiacSign.entries[(sunSign.ordinal + houseFromLagna - 1) % 12]

        return InternalChakraData(
            age = age,
            lagnaHouse = houseFromLagna,
            lagnaSign = activeLagnaSign,
            moonHouse = houseFromLagna,
            moonSign = activeMoonSign,
            sunHouse = houseFromLagna,
            sunSign = activeSunSign
        )
    }

    private fun summarizeYear(data: InternalChakraData): String {
        return "Age ${data.age}: ${getHouseName(data.lagnaHouse)} house activated - " +
               "${getHouseSignificance(data.lagnaHouse).primaryThemes.take(2).joinToString(", ")}"
    }

    private fun determineTrend(
        previous: InternalChakraData?,
        current: InternalChakraData,
        next: InternalChakraData
    ): String {
        val currentHouse = current.lagnaHouse

        return when {
            currentHouse in listOf(1, 5, 9, 10, 11) -> "Favorable period in annual cycle"
            currentHouse in listOf(6, 8, 12) -> "Challenging period requiring extra attention"
            else -> "Neutral period with mixed opportunities"
        }
    }

    private fun generateRecommendations(synthesis: SudarshanasSynthesis): List<String> {
        val recommendations = mutableListOf<String>()

        when {
            synthesis.combinedStrengthScore >= 65 -> {
                recommendations.add("Favorable year - take initiative in ${synthesis.primaryFocus}")
                recommendations.add("Expand activities related to ${synthesis.secondaryFocus}")
            }
            synthesis.combinedStrengthScore >= 45 -> {
                recommendations.add("Balanced approach needed - focus on ${synthesis.primaryFocus}")
                recommendations.add("Be patient with ${synthesis.conflictingThemes.firstOrNull() ?: "challenges"}")
            }
            else -> {
                recommendations.add("Challenging year - strengthen remedies for weak areas")
                recommendations.add("Focus on spiritual practices and patience")
            }
        }

        recommendations.add("Review all three references monthly for detailed guidance")

        return recommendations
    }
}

// Data classes
data class SudarshanaChakraResult(
    val currentAge: Int,
    val lagnaReference: ZodiacSign,
    val moonReference: ZodiacSign,
    val sunReference: ZodiacSign,
    val lagnaChakra: ChakraAnalysis,
    val chandraChakra: ChakraAnalysis,
    val suryaChakra: ChakraAnalysis,
    val synthesis: SudarshanasSynthesis,
    val yearlyProgression: YearlyProgression,
    val recommendations: List<String>
)

enum class SudarshanaReference {
    LAGNA, CHANDRA, SURYA
}

data class ChakraAnalysis(
    val reference: SudarshanaReference,
    val cycleNumber: Int,
    val activeHouse: Int,
    val activeSign: ZodiacSign,
    val signLord: Planet,
    val signLordPosition: PlanetPosition?,
    val planetsInSign: List<PlanetPosition>,
    val aspectingPlanets: List<AspectingPlanet>,
    val houseSignificance: HouseSignificance,
    val signEffects: List<String>,
    val planetaryInfluences: PlanetaryInfluences,
    val strength: ChakraStrength
)

data class AspectingPlanet(
    val planet: Planet,
    val fromSign: ZodiacSign,
    val aspectStrength: Double,
    val isBenefic: Boolean
)

data class HouseSignificance(
    val house: Int,
    val primaryThemes: List<String>,
    val secondaryThemes: List<String>,
    val karakas: List<Planet>,
    val description: String
)

data class PlanetaryInfluences(
    val beneficCount: Int,
    val maleficCount: Int,
    val nature: InfluenceNature,
    val effects: List<String>
)

enum class InfluenceNature {
    STRONGLY_POSITIVE, POSITIVE, MIXED, CHALLENGING, STRONGLY_CHALLENGING
}

data class ChakraStrength(
    val score: Double,
    val level: StrengthLevel,
    val factors: List<String>
)

enum class StrengthLevel {
    EXCELLENT, GOOD, MODERATE, WEAK, VERY_WEAK
}

data class SudarshanasSynthesis(
    val combinedStrengthScore: Double,
    val overallAssessment: String,
    val commonThemes: List<String>,
    val conflictingThemes: List<String>,
    val primaryFocus: String,
    val secondaryFocus: String,
    val lagnaContribution: String,
    val chandraContribution: String,
    val suryaContribution: String
)

data class YearlyProgression(
    val previousYearSummary: String?,
    val currentYearSummary: String,
    val nextYearSummary: String,
    val trend: String
)

data class InternalChakraData(
    val age: Int,
    val lagnaHouse: Int,
    val lagnaSign: ZodiacSign,
    val moonHouse: Int,
    val moonSign: ZodiacSign,
    val sunHouse: Int,
    val sunSign: ZodiacSign
)

data class SudarshanaTimeline(
    val result: SudarshanaChakraResult,
    val natalChart: VedicChart,
    val currentAge: Int
) {
    /** Convenience accessor for lagna sign */
    val lagnaSign: ZodiacSign get() = result.lagnaReference

    /** Convenience accessor for moon sign */
    val moonSign: ZodiacSign get() = result.moonReference

    /** Convenience accessor for sun sign */
    val sunSign: ZodiacSign get() = result.sunReference

    /** Generate yearly analysis for display */
    val yearlyAnalysis: List<YearlyAnalysis> by lazy {
        val birthYear = natalChart.birthData.dateTime.year
        (0..100).mapNotNull { age ->
            try {
                val result = SudarshanaChakraDashaCalculator.calculateSudarshanaChakra(natalChart, age)
                val houseFromLagna = ((age - 1).coerceAtLeast(0) % 12) + 1
                val activeLagnaSign = ZodiacSign.entries[(lagnaSign.ordinal + houseFromLagna - 1) % 12]
                val activeMoonSign = ZodiacSign.entries[(moonSign.ordinal + houseFromLagna - 1) % 12]
                val activeSunSign = ZodiacSign.entries[(sunSign.ordinal + houseFromLagna - 1) % 12]

                YearlyAnalysis(
                    year = birthYear + age,
                    age = age,
                    lagnaSign = activeLagnaSign,
                    moonSign = activeMoonSign,
                    sunSign = activeSunSign,
                    combinedStrength = result.synthesis.combinedStrengthScore,
                    combinedEffects = result.synthesis.commonThemes + listOf(
                        result.lagnaChakra.houseSignificance.description,
                        result.chandraChakra.houseSignificance.description,
                        result.suryaChakra.houseSignificance.description
                    ).filter { it.isNotEmpty() }
                )
            } catch (e: Exception) {
                null
            }
        }
    }
}

data class YearlyAnalysis(
    val year: Int,
    val age: Int,
    val lagnaSign: ZodiacSign,
    val moonSign: ZodiacSign,
    val sunSign: ZodiacSign,
    val combinedStrength: Double,
    val combinedEffects: List<String>
)
