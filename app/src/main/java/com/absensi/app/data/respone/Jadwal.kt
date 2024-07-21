package com.absensi.app.data.respone

import com.google.gson.annotations.SerializedName

data class JadwalResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<JadwalData>
)

data class JadwalData(
    @SerializedName("id")
    val id: String,
    @field:SerializedName("nama_mk")
    val nama_mk: String,
    @SerializedName("nama_dsn")
    val nama_dsn: String,
    @SerializedName("semester")
    val semester: String,
    @field:SerializedName("pertemuan_ke")
    val pertemuan_ke: String,
    @SerializedName("hari")
    val hari: String,
    @field:SerializedName("tanggal")
    val tanggal: String,
)