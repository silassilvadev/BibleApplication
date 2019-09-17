package com.studies.catholicbibleapplication.model.call

interface IGenericResponse<T> {
    fun onResponseSuccess(response: T)
    fun onResponseError(message: String)
}