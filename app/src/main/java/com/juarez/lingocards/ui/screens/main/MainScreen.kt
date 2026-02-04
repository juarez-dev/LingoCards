package com.juarez.lingocards.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.juarez.lingocards.ui.components.AppButton
import com.juarez.lingocards.ui.components.UserCredentials

// Main screen composable function
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onNewUser: () -> Unit,
    onGuestLogin: () -> Unit,
    onLoginSuccess: (userId: Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(uiState.loggedInUserId) {
        val id = uiState.loggedInUserId
        if (id != null) {
            viewModel.consumeLogin()
            onLoginSuccess(id)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User credentials input fields
        UserCredentials(
            username = username,
            password = password,
            onUsernameChange = { username = it },
            onPasswordChange = { password = it }
        )

        if (uiState.usernameError != null) {
            Text(uiState.usernameError!!, color = Color.Red)
        }
        if (uiState.passwordError != null) {
            Text(uiState.passwordError!!, color = Color.Red)
        }
        if (uiState.generalError != null) {
            Text(uiState.generalError!!, color = Color.Red)
        }

        // Login button
        AppButton(if (uiState.isLoading) "Iniciando..." else "Iniciar sesión") {
            viewModel.login(username, password)
        }

        // Guest login button
        AppButton("Ingresar como invitado") { onGuestLogin() }

        // New user button
        AppButton("Nuevo usuario") { onNewUser() }
    }
}

