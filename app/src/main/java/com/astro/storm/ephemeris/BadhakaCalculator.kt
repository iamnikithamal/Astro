package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign

/**
 * BadhakaCalculator - Comprehensive Badhaka (Obstruction) Planet Analysis
 *
 * In Vedic astrology, Badhaka Sthana is the house of obstruction, and its lord
 * (Badhakesh) is responsible for creating obstacles in life. The Badhaka house
 * varies based on the nature of the Ascendant sign:
 *
 * - Movable (Chara) Signs (Aries, Cancer, Libra, Capricorn): 11th house is Badhaka
 * - Fixed (Sthira) Signs (Taurus, Leo, Scorpio, Aquarius): 9th house is Badhaka
 * - Dual (Dwisvabhava) Signs (Gemini, Virgo, Sagittarius, Pisces): 7th house is Badhaka
 *
 * This calculator provides:
 * 1. Badhaka Sthana identification based on Lagna type
 * 2. Badhakesh (lord) analysis and its condition
 * 3. Planets in Badhaka house analysis
 * 4. Badhaka affliction assessment
 * 5. Areas of life affected by obstruction
 * 6. Timing of obstacles (Dasha analysis)
 * 7. Remedial measures
 *
 * Traditional References:
 * - Brihat Parashara Hora Shastra
 * - Uttara Kalamrita
 * - Jataka Parijata
 */
object BadhakaCalculator {

    /**
     * Sign modality (nature) classification
     */
    enum class SignModality(val displayName: String, val description: String) {
        MOVABLE("Movable (Chara)", "Cardinal signs - initiative, action, change"),
        FIXED("Fixed (Sthira)", "Stable signs - persistence, determination, resistance"),
        DUAL("Dual (Dwisvabhava)", "Mutable signs - adaptability, flexibility, duality")
    }

    /**
     * Severity of Badhaka affliction
     */
    enum class BadhakaSeverity(val displayName: String, val level: Int) {
        SEVERE("Severe", 5),
        STRONG("Strong", 4),
        MODERATE("Moderate", 3),
        MILD("Mild", 2),
        MINIMAL("Minimal", 1),
        NONE("None", 0)
    }

    /**
     * Types of obstacles caused by Badhaka
     */
    enum class ObstacleType(val displayName: String, val description: String) {
        CAREER("Career Obstacles", "Hindrances in professional growth and recognition"),
        HEALTH("Health Obstacles", "Chronic or recurring health issues"),
        RELATIONSHIP("Relationship Obstacles", "Difficulties in partnerships and marriage"),
        FINANCIAL("Financial Obstacles", "Blocks in wealth accumulation"),
        SPIRITUAL("Spiritual Obstacles", "Impediments in spiritual progress"),
        LEGAL("Legal Obstacles", "Litigation and legal troubles"),
        TRAVEL("Travel Obstacles", "Issues with travel and relocation"),
        EDUCATION("Education Obstacles", "Hindrances in learning and knowledge"),
        GENERAL("General Obstacles", "Overall life challenges and delays")
    }

    /**
     * Data class for Badhaka planet analysis
     */
    data class BadhakaPlanet(
        val planet: Planet,
        val position: PlanetPosition,
        val isBadhakesh: Boolean,
        val isInBadhakaHouse: Boolean,
        val strength: Double,
        val severity: BadhakaSeverity,
        val isRetrograde: Boolean,
        val isDebilitated: Boolean,
        val isExalted: Boolean,
        val isCombust: Boolean,
        val aspects: List<Int>,
        val conjunctions: List<Planet>,
        val interpretation: String
    )

    /**
     * Data class for Badhaka Dasha period analysis
     */
    data class BadhakaDashaPeriod(
        val planet: Planet,
        val isBadhakesh: Boolean,
        val obstructionLevel: BadhakaSeverity,
        val affectedAreas: List<ObstacleType>,
        val periodDescription: String,
        val recommendations: List<String>
    )

    /**
     * Comprehensive Badhaka analysis result
     */
    data class BadhakaAnalysis(
        val ascendantSign: ZodiacSign,
        val signModality: SignModality,
        val badhakaHouse: Int,
        val badhakeshPlanet: Planet,
        val badhakeshPosition: BadhakaPlanet,
        val planetsInBadhakaHouse: List<BadhakaPlanet>,
        val overallSeverity: BadhakaSeverity,
        val primaryObstacles: List<ObstacleType>,
        val affectedHouses: List<Int>,
        val dashaPeriods: List<BadhakaDashaPeriod>,
        val protectiveFactors: List<String>,
        val remedies: List<BadhakaRemedy>,
        val summary: String,
        val detailedInterpretation: String
    )

    /**
     * Remedy for Badhaka afflictions
     */
    data class BadhakaRemedy(
        val planet: Planet?,
        val remedyType: String,
        val description: String,
        val mantra: String?,
        val deity: String?,
        val charity: String?,
        val ritual: String?,
        val effectiveness: Int
    )

    /**
     * Main analysis function - analyzes Badhaka influences in the chart
     */
    fun analyzeBadhaka(chart: VedicChart): BadhakaAnalysis? {
        if (chart.planetPositions.isEmpty()) return null

        val ascendantSign = ZodiacSign.fromLongitude(chart.ascendant)

        // Determine sign modality and Badhaka house
        val signModality = getSignModality(ascendantSign)
        val badhakaHouse = getBadhakaHouse(signModality)

        // Get Badhakesh (lord of Badhaka house)
        val badhakeshPlanet = getHouseLord(ascendantSign, badhakaHouse)

        // Analyze Badhakesh position
        val badhakeshPos = chart.planetPositions.find { it.planet == badhakeshPlanet }
        val badhakeshAnalysis = if (badhakeshPos != null) {
            analyzeBadhakaPlanet(chart, badhakeshPos, true, false, ascendantSign, badhakaHouse)
        } else {
            createDefaultBadhakeshAnalysis(badhakeshPlanet)
        }

        // Analyze planets in Badhaka house
        val planetsInBadhaka = chart.planetPositions
            .filter { it.house == badhakaHouse }
            .map { pos ->
                analyzeBadhakaPlanet(chart, pos, pos.planet == badhakeshPlanet, true, ascendantSign, badhakaHouse)
            }

        // Calculate overall severity
        val overallSeverity = calculateOverallSeverity(badhakeshAnalysis, planetsInBadhaka)

        // Identify primary obstacles
        val primaryObstacles = identifyObstacles(chart, badhakeshAnalysis, planetsInBadhaka, badhakaHouse)

        // Identify affected houses
        val affectedHouses = identifyAffectedHouses(chart, badhakeshAnalysis, planetsInBadhaka)

        // Analyze Dasha periods
        val dashaPeriods = analyzeBadhakaDashas(badhakeshAnalysis, planetsInBadhaka)

        // Identify protective factors
        val protectiveFactors = identifyProtectiveFactors(chart, ascendantSign, badhakeshAnalysis)

        // Generate remedies
        val remedies = generateRemedies(badhakeshPlanet, overallSeverity, primaryObstacles)

        // Generate interpretations
        val summary = generateSummary(signModality, badhakaHouse, badhakeshPlanet, overallSeverity)
        val detailedInterpretation = generateDetailedInterpretation(
            chart, ascendantSign, signModality, badhakaHouse, badhakeshAnalysis,
            planetsInBadhaka, primaryObstacles, protectiveFactors
        )

        return BadhakaAnalysis(
            ascendantSign = ascendantSign,
            signModality = signModality,
            badhakaHouse = badhakaHouse,
            badhakeshPlanet = badhakeshPlanet,
            badhakeshPosition = badhakeshAnalysis,
            planetsInBadhakaHouse = planetsInBadhaka,
            overallSeverity = overallSeverity,
            primaryObstacles = primaryObstacles,
            affectedHouses = affectedHouses,
            dashaPeriods = dashaPeriods,
            protectiveFactors = protectiveFactors,
            remedies = remedies,
            summary = summary,
            detailedInterpretation = detailedInterpretation
        )
    }

    /**
     * Get sign modality (Movable/Fixed/Dual)
     */
    private fun getSignModality(sign: ZodiacSign): SignModality {
        return when (sign) {
            ZodiacSign.ARIES, ZodiacSign.CANCER,
            ZodiacSign.LIBRA, ZodiacSign.CAPRICORN -> SignModality.MOVABLE

            ZodiacSign.TAURUS, ZodiacSign.LEO,
            ZodiacSign.SCORPIO, ZodiacSign.AQUARIUS -> SignModality.FIXED

            ZodiacSign.GEMINI, ZodiacSign.VIRGO,
            ZodiacSign.SAGITTARIUS, ZodiacSign.PISCES -> SignModality.DUAL
        }
    }

    /**
     * Get Badhaka house based on sign modality
     */
    private fun getBadhakaHouse(modality: SignModality): Int {
        return when (modality) {
            SignModality.MOVABLE -> 11  // 11th house for Movable signs
            SignModality.FIXED -> 9     // 9th house for Fixed signs
            SignModality.DUAL -> 7      // 7th house for Dual signs
        }
    }

    /**
     * Analyze a Badhaka-related planet
     */
    private fun analyzeBadhakaPlanet(
        chart: VedicChart,
        pos: PlanetPosition,
        isBadhakesh: Boolean,
        isInBadhakaHouse: Boolean,
        ascendantSign: ZodiacSign,
        badhakaHouse: Int
    ): BadhakaPlanet {
        val isDebilitated = VedicAstrologyUtils.isDebilitated(pos)
        val isExalted = VedicAstrologyUtils.isExalted(pos)
        val isCombust = checkCombustion(chart, pos)
        val aspects = getAspectedHouses(pos)
        val conjunctions = getConjunctPlanets(chart, pos)

        val strength = calculateBadhakaStrength(
            pos, isBadhakesh, isInBadhakaHouse, isDebilitated, isExalted, isCombust
        )

        val severity = when {
            strength >= 80 -> BadhakaSeverity.SEVERE
            strength >= 60 -> BadhakaSeverity.STRONG
            strength >= 40 -> BadhakaSeverity.MODERATE
            strength >= 20 -> BadhakaSeverity.MILD
            strength > 0 -> BadhakaSeverity.MINIMAL
            else -> BadhakaSeverity.NONE
        }

        val interpretation = buildBadhakaPlanetInterpretation(
            pos, isBadhakesh, isInBadhakaHouse, severity, isDebilitated, isExalted, badhakaHouse
        )

        return BadhakaPlanet(
            planet = pos.planet,
            position = pos,
            isBadhakesh = isBadhakesh,
            isInBadhakaHouse = isInBadhakaHouse,
            strength = strength,
            severity = severity,
            isRetrograde = pos.isRetrograde,
            isDebilitated = isDebilitated,
            isExalted = isExalted,
            isCombust = isCombust,
            aspects = aspects,
            conjunctions = conjunctions,
            interpretation = interpretation
        )
    }

    /**
     * Create default Badhakesh analysis when position not found
     */
    private fun createDefaultBadhakeshAnalysis(planet: Planet): BadhakaPlanet {
        return BadhakaPlanet(
            planet = planet,
            position = PlanetPosition(
                planet = planet,
                longitude = 0.0,
                latitude = 0.0,
                distance = 0.0,
                speed = 0.0,
                sign = ZodiacSign.ARIES,
                degree = 0.0,
                minutes = 0.0,
                seconds = 0.0,
                isRetrograde = false,
                nakshatra = com.astro.storm.data.model.Nakshatra.ASHWINI,
                nakshatraPada = 1,
                house = 1
            ),
            isBadhakesh = true,
            isInBadhakaHouse = false,
            strength = 50.0,
            severity = BadhakaSeverity.MODERATE,
            isRetrograde = false,
            isDebilitated = false,
            isExalted = false,
            isCombust = false,
            aspects = emptyList(),
            conjunctions = emptyList(),
            interpretation = "Badhakesh ${planet.displayName} - position data unavailable"
        )
    }

    /**
     * Calculate Badhaka strength score (0-100)
     */
    private fun calculateBadhakaStrength(
        pos: PlanetPosition,
        isBadhakesh: Boolean,
        isInBadhakaHouse: Boolean,
        isDebilitated: Boolean,
        isExalted: Boolean,
        isCombust: Boolean
    ): Double {
        var score = 30.0

        // Badhakesh has base potency
        if (isBadhakesh) {
            score += 25.0
        }

        // Planet in Badhaka house adds obstruction power
        if (isInBadhakaHouse) {
            score += 20.0
        }

        // Natural malefics as Badhaka are more obstructive
        if (VedicAstrologyUtils.isNaturalMalefic(pos.planet)) {
            score += 15.0
        }

        // Exaltation strengthens Badhaka effect
        if (isExalted) {
            score += 10.0
        }

        // Debilitation weakens Badhaka effect (planet itself is weak)
        if (isDebilitated) {
            score -= 15.0
        }

        // Combustion reduces Badhaka power
        if (isCombust) {
            score -= 10.0
        }

        // Retrograde may intensify effects
        if (pos.isRetrograde) {
            score += 5.0
        }

        // Position in Kendra strengthens
        if (pos.house in VedicAstrologyUtils.KENDRA_HOUSES) {
            score += 5.0
        }

        // Position in Dusthana may modify effects
        if (pos.house in VedicAstrologyUtils.DUSTHANA_HOUSES) {
            score += 5.0  // Dusthana placement can manifest obstacles
        }

        return score.coerceIn(0.0, 100.0)
    }

    /**
     * Calculate overall Badhaka severity
     */
    private fun calculateOverallSeverity(
        badhakesh: BadhakaPlanet,
        planetsInBadhaka: List<BadhakaPlanet>
    ): BadhakaSeverity {
        val badhakeshLevel = badhakesh.severity.level
        val maxPlanetLevel = planetsInBadhaka.maxOfOrNull { it.severity.level } ?: 0

        // Weighted calculation
        val weightedScore = badhakeshLevel * 0.6 + maxPlanetLevel * 0.4 +
                (planetsInBadhaka.size * 0.5)

        return when {
            weightedScore >= 4.0 -> BadhakaSeverity.SEVERE
            weightedScore >= 3.0 -> BadhakaSeverity.STRONG
            weightedScore >= 2.0 -> BadhakaSeverity.MODERATE
            weightedScore >= 1.0 -> BadhakaSeverity.MILD
            weightedScore > 0 -> BadhakaSeverity.MINIMAL
            else -> BadhakaSeverity.NONE
        }
    }

    /**
     * Identify types of obstacles based on chart analysis
     */
    private fun identifyObstacles(
        chart: VedicChart,
        badhakesh: BadhakaPlanet,
        planetsInBadhaka: List<BadhakaPlanet>,
        badhakaHouse: Int
    ): List<ObstacleType> {
        val obstacles = mutableListOf<ObstacleType>()

        // Based on Badhakesh position
        when (badhakesh.position.house) {
            1 -> obstacles.add(ObstacleType.HEALTH)
            2 -> obstacles.add(ObstacleType.FINANCIAL)
            3, 9 -> obstacles.add(ObstacleType.TRAVEL)
            4 -> obstacles.add(ObstacleType.GENERAL)  // Home, peace of mind
            5, 9 -> obstacles.add(ObstacleType.EDUCATION)
            6 -> {
                obstacles.add(ObstacleType.HEALTH)
                obstacles.add(ObstacleType.LEGAL)
            }
            7 -> obstacles.add(ObstacleType.RELATIONSHIP)
            8 -> {
                obstacles.add(ObstacleType.HEALTH)
                obstacles.add(ObstacleType.GENERAL)
            }
            10 -> obstacles.add(ObstacleType.CAREER)
            11 -> obstacles.add(ObstacleType.FINANCIAL)
            12 -> {
                obstacles.add(ObstacleType.SPIRITUAL)
                obstacles.add(ObstacleType.FINANCIAL)
            }
        }

        // Based on Badhaka house itself
        when (badhakaHouse) {
            7 -> obstacles.add(ObstacleType.RELATIONSHIP)
            9 -> {
                obstacles.add(ObstacleType.SPIRITUAL)
                obstacles.add(ObstacleType.TRAVEL)
            }
            11 -> obstacles.add(ObstacleType.FINANCIAL)
        }

        // Based on planets involved
        (listOf(badhakesh) + planetsInBadhaka).forEach { planet ->
            when (planet.planet) {
                Planet.SUN -> obstacles.add(ObstacleType.CAREER)
                Planet.MOON -> obstacles.add(ObstacleType.HEALTH)
                Planet.MARS -> {
                    obstacles.add(ObstacleType.LEGAL)
                    obstacles.add(ObstacleType.RELATIONSHIP)
                }
                Planet.MERCURY -> obstacles.add(ObstacleType.EDUCATION)
                Planet.JUPITER -> obstacles.add(ObstacleType.SPIRITUAL)
                Planet.VENUS -> obstacles.add(ObstacleType.RELATIONSHIP)
                Planet.SATURN -> {
                    obstacles.add(ObstacleType.CAREER)
                    obstacles.add(ObstacleType.GENERAL)
                }
                Planet.RAHU -> obstacles.add(ObstacleType.GENERAL)
                Planet.KETU -> obstacles.add(ObstacleType.SPIRITUAL)
                else -> {}
            }
        }

        return obstacles.distinct().take(5)
    }

    /**
     * Identify houses affected by Badhaka influence
     */
    private fun identifyAffectedHouses(
        chart: VedicChart,
        badhakesh: BadhakaPlanet,
        planetsInBadhaka: List<BadhakaPlanet>
    ): List<Int> {
        val affected = mutableSetOf<Int>()

        // House where Badhakesh is placed
        affected.add(badhakesh.position.house)

        // Houses aspected by Badhakesh
        affected.addAll(badhakesh.aspects)

        // Houses of planets in Badhaka house (their other lordships)
        planetsInBadhaka.forEach { planet ->
            affected.addAll(planet.aspects)
        }

        return affected.toList().sorted()
    }

    /**
     * Analyze Badhaka Dasha periods
     */
    private fun analyzeBadhakaDashas(
        badhakesh: BadhakaPlanet,
        planetsInBadhaka: List<BadhakaPlanet>
    ): List<BadhakaDashaPeriod> {
        val periods = mutableListOf<BadhakaDashaPeriod>()

        // Badhakesh Dasha
        val badhakeshAffected = mutableListOf(ObstacleType.GENERAL)
        when (badhakesh.planet) {
            Planet.SUN -> badhakeshAffected.add(ObstacleType.CAREER)
            Planet.MOON -> badhakeshAffected.add(ObstacleType.HEALTH)
            Planet.MARS -> badhakeshAffected.addAll(listOf(ObstacleType.LEGAL, ObstacleType.HEALTH))
            Planet.SATURN -> badhakeshAffected.addAll(listOf(ObstacleType.CAREER, ObstacleType.HEALTH))
            else -> {}
        }

        periods.add(
            BadhakaDashaPeriod(
                planet = badhakesh.planet,
                isBadhakesh = true,
                obstructionLevel = badhakesh.severity,
                affectedAreas = badhakeshAffected.distinct(),
                periodDescription = buildDashaPeriodDescription(badhakesh, true),
                recommendations = getDashaRecommendations(badhakesh.planet, badhakesh.severity)
            )
        )

        // Planets in Badhaka house
        planetsInBadhaka.filter { !it.isBadhakesh }.forEach { planet ->
            val affected = mutableListOf<ObstacleType>()
            when (planet.planet) {
                Planet.SUN -> affected.add(ObstacleType.CAREER)
                Planet.MOON -> affected.add(ObstacleType.HEALTH)
                Planet.MARS -> affected.addAll(listOf(ObstacleType.LEGAL, ObstacleType.HEALTH))
                Planet.JUPITER -> affected.add(ObstacleType.SPIRITUAL)
                Planet.VENUS -> affected.add(ObstacleType.RELATIONSHIP)
                Planet.SATURN -> affected.addAll(listOf(ObstacleType.CAREER, ObstacleType.GENERAL))
                Planet.RAHU -> affected.add(ObstacleType.GENERAL)
                Planet.KETU -> affected.add(ObstacleType.SPIRITUAL)
                else -> affected.add(ObstacleType.GENERAL)
            }

            periods.add(
                BadhakaDashaPeriod(
                    planet = planet.planet,
                    isBadhakesh = false,
                    obstructionLevel = planet.severity,
                    affectedAreas = affected.distinct(),
                    periodDescription = buildDashaPeriodDescription(planet, false),
                    recommendations = getDashaRecommendations(planet.planet, planet.severity)
                )
            )
        }

        return periods.sortedByDescending { it.obstructionLevel.level }
    }

    /**
     * Build Dasha period description
     */
    private fun buildDashaPeriodDescription(planet: BadhakaPlanet, isBadhakesh: Boolean): String {
        val sb = StringBuilder()

        if (isBadhakesh) {
            sb.append("${planet.planet.displayName} Dasha as Badhakesh: ")
            sb.append("This is the primary obstruction period. ")
        } else {
            sb.append("${planet.planet.displayName} Dasha (in Badhaka house): ")
            sb.append("This period may bring obstacles through association with Badhaka Sthana. ")
        }

        when (planet.severity) {
            BadhakaSeverity.SEVERE -> sb.append("Significant challenges and delays are likely. Careful planning and remedies are essential.")
            BadhakaSeverity.STRONG -> sb.append("Notable obstacles may arise. Proactive measures are recommended.")
            BadhakaSeverity.MODERATE -> sb.append("Some hindrances possible. Stay alert and adaptable.")
            BadhakaSeverity.MILD -> sb.append("Minor obstacles that can be managed with awareness.")
            else -> sb.append("Minimal obstruction effect expected.")
        }

        return sb.toString()
    }

    /**
     * Get Dasha period recommendations
     */
    private fun getDashaRecommendations(planet: Planet, severity: BadhakaSeverity): List<String> {
        val recommendations = mutableListOf<String>()

        // General recommendations based on severity
        when (severity) {
            BadhakaSeverity.SEVERE, BadhakaSeverity.STRONG -> {
                recommendations.add("Perform specific planetary remedies")
                recommendations.add("Avoid major new ventures during peak periods")
                recommendations.add("Maintain patience and persistence")
            }
            BadhakaSeverity.MODERATE -> {
                recommendations.add("Stay flexible and adaptable")
                recommendations.add("Plan important activities carefully")
            }
            else -> {
                recommendations.add("General awareness is sufficient")
            }
        }

        // Planet-specific recommendations
        when (planet) {
            Planet.SUN -> recommendations.add("Respect authority figures and maintain integrity")
            Planet.MOON -> recommendations.add("Care for emotional and mental well-being")
            Planet.MARS -> recommendations.add("Avoid conflicts and legal disputes")
            Planet.MERCURY -> recommendations.add("Double-check communications and contracts")
            Planet.JUPITER -> recommendations.add("Maintain ethical conduct and seek guidance")
            Planet.VENUS -> recommendations.add("Be careful in relationships and financial matters")
            Planet.SATURN -> recommendations.add("Accept delays gracefully and work diligently")
            Planet.RAHU -> recommendations.add("Avoid shortcuts and unconventional paths")
            Planet.KETU -> recommendations.add("Focus on spiritual practices and acceptance")
            else -> {}
        }

        return recommendations.distinct().take(5)
    }

    /**
     * Identify protective factors
     */
    private fun identifyProtectiveFactors(
        chart: VedicChart,
        ascendantSign: ZodiacSign,
        badhakesh: BadhakaPlanet
    ): List<String> {
        val factors = mutableListOf<String>()

        // Jupiter's aspect on Badhakesh
        val jupiterPos = chart.planetPositions.find { it.planet == Planet.JUPITER }
        jupiterPos?.let { pos ->
            val jupiterAspects = getAspectedHouses(pos)
            if (badhakesh.position.house in jupiterAspects) {
                factors.add("Jupiter's aspect on Badhakesh reduces obstruction power")
            }
        }

        // Badhakesh debilitated
        if (badhakesh.isDebilitated) {
            factors.add("Debilitated Badhakesh has weakened obstruction capacity")
        }

        // Badhakesh combust
        if (badhakesh.isCombust) {
            factors.add("Combust Badhakesh has reduced influence")
        }

        // Strong Lagna Lord
        val lagnaLord = ascendantSign.ruler
        val lagnaLordPos = chart.planetPositions.find { it.planet == lagnaLord }
        lagnaLordPos?.let { pos ->
            if (VedicAstrologyUtils.isExalted(pos) || VedicAstrologyUtils.isInOwnSign(pos)) {
                factors.add("Strong Lagna lord provides resilience against obstacles")
            }
        }

        // Benefics in Kendra
        val beneficsInKendra = chart.planetPositions.filter {
            it.house in VedicAstrologyUtils.KENDRA_HOUSES && VedicAstrologyUtils.isNaturalBenefic(it.planet)
        }
        if (beneficsInKendra.isNotEmpty()) {
            factors.add("Benefics in Kendra houses provide support and stability")
        }

        // 9th house strength (Bhagya - fortune)
        val ninthLord = getHouseLord(ascendantSign, 9)
        val ninthLordPos = chart.planetPositions.find { it.planet == ninthLord }
        ninthLordPos?.let { pos ->
            if (VedicAstrologyUtils.isExalted(pos) || pos.house in VedicAstrologyUtils.KENDRA_HOUSES) {
                factors.add("Strong 9th lord supports fortune overcoming obstacles")
            }
        }

        return factors.take(5)
    }

    /**
     * Generate remedies for Badhaka afflictions
     */
    private fun generateRemedies(
        badhakesh: Planet,
        severity: BadhakaSeverity,
        obstacles: List<ObstacleType>
    ): List<BadhakaRemedy> {
        val remedies = mutableListOf<BadhakaRemedy>()

        // General Ganesh remedy (remover of obstacles)
        remedies.add(
            BadhakaRemedy(
                planet = null,
                remedyType = "Worship",
                description = "Lord Ganesha worship - the remover of all obstacles",
                mantra = "Om Gam Ganapataye Namaha",
                deity = "Lord Ganesha",
                charity = "Donate modak (sweets) and durva grass",
                ritual = "Perform Ganesh Puja on Wednesdays and Chaturthi",
                effectiveness = 95
            )
        )

        // Badhakesh-specific remedy
        remedies.add(createRemedyForPlanet(badhakesh, severity))

        // Remedy based on primary obstacle type
        obstacles.firstOrNull()?.let { primaryObstacle ->
            when (primaryObstacle) {
                ObstacleType.CAREER -> remedies.add(
                    BadhakaRemedy(
                        planet = Planet.SUN,
                        remedyType = "Worship",
                        description = "Sun worship for career obstacles",
                        mantra = "Om Suryaya Namaha",
                        deity = "Lord Surya",
                        charity = "Donate wheat and jaggery on Sundays",
                        ritual = "Offer water to Sun at sunrise",
                        effectiveness = 80
                    )
                )
                ObstacleType.HEALTH -> remedies.add(
                    BadhakaRemedy(
                        planet = null,
                        remedyType = "Mantra",
                        description = "Maha Mrityunjaya for health obstacles",
                        mantra = "Om Tryambakam Yajamahe Sugandhim Pushtivardhanam",
                        deity = "Lord Shiva",
                        charity = "Donate medicines to needy",
                        ritual = "Rudrabhishek on Mondays",
                        effectiveness = 90
                    )
                )
                ObstacleType.RELATIONSHIP -> remedies.add(
                    BadhakaRemedy(
                        planet = Planet.VENUS,
                        remedyType = "Worship",
                        description = "Venus and Goddess Lakshmi for relationship harmony",
                        mantra = "Om Shreem Mahalakshmiyei Namaha",
                        deity = "Goddess Lakshmi",
                        charity = "Donate white items on Fridays",
                        ritual = "Lakshmi Puja on Fridays",
                        effectiveness = 80
                    )
                )
                ObstacleType.FINANCIAL -> remedies.add(
                    BadhakaRemedy(
                        planet = Planet.JUPITER,
                        remedyType = "Worship",
                        description = "Jupiter worship for financial obstacles",
                        mantra = "Om Gram Greem Graum Sah Gurave Namaha",
                        deity = "Lord Vishnu / Guru Brihaspati",
                        charity = "Donate yellow items on Thursdays",
                        ritual = "Visit Vishnu temple on Thursdays",
                        effectiveness = 85
                    )
                )
                ObstacleType.SPIRITUAL -> remedies.add(
                    BadhakaRemedy(
                        planet = Planet.KETU,
                        remedyType = "Practice",
                        description = "Spiritual practices for obstacle removal",
                        mantra = "Om Namah Shivaya",
                        deity = "Lord Shiva",
                        charity = "Support spiritual institutions",
                        ritual = "Regular meditation and prayer",
                        effectiveness = 85
                    )
                )
                else -> {}
            }
        }

        // Durga worship for general protection
        remedies.add(
            BadhakaRemedy(
                planet = null,
                remedyType = "Worship",
                description = "Goddess Durga worship for protection from all obstacles",
                mantra = "Om Dum Durgayei Namaha",
                deity = "Goddess Durga",
                charity = "Help women and children in need",
                ritual = "Durga Puja, especially during Navaratri",
                effectiveness = 90
            )
        )

        return remedies.distinctBy { it.description }.take(6)
    }

    /**
     * Create remedy for specific planet
     */
    private fun createRemedyForPlanet(planet: Planet, severity: BadhakaSeverity): BadhakaRemedy {
        return when (planet) {
            Planet.SUN -> BadhakaRemedy(
                planet = Planet.SUN,
                remedyType = "Multi-fold",
                description = "Sun remedies for Badhakesh",
                mantra = "Om Hraam Hreem Hraum Sah Suryaya Namah",
                deity = "Lord Surya",
                charity = "Donate wheat, copper, and red items on Sundays",
                ritual = "Surya Namaskar and water offering at sunrise",
                effectiveness = if (severity.level >= 4) 85 else 75
            )
            Planet.MOON -> BadhakaRemedy(
                planet = Planet.MOON,
                remedyType = "Multi-fold",
                description = "Moon remedies for Badhakesh",
                mantra = "Om Shraam Shreem Shraum Sah Chandraya Namah",
                deity = "Lord Shiva / Goddess Parvati",
                charity = "Donate white rice, milk, and white cloth",
                ritual = "Offer milk to Shiva Linga on Mondays",
                effectiveness = if (severity.level >= 4) 85 else 75
            )
            Planet.MARS -> BadhakaRemedy(
                planet = Planet.MARS,
                remedyType = "Multi-fold",
                description = "Mars remedies for Badhakesh",
                mantra = "Om Kraam Kreem Kraum Sah Bhaumaya Namah",
                deity = "Lord Hanuman / Lord Kartikeya",
                charity = "Donate red lentils and jaggery on Tuesdays",
                ritual = "Hanuman Chalisa recitation on Tuesdays",
                effectiveness = if (severity.level >= 4) 85 else 75
            )
            Planet.MERCURY -> BadhakaRemedy(
                planet = Planet.MERCURY,
                remedyType = "Multi-fold",
                description = "Mercury remedies for Badhakesh",
                mantra = "Om Braam Breem Braum Sah Budhaya Namah",
                deity = "Lord Vishnu",
                charity = "Donate green items and books on Wednesdays",
                ritual = "Vishnu Sahasranama on Wednesdays",
                effectiveness = if (severity.level >= 4) 80 else 70
            )
            Planet.JUPITER -> BadhakaRemedy(
                planet = Planet.JUPITER,
                remedyType = "Multi-fold",
                description = "Jupiter remedies for Badhakesh",
                mantra = "Om Gram Greem Graum Sah Gurave Namah",
                deity = "Lord Vishnu / Guru Brihaspati",
                charity = "Donate yellow items, turmeric on Thursdays",
                ritual = "Brihaspati Puja and fasting on Thursdays",
                effectiveness = if (severity.level >= 4) 85 else 75
            )
            Planet.VENUS -> BadhakaRemedy(
                planet = Planet.VENUS,
                remedyType = "Multi-fold",
                description = "Venus remedies for Badhakesh",
                mantra = "Om Draam Dreem Draum Sah Shukraya Namah",
                deity = "Goddess Lakshmi",
                charity = "Donate white items, sweets on Fridays",
                ritual = "Lakshmi Puja on Fridays",
                effectiveness = if (severity.level >= 4) 80 else 70
            )
            Planet.SATURN -> BadhakaRemedy(
                planet = Planet.SATURN,
                remedyType = "Multi-fold",
                description = "Saturn remedies for Badhakesh",
                mantra = "Om Praam Preem Praum Sah Shanaischaraya Namah",
                deity = "Lord Shani / Lord Hanuman",
                charity = "Donate black sesame, iron, oil on Saturdays",
                ritual = "Shani Shanti Puja and Hanuman Chalisa",
                effectiveness = if (severity.level >= 4) 90 else 80
            )
            Planet.RAHU -> BadhakaRemedy(
                planet = Planet.RAHU,
                remedyType = "Multi-fold",
                description = "Rahu remedies for Badhakesh",
                mantra = "Om Bhraam Bhreem Bhraum Sah Rahave Namah",
                deity = "Goddess Durga",
                charity = "Donate dark blue cloth, coconut on Saturdays",
                ritual = "Durga Kavach recitation",
                effectiveness = if (severity.level >= 4) 85 else 75
            )
            Planet.KETU -> BadhakaRemedy(
                planet = Planet.KETU,
                remedyType = "Multi-fold",
                description = "Ketu remedies for Badhakesh",
                mantra = "Om Sraam Sreem Sraum Sah Ketave Namah",
                deity = "Lord Ganesha",
                charity = "Donate grey/multicolored blankets",
                ritual = "Ganesha Puja and meditation",
                effectiveness = if (severity.level >= 4) 80 else 70
            )
            else -> BadhakaRemedy(
                planet = planet,
                remedyType = "General",
                description = "Planetary pacification for ${planet.displayName}",
                mantra = "Consult astrologer for specific mantra",
                deity = null,
                charity = "General charity on associated day",
                ritual = null,
                effectiveness = 60
            )
        }
    }

    /**
     * Generate summary text
     */
    private fun generateSummary(
        modality: SignModality,
        badhakaHouse: Int,
        badhakesh: Planet,
        severity: BadhakaSeverity
    ): String {
        return "Badhaka Analysis: ${modality.displayName} Ascendant. " +
                "Badhaka Sthana is the ${badhakaHouse}th house. " +
                "Badhakesh: ${badhakesh.displayName}. " +
                "Overall obstruction severity: ${severity.displayName}."
    }

    /**
     * Generate detailed interpretation
     */
    private fun generateDetailedInterpretation(
        chart: VedicChart,
        ascendant: ZodiacSign,
        modality: SignModality,
        badhakaHouse: Int,
        badhakesh: BadhakaPlanet,
        planetsInBadhaka: List<BadhakaPlanet>,
        obstacles: List<ObstacleType>,
        protectiveFactors: List<String>
    ): String {
        val sb = StringBuilder()

        sb.appendLine("=== Detailed Badhaka Analysis ===")
        sb.appendLine()

        // Ascendant Analysis
        sb.appendLine("ASCENDANT ANALYSIS:")
        sb.appendLine("Rising Sign: ${ascendant.displayName}")
        sb.appendLine("Sign Nature: ${modality.displayName}")
        sb.appendLine("• ${modality.description}")
        sb.appendLine()

        // Badhaka Sthana
        sb.appendLine("BADHAKA STHANA (OBSTRUCTION HOUSE):")
        sb.appendLine("The ${badhakaHouse}th house is the Badhaka Sthana for ${modality.displayName} ascendants.")
        when (badhakaHouse) {
            11 -> sb.appendLine("• Obstacles may come through gains, elder siblings, or large organizations")
            9 -> sb.appendLine("• Obstacles may relate to father, fortune, religion, or long journeys")
            7 -> sb.appendLine("• Obstacles may manifest through partnerships, marriage, or business")
        }
        sb.appendLine()

        // Badhakesh Analysis
        sb.appendLine("BADHAKESH (OBSTRUCTION LORD):")
        sb.appendLine("${badhakesh.planet.displayName} is the Badhakesh")
        sb.appendLine(badhakesh.interpretation)
        sb.appendLine()

        // Planets in Badhaka House
        if (planetsInBadhaka.isNotEmpty()) {
            sb.appendLine("PLANETS IN BADHAKA HOUSE:")
            planetsInBadhaka.forEach { planet ->
                sb.appendLine("• ${planet.planet.displayName}: ${planet.interpretation}")
            }
            sb.appendLine()
        }

        // Primary Obstacles
        sb.appendLine("PRIMARY AREAS OF OBSTRUCTION:")
        obstacles.forEach { obstacle ->
            sb.appendLine("• ${obstacle.displayName}: ${obstacle.description}")
        }
        sb.appendLine()

        // Protective Factors
        if (protectiveFactors.isNotEmpty()) {
            sb.appendLine("PROTECTIVE FACTORS:")
            protectiveFactors.forEach { factor ->
                sb.appendLine("✓ $factor")
            }
            sb.appendLine()
        }

        sb.appendLine("Note: Badhaka influences indicate areas where obstacles may arise, not guaranteed difficulties. Awareness and appropriate remedies can significantly mitigate these effects.")

        return sb.toString()
    }

    /**
     * Build interpretation for individual Badhaka planet
     */
    private fun buildBadhakaPlanetInterpretation(
        pos: PlanetPosition,
        isBadhakesh: Boolean,
        isInBadhakaHouse: Boolean,
        severity: BadhakaSeverity,
        isDebilitated: Boolean,
        isExalted: Boolean,
        badhakaHouse: Int
    ): String {
        val sb = StringBuilder()

        if (isBadhakesh) {
            sb.append("${pos.planet.displayName} is the Badhakesh (lord of ${badhakaHouse}th house). ")
        }

        if (isInBadhakaHouse) {
            sb.append("Placed in the Badhaka Sthana itself. ")
        }

        sb.append("Located in House ${pos.house} in ${pos.sign.displayName}. ")

        if (isExalted) sb.append("Exalted state strengthens its obstruction capacity. ")
        if (isDebilitated) sb.append("Debilitated state weakens its power to obstruct. ")
        if (pos.isRetrograde) sb.append("Retrograde motion may intensify and prolong effects. ")

        sb.append("Obstruction severity: ${severity.displayName}.")

        return sb.toString()
    }

    // ============================================
    // HELPER FUNCTIONS
    // ============================================

    /**
     * Get the lord of a house based on Ascendant
     */
    private fun getHouseLord(ascendant: ZodiacSign, house: Int): Planet {
        val houseSignIndex = (ascendant.ordinal + house - 1) % 12
        return ZodiacSign.entries[houseSignIndex].ruler
    }

    /**
     * Get houses aspected by a planet
     */
    private fun getAspectedHouses(pos: PlanetPosition): List<Int> {
        val aspects = mutableListOf<Int>()
        val planetHouse = pos.house

        // 7th aspect (all planets)
        aspects.add(((planetHouse + 6) % 12).let { if (it == 0) 12 else it })

        // Special aspects
        when (pos.planet) {
            Planet.MARS -> {
                aspects.add(((planetHouse + 3) % 12).let { if (it == 0) 12 else it })
                aspects.add(((planetHouse + 7) % 12).let { if (it == 0) 12 else it })
            }
            Planet.JUPITER -> {
                aspects.add(((planetHouse + 4) % 12).let { if (it == 0) 12 else it })
                aspects.add(((planetHouse + 8) % 12).let { if (it == 0) 12 else it })
            }
            Planet.SATURN -> {
                aspects.add(((planetHouse + 2) % 12).let { if (it == 0) 12 else it })
                aspects.add(((planetHouse + 9) % 12).let { if (it == 0) 12 else it })
            }
            Planet.RAHU, Planet.KETU -> {
                aspects.add(((planetHouse + 4) % 12).let { if (it == 0) 12 else it })
                aspects.add(((planetHouse + 8) % 12).let { if (it == 0) 12 else it })
            }
            else -> {}
        }

        return aspects
    }

    /**
     * Get planets conjunct with a given planet
     */
    private fun getConjunctPlanets(chart: VedicChart, pos: PlanetPosition): List<Planet> {
        return chart.planetPositions
            .filter { it.house == pos.house && it.planet != pos.planet }
            .map { it.planet }
    }

    /**
     * Check if planet is combust
     */
    private fun checkCombustion(chart: VedicChart, pos: PlanetPosition): Boolean {
        if (pos.planet == Planet.SUN) return false

        val sunPos = chart.planetPositions.find { it.planet == Planet.SUN } ?: return false
        val distance = kotlin.math.abs(pos.longitude - sunPos.longitude)
        val normalizedDistance = if (distance > 180) 360 - distance else distance

        val combustionOrb = when (pos.planet) {
            Planet.MOON -> 12.0
            Planet.MARS -> 17.0
            Planet.MERCURY -> 14.0
            Planet.JUPITER -> 11.0
            Planet.VENUS -> 10.0
            Planet.SATURN -> 15.0
            else -> 0.0
        }

        return normalizedDistance <= combustionOrb
    }
}
