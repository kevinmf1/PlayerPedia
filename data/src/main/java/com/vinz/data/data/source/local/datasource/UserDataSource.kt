package com.vinz.data.data.source.local.datasource

import androidx.lifecycle.LiveData
import com.vinz.data.data.source.local.room.UserDao
import com.vinz.data.data.source.local.entity.UserEntity
import javax.inject.Inject
import javax.inject.Singleton

interface UserDataSource {
    fun insertUser(user: UserEntity)

    fun updateUser(user: UserEntity)

    fun deleteUser(user: UserEntity)

    fun getUserById(userId: Int): LiveData<UserEntity>

    fun getUserByEmailAndPassword(email: String, password: String): LiveData<UserEntity?>

    fun getUserByEmail(email: String): LiveData<UserEntity?>
}

@Singleton
class UserDatabaseDataSource @Inject constructor(private val userDao: UserDao) : UserDataSource {

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