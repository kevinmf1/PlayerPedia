package com.vinz.playerpedia.activity.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vinz.playerpedia.core.domain.model.User
import com.vinz.playerpedia.core.domain.usecase.UserUseCase

class ProfileViewModel (private val userUseCase: UserUseCase) : ViewModel() {

    fun getUserByEmail(email: String): LiveData<User?> {
        return userUseCase.getUserByEmail(email)
    }

    fun updateUser(user: User) {
        userUseCase.updateUser(user)
    }

}