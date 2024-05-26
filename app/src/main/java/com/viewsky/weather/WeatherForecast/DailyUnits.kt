package com.viewsky.weather.WeatherForecast

data class DailyUnits(
    val time: String,
    val temperature_2m_max: String,
    val relative_humidity_2m_max: String,
    val wind_speed_10m_max: String,
    val precipitation_probability_mean: String
)
