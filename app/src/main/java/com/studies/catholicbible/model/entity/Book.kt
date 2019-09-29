package com.studies.catholicbible.model.entity

import java.io.Serializable

open class Book(val abbrev: String,
                val author: String,
                val chapters: String,
                val group: String,
                val name: String,
                val testament: String): Serializable {

    constructor(book: Book): this(
        book.abbrev,
        book.author,
        book.chapters,
        book.group,
        book.name,
        book.testament)

    fun getAbbrevCapitalize(): String {
        return if (abbrev.contains("[\\d]".toRegex())) {
            abbrev.replace("[\\D]".toRegex(), "") +
                    abbrev.replace("[\\d]".toRegex(), "").capitalize()
        } else {
            abbrev.replace("[\\d]".toRegex(), "").capitalize()
        }
    }
}
