package com.example.mymiso.presentation.model

data class Data(
    val limit: Int,
    val users: ArrayList<User>?,
    val skip: Int,
    val total: Int
)