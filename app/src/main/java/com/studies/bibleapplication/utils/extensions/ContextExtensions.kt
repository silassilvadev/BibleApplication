package com.studies.bibleapplication.utils.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.TypedValue
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.studies.bibleapplication.model.BibleApplication.Companion.application

fun isNetworkConnected(): Boolean {
    val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

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

fun Context.showToast(messageResId: Int, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, messageResId, duration).show()
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, duration).show()
}

fun Context.dpToPx(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
}

fun Context.myGetColor(colorId: Int): Int = ContextCompat.getColor(this, colorId)

fun Context.myGetDrawable(drawableId: Int): Drawable? = ContextCompat.getDrawable(this, drawableId)