package com.example.earthquake

import androidx.room.*

@Database(entities = [Earthquake::class], version = 1)
@TypeConverters(EarthquakeTypeConverters::class)
abstract class EarthquakeDatabase : RoomDatabase() {
    abstract fun earthquakeDAO(): EarthquakeDAO
}