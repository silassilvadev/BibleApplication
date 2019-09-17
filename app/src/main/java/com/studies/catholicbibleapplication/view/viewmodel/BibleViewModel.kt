package com.studies.catholicbibleapplication.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.studies.catholicbibleapplication.model.repository.BibleRepository
import com.studies.catholicbibleapplication.model.entity.Book
import com.studies.catholicbibleapplication.model.call.IGenericProtocol
import com.studies.catholicbibleapplication.model.call.IGenericResponse
import com.studies.catholicbibleapplication.model.entity.BookSelected

class BibleViewModel: ViewModel() {

    private lateinit var protocol: IGenericProtocol
    private lateinit var mutableBooks: MutableLiveData<ArrayList<Book>>
    private lateinit var mutableBookSelected: MutableLiveData<BookSelected>

    private val bibleService = BibleRepository()

    fun setProtocol(protocol: IGenericProtocol){
        this.protocol = protocol
    }

    fun getBooks(): MutableLiveData<ArrayList<Book>> {
        mutableBooks = MutableLiveData()
        bibleService.getBooks(object : IGenericResponse<ArrayList<Book>> {
            override fun onResponseSuccess(response: ArrayList<Book>) {
                mutableBooks.value = response
            }

            override fun onResponseError(message: String) {
                protocol.onResponseError(message)
            }
        })
        return mutableBooks
    }

    fun getChapter(bookAbbrev: String, chapter: Int): MutableLiveData<BookSelected>{
        mutableBookSelected = MutableLiveData()
        bibleService.getChapter("nvi", bookAbbrev, chapter,
            object : IGenericResponse<BookSelected> {
            override fun onResponseSuccess(response: BookSelected) {
                mutableBookSelected.value = response
            }

            override fun onResponseError(message: String) {
                protocol.onResponseError(message)
            }
        })
        return mutableBookSelected
    }
}