package com.absensi.app

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Jadwal(
    val id: String,
    val namaMatkul: String? = null,
    val namaDosen: String? = null,
    val semester: String? = null,
    val pertemuan_ke: String? = null,
    val hari: String? = null,
    val tanggal: String? = null,
) : Parcelable
