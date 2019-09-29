package com.studies.catholicbible.model.service

import com.studies.catholicbible.model.entity.Book
import com.studies.catholicbible.model.entity.ReadingBook
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IBibleService {

    @GET("books")
    fun getBooks(): Call<ArrayList<Book>>

    @GET("verses/{version}/{book}/{chapterNumber}")
    fun getChapter(@Path("version") version: String,
                   @Path("book") abbrev: String,
                   @Path("chapterNumber") chapter: Int): Call<ReadingBook>
}
