package com.example.earthquake

import android.location.Location
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity
class Earthquake(
    @NonNull
    @PrimaryKey
    var id:String,
    @ColumnInfo(name = "date")
    var date: Date,
    @ColumnInfo(name = "details")
    var details:String,
    @ColumnInfo(name = "location")
    var location: Location?,
    @ColumnInfo(name = "magnitude")
    var magnitude: Double,
    @ColumnInfo(name = "link")
    var link: String?
) {

    override fun toString(): String {
        var sdf: SimpleDateFormat = SimpleDateFormat("HH.mm", Locale.US)
        var dateString: String = sdf.format(date)
        return "$dateString: $magnitude $details"
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Earthquake) {
            other.id.contentEquals(id)
        } else
            false
    }
}
