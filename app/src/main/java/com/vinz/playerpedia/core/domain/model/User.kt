package com.vinz.playerpedia.core.domain.model

import android.os.Parcelable

data class User(
    val id: Int = 0,
    val name: String,
    val username: String,
    val email: String,
    val phone: String? = null,
    val password: String
) : Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(username)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : android.os.Parcelable.Creator<User> {
        override fun createFromParcel(parcel: android.os.Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}