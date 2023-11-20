package com.vinz.playerpedia.activity.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vinz.data.domain.model.User
import com.vinz.data.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (private val userUseCase: UserUseCase) : ViewModel() {

    fun getUserByEmail(email: String): LiveData<User?> {
        return userUseCase.getUserByEmail(email)
    }

    fun updateUser(user: User) {
        userUseCase.updateUser(user)
    }

}