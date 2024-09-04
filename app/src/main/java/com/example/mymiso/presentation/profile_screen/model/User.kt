package com.example.mymiso.presentation.profile_screen.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id") val id: Int?= null,
    @SerializedName("firstName") val firstName: String,
    @SerializedName("lastName") val lastName: String,
    @SerializedName("image") val image: String,
    @SerializedName("email") val email: String
) : Serializable