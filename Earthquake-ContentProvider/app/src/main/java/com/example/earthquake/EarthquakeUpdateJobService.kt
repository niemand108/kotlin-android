package com.example.earthquake

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.util.Log
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import com.firebase.jobdispatcher.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.xml.sax.SAXException
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import kotlin.collections.ArrayList

class EarthquakeUpdateJobService : SimpleJobService() {
    companion object {
        const val TAG = "EarthquakeUpdatedJob"
        const val UPDATE_JOB_TAG = "update_job"
        const val PERIODIC_JOB_TAG = "periodic_job"

        fun scheduleUpdateJob(ctx: Context){
            var jobDispatcher = FirebaseJobDispatcher(GooglePlayDriver(ctx))
            jobDispatcher.schedule(jobDispatcher.newJobBuilder()
                .setTag(UPDATE_JOB_TAG)
                .setService(EarthquakeUpdateJobService::class.java)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build())
        }
    }

    override fun onRunJob(job: JobParameters): Int {
        var earthquakes_ = ArrayList<Earthquake>(0)
        var url = URL(getString(R.string.earthquake_feed))
        try {

            var httpconnection = url.openConnection() as HttpURLConnection
            var responseCode = httpconnection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                var input: InputStream = httpconnection.inputStream
                var dbf = DocumentBuilderFactory.newInstance()
                var db: DocumentBuilder = dbf.newDocumentBuilder()

                var dom: Document = db.parse(input)
                var docEl: Element = dom.documentElement

                var nl: NodeList = docEl.getElementsByTagName("entry")
                if (nl != null && nl.length > 0) {
                    for (i in 0..nl.length - 1) {
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
                        var qdate = GregorianCalendar(0, 0, 0).time
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

                        val earthquake: Earthquake = Earthquake(
                            idString, qdate, details,
                            l, magnitude, link_string
                        )

                        earthquakes_.add(earthquake)

                    }
                }
            }

            httpconnection.disconnect()

            EarthquakeDatabaseAccesor
                .getInstance(applicationContext)
                .earthquakeDAO()
                .insertEarthquakes(earthquakes_)

            scheduleNextUpdate(this, job)

            return JobService.RESULT_SUCCESS
        } catch (e: MalformedURLException) {
            Log.e(TAG, "Malformed URL exception", e)
            return JobService.RESULT_FAIL_NORETRY
        } catch (e: IOException) {
            Log.e(TAG, "IOException", e)
            return JobService.RESULT_FAIL_RETRY
        } catch (e: ParserConfigurationException) {
            Log.e(TAG, "Parser confgiguration exception", e)
            return JobService.RESULT_FAIL_NORETRY
        } catch (e: SAXException) {
            Log.e(TAG, "SAX Exception", e)
            return JobService.RESULT_FAIL_NORETRY
        }
    }

    private fun scheduleNextUpdate(ctx: Context, job: JobParameters) {
        if(job.tag == UPDATE_JOB_TAG){
            var pref : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

            var updateFreq: Int = Integer.parseInt( pref.getString(PreferencesActivity.PREF_UPDATE_FREQ, "60").toString() )

            var autoUpdateChecked : Boolean = pref.getBoolean(PreferencesActivity.PREF_AUTO_UPDATE, false)

            if(autoUpdateChecked){
                var jobDispatcher: FirebaseJobDispatcher = FirebaseJobDispatcher(GooglePlayDriver(ctx))
                jobDispatcher.schedule(jobDispatcher.newJobBuilder()
                    .setTag(PERIODIC_JOB_TAG)
                    .setService(EarthquakeUpdateJobService::class.java)
                    .setConstraints(Constraint.ON_ANY_NETWORK)
                    .setReplaceCurrent(true)
                    .setRecurring(true)
                    .setTrigger(Trigger.executionWindow( updateFreq * 60 / 2, updateFreq * 60))
                    .setLifetime(Lifetime.FOREVER)
                    .build())
            }
        }
    }
}