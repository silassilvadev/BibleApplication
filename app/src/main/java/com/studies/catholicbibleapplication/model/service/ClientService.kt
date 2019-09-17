package com.studies.catholicbibleapplication.model.service

import com.google.gson.GsonBuilder
import com.studies.catholicbibleapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ClientService {

    fun configureBibleService(): IBibleService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BIBLE_URL)
            .client(configureClientHttp())
            .addConverterFactory(configureFactory())
            .build()
            .create(IBibleService::class.java)
    }

    private fun configureClientHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    private fun configureFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(GsonBuilder().create())
    }

}
