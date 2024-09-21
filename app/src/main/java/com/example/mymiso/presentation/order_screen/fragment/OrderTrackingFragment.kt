package com.example.mymiso.presentation.order_screen.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mymiso.R
import com.example.mymiso.databinding.FragmentOrderTrackingBinding
import com.example.mymiso.framework.receiver.BatteryReceiver
import com.example.mymiso.framework.service.LocationForegroundService
import com.example.mymiso.presentation.order_screen.viewmodel.BatteryViewModel

class OrderTrackingFragment : Fragment(R.layout.fragment_order_tracking) {

    private lateinit var binding: FragmentOrderTrackingBinding
    private lateinit var batteryReceiver: BatteryReceiver
    private lateinit var batteryViewModel: BatteryViewModel

    // Permission Request Launchers
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            handlePermissionResult(permissions)
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
        checkPermissionsAndStartService()

        binding.trackOrderButton.setOnClickListener {
            stopLocationService()
        }
    }

    private fun initViewModel() {
        batteryViewModel = ViewModelProvider(requireActivity())[BatteryViewModel::class.java]
    }

    private fun checkPermissionsAndStartService() {
        if (hasLocationPermissions()) {
            startLocationService()
        } else {
            requestLocationPermissions()
        }
    }

    // Centralized Permission Check
    private fun hasLocationPermissions(): Boolean {
        val fineLocationGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseLocationGranted = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val backgroundLocationGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else true

        return fineLocationGranted && coarseLocationGranted && backgroundLocationGranted
    }

    // Request Required Permissions
    private fun requestLocationPermissions() {
        val permissionsToRequest = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionsToRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

        requestPermissionsLauncher.launch(permissionsToRequest.toTypedArray())
    }

    // Handle Permission Result
    private fun handlePermissionResult(permissions: Map<String, Boolean>) {
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        val backgroundLocationGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissions[Manifest.permission.ACCESS_BACKGROUND_LOCATION] ?: false
        } else true

        if (fineLocationGranted && coarseLocationGranted) {
            if (backgroundLocationGranted) {
                startLocationService()
            } else {
                Toast.makeText(requireContext(), "Background location permission is required for accurate tracking.", Toast.LENGTH_LONG).show()
            }
        } else {
            Log.d("OrderTrackingFragment", "Permissions denied: FineLocation: $fineLocationGranted, CoarseLocation: $coarseLocationGranted, BackgroundLocation: $backgroundLocationGranted")
            Toast.makeText(requireContext(), "Location permissions are required to track your order.", Toast.LENGTH_LONG).show()
        }
    }


    // Start Foreground Location Service
    private fun startLocationService() {
        Log.d("OrderTrackingFragment", "Starting Location Service")
        val intent = Intent(requireContext(), LocationForegroundService::class.java)
        ContextCompat.startForegroundService(requireContext(), intent)
    }

    // Stop Location Service
    private fun stopLocationService() {
        Log.d("OrderTrackingFragment", "Stopping Location Service")
        val intent = Intent(requireContext(), LocationForegroundService::class.java)
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
