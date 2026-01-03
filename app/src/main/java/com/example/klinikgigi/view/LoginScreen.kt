package com.example.klinikgigi.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.klinikgigi.viewmodel.AuthViewModel
import com.example.klinikgigi.uicontroller.route.DestinasiAdminHome
import com.example.klinikgigi.uicontroller.route.DestinasiDokterHome
import com.example.klinikgigi.uicontroller.route.DestinasiRegister
import com.example.klinikgigi.uicontroller.route.DestinasiLogin

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginStatus by viewModel.loginStatus.collectAsState()
    val loginRole by viewModel.loginRole.collectAsState()
    val loading by viewModel.loadingLogin.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    // Menangani response login
    LaunchedEffect(loginStatus, loginRole) {
        val status = loginStatus
        val role = loginRole

        if (status == "success" && role != null) {
            when (role) {
                "admin" -> navController.navigate(DestinasiAdminHome.route) {
                    popUpTo(DestinasiLogin.route) { inclusive = true }
                }
                "dokter" -> navController.navigate(DestinasiDokterHome.route) {
                    popUpTo(DestinasiLogin.route) { inclusive = true }
                }
            }
            viewModel.clearStatus()
        } else if (status != null && status != "success") {
            snackbarHostState.showSnackbar(status)
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
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Login", style = MaterialTheme.typography.headlineMedium)

                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { viewModel.login(username, password) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text("Login")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = {
                    navController.navigate(DestinasiRegister.route)
                }) {
                    Text("Belum punya akun? Register")
                }
            }
        }
    }
}
