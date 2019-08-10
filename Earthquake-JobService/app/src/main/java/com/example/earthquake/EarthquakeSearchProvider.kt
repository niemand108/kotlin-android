package com.example.earthquake

import android.app.SearchManager
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import java.lang.IllegalArgumentException

class EarthquakeSearchProvider : ContentProvider() {
    companion object {
        const val SEARCH_SUGGESTION = 1

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI("com.example.provider.earthquake",
                SearchManager.SUGGEST_URI_PATH_QUERY, SEARCH_SUGGESTION)
            addURI("com.example.provider.earthquake",
                SearchManager.SUGGEST_URI_PATH_QUERY + "/*", SEARCH_SUGGESTION)
            addURI("com.example.provider.earthquake",
                SearchManager.SUGGEST_URI_PATH_SHORTCUT, SEARCH_SUGGESTION)
            addURI("com.example.provider.earthquake",
                SearchManager.SUGGEST_URI_PATH_SHORTCUT + "/*", SEARCH_SUGGESTION)
        }
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun query(uri: Uri, p1: Array<out String>?, p2: String?, p3: Array<out String>?, p4: String?): Cursor? {
        if(uriMatcher.match(uri) == SEARCH_SUGGESTION) {
            var searchQuery: String = "%" + uri.lastPathSegment + "%"
            var earthquakeDAO: EarthquakeDAO =
                EarthquakeDatabaseAccesor.getInstance(context?.applicationContext!!).earthquakeDAO()
            var c: Cursor = earthquakeDAO.generateSearchSuggestion(searchQuery)

            return c
        }
        return null
    }

    override fun onCreate(): Boolean {
        EarthquakeDatabaseAccesor.getInstance(context?.applicationContext!!)
        return true
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        when(uriMatcher.match(uri)){
            SEARCH_SUGGESTION -> return SearchManager.SUGGEST_MIME_TYPE
            else -> throw IllegalArgumentException("Unsupported URI: " + uri.toString())
        }
    }

}