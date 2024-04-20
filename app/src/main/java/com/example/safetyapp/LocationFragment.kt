package com.example.safetyapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.safetyapp.databinding.FragmentLocationBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback

class LocationFragment : Fragment(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentLocationBinding
    private lateinit var mapView: MapView
    private var isTrackingEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapView
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)

        // Check location permission status and set the switch accordingly
        updateLocationSwitch()


        binding.switch1.setOnCheckedChangeListener { _, isChecked ->
            isTrackingEnabled = isChecked
            if (isChecked) {
                enableLocationTracking()
            } else {
                disableLocationTracking()
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }

    private fun enableLocationTracking() {
        // Check and request location permission
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        } else {
            // Permission granted
            googleMap.isMyLocationEnabled = true
        }
    }

    private fun disableLocationTracking() {
        googleMap.isMyLocationEnabled = false
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permissions if not granted
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        } else {
            // Permission granted
            googleMap.isMyLocationEnabled = false
        }

    }

    private fun updateLocationSwitch() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            binding.switch1.isChecked = false
        } else {
            binding.switch1.isChecked = true
        }
    }
    override fun onResume(){
        super.onResume()
        mapView.onResume()
        if (isTrackingEnabled) {
            enableLocationTracking()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDestroy()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 123
    }
}

