package com.example.klinikgigi.view.tindakan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.klinikgigi.modeldata.Tindakan
import com.example.klinikgigi.viewmodel.TindakanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTindakanScreen(
    viewModel: TindakanViewModel,
    onBack: () -> Unit
) {
    var namaTindakan by remember { mutableStateOf("") }
    var harga by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }

    var showSuccess by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Tindakan", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = namaTindakan,
                onValueChange = { namaTindakan = it },
                label = { Text("Nama Tindakan") },
                leadingIcon = { Icon(Icons.Default.MedicalServices, null) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = harga,
                onValueChange = { harga = it.filter { c -> c.isDigit() } },
                label = { Text("Harga (Rp)") },
                leadingIcon = { Icon(Icons.Default.Payments, null) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = deskripsi,
                onValueChange = { deskripsi = it },
                label = { Text("Deskripsi") },
                leadingIcon = { Icon(Icons.Default.Description, null) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.createTindakan(
                        Tindakan(
                            nama_tindakan = namaTindakan,
                            deskripsi = deskripsi,
                            harga = harga
                        )
                    )
                    showSuccess = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = namaTindakan.isNotBlank() && harga.isNotBlank() && deskripsi.isNotBlank()
            ) {
                Text("Simpan Data", fontWeight = FontWeight.Bold)
            }
        }
    }

    // ---------- Dialog Success ----------
    if (showSuccess) {
        AlertDialog(
            onDismissRequest = { },
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
            title = { Text("Sukses") },
            text = { Text("Data tindakan berhasil disimpan!") },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccess = false
                        onBack()
                    }
                ) { Text("OK") }
            }
        )
    }
}
