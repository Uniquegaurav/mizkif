package com.example.mymiso.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.mymiso.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

//    private lateinit var batteryReceiver: BatteryReceiver
//    private lateinit var batteryViewModel: BatteryViewModel

//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
//            val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
//            if (fineLocationGranted) {
//                startLocationService()
//            } else {
//                // Handle permission denial
//            }
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        if (navHostFragment != null) {
            bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        }

//        checkPermissionsAndStartService()
//
//        val syncManager = SyncManager(applicationContext)
//        syncManager.startDataSync()

//        batteryReceiver = BatteryReceiver { batteryPercentage ->
//            batteryViewModel.onBatteryLevelChanged(batteryPercentage)
//        }
//        batteryReceiver.register(this)


    }

//    private fun checkPermissionsAndStartService() {
//        val permissions = mutableListOf(
//            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
//        )
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            permissions.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
//        }
//
//        val permissionsToRequest = permissions.filter {
//            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
//        }
//
//        if (permissionsToRequest.isNotEmpty()) {
//            requestPermissionLauncher.launch(permissionsToRequest.toTypedArray())
//        } else {
//            startLocationService()
//        }
//    }

//    private fun startLocationService() {
//        val intent = Intent(this, LocationForegroundService::class.java)
//        ContextCompat.startForegroundService(this, intent)
//    }
//
//    private fun stopLocationService() {
//        val intent = Intent(this, LocationForegroundService::class.java)
//        stopService(intent)
//    }


    override fun onDestroy() {
        super.onDestroy()
//        batteryReceiver.unregister(this)
    }
}
