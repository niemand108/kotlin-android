package com.example.earthquake

import android.location.Location
import java.text.SimpleDateFormat
import java.util.*

class Earthquake(
    var id:String,
    var date: Date,
    var details:String,
    var location: Location?,
    var magnitude: Double,
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
