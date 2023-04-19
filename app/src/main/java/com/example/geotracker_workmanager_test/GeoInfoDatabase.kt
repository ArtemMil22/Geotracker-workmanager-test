package com.example.geotracker_workmanager_test

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GeoInfo::class], version = 1, exportSchema = false)

abstract class GeoInfoDatabase : RoomDatabase() {

    abstract fun wordDao(): GeoInfoDataAccessObject

    companion object {
        @Volatile
        private var INSTANCE: GeoInfoDatabase? = null
        fun getDatabase(context: Context): GeoInfoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GeoInfoDatabase::class.java,
                    "geo_info_database"
                ).allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}