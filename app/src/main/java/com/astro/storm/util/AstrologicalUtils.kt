package com.astro.storm.util

import com.astro.storm.data.model.ZodiacSign

/**
 * Common astrological utility functions used across calculators.
 * Consolidates duplicate code from VarshaphalaCalculator, ShadbalaCalculator,
 * AspectCalculator, MuhurtaCalculator, PanchangaCalculator, etc.
 */
object AstrologicalUtils {

    /** Standard zodiac signs in order from Aries to Pisces */
    val ZODIAC_SIGNS = listOf(
        ZodiacSign.ARIES, ZodiacSign.TAURUS, ZodiacSign.GEMINI, ZodiacSign.CANCER,
        ZodiacSign.LEO, ZodiacSign.VIRGO, ZodiacSign.LIBRA, ZodiacSign.SCORPIO,
        ZodiacSign.SAGITTARIUS, ZodiacSign.CAPRICORN, ZodiacSign.AQUARIUS, ZodiacSign.PISCES
    )

    /**
     * Normalizes a longitude/angle to the range [0, 360).
     * This is the canonical implementation - use this instead of local private functions.
     *
     * @param longitude The longitude/angle in degrees.
     * @return The normalized value in range [0, 360).
     */
    fun normalizeLongitude(longitude: Double): Double {
        var result = longitude % 360.0
        if (result < 0) result += 360.0
        return result
    }

    /**
     * Alias for normalizeLongitude for semantic clarity when working with angles.
     */
    fun normalizeAngle(angle: Double): Double = normalizeLongitude(angle)

    /**
     * Alias for normalizeLongitude for semantic clarity when working with degrees.
     */
    fun normalizeDegree(degree: Double): Double = normalizeLongitude(degree)

    /**
     * Get zodiac sign from longitude.
     *
     * @param longitude The sidereal longitude in degrees.
     * @return The corresponding zodiac sign.
     */
    fun getSignFromLongitude(longitude: Double): ZodiacSign {
        val normalizedLong = normalizeLongitude(longitude)
        val signIndex = (normalizedLong / 30.0).toInt().coerceIn(0, 11)
        return ZODIAC_SIGNS[signIndex]
    }

    /**
     * Get the degree within the sign (0-30).
     *
     * @param longitude The sidereal longitude in degrees.
     * @return The degree within the sign (0-30).
     */
    fun getDegreeInSign(longitude: Double): Double {
        return normalizeLongitude(longitude) % 30.0
    }

    /**
     * Get the index of a zodiac sign (0-11, Aries=0).
     *
     * @param sign The zodiac sign.
     * @return The index (0-11), or 0 if not found.
     */
    fun getZodiacIndex(sign: ZodiacSign): Int {
        val index = ZODIAC_SIGNS.indexOf(sign)
        return if (index >= 0) index else 0
    }

    /**
     * Get zodiac sign by index.
     *
     * @param index The index (0-11).
     * @return The corresponding zodiac sign.
     */
    fun getSignByIndex(index: Int): ZodiacSign {
        return ZODIAC_SIGNS[index.coerceIn(0, 11)]
    }

    /**
     * Calculate whole sign house from longitude and ascendant.
     *
     * @param longitude The planet's longitude.
     * @param ascendantLongitude The ascendant longitude.
     * @return The house number (1-12).
     */
    fun calculateWholeSignHouse(longitude: Double, ascendantLongitude: Double): Int {
        val normalizedLong = normalizeLongitude(longitude)
        val normalizedAsc = normalizeLongitude(ascendantLongitude)
        val ascSign = (normalizedAsc / 30.0).toInt().coerceIn(0, 11)
        val planetSign = (normalizedLong / 30.0).toInt().coerceIn(0, 11)
        val house = ((planetSign - ascSign + 12) % 12) + 1
        return house.coerceIn(1, 12)
    }

    /**
     * Get ordinal suffix for a number (1st, 2nd, 3rd, etc.)
     *
     * @param n The number.
     * @return The ordinal suffix.
     */
    fun getOrdinalSuffix(n: Int): String {
        return when {
            n in 11..13 -> "th"
            n % 10 == 1 -> "st"
            n % 10 == 2 -> "nd"
            n % 10 == 3 -> "rd"
            else -> "th"
        }
    }

    /**
     * Calculate the angular distance between two longitudes.
     * Returns the shortest distance (0-180).
     *
     * @param long1 First longitude.
     * @param long2 Second longitude.
     * @return The angular distance (0-180).
     */
    fun angularDistance(long1: Double, long2: Double): Double {
        val diff = kotlin.math.abs(normalizeLongitude(long1) - normalizeLongitude(long2))
        return if (diff > 180.0) 360.0 - diff else diff
    }
}
