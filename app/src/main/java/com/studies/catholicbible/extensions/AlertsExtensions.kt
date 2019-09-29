package com.studies.catholicbible.extensions

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import com.studies.catholicbible.R

fun Context.makeToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, duration).show()
}

private fun Context.configureDialog(title: String,
                                    message: String,
                                    positiveText: String,
                                    negativeText: String?,
                                    onResponse: () -> Unit) {

    AlertDialog.Builder(this).apply {
        this.setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveText){ dialog, _ ->
                onResponse()
                dialog.dismiss()
            }
        if (negativeText.isNullOrBlank()) {
            this.setNegativeButton(negativeText) { dialog, _ ->
                dialog.dismiss()
            }
        }
        this.show()
    }
}

fun Context.showAlertConfirmation(title: String = getString(R.string.st_alert_dialog_title),
                                  message: String,
                                  positiveText: String = getString(R.string.st_alert_dialog_positive_text),
                                  negativeText: String = getString(R.string.st_alert_dialog_negative_text),
                                  onResponseDialog: () -> Unit){
    this.configureDialog(title, message, positiveText, negativeText, onResponseDialog)
}

fun Context.showAlertInformation(title: String = getString(R.string.st_alert_dialog_title),
                                 message: String,
                                 okText: String = getString(R.string.st_alert_dialog_ok_text),
                                 onResponseDialog: () -> Unit){
    this.configureDialog(title, message, okText, null, onResponseDialog)

}