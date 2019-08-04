package com.example.fragments

import android.content.res.Configuration
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity

import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish()
            return
        }

        if(intent != null) {
            var details:DetailsFragment = DetailsFragment.newInstance(intent.extras)
            supportFragmentManager.beginTransaction()
                .add(android.R.id.content, details)
                .commit()
        }
    }

}
