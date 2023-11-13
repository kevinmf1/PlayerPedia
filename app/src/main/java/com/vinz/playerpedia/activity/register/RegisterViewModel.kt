package com.vinz.playerpedia.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vinz.core.domain.model.User
import com.vinz.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    fun insertUser(user: User) = userUseCase.insertUser(user)

    fun getUserByEmailAndPassword(email: String, password: String): LiveData<User?> {
        return userUseCase.getUserByEmailAndPassword(email, password)
    }

    fun getUserByEmail(email: String): LiveData<User?> {
        return userUseCase.getUserByEmail(email)
    }

}