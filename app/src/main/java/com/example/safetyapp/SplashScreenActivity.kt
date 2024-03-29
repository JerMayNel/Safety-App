package com.example.safetyapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long = 1000
    private val SEND_SMS_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pre_splash_activity)

        // Check if the SEND_SMS permission is not granted
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request the SEND_SMS permission
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                SEND_SMS_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission is already granted, proceed to start the main activity
            startMainActivity()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SEND_SMS_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to start the main activity
                startMainActivity()
            } else {
                // Permission denied, handle accordingly (e.g., show a message)
            }
        }
    }

    private fun startMainActivity() {
        Handler().postDelayed({
            try {
                startActivity(Intent(this, LogInActivity::class.java))
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle the exception, e.g., show a toast or log the error
            }
        }, SPLASH_TIME_OUT)
    }
}
