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
            val mahadashas = DashaCalculator.calculateMahadashasWithAntardashas(chart)

            mahadashas.forEach { mahadasha ->
                val antarPeriods = mahadasha.antardashas.map { antar ->
                    AntarDashaPeriod(
                        planet = antar.planet,
                        startDate = java.sql.Date.valueOf(antar.startDate),
                        endDate = java.sql.Date.valueOf(antar.endDate),
                        isCurrent = antar.isActiveOn(today)
                    )
                }

                dashas.add(DashaPeriod(
                    planet = mahadasha.planet,
                    startDate = java.sql.Date.valueOf(mahadasha.startDate),
                    endDate = java.sql.Date.valueOf(mahadasha.endDate),
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
        val mahadashas = DashaCalculator.calculateMahadashasWithAntardashas(chart)
        val today = LocalDate.now()

        val currentMaha = mahadashas.find { it.isActiveOn(today) } ?: mahadashas.first()
        val currentAntar = currentMaha.getAntardashaOn(today) ?: currentMaha.antardashas.first()
        val currentPratyantar = currentAntar.pratyantardashas.find { it.isActiveOn(today) }

        return CurrentDashaInfo(
            mahaDasha = currentMaha.planet,
            mahaDashaStart = java.sql.Date.valueOf(currentMaha.startDate),
            mahaDashaEnd = java.sql.Date.valueOf(currentMaha.endDate),
            antarDasha = currentAntar.planet,
            antarDashaStart = java.sql.Date.valueOf(currentAntar.startDate),
            antarDashaEnd = java.sql.Date.valueOf(currentAntar.endDate),
            pratyantarDasha = currentPratyantar?.planet
        )
    }
}

// ============================================
// YOGA WRAPPERS
// ============================================

class YogaCalculator {

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
            val calculatedYogas = com.astro.storm.ephemeris.YogaCalculator.calculateAllYogas(chart)

            calculatedYogas.forEach { yoga ->
                yogas.add(YogaResult(
                    name = yoga.name,
                    sanskritName = yoga.sanskritName ?: yoga.name,
                    category = yoga.category.name,
                    isAuspicious = yoga.isAuspicious,
                    strength = yoga.strength,
                    description = yoga.description,
                    effects = yoga.effects ?: "",
                    formingPlanets = yoga.formingPlanets
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

class AshtakavargaCalculator {

    fun calculateSarvashtakavarga(chart: VedicChart): List<Int> {
        return try {
            val result = com.astro.storm.ephemeris.AshtakavargaCalculator.calculateSarvashtakavarga(chart)
            result.signBindus.values.toList()
        } catch (e: Exception) {
            List(12) { 25 } // Default average values
        }
    }

    fun calculateBhinnashtakavarga(chart: VedicChart): Map<Planet, List<Int>> {
        return try {
            val result = com.astro.storm.ephemeris.AshtakavargaCalculator.calculateBhinnashtakavarga(chart)
            result.mapValues { (_, bindus) -> bindus.values.toList() }
        } catch (e: Exception) {
            emptyMap()
        }
    }
}

// ============================================
// PANCHANGA WRAPPER
// ============================================

class PanchangaCalculator {

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
            val panchanga = com.astro.storm.ephemeris.PanchangaCalculator.calculate(chart)

            PanchangaResult(
                tithi = TithiInfo(
                    name = panchanga.tithi.name,
                    number = panchanga.tithi.number,
                    paksha = panchanga.tithi.paksha.name,
                    deity = panchanga.tithi.deity ?: ""
                ),
                nakshatra = NakshatraInfo(
                    name = panchanga.nakshatra.name,
                    ruler = panchanga.nakshatra.ruler.displayName,
                    pada = panchanga.nakshatra.pada
                ),
                yoga = YogaInfo(
                    name = panchanga.yoga.name,
                    nature = panchanga.yoga.nature ?: "Neutral"
                ),
                karana = KaranaInfo(
                    name = panchanga.karana.name,
                    nature = panchanga.karana.nature ?: "Neutral"
                ),
                vara = VaraInfo(
                    name = panchanga.vara.name,
                    lord = panchanga.vara.lord.displayName
                ),
                rahuKala = panchanga.rahuKala,
                yamaGanda = panchanga.yamaGanda,
                gulikaKala = panchanga.gulikaKala,
                abhijitMuhurta = panchanga.abhijitMuhurta ?: "N/A"
            )
        } catch (e: Exception) {
            // Return default values on error
            PanchangaResult(
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
    }

    fun calculateForNow(latitude: Double, longitude: Double): PanchangaResult {
        return try {
            val panchanga = com.astro.storm.ephemeris.PanchangaCalculator.calculateForNow(latitude, longitude)

            PanchangaResult(
                tithi = TithiInfo(
                    name = panchanga.tithi.name,
                    number = panchanga.tithi.number,
                    paksha = panchanga.tithi.paksha.name,
                    deity = panchanga.tithi.deity ?: ""
                ),
                nakshatra = NakshatraInfo(
                    name = panchanga.nakshatra.name,
                    ruler = panchanga.nakshatra.ruler.displayName,
                    pada = panchanga.nakshatra.pada
                ),
                yoga = YogaInfo(
                    name = panchanga.yoga.name,
                    nature = panchanga.yoga.nature ?: "Neutral"
                ),
                karana = KaranaInfo(
                    name = panchanga.karana.name,
                    nature = panchanga.karana.nature ?: "Neutral"
                ),
                vara = VaraInfo(
                    name = panchanga.vara.name,
                    lord = panchanga.vara.lord.displayName
                ),
                rahuKala = panchanga.rahuKala,
                yamaGanda = panchanga.yamaGanda,
                gulikaKala = panchanga.gulikaKala,
                abhijitMuhurta = panchanga.abhijitMuhurta ?: "N/A"
            )
        } catch (e: Exception) {
            calculate(VedicChart.empty()) // Fallback
        }
    }
}

// ============================================
// TRANSIT WRAPPER
// ============================================

class TransitCalculator {

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
            val transits = TransitAnalyzer.getCurrentTransits(chart)

            transits.map { transit ->
                TransitResult(
                    planet = transit.planet,
                    transitSign = transit.currentSign,
                    transitHouse = transit.transitHouse,
                    natalSign = transit.natalSign,
                    natalHouse = transit.natalHouse,
                    aspect = transit.aspects.firstOrNull()?.let { "${it.aspectType.name} to ${it.toPlanet?.displayName ?: "House ${it.toHouse}"}" } ?: "None",
                    isRetrograde = transit.isRetrograde,
                    effect = transit.effects.firstOrNull() ?: "General transit influence",
                    intensity = transit.intensity
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

class CompatibilityCalculator {

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
            val result = GunaMilanCalculator.calculate(chart1, chart2)

            CompatibilityResult(
                totalScore = result.totalScore,
                verdict = when {
                    result.totalScore >= 25 -> "Excellent Match - Highly recommended"
                    result.totalScore >= 18 -> "Good Match - Recommended with minor considerations"
                    result.totalScore >= 14 -> "Average Match - Requires understanding and adjustment"
                    else -> "Below Average - May face challenges"
                },
                kutas = result.gunas.map { guna ->
                    KutaResult(
                        name = guna.name,
                        points = guna.obtained.toDouble(),
                        maxPoints = guna.maximum.toDouble(),
                        description = guna.description
                    )
                },
                doshas = listOf(
                    DoshaResult(
                        name = "Manglik Dosha",
                        isPresent = result.manglikMatch?.let { !it.isCompatible } ?: false,
                        severity = result.manglikMatch?.let { if (!it.isCompatible) "Medium" else "None" } ?: "Unknown",
                        remedy = result.manglikMatch?.remedyDescription ?: "Consult an astrologer"
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

class RemedyCalculator {

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
        val forPlanet: Planet,
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
            val remedies = RemediesCalculator.calculateRemedies(chart)

            RemediesResult(
                afflictedPlanets = remedies.weakPlanets.map { weak ->
                    AfflictedPlanet(
                        planet = weak.planet,
                        affliction = weak.weakness,
                        severity = weak.severity
                    )
                },
                recommendations = remedies.remedies.map { remedy ->
                    Remedy(
                        type = remedy.type,
                        forPlanet = remedy.planet,
                        description = remedy.description,
                        mantra = remedy.mantra,
                        gemstone = remedy.gemstone,
                        charity = remedy.charity,
                        fasting = remedy.fasting,
                        color = remedy.color,
                        day = remedy.day
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

class ShadbalaCalculator {

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
            val shadbala = com.astro.storm.ephemeris.ShadbalaCalculator.calculate(chart)

            shadbala.planetStrengths.map { (planet, strength) ->
                ShadbalaResult(
                    planet = planet,
                    totalStrength = strength.totalStrength,
                    requiredStrength = strength.requiredStrength,
                    sthanaBala = strength.sthanaBala,
                    digBala = strength.digBala,
                    kalaBala = strength.kalaBala,
                    chestaBala = strength.chestaBala,
                    naisargikaBala = strength.naisargikaBala,
                    drikBala = strength.drikBala
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

class VargaCalculator {

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
            val divisor = when (varga.uppercase()) {
                "D1" -> 1
                "D2" -> 2
                "D3" -> 3
                "D4" -> 4
                "D7" -> 7
                "D9" -> 9
                "D10" -> 10
                "D12" -> 12
                "D16" -> 16
                "D20" -> 20
                "D24" -> 24
                "D27" -> 27
                "D30" -> 30
                "D40" -> 40
                "D45" -> 45
                "D60" -> 60
                else -> 9 // Default to Navamsa
            }

            val vargaName = when (divisor) {
                1 -> "Rashi"
                2 -> "Hora"
                3 -> "Drekkana"
                4 -> "Chaturthamsa"
                7 -> "Saptamsa"
                9 -> "Navamsa"
                10 -> "Dasamsa"
                12 -> "Dwadashamsa"
                16 -> "Shodashamsa"
                20 -> "Vimshamsa"
                24 -> "Chaturvimshamsa"
                27 -> "Nakshatramsa"
                30 -> "Trimshamsa"
                40 -> "Khavedamsa"
                45 -> "Akshavedamsa"
                60 -> "Shashtiamsa"
                else -> "Navamsa"
            }

            val vargaChart = DivisionalChartCalculator.calculateDivisionalChart(chart, divisor)

            VargaChartResult(
                name = vargaName,
                ascendantSign = vargaChart.ascendantSign,
                ascendantDegree = vargaChart.ascendantDegree,
                planets = vargaChart.planets.map { pos ->
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

class MuhurtaCalculator {

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
            val muhurtas = com.astro.storm.ephemeris.MuhurtaCalculator.findMuhurtas(
                activityType = activity,
                latitude = latitude,
                longitude = longitude,
                daysAhead = daysAhead
            )

            muhurtas.map { muhurta ->
                MuhurtaTime(
                    date = java.sql.Date.valueOf(muhurta.date),
                    startTime = muhurta.startTime,
                    endTime = muhurta.endTime,
                    quality = muhurta.quality.name,
                    score = muhurta.score,
                    tithi = muhurta.tithi,
                    nakshatra = muhurta.nakshatra,
                    yoga = muhurta.yoga,
                    favorableFactors = muhurta.favorableFactors,
                    cautions = muhurta.cautions
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

// ============================================
// BHRIGU BINDU WRAPPER
// ============================================

class BhriguBinduCalculator {

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
            val result = com.astro.storm.ephemeris.BhriguBinduCalculator.calculate(chart)

            BhriguBinduResult(
                longitude = result.longitude,
                sign = result.sign,
                degree = result.degree,
                nakshatra = result.nakshatra,
                nakshatraPada = result.pada,
                house = result.house,
                interpretation = result.interpretation,
                karmicThemes = result.karmicThemes,
                activationPeriods = result.activationPeriods.map { period ->
                    ActivationPeriod(
                        trigger = period.trigger,
                        timing = period.timing,
                        effect = period.effect
                    )
                }
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

class ArgalaCalculator {

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
            val argalas = com.astro.storm.ephemeris.ArgalaCalculator.calculate(chart)

            argalas.map { argala ->
                ArgalaResult(
                    targetHouse = argala.targetHouse,
                    sourceHouse = argala.sourceHouse,
                    type = argala.type.name,
                    planets = argala.planets,
                    strength = argala.strength,
                    isObstructed = argala.isObstructed,
                    effect = argala.effect
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
