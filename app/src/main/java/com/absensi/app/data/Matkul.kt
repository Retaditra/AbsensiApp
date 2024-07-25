package com.absensi.app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Matkul(
    val id: String? = null,
    val id_mk: String? = null,
    val day: String? = null,
    val namaMatkul: String? = null,
    val tanggal: String? = null,
) : Parcelable