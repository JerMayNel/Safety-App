package com.example.safetyapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangePassword : AppCompatActivity() {

    private lateinit var currentPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var currentPasswordLayout: TextInputLayout
    private lateinit var newPasswordLayout: TextInputLayout
    private lateinit var confirmPasswordLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        currentPasswordEditText = findViewById(R.id.currentpasswordEditText)
        newPasswordEditText = findViewById(R.id.newpasswordEditText)
        confirmPasswordEditText = findViewById(R.id.confirmpasswordEditText)
        currentPasswordLayout = findViewById(R.id.currentpassword_layout)
        newPasswordLayout = findViewById(R.id.newpassword_layout)
        confirmPasswordLayout = findViewById(R.id.confirmpassword_layout)

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            finish()
        }

        findViewById<FloatingActionButton>(R.id.saveButton).setOnClickListener {
            changePassword()
        }
        setPasswordToggle()
    }

    private fun changePassword() {
        val currentPassword = currentPasswordEditText.text.toString().trim()
        val newPassword = newPasswordEditText.text.toString().trim()
        val confirmPassword = confirmPasswordEditText.text.toString().trim()

        // Validate the entered passwords
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (newPassword != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }

        // Update the password
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
            user.reauthenticate(credential)
                .addOnSuccessListener {
                    user.updatePassword(newPassword)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Failed to update password: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Incorrect Old Password: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "You are not logged in.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setPasswordToggle() {
        // Set up the password toggle icon visibility
        currentPasswordLayout.endIconMode = TextInputLayout.END_ICON_NONE
        newPasswordLayout.endIconMode = TextInputLayout.END_ICON_NONE
        confirmPasswordLayout.endIconMode = TextInputLayout.END_ICON_NONE

        currentPasswordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                currentPasswordLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            } else {
                currentPasswordLayout.endIconMode = TextInputLayout.END_ICON_NONE
            }
        }

        newPasswordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                newPasswordLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            } else {
                newPasswordLayout.endIconMode = TextInputLayout.END_ICON_NONE
            }
        }

        confirmPasswordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                confirmPasswordLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
            } else {
                confirmPasswordLayout.endIconMode = TextInputLayout.END_ICON_NONE
            }
        }
    }

}
