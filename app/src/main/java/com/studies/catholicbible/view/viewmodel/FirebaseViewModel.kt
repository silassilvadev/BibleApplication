package com.studies.catholicbible.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.FirebaseDatabase
import com.studies.catholicbible.model.calls.IGenericProtocol
import com.studies.catholicbible.model.entity.Book
import com.studies.catholicbible.model.entity.ReadingBook
import com.studies.catholicbible.model.entity.Chapter
import com.studies.catholicbible.model.entity.Verse
import java.util.*
import kotlin.collections.ArrayList

class FirebaseViewModel: ViewModel(){

    private lateinit var protocol: IGenericProtocol
    private lateinit var mutableReadingBooks: MutableLiveData<ArrayList<ReadingBook>>

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val userReference = databaseReference.child("users")
    private val bookReference = databaseReference.child("book")

    fun setProtocol(protocol: IGenericProtocol){
        this.protocol = protocol
    }

    fun getFavorites(): MutableLiveData<ArrayList<ReadingBook>> {
        mutableReadingBooks = MutableLiveData()
        //Serviço do Firebase
        val verses = ArrayList<Verse>()
        verses.add(Verse(1, "Deus é bom", true, false))
        verses.add(Verse(2, "Deus é maravilhoso", true, false))
        verses.add(Verse(3, "Deus sabe o melhor", true, false))

        val books = ArrayList<ReadingBook>()
        books.add(ReadingBook(
            Book("Gn",
                "Silas",
                "10",
                "Pentateuco",
                "Gênesis",
                "Antigo Testamento"),
            false,
            Date(),
            Chapter(1, 10),
            verses
        ))

        mutableReadingBooks.value = books

        return mutableReadingBooks
    }

    fun getDailyVerses(): MutableLiveData<ArrayList<ReadingBook>> {
        mutableReadingBooks = MutableLiveData()
        //Serviço do Firebase
        val verses = ArrayList<Verse>()
        verses.add(Verse(13,
            "13 Por isso, vistam toda a armadura de Deus, para que possam resistir no dia mau e permanecer inabaláveis, depois de terem feito tudo.",
            isFavorite = true,
            isSelected = false
        ))

        val books = ArrayList<ReadingBook>()
        books.add(ReadingBook(
            Book("Ef",
                "Paulo",
                "6",
                "Cartas",
                "Efésios",
                "Novo Testamento"),
            false,
            Date(),
            Chapter(6, 13),
            verses
        ))

        mutableReadingBooks.value = books

        return mutableReadingBooks
    }

    fun setFavoriteTest(readingBook: ReadingBook, verse: Verse){
        
    }
}