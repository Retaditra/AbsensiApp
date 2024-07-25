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

    @Query("SELECT * FROM JadwalEntity WHERE hari = :hari")
    fun getByDay(hari: String): List<JadwalEntity>
}

