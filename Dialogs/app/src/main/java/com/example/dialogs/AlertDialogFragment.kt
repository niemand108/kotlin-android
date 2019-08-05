package com.example.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class AlertDialogFragment : DialogFragment(), DialogInterface.OnClickListener {

    companion object {
        fun newInstance(message:String):AlertDialogFragment {
            var adf = AlertDialogFragment()
            var bundle = Bundle()
            adf.arguments = bundle
            return adf
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.isCancelable = true
        var style = DialogFragment.STYLE_NORMAL
        var theme = 0
        setStyle(style, theme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        var b = AlertDialog.Builder(activity)
            .setTitle("Alert!")
            .setPositiveButton("Ok", this)
            .setNegativeButton("Cancel", this)
            .setMessage(arguments?.getString("alert-message"))
        return b.create()
    }
    /*
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        //var test = activity as OnDialogDoneListener
    }*/

    override fun onClick(p0: DialogInterface?, which: Int) {
        isCancelable = false
        if (which == AlertDialog.BUTTON_NEGATIVE)
            isCancelable = true
        (activity as OnDialogDoneListener).onDialogDone(tag, isCancelable, "Alert dismissed")
    }
}
