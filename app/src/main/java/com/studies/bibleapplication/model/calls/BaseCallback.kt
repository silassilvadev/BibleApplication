package com.studies.bibleapplication.model.calls

import com.google.gson.Gson
import com.studies.bibleapplication.R
import com.studies.bibleapplication.utils.extensions.isNetworkConnected
import com.studies.bibleapplication.model.BibleApplication.Companion.application
import com.studies.bibleapplication.entity.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


abstract class BaseCallback<T> : Callback<T> {

    abstract fun onSuccess(response: T)

    abstract fun onError(error: ErrorResponse)

    override fun onFailure(call: Call<T>, throwable: Throwable) {
        onError(ErrorResponse(
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
                    Gson().fromJson<ErrorResponse>(
                        Objects.requireNonNull<ResponseBody>(response.errorBody()).string(),
                        ErrorResponse::class.java
                    )
                )
            } catch (exception: Exception) {
                val errorResponse = ErrorResponse(
                    exception.message
                            ?: application.getString(R.string.st_error_not_connection_server))
                onError(errorResponse)
            }
        }
    }
}