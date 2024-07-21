package com.absensi.app.data.respone

import com.google.gson.annotations.SerializedName

data class PertemuanResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<PertemuanData>,
)

data class PertemuanData(
    @SerializedName("id")
    val id: String,
    @SerializedName("id_mk")
    val id_mk: String,
    @SerializedName("nama_mk")
    val nama_mk: String,
    @SerializedName("pertemuan_ke")
    val pertemuan_ke: String,
    @SerializedName("hari")
    val hari: String,
    @SerializedName("tanggal")
    val tanggal: String,
    @SerializedName("waktu")
    val waktu: String,
    @SerializedName("nama_dsn")
    val nama_dsn: String,
    @SerializedName("kode_absensi")
    val kode_absensi: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("keterangan")
    val keterangan: String,
)