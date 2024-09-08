package com.example.mymiso.domain.use_cases

import com.example.mymiso.domain.model.Location
import com.example.mymiso.domain.repository.DeliveryPartnerLocationRepository
import kotlinx.coroutines.flow.Flow

class TrackDeliveryPartnerLocationUseCase(private val repository: DeliveryPartnerLocationRepository) {

    operator fun invoke(): Flow<Location> = repository.getDeliveryPartnerLocation()
}
