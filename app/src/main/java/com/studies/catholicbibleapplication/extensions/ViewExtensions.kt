package com.studies.catholicbibleapplication.extensions

import androidx.viewpager.widget.ViewPager

fun ViewPager.onPageSelected(onPageSelected: (Int) -> Unit){
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
        override fun onPageScrollStateChanged(state: Int) {
            //TODO not used
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            //TODO not used
        }

        override fun onPageSelected(position: Int) {
            onPageSelected.invoke(position)
        }
    })
}