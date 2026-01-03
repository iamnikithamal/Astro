package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.localization.StringKeyDosha
import kotlin.math.abs

/**
 * Mrityu Bhaga (Sensitive Degrees) Calculator
 * Implements analysis of critical degrees where planetary placement indicates
 * health vulnerabilities, accidents, or transformative life events.
 *
 * Reference: Phaladeepika, BPHS, Saravali
 */
object MrityuBhagaCalculator {

    /**
     * Traditional Mrityu Bhaga degrees for each planet in each sign
     * These are degrees where the planet is considered to be at its most vulnerable
     * Reference: Phaladeepika Chapter 24, Various traditional texts
     */
    private val MRITYU_BHAGA_DEGREES = mapOf(
        Planet.SUN to mapOf(
            ZodiacSign.ARIES to 20.0, ZodiacSign.TAURUS to 9.0, ZodiacSign.GEMINI to 12.0,
            ZodiacSign.CANCER to 6.0, ZodiacSign.LEO to 8.0, ZodiacSign.VIRGO to 24.0,
            ZodiacSign.LIBRA to 16.0, ZodiacSign.SCORPIO to 17.0, ZodiacSign.SAGITTARIUS to 22.0,
            ZodiacSign.CAPRICORN to 2.0, ZodiacSign.AQUARIUS to 3.0, ZodiacSign.PISCES to 23.0
        ),
        Planet.MOON to mapOf(
            ZodiacSign.ARIES to 26.0, ZodiacSign.TAURUS to 12.0, ZodiacSign.GEMINI to 13.0,
            ZodiacSign.CANCER to 25.0, ZodiacSign.LEO to 24.0, ZodiacSign.VIRGO to 11.0,
            ZodiacSign.LIBRA to 26.0, ZodiacSign.SCORPIO to 14.0, ZodiacSign.SAGITTARIUS to 13.0,
            ZodiacSign.CAPRICORN to 25.0, ZodiacSign.AQUARIUS to 5.0, ZodiacSign.PISCES to 12.0
        ),
        Planet.MARS to mapOf(
            ZodiacSign.ARIES to 19.0, ZodiacSign.TAURUS to 28.0, ZodiacSign.GEMINI to 25.0,
            ZodiacSign.CANCER to 23.0, ZodiacSign.LEO to 29.0, ZodiacSign.VIRGO to 28.0,
            ZodiacSign.LIBRA to 19.0, ZodiacSign.SCORPIO to 14.0, ZodiacSign.SAGITTARIUS to 13.0,
            ZodiacSign.CAPRICORN to 21.0, ZodiacSign.AQUARIUS to 2.0, ZodiacSign.PISCES to 8.0
        ),
        Planet.MERCURY to mapOf(
            ZodiacSign.ARIES to 15.0, ZodiacSign.TAURUS to 14.0, ZodiacSign.GEMINI to 13.0,
            ZodiacSign.CANCER to 12.0, ZodiacSign.LEO to 8.0, ZodiacSign.VIRGO to 18.0,
            ZodiacSign.LIBRA to 20.0, ZodiacSign.SCORPIO to 10.0, ZodiacSign.SAGITTARIUS to 21.0,
            ZodiacSign.CAPRICORN to 22.0, ZodiacSign.AQUARIUS to 7.0, ZodiacSign.PISCES to 15.0
        ),
        Planet.JUPITER to mapOf(
            ZodiacSign.ARIES to 19.0, ZodiacSign.TAURUS to 29.0, ZodiacSign.GEMINI to 12.0,
            ZodiacSign.CANCER to 27.0, ZodiacSign.LEO to 6.0, ZodiacSign.VIRGO to 4.0,
            ZodiacSign.LIBRA to 13.0, ZodiacSign.SCORPIO to 10.0, ZodiacSign.SAGITTARIUS to 17.0,
            ZodiacSign.CAPRICORN to 27.0, ZodiacSign.AQUARIUS to 14.0, ZodiacSign.PISCES to 13.0
        ),
        Planet.VENUS to mapOf(
            ZodiacSign.ARIES to 28.0, ZodiacSign.TAURUS to 15.0, ZodiacSign.GEMINI to 11.0,
            ZodiacSign.CANCER to 17.0, ZodiacSign.LEO to 10.0, ZodiacSign.VIRGO to 13.0,
            ZodiacSign.LIBRA to 4.0, ZodiacSign.SCORPIO to 7.0, ZodiacSign.SAGITTARIUS to 11.0,
            ZodiacSign.CAPRICORN to 6.0, ZodiacSign.AQUARIUS to 15.0, ZodiacSign.PISCES to 17.0
        ),
        Planet.SATURN to mapOf(
            ZodiacSign.ARIES to 10.0, ZodiacSign.TAURUS to 4.0, ZodiacSign.GEMINI to 7.0,
            ZodiacSign.CANCER to 9.0, ZodiacSign.LEO to 12.0, ZodiacSign.VIRGO to 23.0,
            ZodiacSign.LIBRA to 7.0, ZodiacSign.SCORPIO to 22.0, ZodiacSign.SAGITTARIUS to 3.0,
            ZodiacSign.CAPRICORN to 18.0, ZodiacSign.AQUARIUS to 26.0, ZodiacSign.PISCES to 14.0
        ),
        Planet.RAHU to mapOf(
            ZodiacSign.ARIES to 14.0, ZodiacSign.TAURUS to 13.0, ZodiacSign.GEMINI to 12.0,
            ZodiacSign.CANCER to 11.0, ZodiacSign.LEO to 24.0, ZodiacSign.VIRGO to 23.0,
            ZodiacSign.LIBRA to 22.0, ZodiacSign.SCORPIO to 21.0, ZodiacSign.SAGITTARIUS to 20.0,
            ZodiacSign.CAPRICORN to 19.0, ZodiacSign.AQUARIUS to 18.0, ZodiacSign.PISCES to 17.0
        ),
        Planet.KETU to mapOf(
            ZodiacSign.ARIES to 14.0, ZodiacSign.TAURUS to 13.0, ZodiacSign.GEMINI to 12.0,
            ZodiacSign.CANCER to 11.0, ZodiacSign.LEO to 24.0, ZodiacSign.VIRGO to 23.0,
            ZodiacSign.LIBRA to 22.0, ZodiacSign.SCORPIO to 21.0, ZodiacSign.SAGITTARIUS to 20.0,
            ZodiacSign.CAPRICORN to 19.0, ZodiacSign.AQUARIUS to 18.0, ZodiacSign.PISCES to 17.0
        )
    )

    /** Orb for Mrityu Bhaga consideration (degrees) */
    private const val MRITYU_BHAGA_ORB = 1.0

    /** Gandanta junction points (water-fire sign boundaries) */
    private val GANDANTA_POINTS = listOf(
        GandantaPoint(ZodiacSign.CANCER, ZodiacSign.LEO, 29.0, 1.0),
        GandantaPoint(ZodiacSign.SCORPIO, ZodiacSign.SAGITTARIUS, 29.0, 1.0),
        GandantaPoint(ZodiacSign.PISCES, ZodiacSign.ARIES, 29.0, 1.0)
    )

    /** Pushkara Navamsa degrees - highly auspicious placements */
    private val PUSHKARA_NAVAMSA = mapOf(
        ZodiacSign.ARIES to listOf(21.0 to 23.333, 24.0 to 26.667),
        ZodiacSign.TAURUS to listOf(7.333 to 10.0, 14.0 to 16.667),
        ZodiacSign.GEMINI to listOf(17.333 to 20.0, 27.333 to 30.0),
        ZodiacSign.CANCER to listOf(0.0 to 3.333, 10.0 to 13.333),
        ZodiacSign.LEO to listOf(21.0 to 23.333, 24.0 to 26.667),
        ZodiacSign.VIRGO to listOf(7.333 to 10.0, 14.0 to 16.667),
        ZodiacSign.LIBRA to listOf(17.333 to 20.0, 27.333 to 30.0),
        ZodiacSign.SCORPIO to listOf(0.0 to 3.333, 10.0 to 13.333),
        ZodiacSign.SAGITTARIUS to listOf(21.0 to 23.333, 24.0 to 26.667),
        ZodiacSign.CAPRICORN to listOf(7.333 to 10.0, 14.0 to 16.667),
        ZodiacSign.AQUARIUS to listOf(17.333 to 20.0, 27.333 to 30.0),
        ZodiacSign.PISCES to listOf(0.0 to 3.333, 10.0 to 13.333)
    )

    /** Pushkara Bhaga degrees - single auspicious degrees */
    private val PUSHKARA_BHAGA = mapOf(
        ZodiacSign.ARIES to 21.0,
        ZodiacSign.TAURUS to 14.0,
        ZodiacSign.GEMINI to 18.0,
        ZodiacSign.CANCER to 8.0,
        ZodiacSign.LEO to 19.0,
        ZodiacSign.VIRGO to 9.0,
        ZodiacSign.LIBRA to 24.0,
        ZodiacSign.SCORPIO to 11.0,
        ZodiacSign.SAGITTARIUS to 23.0,
        ZodiacSign.CAPRICORN to 14.0,
        ZodiacSign.AQUARIUS to 19.0,
        ZodiacSign.PISCES to 9.0
    )

    /**
     * Comprehensive sensitive degrees analysis
     */
    fun analyzeSensitiveDegrees(chart: VedicChart, language: Language): SensitiveDegreesAnalysis {
        val mrityuBhagaResults = analyzeMrityuBhaga(chart, language)
        val gandantaResults = analyzeGandanta(chart, language)
        val pushkaraNavamsaResults = analyzePushkaraNavamsa(chart, language)
        val pushkaraBhagaResults = analyzePushkaraBhaga(chart, language)

        val criticalPlanets = mrityuBhagaResults.filter { it.isInMrityuBhaga } +
                              gandantaResults.filter { it.isInGandanta }

        val auspiciousPlanets = pushkaraNavamsaResults.filter { it.isInPushkaraNavamsa } +
                                pushkaraBhagaResults.filter { it.isInPushkaraBhaga }

        val overallAssessment = calculateOverallAssessment(
            mrityuBhagaResults, gandantaResults, pushkaraNavamsaResults, pushkaraBhagaResults, language
        )

        return SensitiveDegreesAnalysis(
            mrityuBhagaAnalysis = mrityuBhagaResults,
            gandantaAnalysis = gandantaResults,
            pushkaraNavamsaAnalysis = pushkaraNavamsaResults,
            pushkaraBhagaAnalysis = pushkaraBhagaResults,
            criticalPlanets = criticalPlanets.map {
                when (it) {
                    is MrityuBhagaResult -> it.planet
                    is GandantaResult -> it.planet
                    else -> Planet.SUN
                }
            }.distinct(),
            auspiciousPlanets = auspiciousPlanets.map {
                when (it) {
                    is PushkaraNavamsaResult -> it.planet
                    is PushkaraBhagaResult -> it.planet
                    else -> Planet.SUN
                }
            }.distinct(),
            overallAssessment = overallAssessment,
            recommendations = generateRecommendations(mrityuBhagaResults, gandantaResults, language)
        )
    }

    /**
     * Analyze Mrityu Bhaga placements for all planets
     */
    fun analyzeMrityuBhaga(chart: VedicChart, language: Language): List<MrityuBhagaResult> {
        return chart.planetPositions.mapNotNull { position ->
            val mrityuDegree = MRITYU_BHAGA_DEGREES[position.planet]?.get(position.sign)
                ?: return@mapNotNull null

            val degreeInSign = position.longitude % 30.0
            val distance = abs(degreeInSign - mrityuDegree)
            val isInMrityuBhaga = distance <= MRITYU_BHAGA_ORB

            val severity = when {
                distance <= 0.25 -> MrityuBhagaSeverity.EXACT
                distance <= 0.5 -> MrityuBhagaSeverity.VERY_CLOSE
                distance <= MRITYU_BHAGA_ORB -> MrityuBhagaSeverity.WITHIN_ORB
                distance <= 2.0 -> MrityuBhagaSeverity.APPROACHING
                else -> MrityuBhagaSeverity.SAFE
            }

            val effects = if (isInMrityuBhaga) {
                getMrityuBhagaEffects(position.planet, language)
            } else emptyList()

            val vulnerabilityAreas = if (isInMrityuBhaga) {
                getVulnerabilityAreas(position.planet, language)
            } else emptyList()

            MrityuBhagaResult(
                planet = position.planet,
                sign = position.sign,
                actualDegree = degreeInSign,
                mrityuBhagaDegree = mrityuDegree,
                distanceFromMrityuBhaga = distance,
                isInMrityuBhaga = isInMrityuBhaga,
                severity = severity,
                effects = effects,
                vulnerabilityAreas = vulnerabilityAreas,
                remedies = if (isInMrityuBhaga) getMrityuBhagaRemedies(position.planet, language) else emptyList()
            )
        }
    }

    /**
     * Analyze Gandanta placements
     */
    fun analyzeGandanta(chart: VedicChart, language: Language): List<GandantaResult> {
        return chart.planetPositions.mapNotNull { position ->
            val degreeInSign = position.longitude % 30.0

            val gandanta = GANDANTA_POINTS.find { gp ->
                (position.sign == gp.waterSign && degreeInSign >= 30.0 - gp.orbBefore) ||
                (position.sign == gp.fireSign && degreeInSign <= gp.orbAfter)
            }

            gandanta?.let { gp ->
                val isWaterSide = position.sign == gp.waterSign
                val distanceFromJunction = if (isWaterSide) {
                    30.0 - degreeInSign
                } else {
                    degreeInSign
                }

                val severity = when {
                    distanceFromJunction <= 0.25 -> GandantaSeverity.EXACT_JUNCTION
                    distanceFromJunction <= 0.5 -> GandantaSeverity.CRITICAL
                    distanceFromJunction <= 1.0 -> GandantaSeverity.SEVERE
                    distanceFromJunction <= 2.0 -> GandantaSeverity.MODERATE
                    else -> GandantaSeverity.MILD
                }

                val type = getGandantaType(gp.waterSign)

                GandantaResult(
                    planet = position.planet,
                    sign = position.sign,
                    degree = degreeInSign,
                    isInGandanta = true,
                    distanceFromJunction = distanceFromJunction,
                    severity = severity,
                    gandantaType = type,
                    waterSign = gp.waterSign,
                    fireSign = gp.fireSign,
                    effects = getGandantaEffects(position.planet, type, language),
                    remedies = getGandantaRemedies(position.planet, type, language)
                )
            }
        }
    }

    /**
     * Analyze Pushkara Navamsa placements
     */
    fun analyzePushkaraNavamsa(chart: VedicChart, language: Language): List<PushkaraNavamsaResult> {
        return chart.planetPositions.map { position ->
            val degreeInSign = position.longitude % 30.0
            val ranges = PUSHKARA_NAVAMSA[position.sign] ?: emptyList()

            val matchingRange = ranges.find { (start, end) ->
                degreeInSign >= start && degreeInSign <= end
            }

            val isInPushkara = matchingRange != null

            PushkaraNavamsaResult(
                planet = position.planet,
                sign = position.sign,
                degree = degreeInSign,
                isInPushkaraNavamsa = isInPushkara,
                pushkaraRange = matchingRange,
                benefits = if (isInPushkara) getPushkaraNavamsaBenefits(position.planet, language) else emptyList()
            )
        }
    }

    /**
     * Analyze Pushkara Bhaga placements
     */
    fun analyzePushkaraBhaga(chart: VedicChart, language: Language): List<PushkaraBhagaResult> {
        return chart.planetPositions.map { position ->
            val degreeInSign = position.longitude % 30.0
            val pushkaraDegree = PUSHKARA_BHAGA[position.sign] ?: 0.0
            val distance = abs(degreeInSign - pushkaraDegree)
            val isInPushkara = distance <= 1.0

            PushkaraBhagaResult(
                planet = position.planet,
                sign = position.sign,
                actualDegree = degreeInSign,
                pushkaraBhagaDegree = pushkaraDegree,
                distance = distance,
                isInPushkaraBhaga = isInPushkara,
                benefits = if (isInPushkara) getPushkaraBhagaBenefits(position.planet, language) else emptyList()
            )
        }
    }

    /**
     * Calculate transit vulnerability periods
     */
    fun calculateTransitVulnerability(
        chart: VedicChart,
        transitingPlanet: Planet,
        transitSign: ZodiacSign,
        transitDegree: Double,
        language: Language
    ): TransitVulnerabilityResult {
        val mrityuDegree = MRITYU_BHAGA_DEGREES[transitingPlanet]?.get(transitSign)
        val distance = mrityuDegree?.let { abs(transitDegree - it) } ?: Double.MAX_VALUE

        val isVulnerable = distance <= 2.0

        val natalPlanetsAffected = chart.planetPositions.filter { position ->
            val aspect = calculateAspect(transitSign.ordinal, position.sign.ordinal)
            aspect != null && distance <= 1.0
        }

        return TransitVulnerabilityResult(
            transitingPlanet = transitingPlanet,
            transitSign = transitSign,
            transitDegree = transitDegree,
            mrityuBhagaDegree = mrityuDegree ?: 0.0,
            distanceFromMrityuBhaga = distance,
            isVulnerablePeriod = isVulnerable,
            natalPlanetsAffected = natalPlanetsAffected.map { it.planet },
            cautionLevel = when {
                distance <= 0.5 -> CautionLevel.HIGH
                distance <= 1.0 -> CautionLevel.MODERATE
                distance <= 2.0 -> CautionLevel.LOW
                else -> CautionLevel.NONE
            },
            recommendations = if (isVulnerable) {
                getTransitRecommendations(transitingPlanet, language)
            } else emptyList()
        )
    }

    private fun calculateAspect(fromSignIndex: Int, toSignIndex: Int): Int? {
        val diff = (toSignIndex - fromSignIndex + 12) % 12
        return if (diff in listOf(0, 3, 6, 9)) diff else null
    }

    private fun getMrityuBhagaEffects(planet: Planet, language: Language): List<String> {
        return when (planet) {
            Planet.SUN -> listOf(
                StringResources.get(StringKeyFinder.MB_SUN_EFF_1, language),
                StringResources.get(StringKeyFinder.MB_SUN_EFF_2, language),
                StringResources.get(StringKeyFinder.MB_SUN_EFF_3, language),
                StringResources.get(StringKeyFinder.MB_SUN_EFF_4, language)
            )
            Planet.MOON -> listOf(
                StringResources.get(StringKeyFinder.MB_MOON_EFF_1, language),
                StringResources.get(StringKeyFinder.MB_MOON_EFF_2, language),
                StringResources.get(StringKeyFinder.MB_MOON_EFF_3, language),
                StringResources.get(StringKeyFinder.MB_MOON_EFF_4, language)
            )
            Planet.MARS -> listOf(
                StringResources.get(StringKeyFinder.MB_MARS_EFF_1, language),
                StringResources.get(StringKeyFinder.MB_MARS_EFF_2, language),
                StringResources.get(StringKeyFinder.MB_MARS_EFF_3, language),
                StringResources.get(StringKeyFinder.MB_MARS_EFF_4, language)
            )
            Planet.MERCURY -> listOf(
                StringResources.get(StringKeyFinder.MB_MERC_EFF_1, language),
                StringResources.get(StringKeyFinder.MB_MERC_EFF_2, language),
                StringResources.get(StringKeyFinder.MB_MERC_EFF_3, language),
                StringResources.get(StringKeyFinder.MB_MERC_EFF_4, language)
            )
            Planet.JUPITER -> listOf(
                StringResources.get(StringKeyFinder.MB_JUP_EFF_1, language),
                StringResources.get(StringKeyFinder.MB_JUP_EFF_2, language),
                StringResources.get(StringKeyFinder.MB_JUP_EFF_3, language),
                StringResources.get(StringKeyFinder.MB_JUP_EFF_4, language)
            )
            Planet.VENUS -> listOf(
                StringResources.get(StringKeyFinder.MB_VENUS_EFF_1, language),
                StringResources.get(StringKeyFinder.MB_VENUS_EFF_2, language),
                StringResources.get(StringKeyFinder.MB_VENUS_EFF_3, language),
                StringResources.get(StringKeyFinder.MB_VENUS_EFF_4, language)
            )
            Planet.SATURN -> listOf(
                StringResources.get(StringKeyFinder.MB_SAT_EFF_1, language),
                StringResources.get(StringKeyFinder.MB_SAT_EFF_2, language),
                StringResources.get(StringKeyFinder.MB_SAT_EFF_3, language),
                StringResources.get(StringKeyFinder.MB_SAT_EFF_4, language)
            )
            Planet.RAHU -> listOf(
                StringResources.get(StringKeyFinder.MB_RAHU_EFF_1, language),
                StringResources.get(StringKeyFinder.MB_RAHU_EFF_2, language),
                StringResources.get(StringKeyFinder.MB_RAHU_EFF_3, language),
                StringResources.get(StringKeyFinder.MB_RAHU_EFF_4, language)
            )
            Planet.KETU -> listOf(
                StringResources.get(StringKeyFinder.MB_KETU_EFF_1, language),
                StringResources.get(StringKeyFinder.MB_KETU_EFF_2, language),
                StringResources.get(StringKeyFinder.MB_KETU_EFF_3, language),
                StringResources.get(StringKeyFinder.MB_KETU_EFF_4, language)
            )
            else -> emptyList()
        }
    }

    private fun getVulnerabilityAreas(planet: Planet, language: Language): List<String> {
        val keys = when (planet) {
            Planet.SUN -> listOf(StringKeyFinder.AREA_HEART, StringKeyFinder.AREA_EYES, StringKeyFinder.AREA_SPINE, StringKeyFinder.AREA_VITALITY)
            Planet.MOON -> listOf(StringKeyFinder.AREA_MIND, StringKeyFinder.AREA_EMOTIONS, StringKeyFinder.AREA_STOMACH, StringKeyFinder.AREA_BREASTS, StringKeyFinder.AREA_FLUIDS)
            Planet.MARS -> listOf(StringKeyFinder.AREA_BLOOD, StringKeyFinder.AREA_MUSCLES, StringKeyFinder.AREA_HEAD, StringKeyFinder.AREA_ACCIDENTS, StringKeyFinder.AREA_BURNS)
            Planet.MERCURY -> listOf(StringKeyFinder.AREA_NERVOUS, StringKeyFinder.AREA_SKIN, StringKeyFinder.AREA_LUNGS, StringKeyFinder.AREA_SPEECH)
            Planet.JUPITER -> listOf(StringKeyFinder.AREA_LIVER, StringKeyFinder.AREA_FAT, StringKeyFinder.AREA_EARS, StringKeyFinder.AREA_THIGHS)
            Planet.VENUS -> listOf(StringKeyFinder.AREA_REPRO, StringKeyFinder.AREA_KIDNEYS, StringKeyFinder.AREA_THROAT, StringKeyFinder.AREA_FACE)
            Planet.SATURN -> listOf(StringKeyFinder.AREA_BONES, StringKeyFinder.AREA_JOINTS, StringKeyFinder.AREA_TEETH, StringKeyFinder.AREA_CHRONIC)
            Planet.RAHU -> listOf(StringKeyFinder.AREA_POISONS, StringKeyFinder.AREA_ACCIDENTS, StringKeyFinder.AREA_UNKNOWN_DIS)
            Planet.KETU -> listOf(StringKeyFinder.AREA_WOUNDS, StringKeyFinder.AREA_INFECTIONS, StringKeyFinder.AREA_ACCIDENTS, StringKeyFinder.AREA_SPIRIT_CRISIS)
            else -> emptyList()
        }
        return keys.map { StringResources.get(it, language) }
    }

    private fun getMrityuBhagaRemedies(planet: Planet, language: Language): List<String> {
        return when (planet) {
            Planet.SUN -> listOf(
                StringResources.get(StringKeyFinder.MB_SUN_REM_1, language),
                StringResources.get(StringKeyFinder.MB_SUN_REM_2, language),
                StringResources.get(StringKeyFinder.MB_SUN_REM_3, language),
                StringResources.get(StringKeyFinder.MB_SUN_REM_4, language)
            )
            Planet.MOON -> listOf(
                StringResources.get(StringKeyFinder.MB_MOON_REM_1, language),
                StringResources.get(StringKeyFinder.MB_MOON_REM_2, language),
                StringResources.get(StringKeyFinder.MB_MOON_REM_3, language),
                StringResources.get(StringKeyFinder.MB_MOON_REM_4, language)
            )
            Planet.MARS -> listOf(
                StringResources.get(StringKeyFinder.MB_MARS_REM_1, language),
                StringResources.get(StringKeyFinder.MB_MARS_REM_2, language),
                StringResources.get(StringKeyFinder.MB_MARS_REM_3, language),
                StringResources.get(StringKeyFinder.MB_MARS_REM_4, language)
            )
            Planet.MERCURY -> listOf(
                StringResources.get(StringKeyFinder.MB_MERC_REM_1, language),
                StringResources.get(StringKeyFinder.MB_MERC_REM_2, language),
                StringResources.get(StringKeyFinder.MB_MERC_REM_3, language),
                StringResources.get(StringKeyFinder.MB_MERC_REM_4, language)
            )
            Planet.JUPITER -> listOf(
                StringResources.get(StringKeyFinder.MB_JUP_REM_1, language),
                StringResources.get(StringKeyFinder.MB_JUP_REM_2, language),
                StringResources.get(StringKeyFinder.MB_JUP_REM_3, language),
                StringResources.get(StringKeyFinder.MB_JUP_REM_4, language)
            )
            Planet.VENUS -> listOf(
                StringResources.get(StringKeyFinder.MB_VENUS_REM_1, language),
                StringResources.get(StringKeyFinder.MB_VENUS_REM_2, language),
                StringResources.get(StringKeyFinder.MB_VENUS_REM_3, language),
                StringResources.get(StringKeyFinder.MB_VENUS_REM_4, language)
            )
            Planet.SATURN -> listOf(
                StringResources.get(StringKeyFinder.MB_SAT_REM_1, language),
                StringResources.get(StringKeyFinder.MB_SAT_REM_2, language),
                StringResources.get(StringKeyFinder.MB_SAT_REM_3, language),
                StringResources.get(StringKeyFinder.MB_SAT_REM_4, language)
            )
            Planet.RAHU -> listOf(
                StringResources.get(StringKeyFinder.MB_RAHU_REM_1, language),
                StringResources.get(StringKeyFinder.MB_RAHU_REM_2, language),
                StringResources.get(StringKeyFinder.MB_RAHU_REM_3, language),
                StringResources.get(StringKeyFinder.MB_RAHU_REM_4, language)
            )
            Planet.KETU -> listOf(
                StringResources.get(StringKeyFinder.MB_KETU_REM_1, language),
                StringResources.get(StringKeyFinder.MB_KETU_REM_2, language),
                StringResources.get(StringKeyFinder.MB_KETU_REM_3, language),
                StringResources.get(StringKeyFinder.MB_KETU_REM_4, language)
            )
            else -> emptyList()
        }
    }

    private fun getGandantaType(waterSign: ZodiacSign): GandantaType {
        return when (waterSign) {
            ZodiacSign.CANCER -> GandantaType.BRAHMA_GANDANTA
            ZodiacSign.SCORPIO -> GandantaType.VISHNU_GANDANTA
            ZodiacSign.PISCES -> GandantaType.SHIVA_GANDANTA
            else -> GandantaType.BRAHMA_GANDANTA
        }
    }

    private fun getGandantaEffects(planet: Planet, type: GandantaType, language: Language): List<String> {
        val baseEffects = when (type) {
            GandantaType.BRAHMA_GANDANTA -> listOf(
                StringResources.get(StringKeyFinder.GAND_BRAHMA_EFF_1, language),
                StringResources.get(StringKeyFinder.GAND_BRAHMA_EFF_2, language),
                StringResources.get(StringKeyFinder.GAND_BRAHMA_EFF_3, language)
            )
            GandantaType.VISHNU_GANDANTA -> listOf(
                StringResources.get(StringKeyFinder.GAND_VISHNU_EFF_1, language),
                StringResources.get(StringKeyFinder.GAND_VISHNU_EFF_2, language),
                StringResources.get(StringKeyFinder.GAND_VISHNU_EFF_3, language)
            )
            GandantaType.SHIVA_GANDANTA -> listOf(
                StringResources.get(StringKeyFinder.GAND_SHIVA_EFF_1, language),
                StringResources.get(StringKeyFinder.GAND_SHIVA_EFF_2, language),
                StringResources.get(StringKeyFinder.GAND_SHIVA_EFF_3, language)
            )
        }

        val planetSpecific = when (planet) {
            Planet.MOON -> listOf(
                StringResources.get(StringKeyFinder.GAND_MOON_EFF_1, language),
                StringResources.get(StringKeyFinder.GAND_MOON_EFF_2, language)
            )
            Planet.SUN -> listOf(
                StringResources.get(StringKeyFinder.GAND_SUN_EFF_1, language),
                StringResources.get(StringKeyFinder.GAND_SUN_EFF_2, language)
            )
            Planet.MARS -> listOf(
                StringResources.get(StringKeyFinder.GAND_MARS_EFF_1, language),
                StringResources.get(StringKeyFinder.GAND_MARS_EFF_2, language)
            )
            else -> emptyList()
        }

        return baseEffects + planetSpecific
    }

    private fun getGandantaRemedies(planet: Planet, type: GandantaType, language: Language): List<String> {
        val typeRemedies = when (type) {
            GandantaType.BRAHMA_GANDANTA -> listOf(
                StringResources.get(StringKeyFinder.GAND_BRAHMA_REM_1, language),
                StringResources.get(StringKeyFinder.GAND_BRAHMA_REM_2, language),
                StringResources.get(StringKeyFinder.GAND_BRAHMA_REM_3, language)
            )
            GandantaType.VISHNU_GANDANTA -> listOf(
                StringResources.get(StringKeyFinder.GAND_VISHNU_REM_1, language),
                StringResources.get(StringKeyFinder.GAND_VISHNU_REM_2, language),
                StringResources.get(StringKeyFinder.GAND_VISHNU_REM_3, language)
            )
            GandantaType.SHIVA_GANDANTA -> listOf(
                StringResources.get(StringKeyFinder.GAND_SHIVA_REM_1, language),
                StringResources.get(StringKeyFinder.GAND_SHIVA_REM_2, language),
                StringResources.get(StringKeyFinder.GAND_SHIVA_REM_3, language)
            )
        }

        val planetRemedies = getMrityuBhagaRemedies(planet, language)

        return typeRemedies + planetRemedies.take(2)
    }

    private fun getPushkaraNavamsaBenefits(planet: Planet, language: Language): List<String> {
        val basebenefits = listOf(
            StringResources.get(StringKeyFinder.PUSH_BASE_1, language),
            StringResources.get(StringKeyFinder.PUSH_BASE_2, language),
            StringResources.get(StringKeyFinder.PUSH_BASE_3, language)
        )

        val planetBenefits = when (planet) {
            Planet.MOON -> listOf(
                StringResources.get(StringKeyFinder.PUSH_MOON_1, language),
                StringResources.get(StringKeyFinder.PUSH_MOON_2, language)
            )
            Planet.JUPITER -> listOf(
                StringResources.get(StringKeyFinder.PUSH_JUP_1, language),
                StringResources.get(StringKeyFinder.PUSH_JUP_2, language)
            )
            Planet.VENUS -> listOf(
                StringResources.get(StringKeyFinder.PUSH_VENUS_1, language),
                StringResources.get(StringKeyFinder.PUSH_VENUS_2, language)
            )
            Planet.MERCURY -> listOf(
                StringResources.get(StringKeyFinder.PUSH_MERC_1, language),
                StringResources.get(StringKeyFinder.PUSH_MERC_2, language)
            )
            else -> listOf(StringResources.get(StringKeyFinder.PUSH_GEN_PROT, language))
        }

        return basebenefits + planetBenefits
    }

    private fun getPushkaraBhagaBenefits(planet: Planet, language: Language): List<String> {
        val planetName = planet.getLocalizedName(language)
        return listOf(
            StringResources.get(StringKeyFinder.PUSH_BHAGA_NOURISH, language),
            StringResources.get(StringKeyFinder.PUSH_BHAGA_SUPPORT, language, planetName),
            StringResources.get(StringKeyFinder.PUSH_BHAGA_ACT, language, planetName),
            StringResources.get(StringKeyFinder.PUSH_BHAGA_PROT, language, planetName)
        )
    }

    private fun getTransitRecommendations(planet: Planet, language: Language): List<String> {
        val planetName = planet.getLocalizedName(language)
        return listOf(
            StringResources.get(StringKeyFinder.TRANSIT_CAUTION, language),
            StringResources.get(StringKeyFinder.TRANSIT_MAJOR_DEC, language, planetName),
            StringResources.get(StringKeyFinder.TRANSIT_INTENSE_REM, language, planetName),
            StringResources.get(StringKeyFinder.TRANSIT_AWARENESS, language)
        )
    }

    private fun calculateOverallAssessment(
        mrityuBhaga: List<MrityuBhagaResult>,
        gandanta: List<GandantaResult>,
        pushkaraNavamsa: List<PushkaraNavamsaResult>,
        pushkaraBhaga: List<PushkaraBhagaResult>,
        language: Language
    ): OverallSensitiveDegreesAssessment {
        val criticalCount = mrityuBhaga.count { it.isInMrityuBhaga } +
                           gandanta.count { it.isInGandanta }
        val auspiciousCount = pushkaraNavamsa.count { it.isInPushkaraNavamsa } +
                              pushkaraBhaga.count { it.isInPushkaraBhaga }

        val level = when {
            criticalCount >= 3 && auspiciousCount < 2 -> AssessmentLevel.NEEDS_ATTENTION
            criticalCount >= 2 -> AssessmentLevel.MODERATE_CONCERN
            criticalCount == 1 && auspiciousCount >= 2 -> AssessmentLevel.BALANCED
            auspiciousCount >= 3 -> AssessmentLevel.HIGHLY_AUSPICIOUS
            else -> AssessmentLevel.GENERALLY_POSITIVE
        }

        val summary = when (level) {
            AssessmentLevel.NEEDS_ATTENTION -> StringResources.get(StringKeyFinder.ASSESS_NEEDS_ATTN, language)
            AssessmentLevel.MODERATE_CONCERN -> StringResources.get(StringKeyFinder.ASSESS_MODERATE, language)
            AssessmentLevel.BALANCED -> StringResources.get(StringKeyFinder.ASSESS_BALANCED, language)
            AssessmentLevel.HIGHLY_AUSPICIOUS -> StringResources.get(StringKeyFinder.ASSESS_HIGHLY_AUSP, language)
            AssessmentLevel.GENERALLY_POSITIVE -> StringResources.get(StringKeyFinder.ASSESS_GEN_POS, language)
        }

        return OverallSensitiveDegreesAssessment(
            level = level,
            criticalPlacementCount = criticalCount,
            auspiciousPlacementCount = auspiciousCount,
            summary = summary
        )
    }

    private fun generateRecommendations(
        mrityuBhaga: List<MrityuBhagaResult>,
        gandanta: List<GandantaResult>,
        language: Language
    ): List<String> {
        val recommendations = mutableListOf<String>()

        val criticalMrityu = mrityuBhaga.filter { it.severity == MrityuBhagaSeverity.EXACT ||
                                                   it.severity == MrityuBhagaSeverity.VERY_CLOSE }
        val criticalGandanta = gandanta.filter { it.severity == GandantaSeverity.EXACT_JUNCTION ||
                                                  it.severity == GandantaSeverity.CRITICAL }

        if (criticalMrityu.isNotEmpty()) {
            recommendations.add(StringResources.get(StringKeyFinder.REC_MB_SPECIFIC, language))
            recommendations.add(StringResources.get(StringKeyFinder.REC_MB_CAREFUL, language))
        }

        if (criticalGandanta.isNotEmpty()) {
            val moonGandanta = criticalGandanta.find { it.planet == Planet.MOON }
            if (moonGandanta != null) {
                recommendations.add(StringResources.get(StringKeyFinder.REC_GAND_MOON_ATTN, language))
                recommendations.add(StringResources.get(StringKeyFinder.REC_GAND_MOON_GROUND, language))
            }
            recommendations.add(StringResources.get(StringKeyFinder.REC_GAND_KNOTS, language))
        }

        if (recommendations.isEmpty()) {
            recommendations.add(StringResources.get(StringKeyFinder.REC_NO_CRITICAL, language))
            recommendations.add(StringResources.get(StringKeyFinder.REC_CONT_WORSHIP, language))
        }

        return recommendations
    }
}

// Data classes
data class SensitiveDegreesAnalysis(
    val mrityuBhagaAnalysis: List<MrityuBhagaResult>,
    val gandantaAnalysis: List<GandantaResult>,
    val pushkaraNavamsaAnalysis: List<PushkaraNavamsaResult>,
    val pushkaraBhagaAnalysis: List<PushkaraBhagaResult>,
    val criticalPlanets: List<Planet>,
    val auspiciousPlanets: List<Planet>,
    val overallAssessment: OverallSensitiveDegreesAssessment,
    val recommendations: List<String>
)

data class MrityuBhagaResult(
    val planet: Planet,
    val sign: ZodiacSign,
    val actualDegree: Double,
    val mrityuBhagaDegree: Double,
    val distanceFromMrityuBhaga: Double,
    val isInMrityuBhaga: Boolean,
    val severity: MrityuBhagaSeverity,
    val effects: List<String>,
    val vulnerabilityAreas: List<String>,
    val remedies: List<String>
)

enum class MrityuBhagaSeverity {
    EXACT, VERY_CLOSE, WITHIN_ORB, APPROACHING, SAFE;

    fun getLocalizedName(language: Language): String {
        return StringResources.get(when(this) {
            EXACT -> StringKeyDosha.MB_SEV_EXACT
            VERY_CLOSE -> StringKeyDosha.MB_SEV_VERY_CLOSE
            WITHIN_ORB -> StringKeyDosha.MB_SEV_WITHIN_ORB
            APPROACHING -> StringKeyDosha.MB_SEV_APPROACHING
            SAFE -> StringKeyDosha.MB_SEV_SAFE
        }, language)
    }
}

data class GandantaResult(
    val planet: Planet,
    val sign: ZodiacSign,
    val degree: Double,
    val isInGandanta: Boolean,
    val distanceFromJunction: Double,
    val severity: GandantaSeverity,
    val gandantaType: GandantaType,
    val waterSign: ZodiacSign,
    val fireSign: ZodiacSign,
    val effects: List<String>,
    val remedies: List<String>
)

enum class GandantaSeverity {
    EXACT_JUNCTION, CRITICAL, SEVERE, MODERATE, MILD;

    fun getLocalizedName(language: Language): String {
        return StringResources.get(when(this) {
            EXACT_JUNCTION -> StringKeyDosha.GANDANTA_SEV_EXACT
            CRITICAL -> StringKeyDosha.GANDANTA_SEV_CRITICAL
            SEVERE -> StringKeyDosha.GANDANTA_SEV_SEVERE
            MODERATE -> StringKeyDosha.GANDANTA_SEV_MODERATE
            MILD -> StringKeyDosha.GANDANTA_SEV_MILD
        }, language)
    }
}

enum class GandantaType {
    BRAHMA_GANDANTA,  // Cancer-Leo junction
    VISHNU_GANDANTA,  // Scorpio-Sagittarius junction
    SHIVA_GANDANTA    // Pisces-Aries junction
}

data class GandantaPoint(
    val waterSign: ZodiacSign,
    val fireSign: ZodiacSign,
    val orbBefore: Double,
    val orbAfter: Double
)

data class PushkaraNavamsaResult(
    val planet: Planet,
    val sign: ZodiacSign,
    val degree: Double,
    val isInPushkaraNavamsa: Boolean,
    val pushkaraRange: Pair<Double, Double>?,
    val benefits: List<String>
)

data class PushkaraBhagaResult(
    val planet: Planet,
    val sign: ZodiacSign,
    val actualDegree: Double,
    val pushkaraBhagaDegree: Double,
    val distance: Double,
    val isInPushkaraBhaga: Boolean,
    val benefits: List<String>
)

data class TransitVulnerabilityResult(
    val transitingPlanet: Planet,
    val transitSign: ZodiacSign,
    val transitDegree: Double,
    val mrityuBhagaDegree: Double,
    val distanceFromMrityuBhaga: Double,
    val isVulnerablePeriod: Boolean,
    val natalPlanetsAffected: List<Planet>,
    val cautionLevel: CautionLevel,
    val recommendations: List<String>
)

enum class CautionLevel {
    NONE, LOW, MODERATE, HIGH
}

data class OverallSensitiveDegreesAssessment(
    val level: AssessmentLevel,
    val criticalPlacementCount: Int,
    val auspiciousPlacementCount: Int,
    val summary: String
)

enum class AssessmentLevel {
    NEEDS_ATTENTION, MODERATE_CONCERN, BALANCED, GENERALLY_POSITIVE, HIGHLY_AUSPICIOUS
}
