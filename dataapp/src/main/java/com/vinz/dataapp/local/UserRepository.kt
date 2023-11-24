package com.vinz.dataapp.local

import com.vinz.dataapp.local.datasource.UserDatabaseDataSource
import com.vinz.dataapp.utils.AppExecutors
import com.vinz.dataapp.utils.DataMapper
import com.vinz.domain.model.User
import com.vinz.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getUserById(userId: Int): Flow<User> {
        return userDatabaseDataSource.getUserById(userId)
            .map { DataMapper.userEntityToUserDomain(it) }
    }

    override fun getUserByEmailAndPassword(email: String, password: String): Flow<User?> {
        return userDatabaseDataSource.getUserByEmailAndPassword(email, password)
            .map { DataMapper.userLoginEntityToUserDomain(it) }
    }

    override fun getUserByEmail(email: String): Flow<User?> {
        return userDatabaseDataSource.getUserByEmail(email)
            .map { DataMapper.userLoginEntityToUserDomain(it) }
    }
}