package com.example.earthquake

import android.location.Location
import androidx.room.TypeConverter
import java.util.*

class EarthquakeTypeConverters {
    @TypeConverter
    fun dateFromTimestamp(value:Long): Date? {
        value?.let { return Date(it) }
    }

    @TypeConverter
    fun datetoTimestamp(date: Date) : Long? {
        date?.let { return it.time}
    }

    @TypeConverter
    fun locationToString(location: Location): String {
        location?.let { return location.latitude.toString() + "," + location.longitude.toString() }
    }

    @TypeConverter
    fun locationFromString(location: String) : Location? {
        location?.let {
            if(it.contains(",")) {
                var result: Location = Location("Generated")
                var locationStrings : List<String> = it.split(",")
                if (locationStrings.size == 2) {
                    result.latitude = java.lang.Double.parseDouble(locationStrings[0])
                    result.longitude = java.lang.Double.parseDouble(locationStrings[1])
                    return result
                } else
                    return null
            } else
                return null
        }
    }
}