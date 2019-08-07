package com.example.earthquake

import android.app.Application
import android.content.Context
import android.location.Location
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import kotlin.collections.ArrayList

class EarthquakeViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        private const val TAG = "EarthquakeUpdate"
    }
    protected var earthquakes =  MutableLiveData<List<Earthquake>>()

    fun getEarthquakes() : LiveData<List<Earthquake>> {
        earthquakes = MutableLiveData<List<Earthquake>>()
        loadEarthquakes()
        return earthquakes
    }

    fun loadEarthquakes() {
        class asyncEarthQuakes() : AsyncTask<Unit, Unit, List<Earthquake>>() {
            override fun doInBackground(vararg p0: Unit?): List<Earthquake> {
                var earthquakes_ = ArrayList<Earthquake>(0)
                var url = URL(getApplication<Application>().getString(R.string.earthquake_feed))

                var httpconnection = url.openConnection() as HttpURLConnection
                var responseCode = httpconnection.responseCode

                if(responseCode == HttpURLConnection.HTTP_OK) {
                    var input:InputStream = httpconnection.inputStream
                    var dbf = DocumentBuilderFactory.newInstance()
                    var db: DocumentBuilder = dbf.newDocumentBuilder()

                    var dom: Document = db.parse(input)
                    var docEl: Element = dom.documentElement

                    var nl: NodeList = docEl.getElementsByTagName("entry")
                    if(nl != null && nl.length > 0) {
                        for(i in 0..nl.length-1){
                            if(isCancelled){
                                Log.d(TAG, "Loading Cancelled")
                                return earthquakes_
                            }
                            var entry: Element = nl.item(i) as Element
                            var id: Element = entry.getElementsByTagName("id").item(0) as Element
                            var title: Element = entry.getElementsByTagName("title").item(0) as Element
                            var g: Element = entry.getElementsByTagName("georss:point").item(0) as Element
                            var when_: Element = entry.getElementsByTagName("updated").item(0) as Element
                            var link: Element = entry.getElementsByTagName("link").item(0) as Element

                            var idString: String = id.firstChild.nodeValue
                            var details: String = title.firstChild.nodeValue
                            var hostname: String = "http://earthquake.usgs.gov"
                            var link_string: String = hostname + link.getAttribute("href")
                            var point: String = g.firstChild.nodeValue
                            var dt: String = when_.firstChild.nodeValue

                            var sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'")
                            var qdate = GregorianCalendar(0,0,0).time
                            qdate = sdf.parse(dt)

                            var location: List<String> = point.split(" ")
                            var l: Location = Location("dummyGPS")
                            l.latitude = java.lang.Double.parseDouble(location[0])
                            l.longitude = java.lang.Double.parseDouble(location[1])

                            var magnitudeString = details.split(" ")[1]
                            var end = magnitudeString.length - 1
                            var magnitude = java.lang.Double.parseDouble(magnitudeString.substring(0, end))

                            if (details.contains("-"))
                                details = details.split("-")[1].trim()
                            else
                                details = ""

                            val earthquake: Earthquake = Earthquake(idString, qdate, details,
                                l, magnitude, link_string)

                            earthquakes_.add(earthquake)

                        }
                    }
                }
                return earthquakes_
            }

            override fun onPostExecute(result: List<Earthquake>?) {
                earthquakes?.let{ it.value = result }
            }
        }
        asyncEarthQuakes().execute()

    }
}