package com.example.safetyapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditMyProfile : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var firestore: FirebaseFirestore
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_my_profile)

        // Initialize views
        nameEditText = findViewById(R.id.name)
        phoneEditText = findViewById(R.id.contactnumber)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        // Fetch and display current user data
        fetchAndDisplayUserData()

        // Set click listeners
        setBackButtonClickListener()
        setSaveButtonClickListener()
    }

    private fun fetchAndDisplayUserData() {
        val userDocumentRef = firestore.collection("users").document(userId)

        userDocumentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val name = documentSnapshot.getString("name")
                    val phone = documentSnapshot.getString("phone")

                    // Set retrieved data to EditTexts
                    nameEditText.setText(name)
                    phoneEditText.setText(phone)
                } else {
                    // Document doesn't exist
                    // Handle the case when user document doesn't exist
                }
            }
            .addOnFailureListener { e ->
                // Handle failures
                // Log error or display a message
            }
    }

    private fun setBackButtonClickListener() {
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            // Navigate back to the previous activity
            onBackPressed()
        }
    }

    private fun setSaveButtonClickListener() {
        val saveButton = findViewById<ImageButton>(R.id.SaveButton)
        saveButton.setOnClickListener {
            // Get updated name and phone number
            val newName = nameEditText.text.toString().trim()
            val newPhone = phoneEditText.text.toString().trim()

            // Update Firestore with new data
            updateUserData(newName, newPhone)
            finish()
        }
    }

    private fun updateUserData(name: String, phone: String) {
        val userDocumentRef = firestore.collection("users").document(userId)

        val userData = hashMapOf(
            "name" to name,
            "phone" to phone
        )

        userDocumentRef.set(userData)
            .addOnSuccessListener {
                // Handle success
                // Display a success message or perform any necessary action
            }
            .addOnFailureListener { e ->
                // Handle failures
                // Display an error message or log the error
            }
    }
}
