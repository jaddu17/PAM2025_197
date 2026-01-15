package com.example.klinikgigi.view.dokter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.klinikgigi.viewmodel.DokterViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDokterScreen(
    dokterId: Int,
    viewModel: DokterViewModel,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.loadDokter()
    }

    val dokterList by viewModel.dokterList.collectAsState()
    val dokter = dokterList.find { it.id_dokter == dokterId }

    var nama by remember { mutableStateOf("") }
    var spesialis by remember { mutableStateOf("") }
    var telepon by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val message by viewModel.message.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(dokter) {
        dokter?.let {
            nama = it.nama_dokter
            spesialis = it.spesialisasi
            telepon = it.nomor_telepon
        }
    }

    LaunchedEffect(message) {
        message?.let { msg ->
            if (msg.contains("berhasil", ignoreCase = true)) {
                showSuccessDialog = true
            } else {
                scope.launch { snackbarHostState.showSnackbar(msg) }
            }
            viewModel.clearMessage()
        }
    }

    val telpValid = telepon.isEmpty() || telepon.matches(Regex("^08[0-9]{9,11}$"))
    val telpError = if (telepon.isNotEmpty() && !telpValid) {
        "Nomor telepon harus diawali 08 dan terdiri dari 11–13 digit"
    } else null

    // Cek apakah data berubah
    val isChanged = dokter?.let {
        it.nama_dokter != nama ||
        it.spesialisasi != spesialis ||
        it.nomor_telepon != telepon
    } ?: false

    val isFormValid = nama.isNotBlank() && spesialis.isNotBlank() && telpValid && telepon.length in 11..13

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Dokter", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        if (dokter == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.surface)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            OutlinedTextField(
                value = nama,
                onValueChange = { nama = it },
                label = { Text("Nama Dokter") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = spesialis,
                onValueChange = { spesialis = it },
                label = { Text("Spesialisasi") },
                leadingIcon = { Icon(Icons.Default.MedicalServices, null) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telepon,
                onValueChange = { telepon = it.filter { c -> c.isDigit() } },
                label = { Text("Nomor Telepon") },
                leadingIcon = { Icon(Icons.Default.Phone, null) },
                isError = telpError != null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            if (telpError != null) {
                Text(
                    text = telpError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    val updated = dokter.copy(
                        nama_dokter = nama,
                        spesialisasi = spesialis,
                        nomor_telepon = telepon
                    )
                    viewModel.updateDokter(updated)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = isFormValid && isChanged
            ) {
                Text("Simpan Perubahan", fontWeight = FontWeight.Bold)
            }
        }
    }

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { /* Force click OK */ },
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary) },
            title = { Text("Sukses") },
            text = { Text("Data dokter berhasil diperbarui!") },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        onBack()
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }
}
