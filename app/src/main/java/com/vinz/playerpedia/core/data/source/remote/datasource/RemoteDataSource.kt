package com.vinz.playerpedia.core.data.source.remote.datasource

import com.vinz.playerpedia.core.data.source.remote.network.APIService
import com.vinz.playerpedia.core.data.source.remote.response.PlayerResponse

interface RemoteDataSource {
    suspend fun getPlayers(): PlayerResponse
}

class RemoteDataSourceImpl(private val apiService: APIService) : RemoteDataSource {

    companion object {
        @Volatile
        private var instance: RemoteDataSourceImpl? = null

        fun getInstance(service: APIService): RemoteDataSourceImpl =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSourceImpl(service)
            }
    }

    override suspend fun getPlayers(): PlayerResponse {
        return apiService.getPlayers()
    }

}