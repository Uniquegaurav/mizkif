package com.example.mymiso.presentation.navigation

sealed class NavigationEvent {
    data object ToFragmentSearchScreen : NavigationEvent()
    data object ToFragmentDetailsScreen : NavigationEvent()
    data object ToFragmentHomeScreen : NavigationEvent()
    data class ToFragmentWithArgs(val someArg: String) : NavigationEvent()
    data object Back : NavigationEvent()
}
