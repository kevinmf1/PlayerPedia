package com.vinz.playerpedia.activity.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.vinz.domain.model.User
import org.junit.Assert.assertEquals
import com.vinz.domain.usecase.UserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val userUseCase: UserUseCase = mockk(relaxed = true)
    private lateinit var registerViewModel: RegisterViewModel

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(userUseCase)
    }

    @Test
    fun `insertUser calls use case`() = runTest {
        val user = User(1, email = "test@gmail.com", password = "password", name = "Test User", username = "testuser", phone = "08123456789")

        registerViewModel.insertUser(user)

        coVerify { userUseCase.insertUser(user) }
    }

    @Test
    fun `getUserByEmailAndPassword returns expected user`() = runTest {
        val user = User(1, email = "test@gmail.com", password = "password", name = "Test User", username = "testuser", phone = "08123456789")
        val email = "test@gmail.com"
        val password = "password"

        coEvery { userUseCase.getUserByEmailAndPassword(email, password) } returns flowOf(user)

        val result = registerViewModel.getUserByEmailAndPassword(email, password)

        result.test {
            assertEquals(user, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getUserByEmail returns expected user`() = runTest {
        val user = User(1, email = "test@gmail.com", password = "password", name = "Test User", username = "testuser", phone = "08123456789")
        val email = "test@gmail.com"

        coEvery { userUseCase.getUserByEmail(email) } returns flowOf(user)

        val result = registerViewModel.getUserByEmail(email)

        result.test {
            assertEquals(user, awaitItem())
            awaitComplete()
        }
    }
}