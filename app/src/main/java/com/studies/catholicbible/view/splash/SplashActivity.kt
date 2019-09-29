package com.studies.catholicbible.view.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.core.view.isVisible
import com.studies.catholicbible.R
import com.studies.catholicbible.extensions.initActivity
import com.studies.catholicbible.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity(R.layout.activity_splash)
        startAnimations()
        Handler().postDelayed({
            splash_progress_bar.isVisible = false
            startActivity(Intent(this, MainActivity::class.java))
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
