package com.viewsky.weather.WeatherForecast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherRepository = WeatherRepository()

    private val _weatherForecast = MutableLiveData<WeatherForecastResponse>()
    val weatherForecast: LiveData<WeatherForecastResponse>
        get() = _weatherForecast

    fun fetchWeatherForecast(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            _weatherForecast.value = weatherRepository.getWeatherForecast(latitude, longitude)
            try {
                val response = weatherRepository.getWeatherForecast(latitude, longitude)
                _weatherForecast.value = response
                Log.d("WeatherViewModel", "Weather forecast response: $response")
            } catch (e: Exception) {
                Log.e("WeatherViewModel", "Error fetching weather forecast: ${e.message}")
            }
        }
    }
}
