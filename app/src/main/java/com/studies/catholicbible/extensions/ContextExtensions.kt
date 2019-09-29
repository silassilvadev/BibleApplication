package com.studies.catholicbible.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Parcelable
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.studies.catholicbible.R
import com.studies.catholicbible.model.CatholicBibleApplication
import java.io.Serializable

fun getApplication(): Application {
    return CatholicBibleApplication.application
}

fun isConnected(): Boolean{
    val connectivityManager =
        getApplication().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
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