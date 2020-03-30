package com.studies.bibleapplication.utils.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricConstants
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import br.com.valecard.estabelecimento.ui.generic.GenericViewModel
import br.com.valecard.estabelecimento.utils.generic.GenericProgressDialog
import com.google.android.material.snackbar.Snackbar
import com.studies.bibleapplication.R
import com.studies.bibleapplication.ui.call.IBiometricResponse
import com.studies.bibleapplication.ui.call.IProtocolResponse
import org.jetbrains.anko.contentView
import java.util.concurrent.Executors

//region Activity

fun Activity.forceHideKeyboard() {
    this.currentFocus?.let {
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Activity.forceShowKeyboard() {
    this.currentFocus?.let {
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}

fun Activity.hasBiometricAvailable(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        with(BiometricManager.from(this).canAuthenticate()){
            !(this == BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE
                    && this == BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE)
        }
    } else false
}

fun Activity.hasBiometricEnrolled(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
        val canAuthenticate = BiometricManager.from(this).canAuthenticate()
        canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS
    } else false
}

fun Activity.makeBiometricAuthentication(response: IBiometricResponse,
                                         title: String = this.getString(R.string.biometric_title),
                                         subTitle: String = "",
                                         description: String = "",
                                         cancelButtonText: String = this.getString(R.string.biometric_button_cancel)) {
    val biometricPrompt = BiometricPrompt((this as FragmentActivity),
        Executors.newSingleThreadExecutor(),
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                response.onBiometricResponse(result)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                response.onBiometricError(errorCode, errString.toString(), null)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onAuthenticationError(
                    BiometricConstants.ERROR_NO_DEVICE_CREDENTIAL,
                    this@makeBiometricAuthentication.getString(R.string.error_biometric_invalid))
            }
        })

    biometricPrompt.authenticate(
        BiometricPrompt.PromptInfo.Builder()
        .setTitle(title)
        .setSubtitle(subTitle)
        .setDescription(description)
        .setNegativeButtonText(cancelButtonText)
        .build())
}

fun Activity.saveTagScreen(){
    //FirebaseAnalytics.getInstance(this).setCurrentScreen(this, this.javaClass.simpleName, null)
}

fun Activity.showAlertDialog(title: String = this.getString(R.string.st_alert_dialog_title),
                             message: String,
                             textButtonPositive: String = this.getString(R.string.st_alert_dialog_positive_text),
                             textButtonNegative: String = this.getString(R.string.st_alert_dialog_negative_text),
                             onResponseDialog: ((isPositive: Boolean) -> Unit)? = null) {

    this.getAlertDialog(title, message)
        .setPositiveButton(textButtonPositive) { _, _ ->
            onResponseDialog?.let {
                it(true)
            }
        }
        .setNegativeButton(textButtonNegative) { _, _ ->
            onResponseDialog?.let {
                it(false)
            }
        }
        .create()
        .show()
}


fun Activity.showAlertDialog(title: String = this.getString(R.string.st_alert_dialog_title),
                             message: String,
                             textButtonConfirmation: String = this.getString(R.string.st_alert_dialog_ok_text),
                             onResponseDialog: ((isPositive: Boolean) -> Unit)? = null) {
    this.getAlertDialog(title, message)
        .setNeutralButton(textButtonConfirmation) { _, _ ->
            onResponseDialog?.let {
                it(true)
            }
        }
        .create()
        .show()
}

private fun Activity.getAlertDialog(title: String,
                                    message: String): AlertDialog.Builder {

    return AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(message)
        .setCancelable(false)
}

fun Activity.initActivity(idLayout: Int, isNext: Boolean = true) {
    this.setContentView(idLayout)
    this.animateTransition(isNext)
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    this.saveTagScreen()
}

fun Activity.animateTransition(isNext: Boolean = true) {
    if (isNext) this.overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out)
    else this.overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out)
}

fun Activity.showSnackBar(messageResId: Int, colorText: Int? = null, colorBackground: Int? = null, duration: Int? = null){
    this.showSnackBar(this.getString(messageResId), colorText, colorBackground, duration)
}

fun Activity.showSnackBar(message: String,
                          colorText: Int? = null,
                          colorBackground: Int? = null,
                          duration: Int? = null) {
    this.contentView?.let { itView ->
        val dialogSnack = Snackbar.make(itView, message, duration ?: Snackbar.LENGTH_SHORT)
        dialogSnack.view.let{ itSnackView ->
            val dialogSnackText = itSnackView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            dialogSnackText.setTextColor(colorText ?: this.myGetColor(android.R.color.white))
            itSnackView.setBackgroundColor(colorBackground ?: this.myGetColor(R.color.colorPrimary))
        }
        dialogSnack.show()
    }
}
//endregion

//region FragmentActivity
inline fun <reified VM: GenericViewModel> FragmentActivity.initViewModel(protocolResponse: IProtocolResponse): VM {
    val viewModel = ViewModelProviders.of(this).get(VM::class.java)
    viewModel.setProtocol(protocolResponse)
    return viewModel
}
//endregion

//endregion

//region AppComptatActivity
fun AppCompatActivity.showProgress() = GenericProgressDialog.instance.show(this.supportFragmentManager)

fun AppCompatActivity.hideProgress() = GenericProgressDialog.instance.dismiss()
//endregion
