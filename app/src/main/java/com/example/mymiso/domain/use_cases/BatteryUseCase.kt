package com.example.mymiso.domain.use_cases

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import com.example.mymiso.framework.worker.SyncManager

class BatteryUseCase(private val syncManager: SyncManager) {

    fun handleBatteryLevelChange(batteryPercentage: Int) {
        if (batteryPercentage < 20) {
            // Battery is too low, stop syncing data
            syncManager.stopDataSync()
        } else {
            // Battery is fine, continue syncing data
            syncManager.startDataSync()
        }
    }
}
