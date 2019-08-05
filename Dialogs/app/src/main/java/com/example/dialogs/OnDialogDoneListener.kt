package com.example.dialogs

interface OnDialogDoneListener {
    fun onDialogDone(tag: String?, cancelled: Boolean, message:CharSequence)
}