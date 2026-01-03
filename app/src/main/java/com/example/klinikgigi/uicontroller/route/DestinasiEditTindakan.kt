package com.example.klinikgigi.uicontroller.route

import com.example.klinikgigi.R

object DestinasiEditTindakan : DestinasiNavigasi {

    override val route = "edit_tindakan"
    override val titleRes = R.string.edit_tindakan

    const val tindakanIdArg = "id"

    val routeWithArgs = "$route/{$tindakanIdArg}"
}
