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
    private lateinit var binding2 : ActivityLogInBinding
    private lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding2.root)
        supportActionBar?.hide()

        // Toggle visibility
        val textInputLayout = findViewById<TextInputLayout>(R.id.passwordlayout)
        val passwordEditText = findViewById<TextInputEditText>(R.id.Password)

        binding2.SignUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding2.forgotBtn.setOnClickListener{
            startActivity(Intent(this, ForgotPassword1::class.java))

        }
        auth = FirebaseAuth.getInstance()

        if(auth.currentUser == null){
            setContentView(binding2.root)
            binding2.loginBtn.setOnClickListener(){
                val email = binding2.Email.text.toString()
                val password = binding2.Password.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()){
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, MainScreen::class.java))
                            } else {
                                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                }
            }
        }else{
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