package com.vinz.dataapp.local.datasource

import com.vinz.dataapp.local.room.UserDao
import com.vinz.dataapp.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

interface UserDataSource {
    fun insertUser(user: UserEntity)

    fun updateUser(user: UserEntity)

    fun deleteUser(user: UserEntity)

    fun getUserById(userId: Int): Flow<UserEntity>

    fun getUserByEmailAndPassword(email: String, password: String): Flow<UserEntity?>

    fun getUserByEmail(email: String): Flow<UserEntity?>
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

    override fun getUserById(userId: Int): Flow<UserEntity> {
        return userDao.getUserById(userId)
    }

    override fun getUserByEmailAndPassword(email: String, password: String): Flow<UserEntity?> {
        return userDao.getUserByEmailAndPassword(email, password)
    }

    override fun getUserByEmail(email: String): Flow<UserEntity?> {
        return userDao.getUserByEmail(email)
    }
}