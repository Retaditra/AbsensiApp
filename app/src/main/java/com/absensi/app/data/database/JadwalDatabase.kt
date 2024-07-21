package com.absensi.app.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [JadwalEntity::class], version = 1)
abstract class JadwalDatabase : RoomDatabase() {

    abstract fun jadwalDao(): JadwalDao

    companion object {
        @Volatile
        private var instance: JadwalDatabase? = null

        fun getInstance(context: Context): JadwalDatabase {
            return synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context,
                    JadwalDatabase::class.java,
                    "jadwal.db"
                )
                    .build()
            }
        }
    }
}

