package net.catzie.weather.model.weather

data class WeatherRequest(
    val lat: Double,
    val lon: Double,
    val appId: String,
)