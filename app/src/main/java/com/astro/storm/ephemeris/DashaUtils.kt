package com.astro.storm.ephemeris

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode

/**
 * Shared utility functions for Dasha calculations.
 *
 * This object provides common constants and functions used across
 * different Dasha calculation systems (Vimshottari, Yogini, Chara, Kalachakra).
 */
object DashaUtils {

    /**
     * Standard math context for high-precision Dasha calculations.
     * Uses 20 decimal places with HALF_EVEN rounding (banker's rounding).
     */
    val MATH_CONTEXT = MathContext(20, RoundingMode.HALF_EVEN)

    /**
     * Average days per year used in Dasha calculations.
     * 365.25 accounts for leap years.
     */
    val DAYS_PER_YEAR = BigDecimal("365.25")

    /**
     * Span of one Nakshatra in degrees (360 / 27 = 13.333...).
     */
    val NAKSHATRA_SPAN = BigDecimal("13.333333333333333333")

    /**
     * Convert years (Double) to days with proper rounding.
     * Uses BigDecimal for precision, then rounds to nearest whole day.
     *
     * @param years Duration in years
     * @return Duration in days (minimum 1 day)
     */
    fun yearsToRoundedDays(years: Double): Long {
        return BigDecimal(years.toString())
            .multiply(DAYS_PER_YEAR, MATH_CONTEXT)
            .setScale(0, RoundingMode.HALF_EVEN)
            .toLong()
            .coerceAtLeast(1L)
    }

    /**
     * Convert years (BigDecimal) to days with proper rounding.
     * Maintains full precision throughout calculation.
     *
     * @param years Duration in years as BigDecimal
     * @return Duration in days (minimum 1 day)
     */
    fun yearsToRoundedDays(years: BigDecimal): Long {
        return years
            .multiply(DAYS_PER_YEAR, MATH_CONTEXT)
            .setScale(0, RoundingMode.HALF_EVEN)
            .toLong()
            .coerceAtLeast(1L)
    }

    /**
     * Extension function to coerce a BigDecimal within a range.
     * Useful for ensuring nakshatra progress is within [0, 1].
     *
     * @param min Minimum value
     * @param max Maximum value
     * @return Value coerced to be within [min, max]
     */
    fun BigDecimal.coerceIn(min: BigDecimal, max: BigDecimal): BigDecimal {
        return when {
            this < min -> min
            this > max -> max
            else -> this
        }
    }
}
