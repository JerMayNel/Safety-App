package com.example.safetyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safetyapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        binding.createAccountBtn.setOnClickListener {
            val name = binding.fullNameEdittext.text.toString()
            val email = binding.emailEdittext.text.toString()
            val phone = binding.phoneNumberEdittext.text.toString()
            val password = binding.passwordEdittext.text.toString()
            val confirmPassword = binding.confirmPasswordEdittext.text.toString()
            supportActionBar?.hide()

            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = firebaseAuth.currentUser
                                val userId = user?.uid ?: ""

                                // Save additional user information to Firestore
                                val userMap = hashMapOf(
                                    "name" to name,
                                    "phone" to phone
                                )
                                firestore.collection("users").document(userId).set(userMap)
                                val intent = Intent(this, LogInActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                            }
                        }

                } else {
                    Toast.makeText(this, "Password and Confirm Password should be same", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
