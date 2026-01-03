package com.example.klinikgigi.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.klinikgigi.modeldata.Tindakan
import com.example.klinikgigi.viewmodel.TindakanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanTindakanScreen(
    viewModel: TindakanViewModel,
    onTambah: () -> Unit,
    onEdit: (Int) -> Unit,
    onBack: () -> Unit
) {
    val tindakanList by viewModel.tindakanList.collectAsState()
    val loading by viewModel.loading.collectAsState()

    // State untuk AlertDialog hapus
    var tindakanYangAkanDihapus by remember { mutableStateOf<Tindakan?>(null) }

    LaunchedEffect(Unit) { viewModel.loadTindakan() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Kelola Tindakan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onTambah) {
                Text("+")
            }
        }
    ) { padding ->
        when {
            loading -> Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            tindakanList.isEmpty() -> Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Belum ada data tindakan")
            }

            else -> LazyColumn(
                Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(tindakanList) { tindakan ->
                    tindakan.id_tindakan?.let { id ->
                        TindakanItem(
                            tindakan = tindakan,
                            onEdit = { onEdit(id) },
                            onDelete = { tindakanYangAkanDihapus = tindakan } // simpan tindakan, bukan hanya ID
                        )
                    }
                }
            }
        }
    }

    // 💥 AlertDialog Konfirmasi Hapus
    tindakanYangAkanDihapus?.let { tindakan ->
        AlertDialog(
            onDismissRequest = { tindakanYangAkanDihapus = null },
            title = { Text("Konfirmasi Hapus") },
            text = { Text("Yakin ingin menghapus tindakan \"${tindakan.nama_tindakan}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        tindakan.id_tindakan?.let { id ->
                            viewModel.deleteTindakan(id)
                        }
                        tindakanYangAkanDihapus = null
                    }
                ) {
                    Text("Ya")
                }
            },
            dismissButton = {
                TextButton(onClick = { tindakanYangAkanDihapus = null }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun TindakanItem(
    tindakan: Tindakan,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Nama: ${tindakan.nama_tindakan}")
            Text("Harga: Rp ${tindakan.harga}")
            Text("Deskripsi: ${tindakan.deskripsi}")

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEdit) { Text("Edit") }
                Spacer(Modifier.width(8.dp))
                TextButton(onClick = onDelete) { Text("Hapus") }
            }
        }
    }
}