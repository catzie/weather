package net.catzie.weather.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.catzie.weather.BuildConfig
import net.catzie.weather.COORD_TAGUIG
import net.catzie.weather.datasource.auth.AuthSessionManager
import net.catzie.weather.datasource.weather.WeatherHistoryRepository
import net.catzie.weather.datasource.weather.WeatherRepository
import net.catzie.weather.model.ApiResult
import net.catzie.weather.model.weather.WeatherHistoryEntity
import net.catzie.weather.model.weather.WeatherRequest
import net.catzie.weather.model.weather.WeatherResponse
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class SaveWeatherResponseTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockWeatherRepository: WeatherRepository

    @Mock
    private lateinit var mockWeatherHistoryRepository: WeatherHistoryRepository

    @Mock
    private lateinit var mockAuthSessionManager: AuthSessionManager

    @Mock
    private lateinit var mockWeatherObserver: Observer<ApiResult<WeatherResponse>>

    private val testJsonWeatherResponse =
        "{\"coord\":{\"lon\":121.0509,\"lat\":14.5176},\"weather\":[{\"id\":null,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"base\":\"stations\",\"main\":{\"temp\":32,\"feels_like\":39,\"temp_min\":31.09,\"temp_max\":32.19,\"pressure\":1011,\"humidity\":71},\"visibility\":10000,\"wind\":{\"speed\":3.58,\"deg\":271,\"gust\":8.05},\"clouds\":{\"all\":30},\"dt\":1684810824,\"sys\":{\"type\":2,\"id\":2005706,\"country\":\"PH\",\"sunrise\":1684790807,\"sunset\":1684837109},\"timezone\":28800,\"id\":1684309,\"name\":\"Taguig City\",\"cod\":200}"
    private val testWeatherRequest =
        WeatherRequest(COORD_TAGUIG.first, COORD_TAGUIG.second, BuildConfig.API_KEY)
    private val testWeatherResponse = Gson().fromJson(
        testJsonWeatherResponse, WeatherResponse::class.java
    )
    private val testSuccessfulResponse = Response.success(200, testWeatherResponse)

    private val testWeatherHistoryEntity = WeatherHistoryEntity(
        null, "Clouds", "scattered clouds", "03d", "Taguig City", "PH", 32.0, 1684790807, 1684837109
    )

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {

        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)

        viewModel = MainViewModel(
            mockWeatherRepository,
            mockWeatherHistoryRepository,
            mockAuthSessionManager
        )
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }


    @Test
    fun givenMainViewModel_whenGetCurrentWeatherIsCalled_thenHistoryRepositoryInsertFunctionShouldBeCalled() =
        runTest {

            `when`(mockWeatherRepository.getWeather(testWeatherRequest)).thenReturn(
                testSuccessfulResponse
            )
            `when`(mockWeatherHistoryRepository.insert(testWeatherHistoryEntity)).thenReturn(anyLong())

            viewModel.weather.observeForever(mockWeatherObserver)
            viewModel.getCurrentWeather(testWeatherRequest)

            verify(mockWeatherHistoryRepository).insert(testWeatherHistoryEntity)
        }
}
