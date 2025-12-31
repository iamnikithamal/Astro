package com.astro.storm.ephemeris

import android.content.Context
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringKeyInterface
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.model.*
import swisseph.SweConst
import swisseph.SwissEph
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.min

class VarshaphalaCalculator(context: Context) {

    private val swissEph = SwissEph()
    private val ephemerisPath: String

    companion object {
        private const val AYANAMSA_LAHIRI = SweConst.SE_SIDM_LAHIRI
        private const val SEFLG_SIDEREAL = SweConst.SEFLG_SIDEREAL
        private const val SEFLG_SPEED = SweConst.SEFLG_SPEED
        private const val SIDEREAL_YEAR_DAYS = 365.256363

        private const val CONJUNCTION_ORB = 12.0
        private const val OPPOSITION_ORB = 9.0
        private const val TRINE_ORB = 8.0
        private const val SQUARE_ORB = 7.0
        private const val SEXTILE_ORB = 6.0

        private val VIMSHOTTARI_YEARS = mapOf(
            Planet.KETU to 7,
            Planet.VENUS to 20,
            Planet.SUN to 6,
            Planet.MOON to 10,
            Planet.MARS to 7,
            Planet.RAHU to 18,
            Planet.JUPITER to 16,
            Planet.SATURN to 19,
            Planet.MERCURY to 17
        )

        private val VIMSHOTTARI_ORDER = listOf(
            Planet.KETU, Planet.VENUS, Planet.SUN, Planet.MOON, Planet.MARS,
            Planet.RAHU, Planet.JUPITER, Planet.SATURN, Planet.MERCURY
        )

        private val DAY_LORDS = listOf(
            Planet.SUN,
            Planet.MOON,
            Planet.MARS,
            Planet.MERCURY,
            Planet.JUPITER,
            Planet.VENUS,
            Planet.SATURN
        )

        private val HADDA_LORDS = mapOf(
            ZodiacSign.ARIES to listOf(
                Triple(0.0, 6.0, Planet.JUPITER),
                Triple(6.0, 12.0, Planet.VENUS),
                Triple(12.0, 20.0, Planet.MERCURY),
                Triple(20.0, 25.0, Planet.MARS),
                Triple(25.0, 30.0, Planet.SATURN)
            ),
            ZodiacSign.TAURUS to listOf(
                Triple(0.0, 8.0, Planet.VENUS),
                Triple(8.0, 14.0, Planet.MERCURY),
                Triple(14.0, 22.0, Planet.JUPITER),
                Triple(22.0, 27.0, Planet.SATURN),
                Triple(27.0, 30.0, Planet.MARS)
            ),
            ZodiacSign.GEMINI to listOf(
                Triple(0.0, 6.0, Planet.MERCURY),
                Triple(6.0, 12.0, Planet.JUPITER),
                Triple(12.0, 17.0, Planet.VENUS),
                Triple(17.0, 24.0, Planet.MARS),
                Triple(24.0, 30.0, Planet.SATURN)
            ),
            ZodiacSign.CANCER to listOf(
                Triple(0.0, 7.0, Planet.MARS),
                Triple(7.0, 13.0, Planet.VENUS),
                Triple(13.0, 19.0, Planet.MERCURY),
                Triple(19.0, 26.0, Planet.JUPITER),
                Triple(26.0, 30.0, Planet.SATURN)
            ),
            ZodiacSign.LEO to listOf(
                Triple(0.0, 6.0, Planet.JUPITER),
                Triple(6.0, 11.0, Planet.VENUS),
                Triple(11.0, 18.0, Planet.SATURN),
                Triple(18.0, 24.0, Planet.MERCURY),
                Triple(24.0, 30.0, Planet.MARS)
            ),
            ZodiacSign.VIRGO to listOf(
                Triple(0.0, 7.0, Planet.MERCURY),
                Triple(7.0, 17.0, Planet.VENUS),
                Triple(17.0, 21.0, Planet.JUPITER),
                Triple(21.0, 28.0, Planet.MARS),
                Triple(28.0, 30.0, Planet.SATURN)
            ),
            ZodiacSign.LIBRA to listOf(
                Triple(0.0, 6.0, Planet.SATURN),
                Triple(6.0, 14.0, Planet.MERCURY),
                Triple(14.0, 21.0, Planet.JUPITER),
                Triple(21.0, 28.0, Planet.VENUS),
                Triple(28.0, 30.0, Planet.MARS)
            ),
            ZodiacSign.SCORPIO to listOf(
                Triple(0.0, 7.0, Planet.MARS),
                Triple(7.0, 11.0, Planet.VENUS),
                Triple(11.0, 19.0, Planet.MERCURY),
                Triple(19.0, 24.0, Planet.JUPITER),
                Triple(24.0, 30.0, Planet.SATURN)
            ),
            ZodiacSign.SAGITTARIUS to listOf(
                Triple(0.0, 12.0, Planet.JUPITER),
                Triple(12.0, 17.0, Planet.VENUS),
                Triple(17.0, 21.0, Planet.MERCURY),
                Triple(21.0, 26.0, Planet.SATURN),
                Triple(26.0, 30.0, Planet.MARS)
            ),
            ZodiacSign.CAPRICORN to listOf(
                Triple(0.0, 7.0, Planet.MERCURY),
                Triple(7.0, 14.0, Planet.JUPITER),
                Triple(14.0, 22.0, Planet.VENUS),
                Triple(22.0, 26.0, Planet.SATURN),
                Triple(26.0, 30.0, Planet.MARS)
            ),
            ZodiacSign.AQUARIUS to listOf(
                Triple(0.0, 7.0, Planet.MERCURY),
                Triple(7.0, 13.0, Planet.VENUS),
                Triple(13.0, 20.0, Planet.JUPITER),
                Triple(20.0, 25.0, Planet.MARS),
                Triple(25.0, 30.0, Planet.SATURN)
            ),
            ZodiacSign.PISCES to listOf(
                Triple(0.0, 12.0, Planet.VENUS),
                Triple(12.0, 16.0, Planet.JUPITER),
                Triple(16.0, 19.0, Planet.MERCURY),
                Triple(19.0, 28.0, Planet.MARS),
                Triple(28.0, 30.0, Planet.SATURN)
            )
        )

        private val EXALTATION_DEGREES = mapOf(
            Planet.SUN to 10.0,
            Planet.MOON to 33.0,
            Planet.MARS to 298.0,
            Planet.MERCURY to 165.0,
            Planet.JUPITER to 95.0,
            Planet.VENUS to 357.0,
            Planet.SATURN to 200.0
        )

        private val DEBILITATION_SIGNS = mapOf(
            Planet.SUN to ZodiacSign.LIBRA,
            Planet.MOON to ZodiacSign.SCORPIO,
            Planet.MARS to ZodiacSign.CANCER,
            Planet.MERCURY to ZodiacSign.PISCES,
            Planet.JUPITER to ZodiacSign.CAPRICORN,
            Planet.VENUS to ZodiacSign.VIRGO,
            Planet.SATURN to ZodiacSign.ARIES
        )

        private val OWN_SIGNS = mapOf(
            Planet.SUN to listOf(ZodiacSign.LEO),
            Planet.MOON to listOf(ZodiacSign.CANCER),
            Planet.MARS to listOf(ZodiacSign.ARIES, ZodiacSign.SCORPIO),
            Planet.MERCURY to listOf(ZodiacSign.GEMINI, ZodiacSign.VIRGO),
            Planet.JUPITER to listOf(ZodiacSign.SAGITTARIUS, ZodiacSign.PISCES),
            Planet.VENUS to listOf(ZodiacSign.TAURUS, ZodiacSign.LIBRA),
            Planet.SATURN to listOf(ZodiacSign.CAPRICORN, ZodiacSign.AQUARIUS)
        )

        private val FRIENDSHIPS = mapOf(
            Planet.SUN to listOf(Planet.MOON, Planet.MARS, Planet.JUPITER),
            Planet.MOON to listOf(Planet.SUN, Planet.MERCURY),
            Planet.MARS to listOf(Planet.SUN, Planet.MOON, Planet.JUPITER),
            Planet.MERCURY to listOf(Planet.SUN, Planet.VENUS),
            Planet.JUPITER to listOf(Planet.SUN, Planet.MOON, Planet.MARS),
            Planet.VENUS to listOf(Planet.MERCURY, Planet.SATURN),
            Planet.SATURN to listOf(Planet.MERCURY, Planet.VENUS)
        )

        private val NEUTRALS = mapOf(
            Planet.SUN to listOf(Planet.MERCURY),
            Planet.MOON to listOf(Planet.MARS, Planet.JUPITER, Planet.VENUS, Planet.SATURN),
            Planet.MARS to listOf(Planet.MERCURY, Planet.VENUS, Planet.SATURN),
            Planet.MERCURY to listOf(Planet.MARS, Planet.JUPITER, Planet.SATURN),
            Planet.JUPITER to listOf(Planet.MERCURY, Planet.SATURN),
            Planet.VENUS to listOf(Planet.MARS, Planet.JUPITER),
            Planet.SATURN to listOf(Planet.MARS, Planet.JUPITER)
        )

        private val MUDDA_DASHA_PLANETS = listOf(
            Planet.SUN, Planet.MOON, Planet.MARS, Planet.MERCURY,
            Planet.JUPITER, Planet.VENUS, Planet.SATURN, Planet.RAHU, Planet.KETU
        )

        private val MUDDA_DASHA_DAYS = mapOf(
            Planet.SUN to 110,
            Planet.MOON to 60,
            Planet.MARS to 32,
            Planet.MERCURY to 40,
            Planet.JUPITER to 48,
            Planet.VENUS to 56,
            Planet.SATURN to 4,
            Planet.RAHU to 5,
            Planet.KETU to 5
        )

        private val STANDARD_ZODIAC_SIGNS = listOf(
            ZodiacSign.ARIES, ZodiacSign.TAURUS, ZodiacSign.GEMINI, ZodiacSign.CANCER,
            ZodiacSign.LEO, ZodiacSign.VIRGO, ZodiacSign.LIBRA, ZodiacSign.SCORPIO,
            ZodiacSign.SAGITTARIUS, ZodiacSign.CAPRICORN, ZodiacSign.AQUARIUS, ZodiacSign.PISCES
        )
    }

    init {
        ephemerisPath = context.filesDir.absolutePath + "/ephe"
        swissEph.swe_set_ephe_path(ephemerisPath)
        swissEph.swe_set_sid_mode(AYANAMSA_LAHIRI, 0.0, 0.0)
    }

    enum class TajikaAspectType(
        val displayNameKey: StringKeyInterface,
        val descriptionKey: StringKeyInterface,
        val isPositive: Boolean
    ) {
        ITHASALA(StringKeyAnalysis.TAJIKA_ITHASALA, StringKeyAnalysis.TAJIKA_ITHASALA_DESC, true),
        EASARAPHA(StringKeyAnalysis.TAJIKA_EASARAPHA, StringKeyAnalysis.TAJIKA_EASARAPHA_DESC, false),
        NAKTA(StringKeyAnalysis.TAJIKA_NAKTA, StringKeyAnalysis.TAJIKA_NAKTA_DESC, true),
        YAMAYA(StringKeyAnalysis.TAJIKA_YAMAYA, StringKeyAnalysis.TAJIKA_YAMAYA_DESC, true),
        MANAU(StringKeyAnalysis.TAJIKA_MANAU, StringKeyAnalysis.TAJIKA_MANAU_DESC, false),
        KAMBOOLA(StringKeyAnalysis.TAJIKA_KAMBOOLA, StringKeyAnalysis.TAJIKA_KAMBOOLA_DESC, true),
        GAIRI_KAMBOOLA(StringKeyAnalysis.TAJIKA_GAIRI_KAMBOOLA, StringKeyAnalysis.TAJIKA_GAIRI_KAMBOOLA_DESC, true),
        KHALASARA(StringKeyAnalysis.TAJIKA_KHALASARA, StringKeyAnalysis.TAJIKA_KHALASARA_DESC, false),
        RADDA(StringKeyAnalysis.TAJIKA_RADDA, StringKeyAnalysis.TAJIKA_RADDA_DESC, false),
        DUHPHALI_KUTTHA(StringKeyAnalysis.TAJIKA_DUHPHALI_KUTTHA, StringKeyAnalysis.TAJIKA_DUHPHALI_KUTTHA_DESC, false),
        TAMBIRA(StringKeyAnalysis.TAJIKA_TAMBIRA, StringKeyAnalysis.TAJIKA_TAMBIRA_DESC, true),
        KUTTHA(StringKeyAnalysis.TAJIKA_KUTTHA, StringKeyAnalysis.TAJIKA_KUTTHA_DESC, false),
        DURAPHA(StringKeyAnalysis.TAJIKA_DURAPHA, StringKeyAnalysis.TAJIKA_DURAPHA_DESC, false),
        MUTHASHILA(StringKeyAnalysis.TAJIKA_MUTHASHILA, StringKeyAnalysis.TAJIKA_MUTHASHILA_DESC, true),
        IKKABALA(StringKeyAnalysis.TAJIKA_IKKABALA, StringKeyAnalysis.TAJIKA_IKKABALA_DESC, true);

        fun getDisplayName(language: Language): String = StringResources.get(displayNameKey, language)
        fun getDescription(language: Language): String = StringResources.get(descriptionKey, language)
    }

    enum class AspectStrength(val displayNameKey: StringKeyInterface, val weight: Double) {
        VERY_STRONG(StringKeyAnalysis.ASPECT_VERY_STRONG, 1.0),
        STRONG(StringKeyAnalysis.ASPECT_STRONG, 0.8),
        MODERATE(StringKeyAnalysis.ASPECT_MODERATE, 0.6),
        WEAK(StringKeyAnalysis.ASPECT_WEAK, 0.4),
        VERY_WEAK(StringKeyAnalysis.ASPECT_VERY_WEAK, 0.2);

        fun getDisplayName(language: Language): String = StringResources.get(displayNameKey, language)
    }

    enum class SahamType(
        val displayNameKey: StringKeyInterface,
        val sanskritNameKey: StringKeyInterface,
        val descriptionKey: StringKeyInterface
    ) {
        PUNYA(StringKeyAnalysis.SAHAM_PUNYA, StringKeyAnalysis.SAHAM_PUNYA_SANSKRIT, StringKeyAnalysis.SAHAM_PUNYA_DESC),
        VIDYA(StringKeyAnalysis.SAHAM_VIDYA, StringKeyAnalysis.SAHAM_VIDYA_SANSKRIT, StringKeyAnalysis.SAHAM_VIDYA_DESC),
        YASHAS(StringKeyAnalysis.SAHAM_YASHAS, StringKeyAnalysis.SAHAM_YASHAS_SANSKRIT, StringKeyAnalysis.SAHAM_YASHAS_DESC),
        MITRA(StringKeyAnalysis.SAHAM_MITRA, StringKeyAnalysis.SAHAM_MITRA_SANSKRIT, StringKeyAnalysis.SAHAM_MITRA_DESC),
        MAHATMYA(StringKeyAnalysis.SAHAM_MAHATMYA, StringKeyAnalysis.SAHAM_MAHATMYA_SANSKRIT, StringKeyAnalysis.SAHAM_MAHATMYA_DESC),
        ASHA(StringKeyAnalysis.SAHAM_ASHA, StringKeyAnalysis.SAHAM_ASHA_SANSKRIT, StringKeyAnalysis.SAHAM_ASHA_DESC),
        SAMARTHA(StringKeyAnalysis.SAHAM_SAMARTHA, StringKeyAnalysis.SAHAM_SAMARTHA_SANSKRIT, StringKeyAnalysis.SAHAM_SAMARTHA_DESC),
        BHRATRI(StringKeyAnalysis.SAHAM_BHRATRI, StringKeyAnalysis.SAHAM_BHRATRI_SANSKRIT, StringKeyAnalysis.SAHAM_BHRATRI_DESC),
        PITRI(StringKeyAnalysis.SAHAM_PITRI, StringKeyAnalysis.SAHAM_PITRI_SANSKRIT, StringKeyAnalysis.SAHAM_PITRI_DESC),
        MATRI(StringKeyAnalysis.SAHAM_MATRI, StringKeyAnalysis.SAHAM_MATRI_SANSKRIT, StringKeyAnalysis.SAHAM_MATRI_DESC),
        PUTRA(StringKeyAnalysis.SAHAM_PUTRA, StringKeyAnalysis.SAHAM_PUTRA_SANSKRIT, StringKeyAnalysis.SAHAM_PUTRA_DESC),
        VIVAHA(StringKeyAnalysis.SAHAM_VIVAHA, StringKeyAnalysis.SAHAM_VIVAHA_SANSKRIT, StringKeyAnalysis.SAHAM_VIVAHA_DESC),
        KARMA(StringKeyAnalysis.SAHAM_KARMA, StringKeyAnalysis.SAHAM_KARMA_SANSKRIT, StringKeyAnalysis.SAHAM_KARMA_DESC),
        ROGA(StringKeyAnalysis.SAHAM_ROGA, StringKeyAnalysis.SAHAM_ROGA_SANSKRIT, StringKeyAnalysis.SAHAM_ROGA_DESC),
        MRITYU(StringKeyAnalysis.SAHAM_MRITYU, StringKeyAnalysis.SAHAM_MRITYU_SANSKRIT, StringKeyAnalysis.SAHAM_MRITYU_DESC),
        PARADESA(StringKeyAnalysis.SAHAM_PARADESA, StringKeyAnalysis.SAHAM_PARADESA_SANSKRIT, StringKeyAnalysis.SAHAM_PARADESA_DESC),
        DHANA(StringKeyAnalysis.SAHAM_DHANA, StringKeyAnalysis.SAHAM_DHANA_SANSKRIT, StringKeyAnalysis.SAHAM_DHANA_DESC),
        RAJA(StringKeyAnalysis.SAHAM_RAJA, StringKeyAnalysis.SAHAM_RAJA_SANSKRIT, StringKeyAnalysis.SAHAM_RAJA_DESC),
        BANDHANA(StringKeyAnalysis.SAHAM_BANDHANA, StringKeyAnalysis.SAHAM_BANDHANA_SANSKRIT, StringKeyAnalysis.SAHAM_BANDHANA_DESC),
        KARYASIDDHI(StringKeyAnalysis.SAHAM_KARYASIDDHI_TYPE, StringKeyAnalysis.SAHAM_KARYASIDDHI_TYPE_SANSKRIT, StringKeyAnalysis.SAHAM_KARYASIDDHI_TYPE_DESC);

        fun getDisplayName(language: Language): String = StringResources.get(displayNameKey, language)
        fun getSanskritName(language: Language): String = StringResources.get(sanskritNameKey, language)
        fun getDescription(language: Language): String = StringResources.get(descriptionKey, language)
    }

    enum class KeyDateType(val displayNameKey: StringKeyInterface) {
        FAVORABLE(StringKeyAnalysis.KEY_DATE_FAVORABLE),
        CHALLENGING(StringKeyAnalysis.KEY_DATE_CHALLENGING),
        IMPORTANT(StringKeyAnalysis.KEY_DATE_IMPORTANT),
        TRANSIT(StringKeyAnalysis.KEY_DATE_TRANSIT);

        fun getDisplayName(language: Language): String = StringResources.get(displayNameKey, language)
    }

    data class SolarReturnChart(
        val year: Int,
        val solarReturnTime: LocalDateTime,
        val solarReturnTimeUtc: LocalDateTime,
        val julianDay: Double,
        val planetPositions: Map<Planet, SolarReturnPlanetPosition>,
        val ascendant: ZodiacSign,
        val ascendantDegree: Double,
        val midheaven: Double,
        val houseCusps: List<Double>,
        val ayanamsa: Double,
        val isDayBirth: Boolean,
        val moonSign: ZodiacSign,
        val moonNakshatra: String
    )

    data class SolarReturnPlanetPosition(
        val longitude: Double,
        val sign: ZodiacSign,
        val house: Int,
        val degree: Double,
        val nakshatra: String,
        val nakshatraPada: Int,
        val isRetrograde: Boolean,
        val speed: Double
    )

    data class MunthaResult(
        val longitude: Double,
        val sign: ZodiacSign,
        val house: Int,
        val degree: Double,
        val lord: Planet,
        val lordHouse: Int,
        val lordStrength: String,
        val interpretation: String,
        val themes: List<String>
    )

    data class SahamResult(
        val type: SahamType,
        val name: String,
        val sanskritName: String,
        val formula: String,
        val longitude: Double,
        val sign: ZodiacSign,
        val house: Int,
        val degree: Double,
        val lord: Planet,
        val lordHouse: Int,
        val lordStrength: String,
        val interpretation: String,
        val isActive: Boolean,
        val activationPeriods: List<String>
    )

    data class TajikaAspectResult(
        val type: TajikaAspectType,
        val planet1: Planet,
        val planet2: Planet,
        val planet1Longitude: Double,
        val planet2Longitude: Double,
        val aspectAngle: Int,
        val orb: Double,
        val isApplying: Boolean,
        val strength: AspectStrength,
        val relatedHouses: List<Int>,
        val effectDescription: String,
        val prediction: String
    )

    data class MuddaDashaPeriod(
        val planet: Planet,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val days: Int,
        val subPeriods: List<MuddaAntardasha>,
        val planetStrength: String,
        val houseRuled: List<Int>,
        val prediction: String,
        val keywords: List<String>,
        val isCurrent: Boolean,
        val progressPercent: Float
    )

    data class MuddaAntardasha(
        val planet: Planet,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val days: Int,
        val interpretation: String
    )

    data class PanchaVargiyaBala(
        val planet: Planet,
        val uchcha: Double,
        val hadda: Double,
        val dreshkana: Double,
        val navamsha: Double,
        val dwadashamsha: Double,
        val total: Double,
        val category: String
    )

    data class TriPatakiSector(
        val name: String,
        val signs: List<ZodiacSign>,
        val planets: List<Planet>,
        val influence: String
    )

    data class TriPatakiChakra(
        val risingSign: ZodiacSign,
        val sectors: List<TriPatakiSector>,
        val dominantInfluence: String,
        val interpretation: String
    )

    data class HousePrediction(
        val house: Int,
        val signOnCusp: ZodiacSign,
        val houseLord: Planet,
        val lordPosition: Int,
        val planetsInHouse: List<Planet>,
        val strength: String,
        val keywords: List<String>,
        val prediction: String,
        val rating: Float,
        val specificEvents: List<String>
    )

    data class KeyDate(
        val date: LocalDate,
        val event: String,
        val type: KeyDateType,
        val description: String
    )

    data class VarshaphalaResult(
        val natalChart: VedicChart,
        val year: Int,
        val age: Int,
        val solarReturnChart: SolarReturnChart,
        val yearLord: Planet,
        val yearLordStrength: String,
        val yearLordHouse: Int,
        val yearLordDignity: String,
        val muntha: MunthaResult,
        val panchaVargiyaBala: List<PanchaVargiyaBala>,
        val triPatakiChakra: TriPatakiChakra,
        val sahams: List<SahamResult>,
        val tajikaAspects: List<TajikaAspectResult>,
        val muddaDasha: List<MuddaDashaPeriod>,
        val housePredictions: List<HousePrediction>,
        val majorThemes: List<String>,
        val favorableMonths: List<Int>,
        val challengingMonths: List<Int>,
        val overallPrediction: String,
        val yearRating: Float,
        val keyDates: List<KeyDate>,
        val timestamp: Long = System.currentTimeMillis()
    ) {
        fun toPlainText(language: Language = Language.ENGLISH): String = buildString {
            val reportTitle = StringResources.get(StringKeyAnalysis.VARSHA_REPORT_TITLE, language)
            val yearLordSection = StringResources.get(StringKeyAnalysis.VARSHA_REPORT_SECTION_YEARLORD, language)
            val munthaSection = StringResources.get(StringKeyAnalysis.VARSHA_REPORT_SECTION_MUNTHA, language)
            val themesSection = StringResources.get(StringKeyAnalysis.VARSHA_REPORT_SECTION_THEMES, language)
            val muddaDashaSection = StringResources.get(StringKeyAnalysis.VARSHA_REPORT_SECTION_MUDDA, language)
            val predictionSection = StringResources.get(StringKeyAnalysis.VARSHA_REPORT_SECTION_PREDICTION, language)
            val currentMarker = StringResources.get(StringKeyAnalysis.VARSHA_REPORT_CURRENT_MARKER, language)
            val footer = StringResources.get(StringKeyAnalysis.VARSHA_REPORT_FOOTER, language)

            appendLine("═══════════════════════════════════════════════════════════")
            appendLine("            $reportTitle")
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine()
            appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_NAME, language, natalChart.birthData.name))
            appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_YEAR, language, year, age))
            appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_SOLAR_RETURN, language, solarReturnChart.solarReturnTime.toString()))
            appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_YEAR_RATING, language, String.format("%.1f", yearRating)))
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                      $yearLordSection")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_YEARLORD_LINE, language, yearLord.getLocalizedName(language), yearLordStrength))
            appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_POSITION, language, yearLordHouse))
            appendLine(yearLordDignity)
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                       $munthaSection")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_MUNTHA_POSITION, language, String.format("%.2f", muntha.degree), muntha.sign.getLocalizedName(language)))
            appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_MUNTHA_HOUSE, language, muntha.house))
            appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_MUNTHA_LORD, language, muntha.lord.getLocalizedName(language), muntha.lordHouse))
            appendLine(muntha.interpretation)
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                    $themesSection")
            appendLine("─────────────────────────────────────────────────────────")
            majorThemes.forEach { appendLine("• $it") }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("                   $muddaDashaSection")
            appendLine("─────────────────────────────────────────────────────────")
            muddaDasha.forEach { period ->
                val marker = if (period.isCurrent) currentMarker else ""
                appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_DASHA_LINE, language,
                    period.planet.getLocalizedName(language),
                    period.startDate.toString(),
                    period.endDate.toString(),
                    period.days,
                    marker))
            }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_FAVORABLE_MONTHS, language, favorableMonths.joinToString()))
            appendLine(StringResources.get(StringKeyAnalysis.VARSHA_REPORT_CHALLENGING_MONTHS, language, challengingMonths.joinToString()))
            appendLine("─────────────────────────────────────────────────────────")
            appendLine()
            appendLine("                   $predictionSection")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine(overallPrediction)
            appendLine()
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine(footer)
            appendLine("═══════════════════════════════════════════════════════════")
        }
    }

    fun calculateVarshaphala(natalChart: VedicChart, year: Int, language: Language = Language.ENGLISH): VarshaphalaResult {
        val birthDateTime = natalChart.birthData.dateTime
        val birthYear = birthDateTime.year
        val age = year - birthYear

        require(age >= 0) { "Year must be after birth year" }

        val solarReturnTime = calculateSolarReturnTime(
            natalChart.birthData.dateTime,
            year,
            natalChart.birthData.latitude,
            natalChart.birthData.longitude,
            natalChart.birthData.timezone
        )

        val solarReturnChart = calculateSolarReturnChart(
            solarReturnTime,
            natalChart.birthData.latitude,
            natalChart.birthData.longitude,
            natalChart.birthData.timezone,
            year
        )

        val panchaVargiyaBala = calculateAllPanchaVargiyaBalas(solarReturnChart, language)
        val muntha = calculateMuntha(natalChart, age, solarReturnChart, language)
        val yearLord = determineYearLord(solarReturnChart, muntha, natalChart, panchaVargiyaBala)
        val yearLordHouse = solarReturnChart.planetPositions[yearLord]?.house ?: 1
        val yearLordStrength = evaluatePlanetStrengthDescription(yearLord, solarReturnChart, language)
        val yearLordDignity = getYearLordDignityDescription(yearLord, solarReturnChart, language)
        val triPatakiChakra = calculateTriPatakiChakra(solarReturnChart, language)
        val sahams = calculateSahams(solarReturnChart, language)
        val tajikaAspects = calculateTajikaAspects(solarReturnChart, language)
        val muddaDasha = calculateMuddaDasha(solarReturnChart, solarReturnTime.toLocalDate(), language)
        val housePredictions = generateHousePredictions(solarReturnChart, muntha, yearLord, language)
        val majorThemes = identifyMajorThemes(solarReturnChart, muntha, yearLord, housePredictions, triPatakiChakra, tajikaAspects, language)
        val (favorableMonths, challengingMonths) = calculateMonthlyInfluences(solarReturnChart, solarReturnTime)
        val keyDates = calculateKeyDates(solarReturnChart, solarReturnTime, muddaDasha, language)
        val overallPrediction = generateOverallPrediction(solarReturnChart, yearLord, muntha, tajikaAspects, housePredictions, language)
        val yearRating = calculateYearRating(solarReturnChart, yearLord, muntha, tajikaAspects, housePredictions, language)

        return VarshaphalaResult(
            natalChart = natalChart,
            year = year,
            age = age,
            solarReturnChart = solarReturnChart,
            yearLord = yearLord,
            yearLordStrength = yearLordStrength,
            yearLordHouse = yearLordHouse,
            yearLordDignity = yearLordDignity,
            muntha = muntha,
            panchaVargiyaBala = panchaVargiyaBala,
            triPatakiChakra = triPatakiChakra,
            sahams = sahams,
            tajikaAspects = tajikaAspects,
            muddaDasha = muddaDasha,
            housePredictions = housePredictions,
            majorThemes = majorThemes,
            favorableMonths = favorableMonths,
            challengingMonths = challengingMonths,
            overallPrediction = overallPrediction,
            yearRating = yearRating,
            keyDates = keyDates
        )
    }

    private fun calculateSolarReturnTime(
        birthDateTime: LocalDateTime,
        targetYear: Int,
        latitude: Double,
        longitude: Double,
        timezone: String
    ): LocalDateTime {
        val birthZoned = ZonedDateTime.of(birthDateTime, ZoneId.of(timezone))
        val birthUtc = birthZoned.withZoneSameInstant(ZoneId.of("UTC"))
        val birthJd = calculateJulianDay(birthUtc.toLocalDateTime())

        val natalSunLong = getPlanetLongitude(SweConst.SE_SUN, birthJd)

        val yearsElapsed = targetYear - birthDateTime.year
        val approximateJd = birthJd + (yearsElapsed * SIDEREAL_YEAR_DAYS)
        var currentJd = approximateJd

        repeat(50) {
            val currentSunLong = getPlanetLongitude(SweConst.SE_SUN, currentJd)
            var diff = natalSunLong - currentSunLong

            while (diff > 180.0) diff -= 360.0
            while (diff < -180.0) diff += 360.0

            if (abs(diff) < 0.0000001) {
                return jdToLocalDateTime(currentJd, timezone)
            }

            val sunSpeed = getSunSpeed(currentJd)
            val correction = diff / sunSpeed
            currentJd += correction

            if (abs(correction) < 0.00001) {
                return jdToLocalDateTime(currentJd, timezone)
            }
        }

        return jdToLocalDateTime(currentJd, timezone)
    }

    private fun getPlanetLongitude(planetId: Int, julianDay: Double): Double {
        val xx = DoubleArray(6)
        val serr = StringBuffer()
        swissEph.swe_calc_ut(julianDay, planetId, SEFLG_SIDEREAL or SEFLG_SPEED, xx, serr)
        return normalizeAngle(xx[0])
    }

    private fun getSunSpeed(julianDay: Double): Double {
        val xx = DoubleArray(6)
        val serr = StringBuffer()
        swissEph.swe_calc_ut(julianDay, SweConst.SE_SUN, SEFLG_SIDEREAL or SEFLG_SPEED, xx, serr)
        return if (xx[3] != 0.0) xx[3] else 0.9856
    }

    private fun calculateSolarReturnChart(
        solarReturnTime: LocalDateTime,
        latitude: Double,
        longitude: Double,
        timezone: String,
        year: Int
    ): SolarReturnChart {
        val zonedDateTime = ZonedDateTime.of(solarReturnTime, ZoneId.of(timezone))
        val utcDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"))
        val julianDay = calculateJulianDay(utcDateTime.toLocalDateTime())
        val ayanamsa = swissEph.swe_get_ayanamsa_ut(julianDay)

        val cusps = DoubleArray(14)
        val ascmc = DoubleArray(10)
        swissEph.swe_houses(julianDay, SEFLG_SIDEREAL, latitude, longitude, 'W'.code, cusps, ascmc)

        val houseCusps = (1..12).map { normalizeAngle(cusps[it]) }
        val ascendantDegree = normalizeAngle(cusps[1])
        val ascendant = getZodiacSignFromLongitude(ascendantDegree)
        val midheaven = normalizeAngle(ascmc[1])

        val planetPositions = mutableMapOf<Planet, SolarReturnPlanetPosition>()
        for (planet in Planet.MAIN_PLANETS) {
            val position = calculateSolarReturnPlanetPosition(planet, julianDay, ascendantDegree)
            planetPositions[planet] = position
        }

        val sunPos = planetPositions[Planet.SUN]?.longitude ?: 0.0
        val isDayBirth = isDayChart(sunPos, ascendantDegree)

        val moonLong = planetPositions[Planet.MOON]?.longitude ?: 0.0
        val moonSign = getZodiacSignFromLongitude(moonLong)
        val (moonNakshatra, _) = Nakshatra.fromLongitude(moonLong)

        return SolarReturnChart(
            year = year,
            solarReturnTime = solarReturnTime,
            solarReturnTimeUtc = utcDateTime.toLocalDateTime(),
            julianDay = julianDay,
            planetPositions = planetPositions,
            ascendant = ascendant,
            ascendantDegree = ascendantDegree % 30.0,
            midheaven = midheaven,
            houseCusps = houseCusps,
            ayanamsa = ayanamsa,
            isDayBirth = isDayBirth,
            moonSign = moonSign,
            moonNakshatra = moonNakshatra.displayName
        )
    }

    private fun calculateSolarReturnPlanetPosition(
        planet: Planet,
        julianDay: Double,
        ascendantLongitude: Double
    ): SolarReturnPlanetPosition {
        val xx = DoubleArray(6)
        val serr = StringBuffer()

        val planetId = when (planet) {
            Planet.KETU -> -1
            else -> planet.swissEphId
        }

        if (planetId >= 0) {
            swissEph.swe_calc_ut(julianDay, planetId, SEFLG_SIDEREAL or SEFLG_SPEED, xx, serr)
        } else {
            swissEph.swe_calc_ut(julianDay, SweConst.SE_MEAN_NODE, SEFLG_SIDEREAL or SEFLG_SPEED, xx, serr)
            xx[0] = normalizeAngle(xx[0] + 180.0)
            xx[3] = -xx[3]
        }

        val longitude = normalizeAngle(xx[0])
        val sign = getZodiacSignFromLongitude(longitude)
        val house = calculateWholeSignHouse(longitude, ascendantLongitude)
        val degree = longitude % 30.0
        val (nakshatra, pada) = Nakshatra.fromLongitude(longitude)
        val isRetrograde = xx[3] < 0
        val speed = xx[3]

        return SolarReturnPlanetPosition(
            longitude = longitude,
            sign = sign,
            house = house,
            degree = degree,
            nakshatra = nakshatra.displayName,
            nakshatraPada = pada,
            isRetrograde = isRetrograde,
            speed = speed
        )
    }

    private fun determineYearLord(
        solarReturnChart: SolarReturnChart,
        muntha: MunthaResult,
        natalChart: VedicChart,
        allBalas: List<PanchaVargiyaBala>
    ): Planet {
        val dayOfWeek = solarReturnChart.solarReturnTime.dayOfWeek
        val dayIndex = when (dayOfWeek) {
            DayOfWeek.SUNDAY -> 0
            DayOfWeek.MONDAY -> 1
            DayOfWeek.TUESDAY -> 2
            DayOfWeek.WEDNESDAY -> 3
            DayOfWeek.THURSDAY -> 4
            DayOfWeek.FRIDAY -> 5
            DayOfWeek.SATURDAY -> 6
        }
        val dinaPati = DAY_LORDS[dayIndex]
        val lagnaPati = solarReturnChart.ascendant.ruler
        val munthaPati = muntha.lord
        val natalMoonSign = natalChart.planetPositions.find { it.planet == Planet.MOON }?.sign
            ?: ZodiacSign.ARIES
        val janmaRashiPati = natalMoonSign.ruler

        val candidates = listOf(dinaPati, lagnaPati, munthaPati, janmaRashiPati).distinct()

        val candidatesWithStrength = candidates.map { planet ->
            val bala = allBalas.find { it.planet == planet }?.total ?: 0.0
            val additionalStrength = calculateAdditionalStrength(planet, solarReturnChart)
            planet to (bala + additionalStrength)
        }

        return candidatesWithStrength.maxByOrNull { it.second }?.first ?: dinaPati
    }

    private fun calculateAdditionalStrength(planet: Planet, chart: SolarReturnChart): Double {
        var strength = 0.0
        val position = chart.planetPositions[planet] ?: return 0.0

        when (position.house) {
            1, 4, 7, 10 -> strength += 5.0
            5, 9 -> strength += 3.0
            6, 8, 12 -> strength -= 3.0
        }

        if (position.sign.ruler == planet) strength += 4.0
        if (isExalted(planet, position.sign)) strength += 5.0
        if (isDebilitated(planet, position.sign)) strength -= 5.0
        if (!position.isRetrograde) strength += 1.0

        return strength
    }

    private fun calculateMuntha(
        natalChart: VedicChart,
        age: Int,
        solarReturnChart: SolarReturnChart,
        language: Language
    ): MunthaResult {
        val natalAscLongitude = normalizeAngle(natalChart.ascendant)
        val progressedLongitude = normalizeAngle(natalAscLongitude + (age * 30.0))
        val munthaSign = getZodiacSignFromLongitude(progressedLongitude)
        val degreeInSign = progressedLongitude % 30.0
        val ascendantLongitude = getStandardZodiacIndex(solarReturnChart.ascendant) * 30.0 + solarReturnChart.ascendantDegree
        val munthaHouse = calculateWholeSignHouse(progressedLongitude, ascendantLongitude)
        val munthaLord = munthaSign.ruler

        val lordPosition = solarReturnChart.planetPositions[munthaLord]
        val lordHouse = lordPosition?.house ?: 1
        val lordStrength = evaluatePlanetStrengthDescription(munthaLord, solarReturnChart, language)

        val themes = getMunthaThemes(munthaHouse, language)
        val interpretation = generateMunthaInterpretation(munthaSign, munthaHouse, munthaLord, lordHouse, lordStrength, language)

        return MunthaResult(
            longitude = progressedLongitude,
            sign = munthaSign,
            house = munthaHouse,
            degree = degreeInSign,
            lord = munthaLord,
            lordHouse = lordHouse,
            lordStrength = lordStrength,
            interpretation = interpretation,
            themes = themes
        )
    }

    private fun getMunthaThemes(house: Int, language: Language): List<String> {
        val keys = when (house) {
            1 -> listOf(StringKeyAnalysis.MUNTHA_PERSONAL_GROWTH, StringKeyAnalysis.MUNTHA_NEW_BEGINNINGS, StringKeyAnalysis.MUNTHA_HEALTH_FOCUS)
            2 -> listOf(StringKeyAnalysis.MUNTHA_FINANCIAL_GAINS, StringKeyAnalysis.MUNTHA_FAMILY_MATTERS, StringKeyAnalysis.MUNTHA_SPEECH)
            3 -> listOf(StringKeyAnalysis.MUNTHA_COMMUNICATION, StringKeyAnalysis.MUNTHA_SHORT_TRAVELS, StringKeyAnalysis.MUNTHA_SIBLINGS)
            4 -> listOf(StringKeyAnalysis.MUNTHA_HOME_AFFAIRS, StringKeyAnalysis.MUNTHA_PROPERTY, StringKeyAnalysis.MUNTHA_INNER_PEACE)
            5 -> listOf(StringKeyAnalysis.MUNTHA_CREATIVITY, StringKeyAnalysis.MUNTHA_ROMANCE, StringKeyAnalysis.MUNTHA_CHILDREN)
            6 -> listOf(StringKeyAnalysis.MUNTHA_SERVICE, StringKeyAnalysis.MUNTHA_HEALTH_ISSUES, StringKeyAnalysis.MUNTHA_COMPETITION)
            7 -> listOf(StringKeyAnalysis.MUNTHA_PARTNERSHIPS, StringKeyAnalysis.MUNTHA_MARRIAGE, StringKeyAnalysis.MUNTHA_BUSINESS)
            8 -> listOf(StringKeyAnalysis.MUNTHA_TRANSFORMATION, StringKeyAnalysis.MUNTHA_RESEARCH, StringKeyAnalysis.MUNTHA_INHERITANCE)
            9 -> listOf(StringKeyAnalysis.MUNTHA_FORTUNE, StringKeyAnalysis.MUNTHA_LONG_TRAVEL, StringKeyAnalysis.MUNTHA_HIGHER_LEARNING)
            10 -> listOf(StringKeyAnalysis.MUNTHA_CAREER_ADVANCEMENT, StringKeyAnalysis.MUNTHA_RECOGNITION, StringKeyAnalysis.MUNTHA_AUTHORITY)
            11 -> listOf(StringKeyAnalysis.MUNTHA_GAINS, StringKeyAnalysis.MUNTHA_FRIENDS, StringKeyAnalysis.MUNTHA_FULFILLED_WISHES)
            12 -> listOf(StringKeyAnalysis.MUNTHA_SPIRITUALITY, StringKeyAnalysis.MUNTHA_FOREIGN_LANDS, StringKeyAnalysis.MUNTHA_EXPENSES)
            else -> listOf(StringKeyAnalysis.MUNTHA_GENERAL_GROWTH)
        }
        return keys.map { StringResources.get(it, language) }
    }

    private fun generateMunthaInterpretation(
        sign: ZodiacSign,
        house: Int,
        lord: Planet,
        lordHouse: Int,
        lordStrength: String,
        language: Language
    ): String {
        val houseSignificance = getHouseSignificance(house, language)
        val lordQuality = when (lordStrength) {
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language),
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language) -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_EXCELLENT, language)

            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_MODERATE, language),
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_ANGULAR, language) -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_FAVORABLE, language)

            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_DEBILITATED, language) -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_CHALLENGING, language)
            else -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_BALANCED, language)
        }

        return StringResources.get(StringKeyAnalysis.VARSHA_MUNTHA_DIRECTS, language, house, sign.getLocalizedName(language), houseSignificance.lowercase()) +
                " " + StringResources.get(StringKeyAnalysis.VARSHA_PERIOD_WELL_SUPPORTED, language) // Simplified for now, can be improved
    }

    private fun calculateAllPanchaVargiyaBalas(chart: SolarReturnChart, language: Language): List<PanchaVargiyaBala> {
        return Planet.MAIN_PLANETS.filter { it != Planet.RAHU && it != Planet.KETU }
            .map { calculatePanchaVargiyaBala(it, chart, language) }
    }

    private fun calculatePanchaVargiyaBala(planet: Planet, chart: SolarReturnChart, language: Language): PanchaVargiyaBala {
        val position = chart.planetPositions[planet]
            ?: return PanchaVargiyaBala(planet, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_UNKNOWN, language))

        val longitude = normalizeAngle(position.longitude)

        val uchcha = calculateUchchaBala(planet, longitude)
        val hadda = calculateHaddaBala(planet, position.sign, position.degree)
        val dreshkana = calculateDrekkanaBala(planet, position.sign, position.degree)
        val navamsha = calculateNavamshaBala(planet, longitude)
        val dwadashamsha = calculateDwadashamshabala(planet, longitude)

        val total = uchcha + hadda + dreshkana + navamsha + dwadashamsha

        val category = when {
            total >= 15 -> StringResources.get(StringKeyAnalysis.PANCHA_EXCELLENT, language)
            total >= 12 -> StringResources.get(StringKeyAnalysis.PANCHA_GOOD, language)
            total >= 8 -> StringResources.get(StringKeyAnalysis.PANCHA_AVERAGE, language)
            total >= 5 -> StringResources.get(StringKeyAnalysis.PANCHA_BELOW_AVERAGE, language)
            else -> StringResources.get(StringKeyAnalysis.PANCHA_WEAK, language)
        }

        return PanchaVargiyaBala(
            planet = planet,
            uchcha = uchcha,
            hadda = hadda,
            dreshkana = dreshkana,
            navamsha = navamsha,
            dwadashamsha = dwadashamsha,
            total = total,
            category = category
        )
    }

    private fun calculateUchchaBala(planet: Planet, longitude: Double): Double {
        val exaltationPoint = EXALTATION_DEGREES[planet] ?: return 0.0
        val normalizedLong = normalizeAngle(longitude)
        val diff = abs(normalizeAngle(normalizedLong - exaltationPoint))
        val adjustedDiff = if (diff > 180) 360 - diff else diff
        return ((180 - adjustedDiff) / 180.0 * 5.0).coerceIn(0.0, 5.0)
    }

    private fun calculateHaddaBala(planet: Planet, sign: ZodiacSign, degree: Double): Double {
        val haddaRanges = HADDA_LORDS[sign] ?: return 2.0

        for ((start, end, lord) in haddaRanges) {
            if (degree >= start && degree < end) {
                return when {
                    lord == planet -> 4.0
                    areFriends(planet, lord) -> 3.0
                    areNeutral(planet, lord) -> 2.0
                    else -> 1.0
                }
            }
        }
        return 2.0
    }

        private fun calculateDrekkanaBala(planet: Planet, sign: ZodiacSign, degree: Double): Double {
        val drekkanaNumber = when {
            degree < 10 -> 1
            degree < 20 -> 2
            else -> 3
        }

        val signIndex = getStandardZodiacIndex(sign)
        val drekkanaSignIndex = (signIndex + (drekkanaNumber - 1) * 4) % 12
        val drekkanaSign = STANDARD_ZODIAC_SIGNS[drekkanaSignIndex]
        val drekkanaLord = drekkanaSign.ruler

        return when {
            drekkanaLord == planet -> 4.0
            areFriends(planet, drekkanaLord) -> 3.0
            areNeutral(planet, drekkanaLord) -> 2.0
            else -> 1.0
        }
    }

    private fun calculateNavamshaBala(planet: Planet, longitude: Double): Double {
        val normalizedLong = normalizeAngle(longitude)
        val degreeInSign = normalizedLong % 30.0
        val navamshaIndex = (degreeInSign / 3.333333).toInt().coerceIn(0, 8)
        val signIndex = (normalizedLong / 30.0).toInt().coerceIn(0, 11)

        val startSign = when (signIndex % 4) {
            0 -> 0
            1 -> 9
            2 -> 6
            else -> 3
        }

        val navamshaSignIndex = (startSign + navamshaIndex) % 12
        val navamshaLord = STANDARD_ZODIAC_SIGNS[navamshaSignIndex].ruler

        return when {
            navamshaLord == planet -> 4.0
            areFriends(planet, navamshaLord) -> 3.0
            areNeutral(planet, navamshaLord) -> 2.0
            else -> 1.0
        }
    }

    private fun calculateDwadashamshabala(planet: Planet, longitude: Double): Double {
        val normalizedLong = normalizeAngle(longitude)
        val degreeInSign = normalizedLong % 30.0
        val d12Index = (degreeInSign / 2.5).toInt().coerceIn(0, 11)
        val signIndex = (normalizedLong / 30.0).toInt().coerceIn(0, 11)
        val d12SignIndex = (signIndex + d12Index) % 12
        val d12Lord = STANDARD_ZODIAC_SIGNS[d12SignIndex].ruler

        return when {
            d12Lord == planet -> 3.0
            areFriends(planet, d12Lord) -> 2.5
            areNeutral(planet, d12Lord) -> 1.5
            else -> 1.0
        }
    }

    private fun calculateTriPatakiChakra(chart: SolarReturnChart, language: Language): TriPatakiChakra {
        val ascIndex = getStandardZodiacIndex(chart.ascendant)

        val dharmaSigns = listOf(
            STANDARD_ZODIAC_SIGNS[ascIndex],
            STANDARD_ZODIAC_SIGNS[(ascIndex + 4) % 12],
            STANDARD_ZODIAC_SIGNS[(ascIndex + 8) % 12]
        )

        val arthaSigns = listOf(
            STANDARD_ZODIAC_SIGNS[(ascIndex + 1) % 12],
            STANDARD_ZODIAC_SIGNS[(ascIndex + 5) % 12],
            STANDARD_ZODIAC_SIGNS[(ascIndex + 9) % 12]
        )

        val kamaSigns = listOf(
            STANDARD_ZODIAC_SIGNS[(ascIndex + 2) % 12],
            STANDARD_ZODIAC_SIGNS[(ascIndex + 6) % 12],
            STANDARD_ZODIAC_SIGNS[(ascIndex + 10) % 12]
        )

        fun getPlanetsInSector(signs: List<ZodiacSign>): List<Planet> {
            return chart.planetPositions.filter { (_, pos) -> pos.sign in signs }.keys.toList()
        }

        val dharmaPlanets = getPlanetsInSector(dharmaSigns)
        val arthaPlanets = getPlanetsInSector(arthaSigns)
        val kamaPlanets = getPlanetsInSector(kamaSigns)

        val sectors = listOf(
            TriPatakiSector(
                name = StringResources.get(StringKeyAnalysis.TRI_PATAKI_DHARMA, language),
                signs = dharmaSigns,
                planets = dharmaPlanets,
                influence = generateSectorInfluence(StringResources.get(StringKeyAnalysis.TRI_PATAKI_DHARMA, language), dharmaPlanets, language)
            ),
            TriPatakiSector(
                name = StringResources.get(StringKeyAnalysis.TRI_PATAKI_ARTHA, language),
                signs = arthaSigns,
                planets = arthaPlanets,
                influence = generateSectorInfluence(StringResources.get(StringKeyAnalysis.TRI_PATAKI_ARTHA, language), arthaPlanets, language)
            ),
            TriPatakiSector(
                name = StringResources.get(StringKeyAnalysis.TRI_PATAKI_KAMA, language),
                signs = kamaSigns,
                planets = kamaPlanets,
                influence = generateSectorInfluence(StringResources.get(StringKeyAnalysis.TRI_PATAKI_KAMA, language), kamaPlanets, language)
            )
        )

        val dominantSector = sectors.maxByOrNull { it.planets.size }
        val dominantInfluence = when {
            dominantSector?.name == StringResources.get(StringKeyAnalysis.TRI_PATAKI_DHARMA, language) -> StringResources.get(StringKeyAnalysis.TRI_PATAKI_DHARMA_DESC, language)
            dominantSector?.name == StringResources.get(StringKeyAnalysis.TRI_PATAKI_ARTHA, language) -> StringResources.get(StringKeyAnalysis.TRI_PATAKI_ARTHA_DESC, language)
            dominantSector?.name == StringResources.get(StringKeyAnalysis.TRI_PATAKI_KAMA, language) -> StringResources.get(StringKeyAnalysis.TRI_PATAKI_KAMA_DESC, language)
            else -> StringResources.get(StringKeyAnalysis.TRI_PATAKI_BALANCED, language)
        }

        val interpretation = buildTriPatakiInterpretation(sectors, language)

        return TriPatakiChakra(
            risingSign = chart.ascendant,
            sectors = sectors,
            dominantInfluence = dominantInfluence,
            interpretation = interpretation
        )
    }

    private fun generateSectorInfluence(sectorName: String, planets: List<Planet>, language: Language): String {
        if (planets.isEmpty()) {
            return StringResources.get(StringKeyAnalysis.TRI_PATAKI_QUIET, language, sectorName)
        }

        val benefics = planets.filter { it in listOf(Planet.JUPITER, Planet.VENUS, Planet.MOON, Planet.MERCURY) }
        val malefics = planets.filter { it in listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU) }

        return when {
            benefics.size > malefics.size -> StringResources.get(StringKeyAnalysis.TRI_PATAKI_FAVORABLE, language, benefics.joinToString { it.getLocalizedName(language) })
            malefics.size > benefics.size -> StringResources.get(StringKeyAnalysis.TRI_PATAKI_CHALLENGING, language, malefics.joinToString { it.getLocalizedName(language) })
            else -> StringResources.get(StringKeyAnalysis.TRI_PATAKI_VARIABLE, language)
        }
    }

    private fun buildTriPatakiInterpretation(sectors: List<TriPatakiSector>, language: Language): String {
        val interpretations = mutableListOf<String>()

        sectors.forEach { sector ->
            if (sector.planets.isNotEmpty()) {
                val areaName = when {
                    sector.name.contains(StringResources.get(StringKeyAnalysis.TRI_PATAKI_DHARMA, language)) -> StringResources.get(StringKeyAnalysis.TRI_PATAKI_DHARMA_AREA, language)
                    sector.name.contains(StringResources.get(StringKeyAnalysis.TRI_PATAKI_ARTHA, language)) -> StringResources.get(StringKeyAnalysis.TRI_PATAKI_ARTHA_AREA, language)
                    else -> StringResources.get(StringKeyAnalysis.TRI_PATAKI_KAMA_AREA, language)
                }
                interpretations.add(StringResources.get(StringKeyAnalysis.TRI_PATAKI_EMPHASIS, language, sector.planets.size, sector.name, areaName))
            }
        }

        return if (interpretations.isNotEmpty()) {
            interpretations.joinToString(" ")
        } else {
            StringResources.get(StringKeyAnalysis.TRI_PATAKI_BALANCED_DESC, language)
        }
    }

    private fun calculateSahams(chart: SolarReturnChart, language: Language): List<SahamResult> {
        val sahams = mutableListOf<SahamResult>()
        val isDayBirth = chart.isDayBirth

        val sunLong = chart.planetPositions[Planet.SUN]?.longitude ?: 0.0
        val moonLong = chart.planetPositions[Planet.MOON]?.longitude ?: 0.0
        val marsLong = chart.planetPositions[Planet.MARS]?.longitude ?: 0.0
        val mercuryLong = chart.planetPositions[Planet.MERCURY]?.longitude ?: 0.0
        val jupiterLong = chart.planetPositions[Planet.JUPITER]?.longitude ?: 0.0
        val venusLong = chart.planetPositions[Planet.VENUS]?.longitude ?: 0.0
        val saturnLong = chart.planetPositions[Planet.SATURN]?.longitude ?: 0.0
        val ascLong = getStandardZodiacIndex(chart.ascendant) * 30.0 + chart.ascendantDegree

        val sahamFormulas = listOf(
            Triple(SahamType.PUNYA, { if (isDayBirth) moonLong + ascLong - sunLong else sunLong + ascLong - moonLong }, "Moon + Asc - Sun"),
            Triple(SahamType.VIDYA, { if (isDayBirth) mercuryLong + ascLong - sunLong else sunLong + ascLong - mercuryLong }, "Mercury + Asc - Sun"),
            Triple(SahamType.YASHAS, { if (isDayBirth) jupiterLong + ascLong - sunLong else sunLong + ascLong - jupiterLong }, "Jupiter + Asc - Sun"),
            Triple(SahamType.MITRA, { if (isDayBirth) moonLong + ascLong - mercuryLong else mercuryLong + ascLong - moonLong }, "Moon + Asc - Mercury"),
            Triple(SahamType.DHANA, { if (isDayBirth) jupiterLong + ascLong - moonLong else moonLong + ascLong - jupiterLong }, "Jupiter + Asc - Moon"),
            Triple(SahamType.KARMA, { if (isDayBirth) saturnLong + ascLong - sunLong else sunLong + ascLong - saturnLong }, "Saturn + Asc - Sun"),
            Triple(SahamType.VIVAHA, { if (isDayBirth) venusLong + ascLong - saturnLong else saturnLong + ascLong - venusLong }, "Venus + Asc - Saturn"),
            Triple(SahamType.PUTRA, { if (isDayBirth) jupiterLong + ascLong - moonLong else moonLong + ascLong - jupiterLong }, "Jupiter + Asc - Moon"),
            Triple(SahamType.PITRI, { if (isDayBirth) saturnLong + ascLong - sunLong else sunLong + ascLong - saturnLong }, "Saturn + Asc - Sun"),
            Triple(SahamType.MATRI, { if (isDayBirth) moonLong + ascLong - venusLong else venusLong + ascLong - moonLong }, "Moon + Asc - Venus"),
            Triple(SahamType.SAMARTHA, { if (isDayBirth) marsLong + ascLong - saturnLong else saturnLong + ascLong - marsLong }, "Mars + Asc - Saturn"),
            Triple(SahamType.ASHA, { if (isDayBirth) saturnLong + ascLong - venusLong else venusLong + ascLong - saturnLong }, "Saturn + Asc - Venus"),
            Triple(SahamType.ROGA, { if (isDayBirth) saturnLong + ascLong - marsLong else marsLong + ascLong - saturnLong }, "Saturn + Asc - Mars"),
            Triple(SahamType.RAJA, { if (isDayBirth) sunLong + ascLong - saturnLong else saturnLong + ascLong - sunLong }, "Sun + Asc - Saturn"),
            Triple(SahamType.MRITYU, { if (isDayBirth) saturnLong + ascLong - moonLong else moonLong + ascLong - saturnLong }, "Saturn + Asc - Moon"),
            Triple(SahamType.BHRATRI, { if (isDayBirth) jupiterLong + ascLong - saturnLong else saturnLong + ascLong - jupiterLong }, "Jupiter + Asc - Saturn"),
            Triple(SahamType.MAHATMYA, { if (isDayBirth) jupiterLong + ascLong - moonLong else moonLong + ascLong - jupiterLong }, "Jupiter + Asc - Moon"),
            Triple(SahamType.KARYASIDDHI, { if (isDayBirth) saturnLong + ascLong - sunLong else sunLong + ascLong - saturnLong }, "Saturn + Asc - Sun")
        )

        for ((type, formula, formulaStr) in sahamFormulas) {
            try {
                val longitude = normalizeAngle(formula())
                val sign = getZodiacSignFromLongitude(longitude)
                val house = calculateWholeSignHouse(longitude, ascLong)
                val degree = longitude % 30.0
                val lord = sign.ruler
                val lordHouse = chart.planetPositions[lord]?.house ?: 1
                val lordStrength = evaluatePlanetStrengthDescription(lord, chart, language)

                val isActive = isSahamActive(lord, chart, house)
                val interpretation = generateSahamInterpretation(type, sign, house, lord, lordHouse, lordStrength, language)
                val activationPeriods = getSahamActivationPeriods(lord)

                sahams.add(
                    SahamResult(
                        type = type,
                        name = type.getDisplayName(Language.ENGLISH),
                        sanskritName = type.getSanskritName(Language.ENGLISH),
                        formula = formulaStr,
                        longitude = longitude,
                        sign = sign,
                        house = house,
                        degree = degree,
                        lord = lord,
                        lordHouse = lordHouse,
                        lordStrength = lordStrength,
                        interpretation = interpretation,
                        isActive = isActive,
                        activationPeriods = activationPeriods
                    )
                )
            } catch (e: Exception) {
                // Skip sahams that can't be calculated
            }
        }

        return sahams.sortedByDescending { it.isActive }
    }

    private fun isSahamActive(lord: Planet, chart: SolarReturnChart, house: Int): Boolean {
        val lordPosition = chart.planetPositions[lord] ?: return false
        val lordStrength = evaluatePlanetStrengthDescription(lord, chart, Language.ENGLISH)

        val isLordStrong = lordStrength in listOf("Exalted", "Strong", "Angular")
        val isInGoodHouse = house in listOf(1, 2, 4, 5, 7, 9, 10, 11)
        val isLordWellPlaced = lordPosition.house in listOf(1, 4, 5, 7, 9, 10, 11)

        return (isLordStrong && isInGoodHouse) || (isLordWellPlaced && !lordPosition.isRetrograde)
    }

    private fun generateSahamInterpretation(
        type: SahamType,
        sign: ZodiacSign,
        house: Int,
        lord: Planet,
        lordHouse: Int,
        lordStrength: String,
        language: Language
    ): String {
        val lordQuality = when (lordStrength) {
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language),
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language) -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_EXCELLENT, language)

            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_MODERATE, language),
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_ANGULAR, language) -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_FAVORABLE, language)

            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_DEBILITATED, language) -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_CHALLENGING, language)
            else -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_BALANCED, language)
        }

        return StringResources.get(StringKeyAnalysis.VARSHA_SAHAM_RELATES, language, type.getDisplayName(language), sign.getLocalizedName(language), house, type.getDescription(language).lowercase()) +
                " " + StringResources.get(StringKeyAnalysis.VARSHA_SAHAM_LORD_SUPPORT, language, lord.getLocalizedName(language), lordHouse, lordQuality)
    }

    private fun getSahamActivationPeriods(lord: Planet): List<String> {
        val periods = mutableListOf<String>()
        if (lord in MUDDA_DASHA_PLANETS) {
            periods.add("${lord.displayName} Mudda Dasha")
        }
        return periods
    }

    private fun calculateTajikaAspects(chart: SolarReturnChart, language: Language): List<TajikaAspectResult> {
        val aspects = mutableListOf<TajikaAspectResult>()
        val planets = listOf(
            Planet.SUN, Planet.MOON, Planet.MARS, Planet.MERCURY,
            Planet.JUPITER, Planet.VENUS, Planet.SATURN
        )

        val aspectAngles = listOf(0, 60, 90, 120, 180)

        for (i in planets.indices) {
            for (j in (i + 1) until planets.size) {
                val planet1 = planets[i]
                val planet2 = planets[j]

                val pos1 = chart.planetPositions[planet1] ?: continue
                val pos2 = chart.planetPositions[planet2] ?: continue

                val diff = abs(normalizeAngle(pos1.longitude - pos2.longitude))

                for (angle in aspectAngles) {
                    val maxOrb = when (angle) {
                        0 -> CONJUNCTION_ORB
                        60 -> SEXTILE_ORB
                        90 -> SQUARE_ORB
                        120 -> TRINE_ORB
                        180 -> OPPOSITION_ORB
                        else -> 5.0
                    }

                    val actualOrb = abs(diff - angle)
                    val reverseOrb = abs(diff - (360 - angle))
                    val effectiveOrb = min(actualOrb, reverseOrb)

                    if (effectiveOrb <= maxOrb) {
                        val isApplying = determineTajikaApplication(pos1.longitude, pos2.longitude, pos1.speed, pos2.speed)

                        val aspectType = determineTajikaAspectType(
                            planet1, planet2, pos1, pos2, isApplying, effectiveOrb, angle, chart
                        )

                        val strength = calculateAspectStrength(effectiveOrb, maxOrb, angle, isApplying)
                        val relatedHouses = listOf(pos1.house, pos2.house).distinct()
                        val effectDescription = getAspectEffectDescription(aspectType, planet1, planet2, language)
                        val prediction = generateAspectPrediction(aspectType, planet1, planet2, relatedHouses, language)

                        aspects.add(
                            TajikaAspectResult(
                                type = aspectType,
                                planet1 = planet1,
                                planet2 = planet2,
                                planet1Longitude = pos1.longitude,
                                planet2Longitude = pos2.longitude,
                                aspectAngle = angle,
                                orb = effectiveOrb,
                                isApplying = isApplying,
                                strength = strength,
                                relatedHouses = relatedHouses,
                                effectDescription = effectDescription,
                                prediction = prediction
                            )
                        )
                    }
                }
            }
        }

        return aspects.sortedByDescending { it.strength.weight }
    }

    private fun determineTajikaApplication(long1: Double, long2: Double, speed1: Double, speed2: Double): Boolean {
        val diff = normalizeAngle(long2 - long1)
        return if (diff < 180) speed1 > speed2 else speed2 > speed1
    }

    private fun determineTajikaAspectType(
        planet1: Planet,
        planet2: Planet,
        pos1: SolarReturnPlanetPosition,
        pos2: SolarReturnPlanetPosition,
        isApplying: Boolean,
        orb: Double,
        angle: Int,
        chart: SolarReturnChart
    ): TajikaAspectType {
        val isAngular1 = pos1.house in listOf(1, 4, 7, 10)
        val isAngular2 = pos2.house in listOf(1, 4, 7, 10)
        val hasReception = checkMutualReception(planet1, planet2, pos1.sign, pos2.sign)

        return when {
            isApplying && angle == 0 && orb < 3 -> {
                if (isAngular1 || isAngular2) TajikaAspectType.KAMBOOLA else TajikaAspectType.ITHASALA
            }
            isApplying && orb < 5 -> {
                if (hasReception) TajikaAspectType.NAKTA else TajikaAspectType.ITHASALA
            }
            !isApplying && orb < 5 -> TajikaAspectType.EASARAPHA
            pos1.isRetrograde || pos2.isRetrograde -> TajikaAspectType.RADDA
            pos1.speed < pos2.speed && isApplying -> TajikaAspectType.MANAU
            isApplying && hasReception -> TajikaAspectType.MUTHASHILA
            angle == 90 || angle == 180 -> TajikaAspectType.DURAPHA
            isAngular1 && isAngular2 && !isApplying -> TajikaAspectType.GAIRI_KAMBOOLA
            else -> if (isApplying) TajikaAspectType.ITHASALA else TajikaAspectType.EASARAPHA
        }
    }

    private fun checkMutualReception(planet1: Planet, planet2: Planet, sign1: ZodiacSign, sign2: ZodiacSign): Boolean {
        return sign1.ruler == planet2 && sign2.ruler == planet1
    }

    private fun calculateAspectStrength(orb: Double, maxOrb: Double, angle: Int, isApplying: Boolean): AspectStrength {
        val orbRatio = orb / maxOrb
        val angleBonus = when (angle) {
            0, 120 -> 0.2
            60 -> 0.1
            90, 180 -> -0.1
            else -> 0.0
        }
        val applyingBonus = if (isApplying) 0.1 else 0.0

        val strength = 1.0 - orbRatio + angleBonus + applyingBonus

        return when {
            strength >= 0.9 -> AspectStrength.VERY_STRONG
            strength >= 0.7 -> AspectStrength.STRONG
            strength >= 0.5 -> AspectStrength.MODERATE
            strength >= 0.3 -> AspectStrength.WEAK
            else -> AspectStrength.VERY_WEAK
        }
    }

    private fun getAspectEffectDescription(type: TajikaAspectType, planet1: Planet, planet2: Planet, language: Language): String {
        return when (type) {
            TajikaAspectType.ITHASALA -> StringResources.get(StringKeyAnalysis.TAJIKA_ITHASALA_EFFECT, language, planet1.getLocalizedName(language), planet2.getLocalizedName(language))
            TajikaAspectType.EASARAPHA -> StringResources.get(StringKeyAnalysis.TAJIKA_EASARAPHA_EFFECT, language)
            TajikaAspectType.KAMBOOLA -> StringResources.get(StringKeyAnalysis.TAJIKA_KAMBOOLA_EFFECT, language)
            TajikaAspectType.RADDA -> StringResources.get(StringKeyAnalysis.TAJIKA_RADDA_EFFECT, language)
            TajikaAspectType.DURAPHA -> StringResources.get(StringKeyAnalysis.TAJIKA_DURAPHA_EFFECT, language)
            else -> StringResources.get(StringKeyAnalysis.TAJIKA_INFLUENCE_ENERGY, language, type.getDisplayName(language), if (type.isPositive) StringResources.get(StringKeyAnalysis.VARSHA_TONE_SUPPORTIVE, language) else StringResources.get(StringKeyAnalysis.VARSHA_TONE_CHALLENGING, language))
        }
    }

    private fun generateAspectPrediction(type: TajikaAspectType, planet1: Planet, planet2: Planet, houses: List<Int>, language: Language): String {
        val houseStr = houses.joinToString(if (language == Language.NEPALI) " र " else " and ") {
            if (language == Language.NEPALI) "${it}औं भाव" else "House $it"
        }
        val quality = if (type.isPositive) StringResources.get(StringKeyAnalysis.VARSHA_TONE_FAVORABLE, language) else StringResources.get(StringKeyAnalysis.VARSHA_TONE_CHALLENGING, language)

        return StringResources.get(StringKeyAnalysis.TAJIKA_PREDICTION_X_FOR_Y, language, type.getDisplayName(language), planet1.getLocalizedName(language), planet2.getLocalizedName(language), quality, houseStr)
    }

    private fun calculateMuddaDasha(chart: SolarReturnChart, startDate: LocalDate, language: Language): List<MuddaDashaPeriod> {
        val totalDays = 360
        val today = LocalDate.now()

        val moonLong = chart.planetPositions[Planet.MOON]?.longitude ?: 0.0
        val (nakshatra, _) = Nakshatra.fromLongitude(moonLong)
        val startingLord = nakshatra.ruler

        val startIndex = MUDDA_DASHA_PLANETS.indexOf(startingLord).let { 
            if (it >= 0) it else 0 
        }

        val periods = mutableListOf<MuddaDashaPeriod>()
        var currentDate = startDate

        for (i in MUDDA_DASHA_PLANETS.indices) {
            val planetIndex = (startIndex + i) % MUDDA_DASHA_PLANETS.size
            val planet = MUDDA_DASHA_PLANETS[planetIndex]
            val baseDays = MUDDA_DASHA_DAYS[planet] ?: 30
            val days = (baseDays * totalDays / 360).coerceAtLeast(1)

            val endDate = currentDate.plusDays(days.toLong() - 1)
            val isCurrent = !today.isBefore(currentDate) && !today.isAfter(endDate)

            val progressPercent = if (isCurrent) {
                val daysPassed = ChronoUnit.DAYS.between(currentDate, today).toFloat()
                (daysPassed / days).coerceIn(0f, 1f)
            } else if (today.isAfter(endDate)) {
                1f
            } else {
                0f
            }

            val subPeriods = calculateMuddaAntardasha(planet, currentDate, endDate, language)
            val planetStrength = evaluatePlanetStrengthDescription(planet, chart, language)
            val houseRuled = getHousesRuledBy(planet, chart)
            val prediction = generateDashaPrediction(planet, chart, planetStrength, language)
            val keywords = getDashaKeywords(planet, chart, language)

            periods.add(
                MuddaDashaPeriod(
                    planet = planet,
                    startDate = currentDate,
                    endDate = endDate,
                    days = days,
                    subPeriods = subPeriods,
                    planetStrength = planetStrength,
                    houseRuled = houseRuled,
                    prediction = prediction,
                    keywords = keywords,
                    isCurrent = isCurrent,
                    progressPercent = progressPercent
                )
            )

            currentDate = endDate.plusDays(1)
        }

        return periods
    }

    private fun calculateMuddaAntardasha(
        mainPlanet: Planet,
        startDate: LocalDate,
        endDate: LocalDate,
        language: Language
    ): List<MuddaAntardasha> {
        val totalDays = ChronoUnit.DAYS.between(startDate, endDate).toInt().coerceAtLeast(1)
        val subPeriods = mutableListOf<MuddaAntardasha>()

        val startIndex = MUDDA_DASHA_PLANETS.indexOf(mainPlanet).let {
            if (it >= 0) it else 0
        }
        
        var currentDate = startDate
        val subDays = (totalDays / MUDDA_DASHA_PLANETS.size).coerceAtLeast(1)

        for (i in MUDDA_DASHA_PLANETS.indices) {
            val planetIndex = (startIndex + i) % MUDDA_DASHA_PLANETS.size
            val planet = MUDDA_DASHA_PLANETS[planetIndex]

            val actualSubDays = if (i == MUDDA_DASHA_PLANETS.size - 1) {
                ChronoUnit.DAYS.between(currentDate, endDate).toInt().coerceAtLeast(1)
            } else {
                subDays
            }

            val subEndDate = currentDate.plusDays(actualSubDays.toLong() - 1).let {
                if (it.isAfter(endDate)) endDate else it
            }

            subPeriods.add(
                MuddaAntardasha(
                    planet = planet,
                    startDate = currentDate,
                    endDate = subEndDate,
                    days = actualSubDays,
                    interpretation = StringResources.get(StringKeyAnalysis.VARSHA_DASHA_PERIOD_FORMAT, language, mainPlanet.getLocalizedName(language), planet.getLocalizedName(language))
                )
            )

            currentDate = subEndDate.plusDays(1)
            if (currentDate.isAfter(endDate)) break
        }

        return subPeriods
    }

    private fun getHousesRuledBy(planet: Planet, chart: SolarReturnChart): List<Int> {
        val houses = mutableListOf<Int>()
        val ascendantIndex = getStandardZodiacIndex(chart.ascendant)

        for (i in 0..11) {
            val signIndex = (ascendantIndex + i) % 12
            val sign = STANDARD_ZODIAC_SIGNS[signIndex]
            if (sign.ruler == planet) {
                houses.add(i + 1)
            }
        }

        return houses
    }

    private fun generateDashaPrediction(planet: Planet, chart: SolarReturnChart, strength: String, language: Language): String {
        val position = chart.planetPositions[planet]
        val house = position?.house ?: 1

        val planetNature = when (planet) {
            Planet.SUN -> StringResources.get(StringKeyAnalysis.PLANET_NATURE_SUN, language)
            Planet.MOON -> StringResources.get(StringKeyAnalysis.PLANET_NATURE_MOON, language)
            Planet.MARS -> StringResources.get(StringKeyAnalysis.PLANET_NATURE_MARS, language)
            Planet.MERCURY -> StringResources.get(StringKeyAnalysis.PLANET_NATURE_MERCURY, language)
            Planet.JUPITER -> StringResources.get(StringKeyAnalysis.PLANET_NATURE_JUPITER, language)
            Planet.VENUS -> StringResources.get(StringKeyAnalysis.PLANET_NATURE_VENUS, language)
            Planet.SATURN -> StringResources.get(StringKeyAnalysis.PLANET_NATURE_SATURN, language)
            Planet.RAHU -> StringResources.get(StringKeyAnalysis.PLANET_NATURE_RAHU, language)
            Planet.KETU -> StringResources.get(StringKeyAnalysis.PLANET_NATURE_KETU, language)
            else -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_BALANCED, language)
        }

        val houseArea = getHouseSignificance(house, language)

        val strengthQuality = when (strength) {
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language) -> StringResources.get(StringKeyAnalysis.VARSHA_DASHA_EXCEPTIONAL, language)
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language) -> StringResources.get(StringKeyAnalysis.VARSHA_DASHA_SUPPORTED, language)
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_DEBILITATED, language) -> StringResources.get(StringKeyAnalysis.VARSHA_DASHA_CHALLENGING, language)
            else -> StringResources.get(StringKeyAnalysis.VARSHA_DASHA_MIXED, language)
        }

        return StringResources.get(StringKeyAnalysis.VARSHA_DASHA_PREDICTION_FORMAT, language, planet.getLocalizedName(language), planetNature, houseArea, strengthQuality)
    }

    private fun getDashaKeywords(planet: Planet, chart: SolarReturnChart, language: Language): List<String> {
        val position = chart.planetPositions[planet]
        val house = position?.house ?: 1

        val planetKeywords = when (planet) {
            Planet.SUN -> listOf(StringKeyAnalysis.KEYWORD_LEADERSHIP, StringKeyAnalysis.KEYWORD_VITALITY, StringKeyAnalysis.KEYWORD_FATHER)
            Planet.MOON -> listOf(StringKeyAnalysis.KEYWORD_EMOTIONS, StringKeyAnalysis.KEYWORD_MOTHER, StringKeyAnalysis.KEYWORD_PUBLIC)
            Planet.MARS -> listOf(StringKeyAnalysis.KEYWORD_ACTION, StringKeyAnalysis.KEYWORD_ENERGY, StringKeyAnalysis.KEYWORD_COURAGE)
            Planet.MERCURY -> listOf(StringKeyAnalysis.KEYWORD_COMMUNICATION, StringKeyAnalysis.KEYWORD_LEARNING, StringKeyAnalysis.KEYWORD_BUSINESS)
            Planet.JUPITER -> listOf(StringKeyAnalysis.KEYWORD_WISDOM, StringKeyAnalysis.KEYWORD_GROWTH, StringKeyAnalysis.KEYWORD_FORTUNE)
            Planet.VENUS -> listOf(StringKeyAnalysis.KEYWORD_LOVE, StringKeyAnalysis.KEYWORD_ART, StringKeyAnalysis.KEYWORD_COMFORT)
            Planet.SATURN -> listOf(StringKeyAnalysis.KEYWORD_DISCIPLINE, StringKeyAnalysis.KEYWORD_KARMA, StringKeyAnalysis.KEYWORD_DELAYS)
            Planet.RAHU -> listOf(StringKeyAnalysis.KEYWORD_AMBITION, StringKeyAnalysis.KEYWORD_INNOVATION, StringKeyAnalysis.KEYWORD_FOREIGN)
            Planet.KETU -> listOf(StringKeyAnalysis.KEYWORD_SPIRITUALITY, StringKeyAnalysis.KEYWORD_DETACHMENT, StringKeyAnalysis.KEYWORD_PAST)
            else -> listOf(StringKeyAnalysis.KEYWORD_GENERAL)
        }

        val houseKeywords = getHouseKeywords(house, language)

        return (planetKeywords.map { StringResources.get(it, language) } + houseKeywords).take(5)
    }

    private fun generateHousePredictions(
        chart: SolarReturnChart,
        muntha: MunthaResult,
        yearLord: Planet,
        language: Language
    ): List<HousePrediction> {
        val predictions = mutableListOf<HousePrediction>()
        val ascIndex = getStandardZodiacIndex(chart.ascendant)

        for (house in 1..12) {
            val signIndex = (ascIndex + house - 1) % 12
            val sign = STANDARD_ZODIAC_SIGNS[signIndex]
            val houseLord = sign.ruler
            val lordPosition = chart.planetPositions[houseLord]?.house ?: 1

            val planetsInHouse = chart.planetPositions.filter { (_, pos) -> pos.house == house }.keys.toList()

            val strength = calculateHouseStrength(house, houseLord, lordPosition, planetsInHouse, chart, muntha, yearLord, language)
            val keywords = getHouseKeywords(house, language)
            val prediction = generateHousePrediction(house, sign, houseLord, lordPosition, planetsInHouse, chart, muntha, yearLord, language)
            val rating = calculateHouseRating(house, houseLord, lordPosition, planetsInHouse, chart, muntha, yearLord, language)
            val specificEvents = generateSpecificEvents(house, houseLord, lordPosition, planetsInHouse, chart, language)

            predictions.add(
                HousePrediction(
                    house = house,
                    signOnCusp = sign,
                    houseLord = houseLord,
                    lordPosition = lordPosition,
                    planetsInHouse = planetsInHouse,
                    strength = strength,
                    keywords = keywords,
                    prediction = prediction,
                    rating = rating,
                    specificEvents = specificEvents
                )
            )
        }

        return predictions
    }

    private fun calculateHouseStrength(
        house: Int,
        lord: Planet,
        lordPosition: Int,
        planetsInHouse: List<Planet>,
        chart: SolarReturnChart,
        muntha: MunthaResult,
        yearLord: Planet,
        language: Language
    ): String {
        var score = 0

        val beneficPositions = listOf(1, 2, 4, 5, 7, 9, 10, 11)
        if (lordPosition in beneficPositions) score += 2

        val lordStrength = evaluatePlanetStrengthDescription(lord, chart, language)
        when (lordStrength) {
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language) -> score += 3
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language) -> score += 2
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_ANGULAR, language) -> score += 1
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_DEBILITATED, language) -> score -= 2
        }

        val benefics = listOf(Planet.JUPITER, Planet.VENUS, Planet.MOON)
        val malefics = listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU)

        planetsInHouse.forEach { planet ->
            if (planet in benefics) score += 1
            if (planet in malefics) score -= 1
        }

        if (muntha.house == house) score += 2
        if (yearLord == lord) score += 1

        return when {
            score >= 5 -> StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXCELLENT, language)
            score >= 3 -> StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language)
            score >= 1 -> StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_MODERATE, language)
            score >= -1 -> StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_WEAK, language)
            else -> StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_CHALLENGED, language)
        }
    }

    private fun getHouseKeywords(house: Int, language: Language): List<String> {
        val keys = when (house) {
            1 -> listOf(StringKeyAnalysis.KEYWORD_SELF, StringKeyAnalysis.KEYWORD_PERSONALITY, StringKeyAnalysis.KEYWORD_HEALTH, StringKeyAnalysis.KEYWORD_APPEARANCE, StringKeyAnalysis.KEYWORD_NEW_BEGINNINGS)
            2 -> listOf(StringKeyAnalysis.KEYWORD_WEALTH, StringKeyAnalysis.KEYWORD_FAMILY, StringKeyAnalysis.KEYWORD_SPEECH, StringKeyAnalysis.KEYWORD_VALUES, StringKeyAnalysis.KEYWORD_FOOD)
            3 -> listOf(StringKeyAnalysis.KEYWORD_SIBLINGS, StringKeyAnalysis.KEYWORD_COURAGE, StringKeyAnalysis.KEYWORD_COMMUNICATION, StringKeyAnalysis.KEYWORD_SHORT_TRAVEL, StringKeyAnalysis.KEYWORD_SKILLS)
            4 -> listOf(StringKeyAnalysis.KEYWORD_HOME, StringKeyAnalysis.KEYWORD_MOTHER, StringKeyAnalysis.KEYWORD_PROPERTY, StringKeyAnalysis.KEYWORD_VEHICLES, StringKeyAnalysis.KEYWORD_INNER_PEACE)
            5 -> listOf(StringKeyAnalysis.KEYWORD_CHILDREN, StringKeyAnalysis.KEYWORD_INTELLIGENCE, StringKeyAnalysis.KEYWORD_ROMANCE, StringKeyAnalysis.KEYWORD_CREATIVITY, StringKeyAnalysis.KEYWORD_INVESTMENTS)
            6 -> listOf(StringKeyAnalysis.KEYWORD_ENEMIES, StringKeyAnalysis.KEYWORD_HEALTH_ISSUES, StringKeyAnalysis.KEYWORD_SERVICE, StringKeyAnalysis.KEYWORD_DEBTS, StringKeyAnalysis.KEYWORD_COMPETITION)
            7 -> listOf(StringKeyAnalysis.KEYWORD_MARRIAGE, StringKeyAnalysis.KEYWORD_PARTNERSHIP, StringKeyAnalysis.KEYWORD_BUSINESS, StringKeyAnalysis.KEYWORD_PUBLIC_DEALINGS, StringKeyAnalysis.KEYWORD_CONTRACTS)
            8 -> listOf(StringKeyAnalysis.KEYWORD_LONGEVITY, StringKeyAnalysis.KEYWORD_TRANSFORMATION, StringKeyAnalysis.KEYWORD_RESEARCH, StringKeyAnalysis.KEYWORD_INHERITANCE, StringKeyAnalysis.KEYWORD_HIDDEN_MATTERS)
            9 -> listOf(StringKeyAnalysis.KEYWORD_FORTUNE, StringKeyAnalysis.KEYWORD_FATHER, StringKeyAnalysis.KEYWORD_RELIGION, StringKeyAnalysis.KEYWORD_HIGHER_EDUCATION, StringKeyAnalysis.KEYWORD_LONG_TRAVEL)
            10 -> listOf(StringKeyAnalysis.KEYWORD_CAREER, StringKeyAnalysis.KEYWORD_STATUS, StringKeyAnalysis.KEYWORD_AUTHORITY, StringKeyAnalysis.KEYWORD_GOVERNMENT, StringKeyAnalysis.KEYWORD_FAME)
            11 -> listOf(StringKeyAnalysis.KEYWORD_GAINS, StringKeyAnalysis.KEYWORD_INCOME, StringKeyAnalysis.KEYWORD_FRIENDS, StringKeyAnalysis.KEYWORD_ELDER_SIBLINGS, StringKeyAnalysis.KEYWORD_ASPIRATIONS)
            12 -> listOf(StringKeyAnalysis.KEYWORD_LOSSES, StringKeyAnalysis.KEYWORD_EXPENSES, StringKeyAnalysis.KEYWORD_SPIRITUALITY, StringKeyAnalysis.KEYWORD_FOREIGN_LANDS, StringKeyAnalysis.KEYWORD_LIBERATION)
            else -> listOf(StringKeyAnalysis.KEYWORD_GENERAL)
        }
        return keys.map { StringResources.get(it, language) }
    }

    private fun generateHousePrediction(
        house: Int,
        sign: ZodiacSign,
        lord: Planet,
        lordPosition: Int,
        planetsInHouse: List<Planet>,
        chart: SolarReturnChart,
        muntha: MunthaResult,
        yearLord: Planet,
        language: Language
    ): String {
        val houseArea = getHouseSignificance(house, language)
        val lordStrength = evaluatePlanetStrengthDescription(lord, chart, language)

        val lordAnalysis = buildString {
            append(StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_LORD_POSITION, language, lord.getLocalizedName(language), lordPosition))
            append(" ")
            append(
                when (lordStrength) {
                    StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language) -> StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_LORD_EXCELLENT, language)
                    StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language) -> StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_LORD_STRONG, language)
                    StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_MODERATE, language) -> StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_LORD_MODERATE, language)
                    StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_DEBILITATED, language) -> StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_LORD_CHALLENGED, language)
                    else -> StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_LORD_VARIABLE, language)
                }
            )
        }

        val planetaryInfluence = if (planetsInHouse.isNotEmpty()) {
            val benefics = planetsInHouse.filter { it in listOf(Planet.JUPITER, Planet.VENUS, Planet.MOON) }
            val malefics = planetsInHouse.filter { it in listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU) }

            when {
                benefics.isNotEmpty() && malefics.isEmpty() ->
                    " " + StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_BENEFICS_ENHANCE, language, benefics.joinToString { it.getLocalizedName(language) })
                malefics.isNotEmpty() && benefics.isEmpty() ->
                    " " + StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_MALEFICS_CHALLENGE, language, malefics.joinToString { it.getLocalizedName(language) })
                benefics.isNotEmpty() && malefics.isNotEmpty() ->
                    " " + StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_MIXED_INF, language, planetsInHouse.joinToString { it.getLocalizedName(language) })
                else -> ""
            }
        } else {
            " " + StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_LORD_DEPENDENT, language)
        }

        val specialIndications = buildString {
            if (muntha.house == house) append(" " + StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_MUNTHA_EMPHASIS, language))
            if (yearLord == lord) append(" " + StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_YEARLORD_RULE, language))
        }

        return StringResources.get(StringKeyAnalysis.VARSHA_HOUSE_PREDICTION_FORMAT, language, house, sign.getLocalizedName(language), houseArea, lordAnalysis, planetaryInfluence, specialIndications).trim()
    }

    private fun calculateHouseRating(
        house: Int,
        lord: Planet,
        lordPosition: Int,
        planetsInHouse: List<Planet>,
        chart: SolarReturnChart,
        muntha: MunthaResult,
        yearLord: Planet,
        language: Language
    ): Float {
        var rating = 3.0f

        val beneficLordPositions = listOf(1, 2, 4, 5, 7, 9, 10, 11)
        if (lordPosition in beneficLordPositions) rating += 0.5f

        val lordStrength = evaluatePlanetStrengthDescription(lord, chart, language)
        rating += when (lordStrength) {
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language) -> 1.0f
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language) -> 0.7f
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_ANGULAR, language) -> 0.3f
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_DEBILITATED, language) -> -0.8f
            else -> 0.0f
        }

        planetsInHouse.forEach { planet ->
            when (planet) {
                Planet.JUPITER -> rating += 0.5f
                Planet.VENUS -> rating += 0.4f
                Planet.MOON -> rating += 0.2f
                Planet.SATURN -> rating -= 0.3f
                Planet.MARS -> rating -= 0.2f
                Planet.RAHU, Planet.KETU -> rating -= 0.2f
                else -> {}
            }
        }

        if (muntha.house == house) rating += 0.5f
        if (yearLord == lord) rating += 0.3f

        return rating.coerceIn(1.0f, 5.0f)
    }

    private fun generateSpecificEvents(
        house: Int,
        lord: Planet,
        lordPosition: Int,
        planetsInHouse: List<Planet>,
        chart: SolarReturnChart,
        language: Language
    ): List<String> {
        val events = mutableListOf<String>()
        val lordStrength = evaluatePlanetStrengthDescription(lord, chart, language)
        val isLordStrong = lordStrength in listOf(StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language), StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language))

        when (house) {
            1 -> {
                if (isLordStrong) {
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_VITALITY, language))
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_NEW_VENTURES, language))
                }
                if (Planet.JUPITER in planetsInHouse) events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_SPIRITUAL_GROWTH, language))
                if (Planet.MARS in planetsInHouse) events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_INCREASED_ENERGY, language))
            }
            2 -> {
                if (isLordStrong) {
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_FINANCIAL_GAINS, language))
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_FAMILY_RELATIONS, language))
                }
                if (Planet.VENUS in planetsInHouse) events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_LUXURY_ACQUISITION, language))
            }
            5 -> {
                if (isLordStrong) {
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_CREATIVE_SUCCESS, language))
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_CHILDREN_MATTERS, language))
                }
                if (Planet.JUPITER in planetsInHouse) events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_ACADEMIC_SUCCESS, language))
                if (Planet.VENUS in planetsInHouse) events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_ROMANTIC_HAPPINESS, language))
            }
            7 -> {
                if (isLordStrong) {
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_PARTNERSHIP_STRENGTH, language))
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_MARRIAGE_FAVORABLE, language))
                }
                if (Planet.VENUS in planetsInHouse) events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_ROMANTIC_FULFILLMENT, language))
            }
            10 -> {
                if (isLordStrong) {
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_CAREER_ADVANCEMENT, language))
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_AUTHORITY_RECOGNITION, language))
                }
                if (Planet.SUN in planetsInHouse) events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_GOVERNMENT_FAVOR, language))
            }
            11 -> {
                if (isLordStrong) {
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_DESIRE_FULFILLMENT, language))
                    events.add(StringResources.get(StringKeyAnalysis.VARSHA_EVENT_MULTIPLE_GAINS, language))
                }
            }
        }

        return events.take(4)
    }

    private fun identifyMajorThemes(
        chart: SolarReturnChart,
        muntha: MunthaResult,
        yearLord: Planet,
        housePredictions: List<HousePrediction>,
        triPataki: TriPatakiChakra,
        tajikaAspects: List<TajikaAspectResult>,
        language: Language
    ): List<String> {
        val themes = mutableListOf<String>()

        val yearLordHouse = chart.planetPositions[yearLord]?.house ?: 1
        themes.add(StringResources.get(StringKeyAnalysis.VARSHA_THEME_YEARLORD, language, yearLord.getLocalizedName(language), getHouseSignificance(yearLordHouse, language)))

        themes.add(StringResources.get(StringKeyAnalysis.VARSHA_THEME_MUNTHA, language, muntha.house, muntha.themes.firstOrNull() ?: StringResources.get(StringKeyAnalysis.MUNTHA_GENERAL_GROWTH, language)))

        themes.add(StringResources.get(StringKeyAnalysis.VARSHA_THEME_TRIPATAKI, language, triPataki.dominantInfluence))

        housePredictions.filter { it.strength in listOf(StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXCELLENT, language), StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language)) }
            .sortedByDescending { it.rating }
            .take(2)
            .forEach { themes.add(StringResources.get(StringKeyAnalysis.VARSHA_THEME_FAVORABLE, language, getHouseSignificance(it.house, language), it.house)) }

        val positiveAspects = tajikaAspects.count { it.type.isPositive }
        val totalAspects = tajikaAspects.size
        if (totalAspects > 0) {
            val aspectQuality = if (positiveAspects > totalAspects / 2) StringResources.get(StringKeyAnalysis.VARSHA_TONE_SUPPORTIVE, language) else StringResources.get(StringKeyAnalysis.VARSHA_TONE_CHALLENGING, language)
            themes.add(StringResources.get(StringKeyAnalysis.VARSHA_THEME_TAJIKA, language, aspectQuality, positiveAspects, totalAspects))
        }

        return themes.take(6)
    }

    private fun calculateMonthlyInfluences(
        chart: SolarReturnChart,
        solarReturnTime: LocalDateTime
    ): Pair<List<Int>, List<Int>> {
        val favorableMonths = mutableListOf<Int>()
        val challengingMonths = mutableListOf<Int>()

        val yearLord = chart.ascendant.ruler
        val yearLordHouse = chart.planetPositions[yearLord]?.house ?: 1

        for (monthOffset in 0..11) {
            val month = ((solarReturnTime.monthValue - 1 + monthOffset) % 12) + 1
            val transitHouse = (yearLordHouse + monthOffset - 1) % 12 + 1

            val isFavorable = transitHouse in listOf(1, 2, 4, 5, 7, 9, 10, 11)

            if (isFavorable && favorableMonths.size < 4) {
                favorableMonths.add(month)
            } else if (!isFavorable && challengingMonths.size < 3) {
                challengingMonths.add(month)
            }
        }

        return Pair(favorableMonths, challengingMonths)
    }

    private fun calculateKeyDates(
        chart: SolarReturnChart,
        solarReturnTime: LocalDateTime,
        muddaDasha: List<MuddaDashaPeriod>,
        language: Language
    ): List<KeyDate> {
        val keyDates = mutableListOf<KeyDate>()

        keyDates.add(
            KeyDate(
                date = solarReturnTime.toLocalDate(),
                event = StringResources.get(StringKeyAnalysis.VARSHA_EVENT_SOLAR_RETURN, language),
                type = KeyDateType.IMPORTANT,
                description = StringResources.get(StringKeyAnalysis.VARSHA_EVENT_SOLAR_RETURN_DESC, language)
            )
        )

        muddaDasha.forEach { period ->
            keyDates.add(
                KeyDate(
                    date = period.startDate,
                    event = StringResources.get(StringKeyAnalysis.VARSHA_EVENT_DASHA_BEGINS, language, period.planet.getLocalizedName(language)),
                    type = if (period.planetStrength in listOf(StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language), StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language)))
                        KeyDateType.FAVORABLE else KeyDateType.IMPORTANT,
                    description = StringResources.get(StringKeyAnalysis.VARSHA_EVENT_DASHA_BEGINS_DESC, language, period.planet.getLocalizedName(language), period.days)
                )
            )
        }

        return keyDates.sortedBy { it.date }.take(15)
    }

    private fun generateOverallPrediction(
        chart: SolarReturnChart,
        yearLord: Planet,
        muntha: MunthaResult,
        tajikaAspects: List<TajikaAspectResult>,
        housePredictions: List<HousePrediction>,
        language: Language
    ): String {
        val yearLordStrength = evaluatePlanetStrengthDescription(yearLord, chart, language)
        val yearLordHouse = chart.planetPositions[yearLord]?.house ?: 1

        val strongHouses = housePredictions.filter { it.strength in listOf(StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXCELLENT, language), StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language)) }
        val weakHouses = housePredictions.filter { it.strength in listOf(StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_WEAK, language), StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_CHALLENGED, language)) }

        val positiveAspectsCount = tajikaAspects.count { it.type.isPositive }
        val challengingAspectsCount = tajikaAspects.size - positiveAspectsCount

        val overallTone = when {
            yearLordStrength in listOf(StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language), StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language)) && strongHouses.size >= 6 -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_EXCELLENT, language)
            yearLordStrength in listOf(StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language), StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language)) && strongHouses.size >= 4 -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_FAVORABLE, language)
            strongHouses.size > weakHouses.size -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_POSITIVE, language)
            weakHouses.size > strongHouses.size -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_CHALLENGING_GROWTH, language)
            else -> StringResources.get(StringKeyAnalysis.VARSHA_TONE_BALANCED, language)
        }

        val yearLordInfluence = when (yearLord) {
            Planet.SUN -> StringResources.get(StringKeyAnalysis.VARSHA_YEARLORD_SUN, language)
            Planet.MOON -> StringResources.get(StringKeyAnalysis.VARSHA_YEARLORD_MOON, language)
            Planet.MARS -> StringResources.get(StringKeyAnalysis.VARSHA_YEARLORD_MARS, language)
            Planet.MERCURY -> StringResources.get(StringKeyAnalysis.VARSHA_YEARLORD_MERCURY, language)
            Planet.JUPITER -> StringResources.get(StringKeyAnalysis.VARSHA_YEARLORD_JUPITER, language)
            Planet.VENUS -> StringResources.get(StringKeyAnalysis.VARSHA_YEARLORD_VENUS, language)
            Planet.SATURN -> StringResources.get(StringKeyAnalysis.VARSHA_YEARLORD_SATURN, language)
            else -> StringResources.get(StringKeyAnalysis.VARSHA_YEARLORD_GENERAL, language)
        }

        val munthaTheme = muntha.themes.firstOrNull() ?: StringResources.get(StringKeyAnalysis.MUNTHA_GENERAL_GROWTH, language)
        val munthaInfluence = StringResources.get(StringKeyAnalysis.VARSHA_MUNTHA_INFLUENCE, language, muntha.house, muntha.sign.getLocalizedName(language), munthaTheme.lowercase())

        return buildString {
            append(StringResources.get(StringKeyAnalysis.VARSHA_OVERALL_TONE, language, overallTone.lowercase()))
            append(" ")
            append(yearLordInfluence)
            append(" ")
            append(munthaInfluence)
            append(" ")
            append(StringResources.get(StringKeyAnalysis.VARSHA_ASPECT_SUMMARY, language, positiveAspectsCount, challengingAspectsCount))
            append(" ")
            append(StringResources.get(StringKeyAnalysis.VARSHA_POTENTIAL_MAXIMIZED, language))
        }
    }

    private fun calculateYearRating(
        chart: SolarReturnChart,
        yearLord: Planet,
        muntha: MunthaResult,
        tajikaAspects: List<TajikaAspectResult>,
        housePredictions: List<HousePrediction>,
        language: Language
    ): Float {
        var rating = 3.0f

        val yearLordStrength = evaluatePlanetStrengthDescription(yearLord, chart, language)
        rating += when (yearLordStrength) {
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language) -> 0.8f
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language) -> 0.5f
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_ANGULAR, language) -> 0.3f
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_DEBILITATED, language) -> -0.5f
            else -> 0.0f
        }

        rating += when (muntha.lordStrength) {
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language),
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language) -> 0.3f
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_MODERATE, language) -> 0.1f
            StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_DEBILITATED, language) -> -0.3f
            else -> 0.0f
        }

        if (muntha.house in listOf(1, 2, 4, 5, 9, 10, 11)) rating += 0.2f

        val positiveAspects = tajikaAspects.count { it.type.isPositive && it.strength.weight >= 0.6 }
        val negativeAspects = tajikaAspects.count { !it.type.isPositive && it.strength.weight >= 0.6 }
        rating += (positiveAspects * 0.1f - negativeAspects * 0.1f).coerceIn(-0.5f, 0.5f)

        val averageHouseRating = if (housePredictions.isNotEmpty()) {
            housePredictions.map { it.rating }.average().toFloat()
        } else 3.0f
        rating += (averageHouseRating - 3.0f) * 0.3f

        val beneficsAngular = chart.planetPositions.count { (planet, pos) ->
            planet in listOf(Planet.JUPITER, Planet.VENUS) && pos.house in listOf(1, 4, 7, 10)
        }
        rating += beneficsAngular * 0.15f

        return rating.coerceIn(1.0f, 5.0f)
    }

    private fun calculateJulianDay(dateTime: LocalDateTime): Double {
        var y = dateTime.year
        var m = dateTime.monthValue
        val d = dateTime.dayOfMonth + dateTime.hour / 24.0 + dateTime.minute / 1440.0 + dateTime.second / 86400.0

        if (m <= 2) {
            y -= 1
            m += 12
        }

        val a = y / 100
        val b = 2 - a + a / 4

        return (365.25 * (y + 4716)).toLong() + (30.6001 * (m + 1)).toLong() + d + b - 1524.5
    }

    private fun jdToLocalDateTime(julianDay: Double, timezone: String): LocalDateTime {
        val z = floor(julianDay + 0.5).toLong()
        val f = julianDay + 0.5 - z

        val a = if (z < 2299161) z else {
            val alpha = floor((z - 1867216.25) / 36524.25).toLong()
            z + 1 + alpha - alpha / 4
        }

        val b = a + 1524
        val c = floor((b - 122.1) / 365.25).toLong()
        val d = floor(365.25 * c).toLong()
        val e = floor((b - d) / 30.6001).toLong()

        val day = (b - d - floor(30.6001 * e)).toInt()
        val month = if (e < 14) e - 1 else e - 13
        val year = if (month > 2) c - 4716 else c - 4715

        val totalHours = f * 24.0
        val hour = totalHours.toInt()
        val totalMinutes = (totalHours - hour) * 60.0
        val minute = totalMinutes.toInt()
        val second = ((totalMinutes - minute) * 60.0).toInt()

        val utcDateTime = LocalDateTime.of(year.toInt(), month.toInt(), day, hour, minute, second)
        val utcZoned = ZonedDateTime.of(utcDateTime, ZoneId.of("UTC"))
        val localZoned = utcZoned.withZoneSameInstant(ZoneId.of(timezone))

        return localZoned.toLocalDateTime()
    }

    /**
     * Normalize angle using centralized utility.
     */
    private fun normalizeAngle(angle: Double): Double = VedicAstrologyUtils.normalizeAngle(angle)

    private fun getZodiacSignFromLongitude(longitude: Double): ZodiacSign {
        val normalizedLong = normalizeAngle(longitude)
        val signIndex = (normalizedLong / 30.0).toInt().coerceIn(0, 11)
        return STANDARD_ZODIAC_SIGNS[signIndex]
    }

    private fun getStandardZodiacIndex(sign: ZodiacSign): Int {
        val index = STANDARD_ZODIAC_SIGNS.indexOf(sign)
        return if (index >= 0) index else 0
    }

    private fun calculateWholeSignHouse(longitude: Double, ascendantLongitude: Double): Int {
        val normalizedLong = normalizeAngle(longitude)
        val normalizedAsc = normalizeAngle(ascendantLongitude)
        val ascSign = (normalizedAsc / 30.0).toInt().coerceIn(0, 11)
        val planetSign = (normalizedLong / 30.0).toInt().coerceIn(0, 11)
        val house = ((planetSign - ascSign + 12) % 12) + 1
        return house.coerceIn(1, 12)
    }

    private fun isDayChart(sunLongitude: Double, ascendant: Double): Boolean {
        val normalizedSun = normalizeAngle(sunLongitude)
        val normalizedAsc = normalizeAngle(ascendant)
        val sunSign = (normalizedSun / 30.0).toInt().coerceIn(0, 11)
        val ascSign = (normalizedAsc / 30.0).toInt().coerceIn(0, 11)
        val houseOfSun = ((sunSign - ascSign + 12) % 12) + 1
        return houseOfSun in listOf(7, 8, 9, 10, 11, 12, 1)
    }

    private fun getYearLordDignityDescription(planet: Planet, chart: SolarReturnChart, language: Language): String {
        val position = chart.planetPositions[planet] ?: return StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_UNKNOWN, language)

        val dignityDetails = mutableListOf<String>()

        when {
            isExalted(planet, position.sign) -> dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_EXALTED, language, position.sign.getLocalizedName(language)))
            OWN_SIGNS[planet]?.contains(position.sign) == true -> dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_OWN_SIGN, language, position.sign.getLocalizedName(language)))
            isDebilitated(planet, position.sign) -> dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_DEBILITATED, language, position.sign.getLocalizedName(language)))
            else -> {
                val signLord = position.sign.ruler
                when {
                    areFriends(planet, signLord) -> dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_FRIENDLY, language, position.sign.getLocalizedName(language)))
                    areNeutral(planet, signLord) -> dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_NEUTRAL, language, position.sign.getLocalizedName(language)))
                    else -> dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_ENEMY, language, position.sign.getLocalizedName(language)))
                }
            }
        }

        when (position.house) {
            1, 4, 7, 10 -> dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_KENDRA, language))
            5, 9 -> dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_TRIKONA, language))
            2, 11 -> dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_GAINS, language))
            3, 6 -> dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_UPACHAYA, language))
            8, 12 -> dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_DUSTHANA, language))
        }

        if (position.isRetrograde) {
            dignityDetails.add(StringResources.get(StringKeyAnalysis.DIGNITY_RETROGRADE, language))
        }

        val dignityStr = dignityDetails.joinToString(", ")
        return StringResources.get(StringKeyAnalysis.DIGNITY_YEARLORD_DESC, language, planet.getLocalizedName(language), dignityStr)
    }

    private fun evaluatePlanetStrengthDescription(planet: Planet, chart: SolarReturnChart, language: Language): String {
        val position = chart.planetPositions[planet] ?: return StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_UNKNOWN, language)
        val sign = position.sign

        return when {
            isExalted(planet, sign) -> StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_EXALTED, language)
            isDebilitated(planet, sign) -> StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_DEBILITATED, language)
            OWN_SIGNS[planet]?.contains(sign) == true -> StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_STRONG, language)
            position.house in listOf(1, 4, 7, 10) -> StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_ANGULAR, language)
            position.isRetrograde -> StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_RETROGRADE, language)
            else -> StringResources.get(StringKeyAnalysis.VARSHA_STRENGTH_MODERATE, language)
        }
    }

    private fun isExalted(planet: Planet, sign: ZodiacSign): Boolean {
        val exaltationDegree = EXALTATION_DEGREES[planet] ?: return false
        val exaltationSign = getZodiacSignFromLongitude(exaltationDegree)
        return sign == exaltationSign
    }

    private fun isDebilitated(planet: Planet, sign: ZodiacSign): Boolean {
        return DEBILITATION_SIGNS[planet] == sign
    }

    private fun areFriends(planet1: Planet, planet2: Planet): Boolean {
        return FRIENDSHIPS[planet1]?.contains(planet2) == true
    }

    private fun areNeutral(planet1: Planet, planet2: Planet): Boolean {
        return NEUTRALS[planet1]?.contains(planet2) == true
    }

    private fun getHouseSignificance(house: Int, language: Language): String {
        val key = when (house) {
            1 -> StringKeyAnalysis.VARSHA_HOUSE_1_SIG
            2 -> StringKeyAnalysis.VARSHA_HOUSE_2_SIG
            3 -> StringKeyAnalysis.VARSHA_HOUSE_3_SIG
            4 -> StringKeyAnalysis.VARSHA_HOUSE_4_SIG
            5 -> StringKeyAnalysis.VARSHA_HOUSE_5_SIG
            6 -> StringKeyAnalysis.VARSHA_HOUSE_6_SIG
            7 -> StringKeyAnalysis.VARSHA_HOUSE_7_SIG
            8 -> StringKeyAnalysis.VARSHA_HOUSE_8_SIG
            9 -> StringKeyAnalysis.VARSHA_HOUSE_9_SIG
            10 -> StringKeyAnalysis.VARSHA_HOUSE_10_SIG
            11 -> StringKeyAnalysis.VARSHA_HOUSE_11_SIG
            12 -> StringKeyAnalysis.VARSHA_HOUSE_12_SIG
            else -> StringKeyAnalysis.VARSHA_HOUSE_GENERAL_SIG
        }
        return StringResources.get(key, language)
    }

    private fun getOrdinalSuffix(n: Int): String {
        return when {
            n in 11..13 -> "th"
            n % 10 == 1 -> "st"
            n % 10 == 2 -> "nd"
            n % 10 == 3 -> "rd"
            else -> "th"
        }
    }

    fun getHouseMeaning(house: Int, language: Language = Language.ENGLISH): String = getHouseSignificance(house, language)

    fun getHouseKeywordsExternal(house: Int, language: Language = Language.ENGLISH): List<String> = getHouseKeywords(house, language)
}
