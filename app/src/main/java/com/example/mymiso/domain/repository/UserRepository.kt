package com.example.mymiso.domain.repository
import com.example.mymiso.data.network.UserAPI

class UserRepository (private val userClient : UserAPI) {
    suspend fun getAllUsers() = userClient.getAllUsers();
}