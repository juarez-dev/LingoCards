package com.juarez.lingocards.ui.screens.main

import androidx.lifecycle.ViewModel
import com.juarez.lingocards.data.repository.AuthRepository
import androidx.lifecycle.viewModelScope
import com.juarez.lingocards.data.repository.LoginResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel(
    private val repo: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    fun login(username: String, password: String) {
        _uiState.update {
            it.copy(
                isLoading = false,
                usernameError = null,
                passwordError = null,
                generalError = null,
                loggedInUserId = null
            )
        }

        var valid = true
        if (username.isBlank()) {
            _uiState.update { it.copy(usernameError = "Introduce un usuario") }
            valid = false
        }
        if (password.isBlank()) {
            _uiState.update { it.copy(passwordError = "Introduce una contraseña") }
            valid = false
        }
        if (!valid) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                when (val res = repo.login(username, password)) {
                    is LoginResult.Success ->
                        _uiState.update { it.copy(isLoading = false, loggedInUserId = res.userId) }

                    LoginResult.UserNotFound ->
                        _uiState.update { it.copy(isLoading = false, usernameError = "Usuario no existe") }

                    LoginResult.WrongPassword ->
                        _uiState.update { it.copy(isLoading = false, passwordError = "Contraseña incorrecta") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, generalError = "Error iniciando sesión") }
            }
        }
    }

    fun consumeLogin() {
        _uiState.update { it.copy(loggedInUserId = null) }
    }
}