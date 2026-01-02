package com.astro.storm.ephemeris

import android.content.Context
import android.util.Log
import com.astro.storm.data.model.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.math.roundToInt

class HoroscopeCalculator(private val context: Context) : AutoCloseable {

    private val ephemerisEngine: SwissEphemerisEngine by lazy {
        SwissEphemerisEngine.getInstance(context)
    }

    private val localizationManager: LocalizationManager by lazy {
        LocalizationManager.getInstance(context)
    }

    private fun getString(key: StringKeyInterface): String = localizationManager.getString(key)
    private fun getString(key: StringKeyInterface, vararg args: Any): String = localizationManager.getString(key, *args)

    private val transitCache = LRUCache<TransitCacheKey, VedicChart>(MAX_TRANSIT_CACHE_SIZE)
    private val dailyHoroscopeCache = LRUCache<DailyHoroscopeCacheKey, DailyHoroscope>(MAX_HOROSCOPE_CACHE_SIZE)
    private val natalDataCache = LRUCache<String, NatalChartCachedData>(MAX_NATAL_CACHE_SIZE)

    private val isClosed = AtomicBoolean(false)

    private data class TransitCacheKey(
        val date: LocalDate,
        val latitudeInt: Int,
        val longitudeInt: Int,
        val timezone: String
    ) {
        companion object {
            fun from(date: LocalDate, latitude: Double, longitude: Double, timezone: String): TransitCacheKey {
                return TransitCacheKey(
                    date = date,
                    latitudeInt = (latitude * 1000).toInt(),
                    longitudeInt = (longitude * 1000).toInt(),
                    timezone = timezone
                )
            }
        }
    }

    private data class DailyHoroscopeCacheKey(
        val chartId: String,
        val date: LocalDate
    )

    private data class NatalChartCachedData(
        val planetMap: Map<Planet, PlanetPosition>,
        val moonSign: ZodiacSign,
        val moonHouse: Int,
        val ascendantSign: ZodiacSign,
        val ashtakavarga: AshtakavargaCalculator.AshtakavargaAnalysis?,
        val dashaTimeline: DashaCalculator.DashaTimeline
    )

    data class DailyHoroscope(
        val date: LocalDate,
        val theme: String,
        val themeDescription: String,
        val overallEnergy: Int,
        val moonSign: ZodiacSign,
        val moonNakshatra: Nakshatra,
        val activeDasha: String,
        val lifeAreas: List<LifeAreaPrediction>,
        val luckyElements: LuckyElements,
        val planetaryInfluences: List<PlanetaryInfluence>,
        val recommendations: List<String>,
        val cautions: List<String>,
        val affirmation: String
    )

    data class LifeAreaPrediction(
        val area: LifeArea,
        val rating: Int,
        val prediction: String,
        val advice: String
    )

    enum class LifeArea(val displayName: String, val houses: List<Int>) {
        CAREER("Career", listOf(10, 6, 2)),
        LOVE("Love & Relationships", listOf(7, 5, 11)),
        HEALTH("Health & Vitality", listOf(1, 6, 8)),
        FINANCE("Finance & Wealth", listOf(2, 11, 5)),
        FAMILY("Family & Home", listOf(4, 2, 12)),
        SPIRITUALITY("Spiritual Growth", listOf(9, 12, 5))
    }

    data class LuckyElements(
        val number: Int,
        val color: String,
        val direction: String,
        val time: String,
        val gemstone: String
    )

    data class PlanetaryInfluence(
        val planet: Planet,
        val influence: String,
        val strength: Int,
        val isPositive: Boolean
    )

    data class WeeklyHoroscope(
        val startDate: LocalDate,
        val endDate: LocalDate,
        val weeklyTheme: String,
        val weeklyOverview: String,
        val keyDates: List<KeyDate>,
        val weeklyPredictions: Map<LifeArea, String>,
        val dailyHighlights: List<DailyHighlight>,
        val weeklyAdvice: String
    )

    data class KeyDate(
        val date: LocalDate,
        val event: String,
        val significance: String,
        val isPositive: Boolean
    )

    data class DailyHighlight(
        val date: LocalDate,
        val dayOfWeek: String,
        val energy: Int,
        val focus: String,
        val brief: String
    )

    sealed class HoroscopeResult<out T> {
        data class Success<T>(val data: T) : HoroscopeResult<T>()
        data class Error(val message: String, val cause: Throwable? = null) : HoroscopeResult<Nothing>()
    }

    private data class VedhaInfo(
        val hasVedha: Boolean,
        val obstructingPlanet: Planet? = null,
        val obstructingHouse: Int? = null
    )

    private fun ensureNotClosed() {
        if (isClosed.get()) {
            throw IllegalStateException("HoroscopeCalculator has been closed")
        }
    }

    private fun getChartId(chart: VedicChart): String {
        val birthData = chart.birthData
        return "${birthData.name}_${birthData.dateTime}_${birthData.latitude}_${birthData.longitude}"
    }

    private fun getOrComputeNatalData(chart: VedicChart): NatalChartCachedData {
        val chartId = getChartId(chart)
        
        natalDataCache[chartId]?.let { return it }

        val planetMap = chart.planetPositions.associateBy { it.planet }
        val moonPosition = planetMap[Planet.MOON]
        val moonSign = moonPosition?.sign ?: ZodiacSign.ARIES
        val moonHouse = moonPosition?.house ?: 1
        val ascendantSign = ZodiacSign.fromLongitude(chart.ascendant)

        val ashtakavarga = try {
            AshtakavargaCalculator.calculateAshtakavarga(chart)
        } catch (e: Exception) {
            Log.w(TAG, "Ashtakavarga calculation failed", e)
            null
        }

        val dashaTimeline = DashaCalculator.calculateDashaTimeline(chart)

        return NatalChartCachedData(
            planetMap = planetMap,
            moonSign = moonSign,
            moonHouse = moonHouse,
            ascendantSign = ascendantSign,
            ashtakavarga = ashtakavarga,
            dashaTimeline = dashaTimeline
        ).also { natalDataCache[chartId] = it }
    }

    fun calculateDailyHoroscope(chart: VedicChart, date: LocalDate = LocalDate.now()): DailyHoroscope {
        ensureNotClosed()

        val chartId = getChartId(chart)
        val cacheKey = DailyHoroscopeCacheKey(chartId, date)
        dailyHoroscopeCache[cacheKey]?.let { return it }

        val natalData = getOrComputeNatalData(chart)
        val transitChart = getOrCalculateTransitChart(chart.birthData, date)
        val transitPlanetMap = transitChart.planetPositions.associateBy { it.planet }

        val transitMoon = transitPlanetMap[Planet.MOON]
        val moonSign = transitMoon?.sign ?: natalData.moonSign
        val moonNakshatra = transitMoon?.nakshatra ?: Nakshatra.ASHWINI

        val planetaryInfluences = analyzePlanetaryInfluences(
            natalData = natalData,
            transitPlanetMap = transitPlanetMap,
            transitChart = transitChart
        )

        val lifeAreaPredictions = calculateLifeAreaPredictions(
            chart = chart,
            natalData = natalData,
            transitChart = transitChart,
            transitPlanetMap = transitPlanetMap,
            date = date
        )

        val overallEnergy = calculateOverallEnergy(planetaryInfluences, lifeAreaPredictions, natalData.dashaTimeline)
        val (theme, themeDescription) = calculateDailyTheme(transitPlanetMap, natalData.dashaTimeline)
        val luckyElements = calculateLuckyElements(natalData, transitPlanetMap, date)
        val activeDasha = formatActiveDasha(natalData.dashaTimeline)
        val recommendations = generateRecommendations(natalData.dashaTimeline, lifeAreaPredictions, transitPlanetMap)
        val cautions = generateCautions(transitPlanetMap, planetaryInfluences)
        val affirmation = generateAffirmation(natalData.dashaTimeline.currentMahadasha?.planet)

        return DailyHoroscope(
            date = date,
            theme = theme,
            themeDescription = themeDescription,
            overallEnergy = overallEnergy,
            moonSign = moonSign,
            moonNakshatra = moonNakshatra,
            activeDasha = activeDasha,
            lifeAreas = lifeAreaPredictions,
            luckyElements = luckyElements,
            planetaryInfluences = planetaryInfluences,
            recommendations = recommendations,
            cautions = cautions,
            affirmation = affirmation
        ).also { dailyHoroscopeCache[cacheKey] = it }
    }

    fun calculateDailyHoroscopeSafe(chart: VedicChart, date: LocalDate = LocalDate.now()): HoroscopeResult<DailyHoroscope> {
        return try {
            HoroscopeResult.Success(calculateDailyHoroscope(chart, date))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to calculate daily horoscope for $date", e)
            HoroscopeResult.Error("Unable to calculate horoscope for $date: ${e.message}", e)
        }
    }

    fun calculateWeeklyHoroscope(chart: VedicChart, startDate: LocalDate = LocalDate.now()): WeeklyHoroscope {
        ensureNotClosed()

        val endDate = startDate.plusDays(6)
        val natalData = getOrComputeNatalData(chart)

        val dailyHoroscopes = (0 until 7).mapNotNull { dayOffset ->
            val date = startDate.plusDays(dayOffset.toLong())
            try {
                calculateDailyHoroscope(chart, date)
            } catch (e: Exception) {
                Log.w(TAG, "Failed to calculate horoscope for $date", e)
                null
            }
        }

        val dailyHighlights = if (dailyHoroscopes.isNotEmpty()) {
            dailyHoroscopes.map { horoscope ->
                DailyHighlight(
                    date = horoscope.date,
                    dayOfWeek = horoscope.date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() },
                    energy = horoscope.overallEnergy,
                    focus = horoscope.theme,
                    brief = horoscope.themeDescription.take(100).let {
                        if (horoscope.themeDescription.length > 100) "$it..." else it
                    }
                )
            }
        } else {
            (0 until 7).map { dayOffset ->
                val date = startDate.plusDays(dayOffset.toLong())
                DailyHighlight(
                    date = date,
                    dayOfWeek = date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() },
                    energy = 5,
                    focus = getString(StringKeyHoroscope.THEME_BALANCE_EQUILIBRIUM),
                    brief = getString(StringKeyHoroscope.THEME_DESC_BALANCE_EQUILIBRIUM)
                )
            }
        }

        val keyDates = calculateKeyDates(startDate, endDate)
        val weeklyPredictions = calculateWeeklyPredictions(dailyHoroscopes, natalData.dashaTimeline)
        val (weeklyTheme, weeklyOverview) = calculateWeeklyTheme(natalData.dashaTimeline, dailyHighlights)
        val weeklyAdvice = generateWeeklyAdvice(natalData.dashaTimeline, keyDates)

        return WeeklyHoroscope(
            startDate = startDate,
            endDate = endDate,
            weeklyTheme = weeklyTheme,
            weeklyOverview = weeklyOverview,
            keyDates = keyDates,
            weeklyPredictions = weeklyPredictions,
            dailyHighlights = dailyHighlights,
            weeklyAdvice = weeklyAdvice
        )
    }

    fun calculateWeeklyHoroscopeSafe(chart: VedicChart, startDate: LocalDate = LocalDate.now()): HoroscopeResult<WeeklyHoroscope> {
        return try {
            HoroscopeResult.Success(calculateWeeklyHoroscope(chart, startDate))
        } catch (e: Exception) {
            Log.e(TAG, "Failed to calculate weekly horoscope starting $startDate", e)
            HoroscopeResult.Error("Unable to calculate weekly horoscope: ${e.message}", e)
        }
    }

    private fun getOrCalculateTransitChart(birthData: BirthData, date: LocalDate): VedicChart {
        val cacheKey = TransitCacheKey.from(date, birthData.latitude, birthData.longitude, birthData.timezone)

        transitCache[cacheKey]?.let { return it }

        val transitDateTime = LocalDateTime.of(date, LocalTime.of(6, 0))
        val transitBirthData = BirthData(
            name = "Transit",
            dateTime = transitDateTime,
            latitude = birthData.latitude,
            longitude = birthData.longitude,
            timezone = birthData.timezone,
            location = birthData.location
        )

        return try {
            ephemerisEngine.calculateVedicChart(transitBirthData).also {
                transitCache[cacheKey] = it
            }
        } catch (e: Exception) {
            Log.e(TAG, "Transit calculation failed for $date", e)
            throw HoroscopeCalculationException(
                getString(StringKey.ERROR_EPHEMERIS_DATA),
                e
            )
        }
    }

    private fun formatActiveDasha(timeline: DashaCalculator.DashaTimeline): String {
        val md = timeline.currentMahadasha ?: return getString(StringKey.HOROSCOPE_CALCULATING)
        val ad = timeline.currentAntardasha
        return if (ad != null) {
            "${md.planet.displayName}-${ad.planet.displayName}"
        } else {
            md.planet.displayName
        }
    }

    private val transitAnalyzer: TransitAnalyzer by lazy {
        TransitAnalyzer(context)
    }

    private fun analyzePlanetaryInfluences(
        natalData: NatalChartCachedData,
        transitPlanetMap: Map<Planet, PlanetPosition>,
        transitChart: VedicChart
    ): List<PlanetaryInfluence> {
        val analysis = transitAnalyzer.analyzeTransits(
            natalChart = transitChart, // Analyzer expects natal chart but we use transit chart for positions
            language = localizationManager.language.value
        )
        
        return analysis.gocharaResults.map { result ->
            PlanetaryInfluence(
                planet = result.planet,
                influence = result.interpretation,
                strength = result.effect.score * 2, // Map 1-5 to 2-10 for compatibility
                isPositive = result.effect in listOf(TransitAnalyzer.TransitEffect.EXCELLENT, TransitAnalyzer.TransitEffect.GOOD)
            )
        }
    }

    private fun calculateOverallEnergy(
        influences: List<PlanetaryInfluence>,
        lifeAreas: List<LifeAreaPrediction>,
        dashaTimeline: DashaCalculator.DashaTimeline
    ): Int {
        val planetaryAvg = if (influences.isNotEmpty()) {
            influences.sumOf { it.strength }.toDouble() / influences.size
        } else 5.0

        val lifeAreaAvg = if (lifeAreas.isNotEmpty()) {
            lifeAreas.sumOf { it.rating * 2 }.toDouble() / lifeAreas.size
        } else 5.0

        val dashaBonus = DASHA_ENERGY_MODIFIERS[dashaTimeline.currentMahadasha?.planet] ?: 0.0
        val rawEnergy = (planetaryAvg * 0.4) + (lifeAreaAvg * 0.4) + (5.0 + dashaBonus) * 0.2

        return rawEnergy.roundToInt().coerceIn(1, 10)
    }

    private fun calculateDailyTheme(
        transitPlanetMap: Map<Planet, PlanetPosition>,
        dashaTimeline: DashaCalculator.DashaTimeline
    ): Pair<String, String> {
        val moonSign = transitPlanetMap[Planet.MOON]?.sign ?: ZodiacSign.ARIES
        val currentDashaLord = dashaTimeline.currentMahadasha?.planet ?: Planet.SUN

        val (themeKey, descKey) = determineTheme(moonSign, currentDashaLord)
        return Pair(getString(themeKey), getString(descKey))
    }

    private fun determineTheme(moonSign: ZodiacSign, dashaLord: Planet): Pair<StringKeyHoroscope, StringKeyHoroscope> {
        val moonElement = moonSign.element

        return when {
            moonElement == "Fire" && dashaLord in FIRE_PLANETS -> StringKeyHoroscope.THEME_DYNAMIC_ACTION to StringKeyHoroscope.THEME_DESC_DYNAMIC_ACTION
            moonElement == "Earth" && dashaLord in EARTH_PLANETS -> StringKeyHoroscope.THEME_PRACTICAL_PROGRESS to StringKeyHoroscope.THEME_DESC_PRACTICAL_PROGRESS
            moonElement == "Air" && dashaLord in AIR_PLANETS -> StringKeyHoroscope.THEME_SOCIAL_CONNECTIONS to StringKeyHoroscope.THEME_DESC_SOCIAL_CONNECTIONS
            moonElement == "Water" && dashaLord in WATER_PLANETS -> StringKeyHoroscope.THEME_EMOTIONAL_INSIGHT to StringKeyHoroscope.THEME_DESC_EMOTIONAL_INSIGHT
            else -> {
                val theme = DASHA_LORD_THEME_KEYS[dashaLord] ?: StringKeyHoroscope.THEME_BALANCE_EQUILIBRIUM
                val desc = THEME_DESC_KEYS[theme] ?: StringKeyHoroscope.THEME_DESC_BALANCE_EQUILIBRIUM
                theme to desc
            }
        }
    }

    private fun calculateLuckyElements(
        natalData: NatalChartCachedData,
        transitPlanetMap: Map<Planet, PlanetPosition>,
        date: LocalDate
    ): LuckyElements {
        val moonSign = transitPlanetMap[Planet.MOON]?.sign ?: ZodiacSign.ARIES
        val dayOfWeek = date.dayOfWeek.value
        val ascRuler = natalData.ascendantSign.ruler

        val luckyNumber = ((dayOfWeek + moonSign.ordinal) % 9) + 1
        val luckyColor = getString(ELEMENT_COLOR_KEYS[moonSign.element] ?: StringKeyHoroscope.LUCKY_COLOR_EARTH)
        val luckyDirection = getString(PLANET_DIRECTION_KEYS[ascRuler] ?: StringKeyHoroscope.LUCKY_DIR_EAST)
        val luckyTime = getString(DAY_HORA_KEYS[dayOfWeek] ?: StringKeyHoroscope.LUCKY_TIME_MORNING)
        val gemstone = getString(PLANET_GEMSTONE_KEYS[ascRuler] ?: StringKeyMatch.GEMSTONE_RUBY)

        return LuckyElements(
            number = luckyNumber,
            color = luckyColor,
            direction = luckyDirection,
            time = luckyTime,
            gemstone = gemstone
        )
    }

    private fun generateRecommendations(
        dashaTimeline: DashaCalculator.DashaTimeline,
        lifeAreas: List<LifeAreaPrediction>,
        transitPlanetMap: Map<Planet, PlanetPosition>
    ): List<String> {
        val recommendations = ArrayList<String>(3)

        dashaTimeline.currentMahadasha?.planet?.let { dashaLord ->
            DASHA_RECOMMENDATION_KEYS[dashaLord]?.let { recommendations.add(getString(it)) }
        }

        lifeAreas.maxByOrNull { it.rating }?.let { bestArea ->
            BEST_AREA_RECOMMENDATION_KEYS[bestArea.area]?.let { recommendations.add(getString(it)) }
        }

        transitPlanetMap[Planet.MOON]?.let { moon ->
            ELEMENT_RECOMMENDATION_KEYS[moon.sign.element]?.let { recommendations.add(getString(it)) }
        }

        return recommendations.take(3)
    }

    private fun generateCautions(
        transitPlanetMap: Map<Planet, PlanetPosition>,
        influences: List<PlanetaryInfluence>
    ): List<String> {
        val cautions = ArrayList<String>(3)

        influences.asSequence()
            .filter { !it.isPositive && it.strength <= 4 }
            .take(2)
            .forEach { influence ->
                PLANET_CAUTION_KEYS[influence.planet]?.let { cautions.add(getString(it)) }
            }

        transitPlanetMap.values.asSequence()
            .filter { it.isRetrograde && it.planet in Planet.MAIN_PLANETS }
            .take(1)
            .forEach {
                cautions.add(getString(StringKeyHoroscope.CAUTION_RETROGRADE, it.planet.displayName))
            }

        return cautions.take(2)
    }

    private fun generateAffirmation(dashaLord: Planet?): String {
        return getString(DASHA_AFFIRMATION_KEYS[dashaLord] ?: StringKeyHoroscope.AFF_DEFAULT)
    }

    private fun calculateKeyDates(startDate: LocalDate, endDate: LocalDate): List<KeyDate> {
        val keyDates = ArrayList<KeyDate>(6)

        LUNAR_PHASE_CONFIG.forEach { (dayOffset, eventKey, sigKey) ->
            val date = startDate.plusDays(dayOffset.toLong())
            if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
                keyDates.add(KeyDate(date, getString(eventKey), getString(sigKey), true))
            }
        }

        for (offset in 0 until 7) {
            val date = startDate.plusDays(offset.toLong())
            FAVORABLE_DAY_KEYS[date.dayOfWeek]?.let { descKey ->
                keyDates.add(KeyDate(
                    date = date,
                    event = date.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() },
                    significance = getString(descKey),
                    isPositive = true
                ))
            }
        }

        return keyDates.distinctBy { it.date }.take(4)
    }

    private fun calculateWeeklyPredictions(
        dailyHoroscopes: List<DailyHoroscope>,
        dashaTimeline: DashaCalculator.DashaTimeline
    ): Map<LifeArea, String> {
        val currentDashaLord = dashaTimeline.currentMahadasha?.planet?.displayName ?: getString(StringKeyMatch.MISC_UNKNOWN)

        return LifeArea.entries.associateWith { area ->
            val weeklyRatings = dailyHoroscopes.mapNotNull { horoscope ->
                horoscope.lifeAreas.find { it.area == area }?.rating
            }
            val avgRating = if (weeklyRatings.isNotEmpty()) {
                weeklyRatings.sum().toDouble() / weeklyRatings.size
            } else 3.0

            val ratingCategory = when {
                avgRating >= 4 -> "excellent"
                avgRating >= 3 -> "steady"
                else -> "challenging"
            }

            WEEKLY_PREDICTION_KEYS[area]?.get(ratingCategory)?.let { getString(it, currentDashaLord) }
                ?: getString(StringKeyHoroscope.THEME_BALANCE_EQUILIBRIUM)
        }
    }

    private fun calculateWeeklyTheme(
        dashaTimeline: DashaCalculator.DashaTimeline,
        dailyHighlights: List<DailyHighlight>
    ): Pair<String, String> {
        val avgEnergy = if (dailyHighlights.isNotEmpty()) {
            dailyHighlights.sumOf { it.energy }.toDouble() / dailyHighlights.size
        } else 5.0

        val currentDashaLord = dashaTimeline.currentMahadasha?.planet ?: Planet.SUN

        val themeKey = when {
            avgEnergy >= 7 -> StringKeyHoroscope.WEEKLY_THEME_OPPORTUNITY
            avgEnergy >= 5 -> StringKeyHoroscope.WEEKLY_THEME_PROGRESS
            else -> StringKeyHoroscope.WEEKLY_THEME_NAVIGATION
        }

        val overview = buildWeeklyOverview(currentDashaLord, avgEnergy, dailyHighlights)
        return Pair(getString(themeKey), overview)
    }

    private fun buildWeeklyOverview(
        dashaLord: Planet,
        avgEnergy: Double,
        dailyHighlights: List<DailyHighlight>
    ): String {
        val builder = StringBuilder()
        builder.append(getString(StringKeyHoroscope.WEEKLY_OVERVIEW_PREFIX, dashaLord.displayName))

        when {
            avgEnergy >= 7 -> builder.append(getString(StringKeyHoroscope.WEEKLY_OVERVIEW_HIGH))
            avgEnergy >= 5 -> builder.append(getString(StringKeyHoroscope.WEEKLY_OVERVIEW_MED))
            else -> builder.append(getString(StringKeyHoroscope.WEEKLY_OVERVIEW_LOW))
        }

        dailyHighlights.maxByOrNull { it.energy }?.let {
            builder.append(getString(StringKeyHoroscope.WEEKLY_OVERVIEW_FAVORABLE, it.dayOfWeek))
        }

        dailyHighlights.minByOrNull { it.energy }?.let {
            if (it.energy < 5) {
                builder.append(getString(StringKeyHoroscope.WEEKLY_OVERVIEW_CAUTION, it.dayOfWeek))
            }
        }

        builder.append(getString(StringKeyHoroscope.WEEKLY_OVERVIEW_SUFFIX))

        return builder.toString()
    }

    private fun generateWeeklyAdvice(
        dashaTimeline: DashaCalculator.DashaTimeline,
        keyDates: List<KeyDate>
    ): String {
        val currentDashaLord = dashaTimeline.currentMahadasha?.planet ?: Planet.SUN
        val adviceKey = DASHA_WEEKLY_ADVICE_KEYS[currentDashaLord] ?: StringKeyHoroscope.THEME_BALANCE_EQUILIBRIUM
        
        val builder = StringBuilder()
        builder.append(getString(StringKeyHoroscope.WEEKLY_ADVICE_PREFIX, currentDashaLord.displayName))
        builder.append(getString(adviceKey))

        keyDates.firstOrNull { it.isPositive }?.let {
            builder.append(getString(StringKeyHoroscope.WEEKLY_ADVICE_DATE, it.date.format(DATE_FORMATTER)))
        }

        return builder.toString()
    }

    fun clearCache() {
        transitCache.clear()
        dailyHoroscopeCache.clear()
        natalDataCache.clear()
    }

    override fun close() {
        if (isClosed.compareAndSet(false, true)) {
            try {
                clearCache()
                ephemerisEngine.close()
            } catch (e: Exception) {
                Log.w(TAG, "Error closing ephemeris engine", e)
            }
        }
    }

    class HoroscopeCalculationException(message: String, cause: Throwable? = null) : Exception(message, cause)

    private class LRUCache<K, V>(private val maxSize: Int) {
        private val cache = object : LinkedHashMap<K, V>(maxSize, 0.75f, true) {
            override fun removeEldestEntry(eldest: MutableMap.MutableEntry<K, V>?): Boolean {
                return size > maxSize
            }
        }

        @Synchronized
        operator fun get(key: K): V? = cache[key]

        @Synchronized
        operator fun set(key: K, value: V) {
            cache[key] = value
        }

        @Synchronized
        fun clear() = cache.clear()
    }

    companion object {
        private const val TAG = "HoroscopeCalculator"
        private const val MAX_TRANSIT_CACHE_SIZE = 30
        private const val MAX_HOROSCOPE_CACHE_SIZE = 50
        private const val MAX_NATAL_CACHE_SIZE = 10

        private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d")

        private val NATURAL_BENEFICS = setOf(Planet.JUPITER, Planet.VENUS, Planet.MERCURY, Planet.MOON)
        private val NATURAL_MALEFICS = setOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU)

        private val FIRE_PLANETS = setOf(Planet.SUN, Planet.MARS, Planet.JUPITER)
        private val EARTH_PLANETS = setOf(Planet.VENUS, Planet.MERCURY, Planet.SATURN)
        private val AIR_PLANETS = setOf(Planet.MERCURY, Planet.VENUS, Planet.SATURN)
        private val WATER_PLANETS = setOf(Planet.MOON, Planet.MARS, Planet.JUPITER)

        private val PLANET_OWN_SIGNS = mapOf(
            Planet.SUN to setOf(ZodiacSign.LEO),
            Planet.MOON to setOf(ZodiacSign.CANCER),
            Planet.MARS to setOf(ZodiacSign.ARIES, ZodiacSign.SCORPIO),
            Planet.MERCURY to setOf(ZodiacSign.GEMINI, ZodiacSign.VIRGO),
            Planet.JUPITER to setOf(ZodiacSign.SAGITTARIUS, ZodiacSign.PISCES),
            Planet.VENUS to setOf(ZodiacSign.TAURUS, ZodiacSign.LIBRA),
            Planet.SATURN to setOf(ZodiacSign.CAPRICORN, ZodiacSign.AQUARIUS)
        )

        private val PLANET_EXALTATION = mapOf(
            Planet.SUN to ZodiacSign.ARIES,
            Planet.MOON to ZodiacSign.TAURUS,
            Planet.MARS to ZodiacSign.CAPRICORN,
            Planet.MERCURY to ZodiacSign.VIRGO,
            Planet.JUPITER to ZodiacSign.CANCER,
            Planet.VENUS to ZodiacSign.PISCES,
            Planet.SATURN to ZodiacSign.LIBRA
        )

        private val PLANET_DEBILITATION = mapOf(
            Planet.SUN to ZodiacSign.LIBRA,
            Planet.MOON to ZodiacSign.SCORPIO,
            Planet.MARS to ZodiacSign.CANCER,
            Planet.MERCURY to ZodiacSign.PISCES,
            Planet.JUPITER to ZodiacSign.CAPRICORN,
            Planet.VENUS to ZodiacSign.VIRGO,
            Planet.SATURN to ZodiacSign.ARIES
        )

        private val GOCHARA_FAVORABLE_HOUSES = mapOf(
            Planet.SUN to listOf(3, 6, 10, 11),
            Planet.MOON to listOf(1, 3, 6, 7, 10, 11),
            Planet.MARS to listOf(3, 6, 11),
            Planet.MERCURY to listOf(2, 4, 6, 8, 10, 11),
            Planet.JUPITER to listOf(2, 5, 7, 9, 11),
            Planet.VENUS to listOf(1, 2, 3, 4, 5, 8, 9, 11, 12),
            Planet.SATURN to listOf(3, 6, 11),
            Planet.RAHU to listOf(3, 6, 10, 11),
            Planet.KETU to listOf(3, 6, 9, 11)
        )

        private val GOCHARA_VEDHA_PAIRS = mapOf(
            Planet.SUN to mapOf(3 to 9, 6 to 12, 10 to 4, 11 to 5),
            Planet.MOON to mapOf(1 to 5, 3 to 9, 6 to 12, 7 to 2, 10 to 4, 11 to 8),
            Planet.MARS to mapOf(3 to 12, 6 to 9, 11 to 5),
            Planet.MERCURY to mapOf(2 to 5, 4 to 3, 6 to 9, 8 to 1, 10 to 8, 11 to 12),
            Planet.JUPITER to mapOf(2 to 12, 5 to 4, 7 to 3, 9 to 10, 11 to 8),
            Planet.VENUS to mapOf(1 to 8, 2 to 7, 3 to 1, 4 to 10, 5 to 9, 8 to 5, 9 to 11, 11 to 6, 12 to 3),
            Planet.SATURN to mapOf(3 to 12, 6 to 9, 11 to 5),
            Planet.RAHU to mapOf(3 to 12, 6 to 9, 10 to 4, 11 to 5),
            Planet.KETU to mapOf(3 to 12, 6 to 9, 9 to 10, 11 to 5)
        )

        private val GOCHARA_FAVORABLE_KEYS = mapOf(
            Planet.SUN to mapOf(3 to StringKeyHoroscope.GOCHARA_SUN_3, 6 to StringKeyHoroscope.GOCHARA_SUN_6, 10 to StringKeyHoroscope.GOCHARA_SUN_10, 11 to StringKeyHoroscope.GOCHARA_SUN_11),
            Planet.MOON to mapOf(1 to StringKeyHoroscope.GOCHARA_MOON_1, 3 to StringKeyHoroscope.GOCHARA_MOON_3, 6 to StringKeyHoroscope.GOCHARA_MOON_6, 7 to StringKeyHoroscope.GOCHARA_MOON_7, 10 to StringKeyHoroscope.GOCHARA_MOON_10, 11 to StringKeyHoroscope.GOCHARA_MOON_11),
            Planet.MARS to mapOf(3 to StringKeyHoroscope.GOCHARA_MARS_3, 6 to StringKeyHoroscope.GOCHARA_MARS_6, 11 to StringKeyHoroscope.GOCHARA_MARS_11),
            Planet.MERCURY to mapOf(2 to StringKeyHoroscope.GOCHARA_MERCURY_2, 4 to StringKeyHoroscope.GOCHARA_MERCURY_4, 6 to StringKeyHoroscope.GOCHARA_MERCURY_6, 8 to StringKeyHoroscope.GOCHARA_MERCURY_8, 10 to StringKeyHoroscope.GOCHARA_MERCURY_10, 11 to StringKeyHoroscope.GOCHARA_MERCURY_11),
            Planet.JUPITER to mapOf(2 to StringKeyHoroscope.GOCHARA_JUPITER_2, 5 to StringKeyHoroscope.GOCHARA_JUPITER_5, 7 to StringKeyHoroscope.GOCHARA_JUPITER_7, 9 to StringKeyHoroscope.GOCHARA_JUPITER_9, 11 to StringKeyHoroscope.GOCHARA_JUPITER_11),
            Planet.VENUS to mapOf(1 to StringKeyHoroscope.GOCHARA_VENUS_1, 2 to StringKeyHoroscope.GOCHARA_VENUS_2, 3 to StringKeyHoroscope.GOCHARA_VENUS_3, 4 to StringKeyHoroscope.GOCHARA_VENUS_4, 5 to StringKeyHoroscope.GOCHARA_VENUS_5, 8 to StringKeyHoroscope.GOCHARA_VENUS_8, 9 to StringKeyHoroscope.GOCHARA_VENUS_9, 11 to StringKeyHoroscope.GOCHARA_VENUS_11, 12 to StringKeyHoroscope.GOCHARA_VENUS_12),
            Planet.SATURN to mapOf(3 to StringKeyHoroscope.GOCHARA_SATURN_3, 6 to StringKeyHoroscope.GOCHARA_SATURN_6, 11 to StringKeyHoroscope.GOCHARA_SATURN_11),
            Planet.RAHU to mapOf(3 to StringKeyHoroscope.GOCHARA_RAHU_3, 6 to StringKeyHoroscope.GOCHARA_RAHU_6, 10 to StringKeyHoroscope.GOCHARA_RAHU_10, 11 to StringKeyHoroscope.GOCHARA_RAHU_11),
            Planet.KETU to mapOf(3 to StringKeyHoroscope.GOCHARA_KETU_3, 6 to StringKeyHoroscope.GOCHARA_KETU_6, 9 to StringKeyHoroscope.GOCHARA_KETU_9, 11 to StringKeyHoroscope.GOCHARA_KETU_11)
        )

        private val GOCHARA_UNFAVORABLE_KEYS = mapOf(
            Planet.SUN to mapOf(1 to StringKeyHoroscope.GOCHARA_SUN_1, 2 to StringKeyHoroscope.GOCHARA_SUN_2, 4 to StringKeyHoroscope.GOCHARA_SUN_4, 5 to StringKeyHoroscope.GOCHARA_SUN_5, 7 to StringKeyHoroscope.GOCHARA_SUN_7, 8 to StringKeyHoroscope.GOCHARA_SUN_8, 9 to StringKeyHoroscope.GOCHARA_SUN_9, 12 to StringKeyHoroscope.GOCHARA_SUN_12),
            Planet.MOON to mapOf(2 to StringKeyHoroscope.GOCHARA_MOON_2, 4 to StringKeyHoroscope.GOCHARA_MOON_4, 5 to StringKeyHoroscope.GOCHARA_MOON_5, 8 to StringKeyHoroscope.GOCHARA_MOON_8, 9 to StringKeyHoroscope.GOCHARA_MOON_9, 12 to StringKeyHoroscope.GOCHARA_MOON_12),
            Planet.MARS to mapOf(1 to StringKeyHoroscope.GOCHARA_MARS_1, 2 to StringKeyHoroscope.GOCHARA_MARS_2, 4 to StringKeyHoroscope.GOCHARA_MARS_4, 5 to StringKeyHoroscope.GOCHARA_MARS_5, 7 to StringKeyHoroscope.GOCHARA_MARS_7, 8 to StringKeyHoroscope.GOCHARA_MARS_8, 9 to StringKeyHoroscope.GOCHARA_MARS_9, 10 to StringKeyHoroscope.GOCHARA_MARS_10, 12 to StringKeyHoroscope.GOCHARA_MARS_12),
            Planet.MERCURY to mapOf(1 to StringKeyHoroscope.GOCHARA_MERCURY_1, 3 to StringKeyHoroscope.GOCHARA_MERCURY_3, 5 to StringKeyHoroscope.GOCHARA_MERCURY_5, 7 to StringKeyHoroscope.GOCHARA_MERCURY_7, 9 to StringKeyHoroscope.GOCHARA_MERCURY_9, 12 to StringKeyHoroscope.GOCHARA_MERCURY_12),
            Planet.JUPITER to mapOf(1 to StringKeyHoroscope.GOCHARA_JUPITER_1, 3 to StringKeyHoroscope.GOCHARA_JUPITER_3, 4 to StringKeyHoroscope.GOCHARA_JUPITER_4, 6 to StringKeyHoroscope.GOCHARA_JUPITER_6, 8 to StringKeyHoroscope.GOCHARA_JUPITER_8, 10 to StringKeyHoroscope.GOCHARA_JUPITER_10, 12 to StringKeyHoroscope.GOCHARA_JUPITER_12),
            Planet.SATURN to mapOf(1 to StringKeyHoroscope.GOCHARA_SATURN_1, 2 to StringKeyHoroscope.GOCHARA_SATURN_2, 4 to StringKeyHoroscope.GOCHARA_SATURN_4, 5 to StringKeyHoroscope.GOCHARA_SATURN_5, 7 to StringKeyHoroscope.GOCHARA_SATURN_7, 8 to StringKeyHoroscope.GOCHARA_SATURN_8, 9 to StringKeyHoroscope.GOCHARA_SATURN_9, 10 to StringKeyHoroscope.GOCHARA_SATURN_10, 12 to StringKeyHoroscope.GOCHARA_SATURN_12),
            Planet.RAHU to mapOf(1 to StringKeyHoroscope.GOCHARA_RAHU_1, 2 to StringKeyHoroscope.GOCHARA_RAHU_2, 4 to StringKeyHoroscope.GOCHARA_RAHU_4, 5 to StringKeyHoroscope.GOCHARA_RAHU_5, 7 to StringKeyHoroscope.GOCHARA_RAHU_7, 8 to StringKeyHoroscope.GOCHARA_RAHU_8, 9 to StringKeyHoroscope.GOCHARA_RAHU_9, 12 to StringKeyHoroscope.GOCHARA_RAHU_12),
            Planet.KETU to mapOf(1 to StringKeyHoroscope.GOCHARA_KETU_1, 2 to StringKeyHoroscope.GOCHARA_KETU_2, 4 to StringKeyHoroscope.GOCHARA_KETU_4, 5 to StringKeyHoroscope.GOCHARA_KETU_5, 7 to StringKeyHoroscope.GOCHARA_KETU_7, 8 to StringKeyHoroscope.GOCHARA_KETU_8, 10 to StringKeyHoroscope.GOCHARA_KETU_10, 12 to StringKeyHoroscope.GOCHARA_KETU_12)
        )

        private val FAVORABLE_GOCHARA_EFFECTS_DETAILED = mapOf(
            Planet.SUN to mapOf(3 to ("" to 8), 6 to ("" to 8), 10 to ("" to 9), 11 to ("" to 9)),
            Planet.MOON to mapOf(1 to ("" to 8), 3 to ("" to 7), 6 to ("" to 8), 7 to ("" to 8), 10 to ("" to 8), 11 to ("" to 9)),
            Planet.MARS to mapOf(3 to ("" to 8), 6 to ("" to 8), 11 to ("" to 8)),
            Planet.MERCURY to mapOf(2 to ("" to 8), 4 to ("" to 7), 6 to ("" to 8), 8 to ("" to 7), 10 to ("" to 8), 11 to ("" to 8)),
            Planet.JUPITER to mapOf(2 to ("" to 9), 5 to ("" to 9), 7 to ("" to 8), 9 to ("" to 10), 11 to ("" to 9)),
            Planet.VENUS to mapOf(1 to ("" to 8), 2 to ("" to 8), 3 to ("" to 7), 4 to ("" to 8), 5 to ("" to 9), 8 to ("" to 7), 9 to ("" to 8), 11 to ("" to 9), 12 to ("" to 7)),
            Planet.SATURN to mapOf(3 to ("" to 7), 6 to ("" to 8), 11 to ("" to 8)),
            Planet.RAHU to mapOf(3 to ("" to 7), 6 to ("" to 7), 10 to ("" to 8), 11 to ("" to 8)),
            Planet.KETU to mapOf(3 to ("" to 7), 6 to ("" to 7), 9 to ("" to 8), 11 to ("" to 7))
        )

        private val UNFAVORABLE_GOCHARA_EFFECTS_DETAILED = mapOf(
            Planet.SUN to mapOf(1 to ("" to 4), 2 to ("" to 4), 4 to ("" to 4), 5 to ("" to 4), 7 to ("" to 4), 8 to ("" to 3), 9 to ("" to 4), 12 to ("" to 4)),
            Planet.MOON to mapOf(2 to ("" to 4), 4 to ("" to 4), 5 to ("" to 4), 8 to ("" to 3), 9 to ("" to 4), 12 to ("" to 4)),
            Planet.MARS to mapOf(1 to ("" to 4), 2 to ("" to 4), 4 to ("" to 4), 5 to ("" to 4), 7 to ("" to 3), 8 to ("" to 3), 9 to ("" to 4), 10 to ("" to 4), 12 to ("" to 3)),
            Planet.MERCURY to mapOf(1 to ("" to 4), 3 to ("" to 4), 5 to ("" to 4), 7 to ("" to 4), 9 to ("" to 4), 12 to ("" to 4)),
            Planet.JUPITER to mapOf(1 to ("" to 4), 3 to ("" to 5), 4 to ("" to 4), 6 to ("" to 4), 8 to ("" to 4), 10 to ("" to 4), 12 to ("" to 4)),
            Planet.SATURN to mapOf(1 to ("" to 3), 2 to ("" to 4), 4 to ("" to 3), 5 to ("" to 4), 7 to ("" to 3), 8 to ("" to 2), 9 to ("" to 3), 10 to ("" to 4), 12 to ("" to 3)),
            Planet.RAHU to mapOf(1 to ("" to 4), 2 to ("" to 4), 4 to ("" to 4), 5 to ("" to 4), 7 to ("" to 3), 8 to ("" to 3), 9 to ("" to 4), 12 to ("" to 3)),
            Planet.KETU to mapOf(1 to ("" to 4), 2 to ("" to 4), 4 to ("" to 4), 5 to ("" to 4), 7 to ("" to 3), 8 to ("" to 3), 10 to ("" to 4), 12 to ("" to 4))
        )

        private val DASHA_ENERGY_MODIFIERS = mapOf(
            Planet.JUPITER to 1.5,
            Planet.VENUS to 1.5,
            Planet.MERCURY to 1.0,
            Planet.MOON to 1.0,
            Planet.SUN to 0.5,
            Planet.SATURN to -0.5,
            Planet.MARS to -0.5,
            Planet.RAHU to -1.0,
            Planet.KETU to -1.0
        )

        private val DASHA_LORD_THEME_KEYS = mapOf(
            Planet.JUPITER to StringKeyHoroscope.THEME_EXPANSION_WISDOM,
            Planet.VENUS to StringKeyHoroscope.THEME_HARMONY_BEAUTY,
            Planet.SATURN to StringKeyHoroscope.THEME_DISCIPLINE_GROWTH,
            Planet.MERCURY to StringKeyHoroscope.THEME_COMMUNICATION_LEARNING,
            Planet.MARS to StringKeyHoroscope.THEME_ENERGY_INITIATIVE,
            Planet.SUN to StringKeyHoroscope.THEME_SELF_EXPRESSION,
            Planet.MOON to StringKeyHoroscope.THEME_INTUITION_NURTURING,
            Planet.RAHU to StringKeyHoroscope.THEME_TRANSFORMATION,
            Planet.KETU to StringKeyHoroscope.THEME_SPIRITUAL_LIBERATION
        )

        private val THEME_DESC_KEYS = mapOf(
            StringKeyHoroscope.THEME_DYNAMIC_ACTION to StringKeyHoroscope.THEME_DESC_DYNAMIC_ACTION,
            StringKeyHoroscope.THEME_PRACTICAL_PROGRESS to StringKeyHoroscope.THEME_DESC_PRACTICAL_PROGRESS,
            StringKeyHoroscope.THEME_SOCIAL_CONNECTIONS to StringKeyHoroscope.THEME_DESC_SOCIAL_CONNECTIONS,
            StringKeyHoroscope.THEME_EMOTIONAL_INSIGHT to StringKeyHoroscope.THEME_DESC_EMOTIONAL_INSIGHT,
            StringKeyHoroscope.THEME_EXPANSION_WISDOM to StringKeyHoroscope.THEME_DESC_EXPANSION_WISDOM,
            StringKeyHoroscope.THEME_HARMONY_BEAUTY to StringKeyHoroscope.THEME_DESC_HARMONY_BEAUTY,
            StringKeyHoroscope.THEME_DISCIPLINE_GROWTH to StringKeyHoroscope.THEME_DESC_DISCIPLINE_GROWTH,
            StringKeyHoroscope.THEME_COMMUNICATION_LEARNING to StringKeyHoroscope.THEME_DESC_COMMUNICATION_LEARNING,
            StringKeyHoroscope.THEME_ENERGY_INITIATIVE to StringKeyHoroscope.THEME_DESC_ENERGY_INITIATIVE,
            StringKeyHoroscope.THEME_SELF_EXPRESSION to StringKeyHoroscope.THEME_DESC_SELF_EXPRESSION,
            StringKeyHoroscope.THEME_INTUITION_NURTURING to StringKeyHoroscope.THEME_DESC_INTUITION_NURTURING,
            StringKeyHoroscope.THEME_TRANSFORMATION to StringKeyHoroscope.THEME_DESC_TRANSFORMATION,
            StringKeyHoroscope.THEME_SPIRITUAL_LIBERATION to StringKeyHoroscope.THEME_DESC_SPIRITUAL_LIBERATION,
            StringKeyHoroscope.THEME_BALANCE_EQUILIBRIUM to StringKeyHoroscope.THEME_DESC_BALANCE_EQUILIBRIUM
        )

        private val ELEMENT_COLOR_KEYS = mapOf(
            "Fire" to StringKeyHoroscope.LUCKY_COLOR_FIRE,
            "Earth" to StringKeyHoroscope.LUCKY_COLOR_EARTH,
            "Air" to StringKeyHoroscope.LUCKY_COLOR_AIR,
            "Water" to StringKeyHoroscope.LUCKY_COLOR_WATER
        )

        private val PLANET_DIRECTION_KEYS = mapOf(
            Planet.SUN to StringKeyHoroscope.LUCKY_DIR_EAST,
            Planet.MARS to StringKeyHoroscope.LUCKY_DIR_EAST,
            Planet.MOON to StringKeyHoroscope.LUCKY_DIR_NORTHWEST,
            Planet.VENUS to StringKeyHoroscope.LUCKY_DIR_SOUTHEAST,
            Planet.MERCURY to StringKeyHoroscope.LUCKY_DIR_NORTH,
            Planet.JUPITER to StringKeyHoroscope.LUCKY_DIR_NORTHEAST,
            Planet.SATURN to StringKeyHoroscope.LUCKY_DIR_WEST,
            Planet.RAHU to StringKeyHoroscope.LUCKY_DIR_SOUTHWEST,
            Planet.KETU to StringKeyHoroscope.LUCKY_DIR_NORTHWEST
        )

        private val DAY_HORA_KEYS = mapOf(
            1 to StringKeyHoroscope.HORA_SUN,
            2 to StringKeyHoroscope.HORA_MOON,
            3 to StringKeyHoroscope.HORA_MARS,
            4 to StringKeyHoroscope.HORA_MERCURY,
            5 to StringKeyHoroscope.HORA_JUPITER,
            6 to StringKeyHoroscope.HORA_VENUS,
            7 to StringKeyHoroscope.HORA_SATURN
        )

        private val PLANET_GEMSTONE_KEYS = mapOf(
            Planet.SUN to StringKeyMatch.GEMSTONE_RUBY,
            Planet.MOON to StringKeyMatch.GEMSTONE_PEARL,
            Planet.MARS to StringKeyMatch.GEMSTONE_RED_CORAL,
            Planet.MERCURY to StringKeyMatch.GEMSTONE_EMERALD,
            Planet.JUPITER to StringKeyMatch.GEMSTONE_YELLOW_SAPPHIRE,
            Planet.VENUS to StringKeyMatch.GEMSTONE_DIAMOND,
            Planet.SATURN to StringKeyMatch.GEMSTONE_BLUE_SAPPHIRE,
            Planet.RAHU to StringKeyMatch.GEMSTONE_HESSONITE,
            Planet.KETU to StringKeyMatch.GEMSTONE_CATS_EYE
        )

        private val DASHA_RECOMMENDATION_KEYS = mapOf(
            Planet.SUN to StringKeyHoroscope.REC_SUN,
            Planet.MOON to StringKeyHoroscope.REC_MOON,
            Planet.MARS to StringKeyHoroscope.REC_MARS,
            Planet.MERCURY to StringKeyHoroscope.REC_MERCURY,
            Planet.JUPITER to StringKeyHoroscope.REC_JUPITER,
            Planet.VENUS to StringKeyHoroscope.REC_VENUS,
            Planet.SATURN to StringKeyHoroscope.REC_SATURN,
            Planet.RAHU to StringKeyHoroscope.REC_RAHU,
            Planet.KETU to StringKeyHoroscope.REC_KETU
        )

        private val BEST_AREA_RECOMMENDATION_KEYS = mapOf(
            LifeArea.CAREER to StringKeyHoroscope.REC_AREA_CAREER,
            LifeArea.LOVE to StringKeyHoroscope.REC_AREA_LOVE,
            LifeArea.HEALTH to StringKeyHoroscope.REC_AREA_HEALTH,
            LifeArea.FINANCE to StringKeyHoroscope.REC_AREA_FINANCE,
            LifeArea.FAMILY to StringKeyHoroscope.REC_AREA_FAMILY,
            LifeArea.SPIRITUALITY to StringKeyHoroscope.REC_AREA_SPIRITUALITY
        )

        private val ELEMENT_RECOMMENDATION_KEYS = mapOf(
            "Fire" to StringKeyHoroscope.REC_ELEMENT_FIRE,
            "Earth" to StringKeyHoroscope.REC_ELEMENT_EARTH,
            "Air" to StringKeyHoroscope.REC_ELEMENT_AIR,
            "Water" to StringKeyHoroscope.REC_ELEMENT_WATER
        )

        private val PLANET_CAUTION_KEYS = mapOf(
            Planet.SATURN to StringKeyHoroscope.CAUTION_SATURN,
            Planet.MARS to StringKeyHoroscope.CAUTION_MARS,
            Planet.RAHU to StringKeyHoroscope.CAUTION_RAHU,
            Planet.KETU to StringKeyHoroscope.CAUTION_KETU
        )

        private val DASHA_AFFIRMATION_KEYS = mapOf(
            Planet.SUN to StringKeyHoroscope.AFF_SUN,
            Planet.MOON to StringKeyHoroscope.AFF_MOON,
            Planet.MARS to StringKeyHoroscope.AFF_MARS,
            Planet.MERCURY to StringKeyHoroscope.AFF_MERCURY,
            Planet.JUPITER to StringKeyHoroscope.AFF_JUPITER,
            Planet.VENUS to StringKeyHoroscope.AFF_VENUS,
            Planet.SATURN to StringKeyHoroscope.AFF_SATURN,
            Planet.RAHU to StringKeyHoroscope.AFF_RAHU,
            Planet.KETU to StringKeyHoroscope.AFF_KETU
        )

        private val LUNAR_PHASE_CONFIG = listOf(
            Triple(7, StringKeyHoroscope.LUNAR_FIRST_QUARTER, StringKeyHoroscope.LUNAR_ACTION),
            Triple(14, StringKeyHoroscope.LUNAR_FULL_MOON, StringKeyHoroscope.LUNAR_COMPLETION)
        )

        private val FAVORABLE_DAY_KEYS = mapOf(
            java.time.DayOfWeek.THURSDAY to StringKeyHoroscope.DAY_JUPITER,
            java.time.DayOfWeek.FRIDAY to StringKeyHoroscope.DAY_VENUS
        )

        private val DASHA_WEEKLY_ADVICE_KEYS = mapOf(
            Planet.JUPITER to StringKeyHoroscope.WEEKLY_ADVICE_JUPITER,
            Planet.VENUS to StringKeyHoroscope.WEEKLY_ADVICE_VENUS,
            Planet.SATURN to StringKeyHoroscope.WEEKLY_ADVICE_SATURN,
            Planet.MERCURY to StringKeyHoroscope.WEEKLY_ADVICE_MERCURY,
            Planet.MARS to StringKeyHoroscope.WEEKLY_ADVICE_MARS,
            Planet.SUN to StringKeyHoroscope.WEEKLY_ADVICE_SUN,
            Planet.MOON to StringKeyHoroscope.WEEKLY_ADVICE_MOON,
            Planet.RAHU to StringKeyHoroscope.WEEKLY_ADVICE_RAHU,
            Planet.KETU to StringKeyHoroscope.WEEKLY_ADVICE_KETU
        )

        private val LIFE_AREA_PREDICTION_KEYS = mapOf(
            LifeArea.CAREER to mapOf(
                5 to StringKeyHoroscope.PRED_CAREER_5,
                4 to StringKeyHoroscope.PRED_CAREER_4,
                3 to StringKeyHoroscope.PRED_CAREER_3,
                2 to StringKeyHoroscope.PRED_CAREER_2,
                1 to StringKeyHoroscope.PRED_CAREER_1
            ),
            LifeArea.LOVE to mapOf(
                5 to StringKeyHoroscope.PRED_LOVE_5,
                4 to StringKeyHoroscope.PRED_LOVE_4,
                3 to StringKeyHoroscope.PRED_LOVE_3,
                2 to StringKeyHoroscope.PRED_LOVE_2,
                1 to StringKeyHoroscope.PRED_LOVE_1
            ),
            LifeArea.HEALTH to mapOf(
                5 to StringKeyHoroscope.PRED_HEALTH_5,
                4 to StringKeyHoroscope.PRED_HEALTH_4,
                3 to StringKeyHoroscope.PRED_HEALTH_3,
                2 to StringKeyHoroscope.PRED_HEALTH_2,
                1 to StringKeyHoroscope.PRED_HEALTH_1
            ),
            LifeArea.FINANCE to mapOf(
                5 to StringKeyHoroscope.PRED_FINANCE_5,
                4 to StringKeyHoroscope.PRED_FINANCE_4,
                3 to StringKeyHoroscope.PRED_FINANCE_3,
                2 to StringKeyHoroscope.PRED_FINANCE_2,
                1 to StringKeyHoroscope.PRED_FINANCE_1
            ),
            LifeArea.FAMILY to mapOf(
                5 to StringKeyHoroscope.PRED_FAMILY_5,
                4 to StringKeyHoroscope.PRED_FAMILY_4,
                3 to StringKeyHoroscope.PRED_FAMILY_3,
                2 to StringKeyHoroscope.PRED_FAMILY_2,
                1 to StringKeyHoroscope.PRED_FAMILY_1
            ),
            LifeArea.SPIRITUALITY to mapOf(
                5 to StringKeyHoroscope.PRED_SPIRIT_5,
                4 to StringKeyHoroscope.PRED_SPIRIT_4,
                3 to StringKeyHoroscope.PRED_SPIRIT_3,
                2 to StringKeyHoroscope.PRED_SPIRIT_2,
                1 to StringKeyHoroscope.PRED_SPIRIT_1
            )
        )

        private val LIFE_AREA_ADVICE_KEYS = mapOf(
            LifeArea.CAREER to mapOf(
                "high" to StringKeyPrediction.PRED_CAREER_ADVICE, // Reusing existing keys where possible
                "medium" to StringKeyHoroscope.THEME_DESC_BALANCE_EQUILIBRIUM,
                "low" to StringKeyHoroscope.THEME_DESC_DYNAMIC_ACTION
            ),
            LifeArea.LOVE to mapOf(
                "high" to StringKeyPrediction.PRED_REL_ADVICE,
                "medium" to StringKeyHoroscope.THEME_DESC_BALANCE_EQUILIBRIUM,
                "low" to StringKeyHoroscope.THEME_DESC_EMOTIONAL_INSIGHT
            ),
            LifeArea.HEALTH to mapOf(
                "high" to StringKeyPrediction.PRED_HEALTH_ADVICE,
                "medium" to StringKeyHoroscope.THEME_DESC_BALANCE_EQUILIBRIUM,
                "low" to StringKeyHoroscope.THEME_DESC_PRACTICAL_PROGRESS
            ),
            LifeArea.FINANCE to mapOf(
                "high" to StringKeyPrediction.PRED_FINANCE_ADVICE,
                "medium" to StringKeyHoroscope.THEME_DESC_BALANCE_EQUILIBRIUM,
                "low" to StringKeyHoroscope.THEME_DESC_SOCIAL_CONNECTIONS
            ),
            LifeArea.FAMILY to mapOf(
                "high" to StringKeyPrediction.PRED_FAMILY_ADVICE,
                "medium" to StringKeyHoroscope.THEME_DESC_BALANCE_EQUILIBRIUM,
                "low" to StringKeyHoroscope.THEME_DESC_INTUITION_NURTURING
            ),
            LifeArea.SPIRITUALITY to mapOf(
                "high" to StringKeyPrediction.PRED_SPIRIT_ADVICE,
                "medium" to StringKeyHoroscope.THEME_DESC_BALANCE_EQUILIBRIUM,
                "low" to StringKeyHoroscope.THEME_DESC_SPIRITUAL_LIBERATION
            )
        )

        private val WEEKLY_PREDICTION_KEYS = mapOf(
            LifeArea.CAREER to mapOf(
                "excellent" to StringKeyHoroscope.WEEKLY_CAREER_EXCELLENT,
                "steady" to StringKeyHoroscope.WEEKLY_CAREER_STEADY,
                "challenging" to StringKeyHoroscope.WEEKLY_CAREER_CHALLENGING
            ),
            LifeArea.LOVE to mapOf(
                "excellent" to StringKeyHoroscope.WEEKLY_LOVE_EXCELLENT,
                "steady" to StringKeyHoroscope.WEEKLY_LOVE_STEADY,
                "challenging" to StringKeyHoroscope.WEEKLY_LOVE_CHALLENGING
            ),
            LifeArea.HEALTH to mapOf(
                "excellent" to StringKeyHoroscope.WEEKLY_HEALTH_EXCELLENT,
                "steady" to StringKeyHoroscope.WEEKLY_HEALTH_STEADY,
                "challenging" to StringKeyHoroscope.WEEKLY_HEALTH_CHALLENGING
            ),
            LifeArea.FINANCE to mapOf(
                "excellent" to StringKeyHoroscope.WEEKLY_FINANCE_EXCELLENT,
                "steady" to StringKeyHoroscope.WEEKLY_FINANCE_STEADY,
                "challenging" to StringKeyHoroscope.WEEKLY_FINANCE_CHALLENGING
            ),
            LifeArea.FAMILY to mapOf(
                "excellent" to StringKeyHoroscope.WEEKLY_FAMILY_EXCELLENT,
                "steady" to StringKeyHoroscope.WEEKLY_FAMILY_STEADY,
                "challenging" to StringKeyHoroscope.WEEKLY_FAMILY_CHALLENGING
            ),
            LifeArea.SPIRITUALITY to mapOf(
                "excellent" to StringKeyHoroscope.WEEKLY_SPIRITUALITY_EXCELLENT,
                "steady" to StringKeyHoroscope.WEEKLY_SPIRITUALITY_STEADY,
                "challenging" to StringKeyHoroscope.WEEKLY_SPIRITUALITY_CHALLENGING
            )
        )
    }
}
    }
}