package com.absensi.app.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pertemuan(
    val id: String,
    val id_mk: String? = null,
    val namaMatkul: String? = null,
    val pertemuan_ke: String? = null,
    val hari: String? = null,
    val tanggal: String? = null,
    val waktu: String? = null,
    val semester: String? = null,
    val namaDosen: String? = null,
    val kode_absensi: String? = null,
    val status: String? = null,
    val keterangan: String? = null,
) : Parcelable