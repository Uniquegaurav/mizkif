package com.example.mymiso.data.repository

import com.example.mymiso.domain.model.Location
import com.example.mymiso.domain.repository.LocationServiceRepository
import com.example.mymiso.domain.repository.LocationTrackerRepository
import kotlinx.coroutines.flow.Flow

class LocationTrackerRepositoryImpl(private val locationServiceRepository: LocationServiceRepository) :
    LocationTrackerRepository {
    override suspend fun startLocationUpdates() {
        locationServiceRepository.startLocationUpdates()
    }

    override suspend fun stopLocationUpdates() {
        locationServiceRepository.stopLocationUpdates()
    }

    override fun getLocationUpdates(): Flow<Location> =
        locationServiceRepository.getLocationUpdates()
}