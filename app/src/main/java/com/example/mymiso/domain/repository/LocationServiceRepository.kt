package com.example.mymiso.domain.repository

import com.example.mymiso.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface LocationServiceRepository {
    fun getLocationUpdates(): Flow<Location>
    fun startLocationUpdates()
    fun stopLocationUpdates()
}