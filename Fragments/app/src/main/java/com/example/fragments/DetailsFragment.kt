package com.example.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_details.*

class DetailsFragment : Fragment() {
    var mIndex: Int = 0
        get() = field
        set(value) {
            field = value
        }

    companion object {
        private var df: DetailsFragment = DetailsFragment()

        fun newInstance(index: Int): DetailsFragment {
            var args = Bundle()
            args.putInt("index", index)
            df.arguments = args
            return df
        }

        fun newInstance(bundle:Bundle?):DetailsFragment {
            var index: Int = 0
            if (bundle != null)
                index = bundle.getInt("index",0)
            return newInstance(index)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mIndex = arguments?.getInt("index",0) ?: 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var v:View = inflater.inflate(R.layout.activity_details, container, false)
        var tv: TextView = v.findViewById(R.id.text1)
        tv.text = Shakespeare.DIALOGUE[mIndex]
        return v
    }
}