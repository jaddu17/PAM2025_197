package com.example.klinikgigi.view

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.klinikgigi.modeldata.Pasien
import com.example.klinikgigi.viewmodel.PasienViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPasienScreen(
    pasienViewModel: PasienViewModel,
    pasienId: Int?,
    navigateBack: () -> Unit
) {

    // ================= LOAD DATA EDIT =================
    LaunchedEffect(pasienId) {
        pasienId?.let {
            pasienViewModel.loadPasienById(it)
        }
    }

    val pasien by pasienViewModel.selectedPasien.collectAsState()

    var nama by remember { mutableStateOf("") }
    var jenisKelamin by remember { mutableStateOf("") }
    var tanggalLahir by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var nomorTelepon by remember { mutableStateOf("") }

    LaunchedEffect(pasien) {
        pasien?.let {
            nama = it.nama_pasien
            jenisKelamin = it.jenis_kelamin
            tanggalLahir = it.tanggal_lahir
            alamat = it.alamat
            nomorTelepon = it.nomor_telepon
        }
    }

    // ================= DATE PICKER =================
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    fun openDatePicker() {
        DatePickerDialog(
            context,
            { _, year, month, day ->
                tanggalLahir =
                    "%04d-%02d-%02d".format(year, month + 1, day)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            datePicker.maxDate = System.currentTimeMillis()
        }.show()
    }

    // ================= UI =================
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (pasienId == null) "Tambah Pasien" else "Edit Pasien")
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->

        if (pasienId != null && pasien == null) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                label = { Text("Nama Pasien") },
                modifier = Modifier.fillMaxWidth()
            )

            Text("Jenis Kelamin")
            Row {
                RadioButton(
                    selected = jenisKelamin == "Laki-laki",
                    onClick = { jenisKelamin = "Laki-laki" }
                )
                Text("Laki-laki")
                Spacer(Modifier.width(16.dp))
                RadioButton(
                    selected = jenisKelamin == "Perempuan",
                    onClick = { jenisKelamin = "Perempuan" }
                )
                Text("Perempuan")
            }

            OutlinedTextField(
                value = tanggalLahir,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tanggal Lahir") },
                trailingIcon = {
                    IconButton(onClick = { openDatePicker() }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Pilih Tanggal")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { openDatePicker() }
            )

            OutlinedTextField(
                value = alamat,
                onValueChange = { alamat = it },
                label = { Text("Alamat") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nomorTelepon,
                onValueChange = { nomorTelepon = it },
                label = { Text("Nomor Telepon") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = nama.isNotBlank() &&
                        jenisKelamin.isNotBlank() &&
                        tanggalLahir.isNotBlank(),
                onClick = {

                    val p = Pasien(
                        id_pasien = pasienId ?: 0,   // ✅ FIX UTAMA
                        nama_pasien = nama,
                        jenis_kelamin = jenisKelamin,
                        tanggal_lahir = tanggalLahir,
                        alamat = alamat,
                        nomor_telepon = nomorTelepon
                    )

                    if (pasienId == null)
                        pasienViewModel.createPasien(p)
                    else
                        pasienViewModel.updatePasien(p)

                    navigateBack()
                }
            ) {
                Text(if (pasienId == null) "Tambah" else "Update")
            }
        }
    }
}
