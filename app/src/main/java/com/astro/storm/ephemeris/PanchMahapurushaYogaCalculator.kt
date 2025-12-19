package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import kotlin.math.abs

/**
 * Panch Mahapurusha Yoga Calculator
 *
 * Detects and analyzes the five great yogas formed by Mars, Mercury, Jupiter, Venus, and Saturn
 * when placed in Kendra (1st, 4th, 7th, or 10th house) in their own or exaltation signs.
 *
 * The five Mahapurusha Yogas are:
 * 1. Ruchaka Yoga (Mars) - Courage, leadership, military prowess
 * 2. Bhadra Yoga (Mercury) - Intelligence, communication, commerce
 * 3. Hamsa Yoga (Jupiter) - Wisdom, spirituality, learning
 * 4. Malavya Yoga (Venus) - Beauty, luxury, arts, relationships
 * 5. Sasha Yoga (Saturn) - Discipline, authority, longevity
 *
 * References:
 * - Brihat Parashara Hora Shastra (BPHS) on Panch Mahapurusha Yoga
 * - Saravali by Kalyana Varma
 * - Phaladeepika by Mantreswara
 */
object PanchMahapurushaYogaCalculator {

    // Kendra houses (angular houses)
    private val KENDRA_HOUSES = setOf(1, 4, 7, 10)

    // Exaltation signs for each planet
    private val EXALTATION_SIGNS = mapOf(
        Planet.MARS to ZodiacSign.CAPRICORN,
        Planet.MERCURY to ZodiacSign.VIRGO,
        Planet.JUPITER to ZodiacSign.CANCER,
        Planet.VENUS to ZodiacSign.PISCES,
        Planet.SATURN to ZodiacSign.LIBRA
    )

    // Exaltation degrees for precise strength calculation
    private val EXALTATION_DEGREES = mapOf(
        Planet.MARS to 28.0,      // 28° Capricorn
        Planet.MERCURY to 15.0,   // 15° Virgo
        Planet.JUPITER to 5.0,    // 5° Cancer
        Planet.VENUS to 27.0,     // 27° Pisces
        Planet.SATURN to 20.0     // 20° Libra
    )

    // Own signs for each planet
    private val OWN_SIGNS = mapOf(
        Planet.MARS to setOf(ZodiacSign.ARIES, ZodiacSign.SCORPIO),
        Planet.MERCURY to setOf(ZodiacSign.GEMINI, ZodiacSign.VIRGO),
        Planet.JUPITER to setOf(ZodiacSign.SAGITTARIUS, ZodiacSign.PISCES),
        Planet.VENUS to setOf(ZodiacSign.TAURUS, ZodiacSign.LIBRA),
        Planet.SATURN to setOf(ZodiacSign.CAPRICORN, ZodiacSign.AQUARIUS)
    )

    /**
     * Main analysis entry point
     */
    fun analyzePanchMahapurushaYogas(chart: VedicChart): PanchMahapurushaAnalysis {
        val detectedYogas = mutableListOf<MahapurushaYoga>()

        // Check each of the 5 planets for Mahapurusha Yoga formation
        for (planet in listOf(Planet.MARS, Planet.MERCURY, Planet.JUPITER, Planet.VENUS, Planet.SATURN)) {
            val position = chart.planetPositions.find { it.planet == planet } ?: continue
            val yoga = checkMahapurushaYoga(planet, position, chart)
            if (yoga != null) {
                detectedYogas.add(yoga)
            }
        }

        val interpretation = generateOverallInterpretation(detectedYogas, chart)
        val combinedEffects = analyzeCombinedEffects(detectedYogas)
        val activationPeriods = calculateActivationPeriods(detectedYogas, chart)

        return PanchMahapurushaAnalysis(
            yogas = detectedYogas,
            hasAnyYoga = detectedYogas.isNotEmpty(),
            yogaCount = detectedYogas.size,
            strongestYoga = detectedYogas.maxByOrNull { it.strength },
            interpretation = interpretation,
            combinedEffects = combinedEffects,
            activationPeriods = activationPeriods,
            overallYogaStrength = calculateOverallStrength(detectedYogas)
        )
    }

    /**
     * Check if a specific planet forms Mahapurusha Yoga
     */
    private fun checkMahapurushaYoga(
        planet: Planet,
        position: PlanetPosition,
        chart: VedicChart
    ): MahapurushaYoga? {
        val house = position.house
        val sign = ZodiacSign.fromLongitude(position.longitude)

        // Check if in Kendra
        if (house !in KENDRA_HOUSES) return null

        // Check if in own sign or exaltation
        val isExalted = sign == EXALTATION_SIGNS[planet]
        val isOwnSign = sign in (OWN_SIGNS[planet] ?: emptySet())

        if (!isExalted && !isOwnSign) return null

        // Yoga is formed! Calculate details
        val yogaType = getYogaType(planet)
        val dignity = if (isExalted) PlanetDignity.EXALTED else PlanetDignity.OWN_SIGN
        val strength = calculateYogaStrength(planet, position, dignity, house, chart)
        val effects = generateYogaEffects(yogaType, strength, house, dignity)
        val remedies = generateRemedies(yogaType, strength)
        val interpretation = generateYogaInterpretation(yogaType, dignity, house, strength)

        return MahapurushaYoga(
            type = yogaType,
            planet = planet,
            house = house,
            sign = sign,
            dignity = dignity,
            strength = strength,
            strengthLevel = getStrengthLevel(strength),
            isExalted = isExalted,
            isOwnSign = isOwnSign,
            degreeFromExact = if (isExalted) calculateDegreeFromExact(position.longitude, planet) else null,
            effects = effects,
            interpretation = interpretation,
            recommendations = remedies,
            planetPosition = position
        )
    }

    /**
     * Get yoga type from planet
     */
    private fun getYogaType(planet: Planet): MahapurushaYogaType {
        return when (planet) {
            Planet.MARS -> MahapurushaYogaType.RUCHAKA
            Planet.MERCURY -> MahapurushaYogaType.BHADRA
            Planet.JUPITER -> MahapurushaYogaType.HAMSA
            Planet.VENUS -> MahapurushaYogaType.MALAVYA
            Planet.SATURN -> MahapurushaYogaType.SASHA
            else -> throw IllegalArgumentException("Invalid planet for Mahapurusha Yoga")
        }
    }

    /**
     * Calculate yoga strength (0-100)
     */
    private fun calculateYogaStrength(
        planet: Planet,
        position: PlanetPosition,
        dignity: PlanetDignity,
        house: Int,
        chart: VedicChart
    ): Int {
        var strength = 0

        // Base strength from dignity (40 points max)
        strength += when (dignity) {
            PlanetDignity.EXALTED -> 40
            PlanetDignity.OWN_SIGN -> 30
            else -> 0
        }

        // Strength from house placement (20 points max)
        // 1st and 10th houses give more strength than 4th and 7th
        strength += when (house) {
            1 -> 20
            10 -> 18
            7 -> 15
            4 -> 12
            else -> 0
        }

        // Strength from exact exaltation degree (15 points max)
        if (dignity == PlanetDignity.EXALTED) {
            val degreeFromExact = calculateDegreeFromExact(position.longitude, planet)
            if (degreeFromExact != null) {
                // Closer to exact degree = more strength
                val degreeFactor = (15 - degreeFromExact).coerceAtLeast(0.0)
                strength += degreeFactor.toInt()
            }
        }

        // Strength from aspects (15 points max)
        val aspectBonus = calculateAspectBonus(position, chart)
        strength += aspectBonus

        // Strength from not being combust or retrograde (10 points max)
        if (!position.isRetrograde) strength += 5
        if (!isComust(position, chart)) strength += 5

        return strength.coerceIn(0, 100)
    }

    /**
     * Calculate distance from exact exaltation degree
     */
    private fun calculateDegreeFromExact(longitude: Double, planet: Planet): Double? {
        val exaltationDegree = EXALTATION_DEGREES[planet] ?: return null
        val exaltationSign = EXALTATION_SIGNS[planet] ?: return null

        val signStart = exaltationSign.ordinal * 30.0
        val exactLongitude = signStart + exaltationDegree

        return abs(longitude - exactLongitude)
    }

    /**
     * Calculate bonus from benefic aspects
     */
    private fun calculateAspectBonus(position: PlanetPosition, chart: VedicChart): Int {
        var bonus = 0
        val benefics = listOf(Planet.JUPITER, Planet.VENUS, Planet.MERCURY)
        val malefics = listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU)

        for (otherPosition in chart.planetPositions) {
            if (otherPosition.planet == position.planet) continue

            val houseDiff = calculateHouseDifference(otherPosition.house, position.house)

            // Check for aspects (simplified: 1st, 5th, 7th, 9th aspects)
            val isAspecting = houseDiff in listOf(0, 4, 6, 8) ||
                              (otherPosition.planet == Planet.JUPITER && houseDiff in listOf(4, 6, 8)) ||
                              (otherPosition.planet == Planet.MARS && houseDiff in listOf(3, 6, 7)) ||
                              (otherPosition.planet == Planet.SATURN && houseDiff in listOf(2, 6, 9))

            if (isAspecting) {
                if (otherPosition.planet in benefics) {
                    bonus += 5
                } else if (otherPosition.planet in malefics) {
                    bonus -= 3
                }
            }
        }

        return bonus.coerceIn(0, 15)
    }

    private fun calculateHouseDifference(house1: Int, house2: Int): Int {
        val diff = house1 - house2
        return if (diff < 0) diff + 12 else diff
    }

    private fun isComust(position: PlanetPosition, chart: VedicChart): Boolean {
        val sunPosition = chart.planetPositions.find { it.planet == Planet.SUN } ?: return false
        val separation = abs(position.longitude - sunPosition.longitude)
        val normalizedSeparation = if (separation > 180) 360 - separation else separation

        // Combustion ranges vary by planet
        val combustionOrb = when (position.planet) {
            Planet.MARS -> 17.0
            Planet.MERCURY -> 14.0 // Can be as low as 12° when retrograde
            Planet.JUPITER -> 11.0
            Planet.VENUS -> 10.0
            Planet.SATURN -> 15.0
            else -> 10.0
        }

        return normalizedSeparation <= combustionOrb
    }

    private fun getStrengthLevel(strength: Int): YogaStrengthLevel {
        return when {
            strength >= 80 -> YogaStrengthLevel.EXCEPTIONAL
            strength >= 60 -> YogaStrengthLevel.STRONG
            strength >= 40 -> YogaStrengthLevel.MODERATE
            strength >= 20 -> YogaStrengthLevel.WEAK
            else -> YogaStrengthLevel.VERY_WEAK
        }
    }

    /**
     * Generate effects for a specific yoga
     */
    private fun generateYogaEffects(
        type: MahapurushaYogaType,
        strength: Int,
        house: Int,
        dignity: PlanetDignity
    ): YogaEffects {
        val (physical, mental, career, spiritual, relationships) = when (type) {
            MahapurushaYogaType.RUCHAKA -> listOf(
                "Strong physical constitution, athletic build, courage in action",
                "Fearless mindset, competitive spirit, quick decision-making",
                "Success in military, police, sports, surgery, engineering",
                "Warrior spirit, protection of dharma, courage in spiritual practice",
                "Protective of loved ones, passionate but sometimes aggressive"
            )
            MahapurushaYogaType.BHADRA -> listOf(
                "Youthful appearance, expressive gestures, quick movements",
                "Sharp intellect, excellent memory, witty communication",
                "Success in writing, teaching, commerce, accounting, IT",
                "Knowledge through study, intellectual approach to spirituality",
                "Communicative, adaptable, good at networking"
            )
            MahapurushaYogaType.HAMSA -> listOf(
                "Graceful bearing, fair complexion, pleasant appearance",
                "Wisdom, philosophical outlook, positive thinking",
                "Success in law, education, finance, consulting, priesthood",
                "Natural inclination toward spirituality, dharmic conduct",
                "Generous, supportive, good advisor in relationships"
            )
            MahapurushaYogaType.MALAVYA -> listOf(
                "Attractive appearance, pleasant features, artistic grace",
                "Refined sensibilities, appreciation of beauty, romantic nature",
                "Success in arts, entertainment, luxury goods, hospitality",
                "Devotional approach, appreciation of divine beauty",
                "Loving, romantic, harmony-seeking in relationships"
            )
            MahapurushaYogaType.SASHA -> listOf(
                "Lean build, serious demeanor, endurance and stamina",
                "Disciplined mind, methodical thinking, patience",
                "Success in construction, mining, government, management",
                "Detachment, karma yoga, service-oriented spirituality",
                "Loyal, responsible, may be reserved in expression"
            )
        }

        val strengthMultiplier = strength / 100.0
        val houseSpecificEffect = getHouseSpecificEffect(type, house)

        return YogaEffects(
            physicalTraits = physical,
            mentalTraits = mental,
            careerIndications = career,
            spiritualGrowth = spiritual,
            relationshipImpact = relationships,
            houseSpecificEffect = houseSpecificEffect,
            manifestationStrength = when {
                strengthMultiplier >= 0.8 -> "Results manifest prominently throughout life"
                strengthMultiplier >= 0.6 -> "Results manifest notably, especially during dasha periods"
                strengthMultiplier >= 0.4 -> "Results manifest moderately with conscious effort"
                else -> "Results manifest subtly, may need activation"
            }
        )
    }

    private fun getHouseSpecificEffect(type: MahapurushaYogaType, house: Int): String {
        return when (house) {
            1 -> "${type.displayName} in 1st house: Yoga effects directly impact personality, health, and overall life approach. Strong self-projection of ${type.planet.displayName}'s qualities."
            4 -> "${type.displayName} in 4th house: Benefits related to home, mother, vehicles, property, and inner peace. ${type.planet.displayName}'s blessings in domestic life."
            7 -> "${type.displayName} in 7th house: Strong impact on marriage, partnerships, business dealings. Spouse may have ${type.planet.displayName}'s characteristics."
            10 -> "${type.displayName} in 10th house: Career excellence, public recognition, authority. ${type.planet.displayName}'s qualities shine in professional life."
            else -> "General yoga effects apply"
        }
    }

    /**
     * Generate yoga interpretation
     */
    private fun generateYogaInterpretation(
        type: MahapurushaYogaType,
        dignity: PlanetDignity,
        house: Int,
        strength: Int
    ): String {
        val dignityText = if (dignity == PlanetDignity.EXALTED) "exalted" else "in its own sign"
        val houseText = when (house) {
            1 -> "Ascendant (1st house)"
            4 -> "4th house"
            7 -> "7th house"
            10 -> "10th house (Midheaven)"
            else -> "house $house"
        }

        val strengthText = when {
            strength >= 80 -> "This is an exceptionally powerful yoga that will significantly influence your life."
            strength >= 60 -> "This is a strong yoga with notable effects throughout life."
            strength >= 40 -> "This yoga has moderate strength and will manifest clearly during favorable periods."
            else -> "This yoga exists but may need activation through conscious effort or favorable transits."
        }

        return buildString {
            append("${type.displayName} is formed with ${type.planet.displayName} $dignityText in the $houseText.\n\n")
            append("${type.description}\n\n")
            append("With a strength of $strength%, $strengthText\n\n")
            append("Key blessings: ${type.primaryBlessings.joinToString(", ")}")
        }
    }

    /**
     * Generate recommendations for maximizing yoga benefits
     */
    private fun generateRemedies(type: MahapurushaYogaType, strength: Int): List<YogaRecommendation> {
        val recommendations = mutableListOf<YogaRecommendation>()

        // Strengthening recommendation
        recommendations.add(YogaRecommendation(
            category = RecommendationType.STRENGTHENING,
            action = "Worship ${type.deity} on ${type.planet.displayName}'s day (${getWeekday(type.planet)})",
            timing = "${getWeekday(type.planet)} mornings",
            benefit = "Activates and strengthens the yoga's positive effects"
        ))

        // Career alignment
        recommendations.add(YogaRecommendation(
            category = RecommendationType.CAREER,
            action = "Pursue careers in ${type.suitableCareers.take(3).joinToString(", ")}",
            timing = "During ${type.planet.displayName}'s dasha/antardasha periods",
            benefit = "Maximum success in areas aligned with the yoga"
        ))

        // Gemstone (if strength is moderate or lower)
        if (strength < 60) {
            recommendations.add(YogaRecommendation(
                category = RecommendationType.GEMSTONE,
                action = "Consider wearing ${type.gemstone} after proper consultation",
                timing = "During auspicious muhurta on ${getWeekday(type.planet)}",
                benefit = "Amplifies ${type.planet.displayName}'s positive significations"
            ))
        }

        // Mantra
        recommendations.add(YogaRecommendation(
            category = RecommendationType.MANTRA,
            action = "Recite ${type.mantra} 108 times",
            timing = "Daily during ${type.planet.displayName}'s hora",
            benefit = "Invokes ${type.planet.displayName}'s blessings for yoga activation"
        ))

        // Charity
        recommendations.add(YogaRecommendation(
            category = RecommendationType.CHARITY,
            action = "Donate ${type.charityItems} on ${getWeekday(type.planet)}",
            timing = "${getWeekday(type.planet)}s regularly",
            benefit = "Creates good karma and removes obstacles"
        ))

        return recommendations
    }

    private fun getWeekday(planet: Planet): String {
        return when (planet) {
            Planet.SUN -> "Sunday"
            Planet.MOON -> "Monday"
            Planet.MARS -> "Tuesday"
            Planet.MERCURY -> "Wednesday"
            Planet.JUPITER -> "Thursday"
            Planet.VENUS -> "Friday"
            Planet.SATURN -> "Saturday"
            else -> "any day"
        }
    }

    /**
     * Analyze effects when multiple yogas are present
     */
    private fun analyzeCombinedEffects(yogas: List<MahapurushaYoga>): CombinedYogaEffects? {
        if (yogas.size < 2) return null

        val yogaTypes = yogas.map { it.type }
        val totalStrength = yogas.sumOf { it.strength }
        val averageStrength = totalStrength / yogas.size

        val combinedEffects = mutableListOf<String>()
        val synergies = mutableListOf<String>()

        // Check for specific combinations
        if (MahapurushaYogaType.HAMSA in yogaTypes && MahapurushaYogaType.MALAVYA in yogaTypes) {
            synergies.add("Hamsa-Malavya combination: Wisdom with beauty, spiritual with material balance")
            combinedEffects.add("Excellent for careers in education, arts, counseling")
        }

        if (MahapurushaYogaType.RUCHAKA in yogaTypes && MahapurushaYogaType.SASHA in yogaTypes) {
            synergies.add("Ruchaka-Sasha combination: Courage with discipline, action with patience")
            combinedEffects.add("Excellent for leadership, military, engineering management")
        }

        if (MahapurushaYogaType.BHADRA in yogaTypes && MahapurushaYogaType.HAMSA in yogaTypes) {
            synergies.add("Bhadra-Hamsa combination: Intelligence with wisdom, communication with knowledge")
            combinedEffects.add("Excellent for teaching, writing, law, philosophy")
        }

        if (yogas.size >= 3) {
            combinedEffects.add("Rare combination of ${yogas.size} Mahapurusha Yogas indicates a highly blessed chart")
        }

        return CombinedYogaEffects(
            yogaCount = yogas.size,
            combinedStrength = averageStrength,
            synergies = synergies,
            combinedBenefits = combinedEffects,
            rarity = when (yogas.size) {
                2 -> "Uncommon - Two Mahapurusha Yogas"
                3 -> "Rare - Three Mahapurusha Yogas"
                4 -> "Very Rare - Four Mahapurusha Yogas"
                5 -> "Extremely Rare - All Five Mahapurusha Yogas"
                else -> "Single Yoga"
            },
            overallInterpretation = "Having ${yogas.size} Mahapurusha Yogas is ${if (yogas.size >= 3) "exceptionally rare and powerful" else "a significant blessing"}. " +
                    "The combined influence of ${yogas.joinToString(" and ") { it.type.displayName }} " +
                    "creates a multifaceted personality with diverse talents and blessings."
        )
    }

    /**
     * Calculate when yogas are most likely to manifest
     */
    private fun calculateActivationPeriods(
        yogas: List<MahapurushaYoga>,
        chart: VedicChart
    ): List<ActivationPeriod> {
        val periods = mutableListOf<ActivationPeriod>()

        for (yoga in yogas) {
            // Primary activation: Planet's own dasha
            periods.add(ActivationPeriod(
                yoga = yoga.type,
                periodType = "Mahadasha",
                planet = yoga.planet,
                description = "${yoga.planet.displayName} Mahadasha - Primary yoga activation period",
                importance = ActivationImportance.HIGH
            ))

            // Secondary: Planet's antardasha in any dasha
            periods.add(ActivationPeriod(
                yoga = yoga.type,
                periodType = "Antardasha",
                planet = yoga.planet,
                description = "${yoga.planet.displayName} Antardasha - Secondary activation in any major period",
                importance = ActivationImportance.MEDIUM
            ))

            // Transit activation
            periods.add(ActivationPeriod(
                yoga = yoga.type,
                periodType = "Transit",
                planet = yoga.planet,
                description = "When ${yoga.planet.displayName} transits its natal position or exaltation sign",
                importance = ActivationImportance.MEDIUM
            ))

            // Jupiter transit
            periods.add(ActivationPeriod(
                yoga = yoga.type,
                periodType = "Jupiter Transit",
                planet = Planet.JUPITER,
                description = "When Jupiter transits over or aspects natal ${yoga.planet.displayName}",
                importance = ActivationImportance.HIGH
            ))
        }

        return periods.sortedByDescending { it.importance }
    }

    /**
     * Generate overall interpretation
     */
    private fun generateOverallInterpretation(
        yogas: List<MahapurushaYoga>,
        chart: VedicChart
    ): OverallInterpretation {
        val summary = if (yogas.isEmpty()) {
            "No Panch Mahapurusha Yoga is formed in this chart. These special yogas require " +
                    "Mars, Mercury, Jupiter, Venus, or Saturn to be in Kendra houses (1, 4, 7, 10) " +
                    "in their own or exaltation signs. While these yogas are not present, other " +
                    "yogas and planetary combinations in your chart provide their own blessings."
        } else {
            buildString {
                append("This chart has ${yogas.size} Panch Mahapurusha Yoga(s): ")
                append(yogas.joinToString(", ") { "${it.type.displayName} (${it.planet.displayName})" })
                append(".\n\n")
                append("These are among the most auspicious yogas in Vedic astrology, indicating ")
                append("a soul that has earned special blessings through past-life merit. ")
                append("The native is blessed with the positive qualities of the yoga-forming planets.")
            }
        }

        val keyInsights = yogas.map { yoga ->
            "${yoga.type.displayName}: ${yoga.type.shortDescription} (Strength: ${yoga.strength}%)"
        }

        val recommendations = if (yogas.isEmpty()) {
            listOf(
                "Focus on strengthening the natural benefics in your chart",
                "Explore other yogas present in your horoscope",
                "Planetary remedies can enhance positive influences"
            )
        } else {
            yogas.flatMap { yoga ->
                listOf(
                    "Activate ${yoga.type.displayName} through ${yoga.type.planet.displayName} worship",
                    "Best career alignment: ${yoga.type.suitableCareers.first()}"
                )
            }.distinct()
        }

        return OverallInterpretation(
            summary = summary,
            keyInsights = keyInsights,
            recommendations = recommendations
        )
    }

    private fun calculateOverallStrength(yogas: List<MahapurushaYoga>): Int {
        if (yogas.isEmpty()) return 0
        return yogas.sumOf { it.strength } / yogas.size
    }

    // ============================================
    // DATA CLASSES
    // ============================================

    data class PanchMahapurushaAnalysis(
        val yogas: List<MahapurushaYoga>,
        val hasAnyYoga: Boolean,
        val yogaCount: Int,
        val strongestYoga: MahapurushaYoga?,
        val interpretation: OverallInterpretation,
        val combinedEffects: CombinedYogaEffects?,
        val activationPeriods: List<ActivationPeriod>,
        val overallYogaStrength: Int
    )

    data class MahapurushaYoga(
        val type: MahapurushaYogaType,
        val planet: Planet,
        val house: Int,
        val sign: ZodiacSign,
        val dignity: PlanetDignity,
        val strength: Int,
        val strengthLevel: YogaStrengthLevel,
        val isExalted: Boolean,
        val isOwnSign: Boolean,
        val degreeFromExact: Double?,
        val effects: YogaEffects,
        val interpretation: String,
        val recommendations: List<YogaRecommendation>,
        val planetPosition: PlanetPosition
    )

    data class YogaEffects(
        val physicalTraits: String,
        val mentalTraits: String,
        val careerIndications: String,
        val spiritualGrowth: String,
        val relationshipImpact: String,
        val houseSpecificEffect: String,
        val manifestationStrength: String
    )

    data class YogaRecommendation(
        val category: RecommendationType,
        val action: String,
        val timing: String,
        val benefit: String
    )

    data class CombinedYogaEffects(
        val yogaCount: Int,
        val combinedStrength: Int,
        val synergies: List<String>,
        val combinedBenefits: List<String>,
        val rarity: String,
        val overallInterpretation: String
    )

    data class ActivationPeriod(
        val yoga: MahapurushaYogaType,
        val periodType: String,
        val planet: Planet,
        val description: String,
        val importance: ActivationImportance
    )

    data class OverallInterpretation(
        val summary: String,
        val keyInsights: List<String>,
        val recommendations: List<String>
    )

    enum class MahapurushaYogaType(
        val displayName: String,
        val planet: Planet,
        val description: String,
        val shortDescription: String,
        val primaryBlessings: List<String>,
        val deity: String,
        val gemstone: String,
        val mantra: String,
        val charityItems: String,
        val suitableCareers: List<String>
    ) {
        RUCHAKA(
            displayName = "Ruchaka Yoga",
            planet = Planet.MARS,
            description = "Ruchaka Yoga is formed when Mars is in Kendra in its own sign (Aries/Scorpio) or exaltation (Capricorn). " +
                    "It bestows courage, leadership abilities, physical strength, and success in competitive fields. " +
                    "The native has a commanding presence and excels where bravery and initiative are required.",
            shortDescription = "Courage, leadership, physical prowess",
            primaryBlessings = listOf("Courage", "Leadership", "Physical strength", "Victory over enemies", "Land/property"),
            deity = "Lord Hanuman or Lord Kartikeya",
            gemstone = "Red Coral (Moonga)",
            mantra = "Om Kraam Kreem Kraum Sah Bhaumaya Namah",
            charityItems = "red lentils, red cloth, jaggery to the needy",
            suitableCareers = listOf("Military", "Police", "Sports", "Surgery", "Engineering", "Real estate", "Martial arts")
        ),
        BHADRA(
            displayName = "Bhadra Yoga",
            planet = Planet.MERCURY,
            description = "Bhadra Yoga is formed when Mercury is in Kendra in its own sign (Gemini/Virgo) or exaltation (Virgo). " +
                    "It bestows exceptional intelligence, communication skills, business acumen, and scholarly abilities. " +
                    "The native excels in intellectual pursuits and has a youthful, adaptable nature.",
            shortDescription = "Intelligence, communication, commerce",
            primaryBlessings = listOf("Intelligence", "Eloquence", "Business success", "Learning ability", "Youthfulness"),
            deity = "Lord Vishnu or Lord Ganesha",
            gemstone = "Emerald (Panna)",
            mantra = "Om Braam Breem Braum Sah Budhaya Namah",
            charityItems = "green moong dal, green cloth, books to students",
            suitableCareers = listOf("Writing", "Teaching", "Commerce", "Accounting", "IT", "Journalism", "Astrology")
        ),
        HAMSA(
            displayName = "Hamsa Yoga",
            planet = Planet.JUPITER,
            description = "Hamsa Yoga is formed when Jupiter is in Kendra in its own sign (Sagittarius/Pisces) or exaltation (Cancer). " +
                    "It bestows wisdom, righteousness, fortune, and spiritual inclination. " +
                    "The native is noble-minded, generous, and respected in society as a wise counselor.",
            shortDescription = "Wisdom, spirituality, fortune",
            primaryBlessings = listOf("Wisdom", "Fortune", "Spiritual growth", "Good character", "Respected status"),
            deity = "Lord Brihaspati or Lord Shiva",
            gemstone = "Yellow Sapphire (Pukhraj)",
            mantra = "Om Graam Greem Graum Sah Gurave Namah",
            charityItems = "turmeric, yellow cloth, ghee, gold to Brahmins",
            suitableCareers = listOf("Education", "Law", "Finance", "Consulting", "Priesthood", "Philosophy", "Judiciary")
        ),
        MALAVYA(
            displayName = "Malavya Yoga",
            planet = Planet.VENUS,
            description = "Malavya Yoga is formed when Venus is in Kendra in its own sign (Taurus/Libra) or exaltation (Pisces). " +
                    "It bestows beauty, luxury, artistic talents, and harmonious relationships. " +
                    "The native has refined tastes, attracts comfort and enjoys sensual pleasures in a balanced way.",
            shortDescription = "Beauty, luxury, artistic talents",
            primaryBlessings = listOf("Beauty", "Luxury", "Artistic ability", "Happy marriage", "Vehicles"),
            deity = "Goddess Lakshmi or Goddess Saraswati",
            gemstone = "Diamond (Heera) or White Sapphire",
            mantra = "Om Draam Dreem Draum Sah Shukraya Namah",
            charityItems = "white rice, white cloth, sugar, silver items to women",
            suitableCareers = listOf("Arts", "Entertainment", "Fashion", "Hospitality", "Luxury goods", "Interior design", "Music")
        ),
        SASHA(
            displayName = "Sasha Yoga",
            planet = Planet.SATURN,
            description = "Sasha Yoga is formed when Saturn is in Kendra in its own sign (Capricorn/Aquarius) or exaltation (Libra). " +
                    "It bestows discipline, longevity, authority, and success through persistent effort. " +
                    "The native rises to positions of power through hard work and has command over subordinates.",
            shortDescription = "Discipline, authority, longevity",
            primaryBlessings = listOf("Longevity", "Authority", "Discipline", "Servants", "Success through perseverance"),
            deity = "Lord Shani or Lord Hanuman",
            gemstone = "Blue Sapphire (Neelam) - only after careful consultation",
            mantra = "Om Praam Preem Praum Sah Shanaischaraya Namah",
            charityItems = "black sesame, black cloth, iron items, oil to the poor",
            suitableCareers = listOf("Government", "Construction", "Mining", "Management", "Agriculture", "Real estate", "Manufacturing")
        )
    }

    enum class PlanetDignity(val displayName: String) {
        EXALTED("Exalted"),
        OWN_SIGN("Own Sign"),
        MOOLATRIKONA("Moolatrikona"),
        FRIEND("Friendly Sign"),
        NEUTRAL("Neutral Sign"),
        ENEMY("Enemy Sign"),
        DEBILITATED("Debilitated")
    }

    enum class YogaStrengthLevel(val displayName: String) {
        EXCEPTIONAL("Exceptional (80-100%)"),
        STRONG("Strong (60-79%)"),
        MODERATE("Moderate (40-59%)"),
        WEAK("Weak (20-39%)"),
        VERY_WEAK("Very Weak (0-19%)")
    }

    enum class RecommendationType(val displayName: String) {
        STRENGTHENING("Strengthening Practice"),
        CAREER("Career Alignment"),
        GEMSTONE("Gemstone Recommendation"),
        MANTRA("Mantra Recitation"),
        CHARITY("Charitable Practice")
    }

    enum class ActivationImportance {
        HIGH, MEDIUM, LOW
    }
}
