package com.juarez.lingocards.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_themes")
data class CardTheme(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cardThemeId") val cardThemeId: Long = 0,
    @ColumnInfo(name = "name") val name: String
)
