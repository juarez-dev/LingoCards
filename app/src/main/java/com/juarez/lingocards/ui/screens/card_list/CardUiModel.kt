package com.juarez.lingocards.ui.screens.card_list

// Data model for a card in the UI
data class CardUiModel(
    val id: Long,
    val frontText: String,
    val backText: String,
    val imageResId: Int
)
