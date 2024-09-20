package com.example.mymiso.framework.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.mymiso.R
import com.example.mymiso.domain.model.Location
import com.example.mymiso.domain.repository.DeliveryPartnerLocationRepository
import com.example.mymiso.domain.use_cases.TrackDeliveryPartnerLocationUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeliveryTrackingService: LifecycleService() {

    private val channelId = "DeliveryTrackingChannel"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startTrackingLocation()
    }

    private fun startTrackingLocation() {
        val repository = DeliveryPartnerLocationRepository()
        val useCase = TrackDeliveryPartnerLocationUseCase(repository)

//        ltrackingJob = lifecycleScope.launch {
//            useCase().onEach { location ->
//                updateNotification(location)
//            }.collect() // Collecting the flow to keep it active
//        }
    }

    private fun updateNotification(location: Location) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Delivery Partner Location")
            .setContentText("Location: ${location.latitude}, ${location.longitude}")
            .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your own icon
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

//        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Delivery Tracking"
            val descriptionText = "Notification channel for tracking delivery partner"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

