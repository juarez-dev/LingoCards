package com.juarez.lingocards.ui.screens.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juarez.lingocards.R
import com.juarez.lingocards.ui.components.AppButton
import com.juarez.lingocards.ui.components.CardItem // si lo tienes ya

@Composable
fun GameScreen(
    userId: Long? = null,
    isGuest: Boolean = false,
    onExit: () -> Unit = {}
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isGuest) "Modo invitado" else "Jugador: $userId",
                style = MaterialTheme.typography.titleMedium
            )

            // --- Card area (placeholder) ---
            // Si tu CardItem requiere imageResId, pon uno dummy o crea una versión sin imagen.
            CardItem(
                frontText = "Perro",
                backText = "Hund",
                imageResId = R.drawable.card_dog, // placeholder (luego lo conectamos bien)
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // --- Options (dummy) ---
            AppButton("Hund") { /* TODO */ }
            AppButton("Katze") { /* TODO */ }
            AppButton("Haus") { /* TODO */ }
            AppButton("Wasser") { /* TODO */ }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Puntuación: 0   •   Pregunta 1/10",
                style = MaterialTheme.typography.bodyMedium
            )

            // Exit (optional)
            AppButton("Salir") { onExit() }
        }
    }
}
