package com.juarez.lingocards.data.database.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface CardDao {

    data class CardRow(
        val id: Long,
        val frontText: String,
        val backText: String,
        val imageResId: String
    )

    @Query("""
        SELECT 
            c.cardId AS id,
            tEs.text AS frontText,
            tDe.text AS backText,
            c.imageRes AS imageResId
        FROM cards c
        INNER JOIN translations tEs
            ON tEs.cardId = c.cardId AND tEs.language = 'es'
        INNER JOIN translations tDe
            ON tDe.cardId = c.cardId AND tDe.language = 'de'
        ORDER BY c.cardId ASC
    """)
    suspend fun getAllCardsEsDe(): List<CardRow>
}