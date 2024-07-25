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
    @SerializedName("id_mk")
    val id_mk: String,
    @SerializedName("nama_mk")
    val nama_mk: String,
    @SerializedName("hari")
    val hari: String,
    @SerializedName("tanggal")
    val tanggal: String,
)