package com.vinz.playerpedia.activity.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.vinz.domain.model.User
import com.vinz.domain.usecase.UserUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ProfileViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val userUseCase: UserUseCase = mockk(relaxed = true)
    private lateinit var profileViewModel: ProfileViewModel

    @Before
    fun setUp() {
        profileViewModel = ProfileViewModel(userUseCase)
    }

    @Test
    fun `getUserByEmail returns expected user`() = runTest {
        val expectedUser = User(
            1,
            email = "test@gmail.com",
            password = "password",
            name = "Test User",
            username = "testuser",
            phone = "08123456789"
        )
        val email = "test@gmail.com"

        coEvery { userUseCase.getUserByEmail(email) } returns flowOf(expectedUser)

        val result = profileViewModel.getUserByEmail(email)

        result.test {
            Assert.assertEquals(expectedUser, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getUserByEmail returns null user`() = runTest {
        val email = "test@gmail.com"

        coEvery { userUseCase.getUserByEmail(email) } returns flowOf(null)

        val result = profileViewModel.getUserByEmail(email)

        result.test {
            Assert.assertEquals(null, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getUserByEmail loading`() = runTest {
        val email = "test@gmail.com"
        val user = User(
            1,
            email = email,
            password = "password",
            name = "Test User",
            username = "testuser",
            phone = "08123456789"
        )

        coEvery { userUseCase.getUserByEmail(email) } returns flowOf(user)

        val result = profileViewModel.getUserByEmail(email)

        result.test {
            Assert.assertNotNull(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `updateUser working successfully`() = runTest {
        val expectedUser = User(
            1,
            email = "updated@gmail.com",
            password = "updated",
            name = "Updated User",
            username = "updateduser",
            phone = "08123456789"
        )

        coEvery { userUseCase.updateUser(expectedUser) } just Runs

        profileViewModel.updateUser(expectedUser)

        coVerify { userUseCase.updateUser(expectedUser) }
    }

}