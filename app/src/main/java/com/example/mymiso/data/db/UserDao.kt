package com.example.mymiso.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mymiso.presentation.profile_screen.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(user: User): Long

    @Query("SELECT* FROM users")
    fun getAllUsers(): LiveData<List<User>>

    @Delete
    suspend fun delete(user: User)
}