package com.example.klinikgigi.uicontroller.route

import com.example.klinikgigi.R

object DestinasiAddEditDokter : DestinasiNavigasi {
    override val route = "add_edit_dokter/{dokterId}"
    override val titleRes = R.string.add_edit_dokter

    fun createRoute(dokterId: Int?) = "add_edit_dokter/${dokterId ?: -1}"
}