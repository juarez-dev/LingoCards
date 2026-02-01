package com.juarez.lingocards.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juarez.lingocards.ui.components.AppButton
import com.juarez.lingocards.ui.components.UserCredentials

// Main screen composable function
@Composable
fun MainScreen(onNewUser: () -> Unit, onGuestLogin: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // User credentials input fields
        UserCredentials()
        // Login button
        AppButton("Iniciar sesión") {/*login logic*/ }
        // Guest login button
        AppButton("Ingresar como invitado") { onGuestLogin() }
        // New user button
        AppButton("Nuevo usuario") { onNewUser() }
    }
}

