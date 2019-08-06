package com.example.earthquake

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class EarthquakeRecyclerViewAdapter(var mEarthQuakes: List<Earthquake>) :
    RecyclerView.Adapter<EarthquakeRecyclerViewAdapter.ViewHolder>() {

    companion object {
        var TIME_FORMAT = SimpleDateFormat("HH:mm", Locale.US)
        var MAGNITUDE_FORMAT = DecimalFormat("0.0")
    }
    lateinit var mAdapterItemClickListener: IAdapterItemClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v:View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_earthquake, parent, false)
        return ViewHolder(v, null)
    }

    override fun getItemCount(): Int {
        return mEarthQuakes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var earthquake : Earthquake = mEarthQuakes.get(position)
        holder.date.text = TIME_FORMAT.format(earthquake.date)
        holder.details.text = earthquake.details
        holder.magnitude.text = MAGNITUDE_FORMAT.format(earthquake.magnitude)

        holder.mListener = View.OnClickListener {
            if(mAdapterItemClickListener != null)
                mAdapterItemClickListener.onItemClicked(earthquake.details)
        }
    }

    fun setOnAdapterItemClick(adapterItemClick: IAdapterItemClick){
        mAdapterItemClickListener = adapterItemClick
    }

    class ViewHolder:RecyclerView.ViewHolder, View.OnClickListener{
        var date:TextView
        var details:TextView
        var magnitude:TextView

        var mListener:View.OnClickListener?

        constructor(v: View, listener: View.OnClickListener?) : super(v) {
            date = v.findViewById<TextView>(R.id.date)
            details = v.findViewById<TextView>(R.id.details)
            magnitude = v.findViewById<TextView>(R.id.magnitude)

            mListener = listener
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            mListener?.onClick(v)
        }
    }
}
