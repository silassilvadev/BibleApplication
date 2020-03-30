package com.studies.bibleapplication.utils.extensions

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import com.studies.bibleapplication.R

fun View.showSnackBar(messageResId: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, messageResId, duration).show()
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun ViewPager.onPageChanged(onPageSelected: ((position: Int) -> Unit)? = null,
                            onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null,
                            onPageScrollStateChanged: ((state: Int) -> Unit)? = null) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {
            onPageScrollStateChanged?.let { pageScroll ->
                pageScroll(state)
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            onPageScrolled?.let { pageScroller ->
                pageScroller(position, positionOffset, positionOffsetPixels)
            }
        }

        override fun onPageSelected(position: Int) {
            onPageSelected?.let{ pageSelected ->
                pageSelected(position)
            }
        }

    })

}

fun View.animateTransitionRight(isLeftIn: Boolean = true, delay: Long = 0, onFinishAnimation: ((isFinish: Boolean) -> Unit)? = null){
    this.context?.let {
        val next = AnimationUtils.loadAnimation(it, R.anim.from_left_in)
        val previous = AnimationUtils.loadAnimation(it, R.anim.from_right_out)
        next.reset()
        previous.reset()
        this.startAnimation(if(isLeftIn) next else previous)
        this.postDelayed({
            if (!isLeftIn) this.visibility = View.GONE
            Handler().postDelayed({
                onFinishAnimation?.let {
                    it(true)
                }
            }, delay)
        }, if (isLeftIn) next.duration else previous.duration)
    }
}

fun View.animateTransitionLeft(isRightIn: Boolean = true, delay: Long = 0, onFinishAnimation: ((isFinish: Boolean) -> Unit)? = null){
    this.context?.let {
        val next = AnimationUtils.loadAnimation(it, R.anim.from_right_in)
        val previous = AnimationUtils.loadAnimation(it, R.anim.from_left_out)
        next.reset()
        previous.reset()
        this.startAnimation(if(isRightIn) next else previous)
        this.postDelayed({
            if (!isRightIn) this.visibility = View.GONE
            Handler().postDelayed({
                onFinishAnimation?.let {
                    it(true)
                }
            }, delay)
        }, if (isRightIn) next.duration else previous.duration)
    }
}

fun View.animateFade(isFadeIn: Boolean = true, delay: Long = 0, onFinishAnimation: ((isFinish: Boolean) -> Unit)? = null){
    this.context?.let {
        val fadeIn = AnimationUtils.loadAnimation(it, R.anim.alpha_in)
        val fadeOut = AnimationUtils.loadAnimation(it, R.anim.alpha_out)
        fadeIn.reset()
        fadeOut.reset()
        this.startAnimation(if (isFadeIn) fadeIn else fadeOut)
        this.postDelayed({
            if (!isFadeIn) this.visibility = View.GONE
            Handler().postDelayed({
                onFinishAnimation?.let {
                    it(true)
                }
            }, delay)
        }, if (isFadeIn) fadeIn.duration else fadeOut.duration)
    }
}