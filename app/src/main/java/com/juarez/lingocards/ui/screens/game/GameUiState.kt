package com.juarez.lingocards.ui.screens.game


data class GameUiState (
    // There are 20 questions in total
    val questionIndex: Int = 0,
    val totalQuestions: Int = 20,

    // The maximal score is 80 points, 4 for each correct answer without mistakes
    val xScore: Int = 80,

    // Number of attempts/mistakes for the current question
    val attemptsThisQuestion: Int = 0,

    // Streaks
    val currentStreak: Int = 0,
    val maxStreak: Int = 0,

    // Current question data
    val currentCard: GameCard? = null,
    val locked: Boolean = false, // true while showing feedback (correct/wrong) to prevent multiple taps
    val options: List<String> = emptyList(),

    // Answer state
    val answerState: AnswerState = AnswerState.Idle,
    val finished: Boolean = false
){
    val finalScore: Int
        get() = xScore + maxStreak
}