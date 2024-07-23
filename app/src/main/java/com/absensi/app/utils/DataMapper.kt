package com.absensi.app.utils

import com.absensi.app.data.Jadwal
import com.absensi.app.data.Pertemuan
import com.absensi.app.data.database.JadwalEntity
import com.absensi.app.data.respone.JadwalData
import com.absensi.app.data.respone.MatkulData
import com.absensi.app.data.respone.PertemuanData

class DataMapper {
    fun responseToPertemuan(data: List<PertemuanData>): List<Pertemuan> {
        return data.map {
            Pertemuan(
                id = it.id,
                id_mk = it.id_mk,
                namaMatkul = it.nama_mk,
                pertemuan_ke = it.pertemuan_ke,
                hari = it.hari,
                tanggal = it.tanggal,
                waktu = it.waktu,
                namaDosen = it.nama_dsn,
                kode_absensi = it.kode_absensi,
                status = it.status,
                keterangan = it.keterangan,
            )
        }
    }

    fun responseToJadwal(data: List<JadwalData>): List<Jadwal> {
        return data.map {
            Jadwal(
                idMk = it.id,
                namaMatkul = it.nama_mk,
                namaDosen = it.nama_dsn,
                semester = it.semester,
                pertemuan_ke = it.pertemuan_ke,
                hari = it.hari,
                tanggal = it.tanggal,
            )
        }
    }

    fun responseToMatkul(data: List<MatkulData>): List<Jadwal> {
        return data.map {
            Jadwal(
                idMk = it.id,
                namaMatkul = it.nama_mk,
                namaDosen = it.nama_dsn,
                semester = it.semester,
            )
        }
    }

    fun jadwalToEntity(it: Jadwal): JadwalEntity {
        return JadwalEntity(
            id_mk = it.idMk,
            nama_mk = it.namaMatkul.toString(),
            nama_dsn = it.namaDosen.toString(),
            semester = it.semester.toString(),
            pertemuan_ke = it.pertemuan_ke.toString(),
            hari = it.hari.toString(),
            tanggal = it.tanggal.toString(),
        )
    }

    fun entityToJadwal(entity: List<JadwalEntity>): List<Jadwal> {
        return entity.map {
            Jadwal(
                id = it.id,
                idMk = it.id_mk,
                namaMatkul = it.nama_mk,
                namaDosen = it.nama_dsn,
                semester = it.semester,
                pertemuan_ke = it.pertemuan_ke,
                hari = it.hari,
                tanggal = it.tanggal,
            )
        }
    }
}