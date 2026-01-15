package com.example.klinikgigi.uicontroller.route

import com.example.klinikgigi.R

object DestinasiDokterJanji : DestinasiNavigasi {
    override val route = "dokter_janji"
    override val titleRes = R.string.dokter_janji
    fun createRoute(idDokter: Int) = "dokter_janji_temu/$idDokter"
}
