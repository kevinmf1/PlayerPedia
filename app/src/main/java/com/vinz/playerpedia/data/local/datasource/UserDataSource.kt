package com.vinz.playerpedia.data.local.datasource

import androidx.lifecycle.LiveData
import com.vinz.playerpedia.data.local.entity.UserEntity
import com.vinz.playerpedia.data.local.room.UserDao
import kotlinx.coroutines.flow.Flow

interface UserDataSource {
    fun insertUser(user: UserEntity)

    fun updateUser(user: UserEntity)

    fun deleteUser(user: UserEntity)

    fun getUserById(userId: Int): Flow<UserEntity>

    fun getUserByEmailAndPassword(email: String, password: String): Flow<UserEntity>
}

class UserDatabaseDataSource(private val userDao: UserDao) : UserDataSource {
    override fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    override fun deleteUser(user: UserEntity) {
        userDao.deleteUser(user)
    }

    override fun getUserById(userId: Int): Flow<UserEntity> {
        return userDao.getUserById(userId)
    }

    override fun getUserByEmailAndPassword(email: String, password: String): Flow<UserEntity> {
        return userDao.getUserByEmailAndPassword(email, password)
    }
}