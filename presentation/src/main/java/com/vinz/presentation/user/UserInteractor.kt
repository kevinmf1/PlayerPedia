package com.vinz.presentation.user

import com.vinz.domain.model.User
import com.vinz.domain.repository.IUserRepository
import com.vinz.domain.usecase.UserUseCase
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository) :
    UserUseCase {
    override fun insertUser(user: User) = userRepository.insertUser(user)

    override fun updateUser(user: User) = userRepository.updateUser(user)

    override fun deleteUser(user: User) = userRepository.deleteUser(user)

    override fun getUserById(userId: Int) = userRepository.getUserById(userId)

    override fun getUserByEmailAndPassword(email: String, password: String) =
        userRepository.getUserByEmailAndPassword(email, password)

    override fun getUserByEmail(email: String) = userRepository.getUserByEmail(email)
}