package com.example.mymiso.data.network

import com.example.mymiso.domain.model.Location
import com.example.mymiso.domain.model.Restaurant
import com.example.mymiso.domain.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantAPI {

    @GET("restaurants")
    suspend fun fetchRestaurants(): List<SearchResult>

    @GET("restaurants/{location}")
    suspend fun fetchRestaurants(location: Location): List<SearchResult>

    @GET("restaurants/{query}")
    suspend fun fetchRestaurants(query: String): List<SearchResult>

    @GET("restaurants/{location}/{query}")
    suspend fun fetchRestaurants(location: Location, query: String): List<SearchResult>

    @GET("new-restaurants")
    suspend fun fetchNewRestaurants(): List<Restaurant>

}