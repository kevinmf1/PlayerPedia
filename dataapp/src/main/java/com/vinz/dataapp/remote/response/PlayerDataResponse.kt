package com.vinz.dataapp.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.vinz.domain.model.PlayerRemote

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

fun PlayerDataResponse.toPlayerRemote() = PlayerRemote(
    id = id,
    name = name,
    club = club,
    rate = rate,
    position = position,
    allGoalUntilNow = allGoalUntilNow,
    allAssistUntilNow = allAssistUntilNow,
    activePlayer = activePlayer,
    description = description,
    photo = photo
)

fun Collection<PlayerDataResponse>.toPlayerRemote() = this.map {
    it.toPlayerRemote()
}