package com.example.mymiso.network
import com.example.mymiso.model.Data
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {
    @GET("user")
    suspend fun getAllUsers(): Response<Data>
}