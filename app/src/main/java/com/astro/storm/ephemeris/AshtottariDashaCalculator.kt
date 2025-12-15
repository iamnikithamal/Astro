package com.astro.storm.ephemeris

import com.astro.storm.data.model.Nakshatra
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import com.astro.storm.ephemeris.DashaCalculator.Mahadasha

/**
 * Ashtottari Dasha Calculator
 *
 * 108-year Dasha cycle using 8 planets (excluding Ketu).
 * Applicable when Rahu is in Kendra or Trikona from Lagna lord.
 *
 * Reference: BPHS Chapter 46, Uttara Kalamrita
 */
object AshtottariDashaCalculator {

    /** Total Ashtottari cycle: 108 years */
    const val TOTAL_CYCLE_YEARS = 108.0

    /** Days per year for calculations */
    private const val DAYS_PER_YEAR = 365.25

    /**
     * Ashtottari Dasha periods in years
     * Total: 6+15+8+17+10+19+12+21 = 108 years
     */
    private val ASHTOTTARI_PERIODS = mapOf(
        Planet.SUN to 6.0,
        Planet.MOON to 15.0,
        Planet.MARS to 8.0,
        Planet.MERCURY to 17.0,
        Planet.SATURN to 10.0,
        Planet.JUPITER to 19.0,
        Planet.RAHU to 12.0,
        Planet.VENUS to 21.0
    )

    /** Ashtottari Dasha sequence */
    private val ASHTOTTARI_SEQUENCE = listOf(
        Planet.SUN, Planet.MOON, Planet.MARS, Planet.MERCURY,
        Planet.SATURN, Planet.JUPITER, Planet.RAHU, Planet.VENUS
    )

    /**
     * Nakshatra to starting planet mapping for Ashtottari
     * Different from Vimsottari - uses only specific nakshatras
     */
    private val NAKSHATRA_LORD_ASHTOTTARI = mapOf(
        Nakshatra.ARDRA to Planet.SUN,
        Nakshatra.PUNARVASU to Planet.MOON,
        Nakshatra.PUSHYA to Planet.MARS,
        Nakshatra.ASHLESHA to Planet.MERCURY,
        Nakshatra.MAGHA to Planet.SATURN,
        Nakshatra.PURVA_PHALGUNI to Planet.JUPITER,
        Nakshatra.UTTARA_PHALGUNI to Planet.RAHU,
        Nakshatra.HASTA to Planet.VENUS,
        Nakshatra.CHITRA to Planet.SUN,
        Nakshatra.SWATI to Planet.MOON,
        Nakshatra.VISHAKHA to Planet.MARS,
        Nakshatra.ANURADHA to Planet.MERCURY,
        Nakshatra.JYESHTHA to Planet.SATURN,
        Nakshatra.MULA to Planet.JUPITER,
        Nakshatra.PURVA_ASHADHA to Planet.RAHU,
        Nakshatra.UTTARA_ASHADHA to Planet.VENUS,
        Nakshatra.SHRAVANA to Planet.SUN,
        Nakshatra.DHANISHTHA to Planet.MOON,
        Nakshatra.SHATABHISHA to Planet.MARS,
        Nakshatra.PURVA_BHADRAPADA to Planet.MERCURY,
        Nakshatra.UTTARA_BHADRAPADA to Planet.SATURN,
        Nakshatra.REVATI to Planet.JUPITER,
        Nakshatra.ASHWINI to Planet.RAHU,
        Nakshatra.BHARANI to Planet.VENUS,
        Nakshatra.KRITTIKA to Planet.SUN,
        Nakshatra.ROHINI to Planet.MOON,
        Nakshatra.MRIGASHIRA to Planet.MARS
    )

    /**
     * Check if Ashtottari Dasha is applicable
     * Applicable when Rahu is in Kendra (1,4,7,10) or Trikona (1,5,9) from Lagna lord
     */
    fun isApplicable(chart: VedicChart): AshtottariApplicability {
        val lagnaSign = ZodiacSign.fromLongitude(chart.ascendant)
        val lagnaLord = lagnaSign.ruler
        val lagnaLordPosition = chart.planetPositions.find { it.planet == lagnaLord }
        val rahuPosition = chart.planetPositions.find { it.planet == Planet.RAHU }

        if (lagnaLordPosition == null || rahuPosition == null) {
            return AshtottariApplicability(
                isApplicable = false,
                reason = "Required planetary positions not found",
                rahuFromLagnaLord = null
            )
        }

        val lagnaLordSign = lagnaLordPosition.sign
        val rahuSign = rahuPosition.sign

        val houseFromLagnaLord = ((rahuSign.ordinal - lagnaLordSign.ordinal + 12) % 12) + 1

        val isKendra = houseFromLagnaLord in AstrologicalConstants.KENDRA_HOUSES
        val isTrikona = houseFromLagnaLord in AstrologicalConstants.TRIKONA_HOUSES

        val isApplicable = isKendra || isTrikona

        val reason = when {
            isKendra -> "Rahu in ${getHouseName(houseFromLagnaLord)} (Kendra) from Lagna lord ${lagnaLord.displayName}"
            isTrikona -> "Rahu in ${getHouseName(houseFromLagnaLord)} (Trikona) from Lagna lord ${lagnaLord.displayName}"
            else -> "Rahu in ${getHouseName(houseFromLagnaLord)} house from Lagna lord - not in Kendra/Trikona"
        }

        return AshtottariApplicability(
            isApplicable = isApplicable,
            reason = reason,
            rahuFromLagnaLord = houseFromLagnaLord,
            lagnaLord = lagnaLord,
            rahuSign = rahuSign
        )
    }

    /**
     * Calculate complete Ashtottari Dasha periods
     */
    fun calculateAshtottariDasha(chart: VedicChart): AshtottariDashaResult {
        val applicability = isApplicable(chart)
        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
            ?: throw IllegalArgumentException("Moon position required")

        val nakshatraResult = Nakshatra.fromLongitude(moonPosition.longitude)
        val moonNakshatra = nakshatraResult.first
        val moonProgressInNakshatra = (moonPosition.longitude % (360.0 / 27.0)) / (360.0 / 27.0)

        val startingLord: Planet = NAKSHATRA_LORD_ASHTOTTARI[moonNakshatra] ?: Planet.SUN

        val startingPeriod = ASHTOTTARI_PERIODS[startingLord] ?: 6.0
        val balanceInFirstDasha = startingPeriod * (1 - moonProgressInNakshatra)

        val birthDateTime = chart.birthData.dateTime
        val mahadashas = calculateMahadashas(
            birthDateTime, startingLord, balanceInFirstDasha
        )

        val currentDasha = findCurrentDasha(mahadashas, LocalDateTime.now())
        val currentAntardasha = currentDasha?.let { md ->
            calculateAntardashas(md).find { ad ->
                LocalDateTime.now().isAfter(ad.startDate) &&
                LocalDateTime.now().isBefore(ad.endDate)
            }
        }

        return AshtottariDashaResult(
            applicability = applicability,
            moonNakshatra = moonNakshatra,
            startingLord = startingLord,
            balanceAtBirth = balanceInFirstDasha,
            mahadashas = mahadashas,
            currentMahadasha = currentDasha,
            currentAntardasha = currentAntardasha,
            interpretation = generateInterpretation(currentDasha, currentAntardasha, chart)
        )
    }

    /**
     * Calculate all Mahadasha periods
     */
    private fun calculateMahadashas(
        birthDateTime: LocalDateTime,
        startingLord: Planet,
        balanceInFirst: Double
    ): List<AshtottariMahadasha> {
        val mahadashas = mutableListOf<AshtottariMahadasha>()
        var currentDate = birthDateTime
        val startIndex = ASHTOTTARI_SEQUENCE.indexOf(startingLord)

        for (cycle in 0..2) {
            for (i in ASHTOTTARI_SEQUENCE.indices) {
                val planetIndex = (startIndex + i) % ASHTOTTARI_SEQUENCE.size
                val planet = ASHTOTTARI_SEQUENCE[planetIndex]
                val period = ASHTOTTARI_PERIODS[planet] ?: continue

                val actualPeriod = if (mahadashas.isEmpty()) balanceInFirst else period
                val endDate = currentDate.plusDays((actualPeriod * DAYS_PER_YEAR).toLong())

                mahadashas.add(
                    AshtottariMahadasha(
                        planet = planet,
                        periodYears = period,
                        actualYears = actualPeriod,
                        startDate = currentDate,
                        endDate = endDate,
                        isCurrentlyRunning = LocalDateTime.now().isAfter(currentDate) &&
                                            LocalDateTime.now().isBefore(endDate)
                    )
                )

                currentDate = endDate

                if (currentDate.isAfter(birthDateTime.plusYears(120))) break
            }
            if (currentDate.isAfter(birthDateTime.plusYears(120))) break
        }

        return mahadashas
    }

    /**
     * Calculate Antardashas within a Mahadasha
     */
    fun calculateAntardashas(mahadasha: AshtottariMahadasha): List<AshtottariAntardasha> {
        val antardashas = mutableListOf<AshtottariAntardasha>()
        val mahadashaPlanetIndex = ASHTOTTARI_SEQUENCE.indexOf(mahadasha.planet)
        var currentDate = mahadasha.startDate
        val totalDays = ChronoUnit.DAYS.between(mahadasha.startDate, mahadasha.endDate)

        for (i in ASHTOTTARI_SEQUENCE.indices) {
            val antarIndex = (mahadashaPlanetIndex + i) % ASHTOTTARI_SEQUENCE.size
            val antarPlanet = ASHTOTTARI_SEQUENCE[antarIndex]
            val antarPeriod = ASHTOTTARI_PERIODS[antarPlanet] ?: continue

            val proportion = antarPeriod / TOTAL_CYCLE_YEARS
            val antarDays = (totalDays * proportion).toLong()
            val endDate = currentDate.plusDays(antarDays)

            val relationship = getPlanetRelationship(mahadasha.planet, antarPlanet)

            antardashas.add(
                AshtottariAntardasha(
                    mahadashaLord = mahadasha.planet,
                    antardashaLord = antarPlanet,
                    periodYears = (antarDays / DAYS_PER_YEAR),
                    startDate = currentDate,
                    endDate = endDate,
                    relationship = relationship,
                    isCurrentlyRunning = LocalDateTime.now().isAfter(currentDate) &&
                                        LocalDateTime.now().isBefore(endDate)
                )
            )

            currentDate = endDate
        }

        return antardashas
    }

    /**
     * Calculate Pratyantardashas
     */
    fun calculatePratyantardashas(antardasha: AshtottariAntardasha): List<AshtottariPratyantardasha> {
        val pratyantardashas = mutableListOf<AshtottariPratyantardasha>()
        val antarIndex = ASHTOTTARI_SEQUENCE.indexOf(antardasha.antardashaLord)
        var currentDate = antardasha.startDate
        val totalDays = ChronoUnit.DAYS.between(antardasha.startDate, antardasha.endDate)

        for (i in ASHTOTTARI_SEQUENCE.indices) {
            val pratyIndex = (antarIndex + i) % ASHTOTTARI_SEQUENCE.size
            val pratyPlanet = ASHTOTTARI_SEQUENCE[pratyIndex]
            val pratyPeriod = ASHTOTTARI_PERIODS[pratyPlanet] ?: continue

            val proportion = pratyPeriod / TOTAL_CYCLE_YEARS
            val pratyDays = (totalDays * proportion).toLong()
            val endDate = currentDate.plusDays(pratyDays)

            pratyantardashas.add(
                AshtottariPratyantardasha(
                    mahadashaLord = antardasha.mahadashaLord,
                    antardashaLord = antardasha.antardashaLord,
                    pratyantardashaLord = pratyPlanet,
                    startDate = currentDate,
                    endDate = endDate
                )
            )

            currentDate = endDate
        }

        return pratyantardashas
    }

    private fun findCurrentDasha(
        mahadashas: List<AshtottariMahadasha>,
        date: LocalDateTime
    ): AshtottariMahadasha? {
        return mahadashas.find {
            date.isAfter(it.startDate) && date.isBefore(it.endDate)
        }
    }

    private fun getPlanetRelationship(planet1: Planet, planet2: Planet): PlanetRelationship {
        if (planet1 == planet2) return PlanetRelationship.SAME

        val friends1 = AstrologicalConstants.NATURAL_FRIENDS[planet1] ?: emptySet()
        val enemies1 = AstrologicalConstants.NATURAL_ENEMIES[planet1] ?: emptySet()

        return when {
            planet2 in friends1 -> PlanetRelationship.FRIEND
            planet2 in enemies1 -> PlanetRelationship.ENEMY
            else -> PlanetRelationship.NEUTRAL
        }
    }

    private fun getHouseName(house: Int): String {
        return when (house) {
            1 -> "1st"
            2 -> "2nd"
            3 -> "3rd"
            else -> "${house}th"
        }
    }

    private fun generateInterpretation(
        mahadasha: AshtottariMahadasha?,
        antardasha: AshtottariAntardasha?,
        chart: VedicChart
    ): AshtottariInterpretation {
        if (mahadasha == null) {
            return AshtottariInterpretation(
                mahadashaEffects = emptyList(),
                antardashaEffects = emptyList(),
                combinedEffects = "Current Ashtottari Dasha period could not be determined",
                keyThemes = emptyList(),
                recommendations = emptyList()
            )
        }

        val mdPlanet = mahadasha.planet
        val mdPosition = chart.planetPositions.find { it.planet == mdPlanet }

        val mdEffects = getMahadashaEffects(mdPlanet, mdPosition)

        val adEffects = antardasha?.let {
            val adPosition = chart.planetPositions.find { pos -> pos.planet == it.antardashaLord }
            getAntardashaEffects(it.antardashaLord, adPosition, it.relationship)
        } ?: emptyList()

        val combinedEffects = generateCombinedEffects(mahadasha, antardasha)
        val keyThemes = generateKeyThemes(mdPlanet, antardasha?.antardashaLord, chart)
        val recommendations = generateRecommendations(mdPlanet, antardasha?.antardashaLord)

        return AshtottariInterpretation(
            mahadashaEffects = mdEffects,
            antardashaEffects = adEffects,
            combinedEffects = combinedEffects,
            keyThemes = keyThemes,
            recommendations = recommendations
        )
    }

    private fun getMahadashaEffects(planet: Planet, position: com.astro.storm.data.model.PlanetPosition?): List<String> {
        val baseEffects = when (planet) {
            Planet.SUN -> listOf(
                "Focus on authority, career advancement, and self-expression",
                "Government matters and father figures become prominent",
                "Health consciousness increases, especially heart and vitality"
            )
            Planet.MOON -> listOf(
                "Emotional matters and mental peace take center stage",
                "Mother and maternal figures become important",
                "Public recognition and popularity may increase"
            )
            Planet.MARS -> listOf(
                "Energy, courage, and initiative are heightened",
                "Property matters and siblings come into focus",
                "Competitive activities and physical pursuits favored"
            )
            Planet.MERCURY -> listOf(
                "Communication, learning, and business activities increase",
                "Intellectual pursuits and writing abilities highlighted",
                "Travel and trade opportunities arise"
            )
            Planet.SATURN -> listOf(
                "Karma and discipline become prominent themes",
                "Hard work brings gradual rewards",
                "Service to elders and workers brings blessings"
            )
            Planet.JUPITER -> listOf(
                "Wisdom, expansion, and fortune increase",
                "Spiritual growth and religious activities favored",
                "Teachers, mentors, and children become important"
            )
            Planet.RAHU -> listOf(
                "Unconventional paths and foreign connections highlighted",
                "Material desires and worldly achievements focus",
                "Sudden changes and unexpected events possible"
            )
            Planet.VENUS -> listOf(
                "Relationships, arts, and pleasures become prominent",
                "Marriage, luxury, and comforts are themes",
                "Creative expression and beauty matters increase"
            )
            else -> emptyList()
        }

        val dignityEffects = position?.let { pos ->
            when {
                AstrologicalConstants.isExalted(planet, pos.sign) ->
                    "Planet is exalted - exceptional results expected"
                AstrologicalConstants.isInOwnSign(planet, pos.sign) ->
                    "Planet in own sign - strong and favorable results"
                AstrologicalConstants.isDebilitated(planet, pos.sign) ->
                    "Planet is debilitated - results may face challenges"
                else -> null
            }
        }

        return if (dignityEffects != null) baseEffects + dignityEffects else baseEffects
    }

    private fun getAntardashaEffects(
        planet: Planet,
        position: com.astro.storm.data.model.PlanetPosition?,
        relationship: PlanetRelationship
    ): List<String> {
        val baseEffects = when (planet) {
            Planet.SUN -> listOf("Authority matters and leadership opportunities", "Health and vitality focus")
            Planet.MOON -> listOf("Emotional developments and mental peace", "Public dealings and popularity")
            Planet.MARS -> listOf("Energy and initiative for new ventures", "Property and sibling matters")
            Planet.MERCURY -> listOf("Communication and business activities", "Learning and intellectual growth")
            Planet.SATURN -> listOf("Discipline and karmic lessons", "Service and responsibility themes")
            Planet.JUPITER -> listOf("Wisdom and spiritual growth", "Expansion and good fortune")
            Planet.RAHU -> listOf("Unconventional opportunities", "Foreign or innovative connections")
            Planet.VENUS -> listOf("Relationship and artistic matters", "Luxury and comfort improvements")
            else -> emptyList()
        }

        val relationshipEffect = when (relationship) {
            PlanetRelationship.FRIEND -> "Friendly period - harmonious results expected"
            PlanetRelationship.ENEMY -> "Challenging period - extra effort required"
            PlanetRelationship.NEUTRAL -> "Neutral period - mixed results based on chart"
            PlanetRelationship.SAME -> "Same planet period - intensified effects"
        }

        return baseEffects + relationshipEffect
    }

    private fun generateCombinedEffects(
        mahadasha: AshtottariMahadasha,
        antardasha: AshtottariAntardasha?
    ): String {
        val mdPlanet = mahadasha.planet.displayName
        val adPlanet = antardasha?.antardashaLord?.displayName ?: "N/A"

        return if (antardasha != null) {
            when (antardasha.relationship) {
                PlanetRelationship.FRIEND ->
                    "$mdPlanet Mahadasha with $adPlanet Antardasha creates harmonious energy for growth and success"
                PlanetRelationship.ENEMY ->
                    "$mdPlanet Mahadasha with $adPlanet Antardasha may bring challenges requiring careful navigation"
                PlanetRelationship.NEUTRAL ->
                    "$mdPlanet Mahadasha with $adPlanet Antardasha gives balanced results based on individual chart placements"
                PlanetRelationship.SAME ->
                    "$mdPlanet Mahadasha with $mdPlanet Antardasha intensifies all ${mahadasha.planet.displayName}-related themes"
            }
        } else {
            "$mdPlanet Mahadasha is currently active with its primary influences"
        }
    }

    private fun generateKeyThemes(
        mdPlanet: Planet,
        adPlanet: Planet?,
        chart: VedicChart
    ): List<String> {
        val themes = mutableListOf<String>()

        when (mdPlanet) {
            Planet.SUN -> themes.addAll(listOf("Authority", "Career", "Father", "Health", "Government"))
            Planet.MOON -> themes.addAll(listOf("Mind", "Mother", "Public", "Emotions", "Travel"))
            Planet.MARS -> themes.addAll(listOf("Energy", "Property", "Siblings", "Competition", "Courage"))
            Planet.MERCURY -> themes.addAll(listOf("Communication", "Business", "Learning", "Writing", "Trade"))
            Planet.SATURN -> themes.addAll(listOf("Karma", "Discipline", "Service", "Hard work", "Longevity"))
            Planet.JUPITER -> themes.addAll(listOf("Wisdom", "Fortune", "Spirituality", "Children", "Teachers"))
            Planet.RAHU -> themes.addAll(listOf("Foreign", "Technology", "Innovation", "Desires", "Sudden events"))
            Planet.VENUS -> themes.addAll(listOf("Relationships", "Arts", "Luxury", "Marriage", "Beauty"))
            else -> {}
        }

        return themes.take(5)
    }

    private fun generateRecommendations(mdPlanet: Planet, adPlanet: Planet?): List<String> {
        val recommendations = mutableListOf<String>()

        recommendations.addAll(when (mdPlanet) {
            Planet.SUN -> listOf(
                "Offer water to Sun at sunrise",
                "Recite Aditya Hridayam on Sundays",
                "Respect authority figures and father"
            )
            Planet.MOON -> listOf(
                "Practice meditation for mental peace",
                "Honor mother and maternal figures",
                "Wear Pearl if astrologically suitable"
            )
            Planet.MARS -> listOf(
                "Recite Hanuman Chalisa",
                "Channel energy through physical exercise",
                "Donate red items on Tuesdays"
            )
            Planet.MERCURY -> listOf(
                "Chant Om Budhaya Namaha",
                "Engage in learning and skill development",
                "Practice clear communication"
            )
            Planet.SATURN -> listOf(
                "Serve the elderly and underprivileged",
                "Practice patience and discipline",
                "Recite Shani Stotram on Saturdays"
            )
            Planet.JUPITER -> listOf(
                "Respect teachers and spiritual guides",
                "Study scriptures and philosophy",
                "Donate yellow items on Thursdays"
            )
            Planet.RAHU -> listOf(
                "Worship Goddess Durga",
                "Avoid shortcuts and unethical paths",
                "Stay grounded during sudden changes"
            )
            Planet.VENUS -> listOf(
                "Honor relationships with respect",
                "Engage in artistic and creative activities",
                "Worship Goddess Lakshmi on Fridays"
            )
            else -> emptyList()
        })

        return recommendations
    }

    /**
     * Compare Ashtottari with Vimsottari Dasha
     */
    fun compareWithVimsottari(
        chart: VedicChart,
        vimsottariResult: DashaCalculator.Mahadasha
    ): DashaComparison {
        val ashtottariResult = calculateAshtottariDasha(chart)

        val vimsottariPlanet = vimsottariResult.planet
        val ashtottariPlanet = ashtottariResult.currentMahadasha?.planet

        val agreement = vimsottariPlanet == ashtottariPlanet

        val analysis = if (agreement) {
            "Both Vimsottari and Ashtottari indicate ${vimsottariPlanet.displayName} period - themes strongly emphasized"
        } else {
            "Vimsottari shows ${vimsottariPlanet.displayName} while Ashtottari shows ${ashtottariPlanet?.displayName ?: "N/A"} - both planetary themes are active"
        }

        return DashaComparison(
            vimsottariMahadasha = vimsottariPlanet,
            ashtottariMahadasha = ashtottariPlanet,
            inAgreement = agreement,
            analysis = analysis,
            recommendation = if (agreement) {
                "Focus strongly on ${vimsottariPlanet.displayName} themes and remedies"
            } else {
                "Consider remedies for both ${vimsottariPlanet.displayName} and ${ashtottariPlanet?.displayName ?: "applicable"} planets"
            }
        )
    }

}

// Data classes
data class AshtottariApplicability(
    val isApplicable: Boolean,
    val reason: String,
    val rahuFromLagnaLord: Int?,
    val lagnaLord: Planet? = null,
    val rahuSign: ZodiacSign? = null
)

data class AshtottariDashaResult(
    val applicability: AshtottariApplicability,
    val moonNakshatra: Nakshatra,
    val startingLord: Planet,
    val balanceAtBirth: Double,
    val mahadashas: List<AshtottariMahadasha>,
    val currentMahadasha: AshtottariMahadasha?,
    val currentAntardasha: AshtottariAntardasha?,
    val interpretation: AshtottariInterpretation
)

data class AshtottariMahadasha(
    val planet: Planet,
    val periodYears: Double,
    val actualYears: Double,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val isCurrentlyRunning: Boolean,
    val durationYears: Double = periodYears,
    val antardashas: List<AshtottariAntardasha> = emptyList()
)

data class AshtottariAntardasha(
    val mahadashaLord: Planet,
    val antardashaLord: Planet,
    val periodYears: Double,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val relationship: PlanetRelationship,
    val isCurrentlyRunning: Boolean
) {
    /** Alias for antardashaLord for easier access */
    val planet: Planet get() = antardashaLord

    /** Calculate the progress percentage through this antardasha */
    fun getProgressPercent(): Double {
        val now = LocalDateTime.now()
        if (now.isBefore(startDate)) return 0.0
        if (now.isAfter(endDate)) return 100.0

        val totalDuration = java.time.Duration.between(startDate, endDate).toMillis().toDouble()
        val elapsedDuration = java.time.Duration.between(startDate, now).toMillis().toDouble()
        return if (totalDuration > 0) (elapsedDuration / totalDuration * 100).coerceIn(0.0, 100.0) else 0.0
    }
}

data class AshtottariPratyantardasha(
    val mahadashaLord: Planet,
    val antardashaLord: Planet,
    val pratyantardashaLord: Planet,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime
)

enum class PlanetRelationship {
    FRIEND, ENEMY, NEUTRAL, SAME
}

data class AshtottariInterpretation(
    val mahadashaEffects: List<String>,
    val antardashaEffects: List<String>,
    val combinedEffects: String,
    val keyThemes: List<String>,
    val recommendations: List<String>
)

data class DashaComparison(
    val vimsottariMahadasha: Planet,
    val ashtottariMahadasha: Planet?,
    val inAgreement: Boolean,
    val analysis: String,
    val recommendation: String
)

data class AshtottariTimeline(
    val mahadashas: List<AshtottariMahadasha>,
    val currentMahadasha: AshtottariMahadasha?,
    val currentAntardasha: AshtottariAntardasha?,
    val natalChart: VedicChart,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val applicability: AshtottariApplicability,
    val interpretation: AshtottariInterpretation,
    val birthNakshatra: Nakshatra,
    val birthNakshatraLord: Planet,
    val birthNakshatraPada: Int
)
