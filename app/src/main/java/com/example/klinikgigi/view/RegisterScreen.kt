package com.example.klinikgigi.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.klinikgigi.viewmodel.AuthViewModel
import com.example.klinikgigi.uicontroller.route.DestinasiLogin

@Composable
fun RegisterScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("dokter") } // role default

    val registerStatus by viewModel.registerStatus.collectAsState()
    val loading by viewModel.loadingRegister.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    // menangani response register
    LaunchedEffect(registerStatus) {
        registerStatus?.let {
            snackbarHostState.showSnackbar(it)
            if (it.contains("berhasil", true)) {
                navController.navigate(DestinasiLogin.route)
            }
            viewModel.clearStatus()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Register", style = MaterialTheme.typography.headlineMedium)

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = role,
                    onValueChange = { role = it },
                    label = { Text("Role (admin/dokter)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = { viewModel.register(username, password, role) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Register")
                    }
                }

                TextButton(onClick = {
                    navController.navigate(DestinasiLogin.route)
                }) {
                    Text("Sudah punya akun? Login")
                }
            }
        }
    }
}
