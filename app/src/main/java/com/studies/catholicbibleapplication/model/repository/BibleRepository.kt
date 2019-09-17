package com.studies.catholicbibleapplication.model.repository

import com.studies.catholicbibleapplication.model.call.GenericResponse
import com.studies.catholicbibleapplication.model.call.IGenericResponse
import com.studies.catholicbibleapplication.model.service.ClientService
import com.studies.catholicbibleapplication.model.entity.Book
import com.studies.catholicbibleapplication.model.entity.BookSelected
import com.studies.catholicbibleapplication.model.entity.ErrorBibleResponse

class BibleRepository {

    private var bibleService = ClientService().configureBibleService()

    fun getBooks(listener: IGenericResponse<ArrayList<Book>>){
        bibleService.getBooks().enqueue(object : GenericResponse<ArrayList<Book>>(){
            override fun onSuccess(response: ArrayList<Book>) {
                listener.onResponseSuccess(response)
            }

            override fun onError(errorBible: ErrorBibleResponse) {
                listener.onResponseError(errorBible.message)
            }
        })
    }

    fun getChapter(version: String,
                   bookAbbrev: String,
                   chapter: Int,
                   listener: IGenericResponse<BookSelected>) {
        bibleService.getChapter(version, bookAbbrev, chapter)
            .enqueue(object : GenericResponse<BookSelected>(){
            override fun onSuccess(response: BookSelected) {
                listener.onResponseSuccess(response)
            }

            override fun onError(errorBible: ErrorBibleResponse) {
                listener.onResponseError(errorBible.message)
            }

        })
    }
}
