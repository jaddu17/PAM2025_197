package com.example.klinikgigi.remote

import com.example.klinikgigi.modeldata.*
import retrofit2.Response
import retrofit2.http.*

interface ServiceApiKlinik {

    // ---------------- AUTH ----------------
    @POST("users/login.php")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @POST("users/register.php")
    suspend fun register(@Body body: RegisterRequest): RegisterResponse


    // ---------------- DOKTER ----------------
    @GET("dokter/read.php")
    suspend fun getDokter(): List<Dokter>

    @POST("dokter/create.php")
    suspend fun createDokter(@Body dokter: Dokter): retrofit2.Response<Void>

    @PUT("dokter/update.php")
    suspend fun updateDokter(@Body dokter: Dokter): retrofit2.Response<Void>

    @DELETE("dokter/delete.php")
    suspend fun deleteDokter(@Query("id") id: Int): retrofit2.Response<Void>


    // ---------------- PASIEN ----------------
    @GET("pasien/read.php")
    suspend fun getPasien(): List<Pasien>

    @POST("pasien/create.php")
    suspend fun createPasien(@Body pasien: Pasien): retrofit2.Response<Void>

    @PUT("pasien/update.php")
    suspend fun updatePasien(@Body pasien: Pasien): retrofit2.Response<Void>

    @DELETE("pasien/delete.php")
    suspend fun deletePasien(@Query("id") id: Int): retrofit2.Response<Void>


    // ---------------- JANJI TEMU ----------------
    @GET("janji/read.php")
    suspend fun getJanjiTemu(): List<JanjiTemu>

    @POST("janji/create.php")
    suspend fun createJanjiTemu(@Body janji: JanjiTemu): retrofit2.Response<Void>

    @PUT("janji/update.php")
    suspend fun updateJanjiTemu(@Body janji: JanjiTemu): retrofit2.Response<Void>

    @DELETE("janji/delete.php")
    suspend fun deleteJanjiTemu(@Query("id") id: Int): retrofit2.Response<Void>


    // ---------------- TINDAKAN ----------------
    @GET("tindakan/read.php")
    suspend fun getTindakan(): List<Tindakan>

    @POST("tindakan/create.php")
    suspend fun createTindakan(@Body tindakan: Tindakan): retrofit2.Response<Void>

    @PUT("tindakan/update.php")
    suspend fun updateTindakan(@Body tindakan: Tindakan): retrofit2.Response<Void>

    @DELETE("tindakan/delete.php")
    suspend fun deleteTindakan(@Query("id") id: Int): retrofit2.Response<Void>
}
