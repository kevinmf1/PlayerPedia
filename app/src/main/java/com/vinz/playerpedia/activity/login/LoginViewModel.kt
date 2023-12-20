package com.vinz.playerpedia.activity.login

import androidx.lifecycle.ViewModel
import com.vinz.domain.model.User
import com.vinz.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val userUseCase: UserUseCase) : ViewModel() {

    fun getUserByEmailAndPassword(email: String, password: String): Flow<User?> {
        return userUseCase.getUserByEmailAndPassword(email, password)
    }

}