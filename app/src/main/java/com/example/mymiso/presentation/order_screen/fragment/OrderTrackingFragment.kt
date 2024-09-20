package com.example.mymiso.presentation.order_screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymiso.R

class OrderTrackingFragment : Fragment(R.layout.fragment_order_tracking) {

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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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