// com.example.klinikgigi.view.JanjiTemuDokterScreen.kt

package com.example.klinikgigi.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.klinikgigi.modeldata.JanjiTemuPerDokter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JanjiTemuDokterScreen(
    dokter: JanjiTemuPerDokter,
    onBack: () -> Unit,
    onLihatRekamMedis: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Janji Temu ${dokter.nama_dokter}") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        if (dokter.janji_temu.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada janji temu")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(dokter.janji_temu) { janji ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Pasien: ${janji.nama_pasien}")
                            Text("Tanggal: ${janji.tanggal_janji}")
                            Text("Jam: ${janji.jam_janji}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = { onLihatRekamMedis(janji.id_janji) }) {
                                Text("Lihat Rekam Medis")
                            }
                        }
                    }
                }
            }
        }
    }
}