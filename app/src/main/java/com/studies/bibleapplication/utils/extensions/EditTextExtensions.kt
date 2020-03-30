package com.studies.bibleapplication.utils.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onTextChangedListener(onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null,
                                   afterTextChanged: ((String) -> Unit)? = null,
                                   beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(charText: CharSequence?, start: Int, before: Int, count: Int) {
            beforeTextChanged?.invoke(charText, start, before, count)
        }

        override fun onTextChanged(charText: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged?.invoke(charText, start, before, count)
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged?.invoke(editable.toString())
        }
    })
}
