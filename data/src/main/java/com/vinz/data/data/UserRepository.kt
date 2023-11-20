package com.vinz.data.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vinz.data.data.source.local.datasource.UserDatabaseDataSource
import com.vinz.data.domain.model.User
import com.vinz.data.domain.repository.IUserRepository
import com.vinz.data.utils.AppExecutors
import com.vinz.data.utils.DataMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDatabaseDataSource: UserDatabaseDataSource,
    private val appExecutors: AppExecutors
) : IUserRepository {

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