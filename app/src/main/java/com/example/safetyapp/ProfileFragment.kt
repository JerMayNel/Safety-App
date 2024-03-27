package com.example.safetyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private lateinit var nameTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        nameTextView = view.findViewById(R.id.nameTextView)
        phoneTextView = view.findViewById(R.id.phoneTextView)

        firestore = FirebaseFirestore.getInstance()

        fetchAndDisplayUserData()

        return view
    }

    private fun fetchAndDisplayUserData() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            val userDocumentRef = firestore.collection("users").document(userId)

            userDocumentRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val name = documentSnapshot.getString("name")
                        val phone = documentSnapshot.getString("phone")

                        // Set name and phone to TextViews
                        nameTextView.text = name
                        phoneTextView.text = phone
                    } else {
                        // Document doesn't exist
                    }
                }
                .addOnFailureListener { e ->
                    // Handle failures
                }
        }
    }
}