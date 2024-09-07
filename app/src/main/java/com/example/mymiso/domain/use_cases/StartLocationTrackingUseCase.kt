package com.example.mymiso.domain.use_cases

import com.example.mymiso.domain.repository.LocationTrackerRepository

class StartLocationTrackingUseCase(private val locationTrackerRepository: LocationTrackerRepository) {
    suspend operator fun invoke() {
        locationTrackerRepository.startLocationUpdates()
    }
}