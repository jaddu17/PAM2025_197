package com.example.klinikgigi.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Pasien(
    val id_pasien: Int,
    val nama_pasien: String,
    val jenis_kelamin: String,
    val tanggal_lahir: String,
    val alamat: String,
    val nomor_telepon: String
)
