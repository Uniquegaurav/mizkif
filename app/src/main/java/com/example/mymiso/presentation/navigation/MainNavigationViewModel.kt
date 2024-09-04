package com.example.mymiso.presentation.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainNavigationViewModel : ViewModel() {

    private val _navigationState = MutableStateFlow<NavigationEvent?>(null)
    val navigationState: StateFlow<NavigationEvent?> = _navigationState.asStateFlow()

    fun navigateTo(event: NavigationEvent) {
        _navigationState.value = event
        Log.d("MainNavigationViewModel", "navigateTo: $event")
        Log.d("MainNavigationViewModel", "navigateTo: ${_navigationState.value}")
    }

    fun clearNavigation() {
        _navigationState.value = null
    }

}