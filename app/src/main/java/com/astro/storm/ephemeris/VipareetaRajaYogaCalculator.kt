package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign

/**
 * VipareetaRajaYogaCalculator - Vipareeta (Reverse) Raja Yoga Analysis
 *
 * In Vedic astrology, Vipareeta Raja Yoga is a special category of Raja Yoga
 * that forms when dusthana (evil house) lords occupy other dusthana houses.
 * The principle is "negative times negative equals positive" - when lords of
 * houses of difficulties occupy houses of difficulties, the native benefits
 * from others' misfortunes or rises through unconventional means.
 *
 * Three primary Vipareeta Raja Yogas:
 * 1. HARSHA YOGA - 6th lord in 6th, 8th, or 12th house
 *    - Victory over enemies, good health despite odds
 *
 * 2. SARALA YOGA - 8th lord in 6th, 8th, or 12th house
 *    - Freedom from fear, longevity, occult success
 *
 * 3. VIMALA YOGA - 12th lord in 6th, 8th, or 12th house
 *    - Spending capacity, spiritual growth, liberation
 *
 * This calculator provides:
 * 1. Detection of all three Vipareeta Raja Yogas
 * 2. Yoga strength assessment
 * 3. Activation conditions analysis
 * 4. Dasha period effects
 * 5. House significations affected
 * 6. Interpretation and life areas benefited
 *
 * Traditional References:
 * - Brihat Parashara Hora Shastra (Chapter on Raja Yogas)
 * - Phaladeepika
 * - Uttara Kalamrita
 * - Jataka Parijata
 */
object VipareetaRajaYogaCalculator {

    /**
     * Types of Vipareeta Raja Yoga
     */
    enum class VipareetaYogaType(
        val displayName: String,
        val sanskritName: String,
        val houseLord: Int,
        val description: String
    ) {
        HARSHA(
            "Harsha Yoga",
            "हर्ष योग",
            6,
            "6th lord in dusthana - Victory over enemies, good health, happiness from overcoming obstacles"
        ),
        SARALA(
            "Sarala Yoga",
            "सरल योग",
            8,
            "8th lord in dusthana - Fearlessness, longevity, gains through inheritance, occult knowledge"
        ),
        VIMALA(
            "Vimala Yoga",
            "विमल योग",
            12,
            "12th lord in dusthana - Reduced losses, spiritual advancement, liberation, fame after death"
        )
    }

    /**
     * Strength level of the yoga
     */
    enum class YogaStrength(val displayName: String, val score: Int) {
        EXCEPTIONAL("Exceptional", 5),
        STRONG("Strong", 4),
        MODERATE("Moderate", 3),
        MILD("Mild", 2),
        WEAK("Weak", 1),
        NOT_FORMED("Not Formed", 0)
    }

    /**
     * Activation status of the yoga
     */
    enum class ActivationStatus(val displayName: String) {
        FULLY_ACTIVE("Fully Active"),
        PARTIALLY_ACTIVE("Partially Active"),
        LATENT("Latent - Awaiting Activation"),
        DORMANT("Dormant"),
        CANCELLED("Cancelled by Afflictions")
    }

    /**
     * Data class representing a single Vipareeta Raja Yoga
     */
    data class VipareetaYoga(
        val yogaType: VipareetaYogaType,
        val isFormed: Boolean,
        val strength: YogaStrength,
        val activationStatus: ActivationStatus,
        val dusthanaLord: Planet,
        val dusthanaLordPosition: PlanetPosition,
        val placedInHouse: Int,
        val placedInSign: ZodiacSign,
        val isRetrograde: Boolean,
        val isExalted: Boolean,
        val isDebilitated: Boolean,
        val isCombust: Boolean,
        val strengthFactors: List<String>,
        val weaknessFactors: List<String>,
        val benefitsAreas: List<String>,
        val activationDashas: List<Planet>,
        val interpretation: String
    )

    /**
     * Combined analysis of mutual dusthana lord exchange
     */
    data class DusthanaExchange(
        val planet1: Planet,
        val planet2: Planet,
        val house1: Int,
        val house2: Int,
        val isParivartana: Boolean,
        val effect: String,
        val strength: YogaStrength
    )

    /**
     * Comprehensive Vipareeta Raja Yoga analysis result
     */
    data class VipareetaRajaYogaAnalysis(
        val ascendantSign: ZodiacSign,
        val sixthLord: Planet,
        val eighthLord: Planet,
        val twelfthLord: Planet,
        val harshaYoga: VipareetaYoga,
        val saralaYoga: VipareetaYoga,
        val vimalaYoga: VipareetaYoga,
        val dusthanaExchanges: List<DusthanaExchange>,
        val totalYogasFormed: Int,
        val overallStrength: YogaStrength,
        val primaryBenefits: List<String>,
        val activationTimeline: List<ActivationPeriod>,
        val cancellationFactors: List<String>,
        val enhancementFactors: List<String>,
        val summary: String,
        val detailedInterpretation: String
    )

    /**
     * Period when yogas become active
     */
    data class ActivationPeriod(
        val planet: Planet,
        val yogaType: VipareetaYogaType?,
        val description: String,
        val expectedEffects: List<String>
    )

    /**
     * Main analysis function - analyzes all Vipareeta Raja Yogas
     */
    fun analyzeVipareetaRajaYogas(chart: VedicChart): VipareetaRajaYogaAnalysis? {
        if (chart.planetPositions.isEmpty()) return null

        val ascendantSign = ZodiacSign.fromLongitude(chart.ascendant)

        // Get dusthana lords
        val sixthLord = getHouseLord(ascendantSign, 6)
        val eighthLord = getHouseLord(ascendantSign, 8)
        val twelfthLord = getHouseLord(ascendantSign, 12)

        // Analyze each Vipareeta Raja Yoga
        val harshaYoga = analyzeYoga(chart, VipareetaYogaType.HARSHA, sixthLord, ascendantSign)
        val saralaYoga = analyzeYoga(chart, VipareetaYogaType.SARALA, eighthLord, ascendantSign)
        val vimalaYoga = analyzeYoga(chart, VipareetaYogaType.VIMALA, twelfthLord, ascendantSign)

        // Check for dusthana lord exchanges (Parivartana)
        val dusthanaExchanges = analyzeDusthanaExchanges(chart, ascendantSign, sixthLord, eighthLord, twelfthLord)

        // Count formed yogas
        val formedYogas = listOf(harshaYoga, saralaYoga, vimalaYoga).filter { it.isFormed }
        val totalYogasFormed = formedYogas.size

        // Calculate overall strength
        val overallStrength = calculateOverallStrength(formedYogas, dusthanaExchanges)

        // Identify primary benefits
        val primaryBenefits = identifyPrimaryBenefits(formedYogas)

        // Create activation timeline
        val activationTimeline = createActivationTimeline(formedYogas, chart)

        // Identify cancellation and enhancement factors
        val cancellationFactors = identifyCancellationFactors(chart, formedYogas, ascendantSign)
        val enhancementFactors = identifyEnhancementFactors(chart, formedYogas, ascendantSign)

        // Generate interpretations
        val summary = generateSummary(totalYogasFormed, overallStrength, formedYogas)
        val detailedInterpretation = generateDetailedInterpretation(
            chart, ascendantSign, harshaYoga, saralaYoga, vimalaYoga,
            dusthanaExchanges, cancellationFactors, enhancementFactors
        )

        return VipareetaRajaYogaAnalysis(
            ascendantSign = ascendantSign,
            sixthLord = sixthLord,
            eighthLord = eighthLord,
            twelfthLord = twelfthLord,
            harshaYoga = harshaYoga,
            saralaYoga = saralaYoga,
            vimalaYoga = vimalaYoga,
            dusthanaExchanges = dusthanaExchanges,
            totalYogasFormed = totalYogasFormed,
            overallStrength = overallStrength,
            primaryBenefits = primaryBenefits,
            activationTimeline = activationTimeline,
            cancellationFactors = cancellationFactors,
            enhancementFactors = enhancementFactors,
            summary = summary,
            detailedInterpretation = detailedInterpretation
        )
    }

    /**
     * Analyze a specific Vipareeta Raja Yoga
     */
    private fun analyzeYoga(
        chart: VedicChart,
        yogaType: VipareetaYogaType,
        dusthanaLord: Planet,
        ascendantSign: ZodiacSign
    ): VipareetaYoga {
        val lordPosition = chart.planetPositions.find { it.planet == dusthanaLord }

        if (lordPosition == null) {
            return createDefaultYoga(yogaType, dusthanaLord)
        }

        // Check if lord is in a dusthana house (6, 8, or 12)
        val isInDusthana = lordPosition.house in VedicAstrologyUtils.DUSTHANA_HOUSES

        val isExalted = VedicAstrologyUtils.isExalted(lordPosition)
        val isDebilitated = VedicAstrologyUtils.isDebilitated(lordPosition)
        val isCombust = checkCombustion(chart, lordPosition)

        // Calculate strength factors
        val strengthFactors = mutableListOf<String>()
        val weaknessFactors = mutableListOf<String>()

        if (isInDusthana) {
            strengthFactors.add("Dusthana lord placed in dusthana house (primary condition met)")
        }

        if (isExalted) {
            strengthFactors.add("Dusthana lord is exalted - strong results")
        }

        if (VedicAstrologyUtils.isInOwnSign(lordPosition)) {
            strengthFactors.add("Dusthana lord in own sign - stable results")
        }

        if (lordPosition.isRetrograde) {
            strengthFactors.add("Retrograde position intensifies yoga effects")
        }

        // Check if receiving Jupiter's aspect
        val jupiterPos = chart.planetPositions.find { it.planet == Planet.JUPITER }
        jupiterPos?.let { jupiter ->
            val jupiterAspects = getAspectedHouses(jupiter)
            if (lordPosition.house in jupiterAspects) {
                strengthFactors.add("Jupiter's aspect enhances benefic potential")
            }
        }

        // Weakness factors
        if (isDebilitated) {
            weaknessFactors.add("Debilitated dusthana lord - weakened results")
        }

        if (isCombust) {
            weaknessFactors.add("Combust position reduces yoga strength")
        }

        // Check if afflicted by natural malefics
        val conjunctMalefics = chart.planetPositions.filter {
            it.house == lordPosition.house &&
            it.planet != dusthanaLord &&
            VedicAstrologyUtils.isNaturalMalefic(it.planet)
        }
        if (conjunctMalefics.isNotEmpty()) {
            weaknessFactors.add("Afflicted by malefic conjunction")
        }

        // Calculate yoga strength
        val strength = calculateYogaStrength(
            isInDusthana, isExalted, isDebilitated, isCombust,
            lordPosition.isRetrograde, strengthFactors.size, weaknessFactors.size
        )

        // Determine activation status
        val activationStatus = determineActivationStatus(
            isInDusthana, strength, weaknessFactors
        )

        // Get benefits areas
        val benefitsAreas = getBenefitsAreas(yogaType, lordPosition.house)

        // Get activation dashas
        val activationDashas = getActivationDashas(dusthanaLord, chart, ascendantSign)

        // Build interpretation
        val interpretation = buildYogaInterpretation(
            yogaType, isInDusthana, strength, lordPosition, strengthFactors, weaknessFactors
        )

        return VipareetaYoga(
            yogaType = yogaType,
            isFormed = isInDusthana,
            strength = strength,
            activationStatus = activationStatus,
            dusthanaLord = dusthanaLord,
            dusthanaLordPosition = lordPosition,
            placedInHouse = lordPosition.house,
            placedInSign = lordPosition.sign,
            isRetrograde = lordPosition.isRetrograde,
            isExalted = isExalted,
            isDebilitated = isDebilitated,
            isCombust = isCombust,
            strengthFactors = strengthFactors,
            weaknessFactors = weaknessFactors,
            benefitsAreas = benefitsAreas,
            activationDashas = activationDashas,
            interpretation = interpretation
        )
    }

    /**
     * Create default yoga when position not found
     */
    private fun createDefaultYoga(
        yogaType: VipareetaYogaType,
        dusthanaLord: Planet
    ): VipareetaYoga {
        return VipareetaYoga(
            yogaType = yogaType,
            isFormed = false,
            strength = YogaStrength.NOT_FORMED,
            activationStatus = ActivationStatus.DORMANT,
            dusthanaLord = dusthanaLord,
            dusthanaLordPosition = PlanetPosition(
                planet = dusthanaLord,
                longitude = 0.0,
                latitude = 0.0,
                distance = 0.0,
                speed = 0.0,
                sign = ZodiacSign.ARIES,
                degree = 0.0,
                minutes = 0.0,
                seconds = 0.0,
                isRetrograde = false,
                nakshatra = com.astro.storm.data.model.Nakshatra.ASHWINI,
                nakshatraPada = 1,
                house = 1
            ),
            placedInHouse = 0,
            placedInSign = ZodiacSign.ARIES,
            isRetrograde = false,
            isExalted = false,
            isDebilitated = false,
            isCombust = false,
            strengthFactors = emptyList(),
            weaknessFactors = listOf("Position data unavailable"),
            benefitsAreas = emptyList(),
            activationDashas = emptyList(),
            interpretation = "${yogaType.displayName} - ${dusthanaLord.displayName} position unavailable"
        )
    }

    /**
     * Calculate yoga strength score
     */
    private fun calculateYogaStrength(
        isInDusthana: Boolean,
        isExalted: Boolean,
        isDebilitated: Boolean,
        isCombust: Boolean,
        isRetrograde: Boolean,
        strengthFactorCount: Int,
        weaknessFactorCount: Int
    ): YogaStrength {
        if (!isInDusthana) return YogaStrength.NOT_FORMED

        var score = 3.0  // Base score for yoga formation

        // Positive factors
        if (isExalted) score += 1.5
        if (isRetrograde) score += 0.5
        score += strengthFactorCount * 0.3

        // Negative factors
        if (isDebilitated) score -= 1.5
        if (isCombust) score -= 1.0
        score -= weaknessFactorCount * 0.3

        return when {
            score >= 5.0 -> YogaStrength.EXCEPTIONAL
            score >= 4.0 -> YogaStrength.STRONG
            score >= 3.0 -> YogaStrength.MODERATE
            score >= 2.0 -> YogaStrength.MILD
            score > 0 -> YogaStrength.WEAK
            else -> YogaStrength.NOT_FORMED
        }
    }

    /**
     * Determine activation status
     */
    private fun determineActivationStatus(
        isInDusthana: Boolean,
        strength: YogaStrength,
        weaknessFactors: List<String>
    ): ActivationStatus {
        if (!isInDusthana) return ActivationStatus.DORMANT

        val hasCancellation = weaknessFactors.any {
            it.contains("Debilitated") || it.contains("Afflicted")
        }

        return when {
            hasCancellation && weaknessFactors.size >= 3 -> ActivationStatus.CANCELLED
            strength == YogaStrength.EXCEPTIONAL || strength == YogaStrength.STRONG -> ActivationStatus.FULLY_ACTIVE
            strength == YogaStrength.MODERATE -> ActivationStatus.PARTIALLY_ACTIVE
            strength == YogaStrength.MILD || strength == YogaStrength.WEAK -> ActivationStatus.LATENT
            else -> ActivationStatus.DORMANT
        }
    }

    /**
     * Get benefits areas based on yoga type and house placement
     */
    private fun getBenefitsAreas(
        yogaType: VipareetaYogaType,
        placedInHouse: Int
    ): List<String> {
        val areas = mutableListOf<String>()

        // Yoga-specific benefits
        when (yogaType) {
            VipareetaYogaType.HARSHA -> {
                areas.add("Victory over enemies and competitors")
                areas.add("Good health despite challenging health indications")
                areas.add("Success in litigation and legal matters")
                areas.add("Overcoming debts and financial obligations")
                areas.add("Happiness through service and daily work")
            }
            VipareetaYogaType.SARALA -> {
                areas.add("Freedom from fear and anxiety")
                areas.add("Potential for longevity")
                areas.add("Gains through inheritance and legacy")
                areas.add("Success in occult and esoteric studies")
                areas.add("Transformation through difficulties")
                areas.add("Research and investigative abilities")
            }
            VipareetaYogaType.VIMALA -> {
                areas.add("Reduced expenditure and losses")
                areas.add("Spiritual advancement and moksha")
                areas.add("Success in foreign lands")
                areas.add("Liberation from bondage")
                areas.add("Fame and recognition after death")
                areas.add("Success through charitable activities")
            }
        }

        // House placement modifications
        when (placedInHouse) {
            6 -> areas.add("Benefits manifest through overcoming obstacles and service")
            8 -> areas.add("Benefits manifest through transformation and hidden matters")
            12 -> areas.add("Benefits manifest through spiritual pursuits and foreign connections")
        }

        return areas.take(5)
    }

    /**
     * Get planets whose Dashas will activate the yoga
     */
    private fun getActivationDashas(
        dusthanaLord: Planet,
        chart: VedicChart,
        ascendantSign: ZodiacSign
    ): List<Planet> {
        val activatingPlanets = mutableListOf<Planet>()

        // Primary: The dusthana lord's own Dasha
        activatingPlanets.add(dusthanaLord)

        // Planets conjunct with dusthana lord
        val dusthanaLordPos = chart.planetPositions.find { it.planet == dusthanaLord }
        dusthanaLordPos?.let { pos ->
            chart.planetPositions.filter {
                it.house == pos.house && it.planet != dusthanaLord
            }.forEach {
                activatingPlanets.add(it.planet)
            }

            // Planets aspecting the dusthana lord
            chart.planetPositions.forEach { otherPos ->
                val aspects = getAspectedHouses(otherPos)
                if (pos.house in aspects && otherPos.planet !in activatingPlanets) {
                    activatingPlanets.add(otherPos.planet)
                }
            }
        }

        // Nakshatra lord of dusthana lord
        dusthanaLordPos?.nakshatra?.ruler?.let { ruler ->
            if (ruler !in activatingPlanets) {
                activatingPlanets.add(ruler)
            }
        }

        return activatingPlanets.distinct().take(5)
    }

    /**
     * Analyze exchanges between dusthana lords (Parivartana)
     */
    private fun analyzeDusthanaExchanges(
        chart: VedicChart,
        ascendantSign: ZodiacSign,
        sixthLord: Planet,
        eighthLord: Planet,
        twelfthLord: Planet
    ): List<DusthanaExchange> {
        val exchanges = mutableListOf<DusthanaExchange>()

        val sixthLordPos = chart.planetPositions.find { it.planet == sixthLord }
        val eighthLordPos = chart.planetPositions.find { it.planet == eighthLord }
        val twelfthLordPos = chart.planetPositions.find { it.planet == twelfthLord }

        // Check 6th-8th exchange
        if (sixthLordPos != null && eighthLordPos != null) {
            if (sixthLordPos.house == 8 && eighthLordPos.house == 6) {
                exchanges.add(
                    DusthanaExchange(
                        planet1 = sixthLord,
                        planet2 = eighthLord,
                        house1 = 6,
                        house2 = 8,
                        isParivartana = true,
                        effect = "6th-8th lord exchange creates powerful Vipareeta yoga - enemies face sudden downfall, native benefits from others' losses",
                        strength = YogaStrength.STRONG
                    )
                )
            }
        }

        // Check 6th-12th exchange
        if (sixthLordPos != null && twelfthLordPos != null) {
            if (sixthLordPos.house == 12 && twelfthLordPos.house == 6) {
                exchanges.add(
                    DusthanaExchange(
                        planet1 = sixthLord,
                        planet2 = twelfthLord,
                        house1 = 6,
                        house2 = 12,
                        isParivartana = true,
                        effect = "6th-12th lord exchange - enemies are neutralized, losses become gains through service",
                        strength = YogaStrength.STRONG
                    )
                )
            }
        }

        // Check 8th-12th exchange
        if (eighthLordPos != null && twelfthLordPos != null) {
            if (eighthLordPos.house == 12 && twelfthLordPos.house == 8) {
                exchanges.add(
                    DusthanaExchange(
                        planet1 = eighthLord,
                        planet2 = twelfthLord,
                        house1 = 8,
                        house2 = 12,
                        isParivartana = true,
                        effect = "8th-12th lord exchange - transformation leads to liberation, hidden knowledge revealed",
                        strength = YogaStrength.STRONG
                    )
                )
            }
        }

        return exchanges
    }

    /**
     * Calculate overall strength from all formed yogas
     */
    private fun calculateOverallStrength(
        formedYogas: List<VipareetaYoga>,
        exchanges: List<DusthanaExchange>
    ): YogaStrength {
        if (formedYogas.isEmpty() && exchanges.isEmpty()) return YogaStrength.NOT_FORMED

        val yogaScores = formedYogas.map { it.strength.score }
        val exchangeScores = exchanges.map { it.strength.score }

        val maxScore = (yogaScores + exchangeScores).maxOrNull() ?: 0
        val avgScore = if (yogaScores.isNotEmpty() || exchangeScores.isNotEmpty()) {
            (yogaScores + exchangeScores).average()
        } else 0.0

        // Bonus for multiple yogas
        val multipleYogaBonus = when {
            formedYogas.size >= 3 -> 1.5
            formedYogas.size == 2 -> 0.5
            else -> 0.0
        }

        val finalScore = (maxScore * 0.6 + avgScore * 0.4 + multipleYogaBonus)

        return when {
            finalScore >= 4.5 -> YogaStrength.EXCEPTIONAL
            finalScore >= 3.5 -> YogaStrength.STRONG
            finalScore >= 2.5 -> YogaStrength.MODERATE
            finalScore >= 1.5 -> YogaStrength.MILD
            finalScore > 0 -> YogaStrength.WEAK
            else -> YogaStrength.NOT_FORMED
        }
    }

    /**
     * Identify primary benefits from formed yogas
     */
    private fun identifyPrimaryBenefits(formedYogas: List<VipareetaYoga>): List<String> {
        val benefits = mutableListOf<String>()

        formedYogas.forEach { yoga ->
            when (yoga.yogaType) {
                VipareetaYogaType.HARSHA -> {
                    benefits.add("Success over adversaries and competition")
                    benefits.add("Resilience in health matters")
                }
                VipareetaYogaType.SARALA -> {
                    benefits.add("Fearlessness and courage in difficult situations")
                    benefits.add("Potential gains through inheritance or transformation")
                }
                VipareetaYogaType.VIMALA -> {
                    benefits.add("Spiritual progress and inner peace")
                    benefits.add("Success in foreign lands or isolated environments")
                }
            }
        }

        if (formedYogas.size >= 2) {
            benefits.add("Multiple Vipareeta yogas - compound beneficial effects")
        }

        if (formedYogas.size == 3) {
            benefits.add("All three Vipareeta yogas - rare and powerful combination for rising through difficulties")
        }

        return benefits.distinct().take(6)
    }

    /**
     * Create activation timeline
     */
    private fun createActivationTimeline(
        formedYogas: List<VipareetaYoga>,
        chart: VedicChart
    ): List<ActivationPeriod> {
        val periods = mutableListOf<ActivationPeriod>()

        formedYogas.forEach { yoga ->
            yoga.activationDashas.forEachIndexed { index, planet ->
                val periodType = when (index) {
                    0 -> "Primary activation"
                    1 -> "Secondary activation"
                    else -> "Supporting period"
                }

                periods.add(
                    ActivationPeriod(
                        planet = planet,
                        yogaType = yoga.yogaType,
                        description = "$periodType of ${yoga.yogaType.displayName} during ${planet.displayName} Dasha",
                        expectedEffects = getExpectedEffectsForPeriod(yoga, planet)
                    )
                )
            }
        }

        return periods.distinctBy { "${it.planet.name}_${it.yogaType?.name}" }.take(8)
    }

    /**
     * Get expected effects for activation period
     */
    private fun getExpectedEffectsForPeriod(yoga: VipareetaYoga, planet: Planet): List<String> {
        val effects = mutableListOf<String>()

        // Base yoga effects
        when (yoga.yogaType) {
            VipareetaYogaType.HARSHA -> {
                effects.add("Victory in competition")
                effects.add("Health improvements")
            }
            VipareetaYogaType.SARALA -> {
                effects.add("Inheritance or sudden gains")
                effects.add("Occult understanding")
            }
            VipareetaYogaType.VIMALA -> {
                effects.add("Spiritual advancement")
                effects.add("Foreign opportunities")
            }
        }

        // Planet-specific modifications
        when (planet) {
            Planet.JUPITER -> effects.add("Expansion and wisdom in manifestation")
            Planet.SATURN -> effects.add("Delayed but lasting results")
            Planet.MARS -> effects.add("Quick and decisive outcomes")
            Planet.VENUS -> effects.add("Comfort and pleasure from results")
            Planet.MERCURY -> effects.add("Intellectual and business benefits")
            else -> {}
        }

        return effects.take(3)
    }

    /**
     * Identify factors that could cancel the yoga
     */
    private fun identifyCancellationFactors(
        chart: VedicChart,
        formedYogas: List<VipareetaYoga>,
        ascendantSign: ZodiacSign
    ): List<String> {
        val factors = mutableListOf<String>()

        formedYogas.forEach { yoga ->
            // Debilitation is a major cancellation
            if (yoga.isDebilitated) {
                factors.add("${yoga.yogaType.displayName}: ${yoga.dusthanaLord.displayName} is debilitated - significantly weakens yoga")
            }

            // Combustion weakens
            if (yoga.isCombust) {
                factors.add("${yoga.yogaType.displayName}: ${yoga.dusthanaLord.displayName} is combust - reduces yoga potency")
            }

            // Check if aspected by functional malefics
            val maraka2ndLord = getHouseLord(ascendantSign, 2)
            val maraka7thLord = getHouseLord(ascendantSign, 7)

            val dusthanaLordPos = yoga.dusthanaLordPosition
            chart.planetPositions.forEach { pos ->
                val aspects = getAspectedHouses(pos)
                if (dusthanaLordPos.house in aspects) {
                    if (pos.planet == maraka2ndLord || pos.planet == maraka7thLord) {
                        factors.add("${yoga.yogaType.displayName}: Aspected by Maraka lord ${pos.planet.displayName}")
                    }
                }
            }
        }

        // Check if dusthana lords are in Kendra from each other (can reduce effect)
        val sixthLordHouse = chart.planetPositions.find {
            it.planet == getHouseLord(ascendantSign, 6)
        }?.house ?: 0
        val eighthLordHouse = chart.planetPositions.find {
            it.planet == getHouseLord(ascendantSign, 8)
        }?.house ?: 0

        if (sixthLordHouse > 0 && eighthLordHouse > 0) {
            val difference = kotlin.math.abs(sixthLordHouse - eighthLordHouse)
            if (difference in listOf(1, 4, 7, 10) || (12 - difference) in listOf(1, 4, 7, 10)) {
                // Actually being in dusthana from each other is good, this check is for other cases
            }
        }

        return factors.distinct().take(5)
    }

    /**
     * Identify factors that enhance the yoga
     */
    private fun identifyEnhancementFactors(
        chart: VedicChart,
        formedYogas: List<VipareetaYoga>,
        ascendantSign: ZodiacSign
    ): List<String> {
        val factors = mutableListOf<String>()

        // Jupiter's beneficial aspect
        val jupiterPos = chart.planetPositions.find { it.planet == Planet.JUPITER }
        jupiterPos?.let { jupiter ->
            val jupiterAspects = getAspectedHouses(jupiter)
            formedYogas.forEach { yoga ->
                if (yoga.dusthanaLordPosition.house in jupiterAspects) {
                    factors.add("Jupiter's aspect on ${yoga.dusthanaLord.displayName} enhances ${yoga.yogaType.displayName}")
                }
            }
        }

        // Strong Lagna lord
        val lagnaLord = ascendantSign.ruler
        val lagnaLordPos = chart.planetPositions.find { it.planet == lagnaLord }
        lagnaLordPos?.let { pos ->
            if (VedicAstrologyUtils.isExalted(pos) || VedicAstrologyUtils.isInOwnSign(pos)) {
                factors.add("Strong Lagna lord supports yoga manifestation")
            }
        }

        // Multiple yogas formed
        if (formedYogas.size >= 2) {
            factors.add("Multiple Vipareeta yogas reinforce each other")
        }

        if (formedYogas.size == 3) {
            factors.add("All three yogas present - exceptional configuration for rising through adversity")
        }

        // Exalted dusthana lords
        formedYogas.filter { it.isExalted }.forEach { yoga ->
            factors.add("${yoga.dusthanaLord.displayName} exalted - ${yoga.yogaType.displayName} gives exceptional results")
        }

        // Benefics in Kendra
        val beneficsInKendra = chart.planetPositions.filter {
            it.house in VedicAstrologyUtils.KENDRA_HOUSES && VedicAstrologyUtils.isNaturalBenefic(it.planet)
        }
        if (beneficsInKendra.isNotEmpty()) {
            factors.add("Benefics in Kendra support overall chart strength and yoga results")
        }

        return factors.distinct().take(5)
    }

    /**
     * Build yoga-specific interpretation
     */
    private fun buildYogaInterpretation(
        yogaType: VipareetaYogaType,
        isFormed: Boolean,
        strength: YogaStrength,
        position: PlanetPosition,
        strengthFactors: List<String>,
        weaknessFactors: List<String>
    ): String {
        val sb = StringBuilder()

        if (!isFormed) {
            sb.append("${yogaType.displayName} is NOT formed. ")
            sb.append("The ${yogaType.houseLord}th lord ${position.planet.displayName} is in house ${position.house}, ")
            sb.append("not in a dusthana house (6th, 8th, or 12th).")
            return sb.toString()
        }

        sb.append("${yogaType.displayName} (${yogaType.sanskritName}) is FORMED. ")
        sb.append("${position.planet.displayName}, lord of the ${yogaType.houseLord}th house, ")
        sb.append("is placed in the ${position.house}th house in ${position.sign.displayName}. ")

        sb.append("Yoga strength: ${strength.displayName}. ")

        when (yogaType) {
            VipareetaYogaType.HARSHA -> {
                sb.append("This yoga grants victory over enemies, good health despite odds, ")
                sb.append("and success in overcoming obstacles. ")
            }
            VipareetaYogaType.SARALA -> {
                sb.append("This yoga bestows fearlessness, longevity, ")
                sb.append("gains through inheritance, and success in occult studies. ")
            }
            VipareetaYogaType.VIMALA -> {
                sb.append("This yoga brings reduced losses, spiritual advancement, ")
                sb.append("success in foreign lands, and ultimate liberation. ")
            }
        }

        if (strengthFactors.isNotEmpty()) {
            sb.append("Enhancing factors: ${strengthFactors.joinToString("; ")}. ")
        }

        if (weaknessFactors.isNotEmpty()) {
            sb.append("Limiting factors: ${weaknessFactors.joinToString("; ")}. ")
        }

        return sb.toString()
    }

    /**
     * Generate summary text
     */
    private fun generateSummary(
        totalYogasFormed: Int,
        overallStrength: YogaStrength,
        formedYogas: List<VipareetaYoga>
    ): String {
        return when (totalYogasFormed) {
            0 -> "No Vipareeta Raja Yogas formed in this chart."
            1 -> {
                val yoga = formedYogas.first()
                "${yoga.yogaType.displayName} is present with ${yoga.strength.displayName.lowercase()} strength."
            }
            2 -> {
                val names = formedYogas.joinToString(" and ") { it.yogaType.displayName }
                "Two Vipareeta Raja Yogas: $names. Overall strength: ${overallStrength.displayName}."
            }
            3 -> "All three Vipareeta Raja Yogas (Harsha, Sarala, Vimala) are present - a rare and powerful combination! Overall strength: ${overallStrength.displayName}."
            else -> "Vipareeta Raja Yoga analysis complete."
        }
    }

    /**
     * Generate detailed interpretation
     */
    private fun generateDetailedInterpretation(
        chart: VedicChart,
        ascendantSign: ZodiacSign,
        harshaYoga: VipareetaYoga,
        saralaYoga: VipareetaYoga,
        vimalaYoga: VipareetaYoga,
        exchanges: List<DusthanaExchange>,
        cancellationFactors: List<String>,
        enhancementFactors: List<String>
    ): String {
        val sb = StringBuilder()

        sb.appendLine("=== Vipareeta Raja Yoga Detailed Analysis ===")
        sb.appendLine()

        // Overview
        sb.appendLine("OVERVIEW:")
        sb.appendLine("Ascendant: ${ascendantSign.displayName}")
        sb.appendLine("6th Lord: ${harshaYoga.dusthanaLord.displayName}")
        sb.appendLine("8th Lord: ${saralaYoga.dusthanaLord.displayName}")
        sb.appendLine("12th Lord: ${vimalaYoga.dusthanaLord.displayName}")
        sb.appendLine()

        // Harsha Yoga
        sb.appendLine("HARSHA YOGA (6th Lord Analysis):")
        sb.appendLine(harshaYoga.interpretation)
        if (harshaYoga.isFormed && harshaYoga.benefitsAreas.isNotEmpty()) {
            sb.appendLine("Benefits: ${harshaYoga.benefitsAreas.joinToString(", ")}")
        }
        sb.appendLine()

        // Sarala Yoga
        sb.appendLine("SARALA YOGA (8th Lord Analysis):")
        sb.appendLine(saralaYoga.interpretation)
        if (saralaYoga.isFormed && saralaYoga.benefitsAreas.isNotEmpty()) {
            sb.appendLine("Benefits: ${saralaYoga.benefitsAreas.joinToString(", ")}")
        }
        sb.appendLine()

        // Vimala Yoga
        sb.appendLine("VIMALA YOGA (12th Lord Analysis):")
        sb.appendLine(vimalaYoga.interpretation)
        if (vimalaYoga.isFormed && vimalaYoga.benefitsAreas.isNotEmpty()) {
            sb.appendLine("Benefits: ${vimalaYoga.benefitsAreas.joinToString(", ")}")
        }
        sb.appendLine()

        // Dusthana Exchanges
        if (exchanges.isNotEmpty()) {
            sb.appendLine("DUSTHANA LORD EXCHANGES (Parivartana):")
            exchanges.forEach { exchange ->
                sb.appendLine("• ${exchange.planet1.displayName} ↔ ${exchange.planet2.displayName}: ${exchange.effect}")
            }
            sb.appendLine()
        }

        // Enhancement Factors
        if (enhancementFactors.isNotEmpty()) {
            sb.appendLine("ENHANCING FACTORS:")
            enhancementFactors.forEach { factor ->
                sb.appendLine("✓ $factor")
            }
            sb.appendLine()
        }

        // Cancellation Factors
        if (cancellationFactors.isNotEmpty()) {
            sb.appendLine("LIMITING/CANCELLATION FACTORS:")
            cancellationFactors.forEach { factor ->
                sb.appendLine("⚠ $factor")
            }
            sb.appendLine()
        }

        // Final Note
        sb.appendLine("NOTE:")
        sb.appendLine("Vipareeta Raja Yogas operate on the principle that the lord of a difficult house")
        sb.appendLine("placed in another difficult house cancels out negativity, creating positive outcomes.")
        sb.appendLine("These yogas often manifest as rising through challenges, benefiting from others'")
        sb.appendLine("misfortunes, or achieving success through unconventional means.")

        return sb.toString()
    }

    // ============================================
    // HELPER FUNCTIONS
    // ============================================

    /**
     * Get the lord of a house based on Ascendant
     */
    private fun getHouseLord(ascendant: ZodiacSign, house: Int): Planet {
        val houseSignIndex = (ascendant.ordinal + house - 1) % 12
        return ZodiacSign.entries[houseSignIndex].ruler
    }

    /**
     * Get houses aspected by a planet
     */
    private fun getAspectedHouses(pos: PlanetPosition): List<Int> {
        val aspects = mutableListOf<Int>()
        val planetHouse = pos.house

        // 7th aspect (all planets)
        aspects.add(((planetHouse + 6) % 12).let { if (it == 0) 12 else it })

        // Special aspects
        when (pos.planet) {
            Planet.MARS -> {
                aspects.add(((planetHouse + 3) % 12).let { if (it == 0) 12 else it })
                aspects.add(((planetHouse + 7) % 12).let { if (it == 0) 12 else it })
            }
            Planet.JUPITER -> {
                aspects.add(((planetHouse + 4) % 12).let { if (it == 0) 12 else it })
                aspects.add(((planetHouse + 8) % 12).let { if (it == 0) 12 else it })
            }
            Planet.SATURN -> {
                aspects.add(((planetHouse + 2) % 12).let { if (it == 0) 12 else it })
                aspects.add(((planetHouse + 9) % 12).let { if (it == 0) 12 else it })
            }
            Planet.RAHU, Planet.KETU -> {
                aspects.add(((planetHouse + 4) % 12).let { if (it == 0) 12 else it })
                aspects.add(((planetHouse + 8) % 12).let { if (it == 0) 12 else it })
            }
            else -> {}
        }

        return aspects
    }

    /**
     * Check if planet is combust
     */
    private fun checkCombustion(chart: VedicChart, pos: PlanetPosition): Boolean {
        if (pos.planet == Planet.SUN) return false

        val sunPos = chart.planetPositions.find { it.planet == Planet.SUN } ?: return false
        val distance = kotlin.math.abs(pos.longitude - sunPos.longitude)
        val normalizedDistance = if (distance > 180) 360 - distance else distance

        val combustionOrb = when (pos.planet) {
            Planet.MOON -> 12.0
            Planet.MARS -> 17.0
            Planet.MERCURY -> 14.0
            Planet.JUPITER -> 11.0
            Planet.VENUS -> 10.0
            Planet.SATURN -> 15.0
            else -> 0.0
        }

        return normalizedDistance <= combustionOrb
    }
}
