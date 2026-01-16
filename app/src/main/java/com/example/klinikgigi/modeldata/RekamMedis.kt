package com.example.klinikgigi.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class RekamMedis(
    val id_rekam: Int,        // auto-generate di server
    val id_janji: Int,        // relasi ke tabel janji_temu
    val id_tindakan: Int,     // relasi ke tabel tindakan
    val diagnosa: String,
    val catatan: String,
    val resep: String,
    val created_at: String? = null,
    val updated_at: String? = null
)
