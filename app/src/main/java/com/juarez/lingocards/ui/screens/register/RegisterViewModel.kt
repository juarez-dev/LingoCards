package com.juarez.lingocards.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juarez.lingocards.data.repository.AuthRepository
import com.juarez.lingocards.data.repository.RegisterResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repo: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun register(username: String, email: String, password: String, confirmPassword: String) {
        // Reset errors
        _uiState.update {
            it.copy(
                isLoading = false,
                usernameError = null,
                emailError = null,
                passwordError = null,
                confirmError = null,
                generalError = null,
                success = false
            )
        }

        // Basic validations (UI-level)
        var valid = true
        if (username.isBlank()) {
            _uiState.update { it.copy(usernameError = "Introduce un usuario") }
            valid = false
        }
        if (password.isBlank()) {
            _uiState.update { it.copy(passwordError = "Introduce una contraseña") }
            valid = false
        }
        if (confirmPassword.isBlank()) {
            _uiState.update { it.copy(confirmError = "Repite la contraseña") }
            valid = false
        }
        if (password.isNotBlank() && confirmPassword.isNotBlank() && password != confirmPassword) {
            _uiState.update {
                it.copy(
                    passwordError = "Las contraseñas no coinciden",
                    confirmError = "Las contraseñas no coinciden"
                )
            }
            valid = false
        }
        if (email.isBlank()) {
            _uiState.update { it.copy(emailError = "Introduce un email") }
            valid = false
        } else if (!email.contains("@") || !email.contains(".")) {
            _uiState.update { it.copy(emailError = "Email inválido") }
            valid = false
        }

        if (!valid) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                when (repo.register(username, email, password)) {
                    RegisterResult.Success ->
                        _uiState.update { it.copy(isLoading = false, success = true) }

                    RegisterResult.UsernameExists ->
                        _uiState.update { it.copy(isLoading = false, usernameError = "Ese usuario ya existe") }

                    RegisterResult.EmailExists ->
                        _uiState.update { it.copy(isLoading = false, emailError = "Ese email ya está registrado") }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, generalError = "Error registrando usuario") }
            }
        }
    }

    fun consumeSuccess() {
        _uiState.update { it.copy(success = false) }
    }
}
