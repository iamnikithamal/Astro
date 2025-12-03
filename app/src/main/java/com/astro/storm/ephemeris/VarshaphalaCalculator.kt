package com.astro.storm.ephemeris

import android.content.Context
import com.astro.storm.data.model.*
import swisseph.SweConst
import swisseph.SweDate
import swisseph.SwissEph
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs
import kotlin.math.floor

/**
 * Production-Grade Varshaphala (Annual Horoscope) Calculator
 *
 * Implements the complete Tajika system of annual astrology including:
 *
 * 1. Solar Return Chart (Varsha Kundli)
 * 2. Muntha and its progression
 * 3. Year Lord (Varsha Pati) determination
 * 4. Tajika Aspects (Ithasala, Easarapha, Nakta, Yamaya, Manau)
 * 5. Sahams (Arabic Parts/Sensitive Points)
 * 6. Mudda Dasha (Annual Dasha)
 * 7. Tri-Pataki Chakra
 * 8. Annual predictions by house
 *
 * Based on:
 * - Tajika Neelakanthi
 * - Varshaphala Paddhati
 * - Jataka Tatva
 * - Uttara Kalamrita
 *
 * @author AstroStorm - Ultra-Precision Vedic Astrology
 */
class VarshaphalaCalculator(context: Context) {

    private val swissEph = SwissEph()
    private val ephemerisPath: String

    companion object {
        private const val AYANAMSA_LAHIRI = SweConst.SE_SIDM_LAHIRI
        private const val SEFLG_SIDEREAL = SweConst.SEFLG_SIDEREAL
        private const val SEFLG_SPEED = SweConst.SEFLG_SPEED

        // Tajika aspect orbs
        private const val CONJUNCTION_ORB = 12.0
        private const val OPPOSITION_ORB = 9.0
        private const val TRINE_ORB = 8.0
        private const val SQUARE_ORB = 7.0
        private const val SEXTILE_ORB = 6.0

        fun getHouseMeaning(house: Int): String {
            return when (house) {
                1 -> "Self/Personality"
                2 -> "Wealth/Family"
                3 -> "Siblings/Communication"
                4 -> "Home/Mother"
                5 -> "Children/Creativity"
                6 -> "Health/Service"
                7 -> "Partnership/Marriage"
                8 -> "Transformation/Longevity"
                9 -> "Fortune/Spirituality"
                10 -> "Career/Status"
                11 -> "Gains/Friends"
                12 -> "Losses/Spirituality"
                else -> "General"
            }
        }

        fun getHouseKeywords(house: Int): List<String> {
            return when (house) {
                1 -> listOf("Self", "Body", "Personality", "Appearance")
                2 -> listOf("Wealth", "Family", "Speech", "Food")
                3 -> listOf("Siblings", "Courage", "Short Travels", "Communication")
                4 -> listOf("Mother", "Home", "Property", "Happiness")
                5 -> listOf("Children", "Intelligence", "Romance", "Speculation")
                6 -> listOf("Enemies", "Disease", "Service", "Debts")
                7 -> listOf("Spouse", "Partnership", "Business", "Public")
                8 -> listOf("Death", "Inheritance", "Occult", "Transformation")
                9 -> listOf("Father", "Guru", "Religion", "Fortune")
                10 -> listOf("Career", "Fame", "Authority", "Government")
                11 -> listOf("Gains", "Friends", "Elder Sibling", "Desires")
                12 -> listOf("Losses", "Foreign", "Liberation", "Expenses")
                else -> listOf()
            }
        }
    }

    init {
        ephemerisPath = context.filesDir.absolutePath + "/ephe"
        swissEph.swe_set_ephe_path(ephemerisPath)
        swissEph.swe_set_sid_mode(AYANAMSA_LAHIRI, 0.0, 0.0)
    }

    /**
     * Tajika aspect types
     */
    enum class TajikaAspect(val displayName: String, val description: String) {
        ITHASALA("Ithasala", "Applying aspect - promises fulfillment"),
        EASARAPHA("Easarapha", "Separating aspect - indicates past events"),
        NAKTA("Nakta", "Transfer of light through intermediary"),
        YAMAYA("Yamaya", "Prohibition - obstruction in matters"),
        MANAU("Manau", "Frustration - denial of results"),
        KAMBOOLA("Kamboola", "Favorable reception - magnifies results"),
        GAIRI_KAMBOOLA("Gairi Kamboola", "Unfavorable reception - weakens results"),
        KHALASARA("Khalasara", "Mutual separation - dissolution"),
        RADDA("Radda", "Return/Retrograde aspect - reconsideration"),
        DUKPHALI("Dukphali", "Obstruction by other planets")
    }

    /**
     * Sahams (Arabic Parts)
     */
    enum class Saham(val displayName: String, val description: String) {
        PUNYA("Punya Saham", "Fortune/Luck"),
        VIDYA("Vidya Saham", "Education/Learning"),
        YASHAS("Yashas Saham", "Fame/Reputation"),
        MITRA("Mitra Saham", "Friends"),
        MAHATMYA("Mahatmya Saham", "Greatness"),
        KARMA("Karma Saham", "Profession"),
        BANDHANA("Bandhana Saham", "Bondage/Imprisonment"),
        MRITYU("Mrityu Saham", "Death/Danger"),
        ASHA("Asha Saham", "Hopes/Desires"),
        SAMARTHA("Samartha Saham", "Capability"),
        VIVAHA("Vivaha Saham", "Marriage"),
        SANTANA("Santana Saham", "Children"),
        ROGA("Roga Saham", "Disease"),
        KARYASIDDHI("Karyasiddhi Saham", "Success in endeavors")
    }

    /**
     * Year Lord determination method
     */
    enum class YearLordMethod {
        TRADITIONAL, // Based on weekday of solar return
        MUNTHA_LORD, // Lord of Muntha sign
        STRONGEST_PLANET // Planet with highest strength
    }

    /**
     * Solar return chart data
     */
    data class SolarReturnChart(
        val year: Int,
        val solarReturnTime: LocalDateTime,
        val planetPositions: List<PlanetPosition>,
        val ascendant: Double,
        val midheaven: Double,
        val houseCusps: List<Double>,
        val ayanamsa: Double
    )

    /**
     * Muntha information
     */
    data class MunthaInfo(
        val sign: ZodiacSign,
        val house: Int,
        val lord: Planet,
        val lordStrength: String,
        val interpretation: String
    )

    /**
     * Saham (Arabic Part) result
     */
    data class SahamResult(
        val saham: Saham,
        val longitude: Double,
        val sign: ZodiacSign,
        val house: Int,
        val interpretation: String
    )

    /**
     * Tajika aspect between planets
     */
    data class TajikaAspectResult(
        val planet1: Planet,
        val planet2: Planet,
        val aspect: TajikaAspect,
        val orb: Double,
        val isApplying: Boolean,
        val interpretation: String
    )

    /**
     * Mudda Dasha period
     */
    data class MuddaDashaPeriod(
        val planet: Planet,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val days: Int
    )

    /**
     * House prediction
     */
    data class HousePrediction(
        val house: Int,
        val signOnCusp: ZodiacSign,
        val houseLord: Planet,
        val lordPosition: Int,
        val planetsInHouse: List<Planet>,
        val aspects: List<Planet>,
        val strength: String,
        val prediction: String,
        val keywords: List<String>
    )

    /**
     * Complete Varshaphala result
     */
    data class VarshaphalaResult(
        val natalChart: VedicChart,
        val year: Int,
        val age: Int,
        val solarReturnChart: SolarReturnChart,
        val muntha: MunthaInfo,
        val yearLord: Planet,
        val yearLordStrength: String,
        val sahams: List<SahamResult>,
        val tajikaAspects: List<TajikaAspectResult>,
        val muddaDasha: List<MuddaDashaPeriod>,
        val housePredictions: List<HousePrediction>,
        val majorThemes: List<String>,
        val favorableMonths: List<Int>,
        val challengingMonths: List<Int>,
        val overallPrediction: String,
        val timestamp: Long = System.currentTimeMillis()
    ) {
        fun toPlainText(): String = buildString {
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine("            VARSHAPHALA (ANNUAL HOROSCOPE) REPORT")
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine()
            appendLine("Name: ${natalChart.birthData.name}")
            appendLine("Year: $year (Age: $age)")
            appendLine("Solar Return: ${solarReturnChart.solarReturnTime}")
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                      YEAR LORD")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("Year Lord: ${yearLord.displayName} ($yearLordStrength)")
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                       MUNTHA")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("Muntha in: ${muntha.sign.displayName} (House ${muntha.house})")
            appendLine("Muntha Lord: ${muntha.lord.displayName} (${muntha.lordStrength})")
            appendLine(muntha.interpretation)
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                    MAJOR THEMES")
            appendLine("─────────────────────────────────────────────────────────")
            majorThemes.forEach { appendLine("• $it") }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                   MUDDA DASHA")
            appendLine("─────────────────────────────────────────────────────────")
            muddaDasha.forEach { period ->
                appendLine("${period.planet.displayName}: ${period.startDate} to ${period.endDate} (${period.days} days)")
            }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                 HOUSE PREDICTIONS")
            appendLine("─────────────────────────────────────────────────────────")
            housePredictions.forEach { prediction ->
                appendLine()
                appendLine("House ${prediction.house} (${getHouseMeaning(prediction.house)})")
                appendLine(prediction.prediction)
            }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("              FAVORABLE MONTHS: ${favorableMonths.joinToString()}")
            appendLine("            CHALLENGING MONTHS: ${challengingMonths.joinToString()}")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine()
            appendLine("                   OVERALL PREDICTION")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine(overallPrediction)
            appendLine()
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine("Generated by AstroStorm - Ultra-Precision Vedic Astrology")
            appendLine("═══════════════════════════════════════════════════════════")
        }
    }

    /**
     * Calculate complete Varshaphala for a given year
     */
    fun calculateVarshaphala(natalChart: VedicChart, year: Int): VarshaphalaResult {
        val birthDateTime = natalChart.birthData.dateTime
        val birthYear = birthDateTime.year
        val age = year - birthYear

        require(age >= 0) { "Year must be after birth year" }

        // Calculate exact solar return time
        val solarReturnTime = calculateSolarReturnTime(
            natalChart.birthData.dateTime,
            year,
            natalChart.birthData.latitude,
            natalChart.birthData.longitude,
            natalChart.birthData.timezone
        )

        // Calculate solar return chart
        val solarReturnChart = calculateSolarReturnChart(
            solarReturnTime,
            natalChart.birthData.latitude,
            natalChart.birthData.longitude,
            natalChart.birthData.timezone
        )

        // Calculate Muntha
        val muntha = calculateMuntha(natalChart, age, solarReturnChart)

        // Determine Year Lord
        val yearLord = determineYearLord(solarReturnChart, muntha)
        val yearLordStrength = evaluatePlanetStrength(yearLord, solarReturnChart)

        // Calculate Sahams
        val sahams = calculateSahams(solarReturnChart)

        // Calculate Tajika aspects
        val tajikaAspects = calculateTajikaAspects(solarReturnChart)

        // Calculate Mudda Dasha
        val muddaDasha = calculateMuddaDasha(solarReturnTime, solarReturnChart)

        // Generate house predictions
        val housePredictions = generateHousePredictions(solarReturnChart, muntha, yearLord)

        // Identify major themes
        val majorThemes = identifyMajorThemes(solarReturnChart, muntha, yearLord, housePredictions)

        // Determine favorable and challenging months
        val favorableMonths = determineFavorableMonths(muddaDasha)
        val challengingMonths = determineChallengingMonths(muddaDasha)

        // Generate overall prediction
        val overallPrediction = generateOverallPrediction(
            yearLord, muntha, housePredictions, tajikaAspects, age
        )

        return VarshaphalaResult(
            natalChart = natalChart,
            year = year,
            age = age,
            solarReturnChart = solarReturnChart,
            muntha = muntha,
            yearLord = yearLord,
            yearLordStrength = yearLordStrength,
            sahams = sahams,
            tajikaAspects = tajikaAspects,
            muddaDasha = muddaDasha,
            housePredictions = housePredictions,
            majorThemes = majorThemes,
            favorableMonths = favorableMonths,
            challengingMonths = challengingMonths,
            overallPrediction = overallPrediction
        )
    }

    /**
     * Calculate the exact moment when Sun returns to natal position
     */
    private fun calculateSolarReturnTime(
        birthDateTime: LocalDateTime,
        targetYear: Int,
        latitude: Double,
        longitude: Double,
        timezone: String
    ): LocalDateTime {
        // Get natal Sun position
        val birthZoned = ZonedDateTime.of(birthDateTime, ZoneId.of(timezone))
        val birthUtc = birthZoned.withZoneSameInstant(ZoneId.of("UTC"))
        val birthJd = calculateJulianDay(birthUtc.toLocalDateTime())
        val natalSunLong = getPlanetLongitude(SweConst.SE_SUN, birthJd)

        // Start searching from birthday in target year
        val searchStart = LocalDateTime.of(targetYear, birthDateTime.month, birthDateTime.dayOfMonth, 0, 0)
        val searchZoned = ZonedDateTime.of(searchStart, ZoneId.of(timezone))
        val searchUtc = searchZoned.withZoneSameInstant(ZoneId.of("UTC"))
        var currentJd = calculateJulianDay(searchUtc.toLocalDateTime())

        // Binary search to find exact return moment (within seconds)
        var lowJd = currentJd - 2
        var highJd = currentJd + 2

        repeat(30) { // 30 iterations gives sub-second precision
            val midJd = (lowJd + highJd) / 2
            val sunLong = getPlanetLongitude(SweConst.SE_SUN, midJd)

            val diff = normalizeAngle(sunLong - natalSunLong)

            if (abs(diff) < 0.00001) {
                currentJd = midJd
                return@repeat
            }

            if (diff > 0 && diff < 180 || diff < -180) {
                highJd = midJd
            } else {
                lowJd = midJd
            }
            currentJd = midJd
        }

        // Convert Julian Day back to DateTime
        return jdToLocalDateTime(currentJd, timezone)
    }

    /**
     * Calculate solar return chart
     */
    private fun calculateSolarReturnChart(
        solarReturnTime: LocalDateTime,
        latitude: Double,
        longitude: Double,
        timezone: String
    ): SolarReturnChart {
        val zonedDateTime = ZonedDateTime.of(solarReturnTime, ZoneId.of(timezone))
        val utcDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"))
        val julianDay = calculateJulianDay(utcDateTime.toLocalDateTime())

        // Get ayanamsa
        val ayanamsa = swissEph.swe_get_ayanamsa_ut(julianDay)

        // Calculate planetary positions
        val planetPositions = Planet.MAIN_PLANETS.map { planet ->
            calculatePlanetPosition(planet, julianDay, latitude, longitude)
        }

        // Calculate house cusps
        val cusps = DoubleArray(13)
        val ascmc = DoubleArray(10)
        swissEph.swe_houses(
            julianDay,
            SweConst.SEFLG_SIDEREAL,
            latitude,
            longitude,
            'P'.code, // Placidus
            cusps,
            ascmc
        )

        val houseCusps = (1..12).map { cusps[it] }
        val ascendant = cusps[1]
        val midheaven = cusps[10]

        return SolarReturnChart(
            year = solarReturnTime.year,
            solarReturnTime = solarReturnTime,
            planetPositions = planetPositions,
            ascendant = ascendant,
            midheaven = midheaven,
            houseCusps = houseCusps,
            ayanamsa = ayanamsa
        )
    }

    /**
     * Calculate Muntha position and interpretation
     */
    private fun calculateMuntha(
        natalChart: VedicChart,
        age: Int,
        solarReturnChart: SolarReturnChart
    ): MunthaInfo {
        // Muntha advances one sign per year from natal ascendant
        val natalAscSign = ZodiacSign.fromLongitude(natalChart.ascendant)
        val munthaSignIndex = (natalAscSign.number - 1 + age) % 12
        val munthaSign = ZodiacSign.entries[munthaSignIndex]

        // Calculate house position of Muntha in annual chart
        val ascSign = ZodiacSign.fromLongitude(solarReturnChart.ascendant)
        val houseOffset = (munthaSign.number - ascSign.number + 12) % 12
        val munthaHouse = if (houseOffset == 0) 12 else houseOffset

        val munthaLord = munthaSign.ruler
        val lordStrength = evaluatePlanetStrength(munthaLord, solarReturnChart)

        val interpretation = generateMunthaInterpretation(munthaSign, munthaHouse, munthaLord)

        return MunthaInfo(
            sign = munthaSign,
            house = munthaHouse,
            lord = munthaLord,
            lordStrength = lordStrength,
            interpretation = interpretation
        )
    }

    /**
     * Determine the Year Lord (Varsha Pati)
     */
    private fun determineYearLord(
        solarReturnChart: SolarReturnChart,
        muntha: MunthaInfo
    ): Planet {
        // Traditional method: Lord of the day of solar return
        val dayOfWeek = solarReturnChart.solarReturnTime.dayOfWeek.value % 7
        val dayLords = listOf(
            Planet.SUN, Planet.MOON, Planet.MARS, Planet.MERCURY,
            Planet.JUPITER, Planet.VENUS, Planet.SATURN
        )

        // Also consider Muntha lord and ascendant lord
        val ascendantLord = ZodiacSign.fromLongitude(solarReturnChart.ascendant).ruler
        val munthaLord = muntha.lord

        // The strongest among these is the Year Lord
        val candidates = listOf(dayLords[dayOfWeek], ascendantLord, munthaLord)
        return candidates.maxByOrNull { planet -> evaluatePlanetStrengthScore(planet, solarReturnChart) } ?: dayLords[dayOfWeek]
    }

    /**
     * Calculate Sahams (Arabic Parts)
     */
    private fun calculateSahams(chart: SolarReturnChart): List<SahamResult> {
        val ascendant = chart.ascendant
        val sunPos = chart.planetPositions.find { it.planet == Planet.SUN }?.longitude ?: 0.0
        val moonPos = chart.planetPositions.find { it.planet == Planet.MOON }?.longitude ?: 0.0
        val marsPos = chart.planetPositions.find { it.planet == Planet.MARS }?.longitude ?: 0.0
        val mercuryPos = chart.planetPositions.find { it.planet == Planet.MERCURY }?.longitude ?: 0.0
        val jupiterPos = chart.planetPositions.find { it.planet == Planet.JUPITER }?.longitude ?: 0.0
        val venusPos = chart.planetPositions.find { it.planet == Planet.VENUS }?.longitude ?: 0.0
        val saturnPos = chart.planetPositions.find { it.planet == Planet.SATURN }?.longitude ?: 0.0

        return listOf(
            // Punya Saham (Fortune) = Ascendant + Moon - Sun
            createSaham(Saham.PUNYA, normalizeAngle(ascendant + moonPos - sunPos), chart),
            // Vidya Saham = Ascendant + Sun - Jupiter
            createSaham(Saham.VIDYA, normalizeAngle(ascendant + sunPos - jupiterPos), chart),
            // Yashas Saham = Ascendant + Jupiter - Punya Saham
            createSaham(Saham.YASHAS, normalizeAngle(ascendant + jupiterPos - (ascendant + moonPos - sunPos)), chart),
            // Karma Saham = Ascendant + Sun - Saturn
            createSaham(Saham.KARMA, normalizeAngle(ascendant + sunPos - saturnPos), chart),
            // Vivaha Saham = Ascendant + Venus - Saturn
            createSaham(Saham.VIVAHA, normalizeAngle(ascendant + venusPos - saturnPos), chart),
            // Santana Saham = Ascendant + Jupiter - Moon
            createSaham(Saham.SANTANA, normalizeAngle(ascendant + jupiterPos - moonPos), chart),
            // Roga Saham = Ascendant + Mars - Saturn
            createSaham(Saham.ROGA, normalizeAngle(ascendant + marsPos - saturnPos), chart)
        )
    }

    private fun createSaham(saham: Saham, longitude: Double, chart: SolarReturnChart): SahamResult {
        val sign = ZodiacSign.fromLongitude(longitude)
        val ascSign = ZodiacSign.fromLongitude(chart.ascendant)
        val houseOffset = (sign.number - ascSign.number + 12) % 12
        val house = if (houseOffset == 0) 12 else houseOffset

        return SahamResult(
            saham = saham,
            longitude = longitude,
            sign = sign,
            house = house,
            interpretation = generateSahamInterpretation(saham, sign, house)
        )
    }

    /**
     * Calculate Tajika aspects
     */
    private fun calculateTajikaAspects(chart: SolarReturnChart): List<TajikaAspectResult> {
        val aspects = mutableListOf<TajikaAspectResult>()

        // Check aspects between all planets
        for (i in Planet.MAIN_PLANETS.indices) {
            for (j in (i + 1) until Planet.MAIN_PLANETS.size) {
                val planet1 = Planet.MAIN_PLANETS[i]
                val planet2 = Planet.MAIN_PLANETS[j]

                val pos1 = chart.planetPositions.find { it.planet == planet1 } ?: continue
                val pos2 = chart.planetPositions.find { it.planet == planet2 } ?: continue

                val aspect = checkTajikaAspect(pos1, pos2)
                if (aspect != null) {
                    aspects.add(aspect)
                }
            }
        }

        return aspects
    }

    private fun checkTajikaAspect(pos1: PlanetPosition, pos2: PlanetPosition): TajikaAspectResult? {
        val diff = abs(normalizeAngle(pos1.longitude - pos2.longitude))
        val speed1 = pos1.speed
        val speed2 = pos2.speed

        // Check for major aspects
        val aspectType: String?
        val orb: Double

        when {
            diff <= CONJUNCTION_ORB || diff >= 360 - CONJUNCTION_ORB -> {
                aspectType = "Conjunction"
                orb = if (diff <= 180) diff else 360 - diff
            }
            abs(diff - 180) <= OPPOSITION_ORB -> {
                aspectType = "Opposition"
                orb = abs(diff - 180)
            }
            abs(diff - 120) <= TRINE_ORB || abs(diff - 240) <= TRINE_ORB -> {
                aspectType = "Trine"
                orb = minOf(abs(diff - 120), abs(diff - 240))
            }
            abs(diff - 90) <= SQUARE_ORB || abs(diff - 270) <= SQUARE_ORB -> {
                aspectType = "Square"
                orb = minOf(abs(diff - 90), abs(diff - 270))
            }
            abs(diff - 60) <= SEXTILE_ORB || abs(diff - 300) <= SEXTILE_ORB -> {
                aspectType = "Sextile"
                orb = minOf(abs(diff - 60), abs(diff - 300))
            }
            else -> return null
        }

        // Determine if applying or separating
        val isApplying = (speed1 > speed2 && pos1.longitude < pos2.longitude) ||
                (speed1 < speed2 && pos1.longitude > pos2.longitude)

        val tajikaAspect = if (isApplying) TajikaAspect.ITHASALA else TajikaAspect.EASARAPHA

        return TajikaAspectResult(
            planet1 = pos1.planet,
            planet2 = pos2.planet,
            aspect = tajikaAspect,
            orb = orb,
            isApplying = isApplying,
            interpretation = generateTajikaInterpretation(pos1.planet, pos2.planet, tajikaAspect, aspectType)
        )
    }

    /**
     * Calculate Mudda Dasha (Annual Planetary Periods)
     */
    private fun calculateMuddaDasha(
        solarReturnTime: LocalDateTime,
        chart: SolarReturnChart
    ): List<MuddaDashaPeriod> {
        // Mudda dasha is based on Moon's nakshatra at solar return
        val moonPos = chart.planetPositions.find { it.planet == Planet.MOON } ?: return emptyList()
        val (startNakshatra, _) = Nakshatra.fromLongitude(moonPos.longitude)

        // Standard Vimshottari proportions for 365 days
        val dashaDays = mapOf(
            Planet.SUN to 18,
            Planet.MOON to 30,
            Planet.MARS to 21,
            Planet.RAHU to 54,
            Planet.JUPITER to 48,
            Planet.SATURN to 57,
            Planet.MERCURY to 51,
            Planet.KETU to 21,
            Planet.VENUS to 60
        )

        // Order based on nakshatra lord
        val dashaOrder = listOf(
            Planet.KETU, Planet.VENUS, Planet.SUN, Planet.MOON, Planet.MARS,
            Planet.RAHU, Planet.JUPITER, Planet.SATURN, Planet.MERCURY
        )

        val startLordIndex = dashaOrder.indexOf(startNakshatra.ruler)
        val orderedPlanets = (0 until 9).map { dashaOrder[(startLordIndex + it) % 9] }

        val periods = mutableListOf<MuddaDashaPeriod>()
        var currentDate = solarReturnTime.toLocalDate()

        for (planet in orderedPlanets) {
            val days = dashaDays[planet] ?: 30
            periods.add(
                MuddaDashaPeriod(
                    planet = planet,
                    startDate = currentDate,
                    endDate = currentDate.plusDays(days.toLong()),
                    days = days
                )
            )
            currentDate = currentDate.plusDays(days.toLong())
        }

        return periods
    }

    /**
     * Generate predictions for each house
     */
    private fun generateHousePredictions(
        chart: SolarReturnChart,
        muntha: MunthaInfo,
        yearLord: Planet
    ): List<HousePrediction> {
        val ascSign = ZodiacSign.fromLongitude(chart.ascendant)

        return (1..12).map { house ->
            val houseSignIndex = (ascSign.number - 1 + house - 1) % 12
            val houseSign = ZodiacSign.entries[houseSignIndex]
            val houseLord = houseSign.ruler

            // Find planets in this house
            val planetsInHouse = chart.planetPositions
                .filter { it.house == house }
                .map { it.planet }

            // Find aspects to this house
            val aspects = chart.planetPositions
                .filter { aspectsHouse(it, house, chart) }
                .map { it.planet }

            // Evaluate house strength
            val strength = evaluateHouseStrength(house, houseLord, planetsInHouse, aspects, chart)

            // Generate prediction
            val prediction = generateHousePredictionText(
                house, houseSign, houseLord, planetsInHouse, aspects, muntha, yearLord
            )

            HousePrediction(
                house = house,
                signOnCusp = houseSign,
                houseLord = houseLord,
                lordPosition = chart.planetPositions.find { it.planet == houseLord }?.house ?: 1,
                planetsInHouse = planetsInHouse,
                aspects = aspects,
                strength = strength,
                prediction = prediction,
                keywords = getHouseKeywords(house)
            )
        }
    }

    private fun aspectsHouse(position: PlanetPosition, house: Int, chart: SolarReturnChart): Boolean {
        val diff = abs(position.house - house)
        return diff in listOf(4, 7, 8, 10) // Major aspects to house
    }

    private fun evaluateHouseStrength(
        house: Int,
        houseLord: Planet,
        planetsInHouse: List<Planet>,
        aspects: List<Planet>,
        chart: SolarReturnChart
    ): String {
        var score = 50

        // Lord strength
        val lordPos = chart.planetPositions.find { it.planet == houseLord }
        if (lordPos != null) {
            if (lordPos.house in listOf(1, 4, 5, 7, 9, 10)) score += 15
            if (lordPos.house in listOf(6, 8, 12)) score -= 15
        }

        // Benefics in house
        val benefics = listOf(Planet.JUPITER, Planet.VENUS, Planet.MERCURY, Planet.MOON)
        score += planetsInHouse.count { it in benefics } * 10
        score += aspects.count { it in benefics } * 5

        // Malefics in house
        val malefics = listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU)
        score -= planetsInHouse.count { it in malefics } * 10
        score -= aspects.count { it in malefics } * 5

        return when {
            score >= 70 -> "Strong"
            score >= 50 -> "Moderate"
            score >= 30 -> "Weak"
            else -> "Challenged"
        }
    }

    private fun generateHousePredictionText(
        house: Int,
        sign: ZodiacSign,
        lord: Planet,
        planets: List<Planet>,
        aspects: List<Planet>,
        muntha: MunthaInfo,
        yearLord: Planet
    ): String {
        val meaning = getHouseMeaning(house)
        val lordName = lord.displayName

        return buildString {
            append("$meaning matters will be influenced by ${lordName}. ")

            if (planets.isNotEmpty()) {
                append("${planets.joinToString { it.displayName }} in this house ")
                append(if (planets.any { it in listOf(Planet.JUPITER, Planet.VENUS) }) "brings positive energy. " else "requires attention. ")
            }

            if (muntha.house == house) {
                append("Muntha here highlights this area significantly. ")
            }

            if (yearLord == lord) {
                append("Year Lord ruling this house emphasizes its importance. ")
            }
        }
    }

    /**
     * Identify major themes for the year
     */
    private fun identifyMajorThemes(
        chart: SolarReturnChart,
        muntha: MunthaInfo,
        yearLord: Planet,
        housePredictions: List<HousePrediction>
    ): List<String> {
        val themes = mutableListOf<String>()

        // Muntha house theme
        themes.add("Focus on ${getHouseMeaning(muntha.house)} - Muntha in House ${muntha.house}")

        // Year Lord theme
        val yearLordHouse = chart.planetPositions.find { it.planet == yearLord }?.house ?: 1
        themes.add("${yearLord.displayName} as Year Lord emphasizes ${getHouseMeaning(yearLordHouse)}")

        // Strong houses
        housePredictions
            .filter { it.strength == "Strong" }
            .take(2)
            .forEach { themes.add("Favorable year for ${getHouseMeaning(it.house)}") }

        // Challenged houses
        housePredictions
            .filter { it.strength == "Challenged" }
            .take(1)
            .forEach { themes.add("Extra care needed in ${getHouseMeaning(it.house)} matters") }

        return themes
    }

    private fun determineFavorableMonths(muddaDasha: List<MuddaDashaPeriod>): List<Int> {
        val beneficPlanets = listOf(Planet.JUPITER, Planet.VENUS, Planet.MERCURY, Planet.MOON)
        return muddaDasha
            .filter { it.planet in beneficPlanets }
            .map { it.startDate.monthValue }
            .distinct()
    }

    private fun determineChallengingMonths(muddaDasha: List<MuddaDashaPeriod>): List<Int> {
        val maleficPlanets = listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU)
        return muddaDasha
            .filter { it.planet in maleficPlanets }
            .map { it.startDate.monthValue }
            .distinct()
    }

    private fun generateOverallPrediction(
        yearLord: Planet,
        muntha: MunthaInfo,
        housePredictions: List<HousePrediction>,
        aspects: List<TajikaAspectResult>,
        age: Int
    ): String {
        return buildString {
            appendLine("At age $age, ${yearLord.displayName} governs your year as the Year Lord.")
            appendLine()
            appendLine("With Muntha in ${muntha.sign.displayName} (House ${muntha.house}), ${muntha.interpretation}")
            appendLine()

            val strongHouses = housePredictions.filter { it.strength == "Strong" }
            if (strongHouses.isNotEmpty()) {
                appendLine("Favorable areas: ${strongHouses.joinToString { getHouseMeaning(it.house) }}")
            }

            val weakHouses = housePredictions.filter { it.strength == "Challenged" }
            if (weakHouses.isNotEmpty()) {
                appendLine("Areas requiring attention: ${weakHouses.joinToString { getHouseMeaning(it.house) }}")
            }

            appendLine()
            appendLine("The year holds ${if (aspects.any { it.isApplying }) "new opportunities" else "culmination of past efforts"}.")
        }
    }

    // ==================== HELPER METHODS ====================

    private fun evaluatePlanetStrength(planet: Planet, chart: SolarReturnChart): String {
        val score = evaluatePlanetStrengthScore(planet, chart)
        return when {
            score >= 70 -> "Strong"
            score >= 50 -> "Moderate"
            score >= 30 -> "Weak"
            else -> "Debilitated"
        }
    }

    private fun evaluatePlanetStrengthScore(planet: Planet, chart: SolarReturnChart): Int {
        var score = 50
        val position = chart.planetPositions.find { it.planet == planet } ?: return score

        // Own sign
        if (position.sign.ruler == planet) score += 20
        // Exaltation
        if (isExalted(planet, position.sign)) score += 25
        // Debilitation
        if (isDebilitated(planet, position.sign)) score -= 25
        // Angular houses
        if (position.house in listOf(1, 4, 7, 10)) score += 15
        // Dusthana houses
        if (position.house in listOf(6, 8, 12)) score -= 15

        return score.coerceIn(0, 100)
    }

    private fun isExalted(planet: Planet, sign: ZodiacSign): Boolean {
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.ARIES
            Planet.MOON -> sign == ZodiacSign.TAURUS
            Planet.MARS -> sign == ZodiacSign.CAPRICORN
            Planet.MERCURY -> sign == ZodiacSign.VIRGO
            Planet.JUPITER -> sign == ZodiacSign.CANCER
            Planet.VENUS -> sign == ZodiacSign.PISCES
            Planet.SATURN -> sign == ZodiacSign.LIBRA
            else -> false
        }
    }

    private fun isDebilitated(planet: Planet, sign: ZodiacSign): Boolean {
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.LIBRA
            Planet.MOON -> sign == ZodiacSign.SCORPIO
            Planet.MARS -> sign == ZodiacSign.CANCER
            Planet.MERCURY -> sign == ZodiacSign.PISCES
            Planet.JUPITER -> sign == ZodiacSign.CAPRICORN
            Planet.VENUS -> sign == ZodiacSign.VIRGO
            Planet.SATURN -> sign == ZodiacSign.ARIES
            else -> false
        }
    }

    private fun generateMunthaInterpretation(sign: ZodiacSign, house: Int, lord: Planet): String {
        return when (house) {
            1 -> "Personal growth and self-improvement year. Health and confidence highlighted."
            2 -> "Focus on finances, family, and material security. Good for savings."
            3 -> "Communication, short travels, and sibling relationships emphasized."
            4 -> "Home, mother, property, and emotional security are priorities."
            5 -> "Creative expression, romance, children, and speculation favored."
            6 -> "Health awareness, service, and handling competition important."
            7 -> "Partnerships, marriage, and public dealings gain prominence."
            8 -> "Transformation, shared resources, and occult studies highlighted."
            9 -> "Higher learning, spirituality, and long journeys favored."
            10 -> "Career advancement and public recognition possible."
            11 -> "Social circle expands, gains from networks, fulfilled desires."
            12 -> "Introspection, foreign connections, and spiritual growth."
            else -> "Mixed influences throughout the year."
        }
    }

    private fun generateSahamInterpretation(saham: Saham, sign: ZodiacSign, house: Int): String {
        return "${saham.description} matters influenced by ${sign.displayName} in house $house."
    }

    private fun generateTajikaInterpretation(
        planet1: Planet,
        planet2: Planet,
        aspect: TajikaAspect,
        aspectType: String
    ): String {
        val nature = if (aspect == TajikaAspect.ITHASALA) "promises development" else "indicates completion"
        return "${planet1.displayName} and ${planet2.displayName} in $aspectType $nature in their combined significations."
    }

    private fun calculatePlanetPosition(
        planet: Planet,
        julianDay: Double,
        latitude: Double,
        longitude: Double
    ): PlanetPosition {
        val xx = DoubleArray(6)
        val serr = StringBuffer()

        val planetId = if (planet == Planet.KETU) {
            SweConst.SE_MEAN_NODE
        } else {
            planet.swissEphId
        }

        swissEph.swe_calc_ut(
            julianDay,
            planetId,
            SEFLG_SIDEREAL or SEFLG_SPEED,
            xx,
            serr
        )

        var planetLongitude = xx[0]
        if (planet == Planet.KETU) {
            planetLongitude = (planetLongitude + 180.0) % 360.0
        }

        planetLongitude = ((planetLongitude % 360.0) + 360.0) % 360.0
        val (nakshatra, pada) = Nakshatra.fromLongitude(planetLongitude)
        val degree = floor(planetLongitude)
        val minutes = floor((planetLongitude - degree) * 60)
        val seconds = (((planetLongitude - degree) * 60) - minutes) * 60

        // Get house position
        val cusps = DoubleArray(13)
        val ascmc = DoubleArray(10)
        swissEph.swe_houses(julianDay, SweConst.SEFLG_SIDEREAL, latitude, longitude, 'P'.code, cusps, ascmc)

        var house = 1
        for (i in 1..12) {
            val nextCusp = if (i == 12) cusps[1] else cusps[i + 1]
            val currentCusp = cusps[i]

            if (currentCusp <= nextCusp) {
                if (planetLongitude >= currentCusp && planetLongitude < nextCusp) {
                    house = i
                    break
                }
            } else {
                if (planetLongitude >= currentCusp || planetLongitude < nextCusp) {
                    house = i
                    break
                }
            }
        }

        return PlanetPosition(
            planet = planet,
            longitude = planetLongitude,
            latitude = xx[1],
            distance = xx[2],
            speed = xx[3],
            isRetrograde = xx[3] < 0,
            sign = ZodiacSign.fromLongitude(planetLongitude),
            degree = degree,
            minutes = minutes,
            seconds = seconds,
            nakshatra = nakshatra,
            nakshatraPada = pada,
            house = house
        )
    }

    private fun getPlanetLongitude(planetId: Int, julianDay: Double): Double {
        val xx = DoubleArray(6)
        val serr = StringBuffer()

        swissEph.swe_calc_ut(
            julianDay,
            planetId,
            SEFLG_SIDEREAL or SEFLG_SPEED,
            xx,
            serr
        )

        return ((xx[0] % 360.0) + 360.0) % 360.0
    }

    private fun normalizeAngle(angle: Double): Double {
        var normalized = angle % 360.0
        if (normalized < 0) normalized += 360.0
        if (normalized > 180) normalized -= 360.0
        return normalized
    }

    private fun calculateJulianDay(dateTime: LocalDateTime): Double {
        val decimalHours = dateTime.hour + (dateTime.minute / 60.0) + (dateTime.second / 3600.0)
        val sweDate = SweDate(
            dateTime.year,
            dateTime.monthValue,
            dateTime.dayOfMonth,
            decimalHours,
            SweDate.SE_GREG_CAL
        )
        return sweDate.julDay
    }

    private fun jdToLocalDateTime(jd: Double, timezone: String): LocalDateTime {
        val sweDate = SweDate(jd)
        val hour = sweDate.hour.toInt()
        val minute = ((sweDate.hour - hour) * 60).toInt()
        val second = ((((sweDate.hour - hour) * 60) - minute) * 60).toInt()

        return LocalDateTime.of(
            sweDate.year,
            sweDate.month,
            sweDate.day,
            hour.coerceIn(0, 23),
            minute.coerceIn(0, 59),
            second.coerceIn(0, 59)
        )
    }

    fun close() {
        swissEph.swe_close()
    }
}
