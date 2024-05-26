package com.viewsky.weather.RoomDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DBWeatherData")
data class DBWeatherData(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: String,
    val temperature: Float,
    val windSpeed: Float,
    val humidity: Float,
    val precipitation: Float
)
