package com.absensi.app.data.database

import androidx.room.*
import java.util.*

@Dao
interface JadwalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(matkul: List<JadwalEntity>)

    @Query("DELETE FROM JadwalEntity")
    fun deleteAll()

    @Query("SELECT * FROM JadwalEntity")
    fun getJadwal(): List<JadwalEntity>

//    @Query("SELECT * FROM JadwalEntity WHERE hari = senin")
//    fun getSenin(): List<JadwalEntity>
//
//    @Query("SELECT * FROM JadwalEntity WHERE hari = selasa")
//    fun getSelasa(): List<JadwalEntity>
//
//    @Query("SELECT * FROM JadwalEntity WHERE hari = rabu")
//    fun getRabu(): List<JadwalEntity>
//
//    @Query("SELECT * FROM JadwalEntity WHERE hari = kamis")
//    fun getKamis(): List<JadwalEntity>
//
//    @Query("SELECT * FROM JadwalEntity WHERE hari = jumat")
//    fun getJumat(): List<JadwalEntity>

}

