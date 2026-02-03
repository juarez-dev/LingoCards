package com.juarez.lingocards.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juarez.lingocards.data.database.entities.Card
import com.juarez.lingocards.data.database.entities.CardTheme
import com.juarez.lingocards.data.database.entities.Translation
import com.juarez.lingocards.data.database.dao.CardDao
import com.juarez.lingocards.data.database.dao.ScoreDao
import com.juarez.lingocards.data.database.dao.UserDao
import com.juarez.lingocards.data.database.entities.Score
import com.juarez.lingocards.data.database.entities.User

@Database(
    entities = [
        CardTheme::class,
        Card::class,
        Translation::class,
        User::class,
        Score::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    // Declaration of DAOs
    abstract fun cardDao(): CardDao
    abstract fun ScoreDao(): ScoreDao
    abstract fun userDao(): UserDao
}
