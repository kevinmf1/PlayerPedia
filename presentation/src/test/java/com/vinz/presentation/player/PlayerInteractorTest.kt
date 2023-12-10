package com.vinz.presentation.player

import app.cash.turbine.test
import com.vinz.domain.model.PlayerRemote
import com.vinz.domain.repository.IPlayerRepository
import com.vinz.domain.utils.ResultWrapper
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class PlayerInteractorTest {
    private val playerRepository: IPlayerRepository = mockk()
    private lateinit var playerInteractor: PlayerInteractor

    @Before
    fun setUp() {
        playerInteractor = PlayerInteractor(playerRepository)
    }

    @Test
    fun `getAllRemotePlayer calls repository`() = runTest {
        // Given
        val players = listOf<PlayerRemote>()
        val flow = flowOf(ResultWrapper.Success(players))
        coEvery { playerRepository.getAllRemotePlayer() } returns flow

        // When
        playerInteractor.getAllRemotePlayer()

        // Then
        coVerify { playerRepository.getAllRemotePlayer() }
    }

    @Test
    fun `getAllRemotePlayer returns expected players`() = runTest {
        // Given
        val players = listOf<PlayerRemote>()
        val flow = flowOf(ResultWrapper.Success(players))
        coEvery { playerRepository.getAllRemotePlayer() } returns flow

        // When
        val result = playerInteractor.getAllRemotePlayer()

        // Then
        result.test {
            val resultWrapper = awaitItem()
            if (resultWrapper is ResultWrapper.Success) {
                assertEquals(players, resultWrapper.payload)
            }
            awaitComplete()
        }
    }

    @Test
    fun `getAllRemotePlayer returns null players`() = runTest {
        // Given
        val flow = flowOf(ResultWrapper.Empty<List<PlayerRemote>>(null))
        coEvery { playerRepository.getAllRemotePlayer() } returns flow

        // When
        val result = playerInteractor.getAllRemotePlayer()

        // Then
        result.test {
            val resultWrapper = awaitItem()
            if (resultWrapper is ResultWrapper.Empty) {
                assertEquals(null, resultWrapper.payload)
            }
            awaitComplete()
        }
    }

    @Test
    fun `getAllRemotePlayer loading`() = runTest {
        // Given
        val players = listOf<PlayerRemote>()
        val flow = flowOf(ResultWrapper.Loading(players))
        coEvery { playerRepository.getAllRemotePlayer() } returns flow

        // When
        val result = playerInteractor.getAllRemotePlayer()

        // Then
        result.test {
            val resultWrapper = awaitItem()
            if (resultWrapper is ResultWrapper.Loading) {
                assertEquals(players, resultWrapper.payload)
            }
            awaitComplete()
        }
    }

    @Test
    fun `getAllRemotePlayer empty`() = runTest {
        // Given
        val players = listOf<PlayerRemote>()
        val flow = flowOf(ResultWrapper.Empty(players))
        coEvery { playerRepository.getAllRemotePlayer() } returns flow

        // When
        val result = playerInteractor.getAllRemotePlayer()

        // Then
        result.test {
            val resultWrapper = awaitItem()
            if (resultWrapper is ResultWrapper.Empty) {
                assertEquals(players, resultWrapper.payload)
            }
            awaitComplete()
        }
    }

}