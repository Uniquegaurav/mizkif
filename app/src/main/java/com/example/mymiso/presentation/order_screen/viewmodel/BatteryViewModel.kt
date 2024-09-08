package com.example.mymiso.presentation.order_screen.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mymiso.domain.use_cases.BatteryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BatteryViewModel : ViewModel() {

//    @Inject
//    private lateinit var batteryUseCase: BatteryUseCase(SyncManager())

    fun onBatteryLevelChanged(batteryPercentage: Int) {
//        batteryUseCase.handleBatteryLevelChange(batteryPercentage)
    }
}