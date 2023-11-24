package com.vinz.domain.repository

import com.vinz.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun insertUser(user: User)

    fun updateUser(user: User)

    fun deleteUser(user: User)

    fun getUserById(userId: Int): Flow<User>

    fun getUserByEmailAndPassword(email: String, password: String): Flow<User?>

    fun getUserByEmail(email: String): Flow<User?>
}