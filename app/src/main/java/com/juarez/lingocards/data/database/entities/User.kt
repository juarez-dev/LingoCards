package com.juarez.lingocards.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId") val userId: Long = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "passwordHash") val passwordHash: String,
    @ColumnInfo(name = "email") val email: String
)
