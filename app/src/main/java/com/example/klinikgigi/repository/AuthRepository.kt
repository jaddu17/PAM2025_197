package com.example.klinikgigi.repository

import com.example.klinikgigi.modeldata.LoginRequest
import com.example.klinikgigi.modeldata.LoginResponse
import com.example.klinikgigi.modeldata.RegisterRequest
import com.example.klinikgigi.modeldata.RegisterResponse
import com.example.klinikgigi.remote.ServiceApiKlinik

class AuthRepository(
    private val api: ServiceApiKlinik
) {

    suspend fun login(username: String, password: String): LoginResponse {
        val request = LoginRequest(username, password)
        return api.login(request)
    }

    suspend fun register(username: String, password: String, role: String): RegisterResponse {
        val request = RegisterRequest(username, password, role)
        return api.register(request)
    }
}
