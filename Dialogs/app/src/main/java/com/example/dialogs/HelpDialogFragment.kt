package com.example.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class HelpDialogFragment: DialogFragment(), View.OnClickListener {

    companion object {
        fun newInstance(helpResId: Int) : HelpDialogFragment {
            var hdf = HelpDialogFragment()
            var bundle = Bundle()
            bundle.putInt("help_resource", helpResId)
            hdf.arguments = bundle
            return hdf
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var v = inflater.inflate(R.layout.help_dialog, container, false)
        var tv: TextView = v.findViewById(R.id.helpmessage)
        tv.text = activity?.resources?.getText(arguments?.getInt("help_resource")!!)
        var closeButton : Button = v.findViewById(R.id.btn_close)
        closeButton.setOnClickListener(this)
        return v
    }

    override fun onClick(p0: View?) {
        dismiss()
    }
}
