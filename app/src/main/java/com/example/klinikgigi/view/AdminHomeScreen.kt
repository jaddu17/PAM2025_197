package com.example.klinikgigi.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.klinikgigi.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(
    adminViewModel: AdminViewModel,
    navigateToHalamanDokter: () -> Unit,
    navigateToHalamanPasien: () -> Unit,
    navigateToJanjiTemu: () -> Unit,
    navigateToHalamanTindakan: () -> Unit,   // ← TAMBAHAN
    navigateLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Admin Home") })
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = navigateToHalamanDokter
            ) {
                Text("Kelola Data Dokter")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = navigateToHalamanPasien
            ) {
                Text("Kelola Data Pasien")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = navigateToHalamanTindakan
            ) {
                Text("Kelola Data Tindakan")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = navigateToJanjiTemu
            ) {
                Text("Kelola Janji Temu")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = navigateLogout
            ) {
                Text("Logout")
            }
        }
    }
}
