package com.juarez.lingocards.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "scores",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userOwnerId")]
)
data class Score(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "scoreId") val scoreId: Long = 0,
    @ColumnInfo(name = "userOwnerId") val userOwnerId: Long,
    @ColumnInfo(name = "score") val score: Int,
    // when the score was obtained, stored as a timestamp
    @ColumnInfo(name = "createdAt") val createdAt: Long = System.currentTimeMillis()
)
