package com.example.safetyapp

import androidx.fragment.app.Fragment
import com.example.safetyapp.databinding.FragmentButtonBinding
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.*


class ButtonFragment: Fragment(){
    private lateinit var binding : FragmentButtonBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var Latitude : String = ""
    private var Longitude : String = ""

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    companion object {
        const val TAG = "ButtonFragment"
        const val SIGN_IN_RESULT_CODE = 1001
        private const val PERMISSION_SEND_SMS = 2
    }
}