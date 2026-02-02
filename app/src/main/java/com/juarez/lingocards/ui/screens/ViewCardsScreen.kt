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


@Composable
fun ViewCardsScreen(
) {
    val context = LocalContext.current

    // Estado con las cartas
    var cards by remember { mutableStateOf<List<CardUiModel>>(emptyList()) }



    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Todas las cartas",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cards) { c ->
                CardItem(
                    frontText = c.frontText,
                    backText = c.backText,
                    imageResId = c.imageResId
                )
            }
        }
    }
}