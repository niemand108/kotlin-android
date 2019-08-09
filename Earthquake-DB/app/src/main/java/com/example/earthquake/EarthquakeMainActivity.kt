package com.example.earthquake

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import java.util.*
import kotlin.collections.ArrayList

class EarthquakeMainActivity : AppCompatActivity(), OnListFragmentInteractionListener{
    companion object {
        private var SHOW_PREFERENCES = 1
        private var TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT"
        private var MENU_PREFERENCES = Menu.FIRST + 1
        private var MENU_LOAD_DB = Menu.FIRST + 2
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
        earthquakeViewModel = ViewModelProviders.of(this).get(EarthquakeViewModel::class.java)
    }

    override fun onListFragmentRefreshRequest() {
        updateEarthquakes()
    }

    private fun updateEarthquakes() {
        earthquakeViewModel.loadEarthquakes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menu?.add(0, MENU_PREFERENCES, Menu.NONE, R.string.menu_settings)
        menu?.add(1, MENU_LOAD_DB, Menu.NONE, "Load DB")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId){
            MENU_PREFERENCES -> {
                var i: Intent = Intent(this, PreferencesActivity::class.java)
                startActivityForResult(i, SHOW_PREFERENCES)
                return true
            }
            MENU_LOAD_DB -> {
                earthquakeViewModel.loadDBEarthquakes()
            }
        }
        return false
    }
}