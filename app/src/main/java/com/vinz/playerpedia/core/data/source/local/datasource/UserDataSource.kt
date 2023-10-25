package com.vinz.playerpedia.core.data.source.local.datasource

import androidx.lifecycle.LiveData
import com.vinz.playerpedia.core.data.source.local.room.UserDao
import com.vinz.playerpedia.core.data.source.local.entity.UserEntity

interface UserDataSource {
    fun insertUser(user: UserEntity)

    fun updateUser(user: UserEntity)

    fun deleteUser(user: UserEntity)

    fun getUserById(userId: Int): LiveData<UserEntity>

    fun getUserByEmailAndPassword(email: String, password: String): LiveData<UserEntity?>

    fun getUserByEmail(email: String): LiveData<UserEntity?>
}

class UserDatabaseDataSource(private val userDao: UserDao) : UserDataSource {

    companion object {
        private var instance: UserDatabaseDataSource? = null

        fun getInstance(userDao: UserDao): UserDatabaseDataSource =
            instance ?: synchronized(this) {
                instance ?: UserDatabaseDataSource(userDao)
            }
    }

    override fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override fun updateUser(user: UserEntity) {
        userDao.updateUser(user)
    }

    override fun deleteUser(user: UserEntity) {
        userDao.deleteUser(user)
    }

    override fun getUserById(userId: Int): LiveData<UserEntity> {
        return userDao.getUserById(userId)
    }

    override fun getUserByEmailAndPassword(email: String, password: String): LiveData<UserEntity?> {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    override fun getUserByEmail(email: String): LiveData<UserEntity?> {
        return userDao.getUserByEmail(email)
    }
}