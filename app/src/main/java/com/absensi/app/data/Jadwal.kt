package com.absensi.app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Jadwal(
    val id: Int? = null,
    val idMk: String,
    val namaMatkul: String? = null,
    val namaDosen: String? = null,
    val semester: String? = null,
    val pertemuan_ke: String? = null,
    val hari: String? = null,
    val tanggal: String? = null,
) : Parcelable