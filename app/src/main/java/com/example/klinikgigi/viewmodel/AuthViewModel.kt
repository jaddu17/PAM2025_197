package com.example.klinikgigi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.klinikgigi.modeldata.LoginRequest
import com.example.klinikgigi.modeldata.RegisterRequest
import com.example.klinikgigi.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    // -----------------------
    // STATE LOGIN
    // -----------------------
    private val _loginStatus = MutableStateFlow<String?>(null) // message atau "success"
    val loginStatus: StateFlow<String?> = _loginStatus

    private val _loginRole = MutableStateFlow<String?>(null) // "admin" / "dokter"
    val loginRole: StateFlow<String?> = _loginRole

    private val _loadingLogin = MutableStateFlow(false)
    val loadingLogin: StateFlow<Boolean> = _loadingLogin

    // -----------------------
    // STATE REGISTER
    // -----------------------
    private val _registerStatus = MutableStateFlow<String?>(null)
    val registerStatus: StateFlow<String?> = _registerStatus

    private val _loadingRegister = MutableStateFlow(false)
    val loadingRegister: StateFlow<Boolean> = _loadingRegister


    // ----------------------------------------------------------
    // LOGIN
    // ----------------------------------------------------------
    fun login(username: String, password: String) {

        if (username.isEmpty() || password.isEmpty()) {
            _loginStatus.value = "Username dan Password wajib diisi"
            return
        }

        viewModelScope.launch {
            try {
                _loadingLogin.value = true
                val response = repository.login(username, password)

                if (response.success) {
                    _loginStatus.value = "success"
                    _loginRole.value = response.role // pastikan repository mengembalikan role
                } else {
                    _loginStatus.value = response.message
                    _loginRole.value = null
                }

            } catch (e: Exception) {
                _loginStatus.value = "Terjadi kesalahan pada server"
                _loginRole.value = null
            } finally {
                _loadingLogin.value = false
            }
        }
    }


    // ----------------------------------------------------------
    // REGISTER
    // ----------------------------------------------------------
    fun register(username: String, password: String, role: String) {

        if (username.isEmpty() || password.isEmpty()) {
            _registerStatus.value = "Semua form harus diisi"
            return
        }

        viewModelScope.launch {
            try {
                _loadingRegister.value = true
                val response = repository.register(username, password, role)

                _registerStatus.value = response.message

            } catch (e: Exception) {
                _registerStatus.value = "Gagal koneksi server"
            } finally {
                _loadingRegister.value = false
            }
        }
    }

    // Reset semua status
    fun clearStatus() {
        _loginStatus.value = null
        _loginRole.value = null
        _registerStatus.value = null
    }
}
