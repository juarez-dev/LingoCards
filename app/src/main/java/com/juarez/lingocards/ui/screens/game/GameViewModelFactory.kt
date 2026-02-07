package com.juarez.lingocards.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juarez.lingocards.data.database.dao.CardDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class GameViewModelFactory(
    private val cardDao: CardDao,
    private val totalQuestions: Int = 20,
    private val feedbackDelayMs: Long = 500L,
    private val startScore: Int = 60
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(GameViewModel::class.java)) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        // 🔹 Cargar cartas desde Room y mapear a GameCard
        val cards: List<GameCard> = runBlocking(Dispatchers.IO) {
            cardDao.getAllCardsEsDe().map { row ->
                GameCard(
                    cardId = row.id,
                    frontText = row.frontText,     // Español
                    backText = row.backText,       // Alemán
                    imageResName = row.imageResId  // 👈 ESTE ERA EL PROBLEMA
                )
            }
        }

        @Suppress("UNCHECKED_CAST")
        return GameViewModel(
            allCardsPool = cards,
            totalQuestions = totalQuestions,
            feedbackDelayMs = feedbackDelayMs,
            startScore = startScore
        ) as T
    }
}