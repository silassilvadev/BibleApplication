package com.studies.bibleapplication.model.calls

interface IBaseResponse<T> {
    fun onResponseSuccess(response: T)
    fun onResponseError(message: String)
}