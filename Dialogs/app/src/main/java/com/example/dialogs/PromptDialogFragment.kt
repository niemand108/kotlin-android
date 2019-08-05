package com.example.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction

class PromptDialogFragment : DialogFragment(), View.OnClickListener {
    private lateinit var editT: EditText
    companion object {
        fun newInstance(prompt: String): PromptDialogFragment {
            var pdf : PromptDialogFragment = PromptDialogFragment()
            var bundle: Bundle = Bundle()
            bundle.putString("prompt", prompt)
            pdf.arguments = bundle
            return pdf
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.isCancelable = true
        var style = DialogFragment.STYLE_NORMAL
        var theme = 0
        setStyle(style, theme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        var v: View = inflater.inflate(R.layout.prompt_dialog, container, false)

        var tv: TextView = v.findViewById(R.id.promptmessage)
        tv.text = arguments?.getString("prompt")

        var saveButton: Button = v.findViewById(R.id.btn_save)
        saveButton.setOnClickListener(this)

        var helpButton : Button = v.findViewById(R.id.btn_help)
        helpButton.setOnClickListener(this)

        var dismissButton : Button = v.findViewById(R.id.btn_dismiss)
        dismissButton.setOnClickListener(this)

        editT = v.findViewById(R.id.inputtext)
        editT.setText(savedInstanceState?.getCharSequence("input"))

        return v
    }

    override fun onClick(v: View?) {
        var act = activity as OnDialogDoneListener
        when(v!!.id) {
            R.id.btn_save -> {
                var tv:TextView? = view!!.findViewById(R.id.inputtext)
                act.onDialogDone(this.tag, false, tv!!.text)
                dismiss()
                return
            }
            R.id.btn_dismiss -> {
                act.onDialogDone(this.tag, true, "")
            }
            R.id.btn_help -> {
                var ft:FragmentTransaction = fragmentManager?.beginTransaction() ?: return
                ft.remove(this)
                ft.addToBackStack(null)
                var hdf = HelpDialogFragment.newInstance(R.string.help1)
                hdf.show(ft, MainActivity.HELP_DIALOG_TAG)
            }
        }
    }
}
