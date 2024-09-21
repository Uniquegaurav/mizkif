package com.example.mymiso.presentation.order_screen.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mymiso.R
import com.example.mymiso.databinding.FragmentOrderTrackingBinding
import com.example.mymiso.framework.receiver.BatteryReceiver
import com.example.mymiso.framework.service.LocationForegroundService
import com.example.mymiso.framework.worker.SyncManager
import com.example.mymiso.presentation.order_screen.viewmodel.BatteryViewModel

class OrderTrackingFragment : Fragment(R.layout.fragment_order_tracking) {

    private lateinit var binding: FragmentOrderTrackingBinding
    private lateinit var batteryReceiver: BatteryReceiver
    private lateinit var batteryViewModel: BatteryViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderTrackingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()

        binding.trackOrderButton.setOnClickListener {
            checkPermissionsAndStartService()
        }


        val syncManager = SyncManager(requireContext())
        syncManager.startDataSync()

        batteryReceiver = BatteryReceiver { batteryPercentage ->
            batteryViewModel.onBatteryLevelChanged(batteryPercentage)
        }
        batteryReceiver.register(requireContext())
    }

    private fun init() {
        setUpViewModel()
    }

    private fun setUpViewModel() {
        batteryViewModel = ViewModelProvider(requireActivity())[BatteryViewModel::class.java]
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
            if (fineLocationGranted) {
                startLocationService()
            } else {
                // Handle permission denial
            }
        }


    private fun checkPermissionsAndStartService() {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            permissions.add(Manifest.permission.FOREGROUND_SERVICE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            permissions.add(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
        }

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        } else {
            startLocationService()
        }
    }

    private fun startLocationService() {
        val intent = Intent(requireContext(), LocationForegroundService::class.java)
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    private fun stopLocationService() {
        val intent = Intent(requireContext(), LocationForegroundService::class.java)
        requireContext().stopService(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        batteryReceiver.unregister(requireContext())
    }
}
