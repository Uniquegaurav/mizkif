package com.example.mymiso.util

sealed class Resource<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {

    class Loading<T>() : Resource<T>()
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(data: T?, errorMessage: String?) : Resource<T>(data, errorMessage)

}