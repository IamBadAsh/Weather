package com.viewsky.weather.WeatherForecast

import com.viewsky.weather.Remote.RetrofitInstance

class WeatherRepository {

    suspend fun getWeatherForecast(latitude: Double, longitude: Double): WeatherForecastResponse {
        return RetrofitInstance.weatherApiService.getWeatherForecast(
            latitude,
            longitude,
            5,
            "temperature_2m_max,relative_humidity_2m_max,wind_speed_10m_max,precipitation_probability_mean"
        )
    }
}
