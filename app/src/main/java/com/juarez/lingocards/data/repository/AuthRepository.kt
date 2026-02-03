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
}