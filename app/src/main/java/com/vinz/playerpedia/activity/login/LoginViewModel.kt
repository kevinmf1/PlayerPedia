package com.vinz.playerpedia.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vinz.data.domain.model.User
import com.vinz.data.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    fun getUserByEmailAndPassword(email: String, password: String): LiveData<User?> {
        return userUseCase.getUserByEmailAndPassword(email, password)
    }
}