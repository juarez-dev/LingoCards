package com.juarez.lingocards.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juarez.lingocards.data.database.entities.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE userId = :id LIMIT 1")
    suspend fun getById(id: Long): User?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getByUsername(username: String): User?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): User?

    @Query("DELETE FROM users WHERE userId = :id")
    suspend fun deleteById(id: Long)
}