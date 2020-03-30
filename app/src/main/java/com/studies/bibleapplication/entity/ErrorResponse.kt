package com.studies.bibleapplication.entity

data class ErrorResponse(
    val message: String,
    var number: String? = null
)
