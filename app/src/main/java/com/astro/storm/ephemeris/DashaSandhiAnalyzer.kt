package com.astro.storm.ephemeris

import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKeyAnalysis
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.math.abs

/**
 * Comprehensive Dasha Sandhi (Period Junction) Analyzer
 *
 * Dasha Sandhi is one of the most critical concepts in Vedic astrology timing.
 * It refers to the transition period between two Dasha (planetary period) phases
 * where significant life changes often occur.
 *
 * Key Concepts:
 *
 * 1. **SANDHI PERIOD**
 *    - The junction/transition zone between ending and beginning Dashas
 *    - Effects of both planets are felt simultaneously
 *    - Duration is proportional to the Dasha period length
 *
 * 2. **SANDHI INTENSITY**
 *    - Higher for Mahadasha transitions than Antardasha
 *    - Depends on planetary relationships (friend/enemy)
 *    - Affected by natal chart configurations
 *
 * 3. **SANDHI EFFECTS**
 *    - Can trigger major life events
 *    - Health vulnerabilities may emerge
 *    - Career/relationship changes common
 *    - Financial fluctuations possible
 *
 * Classical References:
 * - Brihat Parashara Hora Shastra (BPHS) on Dasha transitions
 * - Phaladeepika on planetary period effects
 * - Traditional interpretations from Vedic paramparas
 *
 * @author AstroStorm - Ultra-Precision Vedic Astrology
 */
object DashaSandhiAnalyzer {

    // ============================================================================
    // SANDHI INTENSITY LEVELS
    // ============================================================================

    /**
     * Sandhi intensity classification
     */
    enum class SandhiIntensity(
        val displayName: String,
        val score: Int, // 1-5
        val color: String // For UI
    ) {
        CRITICAL("Critical", 5, "#F44336"),      // Major Mahadasha change
        HIGH("High", 4, "#FF9800"),              // Mahadasha or significant Antardasha
        MODERATE("Moderate", 3, "#FFC107"),      // Regular Antardasha change
        MILD("Mild", 2, "#4CAF50"),              // Pratyantardasha change
        MINIMAL("Minimal", 1, "#2196F3");        // Sub-period change

        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                CRITICAL -> StringKeyAnalysis.SANDHI_INTENSITY_CRITICAL
                HIGH -> StringKeyAnalysis.SANDHI_INTENSITY_HIGH
                MODERATE -> StringKeyAnalysis.SANDHI_INTENSITY_MODERATE
                MILD -> StringKeyAnalysis.SANDHI_INTENSITY_MILD
                MINIMAL -> StringKeyAnalysis.SANDHI_INTENSITY_MINIMAL
            }
            return StringResources.get(key, language)
        }
    }

    /**
     * Planetary relationship types for Sandhi analysis
     */
    enum class TransitionType(val displayName: String) {
        FRIEND_TO_FRIEND("Friend to Friend"),
        FRIEND_TO_NEUTRAL("Friend to Neutral"),
        FRIEND_TO_ENEMY("Friend to Enemy"),
        NEUTRAL_TO_FRIEND("Neutral to Friend"),
        NEUTRAL_TO_NEUTRAL("Neutral to Neutral"),
        NEUTRAL_TO_ENEMY("Neutral to Enemy"),
        ENEMY_TO_FRIEND("Enemy to Friend"),
        ENEMY_TO_NEUTRAL("Enemy to Neutral"),
        ENEMY_TO_ENEMY("Enemy to Enemy");

        fun getLocalizedName(language: Language): String {
            val key = when (this) {
                FRIEND_TO_FRIEND -> StringKeyAnalysis.TRANSITION_FRIEND_FRIEND
                FRIEND_TO_NEUTRAL -> StringKeyAnalysis.TRANSITION_FRIEND_NEUTRAL
                FRIEND_TO_ENEMY -> StringKeyAnalysis.TRANSITION_FRIEND_ENEMY
                NEUTRAL_TO_FRIEND -> StringKeyAnalysis.TRANSITION_NEUTRAL_FRIEND
                NEUTRAL_TO_NEUTRAL -> StringKeyAnalysis.TRANSITION_NEUTRAL_NEUTRAL
                NEUTRAL_TO_ENEMY -> StringKeyAnalysis.TRANSITION_NEUTRAL_ENEMY
                ENEMY_TO_FRIEND -> StringKeyAnalysis.TRANSITION_ENEMY_FRIEND
                ENEMY_TO_NEUTRAL -> StringKeyAnalysis.TRANSITION_ENEMY_NEUTRAL
                ENEMY_TO_ENEMY -> StringKeyAnalysis.TRANSITION_ENEMY_ENEMY
            }
            return StringResources.get(key, language)
        }

        /**
         * Get difficulty level for this transition (1-5, 5 being most challenging)
         */
        val difficultyLevel: Int
            get() = when (this) {
                FRIEND_TO_FRIEND -> 1
                FRIEND_TO_NEUTRAL -> 2
                FRIEND_TO_ENEMY -> 4
                NEUTRAL_TO_FRIEND -> 1
                NEUTRAL_TO_NEUTRAL -> 2
                NEUTRAL_TO_ENEMY -> 3
                ENEMY_TO_FRIEND -> 2
                ENEMY_TO_NEUTRAL -> 3
                ENEMY_TO_ENEMY -> 5
            }
    }

    // ============================================================================
    // DATA CLASSES
    // ============================================================================

    /**
     * Enhanced Sandhi analysis result
     */
    data class SandhiAnalysis(
        val sandhi: DashaCalculator.DashaSandhi,
        val intensity: SandhiIntensity,
        val transitionType: TransitionType,
        val daysUntilTransition: Long,
        val isCurrentlyInSandhi: Boolean,
        val sandhiProgress: Double, // 0-100 if in sandhi
        val predictions: SandhiPredictions,
        val remedies: List<SandhiRemedy>,
        val affectedLifeAreas: List<LifeAreaImpact>,
        val interpretation: String
    )

    /**
     * Predictions during Sandhi period
     */
    data class SandhiPredictions(
        val generalOutlook: String,
        val careerEffects: String,
        val healthConcerns: String,
        val relationshipImpact: String,
        val financialTrend: String,
        val spiritualOpportunities: String,
        val keyDates: List<KeyDatePrediction>
    )

    /**
     * Key date during Sandhi
     */
    data class KeyDatePrediction(
        val date: LocalDate,
        val event: String,
        val significance: String
    )

    /**
     * Life area impact during Sandhi
     */
    data class LifeAreaImpact(
        val area: String,
        val impactLevel: Int, // 1-5
        val nature: ImpactNature,
        val description: String
    )

    enum class ImpactNature { POSITIVE, NEUTRAL, CHALLENGING, TRANSFORMATIVE }

    /**
     * Sandhi remedy recommendation
     */
    data class SandhiRemedy(
        val type: RemedyType,
        val description: String,
        val timing: String,
        val priority: Int // 1-3, 1 being highest
    )

    enum class RemedyType {
        MANTRA, DONATION, FASTING, GEMSTONE, YANTRA, PUJA, LIFESTYLE
    }

    /**
     * Complete Sandhi analysis for a chart
     */
    data class CompleteSandhiAnalysis(
        val chart: VedicChart,
        val currentSandhi: SandhiAnalysis?,
        val upcomingSandhis: List<SandhiAnalysis>,
        val recentPastSandhis: List<SandhiAnalysis>,
        val sandhiCalendar: List<SandhiCalendarEntry>,
        val overallVolatilityScore: Int, // 0-100
        val generalGuidance: String,
        val timestamp: Long = System.currentTimeMillis()
    )

    /**
     * Calendar entry for Sandhi tracking
     */
    data class SandhiCalendarEntry(
        val date: LocalDate,
        val sandhiType: String,
        val fromPlanet: Planet,
        val toPlanet: Planet,
        val level: DashaCalculator.DashaLevel,
        val intensity: SandhiIntensity
    )

    // ============================================================================
    // ANALYSIS METHODS
    // ============================================================================

    /**
     * Perform complete Sandhi analysis for a chart
     *
     * @param chart The birth chart
     * @param dashaTimeline Pre-calculated Dasha timeline
     * @param analysisDate Date for analysis (default: today)
     * @param lookAheadMonths How many months to look ahead (default: 6)
     * @return Complete Sandhi analysis
     */
    fun analyzeCompleteSandhis(
        chart: VedicChart,
        dashaTimeline: DashaCalculator.DashaTimeline,
        analysisDate: LocalDate = LocalDate.now(),
        lookAheadMonths: Int = 6
    ): CompleteSandhiAnalysis {
        val lookAheadDays = lookAheadMonths * 30

        // Get upcoming Sandhis from Dasha timeline
        val upcomingRawSandhis = dashaTimeline.getUpcomingSandhisWithin(lookAheadDays)

        // Analyze each Sandhi
        val upcomingSandhis = upcomingRawSandhis.map { sandhi ->
            analyzeSandhi(sandhi, chart, analysisDate)
        }.sortedBy { it.daysUntilTransition }

        // Find current Sandhi (if any)
        val currentSandhi = upcomingSandhis.find { it.isCurrentlyInSandhi }

        // Get recent past Sandhis (last 30 days)
        val recentPastSandhis = findRecentPastSandhis(dashaTimeline, analysisDate, 30)
            .map { analyzeSandhi(it, chart, analysisDate) }

        // Build Sandhi calendar
        val sandhiCalendar = buildSandhiCalendar(upcomingSandhis, analysisDate, lookAheadDays)

        // Calculate overall volatility
        val volatilityScore = calculateVolatilityScore(currentSandhi, upcomingSandhis, lookAheadDays)

        // Generate general guidance
        val generalGuidance = generateOverallGuidance(currentSandhi, upcomingSandhis, volatilityScore)

        return CompleteSandhiAnalysis(
            chart = chart,
            currentSandhi = currentSandhi,
            upcomingSandhis = upcomingSandhis.filter { !it.isCurrentlyInSandhi },
            recentPastSandhis = recentPastSandhis,
            sandhiCalendar = sandhiCalendar,
            overallVolatilityScore = volatilityScore,
            generalGuidance = generalGuidance
        )
    }

    /**
     * Analyze a single Sandhi in detail
     */
    fun analyzeSandhi(
        sandhi: DashaCalculator.DashaSandhi,
        chart: VedicChart,
        analysisDate: LocalDate
    ): SandhiAnalysis {
        val daysUntil = ChronoUnit.DAYS.between(analysisDate, sandhi.transitionDate)

        // Determine if currently in Sandhi
        val isInSandhi = analysisDate.isAfter(sandhi.sandhiStartDate.minusDays(1)) &&
                         analysisDate.isBefore(sandhi.sandhiEndDate.plusDays(1))

        // Calculate progress if in Sandhi
        val sandhiProgress = if (isInSandhi) {
            val totalDays = ChronoUnit.DAYS.between(sandhi.sandhiStartDate, sandhi.sandhiEndDate).toDouble()
            val elapsedDays = ChronoUnit.DAYS.between(sandhi.sandhiStartDate, analysisDate).toDouble()
            ((elapsedDays / totalDays) * 100).coerceIn(0.0, 100.0)
        } else 0.0

        // Determine intensity
        val intensity = calculateIntensity(sandhi, chart)

        // Analyze transition type
        val transitionType = analyzeTransitionType(sandhi.fromPlanet, sandhi.toPlanet)

        // Generate predictions
        val predictions = generatePredictions(sandhi, chart, transitionType, intensity)

        // Generate remedies
        val remedies = generateRemedies(sandhi, intensity, transitionType)

        // Determine affected life areas
        val affectedAreas = analyzeAffectedLifeAreas(sandhi, chart)

        // Generate interpretation
        val interpretation = generateInterpretation(sandhi, intensity, transitionType, isInSandhi)

        return SandhiAnalysis(
            sandhi = sandhi,
            intensity = intensity,
            transitionType = transitionType,
            daysUntilTransition = daysUntil,
            isCurrentlyInSandhi = isInSandhi,
            sandhiProgress = sandhiProgress,
            predictions = predictions,
            remedies = remedies,
            affectedLifeAreas = affectedAreas,
            interpretation = interpretation
        )
    }

    // ============================================================================
    // CALCULATION HELPERS
    // ============================================================================

    /**
     * Calculate Sandhi intensity based on level and planetary factors
     */
    private fun calculateIntensity(
        sandhi: DashaCalculator.DashaSandhi,
        chart: VedicChart
    ): SandhiIntensity {
        var intensityScore = 0

        // Base score from Dasha level
        intensityScore += when (sandhi.level) {
            DashaCalculator.DashaLevel.MAHADASHA -> 5
            DashaCalculator.DashaLevel.ANTARDASHA -> 3
            DashaCalculator.DashaLevel.PRATYANTARDASHA -> 2
            else -> 1
        }

        // Planetary strength modifier
        val fromPos = chart.planetPositions.find { it.planet == sandhi.fromPlanet }
        val toPos = chart.planetPositions.find { it.planet == sandhi.toPlanet }

        // Check if either planet is in a sensitive position
        if (fromPos != null) {
            if (VedicAstrologyUtils.isDebilitated(fromPos)) intensityScore += 1
            if (fromPos.isRetrograde) intensityScore += 1
        }

        if (toPos != null) {
            if (VedicAstrologyUtils.isDebilitated(toPos)) intensityScore += 1
            if (toPos.isRetrograde) intensityScore += 1
        }

        // Natural malefic transitions are more intense
        val malefics = listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU)
        if (sandhi.fromPlanet in malefics || sandhi.toPlanet in malefics) {
            intensityScore += 1
        }

        return when {
            intensityScore >= 8 -> SandhiIntensity.CRITICAL
            intensityScore >= 6 -> SandhiIntensity.HIGH
            intensityScore >= 4 -> SandhiIntensity.MODERATE
            intensityScore >= 2 -> SandhiIntensity.MILD
            else -> SandhiIntensity.MINIMAL
        }
    }

    /**
     * Analyze the type of planetary transition
     */
    private fun analyzeTransitionType(fromPlanet: Planet, toPlanet: Planet): TransitionType {
        val fromNature = getPlanetNatureForTransition(fromPlanet)
        val toNature = getPlanetNatureForTransition(toPlanet)

        // Check natural friendships
        val relationship = VedicAstrologyUtils.getNaturalRelationship(fromPlanet, toPlanet)

        return when {
            relationship == VedicAstrologyUtils.PlanetaryRelationship.FRIEND &&
            VedicAstrologyUtils.areNaturalFriends(toPlanet, fromPlanet) -> TransitionType.FRIEND_TO_FRIEND

            relationship == VedicAstrologyUtils.PlanetaryRelationship.FRIEND -> TransitionType.FRIEND_TO_NEUTRAL

            relationship == VedicAstrologyUtils.PlanetaryRelationship.ENEMY &&
            VedicAstrologyUtils.areNaturalEnemies(toPlanet, fromPlanet) -> TransitionType.ENEMY_TO_ENEMY

            relationship == VedicAstrologyUtils.PlanetaryRelationship.ENEMY -> TransitionType.FRIEND_TO_ENEMY

            VedicAstrologyUtils.areNaturalFriends(toPlanet, fromPlanet) -> TransitionType.NEUTRAL_TO_FRIEND

            VedicAstrologyUtils.areNaturalEnemies(toPlanet, fromPlanet) -> TransitionType.NEUTRAL_TO_ENEMY

            else -> TransitionType.NEUTRAL_TO_NEUTRAL
        }
    }

    private fun getPlanetNatureForTransition(planet: Planet): String {
        return when (planet) {
            Planet.SUN, Planet.MARS, Planet.SATURN, Planet.RAHU, Planet.KETU -> "Malefic"
            Planet.JUPITER, Planet.VENUS -> "Benefic"
            Planet.MOON, Planet.MERCURY -> "Variable"
            else -> "Neutral"
        }
    }

    /**
     * Generate predictions for Sandhi period
     */
    private fun generatePredictions(
        sandhi: DashaCalculator.DashaSandhi,
        chart: VedicChart,
        transitionType: TransitionType,
        intensity: SandhiIntensity
    ): SandhiPredictions {
        val fromPlanet = sandhi.fromPlanet
        val toPlanet = sandhi.toPlanet

        val generalOutlook = generateGeneralOutlook(fromPlanet, toPlanet, transitionType, intensity)
        val careerEffects = generateCareerPrediction(fromPlanet, toPlanet, chart)
        val healthConcerns = generateHealthPrediction(fromPlanet, toPlanet, intensity)
        val relationshipImpact = generateRelationshipPrediction(fromPlanet, toPlanet)
        val financialTrend = generateFinancialPrediction(fromPlanet, toPlanet)
        val spiritualOpportunities = generateSpiritualPrediction(fromPlanet, toPlanet)

        val keyDates = generateKeyDates(sandhi)

        return SandhiPredictions(
            generalOutlook = generalOutlook,
            careerEffects = careerEffects,
            healthConcerns = healthConcerns,
            relationshipImpact = relationshipImpact,
            financialTrend = financialTrend,
            spiritualOpportunities = spiritualOpportunities,
            keyDates = keyDates
        )
    }

    private fun generateGeneralOutlook(
        from: Planet,
        to: Planet,
        transitionType: TransitionType,
        intensity: SandhiIntensity
    ): String {
        val baseOutlook = when (intensity) {
            SandhiIntensity.CRITICAL ->
                "This is a major life transition period requiring careful navigation. " +
                "Significant changes in multiple life areas are likely."

            SandhiIntensity.HIGH ->
                "Important transition with notable shifts in energy. " +
                "Be prepared for meaningful changes and new developments."

            SandhiIntensity.MODERATE ->
                "Moderate transition period. Some adjustments needed but manageable. " +
                "Stay flexible and open to new approaches."

            SandhiIntensity.MILD ->
                "Minor transition with subtle shifts. " +
                "Maintain awareness but no major disruptions expected."

            SandhiIntensity.MINIMAL ->
                "Smooth transition period. Minimal impact on daily life. " +
                "Good time for steady progress."
        }

        val transitionNote = when (transitionType) {
            TransitionType.FRIEND_TO_FRIEND ->
                " The planetary energies are harmonious, supporting smooth change."

            TransitionType.ENEMY_TO_ENEMY ->
                " Challenging planetary combination requires extra caution and patience."

            TransitionType.FRIEND_TO_ENEMY ->
                " Shift from support to challenge - prepare for obstacles."

            TransitionType.ENEMY_TO_FRIEND ->
                " Relief and improvement coming after difficult period."

            else -> ""
        }

        return "$baseOutlook$transitionNote Moving from ${from.displayName} to ${to.displayName} energy."
    }

    private fun generateCareerPrediction(from: Planet, to: Planet, chart: VedicChart): String {
        val careerPlanets = listOf(Planet.SUN, Planet.SATURN, Planet.JUPITER, Planet.MARS)

        return when {
            to in careerPlanets && from !in careerPlanets ->
                "Career matters come into focus. New professional opportunities or responsibilities emerging."

            from in careerPlanets && to !in careerPlanets ->
                "Career intensity decreases. Time to consolidate gains and focus on other life areas."

            to == Planet.SUN ->
                "Leadership and authority themes. Possible recognition or promotion. Government matters favored."

            to == Planet.SATURN ->
                "Hard work and discipline required. Career stability through perseverance. May face delays but lasting results."

            to == Planet.JUPITER ->
                "Expansion and growth in career. Wisdom in professional decisions. Teaching or advisory roles possible."

            to == Planet.MARS ->
                "Dynamic energy for career. Initiative and courage needed. Technical or competitive fields favored."

            to == Planet.MERCURY ->
                "Communication and intellectual work emphasized. Business, writing, or analytical roles highlighted."

            to == Planet.VENUS ->
                "Creative and aesthetic work favored. Diplomacy in career. Arts, luxury, or beauty industries benefit."

            to == Planet.MOON ->
                "Public-facing work increases. Emotional intelligence important. Nurturing or service roles emphasized."

            else -> "Career continues with gradual shifts. Maintain steady effort."
        }
    }

    private fun generateHealthPrediction(from: Planet, to: Planet, intensity: SandhiIntensity): String {
        val healthVulnerabilities = buildString {
            append("Health considerations during this Sandhi: ")

            when (to) {
                Planet.SUN -> append("Watch for heart, eyes, and vitality. Avoid overexertion.")
                Planet.MOON -> append("Mental health and emotional balance important. Sleep quality needs attention.")
                Planet.MARS -> append("Accidents, inflammation, and blood pressure. Control anger and impulsive actions.")
                Planet.MERCURY -> append("Nervous system and skin. Mental stress and anxiety possible.")
                Planet.JUPITER -> append("Liver, weight gain, and excess. Moderation in diet needed.")
                Planet.VENUS -> append("Kidneys, diabetes risk, and reproductive health. Avoid overindulgence.")
                Planet.SATURN -> append("Joints, bones, chronic conditions. Cold and vata-related issues.")
                Planet.RAHU -> append("Mysterious ailments, poisoning risk, mental confusion. Avoid intoxicants.")
                Planet.KETU -> append("Accidents, infections, sudden issues. Spiritual healing beneficial.")
                else -> append("General health maintenance recommended.")
            }

            if (intensity == SandhiIntensity.CRITICAL || intensity == SandhiIntensity.HIGH) {
                append(" Extra vigilance required during this intense transition.")
            }
        }

        return healthVulnerabilities
    }

    private fun generateRelationshipPrediction(from: Planet, to: Planet): String {
        return when {
            to == Planet.VENUS ->
                "Relationships highlighted. Romance, marriage, and partnerships favored. Social life expands."

            to == Planet.MOON ->
                "Emotional connections deepen. Family matters important. Mother figure significant."

            to == Planet.JUPITER ->
                "Wisdom in relationships. Mentors and teachers appear. Married life benefits."

            to == Planet.MARS ->
                "Passion increases but so does conflict potential. Assertiveness in relationships."

            to == Planet.SATURN ->
                "Relationship responsibilities. Commitment tests. Older partners or serious bonds."

            to == Planet.MERCURY ->
                "Communication in relationships crucial. Intellectual connections. Siblings highlighted."

            to == Planet.SUN ->
                "Father figure prominent. Authority in relationships. Ego balance needed."

            to in listOf(Planet.RAHU, Planet.KETU) ->
                "Unconventional relationships or karmic connections. Past-life patterns may surface."

            else -> "Relationships undergo gradual changes. Maintain balance and understanding."
        }
    }

    private fun generateFinancialPrediction(from: Planet, to: Planet): String {
        return when {
            to == Planet.JUPITER ->
                "Financial expansion and opportunities. Investments and speculation may succeed. Generosity increases wealth."

            to == Planet.VENUS ->
                "Luxury spending increases. Income through arts or beauty. Balanced finances through diplomacy."

            to == Planet.SATURN ->
                "Financial discipline essential. Savings important. Slow but steady wealth building."

            to == Planet.MERCURY ->
                "Business and trading opportunities. Income through communication or intellect."

            to == Planet.SUN ->
                "Government or authority-related income. Leadership brings wealth. Ego spending possible."

            to == Planet.MARS ->
                "Quick gains or losses possible. Impulsive spending needs control. Property or vehicles."

            to == Planet.MOON ->
                "Fluctuating finances. Liquid assets important. Mother's wealth or property matters."

            to == Planet.RAHU ->
                "Sudden financial opportunities or challenges. Foreign income possible. Speculative risks."

            to == Planet.KETU ->
                "Detachment from material wealth. Spiritual investments. Unexpected losses possible."

            else -> "Financial situation remains stable with minor adjustments."
        }
    }

    private fun generateSpiritualPrediction(from: Planet, to: Planet): String {
        return when {
            to == Planet.KETU ->
                "Excellent for spiritual growth. Meditation and detachment deepen. Past-life insights emerge."

            to == Planet.JUPITER ->
                "Wisdom and dharmic understanding expand. Teachers appear. Religious practices beneficial."

            to == Planet.SUN ->
                "Soul purpose clarity. Self-realization opportunities. Inner strength develops."

            to == Planet.MOON ->
                "Emotional healing and intuition strengthen. Devotional practices favored."

            to == Planet.SATURN ->
                "Discipline in spiritual practice. Karma yoga path. Service leads to growth."

            to in listOf(Planet.VENUS, Planet.MERCURY) ->
                "Bhakti and intellectual spiritual paths. Arts as meditation. Mantra practices effective."

            to in listOf(Planet.MARS, Planet.RAHU) ->
                "Tantric or energy-based practices. Kundalini awareness. Transform aggression to devotion."

            else -> "Continue existing spiritual practices with renewed commitment."
        }
    }

    private fun generateKeyDates(sandhi: DashaCalculator.DashaSandhi): List<KeyDatePrediction> {
        val dates = mutableListOf<KeyDatePrediction>()

        // Sandhi start
        dates.add(KeyDatePrediction(
            date = sandhi.sandhiStartDate,
            event = "Sandhi Period Begins",
            significance = "Transition effects start becoming noticeable"
        ))

        // Midpoint - most intense
        val midpoint = sandhi.sandhiStartDate.plusDays(
            ChronoUnit.DAYS.between(sandhi.sandhiStartDate, sandhi.sandhiEndDate) / 2
        )
        dates.add(KeyDatePrediction(
            date = midpoint,
            event = "Sandhi Peak",
            significance = "Maximum transition intensity - key decisions or events likely"
        ))

        // Exact transition
        dates.add(KeyDatePrediction(
            date = sandhi.transitionDate,
            event = "Exact Dasha Change",
            significance = "Shift from ${sandhi.fromPlanet.displayName} to ${sandhi.toPlanet.displayName} complete"
        ))

        // Sandhi end
        dates.add(KeyDatePrediction(
            date = sandhi.sandhiEndDate,
            event = "Sandhi Period Ends",
            significance = "New Dasha energy stabilizes"
        ))

        return dates
    }

    /**
     * Generate remedies for Sandhi period
     */
    private fun generateRemedies(
        sandhi: DashaCalculator.DashaSandhi,
        intensity: SandhiIntensity,
        transitionType: TransitionType
    ): List<SandhiRemedy> {
        val remedies = mutableListOf<SandhiRemedy>()

        // Priority 1: Mantra for outgoing planet (to honor and release)
        remedies.add(SandhiRemedy(
            type = RemedyType.MANTRA,
            description = "Chant ${sandhi.fromPlanet.displayName} mantra 108 times daily to honor the concluding period",
            timing = "During Sandhi start phase",
            priority = 1
        ))

        // Priority 1: Mantra for incoming planet (to welcome)
        remedies.add(SandhiRemedy(
            type = RemedyType.MANTRA,
            description = "Begin ${sandhi.toPlanet.displayName} mantra 108 times daily to align with new energy",
            timing = "Starting from Sandhi midpoint",
            priority = 1
        ))

        // Donation based on planets
        remedies.add(SandhiRemedy(
            type = RemedyType.DONATION,
            description = getDonationRemedy(sandhi.fromPlanet, sandhi.toPlanet),
            timing = "On the day of exact transition",
            priority = 2
        ))

        // Fasting if intensity is high
        if (intensity in listOf(SandhiIntensity.CRITICAL, SandhiIntensity.HIGH)) {
            remedies.add(SandhiRemedy(
                type = RemedyType.FASTING,
                description = "Observe light fasting on ${sandhi.toPlanet.displayName}'s day during Sandhi",
                timing = "Weekly during Sandhi period",
                priority = 2
            ))
        }

        // Gemstone consideration
        remedies.add(SandhiRemedy(
            type = RemedyType.GEMSTONE,
            description = "Consider wearing ${sandhi.toPlanet.displayName} gemstone after consulting an astrologer",
            timing = "After Sandhi ends, on auspicious day",
            priority = 3
        ))

        // Puja for difficult transitions
        if (transitionType.difficultyLevel >= 4) {
            remedies.add(SandhiRemedy(
                type = RemedyType.PUJA,
                description = "Perform Graha Shanti puja for ${sandhi.toPlanet.displayName} to ease transition",
                timing = "Before or during Sandhi",
                priority = 1
            ))
        }

        // Lifestyle adjustments
        remedies.add(SandhiRemedy(
            type = RemedyType.LIFESTYLE,
            description = "Maintain regular routine, avoid major decisions, practice patience",
            timing = "Throughout Sandhi period",
            priority = 2
        ))

        return remedies.sortedBy { it.priority }
    }

    private fun getDonationRemedy(from: Planet, to: Planet): String {
        val fromDonation = when (from) {
            Planet.SUN -> "wheat, jaggery, or copper"
            Planet.MOON -> "rice, milk, or white cloth"
            Planet.MARS -> "red lentils, coral, or red cloth"
            Planet.MERCURY -> "green moong, emerald items, or green cloth"
            Planet.JUPITER -> "turmeric, yellow items, or gold"
            Planet.VENUS -> "white items, sugar, or silver"
            Planet.SATURN -> "black sesame, iron, or blue/black cloth"
            Planet.RAHU -> "black/blue items or coconut"
            Planet.KETU -> "multi-colored items or blanket"
            else -> "items related to both planets"
        }

        return "Donate $fromDonation to honor the departing ${from.displayName} energy"
    }

    /**
     * Analyze affected life areas
     */
    private fun analyzeAffectedLifeAreas(
        sandhi: DashaCalculator.DashaSandhi,
        chart: VedicChart
    ): List<LifeAreaImpact> {
        val areas = mutableListOf<LifeAreaImpact>()

        // Get house positions of involved planets
        val fromPos = chart.planetPositions.find { it.planet == sandhi.fromPlanet }
        val toPos = chart.planetPositions.find { it.planet == sandhi.toPlanet }

        // Analyze based on planetary karakatvas (significations)
        val karakatvas = getPlanetaryKarakatvas(sandhi.toPlanet)
        karakatvas.forEach { (area, impact) ->
            areas.add(LifeAreaImpact(
                area = area,
                impactLevel = impact,
                nature = if (impact >= 4) ImpactNature.TRANSFORMATIVE else ImpactNature.NEUTRAL,
                description = "Primary signification of ${sandhi.toPlanet.displayName}"
            ))
        }

        // Add house-based impacts
        if (toPos != null) {
            val houseArea = getHouseLifeArea(toPos.house)
            areas.add(LifeAreaImpact(
                area = houseArea,
                impactLevel = 4,
                nature = ImpactNature.TRANSFORMATIVE,
                description = "House ${toPos.house} matters activated"
            ))
        }

        return areas.distinctBy { it.area }
    }

    private fun getPlanetaryKarakatvas(planet: Planet): List<Pair<String, Int>> {
        return when (planet) {
            Planet.SUN -> listOf("Career/Authority" to 4, "Father" to 3, "Health" to 3)
            Planet.MOON -> listOf("Mind/Emotions" to 5, "Mother" to 4, "Home" to 3)
            Planet.MARS -> listOf("Courage/Energy" to 4, "Property" to 3, "Siblings" to 3)
            Planet.MERCURY -> listOf("Communication" to 5, "Business" to 4, "Education" to 3)
            Planet.JUPITER -> listOf("Wisdom/Fortune" to 5, "Children" to 4, "Spirituality" to 4)
            Planet.VENUS -> listOf("Relationships" to 5, "Finances" to 4, "Comforts" to 3)
            Planet.SATURN -> listOf("Career/Discipline" to 4, "Longevity" to 3, "Service" to 3)
            Planet.RAHU -> listOf("Worldly Desires" to 4, "Foreign" to 3, "Innovation" to 3)
            Planet.KETU -> listOf("Spirituality" to 5, "Liberation" to 4, "Past Karma" to 4)
            else -> listOf("General Life" to 3)
        }
    }

    private fun getHouseLifeArea(house: Int): String {
        return when (house) {
            1 -> "Self & Personality"
            2 -> "Wealth & Family"
            3 -> "Siblings & Communication"
            4 -> "Home & Mother"
            5 -> "Children & Creativity"
            6 -> "Health & Enemies"
            7 -> "Marriage & Partnership"
            8 -> "Transformation & Secrets"
            9 -> "Fortune & Higher Learning"
            10 -> "Career & Status"
            11 -> "Gains & Aspirations"
            12 -> "Losses & Liberation"
            else -> "General Life"
        }
    }

    /**
     * Generate interpretation text
     */
    private fun generateInterpretation(
        sandhi: DashaCalculator.DashaSandhi,
        intensity: SandhiIntensity,
        transitionType: TransitionType,
        isCurrently: Boolean
    ): String {
        val levelName = sandhi.level.displayName
        val fromName = sandhi.fromPlanet.displayName
        val toName = sandhi.toPlanet.displayName

        val timeContext = if (isCurrently) "You are currently experiencing" else "Upcoming"

        return buildString {
            append("$timeContext $levelName Sandhi: Transition from $fromName to $toName. ")
            append("This ${intensity.displayName.lowercase()} intensity period ")

            when (transitionType) {
                TransitionType.FRIEND_TO_FRIEND ->
                    append("benefits from harmonious planetary energies. ")

                TransitionType.ENEMY_TO_ENEMY ->
                    append("presents challenges due to conflicting planetary energies. Extra care needed. ")

                TransitionType.FRIEND_TO_ENEMY, TransitionType.NEUTRAL_TO_ENEMY ->
                    append("marks a shift from support to challenges. Prepare for adjustments. ")

                TransitionType.ENEMY_TO_FRIEND, TransitionType.NEUTRAL_TO_FRIEND ->
                    append("brings relief as energies become more supportive. ")

                else ->
                    append("involves gradual energy shifts. ")
            }

            append("The $toName period will emphasize ${getPlanetaryKarakatvas(sandhi.toPlanet).firstOrNull()?.first ?: "life matters"}. ")

            if (intensity == SandhiIntensity.CRITICAL || intensity == SandhiIntensity.HIGH) {
                append("This is a significant transition - follow recommended remedies for smooth navigation.")
            }
        }
    }

    // ============================================================================
    // HELPER METHODS
    // ============================================================================

    private fun findRecentPastSandhis(
        timeline: DashaCalculator.DashaTimeline,
        fromDate: LocalDate,
        lookBackDays: Int
    ): List<DashaCalculator.DashaSandhi> {
        val startDate = fromDate.minusDays(lookBackDays.toLong())
        return timeline.upcomingSandhis.filter {
            it.transitionDate.isAfter(startDate) && it.transitionDate.isBefore(fromDate)
        }
    }

    private fun buildSandhiCalendar(
        sandhis: List<SandhiAnalysis>,
        startDate: LocalDate,
        days: Int
    ): List<SandhiCalendarEntry> {
        return sandhis.map { analysis ->
            SandhiCalendarEntry(
                date = analysis.sandhi.transitionDate,
                sandhiType = analysis.sandhi.level.displayName,
                fromPlanet = analysis.sandhi.fromPlanet,
                toPlanet = analysis.sandhi.toPlanet,
                level = analysis.sandhi.level,
                intensity = analysis.intensity
            )
        }.sortedBy { it.date }
    }

    private fun calculateVolatilityScore(
        current: SandhiAnalysis?,
        upcoming: List<SandhiAnalysis>,
        lookAheadDays: Int
    ): Int {
        var score = 0

        // Current Sandhi adds significant volatility
        current?.let {
            score += when (it.intensity) {
                SandhiIntensity.CRITICAL -> 40
                SandhiIntensity.HIGH -> 30
                SandhiIntensity.MODERATE -> 20
                SandhiIntensity.MILD -> 10
                SandhiIntensity.MINIMAL -> 5
            }
        }

        // Upcoming Sandhis add based on proximity and intensity
        upcoming.forEach { analysis ->
            val proximityFactor = (1.0 - (analysis.daysUntilTransition.toDouble() / lookAheadDays)).coerceIn(0.0, 1.0)
            val intensityFactor = when (analysis.intensity) {
                SandhiIntensity.CRITICAL -> 15
                SandhiIntensity.HIGH -> 10
                SandhiIntensity.MODERATE -> 6
                SandhiIntensity.MILD -> 3
                SandhiIntensity.MINIMAL -> 1
            }
            score += (intensityFactor * proximityFactor).toInt()
        }

        return score.coerceIn(0, 100)
    }

    private fun generateOverallGuidance(
        current: SandhiAnalysis?,
        upcoming: List<SandhiAnalysis>,
        volatilityScore: Int
    ): String {
        return buildString {
            when {
                volatilityScore >= 70 -> {
                    append("HIGH TRANSITION PERIOD: Multiple or intense Sandhi periods ahead. ")
                    append("This is a time of significant life changes. Maintain flexibility, ")
                    append("follow remedial measures, and avoid major decisions unless necessary. ")
                    append("Focus on spiritual practices and inner balance.")
                }

                volatilityScore >= 40 -> {
                    append("MODERATE TRANSITION PERIOD: Notable changes occurring or approaching. ")
                    append("Stay aware of shifting energies and be prepared to adapt. ")
                    append("Good time for reflection and gradual adjustments.")
                }

                volatilityScore >= 20 -> {
                    append("STABLE PERIOD WITH MINOR TRANSITIONS: Life flows relatively smoothly. ")
                    append("Minor adjustments needed but no major disruptions expected. ")
                    append("Good time for steady progress on existing goals.")
                }

                else -> {
                    append("HIGHLY STABLE PERIOD: Minimal Dasha transitions in the near future. ")
                    append("Current planetary energies are settled. ")
                    append("Excellent time for long-term planning and major initiatives.")
                }
            }

            current?.let {
                append("\n\nCurrent Focus: You are in ${it.sandhi.level.displayName} Sandhi - ")
                append("${it.sandhi.fromPlanet.displayName} to ${it.sandhi.toPlanet.displayName}. ")
                append("${it.interpretation}")
            }
        }
    }

    /**
     * Generate plain text report
     */
    fun generateReport(analysis: CompleteSandhiAnalysis, language: Language = Language.ENGLISH): String {
        return buildString {
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine("           DASHA SANDHI ANALYSIS REPORT")
            appendLine("═══════════════════════════════════════════════════════════")
            appendLine()
            appendLine("Overall Volatility Score: ${analysis.overallVolatilityScore}%")
            appendLine()
            appendLine("General Guidance:")
            appendLine(analysis.generalGuidance)
            appendLine()

            analysis.currentSandhi?.let { current ->
                appendLine("─────────────────────────────────────────────────────────")
                appendLine("CURRENT SANDHI (Active Now)")
                appendLine("─────────────────────────────────────────────────────────")
                appendLine("Transition: ${current.sandhi.fromPlanet.displayName} → ${current.sandhi.toPlanet.displayName}")
                appendLine("Level: ${current.sandhi.level.displayName}")
                appendLine("Intensity: ${current.intensity.displayName}")
                appendLine("Progress: ${String.format("%.1f", current.sandhiProgress)}%")
                appendLine()
                appendLine("Interpretation:")
                appendLine(current.interpretation)
                appendLine()
                appendLine("Predictions:")
                appendLine("  Career: ${current.predictions.careerEffects}")
                appendLine("  Health: ${current.predictions.healthConcerns}")
                appendLine("  Relationships: ${current.predictions.relationshipImpact}")
                appendLine("  Finances: ${current.predictions.financialTrend}")
                appendLine()
                appendLine("Key Remedies:")
                current.remedies.filter { it.priority <= 2 }.forEach { remedy ->
                    appendLine("  • ${remedy.description} (${remedy.timing})")
                }
                appendLine()
            }

            if (analysis.upcomingSandhis.isNotEmpty()) {
                appendLine("─────────────────────────────────────────────────────────")
                appendLine("UPCOMING SANDHIS")
                appendLine("─────────────────────────────────────────────────────────")
                analysis.upcomingSandhis.take(5).forEach { sandhi ->
                    appendLine()
                    appendLine("${sandhi.sandhi.fromPlanet.displayName} → ${sandhi.sandhi.toPlanet.displayName}")
                    appendLine("  Level: ${sandhi.sandhi.level.displayName}")
                    appendLine("  Date: ${sandhi.sandhi.transitionDate}")
                    appendLine("  Days Until: ${sandhi.daysUntilTransition}")
                    appendLine("  Intensity: ${sandhi.intensity.displayName}")
                }
            }

            appendLine()
            appendLine("─────────────────────────────────────────────────────────")
            appendLine("SANDHI CALENDAR")
            appendLine("─────────────────────────────────────────────────────────")
            analysis.sandhiCalendar.forEach { entry ->
                appendLine("${entry.date}: ${entry.fromPlanet.displayName} → ${entry.toPlanet.displayName} (${entry.sandhiType}) [${entry.intensity.displayName}]")
            }
        }
    }
}
