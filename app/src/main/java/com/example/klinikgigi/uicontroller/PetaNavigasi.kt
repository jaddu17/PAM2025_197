package com.example.klinikgigi.uicontroller

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.klinikgigi.uicontroller.route.*
import com.example.klinikgigi.view.*
import com.example.klinikgigi.view.route.DestinasiEditDokter
import com.example.klinikgigi.view.route.DestinasiEditPasien
import com.example.klinikgigi.viewmodel.*
import com.example.klinikgigi.viewmodel.provider.PenyediaViewModel

@Composable
fun KlinikApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    HostNavigasiKlinik(navController = navController, modifier = modifier)
}

@Composable
fun HostNavigasiKlinik(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {

    NavHost(
        navController = navController,
        startDestination = DestinasiLogin.route,
        modifier = modifier
    ) {

        // ---------------- LOGIN ----------------
        composable(DestinasiLogin.route) {
            val authVM: AuthViewModel = viewModel(factory = PenyediaViewModel.Factory)
            LoginScreen(
                navController = navController,
                viewModel = authVM
            )
        }

        // ---------------- REGISTER ----------------
        composable(DestinasiRegister.route) {
            val authVM: AuthViewModel = viewModel(factory = PenyediaViewModel.Factory)
            RegisterScreen(
                navController = navController,
                viewModel = authVM
            )
        }

        // ---------------- ADMIN HOME ----------------
        composable(DestinasiAdminHome.route) {
            val adminVM: AdminViewModel = viewModel(factory = PenyediaViewModel.Factory)
            AdminHomeScreen(
                adminViewModel = adminVM,
                navigateToHalamanDokter = { navController.navigate(DestinasiDokter.route) },
                navigateToHalamanPasien = { navController.navigate(DestinasiHalamanPasien.route) },
                navigateToJanjiTemu = { navController.navigate(DestinasiAdminJanji.route) },
                navigateToHalamanTindakan = { navController.navigate(DestinasiTindakan.route) },
                navigateLogout = {
                    navController.navigate(DestinasiLogin.route) {
                        popUpTo(DestinasiLogin.route) { inclusive = true }
                    }
                }
            )
        }

        // ---------------- ADD / EDIT DOKTER ----------------
        composable(
            route = DestinasiAddEditDokter.route,
            arguments = listOf(navArgument("dokterId") { type = NavType.IntType })
        ) {
            val adminVM: AdminViewModel = viewModel(factory = PenyediaViewModel.Factory)
            HalamanEntryDokter(
                viewModel = adminVM,
                onSelesai = { navController.popBackStack() },
                onKembali = { navController.popBackStack() }
            )
        }

        // ---------------- LIST DOKTER ----------------
        composable(DestinasiDokter.route) {
            val adminVM: AdminViewModel = viewModel(factory = PenyediaViewModel.Factory)
            HalamanDokter(
                viewModel = adminVM,
                onTambah = { navController.navigate(DestinasiAddEditDokter.createRoute(null)) },
                onEdit = { dokterId ->
                    navController.navigate("${DestinasiEditDokter.route}/$dokterId")
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ---------------- EDIT DOKTER ----------------
        composable(
            route = DestinasiEditDokter.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditDokter.dokterIdArg) { type = NavType.IntType })
        ) { backStackEntry ->
            val dokterId = backStackEntry.arguments?.getInt(DestinasiEditDokter.dokterIdArg) ?: 0
            val adminVM: AdminViewModel = viewModel(factory = PenyediaViewModel.Factory)

            EditDokterScreen(
                dokterId = dokterId,
                viewModel = adminVM,
                onBack = { navController.popBackStack() }
            )
        }

        // ---------------- ADMIN JANJI TEMU LIST ----------------
        composable(DestinasiAdminJanji.route) {
            val janjiVM: JanjiTemuViewModel = viewModel(factory = PenyediaViewModel.Factory)

            AdminJanjiTemuScreen(
                viewModel = janjiVM,
                navigateToAdd = {
                    navController.navigate(DestinasiEntryJanji.route)
                },
                navigateToEdit = { janjiId ->
                    navController.navigate("${DestinasiEditJanjiTemu.route}/$janjiId")
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

// ============ TAMBAH JANJI TEMU (TANPA ARGUMEN) ============
        composable(DestinasiEntryJanji.route) {
            val janjiVM: JanjiTemuViewModel = viewModel(factory = PenyediaViewModel.Factory)

            EntryJanjiTemuScreen(
                viewModel = janjiVM,
                janjiId = null,
                navigateBack = { navController.popBackStack() }
            )
        }

// ============ EDIT JANJI TEMU (DENGAN ARGUMEN) ============
        composable(
            route = DestinasiEditJanjiTemu.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiEditJanjiTemu.janjiIdArg) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(DestinasiEditJanjiTemu.janjiIdArg)

            val janjiVM: JanjiTemuViewModel = viewModel(factory = PenyediaViewModel.Factory)

            // ✅ Perbaiki syntax: gunakan tanda kurung!
            EditJanjiTemuScreen(
                viewModel = janjiVM,
                janjiId = id!!,
                navigateBack = { navController.popBackStack() } // ✅ tambahkan parameter navigateBack
            )
        }


        // ---------------- HALAMAN PASIEN ----------------
        composable(DestinasiHalamanPasien.route) {
            val pasienVM: PasienViewModel = viewModel(factory = PenyediaViewModel.Factory)
            HalamanPasienScreen(
                pasienViewModel = pasienVM,
                navigateToEntryPasien = { navController.navigate(DestinasiEntryPasien.route) },
                navigateToEditPasien = { pasienId ->
                    navController.navigate("${DestinasiEditPasien.route}/$pasienId")
                },
                navigateBack = { navController.popBackStack() }
            )
        }

        // ---------------- ENTRY PASIEN ----------------
        composable(DestinasiEntryPasien.route) {
            val pasienVM: PasienViewModel = viewModel(factory = PenyediaViewModel.Factory)
            EntryPasienScreen(
                pasienViewModel = pasienVM,
                pasienId = null,
                navigateBack = { navController.popBackStack() }
            )
        }

        // ---------------- EDIT PASIEN ----------------
        composable(
            route = DestinasiEditPasien.routeWithArgs,
            arguments = listOf(navArgument(DestinasiEditPasien.pasienIdArg) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val pasienId = backStackEntry.arguments?.getInt(DestinasiEditPasien.pasienIdArg)
            val pasienVM: PasienViewModel = viewModel(factory = PenyediaViewModel.Factory)

            EditPasienScreen(
                pasienId = pasienId!!,
                pasienViewModel = pasienVM,
                navigateBack = { navController.popBackStack() }
            )
        }

        // ---------------- ENTRY TINDAKAN ----------------
        composable(DestinasiEntryTindakan.route) {
            val vm: TindakanViewModel = viewModel(factory = PenyediaViewModel.Factory)
            EntryTindakanScreen(
                viewModel = vm,
                onBack = { navController.popBackStack() }
            )
        }

        // ---------------- LIST TINDAKAN ----------------
        composable(DestinasiTindakan.route) {
            val vm: TindakanViewModel = viewModel(factory = PenyediaViewModel.Factory)
            HalamanTindakanScreen(
                viewModel = vm,
                onTambah = { navController.navigate(DestinasiEntryTindakan.route) },
                onEdit = { tindakanId ->
                    navController.navigate("${DestinasiEditTindakan.route}/$tindakanId")
                },
                onBack = { navController.popBackStack() }
            )
        }

        // ---------------- EDIT TINDAKAN ----------------
        composable(
            route = DestinasiEditTindakan.routeWithArgs,
            arguments = listOf(
                navArgument(DestinasiEditTindakan.tindakanIdArg) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val tindakanId = backStackEntry.arguments?.getInt(DestinasiEditTindakan.tindakanIdArg) ?: 0
            val tindakanVM: TindakanViewModel = viewModel(factory = PenyediaViewModel.Factory)

            EditTindakanScreen(
                tindakanId = tindakanId,
                viewModel = tindakanVM,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
