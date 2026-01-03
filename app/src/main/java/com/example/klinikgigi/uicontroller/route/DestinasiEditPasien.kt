package com.example.klinikgigi.view.route

import com.example.klinikgigi.R
import com.example.klinikgigi.uicontroller.route.DestinasiNavigasi

object DestinasiEditPasien : DestinasiNavigasi {
    override val route = "edit_pasien"
    override val titleRes = R.string.edit_pasien

    const val pasienIdArg = "pasienId"
    val routeWithArgs = "$route/{$pasienIdArg}"
}

