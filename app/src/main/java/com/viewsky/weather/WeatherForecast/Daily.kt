package com.viewsky.weather.WeatherForecast

data class Daily(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val relative_humidity_2m_max: List<Int>,
    val wind_speed_10m_max: List<Double>,
    val precipitation_probability_mean: List<Int>
)