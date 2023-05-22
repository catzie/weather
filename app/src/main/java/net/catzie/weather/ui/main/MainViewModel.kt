package net.catzie.weather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.catzie.weather.model.ApiResult
import net.catzie.weather.model.WeatherResponse

class MainViewModel : ViewModel() {

    private val _weather = MutableLiveData<ApiResult<WeatherResponse>>()
    val weather: LiveData<ApiResult<WeatherResponse>> = _weather


}