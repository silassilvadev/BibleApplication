package com.studies.bibleapplication.extensions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.viewpager.widget.ViewPager
import com.studies.bibleapplication.model.entity.Book
import com.studies.bibleapplication.view.chapter.verses.DynamicVerseFragment

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
