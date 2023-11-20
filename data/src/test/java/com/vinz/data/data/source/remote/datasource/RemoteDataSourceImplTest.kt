package com.vinz.data.data.source.remote.datasource

import com.vinz.data.data.source.remote.network.APIService
import com.vinz.data.data.source.remote.response.PlayerResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class RemoteDataSourceImplTest {

    @MockK
    lateinit var service: APIService

    private lateinit var dataSource: RemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = RemoteDataSourceImpl(service)
    }

    @Test
    fun getPlayers() {
        runTest {
            val mockResponse = mockk<PlayerResponse>()
            coEvery { service.getPlayers() } returns mockResponse
            val response = dataSource.getPlayers()
            coVerify { service.getPlayers() }
            assertEquals(response, mockResponse)
        }
    }
}