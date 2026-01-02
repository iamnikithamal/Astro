package com.astro.storm.ephemeris

import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.model.Nakshatra
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.DashaUtils.coerceIn
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val MATH_CONTEXT = DashaUtils.MATH_CONTEXT
private val DAYS_PER_YEAR_BD = DashaUtils.DAYS_PER_YEAR
private val NAKSHATRA_SPAN_BD = DashaUtils.NAKSHATRA_SPAN
private val TOTAL_CYCLE_YEARS_BD = BigDecimal("120")

private fun yearsToRoundedDays(years: Double): Long = DashaUtils.yearsToRoundedDays(years)
private fun yearsToRoundedDays(years: BigDecimal): Long = DashaUtils.yearsToRoundedDays(years)
private fun yearsToSeconds(years: BigDecimal): Long = DashaUtils.yearsToSeconds(years)

object DashaCalculator {

    private val DASHA_YEARS: Map<Planet, BigDecimal> = mapOf(
        Planet.KETU to BigDecimal("7"),
        Planet.VENUS to BigDecimal("20"),
        Planet.SUN to BigDecimal("6"),
        Planet.MOON to BigDecimal("10"),
        Planet.MARS to BigDecimal("7"),
        Planet.RAHU to BigDecimal("18"),
        Planet.JUPITER to BigDecimal("16"),
        Planet.SATURN to BigDecimal("19"),
        Planet.MERCURY to BigDecimal("17")
    )

    private val DASHA_SEQUENCE = listOf(
        Planet.KETU,
        Planet.VENUS,
        Planet.SUN,
        Planet.MOON,
        Planet.MARS,
        Planet.RAHU,
        Planet.JUPITER,
        Planet.SATURN,
        Planet.MERCURY
    )

    private const val MAX_MAHADASHAS = 18 // Allow for more than 120 years (Vimshottari is based on 120 but people live longer sometimes)

    fun getDashaYears(planet: Planet): Double {
        return DASHA_YEARS[planet]?.toDouble() ?: 0.0
    }

    enum class CalculationDepth {
        MAHADASHA_ONLY,
        WITH_ANTARDASHA,
        WITH_PRATYANTARDASHA,
        WITH_SOOKSHMADASHA,
        FULL
    }

    data class Mahadasha(
        val planet: Planet,
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
        val durationYears: Double,
        val antardashas: List<Antardasha>
    ) {
        fun isActiveOn(date: LocalDateTime): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        fun isActiveOnDate(date: LocalDate): Boolean {
            return !date.isBefore(startDate.toLocalDate()) && !date.isAfter(endDate.toLocalDate())
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDateTime.now())

        fun getActiveAntardasha(): Antardasha? = getAntardashaOn(LocalDateTime.now())

        fun getAntardashaOn(date: LocalDateTime): Antardasha? {
            return antardashas.find { it.isActiveOn(date) }
        }

        fun getElapsedYears(asOf: LocalDateTime = LocalDateTime.now()): Double {
            if (asOf.isBefore(startDate)) return 0.0
            if (asOf.isAfter(endDate)) return durationYears
            val elapsedSeconds = ChronoUnit.SECONDS.between(startDate, asOf)
            return elapsedSeconds / (DashaUtils.DAYS_PER_YEAR.toDouble() * 24 * 3600)
        }

        fun getRemainingYears(asOf: LocalDateTime = LocalDateTime.now()): Double {
            return (durationYears - getElapsedYears(asOf)).coerceAtLeast(0.0)
        }

        fun getProgressPercent(asOf: LocalDateTime = LocalDateTime.now()): Double {
            if (durationYears <= 0) return 0.0
            return ((getElapsedYears(asOf) / durationYears) * 100).coerceIn(0.0, 100.0)
        }
    }

    data class Antardasha(
        val planet: Planet,
        val mahadashaPlanet: Planet,
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
        val durationSeconds: Long,
        val pratyantardashas: List<Pratyantardasha> = emptyList()
    ) {
        val durationYears: Double
            get() = durationSeconds / (DashaUtils.DAYS_PER_YEAR.toDouble() * 24 * 3600)

        fun isActiveOn(date: LocalDateTime): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDateTime.now())

        fun getActivePratyantardasha(): Pratyantardasha? = getPratyantardashaOn(LocalDateTime.now())

        fun getPratyantardashaOn(date: LocalDateTime): Pratyantardasha? {
            return pratyantardashas.find { it.isActiveOn(date) }
        }

        fun getElapsedSeconds(asOf: LocalDateTime = LocalDateTime.now()): Long {
            if (asOf.isBefore(startDate)) return 0
            if (asOf.isAfter(endDate)) return durationSeconds
            return ChronoUnit.SECONDS.between(startDate, asOf)
        }

        fun getRemainingSeconds(asOf: LocalDateTime = LocalDateTime.now()): Long {
            return (durationSeconds - getElapsedSeconds(asOf)).coerceAtLeast(0)
        }

        fun getProgressPercent(asOf: LocalDateTime = LocalDateTime.now()): Double {
            if (durationSeconds <= 0L) return 0.0
            return ((getElapsedSeconds(asOf).toDouble() / durationSeconds) * 100).coerceIn(0.0, 100.0)
        }
    }

    data class Pratyantardasha(
        val planet: Planet,
        val antardashaPlanet: Planet,
        val mahadashaPlanet: Planet,
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
        val durationSeconds: Long,
        val sookshmadashas: List<Sookshmadasha> = emptyList()
    ) {
        fun isActiveOn(date: LocalDateTime): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDateTime.now())

        fun getActiveSookshmadasha(): Sookshmadasha? = getSookshmadashaOn(LocalDateTime.now())

        fun getSookshmadashaOn(date: LocalDateTime): Sookshmadasha? {
            return sookshmadashas.find { it.isActiveOn(date) }
        }
    }

    data class Sookshmadasha(
        val planet: Planet,
        val pratyantardashaPlanet: Planet,
        val antardashaPlanet: Planet,
        val mahadashaPlanet: Planet,
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
        val durationSeconds: Long,
        val pranadashas: List<Pranadasha> = emptyList()
    ) {
        fun isActiveOn(date: LocalDateTime): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDateTime.now())

        fun getActivePranadasha(): Pranadasha? = getPranadashaOn(LocalDateTime.now())

        fun getPranadashaOn(date: LocalDateTime): Pranadasha? {
            return pranadashas.find { it.isActiveOn(date) }
        }
    }

    data class Pranadasha(
        val planet: Planet,
        val sookshmadashaPlanet: Planet,
        val pratyantardashaPlanet: Planet,
        val antardashaPlanet: Planet,
        val mahadashaPlanet: Planet,
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
        val durationSeconds: Long,
        val dehadashas: List<Dehadasha> = emptyList()
    ) {
        fun isActiveOn(date: LocalDateTime): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDateTime.now())

        fun getActiveDehadasha(): Dehadasha? = getDehadashaOn(LocalDateTime.now())

        fun getDehadashaOn(date: LocalDateTime): Dehadasha? {
            return dehadashas.find { it.isActiveOn(date) }
        }

        fun getDurationString(): String {
            val totalMins = durationSeconds / 60
            val hours = totalMins / 60
            val mins = totalMins % 60
            return when {
                hours >= 24 -> {
                    val days = hours / 24
                    val remainingHours = hours % 24
                    "${days}d ${remainingHours}h ${mins}m"
                }
                hours > 0 -> "${hours}h ${mins}m"
                else -> "${mins}m"
            }
        }

        fun getLocalizedDurationString(language: Language): String {
            val totalMins = durationSeconds / 60
            val hours = totalMins / 60
            val mins = totalMins % 60
            val daysShort = StringResources.get(StringKey.DAYS_SHORT, language)
            val hoursShort = StringResources.get(StringKey.HOURS_SHORT, language)
            val minutesShort = StringResources.get(StringKey.MINUTES_SHORT, language)
            return when {
                hours >= 24 -> {
                    val days = hours / 24
                    val remainingHours = hours % 24
                    "${days}$daysShort ${remainingHours}$hoursShort ${mins}$minutesShort"
                }
                hours > 0 -> "${hours}$hoursShort ${mins}$minutesShort"
                else -> "${mins}$minutesShort"
            }
        }
    }

    data class Dehadasha(
        val planet: Planet,
        val pranadashaPlanet: Planet,
        val sookshmadashaPlanet: Planet,
        val pratyantardashaPlanet: Planet,
        val antardashaPlanet: Planet,
        val mahadashaPlanet: Planet,
        val startDate: LocalDateTime,
        val endDate: LocalDateTime,
        val durationSeconds: Long
    ) {
        fun isActiveOn(date: LocalDateTime): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDateTime.now())

        fun getDurationString(): String {
            val totalMins = durationSeconds / 60
            val hours = totalMins / 60
            val mins = totalMins % 60
            return if (hours > 0) "${hours}h ${mins}m" else "${mins}m"
        }

        fun getLocalizedDurationString(language: Language): String {
            val totalMins = durationSeconds / 60
            val hours = totalMins / 60
            val mins = totalMins % 60
            val hoursShort = StringResources.get(StringKey.HOURS_SHORT, language)
            val minutesShort = StringResources.get(StringKey.MINUTES_SHORT, language)
            return if (hours > 0) "${hours}$hoursShort ${mins}$minutesShort" else "${mins}$minutesShort"
        }
    }

    data class DashaSandhi(
        val fromPlanet: Planet,
        val toPlanet: Planet,
        val transitionDate: LocalDateTime,
        val level: DashaLevel,
        val sandhiStartDate: LocalDateTime,
        val sandhiEndDate: LocalDateTime
    ) {
        fun isWithinSandhi(date: LocalDateTime): Boolean {
            return !date.isBefore(sandhiStartDate) && !date.isAfter(sandhiEndDate)
        }
    }

    enum class DashaLevel(val displayName: String, val levelNumber: Int) {
        MAHADASHA("Mahadasha", 1),
        ANTARDASHA("Antardasha/Bhukti", 2),
        PRATYANTARDASHA("Pratyantardasha", 3),
        SOOKSHMADASHA("Sookshmadasha", 4),
        PRANADASHA("Pranadasha", 5),
        DEHADASHA("Dehadasha", 6);

        fun getLocalizedName(language: Language): String = when (this) {
            MAHADASHA -> StringResources.get(StringKey.DASHA_MAHADASHA, language)
            ANTARDASHA -> StringResources.get(StringKey.DASHA_ANTARDASHA, language)
            PRATYANTARDASHA -> StringResources.get(StringKey.DASHA_PRATYANTARDASHA, language)
            SOOKSHMADASHA -> StringResources.get(StringKey.DASHA_SOOKSHMADASHA, language)
            PRANADASHA -> StringResources.get(StringKey.DASHA_PRANADASHA, language)
            DEHADASHA -> StringResources.get(StringKey.DASHA_DEHADASHA, language)
        }
    }

    data class DashaTimeline(
        val birthDate: LocalDateTime,
        val birthNakshatra: Nakshatra,
        val birthNakshatraPada: Int,
        val birthNakshatraLord: Planet,
        val nakshatraProgress: Double,
        val balanceOfFirstDasha: Double,
        val mahadashas: List<Mahadasha>,
        val currentMahadasha: Mahadasha?,
        val currentAntardasha: Antardasha?,
        val currentPratyantardasha: Pratyantardasha?,
        val currentSookshmadasha: Sookshmadasha?,
        val currentPranadasha: Pranadasha?,
        val currentDehadasha: Dehadasha?,
        val upcomingSandhis: List<DashaSandhi>
    ) {
        fun getCurrentPeriodDescription(): String {
            return buildString {
                currentMahadasha?.let { md ->
                    append("${md.planet.displayName} Mahadasha")
                    currentAntardasha?.let { ad ->
                        append(" → ${ad.planet.displayName} Bhukti")
                        currentPratyantardasha?.let { pd ->
                            append(" → ${pd.planet.displayName} Pratyantar")
                            currentSookshmadasha?.let { sd ->
                                append(" → ${sd.planet.displayName} Sookshma")
                            }
                        }
                    }
                } ?: append("No active Dasha period")
            }
        }

        fun getLocalizedCurrentPeriodDescription(language: Language): String {
            return buildString {
                currentMahadasha?.let { md ->
                    append("${md.planet.getLocalizedName(language)} ${DashaLevel.MAHADASHA.getLocalizedName(language)}")
                    currentAntardasha?.let { ad ->
                        append(" → ${ad.planet.getLocalizedName(language)} ${StringResources.get(StringKey.DASHA_BHUKTI, language)}")
                        currentPratyantardasha?.let { pd ->
                            append(" → ${pd.planet.getLocalizedName(language)} ${StringResources.get(StringKey.DASHA_PRATYANTAR, language)}")
                            currentSookshmadasha?.let { sd ->
                                append(" → ${sd.planet.getLocalizedName(language)} ${StringResources.get(StringKey.DASHA_SOOKSHMA, language)}")
                            }
                        }
                    }
                } ?: append(StringResources.get(StringKey.DASHA_NO_ACTIVE_PERIOD, language))
            }
        }

        fun getFullPeriodDescription(): String {
            return buildString {
                currentMahadasha?.let { md ->
                    append("${md.planet.displayName} Mahadasha")
                    currentAntardasha?.let { ad ->
                        append(" → ${ad.planet.displayName} Bhukti")
                        currentPratyantardasha?.let { pd ->
                            append(" → ${pd.planet.displayName} Pratyantar")
                            currentSookshmadasha?.let { sd ->
                                append(" → ${sd.planet.displayName} Sookshma")
                                currentPranadasha?.let { prd ->
                                    append(" → ${prd.planet.displayName} Prana")
                                    currentDehadasha?.let { dd ->
                                        append(" → ${dd.planet.displayName} Deha")
                                    }
                                }
                            }
                        }
                    }
                } ?: append("No active Dasha period")
            }
        }

        fun getLocalizedFullPeriodDescription(language: Language): String {
            return buildString {
                currentMahadasha?.let { md ->
                    append("${md.planet.getLocalizedName(language)} ${DashaLevel.MAHADASHA.getLocalizedName(language)}")
                    currentAntardasha?.let { ad ->
                        append(" → ${ad.planet.getLocalizedName(language)} ${StringResources.get(StringKey.DASHA_BHUKTI, language)}")
                        currentPratyantardasha?.let { pd ->
                            append(" → ${pd.planet.getLocalizedName(language)} ${StringResources.get(StringKey.DASHA_PRATYANTAR, language)}")
                            currentSookshmadasha?.let { sd ->
                                append(" → ${sd.planet.getLocalizedName(language)} ${StringResources.get(StringKey.DASHA_SOOKSHMA, language)}")
                                currentPranadasha?.let { prd ->
                                    append(" → ${prd.planet.getLocalizedName(language)} ${StringResources.get(StringKey.DASHA_PRANA, language)}")
                                    currentDehadasha?.let { dd ->
                                        append(" → ${dd.planet.getLocalizedName(language)} ${StringResources.get(StringKey.DASHA_DEHA, language)}")
                                    }
                                }
                            }
                        }
                    }
                } ?: append(StringResources.get(StringKey.DASHA_NO_ACTIVE_PERIOD, language))
            }
        }

        fun getShortDescription(): String {
            return buildString {
                currentMahadasha?.let { md ->
                    append(md.planet.symbol)
                    currentAntardasha?.let { ad ->
                        append("-${ad.planet.symbol}")
                        currentPratyantardasha?.let { pd ->
                            append("-${pd.planet.symbol}")
                        }
                    }
                } ?: append("--")
            }
        }

        fun getFullShortDescription(): String {
            return buildString {
                currentMahadasha?.let { md ->
                    append(md.planet.symbol)
                    currentAntardasha?.let { ad ->
                        append("-${ad.planet.symbol}")
                        currentPratyantardasha?.let { pd ->
                            append("-${pd.planet.symbol}")
                            currentSookshmadasha?.let { sd ->
                                append("-${sd.planet.symbol}")
                                currentPranadasha?.let { prd ->
                                    append("-${prd.planet.symbol}")
                                    currentDehadasha?.let { dd ->
                                        append("-${dd.planet.symbol}")
                                    }
                                }
                            }
                        }
                    }
                } ?: append("--")
            }
        }

        fun getDashaAtDate(date: LocalDateTime): DashaPeriodInfo {
            val mahadasha = mahadashas.find { it.isActiveOn(date) }
            val antardasha = mahadasha?.getAntardashaOn(date)
            val pratyantardasha = antardasha?.getPratyantardashaOn(date)
            val sookshmadasha = pratyantardasha?.getSookshmadashaOn(date)
            val pranadasha = sookshmadasha?.getPranadashaOn(date)
            val dehadasha = pranadasha?.getDehadashaOn(date)

            return DashaPeriodInfo(
                date = date,
                mahadasha = mahadasha,
                antardasha = antardasha,
                pratyantardasha = pratyantardasha,
                sookshmadasha = sookshmadasha,
                pranadasha = pranadasha,
                dehadasha = dehadasha
            )
        }

        fun getNextMahadasha(): Mahadasha? {
            val now = LocalDateTime.now()
            return mahadashas.find { it.startDate.isAfter(now) }
        }

        fun getUpcomingSandhisWithin(days: Int): List<DashaSandhi> {
            val now = LocalDateTime.now()
            val futureDate = now.plusDays(days.toLong())
            return upcomingSandhis.filter { sandhi ->
                !sandhi.transitionDate.isBefore(now) && !sandhi.transitionDate.isAfter(futureDate)
            }
        }

        fun getActiveDashaLords(): List<Pair<DashaLevel, Planet>> {
            val lords = mutableListOf<Pair<DashaLevel, Planet>>()
            currentMahadasha?.let { lords.add(DashaLevel.MAHADASHA to it.planet) }
            currentAntardasha?.let { lords.add(DashaLevel.ANTARDASHA to it.planet) }
            currentPratyantardasha?.let { lords.add(DashaLevel.PRATYANTARDASHA to it.planet) }
            currentSookshmadasha?.let { lords.add(DashaLevel.SOOKSHMADASHA to it.planet) }
            currentPranadasha?.let { lords.add(DashaLevel.PRANADASHA to it.planet) }
            currentDehadasha?.let { lords.add(DashaLevel.DEHADASHA to it.planet) }
            return lords
        }
    }

    data class DashaPeriodInfo(
        val date: LocalDateTime,
        val mahadasha: Mahadasha?,
        val antardasha: Antardasha?,
        val pratyantardasha: Pratyantardasha?,
        val sookshmadasha: Sookshmadasha?,
        val pranadasha: Pranadasha? = null,
        val dehadasha: Dehadasha? = null
    ) {
        fun getAllLords(): List<Planet> {
            return listOfNotNull(
                mahadasha?.planet,
                antardasha?.planet,
                pratyantardasha?.planet,
                sookshmadasha?.planet,
                pranadasha?.planet,
                dehadasha?.planet
            )
        }

        fun getCombinedPeriodString(): String {
            return getAllLords().joinToString("-") { it.displayName }
        }
    }

    fun calculateDashaTimeline(chart: VedicChart): DashaTimeline {
        val birthDateTime = chart.birthData.dateTime
        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
            ?: throw IllegalArgumentException("Moon position not found in chart.")

        val moonLongitude = moonPosition.longitude
        val (birthNakshatra, pada) = Nakshatra.fromLongitude(moonLongitude)
        val nakshatraLord = birthNakshatra.ruler

        val moonLongitudeBd = BigDecimal(moonLongitude.toString())
        val nakshatraStartBd = BigDecimal(birthNakshatra.startDegree.toString())

        var degreesIntoNakshatra = moonLongitudeBd.subtract(nakshatraStartBd, MATH_CONTEXT)
            .remainder(NAKSHATRA_SPAN_BD, MATH_CONTEXT)

        if (degreesIntoNakshatra < BigDecimal.ZERO) {
            degreesIntoNakshatra = degreesIntoNakshatra.add(NAKSHATRA_SPAN_BD, MATH_CONTEXT)
        }

        val nakshatraProgressBd = degreesIntoNakshatra.divide(NAKSHATRA_SPAN_BD, MATH_CONTEXT)
        val nakshatraProgress = nakshatraProgressBd.toDouble().coerceIn(0.0, 1.0)

        val firstDashaYearsBd = DASHA_YEARS[nakshatraLord] ?: BigDecimal.ZERO
        val elapsedInFirstDashaBd = nakshatraProgressBd.multiply(firstDashaYearsBd, MATH_CONTEXT)
        val balanceOfFirstDashaBd = firstDashaYearsBd.subtract(elapsedInFirstDashaBd, MATH_CONTEXT)
        val balanceOfFirstDasha = balanceOfFirstDashaBd.toDouble().coerceAtLeast(0.0)

        val now = LocalDateTime.now()

        val mahadashas = calculateAllMahadashasOptimized(
            birthDate = birthDateTime,
            startingDashaLord = nakshatraLord,
            balanceOfFirstDashaBd = balanceOfFirstDashaBd,
            targetDate = now
        )

        val currentMahadasha = mahadashas.find { it.isActiveOn(now) }
        val currentAntardasha = currentMahadasha?.getAntardashaOn(now)

        var currentPratyantardasha: Pratyantardasha? = null
        var currentSookshmadasha: Sookshmadasha? = null
        var currentPranadasha: Pranadasha? = null
        var currentDehadasha: Dehadasha? = null

        if (currentAntardasha != null) {
            val pratyantardashas = calculatePratyantardashasForAntardasha(currentAntardasha)
            currentPratyantardasha = pratyantardashas.find { it.isActiveOn(now) }

            if (currentPratyantardasha != null) {
                val sookshmadashas = calculateSookshmadashasForPratyantardasha(currentPratyantardasha)
                currentSookshmadasha = sookshmadashas.find { it.isActiveOn(now) }

                if (currentSookshmadasha != null) {
                    val pranadashas = calculatePranadashasForSookshmadasha(currentSookshmadasha)
                    currentPranadasha = pranadashas.find { it.isActiveOn(now) }

                    if (currentPranadasha != null) {
                        val dehadashas = calculateDehadashasForPranadasha(currentPranadasha)
                        currentDehadasha = dehadashas.find { it.isActiveOn(now) }
                    }
                }
            }
        }

        val upcomingSandhis = calculateUpcomingSandhis(mahadashas, now, lookAheadDays = 365)

        return DashaTimeline(
            birthDate = birthDateTime,
            birthNakshatra = birthNakshatra,
            birthNakshatraPada = pada,
            birthNakshatraLord = nakshatraLord,
            nakshatraProgress = nakshatraProgress,
            balanceOfFirstDasha = balanceOfFirstDasha,
            mahadashas = mahadashas,
            currentMahadasha = currentMahadasha,
            currentAntardasha = currentAntardasha,
            currentPratyantardasha = currentPratyantardasha,
            currentSookshmadasha = currentSookshmadasha,
            currentPranadasha = currentPranadasha,
            currentDehadasha = currentDehadasha,
            upcomingSandhis = upcomingSandhis
        )
    }

    private fun calculateAllMahadashasOptimized(
        birthDate: LocalDateTime,
        startingDashaLord: Planet,
        balanceOfFirstDashaBd: BigDecimal,
        targetDate: LocalDateTime
    ): List<Mahadasha> {
        val mahadashas = mutableListOf<Mahadasha>()
        var currentStartDate = birthDate

        val startIndex = DASHA_SEQUENCE.indexOf(startingDashaLord)
        if (startIndex == -1) {
            throw IllegalArgumentException("Invalid starting dasha lord: $startingDashaLord")
        }

        require(balanceOfFirstDashaBd >= BigDecimal.ZERO) {
            "Balance of first dasha must be non-negative, got: $balanceOfFirstDashaBd"
        }

        val firstDashaSeconds = yearsToSeconds(balanceOfFirstDashaBd)
        val firstDashaEndDate = currentStartDate.plusSeconds(firstDashaSeconds)
        val isFirstActive = !targetDate.isBefore(currentStartDate) && !targetDate.isAfter(firstDashaEndDate)

        val firstAntardashas = calculateAntardashasOptimized(
            mahadashaPlanet = startingDashaLord,
            mahadashaStart = currentStartDate,
            mahadashaEnd = firstDashaEndDate,
            mahadashaDurationYearsBd = balanceOfFirstDashaBd,
            isCurrentMahadasha = isFirstActive,
            targetDate = targetDate
        )

        mahadashas.add(
            Mahadasha(
                planet = startingDashaLord,
                startDate = currentStartDate,
                endDate = firstDashaEndDate,
                durationYears = balanceOfFirstDashaBd.toDouble(),
                antardashas = firstAntardashas
            )
        )
        currentStartDate = firstDashaEndDate

        repeat(MAX_MAHADASHAS - 1) { cycle ->
            val planetIndex = (startIndex + 1 + cycle) % DASHA_SEQUENCE.size
            val planet = DASHA_SEQUENCE[planetIndex]
            val dashaYearsBd = DASHA_YEARS[planet] ?: BigDecimal.ZERO
            val dashaSeconds = yearsToSeconds(dashaYearsBd)
            val endDate = currentStartDate.plusSeconds(dashaSeconds)
            val isActive = !targetDate.isBefore(currentStartDate) && !targetDate.isAfter(endDate)

            val antardashas = calculateAntardashasOptimized(
                mahadashaPlanet = planet,
                mahadashaStart = currentStartDate,
                mahadashaEnd = endDate,
                mahadashaDurationYearsBd = dashaYearsBd,
                isCurrentMahadasha = isActive,
                targetDate = targetDate
            )

            mahadashas.add(
                Mahadasha(
                    planet = planet,
                    startDate = currentStartDate,
                    endDate = endDate,
                    durationYears = dashaYearsBd.toDouble(),
                    antardashas = antardashas
                )
            )
            currentStartDate = endDate
        }

        return mahadashas
    }

    private fun calculateAntardashasOptimized(
        mahadashaPlanet: Planet,
        mahadashaStart: LocalDateTime,
        mahadashaEnd: LocalDateTime,
        mahadashaDurationYearsBd: BigDecimal,
        isCurrentMahadasha: Boolean,
        targetDate: LocalDateTime
    ): List<Antardasha> {
        val antardashas = mutableListOf<Antardasha>()
        var currentStart = mahadashaStart

        val startIndex = DASHA_SEQUENCE.indexOf(mahadashaPlanet)

        for (i in 0 until 9) {
            val planetIndex = (startIndex + i) % DASHA_SEQUENCE.size
            val antarPlanet = DASHA_SEQUENCE[planetIndex]

            val antarYearsBd = DASHA_YEARS[antarPlanet] ?: BigDecimal.ZERO
            val proportionalDurationBd = antarYearsBd
                .divide(TOTAL_CYCLE_YEARS_BD, MATH_CONTEXT)
                .multiply(mahadashaDurationYearsBd, MATH_CONTEXT)

            val antarSeconds = yearsToSeconds(proportionalDurationBd)
            val antarEnd = currentStart.plusSeconds(antarSeconds)

            val pratyantardashas = if (isCurrentMahadasha && 
                !targetDate.isBefore(currentStart) && !targetDate.isAfter(antarEnd)) {
                calculatePratyantardashasInternal(
                    mahadashaPlanet = mahadashaPlanet,
                    antardashaPlanet = antarPlanet,
                    antarStart = currentStart,
                    antarEnd = antarEnd,
                    antarDurationYearsBd = proportionalDurationBd
                )
            } else {
                emptyList()
            }

            antardashas.add(
                Antardasha(
                    planet = antarPlanet,
                    mahadashaPlanet = mahadashaPlanet,
                    startDate = currentStart,
                    endDate = antarEnd,
                    durationSeconds = antarSeconds,
                    pratyantardashas = pratyantardashas
                )
            )
            currentStart = antarEnd
        }

        return antardashas
    }

    fun calculatePratyantardashasForAntardasha(antardasha: Antardasha): List<Pratyantardasha> {
        if (antardasha.pratyantardashas.isNotEmpty()) {
            return antardasha.pratyantardashas
        }

        val antarDurationYearsBd = BigDecimal(antardasha.durationYears.toString())
        return calculatePratyantardashasInternal(
            mahadashaPlanet = antardasha.mahadashaPlanet,
            antardashaPlanet = antardasha.planet,
            antarStart = antardasha.startDate,
            antarEnd = antardasha.endDate,
            antarDurationYearsBd = antarDurationYearsBd
        )
    }

    private fun calculatePratyantardashasInternal(
        mahadashaPlanet: Planet,
        antardashaPlanet: Planet,
        antarStart: LocalDateTime,
        antarEnd: LocalDateTime,
        antarDurationYearsBd: BigDecimal
    ): List<Pratyantardasha> {
        val pratyantardashas = mutableListOf<Pratyantardasha>()
        var currentStart = antarStart

        val startIndex = DASHA_SEQUENCE.indexOf(antardashaPlanet)

        for (i in 0 until 9) {
            val planetIndex = (startIndex + i) % DASHA_SEQUENCE.size
            val pratyantarPlanet = DASHA_SEQUENCE[planetIndex]

            val pratyantarYearsBd = DASHA_YEARS[pratyantarPlanet] ?: BigDecimal.ZERO
            val proportionalDurationBd = pratyantarYearsBd
                .divide(TOTAL_CYCLE_YEARS_BD, MATH_CONTEXT)
                .multiply(antarDurationYearsBd, MATH_CONTEXT)

            val pratyantarSeconds = yearsToSeconds(proportionalDurationBd)
            val pratyantarEnd = currentStart.plusSeconds(pratyantarSeconds)

            pratyantardashas.add(
                Pratyantardasha(
                    planet = pratyantarPlanet,
                    antardashaPlanet = antardashaPlanet,
                    mahadashaPlanet = mahadashaPlanet,
                    startDate = currentStart,
                    endDate = pratyantarEnd,
                    durationSeconds = pratyantarSeconds,
                    sookshmadashas = emptyList()
                )
            )
            currentStart = pratyantarEnd
        }

        return pratyantardashas
    }

    fun calculateSookshmadashasForPratyantardasha(pratyantardasha: Pratyantardasha): List<Sookshmadasha> {
        if (pratyantardasha.sookshmadashas.isNotEmpty()) {
            return pratyantardasha.sookshmadashas
        }

        val pratyantarDurationYearsBd = BigDecimal((pratyantardasha.durationSeconds.toDouble() / (DashaUtils.DAYS_PER_YEAR.toDouble() * 24 * 3600)).toString())
        return calculateSookshmadashasInternal(
            mahadashaPlanet = pratyantardasha.mahadashaPlanet,
            antardashaPlanet = pratyantardasha.antardashaPlanet,
            pratyantardashaPlanet = pratyantardasha.planet,
            pratyantarStart = pratyantardasha.startDate,
            pratyantarEnd = pratyantardasha.endDate,
            pratyantarDurationYearsBd = pratyantarDurationYearsBd
        )
    }

    private fun calculateSookshmadashasInternal(
        mahadashaPlanet: Planet,
        antardashaPlanet: Planet,
        pratyantardashaPlanet: Planet,
        pratyantarStart: LocalDateTime,
        pratyantarEnd: LocalDateTime,
        pratyantarDurationYearsBd: BigDecimal
    ): List<Sookshmadasha> {
        val sookshmadashas = mutableListOf<Sookshmadasha>()
        var currentStart = pratyantarStart

        val startIndex = DASHA_SEQUENCE.indexOf(pratyantardashaPlanet)

        for (i in 0 until 9) {
            val planetIndex = (startIndex + i) % DASHA_SEQUENCE.size
            val sookshmaPlanet = DASHA_SEQUENCE[planetIndex]

            val sookshmaYearsBd = DASHA_YEARS[sookshmaPlanet] ?: BigDecimal.ZERO
            val proportionalDurationBd = sookshmaYearsBd
                .divide(TOTAL_CYCLE_YEARS_BD, MATH_CONTEXT)
                .multiply(pratyantarDurationYearsBd, MATH_CONTEXT)

            val sookshmaSeconds = yearsToSeconds(proportionalDurationBd)
            val sookshmaEnd = currentStart.plusSeconds(sookshmaSeconds)

            sookshmadashas.add(
                Sookshmadasha(
                    planet = sookshmaPlanet,
                    pratyantardashaPlanet = pratyantardashaPlanet,
                    antardashaPlanet = antardashaPlanet,
                    mahadashaPlanet = mahadashaPlanet,
                    startDate = currentStart,
                    endDate = sookshmaEnd,
                    durationSeconds = sookshmaSeconds,
                    pranadashas = emptyList()
                )
            )
            currentStart = sookshmaEnd
        }

        return sookshmadashas
    }

    fun calculatePranadashasForSookshmadasha(sookshmadasha: Sookshmadasha): List<Pranadasha> {
        if (sookshmadasha.pranadashas.isNotEmpty()) {
            return sookshmadasha.pranadashas
        }

        return calculatePranadashasInternal(
            mahadashaPlanet = sookshmadasha.mahadashaPlanet,
            antardashaPlanet = sookshmadasha.antardashaPlanet,
            pratyantardashaPlanet = sookshmadasha.pratyantardashaPlanet,
            sookshmadashaPlanet = sookshmadasha.planet,
            sookshmaStart = sookshmadasha.startDate,
            sookshmaEnd = sookshmadasha.endDate,
            sookshmaDurationSeconds = sookshmadasha.durationSeconds
        )
    }

    private fun calculatePranadashasInternal(
        mahadashaPlanet: Planet,
        antardashaPlanet: Planet,
        pratyantardashaPlanet: Planet,
        sookshmadashaPlanet: Planet,
        sookshmaStart: LocalDateTime,
        sookshmaEnd: LocalDateTime,
        sookshmaDurationSeconds: Long
    ): List<Pranadasha> {
        val pranadashas = mutableListOf<Pranadasha>()
        var currentStart = sookshmaStart

        val startIndex = DASHA_SEQUENCE.indexOf(sookshmadashaPlanet)

        for (i in 0 until 9) {
            val planetIndex = (startIndex + i) % DASHA_SEQUENCE.size
            val pranaPlanet = DASHA_SEQUENCE[planetIndex]

            val pranaYearsBd = DASHA_YEARS[pranaPlanet] ?: BigDecimal.ZERO
            val proportionalSeconds = pranaYearsBd
                .divide(TOTAL_CYCLE_YEARS_BD, MATH_CONTEXT)
                .multiply(BigDecimal(sookshmaDurationSeconds), MATH_CONTEXT)
                .toLong()
                .coerceAtLeast(1L)

            val pranaEnd = currentStart.plusSeconds(proportionalSeconds)

            pranadashas.add(
                Pranadasha(
                    planet = pranaPlanet,
                    sookshmadashaPlanet = sookshmadashaPlanet,
                    pratyantardashaPlanet = pratyantardashaPlanet,
                    antardashaPlanet = antardashaPlanet,
                    mahadashaPlanet = mahadashaPlanet,
                    startDate = currentStart,
                    endDate = pranaEnd,
                    durationSeconds = proportionalSeconds,
                    dehadashas = emptyList()
                )
            )
            currentStart = pranaEnd
        }

        return pranadashas
    }

    fun calculateDehadashasForPranadasha(pranadasha: Pranadasha): List<Dehadasha> {
        if (pranadasha.dehadashas.isNotEmpty()) {
            return pranadasha.dehadashas
        }

        return calculateDehadashasInternal(
            mahadashaPlanet = pranadasha.mahadashaPlanet,
            antardashaPlanet = pranadasha.antardashaPlanet,
            pratyantardashaPlanet = pranadasha.pratyantardashaPlanet,
            sookshmadashaPlanet = pranadasha.sookshmadashaPlanet,
            pranadashaPlanet = pranadasha.planet,
            pranaStart = pranadasha.startDate,
            pranaEnd = pranadasha.endDate,
            pranaDurationSeconds = pranadasha.durationSeconds
        )
    }

    private fun calculateDehadashasInternal(
        mahadashaPlanet: Planet,
        antardashaPlanet: Planet,
        pratyantardashaPlanet: Planet,
        sookshmadashaPlanet: Planet,
        pranadashaPlanet: Planet,
        pranaStart: LocalDateTime,
        pranaEnd: LocalDateTime,
        pranaDurationSeconds: Long
    ): List<Dehadasha> {
        val dehadashas = mutableListOf<Dehadasha>()
        var currentStart = pranaStart

        val startIndex = DASHA_SEQUENCE.indexOf(pranadashaPlanet)

        for (i in 0 until 9) {
            val planetIndex = (startIndex + i) % DASHA_SEQUENCE.size
            val dehaPlanet = DASHA_SEQUENCE[planetIndex]

            val dehaYearsBd = DASHA_YEARS[dehaPlanet] ?: BigDecimal.ZERO
            val proportionalSeconds = dehaYearsBd
                .divide(TOTAL_CYCLE_YEARS_BD, MATH_CONTEXT)
                .multiply(BigDecimal(pranaDurationSeconds), MATH_CONTEXT)
                .toLong()
                .coerceAtLeast(1L)

            val dehaEnd = currentStart.plusSeconds(proportionalSeconds)

            dehadashas.add(
                Dehadasha(
                    planet = dehaPlanet,
                    pranadashaPlanet = pranadashaPlanet,
                    sookshmadashaPlanet = sookshmadashaPlanet,
                    pratyantardashaPlanet = pratyantardashaPlanet,
                    antardashaPlanet = antardashaPlanet,
                    mahadashaPlanet = mahadashaPlanet,
                    startDate = currentStart,
                    endDate = dehaEnd,
                    durationSeconds = proportionalSeconds
                )
            )
            currentStart = dehaEnd
        }

        return dehadashas
    }

    /**
     * Calculate upcoming Dasha Sandhis (transition periods) for the given timeline.
     *
     * Sandhi (junction/transition) is the period around dasha changes when the effects
     * of both the ending and beginning dashas are felt. This is a critical period in
     * Vedic astrology as it often brings significant life changes.
     */
    private fun calculateUpcomingSandhis(
        mahadashas: List<Mahadasha>,
        fromDateTime: LocalDateTime,
        lookAheadDays: Int
    ): List<DashaSandhi> {
        val sandhis = mutableListOf<DashaSandhi>()
        val endDateTime = fromDateTime.plusDays(lookAheadDays.toLong())

        for (i in 0 until mahadashas.size - 1) {
            val currentMd = mahadashas[i]
            val nextMd = mahadashas[i + 1]

            // Mahadasha Sandhi
            if (currentMd.endDate.isAfter(fromDateTime) && currentMd.endDate.isBefore(endDateTime)) {
                val sandhiSeconds = calculateSandhiDurationSeconds(DashaLevel.MAHADASHA, currentMd.durationYears)
                sandhis.add(
                    DashaSandhi(
                        fromPlanet = currentMd.planet,
                        toPlanet = nextMd.planet,
                        transitionDate = currentMd.endDate,
                        level = DashaLevel.MAHADASHA,
                        sandhiStartDate = currentMd.endDate.minusSeconds(sandhiSeconds / 2),
                        sandhiEndDate = currentMd.endDate.plusSeconds(sandhiSeconds / 2)
                    )
                )
            }

            // Antardasha and Pratyantardasha Sandhis
            if (currentMd.isActiveOn(fromDateTime) ||
                (currentMd.startDate.isAfter(fromDateTime) && currentMd.startDate.isBefore(endDateTime))) {

                for (j in 0 until currentMd.antardashas.size - 1) {
                    val currentAd = currentMd.antardashas[j]
                    val nextAd = currentMd.antardashas[j + 1]

                    // Antardasha Sandhi
                    if (currentAd.endDate.isAfter(fromDateTime) && currentAd.endDate.isBefore(endDateTime)) {
                        val sandhiSeconds = calculateSandhiDurationSeconds(DashaLevel.ANTARDASHA, currentAd.durationYears)
                        sandhis.add(
                            DashaSandhi(
                                fromPlanet = currentAd.planet,
                                toPlanet = nextAd.planet,
                                transitionDate = currentAd.endDate,
                                level = DashaLevel.ANTARDASHA,
                                sandhiStartDate = currentAd.endDate.minusSeconds(sandhiSeconds / 2),
                                sandhiEndDate = currentAd.endDate.plusSeconds(sandhiSeconds / 2)
                            )
                        )
                    }
                }
            }
        }

        return sandhis.sortedBy { it.transitionDate }
    }

    private fun calculateSandhiDurationSeconds(level: DashaLevel, periodDurationYears: Double): Long {
        val sandhiPercentage = when (level) {
            DashaLevel.MAHADASHA -> 0.05
            DashaLevel.ANTARDASHA -> 0.10
            DashaLevel.PRATYANTARDASHA -> 0.15
            DashaLevel.SOOKSHMADASHA -> 0.20
            DashaLevel.PRANADASHA -> 0.20
            DashaLevel.DEHADASHA -> 0.20
        }

        val sandhiYearsBd = BigDecimal(periodDurationYears.toString()).multiply(BigDecimal(sandhiPercentage.toString()), MATH_CONTEXT)
        return yearsToSeconds(sandhiYearsBd).coerceIn(3600L, 30L * 24 * 3600) // Min 1 hour, Max 30 days
    }

    fun getDashaAtDate(timeline: DashaTimeline, date: LocalDateTime): DashaPeriodInfo {
        return timeline.getDashaAtDate(date)
    }

    fun formatDashaPeriod(mahadasha: Mahadasha): String {
        return buildString {
            appendLine("${mahadasha.planet.displayName} Mahadasha")
            appendLine("Duration: ${String.format("%.2f", mahadasha.durationYears)} years")
            appendLine("Period: ${mahadasha.startDate} to ${mahadasha.endDate}")
            if (mahadasha.isActive) {
                appendLine("Status: CURRENTLY ACTIVE")
                appendLine("Progress: ${String.format("%.1f", mahadasha.getProgressPercent())}%")
                appendLine("Remaining: ${String.format("%.2f", mahadasha.getRemainingYears())} years")
            }
        }
    }

    fun formatLocalizedDashaPeriod(mahadasha: Mahadasha, language: Language): String {
        val mahadashaLabel = DashaLevel.MAHADASHA.getLocalizedName(language)
        val durationLabel = StringResources.get(StringKey.DASHA_DURATION, language)
        val periodLabel = StringResources.get(StringKey.DASHA_PERIOD, language)
        val yearsLabel = StringResources.get(StringKey.YEARS, language)
        val toLabel = StringResources.get(StringKey.TO, language)
        val statusLabel = StringResources.get(StringKey.DASHA_STATUS, language)
        val activeLabel = StringResources.get(StringKey.DASHA_CURRENTLY_ACTIVE, language)
        val progressLabel = StringResources.get(StringKey.DASHA_PROGRESS, language)
        val remainingLabel = StringResources.get(StringKey.DASHA_REMAINING, language)

        return buildString {
            appendLine("${mahadasha.planet.getLocalizedName(language)} $mahadashaLabel")
            appendLine("$durationLabel: ${String.format("%.2f", mahadasha.durationYears)} $yearsLabel")
            appendLine("$periodLabel: ${mahadasha.startDate} $toLabel ${mahadasha.endDate}")
            if (mahadasha.isActive) {
                appendLine("$statusLabel: $activeLabel")
                appendLine("$progressLabel: ${String.format("%.1f", mahadasha.getProgressPercent())}%")
                appendLine("$remainingLabel: ${String.format("%.2f", mahadasha.getRemainingYears())} $yearsLabel")
            }
        }
    }

    fun formatCurrentPeriod(timeline: DashaTimeline): String {
        return buildString {
            timeline.currentMahadasha?.let { md ->
                appendLine("Mahadasha: ${md.planet.displayName}")
                appendLine("  Progress: ${String.format("%.1f", md.getProgressPercent())}%")
                appendLine("  Remaining: ${String.format("%.1f", md.getRemainingYears())} years")

                timeline.currentAntardasha?.let { ad ->
                    appendLine("\nAntardasha: ${ad.planet.displayName}")
                    appendLine("  Progress: ${String.format("%.1f", ad.getProgressPercent())}%")
                    appendLine("  Remaining: ${ad.getRemainingSeconds() / (24 * 3600)} days")

                    timeline.currentPratyantardasha?.let { pd ->
                        appendLine("\nPratyantardasha: ${pd.planet.displayName}")
                        appendLine("  Period: ${pd.startDate} to ${pd.endDate}")

                        timeline.currentSookshmadasha?.let { sd ->
                            appendLine("\nSookshmadasha: ${sd.planet.displayName}")
                            appendLine("  Period: ${sd.startDate} to ${sd.endDate}")
                            appendLine("  Duration: ${sd.durationSeconds / (24 * 3600)} days")

                            timeline.currentPranadasha?.let { prd ->
                                appendLine("\nPranadasha: ${prd.planet.displayName}")
                                appendLine("  Period: ${prd.startDate} to ${prd.endDate}")
                                appendLine("  Duration: ${prd.getDurationString()}")

                                timeline.currentDehadasha?.let { dd ->
                                    appendLine("\nDehadasha: ${dd.planet.displayName}")
                                    appendLine("  Period: ${dd.startDate} to ${dd.endDate}")
                                    appendLine("  Duration: ${dd.getDurationString()}")
                                }
                            }
                        }
                    }
                }
            } ?: appendLine("No active Dasha period found")
        }
    }

    fun formatLocalizedCurrentPeriod(timeline: DashaTimeline, language: Language): String {
        val progressLabel = StringResources.get(StringKey.DASHA_PROGRESS, language)
        val remainingLabel = StringResources.get(StringKey.DASHA_REMAINING, language)
        val periodLabel = StringResources.get(StringKey.DASHA_PERIOD, language)
        val durationLabel = StringResources.get(StringKey.DASHA_DURATION, language)
        val yearsLabel = StringResources.get(StringKey.YEARS, language)
        val daysLabel = StringResources.get(StringKey.DAYS, language)
        val toLabel = StringResources.get(StringKey.TO, language)

        return buildString {
            timeline.currentMahadasha?.let { md ->
                appendLine("${DashaLevel.MAHADASHA.getLocalizedName(language)}: ${md.planet.getLocalizedName(language)}")
                appendLine("  $progressLabel: ${String.format("%.1f", md.getProgressPercent())}%")
                appendLine("  $remainingLabel: ${String.format("%.1f", md.getRemainingYears())} $yearsLabel")

                timeline.currentAntardasha?.let { ad ->
                    appendLine("\n${DashaLevel.ANTARDASHA.getLocalizedName(language)}: ${ad.planet.getLocalizedName(language)}")
                    appendLine("  $progressLabel: ${String.format("%.1f", ad.getProgressPercent())}%")
                    appendLine("  $remainingLabel: ${ad.getRemainingSeconds() / (24 * 3600)} $daysLabel")

                    timeline.currentPratyantardasha?.let { pd ->
                        appendLine("\n${DashaLevel.PRATYANTARDASHA.getLocalizedName(language)}: ${pd.planet.getLocalizedName(language)}")
                        appendLine("  $periodLabel: ${pd.startDate} $toLabel ${pd.endDate}")

                        timeline.currentSookshmadasha?.let { sd ->
                            appendLine("\n${DashaLevel.SOOKSHMADASHA.getLocalizedName(language)}: ${sd.planet.getLocalizedName(language)}")
                            appendLine("  $periodLabel: ${sd.startDate} $toLabel ${sd.endDate}")
                            appendLine("  $durationLabel: ${sd.durationSeconds / (24 * 3600)} $daysLabel")

                            timeline.currentPranadasha?.let { prd ->
                                appendLine("\n${DashaLevel.PRANADASHA.getLocalizedName(language)}: ${prd.planet.getLocalizedName(language)}")
                                appendLine("  $periodLabel: ${prd.startDate} $toLabel ${prd.endDate}")
                                appendLine("  $durationLabel: ${prd.getLocalizedDurationString(language)}")

                                timeline.currentDehadasha?.let { dd ->
                                    appendLine("\n${DashaLevel.DEHADASHA.getLocalizedName(language)}: ${dd.planet.getLocalizedName(language)}")
                                    appendLine("  $periodLabel: ${dd.startDate} $toLabel ${dd.endDate}")
                                    appendLine("  $durationLabel: ${dd.getLocalizedDurationString(language)}")
                                }
                            }
                        }
                    }
                }
            } ?: appendLine(StringResources.get(StringKey.DASHA_NO_ACTIVE_PERIOD, language))
        }
    }
}

object ConditionalDashaCalculator {

    data class YoginiDasha(
        val yogini: Yogini,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val durationYears: Double
    ) {
        fun isActiveOn(date: LocalDate): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDate.now())
    }

    enum class Yogini(
        val displayName: String,
        val deity: String,
        val planet: Planet,
        val years: Int,
        val nature: YoginiNature
    ) {
        MANGALA("Mangala", "Chandra (Moon)", Planet.MOON, 1, YoginiNature.BENEFIC),
        PINGALA("Pingala", "Surya (Sun)", Planet.SUN, 2, YoginiNature.MIXED),
        DHANYA("Dhanya", "Guru (Jupiter)", Planet.JUPITER, 3, YoginiNature.BENEFIC),
        BHRAMARI("Bhramari", "Mangal (Mars)", Planet.MARS, 4, YoginiNature.MALEFIC),
        BHADRIKA("Bhadrika", "Budha (Mercury)", Planet.MERCURY, 5, YoginiNature.BENEFIC),
        ULKA("Ulka", "Shani (Saturn)", Planet.SATURN, 6, YoginiNature.MALEFIC),
        SIDDHA("Siddha", "Shukra (Venus)", Planet.VENUS, 7, YoginiNature.BENEFIC),
        SANKATA("Sankata", "Rahu", Planet.RAHU, 8, YoginiNature.MALEFIC);

        fun getLocalizedName(language: Language): String = when (this) {
            MANGALA -> StringResources.get(StringKey.YOGINI_MANGALA, language)
            PINGALA -> StringResources.get(StringKey.YOGINI_PINGALA, language)
            DHANYA -> StringResources.get(StringKey.YOGINI_DHANYA, language)
            BHRAMARI -> StringResources.get(StringKey.YOGINI_BHRAMARI, language)
            BHADRIKA -> StringResources.get(StringKey.YOGINI_BHADRIKA, language)
            ULKA -> StringResources.get(StringKey.YOGINI_ULKA, language)
            SIDDHA -> StringResources.get(StringKey.YOGINI_SIDDHA, language)
            SANKATA -> StringResources.get(StringKey.YOGINI_SANKATA, language)
        }

        fun getLocalizedDeity(language: Language): String = when (this) {
            MANGALA -> StringResources.get(StringKey.YOGINI_DEITY_CHANDRA, language)
            PINGALA -> StringResources.get(StringKey.YOGINI_DEITY_SURYA, language)
            DHANYA -> StringResources.get(StringKey.YOGINI_DEITY_GURU, language)
            BHRAMARI -> StringResources.get(StringKey.YOGINI_DEITY_MANGAL, language)
            BHADRIKA -> StringResources.get(StringKey.YOGINI_DEITY_BUDHA, language)
            ULKA -> StringResources.get(StringKey.YOGINI_DEITY_SHANI, language)
            SIDDHA -> StringResources.get(StringKey.YOGINI_DEITY_SHUKRA, language)
            SANKATA -> StringResources.get(StringKey.YOGINI_DEITY_RAHU, language)
        }
    }

    enum class YoginiNature(val displayName: String) {
        BENEFIC("Benefic"),
        MALEFIC("Malefic"),
        MIXED("Mixed");

        fun getLocalizedName(language: Language): String = when (this) {
            BENEFIC -> StringResources.get(StringKey.NATURE_BENEFIC, language)
            MALEFIC -> StringResources.get(StringKey.NATURE_MALEFIC, language)
            MIXED -> StringResources.get(StringKey.NATURE_MIXED, language)
        }
    }

    private const val YOGINI_CYCLE_YEARS = 36

    fun calculateYoginiDasha(chart: VedicChart): List<YoginiDasha> {
        val birthDate = chart.birthData.dateTime.toLocalDate()
        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
            ?: throw IllegalArgumentException("Moon position not found")

        val moonLongitude = moonPosition.longitude
        val (nakshatra, _) = Nakshatra.fromLongitude(moonLongitude)

        val yoginiIndex = ((nakshatra.number - 1 + 3) % Yogini.entries.size)
        val startingYogini = Yogini.entries[yoginiIndex]

        // Use BigDecimal for precise nakshatra progress calculation
        val moonLongitudeBd = BigDecimal(moonLongitude.toString())
        val nakshatraStartBd = BigDecimal(nakshatra.startDegree.toString())
        val progressInNakshatraBd = moonLongitudeBd.subtract(nakshatraStartBd, MATH_CONTEXT)
            .divide(NAKSHATRA_SPAN_BD, MATH_CONTEXT)
            .coerceIn(BigDecimal.ZERO, BigDecimal.ONE)

        val yoginis = mutableListOf<YoginiDasha>()
        var currentStart = birthDate

        // Use BigDecimal for balance calculation
        val firstYoginiYearsBd = BigDecimal(startingYogini.years)
        val balanceOfFirstBd = firstYoginiYearsBd.multiply(
            BigDecimal.ONE.subtract(progressInNakshatraBd, MATH_CONTEXT), MATH_CONTEXT
        )
        val firstDays = yearsToRoundedDays(balanceOfFirstBd)
        val firstEnd = currentStart.plusDays(firstDays)

        yoginis.add(
            YoginiDasha(
                yogini = startingYogini,
                startDate = currentStart,
                endDate = firstEnd,
                durationYears = balanceOfFirstBd.toDouble()
            )
        )
        currentStart = firstEnd

        repeat(80) { cycle ->
            val yoginiIdx = (yoginiIndex + 1 + cycle) % Yogini.entries.size
            val yogini = Yogini.entries[yoginiIdx]
            val yearsBd = BigDecimal(yogini.years)
            val days = yearsToRoundedDays(yearsBd)
            val endDate = currentStart.plusDays(days)

            yoginis.add(
                YoginiDasha(
                    yogini = yogini,
                    startDate = currentStart,
                    endDate = endDate,
                    durationYears = yearsBd.toDouble()
                )
            )
            currentStart = endDate
        }

        return yoginis
    }

    fun getCurrentYoginiDasha(yoginiDashas: List<YoginiDasha>): YoginiDasha? {
        return yoginiDashas.find { it.isActive }
    }

    data class AshtottariDasha(
        val planet: Planet,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val durationYears: Double
    ) {
        fun isActiveOn(date: LocalDate): Boolean {
            return !date.isBefore(startDate) && !date.isAfter(endDate)
        }

        val isActive: Boolean
            get() = isActiveOn(LocalDate.now())
    }

    private val ASHTOTTARI_YEARS: Map<Planet, Int> = mapOf(
        Planet.SUN to 6,
        Planet.MOON to 15,
        Planet.MARS to 8,
        Planet.MERCURY to 17,
        Planet.SATURN to 10,
        Planet.JUPITER to 19,
        Planet.RAHU to 12,
        Planet.VENUS to 21
    )

    private val ASHTOTTARI_SEQUENCE = listOf(
        Planet.SUN,
        Planet.MOON,
        Planet.MARS,
        Planet.MERCURY,
        Planet.SATURN,
        Planet.JUPITER,
        Planet.RAHU,
        Planet.VENUS
    )

    private const val ASHTOTTARI_CYCLE_YEARS = 108

    private val ASHTOTTARI_NAKSHATRA_LORDS: Map<Int, Planet> = mapOf(
        1 to Planet.SUN,
        2 to Planet.MOON,
        3 to Planet.MARS,
        4 to Planet.MERCURY,
        5 to Planet.SATURN,
        6 to Planet.JUPITER,
        7 to Planet.RAHU,
        8 to Planet.VENUS
    )

    fun shouldApplyAshtottari(chart: VedicChart): Boolean {
        if (chart.planetPositions.isEmpty()) return false

        val ascendantSign = ZodiacSign.fromLongitude(chart.ascendant)
        val lagnaLord = ascendantSign.ruler
        val lagnaLordPosition = chart.planetPositions.find { it.planet == lagnaLord }
        val rahuPosition = chart.planetPositions.find { it.planet == Planet.RAHU }

        if (lagnaLordPosition == null || rahuPosition == null) return false

        val lagnaLordSign = ZodiacSign.fromLongitude(lagnaLordPosition.longitude)
        val rahuSign = ZodiacSign.fromLongitude(rahuPosition.longitude)

        val houseDistance = ((rahuSign.ordinal - lagnaLordSign.ordinal + 12) % 12) + 1

        return houseDistance in listOf(1, 4, 5, 7, 9, 10)
    }

    fun calculateAshtottariDasha(chart: VedicChart): List<AshtottariDasha>? {
        if (!shouldApplyAshtottari(chart)) return null

        val birthDate = chart.birthData.dateTime.toLocalDate()
        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }
            ?: return null

        val moonLongitude = moonPosition.longitude
        val (nakshatra, _) = Nakshatra.fromLongitude(moonLongitude)

        val nakshatraGroup = ((nakshatra.number - 6 + 27) % 27) / 3
        val startingLord = ASHTOTTARI_NAKSHATRA_LORDS[nakshatraGroup % 8 + 1] ?: Planet.SUN

        // Use BigDecimal for precise nakshatra progress calculation
        val moonLongitudeBd = BigDecimal(moonLongitude.toString())
        val nakshatraStartBd = BigDecimal(nakshatra.startDegree.toString())
        val progressInNakshatraBd = moonLongitudeBd.subtract(nakshatraStartBd, MATH_CONTEXT)
            .divide(NAKSHATRA_SPAN_BD, MATH_CONTEXT)
            .coerceIn(BigDecimal.ZERO, BigDecimal.ONE)

        val dashas = mutableListOf<AshtottariDasha>()
        var currentStart = birthDate

        val startIndex = ASHTOTTARI_SEQUENCE.indexOf(startingLord)
        val firstDashaYearsBd = BigDecimal(ASHTOTTARI_YEARS[startingLord] ?: 0)
        val balanceOfFirstBd = firstDashaYearsBd.multiply(
            BigDecimal.ONE.subtract(progressInNakshatraBd, MATH_CONTEXT), MATH_CONTEXT
        )

        val firstDays = yearsToRoundedDays(balanceOfFirstBd)
        val firstEnd = currentStart.plusDays(firstDays)

        dashas.add(
            AshtottariDasha(
                planet = startingLord,
                startDate = currentStart,
                endDate = firstEnd,
                durationYears = balanceOfFirstBd.toDouble()
            )
        )
        currentStart = firstEnd

        repeat(24) { cycle ->
            val planetIndex = (startIndex + 1 + cycle) % ASHTOTTARI_SEQUENCE.size
            val planet = ASHTOTTARI_SEQUENCE[planetIndex]
            val yearsBd = BigDecimal(ASHTOTTARI_YEARS[planet] ?: 0)
            val days = yearsToRoundedDays(yearsBd)
            val endDate = currentStart.plusDays(days)

            dashas.add(
                AshtottariDasha(
                    planet = planet,
                    startDate = currentStart,
                    endDate = endDate,
                    durationYears = yearsBd.toDouble()
                )
            )
            currentStart = endDate
        }

        return dashas
    }
}