package com.example.safetyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_signup)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSignInButtonClickListener()
        setCreateAccountButtonClickListener()
    }

    private fun setSignInButtonClickListener() {
        val signInButton = findViewById<Button>(R.id.sign_in_btn)
        signInButton.setOnClickListener {
            // Start SignUp activity when SignIn_btn is clicked
            startActivity(Intent(this@SignUp, LogIn::class.java))
        }
    }

    private fun setCreateAccountButtonClickListener() {
        val CreateAccountButton = findViewById<Button>(R.id.create_account_Btn)
        CreateAccountButton.setOnClickListener {
            // Start AddContacts activity when button is clicked
            startActivity(Intent(this@SignUp, AddTrustedContacts::class.java))

        }
    }
}