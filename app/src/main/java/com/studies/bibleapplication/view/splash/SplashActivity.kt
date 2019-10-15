package com.studies.bibleapplication.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.studies.bibleapplication.R
import com.studies.bibleapplication.extensions.animateFade
import com.studies.bibleapplication.extensions.initActivity
import com.studies.bibleapplication.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity(R.layout.activity_splash)
        startAnimations()
        Handler().postDelayed({
            splash_progress_bar.isVisible = false
            startActivity(Intent(this, MainActivity::class.java))
            animateFade()
            finish()
        }, 3500L)
    }

    private fun startAnimations(){
        val fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_in)
        fadeAnimation.reset()
        splash_logo_image.startAnimation(fadeAnimation)
        splash_progress_bar.startAnimation(fadeAnimation)
    }
}
