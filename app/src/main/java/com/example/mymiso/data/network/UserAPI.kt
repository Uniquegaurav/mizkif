package com.example.mymiso.data.network
import com.example.mymiso.presentation.profile_screen.model.Data
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {
    @GET("user")
    suspend fun getAllUsers(): Response<Data>
}