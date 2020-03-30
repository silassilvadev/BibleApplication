package br.com.valecard.estabelecimento.utils.generic

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.studies.bibleapplication.R
import com.studies.bibleapplication.utils.extensions.forceHideKeyboard
import java.util.*

class GenericProgressDialog : DialogFragment() {

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        return AlertDialog.Builder(Objects.requireNonNull<FragmentActivity>(activity))
                .setView(activity!!
                        .layoutInflater
                        .inflate(R.layout.dialog_generic_progress, null))
                .create()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Objects.requireNonNull<Window>(dialog?.window).setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun show(manager: FragmentManager) {
        try {
            if (show || dialog != null && dialog!!.isShowing) return
            show = true
            forceHideKeyboard()
            super.show(manager, TAG)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun dismiss() {
        try {
            show = false
            super.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {

        private const val TAG = "tagLoading"
        private var genericProgressDialog: GenericProgressDialog? = null
        private var show = false

        val instance: GenericProgressDialog
            @Synchronized get() {
                if (genericProgressDialog == null) {
                    genericProgressDialog = GenericProgressDialog()
                }
                return genericProgressDialog!!
            }
    }
}