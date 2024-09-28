package com.example.mymiso.data.repository
import com.example.mymiso.data.network.UserAPI
import com.example.mymiso.domain.repository.UserRepository

class UserRepositoryImpl(private val userClient: UserAPI) : UserRepository {
    override suspend fun getAllUsers() = userClient.getAllUsers();
}