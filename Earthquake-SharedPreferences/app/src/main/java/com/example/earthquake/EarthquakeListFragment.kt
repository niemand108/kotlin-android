package com.example.earthquake

import android.content.Context
import android.content.SharedPreferences
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EarthquakeListFragment : Fragment() {

    private var mEarthquakes = ArrayList<Earthquake>()
    private var mEarthquakeAdapter =  EarthquakeRecyclerViewAdapter(mEarthquakes)
    private lateinit var mRecyclerView: RecyclerView
    protected lateinit var earthquakeViewModel: EarthquakeViewModel
    private var mMininumMagnitude: Int = 0
    private lateinit var mSwipeToRefreshView: SwipeRefreshLayout
    private var mListener:OnListFragmentInteractionListener? = null

    private var mPrefListener = object: SharedPreferences.OnSharedPreferenceChangeListener{
        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            if(PreferencesActivity.PREF_MIN_MAG.equals(key))  {
                var earthquakes: List<Earthquake>? = earthquakeViewModel.getEarthquakes().value
                if(earthquakes != null)
                    setEarthquakes(earthquakes as ArrayList<Earthquake>)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v:View = inflater.inflate(R.layout.fragment_earthquake_list, container, false)
        mRecyclerView = v.findViewById(R.id.list)
        mSwipeToRefreshView = v.findViewById(R.id.swiperefresh)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView.adapter = mEarthquakeAdapter
        mSwipeToRefreshView.setOnRefreshListener { updateEarthquakes() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.registerOnSharedPreferenceChangeListener(mPrefListener)
        earthquakeViewModel = ViewModelProviders.of(activity!!).get(EarthquakeViewModel::class.java)
        earthquakeViewModel.getEarthquakes().observe(this, Observer {
            if (it != null)
                setEarthquakes(it as ArrayList<Earthquake>)
        })
    }

    fun setEarthquakes(earthQuakes: ArrayList<Earthquake>) {
        updateFromPreferences()
        for (e in earthQuakes) {
            if(e.magnitude >= mMininumMagnitude) {
                if (!mEarthquakes.contains(e)) {
                    mEarthquakes.add(e)
                    mEarthquakeAdapter.notifyItemInserted(mEarthquakes.indexOf(e))
                }
            }
        }

        if(mEarthquakes != null && mEarthquakes.size > 0){
            for(i in mEarthquakes.size-1 downTo 0) {
                if(mEarthquakes[i].magnitude < mMininumMagnitude) {
                    mEarthquakes.remove(mEarthquakes[i])
                    mEarthquakeAdapter.notifyItemRemoved(i)
                }
            }
        }
        mSwipeToRefreshView.isRefreshing = false
    }

    private fun updateEarthquakes () {
        mListener?.onListFragmentRefreshRequest()
    }

    private fun updateFromPreferences(): Unit {
        var prefs : SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        mMininumMagnitude = Integer.parseInt(prefs.getString(PreferencesActivity.PREF_MIN_MAG, "3").toString())
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mListener = context as OnListFragmentInteractionListener
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }
}
