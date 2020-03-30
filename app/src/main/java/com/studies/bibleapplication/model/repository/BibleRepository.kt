package com.studies.bibleapplication.model.repository

import com.studies.bibleapplication.model.calls.BaseCallback
import com.studies.bibleapplication.model.calls.IBaseResponse
import com.studies.bibleapplication.entity.*
import com.studies.bibleapplication.model.client.ClientService.Companion.bibleService
import kotlin.collections.ArrayList

class BibleRepository {

    fun getBooks(listener: IBaseResponse<ArrayList<Book>>){
        bibleService.getBooks().enqueue(object : BaseCallback<ArrayList<Book>>(){
            override fun onSuccess(response: ArrayList<Book>) {
                listener.onResponseSuccess(response)
            }

            override fun onError(error: ErrorResponse) {
                listener.onResponseError(error.message)
            }
        })
    }

    fun getChapter(version: String,
                   bookAbbrev: String,
                   chapter: Int,
                   listener: IBaseResponse<ReadingBook>) {
        bibleService.getChapter(version, bookAbbrev, chapter)
            .enqueue(object : BaseCallback<ReadingBook>(){
            override fun onSuccess(response: ReadingBook) {
                listener.onResponseSuccess(response)
            }

            override fun onError(error: ErrorResponse) {
                listener.onResponseError(error.message)
            }

        })
    }
}
