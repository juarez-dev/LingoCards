package com.juarez.lingocards.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "translations",
    primaryKeys = ["cardId", "language"],
    foreignKeys = [
        ForeignKey(
            entity = Card::class,
            parentColumns = ["cardId"],
            childColumns = ["cardId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("cardId"),
        Index("language")
    ]
)
data class Translation(
    @ColumnInfo(name = "cardId") val cardId: Long,
    @ColumnInfo(name = "language") val language: String,
    @ColumnInfo(name = "text") val text: String
)
