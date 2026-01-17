package com.example.klinikgigi.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class RekamMedis(
    val id_rekam: Int,
    val id_janji: Int,
    val id_tindakan: Int,
    val diagnosa: String,
    val catatan: String,
    val resep: String,
    val created_at: String? = null,
    val updated_at: String? = null
)
