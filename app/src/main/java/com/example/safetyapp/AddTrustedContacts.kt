package com.example.safetyapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddTrustedContacts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_trusted_contacts)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setBackButtonClickListener()
        setAddButtonClickListener()
        setMaybeLaterButtonClickListener()
    }
    private fun setBackButtonClickListener() {
        val BackButton = findViewById<ImageButton>(R.id.back_button)
        BackButton.setOnClickListener {
            // Start SignUp activity when back_button is clicked
            startActivity(Intent(this@AddTrustedContacts, SignUpActivity::class.java))

        }
    }
    private fun setMaybeLaterButtonClickListener() {
        val MaybeLaterButton = findViewById<Button>(R.id.maybe_later_btn)
        MaybeLaterButton.setOnClickListener {
            // Start AskLocation activity when button is clicked
            startActivity(Intent(this@AddTrustedContacts, AskLocation::class.java))

        }

    }
    private fun setAddButtonClickListener() {
        val AddButton = findViewById<Button>(R.id.add_btn)
        AddButton.setOnClickListener {
            // Start AskLocation activity when button is clicked
            startActivity(Intent(this@AddTrustedContacts, AskLocation::class.java))

        }
    }

}