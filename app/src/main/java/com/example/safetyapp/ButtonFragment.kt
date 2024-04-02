package com.example.safetyapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_button, container, false)
    }

    companion object {
        const val TAG = "ButtonFragment"
        const val SIGN_IN_RESULT_CODE = 1001
        private const val PERMISSION_SEND_SMS = 2
    }
}