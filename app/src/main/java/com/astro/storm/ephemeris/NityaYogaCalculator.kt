package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.Nakshatra

/**
 * Nitya Yoga Calculator
 *
 * Calculates the 27 Nitya Yogas (daily yogas) based on the combined longitude
 * of Sun and Moon. Each yoga spans 13°20' (800 minutes of arc).
 *
 * Nitya Yoga = (Sun longitude + Moon longitude) / 13°20'
 *
 * These yogas are one of the five elements of the Panchanga (Hindu almanac)
 * and indicate the general auspiciousness of a day or moment.
 *
 * References:
 * - Brihat Parashara Hora Shastra
 * - Traditional Panchanga calculations
 * - Muhurta Chintamani
 */
object NityaYogaCalculator {

    // Each yoga spans 13°20' = 13.333...° = 800 arc minutes
    private const val YOGA_SPAN = 13.333333333333334

    /**
     * Main analysis entry point
     */
    fun analyzeNityaYoga(chart: VedicChart): NityaYogaAnalysis {
        val sunPosition = chart.planetPositions.find { it.planet == Planet.SUN }
        val moonPosition = chart.planetPositions.find { it.planet == Planet.MOON }

        if (sunPosition == null || moonPosition == null) {
            return NityaYogaAnalysis(
                yoga = NityaYogaType.VISHKUMBHA, // Default
                yogaIndex = 1,
                exactDegree = 0.0,
                percentComplete = 0.0,
                strength = YogaStrength.MODERATE,
                interpretation = "Unable to calculate - Sun or Moon position not available",
                effects = NityaYogaEffects(
                    generalNature = "Unknown",
                    auspiciousness = Auspiciousness.NEUTRAL,
                    suitableActivities = emptyList(),
                    unsuitableActivities = emptyList(),
                    healthIndications = "",
                    financialIndications = "",
                    relationshipIndications = ""
                ),
                recommendations = emptyList(),
                timingAdvice = TimingAdvice(
                    bestHours = emptyList(),
                    avoidHours = emptyList(),
                    generalTiming = ""
                )
            )
        }

        return calculateNityaYoga(sunPosition, moonPosition, chart)
    }

    /**
     * Calculate Nitya Yoga from Sun and Moon positions
     */
    private fun calculateNityaYoga(
        sunPosition: PlanetPosition,
        moonPosition: PlanetPosition,
        chart: VedicChart
    ): NityaYogaAnalysis {
        // Sum of Sun and Moon longitudes
        val combinedLongitude = (sunPosition.longitude + moonPosition.longitude) % 360.0

        // Calculate yoga index (1-27)
        val yogaIndex = ((combinedLongitude / YOGA_SPAN).toInt() % 27) + 1
        val yoga = NityaYogaType.fromIndex(yogaIndex)

        // Calculate how far into the current yoga we are (0-100%)
        val yogaStartDegree = (yogaIndex - 1) * YOGA_SPAN
        val degreeInYoga = combinedLongitude - yogaStartDegree
        val percentComplete = (degreeInYoga / YOGA_SPAN) * 100

        // Calculate strength based on various factors
        val strength = calculateYogaStrength(yoga, percentComplete, sunPosition, moonPosition, chart)

        // Generate effects and interpretation
        val effects = generateEffects(yoga, strength)
        val interpretation = generateInterpretation(yoga, strength, chart)
        val recommendations = generateRecommendations(yoga, strength)
        val timingAdvice = generateTimingAdvice(yoga)

        return NityaYogaAnalysis(
            yoga = yoga,
            yogaIndex = yogaIndex,
            exactDegree = combinedLongitude,
            percentComplete = percentComplete,
            strength = strength,
            interpretation = interpretation,
            effects = effects,
            recommendations = recommendations,
            timingAdvice = timingAdvice,
            sunLongitude = sunPosition.longitude,
            moonLongitude = moonPosition.longitude,
            nextYoga = NityaYogaType.fromIndex(if (yogaIndex == 27) 1 else yogaIndex + 1),
            degreesToNextYoga = YOGA_SPAN - degreeInYoga
        )
    }

    /**
     * Calculate yoga strength based on various factors
     */
    private fun calculateYogaStrength(
        yoga: NityaYogaType,
        percentComplete: Double,
        sunPosition: PlanetPosition,
        moonPosition: PlanetPosition,
        chart: VedicChart
    ): YogaStrength {
        var strengthScore = 50 // Base score

        // Yoga at its peak (middle portion) is stronger
        strengthScore += when {
            percentComplete in 30.0..70.0 -> 20 // Peak strength in middle
            percentComplete in 20.0..80.0 -> 10 // Good strength
            else -> 0 // Beginning or end - transitional
        }

        // Moon's brightness affects strength
        val moonPhase = calculateMoonPhase(sunPosition.longitude, moonPosition.longitude)
        strengthScore += when {
            moonPhase > 0.7 -> 15 // Bright moon enhances positive yogas
            moonPhase > 0.4 -> 10
            moonPhase > 0.2 -> 5
            else -> 0
        }

        // Natural benefic yogas get bonus
        if (yoga.auspiciousness == Auspiciousness.AUSPICIOUS ||
            yoga.auspiciousness == Auspiciousness.HIGHLY_AUSPICIOUS) {
            strengthScore += 10
        }

        return when {
            strengthScore >= 80 -> YogaStrength.VERY_STRONG
            strengthScore >= 60 -> YogaStrength.STRONG
            strengthScore >= 40 -> YogaStrength.MODERATE
            strengthScore >= 20 -> YogaStrength.WEAK
            else -> YogaStrength.VERY_WEAK
        }
    }

    private fun calculateMoonPhase(sunLongitude: Double, moonLongitude: Double): Double {
        val elongation = (moonLongitude - sunLongitude + 360) % 360
        return if (elongation <= 180) elongation / 180 else (360 - elongation) / 180
    }

    /**
     * Generate effects for the yoga
     */
    private fun generateEffects(yoga: NityaYogaType, strength: YogaStrength): NityaYogaEffects {
        val strengthModifier = when (strength) {
            YogaStrength.VERY_STRONG -> "strongly manifests"
            YogaStrength.STRONG -> "clearly manifests"
            YogaStrength.MODERATE -> "moderately manifests"
            YogaStrength.WEAK -> "weakly manifests"
            YogaStrength.VERY_WEAK -> "barely manifests"
        }

        return NityaYogaEffects(
            generalNature = "${yoga.nature} - This yoga $strengthModifier its qualities",
            auspiciousness = yoga.auspiciousness,
            suitableActivities = yoga.suitableActivities,
            unsuitableActivities = yoga.unsuitableActivities,
            healthIndications = yoga.healthEffect,
            financialIndications = yoga.financialEffect,
            relationshipIndications = yoga.relationshipEffect
        )
    }

    /**
     * Generate interpretation
     */
    private fun generateInterpretation(
        yoga: NityaYogaType,
        strength: YogaStrength,
        chart: VedicChart
    ): String {
        return buildString {
            append("${yoga.displayName} (${yoga.meaning})\n\n")
            append("${yoga.description}\n\n")
            append("Ruling Planet: ${yoga.rulingPlanet.displayName}\n")
            append("Nature: ${yoga.nature}\n")
            append("Auspiciousness: ${yoga.auspiciousness.displayName}\n\n")

            when (yoga.auspiciousness) {
                Auspiciousness.HIGHLY_AUSPICIOUS -> {
                    append("This is a highly favorable yoga. Activities begun now tend to succeed with minimal obstacles. ")
                    append("The energy supports growth, prosperity, and positive outcomes.")
                }
                Auspiciousness.AUSPICIOUS -> {
                    append("This is a favorable yoga that supports constructive activities. ")
                    append("Good for general undertakings and new beginnings.")
                }
                Auspiciousness.NEUTRAL -> {
                    append("This is a mixed yoga with both positive and challenging aspects. ")
                    append("Results depend significantly on other factors in the chart and timing.")
                }
                Auspiciousness.INAUSPICIOUS -> {
                    append("This yoga requires caution. It's better suited for introspection and routine matters ")
                    append("rather than new ventures. Patience and careful planning are advised.")
                }
                Auspiciousness.HIGHLY_INAUSPICIOUS -> {
                    append("This is a challenging yoga. Major new initiatives should be postponed if possible. ")
                    append("However, it can be useful for activities like fasting, meditation, or ending things.")
                }
            }
        }
    }

    /**
     * Generate recommendations
     */
    private fun generateRecommendations(yoga: NityaYogaType, strength: YogaStrength): List<YogaRecommendation> {
        val recommendations = mutableListOf<YogaRecommendation>()

        // Worship recommendation
        recommendations.add(YogaRecommendation(
            category = RecommendationCategory.SPIRITUAL,
            action = "Worship ${yoga.rulingPlanet.displayName} or its deity",
            benefit = "Enhances positive effects and mitigates challenges",
            timing = "During ${yoga.rulingPlanet.displayName}'s hora or on its day"
        ))

        // Activity recommendation based on yoga nature
        when (yoga.auspiciousness) {
            Auspiciousness.HIGHLY_AUSPICIOUS, Auspiciousness.AUSPICIOUS -> {
                recommendations.add(YogaRecommendation(
                    category = RecommendationCategory.ACTIVITY,
                    action = "Initiate important activities: ${yoga.suitableActivities.take(3).joinToString(", ")}",
                    benefit = "Activities started now have greater chance of success",
                    timing = "During auspicious muhurta within this yoga period"
                ))
            }
            Auspiciousness.NEUTRAL -> {
                recommendations.add(YogaRecommendation(
                    category = RecommendationCategory.ACTIVITY,
                    action = "Continue ongoing work; avoid major new beginnings",
                    benefit = "Maintains stability without creating new complications",
                    timing = "Any time during this yoga"
                ))
            }
            else -> {
                recommendations.add(YogaRecommendation(
                    category = RecommendationCategory.ACTIVITY,
                    action = "Focus on spiritual practices, routine work, or completing tasks",
                    benefit = "Channels the yoga's energy constructively",
                    timing = "Morning hours are generally better"
                ))
            }
        }

        // Mantra recommendation
        recommendations.add(YogaRecommendation(
            category = RecommendationCategory.MANTRA,
            action = "Recite ${yoga.rulingPlanet.displayName}'s beej mantra or Nitya Yoga prayer",
            benefit = "Harmonizes with the cosmic energy of this yoga",
            timing = "108 times during sunrise or sunset"
        ))

        return recommendations
    }

    /**
     * Generate timing advice
     */
    private fun generateTimingAdvice(yoga: NityaYogaType): TimingAdvice {
        val bestHours = when (yoga.auspiciousness) {
            Auspiciousness.HIGHLY_AUSPICIOUS, Auspiciousness.AUSPICIOUS ->
                listOf("Brahma Muhurta (pre-dawn)", "Mid-morning", "Early afternoon")
            Auspiciousness.NEUTRAL ->
                listOf("Morning hours", "After noon")
            else ->
                listOf("Early morning for spiritual practices", "Avoid afternoon for important work")
        }

        val avoidHours = when (yoga.auspiciousness) {
            Auspiciousness.HIGHLY_INAUSPICIOUS, Auspiciousness.INAUSPICIOUS ->
                listOf("Rahu Kaal", "Gulika Kaal", "Late evening")
            else ->
                listOf("Rahu Kaal for important matters")
        }

        val generalTiming = when (yoga.auspiciousness) {
            Auspiciousness.HIGHLY_AUSPICIOUS ->
                "Excellent timing for most activities. Make the most of this auspicious period."
            Auspiciousness.AUSPICIOUS ->
                "Good timing for constructive work. Standard precautions for timing apply."
            Auspiciousness.NEUTRAL ->
                "Average timing. Success depends on other factors and careful planning."
            Auspiciousness.INAUSPICIOUS ->
                "Exercise caution with timing. Prefer morning hours and avoid hasty decisions."
            Auspiciousness.HIGHLY_INAUSPICIOUS ->
                "Challenging period. Best for meditation, fasting, and spiritual withdrawal."
        }

        return TimingAdvice(
            bestHours = bestHours,
            avoidHours = avoidHours,
            generalTiming = generalTiming
        )
    }

    // ============================================
    // DATA CLASSES
    // ============================================

    data class NityaYogaAnalysis(
        val yoga: NityaYogaType,
        val yogaIndex: Int,
        val exactDegree: Double,
        val percentComplete: Double,
        val strength: YogaStrength,
        val interpretation: String,
        val effects: NityaYogaEffects,
        val recommendations: List<YogaRecommendation>,
        val timingAdvice: TimingAdvice,
        val sunLongitude: Double = 0.0,
        val moonLongitude: Double = 0.0,
        val nextYoga: NityaYogaType? = null,
        val degreesToNextYoga: Double = 0.0
    )

    data class NityaYogaEffects(
        val generalNature: String,
        val auspiciousness: Auspiciousness,
        val suitableActivities: List<String>,
        val unsuitableActivities: List<String>,
        val healthIndications: String,
        val financialIndications: String,
        val relationshipIndications: String
    )

    data class YogaRecommendation(
        val category: RecommendationCategory,
        val action: String,
        val benefit: String,
        val timing: String
    )

    data class TimingAdvice(
        val bestHours: List<String>,
        val avoidHours: List<String>,
        val generalTiming: String
    )

    // ============================================
    // ENUMS
    // ============================================

    enum class NityaYogaType(
        val index: Int,
        val displayName: String,
        val meaning: String,
        val rulingPlanet: Planet,
        val nature: String,
        val auspiciousness: Auspiciousness,
        val description: String,
        val suitableActivities: List<String>,
        val unsuitableActivities: List<String>,
        val healthEffect: String,
        val financialEffect: String,
        val relationshipEffect: String
    ) {
        VISHKUMBHA(
            index = 1,
            displayName = "Vishkumbha",
            meaning = "Poison Pot",
            rulingPlanet = Planet.SATURN,
            nature = "Challenging",
            auspiciousness = Auspiciousness.INAUSPICIOUS,
            description = "Vishkumbha yoga brings obstacles and delays. It creates a sense of being stuck or constrained. However, it can be useful for activities requiring patience and perseverance.",
            suitableActivities = listOf("Meditation", "Fasting", "Completing pending work", "Debt repayment"),
            unsuitableActivities = listOf("New ventures", "Marriage", "Travel", "Investments"),
            healthEffect = "Watch for digestive issues and lethargy",
            financialEffect = "Avoid financial commitments; losses possible",
            relationshipEffect = "Misunderstandings may arise; practice patience"
        ),
        PRITI(
            index = 2,
            displayName = "Priti",
            meaning = "Love/Affection",
            rulingPlanet = Planet.MERCURY,
            nature = "Benefic",
            auspiciousness = Auspiciousness.HIGHLY_AUSPICIOUS,
            description = "Priti yoga brings love, affection, and positive relationships. Excellent for romantic matters, social gatherings, and activities requiring harmony and cooperation.",
            suitableActivities = listOf("Marriage", "Romance", "Social events", "Artistic pursuits", "Making friends"),
            unsuitableActivities = listOf("Litigation", "Confrontation", "Aggressive activities"),
            healthEffect = "Good for mental peace and emotional wellness",
            financialEffect = "Favorable for partnerships and collaborations",
            relationshipEffect = "Excellent for all relationship matters"
        ),
        AYUSHMAN(
            index = 3,
            displayName = "Ayushman",
            meaning = "Long Life",
            rulingPlanet = Planet.MOON,
            nature = "Benefic",
            auspiciousness = Auspiciousness.HIGHLY_AUSPICIOUS,
            description = "Ayushman yoga promotes health, longevity, and vitality. Excellent for medical treatments, health-related activities, and anything promoting well-being.",
            suitableActivities = listOf("Medical treatments", "Starting health regimes", "Long-term projects", "Building foundations"),
            unsuitableActivities = listOf("Risky ventures", "Speculative activities"),
            healthEffect = "Very favorable for health and healing",
            financialEffect = "Good for long-term investments",
            relationshipEffect = "Supports lasting commitments"
        ),
        SAUBHAGYA(
            index = 4,
            displayName = "Saubhagya",
            meaning = "Good Fortune",
            rulingPlanet = Planet.SUN,
            nature = "Benefic",
            auspiciousness = Auspiciousness.HIGHLY_AUSPICIOUS,
            description = "Saubhagya yoga brings good fortune, prosperity, and success. One of the most favorable yogas for beginning important activities.",
            suitableActivities = listOf("Marriage", "Business ventures", "Investments", "House construction", "Travel"),
            unsuitableActivities = listOf("Activities requiring secrecy"),
            healthEffect = "Promotes vitality and energy",
            financialEffect = "Excellent for wealth-building activities",
            relationshipEffect = "Brings harmony and happiness in relationships"
        ),
        SHOBHANA(
            index = 5,
            displayName = "Shobhana",
            meaning = "Splendor/Beauty",
            rulingPlanet = Planet.JUPITER,
            nature = "Benefic",
            auspiciousness = Auspiciousness.AUSPICIOUS,
            description = "Shobhana yoga enhances beauty, grace, and artistic endeavors. Favorable for aesthetic pursuits and activities requiring elegance.",
            suitableActivities = listOf("Art", "Music", "Dance", "Fashion", "Decoration", "Photography"),
            unsuitableActivities = listOf("Agriculture", "Rough manual work"),
            healthEffect = "Good for skin and overall appearance",
            financialEffect = "Favorable for luxury goods and beauty industry",
            relationshipEffect = "Enhances romantic attraction"
        ),
        ATIGANDA(
            index = 6,
            displayName = "Atiganda",
            meaning = "Great Danger",
            rulingPlanet = Planet.MOON,
            nature = "Malefic",
            auspiciousness = Auspiciousness.HIGHLY_INAUSPICIOUS,
            description = "Atiganda yoga indicates potential dangers and obstacles. Exercise extreme caution during this period. Best for spiritual practices and introspection.",
            suitableActivities = listOf("Meditation", "Fasting", "Mantra japa", "Charity"),
            unsuitableActivities = listOf("Travel", "Surgery", "New ventures", "Contracts", "Major decisions"),
            healthEffect = "Risk of accidents; avoid risky activities",
            financialEffect = "High risk of losses; avoid investments",
            relationshipEffect = "Conflicts possible; maintain harmony"
        ),
        SUKARMA(
            index = 7,
            displayName = "Sukarma",
            meaning = "Good Deeds",
            rulingPlanet = Planet.MARS,
            nature = "Benefic",
            auspiciousness = Auspiciousness.AUSPICIOUS,
            description = "Sukarma yoga supports righteous actions and good deeds. Favorable for charitable activities, religious ceremonies, and virtuous undertakings.",
            suitableActivities = listOf("Charity", "Religious ceremonies", "Starting good habits", "Helping others"),
            unsuitableActivities = listOf("Selfish endeavors", "Deception"),
            healthEffect = "Good for mental and spiritual health",
            financialEffect = "Dharmic earnings supported; avoid greed",
            relationshipEffect = "Supports selfless love and service"
        ),
        DHRITI(
            index = 8,
            displayName = "Dhriti",
            meaning = "Steadfastness",
            rulingPlanet = Planet.MERCURY,
            nature = "Benefic",
            auspiciousness = Auspiciousness.AUSPICIOUS,
            description = "Dhriti yoga brings determination, patience, and perseverance. Excellent for activities requiring sustained effort and commitment.",
            suitableActivities = listOf("Long-term projects", "Studies", "Research", "Building foundations"),
            unsuitableActivities = listOf("Quick ventures", "Speculative activities"),
            healthEffect = "Supports chronic condition management",
            financialEffect = "Favorable for steady, long-term investments",
            relationshipEffect = "Strengthens commitment and loyalty"
        ),
        SHOOLA(
            index = 9,
            displayName = "Shoola",
            meaning = "Spear/Thorn",
            rulingPlanet = Planet.SUN,
            nature = "Malefic",
            auspiciousness = Auspiciousness.INAUSPICIOUS,
            description = "Shoola yoga can bring sharp, painful experiences. Like a thorn, it creates temporary discomfort. Good for activities requiring sharpness and precision.",
            suitableActivities = listOf("Surgery (if necessary)", "Cutting", "Demolition", "Ending relationships"),
            unsuitableActivities = listOf("Marriage", "New beginnings", "Travel", "Investments"),
            healthEffect = "Watch for sharp pains and injuries",
            financialEffect = "Risk of sudden losses",
            relationshipEffect = "Arguments and separations possible"
        ),
        GANDA(
            index = 10,
            displayName = "Ganda",
            meaning = "Obstacle/Knot",
            rulingPlanet = Planet.JUPITER,
            nature = "Malefic",
            auspiciousness = Auspiciousness.INAUSPICIOUS,
            description = "Ganda yoga creates obstacles and complications. Activities may face unexpected hurdles. Best used for untangling existing problems.",
            suitableActivities = listOf("Problem-solving", "Clearing obstacles", "Meditation", "Fasting"),
            unsuitableActivities = listOf("New ventures", "Contracts", "Travel", "Important meetings"),
            healthEffect = "May experience minor health hiccups",
            financialEffect = "Financial blockages possible",
            relationshipEffect = "Communication issues may arise"
        ),
        VRIDDHI(
            index = 11,
            displayName = "Vriddhi",
            meaning = "Growth/Increase",
            rulingPlanet = Planet.SATURN,
            nature = "Benefic",
            auspiciousness = Auspiciousness.AUSPICIOUS,
            description = "Vriddhi yoga promotes growth, expansion, and increase. Excellent for activities aimed at development and multiplication.",
            suitableActivities = listOf("Business expansion", "Planting", "Investments", "Starting courses"),
            unsuitableActivities = listOf("Reduction activities", "Ending things"),
            healthEffect = "Supports recovery and healing",
            financialEffect = "Excellent for wealth multiplication",
            relationshipEffect = "Family growth and expansion favored"
        ),
        DHRUVA(
            index = 12,
            displayName = "Dhruva",
            meaning = "Fixed/Stable",
            rulingPlanet = Planet.MARS,
            nature = "Benefic",
            auspiciousness = Auspiciousness.HIGHLY_AUSPICIOUS,
            description = "Dhruva yoga brings stability, permanence, and lasting results. Excellent for activities intended to be permanent and enduring.",
            suitableActivities = listOf("Foundation laying", "House construction", "Marriage", "Long-term commitments"),
            unsuitableActivities = listOf("Temporary arrangements", "Quick trades"),
            healthEffect = "Supports establishing healthy routines",
            financialEffect = "Excellent for fixed assets and property",
            relationshipEffect = "Supports permanent bonds and commitments"
        ),
        VYAGHATA(
            index = 13,
            displayName = "Vyaghata",
            meaning = "Beating/Striking",
            rulingPlanet = Planet.MOON,
            nature = "Malefic",
            auspiciousness = Auspiciousness.INAUSPICIOUS,
            description = "Vyaghata yoga can bring conflicts and clashes. There's potential for confrontation and disagreement during this period.",
            suitableActivities = listOf("Competitive activities", "Sports", "Debate (if skilled)", "Self-defense training"),
            unsuitableActivities = listOf("Peace negotiations", "Marriage", "Partnerships", "Sensitive discussions"),
            healthEffect = "Risk of injuries from impacts",
            financialEffect = "Competitive business may succeed; avoid partnerships",
            relationshipEffect = "High conflict potential; practice patience"
        ),
        HARSHANA(
            index = 14,
            displayName = "Harshana",
            meaning = "Joy/Delight",
            rulingPlanet = Planet.SUN,
            nature = "Benefic",
            auspiciousness = Auspiciousness.AUSPICIOUS,
            description = "Harshana yoga brings joy, happiness, and celebration. Excellent for festivities, entertainment, and activities bringing pleasure.",
            suitableActivities = listOf("Celebrations", "Entertainment", "Parties", "Recreation", "Music"),
            unsuitableActivities = listOf("Serious negotiations", "Funeral rites"),
            healthEffect = "Good for mental well-being and stress relief",
            financialEffect = "Entertainment industry favored; avoid serious deals",
            relationshipEffect = "Excellent for romantic and social gatherings"
        ),
        VAJRA(
            index = 15,
            displayName = "Vajra",
            meaning = "Thunderbolt/Diamond",
            rulingPlanet = Planet.JUPITER,
            nature = "Mixed",
            auspiciousness = Auspiciousness.NEUTRAL,
            description = "Vajra yoga is hard and powerful like a thunderbolt or diamond. Good for activities requiring strength and determination, but can be harsh.",
            suitableActivities = listOf("Strength training", "Confronting challenges", "Breaking bad habits"),
            unsuitableActivities = listOf("Gentle activities", "Delicate negotiations", "New relationships"),
            healthEffect = "Good for building strength; avoid overexertion",
            financialEffect = "Hard negotiations may succeed",
            relationshipEffect = "May be too intense for new relationships"
        ),
        SIDDHI(
            index = 16,
            displayName = "Siddhi",
            meaning = "Accomplishment/Success",
            rulingPlanet = Planet.SATURN,
            nature = "Benefic",
            auspiciousness = Auspiciousness.HIGHLY_AUSPICIOUS,
            description = "Siddhi yoga brings accomplishment and success. One of the best yogas for completing tasks and achieving goals.",
            suitableActivities = listOf("Completing projects", "Examinations", "Final submissions", "Achievement ceremonies"),
            unsuitableActivities = listOf("Starting new things (better for completing)"),
            healthEffect = "Good for achieving health goals",
            financialEffect = "Excellent for closing deals and achieving targets",
            relationshipEffect = "Good for achieving relationship milestones"
        ),
        VYATIPATA(
            index = 17,
            displayName = "Vyatipata",
            meaning = "Calamity",
            rulingPlanet = Planet.MARS,
            nature = "Malefic",
            auspiciousness = Auspiciousness.HIGHLY_INAUSPICIOUS,
            description = "Vyatipata yoga indicates potential calamities and misfortunes. Extreme caution advised. Best reserved for spiritual practices only.",
            suitableActivities = listOf("Intense meditation", "Tantric practices", "Fasting", "Penance"),
            unsuitableActivities = listOf("All mundane activities", "Travel", "New ventures", "Major decisions"),
            healthEffect = "High risk period; avoid surgeries and risks",
            financialEffect = "Severe loss potential; avoid all transactions",
            relationshipEffect = "Major conflicts possible; maintain silence"
        ),
        VARIYANA(
            index = 18,
            displayName = "Variyana",
            meaning = "Comfort/Ease",
            rulingPlanet = Planet.MERCURY,
            nature = "Benefic",
            auspiciousness = Auspiciousness.AUSPICIOUS,
            description = "Variyana yoga brings comfort, ease, and relaxation. Good for leisure activities and comfortable pursuits.",
            suitableActivities = listOf("Relaxation", "Vacation planning", "Comfort purchases", "Home improvements"),
            unsuitableActivities = listOf("Strenuous activities", "Challenging ventures"),
            healthEffect = "Good for rest and recuperation",
            financialEffect = "Favorable for luxury and comfort purchases",
            relationshipEffect = "Relaxed and comfortable interactions favored"
        ),
        PARIGHA(
            index = 19,
            displayName = "Parigha",
            meaning = "Obstruction/Iron Bar",
            rulingPlanet = Planet.SUN,
            nature = "Malefic",
            auspiciousness = Auspiciousness.INAUSPICIOUS,
            description = "Parigha yoga creates obstructions like an iron bar across a path. Delays and obstacles are likely during this period.",
            suitableActivities = listOf("Security measures", "Protection activities", "Defense planning"),
            unsuitableActivities = listOf("Travel", "New ventures", "Important meetings", "Investments"),
            healthEffect = "May feel blocked or constrained",
            financialEffect = "Financial progress obstructed",
            relationshipEffect = "Communication barriers possible"
        ),
        SHIVA(
            index = 20,
            displayName = "Shiva",
            meaning = "Auspicious/Lord Shiva",
            rulingPlanet = Planet.JUPITER,
            nature = "Benefic",
            auspiciousness = Auspiciousness.HIGHLY_AUSPICIOUS,
            description = "Shiva yoga is highly auspicious, named after Lord Shiva. Brings divine blessings, transformation, and spiritual progress.",
            suitableActivities = listOf("Spiritual practices", "Temple visits", "Shiva worship", "Transformation activities"),
            unsuitableActivities = listOf("Materialistic pursuits without spiritual foundation"),
            healthEffect = "Excellent for healing and rejuvenation",
            financialEffect = "Dharmic wealth accumulation favored",
            relationshipEffect = "Spiritual connections strengthened"
        ),
        SIDDHA(
            index = 21,
            displayName = "Siddha",
            meaning = "Perfection/Adept",
            rulingPlanet = Planet.SATURN,
            nature = "Benefic",
            auspiciousness = Auspiciousness.HIGHLY_AUSPICIOUS,
            description = "Siddha yoga brings perfection and mastery. Excellent for achieving expertise and bringing things to completion.",
            suitableActivities = listOf("Skill development", "Professional advancement", "Mastery pursuits", "Final exams"),
            unsuitableActivities = listOf("Beginner-level activities"),
            healthEffect = "Supports perfecting health practices",
            financialEffect = "Professional excellence brings rewards",
            relationshipEffect = "Deepening of existing bonds"
        ),
        SADHYA(
            index = 22,
            displayName = "Sadhya",
            meaning = "Achievable/Possible",
            rulingPlanet = Planet.MARS,
            nature = "Benefic",
            auspiciousness = Auspiciousness.AUSPICIOUS,
            description = "Sadhya yoga makes things achievable and possible. Goals that seemed difficult become attainable during this period.",
            suitableActivities = listOf("Goal pursuit", "Challenging tasks", "Negotiations", "Problem-solving"),
            unsuitableActivities = listOf("Impossible ventures", "Unrealistic goals"),
            healthEffect = "Health goals become achievable",
            financialEffect = "Financial targets within reach",
            relationshipEffect = "Relationship goals can be achieved"
        ),
        SHUBHA(
            index = 23,
            displayName = "Shubha",
            meaning = "Auspicious/Fortunate",
            rulingPlanet = Planet.MERCURY,
            nature = "Benefic",
            auspiciousness = Auspiciousness.HIGHLY_AUSPICIOUS,
            description = "Shubha yoga is inherently auspicious and fortunate. One of the best yogas for all positive activities and new beginnings.",
            suitableActivities = listOf("Marriage", "New ventures", "Travel", "Investments", "All positive activities"),
            unsuitableActivities = listOf("Negative or harmful activities"),
            healthEffect = "Generally favorable for health",
            financialEffect = "Excellent for all financial activities",
            relationshipEffect = "Highly favorable for relationships"
        ),
        SHUKLA(
            index = 24,
            displayName = "Shukla",
            meaning = "Bright/Pure",
            rulingPlanet = Planet.SUN,
            nature = "Benefic",
            auspiciousness = Auspiciousness.AUSPICIOUS,
            description = "Shukla yoga brings brightness, purity, and clarity. Excellent for activities requiring clear thinking and pure intentions.",
            suitableActivities = listOf("Education", "Purification rituals", "White magic", "Truth-seeking"),
            unsuitableActivities = listOf("Deception", "Hidden agendas"),
            healthEffect = "Good for cleansing and detox",
            financialEffect = "Honest dealings favored",
            relationshipEffect = "Truthful communication supported"
        ),
        BRAHMA(
            index = 25,
            displayName = "Brahma",
            meaning = "Creator/Supreme",
            rulingPlanet = Planet.JUPITER,
            nature = "Benefic",
            auspiciousness = Auspiciousness.HIGHLY_AUSPICIOUS,
            description = "Brahma yoga invokes the creative energy of Lord Brahma. Excellent for new creations, beginnings, and creative endeavors.",
            suitableActivities = listOf("New beginnings", "Creative projects", "Writing", "Innovation", "Starting businesses"),
            unsuitableActivities = listOf("Destruction activities", "Ending things"),
            healthEffect = "Supports vitality and new health regimes",
            financialEffect = "Excellent for creating new income streams",
            relationshipEffect = "Starting new relationships favored"
        ),
        INDRA(
            index = 26,
            displayName = "Indra",
            meaning = "King of Gods",
            rulingPlanet = Planet.SATURN,
            nature = "Benefic",
            auspiciousness = Auspiciousness.AUSPICIOUS,
            description = "Indra yoga brings leadership, authority, and power. Named after the king of gods, it supports activities requiring dominance and command.",
            suitableActivities = listOf("Leadership activities", "Government work", "Authority positions", "Public speaking"),
            unsuitableActivities = listOf("Subordinate roles", "Hidden activities"),
            healthEffect = "Good for confidence and vitality",
            financialEffect = "Authority positions bring financial benefits",
            relationshipEffect = "Taking leadership in relationships supported"
        ),
        VAIDHRITI(
            index = 27,
            displayName = "Vaidhriti",
            meaning = "Unfavorable/Contradiction",
            rulingPlanet = Planet.MARS,
            nature = "Malefic",
            auspiciousness = Auspiciousness.HIGHLY_INAUSPICIOUS,
            description = "Vaidhriti yoga brings contradictions and unfavorable results. Activities may produce opposite of intended results. Extreme caution advised.",
            suitableActivities = listOf("Meditation", "Introspection", "Fasting", "Silence"),
            unsuitableActivities = listOf("All important activities", "New ventures", "Travel", "Contracts"),
            healthEffect = "Risk of opposite reactions; avoid new treatments",
            financialEffect = "Investments may backfire; avoid transactions",
            relationshipEffect = "Misunderstandings likely; maintain silence"
        );

        companion object {
            fun fromIndex(index: Int): NityaYogaType {
                return entries.find { it.index == index } ?: VISHKUMBHA
            }
        }
    }

    enum class Auspiciousness(val displayName: String, val score: Int) {
        HIGHLY_AUSPICIOUS("Highly Auspicious", 5),
        AUSPICIOUS("Auspicious", 4),
        NEUTRAL("Neutral", 3),
        INAUSPICIOUS("Inauspicious", 2),
        HIGHLY_INAUSPICIOUS("Highly Inauspicious", 1)
    }

    enum class YogaStrength(val displayName: String) {
        VERY_STRONG("Very Strong"),
        STRONG("Strong"),
        MODERATE("Moderate"),
        WEAK("Weak"),
        VERY_WEAK("Very Weak")
    }

    enum class RecommendationCategory(val displayName: String) {
        SPIRITUAL("Spiritual Practice"),
        ACTIVITY("Activity Guidance"),
        MANTRA("Mantra Recitation"),
        TIMING("Timing Advice"),
        GENERAL("General Guidance")
    }
}
