package com.vinz.playerpedia.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vinz.playerpedia.core.domain.model.User
import com.vinz.playerpedia.core.domain.usecase.PlayerUseCase
import com.vinz.playerpedia.core.domain.usecase.UserUseCase

class LoginViewModel(private val userUseCase: UserUseCase) : ViewModel() {

    fun getUserByEmailAndPassword(email: String, password: String): LiveData<User?> {
        return userUseCase.getUserByEmailAndPassword(email, password)
    }
}