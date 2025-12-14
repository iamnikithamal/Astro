package com.astro.storm.ephemeris

import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import kotlin.math.abs

/**
 * Comprehensive Divisional Chart Analyzer
 * Provides detailed interpretations for all 16 Varga charts per BPHS
 */
object DivisionalChartAnalyzer {

    // Hora Chart (D-2) Analysis for Wealth
    fun analyzeHora(chart: VedicChart): HoraAnalysis {
        val horaChart = DivisionalChartCalculator.calculateHora(chart)
        val sunHoraPlanets = mutableListOf<Planet>()
        val moonHoraPlanets = mutableListOf<Planet>()

        horaChart.planetPositions.forEach { position ->
            when (position.sign) {
                ZodiacSign.LEO -> sunHoraPlanets.add(position.planet)
                ZodiacSign.CANCER -> moonHoraPlanets.add(position.planet)
                else -> {}
            }
        }

        val secondLordD1 = getHouseLord(chart, 2)
        val eleventhLordD1 = getHouseLord(chart, 11)
        val secondLordInHora = horaChart.planetPositions.find { it.planet == secondLordD1 }
        val eleventhLordInHora = horaChart.planetPositions.find { it.planet == eleventhLordD1 }

        val wealthIndicators = mutableListOf<WealthIndicator>()

        // Sun Hora planets indicate self-earned wealth
        sunHoraPlanets.forEach { planet ->
            wealthIndicators.add(
                WealthIndicator(
                    planet = planet,
                    type = WealthType.SELF_EARNED,
                    strength = calculateHoraStrength(planet, ZodiacSign.LEO, chart),
                    sources = getSunHoraWealthSources(planet)
                )
            )
        }

        // Moon Hora planets indicate inherited/liquid wealth
        moonHoraPlanets.forEach { planet ->
            wealthIndicators.add(
                WealthIndicator(
                    planet = planet,
                    type = WealthType.INHERITED_LIQUID,
                    strength = calculateHoraStrength(planet, ZodiacSign.CANCER, chart),
                    sources = getMoonHoraWealthSources(planet)
                )
            )
        }

        val overallWealthPotential = calculateOverallWealthPotential(
            sunHoraPlanets, moonHoraPlanets, secondLordInHora, eleventhLordInHora, chart
        )

        val wealthTimingPeriods = calculateWealthTimingFromHora(chart, wealthIndicators)

        return HoraAnalysis(
            sunHoraPlanets = sunHoraPlanets,
            moonHoraPlanets = moonHoraPlanets,
            wealthIndicators = wealthIndicators,
            overallWealthPotential = overallWealthPotential,
            secondLordHoraSign = secondLordInHora?.sign,
            eleventhLordHoraSign = eleventhLordInHora?.sign,
            wealthTimingPeriods = wealthTimingPeriods,
            recommendations = generateHoraRecommendations(wealthIndicators, overallWealthPotential)
        )
    }

    // Drekkana Chart (D-3) Analysis for Siblings and Courage
    fun analyzeDrekkana(chart: VedicChart): DrekkanaAnalysis {
        val drekkanaChart = DivisionalChartCalculator.calculateDrekkana(chart)
        val marsPosition = drekkanaChart.planetPositions.find { it.planet == Planet.MARS }
        val thirdLordD1 = getHouseLord(chart, 3)
        val thirdLordInDrekkana = drekkanaChart.planetPositions.find { it.planet == thirdLordD1 }

        val siblingIndicators = analyzeSiblings(drekkanaChart, chart)
        val courageAnalysis = analyzeCourage(marsPosition, thirdLordInDrekkana, drekkanaChart)
        val communicationAnalysis = analyzeCommunication(drekkanaChart, chart)

        val thirdHousePlanetsD3 = drekkanaChart.planetPositions.filter { it.house == 3 }
        val eleventhHousePlanetsD3 = drekkanaChart.planetPositions.filter { it.house == 11 }

        return DrekkanaAnalysis(
            marsInDrekkana = marsPosition,
            thirdLordPosition = thirdLordInDrekkana,
            siblingIndicators = siblingIndicators,
            courageAnalysis = courageAnalysis,
            communicationSkills = communicationAnalysis,
            thirdHousePlanets = thirdHousePlanetsD3,
            eleventhHousePlanets = eleventhHousePlanetsD3,
            shortJourneyIndicators = analyzeShortJourneys(drekkanaChart),
            recommendations = generateDrekkanaRecommendations(courageAnalysis, siblingIndicators)
        )
    }

    // Navamsa (D-9) Marriage Timing Analysis
    fun analyzeNavamsaForMarriage(chart: VedicChart): NavamsaMarriageAnalysis {
        val navamsaChart = DivisionalChartCalculator.calculateNavamsa(chart)
        val venusD9 = navamsaChart.planetPositions.find { it.planet == Planet.VENUS }
        val jupiterD9 = navamsaChart.planetPositions.find { it.planet == Planet.JUPITER }
        val seventhLordD1 = getHouseLord(chart, 7)
        val seventhLordD9 = navamsaChart.planetPositions.find { it.planet == seventhLordD1 }
        val navamsaLagnaLord = navamsaChart.ascendantSign.ruler
        val navamsaLagnaLordPosition = navamsaChart.planetPositions.find { it.planet == navamsaLagnaLord }

        // Calculate Upapada (A2) - Arudha of 2nd house
        val upapadaSign = calculateUpapada(chart)
        val upapadaLord = upapadaSign.ruler
        val upapadaLordD9 = navamsaChart.planetPositions.find { it.planet == upapadaLord }

        // Calculate Darakaraka (planet with lowest degree excluding Rahu/Ketu)
        val darakaraka = calculateDarakaraka(chart)
        val darakarakaD9 = navamsaChart.planetPositions.find { it.planet == darakaraka }

        val marriageTimingFactors = analyzeMarriageTimingFactors(
            chart, navamsaChart, venusD9, jupiterD9, seventhLordD9, darakarakaD9
        )

        val spouseCharacteristics = analyzeSpouseCharacteristics(
            seventhLordD9, venusD9, darakarakaD9, upapadaLordD9, navamsaChart
        )

        val spouseDirection = calculateSpouseDirection(seventhLordD9, darakarakaD9)

        val multipleMarriageIndicators = analyzeMultipleMarriageFactors(chart, navamsaChart)

        return NavamsaMarriageAnalysis(
            venusInNavamsa = venusD9,
            jupiterInNavamsa = jupiterD9,
            seventhLordNavamsa = seventhLordD9,
            navamsaLagnaLordPosition = navamsaLagnaLordPosition,
            upapadaSign = upapadaSign,
            upapadaLordNavamsa = upapadaLordD9,
            darakaraka = darakaraka,
            darakarakaNavamsa = darakarakaD9,
            marriageTimingFactors = marriageTimingFactors,
            spouseCharacteristics = spouseCharacteristics,
            spouseDirection = spouseDirection,
            multipleMarriageIndicators = multipleMarriageIndicators,
            marriageMuhurtaCompatibility = analyzeMarriageMuhurtaCompatibility(chart, navamsaChart),
            recommendations = generateMarriageRecommendations(marriageTimingFactors)
        )
    }

    // Dashamsa (D-10) Career Guidance Analysis
    fun analyzeDashamsa(chart: VedicChart): DashamsaAnalysis {
        val dashamsaChart = DivisionalChartCalculator.calculateDasamsa(chart)
        val tenthLordD1 = getHouseLord(chart, 10)
        val tenthLordD10 = dashamsaChart.planetPositions.find { it.planet == tenthLordD1 }
        val sunD10 = dashamsaChart.planetPositions.find { it.planet == Planet.SUN }
        val saturnD10 = dashamsaChart.planetPositions.find { it.planet == Planet.SATURN }
        val mercuryD10 = dashamsaChart.planetPositions.find { it.planet == Planet.MERCURY }

        val careerType = determineCareerType(dashamsaChart, chart)
        val industryMappings = mapPlanetsToIndustries(dashamsaChart)
        val governmentServiceIndicators = analyzeGovernmentServicePotential(sunD10, dashamsaChart)
        val businessVsService = analyzeBusinessVsService(dashamsaChart, chart)
        val careerPeakTiming = calculateCareerPeakTiming(chart, dashamsaChart)
        val multipleCareerIndicators = analyzeMultipleCareers(dashamsaChart)

        return DashamsaAnalysis(
            tenthLordInDashamsa = tenthLordD10,
            sunInDashamsa = sunD10,
            saturnInDashamsa = saturnD10,
            mercuryInDashamsa = mercuryD10,
            dashamsaLagna = dashamsaChart.ascendantSign,
            careerTypes = careerType,
            industryMappings = industryMappings,
            governmentServicePotential = governmentServiceIndicators,
            businessVsServiceAptitude = businessVsService,
            careerPeakPeriods = careerPeakTiming,
            multipleCareerIndicators = multipleCareerIndicators,
            professionalStrengths = analyzeProfessionalStrengths(dashamsaChart),
            recommendations = generateCareerRecommendations(careerType, industryMappings)
        )
    }

    // Dwadasamsa (D-12) Parental Analysis
    fun analyzeDwadasamsa(chart: VedicChart): DwadasamsaAnalysis {
        val dwadasamsaChart = DivisionalChartCalculator.calculateDwadasamsa(chart)
        val sunD12 = dwadasamsaChart.planetPositions.find { it.planet == Planet.SUN }
        val moonD12 = dwadasamsaChart.planetPositions.find { it.planet == Planet.MOON }
        val ninthLordD1 = getHouseLord(chart, 9)
        val fourthLordD1 = getHouseLord(chart, 4)
        val ninthLordD12 = dwadasamsaChart.planetPositions.find { it.planet == ninthLordD1 }
        val fourthLordD12 = dwadasamsaChart.planetPositions.find { it.planet == fourthLordD1 }

        val fatherAnalysis = analyzeFatherIndicators(sunD12, ninthLordD12, dwadasamsaChart)
        val motherAnalysis = analyzeMotherIndicators(moonD12, fourthLordD12, dwadasamsaChart)
        val inheritanceAnalysis = analyzeInheritance(dwadasamsaChart, chart)
        val ancestralPropertyAnalysis = analyzeAncestralProperty(dwadasamsaChart)
        val familyLineageAnalysis = analyzeFamilyLineage(dwadasamsaChart)

        return DwadasamsaAnalysis(
            sunInDwadasamsa = sunD12,
            moonInDwadasamsa = moonD12,
            ninthLordPosition = ninthLordD12,
            fourthLordPosition = fourthLordD12,
            fatherAnalysis = fatherAnalysis,
            motherAnalysis = motherAnalysis,
            inheritanceIndicators = inheritanceAnalysis,
            ancestralPropertyIndicators = ancestralPropertyAnalysis,
            familyLineageInsights = familyLineageAnalysis,
            parentalLongevityIndicators = analyzeParentalLongevity(dwadasamsaChart, chart),
            recommendations = generateParentalRecommendations(fatherAnalysis, motherAnalysis)
        )
    }

    // Helper functions
    private fun getHouseLord(chart: VedicChart, house: Int): Planet {
        val ascendantSign = ZodiacSign.fromLongitude(chart.ascendant)
        val houseSign = ZodiacSign.entries[(ascendantSign.ordinal + house - 1) % 12]
        return houseSign.ruler
    }

    private fun calculateHoraStrength(planet: Planet, horaSign: ZodiacSign, chart: VedicChart): Double {
        var strength = 50.0

        // Check if planet is naturally suited for wealth
        if (planet in listOf(Planet.JUPITER, Planet.VENUS, Planet.MERCURY)) {
            strength += 15.0
        }

        // Sun Hora (Leo) favors Sun, Mars, Jupiter
        if (horaSign == ZodiacSign.LEO && planet in listOf(Planet.SUN, Planet.MARS, Planet.JUPITER)) {
            strength += 20.0
        }

        // Moon Hora (Cancer) favors Moon, Venus, Jupiter
        if (horaSign == ZodiacSign.CANCER && planet in listOf(Planet.MOON, Planet.VENUS, Planet.JUPITER)) {
            strength += 20.0
        }

        // Check D1 position dignity
        val d1Position = chart.planetPositions.find { it.planet == planet }
        if (d1Position != null) {
            if (AstrologicalConstants.isExalted(planet, d1Position.sign)) strength += 15.0
            if (AstrologicalConstants.isInOwnSign(planet, d1Position.sign)) strength += 10.0
            if (AstrologicalConstants.isDebilitated(planet, d1Position.sign)) strength -= 20.0
        }

        return strength.coerceIn(0.0, 100.0)
    }

    private fun getSunHoraWealthSources(planet: Planet): List<String> {
        return when (planet) {
            Planet.SUN -> listOf("Government positions", "Gold investments", "Father's legacy", "Authority roles")
            Planet.MARS -> listOf("Real estate", "Engineering", "Military/Police", "Sports")
            Planet.JUPITER -> listOf("Teaching", "Consultancy", "Banking", "Religious institutions")
            Planet.SATURN -> listOf("Mining", "Oil/Petroleum", "Labor management", "Land")
            Planet.MERCURY -> listOf("Business", "Communication", "Technology", "Trade")
            Planet.VENUS -> listOf("Arts", "Entertainment", "Luxury goods", "Beauty industry")
            Planet.MOON -> listOf("Public dealings", "Hospitality", "Dairy", "Liquids")
            Planet.RAHU -> listOf("Foreign sources", "Technology", "Unconventional means", "Speculation")
            Planet.KETU -> listOf("Spiritual pursuits", "Occult", "Detachment-based gains", "Research")
            else -> emptyList()
        }
    }

    private fun getMoonHoraWealthSources(planet: Planet): List<String> {
        return when (planet) {
            Planet.MOON -> listOf("Inheritance", "Mother's side", "Pearls", "Silver", "Liquids")
            Planet.VENUS -> listOf("Spouse's wealth", "Luxury inheritance", "Jewelry")
            Planet.JUPITER -> listOf("Religious inheritance", "Educational trusts", "Family wealth")
            Planet.MERCURY -> listOf("Family business", "Ancestral trade", "Intellectual property")
            Planet.SUN -> listOf("Government benefits", "Royal grants", "Authority inherited")
            Planet.MARS -> listOf("Property inheritance", "Land from family", "Military pension")
            Planet.SATURN -> listOf("Old wealth", "Delayed inheritance", "Hard-earned legacy")
            Planet.RAHU -> listOf("Foreign inheritance", "Unexpected gains", "In-laws wealth")
            Planet.KETU -> listOf("Spiritual assets", "Hidden treasures", "Past-life accumulated")
            else -> emptyList()
        }
    }

    private fun calculateOverallWealthPotential(
        sunHoraPlanets: List<Planet>,
        moonHoraPlanets: List<Planet>,
        secondLord: PlanetPosition?,
        eleventhLord: PlanetPosition?,
        chart: VedicChart
    ): WealthPotential {
        var score = 50.0

        // Benefics in hora increase wealth
        val beneficsInSunHora = sunHoraPlanets.count { it in AstrologicalConstants.NATURAL_BENEFICS }
        val beneficsInMoonHora = moonHoraPlanets.count { it in AstrologicalConstants.NATURAL_BENEFICS }
        score += (beneficsInSunHora + beneficsInMoonHora) * 8.0

        // 2nd and 11th lords well placed
        secondLord?.let {
            if (it.sign == ZodiacSign.LEO || it.sign == ZodiacSign.CANCER) score += 10.0
        }
        eleventhLord?.let {
            if (it.sign == ZodiacSign.LEO || it.sign == ZodiacSign.CANCER) score += 10.0
        }

        // Jupiter in good hora is excellent
        if (Planet.JUPITER in sunHoraPlanets || Planet.JUPITER in moonHoraPlanets) {
            score += 15.0
        }

        return when {
            score >= 85 -> WealthPotential.EXCEPTIONAL
            score >= 70 -> WealthPotential.HIGH
            score >= 55 -> WealthPotential.MODERATE
            score >= 40 -> WealthPotential.AVERAGE
            else -> WealthPotential.LOW
        }
    }

    private fun calculateWealthTimingFromHora(
        chart: VedicChart,
        indicators: List<WealthIndicator>
    ): List<WealthTimingPeriod> {
        val periods = mutableListOf<WealthTimingPeriod>()
        val strongIndicators = indicators.filter { it.strength > 60 }

        strongIndicators.forEach { indicator ->
            val dashaYears = DashaCalculator.getDashaYears(indicator.planet)
            periods.add(
                WealthTimingPeriod(
                    planet = indicator.planet,
                    type = indicator.type,
                    periodDescription = "${indicator.planet.displayName} Mahadasha/Antardasha",
                    favorableForWealth = indicator.strength > 65,
                    wealthSources = indicator.sources
                )
            )
        }

        return periods
    }

    private fun generateHoraRecommendations(
        indicators: List<WealthIndicator>,
        potential: WealthPotential
    ): List<String> {
        val recommendations = mutableListOf<String>()

        when (potential) {
            WealthPotential.EXCEPTIONAL, WealthPotential.HIGH -> {
                recommendations.add("Focus on indicated wealth sources for maximum prosperity")
                recommendations.add("Invest during favorable Dasha periods of strong Hora planets")
            }
            WealthPotential.MODERATE -> {
                recommendations.add("Consistent effort in indicated areas will yield gradual wealth")
                recommendations.add("Strengthen weak planets through appropriate remedies")
            }
            WealthPotential.AVERAGE, WealthPotential.LOW -> {
                recommendations.add("Worship Goddess Lakshmi for wealth blessings")
                recommendations.add("Strengthen Jupiter and Venus through gemstones or mantras")
                recommendations.add("Avoid speculation; focus on steady income sources")
            }
        }

        indicators.filter { it.strength > 70 }.forEach { indicator ->
            recommendations.add("Capitalize on ${indicator.planet.displayName}'s strength in ${indicator.sources.firstOrNull() ?: "indicated area"}")
        }

        return recommendations
    }

    private fun analyzeSiblings(
        drekkanaChart: DivisionalChartData,
        chart: VedicChart
    ): SiblingIndicators {
        val thirdHouseD3 = drekkanaChart.planetPositions.filter { it.house == 3 }
        val eleventhHouseD3 = drekkanaChart.planetPositions.filter { it.house == 11 }
        val marsD3 = drekkanaChart.planetPositions.find { it.planet == Planet.MARS }

        // Elder siblings from 11th house
        val elderSiblingCount = estimateSiblingCount(eleventhHouseD3)
        // Younger siblings from 3rd house
        val youngerSiblingCount = estimateSiblingCount(thirdHouseD3)

        val siblingRelationshipQuality = assessSiblingRelationship(thirdHouseD3, eleventhHouseD3, marsD3)

        return SiblingIndicators(
            estimatedYoungerSiblings = youngerSiblingCount,
            estimatedElderSiblings = elderSiblingCount,
            relationshipQuality = siblingRelationshipQuality,
            youngerSiblingPlanets = thirdHouseD3.map { it.planet },
            elderSiblingPlanets = eleventhHouseD3.map { it.planet },
            siblingWelfareIndicators = assessSiblingWelfare(drekkanaChart)
        )
    }

    private fun estimateSiblingCount(housePlanets: List<PlanetPosition>): IntRange {
        return when {
            housePlanets.isEmpty() -> 0..1
            housePlanets.size == 1 -> 1..2
            housePlanets.size == 2 -> 2..3
            else -> 3..5
        }
    }

    private fun assessSiblingRelationship(
        thirdHouse: List<PlanetPosition>,
        eleventhHouse: List<PlanetPosition>,
        mars: PlanetPosition?
    ): RelationshipQuality {
        val allPlanets = thirdHouse + eleventhHouse
        val beneficCount = allPlanets.count { it.planet in AstrologicalConstants.NATURAL_BENEFICS }
        val maleficCount = allPlanets.count { it.planet in AstrologicalConstants.NATURAL_MALEFICS }

        return when {
            beneficCount > maleficCount + 1 -> RelationshipQuality.EXCELLENT
            beneficCount > maleficCount -> RelationshipQuality.GOOD
            beneficCount == maleficCount -> RelationshipQuality.NEUTRAL
            maleficCount > beneficCount -> RelationshipQuality.CHALLENGING
            else -> RelationshipQuality.DIFFICULT
        }
    }

    private fun assessSiblingWelfare(chart: DivisionalChartData): List<String> {
        val insights = mutableListOf<String>()
        val thirdHousePlanets = chart.planetPositions.filter { it.house == 3 }

        if (Planet.JUPITER in thirdHousePlanets.map { it.planet }) {
            insights.add("Jupiter's blessing on younger siblings - prosperity for them")
        }
        if (Planet.SATURN in thirdHousePlanets.map { it.planet }) {
            insights.add("Saturn indicates hard-working siblings with delayed success")
        }
        if (Planet.MARS in thirdHousePlanets.map { it.planet }) {
            insights.add("Mars shows courageous and competitive siblings")
        }

        return insights
    }

    private fun analyzeCourage(
        mars: PlanetPosition?,
        thirdLord: PlanetPosition?,
        chart: DivisionalChartData
    ): CourageAnalysis {
        var courageScore = 50.0

        mars?.let {
            // Mars in good dignity increases courage
            if (AstrologicalConstants.isExalted(Planet.MARS, it.sign)) courageScore += 25.0
            if (AstrologicalConstants.isInOwnSign(Planet.MARS, it.sign)) courageScore += 20.0
            if (AstrologicalConstants.isDebilitated(Planet.MARS, it.sign)) courageScore -= 20.0

            // Mars in 3rd house is excellent for courage
            if (it.house == 3) courageScore += 15.0
        }

        thirdLord?.let {
            // Well-placed 3rd lord
            if (it.house in AstrologicalConstants.KENDRA_HOUSES) courageScore += 10.0
            if (it.house in AstrologicalConstants.TRIKONA_HOUSES) courageScore += 10.0
        }

        val courageLevel = when {
            courageScore >= 80 -> CourageLevel.EXCEPTIONAL
            courageScore >= 65 -> CourageLevel.HIGH
            courageScore >= 50 -> CourageLevel.MODERATE
            courageScore >= 35 -> CourageLevel.LOW
            else -> CourageLevel.VERY_LOW
        }

        return CourageAnalysis(
            overallCourageLevel = courageLevel,
            marsStrength = mars?.let { calculatePlanetStrengthInVarga(it) } ?: 0.0,
            initiativeAbility = assessInitiativeAbility(mars, thirdLord),
            physicalCourage = assessPhysicalCourage(mars),
            mentalCourage = assessMentalCourage(thirdLord, chart)
        )
    }

    private fun calculatePlanetStrengthInVarga(position: PlanetPosition): Double {
        var strength = 50.0
        if (AstrologicalConstants.isExalted(position.planet, position.sign)) strength += 30.0
        if (AstrologicalConstants.isInOwnSign(position.planet, position.sign)) strength += 20.0
        if (AstrologicalConstants.isDebilitated(position.planet, position.sign)) strength -= 25.0
        return strength.coerceIn(0.0, 100.0)
    }

    private fun assessInitiativeAbility(mars: PlanetPosition?, thirdLord: PlanetPosition?): String {
        val marsStrong = mars?.let {
            AstrologicalConstants.isExalted(Planet.MARS, it.sign) ||
            AstrologicalConstants.isInOwnSign(Planet.MARS, it.sign)
        } ?: false

        return if (marsStrong) {
            "Strong initiative ability - natural leader and pioneer"
        } else {
            "Moderate initiative - benefits from preparation and planning"
        }
    }

    private fun assessPhysicalCourage(mars: PlanetPosition?): String {
        return when {
            mars == null -> "Physical courage depends on other factors"
            AstrologicalConstants.isExalted(Planet.MARS, mars.sign) -> "Exceptional physical courage and athletic ability"
            AstrologicalConstants.isInOwnSign(Planet.MARS, mars.sign) -> "Strong physical constitution and bravery"
            AstrologicalConstants.isDebilitated(Planet.MARS, mars.sign) -> "Physical courage may need development"
            else -> "Adequate physical courage for normal situations"
        }
    }

    private fun assessMentalCourage(thirdLord: PlanetPosition?, chart: DivisionalChartData): String {
        val mercuryD3 = chart.planetPositions.find { it.planet == Planet.MERCURY }
        val jupiterD3 = chart.planetPositions.find { it.planet == Planet.JUPITER }

        return when {
            jupiterD3?.house == 3 -> "Exceptional wisdom-backed mental courage"
            mercuryD3?.house == 3 -> "Sharp intellectual courage and quick decision-making"
            thirdLord?.house in AstrologicalConstants.KENDRA_HOUSES -> "Stable mental fortitude"
            else -> "Mental courage develops through life experiences"
        }
    }

    private fun analyzeCommunication(chart: DivisionalChartData, d1Chart: VedicChart): CommunicationAnalysis {
        val mercuryD3 = chart.planetPositions.find { it.planet == Planet.MERCURY }
        val thirdHousePlanets = chart.planetPositions.filter { it.house == 3 }

        val writingAbility = assessWritingAbility(mercuryD3, thirdHousePlanets)
        val speakingAbility = assessSpeakingAbility(mercuryD3, chart)
        val artisticTalents = assessArtisticTalents(chart)

        return CommunicationAnalysis(
            overallSkillLevel = calculateCommunicationLevel(mercuryD3, thirdHousePlanets),
            writingAbility = writingAbility,
            speakingAbility = speakingAbility,
            artisticTalents = artisticTalents,
            mercuryStrength = mercuryD3?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0
        )
    }

    private fun calculateCommunicationLevel(mercury: PlanetPosition?, thirdHouse: List<PlanetPosition>): String {
        val mercuryStrong = mercury?.let {
            AstrologicalConstants.isExalted(Planet.MERCURY, it.sign) ||
            AstrologicalConstants.isInOwnSign(Planet.MERCURY, it.sign)
        } ?: false

        return when {
            mercuryStrong && thirdHouse.any { it.planet == Planet.JUPITER } -> "Exceptional"
            mercuryStrong -> "Very Good"
            thirdHouse.any { it.planet in AstrologicalConstants.NATURAL_BENEFICS } -> "Good"
            else -> "Average"
        }
    }

    private fun assessWritingAbility(mercury: PlanetPosition?, thirdHouse: List<PlanetPosition>): String {
        return when {
            mercury?.house == 3 && AstrologicalConstants.isInOwnSign(Planet.MERCURY, mercury.sign) ->
                "Exceptional writing talent - potential author or journalist"
            thirdHouse.any { it.planet == Planet.JUPITER } ->
                "Strong writing ability with wisdom and depth"
            thirdHouse.any { it.planet == Planet.MERCURY } ->
                "Good written communication skills"
            else -> "Average writing ability"
        }
    }

    private fun assessSpeakingAbility(mercury: PlanetPosition?, chart: DivisionalChartData): String {
        val sunD3 = chart.planetPositions.find { it.planet == Planet.SUN }
        val jupiterD3 = chart.planetPositions.find { it.planet == Planet.JUPITER }

        return when {
            mercury != null && jupiterD3?.house == 2 -> "Eloquent speaker with wisdom"
            sunD3?.house == 3 -> "Authoritative and commanding speech"
            mercury?.house == 3 -> "Quick and articulate speech"
            else -> "Normal speaking abilities"
        }
    }

    private fun assessArtisticTalents(chart: DivisionalChartData): List<String> {
        val talents = mutableListOf<String>()
        val venusD3 = chart.planetPositions.find { it.planet == Planet.VENUS }
        val moonD3 = chart.planetPositions.find { it.planet == Planet.MOON }

        venusD3?.let {
            if (it.house == 3 || it.house == 5) talents.add("Visual arts and aesthetics")
        }
        moonD3?.let {
            if (it.house == 3) talents.add("Music and rhythm")
        }

        return talents
    }

    private fun analyzeShortJourneys(chart: DivisionalChartData): List<String> {
        val indicators = mutableListOf<String>()
        val thirdHousePlanets = chart.planetPositions.filter { it.house == 3 }

        if (thirdHousePlanets.any { it.planet == Planet.MOON }) {
            indicators.add("Frequent short travels likely")
        }
        if (thirdHousePlanets.any { it.planet == Planet.MERCURY }) {
            indicators.add("Travel for communication or business purposes")
        }
        if (thirdHousePlanets.any { it.planet == Planet.RAHU }) {
            indicators.add("Unusual or sudden short journeys")
        }

        return indicators
    }

    private fun generateDrekkanaRecommendations(
        courage: CourageAnalysis,
        siblings: SiblingIndicators
    ): List<String> {
        val recommendations = mutableListOf<String>()

        when (courage.overallCourageLevel) {
            CourageLevel.EXCEPTIONAL, CourageLevel.HIGH -> {
                recommendations.add("Channel courage into leadership and pioneering ventures")
            }
            CourageLevel.LOW, CourageLevel.VERY_LOW -> {
                recommendations.add("Practice Mars-strengthening activities like sports or martial arts")
                recommendations.add("Worship Lord Hanuman for courage")
            }
            else -> {
                recommendations.add("Maintain balance between caution and boldness")
            }
        }

        when (siblings.relationshipQuality) {
            RelationshipQuality.CHALLENGING, RelationshipQuality.DIFFICULT -> {
                recommendations.add("Practice patience and understanding with siblings")
                recommendations.add("Consider family counseling if needed")
            }
            else -> {}
        }

        return recommendations
    }

    // Navamsa helper functions
    private fun calculateUpapada(chart: VedicChart): ZodiacSign {
        val secondLord = getHouseLord(chart, 2)
        val secondLordPosition = chart.planetPositions.find { it.planet == secondLord }
        val secondLordHouse = secondLordPosition?.house ?: 2

        // Upapada = As far from 2nd house as 2nd lord is from 2nd house
        val ascendantSign = ZodiacSign.fromLongitude(chart.ascendant)
        val offset = (secondLordHouse - 2 + 12) % 12
        val upapadaHouse = (2 + offset - 1 + 12) % 12 + 1

        return ZodiacSign.entries[(ascendantSign.ordinal + upapadaHouse - 1) % 12]
    }

    private fun calculateDarakaraka(chart: VedicChart): Planet {
        val eligiblePlanets = chart.planetPositions
            .filter { it.planet !in listOf(Planet.RAHU, Planet.KETU, Planet.URANUS, Planet.NEPTUNE, Planet.PLUTO) }

        // Darakaraka is the planet with the lowest degree in its sign
        return eligiblePlanets
            .minByOrNull { it.longitude % 30.0 }
            ?.planet ?: Planet.VENUS
    }

    private fun analyzeMarriageTimingFactors(
        chart: VedicChart,
        navamsaChart: DivisionalChartData,
        venus: PlanetPosition?,
        jupiter: PlanetPosition?,
        seventhLord: PlanetPosition?,
        darakaraka: PlanetPosition?
    ): MarriageTimingFactors {
        val favorablePlanets = mutableListOf<Planet>()

        venus?.let {
            if (it.house in listOf(1, 5, 7, 9, 11)) favorablePlanets.add(Planet.VENUS)
        }
        jupiter?.let {
            if (it.house in listOf(1, 5, 7, 9, 11)) favorablePlanets.add(Planet.JUPITER)
        }
        seventhLord?.let {
            favorablePlanets.add(it.planet)
        }

        return MarriageTimingFactors(
            favorableDashaPlanets = favorablePlanets,
            venusNavamsaStrength = venus?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0,
            seventhLordStrength = seventhLord?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0,
            darakarakaStrength = darakaraka?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0,
            transitConsiderations = "Jupiter transit over 7th house from Moon or Navamsa Lagna"
        )
    }

    private fun analyzeSpouseCharacteristics(
        seventhLord: PlanetPosition?,
        venus: PlanetPosition?,
        darakaraka: PlanetPosition?,
        upapadaLord: PlanetPosition?,
        navamsaChart: DivisionalChartData
    ): SpouseCharacteristics {
        val spouseNature = determineSpouseNature(seventhLord, venus)
        val spouseAppearance = determineSpouseAppearance(darakaraka, venus)
        val spouseProfession = estimateSpouseProfession(seventhLord, navamsaChart)

        return SpouseCharacteristics(
            generalNature = spouseNature,
            physicalTraits = spouseAppearance,
            probableProfessions = spouseProfession,
            familyBackground = determineSpouseFamilyBackground(upapadaLord)
        )
    }

    private fun determineSpouseNature(seventhLord: PlanetPosition?, venus: PlanetPosition?): String {
        val dominantPlanet = seventhLord?.planet ?: venus?.planet ?: Planet.VENUS

        return when (dominantPlanet) {
            Planet.SUN -> "Dignified, authoritative, proud, loyal"
            Planet.MOON -> "Emotional, nurturing, sensitive, caring"
            Planet.MARS -> "Energetic, assertive, passionate, athletic"
            Planet.MERCURY -> "Intelligent, communicative, youthful, adaptable"
            Planet.JUPITER -> "Wise, religious, generous, optimistic"
            Planet.VENUS -> "Beautiful, artistic, romantic, pleasure-loving"
            Planet.SATURN -> "Serious, hardworking, mature, responsible"
            Planet.RAHU -> "Unconventional, foreign influence, ambitious"
            Planet.KETU -> "Spiritual, detached, intuitive, mysterious"
            else -> "Mixed qualities"
        }
    }

    private fun determineSpouseAppearance(darakaraka: PlanetPosition?, venus: PlanetPosition?): String {
        val planet = darakaraka?.planet ?: venus?.planet ?: Planet.VENUS

        return when (planet) {
            Planet.SUN -> "Medium height, well-built, commanding presence"
            Planet.MOON -> "Fair complexion, round face, attractive"
            Planet.MARS -> "Athletic build, sharp features, reddish complexion"
            Planet.MERCURY -> "Youthful appearance, quick movements"
            Planet.JUPITER -> "Well-proportioned, dignified appearance"
            Planet.VENUS -> "Very attractive, charming, pleasant features"
            Planet.SATURN -> "Tall, thin build, mature appearance"
            else -> "Appearance varies"
        }
    }

    private fun estimateSpouseProfession(seventhLord: PlanetPosition?, chart: DivisionalChartData): List<String> {
        val professions = mutableListOf<String>()
        val planet = seventhLord?.planet ?: return listOf("Various professions possible")

        professions.addAll(when (planet) {
            Planet.SUN -> listOf("Government", "Administration", "Medicine")
            Planet.MOON -> listOf("Healthcare", "Hospitality", "Public relations")
            Planet.MARS -> listOf("Engineering", "Military", "Sports")
            Planet.MERCURY -> listOf("Business", "Writing", "Technology")
            Planet.JUPITER -> listOf("Teaching", "Law", "Finance")
            Planet.VENUS -> listOf("Arts", "Fashion", "Entertainment")
            Planet.SATURN -> listOf("Labor", "Construction", "Agriculture")
            else -> listOf("Various fields")
        })

        return professions
    }

    private fun determineSpouseFamilyBackground(upapadaLord: PlanetPosition?): String {
        return when (upapadaLord?.planet) {
            Planet.JUPITER -> "Respectable, possibly from educated or religious family"
            Planet.VENUS -> "Cultured family with appreciation for arts"
            Planet.SUN -> "Family with government or administrative background"
            Planet.MOON -> "Family-oriented, possibly business background"
            Planet.SATURN -> "Hardworking family, possibly modest beginnings"
            else -> "Family background varies"
        }
    }

    private fun calculateSpouseDirection(seventhLord: PlanetPosition?, darakaraka: PlanetPosition?): String {
        val sign = seventhLord?.sign ?: darakaraka?.sign ?: return "Direction not clearly indicated"

        return when (sign) {
            ZodiacSign.ARIES, ZodiacSign.LEO, ZodiacSign.SAGITTARIUS -> "East direction"
            ZodiacSign.TAURUS, ZodiacSign.VIRGO, ZodiacSign.CAPRICORN -> "South direction"
            ZodiacSign.GEMINI, ZodiacSign.LIBRA, ZodiacSign.AQUARIUS -> "West direction"
            ZodiacSign.CANCER, ZodiacSign.SCORPIO, ZodiacSign.PISCES -> "North direction"
        }
    }

    private fun analyzeMultipleMarriageFactors(
        chart: VedicChart,
        navamsaChart: DivisionalChartData
    ): MultipleMarriageIndicators {
        val indicators = mutableListOf<String>()
        var risk = 0

        val seventhLordD1 = getHouseLord(chart, 7)
        val seventhLordPositionD1 = chart.planetPositions.find { it.planet == seventhLordD1 }
        val marsD7 = chart.planetPositions.find { it.planet == Planet.MARS }
        val venusD1 = chart.planetPositions.find { it.planet == Planet.VENUS }
        val seventhHousePlanets = chart.planetPositions.filter {
            ((it.planet.ordinal - ZodiacSign.fromLongitude(chart.ascendant).ordinal + 12) % 12) + 1 == 7
        }

        // Check for multiple marriage indicators
        if (seventhHousePlanets.size >= 2) {
            indicators.add("Multiple planets in 7th house")
            risk++
        }

        if (venusD1?.isRetrograde == true) {
            indicators.add("Retrograde Venus")
            risk++
        }

        if (seventhLordPositionD1?.house in AstrologicalConstants.DUSTHANA_HOUSES) {
            indicators.add("7th lord in dusthana")
            risk++
        }

        val hasRisk = risk >= 2

        return MultipleMarriageIndicators(
            hasStrongIndicators = hasRisk,
            riskFactors = indicators,
            mitigatingFactors = if (!hasRisk) listOf("No strong multiple marriage indicators") else emptyList()
        )
    }

    private fun analyzeMarriageMuhurtaCompatibility(
        chart: VedicChart,
        navamsaChart: DivisionalChartData
    ): String {
        val moonSign = chart.planetPositions.find { it.planet == Planet.MOON }?.sign
        return "Best muhurta when Jupiter transits $moonSign or its trikona"
    }

    private fun generateMarriageRecommendations(factors: MarriageTimingFactors): List<String> {
        val recommendations = mutableListOf<String>()

        if (factors.venusNavamsaStrength > 70) {
            recommendations.add("Venus is strong - romantic approach to marriage is favorable")
        }
        if (factors.venusNavamsaStrength < 50) {
            recommendations.add("Strengthen Venus through white colors, Fridays worship, and diamond/white sapphire")
        }

        recommendations.add("Best marriage timing: During ${factors.favorableDashaPlanets.joinToString(", ") { it.displayName }} Dasha/Antardasha")
        recommendations.add(factors.transitConsiderations)

        return recommendations
    }

    // Dashamsa helper functions
    private fun determineCareerType(dashamsaChart: DivisionalChartData, chart: VedicChart): List<CareerType> {
        val careerTypes = mutableListOf<CareerType>()
        val tenthHouseD10 = dashamsaChart.planetPositions.filter { it.house == 10 }
        val lagnaD10 = dashamsaChart.ascendantSign

        // Analyze 10th house planets in D10
        tenthHouseD10.forEach { position ->
            careerTypes.add(getCareerTypeFromPlanet(position.planet))
        }

        // Analyze D10 lagna lord
        val lagnaLord = lagnaD10.ruler
        val lagnaLordPosition = dashamsaChart.planetPositions.find { it.planet == lagnaLord }
        lagnaLordPosition?.let {
            careerTypes.add(getCareerTypeFromPlanet(it.planet))
        }

        return careerTypes.distinct()
    }

    private fun getCareerTypeFromPlanet(planet: Planet): CareerType {
        return when (planet) {
            Planet.SUN -> CareerType(
                name = "Administrative/Government",
                industries = listOf("Government", "Administration", "Politics", "Medicine"),
                suitability = "Leadership positions, authority roles"
            )
            Planet.MOON -> CareerType(
                name = "Public Service/Nurturing",
                industries = listOf("Healthcare", "Hospitality", "Public Relations", "Nursing"),
                suitability = "People-facing roles, caring professions"
            )
            Planet.MARS -> CareerType(
                name = "Technical/Competitive",
                industries = listOf("Engineering", "Military", "Police", "Surgery", "Sports"),
                suitability = "Action-oriented careers requiring courage"
            )
            Planet.MERCURY -> CareerType(
                name = "Communication/Business",
                industries = listOf("Commerce", "Accounting", "Writing", "IT", "Trading"),
                suitability = "Intellectual and analytical roles"
            )
            Planet.JUPITER -> CareerType(
                name = "Advisory/Educational",
                industries = listOf("Teaching", "Law", "Consultancy", "Finance", "Religion"),
                suitability = "Positions requiring wisdom and guidance"
            )
            Planet.VENUS -> CareerType(
                name = "Creative/Luxury",
                industries = listOf("Arts", "Entertainment", "Fashion", "Luxury goods", "Hotels"),
                suitability = "Aesthetic and pleasure-related industries"
            )
            Planet.SATURN -> CareerType(
                name = "Labor/Resource",
                industries = listOf("Mining", "Construction", "Oil", "Agriculture", "Labor management"),
                suitability = "Careers requiring persistence and hard work"
            )
            Planet.RAHU -> CareerType(
                name = "Unconventional/Foreign",
                industries = listOf("Technology", "Foreign companies", "Research", "Media"),
                suitability = "Innovative and cutting-edge fields"
            )
            Planet.KETU -> CareerType(
                name = "Spiritual/Research",
                industries = listOf("Spirituality", "Occult", "Research", "Psychology"),
                suitability = "Fields requiring deep investigation"
            )
            else -> CareerType("General", emptyList(), "Various career possibilities")
        }
    }

    private fun mapPlanetsToIndustries(chart: DivisionalChartData): Map<Planet, List<String>> {
        val mapping = mutableMapOf<Planet, List<String>>()

        chart.planetPositions.filter { it.house in listOf(1, 10) }.forEach { position ->
            mapping[position.planet] = getIndustriesForPlanet(position.planet)
        }

        return mapping
    }

    private fun getIndustriesForPlanet(planet: Planet): List<String> {
        return when (planet) {
            Planet.SUN -> listOf("Government", "Administration", "Medicine", "Gold/Jewelry")
            Planet.MOON -> listOf("Hospitality", "Nursing", "Dairy", "Public relations", "Liquids")
            Planet.MARS -> listOf("Military", "Police", "Surgery", "Engineering", "Real estate")
            Planet.MERCURY -> listOf("Commerce", "Accounting", "Writing", "IT", "Communication")
            Planet.JUPITER -> listOf("Teaching", "Law", "Consultancy", "Finance", "Banking")
            Planet.VENUS -> listOf("Arts", "Entertainment", "Fashion", "Luxury goods", "Beauty")
            Planet.SATURN -> listOf("Mining", "Labor", "Construction", "Oil", "Agriculture")
            Planet.RAHU -> listOf("Technology", "Foreign trade", "Airlines", "Chemicals")
            Planet.KETU -> listOf("Spirituality", "Occult", "Alternative medicine", "Research")
            else -> emptyList()
        }
    }

    private fun analyzeGovernmentServicePotential(
        sun: PlanetPosition?,
        chart: DivisionalChartData
    ): GovernmentServiceAnalysis {
        var potential = 50.0

        sun?.let {
            if (it.house == 10) potential += 25.0
            if (it.house == 1) potential += 15.0
            if (AstrologicalConstants.isExalted(Planet.SUN, it.sign)) potential += 20.0
            if (AstrologicalConstants.isInOwnSign(Planet.SUN, it.sign)) potential += 15.0
        }

        val saturnD10 = chart.planetPositions.find { it.planet == Planet.SATURN }
        saturnD10?.let {
            if (it.house in listOf(1, 10)) potential += 10.0
        }

        return GovernmentServiceAnalysis(
            potential = when {
                potential >= 80 -> "Very High"
                potential >= 65 -> "High"
                potential >= 50 -> "Moderate"
                else -> "Low"
            },
            favorableFactors = buildList {
                sun?.let {
                    if (it.house == 10) add("Sun in 10th house of D10")
                    if (AstrologicalConstants.isExalted(Planet.SUN, it.sign)) add("Exalted Sun")
                }
            },
            recommendedDepartments = if (potential > 60) {
                listOf("Administration", "Revenue", "Foreign Affairs", "Defense")
            } else emptyList()
        )
    }

    private fun analyzeBusinessVsService(
        dashamsaChart: DivisionalChartData,
        chart: VedicChart
    ): BusinessVsServiceAnalysis {
        var businessScore = 0
        var serviceScore = 0

        val mercuryD10 = dashamsaChart.planetPositions.find { it.planet == Planet.MERCURY }
        val saturnD10 = dashamsaChart.planetPositions.find { it.planet == Planet.SATURN }
        val rahuD10 = dashamsaChart.planetPositions.find { it.planet == Planet.RAHU }

        mercuryD10?.let {
            if (it.house in listOf(1, 7, 10)) businessScore += 2
        }
        rahuD10?.let {
            if (it.house in listOf(1, 7, 10)) businessScore += 2
        }
        saturnD10?.let {
            if (it.house in listOf(1, 6, 10)) serviceScore += 2
        }

        val tenthHousePlanets = dashamsaChart.planetPositions.filter { it.house == 10 }
        if (tenthHousePlanets.any { it.planet in listOf(Planet.MERCURY, Planet.VENUS, Planet.RAHU) }) {
            businessScore++
        }
        if (tenthHousePlanets.any { it.planet in listOf(Planet.SUN, Planet.SATURN, Planet.MOON) }) {
            serviceScore++
        }

        return BusinessVsServiceAnalysis(
            businessAptitude = businessScore,
            serviceAptitude = serviceScore,
            recommendation = when {
                businessScore > serviceScore + 1 -> "Strong aptitude for business/entrepreneurship"
                serviceScore > businessScore + 1 -> "Better suited for service/employment"
                else -> "Can excel in both business and service"
            },
            businessSectors = if (businessScore > serviceScore) {
                getIndustriesForPlanet(mercuryD10?.planet ?: Planet.MERCURY)
            } else emptyList()
        )
    }

    private fun calculateCareerPeakTiming(
        chart: VedicChart,
        dashamsaChart: DivisionalChartData
    ): List<CareerPeakPeriod> {
        val periods = mutableListOf<CareerPeakPeriod>()

        val tenthLordD1 = getHouseLord(chart, 10)
        val strongD10Planets = dashamsaChart.planetPositions.filter { position ->
            position.house in listOf(1, 10) ||
            AstrologicalConstants.isExalted(position.planet, position.sign) ||
            AstrologicalConstants.isInOwnSign(position.planet, position.sign)
        }

        periods.add(CareerPeakPeriod(
            planet = tenthLordD1,
            description = "10th lord Dasha - Primary career growth period",
            significance = "Major career advancements and recognition"
        ))

        strongD10Planets.forEach { position ->
            periods.add(CareerPeakPeriod(
                planet = position.planet,
                description = "${position.planet.displayName} Dasha/Antardasha",
                significance = "Career opportunities in ${getIndustriesForPlanet(position.planet).firstOrNull() ?: "related field"}"
            ))
        }

        return periods
    }

    private fun analyzeMultipleCareers(chart: DivisionalChartData): List<String> {
        val indicators = mutableListOf<String>()
        val tenthHousePlanets = chart.planetPositions.filter { it.house == 10 }

        if (tenthHousePlanets.size >= 2) {
            indicators.add("Multiple planets in 10th house indicate potential for multiple careers")
        }

        val mercuryD10 = chart.planetPositions.find { it.planet == Planet.MERCURY }
        if (mercuryD10?.house == 10 || mercuryD10?.house == 1) {
            indicators.add("Strong Mercury suggests versatility in career")
        }

        val rahuD10 = chart.planetPositions.find { it.planet == Planet.RAHU }
        if (rahuD10?.house in listOf(1, 10)) {
            indicators.add("Rahu indicates unconventional career path or career changes")
        }

        return indicators
    }

    private fun analyzeProfessionalStrengths(chart: DivisionalChartData): List<String> {
        val strengths = mutableListOf<String>()
        val lagnaLord = chart.ascendantSign.ruler
        val lagnaLordPosition = chart.planetPositions.find { it.planet == lagnaLord }

        lagnaLordPosition?.let {
            if (it.house in AstrologicalConstants.KENDRA_HOUSES) {
                strengths.add("Strong professional identity and presence")
            }
            if (AstrologicalConstants.isExalted(it.planet, it.sign)) {
                strengths.add("Exceptional abilities in chosen field")
            }
        }

        val tenthHousePlanets = chart.planetPositions.filter { it.house == 10 }
        tenthHousePlanets.forEach { position ->
            when (position.planet) {
                Planet.SUN -> strengths.add("Leadership and authority")
                Planet.MOON -> strengths.add("Public appeal and emotional intelligence")
                Planet.MARS -> strengths.add("Drive and competitive spirit")
                Planet.MERCURY -> strengths.add("Communication and analytical skills")
                Planet.JUPITER -> strengths.add("Wisdom and advisory capabilities")
                Planet.VENUS -> strengths.add("Creativity and relationship skills")
                Planet.SATURN -> strengths.add("Persistence and organizational ability")
                else -> {}
            }
        }

        return strengths
    }

    private fun generateCareerRecommendations(
        careerTypes: List<CareerType>,
        industryMappings: Map<Planet, List<String>>
    ): List<String> {
        val recommendations = mutableListOf<String>()

        if (careerTypes.isNotEmpty()) {
            recommendations.add("Primary career focus: ${careerTypes.firstOrNull()?.name ?: "Various options"}")
            careerTypes.firstOrNull()?.industries?.take(3)?.let { industries ->
                recommendations.add("Top industries: ${industries.joinToString(", ")}")
            }
        }

        industryMappings.entries.take(2).forEach { (planet, industries) ->
            recommendations.add("${planet.displayName} period favors: ${industries.firstOrNull() ?: "general work"}")
        }

        return recommendations
    }

    // Dwadasamsa helper functions
    private fun analyzeFatherIndicators(
        sun: PlanetPosition?,
        ninthLord: PlanetPosition?,
        chart: DivisionalChartData
    ): ParentAnalysis {
        var wellbeingScore = 50.0

        sun?.let {
            if (AstrologicalConstants.isExalted(Planet.SUN, it.sign)) wellbeingScore += 20.0
            if (AstrologicalConstants.isInOwnSign(Planet.SUN, it.sign)) wellbeingScore += 15.0
            if (AstrologicalConstants.isDebilitated(Planet.SUN, it.sign)) wellbeingScore -= 20.0
            if (it.house in AstrologicalConstants.DUSTHANA_HOUSES) wellbeingScore -= 15.0
        }

        ninthLord?.let {
            if (it.house in AstrologicalConstants.KENDRA_HOUSES) wellbeingScore += 10.0
            if (it.house in AstrologicalConstants.TRIKONA_HOUSES) wellbeingScore += 10.0
        }

        return ParentAnalysis(
            parent = "Father",
            significatorStrength = sun?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0,
            houseLordStrength = ninthLord?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0,
            overallWellbeing = when {
                wellbeingScore >= 70 -> "Good health and prosperity indicated"
                wellbeingScore >= 50 -> "Moderate indications"
                else -> "Some challenges indicated - remedies beneficial"
            },
            characteristics = determineFatherCharacteristics(sun, ninthLord),
            relationship = assessParentRelationship(sun)
        )
    }

    private fun analyzeMotherIndicators(
        moon: PlanetPosition?,
        fourthLord: PlanetPosition?,
        chart: DivisionalChartData
    ): ParentAnalysis {
        var wellbeingScore = 50.0

        moon?.let {
            if (AstrologicalConstants.isExalted(Planet.MOON, it.sign)) wellbeingScore += 20.0
            if (AstrologicalConstants.isInOwnSign(Planet.MOON, it.sign)) wellbeingScore += 15.0
            if (AstrologicalConstants.isDebilitated(Planet.MOON, it.sign)) wellbeingScore -= 20.0
            if (it.house in AstrologicalConstants.DUSTHANA_HOUSES) wellbeingScore -= 15.0
        }

        fourthLord?.let {
            if (it.house in AstrologicalConstants.KENDRA_HOUSES) wellbeingScore += 10.0
            if (it.house in AstrologicalConstants.TRIKONA_HOUSES) wellbeingScore += 10.0
        }

        return ParentAnalysis(
            parent = "Mother",
            significatorStrength = moon?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0,
            houseLordStrength = fourthLord?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0,
            overallWellbeing = when {
                wellbeingScore >= 70 -> "Good health and emotional wellbeing indicated"
                wellbeingScore >= 50 -> "Moderate indications"
                else -> "Some challenges indicated - Moon remedies beneficial"
            },
            characteristics = determineMotherCharacteristics(moon, fourthLord),
            relationship = assessParentRelationship(moon)
        )
    }

    private fun determineFatherCharacteristics(sun: PlanetPosition?, ninthLord: PlanetPosition?): String {
        return when {
            sun != null && AstrologicalConstants.isExalted(Planet.SUN, sun.sign) ->
                "Authoritative, successful, government/administrative role"
            sun?.house == 10 -> "Career-focused, ambitious, prominent position"
            ninthLord?.planet == Planet.JUPITER -> "Religious, wise, generous"
            else -> "Characteristics vary based on overall chart"
        }
    }

    private fun determineMotherCharacteristics(moon: PlanetPosition?, fourthLord: PlanetPosition?): String {
        return when {
            moon != null && AstrologicalConstants.isExalted(Planet.MOON, moon.sign) ->
                "Nurturing, emotionally supportive, prosperous"
            moon?.house == 4 -> "Home-oriented, caring, family-focused"
            fourthLord?.planet == Planet.VENUS -> "Artistic, beautiful, comfort-loving"
            else -> "Characteristics vary based on overall chart"
        }
    }

    private fun assessParentRelationship(significator: PlanetPosition?): String {
        return when {
            significator == null -> "Relationship quality depends on other factors"
            significator.house in AstrologicalConstants.KENDRA_HOUSES -> "Strong, supportive relationship"
            significator.house in AstrologicalConstants.TRIKONA_HOUSES -> "Harmonious, beneficial relationship"
            significator.house in AstrologicalConstants.DUSTHANA_HOUSES -> "Some challenges in relationship"
            else -> "Moderate relationship quality"
        }
    }

    private fun analyzeInheritance(
        dwadasamsaChart: DivisionalChartData,
        chart: VedicChart
    ): InheritanceAnalysis {
        val secondHouseD12 = dwadasamsaChart.planetPositions.filter { it.house == 2 }
        val fourthHouseD12 = dwadasamsaChart.planetPositions.filter { it.house == 4 }
        val eighthHouseD12 = dwadasamsaChart.planetPositions.filter { it.house == 8 }

        var inheritanceScore = 50.0

        if (Planet.JUPITER in secondHouseD12.map { it.planet }) inheritanceScore += 20.0
        if (Planet.VENUS in fourthHouseD12.map { it.planet }) inheritanceScore += 15.0
        if (fourthHouseD12.any { it.planet in AstrologicalConstants.NATURAL_BENEFICS }) inheritanceScore += 10.0

        return InheritanceAnalysis(
            potential = when {
                inheritanceScore >= 70 -> "Good inheritance potential"
                inheritanceScore >= 50 -> "Moderate inheritance"
                else -> "Limited inheritance indicated"
            },
            sources = buildList {
                if (secondHouseD12.isNotEmpty()) add("Family wealth")
                if (fourthHouseD12.isNotEmpty()) add("Property/Land")
                if (eighthHouseD12.any { it.planet in AstrologicalConstants.NATURAL_BENEFICS }) add("Unexpected gains")
            },
            timing = "During Dasha of benefic planets in 2nd, 4th, or 8th house of D-12"
        )
    }

    private fun analyzeAncestralProperty(chart: DivisionalChartData): List<String> {
        val indicators = mutableListOf<String>()
        val fourthHousePlanets = chart.planetPositions.filter { it.house == 4 }

        if (Planet.SATURN in fourthHousePlanets.map { it.planet }) {
            indicators.add("Old ancestral property indicated")
        }
        if (Planet.MARS in fourthHousePlanets.map { it.planet }) {
            indicators.add("Land or real estate from ancestors")
        }
        if (Planet.JUPITER in fourthHousePlanets.map { it.planet }) {
            indicators.add("Religious or educational property")
        }

        return indicators
    }

    private fun analyzeFamilyLineage(chart: DivisionalChartData): List<String> {
        val insights = mutableListOf<String>()
        val lagnaLord = chart.ascendantSign.ruler
        val lagnaLordPosition = chart.planetPositions.find { it.planet == lagnaLord }

        lagnaLordPosition?.let {
            when {
                AstrologicalConstants.isExalted(it.planet, it.sign) ->
                    insights.add("Noble or respected family lineage")
                it.house in listOf(9, 10) ->
                    insights.add("Family known for dharma or profession")
                it.house in listOf(5, 11) ->
                    insights.add("Family with creative or financial success")
                else ->
                    insights.add("Family lineage characteristics depend on other factors")
            }
        }

        return insights
    }

    private fun analyzeParentalLongevity(
        dwadasamsaChart: DivisionalChartData,
        chart: VedicChart
    ): ParentalLongevityIndicators {
        val sunD12 = dwadasamsaChart.planetPositions.find { it.planet == Planet.SUN }
        val moonD12 = dwadasamsaChart.planetPositions.find { it.planet == Planet.MOON }

        val fatherLongevity = when {
            sunD12 != null && AstrologicalConstants.isExalted(Planet.SUN, sunD12.sign) -> "Long life indicated"
            sunD12?.house in AstrologicalConstants.DUSTHANA_HOUSES -> "Health attention needed"
            else -> "Moderate longevity"
        }

        val motherLongevity = when {
            moonD12 != null && AstrologicalConstants.isExalted(Planet.MOON, moonD12.sign) -> "Long life indicated"
            moonD12?.house in AstrologicalConstants.DUSTHANA_HOUSES -> "Health attention needed"
            else -> "Moderate longevity"
        }

        return ParentalLongevityIndicators(
            fatherLongevity = fatherLongevity,
            motherLongevity = motherLongevity,
            healthConcerns = buildList {
                if (sunD12?.house in AstrologicalConstants.DUSTHANA_HOUSES) add("Father: Regular health checkups advised")
                if (moonD12?.house in AstrologicalConstants.DUSTHANA_HOUSES) add("Mother: Emotional wellbeing attention")
            }
        )
    }

    private fun generateParentalRecommendations(
        fatherAnalysis: ParentAnalysis,
        motherAnalysis: ParentAnalysis
    ): List<String> {
        val recommendations = mutableListOf<String>()

        if (fatherAnalysis.significatorStrength < 50) {
            recommendations.add("Offer water to Sun on Sundays for father's wellbeing")
            recommendations.add("Respect and serve father figures")
        }

        if (motherAnalysis.significatorStrength < 50) {
            recommendations.add("Worship Moon on Mondays for mother's health")
            recommendations.add("Offer milk/white items in charity")
        }

        recommendations.add("Perform Pitru Tarpan during Pitru Paksha for ancestral blessings")

        return recommendations
    }
}

// Data classes for analysis results
data class HoraAnalysis(
    val sunHoraPlanets: List<Planet>,
    val moonHoraPlanets: List<Planet>,
    val wealthIndicators: List<WealthIndicator>,
    val overallWealthPotential: WealthPotential,
    val secondLordHoraSign: ZodiacSign?,
    val eleventhLordHoraSign: ZodiacSign?,
    val wealthTimingPeriods: List<WealthTimingPeriod>,
    val recommendations: List<String>
)

data class WealthIndicator(
    val planet: Planet,
    val type: WealthType,
    val strength: Double,
    val sources: List<String>
)

enum class WealthType { SELF_EARNED, INHERITED_LIQUID }
enum class WealthPotential { EXCEPTIONAL, HIGH, MODERATE, AVERAGE, LOW }

data class WealthTimingPeriod(
    val planet: Planet,
    val type: WealthType,
    val periodDescription: String,
    val favorableForWealth: Boolean,
    val wealthSources: List<String>
)

data class DrekkanaAnalysis(
    val marsInDrekkana: PlanetPosition?,
    val thirdLordPosition: PlanetPosition?,
    val siblingIndicators: SiblingIndicators,
    val courageAnalysis: CourageAnalysis,
    val communicationSkills: CommunicationAnalysis,
    val thirdHousePlanets: List<PlanetPosition>,
    val eleventhHousePlanets: List<PlanetPosition>,
    val shortJourneyIndicators: List<String>,
    val recommendations: List<String>
)

data class SiblingIndicators(
    val estimatedYoungerSiblings: IntRange,
    val estimatedElderSiblings: IntRange,
    val relationshipQuality: RelationshipQuality,
    val youngerSiblingPlanets: List<Planet>,
    val elderSiblingPlanets: List<Planet>,
    val siblingWelfareIndicators: List<String>
)

enum class RelationshipQuality { EXCELLENT, GOOD, NEUTRAL, CHALLENGING, DIFFICULT }

data class CourageAnalysis(
    val overallCourageLevel: CourageLevel,
    val marsStrength: Double,
    val initiativeAbility: String,
    val physicalCourage: String,
    val mentalCourage: String
)

enum class CourageLevel { EXCEPTIONAL, HIGH, MODERATE, LOW, VERY_LOW }

data class CommunicationAnalysis(
    val overallSkillLevel: String,
    val writingAbility: String,
    val speakingAbility: String,
    val artisticTalents: List<String>,
    val mercuryStrength: Double
)

data class NavamsaMarriageAnalysis(
    val venusInNavamsa: PlanetPosition?,
    val jupiterInNavamsa: PlanetPosition?,
    val seventhLordNavamsa: PlanetPosition?,
    val navamsaLagnaLordPosition: PlanetPosition?,
    val upapadaSign: ZodiacSign,
    val upapadaLordNavamsa: PlanetPosition?,
    val darakaraka: Planet,
    val darakarakaNavamsa: PlanetPosition?,
    val marriageTimingFactors: MarriageTimingFactors,
    val spouseCharacteristics: SpouseCharacteristics,
    val spouseDirection: String,
    val multipleMarriageIndicators: MultipleMarriageIndicators,
    val marriageMuhurtaCompatibility: String,
    val recommendations: List<String>
)

data class MarriageTimingFactors(
    val favorableDashaPlanets: List<Planet>,
    val venusNavamsaStrength: Double,
    val seventhLordStrength: Double,
    val darakarakaStrength: Double,
    val transitConsiderations: String
)

data class SpouseCharacteristics(
    val generalNature: String,
    val physicalTraits: String,
    val probableProfessions: List<String>,
    val familyBackground: String
)

data class MultipleMarriageIndicators(
    val hasStrongIndicators: Boolean,
    val riskFactors: List<String>,
    val mitigatingFactors: List<String>
)

data class DashamsaAnalysis(
    val tenthLordInDashamsa: PlanetPosition?,
    val sunInDashamsa: PlanetPosition?,
    val saturnInDashamsa: PlanetPosition?,
    val mercuryInDashamsa: PlanetPosition?,
    val dashamsaLagna: ZodiacSign,
    val careerTypes: List<CareerType>,
    val industryMappings: Map<Planet, List<String>>,
    val governmentServicePotential: GovernmentServiceAnalysis,
    val businessVsServiceAptitude: BusinessVsServiceAnalysis,
    val careerPeakPeriods: List<CareerPeakPeriod>,
    val multipleCareerIndicators: List<String>,
    val professionalStrengths: List<String>,
    val recommendations: List<String>
)

data class CareerType(
    val name: String,
    val industries: List<String>,
    val suitability: String
)

data class GovernmentServiceAnalysis(
    val potential: String,
    val favorableFactors: List<String>,
    val recommendedDepartments: List<String>
)

data class BusinessVsServiceAnalysis(
    val businessAptitude: Int,
    val serviceAptitude: Int,
    val recommendation: String,
    val businessSectors: List<String>
)

data class CareerPeakPeriod(
    val planet: Planet,
    val description: String,
    val significance: String
)

data class DwadasamsaAnalysis(
    val sunInDwadasamsa: PlanetPosition?,
    val moonInDwadasamsa: PlanetPosition?,
    val ninthLordPosition: PlanetPosition?,
    val fourthLordPosition: PlanetPosition?,
    val fatherAnalysis: ParentAnalysis,
    val motherAnalysis: ParentAnalysis,
    val inheritanceIndicators: InheritanceAnalysis,
    val ancestralPropertyIndicators: List<String>,
    val familyLineageInsights: List<String>,
    val parentalLongevityIndicators: ParentalLongevityIndicators,
    val recommendations: List<String>
)

data class ParentAnalysis(
    val parent: String,
    val significatorStrength: Double,
    val houseLordStrength: Double,
    val overallWellbeing: String,
    val characteristics: String,
    val relationship: String
)

data class InheritanceAnalysis(
    val potential: String,
    val sources: List<String>,
    val timing: String
)

data class ParentalLongevityIndicators(
    val fatherLongevity: String,
    val motherLongevity: String,
    val healthConcerns: List<String>
)
