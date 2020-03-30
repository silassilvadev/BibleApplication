package com.studies.bibleapplication.model.client

import com.google.gson.GsonBuilder
import com.studies.bibleapplication.BuildConfig
import com.studies.bibleapplication.model.service.IBibleService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ClientService {

    companion object {
        lateinit var bibleService: IBibleService
    }

    fun startServices(){
        bibleService = configureService(BuildConfig.BIBLE_URL)
    }

    private inline fun <reified T> configureService(url: String): T {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(configureClientHttp())
            .addConverterFactory(configureFactory())
            .build()
            .create(T::class.java)
    }

    private fun configureClientHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    private fun configureFactory(): GsonConverterFactory {
        val factoryBuilder = GsonBuilder().create()
        return GsonConverterFactory.create(factoryBuilder)
    }

}
