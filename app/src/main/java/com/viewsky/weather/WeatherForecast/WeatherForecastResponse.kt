package com.viewsky.weather.WeatherForecast

data class WeatherForecastResponse(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Double,
    val daily_units: DailyUnits,
    val daily: Daily
)