package com.juarez.lingocards.data.repository

sealed class RegisterResult {
    data object Success : RegisterResult()
    data object UsernameExists : RegisterResult()
    data object EmailExists : RegisterResult()
}