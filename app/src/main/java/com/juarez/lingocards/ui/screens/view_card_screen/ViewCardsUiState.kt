package com.juarez.lingocards.ui.screens.view_card_screen

import com.juarez.lingocards.ui.screens.card_list.CardUiModel

data class ViewCardsUiState(
    val isLoading: Boolean = true,
    val cards: List<CardUiModel> = emptyList(),
    val error: String? = null
)