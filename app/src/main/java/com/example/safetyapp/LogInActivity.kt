package com.example.safetyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safetyapp.databinding.ActivityLogInBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LogInActivity : AppCompatActivity() {

    private lateinit var binding2: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding2 = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding2.root)
        supportActionBar?.hide()

        // Toggle visibility
        val textInputLayout = findViewById<TextInputLayout>(R.id.passwordlayout)
        val passwordEditText = findViewById<TextInputEditText>(R.id.Password)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("users")

        binding2.SignUpBtn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding2.forgotBtn.setOnClickListener {
            startActivity(Intent(this, ForgotPassword1::class.java))
        }

        if (auth.currentUser == null) {
            binding2.loginBtn.setOnClickListener {
                val email = binding2.Email.text.toString()
                val password = binding2.Password.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                user?.uid?.let { userId ->
                                    // Retrieve user details from Firebase Realtime Database
                                    database.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                                        override fun onDataChange(snapshot: DataSnapshot) {
                                            if (snapshot.exists()) {
                                                val name = snapshot.child("name").value.toString()
                                                val phone = snapshot.child("phone").value.toString()
                                                // Now you have the user's name and phone
                                                Toast.makeText(this@LogInActivity, "Welcome back, $name! Your phone number is $phone", Toast.LENGTH_SHORT).show()
                                                startActivity(Intent(this@LogInActivity, MainScreen::class.java))
                                                finish()
                                            } else {
                                                // If no user data found, delete the account
                                                user.delete().addOnCompleteListener {
                                                    if (it.isSuccessful) {
                                                        Toast.makeText(this@LogInActivity, "Account deleted due to missing data", Toast.LENGTH_SHORT).show()
                                                    } else {
                                                        Toast.makeText(this@LogInActivity, "Failed to delete account", Toast.LENGTH_SHORT).show()
                                                    }
                                                }
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            // Handle error
                                            Toast.makeText(this@LogInActivity, "Failed to retrieve user data", Toast.LENGTH_SHORT).show()
                                            // Delete the account if failed to retrieve data
                                            user.delete().addOnCompleteListener {
                                                if (it.isSuccessful) {
                                                    Toast.makeText(this@LogInActivity, "Account deleted due to failed data retrieval", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Toast.makeText(this@LogInActivity, "Failed to delete account", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    })
                                }
                            } else {
                                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                                // If login failed, delete the account
                                auth.currentUser?.delete()?.addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast.makeText(this@LogInActivity, "Account deleted due to login failure", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this@LogInActivity, "Failed to delete account", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                } else {
                    Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            startActivity(Intent(this, MainScreen::class.java))
            finish()
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
