package com.vinz.playerpedia.activity.login

import androidx.lifecycle.ViewModel
import com.vinz.playerpedia.data.local.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getUserByEmailAndPassword(email: String, password: String) = userRepository.getUserByEmailAndPassword(email, password)
}