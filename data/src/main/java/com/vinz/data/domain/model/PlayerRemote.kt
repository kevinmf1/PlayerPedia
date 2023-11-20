package com.vinz.data.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.vinz.data.data.source.remote.response.PlayerDataResponse

data class PlayerRemote(
    val id: Int,
    val name: String,
    val club: String,
    val rate: Double,
    val position: String,
    val allGoalUntilNow: Int,
    val allAssistUntilNow: Int,
    val activePlayer: String,
    val description: String,
    val photo: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(club)
        parcel.writeDouble(rate)
        parcel.writeString(position)
        parcel.writeInt(allGoalUntilNow)
        parcel.writeInt(allAssistUntilNow)
        parcel.writeString(activePlayer)
        parcel.writeString(description)
        parcel.writeString(photo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayerRemote> {
        override fun createFromParcel(parcel: Parcel): PlayerRemote {
            return PlayerRemote(parcel)
        }

        override fun newArray(size: Int): Array<PlayerRemote?> {
            return arrayOfNulls(size)
        }
    }
}

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
