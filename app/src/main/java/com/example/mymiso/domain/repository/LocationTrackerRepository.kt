package com.example.mymiso.domain.repository

import com.example.mymiso.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationTrackerRepository {
    suspend fun startLocationUpdates()
    suspend fun stopLocationUpdates()
    fun getLocationUpdates(): Flow<Location>
}