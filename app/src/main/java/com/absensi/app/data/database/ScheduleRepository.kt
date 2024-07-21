package com.absensi.app.data.database

import android.content.Context
import com.absensi.app.data.utils.executeThread

class ScheduleRepository(private val dao: JadwalDao) {

    fun getAllSchedule(): List<JadwalEntity> {
        return dao.getJadwal()
    }

    fun insertAll(schedule: MutableList<JadwalEntity>) {
        executeThread { dao.insertAll(schedule) }
    }

    fun deleteAll() {
        executeThread { dao.deleteAll() }
    }

    fun getSenin(): List<JadwalEntity> {
        return dao.getSenin()
    }

    fun getSelasa(): List<JadwalEntity> {
        return dao.getSelasa()
    }

    fun getRabu(): List<JadwalEntity> {
        return dao.getRabu()
    }

    fun getKamis(): List<JadwalEntity> {
        return dao.getKamis()
    }

    fun getJumat(): List<JadwalEntity> {
        return dao.getJumat()
    }

    companion object {
        @Volatile
        private var instance: ScheduleRepository? = null
        fun getInstance(context: Context): ScheduleRepository {
            return instance ?: synchronized(ScheduleRepository::class.java) {
                if (instance == null) {
                    val database = JadwalDatabase.getInstance(context)
                    instance = ScheduleRepository(database.scheduleDao())
                }
                return instance as ScheduleRepository
            }
        }
    }
}
