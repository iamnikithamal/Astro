package com.astro.storm.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

/**
 * Room entity for persisting chart data
 *
 * Indices are added for:
 * - createdAt: Used for ordering charts in getAllCharts()
 * - name: Used for searching charts in searchCharts()
 * - location: Used for searching charts in searchCharts()
 */
@Entity(
    tableName = "charts",
    indices = [
        Index(value = ["createdAt"]),
        Index(value = ["name"]),
        Index(value = ["location"])
    ]
)
@TypeConverters(Converters::class)
data class ChartEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val dateTime: String,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val location: String,
    val julianDay: Double,
    val ayanamsa: Double,
    val ayanamsaName: String,
    val ascendant: Double,
    val midheaven: Double,
    val planetPositionsJson: String,
    val houseCuspsJson: String,
    val houseSystem: String,
    val gender: String = "PREFER_NOT_TO_SAY",
    val createdAt: Long = System.currentTimeMillis()
)
