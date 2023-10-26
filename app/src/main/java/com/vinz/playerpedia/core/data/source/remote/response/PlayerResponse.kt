package com.vinz.playerpedia.core.data.source.remote.response

data class PlayerResponse(
    val error: String,
    val message: String,
    val count: Int,
    val data: List<PlayerDataResponse>
)
