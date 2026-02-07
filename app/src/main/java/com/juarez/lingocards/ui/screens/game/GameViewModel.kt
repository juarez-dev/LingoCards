package com.juarez.lingocards.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Reglas:
 * - 20 rondas (o menos si no hay suficientes cartas únicas en BD)
 * - Empieza con 60 puntos
 * - Cada fallo -1 punto
 * - Si acierta -> pasa a la siguiente (con feedback)
 * - Si falla 3 veces -> pasa a la siguiente
 * - No se repiten cartas en una misma partida
 * - Racha máxima: cuenta solo aciertos "a la primera" (sin fallar en esa ronda)
 */
class GameViewModel(
    private val allCardsPool: List<GameCard>,
    private val totalQuestions: Int = 20,
    private val feedbackDelayMs: Long = 500L,
    private val startScore: Int = 60
) : ViewModel() {

    private val uniquePool: List<GameCard> =
        allCardsPool.distinctBy { it.cardId }

    /** Deck de la partida: cartas únicas barajadas y cortadas al nº de rondas */
    private var gameCards: List<GameCard> = buildGameCards()

    private val _uiState = MutableStateFlow(
        GameUiState(
            totalQuestions = gameCards.size, // IMPORTANT: rondas reales (sin repetir)
            xScore = startScore
        )
    )
    val uiState: StateFlow<GameUiState> = _uiState

    init {
        loadQuestion(0)
    }

    fun onAnswerSelected(answer: String) {
        val state = _uiState.value
        if (state.finished || state.locked) return

        val card = state.currentCard ?: return
        val isCorrect = (answer == card.backText)

        if (isCorrect) handleCorrect() else handleWrong()
    }

    fun restart() {
        gameCards = buildGameCards()
        _uiState.value = GameUiState(
            totalQuestions = gameCards.size,
            xScore = startScore
        )
        loadQuestion(0)
    }

    private fun buildGameCards(): List<GameCard> {
        if (uniquePool.isEmpty()) return emptyList()

        val deck = uniquePool.shuffled()
        val rounds = minOf(totalQuestions, deck.size) // sin repetir nunca
        return deck.take(rounds)
    }

    private fun loadQuestion(index: Int) {
        // usamos gameCards.size para no reventar si hay menos de 20 cartas únicas
        if (index >= gameCards.size) {
            _uiState.update { it.copy(finished = true, locked = false, answerState = AnswerState.Idle) }
            return
        }

        val card = gameCards[index]
        val options = generateOptions(card)

        _uiState.update {
            it.copy(
                questionIndex = index,
                attemptsThisQuestion = 0,
                answerState = AnswerState.Idle,
                locked = false,
                currentCard = card,
                options = options,
                finished = false
            )
        }
    }

    private fun generateOptions(card: GameCard): List<String> {
        val correct = card.backText

        val wrong = uniquePool
            .asSequence()
            .filter { it.cardId != card.cardId }
            .map { it.backText }
            .distinct()
            .filter { it != correct }
            .shuffled()
            .take(3)
            .toList()

        val options = (wrong + correct).distinct().shuffled()

        // Si la BD tiene pocas traducciones distintas, evitamos crash rellenando con correct.
        // Ideal: tener mínimo 4 cartas con backText distintos.
        return if (options.size >= 4) {
            options.take(4)
        } else {
            val filled = options.toMutableList()
            while (filled.size < 4) filled.add(correct)
            filled.shuffled()
        }
    }

    private fun handleCorrect() {
        viewModelScope.launch {
            _uiState.update { st ->
                // racha "perfecta": solo suma si no falló en esta pregunta
                val perfect = (st.attemptsThisQuestion == 0)
                val newStreak = if (perfect) st.currentStreak + 1 else 0

                st.copy(
                    currentStreak = newStreak,
                    maxStreak = maxOf(st.maxStreak, newStreak),
                    answerState = AnswerState.Correct,
                    locked = true
                )
            }

            delay(feedbackDelayMs)
            goNext()
        }
    }

    private fun handleWrong() {
        viewModelScope.launch {
            _uiState.update { st ->
                val newAttempts = st.attemptsThisQuestion + 1
                st.copy(
                    xScore = (st.xScore - 1).coerceAtLeast(0),
                    attemptsThisQuestion = newAttempts,
                    // al fallar, se rompe la racha perfecta
                    currentStreak = 0,
                    answerState = AnswerState.Wrong,
                    locked = true
                )
            }

            delay(feedbackDelayMs)

            val stateNow = _uiState.value
            if (stateNow.attemptsThisQuestion >= 3) {
                goNext()
            } else {
                _uiState.update { it.copy(answerState = AnswerState.Idle, locked = false) }
            }
        }
    }

    private fun goNext() {
        val nextIndex = _uiState.value.questionIndex + 1
        if (nextIndex >= gameCards.size) {
            _uiState.update { it.copy(finished = true, locked = false, answerState = AnswerState.Idle) }
        } else {
            loadQuestion(nextIndex)
        }
    }
}

