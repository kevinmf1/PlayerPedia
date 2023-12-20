package com.vinz.dataapp.local.datasource

import app.cash.turbine.test
import com.vinz.dataapp.local.entity.UserEntity
import com.vinz.dataapp.local.room.UserDao
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
    fun getUserById() = runTest {
        val user = mockk<UserEntity>()

        val flowUser: Flow<UserEntity> = flowOf(user)

        coEvery { userDao.getUserById(1) } returns flowUser

        val result = userDataSource.getUserById(1)

        result.test {
            assertEquals(user, awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) { userDao.getUserById(1) }
    }

    @Test
    fun getUserByEmailAndPassword() = runTest {
        val user = mockk<UserEntity>()

        val flowUser: Flow<UserEntity?> = flowOf(user)

        coEvery {
            userDao.getUserByEmailAndPassword(
                "email@gmail.com",
                "password"
            )
        } returns flowUser

        val result = userDataSource.getUserByEmailAndPassword("email@gmail.com", "password")

        result.test {
            assertEquals(user, awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) { userDao.getUserByEmailAndPassword("email@gmail.com", "password") }
    }

    @Test
    fun getUserByEmail() = runTest {
        val user = mockk<UserEntity>()

        val flowUser: Flow<UserEntity?> = flowOf(user)

        coEvery { userDao.getUserByEmail("email@gmail.com") } returns flowUser

        val result = userDataSource.getUserByEmail("email@gmail.com")

        result.test {
            assertEquals(user, awaitItem())
            awaitComplete()
        }

        coVerify(exactly = 1) { userDao.getUserByEmail("email@gmail.com") }
    }
}