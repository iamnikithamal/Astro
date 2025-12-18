package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import kotlin.math.abs

/**
 * Graha Yuddha (Planetary War) Calculator
 *
 * Comprehensive analysis of planetary wars per Vedic astrology principles.
 * A Graha Yuddha occurs when two planets (excluding Sun, Moon, Rahu, Ketu)
 * are within 1 degree of each other.
 *
 * References:
 * - Brihat Parashara Hora Shastra (BPHS) on Graha Yuddha
 * - Surya Siddhanta on planetary brightness
 * - Phaladeepika on war effects
 * - Jataka Parijata on planetary battles
 */
object GrahaYuddhaCalculator {

    private const val WAR_ORB = 1.0 // Degrees for exact war
    private const val APPROACHING_WAR_ORB = 3.0 // Degrees for approaching war
    private const val WAR_ZONE_ORB = 5.0 // Extended war zone

    /**
     * Planets capable of engaging in Graha Yuddha
     * Sun and Moon are luminaries and don't participate
     * Rahu and Ketu are shadow planets and don't participate
     */
    private val WAR_CAPABLE_PLANETS = setOf(
        Planet.MARS, Planet.MERCURY, Planet.JUPITER, Planet.VENUS, Planet.SATURN
    )

    /**
     * Planetary brightness order (Venus is brightest)
     * Per Surya Siddhanta: Venus > Jupiter > Mars > Mercury > Saturn
     */
    private val BRIGHTNESS_ORDER = listOf(
        Planet.VENUS, Planet.JUPITER, Planet.MARS, Planet.MERCURY, Planet.SATURN
    )

    /**
     * Main analysis entry point
     */
    fun analyzeGrahaYuddha(chart: VedicChart): GrahaYuddhaAnalysis {
        val activeWars = detectActiveWars(chart)
        val approachingWars = detectApproachingWars(chart)
        val warHistory = buildWarHistory(activeWars)
        val planetaryStates = analyzeAllPlanetaryStates(chart, activeWars)
        val dashaEffects = analyzeDashaEffects(chart, activeWars)
        val remedies = generateRemedies(activeWars)
        val interpretation = generateInterpretation(chart, activeWars, planetaryStates)

        return GrahaYuddhaAnalysis(
            activeWars = activeWars,
            approachingWars = approachingWars,
            planetaryStates = planetaryStates,
            hasActiveWar = activeWars.isNotEmpty(),
            mostSignificantWar = activeWars.maxByOrNull { it.intensity },
            dashaWarEffects = dashaEffects,
            remedies = remedies,
            interpretation = interpretation
        )
    }

    /**
     * Detect all active Graha Yuddha in the chart
     */
    private fun detectActiveWars(chart: VedicChart): List<GrahaYuddhaResult> {
        val wars = mutableListOf<GrahaYuddhaResult>()
        val positions = chart.planetPositions.filter { it.planet in WAR_CAPABLE_PLANETS }

        for (i in positions.indices) {
            for (j in i + 1 until positions.size) {
                val p1 = positions[i]
                val p2 = positions[j]
                val separation = calculateAngularDistance(p1.longitude, p2.longitude)

                if (separation <= WAR_ORB) {
                    wars.add(createWarResult(p1, p2, separation, chart))
                }
            }
        }

        return wars.sortedByDescending { it.intensity }
    }

    /**
     * Detect wars that are forming (approaching)
     */
    private fun detectApproachingWars(chart: VedicChart): List<ApproachingWar> {
        val approaching = mutableListOf<ApproachingWar>()
        val positions = chart.planetPositions.filter { it.planet in WAR_CAPABLE_PLANETS }

        for (i in positions.indices) {
            for (j in i + 1 until positions.size) {
                val p1 = positions[i]
                val p2 = positions[j]
                val separation = calculateAngularDistance(p1.longitude, p2.longitude)

                // Check if between 1 and 3 degrees (approaching but not in war)
                if (separation > WAR_ORB && separation <= APPROACHING_WAR_ORB) {
                    // Check if planets are moving toward each other
                    val isApproaching = arePlanetsApproaching(p1, p2)
                    if (isApproaching) {
                        approaching.add(ApproachingWar(
                            planet1 = p1.planet,
                            planet2 = p2.planet,
                            currentSeparation = separation,
                            estimatedDaysToWar = estimateDaysToWar(p1, p2, separation),
                            planet1Position = p1,
                            planet2Position = p2
                        ))
                    }
                }
            }
        }

        return approaching
    }

    /**
     * Create detailed war result
     */
    private fun createWarResult(
        p1: PlanetPosition,
        p2: PlanetPosition,
        separation: Double,
        chart: VedicChart
    ): GrahaYuddhaResult {
        // Determine winner based on classical rules
        val (winner, loser, advantage) = determineWinner(p1, p2)

        val intensity = calculateWarIntensity(separation)
        val sign = ZodiacSign.fromDegree(p1.longitude)
        val house = p1.house

        val winnerEffects = generateWinnerEffects(winner, loser, sign, house)
        val loserEffects = generateLoserEffects(loser, winner, sign, house)
        val houseEffects = generateHouseEffects(house, winner, loser)
        val signEffects = generateSignEffects(sign, winner, loser)

        return GrahaYuddhaResult(
            planet1 = p1.planet,
            planet2 = p2.planet,
            separation = separation,
            winner = winner,
            loser = loser,
            winnerAdvantage = advantage,
            intensity = intensity,
            intensityLevel = getIntensityLevel(intensity),
            warSign = sign,
            warHouse = house,
            winnerPosition = if (winner == p1.planet) p1 else p2,
            loserPosition = if (loser == p1.planet) p1 else p2,
            winnerEffects = winnerEffects,
            loserEffects = loserEffects,
            houseEffects = houseEffects,
            signEffects = signEffects,
            interpretation = generateWarInterpretation(winner, loser, sign, house, intensity)
        )
    }

    /**
     * Determine war winner per classical texts
     *
     * Rules (in order of priority):
     * 1. Northern latitude (higher declination) wins
     * 2. Greater brightness wins (Venus > Jupiter > Mars > Mercury > Saturn)
     * 3. If still tied, slower planet has slight advantage
     */
    private fun determineWinner(
        p1: PlanetPosition,
        p2: PlanetPosition
    ): Triple<Planet, Planet, WarAdvantage> {
        // Check latitude (declination) - simplified as we may not have exact declination
        // Using sign-based approach: planets in northern signs have advantage
        val p1NorthernAdvantage = isInNorthernSign(p1.longitude)
        val p2NorthernAdvantage = isInNorthernSign(p2.longitude)

        if (p1NorthernAdvantage && !p2NorthernAdvantage) {
            return Triple(p1.planet, p2.planet, WarAdvantage.NORTHERN_LATITUDE)
        }
        if (p2NorthernAdvantage && !p1NorthernAdvantage) {
            return Triple(p2.planet, p1.planet, WarAdvantage.NORTHERN_LATITUDE)
        }

        // Check brightness
        val p1BrightnessRank = BRIGHTNESS_ORDER.indexOf(p1.planet)
        val p2BrightnessRank = BRIGHTNESS_ORDER.indexOf(p2.planet)

        return when {
            p1BrightnessRank < p2BrightnessRank -> Triple(p1.planet, p2.planet, WarAdvantage.BRIGHTNESS)
            p2BrightnessRank < p1BrightnessRank -> Triple(p2.planet, p1.planet, WarAdvantage.BRIGHTNESS)
            // Evenly matched - use speed as tiebreaker (slower wins slightly)
            abs(p1.speed) < abs(p2.speed) -> Triple(p1.planet, p2.planet, WarAdvantage.SLOWER_MOTION)
            else -> Triple(p2.planet, p1.planet, WarAdvantage.SLOWER_MOTION)
        }
    }

    /**
     * Check if longitude is in northern zodiac signs
     * Aries through Virgo (0-180°) are considered northern
     */
    private fun isInNorthernSign(longitude: Double): Boolean {
        return longitude in 0.0..180.0
    }

    /**
     * Calculate war intensity (0.0 to 1.0)
     * Closer planets = more intense war
     */
    private fun calculateWarIntensity(separation: Double): Double {
        return ((WAR_ORB - separation) / WAR_ORB).coerceIn(0.0, 1.0)
    }

    private fun getIntensityLevel(intensity: Double): WarIntensity {
        return when {
            intensity >= 0.9 -> WarIntensity.SEVERE
            intensity >= 0.7 -> WarIntensity.INTENSE
            intensity >= 0.4 -> WarIntensity.MODERATE
            else -> WarIntensity.MILD
        }
    }

    private fun calculateAngularDistance(long1: Double, long2: Double): Double {
        val diff = abs(long1 - long2)
        return if (diff > 180) 360 - diff else diff
    }

    private fun arePlanetsApproaching(p1: PlanetPosition, p2: PlanetPosition): Boolean {
        // If both moving in same direction, faster one approaching slower
        // Simplified: if separation would decrease based on relative speeds
        val relativeSpeed = p1.speed - p2.speed
        val positionDiff = p1.longitude - p2.longitude
        return (relativeSpeed * positionDiff) < 0
    }

    private fun estimateDaysToWar(p1: PlanetPosition, p2: PlanetPosition, separation: Double): Int {
        val relativeSpeed = abs(p1.speed - p2.speed)
        return if (relativeSpeed > 0.01) {
            ((separation - WAR_ORB) / relativeSpeed).toInt().coerceAtLeast(1)
        } else {
            999 // Very slow approach
        }
    }

    /**
     * Generate effects for the winning planet
     */
    private fun generateWinnerEffects(
        winner: Planet,
        loser: Planet,
        sign: ZodiacSign,
        house: Int
    ): WinnerEffects {
        val strengthGain = when (winner) {
            Planet.MARS -> "Mars gains aggressive dominance, increased courage and initiative"
            Planet.MERCURY -> "Mercury gains intellectual supremacy, sharper communication"
            Planet.JUPITER -> "Jupiter gains wisdom advantage, expanded influence"
            Planet.VENUS -> "Venus gains charm and attractiveness, enhanced relationships"
            Planet.SATURN -> "Saturn gains persistent power, stronger discipline"
            else -> "Enhanced significations"
        }

        val absorbedQualities = "Absorbs some qualities of ${loser.displayName}, but with a competitive edge"

        val houseGain = "Strengthened influence over ${getHouseSignifications(house)}"

        return WinnerEffects(
            strengthGain = strengthGain,
            absorbedQualities = absorbedQualities,
            houseInfluence = houseGain,
            overallBenefit = "The victory brings extra power but also responsibility. " +
                    "${winner.displayName}'s significations are amplified, though with a combative undertone."
        )
    }

    /**
     * Generate effects for the losing planet
     */
    private fun generateLoserEffects(
        loser: Planet,
        winner: Planet,
        sign: ZodiacSign,
        house: Int
    ): LoserEffects {
        val weaknessAreas = when (loser) {
            Planet.MARS -> listOf("Initiative", "Courage", "Physical vitality", "Competition")
            Planet.MERCURY -> listOf("Communication", "Intelligence", "Business", "Learning")
            Planet.JUPITER -> listOf("Wisdom", "Expansion", "Fortune", "Spirituality")
            Planet.VENUS -> listOf("Relationships", "Luxuries", "Creativity", "Harmony")
            Planet.SATURN -> listOf("Discipline", "Longevity", "Structure", "Career stability")
            else -> listOf("General significations")
        }

        val houseImpact = "Weakened influence over ${getHouseSignifications(house)}, " +
                "matters may face obstacles or delays"

        val manifestationIssues = "Results of ${loser.displayName} manifest with difficulty, " +
                "distortion, or require extra effort. ${winner.displayName} overshadows its significations."

        return LoserEffects(
            weaknessAreas = weaknessAreas,
            houseImpact = houseImpact,
            manifestationIssues = manifestationIssues,
            overallDeficit = "${loser.displayName} is diminished in this chart. Its natural " +
                    "significations face obstruction. Remedial measures are recommended."
        )
    }

    private fun generateHouseEffects(house: Int, winner: Planet, loser: Planet): String {
        val houseMeaning = getHouseSignifications(house)
        return "House $house ($houseMeaning) becomes a battleground. ${winner.displayName} " +
                "dominates this area while ${loser.displayName}'s contributions are suppressed. " +
                "Expect competition and conflict in matters of this house."
    }

    private fun generateSignEffects(sign: ZodiacSign, winner: Planet, loser: Planet): String {
        val signLord = sign.ruler
        return "The war in ${sign.displayName} brings ${winner.displayName}'s victory to " +
                "${signLord.displayName}'s domain. If ${signLord.displayName} is well-placed, " +
                "it can mitigate some negative effects. If weak, the war's impact intensifies."
    }

    private fun generateWarInterpretation(
        winner: Planet,
        loser: Planet,
        sign: ZodiacSign,
        house: Int,
        intensity: Double
    ): String {
        val intensityWord = when {
            intensity >= 0.9 -> "severe"
            intensity >= 0.7 -> "intense"
            intensity >= 0.4 -> "moderate"
            else -> "mild"
        }

        return buildString {
            append("A $intensityWord Graha Yuddha occurs between ${winner.displayName} and ")
            append("${loser.displayName} in ${sign.displayName} (House $house).\n\n")
            append("${winner.displayName} emerges victorious, gaining strength and dominance. ")
            append("${loser.displayName} is defeated, its significations weakened and obstructed.\n\n")
            append("This planetary war affects matters of House $house (${getHouseSignifications(house)}). ")
            append("During periods ruled by either planet, these themes become especially active. ")
            append("The loser's dasha/antardasha periods may bring struggles in its natural significations.")
        }
    }

    /**
     * Analyze all planetary states in context of wars
     */
    private fun analyzeAllPlanetaryStates(
        chart: VedicChart,
        activeWars: List<GrahaYuddhaResult>
    ): Map<Planet, PlanetaryWarState> {
        val states = mutableMapOf<Planet, PlanetaryWarState>()

        for (position in chart.planetPositions) {
            if (position.planet !in WAR_CAPABLE_PLANETS) continue

            val involvedWar = activeWars.find {
                it.planet1 == position.planet || it.planet2 == position.planet
            }

            val warStatus = when {
                involvedWar == null -> WarStatus.NOT_IN_WAR
                involvedWar.winner == position.planet -> WarStatus.WINNER
                else -> WarStatus.LOSER
            }

            states[position.planet] = PlanetaryWarState(
                planet = position.planet,
                position = position,
                warStatus = warStatus,
                involvedWar = involvedWar,
                strengthModifier = calculateStrengthModifier(warStatus, involvedWar?.intensity ?: 0.0),
                recommendations = generatePlanetRecommendations(position.planet, warStatus)
            )
        }

        return states
    }

    private fun calculateStrengthModifier(status: WarStatus, intensity: Double): Double {
        return when (status) {
            WarStatus.WINNER -> 1.0 + (intensity * 0.3) // Up to 30% boost
            WarStatus.LOSER -> 1.0 - (intensity * 0.5) // Up to 50% reduction
            WarStatus.NOT_IN_WAR -> 1.0
        }
    }

    private fun generatePlanetRecommendations(planet: Planet, status: WarStatus): List<String> {
        if (status == WarStatus.NOT_IN_WAR) return emptyList()

        return when (status) {
            WarStatus.LOSER -> when (planet) {
                Planet.MARS -> listOf(
                    "Worship Lord Hanuman on Tuesdays",
                    "Wear red coral after consultation",
                    "Donate red items on Tuesdays",
                    "Recite Mars mantras (Om Angarakaya Namah)"
                )
                Planet.MERCURY -> listOf(
                    "Worship Lord Vishnu on Wednesdays",
                    "Wear emerald after consultation",
                    "Donate green items on Wednesdays",
                    "Recite Mercury mantras (Om Budhaya Namah)"
                )
                Planet.JUPITER -> listOf(
                    "Worship Lord Shiva or Brihaspati on Thursdays",
                    "Wear yellow sapphire after consultation",
                    "Donate yellow items on Thursdays",
                    "Recite Jupiter mantras (Om Brihaspataye Namah)"
                )
                Planet.VENUS -> listOf(
                    "Worship Goddess Lakshmi on Fridays",
                    "Wear diamond or white sapphire after consultation",
                    "Donate white items on Fridays",
                    "Recite Venus mantras (Om Shukraya Namah)"
                )
                Planet.SATURN -> listOf(
                    "Worship Lord Shani on Saturdays",
                    "Wear blue sapphire only after detailed consultation",
                    "Donate black/blue items on Saturdays",
                    "Recite Saturn mantras (Om Shanaischaraya Namah)"
                )
                else -> emptyList()
            }
            WarStatus.WINNER -> listOf(
                "Channel ${planet.displayName}'s victory energy constructively",
                "Avoid overconfidence in ${planet.displayName}'s significations",
                "Regular gratitude practice for ${planet.displayName}'s blessings"
            )
            else -> emptyList()
        }
    }

    /**
     * Analyze how war affects dasha periods
     */
    private fun analyzeDashaEffects(
        chart: VedicChart,
        activeWars: List<GrahaYuddhaResult>
    ): List<DashaWarEffect> {
        val effects = mutableListOf<DashaWarEffect>()

        for (war in activeWars) {
            // Winner's dasha effects
            effects.add(DashaWarEffect(
                planet = war.winner,
                isWinner = true,
                mahadashaEffect = "During ${war.winner.displayName} Mahadasha: Enhanced results, " +
                        "victory over competition, gains through assertiveness. House ${war.warHouse} " +
                        "matters flourish with effort.",
                antardashaEffect = "During ${war.winner.displayName} Antardasha: Short-term " +
                        "competitive advantages, opportunities through assertive action.",
                transitEffect = "When ${war.winner.displayName} transits war degree: Activation " +
                        "of victory themes, competitive success likely."
            ))

            // Loser's dasha effects
            effects.add(DashaWarEffect(
                planet = war.loser,
                isWinner = false,
                mahadashaEffect = "During ${war.loser.displayName} Mahadasha: Struggles in " +
                        "${war.loser.displayName}'s significations, obstacles in House ${war.warHouse} " +
                        "matters. Patience and remedies essential.",
                antardashaEffect = "During ${war.loser.displayName} Antardasha: Temporary " +
                        "setbacks possible, need extra effort to achieve results.",
                transitEffect = "When ${war.loser.displayName} transits war degree: Challenging " +
                        "period, old issues may resurface. Focus on remedial measures."
            ))
        }

        return effects
    }

    /**
     * Generate comprehensive remedies
     */
    private fun generateRemedies(activeWars: List<GrahaYuddhaResult>): List<WarRemedy> {
        val remedies = mutableListOf<WarRemedy>()

        for (war in activeWars) {
            // Remedies for the loser
            remedies.add(WarRemedy(
                forPlanet = war.loser,
                type = RemedyType.MANTRA,
                description = "Recite ${war.loser.displayName} beej mantra 108 times daily",
                mantra = getPlanetMantra(war.loser),
                timing = "During ${war.loser.displayName}'s hora on its day",
                expectedBenefit = "Strengthens ${war.loser.displayName} to mitigate war effects"
            ))

            remedies.add(WarRemedy(
                forPlanet = war.loser,
                type = RemedyType.CHARITY,
                description = "Donate ${getCharityItems(war.loser)} on ${getPlanetDay(war.loser)}",
                mantra = null,
                timing = getPlanetDay(war.loser),
                expectedBenefit = "Pacifies ${war.loser.displayName} and reduces malefic effects"
            ))

            remedies.add(WarRemedy(
                forPlanet = war.loser,
                type = RemedyType.WORSHIP,
                description = "Worship ${getPlanetDeity(war.loser)} regularly",
                mantra = null,
                timing = "${getPlanetDay(war.loser)} evenings",
                expectedBenefit = "Gains divine blessings to overcome ${war.loser.displayName}'s weakness"
            ))

            // General war remedy
            remedies.add(WarRemedy(
                forPlanet = null,
                type = RemedyType.GENERAL,
                description = "Perform Navagraha Shanti Puja to balance planetary energies",
                mantra = "Om Navagrahaaya Namah",
                timing = "Auspicious muhurta",
                expectedBenefit = "Overall planetary harmony and war mitigation"
            ))
        }

        return remedies.distinctBy { "${it.forPlanet}-${it.type}" }
    }

    private fun getPlanetMantra(planet: Planet): String {
        return when (planet) {
            Planet.SUN -> "Om Hraam Hreem Hraum Sah Suryaya Namah"
            Planet.MOON -> "Om Shraam Shreem Shraum Sah Chandraya Namah"
            Planet.MARS -> "Om Kraam Kreem Kraum Sah Bhaumaya Namah"
            Planet.MERCURY -> "Om Braam Breem Braum Sah Budhaya Namah"
            Planet.JUPITER -> "Om Graam Greem Graum Sah Gurave Namah"
            Planet.VENUS -> "Om Draam Dreem Draum Sah Shukraya Namah"
            Planet.SATURN -> "Om Praam Preem Praum Sah Shanaischaraya Namah"
            Planet.RAHU -> "Om Bhraam Bhreem Bhraum Sah Rahave Namah"
            Planet.KETU -> "Om Sraam Sreem Sraum Sah Ketave Namah"
            else -> ""
        }
    }

    private fun getCharityItems(planet: Planet): String {
        return when (planet) {
            Planet.MARS -> "red lentils, red cloth, copper items"
            Planet.MERCURY -> "green moong dal, green cloth, books"
            Planet.JUPITER -> "turmeric, yellow cloth, gold items, ghee"
            Planet.VENUS -> "rice, white cloth, sugar, silver items"
            Planet.SATURN -> "black sesame, black cloth, iron items, oil"
            else -> "appropriate items"
        }
    }

    private fun getPlanetDay(planet: Planet): String {
        return when (planet) {
            Planet.SUN -> "Sunday"
            Planet.MOON -> "Monday"
            Planet.MARS -> "Tuesday"
            Planet.MERCURY -> "Wednesday"
            Planet.JUPITER -> "Thursday"
            Planet.VENUS -> "Friday"
            Planet.SATURN -> "Saturday"
            else -> "any day"
        }
    }

    private fun getPlanetDeity(planet: Planet): String {
        return when (planet) {
            Planet.SUN -> "Lord Surya"
            Planet.MOON -> "Lord Shiva or Goddess Parvati"
            Planet.MARS -> "Lord Hanuman or Lord Kartikeya"
            Planet.MERCURY -> "Lord Vishnu or Lord Ganesha"
            Planet.JUPITER -> "Lord Brihaspati or Lord Shiva"
            Planet.VENUS -> "Goddess Lakshmi or Goddess Saraswati"
            Planet.SATURN -> "Lord Shani or Lord Hanuman"
            else -> "Navagraha"
        }
    }

    private fun buildWarHistory(activeWars: List<GrahaYuddhaResult>): List<String> {
        return activeWars.map { war ->
            "${war.winner.displayName} defeats ${war.loser.displayName} in ${war.warSign.displayName}"
        }
    }

    /**
     * Generate overall interpretation
     */
    private fun generateInterpretation(
        chart: VedicChart,
        activeWars: List<GrahaYuddhaResult>,
        planetaryStates: Map<Planet, PlanetaryWarState>
    ): GrahaYuddhaInterpretation {
        val summary = if (activeWars.isEmpty()) {
            "No Graha Yuddha (Planetary War) is present in this chart. All planets operate " +
                    "without the combative influence of close conjunction. This is generally favorable " +
                    "as each planet can express its significations without direct obstruction from another."
        } else {
            buildString {
                append("This chart contains ${activeWars.size} Graha Yuddha (Planetary War). ")
                append("Planetary wars significantly affect the involved planets' ability to deliver results. ")

                val winners = activeWars.map { it.winner }.distinct()
                val losers = activeWars.map { it.loser }.distinct()

                append("\n\nVictorious planets: ${winners.joinToString { it.displayName }}. ")
                append("These planets gain extra strength and dominance.\n\n")
                append("Defeated planets: ${losers.joinToString { it.displayName }}. ")
                append("These planets struggle to deliver their natural significations and require remedial attention.")
            }
        }

        val keyInsights = mutableListOf<String>()
        for (war in activeWars) {
            keyInsights.add("${war.winner.displayName} dominates ${war.loser.displayName} " +
                    "in House ${war.warHouse} (${war.warSign.displayName})")
        }

        val losingPlanets = activeWars.map { it.loser }.distinct()
        val recommendations = if (losingPlanets.isEmpty()) {
            listOf("No specific war-related remedies needed",
                "Continue with general planetary strengthening as appropriate")
        } else {
            losingPlanets.flatMap { planet ->
                listOf(
                    "Strengthen ${planet.displayName} through its specific remedies",
                    "Be cautious during ${planet.displayName}'s dasha/antardasha periods"
                )
            }
        }

        return GrahaYuddhaInterpretation(
            summary = summary,
            keyInsights = keyInsights,
            recommendations = recommendations,
            overallWarImpact = calculateOverallWarImpact(activeWars),
            affectedLifeAreas = getAffectedLifeAreas(activeWars)
        )
    }

    private fun calculateOverallWarImpact(activeWars: List<GrahaYuddhaResult>): WarImpactLevel {
        if (activeWars.isEmpty()) return WarImpactLevel.NONE

        val maxIntensity = activeWars.maxOfOrNull { it.intensity } ?: 0.0
        val warCount = activeWars.size

        return when {
            warCount >= 3 || maxIntensity >= 0.9 -> WarImpactLevel.SEVERE
            warCount >= 2 || maxIntensity >= 0.7 -> WarImpactLevel.SIGNIFICANT
            maxIntensity >= 0.4 -> WarImpactLevel.MODERATE
            else -> WarImpactLevel.MILD
        }
    }

    private fun getAffectedLifeAreas(activeWars: List<GrahaYuddhaResult>): List<String> {
        val areas = mutableSetOf<String>()
        for (war in activeWars) {
            areas.add(getHouseSignifications(war.warHouse))
            areas.addAll(getPlanetSignifications(war.loser))
        }
        return areas.toList()
    }

    private fun getHouseSignifications(house: Int): String {
        return when (house) {
            1 -> "Self, Body, Personality"
            2 -> "Wealth, Family, Speech"
            3 -> "Siblings, Courage, Communication"
            4 -> "Home, Mother, Comfort"
            5 -> "Children, Intelligence, Creativity"
            6 -> "Enemies, Health, Service"
            7 -> "Marriage, Partnerships, Business"
            8 -> "Transformation, Inheritance, Longevity"
            9 -> "Fortune, Father, Higher Learning"
            10 -> "Career, Status, Authority"
            11 -> "Gains, Friends, Aspirations"
            12 -> "Losses, Spirituality, Foreign Lands"
            else -> "General matters"
        }
    }

    private fun getPlanetSignifications(planet: Planet): List<String> {
        return when (planet) {
            Planet.MARS -> listOf("Courage", "Property", "Siblings", "Energy")
            Planet.MERCURY -> listOf("Communication", "Business", "Intelligence", "Education")
            Planet.JUPITER -> listOf("Wisdom", "Children", "Fortune", "Spirituality")
            Planet.VENUS -> listOf("Marriage", "Luxury", "Arts", "Vehicles")
            Planet.SATURN -> listOf("Career", "Longevity", "Discipline", "Service")
            else -> listOf()
        }
    }

    // ============================================
    // DATA CLASSES
    // ============================================

    data class GrahaYuddhaAnalysis(
        val activeWars: List<GrahaYuddhaResult>,
        val approachingWars: List<ApproachingWar>,
        val planetaryStates: Map<Planet, PlanetaryWarState>,
        val hasActiveWar: Boolean,
        val mostSignificantWar: GrahaYuddhaResult?,
        val dashaWarEffects: List<DashaWarEffect>,
        val remedies: List<WarRemedy>,
        val interpretation: GrahaYuddhaInterpretation
    )

    data class GrahaYuddhaResult(
        val planet1: Planet,
        val planet2: Planet,
        val separation: Double,
        val winner: Planet,
        val loser: Planet,
        val winnerAdvantage: WarAdvantage,
        val intensity: Double,
        val intensityLevel: WarIntensity,
        val warSign: ZodiacSign,
        val warHouse: Int,
        val winnerPosition: PlanetPosition,
        val loserPosition: PlanetPosition,
        val winnerEffects: WinnerEffects,
        val loserEffects: LoserEffects,
        val houseEffects: String,
        val signEffects: String,
        val interpretation: String
    )

    data class ApproachingWar(
        val planet1: Planet,
        val planet2: Planet,
        val currentSeparation: Double,
        val estimatedDaysToWar: Int,
        val planet1Position: PlanetPosition,
        val planet2Position: PlanetPosition
    )

    data class PlanetaryWarState(
        val planet: Planet,
        val position: PlanetPosition,
        val warStatus: WarStatus,
        val involvedWar: GrahaYuddhaResult?,
        val strengthModifier: Double,
        val recommendations: List<String>
    )

    data class WinnerEffects(
        val strengthGain: String,
        val absorbedQualities: String,
        val houseInfluence: String,
        val overallBenefit: String
    )

    data class LoserEffects(
        val weaknessAreas: List<String>,
        val houseImpact: String,
        val manifestationIssues: String,
        val overallDeficit: String
    )

    data class DashaWarEffect(
        val planet: Planet,
        val isWinner: Boolean,
        val mahadashaEffect: String,
        val antardashaEffect: String,
        val transitEffect: String
    )

    data class WarRemedy(
        val forPlanet: Planet?,
        val type: RemedyType,
        val description: String,
        val mantra: String?,
        val timing: String,
        val expectedBenefit: String
    )

    data class GrahaYuddhaInterpretation(
        val summary: String,
        val keyInsights: List<String>,
        val recommendations: List<String>,
        val overallWarImpact: WarImpactLevel,
        val affectedLifeAreas: List<String>
    )

    enum class WarStatus {
        NOT_IN_WAR,
        WINNER,
        LOSER
    }

    enum class WarAdvantage(val displayName: String) {
        NORTHERN_LATITUDE("Northern Latitude"),
        BRIGHTNESS("Greater Brightness"),
        SLOWER_MOTION("More Stable Position"),
        COMBINED("Multiple Factors")
    }

    enum class WarIntensity(val displayName: String) {
        MILD("Mild"),
        MODERATE("Moderate"),
        INTENSE("Intense"),
        SEVERE("Severe")
    }

    enum class WarImpactLevel(val displayName: String) {
        NONE("No War Impact"),
        MILD("Mild Impact"),
        MODERATE("Moderate Impact"),
        SIGNIFICANT("Significant Impact"),
        SEVERE("Severe Impact")
    }

    enum class RemedyType(val displayName: String) {
        MANTRA("Mantra Recitation"),
        CHARITY("Charitable Donation"),
        WORSHIP("Deity Worship"),
        GEMSTONE("Gemstone"),
        GENERAL("General Remedy")
    }
}
