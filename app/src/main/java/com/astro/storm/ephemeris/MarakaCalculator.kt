package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign

/**
 * MarakaCalculator - Comprehensive Maraka (Death-Inflicting) Planet Analysis
 *
 * In Vedic astrology, Maraka planets are those capable of causing death or serious
 * health issues during their planetary periods (Dasha/Antardasha). The 2nd and 7th
 * houses are known as Maraka Sthanas (death-inflicting houses).
 *
 * This calculator provides:
 * 1. Primary Maraka identification (lords of 2nd and 7th houses)
 * 2. Secondary Maraka (planets occupying 2nd/7th houses)
 * 3. Tertiary Maraka (planets associated with Maraka lords)
 * 4. Longevity classification (Alpayu, Madhyayu, Purnayu)
 * 5. Maraka strength assessment
 * 6. Dasha period risk analysis
 * 7. Remedial measures
 *
 * Traditional Vedic References:
 * - Brihat Parashara Hora Shastra (Chapter on Ayurdaya)
 * - Jataka Parijata
 * - Phaladeepika
 */
object MarakaCalculator {

    /**
     * Severity levels for Maraka influence
     */
    enum class MarakaSeverity(val displayName: String, val level: Int) {
        VERY_HIGH("Very High", 5),
        HIGH("High", 4),
        MODERATE("Moderate", 3),
        LOW("Low", 2),
        MINIMAL("Minimal", 1),
        NONE("None", 0)
    }

    /**
     * Types of Maraka planets based on their relationship to Maraka houses
     */
    enum class MarakaType(val displayName: String, val description: String) {
        PRIMARY("Primary Maraka", "Lord of 2nd or 7th house - most potent"),
        SECONDARY("Secondary Maraka", "Planet occupying 2nd or 7th house"),
        TERTIARY("Tertiary Maraka", "Planet associated with Maraka lord"),
        FUNCTIONAL("Functional Maraka", "Planet acting as Maraka due to specific conditions"),
        NONE("Not Maraka", "No Maraka influence")
    }

    /**
     * Longevity categories based on classical Vedic astrology
     */
    enum class LongevityCategory(val displayName: String, val description: String, val yearsRange: String) {
        BALARISHTA("Balarishta", "Early childhood affliction (0-8 years)", "0-8"),
        ALPAYU("Alpayu", "Short lifespan", "32-40"),
        MADHYAYU("Madhyayu", "Medium lifespan", "40-70"),
        PURNAYU("Purnayu", "Full lifespan", "70-100"),
        AMITAYU("Amitayu", "Long lifespan (100+ years)", "100+")
    }

    /**
     * Data class representing a Maraka planet with its analysis
     */
    data class MarakaPlanet(
        val planet: Planet,
        val position: PlanetPosition,
        val marakaType: MarakaType,
        val severity: MarakaSeverity,
        val strengthScore: Double,
        val isRetrograde: Boolean,
        val isDebilitated: Boolean,
        val isExalted: Boolean,
        val isCombust: Boolean,
        val housePosition: Int,
        val ownedHouses: List<Int>,
        val aspects: List<Int>,
        val associations: List<Planet>,
        val interpretation: String
    )

    /**
     * Data class for Maraka Dasha period analysis
     */
    data class MarakaDashaPeriod(
        val planet: Planet,
        val marakaType: MarakaType,
        val riskLevel: MarakaSeverity,
        val periodDescription: String,
        val healthConcerns: List<String>,
        val precautions: List<String>,
        val favorableRemedies: List<String>
    )

    /**
     * Data class for longevity analysis
     */
    data class LongevityAnalysis(
        val category: LongevityCategory,
        val ascendantFactor: Int,
        val moonFactor: Int,
        val saturnFactor: Int,
        val eighthHouseFactor: Int,
        val beneficInfluence: Int,
        val maleficInfluence: Int,
        val overallScore: Int,
        val interpretation: String,
        val supportingFactors: List<String>,
        val challengingFactors: List<String>
    )

    /**
     * Comprehensive Maraka analysis result
     */
    data class MarakaAnalysis(
        val primaryMarakas: List<MarakaPlanet>,
        val secondaryMarakas: List<MarakaPlanet>,
        val tertiaryMarakas: List<MarakaPlanet>,
        val secondHouseLord: Planet,
        val seventhHouseLord: Planet,
        val planetsInSecondHouse: List<PlanetPosition>,
        val planetsInSeventhHouse: List<PlanetPosition>,
        val longevityAnalysis: LongevityAnalysis,
        val dashaPeriods: List<MarakaDashaPeriod>,
        val overallMarakaSeverity: MarakaSeverity,
        val healthVulnerabilities: List<String>,
        val protectiveFactors: List<String>,
        val remedies: List<MarakaRemedy>,
        val summary: String,
        val detailedInterpretation: String
    )

    /**
     * Remedy recommendation for Maraka afflictions
     */
    data class MarakaRemedy(
        val planet: Planet?,
        val remedyType: String,
        val description: String,
        val mantra: String?,
        val gemstone: String?,
        val charity: String?,
        val fasting: String?,
        val deity: String?,
        val effectiveness: Int
    )

    /**
     * Main analysis function - analyzes Maraka planets and longevity
     */
    fun analyzeMaraka(chart: VedicChart): MarakaAnalysis? {
        if (chart.planetPositions.isEmpty()) return null

        val ascendantSign = ZodiacSign.fromLongitude(chart.ascendant)

        // Get lords of 2nd and 7th houses
        val secondHouseLord = getHouseLord(ascendantSign, 2)
        val seventhHouseLord = getHouseLord(ascendantSign, 7)

        // Get planets in Maraka houses
        val planetsInSecond = chart.planetPositions.filter { it.house == 2 }
        val planetsInSeventh = chart.planetPositions.filter { it.house == 7 }

        // Analyze primary Marakas (house lords)
        val primaryMarakas = analyzePrimaryMarakas(chart, secondHouseLord, seventhHouseLord, ascendantSign)

        // Analyze secondary Marakas (planets in houses)
        val secondaryMarakas = analyzeSecondaryMarakas(chart, planetsInSecond, planetsInSeventh, ascendantSign)

        // Analyze tertiary Marakas (associated planets)
        val tertiaryMarakas = analyzeTertiaryMarakas(chart, primaryMarakas, ascendantSign)

        // Calculate longevity
        val longevityAnalysis = calculateLongevity(chart, ascendantSign, primaryMarakas)

        // Analyze Dasha periods
        val dashaPeriods = analyzeMarakaDashas(chart, primaryMarakas + secondaryMarakas)

        // Calculate overall severity
        val overallSeverity = calculateOverallSeverity(primaryMarakas, secondaryMarakas, tertiaryMarakas)

        // Identify health vulnerabilities
        val healthVulnerabilities = identifyHealthVulnerabilities(chart, primaryMarakas, secondaryMarakas)

        // Identify protective factors
        val protectiveFactors = identifyProtectiveFactors(chart, ascendantSign)

        // Generate remedies
        val remedies = generateRemedies(primaryMarakas, secondaryMarakas, overallSeverity)

        // Generate interpretation
        val summary = generateSummary(primaryMarakas, secondaryMarakas, longevityAnalysis, overallSeverity)
        val detailedInterpretation = generateDetailedInterpretation(
            chart, primaryMarakas, secondaryMarakas, tertiaryMarakas,
            longevityAnalysis, protectiveFactors
        )

        return MarakaAnalysis(
            primaryMarakas = primaryMarakas,
            secondaryMarakas = secondaryMarakas,
            tertiaryMarakas = tertiaryMarakas,
            secondHouseLord = secondHouseLord,
            seventhHouseLord = seventhHouseLord,
            planetsInSecondHouse = planetsInSecond,
            planetsInSeventhHouse = planetsInSeventh,
            longevityAnalysis = longevityAnalysis,
            dashaPeriods = dashaPeriods,
            overallMarakaSeverity = overallSeverity,
            healthVulnerabilities = healthVulnerabilities,
            protectiveFactors = protectiveFactors,
            remedies = remedies,
            summary = summary,
            detailedInterpretation = detailedInterpretation
        )
    }

    /**
     * Analyze primary Maraka planets (lords of 2nd and 7th houses)
     */
    private fun analyzePrimaryMarakas(
        chart: VedicChart,
        secondLord: Planet,
        seventhLord: Planet,
        ascendantSign: ZodiacSign
    ): List<MarakaPlanet> {
        val primaryMarakas = mutableListOf<MarakaPlanet>()

        // Analyze 2nd house lord
        chart.planetPositions.find { it.planet == secondLord }?.let { pos ->
            primaryMarakas.add(
                createMarakaPlanet(
                    chart, pos, MarakaType.PRIMARY, ascendantSign,
                    "Lord of 2nd house (primary Maraka Sthana)"
                )
            )
        }

        // Analyze 7th house lord (if different from 2nd lord)
        if (seventhLord != secondLord) {
            chart.planetPositions.find { it.planet == seventhLord }?.let { pos ->
                primaryMarakas.add(
                    createMarakaPlanet(
                        chart, pos, MarakaType.PRIMARY, ascendantSign,
                        "Lord of 7th house (secondary Maraka Sthana)"
                    )
                )
            }
        }

        return primaryMarakas
    }

    /**
     * Analyze secondary Maraka planets (occupants of 2nd and 7th houses)
     */
    private fun analyzeSecondaryMarakas(
        chart: VedicChart,
        planetsInSecond: List<PlanetPosition>,
        planetsInSeventh: List<PlanetPosition>,
        ascendantSign: ZodiacSign
    ): List<MarakaPlanet> {
        val secondaryMarakas = mutableListOf<MarakaPlanet>()

        planetsInSecond.forEach { pos ->
            secondaryMarakas.add(
                createMarakaPlanet(
                    chart, pos, MarakaType.SECONDARY, ascendantSign,
                    "Occupant of 2nd house (Maraka by position)"
                )
            )
        }

        planetsInSeventh.forEach { pos ->
            secondaryMarakas.add(
                createMarakaPlanet(
                    chart, pos, MarakaType.SECONDARY, ascendantSign,
                    "Occupant of 7th house (Maraka by position)"
                )
            )
        }

        return secondaryMarakas
    }

    /**
     * Analyze tertiary Maraka planets (associated with primary Marakas)
     */
    private fun analyzeTertiaryMarakas(
        chart: VedicChart,
        primaryMarakas: List<MarakaPlanet>,
        ascendantSign: ZodiacSign
    ): List<MarakaPlanet> {
        val tertiaryMarakas = mutableListOf<MarakaPlanet>()
        val primaryPlanets = primaryMarakas.map { it.planet }.toSet()

        chart.planetPositions.forEach { pos ->
            if (pos.planet !in primaryPlanets && pos.planet != Planet.RAHU && pos.planet != Planet.KETU) {
                // Check if conjunct with primary Maraka
                val isConjunct = primaryMarakas.any { primary ->
                    primary.housePosition == pos.house
                }

                // Check if aspected by primary Maraka (within orb)
                val isAspected = primaryMarakas.any { primary ->
                    isAspectedBy(pos.house, primary.position, chart)
                }

                if (isConjunct || isAspected) {
                    tertiaryMarakas.add(
                        createMarakaPlanet(
                            chart, pos, MarakaType.TERTIARY, ascendantSign,
                            if (isConjunct) "Conjunct with primary Maraka"
                            else "Aspected by primary Maraka"
                        )
                    )
                }
            }
        }

        return tertiaryMarakas
    }

    /**
     * Create a MarakaPlanet object with full analysis
     */
    private fun createMarakaPlanet(
        chart: VedicChart,
        pos: PlanetPosition,
        marakaType: MarakaType,
        ascendantSign: ZodiacSign,
        reasonDescription: String
    ): MarakaPlanet {
        val isDebilitated = VedicAstrologyUtils.isDebilitated(pos)
        val isExalted = VedicAstrologyUtils.isExalted(pos)
        val isCombust = checkCombustion(chart, pos)
        val ownedHouses = getOwnedHouses(pos.planet, ascendantSign)
        val aspects = getAspectedHouses(pos)
        val associations = getConjunctPlanets(chart, pos)

        val strengthScore = calculateMarakaStrength(
            pos, marakaType, isDebilitated, isExalted, isCombust, pos.isRetrograde
        )

        val severity = when {
            strengthScore >= 80 -> MarakaSeverity.VERY_HIGH
            strengthScore >= 60 -> MarakaSeverity.HIGH
            strengthScore >= 40 -> MarakaSeverity.MODERATE
            strengthScore >= 20 -> MarakaSeverity.LOW
            strengthScore > 0 -> MarakaSeverity.MINIMAL
            else -> MarakaSeverity.NONE
        }

        val interpretation = buildMarakaPlanetInterpretation(
            pos, marakaType, severity, isDebilitated, isExalted, isCombust, reasonDescription
        )

        return MarakaPlanet(
            planet = pos.planet,
            position = pos,
            marakaType = marakaType,
            severity = severity,
            strengthScore = strengthScore,
            isRetrograde = pos.isRetrograde,
            isDebilitated = isDebilitated,
            isExalted = isExalted,
            isCombust = isCombust,
            housePosition = pos.house,
            ownedHouses = ownedHouses,
            aspects = aspects,
            associations = associations,
            interpretation = interpretation
        )
    }

    /**
     * Calculate Maraka strength score (0-100)
     */
    private fun calculateMarakaStrength(
        pos: PlanetPosition,
        marakaType: MarakaType,
        isDebilitated: Boolean,
        isExalted: Boolean,
        isCombust: Boolean,
        isRetrograde: Boolean
    ): Double {
        var score = when (marakaType) {
            MarakaType.PRIMARY -> 60.0
            MarakaType.SECONDARY -> 45.0
            MarakaType.TERTIARY -> 30.0
            MarakaType.FUNCTIONAL -> 40.0
            MarakaType.NONE -> 0.0
        }

        // Natural malefics as Maraka are more potent
        if (VedicAstrologyUtils.isNaturalMalefic(pos.planet)) {
            score += 15.0
        }

        // Debilitation weakens Maraka effect (planet itself is weak)
        if (isDebilitated) {
            score -= 10.0
        }

        // Exaltation strengthens Maraka effect (planet is strong to cause effect)
        if (isExalted) {
            score += 10.0
        }

        // Combustion reduces Maraka power
        if (isCombust) {
            score -= 15.0
        }

        // Retrograde planets give prolonged effects
        if (isRetrograde) {
            score += 5.0
        }

        // Position in Dusthana reduces life-giving capacity (6, 8, 12)
        if (pos.house in VedicAstrologyUtils.DUSTHANA_HOUSES) {
            score += 10.0
        }

        // Position in Kendra gives strength
        if (pos.house in VedicAstrologyUtils.KENDRA_HOUSES) {
            score += 5.0
        }

        return score.coerceIn(0.0, 100.0)
    }

    /**
     * Calculate longevity based on multiple Vedic factors
     */
    private fun calculateLongevity(
        chart: VedicChart,
        ascendantSign: ZodiacSign,
        primaryMarakas: List<MarakaPlanet>
    ): LongevityAnalysis {
        // Ascendant factor (Lagna strength)
        val ascendantFactor = calculateAscendantStrength(chart, ascendantSign)

        // Moon factor (Mind and vitality)
        val moonFactor = calculateMoonStrength(chart)

        // Saturn factor (Longevity karaka)
        val saturnFactor = calculateSaturnStrength(chart)

        // 8th house factor (House of longevity)
        val eighthHouseFactor = calculateEighthHouseStrength(chart, ascendantSign)

        // Benefic influences on longevity houses (1, 3, 8)
        val beneficInfluence = calculateBeneficInfluence(chart, ascendantSign)

        // Malefic influences
        val maleficInfluence = calculateMaleficInfluence(chart, primaryMarakas)

        // Calculate overall score
        val overallScore = (ascendantFactor + moonFactor + saturnFactor + eighthHouseFactor +
                beneficInfluence - maleficInfluence).coerceIn(0, 100)

        // Determine longevity category
        val category = when {
            overallScore < 20 -> LongevityCategory.BALARISHTA
            overallScore < 40 -> LongevityCategory.ALPAYU
            overallScore < 65 -> LongevityCategory.MADHYAYU
            overallScore < 85 -> LongevityCategory.PURNAYU
            else -> LongevityCategory.AMITAYU
        }

        // Gather supporting and challenging factors
        val supportingFactors = mutableListOf<String>()
        val challengingFactors = mutableListOf<String>()

        if (ascendantFactor >= 15) supportingFactors.add("Strong Ascendant lord")
        else if (ascendantFactor < 10) challengingFactors.add("Weak Ascendant lord")

        if (moonFactor >= 15) supportingFactors.add("Well-placed Moon")
        else if (moonFactor < 10) challengingFactors.add("Afflicted Moon")

        if (saturnFactor >= 15) supportingFactors.add("Favorable Saturn placement")
        else if (saturnFactor < 10) challengingFactors.add("Challenging Saturn placement")

        if (eighthHouseFactor >= 15) supportingFactors.add("Strong 8th house")
        else if (eighthHouseFactor < 10) challengingFactors.add("Afflicted 8th house")

        if (beneficInfluence >= 15) supportingFactors.add("Benefic influences on longevity houses")

        if (maleficInfluence >= 20) challengingFactors.add("Strong malefic afflictions")

        val interpretation = buildLongevityInterpretation(category, supportingFactors, challengingFactors)

        return LongevityAnalysis(
            category = category,
            ascendantFactor = ascendantFactor,
            moonFactor = moonFactor,
            saturnFactor = saturnFactor,
            eighthHouseFactor = eighthHouseFactor,
            beneficInfluence = beneficInfluence,
            maleficInfluence = maleficInfluence,
            overallScore = overallScore,
            interpretation = interpretation,
            supportingFactors = supportingFactors,
            challengingFactors = challengingFactors
        )
    }

    /**
     * Calculate Ascendant strength for longevity
     */
    private fun calculateAscendantStrength(chart: VedicChart, ascendantSign: ZodiacSign): Int {
        var strength = 10

        val ascendantLord = ascendantSign.ruler
        val lordPos = chart.planetPositions.find { it.planet == ascendantLord }

        lordPos?.let { pos ->
            // Lord in Kendra or Trikona
            if (pos.house in VedicAstrologyUtils.KENDRA_HOUSES ||
                pos.house in VedicAstrologyUtils.TRIKONA_HOUSES) {
                strength += 5
            }

            // Lord exalted or in own sign
            if (VedicAstrologyUtils.isExalted(pos) || VedicAstrologyUtils.isInOwnSign(pos)) {
                strength += 5
            }

            // Lord debilitated
            if (VedicAstrologyUtils.isDebilitated(pos)) {
                strength -= 5
            }

            // Lord in Dusthana
            if (pos.house in VedicAstrologyUtils.DUSTHANA_HOUSES) {
                strength -= 3
            }
        }

        // Benefics in Lagna
        val beneficsInLagna = chart.planetPositions.filter {
            it.house == 1 && VedicAstrologyUtils.isNaturalBenefic(it.planet)
        }
        strength += beneficsInLagna.size * 2

        return strength.coerceIn(0, 20)
    }

    /**
     * Calculate Moon strength for longevity
     */
    private fun calculateMoonStrength(chart: VedicChart): Int {
        var strength = 10

        val moonPos = chart.planetPositions.find { it.planet == Planet.MOON } ?: return 5

        // Bright Moon (waxing, away from Sun)
        val sunPos = chart.planetPositions.find { it.planet == Planet.SUN }
        if (sunPos != null) {
            val distance = kotlin.math.abs(moonPos.longitude - sunPos.longitude)
            if (distance > 72) strength += 5  // More than 72 degrees from Sun
        }

        // Moon in good houses
        if (moonPos.house in listOf(1, 4, 5, 7, 9, 10, 11)) {
            strength += 3
        }

        // Moon exalted
        if (VedicAstrologyUtils.isExalted(moonPos)) {
            strength += 5
        }

        // Moon debilitated
        if (VedicAstrologyUtils.isDebilitated(moonPos)) {
            strength -= 5
        }

        // Moon with malefics
        val maleficsWithMoon = chart.planetPositions.filter {
            it.house == moonPos.house && VedicAstrologyUtils.isNaturalMalefic(it.planet)
        }
        strength -= maleficsWithMoon.size * 2

        return strength.coerceIn(0, 20)
    }

    /**
     * Calculate Saturn strength (longevity karaka)
     */
    private fun calculateSaturnStrength(chart: VedicChart): Int {
        var strength = 10

        val saturnPos = chart.planetPositions.find { it.planet == Planet.SATURN } ?: return 10

        // Saturn in own sign or exalted
        if (VedicAstrologyUtils.isExalted(saturnPos) || VedicAstrologyUtils.isInOwnSign(saturnPos)) {
            strength += 5
        }

        // Saturn debilitated
        if (VedicAstrologyUtils.isDebilitated(saturnPos)) {
            strength -= 5
        }

        // Saturn in good houses for longevity (3, 6, 10, 11)
        if (saturnPos.house in listOf(3, 6, 10, 11)) {
            strength += 3
        }

        // Saturn in 8th house (good for longevity)
        if (saturnPos.house == 8) {
            strength += 5
        }

        return strength.coerceIn(0, 20)
    }

    /**
     * Calculate 8th house strength
     */
    private fun calculateEighthHouseStrength(chart: VedicChart, ascendantSign: ZodiacSign): Int {
        var strength = 10

        // Get 8th house lord
        val eighthLord = getHouseLord(ascendantSign, 8)
        val lordPos = chart.planetPositions.find { it.planet == eighthLord }

        lordPos?.let { pos ->
            // 8th lord in Kendra or Trikona
            if (pos.house in VedicAstrologyUtils.KENDRA_HOUSES ||
                pos.house in VedicAstrologyUtils.TRIKONA_HOUSES) {
                strength += 5
            }

            // 8th lord strong
            if (VedicAstrologyUtils.isExalted(pos) || VedicAstrologyUtils.isInOwnSign(pos)) {
                strength += 5
            }

            // 8th lord weak
            if (VedicAstrologyUtils.isDebilitated(pos)) {
                strength -= 5
            }
        }

        // Benefics in 8th house
        val beneficsIn8th = chart.planetPositions.filter {
            it.house == 8 && VedicAstrologyUtils.isNaturalBenefic(it.planet)
        }
        strength += beneficsIn8th.size * 2

        // Malefics in 8th house (can be protective in certain cases)
        val maleficsIn8th = chart.planetPositions.filter {
            it.house == 8 && VedicAstrologyUtils.isNaturalMalefic(it.planet)
        }
        if (maleficsIn8th.isNotEmpty()) {
            // Saturn in 8th is good for longevity
            if (maleficsIn8th.any { it.planet == Planet.SATURN }) {
                strength += 3
            }
        }

        return strength.coerceIn(0, 20)
    }

    /**
     * Calculate benefic influence on longevity houses
     */
    private fun calculateBeneficInfluence(chart: VedicChart, ascendantSign: ZodiacSign): Int {
        var influence = 0

        // Check benefics in 1st, 3rd, 8th houses
        val longevityHouses = listOf(1, 3, 8)

        chart.planetPositions.forEach { pos ->
            if (pos.house in longevityHouses && VedicAstrologyUtils.isNaturalBenefic(pos.planet)) {
                influence += 3
            }

            // Jupiter aspecting longevity houses
            if (pos.planet == Planet.JUPITER) {
                val aspectedHouses = getAspectedHouses(pos)
                influence += aspectedHouses.count { it in longevityHouses } * 2
            }
        }

        return influence.coerceIn(0, 20)
    }

    /**
     * Calculate malefic influence reducing longevity
     */
    private fun calculateMaleficInfluence(
        chart: VedicChart,
        primaryMarakas: List<MarakaPlanet>
    ): Int {
        var influence = 0

        // Strong primary Marakas
        primaryMarakas.forEach { maraka ->
            if (maraka.severity == MarakaSeverity.VERY_HIGH || maraka.severity == MarakaSeverity.HIGH) {
                influence += 5
            } else if (maraka.severity == MarakaSeverity.MODERATE) {
                influence += 3
            }
        }

        // Malefics afflicting Lagna or Lagna lord
        val maleficsInLagna = chart.planetPositions.filter {
            it.house == 1 && VedicAstrologyUtils.isNaturalMalefic(it.planet)
        }
        influence += maleficsInLagna.size * 3

        // Rahu-Ketu on Lagna/7th axis
        val rahuPos = chart.planetPositions.find { it.planet == Planet.RAHU }
        val ketuPos = chart.planetPositions.find { it.planet == Planet.KETU }

        if (rahuPos?.house == 1 || rahuPos?.house == 7 ||
            ketuPos?.house == 1 || ketuPos?.house == 7) {
            influence += 5
        }

        return influence.coerceIn(0, 30)
    }

    /**
     * Analyze Maraka Dasha periods
     */
    private fun analyzeMarakaDashas(
        chart: VedicChart,
        marakas: List<MarakaPlanet>
    ): List<MarakaDashaPeriod> {
        return marakas.sortedByDescending { it.severity.level }.take(5).map { maraka ->
            val healthConcerns = getHealthConcernsForPlanet(maraka.planet, maraka.position)
            val precautions = getPrecautionsForMaraka(maraka)
            val remedies = getRemediesForPlanet(maraka.planet)

            MarakaDashaPeriod(
                planet = maraka.planet,
                marakaType = maraka.marakaType,
                riskLevel = maraka.severity,
                periodDescription = buildDashaPeriodDescription(maraka),
                healthConcerns = healthConcerns,
                precautions = precautions,
                favorableRemedies = remedies
            )
        }
    }

    /**
     * Get health concerns associated with a planet
     */
    private fun getHealthConcernsForPlanet(planet: Planet, pos: PlanetPosition): List<String> {
        val concerns = mutableListOf<String>()

        when (planet) {
            Planet.SUN -> {
                concerns.add("Heart and cardiovascular issues")
                concerns.add("Eye problems")
                concerns.add("Bone health, especially spine")
                concerns.add("Vitality and energy levels")
            }
            Planet.MOON -> {
                concerns.add("Mental health and emotional stability")
                concerns.add("Digestive issues, especially fluid balance")
                concerns.add("Sleep disorders")
                concerns.add("Blood-related conditions")
            }
            Planet.MARS -> {
                concerns.add("Accidents and injuries")
                concerns.add("Blood pressure and circulation")
                concerns.add("Inflammatory conditions")
                concerns.add("Surgical interventions")
            }
            Planet.MERCURY -> {
                concerns.add("Nervous system disorders")
                concerns.add("Skin conditions")
                concerns.add("Respiratory issues")
                concerns.add("Mental stress and anxiety")
            }
            Planet.JUPITER -> {
                concerns.add("Liver and gallbladder issues")
                concerns.add("Diabetes and metabolic disorders")
                concerns.add("Weight-related problems")
                concerns.add("Arterial conditions")
            }
            Planet.VENUS -> {
                concerns.add("Kidney and urinary tract issues")
                concerns.add("Reproductive health")
                concerns.add("Diabetes")
                concerns.add("Throat and thyroid conditions")
            }
            Planet.SATURN -> {
                concerns.add("Chronic and long-term ailments")
                concerns.add("Joint problems and arthritis")
                concerns.add("Dental issues")
                concerns.add("Depression and melancholy")
            }
            Planet.RAHU -> {
                concerns.add("Mysterious or difficult to diagnose conditions")
                concerns.add("Psychological disorders")
                concerns.add("Poisoning or toxicity")
                concerns.add("Addictions")
            }
            Planet.KETU -> {
                concerns.add("Sudden health crises")
                concerns.add("Viral infections")
                concerns.add("Accidents from negligence")
                concerns.add("Spiritual or psychosomatic issues")
            }
            else -> {}
        }

        // Add house-specific concerns
        when (pos.house) {
            1 -> concerns.add("General constitution and vitality")
            2 -> concerns.add("Face, mouth, teeth, right eye")
            3 -> concerns.add("Arms, shoulders, respiratory upper")
            4 -> concerns.add("Chest, heart, lungs, breasts")
            5 -> concerns.add("Stomach, upper digestive tract")
            6 -> concerns.add("Intestines, lower abdomen, immune system")
            7 -> concerns.add("Kidneys, lower back, reproductive organs")
            8 -> concerns.add("Chronic diseases, reproductive system, accidents")
            9 -> concerns.add("Hips, thighs, arterial system")
            10 -> concerns.add("Knees, joints, skeletal system")
            11 -> concerns.add("Ankles, calves, circulatory system")
            12 -> concerns.add("Feet, left eye, sleep disorders, hospitalization")
        }

        return concerns.take(5)
    }

    /**
     * Get precautions for Maraka period
     */
    private fun getPrecautionsForMaraka(maraka: MarakaPlanet): List<String> {
        val precautions = mutableListOf<String>()

        precautions.add("Regular health check-ups are advisable")
        precautions.add("Avoid unnecessary risks and dangerous activities")

        when (maraka.severity) {
            MarakaSeverity.VERY_HIGH, MarakaSeverity.HIGH -> {
                precautions.add("Extra caution during travel")
                precautions.add("Maintain a healthy lifestyle strictly")
                precautions.add("Consider preventive health measures")
            }
            MarakaSeverity.MODERATE -> {
                precautions.add("Balance work and rest")
                precautions.add("Pay attention to stress management")
            }
            else -> {
                precautions.add("General health awareness")
            }
        }

        // Planet-specific precautions
        when (maraka.planet) {
            Planet.MARS -> precautions.add("Be cautious with sharp objects and fire")
            Planet.SATURN -> precautions.add("Take care of bones and joints")
            Planet.RAHU -> precautions.add("Avoid unknown foods and substances")
            Planet.KETU -> precautions.add("Be mindful during spiritual practices")
            else -> {}
        }

        return precautions.distinct().take(5)
    }

    /**
     * Get remedies for a specific planet
     */
    private fun getRemediesForPlanet(planet: Planet): List<String> {
        return when (planet) {
            Planet.SUN -> listOf(
                "Recite Aditya Hridayam or Surya Mantra",
                "Offer water to Sun at sunrise",
                "Donate wheat and jaggery on Sundays",
                "Wear Ruby gemstone (after consultation)"
            )
            Planet.MOON -> listOf(
                "Recite Chandra Mantra or Devi prayers",
                "Offer milk to Shiva Linga on Mondays",
                "Donate white items and rice",
                "Wear Pearl gemstone (after consultation)"
            )
            Planet.MARS -> listOf(
                "Recite Mangal Mantra or Hanuman Chalisa",
                "Donate red items on Tuesdays",
                "Fast on Tuesdays",
                "Perform Kuja Shanti puja"
            )
            Planet.MERCURY -> listOf(
                "Recite Budha Mantra or Vishnu Sahasranama",
                "Donate green items on Wednesdays",
                "Feed green grass to cows",
                "Wear Emerald gemstone (after consultation)"
            )
            Planet.JUPITER -> listOf(
                "Recite Guru Mantra or Brihaspati Stotram",
                "Donate yellow items on Thursdays",
                "Respect teachers and elders",
                "Wear Yellow Sapphire (after consultation)"
            )
            Planet.VENUS -> listOf(
                "Recite Shukra Mantra or Lakshmi prayers",
                "Donate white items on Fridays",
                "Offer white flowers to Goddess",
                "Wear Diamond or White Sapphire (after consultation)"
            )
            Planet.SATURN -> listOf(
                "Recite Shani Mantra or Hanuman Chalisa",
                "Donate black items on Saturdays",
                "Feed crows and serve the elderly",
                "Wear Blue Sapphire only after careful analysis"
            )
            Planet.RAHU -> listOf(
                "Recite Rahu Mantra or Durga prayers",
                "Donate dark blue items on Saturdays",
                "Feed birds and care for animals",
                "Wear Hessonite Garnet (after consultation)"
            )
            Planet.KETU -> listOf(
                "Recite Ketu Mantra or Ganesha prayers",
                "Donate grey/multicolored items",
                "Practice meditation and spiritual activities",
                "Wear Cat's Eye gemstone (after consultation)"
            )
            else -> listOf("Consult an experienced astrologer for specific remedies")
        }
    }

    /**
     * Build Dasha period description
     */
    private fun buildDashaPeriodDescription(maraka: MarakaPlanet): String {
        val sb = StringBuilder()
        sb.append("${maraka.planet.displayName} Dasha/Antardasha period requires attention. ")
        sb.append("As a ${maraka.marakaType.displayName}, ")

        when (maraka.severity) {
            MarakaSeverity.VERY_HIGH -> sb.append("this period carries significant health implications and requires careful management.")
            MarakaSeverity.HIGH -> sb.append("this period may bring health challenges that need proactive attention.")
            MarakaSeverity.MODERATE -> sb.append("this period may have some health-related events but generally manageable.")
            MarakaSeverity.LOW -> sb.append("this period has minor Maraka influence with limited health impact.")
            else -> sb.append("this period has minimal direct health implications.")
        }

        return sb.toString()
    }

    /**
     * Calculate overall Maraka severity
     */
    private fun calculateOverallSeverity(
        primary: List<MarakaPlanet>,
        secondary: List<MarakaPlanet>,
        tertiary: List<MarakaPlanet>
    ): MarakaSeverity {
        val primaryMax = primary.maxOfOrNull { it.severity.level } ?: 0
        val secondaryMax = secondary.maxOfOrNull { it.severity.level } ?: 0
        val tertiaryMax = tertiary.maxOfOrNull { it.severity.level } ?: 0

        // Weighted calculation
        val weightedScore = (primaryMax * 0.5 + secondaryMax * 0.3 + tertiaryMax * 0.2) * 1.5

        return when {
            weightedScore >= 4.0 -> MarakaSeverity.VERY_HIGH
            weightedScore >= 3.0 -> MarakaSeverity.HIGH
            weightedScore >= 2.0 -> MarakaSeverity.MODERATE
            weightedScore >= 1.0 -> MarakaSeverity.LOW
            weightedScore > 0 -> MarakaSeverity.MINIMAL
            else -> MarakaSeverity.NONE
        }
    }

    /**
     * Identify health vulnerabilities from chart
     */
    private fun identifyHealthVulnerabilities(
        chart: VedicChart,
        primaryMarakas: List<MarakaPlanet>,
        secondaryMarakas: List<MarakaPlanet>
    ): List<String> {
        val vulnerabilities = mutableListOf<String>()

        // From Maraka planets
        (primaryMarakas + secondaryMarakas).forEach { maraka ->
            when (maraka.planet) {
                Planet.SUN -> vulnerabilities.add("Cardiovascular system, eyes, vitality")
                Planet.MOON -> vulnerabilities.add("Mental health, digestive system, fluids")
                Planet.MARS -> vulnerabilities.add("Blood, accidents, inflammatory conditions")
                Planet.SATURN -> vulnerabilities.add("Chronic conditions, joints, longevity")
                Planet.RAHU -> vulnerabilities.add("Mysterious ailments, psychological issues")
                Planet.KETU -> vulnerabilities.add("Sudden health events, spiritual crises")
                else -> {}
            }
        }

        // From 6th house (disease house)
        val planetsIn6th = chart.planetPositions.filter { it.house == 6 }
        planetsIn6th.forEach { pos ->
            when (pos.planet) {
                Planet.SUN -> vulnerabilities.add("Digestive fire, stomach issues")
                Planet.MOON -> vulnerabilities.add("Emotional eating, digestive problems")
                Planet.MARS -> vulnerabilities.add("Inflammation, surgery potential")
                Planet.SATURN -> vulnerabilities.add("Long-term health challenges")
                else -> {}
            }
        }

        return vulnerabilities.distinct().take(6)
    }

    /**
     * Identify protective factors in the chart
     */
    private fun identifyProtectiveFactors(chart: VedicChart, ascendantSign: ZodiacSign): List<String> {
        val factors = mutableListOf<String>()

        // Strong Jupiter
        val jupiterPos = chart.planetPositions.find { it.planet == Planet.JUPITER }
        jupiterPos?.let { pos ->
            if (VedicAstrologyUtils.isExalted(pos) || VedicAstrologyUtils.isInOwnSign(pos)) {
                factors.add("Strong Jupiter provides divine protection and good fortune")
            }
            if (pos.house in listOf(1, 5, 9)) {
                factors.add("Jupiter in Trikona protects overall well-being")
            }
        }

        // Benefics in Kendra
        val beneficsInKendra = chart.planetPositions.filter {
            it.house in VedicAstrologyUtils.KENDRA_HOUSES && VedicAstrologyUtils.isNaturalBenefic(it.planet)
        }
        if (beneficsInKendra.isNotEmpty()) {
            factors.add("Benefics in Kendra houses provide stability and protection")
        }

        // Strong Lagna Lord
        val lagnaLord = ascendantSign.ruler
        val lagnaLordPos = chart.planetPositions.find { it.planet == lagnaLord }
        lagnaLordPos?.let { pos ->
            if (VedicAstrologyUtils.isExalted(pos) || VedicAstrologyUtils.isInOwnSign(pos)) {
                factors.add("Strong Lagna lord ensures good vitality and resilience")
            }
        }

        // Saturn in 8th (longevity indicator)
        val saturnPos = chart.planetPositions.find { it.planet == Planet.SATURN }
        if (saturnPos?.house == 8) {
            factors.add("Saturn in 8th house indicates potential for long life")
        }

        // No malefics in Lagna
        val maleficsInLagna = chart.planetPositions.filter {
            it.house == 1 && VedicAstrologyUtils.isNaturalMalefic(it.planet)
        }
        if (maleficsInLagna.isEmpty()) {
            factors.add("Lagna free from malefic affliction supports good health")
        }

        return factors.take(5)
    }

    /**
     * Generate remedies based on Maraka analysis
     */
    private fun generateRemedies(
        primaryMarakas: List<MarakaPlanet>,
        secondaryMarakas: List<MarakaPlanet>,
        overallSeverity: MarakaSeverity
    ): List<MarakaRemedy> {
        val remedies = mutableListOf<MarakaRemedy>()

        // General Mritunjaya remedy for all Maraka conditions
        if (overallSeverity.level >= MarakaSeverity.MODERATE.level) {
            remedies.add(
                MarakaRemedy(
                    planet = null,
                    remedyType = "Mantra",
                    description = "Maha Mrityunjaya Mantra - the great death-conquering mantra",
                    mantra = "Om Tryambakam Yajamahe Sugandhim Pushtivardhanam | Urvarukamiva Bandhanan Mrityor Mukshiya Maamritat ||",
                    gemstone = null,
                    charity = "Donate medicines or support healthcare",
                    fasting = "Fast on Mondays for Lord Shiva",
                    deity = "Lord Shiva (Mrityunjaya form)",
                    effectiveness = 95
                )
            )
        }

        // Planet-specific remedies for primary Marakas
        primaryMarakas.filter { it.severity.level >= MarakaSeverity.MODERATE.level }.forEach { maraka ->
            remedies.add(createRemedyForPlanet(maraka.planet, maraka.severity))
        }

        // Add Ayushya (longevity) remedy
        remedies.add(
            MarakaRemedy(
                planet = null,
                remedyType = "Ritual",
                description = "Ayushya Homa - Fire ritual for longevity",
                mantra = "Om Ayur Dehim Dhanam Dehim Vidyam Dehim Maheshwari",
                gemstone = null,
                charity = "Donate food to Brahmins and the needy",
                fasting = null,
                deity = "Goddess Parvati and Lord Shiva",
                effectiveness = 85
            )
        )

        return remedies.distinctBy { it.description }.take(7)
    }

    /**
     * Create remedy for specific planet
     */
    private fun createRemedyForPlanet(planet: Planet, severity: MarakaSeverity): MarakaRemedy {
        return when (planet) {
            Planet.SUN -> MarakaRemedy(
                planet = Planet.SUN,
                remedyType = "Multi-fold",
                description = "Surya Shanti remedies for Sun as Maraka",
                mantra = "Om Hraam Hreem Hraum Sah Suryaya Namah",
                gemstone = "Ruby (Manikya) - wear only if Sun is functional benefic",
                charity = "Donate wheat, jaggery, copper to needy",
                fasting = "Fast on Sundays",
                deity = "Lord Surya (Sun God)",
                effectiveness = if (severity.level >= 4) 80 else 70
            )
            Planet.MOON -> MarakaRemedy(
                planet = Planet.MOON,
                remedyType = "Multi-fold",
                description = "Chandra Shanti remedies for Moon as Maraka",
                mantra = "Om Shraam Shreem Shraum Sah Chandraya Namah",
                gemstone = "Pearl (Moti) - wear only if Moon is functional benefic",
                charity = "Donate white rice, milk, white cloth",
                fasting = "Fast on Mondays",
                deity = "Lord Shiva",
                effectiveness = if (severity.level >= 4) 80 else 70
            )
            Planet.MARS -> MarakaRemedy(
                planet = Planet.MARS,
                remedyType = "Multi-fold",
                description = "Mangal Shanti remedies for Mars as Maraka",
                mantra = "Om Kraam Kreem Kraum Sah Bhaumaya Namah",
                gemstone = "Red Coral (Moonga) - with caution",
                charity = "Donate red lentils, jaggery, red cloth",
                fasting = "Fast on Tuesdays",
                deity = "Lord Hanuman",
                effectiveness = if (severity.level >= 4) 85 else 75
            )
            Planet.SATURN -> MarakaRemedy(
                planet = Planet.SATURN,
                remedyType = "Multi-fold",
                description = "Shani Shanti remedies for Saturn as Maraka",
                mantra = "Om Praam Preem Praum Sah Shanaischaraya Namah",
                gemstone = "Blue Sapphire (Neelam) - only after careful analysis",
                charity = "Donate black sesame, iron, oil to needy",
                fasting = "Fast on Saturdays",
                deity = "Lord Shani / Lord Hanuman",
                effectiveness = if (severity.level >= 4) 90 else 80
            )
            Planet.RAHU -> MarakaRemedy(
                planet = Planet.RAHU,
                remedyType = "Multi-fold",
                description = "Rahu Shanti remedies for Rahu as Maraka",
                mantra = "Om Bhraam Bhreem Bhraum Sah Rahave Namah",
                gemstone = "Hessonite (Gomed) - after careful analysis",
                charity = "Donate dark blue cloth, coconut, mustard oil",
                fasting = "Observe fast on Saturdays",
                deity = "Goddess Durga",
                effectiveness = if (severity.level >= 4) 85 else 75
            )
            Planet.KETU -> MarakaRemedy(
                planet = Planet.KETU,
                remedyType = "Multi-fold",
                description = "Ketu Shanti remedies for Ketu as Maraka",
                mantra = "Om Sraam Sreem Sraum Sah Ketave Namah",
                gemstone = "Cat's Eye (Lahsuniya) - after careful analysis",
                charity = "Donate grey/multicolored blankets",
                fasting = "Observe fast on Tuesdays",
                deity = "Lord Ganesha",
                effectiveness = if (severity.level >= 4) 80 else 70
            )
            else -> MarakaRemedy(
                planet = planet,
                remedyType = "General",
                description = "Planetary pacification for ${planet.displayName}",
                mantra = "Consult astrologer for specific mantra",
                gemstone = null,
                charity = "General charity on associated day",
                fasting = null,
                deity = null,
                effectiveness = 60
            )
        }
    }

    /**
     * Generate summary text
     */
    private fun generateSummary(
        primaryMarakas: List<MarakaPlanet>,
        secondaryMarakas: List<MarakaPlanet>,
        longevity: LongevityAnalysis,
        overallSeverity: MarakaSeverity
    ): String {
        val sb = StringBuilder()

        sb.append("Maraka Analysis Summary: ")
        sb.append("Primary Marakas: ${primaryMarakas.joinToString { it.planet.displayName }}. ")

        if (secondaryMarakas.isNotEmpty()) {
            sb.append("Secondary Marakas: ${secondaryMarakas.joinToString { it.planet.displayName }}. ")
        }

        sb.append("Longevity indication: ${longevity.category.displayName} (${longevity.category.yearsRange} years). ")
        sb.append("Overall Maraka severity: ${overallSeverity.displayName}.")

        return sb.toString()
    }

    /**
     * Generate detailed interpretation
     */
    private fun generateDetailedInterpretation(
        chart: VedicChart,
        primaryMarakas: List<MarakaPlanet>,
        secondaryMarakas: List<MarakaPlanet>,
        tertiaryMarakas: List<MarakaPlanet>,
        longevity: LongevityAnalysis,
        protectiveFactors: List<String>
    ): String {
        val sb = StringBuilder()

        sb.appendLine("=== Detailed Maraka Analysis ===")
        sb.appendLine()

        // Primary Marakas
        sb.appendLine("PRIMARY MARAKA PLANETS:")
        primaryMarakas.forEach { maraka ->
            sb.appendLine("• ${maraka.planet.displayName}: ${maraka.interpretation}")
        }
        sb.appendLine()

        // Secondary Marakas
        if (secondaryMarakas.isNotEmpty()) {
            sb.appendLine("SECONDARY MARAKA PLANETS:")
            secondaryMarakas.forEach { maraka ->
                sb.appendLine("• ${maraka.planet.displayName}: ${maraka.interpretation}")
            }
            sb.appendLine()
        }

        // Longevity
        sb.appendLine("LONGEVITY ASSESSMENT:")
        sb.appendLine("Category: ${longevity.category.displayName} (${longevity.category.description})")
        sb.appendLine("Range: ${longevity.category.yearsRange} years")
        sb.appendLine(longevity.interpretation)
        sb.appendLine()

        // Protective factors
        if (protectiveFactors.isNotEmpty()) {
            sb.appendLine("PROTECTIVE FACTORS:")
            protectiveFactors.forEach { factor ->
                sb.appendLine("✓ $factor")
            }
            sb.appendLine()
        }

        sb.appendLine("Note: This analysis is for educational purposes. Maraka periods do not necessarily indicate death - they can manifest as health challenges, major life transitions, or endings of phases. Always consult qualified professionals for health matters.")

        return sb.toString()
    }

    /**
     * Build interpretation for individual Maraka planet
     */
    private fun buildMarakaPlanetInterpretation(
        pos: PlanetPosition,
        marakaType: MarakaType,
        severity: MarakaSeverity,
        isDebilitated: Boolean,
        isExalted: Boolean,
        isCombust: Boolean,
        reasonDescription: String
    ): String {
        val sb = StringBuilder()
        sb.append("${pos.planet.displayName} is a ${marakaType.displayName}. ")
        sb.append(reasonDescription)
        sb.append(" Located in House ${pos.house} in ${pos.sign.displayName}. ")

        if (isExalted) sb.append("Being exalted, its effects are pronounced. ")
        if (isDebilitated) sb.append("Debilitation somewhat reduces its Maraka potency. ")
        if (isCombust) sb.append("Combustion weakens its independent effect. ")
        if (pos.isRetrograde) sb.append("Retrograde status may delay but intensify effects. ")

        sb.append("Maraka severity: ${severity.displayName}.")

        return sb.toString()
    }

    /**
     * Build longevity interpretation
     */
    private fun buildLongevityInterpretation(
        category: LongevityCategory,
        supportingFactors: List<String>,
        challengingFactors: List<String>
    ): String {
        val sb = StringBuilder()

        sb.append("Based on classical Vedic longevity indicators, the chart shows ${category.displayName} characteristics. ")

        when (category) {
            LongevityCategory.BALARISHTA -> {
                sb.append("Early childhood requires special protection and care. Traditional remedies are strongly recommended. ")
            }
            LongevityCategory.ALPAYU -> {
                sb.append("Health consciousness throughout life is important. Regular remedial measures support well-being. ")
            }
            LongevityCategory.MADHYAYU -> {
                sb.append("A balanced life approach with attention to health after middle age is indicated. ")
            }
            LongevityCategory.PURNAYU -> {
                sb.append("Good longevity potential exists. Maintaining dharmic lifestyle supports this indication. ")
            }
            LongevityCategory.AMITAYU -> {
                sb.append("Excellent longevity indicators are present. Continued righteous living strengthens this. ")
            }
        }

        if (supportingFactors.isNotEmpty()) {
            sb.append("Supporting factors include: ${supportingFactors.joinToString(", ")}. ")
        }

        if (challengingFactors.isNotEmpty()) {
            sb.append("Areas requiring attention: ${challengingFactors.joinToString(", ")}.")
        }

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
     * Get houses owned by a planet from a given Ascendant
     */
    private fun getOwnedHouses(planet: Planet, ascendant: ZodiacSign): List<Int> {
        val ownedHouses = mutableListOf<Int>()
        for (houseNum in 1..12) {
            val houseSignIndex = (ascendant.ordinal + houseNum - 1) % 12
            val houseSign = ZodiacSign.entries[houseSignIndex]
            if (houseSign.ruler == planet) {
                ownedHouses.add(houseNum)
            }
        }
        return ownedHouses
    }

    /**
     * Get houses aspected by a planet
     */
    private fun getAspectedHouses(pos: PlanetPosition): List<Int> {
        val aspects = mutableListOf<Int>()
        val planetHouse = pos.house

        // 7th aspect (all planets)
        aspects.add(((planetHouse + 6) % 12) + 1)

        // Special aspects
        when (pos.planet) {
            Planet.MARS -> {
                // 4th and 8th aspects
                aspects.add(((planetHouse + 3) % 12) + 1)
                aspects.add(((planetHouse + 7) % 12) + 1)
            }
            Planet.JUPITER -> {
                // 5th and 9th aspects
                aspects.add(((planetHouse + 4) % 12) + 1)
                aspects.add(((planetHouse + 8) % 12) + 1)
            }
            Planet.SATURN -> {
                // 3rd and 10th aspects
                aspects.add(((planetHouse + 2) % 12) + 1)
                aspects.add(((planetHouse + 9) % 12) + 1)
            }
            Planet.RAHU, Planet.KETU -> {
                // 5th and 9th aspects (like Jupiter)
                aspects.add(((planetHouse + 4) % 12) + 1)
                aspects.add(((planetHouse + 8) % 12) + 1)
            }
            else -> {}
        }

        return aspects.map { if (it == 0) 12 else it }
    }

    /**
     * Check if a house is aspected by a planet
     */
    private fun isAspectedBy(targetHouse: Int, pos: PlanetPosition, chart: VedicChart): Boolean {
        return targetHouse in getAspectedHouses(pos)
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
     * Check if planet is combust (too close to Sun)
     */
    private fun checkCombustion(chart: VedicChart, pos: PlanetPosition): Boolean {
        if (pos.planet == Planet.SUN) return false

        val sunPos = chart.planetPositions.find { it.planet == Planet.SUN } ?: return false
        val distance = kotlin.math.abs(pos.longitude - sunPos.longitude)
        val normalizedDistance = if (distance > 180) 360 - distance else distance

        val combustionOrb = when (pos.planet) {
            Planet.MOON -> 12.0
            Planet.MARS -> 17.0
            Planet.MERCURY -> 14.0  // 12 when retrograde
            Planet.JUPITER -> 11.0
            Planet.VENUS -> 10.0  // 8 when retrograde
            Planet.SATURN -> 15.0
            else -> 0.0
        }

        return normalizedDistance <= combustionOrb
    }
}
