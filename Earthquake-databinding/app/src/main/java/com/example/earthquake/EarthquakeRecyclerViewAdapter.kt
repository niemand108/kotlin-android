package com.example.earthquake

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.earthquake.databinding.ListItemEarthquakeBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class EarthquakeRecyclerViewAdapter(var mEarthQuakes: List<Earthquake>) :
    RecyclerView.Adapter<EarthquakeRecyclerViewAdapter.ViewHolder>() {
    companion object {
        var TIME_FORMAT = SimpleDateFormat("HH:mm", Locale.US)
        var MAGNITUDE_FORMAT = DecimalFormat("0.0")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding: ListItemEarthquakeBinding =
            ListItemEarthquakeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mEarthQuakes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var earthquake : Earthquake = mEarthQuakes.get(position)
        holder.binding.earthquake = earthquake
        holder.binding.executePendingBindings()
    }

    class ViewHolder:RecyclerView.ViewHolder {
        var binding: ListItemEarthquakeBinding

        constructor(binding: ListItemEarthquakeBinding) : super(binding.root) {
            this.binding = binding
            binding.magnitudeformat = MAGNITUDE_FORMAT
            binding.timeformat = TIME_FORMAT
        }
    }
}
