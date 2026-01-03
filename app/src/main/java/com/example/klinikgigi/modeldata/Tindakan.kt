package com.example.klinikgigi.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Tindakan(
    val id_tindakan: Int = 0,
    val nama_tindakan: String,
    val deskripsi: String,
    val harga: String
)
