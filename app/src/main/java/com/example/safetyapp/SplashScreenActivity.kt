package com.example.safetyapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity(){

    private val Splash_time_out:Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pre_splash_activity)

        Handler().postDelayed({
            try {
                startActivity(Intent(this, LogInActivity::class.java))
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle the exception, e.g., show a toast or log the error
            }
        }, Splash_time_out)


    }
}