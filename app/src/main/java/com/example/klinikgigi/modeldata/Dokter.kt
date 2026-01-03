package com.example.klinikgigi.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Dokter(
    val id_dokter: Int,
    val nama_dokter: String,
    val spesialisasi: String,
    val nomor_telepon: String
)