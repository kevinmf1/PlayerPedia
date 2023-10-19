package com.vinz.playerpedia.data.local.repository

import com.vinz.playerpedia.data.local.datasource.UserDataSource
import com.vinz.playerpedia.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(
    private val dataSource: UserDataSource
) {
    fun insertUser(user: UserEntity) {
        dataSource.insertUser(user)
    }

    fun updateUser(user: UserEntity) {
        dataSource.updateUser(user)
    }

    fun deleteUser(user: UserEntity) {
        dataSource.deleteUser(user)
    }

    fun getUserById(userId: Int) : Flow<UserEntity> = dataSource.getUserById(userId)
}