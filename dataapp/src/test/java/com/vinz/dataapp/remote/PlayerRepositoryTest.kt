package com.vinz.dataapp.remote

import app.cash.turbine.test
import com.vinz.dataapp.remote.datasource.RemoteDataSourceImpl
import com.vinz.dataapp.remote.response.PlayerDataResponse
import com.vinz.dataapp.remote.response.PlayerResponse
import com.vinz.domain.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class PlayerRepositoryTest {


    @MockK
    lateinit var remoteDataSource: RemoteDataSourceImpl

    private lateinit var repository: PlayerRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = PlayerRepository(remoteDataSource)
    }

    @Test
    fun `get player, with result loading`() {
        val mockPlayerResponse = mockk<PlayerResponse>()
        runTest {
            coEvery { remoteDataSource.getPlayers() } returns mockPlayerResponse
            repository.getAllRemotePlayer().map {
                delay(100)
                it
            }.test {
                delay(110)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Loading)
                coVerify { remoteDataSource.getPlayers() }
            }
        }
    }

    @Test
    fun `get player, with result success`() {
        val fakePlayerDataResponse = PlayerDataResponse(
            id = 1,
            name = "Kevin De Bruyne",
            club = "Manchester City",
            rate = 4.8,
            position = "Midfielder",
            allGoalUntilNow = 140,
            allAssistUntilNow = 240,
            activePlayer = "YES",
            description = "Kevin De Bruyne is a Belgian professional footballer who plays as a midfielder for Premier League club Manchester City, where he is vice-captain, and the Belgium national team.",
            photo = "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1e/Kevin_De_Bruyne_2018.jpg/220px-Kevin_De_Bruyne_2018.jpg"
        )
        val fakePlayerResponse = PlayerResponse(
            error = "false",
            message = "Success",
            count = 1,
            data = listOf(fakePlayerDataResponse)
        )
        runTest {
            coEvery { remoteDataSource.getPlayers() } returns fakePlayerResponse
            repository.getAllRemotePlayer().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Success)
                Assert.assertEquals(data.payload?.size, 1)
                Assert.assertEquals(data.payload?.get(0)?.id, 1)
                coVerify { remoteDataSource.getPlayers() }
            }
        }
    }

    @Test
    fun `get player, with result error`() {
        runTest {
            coEvery { remoteDataSource.getPlayers() } throws IllegalStateException("Mock error")
            repository.getAllRemotePlayer().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Error)
                coVerify { remoteDataSource.getPlayers() }
            }
        }
    }

    @Test
    fun `get player, with result empty`() {
        val fakePlayerResponse = PlayerResponse(
            error = "true",
            message = "Error",
            count = 0,
            data = emptyList()
        )
        runTest {
            coEvery { remoteDataSource.getPlayers() } returns fakePlayerResponse
            repository.getAllRemotePlayer().map {
                delay(100)
                it
            }.test {
                delay(220)
                val data = expectMostRecentItem()
                Assert.assertTrue(data is ResultWrapper.Empty)
                coVerify { remoteDataSource.getPlayers() }
            }
        }
    }

}