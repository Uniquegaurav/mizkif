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
import com.example.mymiso.framework.service.LocationForegroundService
import com.example.mymiso.presentation.order_screen.viewmodel.BatteryViewModel

class OrderTrackingFragment : Fragment(R.layout.fragment_order_tracking) {

    private lateinit var binding: FragmentOrderTrackingBinding
    private lateinit var batteryReceiver: BatteryReceiver
    private lateinit var batteryViewModel: BatteryViewModel

    private val requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // Permission granted, proceed to start foreground service
                startLocationService()
            } else {
                Toast.makeText(requireContext(), "Location Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }

    // Background location permission launcher
    private val requestBackgroundLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Background Location Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Background Location Permission Denied", Toast.LENGTH_SHORT).show()
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
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // Foreground location permission is already granted
                checkAndRequestBackgroundPermission()
                startLocationService()
            }
            else -> {
                // Request foreground location permission
                requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun checkAndRequestBackgroundPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Check if background location permission is needed (Android 10 and above)
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestBackgroundLocationPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
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
