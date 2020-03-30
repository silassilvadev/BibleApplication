package com.studies.bibleapplication.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.studies.bibleapplication.R
import com.studies.bibleapplication.utils.extensions.animateFade
import com.studies.bibleapplication.utils.extensions.initActivity
import com.studies.bibleapplication.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity(R.layout.activity_splash)

        splash_constraint_layout.animateFade(true, 2700) {
            if (it) splash_logo_image.animateFade(false, 300) {
                splash_logo_image.visibility = View.GONE
                startActivity<MainActivity>()
                finish()
            }
        }
    }
}
