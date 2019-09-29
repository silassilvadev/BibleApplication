package com.studies.catholicbible.model.calls

interface IGenericResponse<T> {
    fun onResponseSuccess(response: T)
    fun onResponseError(message: String)
}