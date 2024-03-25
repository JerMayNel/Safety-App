package com.example.safetyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ForgotPassword1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forgot_password1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setBackButtonClickListener()
        setSubmitButtonClickListener()

    }

    private fun setBackButtonClickListener() {
        val BackButton = findViewById<ImageButton>(R.id.back_button)
        BackButton.setOnClickListener {
            // Start LogIn activity when back_button is clicked
            startActivity(Intent(this@ForgotPassword1, LogInAcitvity::class.java))

        }
    }

    private fun setSubmitButtonClickListener() {
        val SubmitButton = findViewById<Button>(R.id.submit_btn)
        SubmitButton.setOnClickListener {
            // Start ForgotPassword2 activity when submit_btn is clicked
            startActivity(Intent(this@ForgotPassword1, ForgotPassword2::class.java))

        }
    }
}