package com.example.earthquake

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
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

        var toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var fm: FragmentManager = supportFragmentManager

        /*
        if (savedInstanceState == null) {
            var ft: FragmentTransaction = fm.beginTransaction()
            mEarthquakeListFragment = EarthquakeListFragment()
            ft.add(R.id.main_activity_frame, mEarthquakeListFragment, TAG_LIST_FRAGMENT)
            ft.commitNow()
        } else {
            mEarthquakeListFragment = fm.findFragmentByTag(TAG_LIST_FRAGMENT) as EarthquakeListFragment
        }
        */

        var viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager?.let {
            var pageAdapter = EarthquakeTabsPageAdapter(supportFragmentManager, this)
            viewPager.adapter = pageAdapter

            var tabLayout: TabLayout = findViewById(R.id.tab_layout)
            tabLayout.setupWithViewPager(viewPager)
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
        menuInflater.inflate(R.menu.options_menu, menu)

        var searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchableInfo = searchManager.getSearchableInfo(
            ComponentName(applicationContext, EarthquakeSearchResultActivity::class.java)
        )
        var searchView:SearchView = menu!!.findItem(R.id.search_view).actionView as SearchView
        searchView.setSearchableInfo(searchableInfo)
        searchView.setIconifiedByDefault(false)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.settings_menu_item ->{
                var intent: Intent = Intent(this, PreferencesActivity::class.java)
                startActivityForResult(intent, SHOW_PREFERENCES)
                return true
            }
        }
        return false
    }
}