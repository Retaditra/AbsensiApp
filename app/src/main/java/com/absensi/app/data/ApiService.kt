package com.absensi.app.data

import com.absensi.app.data.respone.*
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiService {

    @POST("/api/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/update-password")
    fun getUpdate(
        @Header("Authorization") token: String,
        @Body update: UpdateRequest,
    ): Call<MessageResponse>

    @GET("/api/profile")
    fun getProfile(@Header("Authorization") token: String): Call<ProfileResponse>

    @GET("/api/matakuliah")
    fun getAllMatkul(@Header("Authorization") token: String): Call<MatkulResponse>

    @GET("/api/jadwal")
    fun getJadwal(@Header("Authorization") token: String): Call<MatkulResponse>

    @GET("/api/matakuliah/{id}/pertemuan")
    fun getMeet(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<PertemuanResponse>

    @GET("/api/pertemuan/today")
    fun getToday(@Header("Authorization") token: String): Call<PertemuanResponse>

    @POST("/api/absent")
    fun absent(
        @Header("Authorization") token: String,
        @Body absent: AbsentRequest
    ): Call<MessageResponse>

    @POST("/api/absent/izin")
    fun izin(
        @Header("Authorization") token: String,
        @Body izin: IzinRequest
    ): Call<MessageResponse>

    @GET("/api/absent/riwayat")
    fun getHistory(@Header("Authorization") token: String): Call<PertemuanResponse>

    @POST("/api/logout")
    fun logout(@Header("Authorization") token: String): Call<MessageResponse>
}
