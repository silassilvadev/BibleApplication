package com.studies.catholicbible.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.inputmethod.InputMethodManager
import com.studies.catholicbible.R
import com.studies.catholicbible.model.CatholicBibleApplication

fun getApplication(): Application {
    return CatholicBibleApplication.application
}

fun isNetworkConnected(): Boolean {
    val connectivityManager =
        getApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT < 23) {
        connectivityManager.activeNetworkInfo?.let {
            return it.isConnected && (it.type == ConnectivityManager.TYPE_WIFI
                    || it.type == ConnectivityManager.TYPE_MOBILE)
        }
    } else {
        connectivityManager.activeNetwork?.let { network ->
            connectivityManager.getNetworkCapabilities(network)?.let {
                return it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                        || it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            }
        }
    }
    return false
}

fun Activity.forceHideKeyboard() {
    this.currentFocus?.let { view ->
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.initActivity(idLayout: Int, isNext: Boolean = true) {
    this.setContentView(idLayout)
    animateTransition(isNext)
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
}

fun Activity.animateTransition(isNext: Boolean = true) {
    if (isNext) this.overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out)
    else this.overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out)
}

fun Activity.animateFade(isFadeIn: Boolean = true){
    if (isFadeIn) this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out)
    else this.overridePendingTransition(R.anim.alpha_out, R.anim.alpha_in)
}