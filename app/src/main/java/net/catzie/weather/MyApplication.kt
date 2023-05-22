package net.catzie.weather

import android.app.Application
import net.catzie.weather.datasource.AuthSessionManager
import timber.log.Timber

class MyApplication : Application() {

    val authSessionManager by lazy { AuthSessionManager(this) }

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}