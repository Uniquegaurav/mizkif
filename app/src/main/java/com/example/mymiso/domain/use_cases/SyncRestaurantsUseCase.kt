package com.example.mymiso.domain.use_cases

import com.example.mymiso.domain.repository.RestaurantRepository

class SyncRestaurantsUseCase(private val restaurantRepository: RestaurantRepository) {
    suspend operator fun invoke() {
        restaurantRepository.fetchNewRestaurants()
    }
}