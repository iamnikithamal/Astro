package com.astro.storm.ephemeris

import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringKeyDivisional
import com.astro.storm.data.localization.StringKeyPrediction
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringKeyMatch
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
    fun analyzeHora(chart: VedicChart, language: Language): HoraAnalysis {
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
                    sources = getSunHoraWealthSources(planet, language)
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
                    sources = getMoonHoraWealthSources(planet, language)
                )
            )
        }

        val overallWealthPotential = calculateOverallWealthPotential(
            sunHoraPlanets, moonHoraPlanets, secondLordInHora, eleventhLordInHora, chart
        )

        val wealthTimingPeriods = calculateWealthTimingFromHora(chart, wealthIndicators, language)

        return HoraAnalysis(
            sunHoraPlanets = sunHoraPlanets,
            moonHoraPlanets = moonHoraPlanets,
            wealthIndicators = wealthIndicators,
            overallWealthPotential = overallWealthPotential,
            secondLordHoraSign = secondLordInHora?.sign,
            eleventhLordHoraSign = eleventhLordInHora?.sign,
            wealthTimingPeriods = wealthTimingPeriods,
            recommendations = generateHoraRecommendations(wealthIndicators, overallWealthPotential, language)
        )
    }

    // Drekkana Chart (D-3) Analysis for Siblings and Courage
    fun analyzeDrekkana(chart: VedicChart, language: Language): DrekkanaAnalysis {
        val drekkanaChart = DivisionalChartCalculator.calculateDrekkana(chart)
        val marsPosition = drekkanaChart.planetPositions.find { it.planet == Planet.MARS }
        val thirdLordD1 = getHouseLord(chart, 3)
        val thirdLordInDrekkana = drekkanaChart.planetPositions.find { it.planet == thirdLordD1 }

        val siblingIndicators = analyzeSiblings(drekkanaChart, chart, language)
        val courageAnalysis = analyzeCourage(marsPosition, thirdLordInDrekkana, drekkanaChart, language)
        val communicationAnalysis = analyzeCommunication(drekkanaChart, chart, language)

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
            shortJourneyIndicators = analyzeShortJourneys(drekkanaChart, language),
            recommendations = generateDrekkanaRecommendations(courageAnalysis, siblingIndicators, language)
        )
    }

    // Navamsa (D-9) Marriage Timing Analysis
    fun analyzeNavamsaForMarriage(chart: VedicChart, language: Language): NavamsaMarriageAnalysis {
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
            chart, navamsaChart, venusD9, jupiterD9, seventhLordD9, darakarakaD9, language
        )

        val spouseCharacteristics = analyzeSpouseCharacteristics(
            seventhLordD9, venusD9, darakarakaD9, upapadaLordD9, navamsaChart, language
        )

        val spouseDirection = calculateSpouseDirection(seventhLordD9, darakarakaD9, language)

        val multipleMarriageIndicators = analyzeMultipleMarriageFactors(chart, navamsaChart, language)

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
            marriageMuhurtaCompatibility = analyzeMarriageMuhurtaCompatibility(chart, navamsaChart, language),
            recommendations = generateMarriageRecommendations(marriageTimingFactors, language)
        )
    }

    // Dashamsa (D-10) Career Guidance Analysis
    fun analyzeDashamsa(chart: VedicChart, language: Language): DashamsaAnalysis {
        val dashamsaChart = DivisionalChartCalculator.calculateDasamsa(chart)
        val tenthLordD1 = getHouseLord(chart, 10)
        val tenthLordD10 = dashamsaChart.planetPositions.find { it.planet == tenthLordD1 }
        val sunD10 = dashamsaChart.planetPositions.find { it.planet == Planet.SUN }
        val saturnD10 = dashamsaChart.planetPositions.find { it.planet == Planet.SATURN }
        val mercuryD10 = dashamsaChart.planetPositions.find { it.planet == Planet.MERCURY }

        val careerType = determineCareerType(dashamsaChart, chart, language)
        val industryMappings = mapPlanetsToIndustries(dashamsaChart, language)
        val governmentServiceIndicators = analyzeGovernmentServicePotential(sunD10, dashamsaChart, language)
        val businessVsService = analyzeBusinessVsService(dashamsaChart, chart, language)
        val careerPeakTiming = calculateCareerPeakTiming(chart, dashamsaChart, language)
        val multipleCareerIndicators = analyzeMultipleCareers(dashamsaChart, language)

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
            professionalStrengths = analyzeProfessionalStrengths(dashamsaChart, language),
            recommendations = generateCareerRecommendations(careerType, industryMappings, language)
        )
    }

    // Dwadasamsa (D-12) Parental Analysis
    fun analyzeDwadasamsa(chart: VedicChart, language: Language): DwadasamsaAnalysis {
        val dwadasamsaChart = DivisionalChartCalculator.calculateDwadasamsa(chart)
        val sunD12 = dwadasamsaChart.planetPositions.find { it.planet == Planet.SUN }
        val moonD12 = dwadasamsaChart.planetPositions.find { it.planet == Planet.MOON }
        val ninthLordD1 = getHouseLord(chart, 9)
        val fourthLordD1 = getHouseLord(chart, 4)
        val ninthLordD12 = dwadasamsaChart.planetPositions.find { it.planet == ninthLordD1 }
        val fourthLordD12 = dwadasamsaChart.planetPositions.find { it.planet == fourthLordD1 }

        val fatherAnalysis = analyzeFatherIndicators(sunD12, ninthLordD12, dwadasamsaChart, language)
        val motherAnalysis = analyzeMotherIndicators(moonD12, fourthLordD12, dwadasamsaChart, language)
        val inheritanceAnalysis = analyzeInheritance(dwadasamsaChart, chart, language)
        val ancestralPropertyAnalysis = analyzeAncestralProperty(dwadasamsaChart, language)
        val familyLineageAnalysis = analyzeFamilyLineage(dwadasamsaChart, language)

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
            parentalLongevityIndicators = analyzeParentalLongevity(dwadasamsaChart, chart, language),
            recommendations = generateParentalRecommendations(fatherAnalysis, motherAnalysis, language)
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

    private fun getSunHoraWealthSources(planet: Planet, language: Language): List<String> {
        return when (planet) {
            Planet.SUN -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_GOVT, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_GOLD, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_FATHER, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_AUTHORITY, language)
            )
            Planet.MARS -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_REAL_ESTATE, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_ENGINEERING, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_MILITARY, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_SPORTS, language)
            )
            Planet.JUPITER -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_TEACHING, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_CONSULTANCY, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_BANKING, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_RELIGIOUS, language)
            )
            Planet.SATURN -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_MINING, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_OIL, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LABOR, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LAND, language)
            )
            Planet.MERCURY -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_BUSINESS, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_COMMUNICATION, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_TECHNOLOGY, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_TRADE, language)
            )
            Planet.VENUS -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_ARTS, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_ENTERTAINMENT, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LUXURY, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_BEAUTY, language)
            )
            Planet.MOON -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_PUBLIC, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_HOSPITALITY, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_DAIRY, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LIQUIDS, language)
            )
            Planet.RAHU -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_FOREIGN, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_TECHNOLOGY, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_UNCONVENTIONAL, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_SPECULATION, language)
            )
            Planet.KETU -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_SPIRITUAL, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_OCCULT, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_DETACHMENT, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_RESEARCH, language)
            )
            else -> emptyList()
        }
    }

    private fun getMoonHoraWealthSources(planet: Planet, language: Language): List<String> {
        return when (planet) {
            Planet.MOON -> listOf(
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_INHERITANCE, language),
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_MOTHER, language),
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_PEARLS, language),
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_SILVER, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LIQUIDS, language)
            )
            Planet.VENUS -> listOf(
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_SPOUSE, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LUXURY, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_GOLD, language) // Reusing gold
            )
            Planet.JUPITER -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_RELIGIOUS, language),
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_EDUCATIONAL, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_FATHER, language) // Family wealth proxy
            )
            Planet.MERCURY -> listOf(
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_FAMILY_BIZ, language),
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_ANCESTRAL_TRADE, language),
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_INTELLECTUAL, language)
            )
            Planet.SUN -> listOf(
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_GOVT_BENEFITS, language),
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_ROYAL, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_AUTHORITY, language)
            )
            Planet.MARS -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_REAL_ESTATE, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LAND, language),
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_MILITARY_PENSION, language)
            )
            Planet.SATURN -> listOf(
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_OLD_WEALTH, language),
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_DELAYED, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LABOR, language)
            )
            Planet.RAHU -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_FOREIGN, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_UNCONVENTIONAL, language),
                StringResources.get(StringKeyDivisional.HORA_MOON_SOURCE_SPOUSE, language) // In-laws wealth proxy
            )
            Planet.KETU -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_SPIRITUAL, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_RESEARCH, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_DETACHMENT, language)
            )
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
        indicators: List<WealthIndicator>,
        language: Language
    ): List<WealthTimingPeriod> {
        val periods = mutableListOf<WealthTimingPeriod>()
        val strongIndicators = indicators.filter { it.strength > 60 }

        strongIndicators.forEach { indicator ->
            val planetName = indicator.planet.getLocalizedName(language)
            periods.add(
                WealthTimingPeriod(
                    planet = indicator.planet,
                    type = indicator.type,
                    periodDescription = StringResources.get(StringKeyPrediction.PRED_MAHADASHA_LABEL, language, planetName),
                    favorableForWealth = indicator.strength > 65,
                    wealthSources = indicator.sources
                )
            )
        }

        return periods
    }

    private fun generateHoraRecommendations(
        indicators: List<WealthIndicator>,
        potential: WealthPotential,
        language: Language
    ): List<String> {
        val recommendations = mutableListOf<String>()

        when (potential) {
            WealthPotential.EXCEPTIONAL, WealthPotential.HIGH -> {
                recommendations.add(StringResources.get(StringKeyDivisional.HORA_REC_FOCUS_SOURCES, language))
                recommendations.add(StringResources.get(StringKeyDivisional.HORA_REC_INVEST_FAVORABLE, language))
            }
            WealthPotential.MODERATE -> {
                recommendations.add(StringResources.get(StringKeyDivisional.HORA_REC_CONSISTENT_EFFORT, language))
                recommendations.add(StringResources.get(StringKeyDivisional.HORA_REC_STRENGTHEN_WEAK, language))
            }
            WealthPotential.AVERAGE, WealthPotential.LOW -> {
                recommendations.add(StringResources.get(StringKeyDivisional.HORA_REC_WORSHIP_LAKSHMI, language))
                recommendations.add(StringResources.get(StringKeyDivisional.HORA_REC_STRENGTHEN_JUP_VEN, language))
                recommendations.add(StringResources.get(StringKeyDivisional.HORA_REC_AVOID_SPECULATION, language))
            }
        }

        indicators.filter { it.strength > 70 }.forEach { indicator ->
            recommendations.add(
                StringResources.get(
                    StringKeyDivisional.HORA_REC_CAPITALIZE_STRENGTH,
                    language,
                    indicator.planet.getLocalizedName(language),
                    indicator.sources.firstOrNull() ?: StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_GENERAL, language)
                )
            )
        }

        return recommendations
    }

    private fun analyzeSiblings(
        drekkanaChart: DivisionalChartData,
        chart: VedicChart,
        language: Language
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
            siblingWelfareIndicators = assessSiblingWelfare(drekkanaChart, language)
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

    private fun assessSiblingWelfare(chart: DivisionalChartData, language: Language): List<String> {
        val insights = mutableListOf<String>()
        val thirdHousePlanets = chart.planetPositions.filter { it.house == 3 }

        if (Planet.JUPITER in thirdHousePlanets.map { it.planet }) {
            insights.add(StringResources.get(StringKeyDivisional.DREKKANA_SIBLING_JUPITER, language))
        }
        if (Planet.SATURN in thirdHousePlanets.map { it.planet }) {
            insights.add(StringResources.get(StringKeyDivisional.DREKKANA_SIBLING_SATURN, language))
        }
        if (Planet.MARS in thirdHousePlanets.map { it.planet }) {
            insights.add(StringResources.get(StringKeyDivisional.DREKKANA_SIBLING_MARS, language))
        }

        return insights
    }

    private fun analyzeCourage(
        mars: PlanetPosition?,
        thirdLord: PlanetPosition?,
        chart: DivisionalChartData,
        language: Language
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
            initiativeAbility = assessInitiativeAbility(mars, thirdLord, language),
            physicalCourage = assessPhysicalCourage(mars, language),
            mentalCourage = assessMentalCourage(thirdLord, chart, language)
        )
    }

    private fun calculatePlanetStrengthInVarga(position: PlanetPosition): Double {
        var strength = 50.0
        if (AstrologicalConstants.isExalted(position.planet, position.sign)) strength += 30.0
        if (AstrologicalConstants.isInOwnSign(position.planet, position.sign)) strength += 20.0
        if (AstrologicalConstants.isDebilitated(position.planet, position.sign)) strength -= 25.0
        return strength.coerceIn(0.0, 100.0)
    }

    private fun assessInitiativeAbility(mars: PlanetPosition?, thirdLord: PlanetPosition?, language: Language): String {
        val marsStrong = mars?.let {
            AstrologicalConstants.isExalted(Planet.MARS, it.sign) ||
            AstrologicalConstants.isInOwnSign(Planet.MARS, it.sign)
        } ?: false

        return if (marsStrong) {
            StringResources.get(StringKeyDivisional.DREKKANA_INITIATIVE_STRONG, language)
        } else {
            StringResources.get(StringKeyDivisional.DREKKANA_INITIATIVE_MODERATE, language)
        }
    }

    private fun assessPhysicalCourage(mars: PlanetPosition?, language: Language): String {
        return when {
            mars == null -> StringResources.get(StringKeyDivisional.DREKKANA_PHYSICAL_DEPENDS, language)
            AstrologicalConstants.isExalted(Planet.MARS, mars.sign) -> StringResources.get(StringKeyDivisional.DREKKANA_PHYSICAL_EXCEPTIONAL, language)
            AstrologicalConstants.isInOwnSign(Planet.MARS, mars.sign) -> StringResources.get(StringKeyDivisional.DREKKANA_PHYSICAL_STRONG, language)
            AstrologicalConstants.isDebilitated(Planet.MARS, mars.sign) -> StringResources.get(StringKeyDivisional.DREKKANA_PHYSICAL_DEVELOPMENT, language)
            else -> StringResources.get(StringKeyDivisional.DREKKANA_PHYSICAL_ADEQUATE, language)
        }
    }

    private fun assessMentalCourage(thirdLord: PlanetPosition?, chart: DivisionalChartData, language: Language): String {
        val mercuryD3 = chart.planetPositions.find { it.planet == Planet.MERCURY }
        val jupiterD3 = chart.planetPositions.find { it.planet == Planet.JUPITER }

        return when {
            jupiterD3?.house == 3 -> StringResources.get(StringKeyDivisional.DREKKANA_MENTAL_WISDOM, language)
            mercuryD3?.house == 3 -> StringResources.get(StringKeyDivisional.DREKKANA_MENTAL_INTELLECTUAL, language)
            thirdLord?.house in AstrologicalConstants.KENDRA_HOUSES -> StringResources.get(StringKeyDivisional.DREKKANA_MENTAL_STABLE, language)
            else -> StringResources.get(StringKeyDivisional.DREKKANA_MENTAL_EXPERIENCE, language)
        }
    }

    private fun analyzeCommunication(chart: DivisionalChartData, d1Chart: VedicChart, language: Language): CommunicationAnalysis {
        val mercuryD3 = chart.planetPositions.find { it.planet == Planet.MERCURY }
        val thirdHousePlanets = chart.planetPositions.filter { it.house == 3 }

        val writingAbility = assessWritingAbility(mercuryD3, thirdHousePlanets, language)
        val speakingAbility = assessSpeakingAbility(mercuryD3, chart, language)
        val artisticTalents = assessArtisticTalents(chart, language)

        return CommunicationAnalysis(
            overallSkillLevel = calculateCommunicationLevel(mercuryD3, thirdHousePlanets, language),
            writingAbility = writingAbility,
            speakingAbility = speakingAbility,
            artisticTalents = artisticTalents,
            mercuryStrength = mercuryD3?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0
        )
    }

    private fun calculateCommunicationLevel(mercury: PlanetPosition?, thirdHouse: List<PlanetPosition>, language: Language): String {
        val mercuryStrong = mercury?.let {
            AstrologicalConstants.isExalted(Planet.MERCURY, it.sign) ||
            AstrologicalConstants.isInOwnSign(Planet.MERCURY, it.sign)
        } ?: false

        return when {
            mercuryStrong && thirdHouse.any { it.planet == Planet.JUPITER } -> StringResources.get(StringKeyDivisional.DREKKANA_COMM_EXCEPTIONAL, language)
            mercuryStrong -> StringResources.get(StringKeyDivisional.DREKKANA_COMM_VERY_GOOD, language)
            thirdHouse.any { it.planet in AstrologicalConstants.NATURAL_BENEFICS } -> StringResources.get(StringKeyDivisional.DREKKANA_COMM_GOOD, language)
            else -> StringResources.get(StringKeyDivisional.DREKKANA_COMM_AVERAGE, language)
        }
    }

    private fun assessWritingAbility(mercury: PlanetPosition?, thirdHouse: List<PlanetPosition>, language: Language): String {
        return when {
            mercury?.house == 3 && AstrologicalConstants.isInOwnSign(Planet.MERCURY, mercury.sign) ->
                StringResources.get(StringKeyDivisional.DREKKANA_WRITING_EXCEPTIONAL, language)
            thirdHouse.any { it.planet == Planet.JUPITER } ->
                StringResources.get(StringKeyDivisional.DREKKANA_WRITING_DEPTH, language)
            thirdHouse.any { it.planet == Planet.MERCURY } ->
                StringResources.get(StringKeyDivisional.DREKKANA_WRITING_GOOD, language)
            else -> StringResources.get(StringKeyDivisional.DREKKANA_WRITING_AVERAGE, language)
        }
    }

    private fun assessSpeakingAbility(mercury: PlanetPosition?, chart: DivisionalChartData, language: Language): String {
        val sunD3 = chart.planetPositions.find { it.planet == Planet.SUN }
        val jupiterD3 = chart.planetPositions.find { it.planet == Planet.JUPITER }

        return when {
            mercury != null && jupiterD3?.house == 2 -> StringResources.get(StringKeyDivisional.DREKKANA_SPEAKING_ELOQUENT, language)
            sunD3?.house == 3 -> StringResources.get(StringKeyDivisional.DREKKANA_SPEAKING_AUTHORITATIVE, language)
            mercury?.house == 3 -> StringResources.get(StringKeyDivisional.DREKKANA_SPEAKING_QUICK, language)
            else -> StringResources.get(StringKeyDivisional.DREKKANA_SPEAKING_NORMAL, language)
        }
    }

    private fun assessArtisticTalents(chart: DivisionalChartData, language: Language): List<String> {
        val talents = mutableListOf<String>()
        val venusD3 = chart.planetPositions.find { it.planet == Planet.VENUS }
        val moonD3 = chart.planetPositions.find { it.planet == Planet.MOON }

        venusD3?.let {
            if (it.house == 3 || it.house == 5) talents.add(StringResources.get(StringKeyDivisional.DREKKANA_ART_VISUAL, language))
        }
        moonD3?.let {
            if (it.house == 3) talents.add(StringResources.get(StringKeyDivisional.DREKKANA_ART_MUSIC, language))
        }

        return talents
    }

    private fun analyzeShortJourneys(chart: DivisionalChartData, language: Language): List<String> {
        val indicators = mutableListOf<String>()
        val thirdHousePlanets = chart.planetPositions.filter { it.house == 3 }

        if (thirdHousePlanets.any { it.planet == Planet.MOON }) {
            indicators.add(StringResources.get(StringKeyDivisional.DREKKANA_JOURNEY_FREQUENT, language))
        }
        if (thirdHousePlanets.any { it.planet == Planet.MERCURY }) {
            indicators.add(StringResources.get(StringKeyDivisional.DREKKANA_JOURNEY_BUSINESS, language))
        }
        if (thirdHousePlanets.any { it.planet == Planet.RAHU }) {
            indicators.add(StringResources.get(StringKeyDivisional.DREKKANA_JOURNEY_UNUSUAL, language))
        }

        return indicators
    }

    private fun generateDrekkanaRecommendations(
        courage: CourageAnalysis,
        siblings: SiblingIndicators,
        language: Language
    ): List<String> {
        val recommendations = mutableListOf<String>()

        when (courage.overallCourageLevel) {
            CourageLevel.EXCEPTIONAL, CourageLevel.HIGH -> {
                recommendations.add(StringResources.get(StringKeyDivisional.DREKKANA_REC_CHANNEL_COURAGE, language))
            }
            CourageLevel.LOW, CourageLevel.VERY_LOW -> {
                recommendations.add(StringResources.get(StringKeyDivisional.DREKKANA_REC_MARS_ACTIVITIES, language))
                recommendations.add(StringResources.get(StringKeyDivisional.DREKKANA_REC_HANUMAN, language))
            }
            else -> {
                recommendations.add(StringResources.get(StringKeyDivisional.DREKKANA_REC_BALANCE_CAUTION, language))
            }
        }

        when (siblings.relationshipQuality) {
            RelationshipQuality.CHALLENGING, RelationshipQuality.DIFFICULT -> {
                recommendations.add(StringResources.get(StringKeyDivisional.DREKKANA_REC_SIBLING_PATIENCE, language))
                recommendations.add(StringResources.get(StringKeyDivisional.DREKKANA_REC_COUNSELING, language))
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
        darakaraka: PlanetPosition?,
        language: Language
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
            transitConsiderations = StringResources.get(StringKeyDivisional.NAVAMSA_TRANSIT_JUPITER, language)
        )
    }

    private fun analyzeSpouseCharacteristics(
        seventhLord: PlanetPosition?,
        venus: PlanetPosition?,
        darakaraka: PlanetPosition?,
        upapadaLord: PlanetPosition?,
        navamsaChart: DivisionalChartData,
        language: Language
    ): SpouseCharacteristics {
        val spouseNature = determineSpouseNature(seventhLord, venus, language)
        val spouseAppearance = determineSpouseAppearance(darakaraka, venus, language)
        val spouseProfession = estimateSpouseProfession(seventhLord, navamsaChart, language)

        return SpouseCharacteristics(
            generalNature = spouseNature,
            physicalTraits = spouseAppearance,
            probableProfessions = spouseProfession,
            familyBackground = determineSpouseFamilyBackground(upapadaLord, language)
        )
    }

    private fun determineSpouseNature(seventhLord: PlanetPosition?, venus: PlanetPosition?, language: Language): String {
        val dominantPlanet = seventhLord?.planet ?: venus?.planet ?: Planet.VENUS

        return when (dominantPlanet) {
            Planet.SUN -> StringResources.get(StringKeyDivisional.NAVAMSA_NATURE_SUN, language)
            Planet.MOON -> StringResources.get(StringKeyDivisional.NAVAMSA_NATURE_MOON, language)
            Planet.MARS -> StringResources.get(StringKeyDivisional.NAVAMSA_NATURE_MARS, language)
            Planet.MERCURY -> StringResources.get(StringKeyDivisional.NAVAMSA_NATURE_MERCURY, language)
            Planet.JUPITER -> StringResources.get(StringKeyDivisional.NAVAMSA_NATURE_JUPITER, language)
            Planet.VENUS -> StringResources.get(StringKeyDivisional.NAVAMSA_NATURE_VENUS, language)
            Planet.SATURN -> StringResources.get(StringKeyDivisional.NAVAMSA_NATURE_SATURN, language)
            Planet.RAHU -> StringResources.get(StringKeyDivisional.NAVAMSA_NATURE_RAHU, language)
            Planet.KETU -> StringResources.get(StringKeyDivisional.NAVAMSA_NATURE_KETU, language)
            else -> StringResources.get(StringKeyDivisional.NAVAMSA_NATURE_MIXED, language)
        }
    }

    private fun determineSpouseAppearance(darakaraka: PlanetPosition?, venus: PlanetPosition?, language: Language): String {
        val planet = darakaraka?.planet ?: venus?.planet ?: Planet.VENUS

        return when (planet) {
            Planet.SUN -> StringResources.get(StringKeyDivisional.NAVAMSA_APPEAR_SUN, language)
            Planet.MOON -> StringResources.get(StringKeyDivisional.NAVAMSA_APPEAR_MOON, language)
            Planet.MARS -> StringResources.get(StringKeyDivisional.NAVAMSA_APPEAR_MARS, language)
            Planet.MERCURY -> StringResources.get(StringKeyDivisional.NAVAMSA_APPEAR_MERCURY, language)
            Planet.JUPITER -> StringResources.get(StringKeyDivisional.NAVAMSA_APPEAR_JUPITER, language)
            Planet.VENUS -> StringResources.get(StringKeyDivisional.NAVAMSA_APPEAR_VENUS, language)
            Planet.SATURN -> StringResources.get(StringKeyDivisional.NAVAMSA_APPEAR_SATURN, language)
            else -> StringResources.get(StringKeyDivisional.NAVAMSA_APPEAR_VARIES, language)
        }
    }

    private fun estimateSpouseProfession(seventhLord: PlanetPosition?, chart: DivisionalChartData, language: Language): List<String> {
        val professions = mutableListOf<String>()
        val planet = seventhLord?.planet ?: return listOf(StringResources.get(StringKeyDivisional.NAVAMSA_PROF_VARIOUS, language))

        professions.addAll(when (planet) {
            Planet.SUN -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_GOVT, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ADMIN, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_MEDICINE, language)
            )
            Planet.MOON -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_HEALTHCARE, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_HOSPITALITY, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_PR, language)
            )
            Planet.MARS -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ENGINEERING, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_MILITARY, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_SPORTS, language)
            )
            Planet.MERCURY -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_BUSINESS, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_WRITING, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_TECH, language)
            )
            Planet.JUPITER -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_TEACHING, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_LAW, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_FINANCE, language)
            )
            Planet.VENUS -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ARTS, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_FASHION, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ENTERTAINMENT, language)
            )
            Planet.SATURN -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_LABOR, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_CONSTRUCTION, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_AGRICULTURE, language)
            )
            else -> listOf(StringResources.get(StringKeyDivisional.NAVAMSA_PROF_FIELDS, language))
        })

        return professions
    }

    private fun determineSpouseFamilyBackground(upapadaLord: PlanetPosition?, language: Language): String {
        return when (upapadaLord?.planet) {
            Planet.JUPITER -> StringResources.get(StringKeyDivisional.NAVAMSA_FAM_JUPITER, language)
            Planet.VENUS -> StringResources.get(StringKeyDivisional.NAVAMSA_FAM_VENUS, language)
            Planet.SUN -> StringResources.get(StringKeyDivisional.NAVAMSA_FAM_SUN, language)
            Planet.MOON -> StringResources.get(StringKeyDivisional.NAVAMSA_FAM_MOON, language)
            Planet.SATURN -> StringResources.get(StringKeyDivisional.NAVAMSA_FAM_SATURN, language)
            else -> StringResources.get(StringKeyDivisional.NAVAMSA_FAM_VARIES, language)
        }
    }

    private fun calculateSpouseDirection(seventhLord: PlanetPosition?, darakaraka: PlanetPosition?, language: Language): String {
        val sign = seventhLord?.sign ?: darakaraka?.sign ?: return StringResources.get(StringKeyDivisional.NAVAMSA_DIR_UNKNOWN, language)

        return when (sign) {
            ZodiacSign.ARIES, ZodiacSign.LEO, ZodiacSign.SAGITTARIUS -> StringResources.get(StringKeyDivisional.NAVAMSA_DIR_EAST, language)
            ZodiacSign.TAURUS, ZodiacSign.VIRGO, ZodiacSign.CAPRICORN -> StringResources.get(StringKeyDivisional.NAVAMSA_DIR_SOUTH, language)
            ZodiacSign.GEMINI, ZodiacSign.LIBRA, ZodiacSign.AQUARIUS -> StringResources.get(StringKeyDivisional.NAVAMSA_DIR_WEST, language)
            ZodiacSign.CANCER, ZodiacSign.SCORPIO, ZodiacSign.PISCES -> StringResources.get(StringKeyDivisional.NAVAMSA_DIR_NORTH, language)
        }
    }

    private fun analyzeMultipleMarriageFactors(
        chart: VedicChart,
        navamsaChart: DivisionalChartData,
        language: Language
    ): MultipleMarriageIndicators {
        val indicators = mutableListOf<String>()
        var risk = 0

        val seventhLordD1 = getHouseLord(chart, 7)
        val seventhLordPositionD1 = chart.planetPositions.find { it.planet == seventhLordD1 }
        val venusD1 = chart.planetPositions.find { it.planet == Planet.VENUS }
        
        val ascSign = ZodiacSign.fromLongitude(chart.ascendant)
        val seventhHousePlanets = chart.planetPositions.filter { 
            ((it.sign.ordinal - ascSign.ordinal + 12) % 12) + 1 == 7 
        }

        // Check for multiple marriage indicators
        if (seventhHousePlanets.size >= 2) {
            indicators.add(StringResources.get(StringKeyDivisional.NAVAMSA_RISK_MULTIPLE_PLANETS, language))
            risk++
        }

        if (venusD1?.isRetrograde == true) {
            indicators.add(StringResources.get(StringKeyDivisional.NAVAMSA_RISK_RETRO_VENUS, language))
            risk++
        }

        if (seventhLordPositionD1?.house in AstrologicalConstants.DUSTHANA_HOUSES) {
            indicators.add(StringResources.get(StringKeyDivisional.NAVAMSA_RISK_7TH_LORD_DUSTHANA, language))
            risk++
        }

        val hasRisk = risk >= 2

        return MultipleMarriageIndicators(
            hasStrongIndicators = hasRisk,
            riskFactors = indicators,
            mitigatingFactors = if (!hasRisk) listOf(StringResources.get(StringKeyDivisional.NAVAMSA_MITIGATE_NONE, language)) else emptyList()
        )
    }

    private fun analyzeMarriageMuhurtaCompatibility(
        chart: VedicChart,
        navamsaChart: DivisionalChartData,
        language: Language
    ): String {
        val moonSign = chart.planetPositions.find { it.planet == Planet.MOON }?.sign
        return StringResources.get(
            StringKeyDivisional.NAVAMSA_MUHURTA_JUPITER, 
            language, 
            moonSign?.getLocalizedName(language) ?: StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_GENERAL, language)
        )
    }

    private fun generateMarriageRecommendations(factors: MarriageTimingFactors, language: Language): List<String> {
        val recommendations = mutableListOf<String>()

        if (factors.venusNavamsaStrength > 70) {
            recommendations.add(StringResources.get(StringKeyDivisional.NAVAMSA_REC_VENUS_STRONG, language))
        }
        if (factors.venusNavamsaStrength < 50) {
            recommendations.add(StringResources.get(StringKeyDivisional.NAVAMSA_REC_VENUS_WEAK, language))
        }

        val favorablePlanetsText = factors.favorableDashaPlanets.joinToString(", ") { it.getLocalizedName(language) }
        recommendations.add(StringResources.get(StringKeyDivisional.NAVAMSA_REC_TIMING, language, favorablePlanetsText))
        recommendations.add(factors.transitConsiderations)

        return recommendations
    }

    // Dashamsa helper functions
    private fun determineCareerType(dashamsaChart: DivisionalChartData, chart: VedicChart, language: Language): List<CareerType> {
        val careerTypes = mutableListOf<CareerType>()
        val tenthHouseD10 = dashamsaChart.planetPositions.filter { it.house == 10 }
        val lagnaD10 = dashamsaChart.ascendantSign

        // Analyze 10th house planets in D10
        tenthHouseD10.forEach { position ->
            careerTypes.add(getCareerTypeFromPlanet(position.planet, language))
        }

        // Analyze D10 lagna lord
        val lagnaLord = lagnaD10.ruler
        val lagnaLordPosition = dashamsaChart.planetPositions.find { it.planet == lagnaLord }
        lagnaLordPosition?.let {
            careerTypes.add(getCareerTypeFromPlanet(it.planet, language))
        }

        return careerTypes.distinct()
    }

    private fun getCareerTypeFromPlanet(planet: Planet, language: Language): CareerType {
        return when (planet) {
            Planet.SUN -> CareerType(
                name = StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_ADMIN, language),
                industries = listOf(
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_GOVT, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ADMIN, language),
                    StringResources.get(StringKeyAnalysis.PRASHNA_CAT_CAREER, language), // Politics proxy
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_MEDICINE, language)
                ),
                suitability = StringResources.get(StringKeyDivisional.DASHAMSA_SUIT_ADMIN, language)
            )
            Planet.MOON -> CareerType(
                name = StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_PUBLIC, language),
                industries = listOf(
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_HEALTHCARE, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_HOSPITALITY, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_PR, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_MEDICINE, language) // Nursing proxy
                ),
                suitability = StringResources.get(StringKeyDivisional.DASHAMSA_SUIT_PUBLIC, language)
            )
            Planet.MARS -> CareerType(
                name = StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_TECH, language),
                industries = listOf(
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ENGINEERING, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_MILITARY, language),
                    StringResources.get(StringKeyMatch.ACTIVITY_BUSINESS_NAME, language), // Police proxy
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_MEDICINE, language), // Surgery proxy
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_SPORTS, language)
                ),
                suitability = StringResources.get(StringKeyDivisional.DASHAMSA_SUIT_TECH, language)
            )
            Planet.MERCURY -> CareerType(
                name = StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_COMM, language),
                industries = listOf(
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_BUSINESS, language),
                    StringResources.get(StringKeyMatch.PRASHNA_CAT_FINANCE, language), // Accounting proxy
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_WRITING, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_TECH, language),
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_TRADE, language)
                ),
                suitability = StringResources.get(StringKeyDivisional.DASHAMSA_SUIT_COMM, language)
            )
            Planet.JUPITER -> CareerType(
                name = StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_ADVISORY, language),
                industries = listOf(
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_TEACHING, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_LAW, language),
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_CONSULTANCY, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_FINANCE, language),
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_RELIGIOUS, language)
                ),
                suitability = StringResources.get(StringKeyDivisional.DASHAMSA_SUIT_ADVISORY, language)
            )
            Planet.VENUS -> CareerType(
                name = StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_CREATIVE, language),
                industries = listOf(
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ARTS, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ENTERTAINMENT, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_FASHION, language),
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LUXURY, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_HOSPITALITY, language) // Hotels proxy
                ),
                suitability = StringResources.get(StringKeyDivisional.DASHAMSA_SUIT_CREATIVE, language)
            )
            Planet.SATURN -> CareerType(
                name = StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_LABOR, language),
                industries = listOf(
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_MINING, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_CONSTRUCTION, language),
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_OIL, language),
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_AGRICULTURE, language),
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LABOR, language)
                ),
                suitability = StringResources.get(StringKeyDivisional.DASHAMSA_SUIT_LABOR, language)
            )
            Planet.RAHU -> CareerType(
                name = StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_UNCONVENTIONAL, language),
                industries = listOf(
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_TECHNOLOGY, language),
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_FOREIGN, language),
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_RESEARCH, language),
                    StringResources.get(StringKeyAnalysis.PRASHNA_CAT_GENERAL, language) // Media proxy
                ),
                suitability = StringResources.get(StringKeyDivisional.DASHAMSA_SUIT_UNCONVENTIONAL, language)
            )
            Planet.KETU -> CareerType(
                name = StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_SPIRITUAL, language),
                industries = listOf(
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_SPIRITUAL, language),
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_OCCULT, language),
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_RESEARCH, language),
                    StringResources.get(StringKeyAnalysis.PRASHNA_CAT_HEALTH, language) // Psychology proxy
                ),
                suitability = StringResources.get(StringKeyDivisional.DASHAMSA_SUIT_SPIRITUAL, language)
            )
            else -> CareerType(
                StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_GENERAL, language),
                emptyList(),
                StringResources.get(StringKeyDivisional.DASHAMSA_SUIT_VARIOUS, language)
            )
        }
    }

    private fun mapPlanetsToIndustries(chart: DivisionalChartData, language: Language): Map<Planet, List<String>> {
        val mapping = mutableMapOf<Planet, List<String>>()

        chart.planetPositions.filter { it.house in listOf(1, 10) }.forEach { position ->
            mapping[position.planet] = getIndustriesForPlanet(position.planet, language)
        }

        return mapping
    }

    private fun getIndustriesForPlanet(planet: Planet, language: Language): List<String> {
        return when (planet) {
            Planet.SUN -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_GOVT, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ADMIN, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_MEDICINE, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_GOLD, language)
            )
            Planet.MOON -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_HOSPITALITY, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_HEALTHCARE, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_DAIRY, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_PR, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LIQUIDS, language)
            )
            Planet.MARS -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_MILITARY, language),
                StringResources.get(StringKeyMatch.ACTIVITY_BUSINESS_NAME, language), // Police proxy
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_MEDICINE, language), // Surgery proxy
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ENGINEERING, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_REAL_ESTATE, language)
            )
            Planet.MERCURY -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_BUSINESS, language),
                StringResources.get(StringKeyMatch.PRASHNA_CAT_FINANCE, language), // Accounting proxy
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_WRITING, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_TECH, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_COMMUNICATION, language)
            )
            Planet.JUPITER -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_TEACHING, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_LAW, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_CONSULTANCY, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_FINANCE, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_BANKING, language)
            )
            Planet.VENUS -> listOf(
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ARTS, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ENTERTAINMENT, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_FASHION, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LUXURY, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_BEAUTY, language)
            )
            Planet.SATURN -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_MINING, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LABOR, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_CONSTRUCTION, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_OIL, language),
                StringResources.get(StringKeyDivisional.NAVAMSA_PROF_AGRICULTURE, language)
            )
            Planet.RAHU -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_TECHNOLOGY, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_TRADE, language), // Foreign trade proxy
                StringResources.get(StringKeyMatch.ACTIVITY_TRAVEL_NAME, language), // Airlines proxy
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_LIQUIDS, language) // Chemicals proxy
            )
            Planet.KETU -> listOf(
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_SPIRITUAL, language),
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_OCCULT, language),
                StringResources.get(StringKeyMatch.ACTIVITY_MEDICAL_NAME, language), // Alternative med proxy
                StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_RESEARCH, language)
            )
            else -> emptyList()
        }
    }

    private fun analyzeGovernmentServicePotential(
        sun: PlanetPosition?,
        chart: DivisionalChartData,
        language: Language
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
                potential >= 80 -> StringResources.get(StringKeyDivisional.DASHAMSA_GOVT_VERY_HIGH, language)
                potential >= 65 -> StringResources.get(StringKeyDivisional.DASHAMSA_GOVT_HIGH, language)
                potential >= 50 -> StringResources.get(StringKeyDivisional.DASHAMSA_GOVT_MODERATE, language)
                else -> StringResources.get(StringKeyDivisional.DASHAMSA_GOVT_LOW, language)
            },
            favorableFactors = buildList {
                sun?.let {
                    if (it.house == 10) add(StringResources.get(StringKeyDivisional.DASHAMSA_FACTOR_SUN_10, language))
                    if (AstrologicalConstants.isExalted(Planet.SUN, it.sign)) add(StringResources.get(StringKeyDivisional.DASHAMSA_FACTOR_EXALTED_SUN, language))
                }
            },
            recommendedDepartments = if (potential > 60) {
                listOf(
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_ADMIN, language),
                    StringResources.get(StringKeyMatch.PRASHNA_CAT_FINANCE, language), // Revenue proxy
                    StringResources.get(StringKeyDivisional.HORA_SUN_SOURCE_FOREIGN, language), // Foreign affairs proxy
                    StringResources.get(StringKeyDivisional.NAVAMSA_PROF_MILITARY, language) // Defense proxy
                )
            } else emptyList()
        )
    }

    private fun analyzeBusinessVsService(
        dashamsaChart: DivisionalChartData,
        chart: VedicChart,
        language: Language
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
            businessAptitude = (businessScore * 10).coerceAtMost(100),
            serviceAptitude = (serviceScore * 10).coerceAtMost(100),
            recommendation = when {
                businessScore > serviceScore + 1 -> StringResources.get(StringKeyDivisional.DASHAMSA_BIZ_APTITUDE, language)
                serviceScore > businessScore + 1 -> StringResources.get(StringKeyDivisional.DASHAMSA_SERVICE_APTITUDE, language)
                else -> StringResources.get(StringKeyDivisional.DASHAMSA_BOTH_APTITUDE, language)
            },
            businessSectors = if (businessScore > serviceScore) {
                getIndustriesForPlanet(mercuryD10?.planet ?: Planet.MERCURY, language)
            } else emptyList()
        )
    }

    private fun calculateCareerPeakTiming(
        chart: VedicChart,
        dashamsaChart: DivisionalChartData,
        language: Language
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
            description = StringResources.get(StringKeyDivisional.DASHAMSA_PEAK_10TH_LORD, language),
            significance = StringResources.get(StringKeyDivisional.DASHAMSA_PEAK_10TH_SIG, language)
        ))

        strongD10Planets.forEach { position ->
            val planetName = position.planet.getLocalizedName(language)
            val industry = getIndustriesForPlanet(position.planet, language).firstOrNull() ?: StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_GENERAL, language)
            periods.add(CareerPeakPeriod(
                planet = position.planet,
                description = StringResources.get(StringKeyDivisional.DASHAMSA_PEAK_PLANET_FMT, language, planetName),
                significance = StringResources.get(StringKeyDivisional.DASHAMSA_PEAK_PLANET_SIG, language, industry)
            ))
        }

        return periods
    }

    private fun analyzeMultipleCareers(chart: DivisionalChartData, language: Language): List<String> {
        val indicators = mutableListOf<String>()
        val tenthHousePlanets = chart.planetPositions.filter { it.house == 10 }

        if (tenthHousePlanets.size >= 2) {
            indicators.add(StringResources.get(StringKeyDivisional.DASHAMSA_MULTIPLE_10TH, language))
        }

        val mercuryD10 = chart.planetPositions.find { it.planet == Planet.MERCURY }
        if (mercuryD10?.house == 10 || mercuryD10?.house == 1) {
            indicators.add(StringResources.get(StringKeyDivisional.DASHAMSA_MERC_VERSATILE, language))
        }

        val rahuD10 = chart.planetPositions.find { it.planet == Planet.RAHU }
        if (rahuD10?.house in listOf(1, 10)) {
            indicators.add(StringResources.get(StringKeyDivisional.DASHAMSA_RAHU_UNCONVENTIONAL, language))
        }

        return indicators
    }

    private fun analyzeProfessionalStrengths(chart: DivisionalChartData, language: Language): List<String> {
        val strengths = mutableListOf<String>()
        val lagnaLord = chart.ascendantSign.ruler
        val lagnaLordPosition = chart.planetPositions.find { it.planet == lagnaLord }

        lagnaLordPosition?.let {
            if (it.house in AstrologicalConstants.KENDRA_HOUSES) {
                strengths.add(StringResources.get(StringKeyDivisional.DASHAMSA_STRENGTH_IDENTITY, language))
            }
            if (AstrologicalConstants.isExalted(it.planet, it.sign)) {
                strengths.add(StringResources.get(StringKeyDivisional.DASHAMSA_STRENGTH_EXCEPTIONAL, language))
            }
        }

        val tenthHousePlanets = chart.planetPositions.filter { it.house == 10 }
        tenthHousePlanets.forEach { position ->
            when (position.planet) {
                Planet.SUN -> strengths.add(StringResources.get(StringKeyDivisional.DASHAMSA_STRENGTH_LEADERSHIP, language))
                Planet.MOON -> strengths.add(StringResources.get(StringKeyDivisional.DASHAMSA_STRENGTH_PUBLIC, language))
                Planet.MARS -> strengths.add(StringResources.get(StringKeyDivisional.DASHAMSA_STRENGTH_DRIVE, language))
                Planet.MERCURY -> strengths.add(StringResources.get(StringKeyDivisional.DASHAMSA_STRENGTH_COMM, language))
                Planet.JUPITER -> strengths.add(StringResources.get(StringKeyDivisional.DASHAMSA_STRENGTH_WISDOM, language))
                Planet.VENUS -> strengths.add(StringResources.get(StringKeyDivisional.DASHAMSA_STRENGTH_CREATIVITY, language))
                Planet.SATURN -> strengths.add(StringResources.get(StringKeyDivisional.DASHAMSA_STRENGTH_PERSISTENCE, language))
                else -> {}
            }
        }

        return strengths
    }

    private fun generateCareerRecommendations(
        careerTypes: List<CareerType>,
        industryMappings: Map<Planet, List<String>>,
        language: Language
    ): List<String> {
        val recommendations = mutableListOf<String>()

        if (careerTypes.isNotEmpty()) {
            recommendations.add(StringResources.get(StringKeyDivisional.DASHAMSA_REC_PRIMARY_FOCUS, language, careerTypes.firstOrNull()?.name ?: StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_GENERAL, language)))
            careerTypes.firstOrNull()?.industries?.take(3)?.let { industries ->
                recommendations.add(StringResources.get(StringKeyDivisional.DASHAMSA_REC_TOP_INDUSTRIES, language, industries.joinToString(", ")))
            }
        }

        industryMappings.entries.take(2).forEach { (planet, industries) ->
            recommendations.add(StringResources.get(StringKeyDivisional.DASHAMSA_REC_PERIOD_FAVORS, language, planet.getLocalizedName(language), industries.firstOrNull() ?: StringResources.get(StringKeyDivisional.DASHAMSA_TYPE_GENERAL, language)))
        }

        return recommendations
    }

    // Dwadasamsa helper functions
    private fun analyzeFatherIndicators(
        sun: PlanetPosition?,
        ninthLord: PlanetPosition?,
        chart: DivisionalChartData,
        language: Language
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
            parent = StringResources.get(StringKeyDosha.DWADASAMSA_FATHER, language),
            significatorStrength = sun?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0,
            houseLordStrength = ninthLord?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0,
            overallWellbeing = when {
                wellbeingScore >= 70 -> StringResources.get(StringKeyDivisional.DWADASAMSA_FATHER_WELLBEING_GOOD, language)
                wellbeingScore >= 50 -> StringResources.get(StringKeyDivisional.HORA_POTENTIAL_MODERATE, language)
                else -> StringResources.get(StringKeyDivisional.HORA_POTENTIAL_CHALLENGES, language)
            },
            characteristics = determineFatherCharacteristics(sun, ninthLord, language),
            relationship = assessParentRelationship(sun, language)
        )
    }

    private fun analyzeMotherIndicators(
        moon: PlanetPosition?,
        fourthLord: PlanetPosition?,
        chart: DivisionalChartData,
        language: Language
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
            parent = StringResources.get(StringKeyDosha.DWADASAMSA_MOTHER, language),
            significatorStrength = moon?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0,
            houseLordStrength = fourthLord?.let { calculatePlanetStrengthInVarga(it) } ?: 50.0,
            overallWellbeing = when {
                wellbeingScore >= 70 -> StringResources.get(StringKeyDivisional.DWADASAMSA_MOTHER_WELLBEING_GOOD, language)
                wellbeingScore >= 50 -> StringResources.get(StringKeyDivisional.HORA_POTENTIAL_MODERATE, language)
                else -> StringResources.get(StringKeyDivisional.HORA_POTENTIAL_CHALLENGES, language)
            },
            characteristics = determineMotherCharacteristics(moon, fourthLord, language),
            relationship = assessParentRelationship(moon, language)
        )
    }

    private fun determineFatherCharacteristics(sun: PlanetPosition?, ninthLord: PlanetPosition?, language: Language): String {
        return when {
            sun != null && AstrologicalConstants.isExalted(Planet.SUN, sun.sign) ->
                StringResources.get(StringKeyDivisional.DWADASAMSA_FATHER_CHAR_AUTHORITATIVE, language)
            sun?.house == 10 -> StringResources.get(StringKeyDivisional.DWADASAMSA_FATHER_CHAR_AMBITIOUS, language)
            ninthLord?.planet == Planet.JUPITER -> StringResources.get(StringKeyDivisional.DWADASAMSA_FATHER_CHAR_RELIGIOUS, language)
            else -> StringResources.get(StringKeyDivisional.DWADASAMSA_FATHER_CHAR_VARIES, language)
        }
    }

    private fun determineMotherCharacteristics(moon: PlanetPosition?, fourthLord: PlanetPosition?, language: Language): String {
        return when {
            moon != null && AstrologicalConstants.isExalted(Planet.MOON, moon.sign) ->
                StringResources.get(StringKeyDivisional.DWADASAMSA_MOTHER_CHAR_NURTURING, language)
            moon?.house == 4 -> StringResources.get(StringKeyDivisional.DWADASAMSA_MOTHER_CHAR_HOME, language)
            fourthLord?.planet == Planet.VENUS -> StringResources.get(StringKeyDivisional.DWADASAMSA_MOTHER_CHAR_ARTISTIC, language)
            else -> StringResources.get(StringKeyDivisional.DWADASAMSA_MOTHER_CHAR_VARIES, language)
        }
    }

    private fun assessParentRelationship(significator: PlanetPosition?, language: Language): String {
        return when {
            significator == null -> StringResources.get(StringKeyDivisional.DWADASAMSA_REL_DEPENDS, language)
            significator.house in AstrologicalConstants.KENDRA_HOUSES -> StringResources.get(StringKeyDivisional.DWADASAMSA_REL_STRONG, language)
            significator.house in AstrologicalConstants.TRIKONA_HOUSES -> StringResources.get(StringKeyDivisional.DWADASAMSA_REL_HARMONIOUS, language)
            significator.house in AstrologicalConstants.DUSTHANA_HOUSES -> StringResources.get(StringKeyDivisional.DWADASAMSA_REL_CHALLENGES, language)
            else -> StringResources.get(StringKeyDivisional.DWADASAMSA_REL_MODERATE, language)
        }
    }

    private fun analyzeInheritance(
        dwadasamsaChart: DivisionalChartData,
        chart: VedicChart,
        language: Language
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
                inheritanceScore >= 70 -> StringResources.get(StringKeyDivisional.DWADASAMSA_INH_GOOD, language)
                inheritanceScore >= 50 -> StringResources.get(StringKeyDivisional.DWADASAMSA_INH_MODERATE, language)
                else -> StringResources.get(StringKeyDivisional.DWADASAMSA_INH_LIMITED, language)
            },
            sources = buildList {
                if (secondHouseD12.isNotEmpty()) add(StringResources.get(StringKeyDivisional.DWADASAMSA_SOURCE_WEALTH, language))
                if (fourthHouseD12.isNotEmpty()) add(StringResources.get(StringKeyDivisional.DWADASAMSA_SOURCE_PROPERTY, language))
                if (eighthHouseD12.any { it.planet in AstrologicalConstants.NATURAL_BENEFICS }) add(StringResources.get(StringKeyDivisional.DWADASAMSA_SOURCE_UNEXPECTED, language))
            },
            timing = StringResources.get(StringKeyDivisional.DWADASAMSA_INH_TIMING, language)
        )
    }

    private fun analyzeAncestralProperty(chart: DivisionalChartData, language: Language): List<String> {
        val indicators = mutableListOf<String>()
        val fourthHousePlanets = chart.planetPositions.filter { it.house == 4 }

        if (Planet.SATURN in fourthHousePlanets.map { it.planet }) {
            indicators.add(StringResources.get(StringKeyDivisional.DWADASAMSA_PROP_OLD, language))
        }
        if (Planet.MARS in fourthHousePlanets.map { it.planet }) {
            indicators.add(StringResources.get(StringKeyDivisional.DWADASAMSA_PROP_LAND, language))
        }
        if (Planet.JUPITER in fourthHousePlanets.map { it.planet }) {
            indicators.add(StringResources.get(StringKeyDivisional.DWADASAMSA_PROP_RELIGIOUS, language))
        }

        return indicators
    }

    private fun analyzeFamilyLineage(chart: DivisionalChartData, language: Language): List<String> {
        val insights = mutableListOf<String>()
        val lagnaLord = chart.ascendantSign.ruler
        val lagnaLordPosition = chart.planetPositions.find { it.planet == lagnaLord }

        lagnaLordPosition?.let {
            when {
                AstrologicalConstants.isExalted(it.planet, it.sign) ->
                    insights.add(StringResources.get(StringKeyDivisional.DWADASAMSA_FAM_NOBLE, language))
                it.house in listOf(9, 10) ->
                    insights.add(StringResources.get(StringKeyDivisional.DWADASAMSA_FAM_DHARMA, language))
                it.house in listOf(5, 11) ->
                    insights.add(StringResources.get(StringKeyDivisional.DWADASAMSA_FAM_SUCCESS, language))
                else ->
                    insights.add(StringResources.get(StringKeyDivisional.DWADASAMSA_FAM_DEPENDS, language))
            }
        }

        return insights
    }

    private fun analyzeParentalLongevity(
        dwadasamsaChart: DivisionalChartData,
        chart: VedicChart,
        language: Language
    ): ParentalLongevityIndicators {
        val sunD12 = dwadasamsaChart.planetPositions.find { it.planet == Planet.SUN }
        val moonD12 = dwadasamsaChart.planetPositions.find { it.planet == Planet.MOON }

        val fatherLongevity = when {
            sunD12 != null && AstrologicalConstants.isExalted(Planet.SUN, sunD12.sign) -> StringResources.get(StringKeyDivisional.DWADASAMSA_LONG_INDICATED, language)
            sunD12?.house in AstrologicalConstants.DUSTHANA_HOUSES -> StringResources.get(StringKeyDivisional.DWADASAMSA_LONG_ATTENTION, language)
            else -> StringResources.get(StringKeyDivisional.DWADASAMSA_LONG_MODERATE, language)
        }

        val motherLongevity = when {
            moonD12 != null && AstrologicalConstants.isExalted(Planet.MOON, moonD12.sign) -> StringResources.get(StringKeyDivisional.DWADASAMSA_LONG_INDICATED, language)
            moonD12?.house in AstrologicalConstants.DUSTHANA_HOUSES -> StringResources.get(StringKeyDivisional.DWADASAMSA_LONG_ATTENTION, language)
            else -> StringResources.get(StringKeyDivisional.DWADASAMSA_LONG_MODERATE, language)
        }

        return ParentalLongevityIndicators(
            fatherLongevity = fatherLongevity,
            motherLongevity = motherLongevity,
            healthConcerns = buildList {
                if (sunD12?.house in AstrologicalConstants.DUSTHANA_HOUSES) add(StringResources.get(StringKeyDivisional.DWADASAMSA_CONCERN_FATHER, language))
                if (moonD12?.house in AstrologicalConstants.DUSTHANA_HOUSES) add(StringResources.get(StringKeyDivisional.DWADASAMSA_CONCERN_MOTHER, language))
            }
        )
    }

    private fun generateParentalRecommendations(
        fatherAnalysis: ParentAnalysis,
        motherAnalysis: ParentAnalysis,
        language: Language
    ): List<String> {
        val recommendations = mutableListOf<String>()

        if (fatherAnalysis.significatorStrength < 50) {
            recommendations.add(StringResources.get(StringKeyDivisional.DWADASAMSA_REC_FATHER_SUN, language))
            recommendations.add(StringResources.get(StringKeyDivisional.DWADASAMSA_REC_FATHER_RESPECT, language))
        }

        if (motherAnalysis.significatorStrength < 50) {
            recommendations.add(StringResources.get(StringKeyDivisional.DWADASAMSA_REC_MOTHER_MOON, language))
            recommendations.add(StringResources.get(StringKeyDivisional.DWADASAMSA_REC_MOTHER_CHARITY, language))
        }

        recommendations.add(StringResources.get(StringKeyDivisional.DWADASAMSA_REC_PITRU_TARPAN, language))

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
enum class WealthPotential { 
    EXCEPTIONAL, HIGH, MODERATE, AVERAGE, LOW;
    
    fun getLocalizedName(language: Language): String {
        val key = when(this) {
            EXCEPTIONAL -> StringKeyDosha.HORA_POTENTIAL_EXCEPTIONAL
            HIGH -> StringKeyDosha.HORA_POTENTIAL_HIGH
            MODERATE -> StringKeyDosha.HORA_POTENTIAL_MODERATE
            AVERAGE -> StringKeyDosha.HORA_POTENTIAL_AVERAGE
            LOW -> StringKeyDosha.HORA_POTENTIAL_NEEDS_EFFORT
        }
        return StringResources.get(key, language)
    }
}

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

enum class RelationshipQuality { 
    EXCELLENT, GOOD, NEUTRAL, CHALLENGING, DIFFICULT;
    
    fun getLocalizedName(language: Language): String {
        val key = when(this) {
            EXCELLENT -> StringKeyDivisional.REL_QUAL_EXCELLENT
            GOOD -> StringKeyDivisional.REL_QUAL_GOOD
            NEUTRAL -> StringKeyDivisional.REL_QUAL_NEUTRAL
            CHALLENGING -> StringKeyDivisional.REL_QUAL_CHALLENGING
            DIFFICULT -> StringKeyDivisional.REL_QUAL_DIFFICULT
        }
        return StringResources.get(key, language)
    }
}

data class CourageAnalysis(
    val overallCourageLevel: CourageLevel,
    val marsStrength: Double,
    val initiativeAbility: String,
    val physicalCourage: String,
    val mentalCourage: String
)

enum class CourageLevel { 
    EXCEPTIONAL, HIGH, MODERATE, LOW, VERY_LOW;
    
    fun getLocalizedName(language: Language): String {
        val key = when(this) {
            EXCEPTIONAL -> StringKeyDosha.COURAGE_EXCEPTIONAL
            HIGH -> StringKeyDosha.COURAGE_HIGH
            MODERATE -> StringKeyDosha.COURAGE_MODERATE
            LOW -> StringKeyDosha.COURAGE_DEVELOPING
            VERY_LOW -> StringKeyDosha.COURAGE_NEEDS_WORK
        }
        return StringResources.get(key, language)
    }
}

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
