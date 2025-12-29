package com.astro.storm.ephemeris

import com.astro.storm.data.model.Nakshatra
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Kalachakra Dasha Calculator - Production-grade Implementation
 *
 * Kalachakra Dasha is a highly respected but complex Nakshatra-based dasha system
 * from Brihat Parashara Hora Shastra (BPHS, Chapter 45). It is particularly useful
 * for timing health events, spiritual transformations, and life's major transitions.
 *
 * ## Key Principles (from BPHS)
 *
 * ### 1. Savya (Clockwise) and Apsavya (Anti-clockwise) Groups
 * The 27 Nakshatras are divided into groups that determine the direction of counting:
 * - **Savya (Direct/Clockwise)**: Ashwini, Bharani, Rohini, Mrigashira, Pushya, Ashlesha,
 *   Purva Phalguni, Uttara Phalguni, Hasta, Chitra, Swati, Anuradha, Purva Ashadha,
 *   Uttara Ashadha (1st half), Uttara Bhadrapada, Revati
 * - **Apsavya (Retrograde/Anti-clockwise)**: Krittika, Ardra, Punarvasu, Magha,
 *   Uttara Ashadha (2nd half), Shravana, Dhanishtha, Shatabhisha, Purva Bhadrapada
 *
 * ### 2. Deha (Body) and Jeeva (Soul) Rashis
 * Each Nakshatra pada has associated Deha and Jeeva rashis:
 * - **Deha Rashi**: Represents physical body, health, material matters
 * - **Jeeva Rashi**: Represents soul, consciousness, spiritual matters
 * Transit of benefics over Jeeva and malefics avoiding Deha is favorable.
 *
 * ### 3. Dasha Sequence and Periods
 * The dasha periods follow the Kalachakra pattern:
 * - Each sign rules for a specific period (7, 16, 9, 21, 5, 9, 16, 7, 10 years)
 * - The sequence follows the Navamsa pattern within each pada
 * - Total cycle varies based on the nakshatra group
 *
 * ### 4. Pada-based Calculation
 * The starting point is determined by Moon's Nakshatra and Pada at birth.
 * Each of the 4 padas has a specific starting rashi and direction.
 *
 * ## References
 * - BPHS Chapter 45: Kalachakra Dasha Adhyaya
 * - Jataka Parijata on alternative dasha systems
 * - Uttara Kalamrita on Kalachakra application
 * - Dr. K.S. Charak's works on Dasha systems
 *
 * @author AstroStorm
 */
object KalachakraDashaCalculator {

    private val MATH_CONTEXT = DashaUtils.MATH_CONTEXT
    private val DAYS_PER_YEAR = DashaUtils.DAYS_PER_YEAR

    // ============================================
    // KALACHAKRA DASHA CONSTANTS (from BPHS)
    // ============================================

    /**
     * Dasha periods for each sign in Kalachakra (in years)
     * This follows the specific pattern laid out in BPHS Chapter 45
     *
     * The pattern is: Aries(7), Taurus(16), Gemini(9), Cancer(21),
     * Leo(5), Virgo(9), Libra(16), Scorpio(7), Sagittarius(10)
     *
     * Note: Capricorn, Aquarius, Pisces follow the reverse of first 9 signs
     * in Apsavya movement
     */
    private val KALACHAKRA_PERIODS = mapOf(
        ZodiacSign.ARIES to 7,
        ZodiacSign.TAURUS to 16,
        ZodiacSign.GEMINI to 9,
        ZodiacSign.CANCER to 21,
        ZodiacSign.LEO to 5,
        ZodiacSign.VIRGO to 9,
        ZodiacSign.LIBRA to 16,
        ZodiacSign.SCORPIO to 7,
        ZodiacSign.SAGITTARIUS to 10,
        ZodiacSign.CAPRICORN to 10, // Mirror of Sagittarius
        ZodiacSign.AQUARIUS to 7,   // Mirror of Scorpio
        ZodiacSign.PISCES to 16     // Mirror of Libra
    )

    /**
     * Total cycle duration based on the 9-sign sequence (100 years)
     */
    private const val TOTAL_SAVYA_CYCLE_YEARS = 100 // 7+16+9+21+5+9+16+7+10
    private const val TOTAL_APSAVYA_CYCLE_YEARS = 100

    /**
     * Nakshatra group assignments - Savya (Direct) nakshatras
     * These follow clockwise progression through signs
     */
    private val SAVYA_NAKSHATRAS = setOf(
        Nakshatra.ASHWINI,
        Nakshatra.BHARANI,
        Nakshatra.ROHINI,
        Nakshatra.MRIGASHIRA,
        Nakshatra.PUSHYA,
        Nakshatra.ASHLESHA,
        Nakshatra.PURVA_PHALGUNI,
        Nakshatra.UTTARA_PHALGUNI,
        Nakshatra.HASTA,
        Nakshatra.CHITRA,
        Nakshatra.SWATI,
        Nakshatra.ANURADHA,
        Nakshatra.PURVA_ASHADHA,
        Nakshatra.UTTARA_BHADRAPADA,
        Nakshatra.REVATI
    )

    /**
     * Nakshatra group assignments - Apsavya (Retrograde) nakshatras
     * These follow anti-clockwise progression through signs
     */
    private val APSAVYA_NAKSHATRAS = setOf(
        Nakshatra.KRITTIKA,
        Nakshatra.ARDRA,
        Nakshatra.PUNARVASU,
        Nakshatra.MAGHA,
        Nakshatra.SHRAVANA,
        Nakshatra.DHANISHTHA,
        Nakshatra.SHATABHISHA,
        Nakshatra.PURVA_BHADRAPADA
    )

    /**
     * Special handling for Uttara Ashadha (split between Savya and Apsavya)
     * First 2 padas are Savya, last 2 padas are Apsavya
     */
    private val UTTARA_ASHADHA_SAVYA_PADAS = setOf(1, 2)
    private val UTTARA_ASHADHA_APSAVYA_PADAS = setOf(3, 4)

    /**
     * Starting signs for each Nakshatra Pada in Savya group
     * Based on BPHS Kalachakra mapping
     */
    private val SAVYA_PADA_STARTING_SIGNS = mapOf(
        // Group 1: Fire nakshatras starting from Aries
        "FIRE_1" to ZodiacSign.ARIES,
        "FIRE_2" to ZodiacSign.CANCER,
        "FIRE_3" to ZodiacSign.LIBRA,
        "FIRE_4" to ZodiacSign.CAPRICORN,
        // Group 2: Earth nakshatras starting from Taurus
        "EARTH_1" to ZodiacSign.TAURUS,
        "EARTH_2" to ZodiacSign.LEO,
        "EARTH_3" to ZodiacSign.SCORPIO,
        "EARTH_4" to ZodiacSign.AQUARIUS,
        // Group 3: Air nakshatras starting from Gemini
        "AIR_1" to ZodiacSign.GEMINI,
        "AIR_2" to ZodiacSign.VIRGO,
        "AIR_3" to ZodiacSign.SAGITTARIUS,
        "AIR_4" to ZodiacSign.PISCES
    )

    /**
     * Deha-Jeeva pairs for each sign
     * These are crucial for health and spiritual predictions
     */
    private val DEHA_JEEVA_PAIRS = mapOf(
        ZodiacSign.ARIES to Pair(ZodiacSign.ARIES, ZodiacSign.SAGITTARIUS),
        ZodiacSign.TAURUS to Pair(ZodiacSign.TAURUS, ZodiacSign.CAPRICORN),
        ZodiacSign.GEMINI to Pair(ZodiacSign.GEMINI, ZodiacSign.AQUARIUS),
        ZodiacSign.CANCER to Pair(ZodiacSign.CANCER, ZodiacSign.PISCES),
        ZodiacSign.LEO to Pair(ZodiacSign.LEO, ZodiacSign.ARIES),
        ZodiacSign.VIRGO to Pair(ZodiacSign.VIRGO, ZodiacSign.TAURUS),
        ZodiacSign.LIBRA to Pair(ZodiacSign.LIBRA, ZodiacSign.GEMINI),
        ZodiacSign.SCORPIO to Pair(ZodiacSign.SCORPIO, ZodiacSign.CANCER),
        ZodiacSign.SAGITTARIUS to Pair(ZodiacSign.SAGITTARIUS, ZodiacSign.LEO),
        ZodiacSign.CAPRICORN to Pair(ZodiacSign.CAPRICORN, ZodiacSign.VIRGO),
        ZodiacSign.AQUARIUS to Pair(ZodiacSign.AQUARIUS, ZodiacSign.LIBRA),
        ZodiacSign.PISCES to Pair(ZodiacSign.PISCES, ZodiacSign.SCORPIO)
    )

    // ============================================
    // DATA CLASSES
    // ============================================

    /**
     * Complete Kalachakra Dasha analysis result
     */
    data class KalachakraDashaResult(
        val birthNakshatra: Nakshatra,
        val birthNakshatraPada: Int,
        val nakshatraGroup: NakshatraGroup,
        val startingSign: ZodiacSign,
        val dehaRashi: ZodiacSign,
        val jeevaRashi: ZodiacSign,
        val mahadashas: List<KalachakraMahadasha>,
        val currentMahadasha: KalachakraMahadasha?,
        val currentAntardasha: KalachakraAntardasha?,
        val dehaJeevaAnalysis: DehaJeevaAnalysis,
        val interpretation: KalachakraInterpretation,
        val applicabilityScore: Int // 0-100: how applicable is this system
    )

    /**
     * Nakshatra group type
     */
    enum class NakshatraGroup(val displayName: String, val description: String) {
        SAVYA("Savya (Direct)", "Clockwise progression through signs - generally smoother life flow"),
        APSAVYA("Apsavya (Retrograde)", "Anti-clockwise progression - more karmic intensity and transformation")
    }

    /**
     * Kalachakra Mahadasha period
     */
    data class KalachakraMahadasha(
        val sign: ZodiacSign,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val durationYears: Int,
        val signLord: Planet,
        val dehaRashi: ZodiacSign,
        val jeevaRashi: ZodiacSign,
        val antardashas: List<KalachakraAntardasha>,
        val isParamaAyushSign: Boolean, // Special longevity indicator
        val healthIndicator: HealthIndicator,
        val interpretation: MahadashaInterpretation
    ) {
        val durationDays: Long
            get() = ChronoUnit.DAYS.between(startDate, endDate)

        fun isActiveOn(date: LocalDate): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDate.now())

        fun getAntardashaOn(date: LocalDate): KalachakraAntardasha? {
            return antardashas.find { it.isActiveOn(date) }
        }

        fun getProgressPercent(asOf: LocalDate = LocalDate.now()): Double {
            if (durationDays <= 0) return 0.0
            val elapsed = ChronoUnit.DAYS.between(startDate, asOf.coerceIn(startDate, endDate))
            return ((elapsed.toDouble() / durationDays) * 100).coerceIn(0.0, 100.0)
        }

        fun getRemainingDays(asOf: LocalDate = LocalDate.now()): Long {
            if (asOf.isAfter(endDate)) return 0
            if (asOf.isBefore(startDate)) return durationDays
            return ChronoUnit.DAYS.between(asOf, endDate)
        }
    }

    /**
     * Kalachakra Antardasha (sub-period)
     */
    data class KalachakraAntardasha(
        val sign: ZodiacSign,
        val mahadashaSign: ZodiacSign,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val durationDays: Long,
        val signLord: Planet,
        val isDehaSign: Boolean,
        val isJeevaSign: Boolean,
        val interpretation: String
    ) {
        val durationMonths: Double
            get() = durationDays / 30.4375

        fun isActiveOn(date: LocalDate): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDate.now())

        fun getProgressPercent(asOf: LocalDate = LocalDate.now()): Double {
            if (durationDays <= 0) return 0.0
            val elapsed = ChronoUnit.DAYS.between(startDate, asOf.coerceIn(startDate, endDate))
            return ((elapsed.toDouble() / durationDays) * 100).coerceIn(0.0, 100.0)
        }
    }

    /**
     * Health indicator for the dasha period
     */
    enum class HealthIndicator(val displayName: String, val description: String, val score: Int) {
        EXCELLENT("Excellent", "Very favorable for health and vitality", 5),
        GOOD("Good", "Generally supportive of health", 4),
        MODERATE("Moderate", "Mixed health indications", 3),
        CHALLENGING("Challenging", "Need to take care of health", 2),
        CRITICAL("Critical", "Extra caution needed - follow remedies", 1)
    }

    /**
     * Deha-Jeeva analysis for health and spiritual matters
     */
    data class DehaJeevaAnalysis(
        val dehaRashi: ZodiacSign,
        val jeevaRashi: ZodiacSign,
        val dehaLord: Planet,
        val jeevaLord: Planet,
        val dehaLordStrength: String,
        val jeevaLordStrength: String,
        val dehaJeevaRelationship: DehaJeevaRelationship,
        val healthPrediction: String,
        val spiritualPrediction: String,
        val recommendations: List<String>
    )

    /**
     * Relationship between Deha and Jeeva rashis
     */
    enum class DehaJeevaRelationship(val displayName: String, val description: String) {
        HARMONIOUS("Harmonious", "Body and soul are aligned - good health and spiritual progress"),
        SUPPORTIVE("Supportive", "Jeeva supports Deha - spiritual practices benefit health"),
        NEUTRAL("Neutral", "Independent functioning of body and spirit"),
        CHALLENGING("Challenging", "Some friction between material and spiritual needs"),
        TRANSFORMATIVE("Transformative", "Deep karmic work needed to align body and soul")
    }

    /**
     * Mahadasha interpretation
     */
    data class MahadashaInterpretation(
        val generalEffects: String,
        val healthPrediction: String,
        val spiritualPrediction: String,
        val materialPrediction: String,
        val favorableAreas: List<String>,
        val cautionAreas: List<String>,
        val remedies: List<String>
    )

    /**
     * Overall Kalachakra interpretation
     */
    data class KalachakraInterpretation(
        val systemOverview: String,
        val nakshatraGroupAnalysis: String,
        val dehaJeevaSummary: String,
        val currentPhaseAnalysis: String,
        val healthOutlook: String,
        val spiritualOutlook: String,
        val generalGuidance: List<String>
    )

    // ============================================
    // MAIN CALCULATION METHODS
    // ============================================

    /**
     * Calculate complete Kalachakra Dasha from a Vedic chart
     *
     * @param chart The VedicChart to analyze
     * @param numberOfCycles Number of complete cycles to calculate
     * @return Complete KalachakraDashaResult
     */
    fun calculateKalachakraDasha(
        chart: VedicChart,
        numberOfCycles: Int = 2
    ): KalachakraDashaResult {
        val birthDate = chart.birthData.dateTime.toLocalDate()

        // Get Moon's nakshatra and pada
        val moonPosition = chart.planetPositions.first { it.planet == Planet.MOON }
        val birthNakshatra = moonPosition.nakshatra
        val birthPada = moonPosition.nakshatraPada

        // Determine nakshatra group
        val nakshatraGroup = determineNakshatraGroup(birthNakshatra, birthPada)

        // Calculate starting sign based on nakshatra and pada
        val startingSign = calculateStartingSign(birthNakshatra, birthPada, nakshatraGroup)

        // Get Deha and Jeeva rashis
        val (dehaRashi, jeevaRashi) = calculateDehaJeeva(birthNakshatra, birthPada)

        // Calculate balance of dasha at birth
        val balanceInfo = calculateDashaBalance(moonPosition.longitude, birthNakshatra, birthPada)

        // Calculate all Mahadashas
        val mahadashas = calculateMahadashas(
            startingSign = startingSign,
            nakshatraGroup = nakshatraGroup,
            birthDate = birthDate,
            balanceInfo = balanceInfo,
            dehaRashi = dehaRashi,
            jeevaRashi = jeevaRashi,
            numberOfCycles = numberOfCycles,
            chart = chart
        )

        // Find current periods
        val today = LocalDate.now()
        val currentMahadasha = mahadashas.find { it.isActiveOn(today) }
        val currentAntardasha = currentMahadasha?.getAntardashaOn(today)

        // Deha-Jeeva analysis
        val dehaJeevaAnalysis = analyzeDehaJeeva(dehaRashi, jeevaRashi, chart)

        // Calculate applicability score
        val applicabilityScore = calculateApplicabilityScore(chart, birthNakshatra)

        // Generate interpretation
        val interpretation = generateKalachakraInterpretation(
            nakshatraGroup = nakshatraGroup,
            dehaRashi = dehaRashi,
            jeevaRashi = jeevaRashi,
            currentMahadasha = currentMahadasha,
            dehaJeevaAnalysis = dehaJeevaAnalysis,
            chart = chart
        )

        return KalachakraDashaResult(
            birthNakshatra = birthNakshatra,
            birthNakshatraPada = birthPada,
            nakshatraGroup = nakshatraGroup,
            startingSign = startingSign,
            dehaRashi = dehaRashi,
            jeevaRashi = jeevaRashi,
            mahadashas = mahadashas,
            currentMahadasha = currentMahadasha,
            currentAntardasha = currentAntardasha,
            dehaJeevaAnalysis = dehaJeevaAnalysis,
            interpretation = interpretation,
            applicabilityScore = applicabilityScore
        )
    }

    /**
     * Determine if nakshatra belongs to Savya or Apsavya group
     */
    private fun determineNakshatraGroup(nakshatra: Nakshatra, pada: Int): NakshatraGroup {
        // Special case for Uttara Ashadha which is split
        if (nakshatra == Nakshatra.UTTARA_ASHADHA) {
            return if (pada in UTTARA_ASHADHA_SAVYA_PADAS) {
                NakshatraGroup.SAVYA
            } else {
                NakshatraGroup.APSAVYA
            }
        }

        return when {
            nakshatra in SAVYA_NAKSHATRAS -> NakshatraGroup.SAVYA
            nakshatra in APSAVYA_NAKSHATRAS -> NakshatraGroup.APSAVYA
            else -> NakshatraGroup.SAVYA // Default to Savya for any edge cases
        }
    }

    /**
     * Calculate the starting sign based on nakshatra, pada, and group
     */
    private fun calculateStartingSign(
        nakshatra: Nakshatra,
        pada: Int,
        group: NakshatraGroup
    ): ZodiacSign {
        // The starting sign is based on the Navamsa of the Moon
        // Each nakshatra pada corresponds to one Navamsa sign
        val nakshatraIndex = nakshatra.ordinal
        val navamsaStartIndex = (nakshatraIndex * 4 + (pada - 1)) % 12

        return ZodiacSign.entries[navamsaStartIndex]
    }

    /**
     * Calculate Deha and Jeeva rashis based on nakshatra pada
     */
    private fun calculateDehaJeeva(nakshatra: Nakshatra, pada: Int): Pair<ZodiacSign, ZodiacSign> {
        // The Deha-Jeeva calculation is based on the sign-wise mapping
        // Each pada has specific Deha and Jeeva rashis

        // Calculate the Navamsa sign (which gives us Deha)
        val nakshatraIndex = nakshatra.ordinal
        val navamsaIndex = (nakshatraIndex * 4 + (pada - 1)) % 12
        val dehaRashi = ZodiacSign.entries[navamsaIndex]

        // Jeeva is calculated as the 5th from Deha (trine relationship)
        val jeevaIndex = (navamsaIndex + 4) % 12
        val jeevaRashi = ZodiacSign.entries[jeevaIndex]

        return Pair(dehaRashi, jeevaRashi)
    }

    /**
     * Balance of dasha at birth
     */
    private data class DashaBalance(
        val completedPortion: Double,
        val remainingPortion: Double,
        val remainingYears: Double,
        val startingSignIndex: Int
    )

    /**
     * Calculate the balance of dasha at birth based on Moon's position
     */
    private fun calculateDashaBalance(
        moonLongitude: Double,
        nakshatra: Nakshatra,
        pada: Int
    ): DashaBalance {
        // Each pada is 3°20' = 3.333... degrees
        val padaSize = 360.0 / 108.0 // 108 padas total

        // Calculate position within the pada
        val nakshatraStart = nakshatra.ordinal * (360.0 / 27.0)
        val padaStart = nakshatraStart + (pada - 1) * padaSize
        val positionInPada = ((moonLongitude - padaStart + 360.0) % 360.0)
        val completedPortion = (positionInPada / padaSize).coerceIn(0.0, 1.0)
        val remainingPortion = 1.0 - completedPortion

        // The starting sign for this pada
        val nakshatraIndex = nakshatra.ordinal
        val startingSignIndex = (nakshatraIndex * 4 + (pada - 1)) % 12

        // Get the period of the starting sign
        val startingSign = ZodiacSign.entries[startingSignIndex]
        val signPeriod = KALACHAKRA_PERIODS[startingSign] ?: 9
        val remainingYears = signPeriod * remainingPortion

        return DashaBalance(
            completedPortion = completedPortion,
            remainingPortion = remainingPortion,
            remainingYears = remainingYears,
            startingSignIndex = startingSignIndex
        )
    }

    /**
     * Calculate all Mahadashas
     */
    private fun calculateMahadashas(
        startingSign: ZodiacSign,
        nakshatraGroup: NakshatraGroup,
        birthDate: LocalDate,
        balanceInfo: DashaBalance,
        dehaRashi: ZodiacSign,
        jeevaRashi: ZodiacSign,
        numberOfCycles: Int,
        chart: VedicChart
    ): List<KalachakraMahadasha> {
        val mahadashas = mutableListOf<KalachakraMahadasha>()
        var currentDate = birthDate

        // Generate sign sequence based on group (Savya or Apsavya)
        val signSequence = generateKalachakraSignSequence(
            startingSign = startingSign,
            group = nakshatraGroup,
            count = 9 * numberOfCycles // 9 signs per cycle
        )

        var isFirstDasha = true

        for ((index, sign) in signSequence.withIndex()) {
            var durationYears = KALACHAKRA_PERIODS[sign] ?: 9

            // For first dasha, use the balance
            if (isFirstDasha) {
                durationYears = balanceInfo.remainingYears.toInt().coerceAtLeast(1)
                isFirstDasha = false
            }

            val durationDays = yearsToRoundedDays(durationYears.toDouble())
            val endDate = currentDate.plusDays(durationDays)

            val signLord = sign.ruler
            val signDehaJeeva = DEHA_JEEVA_PAIRS[sign] ?: Pair(sign, sign)
            val isParamaAyush = isParamaAyushSign(sign, chart)
            val healthIndicator = calculateHealthIndicator(sign, dehaRashi, jeevaRashi, chart)

            val antardashas = calculateAntardashas(
                mahadashaSign = sign,
                group = nakshatraGroup,
                mahaStart = currentDate,
                mahaEnd = endDate,
                dehaRashi = dehaRashi,
                jeevaRashi = jeevaRashi,
                chart = chart
            )

            val interpretation = generateMahadashaInterpretation(
                sign = sign,
                signLord = signLord,
                healthIndicator = healthIndicator,
                dehaRashi = dehaRashi,
                jeevaRashi = jeevaRashi,
                chart = chart
            )

            mahadashas.add(
                KalachakraMahadasha(
                    sign = sign,
                    startDate = currentDate,
                    endDate = endDate,
                    durationYears = durationYears,
                    signLord = signLord,
                    dehaRashi = signDehaJeeva.first,
                    jeevaRashi = signDehaJeeva.second,
                    antardashas = antardashas,
                    isParamaAyushSign = isParamaAyush,
                    healthIndicator = healthIndicator,
                    interpretation = interpretation
                )
            )

            currentDate = endDate.plusDays(1)
        }

        return mahadashas
    }

    /**
     * Generate sign sequence based on Kalachakra rules
     */
    private fun generateKalachakraSignSequence(
        startingSign: ZodiacSign,
        group: NakshatraGroup,
        count: Int
    ): List<ZodiacSign> {
        val sequence = mutableListOf<ZodiacSign>()

        // Kalachakra follows a specific 9-sign pattern
        // The pattern changes based on Savya/Apsavya

        val savyaPattern = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8) // Aries to Sagittarius
        val apsavyaPattern = listOf(0, 11, 10, 9, 8, 7, 6, 5, 4) // Reverse direction

        val pattern = if (group == NakshatraGroup.SAVYA) savyaPattern else apsavyaPattern
        var cycleCount = 0

        while (sequence.size < count) {
            for (offset in pattern) {
                if (sequence.size >= count) break

                val signIndex = if (group == NakshatraGroup.SAVYA) {
                    (startingSign.ordinal + offset) % 12
                } else {
                    (startingSign.ordinal - offset + 12) % 12
                }
                sequence.add(ZodiacSign.entries[signIndex])
            }
            cycleCount++
        }

        return sequence
    }

    /**
     * Calculate Antardashas within a Mahadasha
     */
    private fun calculateAntardashas(
        mahadashaSign: ZodiacSign,
        group: NakshatraGroup,
        mahaStart: LocalDate,
        mahaEnd: LocalDate,
        dehaRashi: ZodiacSign,
        jeevaRashi: ZodiacSign,
        chart: VedicChart
    ): List<KalachakraAntardasha> {
        val antardashas = mutableListOf<KalachakraAntardasha>()
        val mahaDurationDays = ChronoUnit.DAYS.between(mahaStart, mahaEnd)

        if (mahaDurationDays <= 0) return antardashas

        // Antardasha sequence follows the same Kalachakra pattern
        val antarSignSequence = generateKalachakraSignSequence(
            startingSign = mahadashaSign,
            group = group,
            count = 9
        )

        // Calculate total years for proportional division
        val totalYears = antarSignSequence.sumOf { KALACHAKRA_PERIODS[it] ?: 9 }

        var currentDate = mahaStart

        for ((index, antarSign) in antarSignSequence.withIndex()) {
            val antarYears = KALACHAKRA_PERIODS[antarSign] ?: 9
            val proportion = antarYears.toDouble() / totalYears
            val antarDurationDays = (mahaDurationDays * proportion).toLong().coerceAtLeast(1L)

            val endDate = if (index == antarSignSequence.size - 1) {
                mahaEnd
            } else {
                currentDate.plusDays(antarDurationDays).let {
                    if (it.isAfter(mahaEnd)) mahaEnd else it
                }
            }

            val isDeha = antarSign == dehaRashi
            val isJeeva = antarSign == jeevaRashi

            val interpretation = generateAntardashaInterpretation(
                mahadashaSign = mahadashaSign,
                antardashaSign = antarSign,
                isDeha = isDeha,
                isJeeva = isJeeva
            )

            antardashas.add(
                KalachakraAntardasha(
                    sign = antarSign,
                    mahadashaSign = mahadashaSign,
                    startDate = currentDate,
                    endDate = endDate,
                    durationDays = ChronoUnit.DAYS.between(currentDate, endDate).coerceAtLeast(1L),
                    signLord = antarSign.ruler,
                    isDehaSign = isDeha,
                    isJeevaSign = isJeeva,
                    interpretation = interpretation
                )
            )

            if (endDate >= mahaEnd) break
            currentDate = endDate.plusDays(1)
        }

        return antardashas
    }

    /**
     * Check if sign is Parama Ayush (special longevity indicator)
     */
    private fun isParamaAyushSign(sign: ZodiacSign, chart: VedicChart): Boolean {
        // Signs where Jupiter is strong or exalted contribute to longevity
        val jupiterPosition = chart.planetPositions.find { it.planet == Planet.JUPITER }
        return sign == ZodiacSign.CANCER || // Jupiter's exaltation
               sign == ZodiacSign.SAGITTARIUS || // Jupiter's own sign
               sign == ZodiacSign.PISCES || // Jupiter's own sign
               jupiterPosition?.sign == sign
    }

    /**
     * Calculate health indicator for the dasha
     */
    private fun calculateHealthIndicator(
        sign: ZodiacSign,
        dehaRashi: ZodiacSign,
        jeevaRashi: ZodiacSign,
        chart: VedicChart
    ): HealthIndicator {
        var score = 3 // Start neutral

        // Check relationship with Deha and Jeeva
        if (sign == dehaRashi) {
            score += 1 // Deha sign period focuses on body
        }
        if (sign == jeevaRashi) {
            score += 1 // Jeeva sign period is spiritually uplifting
        }

        // Check if sign has benefics
        val beneficsInSign = chart.planetPositions.filter {
            it.sign == sign && it.planet in listOf(Planet.JUPITER, Planet.VENUS, Planet.MOON, Planet.MERCURY)
        }
        score += beneficsInSign.size.coerceAtMost(2)

        // Check for malefics
        val maleficsInSign = chart.planetPositions.filter {
            it.sign == sign && it.planet in listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU)
        }
        score -= maleficsInSign.size

        // Check sign lord strength
        val signLord = sign.ruler
        val signLordPosition = chart.planetPositions.find { it.planet == signLord }
        if (signLordPosition != null) {
            when (signLordPosition.sign) {
                sign -> score += 1 // In own sign
                signLord.exaltationSign -> score += 1 // Exalted
                signLord.debilitationSign -> score -= 1 // Debilitated
                else -> {}
            }
        }

        return when {
            score >= 5 -> HealthIndicator.EXCELLENT
            score >= 4 -> HealthIndicator.GOOD
            score >= 3 -> HealthIndicator.MODERATE
            score >= 2 -> HealthIndicator.CHALLENGING
            else -> HealthIndicator.CRITICAL
        }
    }

    /**
     * Analyze Deha-Jeeva relationship
     */
    private fun analyzeDehaJeeva(
        dehaRashi: ZodiacSign,
        jeevaRashi: ZodiacSign,
        chart: VedicChart
    ): DehaJeevaAnalysis {
        val dehaLord = dehaRashi.ruler
        val jeevaLord = jeevaRashi.ruler

        val dehaLordPosition = chart.planetPositions.find { it.planet == dehaLord }
        val jeevaLordPosition = chart.planetPositions.find { it.planet == jeevaLord }

        val dehaLordStrength = assessPlanetStrength(dehaLord, dehaLordPosition, chart)
        val jeevaLordStrength = assessPlanetStrength(jeevaLord, jeevaLordPosition, chart)

        // Determine relationship
        val relationship = determineDehaJeevaRelationship(
            dehaRashi, jeevaRashi, dehaLord, jeevaLord, chart
        )

        val healthPrediction = generateHealthPrediction(dehaLordStrength, relationship)
        val spiritualPrediction = generateSpiritualPrediction(jeevaLordStrength, relationship)

        val recommendations = generateDehaJeevaRecommendations(
            dehaLord, jeevaLord, relationship
        )

        return DehaJeevaAnalysis(
            dehaRashi = dehaRashi,
            jeevaRashi = jeevaRashi,
            dehaLord = dehaLord,
            jeevaLord = jeevaLord,
            dehaLordStrength = dehaLordStrength,
            jeevaLordStrength = jeevaLordStrength,
            dehaJeevaRelationship = relationship,
            healthPrediction = healthPrediction,
            spiritualPrediction = spiritualPrediction,
            recommendations = recommendations
        )
    }

    /**
     * Assess planet strength for display
     */
    private fun assessPlanetStrength(
        planet: Planet,
        position: com.astro.storm.data.model.PlanetPosition?,
        chart: VedicChart
    ): String {
        if (position == null) return "Unknown"

        return when {
            position.sign == planet.exaltationSign -> "Exalted - Very Strong"
            position.sign.ruler == planet -> "Own Sign - Strong"
            position.sign == planet.debilitationSign -> "Debilitated - Weak"
            position.isRetrograde -> "Retrograde - Introspective"
            else -> "Moderate"
        }
    }

    /**
     * Determine the relationship between Deha and Jeeva
     */
    private fun determineDehaJeevaRelationship(
        dehaRashi: ZodiacSign,
        jeevaRashi: ZodiacSign,
        dehaLord: Planet,
        jeevaLord: Planet,
        chart: VedicChart
    ): DehaJeevaRelationship {
        // Calculate the house distance between Deha and Jeeva
        val distance = ((jeevaRashi.number - dehaRashi.number + 12) % 12) + 1

        // Trine relationship (5th or 9th) is most harmonious
        if (distance in listOf(5, 9)) {
            return DehaJeevaRelationship.HARMONIOUS
        }

        // Same sign or 7th (opposition but partnership)
        if (distance == 1 || distance == 7) {
            return DehaJeevaRelationship.SUPPORTIVE
        }

        // Square relationship (4th, 10th)
        if (distance in listOf(4, 10)) {
            return DehaJeevaRelationship.CHALLENGING
        }

        // 6th, 8th, 12th (dusthana relationship)
        if (distance in listOf(6, 8, 12)) {
            return DehaJeevaRelationship.TRANSFORMATIVE
        }

        return DehaJeevaRelationship.NEUTRAL
    }

    /**
     * Generate health prediction based on Deha analysis
     */
    private fun generateHealthPrediction(
        dehaLordStrength: String,
        relationship: DehaJeevaRelationship
    ): String {
        return buildString {
            append("Based on Deha lord's $dehaLordStrength status and ")
            append("${relationship.displayName.lowercase()} Deha-Jeeva relationship: ")
            when (relationship) {
                DehaJeevaRelationship.HARMONIOUS ->
                    append("Physical health is well-supported by spiritual practices. Body responds well to holistic healing.")
                DehaJeevaRelationship.SUPPORTIVE ->
                    append("Good baseline health with spiritual practices enhancing physical wellbeing.")
                DehaJeevaRelationship.NEUTRAL ->
                    append("Health matters follow their natural course. Maintain regular routines.")
                DehaJeevaRelationship.CHALLENGING ->
                    append("May experience tension between physical demands and spiritual aspirations. Balance is key.")
                DehaJeevaRelationship.TRANSFORMATIVE ->
                    append("Health challenges may serve as catalysts for spiritual growth. Deep healing possible.")
            }
        }
    }

    /**
     * Generate spiritual prediction based on Jeeva analysis
     */
    private fun generateSpiritualPrediction(
        jeevaLordStrength: String,
        relationship: DehaJeevaRelationship
    ): String {
        return buildString {
            append("Jeeva lord's $jeevaLordStrength condition indicates ")
            when (relationship) {
                DehaJeevaRelationship.HARMONIOUS ->
                    append("natural spiritual progress. Meditation and dharmic practices flow easily.")
                DehaJeevaRelationship.SUPPORTIVE ->
                    append("spiritual growth through practical application of wisdom.")
                DehaJeevaRelationship.NEUTRAL ->
                    append("steady spiritual development through consistent practice.")
                DehaJeevaRelationship.CHALLENGING ->
                    append("spiritual growth through overcoming material attachments.")
                DehaJeevaRelationship.TRANSFORMATIVE ->
                    append("profound spiritual transformation through life's challenges.")
            }
        }
    }

    /**
     * Generate recommendations based on Deha-Jeeva analysis
     */
    private fun generateDehaJeevaRecommendations(
        dehaLord: Planet,
        jeevaLord: Planet,
        relationship: DehaJeevaRelationship
    ): List<String> {
        val recommendations = mutableListOf<String>()

        // Deha lord remedies
        recommendations.add("Strengthen Deha lord (${dehaLord.displayName}) through appropriate mantras and gemstones")

        // Jeeva lord remedies
        recommendations.add("Honor Jeeva lord (${jeevaLord.displayName}) through spiritual practices")

        // Relationship-specific recommendations
        when (relationship) {
            DehaJeevaRelationship.HARMONIOUS -> {
                recommendations.add("Continue current spiritual and health practices - they are aligned")
                recommendations.add("Use this favorable period for deepening meditation")
            }
            DehaJeevaRelationship.SUPPORTIVE -> {
                recommendations.add("Integrate physical yoga with spiritual practices")
                recommendations.add("Serve others as part of spiritual path")
            }
            DehaJeevaRelationship.NEUTRAL -> {
                recommendations.add("Establish regular routines for both physical and spiritual health")
                recommendations.add("Create balance between material and spiritual pursuits")
            }
            DehaJeevaRelationship.CHALLENGING -> {
                recommendations.add("Practice patience and acceptance with physical limitations")
                recommendations.add("Transform challenges into spiritual growth opportunities")
            }
            DehaJeevaRelationship.TRANSFORMATIVE -> {
                recommendations.add("Embrace transformation as part of soul's journey")
                recommendations.add("Seek guidance from spiritual teachers during difficult periods")
            }
        }

        return recommendations
    }

    /**
     * Calculate how applicable Kalachakra Dasha is for this chart
     */
    private fun calculateApplicabilityScore(chart: VedicChart, nakshatra: Nakshatra): Int {
        var score = 50 // Base score

        // Moon's strength affects applicability (Moon-based system)
        val moonPosition = chart.planetPositions.first { it.planet == Planet.MOON }
        when (moonPosition.sign) {
            ZodiacSign.TAURUS -> score += 20 // Moon exalted
            ZodiacSign.CANCER -> score += 15 // Moon in own sign
            ZodiacSign.SCORPIO -> score -= 15 // Moon debilitated
            else -> {}
        }

        // Nakshatra type affects score
        if (nakshatra in SAVYA_NAKSHATRAS || nakshatra in APSAVYA_NAKSHATRAS) {
            score += 10 // Clear group assignment
        }

        // 8th house and 8th lord analysis (longevity house)
        val eighthSign = ZodiacSign.entries[(ZodiacSign.fromLongitude(chart.ascendant).ordinal + 7) % 12]
        val eighthLord = eighthSign.ruler
        val eighthLordPosition = chart.planetPositions.find { it.planet == eighthLord }
        if (eighthLordPosition != null) {
            if (eighthLordPosition.sign == eighthLord.exaltationSign) {
                score += 10 // Strong 8th lord increases longevity focus relevance
            }
        }

        return score.coerceIn(0, 100)
    }

    // ============================================
    // INTERPRETATION METHODS
    // ============================================

    /**
     * Generate Mahadasha interpretation
     */
    private fun generateMahadashaInterpretation(
        sign: ZodiacSign,
        signLord: Planet,
        healthIndicator: HealthIndicator,
        dehaRashi: ZodiacSign,
        jeevaRashi: ZodiacSign,
        chart: VedicChart
    ): MahadashaInterpretation {
        val generalEffects = getSignGeneralEffects(sign)
        val healthPrediction = "Health outlook: ${healthIndicator.displayName} - ${healthIndicator.description}"
        val spiritualPrediction = getSignSpiritualEffects(sign)
        val materialPrediction = getSignMaterialEffects(sign, chart)
        val favorableAreas = getFavorableAreas(sign, signLord)
        val cautionAreas = getCautionAreas(sign, healthIndicator)
        val remedies = getKalachakraRemedies(sign, signLord)

        return MahadashaInterpretation(
            generalEffects = generalEffects,
            healthPrediction = healthPrediction,
            spiritualPrediction = spiritualPrediction,
            materialPrediction = materialPrediction,
            favorableAreas = favorableAreas,
            cautionAreas = cautionAreas,
            remedies = remedies
        )
    }

    /**
     * Generate Antardasha interpretation
     */
    private fun generateAntardashaInterpretation(
        mahadashaSign: ZodiacSign,
        antardashaSign: ZodiacSign,
        isDeha: Boolean,
        isJeeva: Boolean
    ): String {
        return buildString {
            append("${antardashaSign.displayName} period within ${mahadashaSign.displayName}: ")

            if (isDeha) {
                append("This is a DEHA period - focus on physical health and material matters. ")
            }
            if (isJeeva) {
                append("This is a JEEVA period - focus on spiritual growth and consciousness. ")
            }
            if (!isDeha && !isJeeva) {
                append("General period for ${antardashaSign.displayName} themes. ")
            }

            append(getSignBriefEffect(antardashaSign))
        }
    }

    /**
     * Generate overall Kalachakra interpretation
     */
    private fun generateKalachakraInterpretation(
        nakshatraGroup: NakshatraGroup,
        dehaRashi: ZodiacSign,
        jeevaRashi: ZodiacSign,
        currentMahadasha: KalachakraMahadasha?,
        dehaJeevaAnalysis: DehaJeevaAnalysis,
        chart: VedicChart
    ): KalachakraInterpretation {
        val systemOverview = buildString {
            append("Kalachakra Dasha is a sophisticated timing system particularly useful for ")
            append("health predictions and spiritual transformation timing. It operates on the ")
            append("principle that body (Deha) and soul (Jeeva) follow different but related cycles.")
        }

        val nakshatraGroupAnalysis = buildString {
            append("Your Moon falls in ${nakshatraGroup.displayName} group. ")
            append(nakshatraGroup.description)
            append(" This influences the direction and nature of your life's unfoldment.")
        }

        val dehaJeevaSummary = buildString {
            append("Deha (Body) Rashi: ${dehaRashi.displayName}, ")
            append("Jeeva (Soul) Rashi: ${jeevaRashi.displayName}. ")
            append("Relationship: ${dehaJeevaAnalysis.dehaJeevaRelationship.description}")
        }

        val currentPhaseAnalysis = if (currentMahadasha != null) {
            buildString {
                append("Currently in ${currentMahadasha.sign.displayName} Mahadasha ")
                append("(${currentMahadasha.durationYears} years). ")
                append("Health outlook: ${currentMahadasha.healthIndicator.displayName}. ")
                append(currentMahadasha.interpretation.generalEffects)
            }
        } else {
            "Current Mahadasha extends beyond calculated range."
        }

        val healthOutlook = dehaJeevaAnalysis.healthPrediction
        val spiritualOutlook = dehaJeevaAnalysis.spiritualPrediction

        val generalGuidance = listOf(
            "Monitor transits over Deha Rashi for physical health events",
            "Observe transits over Jeeva Rashi for spiritual opportunities",
            "When Deha and Jeeva sign lords are strong in transit, both health and spiritual progress are favored",
            "Use Kalachakra Dasha alongside Vimsottari for comprehensive analysis",
            "Pay special attention when malefics transit your Deha Rashi"
        )

        return KalachakraInterpretation(
            systemOverview = systemOverview,
            nakshatraGroupAnalysis = nakshatraGroupAnalysis,
            dehaJeevaSummary = dehaJeevaSummary,
            currentPhaseAnalysis = currentPhaseAnalysis,
            healthOutlook = healthOutlook,
            spiritualOutlook = spiritualOutlook,
            generalGuidance = generalGuidance
        )
    }

    // ============================================
    // HELPER METHODS
    // ============================================

    private fun yearsToRoundedDays(years: Double): Long = DashaUtils.yearsToRoundedDays(years)

    private fun getSignGeneralEffects(sign: ZodiacSign): String {
        return when (sign) {
            ZodiacSign.ARIES -> "Period of initiative, new beginnings, and physical vitality. Mars energy brings action and courage."
            ZodiacSign.TAURUS -> "Focus on stability, wealth accumulation, and sensory pleasures. Venus brings comfort and beauty."
            ZodiacSign.GEMINI -> "Communication, learning, and intellectual pursuits are highlighted. Mercury brings versatility."
            ZodiacSign.CANCER -> "Emotional growth, home, and family matters take center stage. Moon brings nurturing energy."
            ZodiacSign.LEO -> "Recognition, authority, and creative self-expression. Sun brings confidence and vitality."
            ZodiacSign.VIRGO -> "Analysis, health consciousness, and service. Mercury brings discrimination and healing potential."
            ZodiacSign.LIBRA -> "Partnerships, balance, and aesthetic pursuits. Venus brings harmony and relationships."
            ZodiacSign.SCORPIO -> "Deep transformation, hidden matters, and research. Mars/Ketu bring intensity and rebirth."
            ZodiacSign.SAGITTARIUS -> "Higher learning, philosophy, and expansion. Jupiter brings wisdom and fortune."
            ZodiacSign.CAPRICORN -> "Career, discipline, and long-term achievements. Saturn brings structure and maturity."
            ZodiacSign.AQUARIUS -> "Innovation, humanitarian concerns, and group activities. Saturn/Rahu bring progressive change."
            ZodiacSign.PISCES -> "Spirituality, imagination, and transcendence. Jupiter brings divine connection and liberation."
        }
    }

    private fun getSignSpiritualEffects(sign: ZodiacSign): String {
        return when (sign.element) {
            "Fire" -> "Spiritual growth through action, courage, and self-assertion. Karma yoga is favored."
            "Earth" -> "Spiritual grounding through practical service and material detachment. Seva is emphasized."
            "Air" -> "Spiritual development through knowledge, study, and intellectual understanding. Jnana yoga is favored."
            "Water" -> "Spiritual awakening through devotion, intuition, and emotional surrender. Bhakti yoga is emphasized."
            else -> "Balanced spiritual development across all paths."
        }
    }

    private fun getSignMaterialEffects(sign: ZodiacSign, chart: VedicChart): String {
        val signLord = sign.ruler
        val lordPosition = chart.planetPositions.find { it.planet == signLord }
        val lordHouse = lordPosition?.house ?: 1

        return buildString {
            append("Material focus on ${sign.displayName} matters. ")
            append("Sign lord ${signLord.displayName} in House $lordHouse indicates ")
            when (lordHouse) {
                1 -> append("self-focused material gains.")
                2 -> append("wealth and resource accumulation.")
                3 -> append("gains through courage and communication.")
                4 -> append("property and domestic comfort.")
                5 -> append("creative and speculative gains.")
                6 -> append("gains through service and overcoming obstacles.")
                7 -> append("partnership-related material matters.")
                8 -> append("transformation of resources, inheritance.")
                9 -> append("fortune through dharma and higher pursuits.")
                10 -> append("career advancement and public recognition.")
                11 -> append("fulfillment of desires and network gains.")
                12 -> append("spiritual investments and foreign connections.")
                else -> append("general material progression.")
            }
        }
    }

    private fun getFavorableAreas(sign: ZodiacSign, signLord: Planet): List<String> {
        val areas = mutableListOf<String>()

        areas.add("${signLord.displayName}-related activities and matters")

        when (sign.element) {
            "Fire" -> areas.addAll(listOf("Leadership", "Sports", "Engineering", "Military"))
            "Earth" -> areas.addAll(listOf("Finance", "Real estate", "Agriculture", "Construction"))
            "Air" -> areas.addAll(listOf("Communication", "Education", "Travel", "Technology"))
            "Water" -> areas.addAll(listOf("Healing", "Arts", "Psychology", "Spirituality"))
        }

        return areas
    }

    private fun getCautionAreas(sign: ZodiacSign, healthIndicator: HealthIndicator): List<String> {
        val areas = mutableListOf<String>()

        if (healthIndicator.score <= 2) {
            areas.add("Physical health requires attention")
        }

        when (sign) {
            ZodiacSign.ARIES -> areas.add("Head injuries, fevers, impulsive actions")
            ZodiacSign.TAURUS -> areas.add("Throat issues, dietary excess, stubbornness")
            ZodiacSign.GEMINI -> areas.add("Nervous tension, respiratory issues, inconsistency")
            ZodiacSign.CANCER -> areas.add("Emotional sensitivity, digestive issues, attachment")
            ZodiacSign.LEO -> areas.add("Heart issues, ego conflicts, excessive pride")
            ZodiacSign.VIRGO -> areas.add("Digestive problems, anxiety, over-criticism")
            ZodiacSign.LIBRA -> areas.add("Kidney issues, indecision, relationship dependency")
            ZodiacSign.SCORPIO -> areas.add("Reproductive issues, intensity, control issues")
            ZodiacSign.SAGITTARIUS -> areas.add("Liver issues, over-expansion, dogmatism")
            ZodiacSign.CAPRICORN -> areas.add("Joint problems, depression, workaholism")
            ZodiacSign.AQUARIUS -> areas.add("Circulation issues, detachment, rebellion")
            ZodiacSign.PISCES -> areas.add("Foot problems, escapism, boundary issues")
        }

        return areas
    }

    private fun getKalachakraRemedies(sign: ZodiacSign, signLord: Planet): List<String> {
        val remedies = mutableListOf<String>()

        // Sign lord mantra
        remedies.add("Chant ${signLord.displayName} mantra for strengthening this period")

        // Elemental remedies
        when (sign.element) {
            "Fire" -> remedies.add("Perform Agni-related rituals (Homa, lighting lamp)")
            "Earth" -> remedies.add("Service to earth (gardening, feeding, grounding practices)")
            "Air" -> remedies.add("Pranayama and breath-related practices")
            "Water" -> remedies.add("Water offerings, visiting sacred water bodies")
        }

        // Health-specific
        remedies.add("Honor the sign deity through appropriate worship")

        return remedies
    }

    private fun getSignBriefEffect(sign: ZodiacSign): String {
        return when (sign) {
            ZodiacSign.ARIES -> "Energy, initiative, new starts."
            ZodiacSign.TAURUS -> "Stability, wealth, comfort."
            ZodiacSign.GEMINI -> "Communication, learning, versatility."
            ZodiacSign.CANCER -> "Emotions, home, nurturing."
            ZodiacSign.LEO -> "Recognition, creativity, authority."
            ZodiacSign.VIRGO -> "Analysis, health, service."
            ZodiacSign.LIBRA -> "Balance, relationships, harmony."
            ZodiacSign.SCORPIO -> "Transformation, depth, intensity."
            ZodiacSign.SAGITTARIUS -> "Expansion, wisdom, fortune."
            ZodiacSign.CAPRICORN -> "Career, discipline, achievement."
            ZodiacSign.AQUARIUS -> "Innovation, humanity, progress."
            ZodiacSign.PISCES -> "Spirituality, imagination, liberation."
        }
    }

    // ============================================
    // UTILITY METHODS
    // ============================================

    /**
     * Get current period summary
     */
    fun getCurrentPeriodSummary(result: KalachakraDashaResult): String {
        val maha = result.currentMahadasha ?: return "No active Kalachakra Dasha period"
        val antar = result.currentAntardasha

        return buildString {
            appendLine("=== KALACHAKRA DASHA - CURRENT PERIOD ===")
            appendLine()
            appendLine("Nakshatra Group: ${result.nakshatraGroup.displayName}")
            appendLine("Deha (Body) Rashi: ${result.dehaRashi.displayName}")
            appendLine("Jeeva (Soul) Rashi: ${result.jeevaRashi.displayName}")
            appendLine()
            appendLine("Mahadasha: ${maha.sign.displayName} (${maha.durationYears} years)")
            appendLine("Health: ${maha.healthIndicator.displayName}")
            appendLine("Progress: ${String.format("%.1f", maha.getProgressPercent())}%")
            appendLine("Remaining: ${maha.getRemainingDays()} days")
            appendLine()
            if (antar != null) {
                appendLine("Antardasha: ${antar.sign.displayName}")
                if (antar.isDehaSign) appendLine("  * DEHA period - focus on physical matters")
                if (antar.isJeevaSign) appendLine("  * JEEVA period - focus on spiritual matters")
                appendLine("Progress: ${String.format("%.1f", antar.getProgressPercent())}%")
            }
        }
    }

    /**
     * Get Deha-Jeeva summary
     */
    fun getDehaJeevaSummary(result: KalachakraDashaResult): String {
        return buildString {
            appendLine("=== DEHA-JEEVA ANALYSIS ===")
            appendLine()
            appendLine("Deha (Body) Rashi: ${result.dehaRashi.displayName}")
            appendLine("Deha Lord: ${result.dehaJeevaAnalysis.dehaLord.displayName}")
            appendLine("Deha Lord Status: ${result.dehaJeevaAnalysis.dehaLordStrength}")
            appendLine()
            appendLine("Jeeva (Soul) Rashi: ${result.jeevaRashi.displayName}")
            appendLine("Jeeva Lord: ${result.dehaJeevaAnalysis.jeevaLord.displayName}")
            appendLine("Jeeva Lord Status: ${result.dehaJeevaAnalysis.jeevaLordStrength}")
            appendLine()
            appendLine("Relationship: ${result.dehaJeevaAnalysis.dehaJeevaRelationship.displayName}")
            appendLine(result.dehaJeevaAnalysis.dehaJeevaRelationship.description)
        }
    }
}

/**
 * Extension property for Planet exaltation sign
 */
private val Planet.exaltationSign: ZodiacSign?
    get() = when (this) {
        Planet.SUN -> ZodiacSign.ARIES
        Planet.MOON -> ZodiacSign.TAURUS
        Planet.MARS -> ZodiacSign.CAPRICORN
        Planet.MERCURY -> ZodiacSign.VIRGO
        Planet.JUPITER -> ZodiacSign.CANCER
        Planet.VENUS -> ZodiacSign.PISCES
        Planet.SATURN -> ZodiacSign.LIBRA
        else -> null
    }

/**
 * Extension property for Planet debilitation sign
 */
private val Planet.debilitationSign: ZodiacSign?
    get() = when (this) {
        Planet.SUN -> ZodiacSign.LIBRA
        Planet.MOON -> ZodiacSign.SCORPIO
        Planet.MARS -> ZodiacSign.CANCER
        Planet.MERCURY -> ZodiacSign.PISCES
        Planet.JUPITER -> ZodiacSign.CAPRICORN
        Planet.VENUS -> ZodiacSign.VIRGO
        Planet.SATURN -> ZodiacSign.ARIES
        else -> null
    }
