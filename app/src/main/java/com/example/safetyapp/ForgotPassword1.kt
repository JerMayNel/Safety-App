package com.example.safetyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword1 : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var forgotEmail: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password1)

        auth = FirebaseAuth.getInstance()
        forgotEmail = findViewById(R.id.ForgotEmail)
        submitButton = findViewById(R.id.submit_btn)

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            finish()
        }

        submitButton.setOnClickListener {
            val email = forgotEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "Password reset email sent to $email",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this,
                            "Failed to send password reset email",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}