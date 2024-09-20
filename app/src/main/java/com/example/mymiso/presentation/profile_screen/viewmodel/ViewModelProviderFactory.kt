package com.example.mymiso.presentation.profile_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mymiso.domain.repository.NewRepository

class ViewModelProviderFactory(private val repository: NewRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}