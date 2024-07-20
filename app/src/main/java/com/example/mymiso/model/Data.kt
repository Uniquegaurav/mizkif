package com.example.mymiso.model

data class Data(
    val limit: Int,
    val users: ArrayList<User>?,
    val skip: Int,
    val total: Int
)