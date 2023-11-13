package com.vinz.playerpedia.activity.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.vinz.playerpedia.R
import com.vinz.playerpedia.activity.home.MainActivity
import com.vinz.playerpedia.activity.login.LoginActivity
import com.vinz.core.data.source.local.datastore.NightModePreferences
import com.vinz.core.data.source.local.datastore.NightModeViewModel
import com.vinz.core.data.source.local.datastore.NightModeViewModelFactory
import com.vinz.core.data.source.local.datastore.dataStore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = NightModePreferences.getInstance(application.dataStore)
        val nightModeViewModel =
            ViewModelProvider(this, NightModeViewModelFactory(pref))[NightModeViewModel::class.java]
        nightModeViewModel.getNightModeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        setContentView(R.layout.activity_splash_screen)

        val sharedPreferences = getSharedPreferences("isUserLogin", MODE_PRIVATE)
        val isUserLogin = sharedPreferences.getBoolean("isUserLogin", false)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isUserLogin) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }, 3000L)
    }
}