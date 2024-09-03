package com.example.mymiso.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitUserApiClient {
    companion object{
        val userClient : UserAPI by lazy {
            Retrofit.Builder()
                .baseUrl("https://dummyjson.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserAPI::class.java)
        }
    }
}