package com.juarez.lingocards.ui.screens.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juarez.lingocards.data.repository.AuthRepository

class RegisterViewModelFactory(
    private val repo: AuthRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return RegisterViewModel(repo) as T
    }
}
