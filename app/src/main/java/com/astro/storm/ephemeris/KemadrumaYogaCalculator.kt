package com.astro.storm.ephemeris

import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign

/**
 * Comprehensive Kemadruma Yoga Calculator
 *
 * Kemadruma Yoga is one of the most significant negative yogas in Vedic astrology,
 * directly affecting the Moon - the significator of mind, emotions, and inner peace.
 *
 * Definition (Per BPHS and Phaladeepika):
 * Kemadruma Yoga forms when there are no planets in the 2nd and 12th houses from Moon,
 * and no planets conjunct the Moon (excluding Sun, Rahu, Ketu).
 *
 * Effects:
 * - Mental distress and emotional instability
 * - Financial difficulties despite hard work
 * - Lack of support from family and friends
 * - Sudden reversals in fortune
 * - Feelings of isolation and loneliness
 *
 * IMPORTANT: Kemadruma has several cancellation (Bhanga) conditions that significantly
 * reduce or nullify its negative effects. This calculator provides comprehensive
 * cancellation analysis.
 *
 * Cancellation Conditions:
 * 1. Planets in Kendra (1, 4, 7, 10) from Moon or Lagna
 * 2. Moon in Kendra from Lagna
 * 3. Moon aspected by benefics (Jupiter, Venus, Mercury)
 * 4. Moon conjunct benefics
 * 5. Moon in exaltation, own sign, or friend's sign
 * 6. Full Moon (Shukla Paksha and bright)
 * 7. Strong planets aspecting Moon
 *
 * Classical References:
 * - Brihat Parashara Hora Shastra (BPHS)
 * - Phaladeepika
 * - Saravali
 * - Hora Sara
 *
 * @author AstroStorm - Ultra-Precision Vedic Astrology
 */
object KemadrumaYogaCalculator {

    // ============================================================================
    // KEMADRUMA STATUS
    // ============================================================================

    /**
     * Kemadruma formation status
     */
    enum class KemadrumaStatus(
        val displayName: String,
        val severity: Int // 0-5, 5 being most severe
    ) {
        NOT_PRESENT("Not Present", 0),
        FULLY_CANCELLED("Fully Cancelled", 0),
        MOSTLY_CANCELLED("Mostly Cancelled", 1),
        PARTIALLY_CANCELLED("Partially Cancelled", 2),
        WEAKLY_CANCELLED("Weakly Cancelled", 3),
        ACTIVE_MODERATE("Active (Moderate)", 4),
        ACTIVE_SEVERE("Active (Severe)", 5);

        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                NOT_PRESENT -> StringKeyAnalysis.KEMADRUMA_NOT_PRESENT
                FULLY_CANCELLED -> StringKeyAnalysis.KEMADRUMA_FULLY_CANCELLED
                MOSTLY_CANCELLED -> StringKeyAnalysis.KEMADRUMA_MOSTLY_CANCELLED
                PARTIALLY_CANCELLED -> StringKeyAnalysis.KEMADRUMA_PARTIALLY_CANCELLED
                WEAKLY_CANCELLED -> StringKeyAnalysis.KEMADRUMA_WEAKLY_CANCELLED
                ACTIVE_MODERATE -> StringKeyAnalysis.KEMADRUMA_ACTIVE_MODERATE
                ACTIVE_SEVERE -> StringKeyAnalysis.KEMADRUMA_ACTIVE_SEVERE
            }
            return StringResources.get(key, language)
        }
    }

    /**
     * Types of Kemadruma Bhanga (cancellation)
     */
    enum class BhangaType(
        val displayName: String,
        val cancellationStrength: Int // 1-3, 3 being strongest
    ) {
        KENDRA_FROM_MOON("Planets in Kendra from Moon", 3),
        KENDRA_FROM_LAGNA("Planets in Kendra from Lagna", 2),
        MOON_IN_KENDRA("Moon in Kendra from Lagna", 3),
        BENEFIC_ASPECT("Benefic planet aspects Moon", 2),
        BENEFIC_CONJUNCTION("Benefic planet conjuncts Moon", 3),
        MOON_EXALTED("Moon in exaltation (Taurus)", 3),
        MOON_OWN_SIGN("Moon in own sign (Cancer)", 3),
        MOON_FRIEND_SIGN("Moon in friendly sign", 1),
        FULL_MOON("Full or bright Moon", 2),
        ANGULAR_MOON("Moon in angular house", 2),
        STRONG_DISPOSITOR("Moon's dispositor is strong", 1),
        JUPITER_ASPECT("Jupiter aspects Moon", 3),
        VENUS_ASPECT("Venus aspects Moon", 2);

        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                KENDRA_FROM_MOON -> StringKeyAnalysis.BHANGA_KENDRA_MOON
                KENDRA_FROM_LAGNA -> StringKeyAnalysis.BHANGA_KENDRA_LAGNA
                MOON_IN_KENDRA -> StringKeyAnalysis.BHANGA_MOON_KENDRA
                BENEFIC_ASPECT -> StringKeyAnalysis.BHANGA_BENEFIC_ASPECT
                BENEFIC_CONJUNCTION -> StringKeyAnalysis.BHANGA_BENEFIC_CONJUNCTION
                MOON_EXALTED -> StringKeyAnalysis.BHANGA_MOON_EXALTED
                MOON_OWN_SIGN -> StringKeyAnalysis.BHANGA_MOON_OWN
                MOON_FRIEND_SIGN -> StringKeyAnalysis.BHANGA_MOON_FRIEND
                FULL_MOON -> StringKeyAnalysis.BHANGA_FULL_MOON
                ANGULAR_MOON -> StringKeyAnalysis.BHANGA_ANGULAR_MOON
                STRONG_DISPOSITOR -> StringKeyAnalysis.BHANGA_STRONG_DISPOSITOR
                JUPITER_ASPECT -> StringKeyAnalysis.BHANGA_JUPITER_ASPECT
                VENUS_ASPECT -> StringKeyAnalysis.BHANGA_VENUS_ASPECT
            }
            return StringResources.get(key, language)
        }
    }

    // ============================================================================
    // DATA CLASSES
    // ============================================================================

    /**
     * Complete Kemadruma analysis
     */
    data class KemadrumaAnalysis(
        val moonPosition: MoonAnalysis,
        val isKemadrumaFormed: Boolean,
        val formationDetails: KemadrumaFormation,
        val cancellations: List<KemadrumaBhanga>,
        val totalCancellationScore: Int,
        val effectiveStatus: KemadrumaStatus,
        val emotionalImpact: EmotionalImpact,
        val financialImpact: FinancialImpact,
        val socialImpact: SocialImpact,
        val activationPeriods: List<ActivationPeriod>,
        val remedies: List<KemadrumaRemedy>,
        val interpretation: String,
        val timestamp: Long = System.currentTimeMillis()
    )

    /**
     * Moon position analysis
     */
    data class MoonAnalysis(
        val sign: ZodiacSign,
        val house: Int,
        val degree: Double,
        val nakshatra: String,
        val isExalted: Boolean,
        val isDebilitated: Boolean,
        val isInOwnSign: Boolean,
        val paksha: LunarPaksha,
        val tithi: Int,
        val brightness: MoonBrightness,
        val dispositor: Planet,
        val dispositorStrength: String
    )

    enum class LunarPaksha { SHUKLA, KRISHNA }
    enum class MoonBrightness { FULL, BRIGHT, AVERAGE, DIM, NEW }

    /**
     * Kemadruma formation details
     */
    data class KemadrumaFormation(
        val hasSecondHouseEmpty: Boolean,
        val hasTwelfthHouseEmpty: Boolean,
        val isMoonUnaspected: Boolean,
        val planetsInSecondFromMoon: List<Planet>,
        val planetsInTwelfthFromMoon: List<Planet>,
        val planetsConjunctMoon: List<Planet>,
        val formationStrength: Int // 0-100
    )

    /**
     * Single cancellation factor
     */
    data class KemadrumaBhanga(
        val type: BhangaType,
        val planet: Planet?,
        val house: Int?,
        val description: String,
        val strength: Int,
        val isEffective: Boolean
    )

    /**
     * Emotional impact analysis
     */
    data class EmotionalImpact(
        val overallLevel: ImpactLevel,
        val mentalPeace: Int, // 0-100, 100 being most peaceful
        val emotionalStability: Int,
        val confidenceLevel: Int,
        val anxietyTendency: Int,
        val depressionRisk: Int,
        val recommendations: List<String>
    )

    /**
     * Financial impact analysis
     */
    data class FinancialImpact(
        val overallLevel: ImpactLevel,
        val wealthRetention: Int, // 0-100
        val financialStability: Int,
        val unexpectedExpenses: Int,
        val supportFromOthers: Int,
        val recommendations: List<String>
    )

    /**
     * Social impact analysis
     */
    data class SocialImpact(
        val overallLevel: ImpactLevel,
        val familySupport: Int, // 0-100
        val friendshipQuality: Int,
        val publicImage: Int,
        val isolationTendency: Int,
        val recommendations: List<String>
    )

    enum class ImpactLevel { SEVERE, HIGH, MODERATE, MILD, MINIMAL, POSITIVE }

    /**
     * Dasha periods when Kemadruma activates
     */
    data class ActivationPeriod(
        val planet: Planet,
        val periodType: String, // Mahadasha, Antardasha
        val reason: String,
        val intensity: Int // 1-5
    )

    /**
     * Remedy for Kemadruma
     */
    data class KemadrumaRemedy(
        val category: RemedyCategory,
        val description: String,
        val timing: String,
        val priority: Int // 1-3
    )

    enum class RemedyCategory {
        MANTRA, DONATION, FASTING, GEMSTONE, PUJA, LIFESTYLE, YANTRA
    }

    // ============================================================================
    // CALCULATION METHODS
    // ============================================================================

    /**
     * Perform complete Kemadruma analysis
     */
    fun analyzeKemadruma(chart: VedicChart): KemadrumaAnalysis? {
        // Get Moon position
        val moonPos = chart.planetPositions.find { it.planet == Planet.MOON }
            ?: return null

        val sunPos = chart.planetPositions.find { it.planet == Planet.SUN }
            ?: return null

        // Analyze Moon
        val moonAnalysis = analyzeMoon(moonPos, sunPos, chart)

        // Check Kemadruma formation
        val formationDetails = checkKemadrumaFormation(moonPos, chart)
        val isKemadrumaFormed = formationDetails.hasSecondHouseEmpty &&
                                formationDetails.hasTwelfthHouseEmpty &&
                                formationDetails.planetsConjunctMoon.isEmpty()

        // Calculate cancellations
        val cancellations = if (isKemadrumaFormed) {
            calculateCancellations(moonPos, chart)
        } else {
            emptyList()
        }

        val totalCancellationScore = cancellations.sumOf { it.strength }

        // Determine effective status
        val effectiveStatus = determineEffectiveStatus(isKemadrumaFormed, totalCancellationScore)

        // Calculate impacts
        val emotionalImpact = calculateEmotionalImpact(effectiveStatus, moonAnalysis, cancellations)
        val financialImpact = calculateFinancialImpact(effectiveStatus, chart)
        val socialImpact = calculateSocialImpact(effectiveStatus, chart)

        // Find activation periods
        val activationPeriods = findActivationPeriods(chart, effectiveStatus)

        // Generate remedies
        val remedies = generateRemedies(effectiveStatus, moonAnalysis)

        // Generate interpretation
        val interpretation = generateInterpretation(
            isKemadrumaFormed, effectiveStatus, moonAnalysis, cancellations
        )

        return KemadrumaAnalysis(
            moonPosition = moonAnalysis,
            isKemadrumaFormed = isKemadrumaFormed,
            formationDetails = formationDetails,
            cancellations = cancellations,
            totalCancellationScore = totalCancellationScore,
            effectiveStatus = effectiveStatus,
            emotionalImpact = emotionalImpact,
            financialImpact = financialImpact,
            socialImpact = socialImpact,
            activationPeriods = activationPeriods,
            remedies = remedies,
            interpretation = interpretation
        )
    }

    /**
     * Analyze Moon's position and strength
     */
    private fun analyzeMoon(
        moonPos: PlanetPosition,
        sunPos: PlanetPosition,
        chart: VedicChart
    ): MoonAnalysis {
        val isExalted = VedicAstrologyUtils.isExalted(moonPos)
        val isDebilitated = VedicAstrologyUtils.isDebilitated(moonPos)
        val isInOwnSign = moonPos.sign == ZodiacSign.CANCER

        // Calculate lunar phase (paksha and tithi)
        val moonLong = moonPos.longitude
        val sunLong = sunPos.longitude
        var diff = moonLong - sunLong
        if (diff < 0) diff += 360

        val tithi = ((diff / 12.0) + 1).toInt().coerceIn(1, 30)
        val paksha = if (tithi <= 15) LunarPaksha.SHUKLA else LunarPaksha.KRISHNA

        // Determine Moon brightness
        val brightness = when {
            tithi in 11..15 || tithi in 26..30 -> MoonBrightness.FULL
            tithi in 8..10 || tithi in 23..25 -> MoonBrightness.BRIGHT
            tithi in 5..7 || tithi in 20..22 -> MoonBrightness.AVERAGE
            tithi in 2..4 || tithi in 17..19 -> MoonBrightness.DIM
            else -> MoonBrightness.NEW
        }

        // Get dispositor
        val dispositor = moonPos.sign.ruler
        val dispositorPos = chart.planetPositions.find { it.planet == dispositor }
        val dispositorStrength = dispositorPos?.let { assessPlanetStrength(it) } ?: "Unknown"

        return MoonAnalysis(
            sign = moonPos.sign,
            house = moonPos.house,
            degree = moonPos.degree,
            nakshatra = moonPos.nakshatra.displayName,
            isExalted = isExalted,
            isDebilitated = isDebilitated,
            isInOwnSign = isInOwnSign,
            paksha = paksha,
            tithi = tithi,
            brightness = brightness,
            dispositor = dispositor,
            dispositorStrength = dispositorStrength
        )
    }

    /**
     * Check if Kemadruma Yoga is formed
     */
    private fun checkKemadrumaFormation(moonPos: PlanetPosition, chart: VedicChart): KemadrumaFormation {
        val moonSign = moonPos.sign.number

        // Calculate 2nd and 12th from Moon
        val secondFromMoon = ((moonSign) % 12) + 1
        val twelfthFromMoon = if (moonSign == 1) 12 else moonSign - 1

        // Planets to check (exclude Sun, Rahu, Ketu for classical Kemadruma)
        val relevantPlanets = listOf(
            Planet.MOON, Planet.MARS, Planet.MERCURY, Planet.JUPITER, Planet.VENUS, Planet.SATURN
        )

        val planetsInSecond = mutableListOf<Planet>()
        val planetsInTwelfth = mutableListOf<Planet>()
        val planetsConjunct = mutableListOf<Planet>()

        for (pos in chart.planetPositions) {
            if (pos.planet !in relevantPlanets || pos.planet == Planet.MOON) continue

            when (pos.sign.number) {
                secondFromMoon -> planetsInSecond.add(pos.planet)
                twelfthFromMoon -> planetsInTwelfth.add(pos.planet)
                moonSign -> planetsConjunct.add(pos.planet)
            }
        }

        val hasSecondEmpty = planetsInSecond.isEmpty()
        val hasTwelfthEmpty = planetsInTwelfth.isEmpty()
        val isMoonAlone = planetsConjunct.isEmpty()

        // Calculate formation strength
        val formationStrength = when {
            !hasSecondEmpty || !hasTwelfthEmpty || !isMoonAlone -> 0
            moonPos.isRetrograde -> 70
            else -> 85
        }

        return KemadrumaFormation(
            hasSecondHouseEmpty = hasSecondEmpty,
            hasTwelfthHouseEmpty = hasTwelfthEmpty,
            isMoonUnaspected = isMoonAlone,
            planetsInSecondFromMoon = planetsInSecond,
            planetsInTwelfthFromMoon = planetsInTwelfth,
            planetsConjunctMoon = planetsConjunct,
            formationStrength = formationStrength
        )
    }

    /**
     * Calculate all cancellation factors
     */
    private fun calculateCancellations(
        moonPos: PlanetPosition,
        chart: VedicChart
    ): List<KemadrumaBhanga> {
        val cancellations = mutableListOf<KemadrumaBhanga>()
        val moonSign = moonPos.sign.number
        val ascendantSign = ZodiacSign.fromLongitude(chart.ascendant).number

        // 1. Planets in Kendra from Moon
        val kendraFromMoon = listOf(
            moonSign,
            ((moonSign + 2) % 12).let { if (it == 0) 12 else it },
            ((moonSign + 5) % 12).let { if (it == 0) 12 else it },
            ((moonSign + 8) % 12).let { if (it == 0) 12 else it }
        )

        for (pos in chart.planetPositions) {
            if (pos.planet == Planet.MOON || pos.planet == Planet.RAHU || pos.planet == Planet.KETU) continue

            if (pos.sign.number in kendraFromMoon) {
                cancellations.add(KemadrumaBhanga(
                    type = BhangaType.KENDRA_FROM_MOON,
                    planet = pos.planet,
                    house = kendraFromMoon.indexOf(pos.sign.number) * 3 + 1, // 1, 4, 7, or 10
                    description = "${pos.planet.displayName} in Kendra from Moon",
                    strength = BhangaType.KENDRA_FROM_MOON.cancellationStrength * 10,
                    isEffective = true
                ))
            }
        }

        // 2. Planets in Kendra from Lagna
        val kendraFromLagna = listOf(1, 4, 7, 10)
        for (pos in chart.planetPositions) {
            if (pos.planet == Planet.MOON || pos.planet == Planet.RAHU || pos.planet == Planet.KETU) continue

            if (pos.house in kendraFromLagna && pos.sign.number !in kendraFromMoon) {
                cancellations.add(KemadrumaBhanga(
                    type = BhangaType.KENDRA_FROM_LAGNA,
                    planet = pos.planet,
                    house = pos.house,
                    description = "${pos.planet.displayName} in ${pos.house}th house (Kendra from Lagna)",
                    strength = BhangaType.KENDRA_FROM_LAGNA.cancellationStrength * 10,
                    isEffective = true
                ))
            }
        }

        // 3. Moon in Kendra from Lagna
        if (moonPos.house in listOf(1, 4, 7, 10)) {
            cancellations.add(KemadrumaBhanga(
                type = BhangaType.MOON_IN_KENDRA,
                planet = Planet.MOON,
                house = moonPos.house,
                description = "Moon in ${moonPos.house}th house (Kendra from Lagna)",
                strength = BhangaType.MOON_IN_KENDRA.cancellationStrength * 10,
                isEffective = true
            ))
        }

        // 4. Jupiter aspecting Moon
        val jupiterPos = chart.planetPositions.find { it.planet == Planet.JUPITER }
        jupiterPos?.let {
            if (hasJupiterAspect(it.sign.number, moonSign)) {
                cancellations.add(KemadrumaBhanga(
                    type = BhangaType.JUPITER_ASPECT,
                    planet = Planet.JUPITER,
                    house = it.house,
                    description = "Jupiter aspects Moon from ${it.sign.displayName}",
                    strength = BhangaType.JUPITER_ASPECT.cancellationStrength * 10,
                    isEffective = true
                ))
            }
        }

        // 5. Venus aspecting Moon
        val venusPos = chart.planetPositions.find { it.planet == Planet.VENUS }
        venusPos?.let {
            if (hasStandardAspect(it.sign.number, moonSign)) {
                cancellations.add(KemadrumaBhanga(
                    type = BhangaType.VENUS_ASPECT,
                    planet = Planet.VENUS,
                    house = it.house,
                    description = "Venus aspects Moon from ${it.sign.displayName}",
                    strength = BhangaType.VENUS_ASPECT.cancellationStrength * 10,
                    isEffective = true
                ))
            }
        }

        // 6. Moon in exaltation
        if (VedicAstrologyUtils.isExalted(moonPos)) {
            cancellations.add(KemadrumaBhanga(
                type = BhangaType.MOON_EXALTED,
                planet = Planet.MOON,
                house = moonPos.house,
                description = "Moon exalted in Taurus",
                strength = BhangaType.MOON_EXALTED.cancellationStrength * 10,
                isEffective = true
            ))
        }

        // 7. Moon in own sign
        if (moonPos.sign == ZodiacSign.CANCER) {
            cancellations.add(KemadrumaBhanga(
                type = BhangaType.MOON_OWN_SIGN,
                planet = Planet.MOON,
                house = moonPos.house,
                description = "Moon in own sign Cancer",
                strength = BhangaType.MOON_OWN_SIGN.cancellationStrength * 10,
                isEffective = true
            ))
        }

        // 8. Moon in friendly sign
        val friendlySigns = listOf(ZodiacSign.TAURUS, ZodiacSign.CANCER, ZodiacSign.GEMINI, ZodiacSign.VIRGO)
        if (moonPos.sign in friendlySigns && !VedicAstrologyUtils.isExalted(moonPos) && moonPos.sign != ZodiacSign.CANCER) {
            cancellations.add(KemadrumaBhanga(
                type = BhangaType.MOON_FRIEND_SIGN,
                planet = Planet.MOON,
                house = moonPos.house,
                description = "Moon in friendly sign ${moonPos.sign.displayName}",
                strength = BhangaType.MOON_FRIEND_SIGN.cancellationStrength * 10,
                isEffective = true
            ))
        }

        return cancellations
    }

    /**
     * Check Jupiter's special aspects (5th, 7th, 9th from its position)
     */
    private fun hasJupiterAspect(jupiterSign: Int, targetSign: Int): Boolean {
        val aspects = listOf(
            ((jupiterSign + 4) % 12).let { if (it == 0) 12 else it }, // 5th
            ((jupiterSign + 6) % 12).let { if (it == 0) 12 else it }, // 7th
            ((jupiterSign + 8) % 12).let { if (it == 0) 12 else it }  // 9th
        )
        return targetSign in aspects
    }

    /**
     * Check standard 7th house aspect
     */
    private fun hasStandardAspect(fromSign: Int, toSign: Int): Boolean {
        val seventhFrom = ((fromSign + 6) % 12).let { if (it == 0) 12 else it }
        return toSign == seventhFrom
    }

    /**
     * Determine effective status based on cancellations
     */
    private fun determineEffectiveStatus(isFormed: Boolean, cancellationScore: Int): KemadrumaStatus {
        if (!isFormed) return KemadrumaStatus.NOT_PRESENT

        return when {
            cancellationScore >= 60 -> KemadrumaStatus.FULLY_CANCELLED
            cancellationScore >= 45 -> KemadrumaStatus.MOSTLY_CANCELLED
            cancellationScore >= 30 -> KemadrumaStatus.PARTIALLY_CANCELLED
            cancellationScore >= 15 -> KemadrumaStatus.WEAKLY_CANCELLED
            cancellationScore >= 5 -> KemadrumaStatus.ACTIVE_MODERATE
            else -> KemadrumaStatus.ACTIVE_SEVERE
        }
    }

    /**
     * Calculate emotional impact
     */
    private fun calculateEmotionalImpact(
        status: KemadrumaStatus,
        moonAnalysis: MoonAnalysis,
        cancellations: List<KemadrumaBhanga>
    ): EmotionalImpact {
        val severityFactor = status.severity / 5.0

        // Base scores (100 = best)
        var mentalPeace = (100 - severityFactor * 50).toInt()
        var emotionalStability = (100 - severityFactor * 45).toInt()
        var confidence = (100 - severityFactor * 40).toInt()
        var anxiety = (severityFactor * 60).toInt()
        var depressionRisk = (severityFactor * 55).toInt()

        // Adjust for Moon's brightness
        when (moonAnalysis.brightness) {
            MoonBrightness.FULL, MoonBrightness.BRIGHT -> {
                mentalPeace += 15
                anxiety -= 10
            }
            MoonBrightness.DIM, MoonBrightness.NEW -> {
                mentalPeace -= 10
                anxiety += 15
            }
            else -> {}
        }

        // Adjust for exaltation/debilitation
        if (moonAnalysis.isExalted) {
            mentalPeace += 20
            confidence += 15
        }
        if (moonAnalysis.isDebilitated) {
            mentalPeace -= 20
            depressionRisk += 20
        }

        // Adjust for cancellations
        if (cancellations.any { it.type == BhangaType.JUPITER_ASPECT }) {
            mentalPeace += 15
            confidence += 10
        }

        val overallLevel = when (status.severity) {
            0 -> ImpactLevel.POSITIVE
            1 -> ImpactLevel.MINIMAL
            2 -> ImpactLevel.MILD
            3 -> ImpactLevel.MODERATE
            4 -> ImpactLevel.HIGH
            else -> ImpactLevel.SEVERE
        }

        val recommendations = buildEmotionalRecommendations(status, moonAnalysis)

        return EmotionalImpact(
            overallLevel = overallLevel,
            mentalPeace = mentalPeace.coerceIn(0, 100),
            emotionalStability = emotionalStability.coerceIn(0, 100),
            confidenceLevel = confidence.coerceIn(0, 100),
            anxietyTendency = anxiety.coerceIn(0, 100),
            depressionRisk = depressionRisk.coerceIn(0, 100),
            recommendations = recommendations
        )
    }

    /**
     * Calculate financial impact
     */
    private fun calculateFinancialImpact(
        status: KemadrumaStatus,
        chart: VedicChart
    ): FinancialImpact {
        val severityFactor = status.severity / 5.0

        var wealthRetention = (100 - severityFactor * 50).toInt()
        var stability = (100 - severityFactor * 45).toInt()
        var unexpectedExpenses = (severityFactor * 55).toInt()
        var support = (100 - severityFactor * 60).toInt()

        // Check 2nd and 11th house strength for financial matters
        val secondLord = ZodiacSign.entries[(ZodiacSign.fromLongitude(chart.ascendant).number) % 12].ruler
        val secondLordPos = chart.planetPositions.find { it.planet == secondLord }

        secondLordPos?.let {
            if (VedicAstrologyUtils.isExalted(it) || VedicAstrologyUtils.isInOwnSign(it)) {
                wealthRetention += 15
                stability += 10
            }
        }

        val overallLevel = when (status.severity) {
            0 -> ImpactLevel.POSITIVE
            1 -> ImpactLevel.MINIMAL
            2 -> ImpactLevel.MILD
            3 -> ImpactLevel.MODERATE
            4 -> ImpactLevel.HIGH
            else -> ImpactLevel.SEVERE
        }

        return FinancialImpact(
            overallLevel = overallLevel,
            wealthRetention = wealthRetention.coerceIn(0, 100),
            financialStability = stability.coerceIn(0, 100),
            unexpectedExpenses = unexpectedExpenses.coerceIn(0, 100),
            supportFromOthers = support.coerceIn(0, 100),
            recommendations = buildFinancialRecommendations(status)
        )
    }

    /**
     * Calculate social impact
     */
    private fun calculateSocialImpact(
        status: KemadrumaStatus,
        chart: VedicChart
    ): SocialImpact {
        val severityFactor = status.severity / 5.0

        var familySupport = (100 - severityFactor * 55).toInt()
        var friendship = (100 - severityFactor * 50).toInt()
        var publicImage = (100 - severityFactor * 40).toInt()
        var isolation = (severityFactor * 60).toInt()

        // Check 4th house (family) and 11th house (friends)
        val fourthLord = ZodiacSign.entries[(ZodiacSign.fromLongitude(chart.ascendant).number + 3) % 12].ruler
        val fourthLordPos = chart.planetPositions.find { it.planet == fourthLord }

        fourthLordPos?.let {
            if (VedicAstrologyUtils.isExalted(it) || VedicAstrologyUtils.isInOwnSign(it)) {
                familySupport += 15
            }
        }

        val overallLevel = when (status.severity) {
            0 -> ImpactLevel.POSITIVE
            1 -> ImpactLevel.MINIMAL
            2 -> ImpactLevel.MILD
            3 -> ImpactLevel.MODERATE
            4 -> ImpactLevel.HIGH
            else -> ImpactLevel.SEVERE
        }

        return SocialImpact(
            overallLevel = overallLevel,
            familySupport = familySupport.coerceIn(0, 100),
            friendshipQuality = friendship.coerceIn(0, 100),
            publicImage = publicImage.coerceIn(0, 100),
            isolationTendency = isolation.coerceIn(0, 100),
            recommendations = buildSocialRecommendations(status)
        )
    }

    /**
     * Find Dasha periods when Kemadruma activates
     */
    private fun findActivationPeriods(chart: VedicChart, status: KemadrumaStatus): List<ActivationPeriod> {
        if (status == KemadrumaStatus.NOT_PRESENT || status == KemadrumaStatus.FULLY_CANCELLED) {
            return emptyList()
        }

        val periods = mutableListOf<ActivationPeriod>()

        // Moon Mahadasha/Antardasha always activates Kemadruma
        periods.add(ActivationPeriod(
            planet = Planet.MOON,
            periodType = "Mahadasha/Antardasha",
            reason = "Moon is the yoga-forming planet",
            intensity = 5
        ))

        // Rahu periods can trigger mental disturbances
        periods.add(ActivationPeriod(
            planet = Planet.RAHU,
            periodType = "Mahadasha/Antardasha",
            reason = "Rahu amplifies mental anxieties",
            intensity = 4
        ))

        // Saturn periods can bring isolation
        periods.add(ActivationPeriod(
            planet = Planet.SATURN,
            periodType = "Mahadasha/Antardasha",
            reason = "Saturn increases feelings of separation",
            intensity = 4
        ))

        // Check Moon's dispositor
        val moonPos = chart.planetPositions.find { it.planet == Planet.MOON }
        moonPos?.let {
            val dispositor = it.sign.ruler
            periods.add(ActivationPeriod(
                planet = dispositor,
                periodType = "Mahadasha/Antardasha",
                reason = "As Moon's dispositor, activates Moon-related yogas",
                intensity = 3
            ))
        }

        return periods
    }

    /**
     * Generate remedies for Kemadruma
     */
    private fun generateRemedies(status: KemadrumaStatus, moonAnalysis: MoonAnalysis): List<KemadrumaRemedy> {
        if (status == KemadrumaStatus.NOT_PRESENT || status == KemadrumaStatus.FULLY_CANCELLED) {
            return listOf(
                KemadrumaRemedy(
                    category = RemedyCategory.LIFESTYLE,
                    description = "Continue regular Moon worship on Mondays for emotional wellbeing",
                    timing = "Monday evenings",
                    priority = 3
                )
            )
        }

        val remedies = mutableListOf<KemadrumaRemedy>()

        // Priority 1: Mantra
        remedies.add(KemadrumaRemedy(
            category = RemedyCategory.MANTRA,
            description = "Chant 'Om Som Somaya Namah' or 'Om Chandraya Namah' 108 times daily",
            timing = "Monday evenings, during Moon hora",
            priority = 1
        ))

        // Priority 1: Puja
        remedies.add(KemadrumaRemedy(
            category = RemedyCategory.PUJA,
            description = "Perform Chandra Shanti puja or Kemadruma Nivarana puja",
            timing = "On a Monday in Shukla Paksha",
            priority = 1
        ))

        // Priority 2: Donation
        remedies.add(KemadrumaRemedy(
            category = RemedyCategory.DONATION,
            description = "Donate white items (rice, milk, white cloth) on Mondays",
            timing = "Every Monday",
            priority = 2
        ))

        // Priority 2: Fasting
        remedies.add(KemadrumaRemedy(
            category = RemedyCategory.FASTING,
            description = "Observe Monday fasts consuming only milk and white foods",
            timing = "Mondays, especially on Purnima",
            priority = 2
        ))

        // Priority 2: Gemstone
        if (status.severity >= 3) {
            remedies.add(KemadrumaRemedy(
                category = RemedyCategory.GEMSTONE,
                description = "Wear natural Pearl (Moti) in silver on Monday after proper energization",
                timing = "On Monday during Shukla Paksha",
                priority = 2
            ))
        }

        // Priority 3: Yantra
        remedies.add(KemadrumaRemedy(
            category = RemedyCategory.YANTRA,
            description = "Worship Chandra Yantra daily with sandalwood paste and white flowers",
            timing = "Daily in the evening",
            priority = 3
        ))

        // Priority 3: Lifestyle
        remedies.add(KemadrumaRemedy(
            category = RemedyCategory.LIFESTYLE,
            description = "Maintain good relationship with mother, serve elderly women, avoid isolation",
            timing = "Ongoing",
            priority = 3
        ))

        remedies.add(KemadrumaRemedy(
            category = RemedyCategory.LIFESTYLE,
            description = "Practice meditation and pranayama for mental peace",
            timing = "Daily, preferably at dawn and dusk",
            priority = 3
        ))

        return remedies.sortedBy { it.priority }
    }

    // ============================================================================
    // HELPER METHODS
    // ============================================================================

    private fun assessPlanetStrength(pos: PlanetPosition): String {
        return when {
            VedicAstrologyUtils.isExalted(pos) -> "Strong (Exalted)"
            VedicAstrologyUtils.isDebilitated(pos) -> "Weak (Debilitated)"
            VedicAstrologyUtils.isInOwnSign(pos) -> "Strong (Own Sign)"
            VedicAstrologyUtils.isInMoolatrikona(pos) -> "Strong (Moolatrikona)"
            else -> "Average"
        }
    }

    private fun buildEmotionalRecommendations(status: KemadrumaStatus, moon: MoonAnalysis): List<String> {
        val recs = mutableListOf<String>()

        if (status.severity >= 3) {
            recs.add("Regular Moon worship is essential for emotional balance")
            recs.add("Meditation and mindfulness practices highly recommended")
            recs.add("Avoid isolation - maintain social connections")
        }

        if (moon.brightness in listOf(MoonBrightness.DIM, MoonBrightness.NEW)) {
            recs.add("Be extra careful during New Moon periods")
            recs.add("Shukla Paksha (waxing Moon) is better for important activities")
        }

        if (moon.isDebilitated) {
            recs.add("Moon debilitated - strengthen through charity and worship")
        }

        return recs
    }

    private fun buildFinancialRecommendations(status: KemadrumaStatus): List<String> {
        val recs = mutableListOf<String>()

        if (status.severity >= 3) {
            recs.add("Build emergency savings for unexpected expenses")
            recs.add("Avoid speculative investments")
            recs.add("Seek stable income sources")
        }

        if (status.severity >= 4) {
            recs.add("Consider financial planning consultation")
            recs.add("Avoid loans and debts during Moon periods")
        }

        return recs
    }

    private fun buildSocialRecommendations(status: KemadrumaStatus): List<String> {
        val recs = mutableListOf<String>()

        if (status.severity >= 3) {
            recs.add("Actively maintain family relationships")
            recs.add("Join community or spiritual groups")
            recs.add("Service to others reduces isolation effects")
        }

        if (status.severity >= 4) {
            recs.add("Prioritize relationship nurturing during Moon periods")
            recs.add("Seek emotional support networks")
        }

        return recs
    }

    /**
     * Generate comprehensive interpretation
     */
    private fun generateInterpretation(
        isFormed: Boolean,
        status: KemadrumaStatus,
        moon: MoonAnalysis,
        cancellations: List<KemadrumaBhanga>
    ): String {
        if (!isFormed) {
            return "Kemadruma Yoga is NOT present in this chart. The Moon has adequate planetary support, " +
                   "indicating good emotional stability and ability to receive help from others. " +
                   "Mental peace and financial support from family/friends is generally available."
        }

        return buildString {
            append("Kemadruma Yoga IS formed in this chart as the Moon lacks planetary support in adjacent houses. ")

            when (status) {
                KemadrumaStatus.FULLY_CANCELLED -> {
                    append("However, the yoga is FULLY CANCELLED due to strong cancellation factors. ")
                    append("The negative effects are largely neutralized. ")
                }
                KemadrumaStatus.MOSTLY_CANCELLED -> {
                    append("The yoga is MOSTLY cancelled with ${cancellations.size} cancellation factors. ")
                    append("Negative effects are significantly reduced but some sensitivity remains. ")
                }
                KemadrumaStatus.PARTIALLY_CANCELLED -> {
                    append("The yoga is PARTIALLY cancelled. ")
                    append("Some negative effects may manifest during challenging planetary periods. ")
                }
                KemadrumaStatus.WEAKLY_CANCELLED -> {
                    append("The yoga has WEAK cancellation. ")
                    append("Emotional and financial challenges may arise, especially during Moon periods. ")
                }
                KemadrumaStatus.ACTIVE_MODERATE -> {
                    append("The yoga is ACTIVE with moderate intensity. ")
                    append("Feelings of isolation, financial instability, and emotional sensitivity are likely. ")
                    append("Regular Moon remedies are recommended. ")
                }
                KemadrumaStatus.ACTIVE_SEVERE -> {
                    append("The yoga is SEVERELY ACTIVE with minimal cancellation. ")
                    append("Significant challenges in emotional wellbeing, finances, and social support may occur. ")
                    append("Strong remedial measures are essential. ")
                }
                else -> {}
            }

            // Moon analysis
            append("\n\nMoon is placed in ${moon.sign.displayName} (${moon.house}th house) in ${moon.nakshatra}. ")

            if (moon.isExalted) {
                append("Moon's exaltation provides inherent strength to handle challenges. ")
            } else if (moon.isDebilitated) {
                append("Moon's debilitation intensifies emotional sensitivity. Extra care needed. ")
            }

            append("The ${moon.paksha.name.lowercase()} paksha ${moon.brightness.name.lowercase()} Moon ")
            append("indicates ${if (moon.brightness.ordinal <= 1) "good" else "variable"} emotional resilience. ")

            // Cancellation summary
            if (cancellations.isNotEmpty()) {
                append("\n\nCancellation factors present: ")
                cancellations.take(3).forEach { append("${it.description}; ") }
                if (cancellations.size > 3) append("and ${cancellations.size - 3} more. ")
            }
        }
    }

    /**
     * Generate plain text report
     */
    fun generateReport(analysis: KemadrumaAnalysis, language: Language = Language.ENGLISH): String {
        return buildString {
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine("           KEMADRUMA YOGA ANALYSIS REPORT")
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine()
            appendLine("Formation Status: ${if (analysis.isKemadrumaFormed) "PRESENT" else "NOT PRESENT"}")
            appendLine("Effective Status: ${analysis.effectiveStatus.displayName}")
            appendLine("Cancellation Score: ${analysis.totalCancellationScore}/60")
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("MOON ANALYSIS")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("Sign: ${analysis.moonPosition.sign.displayName}")
            appendLine("House: ${analysis.moonPosition.house}")
            appendLine("Nakshatra: ${analysis.moonPosition.nakshatra}")
            appendLine("Paksha: ${analysis.moonPosition.paksha}")
            appendLine("Tithi: ${analysis.moonPosition.tithi}")
            appendLine("Brightness: ${analysis.moonPosition.brightness}")
            appendLine("Dispositor: ${analysis.moonPosition.dispositor.displayName} (${analysis.moonPosition.dispositorStrength})")
            appendLine()

            if (analysis.isKemadrumaFormed) {
                appendLine("─────────────────────────────────────────────────────────")
                appendLine("FORMATION DETAILS")
                appendLine("─────────────────────────────────────────────────────────")
                appendLine("2nd from Moon empty: ${analysis.formationDetails.hasSecondHouseEmpty}")
                appendLine("12th from Moon empty: ${analysis.formationDetails.hasTwelfthHouseEmpty}")
                appendLine("Moon without conjunction: ${analysis.formationDetails.planetsConjunctMoon.isEmpty()}")
                appendLine()

                if (analysis.cancellations.isNotEmpty()) {
                    appendLine("─────────────────────────────────────────────────────────")
                    appendLine("CANCELLATION FACTORS (Bhanga)")
                    appendLine("─────────────────────────────────────────────────────────")
                    analysis.cancellations.forEach { bhanga ->
                        appendLine("✓ ${bhanga.description} (Strength: ${bhanga.strength})")
                    }
                    appendLine()
                }
            }

            appendLine("─────────────────────────────────────────────────────────")
            appendLine("IMPACT ANALYSIS")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine()
            appendLine("Emotional Impact: ${analysis.emotionalImpact.overallLevel}")
            appendLine("  Mental Peace: ${analysis.emotionalImpact.mentalPeace}%")
            appendLine("  Emotional Stability: ${analysis.emotionalImpact.emotionalStability}%")
            appendLine("  Confidence: ${analysis.emotionalImpact.confidenceLevel}%")
            appendLine("  Anxiety Tendency: ${analysis.emotionalImpact.anxietyTendency}%")
            appendLine()
            appendLine("Financial Impact: ${analysis.financialImpact.overallLevel}")
            appendLine("  Wealth Retention: ${analysis.financialImpact.wealthRetention}%")
            appendLine("  Financial Stability: ${analysis.financialImpact.financialStability}%")
            appendLine()
            appendLine("Social Impact: ${analysis.socialImpact.overallLevel}")
            appendLine("  Family Support: ${analysis.socialImpact.familySupport}%")
            appendLine("  Friendship Quality: ${analysis.socialImpact.friendshipQuality}%")
            appendLine()

            if (analysis.activationPeriods.isNotEmpty()) {
                appendLine("─────────────────────────────────────────────────────────")
                appendLine("ACTIVATION PERIODS")
                appendLine("─────────────────────────────────────────────────────────")
                analysis.activationPeriods.forEach { period ->
                    appendLine("${period.planet.displayName} ${period.periodType}: ${period.reason}")
                }
                appendLine()
            }

            appendLine("─────────────────────────────────────────────────────────")
            appendLine("REMEDIES")
            appendLine("─────────────────────────────────────────────────────────")
            analysis.remedies.forEach { remedy ->
                val priorityIndicator = when (remedy.priority) {
                    1 -> "★★★"
                    2 -> "★★"
                    else -> "★"
                }
                appendLine("$priorityIndicator [${remedy.category}] ${remedy.description}")
                appendLine("   Timing: ${remedy.timing}")
            }
            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("INTERPRETATION")
            appendLine("─────────────────────────────────────────────────────────")
            appendLine(analysis.interpretation)
        }
    }
}
