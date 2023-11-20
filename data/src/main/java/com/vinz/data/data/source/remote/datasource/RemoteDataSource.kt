package com.vinz.data.data.source.remote.datasource

import com.vinz.data.data.source.remote.network.APIService
import com.vinz.data.data.source.remote.response.PlayerResponse
import javax.inject.Inject
import javax.inject.Singleton

interface RemoteDataSource {
    suspend fun getPlayers(): PlayerResponse
}

@Singleton
class RemoteDataSourceImpl @Inject constructor(private val apiService: APIService) : RemoteDataSource {

    override suspend fun getPlayers(): PlayerResponse {
        return apiService.getPlayers()
    }

}