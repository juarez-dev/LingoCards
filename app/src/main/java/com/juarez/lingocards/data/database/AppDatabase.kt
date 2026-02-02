package com.juarez.lingocards.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juarez.lingocards.data.database.entities.Card
import com.juarez.lingocards.data.database.entities.CardTheme
import com.juarez.lingocards.data.database.entities.Translation
import com.juarez.lingocards.data.database.dao.CardDao

@Database(
    entities = [
        CardTheme::class,
        Card::class,
        Translation::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    // Declaration of DAOs
    abstract fun cardDao(): CardDao
}
