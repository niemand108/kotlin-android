package com.example.earthquake

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class EarthquakeTabsPageAdapter(var fm: FragmentManager, var act: Activity) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        when(position){
            0 -> return EarthquakeListFragment()
            1 -> return EarthquakeMapFragment()
            else -> return null
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return act.getString(R.string.tab_list)
            1 -> return act.getString(R.string.tab_map)
            else -> return null
        }
    }
}