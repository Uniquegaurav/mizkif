package com.example.mymiso.domain.repository

import com.example.mymiso.domain.model.Location
import com.example.mymiso.domain.model.Restaurant
import com.example.mymiso.domain.model.SearchResult

interface RestaurantRepository {
    suspend fun searchRestaurantByLocation(location: Location): List<SearchResult>
    suspend fun searchRestaurantByQuery(query: String): List<SearchResult>
    suspend fun searchRestaurantByLocationAndQuery(
        location: Location,
        query: String
    ): List<SearchResult>

    suspend fun fetchNewRestaurants(): List<Restaurant>
}