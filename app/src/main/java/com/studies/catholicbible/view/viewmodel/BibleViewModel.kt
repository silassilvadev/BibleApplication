package com.studies.catholicbible.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.studies.catholicbible.model.repository.BibleRepository
import com.studies.catholicbible.model.entity.Book
import com.studies.catholicbible.model.calls.IGenericProtocol
import com.studies.catholicbible.model.calls.IGenericResponse
import com.studies.catholicbible.model.entity.ReadingBook

class BibleViewModel: ViewModel() {

    private lateinit var protocol: IGenericProtocol
    private lateinit var mutableBooks: MutableLiveData<ArrayList<Book>>
    private lateinit var mutableReadingBook: MutableLiveData<ReadingBook>

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

    fun getChapter(bookAbbrev: String, chapter: Int): MutableLiveData<ReadingBook>{
        mutableReadingBook = MutableLiveData()
        bibleService.getChapter("nvi", bookAbbrev, chapter,
            object : IGenericResponse<ReadingBook> {
            override fun onResponseSuccess(response: ReadingBook) {
                mutableReadingBook.value = response
            }

            override fun onResponseError(message: String) {
                protocol.onResponseError(message)
            }
        })
        return mutableReadingBook
    }
}