package com.example.klinikgigi.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val success: Boolean,
    val message: String,
    val id_user: Int? = null,
    val role: String? = null
)