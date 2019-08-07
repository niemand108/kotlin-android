package com.example.earthquake

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import java.util.*
import kotlin.collections.ArrayList

class EarthquakeMainActivity : AppCompatActivity() {
    companion object {
        private var TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT"
    }

    private lateinit var mEarthquakeListFragment:EarthquakeListFragment
    private lateinit var earthquakeViewModel: EarthquakeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earthquake_main)

        var fm: FragmentManager = supportFragmentManager

        if (savedInstanceState == null) {
            var ft: FragmentTransaction = fm.beginTransaction()
            mEarthquakeListFragment = EarthquakeListFragment()
            ft.add(R.id.main_activity_frame, mEarthquakeListFragment, TAG_LIST_FRAGMENT)
            ft.commitNow()
        } else {
            mEarthquakeListFragment = fm.findFragmentByTag(TAG_LIST_FRAGMENT) as EarthquakeListFragment
        }

        var now:Date = Calendar.getInstance().time
        var dummyQuakes = ArrayList<Earthquake>(0)

        earthquakeViewModel = ViewModelProviders.of(this).get(EarthquakeViewModel::class.java)
    }
}