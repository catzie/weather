package net.catzie.weather.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.catzie.weather.R
import net.catzie.weather.ui.auth.ui.auth.LoginFragment

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }
}