package com.astro.storm.ephemeris

import com.astro.storm.data.model.Planet
import com.astro.storm.data.model.PlanetPosition
import com.astro.storm.data.model.VedicChart
import com.astro.storm.data.model.ZodiacSign
import java.time.LocalDate
import kotlin.math.abs

/**
 * Arudha Pada Calculator (Jaimini System)
 *
 * Arudha Padas are the "image" or "manifestation" points that show how
 * the matters of each house manifest in the material world. They are
 * essential for understanding worldly perception and material outcomes.
 *
 * ## Calculation Method (per Jaimini Sutras)
 *
 * For a house H with lord L in position P:
 * 1. Count the distance from H to P (inclusive)
 * 2. Count the same distance forward from P
 * 3. The resulting sign is the Arudha Pada of H
 *
 * ## Exception Rules (Classical)
 * - If Arudha falls in the same house or 7th from it, move 10 signs forward
 * - Some scholars use: if in 1st/7th, then take 4th/10th respectively
 *
 * ## Important Arudhas
 * - A1 (Arudha Lagna/AL): Public image, status, maya
 * - A7 (Darapada): Business partnerships, trade
 * - A10 (Rajyapada): Career manifestation, authority
 * - A11 (Labha Pada): Gains, fulfillment of desires
 * - A12 (Upapada/UL): Spouse, marital matters
 *
 * ## References
 * - Jaimini Sutras (Chapter 1, Pada 1)
 * - BPHS Chapter 29-30 on Arudha
 * - Commentary by Raghunatha Bhatta
 * - Sanjay Rath's "Crux of Vedic Astrology"
 *
 * @author AstroStorm
 */
object ArudhaPadaCalculator {

    // ============================================
    // DATA CLASSES
    // ============================================

    /**
     * Complete Arudha Pada analysis result
     */
    data class ArudhaPadaAnalysis(
        val ascendantSign: ZodiacSign,
        val arudhaPadas: List<ArudhaPada>,
        val specialArudhas: SpecialArudhas,
        val arudhaYogas: List<ArudhaYoga>,
        val arudhaRelationships: List<ArudhaRelationship>,
        val transitEffects: List<ArudhaTransitEffect>,
        val dashaActivation: List<ArudhaDashaActivation>,
        val overallAssessment: ArudhaOverallAssessment,
        val interpretation: ArudhaInterpretation
    )

    /**
     * Individual Arudha Pada information
     */
    data class ArudhaPada(
        val house: Int,                    // Original house (1-12)
        val name: String,                  // e.g., "A1", "A7", "AL", "UL"
        val fullName: String,              // e.g., "Arudha Lagna", "Upapada"
        val sign: ZodiacSign,              // Sign where Arudha falls
        val signDegree: Double,            // Degree in the sign (0-30)
        val houseLord: Planet,             // Lord of the original house
        val houseLordSign: ZodiacSign,     // Sign where house lord is placed
        val houseLordHouse: Int,           // House where house lord is placed
        val planetsInArudha: List<PlanetPosition>,  // Planets in the Arudha sign
        val strength: ArudhaStrength,      // Strength assessment
        val significations: List<String>,  // What this Arudha signifies
        val interpretation: String         // Detailed interpretation
    )

    /**
     * Special Arudhas with detailed analysis
     */
    data class SpecialArudhas(
        val arudhaLagna: ArudhaPadaDetail,     // AL - Public image
        val upapada: ArudhaPadaDetail,          // UL - Spouse
        val darapada: ArudhaPadaDetail,         // A7 - Business/Partners
        val labhaPada: ArudhaPadaDetail,        // A11 - Gains
        val rajyaPada: ArudhaPadaDetail,        // A10 - Career/Authority
        val mantriPada: ArudhaPadaDetail,       // A5 - Intelligence/Counsel
        val shatruPada: ArudhaPadaDetail        // A6 - Enemies/Diseases
    )

    /**
     * Detailed Arudha information for special Arudhas
     */
    data class ArudhaPadaDetail(
        val arudha: ArudhaPada,
        val arudhaLord: Planet,
        val arudhaLordSign: ZodiacSign,
        val arudhaLordHouse: Int,
        val dignityOfLord: PlanetaryDignity,
        val beneficsInArudha: List<Planet>,
        val maleficsInArudha: List<Planet>,
        val aspectsOnArudha: List<AspectOnArudha>,
        val detailedInterpretation: DetailedArudhaInterpretation
    )

    /**
     * Yoga formed by Arudha positions
     */
    data class ArudhaYoga(
        val name: String,
        val type: ArudhaYogaType,
        val involvedArudhas: List<String>,
        val involvedSigns: List<ZodiacSign>,
        val strength: YogaStrength,
        val effects: String,
        val timing: String,
        val recommendations: List<String>
    )

    /**
     * Relationship between two Arudhas
     */
    data class ArudhaRelationship(
        val arudha1: String,
        val arudha2: String,
        val distanceInSigns: Int,
        val relationship: RelationshipType,
        val effect: String,
        val isPositive: Boolean
    )

    /**
     * Transit effects on Arudha positions
     */
    data class ArudhaTransitEffect(
        val arudha: String,
        val arudhaSign: ZodiacSign,
        val transitingPlanet: Planet,
        val transitSign: ZodiacSign,
        val aspectType: String,
        val effect: String,
        val intensity: EffectIntensity,
        val approximateTiming: String
    )

    /**
     * Dasha activation of Arudha matters
     */
    data class ArudhaDashaActivation(
        val arudha: String,
        val activatingPeriod: String,
        val activatingPlanet: Planet,
        val activationReason: String,
        val expectedEffects: List<String>,
        val timing: String
    )

    /**
     * Overall assessment of Arudha placements
     */
    data class ArudhaOverallAssessment(
        val publicImageStrength: Int,      // 1-100
        val materialSuccessIndicator: Int,  // 1-100
        val relationshipIndicator: Int,     // 1-100
        val careerManifestationStrength: Int, // 1-100
        val gainsAndFulfillment: Int,      // 1-100
        val overallMayaStrength: Int,      // 1-100 - how strongly material world manifests
        val keyThemes: List<String>,
        val strengthAreas: List<String>,
        val challengeAreas: List<String>
    )

    /**
     * Complete interpretation
     */
    data class ArudhaInterpretation(
        val summary: String,
        val publicPerception: String,
        val materialLife: String,
        val relationshipManifestation: String,
        val careerAndAuthority: String,
        val recommendations: List<String>
    )

    // Supporting types
    data class AspectOnArudha(
        val planet: Planet,
        val aspectType: String,
        val nature: String,  // Benefic/Malefic
        val effect: String
    )

    data class DetailedArudhaInterpretation(
        val primaryMeaning: String,
        val secondaryEffects: List<String>,
        val timingOfResults: String,
        val remedialMeasures: List<String>
    )

    enum class ArudhaStrength {
        VERY_STRONG,  // Benefics in Arudha, lord well-placed
        STRONG,       // Good placement, some support
        MODERATE,     // Mixed influences
        WEAK,         // Malefic influences
        VERY_WEAK     // Severely afflicted
    }

    enum class ArudhaYogaType {
        RAJA_YOGA,        // Royal combinations from Arudhas
        DHANA_YOGA,       // Wealth combinations
        PARIVARTANA,      // Exchange between Arudhas
        ARGALA_YOGA,      // Intervention on Arudha
        BHAVA_YOGA,       // House-based combinations
        GRAHA_YOGA        // Planet-based combinations
    }

    enum class YogaStrength {
        EXCEPTIONAL, STRONG, MODERATE, MILD, WEAK
    }

    enum class RelationshipType {
        CONJUNCTION,      // Same sign
        TRINE,           // 1st, 5th, 9th
        KENDRA,          // 1st, 4th, 7th, 10th
        OPPOSITION,      // 7th
        DUSTHANA,        // 6th, 8th, 12th
        UPACHAYA,        // 3rd, 6th, 10th, 11th
        NEUTRAL          // Other
    }

    enum class EffectIntensity {
        VERY_HIGH, HIGH, MODERATE, LOW, NEGLIGIBLE
    }

    enum class PlanetaryDignity {
        EXALTED, OWN_SIGN, MOOLATRIKONA, FRIEND_SIGN, NEUTRAL_SIGN, ENEMY_SIGN, DEBILITATED
    }

    // ============================================
    // MAIN CALCULATION
    // ============================================

    /**
     * Calculate complete Arudha Pada analysis
     */
    fun analyzeArudhaPadas(chart: VedicChart): ArudhaPadaAnalysis {
        val ascendantSign = ZodiacSign.fromLongitude(chart.ascendant)

        // Calculate all 12 Arudha Padas
        val arudhaPadas = (1..12).map { house ->
            calculateArudhaPada(chart, house, ascendantSign)
        }

        // Get special Arudhas with detailed analysis
        val specialArudhas = analyzeSpecialArudhas(chart, arudhaPadas)

        // Calculate Arudha Yogas
        val arudhaYogas = calculateArudhaYogas(chart, arudhaPadas, specialArudhas)

        // Analyze Arudha-to-Arudha relationships
        val arudhaRelationships = analyzeArudhaRelationships(arudhaPadas)

        // Calculate transit effects on Arudhas
        val transitEffects = calculateTransitEffects(chart, arudhaPadas)

        // Calculate Dasha activation
        val dashaActivation = calculateDashaActivation(chart, arudhaPadas)

        // Overall assessment
        val overallAssessment = calculateOverallAssessment(chart, arudhaPadas, specialArudhas, arudhaYogas)

        // Generate interpretation
        val interpretation = generateInterpretation(chart, arudhaPadas, specialArudhas, arudhaYogas, overallAssessment)

        return ArudhaPadaAnalysis(
            ascendantSign = ascendantSign,
            arudhaPadas = arudhaPadas,
            specialArudhas = specialArudhas,
            arudhaYogas = arudhaYogas,
            arudhaRelationships = arudhaRelationships,
            transitEffects = transitEffects,
            dashaActivation = dashaActivation,
            overallAssessment = overallAssessment,
            interpretation = interpretation
        )
    }

    /**
     * Calculate Arudha Pada for a specific house
     */
    private fun calculateArudhaPada(
        chart: VedicChart,
        house: Int,
        ascendantSign: ZodiacSign
    ): ArudhaPada {
        // Get the sign of the house
        val houseSign = getSignOfHouse(ascendantSign, house)

        // Get the lord of that sign
        val houseLord = houseSign.ruler

        // Find where the house lord is placed
        val houseLordPosition = chart.planetPositions.find { it.planet == houseLord }
        val houseLordSign = houseLordPosition?.sign ?: houseSign
        val houseLordHouse = houseLordPosition?.house ?: house

        // Calculate Arudha position
        // Step 1: Count from house to house lord's position
        val houseSignOrdinal = houseSign.ordinal
        val lordSignOrdinal = houseLordSign.ordinal
        val distanceToLord = ((lordSignOrdinal - houseSignOrdinal + 12) % 12) + 1

        // Step 2: Count same distance from lord's position
        var arudhaOrdinal = (lordSignOrdinal + distanceToLord - 1) % 12
        var arudhaSign = ZodiacSign.entries[arudhaOrdinal]

        // Exception: If Arudha falls in same house or 7th from it
        val arudhaHouseFromOriginal = ((arudhaOrdinal - houseSignOrdinal + 12) % 12) + 1
        if (arudhaHouseFromOriginal == 1 || arudhaHouseFromOriginal == 7) {
            // Move 10 signs forward from original position
            arudhaOrdinal = (arudhaOrdinal + 9) % 12  // 10 signs = 9 indices
            arudhaSign = ZodiacSign.entries[arudhaOrdinal]
        }

        // Find planets in the Arudha sign
        val planetsInArudha = chart.planetPositions.filter { it.sign == arudhaSign }

        // Calculate strength
        val strength = calculateArudhaStrength(chart, arudhaSign, houseLord, houseLordPosition)

        // Get name and full name
        val (name, fullName) = getArudhaName(house)

        // Get significations
        val significations = getHouseSignifications(house)

        // Generate interpretation
        val interpretation = generateArudhaPadaInterpretation(
            house, arudhaSign, houseLord, houseLordSign, planetsInArudha, strength
        )

        return ArudhaPada(
            house = house,
            name = name,
            fullName = fullName,
            sign = arudhaSign,
            signDegree = 15.0, // Middle of sign as default
            houseLord = houseLord,
            houseLordSign = houseLordSign,
            houseLordHouse = houseLordHouse,
            planetsInArudha = planetsInArudha,
            strength = strength,
            significations = significations,
            interpretation = interpretation
        )
    }

    /**
     * Get the sign of a house based on ascendant
     */
    private fun getSignOfHouse(ascendant: ZodiacSign, house: Int): ZodiacSign {
        return ZodiacSign.entries[(ascendant.ordinal + house - 1) % 12]
    }

    /**
     * Calculate strength of Arudha placement
     */
    private fun calculateArudhaStrength(
        chart: VedicChart,
        arudhaSign: ZodiacSign,
        houseLord: Planet,
        houseLordPosition: PlanetPosition?
    ): ArudhaStrength {
        var strengthScore = 50 // Start at moderate

        // Check planets in Arudha sign
        val planetsInArudha = chart.planetPositions.filter { it.sign == arudhaSign }

        val benefics = listOf(Planet.JUPITER, Planet.VENUS, Planet.MERCURY, Planet.MOON)
        val malefics = listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU, Planet.SUN)

        // Benefics in Arudha increase strength
        val beneficCount = planetsInArudha.count { it.planet in benefics }
        strengthScore += beneficCount * 10

        // Malefics in Arudha decrease strength
        val maleficCount = planetsInArudha.count { it.planet in malefics }
        strengthScore -= maleficCount * 10

        // Check house lord's dignity
        houseLordPosition?.let { pos ->
            when {
                isExalted(pos.planet, pos.sign) -> strengthScore += 15
                isOwnSign(pos.planet, pos.sign) -> strengthScore += 10
                isDebilitated(pos.planet, pos.sign) -> strengthScore -= 15
            }
        }

        // Check if lord aspects Arudha
        houseLordPosition?.let { pos ->
            if (aspectsSign(pos.planet, pos.sign, arudhaSign)) {
                strengthScore += 10
            }
        }

        return when {
            strengthScore >= 80 -> ArudhaStrength.VERY_STRONG
            strengthScore >= 65 -> ArudhaStrength.STRONG
            strengthScore >= 45 -> ArudhaStrength.MODERATE
            strengthScore >= 30 -> ArudhaStrength.WEAK
            else -> ArudhaStrength.VERY_WEAK
        }
    }

    /**
     * Get Arudha name and full name based on house
     */
    private fun getArudhaName(house: Int): Pair<String, String> {
        return when (house) {
            1 -> "AL" to "Arudha Lagna (Pada Lagna)"
            2 -> "A2" to "Dhana Pada (Wealth Image)"
            3 -> "A3" to "Vikrama Pada (Courage Image)"
            4 -> "A4" to "Matri Pada (Mother/Property)"
            5 -> "A5" to "Mantri Pada (Intelligence/Counsel)"
            6 -> "A6" to "Shatru Pada (Enemy/Disease)"
            7 -> "A7" to "Dara Pada (Spouse/Business)"
            8 -> "A8" to "Mrityu Pada (Transformation)"
            9 -> "A9" to "Bhagya Pada (Fortune/Guru)"
            10 -> "A10" to "Rajya Pada (Career/Authority)"
            11 -> "A11" to "Labha Pada (Gains)"
            12 -> "UL" to "Upapada (Spouse Indicator)"
            else -> "A$house" to "Arudha of House $house"
        }
    }

    /**
     * Get significations for each house
     */
    private fun getHouseSignifications(house: Int): List<String> {
        return when (house) {
            1 -> listOf("Public image", "Physical appearance", "How others perceive you", "Maya/Illusion of self")
            2 -> listOf("Wealth perception", "Family status", "Speech and communication", "Food habits")
            3 -> listOf("Courage display", "Siblings' status", "Communication skills", "Short travels")
            4 -> listOf("Property matters", "Mother's image", "Vehicles", "Comfort and luxury")
            5 -> listOf("Intelligence display", "Children's image", "Speculative gains", "Counsel given")
            6 -> listOf("Enemies' strength", "Disease manifestation", "Debts", "Service conditions")
            7 -> listOf("Business partnerships", "Trade", "Public dealings", "Marriage manifestation")
            8 -> listOf("Hidden matters", "Insurance/Legacy", "Transformation", "Research")
            9 -> listOf("Guru/Father image", "Fortune manifestation", "Religious displays", "Higher learning")
            10 -> listOf("Career manifestation", "Authority", "Reputation", "Government dealings")
            11 -> listOf("Gains and profits", "Elder siblings", "Fulfillment of desires", "Networks")
            12 -> listOf("Spouse characteristics", "Marriage outcome", "Bedroom matters", "Foreign lands")
            else -> listOf()
        }
    }

    /**
     * Generate interpretation for individual Arudha Pada
     */
    private fun generateArudhaPadaInterpretation(
        house: Int,
        arudhaSign: ZodiacSign,
        houseLord: Planet,
        houseLordSign: ZodiacSign,
        planetsInArudha: List<PlanetPosition>,
        strength: ArudhaStrength
    ): String {
        val houseArea = when (house) {
            1 -> "public image and how you are perceived"
            2 -> "wealth manifestation and family status"
            3 -> "display of courage and sibling matters"
            4 -> "property and comfort in life"
            5 -> "intelligence recognition and children"
            6 -> "enemies, debts, and health matters"
            7 -> "business partnerships and marriage"
            8 -> "inheritance and hidden transformations"
            9 -> "fortune and spiritual pursuits"
            10 -> "career and authoritative position"
            11 -> "gains and fulfillment of desires"
            12 -> "spouse characteristics and marriage outcome"
            else -> "house $house matters"
        }

        val signNature = when (arudhaSign.element) {
            "Fire" -> "dynamic and visible"
            "Earth" -> "practical and tangible"
            "Air" -> "intellectual and social"
            "Water" -> "emotional and intuitive"
            else -> "mixed"
        }

        val strengthDesc = when (strength) {
            ArudhaStrength.VERY_STRONG -> "This Arudha is exceptionally strong, indicating powerful manifestation"
            ArudhaStrength.STRONG -> "This Arudha is well-placed for positive results"
            ArudhaStrength.MODERATE -> "This Arudha shows mixed results depending on periods"
            ArudhaStrength.WEAK -> "This Arudha needs strengthening for better outcomes"
            ArudhaStrength.VERY_WEAK -> "This Arudha is challenged and may struggle to manifest"
        }

        val planetEffects = if (planetsInArudha.isNotEmpty()) {
            val planetNames = planetsInArudha.joinToString(", ") { it.planet.displayName }
            "Planets in this Arudha ($planetNames) directly influence how $houseArea manifest in the material world."
        } else {
            "No planets directly in this Arudha; results depend primarily on the lord's condition."
        }

        return "The $houseArea falls in ${arudhaSign.displayName}, giving a $signNature quality to how these matters manifest. " +
                "The lord ${houseLord.displayName} is placed in ${houseLordSign.displayName}. " +
                "$strengthDesc. $planetEffects"
    }

    // ============================================
    // SPECIAL ARUDHAS ANALYSIS
    // ============================================

    private fun analyzeSpecialArudhas(
        chart: VedicChart,
        arudhaPadas: List<ArudhaPada>
    ): SpecialArudhas {
        return SpecialArudhas(
            arudhaLagna = analyzeSpecialArudha(chart, arudhaPadas[0]),     // AL - House 1
            upapada = analyzeSpecialArudha(chart, arudhaPadas[11]),         // UL - House 12
            darapada = analyzeSpecialArudha(chart, arudhaPadas[6]),         // A7 - House 7
            labhaPada = analyzeSpecialArudha(chart, arudhaPadas[10]),       // A11 - House 11
            rajyaPada = analyzeSpecialArudha(chart, arudhaPadas[9]),        // A10 - House 10
            mantriPada = analyzeSpecialArudha(chart, arudhaPadas[4]),       // A5 - House 5
            shatruPada = analyzeSpecialArudha(chart, arudhaPadas[5])        // A6 - House 6
        )
    }

    private fun analyzeSpecialArudha(
        chart: VedicChart,
        arudha: ArudhaPada
    ): ArudhaPadaDetail {
        val arudhaLord = arudha.sign.ruler
        val arudhaLordPosition = chart.planetPositions.find { it.planet == arudhaLord }
        val arudhaLordSign = arudhaLordPosition?.sign ?: arudha.sign
        val arudhaLordHouse = arudhaLordPosition?.house ?: 1

        val dignity = calculatePlanetDignity(arudhaLord, arudhaLordSign)

        val benefics = listOf(Planet.JUPITER, Planet.VENUS, Planet.MERCURY, Planet.MOON)
        val malefics = listOf(Planet.SATURN, Planet.MARS, Planet.RAHU, Planet.KETU, Planet.SUN)

        val beneficsInArudha = arudha.planetsInArudha
            .filter { it.planet in benefics }
            .map { it.planet }

        val maleficsInArudha = arudha.planetsInArudha
            .filter { it.planet in malefics }
            .map { it.planet }

        val aspectsOnArudha = calculateAspectsOnSign(chart, arudha.sign)

        val detailedInterpretation = generateDetailedArudhaInterpretation(
            arudha, arudhaLord, dignity, beneficsInArudha, maleficsInArudha, aspectsOnArudha
        )

        return ArudhaPadaDetail(
            arudha = arudha,
            arudhaLord = arudhaLord,
            arudhaLordSign = arudhaLordSign,
            arudhaLordHouse = arudhaLordHouse,
            dignityOfLord = dignity,
            beneficsInArudha = beneficsInArudha,
            maleficsInArudha = maleficsInArudha,
            aspectsOnArudha = aspectsOnArudha,
            detailedInterpretation = detailedInterpretation
        )
    }

    private fun calculatePlanetDignity(planet: Planet, sign: ZodiacSign): PlanetaryDignity {
        return when {
            isExalted(planet, sign) -> PlanetaryDignity.EXALTED
            isDebilitated(planet, sign) -> PlanetaryDignity.DEBILITATED
            isOwnSign(planet, sign) -> PlanetaryDignity.OWN_SIGN
            isMoolatrikona(planet, sign) -> PlanetaryDignity.MOOLATRIKONA
            isFriendSign(planet, sign) -> PlanetaryDignity.FRIEND_SIGN
            isEnemySign(planet, sign) -> PlanetaryDignity.ENEMY_SIGN
            else -> PlanetaryDignity.NEUTRAL_SIGN
        }
    }

    private fun calculateAspectsOnSign(chart: VedicChart, sign: ZodiacSign): List<AspectOnArudha> {
        val aspects = mutableListOf<AspectOnArudha>()

        chart.planetPositions.forEach { pos ->
            if (aspectsSign(pos.planet, pos.sign, sign)) {
                val benefics = listOf(Planet.JUPITER, Planet.VENUS, Planet.MERCURY)
                val nature = if (pos.planet in benefics) "Benefic" else "Malefic"
                val effect = when (pos.planet) {
                    Planet.JUPITER -> "Expands and blesses the matters"
                    Planet.VENUS -> "Adds beauty and harmony"
                    Planet.MARS -> "Adds energy but also conflicts"
                    Planet.SATURN -> "Delays but gives eventual stability"
                    Planet.SUN -> "Brings authority but also ego issues"
                    Planet.MOON -> "Adds emotional dimension"
                    Planet.MERCURY -> "Enhances communication"
                    Planet.RAHU -> "Creates illusions and worldly desires"
                    Planet.KETU -> "Brings detachment or spiritual twist"
                    else -> "Influences the manifestation"
                }
                aspects.add(AspectOnArudha(pos.planet, "Graha Drishti", nature, effect))
            }
        }

        return aspects
    }

    private fun generateDetailedArudhaInterpretation(
        arudha: ArudhaPada,
        lord: Planet,
        dignity: PlanetaryDignity,
        benefics: List<Planet>,
        malefics: List<Planet>,
        aspects: List<AspectOnArudha>
    ): DetailedArudhaInterpretation {
        val primaryMeaning = when (arudha.house) {
            1 -> "Your public image in ${arudha.sign.displayName} suggests ${getSignImageDescription(arudha.sign)}. " +
                    "People perceive you with ${arudha.sign.element} qualities."
            7 -> "Business partnerships and public dealings manifest through ${arudha.sign.displayName}. " +
                    "${getA7BusinessDescription(arudha.sign)}"
            10 -> "Your career and authority manifest through ${arudha.sign.displayName}. " +
                    "${getA10CareerDescription(arudha.sign)}"
            11 -> "Gains and fulfillment come through ${arudha.sign.displayName} qualities. " +
                    "${getA11GainsDescription(arudha.sign)}"
            12 -> "Your spouse characteristics are indicated by ${arudha.sign.displayName}. " +
                    "${getULSpouseDescription(arudha.sign)}"
            else -> "The matters of house ${arudha.house} manifest through ${arudha.sign.displayName}."
        }

        val secondaryEffects = mutableListOf<String>()

        if (benefics.isNotEmpty()) {
            secondaryEffects.add("Benefic planets (${benefics.joinToString { it.displayName }}) enhance positive outcomes")
        }
        if (malefics.isNotEmpty()) {
            secondaryEffects.add("Malefic planets (${malefics.joinToString { it.displayName }}) may create challenges")
        }

        val dignityEffect = when (dignity) {
            PlanetaryDignity.EXALTED -> "Lord exalted - excellent manifestation expected"
            PlanetaryDignity.OWN_SIGN -> "Lord in own sign - stable and reliable results"
            PlanetaryDignity.MOOLATRIKONA -> "Lord in moolatrikona - strong manifestation"
            PlanetaryDignity.FRIEND_SIGN -> "Lord in friendly sign - supportive results"
            PlanetaryDignity.NEUTRAL_SIGN -> "Lord in neutral sign - average manifestation"
            PlanetaryDignity.ENEMY_SIGN -> "Lord in enemy sign - struggles in manifestation"
            PlanetaryDignity.DEBILITATED -> "Lord debilitated - may face obstacles"
        }
        secondaryEffects.add(dignityEffect)

        val timingOfResults = "Results manifest strongly during ${lord.displayName} dasha/antardasha, " +
                "and when transits activate ${arudha.sign.displayName}"

        val remedialMeasures = generateRemediesForArudha(arudha, dignity, malefics)

        return DetailedArudhaInterpretation(
            primaryMeaning = primaryMeaning,
            secondaryEffects = secondaryEffects,
            timingOfResults = timingOfResults,
            remedialMeasures = remedialMeasures
        )
    }

    private fun getSignImageDescription(sign: ZodiacSign): String {
        return when (sign) {
            ZodiacSign.ARIES -> "a dynamic, pioneering, and leadership-oriented personality"
            ZodiacSign.TAURUS -> "a stable, wealthy, and comfort-loving image"
            ZodiacSign.GEMINI -> "an intellectual, communicative, and versatile personality"
            ZodiacSign.CANCER -> "a nurturing, emotional, and family-oriented image"
            ZodiacSign.LEO -> "a royal, authoritative, and creative personality"
            ZodiacSign.VIRGO -> "a practical, analytical, and service-oriented image"
            ZodiacSign.LIBRA -> "a balanced, diplomatic, and artistic personality"
            ZodiacSign.SCORPIO -> "a mysterious, intense, and transformative image"
            ZodiacSign.SAGITTARIUS -> "a philosophical, adventurous, and teaching personality"
            ZodiacSign.CAPRICORN -> "a professional, disciplined, and ambitious image"
            ZodiacSign.AQUARIUS -> "an innovative, humanitarian, and unconventional personality"
            ZodiacSign.PISCES -> "a spiritual, compassionate, and artistic image"
        }
    }

    private fun getA7BusinessDescription(sign: ZodiacSign): String {
        return when (sign.element) {
            "Fire" -> "Business success through leadership, entrepreneurship, and bold ventures"
            "Earth" -> "Business success through practical dealings, real estate, and steady growth"
            "Air" -> "Business success through communication, networking, and intellectual services"
            "Water" -> "Business success through intuition, healing, or emotionally satisfying services"
            else -> "Business success through various means"
        }
    }

    private fun getA10CareerDescription(sign: ZodiacSign): String {
        return when (sign.ruler) {
            Planet.SUN -> "Authority in government, administration, or leadership roles"
            Planet.MOON -> "Public-facing roles, hospitality, or nurturing professions"
            Planet.MARS -> "Technical, engineering, military, or competitive fields"
            Planet.MERCURY -> "Communication, commerce, writing, or analytical work"
            Planet.JUPITER -> "Teaching, law, consultancy, or spiritual guidance"
            Planet.VENUS -> "Arts, entertainment, luxury goods, or beauty industry"
            Planet.SATURN -> "Structured organizations, labor, or traditional fields"
            else -> "Diverse career manifestation"
        }
    }

    private fun getA11GainsDescription(sign: ZodiacSign): String {
        return when (sign.element) {
            "Fire" -> "Gains through initiative, leadership, and competitive ventures"
            "Earth" -> "Gains through property, steady investments, and practical work"
            "Air" -> "Gains through networking, intellectual pursuits, and communication"
            "Water" -> "Gains through intuition, creative work, and emotional intelligence"
            else -> "Gains through various channels"
        }
    }

    private fun getULSpouseDescription(sign: ZodiacSign): String {
        return when (sign) {
            ZodiacSign.ARIES -> "Spouse may be energetic, independent, and pioneering"
            ZodiacSign.TAURUS -> "Spouse may be attractive, stable, and comfort-loving"
            ZodiacSign.GEMINI -> "Spouse may be intellectual, communicative, and versatile"
            ZodiacSign.CANCER -> "Spouse may be nurturing, emotional, and family-oriented"
            ZodiacSign.LEO -> "Spouse may be dignified, creative, and authoritative"
            ZodiacSign.VIRGO -> "Spouse may be practical, analytical, and health-conscious"
            ZodiacSign.LIBRA -> "Spouse may be attractive, diplomatic, and artistic"
            ZodiacSign.SCORPIO -> "Spouse may be intense, mysterious, and transformative"
            ZodiacSign.SAGITTARIUS -> "Spouse may be philosophical, adventurous, and optimistic"
            ZodiacSign.CAPRICORN -> "Spouse may be professional, disciplined, and ambitious"
            ZodiacSign.AQUARIUS -> "Spouse may be innovative, independent, and unconventional"
            ZodiacSign.PISCES -> "Spouse may be spiritual, artistic, and compassionate"
        }
    }

    private fun generateRemediesForArudha(
        arudha: ArudhaPada,
        dignity: PlanetaryDignity,
        malefics: List<Planet>
    ): List<String> {
        val remedies = mutableListOf<String>()

        // Remedies based on dignity
        if (dignity == PlanetaryDignity.DEBILITATED || dignity == PlanetaryDignity.ENEMY_SIGN) {
            remedies.add("Strengthen ${arudha.sign.ruler.displayName} through mantra: ${getPlanetMantra(arudha.sign.ruler)}")
            remedies.add("Donate items related to ${arudha.sign.ruler.displayName} on its day")
        }

        // Remedies for malefics in Arudha
        malefics.forEach { malefic ->
            when (malefic) {
                Planet.SATURN -> remedies.add("Feed crows on Saturdays to pacify Saturn's influence on ${arudha.name}")
                Planet.RAHU -> remedies.add("Donate to disadvantaged on Saturdays to reduce Rahu's illusions")
                Planet.KETU -> remedies.add("Spiritual practices and meditation help channel Ketu's energy positively")
                Planet.MARS -> remedies.add("Donate red items on Tuesdays to channel Mars's energy constructively")
                else -> {}
            }
        }

        // General remedies
        remedies.add("Worship the deity associated with ${arudha.sign.displayName}")
        remedies.add("Act with integrity in ${arudha.fullName} matters for better manifestation")

        return remedies.distinct()
    }

    private fun getPlanetMantra(planet: Planet): String {
        return when (planet) {
            Planet.SUN -> "Om Suryaya Namaha"
            Planet.MOON -> "Om Chandraya Namaha"
            Planet.MARS -> "Om Mangalaya Namaha"
            Planet.MERCURY -> "Om Budhaya Namaha"
            Planet.JUPITER -> "Om Gurave Namaha"
            Planet.VENUS -> "Om Shukraya Namaha"
            Planet.SATURN -> "Om Shanaischaraya Namaha"
            Planet.RAHU -> "Om Rahave Namaha"
            Planet.KETU -> "Om Ketave Namaha"
            else -> "Om Navagraha Namaha"
        }
    }

    // ============================================
    // ARUDHA YOGAS
    // ============================================

    private fun calculateArudhaYogas(
        chart: VedicChart,
        arudhaPadas: List<ArudhaPada>,
        specialArudhas: SpecialArudhas
    ): List<ArudhaYoga> {
        val yogas = mutableListOf<ArudhaYoga>()

        val al = arudhaPadas[0]  // Arudha Lagna
        val a10 = arudhaPadas[9] // Rajya Pada
        val a11 = arudhaPadas[10] // Labha Pada
        val ul = arudhaPadas[11] // Upapada
        val a7 = arudhaPadas[6]  // Darapada

        // 1. Raja Yoga from AL-A10 connection
        val alToA10Distance = getSignDistance(al.sign, a10.sign)
        if (alToA10Distance in listOf(1, 5, 9)) { // Trine relationship
            yogas.add(ArudhaYoga(
                name = "Arudha Raja Yoga",
                type = ArudhaYogaType.RAJA_YOGA,
                involvedArudhas = listOf("AL", "A10"),
                involvedSigns = listOf(al.sign, a10.sign),
                strength = YogaStrength.STRONG,
                effects = "Public image and career are harmoniously connected. Recognition and authority manifest together.",
                timing = "Activates during dasha of AL or A10 lords",
                recommendations = listOf(
                    "Leverage public image for career advancement",
                    "Leadership roles bring recognition"
                )
            ))
        }

        // 2. Dhana Yoga from AL-A11 connection
        val alToA11Distance = getSignDistance(al.sign, a11.sign)
        if (alToA11Distance in listOf(1, 5, 9, 4, 7, 10)) { // Trine or Kendra
            yogas.add(ArudhaYoga(
                name = "Arudha Dhana Yoga",
                type = ArudhaYogaType.DHANA_YOGA,
                involvedArudhas = listOf("AL", "A11"),
                involvedSigns = listOf(al.sign, a11.sign),
                strength = if (alToA11Distance in listOf(1, 5, 9)) YogaStrength.STRONG else YogaStrength.MODERATE,
                effects = "Public image directly supports gains. Reputation brings financial success.",
                timing = "Strong during Jupiter and Venus transits to these Arudhas",
                recommendations = listOf(
                    "Build reputation to enhance income",
                    "Networking leads to financial opportunities"
                )
            ))
        }

        // 3. Marriage Yoga from AL-UL connection
        val alToULDistance = getSignDistance(al.sign, ul.sign)
        if (alToULDistance in listOf(1, 5, 9, 7)) {
            val strength = when (alToULDistance) {
                1 -> YogaStrength.EXCEPTIONAL
                5, 9 -> YogaStrength.STRONG
                7 -> YogaStrength.MODERATE
                else -> YogaStrength.MILD
            }
            yogas.add(ArudhaYoga(
                name = "Arudha Vivaha Yoga",
                type = ArudhaYogaType.BHAVA_YOGA,
                involvedArudhas = listOf("AL", "UL"),
                involvedSigns = listOf(al.sign, ul.sign),
                strength = strength,
                effects = "Public image and marriage are connected. Spouse enhances social standing.",
                timing = "Marriage timing indicated by UL lord dasha",
                recommendations = listOf(
                    "Spouse supports public image",
                    "Marriage enhances social status"
                )
            ))
        }

        // 4. Business Success from A7-A10-A11 connection
        val a7ToA10 = getSignDistance(a7.sign, a10.sign)
        val a7ToA11 = getSignDistance(a7.sign, a11.sign)
        if (a7ToA10 in listOf(1, 5, 9) || a7ToA11 in listOf(1, 5, 9)) {
            yogas.add(ArudhaYoga(
                name = "Vyapara Yoga",
                type = ArudhaYogaType.DHANA_YOGA,
                involvedArudhas = listOf("A7", "A10", "A11"),
                involvedSigns = listOf(a7.sign, a10.sign, a11.sign),
                strength = YogaStrength.STRONG,
                effects = "Business partnerships bring career growth and gains.",
                timing = "Mercury and Jupiter periods activate this yoga",
                recommendations = listOf(
                    "Business ventures are favored",
                    "Partnerships lead to gains"
                )
            ))
        }

        // 5. Check for planets creating Argala on AL
        chart.planetPositions.forEach { pos ->
            val distFromAL = getSignDistance(al.sign, pos.sign)
            if (distFromAL in listOf(2, 4, 11)) { // Argala positions
                val isJupiter = pos.planet == Planet.JUPITER
                val isVenus = pos.planet == Planet.VENUS
                if (isJupiter || isVenus) {
                    yogas.add(ArudhaYoga(
                        name = "Shubha Argala on Arudha Lagna",
                        type = ArudhaYogaType.ARGALA_YOGA,
                        involvedArudhas = listOf("AL"),
                        involvedSigns = listOf(al.sign, pos.sign),
                        strength = if (isJupiter) YogaStrength.STRONG else YogaStrength.MODERATE,
                        effects = "${pos.planet.displayName} creates positive intervention on public image from house $distFromAL",
                        timing = "Active throughout life, especially during ${pos.planet.displayName} periods",
                        recommendations = listOf(
                            "Benefic influence enhances reputation",
                            "Use this positive energy for public ventures"
                        )
                    ))
                }
            }
        }

        return yogas
    }

    // ============================================
    // ARUDHA RELATIONSHIPS
    // ============================================

    private fun analyzeArudhaRelationships(arudhaPadas: List<ArudhaPada>): List<ArudhaRelationship> {
        val relationships = mutableListOf<ArudhaRelationship>()

        // Key relationships to analyze
        val keyPairs = listOf(
            0 to 9,   // AL - A10 (Image - Career)
            0 to 10,  // AL - A11 (Image - Gains)
            0 to 11,  // AL - UL (Image - Spouse)
            6 to 9,   // A7 - A10 (Business - Career)
            6 to 10,  // A7 - A11 (Business - Gains)
            9 to 10,  // A10 - A11 (Career - Gains)
            4 to 9,   // A5 - A10 (Intelligence - Career)
            11 to 6   // UL - A7 (Spouse - Business)
        )

        keyPairs.forEach { (idx1, idx2) ->
            val a1 = arudhaPadas[idx1]
            val a2 = arudhaPadas[idx2]
            val distance = getSignDistance(a1.sign, a2.sign)
            val relType = getRelationshipType(distance)

            val (effect, isPositive) = getRelationshipEffect(a1.name, a2.name, relType, distance)

            relationships.add(ArudhaRelationship(
                arudha1 = a1.name,
                arudha2 = a2.name,
                distanceInSigns = distance,
                relationship = relType,
                effect = effect,
                isPositive = isPositive
            ))
        }

        return relationships
    }

    private fun getRelationshipType(distance: Int): RelationshipType {
        return when (distance) {
            1 -> RelationshipType.CONJUNCTION
            5, 9 -> RelationshipType.TRINE
            4, 7, 10 -> RelationshipType.KENDRA
            7 -> RelationshipType.OPPOSITION
            6, 8, 12 -> RelationshipType.DUSTHANA
            3, 6, 10, 11 -> RelationshipType.UPACHAYA
            else -> RelationshipType.NEUTRAL
        }
    }

    private fun getRelationshipEffect(
        a1: String,
        a2: String,
        relType: RelationshipType,
        distance: Int
    ): Pair<String, Boolean> {
        val isPositive = relType in listOf(
            RelationshipType.CONJUNCTION,
            RelationshipType.TRINE,
            RelationshipType.KENDRA,
            RelationshipType.UPACHAYA
        )

        val effect = when (relType) {
            RelationshipType.CONJUNCTION -> "$a1 and $a2 are in same sign - their matters are closely intertwined"
            RelationshipType.TRINE -> "$a1 and $a2 support each other harmoniously (trine relationship)"
            RelationshipType.KENDRA -> "$a1 and $a2 have strong mutual influence (kendra relationship)"
            RelationshipType.OPPOSITION -> "$a1 and $a2 face each other - balance needed between these areas"
            RelationshipType.DUSTHANA -> "$a1 and $a2 have challenging relationship - one may affect other negatively"
            RelationshipType.UPACHAYA -> "$a1 and $a2 relationship improves with time"
            RelationshipType.NEUTRAL -> "$a1 and $a2 have neutral relationship"
        }

        return effect to isPositive
    }

    // ============================================
    // TRANSIT EFFECTS
    // ============================================

    private fun calculateTransitEffects(
        chart: VedicChart,
        arudhaPadas: List<ArudhaPada>
    ): List<ArudhaTransitEffect> {
        val effects = mutableListOf<ArudhaTransitEffect>()

        // Current transits would need real-time ephemeris data
        // For now, we provide general transit guidance for key Arudhas

        val keyArudhas = listOf(
            arudhaPadas[0],  // AL
            arudhaPadas[6],  // A7
            arudhaPadas[9],  // A10
            arudhaPadas[10], // A11
            arudhaPadas[11]  // UL
        )

        val slowPlanets = listOf(Planet.JUPITER, Planet.SATURN, Planet.RAHU, Planet.KETU)

        keyArudhas.forEach { arudha ->
            slowPlanets.forEach { planet ->
                val effect = getTransitEffectDescription(planet, arudha)
                effects.add(ArudhaTransitEffect(
                    arudha = arudha.name,
                    arudhaSign = arudha.sign,
                    transitingPlanet = planet,
                    transitSign = arudha.sign, // Transit over Arudha sign
                    aspectType = "Conjunction/Transit",
                    effect = effect,
                    intensity = if (planet == Planet.JUPITER || planet == Planet.SATURN)
                        EffectIntensity.HIGH else EffectIntensity.MODERATE,
                    approximateTiming = "${planet.displayName} transit through ${arudha.sign.displayName}"
                ))
            }
        }

        return effects
    }

    private fun getTransitEffectDescription(planet: Planet, arudha: ArudhaPada): String {
        val area = when (arudha.house) {
            1 -> "public image"
            7 -> "business and partnerships"
            10 -> "career and authority"
            11 -> "gains and profits"
            12 -> "spouse matters"
            else -> "house ${arudha.house} matters"
        }

        return when (planet) {
            Planet.JUPITER -> "Jupiter transit brings expansion, opportunities, and blessings to $area. Favorable for growth."
            Planet.SATURN -> "Saturn transit brings structure, delays, but lasting results in $area. Tests and solidifies."
            Planet.RAHU -> "Rahu transit amplifies worldly desires and can bring sudden changes in $area. Watch for illusions."
            Planet.KETU -> "Ketu transit brings spiritual insights but may cause detachment in $area."
            else -> "Transit influences $area"
        }
    }

    // ============================================
    // DASHA ACTIVATION
    // ============================================

    private fun calculateDashaActivation(
        chart: VedicChart,
        arudhaPadas: List<ArudhaPada>
    ): List<ArudhaDashaActivation> {
        val activations = mutableListOf<ArudhaDashaActivation>()

        // Key Arudhas and their activation triggers
        val keyArudhas = listOf(
            arudhaPadas[0],  // AL
            arudhaPadas[6],  // A7
            arudhaPadas[9],  // A10
            arudhaPadas[10], // A11
            arudhaPadas[11]  // UL
        )

        keyArudhas.forEach { arudha ->
            val lord = arudha.sign.ruler

            // Primary activation - Lord's dasha
            activations.add(ArudhaDashaActivation(
                arudha = arudha.name,
                activatingPeriod = "${lord.displayName} Mahadasha/Antardasha",
                activatingPlanet = lord,
                activationReason = "Lord of ${arudha.name} sign activates ${arudha.fullName} matters",
                expectedEffects = getExpectedEffects(arudha),
                timing = "During ${lord.displayName} periods"
            ))

            // Secondary activation - Planets in Arudha
            arudha.planetsInArudha.forEach { planetPos ->
                activations.add(ArudhaDashaActivation(
                    arudha = arudha.name,
                    activatingPeriod = "${planetPos.planet.displayName} Dasha",
                    activatingPlanet = planetPos.planet,
                    activationReason = "${planetPos.planet.displayName} placed in ${arudha.name}",
                    expectedEffects = getExpectedEffectsWithPlanet(arudha, planetPos.planet),
                    timing = "During ${planetPos.planet.displayName} periods"
                ))
            }
        }

        return activations
    }

    private fun getExpectedEffects(arudha: ArudhaPada): List<String> {
        return when (arudha.house) {
            1 -> listOf(
                "Changes in public image and perception",
                "Recognition or reputation shifts",
                "Physical appearance changes"
            )
            7 -> listOf(
                "Business opportunities or challenges",
                "Partnership developments",
                "Public dealings intensify"
            )
            10 -> listOf(
                "Career changes or advancement",
                "Authority and position changes",
                "Professional recognition"
            )
            11 -> listOf(
                "Financial gains or fluctuations",
                "Desires manifest or face obstacles",
                "Network expansion"
            )
            12 -> listOf(
                "Spouse-related events",
                "Marriage developments",
                "Bedroom/private life changes"
            )
            else -> listOf("${arudha.fullName} matters activate")
        }
    }

    private fun getExpectedEffectsWithPlanet(arudha: ArudhaPada, planet: Planet): List<String> {
        val effects = mutableListOf<String>()

        val planetEffect = when (planet) {
            Planet.JUPITER -> "expansion, blessings, and growth"
            Planet.SATURN -> "structure, delays, and hard work"
            Planet.MARS -> "energy, competition, and drive"
            Planet.VENUS -> "harmony, beauty, and comfort"
            Planet.MERCURY -> "communication, commerce, and learning"
            Planet.SUN -> "authority, recognition, and ego"
            Planet.MOON -> "emotions, popularity, and changes"
            Planet.RAHU -> "ambition, worldly gains, and illusions"
            Planet.KETU -> "spirituality, detachment, and sudden changes"
            else -> "influence"
        }

        effects.add("${planet.displayName} brings $planetEffect to ${arudha.fullName} matters")

        return effects
    }

    // ============================================
    // OVERALL ASSESSMENT
    // ============================================

    private fun calculateOverallAssessment(
        chart: VedicChart,
        arudhaPadas: List<ArudhaPada>,
        specialArudhas: SpecialArudhas,
        yogas: List<ArudhaYoga>
    ): ArudhaOverallAssessment {
        // Calculate scores based on Arudha strengths and yogas

        val al = specialArudhas.arudhaLagna
        val a7 = specialArudhas.darapada
        val a10 = specialArudhas.rajyaPada
        val a11 = specialArudhas.labhaPada
        val ul = specialArudhas.upapada

        val publicImageStrength = calculateStrengthScore(al)
        val materialSuccess = (calculateStrengthScore(a10) + calculateStrengthScore(a11)) / 2
        val relationshipIndicator = (calculateStrengthScore(ul) + calculateStrengthScore(a7)) / 2
        val careerStrength = calculateStrengthScore(a10)
        val gainsIndicator = calculateStrengthScore(a11)

        // Overall maya strength - how strongly material world manifests
        val overallMaya = (publicImageStrength + materialSuccess + relationshipIndicator +
                careerStrength + gainsIndicator) / 5

        // Add yoga bonuses
        val yogaBonus = yogas.sumOf {
            when (it.strength) {
                YogaStrength.EXCEPTIONAL -> 10
                YogaStrength.STRONG -> 7
                YogaStrength.MODERATE -> 4
                YogaStrength.MILD -> 2
                YogaStrength.WEAK -> 0
            }
        }.coerceAtMost(20)

        val keyThemes = mutableListOf<String>()
        val strengthAreas = mutableListOf<String>()
        val challengeAreas = mutableListOf<String>()

        // Determine themes and areas
        if (publicImageStrength >= 60) {
            strengthAreas.add("Strong public image and recognition potential")
        } else if (publicImageStrength <= 40) {
            challengeAreas.add("Public image may need conscious cultivation")
        }

        if (careerStrength >= 60) {
            strengthAreas.add("Career manifestation is strong")
        }

        if (gainsIndicator >= 60) {
            strengthAreas.add("Good potential for financial gains")
        }

        if (relationshipIndicator >= 60) {
            strengthAreas.add("Favorable relationship manifestation")
        }

        // Yogas add to themes
        yogas.forEach { yoga ->
            if (yoga.strength in listOf(YogaStrength.EXCEPTIONAL, YogaStrength.STRONG)) {
                keyThemes.add(yoga.name)
            }
        }

        return ArudhaOverallAssessment(
            publicImageStrength = publicImageStrength,
            materialSuccessIndicator = materialSuccess + yogaBonus / 2,
            relationshipIndicator = relationshipIndicator,
            careerManifestationStrength = careerStrength,
            gainsAndFulfillment = gainsIndicator,
            overallMayaStrength = (overallMaya + yogaBonus).coerceAtMost(100),
            keyThemes = keyThemes.ifEmpty { listOf("Individual growth and development") },
            strengthAreas = strengthAreas.ifEmpty { listOf("Balanced manifestation across areas") },
            challengeAreas = challengeAreas.ifEmpty { listOf("No major challenging areas identified") }
        )
    }

    private fun calculateStrengthScore(detail: ArudhaPadaDetail): Int {
        var score = 50

        // Dignity of lord
        score += when (detail.dignityOfLord) {
            PlanetaryDignity.EXALTED -> 20
            PlanetaryDignity.OWN_SIGN -> 15
            PlanetaryDignity.MOOLATRIKONA -> 12
            PlanetaryDignity.FRIEND_SIGN -> 8
            PlanetaryDignity.NEUTRAL_SIGN -> 0
            PlanetaryDignity.ENEMY_SIGN -> -10
            PlanetaryDignity.DEBILITATED -> -15
        }

        // Benefics in Arudha
        score += detail.beneficsInArudha.size * 8

        // Malefics in Arudha
        score -= detail.maleficsInArudha.size * 6

        // Benefic aspects
        score += detail.aspectsOnArudha.count { it.nature == "Benefic" } * 5

        return score.coerceIn(10, 100)
    }

    // ============================================
    // INTERPRETATION GENERATION
    // ============================================

    private fun generateInterpretation(
        chart: VedicChart,
        arudhaPadas: List<ArudhaPada>,
        specialArudhas: SpecialArudhas,
        yogas: List<ArudhaYoga>,
        assessment: ArudhaOverallAssessment
    ): ArudhaInterpretation {
        val al = specialArudhas.arudhaLagna.arudha
        val ul = specialArudhas.upapada.arudha
        val a10 = specialArudhas.rajyaPada.arudha

        val summary = buildString {
            append("Your Arudha Lagna falls in ${al.sign.displayName}, suggesting that you are perceived as ${getSignImageDescription(al.sign)}. ")
            append("Career manifestation (A10) through ${a10.sign.displayName} indicates professional success in ${a10.sign.ruler.displayName}-related fields. ")
            if (yogas.isNotEmpty()) {
                append("You have ${yogas.size} significant Arudha Yogas enhancing material outcomes.")
            }
        }

        val publicPerception = buildString {
            append("The Arudha Lagna in ${al.sign.displayName} ")
            append("with lord ${al.houseLord.displayName} in ${al.houseLordSign.displayName} ")
            append("creates ${getSignImageDescription(al.sign)}. ")
            if (al.planetsInArudha.isNotEmpty()) {
                append("Planets in AL (${al.planetsInArudha.joinToString { it.planet.displayName }}) ")
                append("directly influence your public image.")
            }
        }

        val materialLife = buildString {
            val a11 = specialArudhas.labhaPada.arudha
            append("Material gains manifest through ${a11.sign.displayName} (A11). ")
            append("Your ${a10.sign.displayName} career manifestation and ${a11.sign.displayName} gains indicator ")
            append("suggest ${if (assessment.materialSuccessIndicator >= 60) "favorable" else "moderate"} ")
            append("material success potential.")
        }

        val relationshipManifestation = buildString {
            append("The Upapada (UL) in ${ul.sign.displayName} indicates ${getULSpouseDescription(ul.sign)}. ")
            val a7 = specialArudhas.darapada.arudha
            append("Business partnerships (A7) manifest through ${a7.sign.displayName}, ")
            append("suggesting ${getA7BusinessDescription(a7.sign)}.")
        }

        val careerAndAuthority = buildString {
            append("Your career and authority manifest through ${a10.sign.displayName} (A10). ")
            append("The ${a10.sign.ruler.displayName} lord indicates ${getA10CareerDescription(a10.sign)}. ")
            append("Career matters are ${if (assessment.careerManifestationStrength >= 60) "strongly" else "moderately"} supported.")
        }

        val recommendations = mutableListOf<String>()
        recommendations.add("Focus on ${al.sign.element.lowercase()} sign activities to enhance public image")
        recommendations.add("Career advancement favored during ${a10.houseLord.displayName} periods")
        recommendations.add("Business partnerships benefit from ${specialArudhas.darapada.arudha.sign.element.lowercase()} sign qualities")

        assessment.challengeAreas.forEach {
            recommendations.add("Address: $it")
        }

        yogas.flatMap { it.recommendations }.take(3).forEach {
            recommendations.add(it)
        }

        return ArudhaInterpretation(
            summary = summary,
            publicPerception = publicPerception,
            materialLife = materialLife,
            relationshipManifestation = relationshipManifestation,
            careerAndAuthority = careerAndAuthority,
            recommendations = recommendations.distinct()
        )
    }

    // ============================================
    // HELPER FUNCTIONS
    // ============================================

    private fun getSignDistance(from: ZodiacSign, to: ZodiacSign): Int {
        return ((to.ordinal - from.ordinal + 12) % 12) + 1
    }

    private fun isExalted(planet: Planet, sign: ZodiacSign): Boolean {
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.ARIES
            Planet.MOON -> sign == ZodiacSign.TAURUS
            Planet.MARS -> sign == ZodiacSign.CAPRICORN
            Planet.MERCURY -> sign == ZodiacSign.VIRGO
            Planet.JUPITER -> sign == ZodiacSign.CANCER
            Planet.VENUS -> sign == ZodiacSign.PISCES
            Planet.SATURN -> sign == ZodiacSign.LIBRA
            else -> false
        }
    }

    private fun isDebilitated(planet: Planet, sign: ZodiacSign): Boolean {
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.LIBRA
            Planet.MOON -> sign == ZodiacSign.SCORPIO
            Planet.MARS -> sign == ZodiacSign.CANCER
            Planet.MERCURY -> sign == ZodiacSign.PISCES
            Planet.JUPITER -> sign == ZodiacSign.CAPRICORN
            Planet.VENUS -> sign == ZodiacSign.VIRGO
            Planet.SATURN -> sign == ZodiacSign.ARIES
            else -> false
        }
    }

    private fun isOwnSign(planet: Planet, sign: ZodiacSign): Boolean {
        return sign.ruler == planet
    }

    private fun isMoolatrikona(planet: Planet, sign: ZodiacSign): Boolean {
        return when (planet) {
            Planet.SUN -> sign == ZodiacSign.LEO
            Planet.MOON -> sign == ZodiacSign.TAURUS
            Planet.MARS -> sign == ZodiacSign.ARIES
            Planet.MERCURY -> sign == ZodiacSign.VIRGO
            Planet.JUPITER -> sign == ZodiacSign.SAGITTARIUS
            Planet.VENUS -> sign == ZodiacSign.LIBRA
            Planet.SATURN -> sign == ZodiacSign.AQUARIUS
            else -> false
        }
    }

    private fun isFriendSign(planet: Planet, sign: ZodiacSign): Boolean {
        val friends = when (planet) {
            Planet.SUN -> listOf(Planet.MOON, Planet.MARS, Planet.JUPITER)
            Planet.MOON -> listOf(Planet.SUN, Planet.MERCURY)
            Planet.MARS -> listOf(Planet.SUN, Planet.MOON, Planet.JUPITER)
            Planet.MERCURY -> listOf(Planet.SUN, Planet.VENUS)
            Planet.JUPITER -> listOf(Planet.SUN, Planet.MOON, Planet.MARS)
            Planet.VENUS -> listOf(Planet.MERCURY, Planet.SATURN)
            Planet.SATURN -> listOf(Planet.MERCURY, Planet.VENUS)
            else -> emptyList()
        }
        return sign.ruler in friends
    }

    private fun isEnemySign(planet: Planet, sign: ZodiacSign): Boolean {
        val enemies = when (planet) {
            Planet.SUN -> listOf(Planet.SATURN, Planet.VENUS)
            Planet.MOON -> emptyList()
            Planet.MARS -> listOf(Planet.MERCURY)
            Planet.MERCURY -> listOf(Planet.MOON)
            Planet.JUPITER -> listOf(Planet.MERCURY, Planet.VENUS)
            Planet.VENUS -> listOf(Planet.SUN, Planet.MOON)
            Planet.SATURN -> listOf(Planet.SUN, Planet.MOON, Planet.MARS)
            else -> emptyList()
        }
        return sign.ruler in enemies
    }

    private fun aspectsSign(planet: Planet, fromSign: ZodiacSign, toSign: ZodiacSign): Boolean {
        val distance = getSignDistance(fromSign, toSign)

        // All planets aspect 7th sign
        if (distance == 7) return true

        // Special aspects
        return when (planet) {
            Planet.MARS -> distance in listOf(4, 8) // 4th and 8th
            Planet.JUPITER -> distance in listOf(5, 9) // 5th and 9th
            Planet.SATURN -> distance in listOf(3, 10) // 3rd and 10th
            Planet.RAHU, Planet.KETU -> distance in listOf(5, 9) // Like Jupiter
            else -> false
        }
    }
}
