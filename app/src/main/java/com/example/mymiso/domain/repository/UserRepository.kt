package com.example.mymiso.domain.repository

import com.example.mymiso.presentation.profile_screen.model.Data
import retrofit2.Response

interface UserRepository {
    suspend fun getAllUsers(): Response<Data>
}