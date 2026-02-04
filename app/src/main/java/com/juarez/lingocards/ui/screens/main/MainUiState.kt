package com.juarez.lingocards.ui.screens.main

data class MainUiState(
    val isLoading: Boolean = false,
    val usernameError: String? = null,
    val passwordError: String? = null,
    val generalError: String? = null,
    val loggedInUserId: Long? = null
)
