package com.absensi.app.data.database

import android.content.Context
import com.absensi.app.utils.executeThread

class JadwalRepository(private val dao: JadwalDao) {

    fun getJadwal(): List<JadwalEntity> {
        return dao.getJadwal()
    }

    fun insertAll(schedule: MutableList<JadwalEntity>) {
        executeThread { dao.insertAll(schedule) }
    }

    fun deleteAll() {
        executeThread { dao.deleteAll() }
    }

//    fun getSenin(): List<JadwalEntity> {
//        return dao.getSenin()
//    }
//
//    fun getSelasa(): List<JadwalEntity> {
//        return dao.getSelasa()
//    }
//
//    fun getRabu(): List<JadwalEntity> {
//        return dao.getRabu()
//    }
//
//    fun getKamis(): List<JadwalEntity> {
//        return dao.getKamis()
//    }
//
//    fun getJumat(): List<JadwalEntity> {
//        return dao.getJumat()
//    }

    companion object {
        @Volatile
        private var instance: JadwalRepository? = null
        fun getInstance(context: Context): JadwalRepository {
            return instance ?: synchronized(JadwalRepository::class.java) {
                if (instance == null) {
                    val database = JadwalDatabase.getInstance(context)
                    instance = JadwalRepository(database.jadwalDao())
                }
                return instance as JadwalRepository
            }
        }
    }
}
