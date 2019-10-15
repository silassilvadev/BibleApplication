package com.studies.bibleapplication.extensions

import com.studies.bibleapplication.R
import com.studies.bibleapplication.model.BibleApplication.Companion.application

fun String.iteratorChapters(): ArrayList<String> {
    val listChapters = ArrayList<String>()
    for (item in 1..this.toByte()){
        listChapters.add(application.getString(R.string.st_chapter_item_spinner_toolbar, item.toString()))
    }
    return listChapters
}