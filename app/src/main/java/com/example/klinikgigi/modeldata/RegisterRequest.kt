package com.example.klinikgigi.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val password: String,
    val role: String
)
