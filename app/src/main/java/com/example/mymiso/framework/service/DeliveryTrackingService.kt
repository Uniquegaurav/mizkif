package com.example.mymiso.framework.service
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.mymiso.R
import com.example.mymiso.domain.model.Location
import com.example.mymiso.domain.use_cases.TrackDeliveryPartnerLocationUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DeliveryTrackingService : LifecycleService() {

    private val channelId = "DeliveryTrackingChannel"
    private var trackingJob: Job? = null


    @Inject
    lateinit var useCase: TrackDeliveryPartnerLocationUseCase

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startTrackingLocation()
    }

    private fun startTrackingLocation() {
        trackingJob = lifecycleScope.launch {
            useCase().onEach { location ->
                updateNotification(location)
            }.collect() // Collecting the flow to keep it active
        }
    }

    private fun updateNotification(location: Location) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Delivery Partner Location")
            .setContentText("Location: ${location.latitude}, ${location.longitude}")
            .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your own icon
            .setOngoing(true) // Makes the notification ongoing
            .build()

        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Delivery Tracking"
            val descriptionText = "Notification channel for tracking delivery partner"
            val importance = NotificationManager.IMPORTANCE_LOW // Set importance based on your requirement
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
        trackingJob?.cancel() // Cancel the job to avoid memory leaks
    }
}
