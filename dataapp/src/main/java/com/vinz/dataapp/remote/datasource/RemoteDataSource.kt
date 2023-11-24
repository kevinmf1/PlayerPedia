package com.vinz.dataapp.remote.datasource

import com.vinz.dataapp.remote.network.APIService
import com.vinz.dataapp.remote.response.PlayerResponse
import javax.inject.Inject
import javax.inject.Singleton

interface RemoteDataSource {
    suspend fun getPlayers(): PlayerResponse
}

@Singleton
class RemoteDataSourceImpl @Inject constructor(private val apiService: APIService) :
    RemoteDataSource {

    override suspend fun getPlayers(): PlayerResponse {
        return apiService.getPlayers()
    }

}