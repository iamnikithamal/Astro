package com.astro.storm.data.preferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Onboarding Manager for persisting onboarding completion state
 *
 * Manages:
 * - Whether the user has completed the onboarding flow
 * - Provides reactive state for observing onboarding status
 *
 * This uses SharedPreferences for instant access on app startup
 * without database overhead.
 */
class OnboardingManager private constructor(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    private val _hasCompletedOnboarding = MutableStateFlow(getPersistedOnboardingStatus())
    val hasCompletedOnboarding: StateFlow<Boolean> = _hasCompletedOnboarding.asStateFlow()

    /**
     * Mark onboarding as completed
     */
    fun completeOnboarding() {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, true).apply()
        _hasCompletedOnboarding.value = true
    }

    /**
     * Reset onboarding status (useful for testing or re-showing onboarding)
     */
    fun resetOnboarding() {
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, false).apply()
        _hasCompletedOnboarding.value = false
    }

    /**
     * Check if onboarding has been completed
     */
    fun isOnboardingCompleted(): Boolean = _hasCompletedOnboarding.value

    /**
     * Get the persisted onboarding status from SharedPreferences
     */
    private fun getPersistedOnboardingStatus(): Boolean {
        return prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    companion object {
        private const val PREFS_NAME = "astro_storm_onboarding_prefs"
        private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

        @Volatile
        private var INSTANCE: OnboardingManager? = null

        fun getInstance(context: Context): OnboardingManager {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: OnboardingManager(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }
}
