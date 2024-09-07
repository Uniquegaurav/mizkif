package com.example.mymiso.domain.use_cases

import com.example.mymiso.domain.repository.LocationTrackerRepository

class ObserveLocationUpdateUseCase(private val locationTrackerRepository: LocationTrackerRepository) {
    operator fun invoke() = locationTrackerRepository.getLocationUpdates()
}