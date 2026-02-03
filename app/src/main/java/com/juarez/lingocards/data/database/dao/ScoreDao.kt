package com.juarez.lingocards.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.juarez.lingocards.data.database.entities.Score
import com.juarez.lingocards.ui.screens.RankingRow

@Dao
interface ScoreDao {

    @Insert
    suspend fun insert(score: Score): Long

    @Query("""
        SELECT * FROM scores
        WHERE userOwnerId = :userId
        ORDER BY createdAt DESC
    """)
    suspend fun getHistoryForUser(userId: Long): List<Score>

    @Query("""
        SELECT MAX(score) FROM scores
        WHERE userOwnerId = :userId
    """)
    suspend fun getBestForUser(userId: Long): Int?

    @Query("""
    SELECT u.username AS username, s.score AS score, s.createdAt AS createdAt
    FROM scores s
    INNER JOIN users u ON u.userId = s.userOwnerId
    ORDER BY s.score DESC, s.createdAt ASC
    LIMIT :limit
""")
    suspend fun getTopGlobal(limit: Int = 10): List<RankingRow>
}