package com.example.safetyapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditMyProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_my_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setBackButtonClickListener()
        setSaveButtonClickListener()
    }

    private fun setBackButtonClickListener() {
        val BackButton = findViewById<ImageButton>(R.id.back_button)
        BackButton.setOnClickListener {
            // Start SignUp activity when back_button is clicked
            startActivity(Intent(this@EditMyProfile, MainScreen::class.java))
        }
    }

    private fun setSaveButtonClickListener() {
        val BackButton = findViewById<ImageButton>(R.id.SaveButton)
        BackButton.setOnClickListener {
            // Start SignUp activity when back_button is clicked
            startActivity(Intent(this@EditMyProfile, MainScreen::class.java))
        }
    }
}