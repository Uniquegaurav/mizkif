package com.example.mymiso.presentation.order_screen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymiso.domain.use_cases.TrackOrderStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderStatusViewModel(private val trackOrderStatusUseCase: TrackOrderStatusUseCase) :
    ViewModel() {

    private val _orderStatus = MutableStateFlow("Idle")
    val orderStatus: StateFlow<String> = _orderStatus

    init {
        // Start tracking the order status when the ViewModel is created
        trackOrderStatus()
    }

    private fun trackOrderStatus() {
        viewModelScope.launch {
            trackOrderStatusUseCase(orderId = "2").collect { orderStatus ->
                _orderStatus.value = orderStatus.status // Update the StateFlow
            }
        }
    }
}
