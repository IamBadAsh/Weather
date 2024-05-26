package com.viewsky.weather.UI

data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double
)

val cities = listOf(
    City("New York", 40.7128, 74.0060),
    City("Los Angeles", 34.0522, 118.2437),
    City("Chicago", 41.8781, 87.6298),
    City("Delhi", 28.7041, 77.1025),
    City("Lucknow", 26.850000, 	-80.949997),
    // Add more cities as needed
)
