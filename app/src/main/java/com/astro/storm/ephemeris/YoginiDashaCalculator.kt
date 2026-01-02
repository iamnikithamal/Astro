package com.astro.storm.ephemeris

import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyDosha
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.model.Nakshatra
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import java.math.BigDecimal
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Yogini Dasha Calculator
 *
 * Yogini Dasha is a nakshatra-based dasha system with a total cycle of 36 years.
 * It is particularly effective for:
 * - Female horoscopes
 * - Timing of relationships and marriage
 * - Specific event prediction
 * - Short-term predictions
 */
object YoginiDashaCalculator {

    private val MATH_CONTEXT = DashaUtils.MATH_CONTEXT
    private val NAKSHATRA_SPAN_DEGREES = DashaUtils.NAKSHATRA_SPAN

    /**
     * Yogini enumeration with their planets, years, and characteristics
     */
    enum class Yogini(
        val planet: Planet,
        val years: Int,
        val displayName: String,
        val sanskrit: String,
        val nature: YoginiNature,
        val descriptionKey: StringKeyDosha
    ) {
        MANGALA(
            planet = Planet.MOON,
            years = 1,
            displayName = "Mangala",
            sanskrit = "मंगला",
            nature = YoginiNature.AUSPICIOUS,
            descriptionKey = StringKeyDosha.YOGINI_MANGALA_DESC
        ),
        PINGALA(
            planet = Planet.SUN,
            years = 2,
            displayName = "Pingala",
            sanskrit = "पिंगला",
            nature = YoginiNature.MIXED,
            descriptionKey = StringKeyDosha.YOGINI_PINGALA_DESC
        ),
        DHANYA(
            planet = Planet.JUPITER,
            years = 3,
            displayName = "Dhanya",
            sanskrit = "धान्या",
            nature = YoginiNature.HIGHLY_AUSPICIOUS,
            descriptionKey = StringKeyDosha.YOGINI_DHANYA_DESC
        ),
        BHRAMARI(
            planet = Planet.MARS,
            years = 4,
            displayName = "Bhramari",
            sanskrit = "भ्रामरी",
            nature = YoginiNature.CHALLENGING,
            descriptionKey = StringKeyDosha.YOGINI_BHRAMARI_DESC
        ),
        BHADRIKA(
            planet = Planet.MERCURY,
            years = 5,
            displayName = "Bhadrika",
            sanskrit = "भद्रिका",
            nature = YoginiNature.AUSPICIOUS,
            descriptionKey = StringKeyDosha.YOGINI_BHADRIKA_DESC
        ),
        ULKA(
            planet = Planet.SATURN,
            years = 6,
            displayName = "Ulka",
            sanskrit = "उल्का",
            nature = YoginiNature.CHALLENGING,
            descriptionKey = StringKeyDosha.YOGINI_ULKA_DESC
        ),
        SIDDHA(
            planet = Planet.VENUS,
            years = 7,
            displayName = "Siddha",
            sanskrit = "सिद्धा",
            nature = YoginiNature.HIGHLY_AUSPICIOUS,
            descriptionKey = StringKeyDosha.YOGINI_SIDDHA_DESC
        ),
        SANKATA(
            planet = Planet.RAHU,
            years = 8,
            displayName = "Sankata",
            sanskrit = "संकटा",
            nature = YoginiNature.DIFFICULT,
            descriptionKey = StringKeyDosha.YOGINI_SANKATA_DESC
        );

        fun getDescription(language: Language): String {
            return StringResources.get(descriptionKey, language)
        }

        fun getLocalizedName(language: Language): String {
            return when (language) {
                Language.ENGLISH -> displayName
                Language.NEPALI -> sanskrit
            }
        }

        companion object {
            /**
             * Get Yogini by index (0-7)
             */
            fun fromIndex(index: Int): Yogini {
                val normalizedIndex = ((index % 8) + 8) % 8
                return entries[normalizedIndex]
            }

            /**
             * Get starting Yogini for a nakshatra
             * Formula: (Nakshatra number + 3) mod 8
             */
            fun getStartingYogini(nakshatra: Nakshatra): Yogini {
                val nakshatraIndex = nakshatra.ordinal + 1 // 1-based index
                val yoginiIndex = (nakshatraIndex + 3) % 8
                return fromIndex(yoginiIndex)
            }
        }
    }

    enum class YoginiNature {
        HIGHLY_AUSPICIOUS,
        AUSPICIOUS,
        MIXED,
        CHALLENGING,
        DIFFICULT;

        fun getLocalizedName(language: Language): String {
            return when (this) {
                HIGHLY_AUSPICIOUS -> StringResources.get(StringKeyDosha.YOGINI_NATURE_HIGHLY_AUSPICIOUS, language)
                AUSPICIOUS -> StringResources.get(StringKeyDosha.YOGINI_NATURE_AUSPICIOUS, language)
                MIXED -> StringResources.get(StringKeyDosha.YOGINI_NATURE_MIXED, language)
                CHALLENGING -> StringResources.get(StringKeyDosha.YOGINI_NATURE_CHALLENGING, language)
                DIFFICULT -> StringResources.get(StringKeyDosha.YOGINI_NATURE_DIFFICULT, language)
            }
        }
    }

    /**
     * Yogini Mahadasha period
     */
    data class YoginiMahadasha(
        val yogini: Yogini,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val durationYears: Int,
        val antardashas: List<YoginiAntardasha>,
        val interpretation: YoginiInterpretation
    ) {
        val durationDays: Long
            get() = ChronoUnit.DAYS.between(startDate, endDate)

        fun isActiveOn(date: LocalDate): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDate.now())

        fun getActiveAntardasha(): YoginiAntardasha? = getAntardashaOn(LocalDate.now())

        fun getAntardashaOn(date: LocalDate): YoginiAntardasha? {
            return antardashas.find { it.isActiveOn(date) }
        }

        fun getElapsedDays(asOf: LocalDate = LocalDate.now()): Long {
            if (asOf.isBefore(startDate)) return 0
            if (asOf.isAfter(endDate)) return durationDays
            return ChronoUnit.DAYS.between(startDate, asOf)
        }

        fun getRemainingDays(asOf: LocalDate = LocalDate.now()): Long {
            return (durationDays - getElapsedDays(asOf)).coerceAtLeast(0)
        }

        fun getProgressPercent(asOf: LocalDate = LocalDate.now()): Double {
            if (durationDays <= 0) return 0.0
            return ((getElapsedDays(asOf).toDouble() / durationDays) * 100).coerceIn(0.0, 100.0)
        }
    }

    /**
     * Yogini Antardasha (sub-period)
     */
    data class YoginiAntardasha(
        val yogini: Yogini,
        val mahadashaYogini: Yogini,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val durationDays: Long,
        val interpretation: String
    ) {
        val durationMonths: Double
            get() = durationDays / 30.4375

        fun isActiveOn(date: LocalDate): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDate.now())

        fun getElapsedDays(asOf: LocalDate = LocalDate.now()): Long {
            if (asOf.isBefore(startDate)) return 0
            if (asOf.isAfter(endDate)) return durationDays
            return ChronoUnit.DAYS.between(startDate, asOf)
        }

        fun getRemainingDays(asOf: LocalDate = LocalDate.now()): Long {
            return (durationDays - getElapsedDays(asOf)).coerceAtLeast(0)
        }

        fun getProgressPercent(asOf: LocalDate = LocalDate.now()): Double {
            if (durationDays <= 0) return 0.0
            return ((getElapsedDays(asOf).toDouble() / durationDays) * 100).coerceIn(0.0, 100.0)
        }
    }

    /**
     * Interpretation for Yogini period
     */
    data class YoginiInterpretation(
        val generalEffects: String,
        val careerEffects: String,
        val relationshipEffects: String,
        val healthEffects: String,
        val spiritualEffects: String,
        val recommendations: List<String>,
        val cautionAreas: List<String>
    )

    /**
     * Complete Yogini Dasha analysis result
     */
    data class YoginiDashaResult(
        val birthNakshatra: Nakshatra,
        val moonLongitude: Double,
        val positionInNakshatra: Double,
        val startingYogini: Yogini,
        val balanceAtBirth: BalanceAtBirth,
        val mahadashas: List<YoginiMahadasha>,
        val currentMahadasha: YoginiMahadasha?,
        val currentAntardasha: YoginiAntardasha?,
        val applicability: Applicability
    )

    /**
     * Balance of first Yogini at birth
     */
    data class BalanceAtBirth(
        val yogini: Yogini,
        val totalYears: Int,
        val balanceYears: Double,
        val balanceDays: Long,
        val elapsed: Double
    )

    /**
     * Applicability assessment for Yogini Dasha
     */
    data class Applicability(
        val isRecommended: Boolean,
        val applicabilityScore: Double,
        val reasons: List<String>
    )

    // ============================================
    // TOTAL CYCLE YEARS
    // ============================================
    private const val TOTAL_CYCLE_YEARS = 36

    // ============================================
    // MAIN CALCULATION METHODS
    // ============================================

    /**
     * Calculate complete Yogini Dasha from a VedicChart
     */
    fun calculateYoginiDasha(
        chart: VedicChart,
        numberOfCycles: Int = 3,
        language: Language = Language.ENGLISH
    ): YoginiDashaResult {
        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
            ?: throw IllegalArgumentException("Moon position not found in chart")

        val birthDate = chart.birthData.dateTime.toLocalDate()
        val moonLongitude = moonPosition.longitude

        return calculateYoginiDashaFromMoon(moonLongitude, birthDate, numberOfCycles, chart, language)
    }

    /**
     * Calculate Yogini Dasha from Moon longitude and birth date
     */
    fun calculateYoginiDashaFromMoon(
        moonLongitude: Double,
        birthDate: LocalDate,
        numberOfCycles: Int = 3,
        chart: VedicChart? = null,
        language: Language = Language.ENGLISH
    ): YoginiDashaResult {
        // Get birth nakshatra
        val (birthNakshatra, _) = Nakshatra.fromLongitude(moonLongitude)

        // Calculate position within nakshatra (0-1 range)
        val positionInNakshatra = calculatePositionInNakshatra(moonLongitude)

        // Get starting Yogini
        val startingYogini = Yogini.getStartingYogini(birthNakshatra)

        // Calculate balance at birth
        val balanceAtBirth = calculateBalanceAtBirth(startingYogini, positionInNakshatra)

        // Calculate all Mahadashas
        val mahadashas = calculateMahadashas(
            startingYogini,
            balanceAtBirth,
            birthDate,
            numberOfCycles,
            chart,
            language
        )

        // Find current periods
        val today = LocalDate.now()
        val currentMahadasha = mahadashas.find { it.isActiveOn(today) }
        val currentAntardasha = currentMahadasha?.getAntardashaOn(today)

        // Assess applicability
        val applicability = assessApplicability(chart, language)

        return YoginiDashaResult(
            birthNakshatra = birthNakshatra,
            moonLongitude = moonLongitude,
            positionInNakshatra = positionInNakshatra,
            startingYogini = startingYogini,
            balanceAtBirth = balanceAtBirth,
            mahadashas = mahadashas,
            currentMahadasha = currentMahadasha,
            currentAntardasha = currentAntardasha,
            applicability = applicability
        )
    }

    /**
     * Calculate position within nakshatra (0-1 range)
     */
    private fun calculatePositionInNakshatra(moonLongitude: Double): Double {
        val normalizedLong = ((moonLongitude % 360.0) + 360.0) % 360.0
        val positionWithinZodiac = BigDecimal(normalizedLong.toString())
        val nakshatraIndex = positionWithinZodiac.divide(NAKSHATRA_SPAN_DEGREES, MATH_CONTEXT)
        return nakshatraIndex.remainder(BigDecimal.ONE, MATH_CONTEXT).toDouble()
    }

    /**
     * Calculate balance of first Yogini at birth
     */
    private fun calculateBalanceAtBirth(
        startingYogini: Yogini,
        positionInNakshatra: Double
    ): BalanceAtBirth {
        val totalYears = startingYogini.years
        val elapsedPortion = positionInNakshatra
        val balanceYears = totalYears * (1.0 - elapsedPortion)
        val balanceDays = yearsToRoundedDays(balanceYears)

        return BalanceAtBirth(
            yogini = startingYogini,
            totalYears = totalYears,
            balanceYears = balanceYears,
            balanceDays = balanceDays,
            elapsed = totalYears * elapsedPortion
        )
    }

    /**
     * Convert years to days with proper rounding
     */
    private fun yearsToRoundedDays(years: Double): Long = DashaUtils.yearsToRoundedDays(years)

    /**
     * Calculate all Mahadashas for the specified number of cycles
     */
    private fun calculateMahadashas(
        startingYogini: Yogini,
        balanceAtBirth: BalanceAtBirth,
        birthDate: LocalDate,
        numberOfCycles: Int,
        chart: VedicChart?,
        language: Language
    ): List<YoginiMahadasha> {
        val mahadashas = mutableListOf<YoginiMahadasha>()

        var currentDate = birthDate
        val totalMahadashas = numberOfCycles * 8 // 8 Yoginis per cycle

        // First, add the balance of starting Yogini
        if (balanceAtBirth.balanceDays > 0) {
            val endDate = currentDate.plusDays(balanceAtBirth.balanceDays)
            val antardashas = calculateAntardashas(
                balanceAtBirth.yogini,
                currentDate,
                endDate,
                chart,
                language
            )
            val interpretation = generateInterpretation(balanceAtBirth.yogini, chart, language)

            mahadashas.add(
                YoginiMahadasha(
                    yogini = balanceAtBirth.yogini,
                    startDate = currentDate,
                    endDate = endDate,
                    durationYears = balanceAtBirth.totalYears,
                    antardashas = antardashas,
                    interpretation = interpretation
                )
            )
            currentDate = endDate.plusDays(1)
        }

        // Calculate subsequent Mahadashas
        var yoginiIndex = (startingYogini.ordinal + 1) % 8
        var count = 1 // Already added balance period

        while (count < totalMahadashas) {
            val yogini = Yogini.fromIndex(yoginiIndex)
            val durationDays = yearsToRoundedDays(yogini.years.toDouble())
            val endDate = currentDate.plusDays(durationDays)

            val antardashas = calculateAntardashas(yogini, currentDate, endDate, chart, language)
            val interpretation = generateInterpretation(yogini, chart, language)

            mahadashas.add(
                YoginiMahadasha(
                    yogini = yogini,
                    startDate = currentDate,
                    endDate = endDate,
                    durationYears = yogini.years,
                    antardashas = antardashas,
                    interpretation = interpretation
                )
            )

            currentDate = endDate.plusDays(1)
            yoginiIndex = (yoginiIndex + 1) % 8
            count++
        }

        return mahadashas
    }

    /**
     * Calculate Antardashas within a Mahadasha
     */
    private fun calculateAntardashas(
        mahadashaYogini: Yogini,
        mahadashaStart: LocalDate,
        mahadashaEnd: LocalDate,
        chart: VedicChart?,
        language: Language
    ): List<YoginiAntardasha> {
        val antardashas = mutableListOf<YoginiAntardasha>()
        val mahaDurationDays = ChronoUnit.DAYS.between(mahadashaStart, mahadashaEnd)

        if (mahaDurationDays <= 0) return antardashas

        // Calculate total years for proportion calculation
        val totalAntardashaYears = TOTAL_CYCLE_YEARS.toDouble()

        var currentDate = mahadashaStart
        var yoginiIndex = mahadashaYogini.ordinal

        // Calculate 8 antardashas, starting from mahadasha yogini
        for (i in 0 until 8) {
            val antardashaYogini = Yogini.fromIndex(yoginiIndex)

            // Proportionate duration: (Antardasha Yogini years / 36) * Mahadasha duration
            val proportion = antardashaYogini.years.toDouble() / totalAntardashaYears
            val antarDurationDays = (mahaDurationDays * proportion).toLong().coerceAtLeast(1L)

            val endDate = if (i == 7) {
                // Last antardasha ends exactly at mahadasha end
                mahadashaEnd
            } else {
                currentDate.plusDays(antarDurationDays).let {
                    if (it.isAfter(mahadashaEnd)) mahadashaEnd else it
                }
            }

            val interpretation = generateAntardashaInterpretation(
                mahadashaYogini,
                antardashaYogini,
                chart,
                language
            )

            antardashas.add(
                YoginiAntardasha(
                    yogini = antardashaYogini,
                    mahadashaYogini = mahadashaYogini,
                    startDate = currentDate,
                    endDate = endDate,
                    durationDays = ChronoUnit.DAYS.between(currentDate, endDate).coerceAtLeast(1L),
                    interpretation = interpretation
                )
            )

            if (endDate >= mahadashaEnd) break

            currentDate = endDate.plusDays(1)
            yoginiIndex = (yoginiIndex + 1) % 8
        }

        return antardashas
    }

    /**
     * Generate interpretation for Yogini Mahadasha
     */
    fun generateInterpretation(
        yogini: Yogini,
        chart: VedicChart?,
        language: Language
    ): YoginiInterpretation {
        val generalEffects = yogini.getDescription(language)

        val careerEffects = when (yogini) {
            Yogini.MANGALA -> StringResources.get(StringKeyDosha.YOGINI_MANGALA_CAREER, language)
            Yogini.PINGALA -> StringResources.get(StringKeyDosha.YOGINI_PINGALA_CAREER, language)
            Yogini.DHANYA -> StringResources.get(StringKeyDosha.YOGINI_DHANYA_CAREER, language)
            Yogini.BHRAMARI -> StringResources.get(StringKeyDosha.YOGINI_BHRAMARI_CAREER, language)
            Yogini.BHADRIKA -> StringResources.get(StringKeyDosha.YOGINI_BHADRIKA_CAREER, language)
            Yogini.ULKA -> StringResources.get(StringKeyDosha.YOGINI_ULKA_CAREER, language)
            Yogini.SIDDHA -> StringResources.get(StringKeyDosha.YOGINI_SIDDHA_CAREER, language)
            Yogini.SANKATA -> StringResources.get(StringKeyDosha.YOGINI_SANKATA_CAREER, language)
        }

        val relationshipEffects = when (yogini) {
            Yogini.MANGALA -> StringResources.get(StringKeyDosha.YOGINI_MANGALA_RELATIONSHIP, language)
            Yogini.PINGALA -> StringResources.get(StringKeyDosha.YOGINI_PINGALA_RELATIONSHIP, language)
            Yogini.DHANYA -> StringResources.get(StringKeyDosha.YOGINI_DHANYA_RELATIONSHIP, language)
            Yogini.BHRAMARI -> StringResources.get(StringKeyDosha.YOGINI_BHRAMARI_RELATIONSHIP, language)
            Yogini.BHADRIKA -> StringResources.get(StringKeyDosha.YOGINI_BHADRIKA_RELATIONSHIP, language)
            Yogini.ULKA -> StringResources.get(StringKeyDosha.YOGINI_ULKA_RELATIONSHIP, language)
            Yogini.SIDDHA -> StringResources.get(StringKeyDosha.YOGINI_SIDDHA_RELATIONSHIP, language)
            Yogini.SANKATA -> StringResources.get(StringKeyDosha.YOGINI_SANKATA_RELATIONSHIP, language)
        }

        val healthEffects = when (yogini) {
            Yogini.MANGALA -> StringResources.get(StringKeyDosha.YOGINI_MANGALA_HEALTH, language)
            Yogini.PINGALA -> StringResources.get(StringKeyDosha.YOGINI_PINGALA_HEALTH, language)
            Yogini.DHANYA -> StringResources.get(StringKeyDosha.YOGINI_DHANYA_HEALTH, language)
            Yogini.BHRAMARI -> StringResources.get(StringKeyDosha.YOGINI_BHRAMARI_HEALTH, language)
            Yogini.BHADRIKA -> StringResources.get(StringKeyDosha.YOGINI_BHADRIKA_HEALTH, language)
            Yogini.ULKA -> StringResources.get(StringKeyDosha.YOGINI_ULKA_HEALTH, language)
            Yogini.SIDDHA -> StringResources.get(StringKeyDosha.YOGINI_SIDDHA_HEALTH, language)
            Yogini.SANKATA -> StringResources.get(StringKeyDosha.YOGINI_SANKATA_HEALTH, language)
        }

        val spiritualEffects = when (yogini) {
            Yogini.MANGALA -> StringResources.get(StringKeyDosha.YOGINI_MANGALA_SPIRITUAL, language)
            Yogini.PINGALA -> StringResources.get(StringKeyDosha.YOGINI_PINGALA_SPIRITUAL, language)
            Yogini.DHANYA -> StringResources.get(StringKeyDosha.YOGINI_DHANYA_SPIRITUAL, language)
            Yogini.BHRAMARI -> StringResources.get(StringKeyDosha.YOGINI_BHRAMARI_SPIRITUAL, language)
            Yogini.BHADRIKA -> StringResources.get(StringKeyDosha.YOGINI_BHADRIKA_SPIRITUAL, language)
            Yogini.ULKA -> StringResources.get(StringKeyDosha.YOGINI_ULKA_SPIRITUAL, language)
            Yogini.SIDDHA -> StringResources.get(StringKeyDosha.YOGINI_SIDDHA_SPIRITUAL, language)
            Yogini.SANKATA -> StringResources.get(StringKeyDosha.YOGINI_SANKATA_SPIRITUAL, language)
        }

        val recommendations = when (yogini) {
            Yogini.MANGALA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_MANGALA_REC_1, language),
                StringResources.get(StringKeyDosha.YOGINI_MANGALA_REC_2, language),
                StringResources.get(StringKeyDosha.YOGINI_MANGALA_REC_3, language),
                StringResources.get(StringKeyDosha.YOGINI_MANGALA_REC_4, language)
            )
            Yogini.PINGALA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_PINGALA_REC_1, language),
                StringResources.get(StringKeyDosha.YOGINI_PINGALA_REC_2, language),
                StringResources.get(StringKeyDosha.YOGINI_PINGALA_REC_3, language),
                StringResources.get(StringKeyDosha.YOGINI_PINGALA_REC_4, language)
            )
            Yogini.DHANYA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_DHANYA_REC_1, language),
                StringResources.get(StringKeyDosha.YOGINI_DHANYA_REC_2, language),
                StringResources.get(StringKeyDosha.YOGINI_DHANYA_REC_3, language),
                StringResources.get(StringKeyDosha.YOGINI_DHANYA_REC_4, language)
            )
            Yogini.BHRAMARI -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_BHRAMARI_REC_1, language),
                StringResources.get(StringKeyDosha.YOGINI_BHRAMARI_REC_2, language),
                StringResources.get(StringKeyDosha.YOGINI_BHRAMARI_REC_3, language),
                StringResources.get(StringKeyDosha.YOGINI_BHRAMARI_REC_4, language)
            )
            Yogini.BHADRIKA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_BHADRIKA_REC_1, language),
                StringResources.get(StringKeyDosha.YOGINI_BHADRIKA_REC_2, language),
                StringResources.get(StringKeyDosha.YOGINI_BHADRIKA_REC_3, language),
                StringResources.get(StringKeyDosha.YOGINI_BHADRIKA_REC_4, language)
            )
            Yogini.ULKA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_ULKA_REC_1, language),
                StringResources.get(StringKeyDosha.YOGINI_ULKA_REC_2, language),
                StringResources.get(StringKeyDosha.YOGINI_ULKA_REC_3, language),
                StringResources.get(StringKeyDosha.YOGINI_ULKA_REC_4, language)
            )
            Yogini.SIDDHA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_SIDDHA_REC_1, language),
                StringResources.get(StringKeyDosha.YOGINI_SIDDHA_REC_2, language),
                StringResources.get(StringKeyDosha.YOGINI_SIDDHA_REC_3, language),
                StringResources.get(StringKeyDosha.YOGINI_SIDDHA_REC_4, language)
            )
            Yogini.SANKATA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_SANKATA_REC_1, language),
                StringResources.get(StringKeyDosha.YOGINI_SANKATA_REC_2, language),
                StringResources.get(StringKeyDosha.YOGINI_SANKATA_REC_3, language),
                StringResources.get(StringKeyDosha.YOGINI_SANKATA_REC_4, language)
            )
        }

        val cautionAreas = when (yogini) {
            Yogini.MANGALA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_MANGALA_CAUTION_1, language),
                StringResources.get(StringKeyDosha.YOGINI_MANGALA_CAUTION_2, language),
                StringResources.get(StringKeyDosha.YOGINI_MANGALA_CAUTION_3, language)
            )
            Yogini.PINGALA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_PINGALA_CAUTION_1, language),
                StringResources.get(StringKeyDosha.YOGINI_PINGALA_CAUTION_2, language),
                StringResources.get(StringKeyDosha.YOGINI_PINGALA_CAUTION_3, language)
            )
            Yogini.DHANYA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_DHANYA_CAUTION_1, language),
                StringResources.get(StringKeyDosha.YOGINI_DHANYA_CAUTION_2, language),
                StringResources.get(StringKeyDosha.YOGINI_DHANYA_CAUTION_3, language)
            )
            Yogini.BHRAMARI -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_BHRAMARI_CAUTION_1, language),
                StringResources.get(StringKeyDosha.YOGINI_BHRAMARI_CAUTION_2, language),
                StringResources.get(StringKeyDosha.YOGINI_BHRAMARI_CAUTION_3, language)
            )
            Yogini.BHADRIKA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_BHADRIKA_CAUTION_1, language),
                StringResources.get(StringKeyDosha.YOGINI_BHADRIKA_CAUTION_2, language),
                StringResources.get(StringKeyDosha.YOGINI_BHADRIKA_CAUTION_3, language)
            )
            Yogini.ULKA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_ULKA_CAUTION_1, language),
                StringResources.get(StringKeyDosha.YOGINI_ULKA_CAUTION_2, language),
                StringResources.get(StringKeyDosha.YOGINI_ULKA_CAUTION_3, language)
            )
            Yogini.SIDDHA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_SIDDHA_CAUTION_1, language),
                StringResources.get(StringKeyDosha.YOGINI_SIDDHA_CAUTION_2, language),
                StringResources.get(StringKeyDosha.YOGINI_SIDDHA_CAUTION_3, language)
            )
            Yogini.SANKATA -> listOf(
                StringResources.get(StringKeyDosha.YOGINI_SANKATA_CAUTION_1, language),
                StringResources.get(StringKeyDosha.YOGINI_SANKATA_CAUTION_2, language),
                StringResources.get(StringKeyDosha.YOGINI_SANKATA_CAUTION_3, language)
            )
        }

        return YoginiInterpretation(
            generalEffects = generalEffects,
            careerEffects = careerEffects,
            relationshipEffects = relationshipEffects,
            healthEffects = healthEffects,
            spiritualEffects = spiritualEffects,
            recommendations = recommendations,
            cautionAreas = cautionAreas
        )
    }

    /**
     * Generate interpretation for Antardasha
     */
    private fun generateAntardashaInterpretation(
        mahadashaYogini: Yogini,
        antardashaYogini: Yogini,
        chart: VedicChart?,
        language: Language
    ): String {
        val mahaPlanet = mahadashaYogini.planet
        val antarPlanet = antardashaYogini.planet

        // Natural friendship/enmity affects interpretation
        val relationship = getPlanetaryRelationship(mahaPlanet, antarPlanet)

        // Map comprehensive relationship to simplified interpretation categories
        val isFriendly = relationship == VedicAstrologyUtils.PlanetaryRelationship.FRIEND ||
                         relationship == VedicAstrologyUtils.PlanetaryRelationship.BEST_FRIEND
        val isHostile = relationship == VedicAstrologyUtils.PlanetaryRelationship.ENEMY ||
                        relationship == VedicAstrologyUtils.PlanetaryRelationship.BITTER_ENEMY

        val mahaName = mahadashaYogini.getLocalizedName(language)
        val antarName = antardashaYogini.getLocalizedName(language)
        val mahaPlanetName = mahaPlanet.getLocalizedName(language)
        val antarPlanetName = antarPlanet.getLocalizedName(language)

        return when {
            isFriendly -> StringResources.get(
                StringKeyDosha.YOGINI_ANTAR_FRIENDLY,
                language,
                mahaName, antarName, // %s-%s (Header)
                antarPlanetName, mahaPlanetName // %s's significations blend with %s's
            )
            isHostile -> StringResources.get(
                StringKeyDosha.YOGINI_ANTAR_HOSTILE,
                language,
                mahaName, antarName,
                antarPlanetName, mahaPlanetName
            )
            else -> StringResources.get(
                StringKeyDosha.YOGINI_ANTAR_NEUTRAL,
                language,
                mahaName, antarName,
                antarPlanetName, mahaPlanetName
            )
        }
    }

    /**
     * Get planetary relationship using centralized VedicAstrologyUtils.
     * Removes duplicate friendship/enmity data that was previously hardcoded here.
     */
    private fun getPlanetaryRelationship(planet1: Planet, planet2: Planet): VedicAstrologyUtils.PlanetaryRelationship {
        if (planet1 == planet2) return VedicAstrologyUtils.PlanetaryRelationship.FRIEND
        return VedicAstrologyUtils.getNaturalRelationship(planet1, planet2)
    }

    /**
     * Assess applicability of Yogini Dasha for a chart
     */
    private fun assessApplicability(chart: VedicChart?, language: Language): Applicability {
        if (chart == null) {
            return Applicability(
                isRecommended = true,
                applicabilityScore = 0.7,
                reasons = listOf(StringResources.get(StringKeyDosha.YOGINI_APP_UNIVERSAL, language))
            )
        }

        val reasons = mutableListOf<String>()
        var score = 0.5 // Base score

        // Check gender (if available - traditionally more applicable for females)
        if (chart.birthData.gender == com.astro.storm.data.model.Gender.FEMALE) {
            score += 0.2
            reasons.add(StringResources.get(StringKeyDosha.YOGINI_APP_FEMALE, language))
        }

        // Check Moon strength
        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
        if (moonPosition != null) {
            val moonSign = ZodiacSign.fromLongitude(moonPosition.longitude)
            // Moon strong in Taurus (exalted) or Cancer (own sign)
            if (moonSign == ZodiacSign.TAURUS || moonSign == ZodiacSign.CANCER) {
                score += 0.15
                reasons.add(StringResources.get(StringKeyDosha.YOGINI_APP_STRONG_MOON, language, moonSign.getLocalizedName(language)))
            }
        }

        // Night birth gives preference to Yogini Dasha (simplified check)
        // In production, you would check actual sunrise/sunset times
        val birthHour = chart.birthData.dateTime.hour
        if (birthHour < 6 || birthHour >= 18) {
            score += 0.1
            reasons.add(StringResources.get(StringKeyDosha.YOGINI_APP_NIGHT_BIRTH, language))
        }

        // Always applicable for relationship timing
        reasons.add(StringResources.get(StringKeyDosha.YOGINI_APP_RELATIONSHIP, language))

        // Add general applicability statement
        reasons.add(StringResources.get(StringKeyDosha.YOGINI_APP_VALIDATION, language))

        return Applicability(
            isRecommended = score >= 0.6,
            applicabilityScore = score.coerceIn(0.0, 1.0),
            reasons = reasons
        )
    }

    // ============================================
    // UTILITY METHODS
    // ============================================

    /**
     * Get current Yogini period summary
     */
    fun getCurrentPeriodSummary(result: YoginiDashaResult, language: Language = Language.ENGLISH): String {
        val maha = result.currentMahadasha ?: return StringResources.get(StringKey.DASHA_NO_ACTIVE_PERIOD, language)
        val antar = result.currentAntardasha

        // Helper to format days
        fun formatDays(days: Long): String {
             val months = days / 30
             val remainingDays = days % 30
             val parts = mutableListOf<String>()
             if (months > 0) parts.add("$months m")
             if (remainingDays > 0) parts.add("$remainingDays d")
             return if (parts.isEmpty()) "0 d" else parts.joinToString(" ")
        }

        return buildString {
            appendLine("${StringResources.get(StringKeyDosha.YOGINI_DASHA_TITLE, language)}: ${maha.yogini.getLocalizedName(language)}")
            appendLine("${StringResources.get(StringKey.PLANET, language)}: ${maha.yogini.planet.getLocalizedName(language)}")
            appendLine("${StringResources.get(StringKey.DASHA_DURATION, language)}: ${maha.durationYears} ${StringResources.get(StringKey.YEARS, language)}")
            appendLine("${StringResources.get(StringKey.DASHA_PROGRESS, language)}: ${String.format("%.1f", maha.getProgressPercent())}%")
            appendLine("${StringResources.get(StringKey.DASHA_REMAINING, language)}: ${formatDays(maha.getRemainingDays())}")

            if (antar != null) {
                appendLine()
                appendLine("${StringResources.get(StringKey.DASHA_ANTARDASHA, language)}: ${antar.yogini.getLocalizedName(language)}")
                appendLine("${StringResources.get(StringKey.DASHA_PROGRESS, language)}: ${String.format("%.1f", antar.getProgressPercent())}%")
                append("${StringResources.get(StringKey.DASHA_REMAINING, language)}: ${formatDays(antar.getRemainingDays())}")
            }
        }
    }
    /**
     * Get the Yogini sequence starting from a given Yogini
     * Returns all 8 Yoginis in order, starting from the specified Yogini
     */
    fun getYoginiSequence(startingYogini: Yogini): List<Yogini> {
        val sequence = mutableListOf<Yogini>()
        var currentIndex = startingYogini.ordinal
        for (i in 0 until 8) {
            sequence.add(Yogini.fromIndex(currentIndex))
            currentIndex = (currentIndex + 1) % 8
        }
        return sequence
    }
}

