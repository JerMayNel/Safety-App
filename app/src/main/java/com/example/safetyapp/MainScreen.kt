package com.example.safetyapp


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.safetyapp.database.list.ContactsFragment
import com.google.firebase.auth.FirebaseAuth

class MainScreen : AppCompatActivity() {

    private var selectedTab = 1 // Use var instead of int for Kotlin
    private lateinit var dialog: Dialog
    private lateinit var btndialogCancel: Button
    private lateinit var btnDialogLogout: Button
    private lateinit var verifydialog: Dialog
    private lateinit var btnVerifySubmit: Button
    private lateinit var btnVerifyCancel: Button

    private var doubleBackToExitPressedOnce: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_screen)
        supportActionBar?.hide()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dialog = Dialog(this)
        dialog.setContentView(R.layout.log_out_dialog_box)
        dialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(getDrawable(R.drawable.logout_dialog_bg))
        dialog.setCancelable(false)

        btndialogCancel = dialog.findViewById(R.id.cancel_button)
        btnDialogLogout = dialog.findViewById(R.id.logout_button)

        btndialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnDialogLogout.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            auth.signOut()
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Logout Successful!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        verifydialog = Dialog(this)
        verifydialog.setContentView(R.layout.verify_dialog_box)
        verifydialog.window?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        verifydialog.window?.setBackgroundDrawable(getDrawable(R.drawable.logout_dialog_bg))
        verifydialog.setCancelable(false)

        btnVerifyCancel = verifydialog.findViewById(R.id.cancel_verify_button)
        btnVerifySubmit = verifydialog.findViewById(R.id.submit_button)

        btnVerifyCancel.setOnClickListener {
            verifydialog.dismiss()
        }

        btnVerifySubmit.setOnClickListener {
            // TODO: CREATE INTERFACE FOR CHANGE PASSWORD AND EMAIL
            verifydialog.dismiss()
        }

        val homelayout = findViewById<LinearLayout>(R.id.home_layout)
        val contactslayout = findViewById<LinearLayout>(R.id.contacts_layout)
        val locationlayout = findViewById<LinearLayout>(R.id.location_layout)
        val profilelayout = findViewById<LinearLayout>(R.id.profile_layout)

        val homeimage = findViewById<ImageView>(R.id.home_image)
        val contactsimage = findViewById<ImageView>(R.id.contacts_image)
        val locationimage = findViewById<ImageView>(R.id.location_image)
        val profileimage = findViewById<ImageView>(R.id.profile_image)

        val hometext = findViewById<TextView>(R.id.home_text)
        val contacttext = findViewById<TextView>(R.id.contacts_text)
        val locationtext = findViewById<TextView>(R.id.location_text)
        val profiletext = findViewById<TextView>(R.id.profile_text)

        supportFragmentManager.beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainer, ButtonFragment::class.java, null)
            .commit()

        homelayout.setOnClickListener { v: View ->
            if (selectedTab != 1) {

                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, ButtonFragment::class.java, null)
                    .commit()

                contacttext.visibility = View.GONE
                locationtext.visibility = View.GONE
                profiletext.visibility = View.GONE

                contactsimage.setImageResource(R.drawable.contacts_icon)
                locationimage.setImageResource(R.drawable.location_icon)
                profileimage.setImageResource(R.drawable.profile_icon)

                contactslayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                locationlayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                profilelayout.setBackgroundColor(resources.getColor(android.R.color.transparent))

                hometext.visibility = View.VISIBLE
                homeimage.setImageResource(R.drawable.selected_shield_icon)
                homelayout.setBackgroundResource(R.drawable.round_back_shield_100)

                val scaleAnimation = ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f)
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                homelayout.startAnimation(scaleAnimation)

                selectedTab = 1

            }
        }

        contactslayout.setOnClickListener { v: View ->
            if (selectedTab != 2) {

                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, ContactsFragment::class.java, null)
                    .commit()

                hometext.visibility = View.GONE
                locationtext.visibility = View.GONE
                profiletext.visibility = View.GONE

                homeimage.setImageResource(R.drawable.shield_icon)
                locationimage.setImageResource(R.drawable.location_icon)
                profileimage.setImageResource(R.drawable.profile_icon)

                homelayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                locationlayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                profilelayout.setBackgroundColor(resources.getColor(android.R.color.transparent))

                contacttext.visibility = View.VISIBLE
                contactsimage.setImageResource(R.drawable.selected_contacts_icon)
                contactslayout.setBackgroundResource(R.drawable.round_back_shield_100)

                val scaleAnimation = ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f)
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                contactslayout.startAnimation(scaleAnimation)

                selectedTab = 2

            }
        }

        locationlayout.setOnClickListener { v: View ->
            if (selectedTab != 3) {

                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, LocationFragment::class.java, null)
                    .commit()

                hometext.visibility = View.GONE
                contacttext.visibility = View.GONE
                profiletext.visibility = View.GONE

                homeimage.setImageResource(R.drawable.shield_icon)
                contactsimage.setImageResource(R.drawable.contacts_icon)
                profileimage.setImageResource(R.drawable.profile_icon)

                homelayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                contactslayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                profilelayout.setBackgroundColor(resources.getColor(android.R.color.transparent))

                locationtext.visibility = View.VISIBLE
                locationimage.setImageResource(R.drawable.selected_location_icon)
                locationlayout.setBackgroundResource(R.drawable.round_back_shield_100)

                val scaleAnimation = ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f)
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                locationlayout.startAnimation(scaleAnimation)

                selectedTab = 3

            }
        }

        profilelayout.setOnClickListener { v: View ->
            if (selectedTab != 4) {

                supportFragmentManager.beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragmentContainer, ProfileFragment::class.java, null)
                    .commit()

                hometext.visibility = View.GONE
                contacttext.visibility = View.GONE
                locationtext.visibility = View.GONE

                homeimage.setImageResource(R.drawable.shield_icon)
                contactsimage.setImageResource(R.drawable.contacts_icon)
                locationimage.setImageResource(R.drawable.location_icon)

                homelayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                contactslayout.setBackgroundColor(resources.getColor(android.R.color.transparent))
                locationlayout.setBackgroundColor(resources.getColor(android.R.color.transparent))

                profiletext.visibility = View.VISIBLE
                profileimage.setImageResource(R.drawable.selected_profile_icon)
                profilelayout.setBackgroundResource(R.drawable.round_back_shield_100)

                val scaleAnimation = ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f)
                scaleAnimation.duration = 200
                scaleAnimation.fillAfter = true
                profilelayout.startAnimation(scaleAnimation)

                selectedTab = 4

            }

        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
    }

    fun EditProfile(view: View?) {
        val intent = Intent(this, EditMyProfile::class.java)
        startActivity(intent)
    }

    fun Logout(view: View?) {
        dialog.show()
    }

    fun ChangeEmail(view: View?) {
        verifydialog.show()
    }

    fun ChangePassword(view: View?) {
        verifydialog.show()
    }

}