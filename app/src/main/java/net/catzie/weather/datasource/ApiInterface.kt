package net.catzie.weather.datasource

import net.catzie.weather.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
    ): Response<WeatherResponse>
}