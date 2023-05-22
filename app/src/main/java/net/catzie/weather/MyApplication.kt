package net.catzie.weather

import android.app.Application
import androidx.room.Room
import net.catzie.weather.datasource.ApiInterface
import net.catzie.weather.datasource.AppDatabase
import net.catzie.weather.datasource.RetrofitClient
import net.catzie.weather.datasource.auth.AuthSessionManager
import net.catzie.weather.datasource.weather.WeatherHistoryRepository
import timber.log.Timber

val COORD_TAGUIG = Pair(14.5176, 121.0509)

class MyApplication : Application() {

    // API Requests
    private val retrofit by lazy { RetrofitClient.getInstance() }
    val apiInterface by lazy { retrofit.create(ApiInterface::class.java) }

    // Auth
    val authSessionManager by lazy { AuthSessionManager(this) }

    // Local Database
    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "weather-db"
        ).build()
    }
    val weatherHistoryRepository by lazy { WeatherHistoryRepository(database.weatherHistoryDao()) }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}