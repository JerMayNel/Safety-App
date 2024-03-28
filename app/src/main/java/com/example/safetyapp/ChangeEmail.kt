package com.example.safetyapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth

class ChangeEmail : AppCompatActivity() {

    private lateinit var currentEmailTextView: TextView
    private lateinit var newEmailEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)

        currentEmailTextView = findViewById(R.id.currentEmailTextView)
        newEmailEditText = findViewById(R.id.newEmailEditText)

        // Fetch and display the current email
        fetchAndDisplayCurrentEmail()

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            finish()
        }

        findViewById<FloatingActionButton>(R.id.saveButton).setOnClickListener {
            // Update the email
            updateEmail()
        }
    }

    private fun fetchAndDisplayCurrentEmail() {
        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            val currentEmail = currentUser.email
            currentEmailTextView.text = currentEmail
        } else {
            Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateEmail() {
        val newEmail = newEmailEditText.text.toString().trim()

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) {
            currentUser.updateEmail(newEmail)
                .addOnSuccessListener {
                    Toast.makeText(this, "Email updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to update email: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show()
        }
    }
}
