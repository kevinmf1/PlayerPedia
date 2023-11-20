package com.vinz.data.domain.usecase

import androidx.lifecycle.LiveData
import com.vinz.data.domain.model.User

interface UserUseCase {

    fun insertUser(user: User)

    fun updateUser(user: User)

    fun deleteUser(user: User)

    fun getUserById(userId: Int): LiveData<User>

    fun getUserByEmailAndPassword(email: String, password: String): LiveData<User?>

    fun getUserByEmail(email: String): LiveData<User?>
}