package com.example.mymiso.repository

import com.example.mymiso.db.UserDatabase
import com.example.mymiso.network.RetrofitUserApiClient

class NewRepository(val db : UserDatabase) {
    suspend fun getAllUsers() = RetrofitUserApiClient.userClient.getAllUsers();
}