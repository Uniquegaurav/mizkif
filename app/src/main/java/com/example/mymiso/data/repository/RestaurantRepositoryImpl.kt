package com.example.mymiso.data.repository

import com.example.mymiso.data.network.RestaurantAPI
import com.example.mymiso.domain.model.Location
import com.example.mymiso.domain.model.Restaurant
import com.example.mymiso.domain.model.SearchResult
import com.example.mymiso.domain.repository.RestaurantRepository

class RestaurantRepositoryImpl(private val apiService: RestaurantAPI) : RestaurantRepository {
    override suspend fun searchRestaurantByLocation(location: Location): List<SearchResult> {
        return apiService.fetchRestaurants(location)
    }

    override suspend fun searchRestaurantByQuery(query: String): List<SearchResult> {
        return apiService.fetchRestaurants(query)
    }

    override suspend fun searchRestaurantByLocationAndQuery(
        location: Location,
        query: String
    ): List<SearchResult> {
        return apiService.fetchRestaurants(location, query)
    }

    override suspend fun fetchNewRestaurants(): List<Restaurant> {
        return apiService.fetchNewRestaurants()
    }
}