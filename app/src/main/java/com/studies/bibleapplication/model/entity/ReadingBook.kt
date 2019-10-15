package com.studies.bibleapplication.model.entity

import com.studies.bibleapplication.R
import com.studies.bibleapplication.model.BibleApplication.Companion.application
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

data class ReadingBook(var book: Book,
                       var isSelected: Boolean,
                       var date: Date,
                       var chapter: Chapter,
                       var verses: ArrayList<Verse>): Book(book), Serializable {

    fun setUpSelectedShare(): String {
        var sharingText = ""
        var verseDescription = ""
        val abbrevFirstUpper = book.getAbbrevCapitalize()
        val versesSelected = ArrayList<String>()

        for (verse in verses) {
            if (verse.isSelected) {
                versesSelected.add(verse.number.toString())
                verseDescription += application.getString(R.string.st_verse_selected,
                    verse.number.toString(),
                    verse.text)
            }
        }

        if (versesSelected.isNotEmpty()) {
            sharingText = if (versesSelected.size == 1) {
                application.getString(
                    R.string.st_info_verse_selected,
                    verseDescription,
                    abbrevFirstUpper,
                    chapter.number.toString(),
                    versesSelected.first()
                )
            } else {
                application.getString(
                    R.string.st_info_verses_selected,
                    verseDescription,
                    abbrevFirstUpper,
                    chapter.number.toString(),
                    versesSelected.first(),
                    versesSelected.last()
                )
            }
        }
        return sharingText
    }
}