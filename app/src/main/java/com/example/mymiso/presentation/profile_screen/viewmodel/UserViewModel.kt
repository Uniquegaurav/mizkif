package com.example.mymiso.presentation.profile_screen.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymiso.presentation.profile_screen.model.Data
import com.example.mymiso.domain.repository.UserRepository
import com.example.mymiso.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val users: MutableLiveData<Resource<Data>> = MutableLiveData()

    init {
        getAllUser()
    }

    private fun getAllUser() = viewModelScope.launch {
        users.postValue(Resource.Loading())
        val response = repository.getAllUsers()
        users.postValue(handleResponse(response))
    }

    private fun handleResponse(response: Response<Data>): Resource<Data> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(response.body())
            }
        }
        return Resource.Error(response.body(), response.message())
    }

}