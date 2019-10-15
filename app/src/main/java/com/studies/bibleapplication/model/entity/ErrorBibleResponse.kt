package com.studies.bibleapplication.model.entity

data class ErrorBibleResponse(
    val message: String,
    var number: String? = null
)
