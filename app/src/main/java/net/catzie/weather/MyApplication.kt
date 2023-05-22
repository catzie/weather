package net.catzie.weather

import android.app.Application
import net.catzie.weather.datasource.AuthSessionManager
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