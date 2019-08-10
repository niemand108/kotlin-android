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
    private var earthquakes:LiveData<List<Earthquake>> = EarthquakeDatabaseAccesor
        .getInstance(getApplication())
        .earthquakeDAO()
        .loadAllEarthquakes()

        fun getEarthquakes() : LiveData<List<Earthquake>> {
            loadEarthquakes()
            return earthquakes
        }

        fun loadEarthquakes() {
            EarthquakeUpdateJobService.scheduleUpdateJob(getApplication())
        }
    }
