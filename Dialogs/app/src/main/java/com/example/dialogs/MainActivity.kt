package com.example.dialogs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), OnDialogDoneListener {

    companion object{
        val LOGTAG = "D"
        val ALERT_DIALOG_TAG = "ALERT_DIALOG_TAG"
        val HELP_DIALOG_TAG = "HELP_DIALOG_TAG"
        val PROMPT_DIALOG_TAG = "PROMPT_DIALOG_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when(item.itemId){
            R.id.menu_show_alert_dialog -> testAlertDialog()
            R.id.menu_show_prompt_dialog -> testPromptDialog()
            R.id.menu_help -> testHelpDialog()
        }
        return true
    }

    private fun testHelpDialog() {
        var ft: FragmentTransaction? = supportFragmentManager.beginTransaction()
        var hdf = HelpDialogFragment.newInstance(R.string.help_text)
        hdf.show(ft, PROMPT_DIALOG_TAG)
    }

    private fun testPromptDialog() {
        var ft: FragmentTransaction? = supportFragmentManager.beginTransaction()
        var pdf = PromptDialogFragment.newInstance("Enter Message")
        pdf.show(ft, PROMPT_DIALOG_TAG)

    }

    private fun testAlertDialog() {
        var ft: FragmentTransaction? = supportFragmentManager.beginTransaction()
        var adf = AlertDialogFragment.newInstance("Alert Message")
        adf.show(ft, ALERT_DIALOG_TAG)

    }

    override fun onDialogDone(tag: String?, cancelled: Boolean, message: CharSequence) {
        var s = "$tag responds with: $message"
        if (cancelled)
            s = "$tag was cancelled by the user"
        else{
            var tv:TextView = findViewById(R.id.promptmessage)
            if (tv.text.toString() == "Enter Text")
                tv.text = "$message\n"
            else
                tv.text = tv.text.toString() + "$message\n"
        }
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }
}
