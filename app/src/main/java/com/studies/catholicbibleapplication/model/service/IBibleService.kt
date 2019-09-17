package com.studies.catholicbibleapplication.model.service

import com.studies.catholicbibleapplication.model.entity.Book
import com.studies.catholicbibleapplication.model.entity.BookSelected
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IBibleService {

    @GET("books")
    fun getBooks(): Call<ArrayList<Book>>

    @GET("verses/{version}/{book}/{chapter}")
    fun getChapter(@Path("version") version: String,
                   @Path("book") bookAbbrev: String,
                   @Path("chapter") chapter: Int): Call<BookSelected>
}
