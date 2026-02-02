package com.juarez.lingocards

import android.app.Application
import androidx.room.Room
import com.juarez.lingocards.data.database.AppDatabase

class LingoCardsApp : Application() {
    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "lingocards-db")
            .build()
    }
}