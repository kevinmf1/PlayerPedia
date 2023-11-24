package com.vinz.playerpedia.activity.register

import androidx.lifecycle.ViewModel
import com.vinz.domain.model.User
import com.vinz.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    fun insertUser(user: User) = userUseCase.insertUser(user)

    fun getUserByEmailAndPassword(email: String, password: String): Flow<User?> {
        return userUseCase.getUserByEmailAndPassword(email, password)
    }

    fun getUserByEmail(email: String): Flow<User?> {
        return userUseCase.getUserByEmail(email)
    }

}