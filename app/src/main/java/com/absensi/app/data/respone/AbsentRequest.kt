package com.absensi.app.data.respone

import com.google.gson.annotations.SerializedName

data class AbsentRequest(
    @SerializedName("id_ptm")
    val id_ptm: String,
    @SerializedName("kode_absensi")
    val kode_absensi: String
)