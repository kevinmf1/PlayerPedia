package com.vinz.playerpedia.activity.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.vinz.domain.model.User
import com.vinz.domain.usecase.UserUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    private val userUseCase: UserUseCase = mockk()
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(userUseCase)
    }

    @Test
    fun `getUserByEmailAndPassword returns expected user`() = runTest {
        val expectedUser = User(1, email = "test@gmail.com", password = "password", name = "Test User", username = "testuser", phone = "08123456789")
        val email = "test@gmail.com"
        val password = "password"

        coEvery { userUseCase.getUserByEmailAndPassword(email, password) } returns flowOf(expectedUser)

        val result = loginViewModel.getUserByEmailAndPassword(email, password)

        result.test {
            assertEquals(expectedUser, awaitItem())
            awaitComplete()
        }
    }


    @Test
    fun `getUserByEmailAndPassword returns null user`() = runTest {
        val email = "test@gmail.com"
        val password = "password"

        coEvery { userUseCase.getUserByEmailAndPassword(email, password) } returns flowOf(null)

        val result = loginViewModel.getUserByEmailAndPassword(email, password)

        result.test {
            assertEquals(null, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getUserByEmailAndPassword loading`() = runTest {
        val email = "test@gmail.com"
        val password = "password"
        val user = User(
            1,
            email = email,
            password = "password",
            name = "Test User",
            username = "testuser",
            phone = "08123456789"
        )

        coEvery { userUseCase.getUserByEmailAndPassword(email, password) } returns flowOf(user)

        val result = loginViewModel.getUserByEmailAndPassword(email, password)

        result.test {
            Assert.assertNotNull(awaitItem())
            awaitComplete()
        }
    }

}