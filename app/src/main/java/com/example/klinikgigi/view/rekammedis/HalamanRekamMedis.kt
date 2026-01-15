package com.example.klinikgigi.view.rekammedis

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Note
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.klinikgigi.modeldata.RekamMedis
import com.example.klinikgigi.viewmodel.RekamMedisViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HalamanRekamMedis(
    viewModel: RekamMedisViewModel,
    onEdit: (Int) -> Unit,
    onBack: () -> Unit
) {
    val rekamMedisList by viewModel.rekamMedisList.collectAsState()
    val loading by viewModel.loading.collectAsState()

    var rekamMedisHapus by remember { mutableStateOf<RekamMedis?>(null) }

    LaunchedEffect(Unit) {
        viewModel.loadRekamMedis()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Rekam Medis", fontWeight = FontWeight.Bold) },
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

        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (rekamMedisList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.MedicalServices, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.height(16.dp))
                    Text("Belum ada data rekam medis", color = MaterialTheme.colorScheme.outline)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(rekamMedisList) { rm ->
                    RekamMedisCard(
                        rekamMedis = rm,
                        onEdit = { onEdit(rm.id_rekam) },
                        onDelete = { rekamMedisHapus = rm }
                    )
                }
            }
        }
    }

    // ================= KONFIRMASI HAPUS =================
    rekamMedisHapus?.let { rm ->
        AlertDialog(
            onDismissRequest = { rekamMedisHapus = null },
            icon = { Icon(Icons.Default.Delete, contentDescription = null) },
            title = { Text("Hapus Rekam Medis") },
            text = {
                Text("Yakin ingin menghapus rekam medis ini?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteRekamMedis(rm.id_rekam)
                        rekamMedisHapus = null
                    },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { rekamMedisHapus = null }) {
                    Text("Batal")
                }
            }
        )
    }
}

@Composable
fun RekamMedisCard(
    rekamMedis: RekamMedis,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "ID Janji: ${rekamMedis.id_janji}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "ID Tindakan: ${rekamMedis.id_tindakan}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            
            Divider()

            Row(verticalAlignment = Alignment.Top) {
                Icon(Icons.Default.MedicalServices, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.tertiary)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Diagnosa: ${rekamMedis.diagnosa}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            if (rekamMedis.catatan.isNotBlank()) {
                Row(verticalAlignment = Alignment.Top) {
                   Icon(Icons.Default.Note, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.outline)
                   Spacer(Modifier.width(8.dp))
                   Text("Catatan: ${rekamMedis.catatan}", style = MaterialTheme.typography.bodySmall)
                }
            }

            if (rekamMedis.resep.isNotBlank()) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.outline)
                    Spacer(Modifier.width(8.dp))
                    Text("Resep: ${rekamMedis.resep}", style = MaterialTheme.typography.bodySmall)
                }
            }

            Divider(modifier = Modifier.padding(vertical = 4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Edit")
                }

                Spacer(modifier = Modifier.width(8.dp))

                TextButton(onClick = onDelete, colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)) {
                    Icon(Icons.Default.Delete, contentDescription = "Hapus", modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Hapus")
                }
            }
        }
    }
}
