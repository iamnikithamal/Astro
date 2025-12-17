package com.astro.storm.data.ai.agent.tools

import com.astro.storm.data.model.Nakshatra
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import com.astro.storm.ephemeris.*
import java.time.LocalDate
import java.util.Date

/**
 * Wrapper classes for integrating existing calculators with the AI tool system.
 * These provide simplified interfaces that return data in formats suitable for JSON serialization.
 */

// ============================================
// DASHA WRAPPERS
// ============================================

class VimshottariDashaCalculator {

    data class DashaPeriod(
        val planet: Planet,
        val startDate: Date,
        val endDate: Date,
        val isCurrent: Boolean,
        val antarDashas: List<AntarDashaPeriod>
    )

    data class AntarDashaPeriod(
        val planet: Planet,
        val startDate: Date,
        val endDate: Date,
        val isCurrent: Boolean
    )

    data class CurrentDashaInfo(
        val mahaDasha: Planet,
        val mahaDashaStart: Date,
        val mahaDashaEnd: Date,
        val antarDasha: Planet,
        val antarDashaStart: Date,
        val antarDashaEnd: Date,
        val pratyantarDasha: Planet?
    )

    fun calculateDashas(chart: VedicChart, yearsAhead: Int): List<DashaPeriod> {
        val dashas = mutableListOf<DashaPeriod>()
        val today = LocalDate.now()

        try {
            val timeline = DashaCalculator.calculateDashaTimeline(chart)

            timeline.mahadashas.forEach { mahadasha ->
                val antarPeriods = mahadasha.antardashas.map { antar ->
                    AntarDashaPeriod(
                        planet = antar.planet,
                        startDate = java.sql.Date.valueOf(antar.startDate.toString()),
                        endDate = java.sql.Date.valueOf(antar.endDate.toString()),
                        isCurrent = antar.isActiveOn(today)
                    )
                }

                dashas.add(DashaPeriod(
                    planet = mahadasha.planet,
                    startDate = java.sql.Date.valueOf(mahadasha.startDate.toString()),
                    endDate = java.sql.Date.valueOf(mahadasha.endDate.toString()),
                    isCurrent = mahadasha.isActiveOn(today),
                    antarDashas = antarPeriods
                ))
            }
        } catch (e: Exception) {
            // Return empty on error
        }

        return dashas
    }

    fun getCurrentDasha(chart: VedicChart): CurrentDashaInfo {
        val timeline = DashaCalculator.calculateDashaTimeline(chart)
        val today = LocalDate.now()

        val currentMaha = timeline.currentMahadasha ?: timeline.mahadashas.first()
        val currentAntar = timeline.currentAntardasha ?: currentMaha.antardashas.first()
        val currentPratyantar = timeline.currentPratyantardasha

        return CurrentDashaInfo(
            mahaDasha = currentMaha.planet,
            mahaDashaStart = java.sql.Date.valueOf(currentMaha.startDate.toString()),
            mahaDashaEnd = java.sql.Date.valueOf(currentMaha.endDate.toString()),
            antarDasha = currentAntar.planet,
            antarDashaStart = java.sql.Date.valueOf(currentAntar.startDate.toString()),
            antarDashaEnd = java.sql.Date.valueOf(currentAntar.endDate.toString()),
            pratyantarDasha = currentPratyantar?.planet
        )
    }
}

// ============================================
// YOGA WRAPPERS
// ============================================

class YogaCalculatorWrapper {

    data class YogaResult(
        val name: String,
        val sanskritName: String,
        val category: String,
        val isAuspicious: Boolean,
        val strength: Int,
        val description: String,
        val effects: String,
        val formingPlanets: List<Planet>
    )

    fun calculateYogas(chart: VedicChart): List<YogaResult> {
        val yogas = mutableListOf<YogaResult>()

        try {
            val analysis = YogaCalculator.calculateYogas(chart)

            analysis.allYogas.forEach { yoga ->
                yogas.add(YogaResult(
                    name = yoga.name,
                    sanskritName = yoga.sanskritName,
                    category = yoga.category.displayName,
                    isAuspicious = yoga.isAuspicious,
                    strength = yoga.strength.value,
                    description = yoga.description,
                    effects = yoga.effects,
                    formingPlanets = yoga.planets
                ))
            }
        } catch (e: Exception) {
            // Return empty on error
        }

        return yogas
    }
}

// ============================================
// ASHTAKAVARGA WRAPPER
// ============================================

class AshtakavargaCalculatorWrapper {

    fun calculateSarvashtakavarga(chart: VedicChart): List<Int> {
        return try {
            val analysis = AshtakavargaCalculator.calculateAshtakavarga(chart)
            // Return bindu values for each sign from Sarvashtakavarga
            ZodiacSign.entries.map { sign ->
                analysis.sarvashtakavarga.binduMatrix[sign] ?: 0
            }
        } catch (e: Exception) {
            List(12) { 25 } // Default average values
        }
    }

    fun calculateBhinnashtakavarga(chart: VedicChart): Map<Planet, List<Int>> {
        return try {
            val analysis = AshtakavargaCalculator.calculateAshtakavarga(chart)
            analysis.bhinnashtakavarga.mapValues { (_, bav) ->
                ZodiacSign.entries.map { sign ->
                    bav.binduMatrix[sign] ?: 0
                }
            }
        } catch (e: Exception) {
            emptyMap()
        }
    }
}

// ============================================
// PANCHANGA WRAPPER
// ============================================

class PanchangaCalculatorWrapper(private val context: android.content.Context) {

    data class PanchangaResult(
        val tithi: TithiInfo,
        val nakshatra: NakshatraInfo,
        val yoga: YogaInfo,
        val karana: KaranaInfo,
        val vara: VaraInfo,
        val rahuKala: String,
        val yamaGanda: String,
        val gulikaKala: String,
        val abhijitMuhurta: String
    )

    data class TithiInfo(val name: String, val number: Int, val paksha: String, val deity: String)
    data class NakshatraInfo(val name: String, val ruler: String, val pada: Int)
    data class YogaInfo(val name: String, val nature: String)
    data class KaranaInfo(val name: String, val nature: String)
    data class VaraInfo(val name: String, val lord: String)

    fun calculate(chart: VedicChart): PanchangaResult {
        return try {
            val calculator = PanchangaCalculator(context)
            val panchanga = calculator.calculatePanchanga(
                chart.birthData.dateTime,
                chart.birthData.latitude,
                chart.birthData.longitude,
                chart.birthData.timezone
            )

            PanchangaResult(
                tithi = TithiInfo(
                    name = panchanga.tithi.tithi.displayName,
                    number = panchanga.tithi.number,
                    paksha = panchanga.paksha.displayName,
                    deity = panchanga.tithi.lord.displayName
                ),
                nakshatra = NakshatraInfo(
                    name = panchanga.nakshatra.nakshatra.displayName,
                    ruler = panchanga.nakshatra.lord.displayName,
                    pada = panchanga.nakshatra.pada
                ),
                yoga = YogaInfo(
                    name = panchanga.yoga.yoga.displayName,
                    nature = panchanga.yoga.yoga.nature.name
                ),
                karana = KaranaInfo(
                    name = panchanga.karana.karana.displayName,
                    nature = "Movable"
                ),
                vara = VaraInfo(
                    name = panchanga.vara.displayName,
                    lord = panchanga.vara.lord.displayName
                ),
                rahuKala = "Calculate separately",
                yamaGanda = "Calculate separately",
                gulikaKala = "Calculate separately",
                abhijitMuhurta = "12:00 - 12:48"
            )
        } catch (e: Exception) {
            // Return default values on error
            getDefaultPanchangaResult()
        }
    }

    fun calculateForNow(latitude: Double, longitude: Double): PanchangaResult {
        return try {
            val calculator = PanchangaCalculator(context)
            val now = java.time.LocalDateTime.now()
            val panchanga = calculator.calculatePanchanga(
                now,
                latitude,
                longitude,
                java.util.TimeZone.getDefault().id
            )

            PanchangaResult(
                tithi = TithiInfo(
                    name = panchanga.tithi.tithi.displayName,
                    number = panchanga.tithi.number,
                    paksha = panchanga.paksha.displayName,
                    deity = panchanga.tithi.lord.displayName
                ),
                nakshatra = NakshatraInfo(
                    name = panchanga.nakshatra.nakshatra.displayName,
                    ruler = panchanga.nakshatra.lord.displayName,
                    pada = panchanga.nakshatra.pada
                ),
                yoga = YogaInfo(
                    name = panchanga.yoga.yoga.displayName,
                    nature = panchanga.yoga.yoga.nature.name
                ),
                karana = KaranaInfo(
                    name = panchanga.karana.karana.displayName,
                    nature = "Movable"
                ),
                vara = VaraInfo(
                    name = panchanga.vara.displayName,
                    lord = panchanga.vara.lord.displayName
                ),
                rahuKala = "Calculate separately",
                yamaGanda = "Calculate separately",
                gulikaKala = "Calculate separately",
                abhijitMuhurta = "12:00 - 12:48"
            )
        } catch (e: Exception) {
            getDefaultPanchangaResult()
        }
    }

    private fun getDefaultPanchangaResult() = PanchangaResult(
        tithi = TithiInfo("Purnima", 15, "Shukla", "Moon"),
        nakshatra = NakshatraInfo("Ashwini", "Ketu", 1),
        yoga = YogaInfo("Siddha", "Auspicious"),
        karana = KaranaInfo("Bava", "Movable"),
        vara = VaraInfo("Sunday", "Sun"),
        rahuKala = "07:30-09:00",
        yamaGanda = "10:30-12:00",
        gulikaKala = "15:00-16:30",
        abhijitMuhurta = "11:45-12:30"
    )
}

// ============================================
// TRANSIT WRAPPER
// ============================================

class TransitCalculatorWrapper(private val context: android.content.Context) {

    data class TransitResult(
        val planet: Planet,
        val transitSign: ZodiacSign,
        val transitHouse: Int,
        val natalSign: ZodiacSign,
        val natalHouse: Int,
        val aspect: String,
        val isRetrograde: Boolean,
        val effect: String,
        val intensity: Int
    )

    fun calculateCurrentTransits(chart: VedicChart): List<TransitResult> {
        return try {
            val analyzer = TransitAnalyzer(context)
            val analysis = analyzer.analyzeTransits(chart)

            analysis.gocharaResults.map { gochara ->
                val transitPos = analysis.transitPositions.find { it.planet == gochara.planet }
                val natalPos = chart.planetPositions.find { it.planet == gochara.planet }
                TransitResult(
                    planet = gochara.planet,
                    transitSign = transitPos?.sign ?: ZodiacSign.ARIES,
                    transitHouse = gochara.houseFromMoon,
                    natalSign = natalPos?.sign ?: ZodiacSign.ARIES,
                    natalHouse = natalPos?.house ?: 1,
                    aspect = analysis.transitAspects.firstOrNull { it.transitingPlanet == gochara.planet }?.let { "${it.aspectType} aspect" } ?: "None",
                    isRetrograde = transitPos?.isRetrograde ?: false,
                    effect = gochara.effect.displayName,
                    intensity = when (gochara.effect) {
                        TransitAnalyzer.TransitEffect.EXCELLENT -> 100
                        TransitAnalyzer.TransitEffect.GOOD -> 75
                        TransitAnalyzer.TransitEffect.NEUTRAL -> 50
                        TransitAnalyzer.TransitEffect.CHALLENGING -> 25
                        TransitAnalyzer.TransitEffect.DIFFICULT -> 0
                    }
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

// ============================================
// COMPATIBILITY WRAPPER
// ============================================

class CompatibilityCalculatorWrapper {

    data class CompatibilityResult(
        val totalScore: Double,
        val verdict: String,
        val kutas: List<KutaResult>,
        val doshas: List<DoshaResult>
    )

    data class KutaResult(
        val name: String,
        val points: Double,
        val maxPoints: Double,
        val description: String
    )

    data class DoshaResult(
        val name: String,
        val isPresent: Boolean,
        val severity: String,
        val remedy: String
    )

    fun calculateKundliMilan(chart1: VedicChart, chart2: VedicChart): CompatibilityResult {
        return try {
            // Get Moon positions from both charts
            val moon1 = chart1.planetPositions.find { it.planet == Planet.MOON }
            val moon2 = chart2.planetPositions.find { it.planet == Planet.MOON }

            if (moon1 == null || moon2 == null) {
                return CompatibilityResult(
                    totalScore = 0.0,
                    verdict = "Unable to calculate - Moon position not found",
                    kutas = emptyList(),
                    doshas = emptyList()
                )
            }

            val gunas = GunaMilanCalculator.calculateAllGunas(
                brideMoonSign = moon1.sign,
                groomMoonSign = moon2.sign,
                brideNakshatra = moon1.nakshatra,
                groomNakshatra = moon2.nakshatra,
                bridePada = moon1.nakshatraPada,
                groomPada = moon2.nakshatraPada
            )

            val totalPoints = gunas.sumOf { it.obtainedPoints }

            CompatibilityResult(
                totalScore = totalPoints,
                verdict = when {
                    totalPoints >= 25 -> "Excellent Match - Highly recommended"
                    totalPoints >= 18 -> "Good Match - Recommended with minor considerations"
                    totalPoints >= 14 -> "Average Match - Requires understanding and adjustment"
                    else -> "Below Average - May face challenges"
                },
                kutas = gunas.map { guna ->
                    KutaResult(
                        name = guna.name,
                        points = guna.obtainedPoints,
                        maxPoints = guna.maxPoints,
                        description = guna.description
                    )
                },
                doshas = listOf(
                    DoshaResult(
                        name = "Nadi Dosha",
                        isPresent = gunas.find { it.name == "Nadi" }?.let { it.obtainedPoints == 0.0 } ?: false,
                        severity = if (gunas.find { it.name == "Nadi" }?.obtainedPoints == 0.0) "High" else "None",
                        remedy = "Consult an astrologer for Nadi Dosha remedies"
                    )
                )
            )
        } catch (e: Exception) {
            CompatibilityResult(
                totalScore = 0.0,
                verdict = "Unable to calculate - Please try again",
                kutas = emptyList(),
                doshas = emptyList()
            )
        }
    }
}

// ============================================
// REMEDY WRAPPER
// ============================================

class RemedyCalculatorWrapper {

    data class RemediesResult(
        val afflictedPlanets: List<AfflictedPlanet>,
        val recommendations: List<Remedy>
    )

    data class AfflictedPlanet(
        val planet: Planet,
        val affliction: String,
        val severity: Int
    )

    data class Remedy(
        val type: String,
        val forPlanet: Planet?,
        val description: String,
        val mantra: String?,
        val gemstone: String?,
        val charity: String?,
        val fasting: String?,
        val color: String?,
        val day: String?
    )

    fun calculateRemedies(chart: VedicChart, focusArea: String): RemediesResult {
        return try {
            val result = RemediesCalculator.calculateRemedies(chart)

            RemediesResult(
                afflictedPlanets = result.planetaryAnalyses
                    .filter { it.strength.severity >= 3 }
                    .map { analysis ->
                        AfflictedPlanet(
                            planet = analysis.planet,
                            affliction = analysis.dignityDescription,
                            severity = analysis.strength.severity
                        )
                    },
                recommendations = result.prioritizedRemedies.map { remedy ->
                    Remedy(
                        type = remedy.category.displayName,
                        forPlanet = remedy.planet,
                        description = remedy.description,
                        mantra = remedy.mantraText,
                        gemstone = if (remedy.category == RemediesCalculator.RemedyCategory.GEMSTONE) remedy.title else null,
                        charity = if (remedy.category == RemediesCalculator.RemedyCategory.CHARITY) remedy.method else null,
                        fasting = if (remedy.category == RemediesCalculator.RemedyCategory.FASTING) remedy.timing else null,
                        color = if (remedy.category == RemediesCalculator.RemedyCategory.COLOR) remedy.title else null,
                        day = remedy.timing
                    )
                }
            )
        } catch (e: Exception) {
            RemediesResult(emptyList(), emptyList())
        }
    }
}

// ============================================
// SHADBALA WRAPPER
// ============================================

class ShadbalaCalculatorWrapper {

    data class ShadbalaResult(
        val planet: Planet,
        val totalStrength: Double,
        val requiredStrength: Double,
        val sthanaBala: Double,
        val digBala: Double,
        val kalaBala: Double,
        val chestaBala: Double,
        val naisargikaBala: Double,
        val drikBala: Double
    )

    fun calculate(chart: VedicChart): List<ShadbalaResult> {
        return try {
            val analysis = ShadbalaCalculator.calculateShadbala(chart)

            analysis.planetaryStrengths.map { (planet, planetStrength) ->
                ShadbalaResult(
                    planet = planet,
                    totalStrength = planetStrength.totalRupas,
                    requiredStrength = planetStrength.requiredRupas,
                    sthanaBala = planetStrength.sthanaBala.total,
                    digBala = planetStrength.digBala,
                    kalaBala = planetStrength.kalaBala.total,
                    chestaBala = planetStrength.chestaBala,
                    naisargikaBala = planetStrength.naisargikaBala,
                    drikBala = planetStrength.drikBala
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

// ============================================
// VARGA (DIVISIONAL CHART) WRAPPER
// ============================================

class VargaCalculatorWrapper {

    data class VargaChartResult(
        val name: String,
        val ascendantSign: ZodiacSign,
        val ascendantDegree: Double,
        val planets: List<VargaPlanetPosition>
    )

    data class VargaPlanetPosition(
        val planet: Planet,
        val sign: ZodiacSign,
        val degree: Double,
        val house: Int
    )

    fun calculate(chart: VedicChart, varga: String): VargaChartResult {
        return try {
            val chartType = when (varga.uppercase()) {
                "D1", "RASHI" -> DivisionalChartType.D1_RASHI
                "D2", "HORA" -> DivisionalChartType.D2_HORA
                "D3", "DREKKANA" -> DivisionalChartType.D3_DREKKANA
                "D4", "CHATURTHAMSA" -> DivisionalChartType.D4_CHATURTHAMSA
                "D7", "SAPTAMSA" -> DivisionalChartType.D7_SAPTAMSA
                "D9", "NAVAMSA" -> DivisionalChartType.D9_NAVAMSA
                "D10", "DASAMSA" -> DivisionalChartType.D10_DASAMSA
                "D12", "DWADASHAMSA" -> DivisionalChartType.D12_DWADASAMSA
                "D16", "SHODASHAMSA" -> DivisionalChartType.D16_SHODASAMSA
                "D20", "VIMSHAMSA" -> DivisionalChartType.D20_VIMSAMSA
                "D24", "CHATURVIMSHAMSA" -> DivisionalChartType.D24_CHATURVIMSAMSA
                "D27", "NAKSHATRAMSA" -> DivisionalChartType.D27_SAPTAVIMSAMSA
                "D30", "TRIMSHAMSA" -> DivisionalChartType.D30_TRIMSAMSA
                "D40", "KHAVEDAMSA" -> DivisionalChartType.D40_KHAVEDAMSA
                "D45", "AKSHAVEDAMSA" -> DivisionalChartType.D45_AKSHAVEDAMSA
                "D60", "SHASHTIAMSA" -> DivisionalChartType.D60_SHASHTIAMSA
                else -> DivisionalChartType.D9_NAVAMSA // Default to Navamsa
            }

            val vargaChart = DivisionalChartCalculator.calculateDivisionalChart(chart, chartType)

            VargaChartResult(
                name = chartType.displayName,
                ascendantSign = vargaChart.ascendantSign,
                ascendantDegree = vargaChart.ascendantDegreeInSign,
                planets = vargaChart.planetPositions.map { pos ->
                    VargaPlanetPosition(
                        planet = pos.planet,
                        sign = pos.sign,
                        degree = pos.degree,
                        house = pos.house
                    )
                }
            )
        } catch (e: Exception) {
            // Return D1 chart as fallback
            VargaChartResult(
                name = "Rashi",
                ascendantSign = ZodiacSign.fromLongitude(chart.ascendant),
                ascendantDegree = chart.ascendant % 30,
                planets = chart.planetPositions.map { pos ->
                    VargaPlanetPosition(
                        planet = pos.planet,
                        sign = pos.sign,
                        degree = pos.degree,
                        house = pos.house
                    )
                }
            )
        }
    }
}

// ============================================
// MUHURTA WRAPPER
// ============================================

class MuhurtaCalculatorWrapper(private val context: android.content.Context) {

    data class MuhurtaTime(
        val date: Date,
        val startTime: String,
        val endTime: String,
        val quality: String,
        val score: Int,
        val tithi: String,
        val nakshatra: String,
        val yoga: String,
        val favorableFactors: List<String>,
        val cautions: List<String>
    )

    fun findAuspiciousTimes(
        activity: String,
        latitude: Double,
        longitude: Double,
        daysAhead: Int
    ): List<MuhurtaTime> {
        return try {
            val calculator = MuhurtaCalculator(context)
            val muhurtaResult = calculator.calculateMuhurta(
                java.time.LocalDateTime.now(),
                latitude,
                longitude,
                java.util.TimeZone.getDefault().id
            )

            // For now, return current day's assessment
            val inauspiciousList = mutableListOf<String>()
            inauspiciousList.add(muhurtaResult.inauspiciousPeriods.rahukala.name)
            inauspiciousList.add(muhurtaResult.inauspiciousPeriods.yamaghanta.name)
            inauspiciousList.add(muhurtaResult.inauspiciousPeriods.gulikaKala.name)
            muhurtaResult.inauspiciousPeriods.durmuhurtas.forEach { inauspiciousList.add(it.name) }

            listOf(
                MuhurtaTime(
                    date = java.sql.Date(System.currentTimeMillis()),
                    startTime = muhurtaResult.abhijitMuhurta.startTime.toString(),
                    endTime = muhurtaResult.abhijitMuhurta.endTime.toString(),
                    quality = if (muhurtaResult.isAuspicious) "Good" else "Mixed",
                    score = muhurtaResult.overallScore,
                    tithi = muhurtaResult.tithi.name,
                    nakshatra = muhurtaResult.nakshatra.nakshatra.displayName,
                    yoga = muhurtaResult.yoga.name,
                    favorableFactors = muhurtaResult.recommendations,
                    cautions = inauspiciousList.filter { it.isNotEmpty() }
                )
            )
        } catch (e: Exception) {
            emptyList()
        }
    }
}

// ============================================
// BHRIGU BINDU WRAPPER
// ============================================

class BhriguBinduCalculatorWrapper {

    data class BhriguBinduResult(
        val longitude: Double,
        val sign: ZodiacSign,
        val degree: Double,
        val nakshatra: Nakshatra,
        val nakshatraPada: Int,
        val house: Int,
        val interpretation: String,
        val karmicThemes: List<String>,
        val activationPeriods: List<ActivationPeriod>
    )

    data class ActivationPeriod(
        val trigger: String,
        val timing: String,
        val effect: String
    )

    fun calculate(chart: VedicChart): BhriguBinduResult {
        return try {
            val analysis = BhriguBinduCalculator.analyzeBhriguBindu(chart)

            // Calculate degree within sign from longitude
            val degreeInSign = analysis.bhriguBindu % 30.0

            // Extract karmic themes from interpretation
            val karmicThemes = analysis.interpretation.lifeAreas.map { it.description }

            // Convert significant periods to activation periods
            val activationPeriods = analysis.transitAnalysis?.significantPeriods?.map { period ->
                ActivationPeriod(
                    trigger = period.triggeringPlanet.displayName,
                    timing = "${period.startDate} to ${period.endDate}",
                    effect = period.description
                )
            } ?: emptyList()

            BhriguBinduResult(
                longitude = analysis.bhriguBindu,
                sign = analysis.bhriguBinduSign,
                degree = degreeInSign,
                nakshatra = analysis.bhriguBinduNakshatra,
                nakshatraPada = analysis.bhriguBinduPada,
                house = analysis.bhriguBinduHouse,
                interpretation = analysis.interpretation.generalMeaning,
                karmicThemes = karmicThemes.ifEmpty { listOf(analysis.interpretation.karmicSignificance) },
                activationPeriods = activationPeriods
            )
        } catch (e: Exception) {
            // Return default
            val moonPos = chart.planetPositions.find { it.planet == Planet.MOON }
            BhriguBinduResult(
                longitude = moonPos?.longitude ?: 0.0,
                sign = moonPos?.sign ?: ZodiacSign.ARIES,
                degree = moonPos?.degree ?: 0.0,
                nakshatra = moonPos?.nakshatra ?: Nakshatra.ASHWINI,
                nakshatraPada = moonPos?.nakshatraPada ?: 1,
                house = moonPos?.house ?: 1,
                interpretation = "Karmic destiny point requires detailed analysis",
                karmicThemes = listOf("Spiritual growth", "Material achievement"),
                activationPeriods = emptyList()
            )
        }
    }
}

// ============================================
// ARGALA WRAPPER
// ============================================

class ArgalaCalculatorWrapper {

    data class ArgalaResult(
        val targetHouse: Int,
        val sourceHouse: Int,
        val type: String,
        val planets: List<Planet>,
        val strength: Int,
        val isObstructed: Boolean,
        val effect: String
    )

    fun calculate(chart: VedicChart): List<ArgalaResult> {
        return try {
            val analysis = ArgalaCalculator.analyzeArgala(chart)

            analysis.houseArgalas.flatMap { (targetHouse, houseResult) ->
                houseResult.primaryArgalas.map { argala ->
                    // Check if this argala is obstructed
                    val isObstructed = houseResult.virodhaArgalas.any {
                        it.obstructedArgalaHouse == argala.argalaHouse && it.isEffective
                    }
                    ArgalaResult(
                        targetHouse = targetHouse,
                        sourceHouse = argala.sourceHouse,
                        type = argala.argalaType.displayName,
                        planets = argala.planets,
                        strength = (argala.strength * 100).toInt(),
                        isObstructed = isObstructed,
                        effect = argala.description
                    )
                }
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
