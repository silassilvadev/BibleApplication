package com.studies.catholicbible.model.entity

data class ErrorBibleResponse(
    val message: String,
    var number: String? = null
)
