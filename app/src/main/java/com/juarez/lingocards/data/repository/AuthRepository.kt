package com.juarez.lingocards.data.repository

import com.juarez.lingocards.data.database.dao.UserDao
import com.juarez.lingocards.data.database.entities.User
import com.juarez.lingocards.data.security.hashPassword

class AuthRepository(
    private val userDao: UserDao
) {
    suspend fun register(username: String, email: String, password: String): RegisterResult {
        val cleanUsername = username.trim()
        val cleanEmail = email.trim()

        if (userDao.getByUsername(cleanUsername) != null) return RegisterResult.UsernameExists
        if (userDao.getByEmail(cleanEmail) != null) return RegisterResult.EmailExists

        val hashed = hashPassword(password)

        userDao.insert(
            User(
                username = cleanUsername,
                email = cleanEmail,
                passwordHash = hashed
            )
        )

        return RegisterResult.Success
    }

    suspend fun login(username: String, password: String): LoginResult {
        val cleanUsername = username.trim()
        val user = userDao.getByUsername(cleanUsername) ?: return LoginResult.UserNotFound

        val inputHash = hashPassword(password)
        return if (inputHash == user.passwordHash) {
            LoginResult.Success(user.userId)
        } else {
            LoginResult.WrongPassword
        }
    }
}

sealed class LoginResult {
    data class Success(val userId: Long) : LoginResult()
    data object UserNotFound : LoginResult()
    data object WrongPassword : LoginResult()
}