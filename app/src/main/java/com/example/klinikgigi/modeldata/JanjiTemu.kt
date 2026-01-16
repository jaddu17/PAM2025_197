package com.example.klinikgigi.modeldata

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonNames

object StatusJanjiSerializer : KSerializer<StatusJanji> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("StatusJanji", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: StatusJanji) {
        encoder.encodeString(value.name.lowercase())
    }

    override fun deserialize(decoder: Decoder): StatusJanji {
        val string = decoder.decodeString().lowercase()
        return try {
            when (string) {
                "menunggu", "konfirmasi", "" -> StatusJanji.KONFIRMASI
                "selesai" -> StatusJanji.SELESAI
                "batal", "dibatalkan" -> StatusJanji.DIBATALKAN
                "tidak_hadir" -> StatusJanji.TIDAK_HADIR
                else -> StatusJanji.KONFIRMASI // Fallback for any other unknown value
            }
        } catch (e: Exception) {
            StatusJanji.KONFIRMASI
        }
    }
}

@Serializable(with = StatusJanjiSerializer::class)
enum class StatusJanji {
    @SerialName("konfirmasi")
    @JsonNames("menunggu") // Backward compatibility
    KONFIRMASI,

    @SerialName("selesai")
    SELESAI,

    @SerialName("dibatalkan")
    @JsonNames("batal") // Backward compatibility
    DIBATALKAN,

    @SerialName("tidak_hadir")
    TIDAK_HADIR
}
@Serializable
data class JanjiTemu(
    @SerialName("id")
    val id_janji: Int,
    val id_dokter: Int,
    val id_pasien: Int,
    val tanggal_janji: String,
    val jam_janji: String,
    val keluhan: String,
    val status: StatusJanji,
    val nama_pasien: String? = "-",

    val sudah_ada_rekam_medis: Boolean = false
)
