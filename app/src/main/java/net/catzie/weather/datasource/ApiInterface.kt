package net.catzie.weather.datasource

import net.catzie.weather.model.WeatherRequest
import net.catzie.weather.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

    @GET("weather")
    suspend fun getWeather(@Body weatherRequest: WeatherRequest): Response<WeatherResponse>
}