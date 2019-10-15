package com.studies.bibleapplication.model.calls

import com.google.gson.Gson
import com.studies.bibleapplication.R
import com.studies.bibleapplication.extensions.isNetworkConnected
import com.studies.bibleapplication.model.BibleApplication.Companion.application
import com.studies.bibleapplication.model.entity.ErrorBibleResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


abstract class GenericResponse<T> : Callback<T> {

    abstract fun onSuccess(response: T)

    abstract fun onError(errorBible: ErrorBibleResponse)

    override fun onFailure(call: Call<T>, throwable: Throwable) {
        onError(ErrorBibleResponse(
            if (isNetworkConnected()) {
                throwable.message ?: application.getString(R.string.st_error_not_connection_server)
            } else {
                application.getString(R.string.st_error_network_unavailable)
            }
        ))
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            onSuccess(response.body()!!)
        } else {
            try {
                onError(
                    Gson().fromJson<ErrorBibleResponse>(
                        Objects.requireNonNull<ResponseBody>(response.errorBody()).string(),
                        ErrorBibleResponse::class.java
                    )
                )
            } catch (exception: Exception) {
                val errorResponse = ErrorBibleResponse(
                    exception.message
                            ?: application.getString(R.string.st_error_not_connection_server))
                onError(errorResponse)
            }
        }
    }
}