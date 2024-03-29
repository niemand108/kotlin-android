package com.example.earthquake

import android.content.Context
import androidx.room.Room

class EarthquakeDatabaseAccesor {
    companion object {
        private var EarthquakeDatabaseInstance: EarthquakeDatabase? = null
        private val LOCK = Any()
        private const val EARTHQUAKE_DB_NAME = "earthquake_db"

        fun getInstance(context: Context): EarthquakeDatabase {
            EarthquakeDatabaseInstance?.let { return it } ?: run {
                synchronized(LOCK) {
                    EarthquakeDatabaseInstance =
                        Room.databaseBuilder(context, EarthquakeDatabase::class.java, EARTHQUAKE_DB_NAME).build()
                }
            }
            return EarthquakeDatabaseInstance!!
        }
    }
}