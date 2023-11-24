package com.vinz.dataapp.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.vinz.dataapp.remote.response.PlayerDataResponse

@Keep
data class PlayerResponse(
    @SerializedName("error")
    val error: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("count")
    val count: Int,

    @SerializedName("data")
    val data: List<PlayerDataResponse>
)
