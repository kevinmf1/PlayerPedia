package com.vinz.dataapp.remote.network

import com.vinz.dataapp.remote.response.PlayerResponse
import retrofit2.http.GET

interface APIService {

    @GET("players")
    suspend fun getPlayers(): PlayerResponse

}