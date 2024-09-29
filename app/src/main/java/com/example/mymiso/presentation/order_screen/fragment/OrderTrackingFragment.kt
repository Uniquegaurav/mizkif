package com.example.mymiso.presentation.order_screen.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mymiso.R
import com.example.mymiso.databinding.FragmentOrderTrackingBinding
import com.example.mymiso.framework.receiver.BatteryReceiver
import com.example.mymiso.framework.service.DeliveryTrackingService
import com.example.mymiso.presentation.order_screen.viewmodel.BatteryViewModel

class OrderTrackingFragment : Fragment(R.layout.fragment_order_tracking) {

    private lateinit var binding: FragmentOrderTrackingBinding
    private lateinit var batteryReceiver: BatteryReceiver
    private lateinit var batteryViewModel: BatteryViewModel

    private val requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                checkAndRequestNotificationPermission() // Check for notification permission
            } else {
                Toast.makeText(requireContext(), "Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val requestBackgroundLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                checkAndRequestNotificationPermission() // Check for notification permission
            } else {
                Toast.makeText(requireContext(), "Background Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, proceed to start foreground service
                startLocationService()
            } else {
                Toast.makeText(requireContext(), "Notification Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

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
        initViewModel()
        checkAndRequestLocationPermissions()

        binding.trackOrderButton.setOnClickListener {
            stopLocationService()
        }
    }

    private fun initViewModel() {
        batteryViewModel = ViewModelProvider(requireActivity())[BatteryViewModel::class.java]
    }

    private fun checkAndRequestLocationPermissions() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                checkAndRequestBackgroundPermission()
            }
            else -> {
                requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun checkAndRequestBackgroundPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestBackgroundLocationPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            } else {
                checkAndRequestNotificationPermission()
            }
        } else {
            checkAndRequestNotificationPermission() // Directly check for notification permission for Android versions below Q
        }
    }

    private fun checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                startLocationService() // If already granted, start the service
            }
        } else {
            startLocationService() // No need to check for notification permission for older versions
        }
    }

    private fun startLocationService() {
        val intent = Intent(requireContext(), DeliveryTrackingService::class.java)
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    private fun stopLocationService() {
        val intent = Intent(requireContext(), DeliveryTrackingService::class.java)
        requireContext().stopService(intent)
    }

    override fun onResume() {
        super.onResume()
        batteryReceiver = BatteryReceiver { batteryPercentage ->
            batteryViewModel.onBatteryLevelChanged(batteryPercentage)
        }
        batteryReceiver.register(requireContext())
    }

    override fun onPause() {
        super.onPause()
        batteryReceiver.unregister(requireContext())
    }
}
