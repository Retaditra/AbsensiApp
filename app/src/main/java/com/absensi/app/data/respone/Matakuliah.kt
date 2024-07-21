package com.absensi.app.data.respone

import com.google.gson.annotations.SerializedName

data class MatkulResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<MatkulData>,
)

data class MatkulData(
    @SerializedName("id")
    val id: String,
    @SerializedName("nama_mk")
    val nama_mk: String,
    @SerializedName("nama_dsn")
    val nama_dsn: String,
    @SerializedName("semester")
    val semester: String,
)