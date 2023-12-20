package com.vinz.playerpedia.activity.user

import androidx.lifecycle.ViewModel
import com.vinz.domain.model.User
import com.vinz.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor (private val userUseCase: UserUseCase) : ViewModel() {

    fun getUserByEmail(email: String): Flow<User?> {
        return userUseCase.getUserByEmail(email)
    }

    fun updateUser(user: User) {
        userUseCase.updateUser(user)
    }

}