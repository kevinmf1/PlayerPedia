package com.vinz.playerpedia.core.domain.model

import android.os.Parcel
import android.os.Parcelable
import java.io.File
data class Player(
    val playerId: Int,
    val playerName: String,
    val playerClub: String,
    val playerPosition: String,
    val playerNationality: String,
    val playerDescription: String,
    val playerImage: File,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readSerializable() as File
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(playerId)
        parcel.writeString(playerName)
        parcel.writeString(playerClub)
        parcel.writeString(playerPosition)
        parcel.writeString(playerNationality)
        parcel.writeString(playerDescription)
        parcel.writeSerializable(playerImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Player> {
        override fun createFromParcel(parcel: Parcel): Player {
            return Player(parcel)
        }

        override fun newArray(size: Int): Array<Player?> {
            return arrayOfNulls(size)
        }
    }
}
