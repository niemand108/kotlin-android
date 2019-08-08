package com.example.earthquake

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EarthquakeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEarthquakes(earthquakes: List<Earthquake>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEarthquake(earthquake: Earthquake)

    @Delete
    fun deleteEarthquake(earthquake: Earthquake)

    @Query("SELECT * FROM earthquake ORDER BY date DESC")
    fun loadAllEarthquakes(): LiveData<List<Earthquake>>
}