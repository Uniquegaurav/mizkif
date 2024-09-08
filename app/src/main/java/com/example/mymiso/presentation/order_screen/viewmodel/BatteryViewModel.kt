package com.example.mymiso.presentation.order_screen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mymiso.domain.use_cases.BatteryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BatteryViewModel @Inject constructor() : ViewModel() {

    @Inject
    private lateinit var batteryUseCase: BatteryUseCase

    fun onBatteryLevelChanged(batteryPercentage: Int) {
        batteryUseCase.handleBatteryLevelChange(batteryPercentage)
    }
}