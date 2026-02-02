package com.juarez.lingocards.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.juarez.lingocards.ui.components.CardItem
import com.juarez.lingocards.ui.screens.card_list.CardUiModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.juarez.lingocards.LingoCardsApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun ViewCardsScreen() {
    val context = LocalContext.current

    var cards by remember { mutableStateOf<List<CardUiModel>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val app = context.applicationContext as LingoCardsApp
            val dao = app.db.cardDao()

            val rows = withContext(Dispatchers.IO) {
                dao.getAllCardsEsDe()
            }

            cards = rows.map { r ->
                val resId = context.resources.getIdentifier(
                    r.imageResId,       // nombre en drawable, ej: "cat_01"
                    "drawable",
                    context.packageName
                )

                CardUiModel(
                    id = r.id,
                    frontText = r.frontText,
                    backText = r.backText,
                    imageResId = resId // puede ser 0 si no existe
                )
            }

            isLoading = false
        } catch (t: Throwable) {
            isLoading = false
            error = t.message ?: "Error cargando cartas"
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Todas las cartas",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        when {
            isLoading -> {
                Text(
                    text = "Cargando...",
                    modifier = Modifier.padding(16.dp)
                )
            }

            error != null -> {
                Text(
                    text = "❌ $error",
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cards, key = { it.id }) { c ->
                        CardItem(
                            frontText = c.frontText,
                            backText = c.backText,
                            imageResId = c.imageResId
                        )
                    }
                }
            }
        }
    }
}
