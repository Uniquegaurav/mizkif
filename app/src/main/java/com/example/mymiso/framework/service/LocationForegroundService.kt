package com.example.mymiso.framework.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.mymiso.domain.use_cases.StartLocationTrackingUseCase
import com.example.mymiso.domain.use_cases.StopLocationTrackingUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationForegroundService @Inject constructor() : LifecycleService() {

    @Inject
    lateinit var startLocationTrackingUseCase: StartLocationTrackingUseCase

    @Inject
    lateinit var stopLocationTrackingUseCase: StopLocationTrackingUseCase

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.d("LocationForegroundService", "Service Started")

        // Start tracking location in the background
        lifecycleScope.launch(Dispatchers.IO) {
            startLocationTrackingUseCase()
        }

        return START_STICKY
    }

    private fun createNotificationChannel() {
        val channelID = "LocationChannel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelID,
                "Location Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Tracks your location for order updates"
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun startForegroundService() {
        val notification = NotificationCompat.Builder(this, "LocationChannel")
            .setContentTitle("Location Tracking Active")
            .setContentText("We are tracking your location for updates.")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

        Log.d("LocationForegroundService", "Foreground service started")
        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LocationForegroundService", "Service Stopped")
        lifecycleScope.launch(Dispatchers.IO) {
            stopLocationTrackingUseCase()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
    }
}
