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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.juarez.lingocards.ui.components.AppButton
import com.juarez.lingocards.ui.components.CardItem

@Composable
fun GameScreen(
    viewModel: GameViewModel,
    isGuest: Boolean,
    onExit: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "Puntuación: ${state.xScore}  •  Racha: ${state.currentStreak} (max ${state.maxStreak})",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Pregunta ${state.questionIndex + 1}/${state.totalQuestions}",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(4.dp))

            // If finished -> show end screen
            if (state.finished) {
                Text(
                    text = "¡Partida terminada!",
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = "Puntuación final: ${state.finalScore}",
                    style = MaterialTheme.typography.headlineMedium
                )

                if (isGuest) {
                    Text(
                        text = "Modo invitado: no se guardan puntuaciones",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(Modifier.height(12.dp))
                AppButton("Salir") { onExit() }
                return@Column
            }

            val card = state.currentCard
            if (card != null) {
                val context = LocalContext.current

                val imageResId: Int? = remember(card.imageResName) {
                    val name = card.imageResName?.trim().orEmpty()
                    if (name.isBlank()) null
                    else {
                        val id = context.resources.getIdentifier(name, "drawable", context.packageName)
                        if (id == 0) null else id
                    }
                }

                CardItem(
                    frontText = card.frontText,
                    backText = card.backText,
                    imageResId = imageResId,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                Text("Cargando carta...")
            }

            Spacer(Modifier.height(8.dp))

            // Options
            state.options.forEach { option ->
                AppButton(
                    text = option,
                    enabled = !state.locked
                ) {
                    viewModel.onAnswerSelected(option)
                }
            }

            Spacer(Modifier.height(8.dp))
            AppButton("Salir") { onExit() }
        }
    }
}
