package com.vinz.playerpedia.core.data.source.remote.network

import com.vinz.playerpedia.core.data.source.remote.response.PlayerResponse
import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET("players")
    suspend fun getPlayers(): PlayerResponse

}