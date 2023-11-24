package com.vinz.playerpedia.utils

import android.os.Parcelable

data class PlayerRemoteSend(
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
    constructor(parcel: android.os.Parcel) : this(
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

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
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

    companion object CREATOR : android.os.Parcelable.Creator<PlayerRemoteSend> {
        override fun createFromParcel(parcel: android.os.Parcel): PlayerRemoteSend {
            return PlayerRemoteSend(parcel)
        }

        override fun newArray(size: Int): Array<PlayerRemoteSend?> {
            return arrayOfNulls(size)
        }
    }
}
