package com.absensi.app.utils

import com.absensi.app.data.Matkul
import com.absensi.app.data.Pertemuan
import com.absensi.app.data.database.JadwalEntity
import com.absensi.app.data.respone.MatkulData
import com.absensi.app.data.respone.PertemuanData

class DataMapper {
    fun responseToMeet(data: List<PertemuanData>): List<Pertemuan> {
        return data.map {
            Pertemuan(
                id = it.id,
                id_mk = it.id_mk,
                namaMatkul = it.nama_mk,
                pertemuan_ke = it.pertemuan_ke,
                hari = it.hari,
                tanggal = it.tanggal,
                waktu = it.waktu,
                semester = it.semester,
                namaDosen = it.nama_dsn,
                kode_absensi = it.kode_absensi,
                status = it.status,
                keterangan = it.keterangan,
            )
        }
    }

    fun responseToMatkul(data: List<MatkulData>): List<Matkul> {
        return data.map {
            Matkul(
                id_mk = it.id_mk,
                namaMatkul = it.nama_mk,
                day = it.hari,
                tanggal = it.tanggal,
            )
        }
    }

    fun meetToEntity(it: Matkul): JadwalEntity {
        return JadwalEntity(
            id_mk = it.id_mk.toString(),
            nama_mk = it.namaMatkul.toString(),
            hari = it.day.toString(),
            tanggal = it.tanggal.toString(),
        )
    }

    fun entityToMeet(entity: List<JadwalEntity>): List<Matkul> {
        return entity.map {
            Matkul(
                id = it.id.toString(),
                id_mk = it.id_mk,
                namaMatkul = it.nama_mk,
                day = it.hari,
                tanggal = it.tanggal,
            )
        }
    }
}