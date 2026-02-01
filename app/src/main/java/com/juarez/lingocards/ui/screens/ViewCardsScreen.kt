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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juarez.lingocards.ui.components.CardItem
import com.juarez.lingocards.ui.screens.card_list.CardUiModel


@Composable
fun ViewCardsScreen(
    cards: List<CardUiModel>
) {
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