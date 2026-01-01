package com.astro.storm.ephemeris

import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKeyMatch
import com.astro.storm.data.localization.StringResources
import com.astro.storm.data.model.*
import com.astro.storm.ephemeris.VedicAstrologyUtils.PlanetaryRelationship

/**
 * Guna Milan (Ashtakoota) Calculator
 *
 * Calculates the 8 Gunas for Vedic matchmaking based on:
 * - Brihat Parasara Hora Shastra (BPHS)
 * - Muhurta Chintamani
 * - Jataka Parijata
 *
 * The 8 Gunas (Kootas) are:
 * 1. Varna (1 point) - Spiritual compatibility
 * 2. Vashya (2 points) - Mutual attraction
 * 3. Tara (3 points) - Destiny compatibility
 * 4. Yoni (4 points) - Physical compatibility
 * 5. Graha Maitri (5 points) - Mental compatibility
 * 6. Gana (6 points) - Temperament
 * 7. Bhakoot (7 points) - Love, health, finances
 * 8. Nadi (8 points) - Health and progeny
 *
 * Total: 36 points maximum
 */
object GunaMilanCalculator {

    /**
     * Calculate all 8 Guna analyses
     */
    fun calculateAllGunas(
        brideMoonSign: ZodiacSign,
        groomMoonSign: ZodiacSign,
        brideNakshatra: Nakshatra,
        groomNakshatra: Nakshatra,
        bridePada: Int,
        groomPada: Int,
        language: Language = Language.ENGLISH
    ): List<GunaAnalysis> {
        return listOf(
            calculateVarna(brideMoonSign, groomMoonSign, language),
            calculateVashya(brideMoonSign, groomMoonSign, language),
            calculateTara(brideNakshatra, groomNakshatra, language),
            calculateYoni(brideNakshatra, groomNakshatra, language),
            calculateGrahaMaitri(brideMoonSign, groomMoonSign, language),
            calculateGana(brideNakshatra, groomNakshatra, language),
            calculateBhakoot(brideMoonSign, groomMoonSign, language),
            calculateNadi(brideNakshatra, groomNakshatra, brideMoonSign, groomMoonSign, bridePada, groomPada, language)
        )
    }

    // region 1. Varna Koota (1 Point)

    fun calculateVarna(
        brideSign: ZodiacSign,
        groomSign: ZodiacSign,
        language: Language = Language.ENGLISH
    ): GunaAnalysis {
        val brideVarna = Varna.fromZodiacSign(brideSign)
        val groomVarna = Varna.fromZodiacSign(groomSign)

        // Groom's Varna should be equal or superior to Bride's for full points
        val points = if (groomVarna.value >= brideVarna.value) MatchmakingConstants.MAX_VARNA else 0.0

        val analysis = buildString {
            if (points > 0) {
                append(StringResources.get(StringKeyMatch.VARNA_COMPATIBLE, language)
                    .replace("{groom}", groomVarna.getLocalizedName(language))
                    .replace("{bride}", brideVarna.getLocalizedName(language)))
                append(" ")
                append(StringResources.get(StringKeyMatch.VARNA_DETAILED_COMPATIBLE, language))
            } else {
                append(StringResources.get(StringKeyMatch.VARNA_INCOMPATIBLE, language)
                    .replace("{bride}", brideVarna.getLocalizedName(language))
                    .replace("{groom}", groomVarna.getLocalizedName(language)))
                append(" ")
                append(StringResources.get(StringKeyMatch.VARNA_DETAILED_INCOMPATIBLE, language))
            }
        }

        return GunaAnalysis(
            gunaType = GunaType.VARNA,
            name = GunaType.VARNA.getLocalizedName(language),
            maxPoints = MatchmakingConstants.MAX_VARNA,
            obtainedPoints = points,
            description = StringResources.get(StringKeyMatch.VARNA_DESC, language),
            brideValue = brideVarna.getLocalizedName(language),
            groomValue = groomVarna.getLocalizedName(language),
            analysis = analysis,
            isPositive = points > 0
        )
    }

    // endregion

    // region 2. Vashya Koota (2 Points)

    fun calculateVashya(
        brideSign: ZodiacSign,
        groomSign: ZodiacSign,
        language: Language = Language.ENGLISH
    ): GunaAnalysis {
        val brideVashya = Vashya.fromZodiacSign(brideSign)
        val groomVashya = Vashya.fromZodiacSign(groomSign)

        val points = calculateVashyaPoints(brideVashya, groomVashya, brideSign, groomSign)

        val analysisKey = when {
            points >= 2.0 -> StringKeyMatch.VASHYA_EXCELLENT
            points >= 1.5 -> StringKeyMatch.VASHYA_VERY_GOOD
            points >= 1.0 -> StringKeyMatch.VASHYA_GOOD
            points >= 0.5 -> StringKeyMatch.VASHYA_PARTIAL
            else -> StringKeyMatch.VASHYA_INCOMPATIBLE
        }

        return GunaAnalysis(
            gunaType = GunaType.VASHYA,
            name = GunaType.VASHYA.getLocalizedName(language),
            maxPoints = MatchmakingConstants.MAX_VASHYA,
            obtainedPoints = points,
            description = StringResources.get(StringKeyMatch.VASHYA_DESC, language),
            brideValue = "${brideVashya.getLocalizedName(language)} (${brideSign.getLocalizedName(language)})",
            groomValue = "${groomVashya.getLocalizedName(language)} (${groomSign.getLocalizedName(language)})",
            analysis = StringResources.get(analysisKey, language),
            isPositive = points >= 1.0
        )
    }

    private fun calculateVashyaPoints(
        brideVashya: Vashya,
        groomVashya: Vashya,
        brideSign: ZodiacSign,
        groomSign: ZodiacSign
    ): Double {
        if (brideSign == groomSign) return 2.0
        if (brideVashya == groomVashya) return 2.0

        val groomControlsBride = Vashya.controlPairs[groomVashya]?.contains(brideVashya) == true
        val brideControlsGroom = Vashya.controlPairs[brideVashya]?.contains(groomVashya) == true

        return when {
            groomControlsBride && brideControlsGroom -> 2.0
            groomControlsBride || brideControlsGroom -> 1.0
            Vashya.enemyPairs.any { it.contains(brideVashya) && it.contains(groomVashya) } -> 0.0
            else -> 0.5
        }
    }

    // endregion

    // region 3. Tara Koota (3 Points)

    fun calculateTara(
        brideNakshatra: Nakshatra,
        groomNakshatra: Nakshatra,
        language: Language = Language.ENGLISH
    ): GunaAnalysis {
        val brideToGroom = calculateTaraNumber(brideNakshatra, groomNakshatra)
        val groomToBride = calculateTaraNumber(groomNakshatra, brideNakshatra)

        val brideTara = getTaraName(brideToGroom, language)
        val groomTara = getTaraName(groomToBride, language)

        val brideAuspicious = isAuspiciousTara(brideToGroom)
        val groomAuspicious = isAuspiciousTara(groomToBride)

        val points = when {
            brideAuspicious && groomAuspicious -> 3.0
            brideAuspicious || groomAuspicious -> 1.5
            else -> 0.0
        }

        val analysisKey = when {
            points >= 3.0 -> StringKeyMatch.TARA_EXCELLENT
            points >= 1.5 -> StringKeyMatch.TARA_MODERATE
            else -> StringKeyMatch.TARA_INAUSPICIOUS
        }

        return GunaAnalysis(
            gunaType = GunaType.TARA,
            name = GunaType.TARA.getLocalizedName(language),
            maxPoints = MatchmakingConstants.MAX_TARA,
            obtainedPoints = points,
            description = StringResources.get(StringKeyMatch.TARA_DESC, language),
            brideValue = "${brideNakshatra.getLocalizedName(language)} → $brideTara",
            groomValue = "${groomNakshatra.getLocalizedName(language)} → $groomTara",
            analysis = StringResources.get(analysisKey, language),
            isPositive = points >= 1.5
        )
    }

    private fun calculateTaraNumber(fromNakshatra: Nakshatra, toNakshatra: Nakshatra): Int {
        val diff = (toNakshatra.number - fromNakshatra.number + 27) % 27
        return if (diff == 0) 9 else ((diff - 1) % 9) + 1
    }

    private fun getTaraName(taraNumber: Int, language: Language): String = when (taraNumber) {
        1 -> StringResources.get(StringKeyMatch.TARA_JANMA, language)
        2 -> StringResources.get(StringKeyMatch.TARA_SAMPAT, language)
        3 -> StringResources.get(StringKeyMatch.TARA_VIPAT, language)
        4 -> StringResources.get(StringKeyMatch.TARA_KSHEMA, language)
        5 -> StringResources.get(StringKeyMatch.TARA_PRATYARI, language)
        6 -> StringResources.get(StringKeyMatch.TARA_SADHANA, language)
        7 -> StringResources.get(StringKeyMatch.TARA_VADHA, language)
        8 -> StringResources.get(StringKeyMatch.TARA_MITRA, language)
        9 -> StringResources.get(StringKeyMatch.TARA_PARAMA_MITRA, language)
        else -> StringResources.get(StringKeyMatch.TARA_JANMA, language)
    }

    private fun isAuspiciousTara(taraNumber: Int): Boolean = taraNumber in listOf(2, 4, 6, 8, 9)

    // endregion

    // region 4. Yoni Koota (4 Points)

    fun calculateYoni(
        brideNakshatra: Nakshatra,
        groomNakshatra: Nakshatra,
        language: Language = Language.ENGLISH
    ): GunaAnalysis {
        val brideYoni = Yoni.fromNakshatra(brideNakshatra)
        val groomYoni = Yoni.fromNakshatra(groomNakshatra)

        val points = calculateYoniPoints(brideYoni, groomYoni)

        val analysisKey = when {
            points >= 4.0 -> StringKeyMatch.YONI_SAME
            points >= 3.0 -> StringKeyMatch.YONI_FRIENDLY
            points >= 2.0 -> StringKeyMatch.YONI_NEUTRAL
            points >= 1.0 -> StringKeyMatch.YONI_UNFRIENDLY
            else -> StringKeyMatch.YONI_ENEMY
        }

        return GunaAnalysis(
            gunaType = GunaType.YONI,
            name = GunaType.YONI.getLocalizedName(language),
            maxPoints = MatchmakingConstants.MAX_YONI,
            obtainedPoints = points,
            description = StringResources.get(StringKeyMatch.YONI_DESC, language),
            brideValue = "${brideYoni.animal} (${brideYoni.gender})",
            groomValue = "${groomYoni.animal} (${groomYoni.gender})",
            analysis = StringResources.get(analysisKey, language),
            isPositive = points >= 2.0
        )
    }

    private fun calculateYoniPoints(brideYoni: Yoni, groomYoni: Yoni): Double {
        if (brideYoni.groupId == groomYoni.groupId) return 4.0

        if (Yoni.enemyPairs.any { it.contains(brideYoni.groupId) && it.contains(groomYoni.groupId) }) {
            return 0.0
        }

        for (group in Yoni.friendlyGroups) {
            if (group.contains(brideYoni.groupId) && group.contains(groomYoni.groupId)) {
                return 3.0
            }
        }

        return 2.0
    }

    // endregion

    // region 5. Graha Maitri Koota (5 Points)

    fun calculateGrahaMaitri(
        brideSign: ZodiacSign,
        groomSign: ZodiacSign,
        language: Language = Language.ENGLISH
    ): GunaAnalysis {
        val brideLord = brideSign.ruler
        val groomLord = groomSign.ruler

        val points = calculateGrahaMaitriPoints(brideLord, groomLord)

        val analysisKey = when {
            points >= 5.0 -> StringKeyMatch.GRAHA_MAITRI_EXCELLENT
            points >= 4.0 -> StringKeyMatch.GRAHA_MAITRI_VERY_GOOD
            points >= 3.0 -> StringKeyMatch.GRAHA_MAITRI_AVERAGE
            points >= 1.0 -> StringKeyMatch.GRAHA_MAITRI_FRICTION
            else -> StringKeyMatch.GRAHA_MAITRI_INCOMPATIBLE
        }

        return GunaAnalysis(
            gunaType = GunaType.GRAHA_MAITRI,
            name = GunaType.GRAHA_MAITRI.getLocalizedName(language),
            maxPoints = MatchmakingConstants.MAX_GRAHA_MAITRI,
            obtainedPoints = points,
            description = StringResources.get(StringKeyMatch.GRAHA_MAITRI_DESC, language),
            brideValue = "${brideSign.getLocalizedName(language)} (${brideLord.getLocalizedName(language)})",
            groomValue = "${groomSign.getLocalizedName(language)} (${groomLord.getLocalizedName(language)})",
            analysis = StringResources.get(analysisKey, language),
            isPositive = points >= 3.0
        )
    }

    private fun calculateGrahaMaitriPoints(lord1: Planet, lord2: Planet): Double {
        if (lord1 == lord2) return 5.0

        val relationship1 = VedicAstrologyUtils.getNaturalRelationship(lord1, lord2)
        val relationship2 = VedicAstrologyUtils.getNaturalRelationship(lord2, lord1)

        val isFriend1 = relationship1 == PlanetaryRelationship.FRIEND
        val isFriend2 = relationship2 == PlanetaryRelationship.FRIEND
        val isNeutral1 = relationship1 == PlanetaryRelationship.NEUTRAL
        val isNeutral2 = relationship2 == PlanetaryRelationship.NEUTRAL
        val isEnemy1 = relationship1 == PlanetaryRelationship.ENEMY
        val isEnemy2 = relationship2 == PlanetaryRelationship.ENEMY

        return when {
            // Both are friends
            isFriend1 && isFriend2 -> 5.0
            
            // One Friend, One Neutral
            (isFriend1 && isNeutral2) || (isNeutral1 && isFriend2) -> 4.0
            
            // Both Neutral
            isNeutral1 && isNeutral2 -> 3.0
            
            // One Friend, One Enemy
            (isFriend1 && isEnemy2) || (isEnemy1 && isFriend2) -> 1.0
            
            // One Neutral, One Enemy
            (isNeutral1 && isEnemy2) || (isEnemy1 && isNeutral2) -> 0.5
            
            // Both Enemies
            else -> 0.0
        }
    }

    // endregion

    // region 6. Gana Koota (6 Points)

    fun calculateGana(
        brideNakshatra: Nakshatra,
        groomNakshatra: Nakshatra,
        language: Language = Language.ENGLISH
    ): GunaAnalysis {
        val brideGana = Gana.fromNakshatra(brideNakshatra)
        val groomGana = Gana.fromNakshatra(groomNakshatra)

        val points = calculateGanaPoints(brideGana, groomGana)

        val analysisKey = when {
            points >= 6.0 -> StringKeyMatch.GANA_SAME
            points >= 5.0 -> StringKeyMatch.GANA_COMPATIBLE
            points >= 3.0 -> StringKeyMatch.GANA_PARTIAL
            points >= 1.0 -> StringKeyMatch.GANA_DIFFERENT
            else -> StringKeyMatch.GANA_OPPOSITE
        }

        return GunaAnalysis(
            gunaType = GunaType.GANA,
            name = GunaType.GANA.getLocalizedName(language),
            maxPoints = MatchmakingConstants.MAX_GANA,
            obtainedPoints = points,
            description = StringResources.get(StringKeyMatch.GANA_DESC, language),
            brideValue = "${brideGana.getLocalizedName(language)} (${brideGana.getLocalizedDescription(language)})",
            groomValue = "${groomGana.getLocalizedName(language)} (${groomGana.getLocalizedDescription(language)})",
            analysis = StringResources.get(analysisKey, language),
            isPositive = points >= 3.0
        )
    }

    private fun calculateGanaPoints(brideGana: Gana, groomGana: Gana): Double {
        return when {
            brideGana == groomGana -> 6.0
            brideGana == Gana.DEVA && groomGana == Gana.MANUSHYA -> 5.0
            brideGana == Gana.MANUSHYA && groomGana == Gana.DEVA -> 6.0
            brideGana == Gana.MANUSHYA && groomGana == Gana.RAKSHASA -> 1.0
            brideGana == Gana.RAKSHASA && groomGana == Gana.MANUSHYA -> 3.0  // Note: Some traditions say 0, others 3. BPHS permits this for Bhakoot reasons sometimes
            brideGana == Gana.RAKSHASA && groomGana == Gana.RAKSHASA -> 6.0
            else -> 0.0  // Deva-Rakshasa / Rakshasa-Deva is the main Gana Dosha
        }
    }

    // endregion

    // region 7. Bhakoot Koota (7 Points)

    fun calculateBhakoot(
        brideSign: ZodiacSign,
        groomSign: ZodiacSign,
        language: Language = Language.ENGLISH
    ): GunaAnalysis {
        val brideNumber = brideSign.number
        val groomNumber = groomSign.number

        val (points, doshaType, doshaDescription) = calculateBhakootPoints(
            brideNumber, groomNumber, brideSign, groomSign, language
        )

        val analysis = when (doshaType) {
            "None" -> StringResources.get(StringKeyMatch.BHAKOOT_NO_DOSHA, language)
            "Cancelled" -> "${StringResources.get(StringKeyMatch.BHAKOOT_CANCELLED, language)} - $doshaDescription"
            "2-12" -> "${StringResources.get(StringKeyMatch.BHAKOOT_2_12, language)} $doshaDescription"
            "6-8" -> "${StringResources.get(StringKeyMatch.BHAKOOT_6_8, language)} $doshaDescription"
            "5-9" -> "${StringResources.get(StringKeyMatch.BHAKOOT_5_9, language)} $doshaDescription"
            else -> doshaDescription
        }

        return GunaAnalysis(
            gunaType = GunaType.BHAKOOT,
            name = GunaType.BHAKOOT.getLocalizedName(language),
            maxPoints = MatchmakingConstants.MAX_BHAKOOT,
            obtainedPoints = points,
            description = StringResources.get(StringKeyMatch.BHAKOOT_DESC, language),
            brideValue = brideSign.getLocalizedName(language),
            groomValue = groomSign.getLocalizedName(language),
            analysis = analysis,
            isPositive = points >= 7.0
        )
    }

    private fun calculateBhakootPoints(
        brideNumber: Int,
        groomNumber: Int,
        brideSign: ZodiacSign,
        groomSign: ZodiacSign,
        language: Language
    ): Triple<Double, String, String> {
        val diff = ((groomNumber - brideNumber + 12) % 12)

        // Count from Bride to Groom
        val is2_12 = (diff == 1 || diff == 11) // 2nd or 12th
        val is6_8 = (diff == 5 || diff == 7)   // 6th or 8th
        val is5_9 = (diff == 4 || diff == 8)   // 5th or 9th

        // Check for Doshas
        if (is2_12 || is6_8 || is5_9) {
            // Bhakoot Dosha exists, check for cancellation
            val cancellation = checkBhakootDoshaCancellation(brideSign, groomSign, is6_8, language)

            if (cancellation != null) {
                return Triple(7.0, "Cancelled", cancellation)
            }

            // Dosha confirmed (0 points)
            return when {
                is2_12 -> Triple(0.0, "2-12", StringResources.get(StringKeyMatch.BHAKOOT_2_12_DESC, language))
                is6_8 -> Triple(0.0, "6-8", StringResources.get(StringKeyMatch.BHAKOOT_6_8_DESC, language))
                is5_9 -> Triple(0.0, "5-9", StringResources.get(StringKeyMatch.BHAKOOT_5_9_DESC, language))
                else -> Triple(0.0, "Unknown", "")
            }
        }

        // All other positions (1-1, 3-11, 4-10) are auspicious or neutral-good
        return Triple(7.0, "None", StringResources.get(StringKeyMatch.BHAKOOT_FAVORABLE, language))
    }

    private fun checkBhakootDoshaCancellation(
        brideSign: ZodiacSign,
        groomSign: ZodiacSign,
        isShadashtak: Boolean,
        language: Language
    ): String? {
        val brideLord = brideSign.ruler
        val groomLord = groomSign.ruler

        // 1. Same lord cancels dosha (e.g. Aries-Scorpio is 6-8 but both ruled by Mars)
        if (brideLord == groomLord) {
            return StringResources.get(StringKeyMatch.BHAKOOT_CANCEL_SAME_LORD, language)
                .replace("{lord}", brideLord.getLocalizedName(language))
        }

        // 2. Mutual friends cancel dosha
        // Using VedicAstrologyUtils for consistent friendship logic
        val rel1 = VedicAstrologyUtils.getNaturalRelationship(brideLord, groomLord)
        val rel2 = VedicAstrologyUtils.getNaturalRelationship(groomLord, brideLord)
        
        if (rel1 == PlanetaryRelationship.FRIEND && rel2 == PlanetaryRelationship.FRIEND) {
            return StringResources.get(StringKeyMatch.BHAKOOT_CANCEL_MUTUAL_FRIENDS, language)
                .replace("{lord1}", brideLord.getLocalizedName(language))
                .replace("{lord2}", groomLord.getLocalizedName(language))
        }

        // 3. Exaltation cancellation involved? (Simplification: If lords are friends/neutral, we take it as partial cancellation in strict systems, but here sticking to standard)

        // 4. Same element cancellation (optional in some texts, but often used)
        val brideElement = getSignElement(brideSign, language)
        val groomElement = getSignElement(groomSign, language)
        if (brideElement == groomElement) {
            return StringResources.get(StringKeyMatch.BHAKOOT_CANCEL_ELEMENT, language).replace("{element}", brideElement)
        }
        
        // 5. Friendly disposition (Friend-Neutral) sometimes considered cancellation
        if ((rel1 == PlanetaryRelationship.FRIEND && rel2 == PlanetaryRelationship.NEUTRAL) ||
            (rel1 == PlanetaryRelationship.NEUTRAL && rel2 == PlanetaryRelationship.FRIEND)) {
             return StringResources.get(StringKeyMatch.BHAKOOT_CANCEL_FRIENDLY, language)
        }

        return null
    }
    
    // endregion

    // region 8. Nadi Koota (8 Points)

    fun calculateNadi(
        brideNakshatra: Nakshatra,
        groomNakshatra: Nakshatra,
        brideMoonSign: ZodiacSign,
        groomMoonSign: ZodiacSign,
        bridePada: Int,
        groomPada: Int,
        language: Language = Language.ENGLISH
    ): GunaAnalysis {
        val brideNadi = Nadi.fromNakshatra(brideNakshatra)
        val groomNadi = Nadi.fromNakshatra(groomNakshatra)

        val (points, hasDosha, cancellationReason) = if (brideNadi == groomNadi) {
            val cancellation = checkNadiDoshaCancellation(
                brideNakshatra, groomNakshatra, brideMoonSign, groomMoonSign, bridePada, groomPada, language
            )
            if (cancellation != null) {
                Triple(8.0, false, cancellation)
            } else {
                Triple(0.0, true, null)
            }
        } else {
            Triple(8.0, false, null)
        }

        val analysis = when {
            hasDosha -> StringResources.get(StringKeyMatch.NADI_DOSHA_PRESENT, language)
                .replace("{nadi}", brideNadi.getLocalizedName(language))
            cancellationReason != null -> "${StringResources.get(StringKeyMatch.NADI_DOSHA_CANCELLED, language)} $cancellationReason"
            else -> StringResources.get(StringKeyMatch.NADI_DIFFERENT, language)
                .replace("{nadi1}", brideNadi.getLocalizedName(language))
                .replace("{nadi2}", groomNadi.getLocalizedName(language))
        }

        return GunaAnalysis(
            gunaType = GunaType.NADI,
            name = GunaType.NADI.getLocalizedName(language),
            maxPoints = MatchmakingConstants.MAX_NADI,
            obtainedPoints = points,
            description = StringResources.get(StringKeyMatch.NADI_DESC, language),
            brideValue = brideNadi.getLocalizedName(language),
            groomValue = groomNadi.getLocalizedName(language),
            analysis = analysis,
            isPositive = !hasDosha
        )
    }

    private fun checkNadiDoshaCancellation(
        brideNakshatra: Nakshatra,
        groomNakshatra: Nakshatra,
        brideMoonSign: ZodiacSign,
        groomMoonSign: ZodiacSign,
        bridePada: Int,
        groomPada: Int,
        language: Language
    ): String? {
        // Same Nakshatra with different Rashi - strongest cancellation (e.g. Krittika Aries vs Krittika Taurus)
        if (brideNakshatra == groomNakshatra && brideMoonSign != groomMoonSign) {
            return StringResources.get(StringKeyMatch.NADI_CANCEL_SAME_NAK_DIFF_RASHI, language)
                .replace("{nakshatra}", brideNakshatra.getLocalizedName(language))
        }

        // Same Rashi with different Nakshatra
        if (brideMoonSign == groomMoonSign && brideNakshatra != groomNakshatra) {
            return StringResources.get(StringKeyMatch.NADI_CANCEL_SAME_RASHI_DIFF_NAK, language)
                .replace("{rashi}", brideMoonSign.getLocalizedName(language))
        }

        // Same Nakshatra, same Rashi but different Pada
        if (brideNakshatra == groomNakshatra && brideMoonSign == groomMoonSign && bridePada != groomPada) {
            return StringResources.get(StringKeyMatch.NADI_CANCEL_DIFF_PADA, language)
                .replace("{pada1}", bridePada.toString())
                .replace("{pada2}", groomPada.toString())
        }

        // Special Nakshatra pairs that cancel (e.g. Rohini-Magha)
        for (pair in Nadi.cancellingPairs) {
            if (pair.contains(brideNakshatra) && pair.contains(groomNakshatra)) {
                return StringResources.get(StringKeyMatch.NADI_CANCEL_SPECIAL_PAIR, language)
                    .replace("{nak1}", brideNakshatra.getLocalizedName(language))
                    .replace("{nak2}", groomNakshatra.getLocalizedName(language))
            }
        }

        // Moon sign lords are mutual friends
        val brideLord = brideMoonSign.ruler
        val groomLord = groomMoonSign.ruler
        
        // Ensure not same lord to avoid duplication with other rules, though same lord is fundamentally good
        if (brideLord != groomLord) {
            val rel1 = VedicAstrologyUtils.getNaturalRelationship(brideLord, groomLord)
            val rel2 = VedicAstrologyUtils.getNaturalRelationship(groomLord, brideLord)
            
            if (rel1 == PlanetaryRelationship.FRIEND && rel2 == PlanetaryRelationship.FRIEND) {
                return StringResources.get(StringKeyMatch.NADI_CANCEL_LORDS_FRIENDS, language)
                    .replace("{lord1}", brideLord.getLocalizedName(language))
                    .replace("{lord2}", groomLord.getLocalizedName(language))
            }
        }

        // Same Nakshatra ruler
        if (brideNakshatra.ruler == groomNakshatra.ruler) {
            return StringResources.get(StringKeyMatch.NADI_CANCEL_SAME_NAK_LORD, language)
                .replace("{lord}", brideNakshatra.ruler.getLocalizedName(language))
        }

        return null
    }

    // endregion

    // region Helpers

    private fun getSignElement(sign: ZodiacSign, language: Language): String = when (sign) {
        ZodiacSign.ARIES, ZodiacSign.LEO, ZodiacSign.SAGITTARIUS -> StringResources.get(StringKeyMatch.ELEMENT_FIRE, language)
        ZodiacSign.TAURUS, ZodiacSign.VIRGO, ZodiacSign.CAPRICORN -> StringResources.get(StringKeyMatch.ELEMENT_EARTH, language)
        ZodiacSign.GEMINI, ZodiacSign.LIBRA, ZodiacSign.AQUARIUS -> StringResources.get(StringKeyMatch.ELEMENT_AIR, language)
        ZodiacSign.CANCER, ZodiacSign.SCORPIO, ZodiacSign.PISCES -> StringResources.get(StringKeyMatch.ELEMENT_WATER, language)
    }

    // endregion
}
