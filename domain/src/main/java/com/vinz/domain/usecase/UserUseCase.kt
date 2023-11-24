package com.vinz.domain.usecase

import com.vinz.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {

    fun insertUser(user: User)

    fun updateUser(user: User)

    fun deleteUser(user: User)

    fun getUserById(userId: Int): Flow<User>

    fun getUserByEmailAndPassword(email: String, password: String): Flow<User?>

    fun getUserByEmail(email: String): Flow<User?>
}