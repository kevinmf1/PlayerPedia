package com.vinz.playerpedia.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File

@Parcelize
data class User(
    val id: Int = 0,
    val name: String,
    val username: String,
    val email: String,
    val phone: String? = null,
    val password: String,
    val image: File? = null,
) : Parcelable