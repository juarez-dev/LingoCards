package com.juarez.lingocards.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.juarez.lingocards.data.database.entities.Score
import com.juarez.lingocards.data.dto.RankingRow

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
    SELECT 
        u.username AS username,
        MAX(s.score) AS bestScore,
        MIN(s.createdAt) AS bestAt
    FROM scores s
    INNER JOIN users u ON u.userId = s.userOwnerId
    GROUP BY s.userOwnerId
    ORDER BY bestScore DESC, bestAt ASC
    LIMIT :limit
""")
    suspend fun getTopPlayers(limit: Int = 20): List<RankingRow>
}