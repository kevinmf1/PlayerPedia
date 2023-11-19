package com.vinz.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.vinz.core.data.source.local.datasource.UserDatabaseDataSource
import com.vinz.core.domain.model.User
import com.vinz.core.utils.AppExecutors
import com.vinz.core.utils.DataMapper
import com.vinz.core.utils.getOrAwaitValue
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executor

class UserRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var localDataSource: UserDatabaseDataSource

    @MockK
    lateinit var appExecutors: AppExecutors

    private lateinit var repository: UserRepository

    private val mockUser = User(
        id = 1,
        name = "Kevin",
        username = "kevin111",
        email = "kevin123@gmail.com",
        phone = "0812345678",
        password = "aabbccdd",
        image = null
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = UserRepository(localDataSource, appExecutors)
    }

    @Test
    fun insertUser() {
        val userEntity = DataMapper.userDomainToUserEntity(mockUser)
        val executor = mockk<Executor>()

        every { executor.execute(any()) } answers { firstArg<Runnable>().run() }
        coEvery { appExecutors.diskIO() } returns executor
        coEvery { localDataSource.insertUser(userEntity) } returns Unit

        runTest {
            repository.insertUser(mockUser)

            coVerify(exactly = 1) { appExecutors.diskIO() }
            coVerify(exactly = 1) { localDataSource.insertUser(userEntity) }
        }
    }

    @Test
    fun updateUser() {
        val userEntity = DataMapper.userDomainToUserEntity(mockUser)
        val executor = mockk<Executor>()

        every { executor.execute(any()) } answers { firstArg<Runnable>().run() }
        coEvery { appExecutors.diskIO() } returns executor
        coEvery { localDataSource.updateUser(userEntity) } returns Unit

        runTest {
            repository.updateUser(mockUser)

            coVerify(exactly = 1) { appExecutors.diskIO() }
            coVerify(exactly = 1) { localDataSource.updateUser(userEntity) }
        }
    }

    @Test
    fun deleteUser() {
        val userEntity = DataMapper.userDomainToUserEntity(mockUser)
        val executor = mockk<Executor>()

        every { executor.execute(any()) } answers { firstArg<Runnable>().run() }
        coEvery { appExecutors.diskIO() } returns executor
        coEvery { localDataSource.deleteUser(userEntity) } returns Unit

        runTest {
            repository.deleteUser(mockUser)

            coVerify(exactly = 1) { appExecutors.diskIO() }
            coVerify(exactly = 1) { localDataSource.deleteUser(userEntity) }
        }
    }

    @Test
    fun getUserById() {
        coEvery { localDataSource.getUserById(1) } returns mockk(relaxed = true)

        runTest {
            repository.getUserById(1)

            coVerify(exactly = 1) { localDataSource.getUserById(1) }
        }
    }

    @Test
    fun `get user by id with right value`() {
        val userEntity = DataMapper.userDomainToUserEntity(mockUser)
        coEvery { localDataSource.getUserById(1) } returns MutableLiveData(userEntity)

        runTest {
            val result = repository.getUserById(1).getOrAwaitValue()
            assertEquals(mockUser.name, result.name)
            assertEquals(mockUser.email, result.email)

            coVerify(exactly = 1) { localDataSource.getUserById(1) }
        }
    }

    @Test
    fun getUserByEmailAndPassword() {
        coEvery { localDataSource.getUserByEmailAndPassword("kevin@gmail.com", "aabbccdd") } returns mockk()

        runTest {
            repository.getUserByEmailAndPassword("kevin@gmail.com", "aabbccdd")

            coVerify(exactly = 1) { localDataSource.getUserByEmailAndPassword("kevin@gmail.com", "aabbccdd") }
        }
    }

    @Test
    fun getUserByEmail() {
        coEvery { localDataSource.getUserByEmail("kevin@gmail.com") } returns mockk()
        runTest {
            repository.getUserByEmail("kevin@gmail.com")

            coVerify(exactly = 1) { localDataSource.getUserByEmail("kevin@gmail.com")}
        }
    }

}