package com.juarez.lingocards.ui.screens.game

data class GameCard(
    val cardId: Long,
    val frontText: String,      // Español (o lo que uses como “pregunta”)
    val backText: String,       // Alemán (respuesta correcta)
    val imageResName: String?   // ej: "card_dog" (puede ser null)
)