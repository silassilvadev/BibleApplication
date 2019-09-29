package com.studies.catholicbible.extensions

import com.studies.catholicbible.R
import com.studies.catholicbible.model.CatholicBibleApplication.Companion.application

fun String.iteratorChapters(): ArrayList<String> {
    val listChapters = ArrayList<String>()
    for (item in 1..this.toByte()){
        listChapters.add(application.getString(R.string.st_chapter_item_spinner_toolbar, item.toString()))
    }
    return listChapters
}