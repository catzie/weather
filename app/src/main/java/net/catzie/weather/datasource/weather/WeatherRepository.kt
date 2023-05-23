package net.catzie.weather.datasource.weather

import net.catzie.weather.datasource.ApiInterface
import net.catzie.weather.model.weather.WeatherRequest
import net.catzie.weather.model.weather.WeatherResponse
import retrofit2.Response


class WeatherRepository(private val apiInterface: ApiInterface) {

    suspend fun getWeather(weatherRequest: WeatherRequest): Response<WeatherResponse> {

        val result =
            apiInterface.getWeather(
                weatherRequest.lat,
                weatherRequest.lon,
                "metric",
                weatherRequest.appId
            )

        return result
    }
}