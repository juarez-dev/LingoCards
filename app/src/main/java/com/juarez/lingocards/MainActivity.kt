package com.juarez.lingocards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.juarez.lingocards.data.database.AppDatabase
import com.juarez.lingocards.ui.navigation.AppNavHost
import com.juarez.lingocards.ui.theme.LingoCardsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        val db = (application as LingoCardsApp).db

        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContent {
            LingoCardsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        onExit = { finish() },
                        db = db
                    )
                }
            }
        }
    }
}

