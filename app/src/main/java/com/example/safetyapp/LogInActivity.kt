package com.example.safetyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safetyapp.databinding.ActivityLogInBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Access views after setting content view using data binding
        val textInputLayout = findViewById<TextInputLayout>(R.id.passwordlayout)
        val passwordEditText = findViewById<TextInputEditText>(R.id.Password)


        binding.SignUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.forgotBtn.setOnClickListener {
            startActivity(Intent(this, ForgotPassword1::class.java))
        }

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            binding.loginBtn.setOnClickListener {
                val email = binding.Email.text.toString()
                val password = binding.Password.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, MainScreen::class.java))
                            } else {
                                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            startActivity(Intent(this, MainScreen::class.java))
        }

        // Set up the password toggle icon visibility
        textInputLayout.endIconMode = TextInputLayout.END_ICON_NONE

        passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                textInputLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            } else {
                textInputLayout.endIconMode = TextInputLayout.END_ICON_NONE
            }
        }
    }
}