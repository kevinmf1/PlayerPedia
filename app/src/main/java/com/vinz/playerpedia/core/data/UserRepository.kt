package com.vinz.playerpedia.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vinz.playerpedia.core.data.source.local.datasource.UserDatabaseDataSource
import com.vinz.playerpedia.core.domain.model.User
import com.vinz.playerpedia.core.domain.repository.IUserRepository
import com.vinz.playerpedia.core.utils.AppExecutors
import com.vinz.playerpedia.core.utils.DataMapper

class UserRepository private constructor(
    private val userDatabaseDataSource: UserDatabaseDataSource,
    private val appExecutors: AppExecutors
) : IUserRepository {

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            userData: UserDatabaseDataSource,
            appExecutors: AppExecutors
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userData, appExecutors)
            }
    }

    override fun insertUser(user: User) {
        val userEntity = DataMapper.userDomainToUserEntity(user)
        appExecutors.diskIO().execute { userDatabaseDataSource.insertUser(userEntity) }
    }

    override fun updateUser(user: User) {
        val userEntity = DataMapper.userDomainToUserEntity(user)
        appExecutors.diskIO().execute { userDatabaseDataSource.updateUser(userEntity) }
    }

    override fun deleteUser(user: User) {
        val userEntity = DataMapper.userDomainToUserEntity(user)
        appExecutors.diskIO().execute { userDatabaseDataSource.deleteUser(userEntity) }
    }

    override fun getUserById(userId: Int): LiveData<User> {
        return userDatabaseDataSource.getUserById(userId)
            .map { DataMapper.userEntityToUserDomain(it) }
    }

    override fun getUserByEmailAndPassword(email: String, password: String): LiveData<User?> {
        return userDatabaseDataSource.getUserByEmailAndPassword(email, password)
            .map { DataMapper.userLoginEntityToUserDomain(it) }
    }

    override fun getUserByEmail(email: String): LiveData<User?> {
        return userDatabaseDataSource.getUserByEmail(email)
            .map { DataMapper.userLoginEntityToUserDomain(it) }
    }
}