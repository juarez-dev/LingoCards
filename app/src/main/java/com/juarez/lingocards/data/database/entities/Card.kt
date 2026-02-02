package com.juarez.lingocards.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cards",
    foreignKeys = [
        ForeignKey(
            entity = CardTheme::class,
            parentColumns = ["cardThemeId"],
            childColumns = ["cardThemeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("cardThemeId")]
)
data class Card(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cardId") val cardId: Long = 0,
    @ColumnInfo(name = "cardThemeId") val cardThemeId: Long,
    @ColumnInfo(name = "imageRes") val imageRes: String
)
