package com.example.mymiso.features.design_patterns.facade

//Facade is a structural design pattern that provides a simplified interface to a complex system of classes, library, or framework.


// Facade as a helper that takes care of interacting with multiple systems or APIs behind the scenes, while the client only interacts with one simplified object.
class GoogleMapsApi {
    fun getCurrentLocation(): String {
        return "Current location from Google Maps"
    }

    fun getDistance(from: Int, to: Int): Int {
        return to - from
    }

    fun getETA(distance: Int): Int {
        return distance * 3
    }
}

class AppleMapsApi {
    fun getCurrentLocation(): String {
        return "Current location from Apple Maps"
    }

    fun getDistance(from: Int, to: Int): Int {
        return to - from
    }

    fun getETA(distance: Int): Int {
        return distance * 4
    }
}

class MapServiceFacade(
    private val googleMapsApi: GoogleMapsApi,
    private val appleMapsApi: AppleMapsApi
) {

    fun getCurrentLocation(): String {
        return googleMapsApi.getCurrentLocation()
    }

    fun calculateDeliveryETA(from: Int, to: Int, useGoogleMaps: Boolean): Int {
        val distance = if (useGoogleMaps) {
            googleMapsApi.getDistance(from, to)
        } else {
            appleMapsApi.getDistance(from, to)
        }
        return if (useGoogleMaps) {
            googleMapsApi.getETA(distance)
        } else {
            appleMapsApi.getETA(distance)
        }

    }
}

fun main() {
    val googleMapsApi = GoogleMapsApi()
    val appleMapsApi = AppleMapsApi()
    val mapService = MapServiceFacade(googleMapsApi, appleMapsApi)

    println(mapService.getCurrentLocation())
    println(mapService.calculateDeliveryETA(10, 20, true))
    println(mapService.calculateDeliveryETA(10, 20, false))
}


//Simplification: The client interacts with a single interface, which simplifies the process of working with multiple subsystems.
//Loose Coupling: The client is decoupled from the specific implementations of the subsystems. If the underlying API changes, only the Facade needs to be updated.
//Maintainability: Since the complexity is hidden behind the facade, it's easier to maintain the code and make future changes.