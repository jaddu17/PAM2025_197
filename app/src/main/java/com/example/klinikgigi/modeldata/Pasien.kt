package com.example.klinikgigi.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Pasien(
    val id_pasien: Int, // otomatis di-generate di server, bisa null saat create
    val nama_pasien: String,
    val jenis_kelamin: String,
    val tanggal_lahir: String, // format: "YYYY-MM-DD"
    val alamat: String,
    val nomor_telepon: String
)
