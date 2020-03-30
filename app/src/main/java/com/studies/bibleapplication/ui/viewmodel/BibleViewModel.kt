package com.studies.bibleapplication.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.studies.bibleapplication.model.repository.BibleRepository
import com.studies.bibleapplication.entity.Book
import com.studies.bibleapplication.ui.call.IProtocolResponse
import com.studies.bibleapplication.model.calls.IBaseResponse
import com.studies.bibleapplication.entity.ReadingBook

class BibleViewModel: ViewModel() {

    private lateinit var protocolResponse: IProtocolResponse
    private lateinit var mutableBooks: MutableLiveData<ArrayList<Book>>
    private lateinit var mutableReadingBook: MutableLiveData<ReadingBook>

    private val bibleService = BibleRepository()

    fun setProtocol(protocolResponse: IProtocolResponse){
        this.protocolResponse = protocolResponse
    }

    fun getBooks(): MutableLiveData<ArrayList<Book>> {
        mutableBooks = MutableLiveData()
        bibleService.getBooks(object : IBaseResponse<ArrayList<Book>> {
            override fun onResponseSuccess(response: ArrayList<Book>) {
                mutableBooks.value = response
            }

            override fun onResponseError(message: String) {
                protocolResponse.onResponseError(message)
            }
        })
        return mutableBooks
    }

    fun getChapter(bookAbbrev: String, chapter: Int): MutableLiveData<ReadingBook>{
        mutableReadingBook = MutableLiveData()
        bibleService.getChapter("nvi", bookAbbrev, chapter,
            object : IBaseResponse<ReadingBook> {
            override fun onResponseSuccess(response: ReadingBook) {
                mutableReadingBook.value = response
            }

            override fun onResponseError(message: String) {
                protocolResponse.onResponseError(message)
            }
        })
        return mutableReadingBook
    }
}