package com.juarez.lingocards.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juarez.lingocards.data.database.AppDatabase
import com.juarez.lingocards.data.repository.AuthRepository
import com.juarez.lingocards.ui.screens.*
import com.juarez.lingocards.ui.screens.register.RegisterScreen
import com.juarez.lingocards.ui.screens.register.RegisterViewModel
import com.juarez.lingocards.ui.screens.register.RegisterViewModelFactory

@Composable
fun AppNavHost(
    navController: NavHostController,
    onExit: () -> Unit,
    db: AppDatabase
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
            // English note: build VM with manual DI (no Hilt)
            val repo = remember { AuthRepository(db.userDao()) }
            val factory = remember { RegisterViewModelFactory(repo) }
            val vm: RegisterViewModel = viewModel(factory = factory)

            RegisterScreen(
                viewModel = vm,
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
