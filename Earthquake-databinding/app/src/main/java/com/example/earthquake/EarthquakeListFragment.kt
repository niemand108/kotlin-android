package com.example.earthquake

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EarthquakeListFragment : Fragment() {
    private var mEarthquakes = ArrayList<Earthquake>()
    private var mEarthquakeAdapter =  EarthquakeRecyclerViewAdapter(mEarthquakes)
    private lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v:View = inflater.inflate(R.layout.fragment_earthquake_list, container, false)
        mRecyclerView = v.findViewById(R.id.list)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var ctx: Context = view.context
        mRecyclerView.layoutManager = LinearLayoutManager(ctx)
        mRecyclerView.adapter = mEarthquakeAdapter
    }

    fun setEarthquakes(earthQuakes: ArrayList<Earthquake>) {
        for (e in earthQuakes) {
            if(!mEarthquakes.contains(e)) {
                mEarthquakes.add(e)
                mEarthquakeAdapter.notifyItemInserted(mEarthquakes.indexOf(e))
            }
        }

    }

}
