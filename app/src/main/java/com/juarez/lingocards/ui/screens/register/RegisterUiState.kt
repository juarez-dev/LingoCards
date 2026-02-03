package com.juarez.lingocards.ui.screens.register

data class RegisterUiState(
    val isLoading: Boolean = false,
    val usernameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmError: String? = null,
    val generalError: String? = null,
    val success: Boolean = false
)