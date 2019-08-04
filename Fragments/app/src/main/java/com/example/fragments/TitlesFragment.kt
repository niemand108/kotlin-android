package com.example.fragments

import android.R
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.ListFragment

class TitlesFragment : ListFragment()
{
    private var myActivity: MainActivity? = null
    private var mCurCheckPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState != null)
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0)
        else
            mCurCheckPosition = 0
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        myActivity = context as MainActivity
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listAdapter = ArrayAdapter<String>(myActivity!!, R.layout.simple_list_item_1, Shakespeare.TITLES)
        var lv:ListView = listView
        lv.choiceMode = ListView.CHOICE_MODE_SINGLE
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        myActivity?.showDetails(position)
        mCurCheckPosition = position
    }

}