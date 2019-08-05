package com.example.fragments

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.logging.Logger

class MainActivity : FragmentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FragmentManager.enableDebugLogging(true)
        setContentView(R.layout.activity_main)
    }

    fun showDetails(index: Int) {
        if(isMultipane()) {
            var details: DetailsFragment? = supportFragmentManager.findFragmentById(R.id.details) as DetailsFragment
            if (details == null || details.mIndex != index) {
                details = DetailsFragment.newInstance(index)
                var ft: FragmentTransaction = supportFragmentManager.beginTransaction().apply {
                    setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                    replace(R.id.details, details)
                    commit()
                }
            }
        } else {
                val Log = Logger.getLogger(MainActivity::class.java.name)
                Log.info("showDetails")
            var intent = Intent().also {
                it.setClass(this, DetailsActivity::class.java)
                it.putExtra("index", index)
                startActivity(it)
            }
        }
    }

    private fun isMultipane() : Boolean {
        return baseContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }
}
