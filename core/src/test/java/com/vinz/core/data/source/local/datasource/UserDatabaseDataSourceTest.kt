package com.vinz.core.data.source.local.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vinz.core.data.source.local.entity.UserEntity
import com.vinz.core.data.source.local.room.UserDao
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserDatabaseDataSourceTest {

    @MockK
    lateinit var userDao: UserDao

    private lateinit var userDataSource: UserDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userDataSource = UserDatabaseDataSource(userDao)
    }

    @Test
    fun insertUser() {
        val user = mockk<UserEntity>()
        runTest {
            coEvery { userDao.insertUser(user) } returns Unit

            userDataSource.insertUser(user)

            coVerify(exactly = 1) { userDao.insertUser(user) }
        }
    }

    @Test
    fun updateUser() {
        val user = mockk<UserEntity>()
        runTest {
            coEvery { userDao.updateUser(user) } returns Unit

            userDataSource.updateUser(user)

            coVerify(exactly = 1) { userDao.updateUser(user) }
        }
    }

    @Test
    fun deleteUser() {
        val user = mockk<UserEntity>()
        runTest {
            coEvery { userDao.deleteUser(user) } returns Unit

            userDataSource.deleteUser(user)

            coVerify(exactly = 1) { userDao.deleteUser(user) }
        }
    }

    @Test
    fun getUserById() {
        val user = mockk<UserEntity>()

        val liveDataUser: LiveData<UserEntity> = MutableLiveData(user)

        runTest {
            coEvery { userDao.getUserById(1) } returns liveDataUser

            val result = userDataSource.getUserById(1)

            assertEquals(user, result.value)
            coVerify(exactly = 1) { userDao.getUserById(1) }
        }
    }

    @Test
    fun getUserByEmailAndPassword() {
        val user = mockk<UserEntity>()

        val liveDataUser: LiveData<UserEntity?> = MutableLiveData(user)

        runTest {
            coEvery { userDao.getUserByEmailAndPassword("email@gmail.com", "password") } returns liveDataUser

            val result = userDataSource.getUserByEmailAndPassword("email@gmail.com", "password")

            assertEquals(user, result.value)
            coVerify(exactly = 1) { userDao.getUserByEmailAndPassword("email@gmail.com", "password") }
        }
    }

    @Test
    fun getUserByEmail() {
        val user = mockk<UserEntity>()

        val liveDataUser: LiveData<UserEntity?> = MutableLiveData(user)

        runTest {
            coEvery { userDao.getUserByEmail("email@gmail.com") } returns liveDataUser

            val result = userDataSource.getUserByEmail("email@gmail.com")

            assertEquals(user, result.value)

            coVerify(exactly = 1) { userDao.getUserByEmail("email@gmail.com") }
        }
    }
}