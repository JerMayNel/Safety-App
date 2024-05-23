package com.example.safetyapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.safetyapp.R
import com.example.safetyapp.data.ContactsViewModel
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class ButtonFragment : Fragment(), KeyEvent.Callback {

    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: android.location.LocationListener
    private var volumeUpPressedCount = 0
    private val volumeUpButtonReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == Intent.ACTION_MEDIA_BUTTON) {
                val keyEvent = intent.getParcelableExtra<KeyEvent>(Intent.EXTRA_KEY_EVENT)
                if (keyEvent?.keyCode == KeyEvent.KEYCODE_VOLUME_UP && keyEvent.action == KeyEvent.ACTION_UP) {
                    volumeUpPressedCount++
                    if (volumeUpPressedCount == 3) {
                        volumeUpPressedCount = 0 // Reset count for next gesture
                        sendEmergencySMS()
                    }
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_button, container, false)

        val emergencyButton: ImageButton = view.findViewById(R.id.emergency_button)

        // Initialize ContactsViewModel
        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        emergencyButton.setOnClickListener {
            if (checkPermissions()) {
                sendEmergencySMS()
            } else {
                requestPermissions()
            }
        }

        // Initialize location manager
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Initialize location listener
        locationListener = object : android.location.LocationListener {
            override fun onLocationChanged(location: Location) {
                // Log location changes
                Log.d("LocationListener", "Location changed: $location")

                // Handle location updates here if needed
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                // Log status changes if needed
                Log.d(
                    "LocationListener",
                    "Provider status changed: $provider, Status: $status"
                )

                // Handle status changes if needed
            }

            override fun onProviderEnabled(provider: String) {
                // Log provider enabled if needed
                Log.d("LocationListener", "Provider enabled: $provider")

                // Handle provider enabled if needed
            }

            override fun onProviderDisabled(provider: String) {
                // Log provider disabled if needed
                Log.d("LocationListener", "Provider disabled: $provider")

                // Handle provider disabled if needed
            }
        }

        // Request location updates
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES,
                locationListener
            )
        } catch (ex: SecurityException) {
            ex.printStackTrace()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        // Register receiver for volume up button events
        requireActivity().registerReceiver(
            volumeUpButtonReceiver,
            IntentFilter("android.intent.action.MEDIA_BUTTON")
        )
    }

    override fun onPause() {
        super.onPause()
        // Unregister receiver when fragment is not visible
        requireActivity().unregisterReceiver(volumeUpButtonReceiver)
    }

    private fun checkPermissions(): Boolean {
        return (ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.SEND_SMS
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermissions() {
        requestPermissions(
            arrayOf(
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_CODE
        )
    }

    private fun sendEmergencySMS() {
        if (checkPermissions() && isLocationEnabled()) {
            // Get current location
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val latitude = location?.latitude
            val longitude = location?.longitude

            if (latitude != null && longitude != null) {
                val mapLink = "https://maps.google.com/maps?q=$latitude,$longitude"

                FirebaseDynamicLinks.getInstance().createDynamicLink()
                    .setLink(Uri.parse(mapLink))
                    .setDomainUriPrefix("https://myfinelocation.page.link")
                    .buildShortDynamicLink()
                    .addOnSuccessListener { result ->
                        val shortLink = result.shortLink.toString().removePrefix("https://")
                        val message = "I need help! My current location is $shortLink"

                        contactsViewModel.readAllData.observe(viewLifecycleOwner) { contacts ->
                            for (contact in contacts) {
                                sendSMS(contact.number, message)
                            }
                        }
                    }
                    .addOnFailureListener { ex ->
                        Toast.makeText(
                            requireContext(),
                            "Failed to create dynamic link",
                            Toast.LENGTH_SHORT
                        ).show()
                        ex.printStackTrace()
                    }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Unable to retrieve current location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                requireContext(),
                "Location permission not granted or location service disabled",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(
                requireContext(),
                "Emergency SMS sent successfully!",
                Toast.LENGTH_SHORT
            ).show()
        } catch (ex: Exception) {
            Toast.makeText(requireContext(), "Failed to send SMS", Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
        }
    }

    companion object {
        private const val MIN_TIME_BW_UPDATES: Long = 5000
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 10f // 10 meters
        private const val PERMISSION_REQUEST_CODE = 123
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onKeyMultiple(keyCode: Int, count: Int, event: KeyEvent?): Boolean {
        TODO("Not yet implemented")
    }
}
