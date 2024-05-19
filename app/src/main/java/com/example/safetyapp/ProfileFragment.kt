package com.example.safetyapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProfileFragment : Fragment() {
    private lateinit var nameTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var database: DatabaseReference
    private lateinit var themeSwitch: SwitchCompat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        nameTextView = view.findViewById(R.id.nameTextView)
        phoneTextView = view.findViewById(R.id.phoneTextView)
        themeSwitch = view.findViewById(R.id.theme_switch)
        themeSwitch.isChecked = isDarkThemeEnabled()
        database = FirebaseDatabase.getInstance().getReference("users")

        fetchAndDisplayUserData()

        // Set up a listener for the switch to detect when its state changes
        themeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Dark theme selected
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Light theme selected
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        return view
    }

    private fun fetchAndDisplayUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            val userRef = database.child(userId)

            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val name = dataSnapshot.child("name").value.toString()
                        val phone = dataSnapshot.child("phone").value.toString()

                        // Set name and phone to TextViews
                        nameTextView.text = name
                        phoneTextView.text = phone

                        // Log user's name and phone number
                        Log.d("ProfileFragment", "Name: $name, Phone: $phone")
                    } else {
                        // Data for user doesn't exist
                        Log.d("ProfileFragment", "User data doesn't exist")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle database errors
                    Log.e("ProfileFragment", "Error fetching user data: ${databaseError.message}")
                }
            })
        }
    }

    // Function to check if the dark theme is currently enabled
    private fun isDarkThemeEnabled(): Boolean {
        return AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
    }
}