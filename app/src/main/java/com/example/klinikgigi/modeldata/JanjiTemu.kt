package com.example.klinikgigi.modeldata

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
enum class StatusJanji {
    @SerialName("menunggu")
    MENUNGGU,

    @SerialName("selesai")
    SELESAI,

    @SerialName("batal")
    BATAL;

    companion object {
        fun fromString(value: String?): StatusJanji {
            return when (value?.lowercase()?.trim()) {
                "menunggu" -> MENUNGGU
                "selesai" -> SELESAI
                "batal" -> BATAL
                else -> MENUNGGU
            }
        }
    }

    override fun toString(): String {
        return when (this) {
            MENUNGGU -> "menunggu"
            SELESAI -> "selesai"
            BATAL -> "batal"
        }
    }
}

object StatusJanjiSerializer : KSerializer<StatusJanji> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("StatusJanji", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: StatusJanji) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): StatusJanji {
        return StatusJanji.fromString(decoder.decodeString())
    }
}

@Serializable
data class JanjiTemu(
    val id: Int = 0,
    val id_dokter: Int,
    val id_pasien: Int,
    val tanggal_janji: String,
    val jam_janji: String,
    val keluhan: String,
    @Serializable(with = StatusJanjiSerializer::class)
    val status: StatusJanji = StatusJanji.MENUNGGU
)