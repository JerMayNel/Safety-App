package com.example.safetyapp

import android.os.Bundle
import android.widget.GridView
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AboutUs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about_us)

        val gridView: GridView = findViewById(R.id.gridView)

        // Sample data
        val names = arrayOf("Janel Antolin", "Jeramelle Dela Cruz", "Mi-ro Dela Cruz", "Revin Jay Delgado", "Angela Olaera", "Jomari Sedanto")
        val roles = arrayOf("Lead Developer", "Lead Developer", "Member", "Member", "Member", "Member")
        val images = intArrayOf(R.drawable.janel_icon, R.drawable.jeramelle_icon, R.drawable.miro_icon, R.drawable.revin_icon,  R.drawable.angela_icon,  R.drawable.jomari_icon)

        val adapter = GridAdapter(this, names, roles, images)
        gridView.adapter = adapter

        setBackButtonClickListener()
    }

    private fun setBackButtonClickListener() {
        val backButton = findViewById<ImageButton>(R.id.back_button)
        backButton.setOnClickListener {
            // Navigate back to the previous activity
            onBackPressed()
        }
    }
}