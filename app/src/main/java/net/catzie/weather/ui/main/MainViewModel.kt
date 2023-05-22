package net.catzie.weather.ui.main

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import net.catzie.weather.BuildConfig
import net.catzie.weather.COORD_TAGUIG
import net.catzie.weather.MyApplication
import net.catzie.weather.R
import net.catzie.weather.datasource.auth.AuthSessionManager
import net.catzie.weather.datasource.weather.WeatherRepository
import net.catzie.weather.model.ApiResult
import net.catzie.weather.model.weather.WeatherRequest
import net.catzie.weather.model.weather.WeatherResponse
import timber.log.Timber

class MainViewModel(weatherRepository: WeatherRepository, authSessionManager: AuthSessionManager) :
    ViewModel() {

    private val _weather = MutableLiveData<ApiResult<WeatherResponse>>()
    val weather: LiveData<ApiResult<WeatherResponse>> = _weather

    init {
        val weatherRequest =
            WeatherRequest(COORD_TAGUIG.first, COORD_TAGUIG.second, BuildConfig.API_KEY)
        viewModelScope.launch {
            val res = weatherRepository.getWeather(weatherRequest)

            if (res.code() == 200) {

                // Handle success
                res.body()?.let { weather ->

                    _weather.value = ApiResult.Success(weather)

                    //todo remove this chunk after storage
                    with(weather) {
                        Timber.d("weatherRequest: weather=${weather}")
                        Timber.d("weatherRequest: city=${name}")
                        Timber.d("weatherRequest: country=${sys.country}")
                        Timber.d("weatherRequest: temp=${main.temp}")
                        Timber.d("weatherRequest: sunrise=${sys.sunrise}")
                        Timber.d("weatherRequest: sunset=${sys.sunset}")

                    }
                    //todo save to storage
                }
            } else {

                // Handle error
                _weather.value = ApiResult.Error(R.string.weather_res_failed)
            }
        }
    }

    fun testCall() {

    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    val application =
                        checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                    return MainViewModel(
                        WeatherRepository((application as MyApplication).apiInterface),
                        (application as MyApplication).authSessionManager
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}