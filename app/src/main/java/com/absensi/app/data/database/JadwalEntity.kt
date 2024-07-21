package com.absensi.app.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "JadwalEntity")
data class JadwalEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "id_mk")
    val id_mk: String,

    @ColumnInfo(name = "nama_mk")
    val nama_mk: String,

    @ColumnInfo(name = "nama_dsn")
    val nama_dsn: String,

    @ColumnInfo(name = "semester")
    val semester: String,

    @ColumnInfo(name = "pertemuan_ke")
    val pertemuan_ke: String,

    @ColumnInfo(name = "hari")
    val hari: String,

    @ColumnInfo(name = "tanggal")
    val tanggal: String,
)

