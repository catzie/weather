package net.catzie.weather.model

data class WeatherRequest(
    val lat: Double,
    val lon: Double,
    val appId: String,
)