package net.catzie.weather.ui.main

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.catzie.weather.BuildConfig
import net.catzie.weather.COORD_TAGUIG
import net.catzie.weather.MyApplication
import net.catzie.weather.R
import net.catzie.weather.datasource.auth.AuthSessionManager
import net.catzie.weather.datasource.weather.WeatherHistoryRepository
import net.catzie.weather.datasource.weather.WeatherRepository
import net.catzie.weather.model.ApiResult
import net.catzie.weather.model.weather.WeatherHistoryEntity
import net.catzie.weather.model.weather.WeatherRequest
import net.catzie.weather.model.weather.WeatherResponse

class MainViewModel(
    private val weatherRepository: WeatherRepository,
    private val weatherHistoryRepository: WeatherHistoryRepository,
    private val authSessionManager: AuthSessionManager,
) :
    ViewModel() {

    private val _weather = MutableLiveData<ApiResult<WeatherResponse>>()
    val weather: LiveData<ApiResult<WeatherResponse>> = _weather

    private val _history = MutableLiveData<ApiResult<List<WeatherHistoryEntity>>>()
    val history: LiveData<ApiResult<List<WeatherHistoryEntity>>> = _history

    init {
        requestCurrentWeather(
            WeatherRequest(
                COORD_TAGUIG.first,
                COORD_TAGUIG.second,
                BuildConfig.API_KEY
            )
        )
    }

    fun onWeatherHistoryFragmentCreated() {
        requestWeatherHistory()
    }

    fun getCurrentWeather(weatherRequest: WeatherRequest) {
        requestCurrentWeather(weatherRequest)
    }

    private fun requestWeatherHistory() {
        CoroutineScope(Dispatchers.IO).launch {

            val res = weatherHistoryRepository.get()

            if (res.isNotEmpty()) {
                //Handle success
                _history.postValue(ApiResult.Success(res))
            } else {
                // Handle error
                _history.postValue(ApiResult.Error(R.string.weather_history_res_failed))
            }
        }
    }

    private fun requestCurrentWeather(weatherRequest: WeatherRequest) {

        viewModelScope.launch {
            val res = weatherRepository.getWeather(weatherRequest)

            if (res.code() == 200) {

                // Handle success
                res.body()?.let { weather ->

                    _weather.value = ApiResult.Success(weather)

                    //save to storage
                    val weatherHistory =
                        WeatherHistoryEntity(
                            null,
                            weather.weather.first().main,
                            weather.weather.first().description,
                            weather.weather.first().icon,
                            weather.name,
                            weather.sys.country,
                            weather.main.temp,
                            weather.sys.sunrise,
                            weather.sys.sunset,
                        )

                    CoroutineScope(Dispatchers.IO).launch {
                        weatherHistoryRepository.insert(weatherHistory)
                    }
                }
            } else {

                // Handle error
                _weather.value = ApiResult.Error(R.string.weather_res_failed)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    val application =
                        checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                    return MainViewModel(
                        WeatherRepository((application as MyApplication).apiInterface),
                        (application as MyApplication).weatherHistoryRepository,
                        (application as MyApplication).authSessionManager
                    ) as T

                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}