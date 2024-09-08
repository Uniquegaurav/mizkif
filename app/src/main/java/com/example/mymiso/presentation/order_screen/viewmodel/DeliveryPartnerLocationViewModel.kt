package com.example.mymiso.presentation.order_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymiso.domain.model.Location
import com.example.mymiso.domain.use_cases.TrackDeliveryPartnerLocationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeliveryPartnerLocationViewModel(private val trackLocationUseCase: TrackDeliveryPartnerLocationUseCase) :
    ViewModel() {

    private val _locationState = MutableStateFlow(Location(0.0, 0.0))
    val locationState: StateFlow<Location> = _locationState

    init {
        startTrackingLocation()
    }

    private fun startTrackingLocation() {
        viewModelScope.launch {
            trackLocationUseCase().collect { location ->
                _locationState.value = location
            }
        }
    }
}