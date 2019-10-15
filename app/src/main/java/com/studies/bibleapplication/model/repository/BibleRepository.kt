package com.studies.bibleapplication.model.repository

import com.studies.bibleapplication.model.calls.GenericResponse
import com.studies.bibleapplication.model.calls.IGenericResponse
import com.studies.bibleapplication.model.entity.*
import com.studies.bibleapplication.model.service.ClientService
import kotlin.collections.ArrayList

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
                   listener: IGenericResponse<ReadingBook>) {
        bibleService.getChapter(version, bookAbbrev, chapter)
            .enqueue(object : GenericResponse<ReadingBook>(){
            override fun onSuccess(response: ReadingBook) {
                listener.onResponseSuccess(response)
            }

            override fun onError(errorBible: ErrorBibleResponse) {
                listener.onResponseError(errorBible.message)
            }

        })
    }
}
