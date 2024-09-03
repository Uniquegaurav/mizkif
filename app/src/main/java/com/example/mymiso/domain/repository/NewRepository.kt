package com.example.mymiso.domain.repository

import com.example.mymiso.data.db.UserDatabase
import com.example.mymiso.data.network.RetrofitUserApiClient

class NewRepository(val db : UserDatabase) {
    suspend fun getAllUsers() = RetrofitUserApiClient.userClient.getAllUsers();
}