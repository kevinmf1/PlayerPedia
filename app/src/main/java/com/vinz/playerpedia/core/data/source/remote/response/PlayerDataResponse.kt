package com.vinz.playerpedia.core.data.source.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PlayerDataResponse(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("club")
    val club: String,

    @SerializedName("rate")
    val rate: Double,

    @SerializedName("position")
    val position: String,

    @SerializedName("allGoalUntilNow")
    val allGoalUntilNow: Int,

    @SerializedName("allAssistUntilNow")
    val allAssistUntilNow: Int,

    @SerializedName("activePlayer")
    val activePlayer: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("photo")
    val photo: String
)