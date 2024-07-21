package com.absensi.app.data.respone

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ProfileData,
)

data class ProfileData(
    @SerializedName("id")
    val id: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("nama")
    val nama: String,
    @SerializedName("nim")
    val nim: String,
    @SerializedName("semester")
    val semester: String,
    @SerializedName("prodi")
    val prodi: String,
    @SerializedName("fakultas")
    val fakultas: String,
)
