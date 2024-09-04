package com.example.mymiso.presentation.profile_screen.model

data class Data(
    val limit: Int,
    val users: ArrayList<User>?,
    val skip: Int,
    val total: Int
)