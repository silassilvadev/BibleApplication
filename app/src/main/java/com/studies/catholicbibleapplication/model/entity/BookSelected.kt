package com.studies.catholicbibleapplication.model.entity

import java.io.Serializable

data class BookSelected(var book: Book,
                        var chapter: Chapter,
                        var verses: ArrayList<Verse>): Serializable