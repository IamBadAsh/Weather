package com.viewsky.weather.Remote

import com.viewsky.weather.WeatherForecast.WeatherForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("forecast_days") forecastDays: Int,
        @Query("daily") daily: String
    ): WeatherForecastResponse
}
