package com.astro.storm.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.astro.storm.data.localization.Language
import com.astro.storm.data.localization.StringKey
import com.astro.storm.data.localization.StringKeyRemedy
import com.astro.storm.data.localization.StringResources

/**
 * Common Life Areas for Astrological Analysis
 */
enum class LifeArea(val stringKey: com.astro.storm.data.localization.StringKeyInterface) {
    CAREER(StringKeyRemedy.LIFE_AREA_CAREER),
    RELATIONSHIPS(StringKeyRemedy.LIFE_AREA_RELATIONSHIPS),
    HEALTH(StringKeyRemedy.LIFE_AREA_HEALTH),
    FINANCE(StringKeyRemedy.LIFE_AREA_WEALTH),
    EDUCATION(StringKeyRemedy.LIFE_AREA_EDUCATION),
    SPIRITUAL(StringKeyRemedy.LIFE_AREA_SPIRITUAL),
    FAMILY(StringKeyRemedy.LIFE_AREA_FAMILY),
    PROPERTY(StringKeyRemedy.LIFE_AREA_PROPERTY),
    FOREIGN(StringKeyRemedy.LIFE_AREA_FOREIGN);

    fun getLocalizedName(language: Language): String = StringResources.get(stringKey, language)

    val icon: ImageVector get() = when (this) {
        CAREER -> Icons.Outlined.Work
        RELATIONSHIPS -> Icons.Outlined.Favorite
        HEALTH -> Icons.Outlined.Healing
        FINANCE -> Icons.Outlined.AccountBalance
        EDUCATION -> Icons.Outlined.School
        SPIRITUAL -> Icons.Outlined.SelfImprovement
        FAMILY -> Icons.Outlined.FamilyRestroom
        PROPERTY -> Icons.Outlined.Home
        FOREIGN -> Icons.Outlined.Public
    }

    val color: Color get() = when (this) {
        CAREER -> Color(0xFF2196F3)
        RELATIONSHIPS -> Color(0xFFE91E63)
        HEALTH -> Color(0xFF4CAF50)
        FINANCE -> Color(0xFFFF9800)
        SPIRITUAL -> Color(0xFF9C27B0)
        EDUCATION -> Color(0xFF00BCD4)
        FAMILY -> Color(0xFF795548)
        PROPERTY -> Color(0xFF8BC34A)
        FOREIGN -> Color(0xFF607D8B)
    }
}
