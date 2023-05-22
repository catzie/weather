package net.catzie.weather

import android.app.Application
import net.catzie.weather.datasource.ApiInterface
import net.catzie.weather.datasource.RetrofitClient
import net.catzie.weather.datasource.auth.AuthSessionManager
import timber.log.Timber

class MyApplication : Application() {

    private val retrofit by lazy { RetrofitClient.getInstance() }
    val apiInterface by lazy { retrofit.create(ApiInterface::class.java) }

    val authSessionManager by lazy { AuthSessionManager(this) }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}