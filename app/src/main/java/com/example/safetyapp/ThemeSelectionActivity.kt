package com.example.safetyapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class ThemeSelectionActivity : AppCompatActivity() {

    private lateinit var darkModeIcon: ImageView
    private lateinit var lightModeIcon: ImageView
    private lateinit var nextButton: Button
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prefered_theme)

        darkModeIcon = findViewById(R.id.iconMoon)
        lightModeIcon = findViewById(R.id.iconSun)
        nextButton = findViewById(R.id.nextButton)
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        findViewById<View>(R.id.darkCard).setOnClickListener { setDarkMode() }
        findViewById<View>(R.id.lightCard).setOnClickListener { setLightMode() }
        nextButton.setOnClickListener { nextpage() }

        // Determine the initial theme based on shared preferences
        if (isDarkModeEnabled()) {
            setDarkMode()
        } else {
            setLightMode()
        }
    }

    private fun setDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        saveThemeSelection(true)
        darkModeIcon.visibility = View.VISIBLE
        lightModeIcon.visibility = View.INVISIBLE
    }

    private fun setLightMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        saveThemeSelection(false)
        lightModeIcon.visibility = View.VISIBLE
        darkModeIcon.visibility = View.INVISIBLE
    }

    private fun saveThemeSelection(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(DARK_MODE_KEY, isDarkMode).apply()
    }

    private fun isDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean(DARK_MODE_KEY, false)
    }
    private fun nextpage() {
        startActivity(Intent(this, LogInActivity::class.java))
        finish()
    }

    companion object {
        private const val PREFS_NAME = "prefs"
        private const val DARK_MODE_KEY = "dark_mode"
    }
}
