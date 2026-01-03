package com.example.klinikgigi.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id_user: Int? = null,
    val username: String,
    val role: String
)