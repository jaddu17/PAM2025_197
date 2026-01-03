package com.example.klinikgigi.uicontroller.route

import com.example.klinikgigi.R

object DestinasiEditJanjiTemu : DestinasiNavigasi {
    override val route = "edit_janji_temu"
    override val titleRes = R.string.edit_janji_temu

    const val janjiIdArg = "janjiId"
    val routeWithArgs = "$route/{$janjiIdArg}"
}