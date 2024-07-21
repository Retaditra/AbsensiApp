package com.absensi.app.data.respone

import com.google.gson.annotations.SerializedName

data class IzinRequest(
    @SerializedName("id_ptm")
    val id_ptm: String,
    @SerializedName("keterangan")
    val keterangan: String
)