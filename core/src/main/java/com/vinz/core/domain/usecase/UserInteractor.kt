package com.vinz.core.domain.usecase

import com.vinz.core.domain.model.User
import com.vinz.core.domain.repository.IUserRepository
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository) : UserUseCase {
    override fun insertUser(user: User) = userRepository.insertUser(user)

    override fun updateUser(user: User) = userRepository.updateUser(user)

    override fun deleteUser(user: User) = userRepository.deleteUser(user)

    override fun getUserById(userId: Int) = userRepository.getUserById(userId)

    override fun getUserByEmailAndPassword(email: String, password: String) =
        userRepository.getUserByEmailAndPassword(email, password)

    override fun getUserByEmail(email: String) = userRepository.getUserByEmail(email)
}