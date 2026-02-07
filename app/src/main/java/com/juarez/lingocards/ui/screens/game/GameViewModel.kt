package com.juarez.lingocards.ui.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val allCardsPool: List<GameCard>,
    private val totalQuestions: Int = 20,
    private val feedbackDelayMs: Long = 500L
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        GameUiState(
            totalQuestions = totalQuestions,
            xScore = totalQuestions * 4
        )
    )
    val uiState: StateFlow<GameUiState> = _uiState

    private val gameCards: List<GameCard> = buildGameCards()

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
        _uiState.value = GameUiState(
            totalQuestions = totalQuestions,
            xScore = totalQuestions * 4
        )
        loadQuestion(0)
    }

    private fun buildGameCards(): List<GameCard> {
        if (allCardsPool.isEmpty()) return emptyList()

        return if (allCardsPool.size >= totalQuestions) {
            allCardsPool.shuffled().take(totalQuestions)
        } else {
            List(totalQuestions) { allCardsPool.random() }
        }
    }

    private fun loadQuestion(index: Int) {
        if (index >= totalQuestions) {
            _uiState.update { it.copy(finished = true) }
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

        val wrong = allCardsPool
            .asSequence()
            .filter { it.cardId != card.cardId }
            .map { it.backText }
            .distinct()
            .filter { it != correct }
            .shuffled()
            .take(3)
            .toList()

        val options = (wrong + correct).shuffled()
        return if (options.size >= 4) options.take(4) else options
    }

    private fun handleCorrect() {
        viewModelScope.launch {
            _uiState.update {
                val newStreak = it.currentStreak + 1
                it.copy(
                    currentStreak = newStreak,
                    maxStreak = maxOf(it.maxStreak, newStreak),
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
            _uiState.update {
                val newAttempts = it.attemptsThisQuestion + 1
                it.copy(
                    xScore = it.xScore - 1,
                    attemptsThisQuestion = newAttempts,
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
        if (nextIndex >= totalQuestions) {
            _uiState.update { it.copy(finished = true, locked = false, answerState = AnswerState.Idle) }
        } else {
            loadQuestion(nextIndex)
        }
    }
}
