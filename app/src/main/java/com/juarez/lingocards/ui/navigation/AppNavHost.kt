package com.juarez.lingocards.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juarez.lingocards.R
import com.juarez.lingocards.ui.screens.*
import com.juarez.lingocards.ui.screens.card_list.CardUiModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    onExit: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Main
    ) {

        composable(Routes.Main) {
            MainScreen(
                onNewUser = { navController.navigate(Routes.Register) },
                onGuestLogin = { navController.navigate(Routes.GameSelection) }
            )
        }

        composable(Routes.Register) {
            RegisterScreen(
                onRegistered = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }

        composable(Routes.GameSelection) {
            GameSelectionScreen(
                onStartGame = { /* TODO */ },
                onViewScores = { navController.navigate(Routes.Score) },
                onViewCards = { navController.navigate(Routes.Cards) },
                onExit = onExit
            )
        }

        composable(Routes.Score) {
            ScoreScreen()
        }

        composable(Routes.Cards) {
            ViewCardsScreen()
        }
    }
}

object Routes {
    const val Main = "main"
    const val Register = "register"
    const val GameSelection = "game_selection"
    const val Score = "score"
    const val Cards = "cards"
}