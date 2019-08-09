package com.example.earthquake

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EarthquakeSearchResultActivity : AppCompatActivity(), LifecycleOwner  {
    private var mEarthquakes = ArrayList<Earthquake>(0)
    private var mEarthquakeAdapter: EarthquakeRecyclerViewAdapter = EarthquakeRecyclerViewAdapter(mEarthquakes)
    private lateinit var searchQuery: MutableLiveData<String>
    private lateinit var searchResults: LiveData<List<Earthquake>>
    private lateinit var selectedSearchSuggestionId: MutableLiveData<String>
    private lateinit var selectedSearchSuggestion: LiveData<Earthquake>
    private lateinit var lifecycleRegistry: LifecycleRegistry

    private val selectedSearchSuggestionObserver : Observer<Earthquake> = Observer {
        it?.let { setSearchQuery(it.details) }
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }

    private fun setSearchQuery(query: String) {
        searchQuery.value = query
    }

    private var searchQueryResultObserver: Observer<List<Earthquake>> =
        Observer {
                    mEarthquakes.clear()
                    it?.let { mEarthquakes.addAll(it) }
                    mEarthquakeAdapter.notifyDataSetChanged()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleRegistry = LifecycleRegistry(this)
        setContentView(R.layout.activity_earthquake_search_result)

        var recyclerViewAdapter: RecyclerView = findViewById(R.id.search_result_list)
        recyclerViewAdapter.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter.adapter = mEarthquakeAdapter

        searchQuery = MutableLiveData()
        searchQuery.value = null

        searchResults = Transformations.switchMap(searchQuery) { query ->
            EarthquakeDatabaseAccesor.getInstance(applicationContext)
                .earthquakeDAO()
                .searchEarthquakes("%$query%")
        }
        searchResults.observe(this, searchQueryResultObserver)

        selectedSearchSuggestionId = MutableLiveData()
        selectedSearchSuggestionId.value = null
        selectedSearchSuggestion = Transformations.switchMap(selectedSearchSuggestionId) {
            id -> EarthquakeDatabaseAccesor
            .getInstance(applicationContext)
            .earthquakeDAO()
            .getEarthquake(id)
        }

        if(Intent.ACTION_VIEW == intent.action) {
            selectedSearchSuggestion.observe(this, selectedSearchSuggestionObserver)
            intent.data?.let { setSelectedSearchSuggestion(it) }
        } else {
            var query: String = intent.getStringExtra(SearchManager.QUERY)
            setSearchQuery(query)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        if(Intent.ACTION_VIEW == intent?.action) {
            intent!!.data?.let { setSelectedSearchSuggestion(it) }
        } else {
            var query: String? = intent?.getStringExtra(SearchManager.QUERY)
            setSearchQuery(query!!)
        }
    }

    private fun setSelectedSearchSuggestion(dataString: Uri) {
        if(dataString.pathSegments.size > 1) {
            var id: String = dataString.pathSegments[1]
            selectedSearchSuggestionId.value = id
        } else throw ArrayIndexOutOfBoundsException("mmmm")
    }


}