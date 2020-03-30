package br.com.valecard.estabelecimento.ui.generic

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.studies.bibleapplication.ui.call.IProtocolResponse
import com.studies.bibleapplication.utils.extensions.animateTransition
import com.studies.bibleapplication.utils.extensions.hideProgress
import com.studies.bibleapplication.utils.extensions.showSnackBar


open class GenericActivity (private val withStatusBar: Boolean = true,
                            private val isAnimation: Boolean = true,
                            private val isAnimationNext: Boolean = true)
    : AppCompatActivity(), IProtocolResponse {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isAnimation) this.animateTransition(isAnimationNext)
        configureActivity()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isAnimation) this.animateTransition(!isAnimationNext)
    }

    override fun finish() {
        if (isAnimation) this.animateTransition(!isAnimationNext)
        super.finish()
    }

    override fun onResponseError(message: String) {
        hideProgress()
        this.showSnackBar(message)
    }

    private fun configureActivity(){
        if (!withStatusBar) window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}