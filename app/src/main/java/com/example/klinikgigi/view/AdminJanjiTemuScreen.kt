package com.example.klinikgigi.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.klinikgigi.modeldata.JanjiTemu
import com.example.klinikgigi.viewmodel.JanjiTemuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminJanjiTemuScreen(
    viewModel: JanjiTemuViewModel,
    navigateToAdd: () -> Unit,
    navigateToEdit: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    val janjiList by viewModel.janjiList.collectAsState()
    val pasienList by viewModel.pasienList.collectAsState()
    val dokterList by viewModel.dokterList.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val status by viewModel.status.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(status) {
        status?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatus()
        }
    }

    fun getNamaPasien(idPasien: Int): String {
        return pasienList.find { it.id_pasien == idPasien }?.nama_pasien ?: "Pasien tidak ditemukan"
    }

    fun getNamaDokter(idDokter: Int): String {
        return dokterList.find { it.id_dokter == idDokter }?.nama_dokter ?: "Dokter tidak ditemukan"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Janji Temu (Admin)") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAdd) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Janji Temu")
            }
        }
    ) { padding ->

        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(janjiList, key = { it.id }) { janji ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Pasien: ${getNamaPasien(janji.id_pasien)}")
                            Text("Dokter: ${getNamaDokter(janji.id_dokter)}")
                            Text("Tanggal: ${janji.tanggal_janji} • ${janji.jam_janji}")
                            Text("Keluhan: ${janji.keluhan}")

                            // ✅ STATUS SUDAH ENUM — TIDAK PERLU ifBlank!
                            val statusText = janji.status.toString().replaceFirstChar { it.titlecase() }
                            Text("Status: $statusText")

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                TextButton(
                                    onClick = {
                                        navigateToEdit(janji.id)
                                    }
                                ) {
                                    Text("Edit")
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                TextButton(onClick = { viewModel.deleteJanji(janji.id) }) {
                                    Text("Hapus")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}