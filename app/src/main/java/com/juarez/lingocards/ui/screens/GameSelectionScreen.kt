package com.juarez.lingocards.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameSelectionScreen(
    onStartGame: () -> Unit,
    onViewCards: () -> Unit,
    onViewScores: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
    userId: Long? = null,
    isGuest: Boolean = false
) {
    Scaffold {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SELECCIÓN DE JUEGO",
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Start Game Button
            Button(
                onClick = onStartGame,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Empezar Juego")
            }

            Spacer(modifier = Modifier.height(12.dp))

            // View Cards Button
            Button(
                onClick = onViewCards,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Ver Cartas")
            }

            // View Scores Button
            Button(
                onClick = onViewScores,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Ver Puntuaciones")
            }

            Spacer(modifier = Modifier.height(12.dp))


            // Exit Button
            OutlinedButton(
                onClick = onExit,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text("Sair")
            }
        }
    }
}
