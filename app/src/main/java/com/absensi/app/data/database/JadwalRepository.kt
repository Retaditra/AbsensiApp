package com.absensi.app.data.database

import android.content.Context
import com.absensi.app.utils.executeThread

class JadwalRepository(private val dao: JadwalDao) {
    fun insertAll(schedule: MutableList<JadwalEntity>) {
        executeThread { dao.insertAll(schedule) }
    }

    fun getJadwal(): List<JadwalEntity> {
        return dao.getJadwal()
    }

    fun getByDay(day: String): List<JadwalEntity> {
        return dao.getByDay(day)
    }

    fun deleteAll() {
        executeThread { dao.deleteAll() }
    }

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
