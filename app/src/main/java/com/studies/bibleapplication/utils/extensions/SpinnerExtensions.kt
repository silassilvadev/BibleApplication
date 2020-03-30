package com.studies.bibleapplication.utils.extensions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

fun Spinner.onItemSelectedListener(onItemSelected: ((AdapterView<*>?, View?, Int, Long) -> Unit)? = null,
                                   onNothingSelected: ((AdapterView<*>?) -> Unit)? = null){
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected?.invoke(parent, view, position, id)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            onNothingSelected?.invoke(parent)
        }
    }
}