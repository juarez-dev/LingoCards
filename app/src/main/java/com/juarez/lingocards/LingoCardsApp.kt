package com.juarez.lingocards

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.juarez.lingocards.data.database.AppDatabase
import kotlinx.coroutines.launch

class LingoCardsApp : Application() {

    val db: AppDatabase by lazy {
        Log.d("APP_DB", "Inicializando Room (vacía)...")

        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "lingocards-db")
            .createFromAsset("lingocards.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        Log.d("APP", "LingoCardsApp creada ✅")

        // Forzar creación en background (crea archivo + tablas)
        kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO).launch {
            db.openHelper.writableDatabase
            val f = applicationContext.getDatabasePath("lingocards-db")
            Log.d("APP_DB", "Room abierta ✅ exists=${f.exists()} path=${f.absolutePath}")
        }
    }
}