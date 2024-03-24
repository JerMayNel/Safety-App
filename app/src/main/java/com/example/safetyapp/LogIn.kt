package com.example.safetyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LogIn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSignUpButtonClickListener()
        setForgotButtonClickListener()
        setLoginButtonClickListener()
    }

    private fun setSignUpButtonClickListener() {
        val signInButton = findViewById<Button>(R.id.SignUp_btn)
        signInButton.setOnClickListener {
            // Start SignUp activity when SignIn_btn is clicked
            startActivity(Intent(this@LogIn, SignUp::class.java))
        }
    }

    private fun setForgotButtonClickListener() {
        val ForgotPasswordButton = findViewById<Button>(R.id.forgot_btn)
        ForgotPasswordButton.setOnClickListener {
            // Start LogIn activity when submit_btn is clicked
            startActivity(Intent(this@LogIn, ForgotPassword1::class.java))

        }
    }

    private fun setLoginButtonClickListener() {
        val LoginButton = findViewById<Button>(R.id.login_btn)
        LoginButton.setOnClickListener {
            // Start LogIn activity when submit_btn is clicked
            startActivity(Intent(this@LogIn, MainScreen::class.java))

        }
    }


}