package com.vinz.domain.model

import java.io.File

data class User(
    val id: Int = 0,
    val name: String,
    val username: String,
    val email: String,
    val phone: String? = null,
    val password: String,
    val image: File? = null,
)