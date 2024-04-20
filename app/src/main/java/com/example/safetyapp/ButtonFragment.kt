package com.example.safetyapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.safetyapp.data.ContactsViewModel

class ButtonFragment : Fragment() {
    private lateinit var contactsViewModel: ContactsViewModel
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: android.location.LocationListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_button, container, false)

        val emergencyButton: Button = view.findViewById(R.id.emergency_button)

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
        locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Initialize location listener
        // Initialize location listener
        locationListener = object : android.location.LocationListener {
            override fun onLocationChanged(location: Location) {
                // Log location changes
                Log.d("LocationListener", "Location changed: $location")

                // Handle location updates here if needed
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                // Log status changes if needed
                Log.d("LocationListener", "Provider status changed: $provider, Status: $status")

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

    private fun checkPermissions(): Boolean {
        return (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
    }
    private fun isLocationEnabled(): Boolean {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


    private fun requestPermissions() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION
        ), PERMISSION_REQUEST_CODE)
    }

    private fun sendEmergencySMS() {
        if (checkPermissions() && isLocationEnabled()) {
            // Get current location
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            val latitude = location?.latitude
            val longitude = location?.longitude

            if (latitude != null && longitude != null) {
                val mapLink = StringBuilder("www.google.com/maps/search/?api=1&query=$latitude,$longitude")
                val link = mapLink.toString()
                val message = "I need help! My current location is at $link. Please send help!"

                // Get contacts from ViewModel
                contactsViewModel.readAllData.observe(viewLifecycleOwner) { contacts ->
                    // Send SMS to each contact
                    contacts.forEach { contact ->
                        sendSMS(contact.number, message)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Unable to retrieve current location", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Location permission not granted or location service disabled", Toast.LENGTH_SHORT).show()
        }
    }



    private fun sendSMS(phoneNumber: String, message: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(requireContext(), "Emergency SMS sent successfully!", Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(requireContext(), "Failed to send SMS", Toast.LENGTH_SHORT).show()
            ex.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Remove location updates when the fragment is destroyed
        locationManager.removeUpdates(locationListener)
    }

    companion object {
        private const val MIN_TIME_BW_UPDATES: Long = 5000
        private const val MIN_DISTANCE_CHANGE_FOR_UPDATES: Float = 10f // 10 meters
        private const val PERMISSION_REQUEST_CODE = 123
    }
}