package com.example.mymiso.di

import com.example.mymiso.data.network.UserAPI
import com.example.mymiso.data.repository.UserRepositoryImpl
import com.example.mymiso.domain.repository.UserRepository
import com.example.mymiso.domain.use_cases.GetAllUsersUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Singleton
    @Provides
    fun provideUserAPIClient(): UserAPI {
        return Retrofit.Builder()
            .baseUrl("https://dummyjson.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userAPI: UserAPI): UserRepository {
        return UserRepositoryImpl(userAPI)
    }

    @Singleton
    @Provides
    fun provideGetUserUseCase(userRepository: UserRepository): GetAllUsersUseCase {
        return GetAllUsersUseCase(userRepository)
    }
}