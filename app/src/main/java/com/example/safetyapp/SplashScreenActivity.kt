package com.example.safetyapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 4000
    private val PERMISSIONS_REQUEST_CODE = 123
    private val PREFS_NAME = "MyPrefs"
    private val FIRST_TIME_FLAG = "first_time"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pre_splash_activity)

        val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isFirstTime = sharedPrefs.getBoolean(FIRST_TIME_FLAG, true)

        if (isFirstTime) {
            // First time launching the app, request permissions and show theme selection
            requestPermissionsAndShowThemeSelection()
            sharedPrefs.edit().putBoolean(FIRST_TIME_FLAG, false).apply()
        } else {
            // Not the first time, proceed to start the main activity
            startMainActivity()
        }
    }

    private fun requestPermissionsAndShowThemeSelection() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.INTERNET
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the SEND_SMS and INTERNET permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.SEND_SMS,
                    Manifest.permission.INTERNET
                ),
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            // Permissions are already granted, proceed to start the theme selection activity
            startThemeSelectionActivity()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // Permissions granted, proceed to start the theme selection activity
                startThemeSelectionActivity()
            } else {
                // Permissions denied, handle accordingly (e.g., show a message)
                Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startThemeSelectionActivity() {
        Handler().postDelayed({
            startActivity(Intent(this, ThemeSelectionActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }

    private fun startMainActivity() {
        Handler().postDelayed({
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}
