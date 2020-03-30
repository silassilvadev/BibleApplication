package com.studies.bibleapplication.utils.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.studies.bibleapplication.entity.Book
import com.studies.bibleapplication.ui.chapter.verses.DynamicVerseFragment

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT){
    this.activity?.showToast(message, duration)
}

fun Fragment.saveTagScreen(){
    this.activity?.saveTagScreen()
}

fun Fragment.forceHideKeyboard(){
    this.activity?.forceHideKeyboard()
}

fun Fragment.forceShowKeyboard(){
    this.activity?.forceShowKeyboard()
}

fun createDynamicFragments(book: Book, totalChapters: Int): ArrayList<DynamicVerseFragment> {
    val listFragments = ArrayList<DynamicVerseFragment>()
    for (position in 1..totalChapters){
        listFragments.add(DynamicVerseFragment(book, position))
    }
    return listFragments
}