package com.example.mymiso.framework.location
import com.example.mymiso.domain.model.Location
import com.example.mymiso.domain.repository.LocationServiceRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FusedLocationService(private val fusedLocationClient: FusedLocationProviderClient) :
    LocationServiceRepository {
    override fun getLocationUpdates(): Flow<Location> = callbackFlow {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                for (location in result.locations) {
                    trySend(Location(location.latitude, location.longitude))
                }
            }
        }

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setMaxUpdates(1)
            .build()
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
            .addOnFailureListener { close(it) }

        awaitClose {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }


    }

    override fun startLocationUpdates() {
        // Requesting location updates is handled by getLocationUpdates()
    }

    override fun stopLocationUpdates() {
        // Removing location updates is handled by callBackFlow's await close
    }

}