package com.studies.catholicbibleapplication.model.entity

data class ErrorBibleResponse(
    val message: String,
    var number: String? = null
)
