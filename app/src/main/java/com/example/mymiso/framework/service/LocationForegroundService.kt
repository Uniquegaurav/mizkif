package com.example.mymiso.framework.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.IBinder
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

        lifecycleScope.launch(Dispatchers.IO) {
            startLocationTrackingUseCase()

        }
        return super.onStartCommand(intent, flags, startId)

    }

    private fun createNotificationChannel() {
        val channelID = "LocationChannel"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelID,
                "Location Service",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Tracking your location"
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun startForegroundService() {
        val notification = NotificationCompat.Builder(this, "LocationChannel")
            .setContentTitle("Tracking Location")
            .setContentText("Your location is being tracked")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .build()

        startForeground(1, notification)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleScope.launch(Dispatchers.IO) {
            stopLocationTrackingUseCase()
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
    }
}
