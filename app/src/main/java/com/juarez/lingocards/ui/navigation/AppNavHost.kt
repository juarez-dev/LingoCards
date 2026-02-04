package com.juarez.lingocards.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.juarez.lingocards.data.database.AppDatabase
import com.juarez.lingocards.data.repository.AuthRepository
import com.juarez.lingocards.ui.screens.*
import com.juarez.lingocards.ui.screens.game.GameScreen
import com.juarez.lingocards.ui.screens.main.MainScreen
import com.juarez.lingocards.ui.screens.main.MainViewModel
import com.juarez.lingocards.ui.screens.main.MainViewModelFactory
import com.juarez.lingocards.ui.screens.register.RegisterScreen
import com.juarez.lingocards.ui.screens.register.RegisterViewModel
import com.juarez.lingocards.ui.screens.register.RegisterViewModelFactory

@Composable
fun AppNavHost(
    navController: NavHostController,
    onExit: () -> Unit,
    db: AppDatabase
) {
    var loggedUserId by remember { mutableStateOf<Long?>(null) }
    var isGuest by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = Routes.Main
    ) {

        composable(Routes.Main) {
            val repo = remember { AuthRepository(db.userDao()) }
            val factory = remember { MainViewModelFactory(repo) }
            val vm: MainViewModel = viewModel(factory = factory)

            MainScreen(
                viewModel = vm,
                onNewUser = { navController.navigate(Routes.Register) },
                onGuestLogin = {
                    isGuest = true
                    loggedUserId = null
                    navController.navigate(Routes.GameSelection) },
                onLoginSuccess = { userId ->
                    isGuest = false
                    loggedUserId = userId
                    navController.navigate(Routes.GameSelection)
                }
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
                onStartGame = { navController.navigate(Routes.Game) },
                onViewScores = { navController.navigate(Routes.Score) },
                onViewCards = { navController.navigate(Routes.Cards) },
                onExit = onExit,
                userId = loggedUserId,
                isGuest = isGuest
            )
        }

        composable(Routes.Score) {
            ScoreScreen()
        }

        composable(Routes.Cards) {
            ViewCardsScreen()
        }

        composable(Routes.Game) {
            GameScreen(
                userId = loggedUserId,
                isGuest = isGuest
            )
        }
    }
}

object Routes {
    const val Main = "main"
    const val Register = "register"
    const val GameSelection = "game_selection"
    const val Score = "score"
    const val Cards = "cards"
    const val Game = "game"
}
