package com.example.earthquake

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class EarthquakeRecyclerViewAdapter(var mEarthQuakes: List<Earthquake>) :
    RecyclerView.Adapter<EarthquakeRecyclerViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v:View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_earthquake, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mEarthQuakes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.earquake = mEarthQuakes[position]
        holder.detailsView.text = mEarthQuakes[position].toString()
    }

    inner class ViewHolder:RecyclerView.ViewHolder {
        var parentView:View
        var detailsView:TextView
        lateinit var earquake:Earthquake

        constructor(v: View) : super(v) {
            parentView = v
            detailsView = v.findViewById(R.id.list_item_earthquake_details)
        }

        override fun toString(): String {
            return super.toString() + "'" + detailsView.text + "'"
        }
    }
}
