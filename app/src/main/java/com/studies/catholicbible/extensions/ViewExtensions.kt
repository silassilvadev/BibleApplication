package com.studies.catholicbible.extensions

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.studies.catholicbible.R
import com.studies.catholicbible.model.entity.Book
import com.studies.catholicbible.view.chapter.verses.DynamicVerseFragment

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

fun Spinner.onItemSelected(onItemSelected: (AdapterView<*>?, View?, Int, Long) -> Unit){
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
            onItemSelected.invoke(adapterView, view, position, id)
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            //TODO not used
        }
    }
}

fun createDynamicFragments(book: Book, totalChapters: Int): ArrayList<DynamicVerseFragment> {
    val listFragments = ArrayList<DynamicVerseFragment>()
    for (position in 1..totalChapters){
        listFragments.add(DynamicVerseFragment(book, position))
    }
    return listFragments
}
