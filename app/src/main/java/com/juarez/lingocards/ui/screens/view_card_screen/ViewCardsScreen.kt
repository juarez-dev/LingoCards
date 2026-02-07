package com.juarez.lingocards.ui.screens.view_card_screen

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.juarez.lingocards.ui.components.CardItem
import com.juarez.lingocards.ui.screens.card_list.CardUiModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.juarez.lingocards.LingoCardsApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun ViewCardsScreen() {
    val context = LocalContext.current
    val vm: ViewCardsViewModel = viewModel(
        factory = ViewCardsViewModelFactory(context.applicationContext as android.app.Application)
    )

    val state by vm.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Todas las cartas",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        when {
            state.isLoading -> {
                Text("Cargando...", modifier = Modifier.padding(16.dp))
            }

            state.error != null -> {
                Text(
                    text = state.error!!,
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.cards, key = { it.id }) { c: CardUiModel ->
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