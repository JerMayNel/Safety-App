package com.example.safetyapp

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class MyApp : Application() {

    companion object {
        private const val PREFS_NAME = "prefs"
        private const val DARK_MODE_KEY = "dark_mode"
    }

    override fun onCreate() {
        super.onCreate()

        // Apply the theme before any activity is created
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isDarkModeEnabled = sharedPreferences.getBoolean(DARK_MODE_KEY, false)
        if (isDarkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}
