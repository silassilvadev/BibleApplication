package com.studies.catholicbibleapplication.view.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.studies.catholicbibleapplication.view.bible.BibleFragment
import com.studies.catholicbibleapplication.view.favorites.FavoritesFragment
import com.studies.catholicbibleapplication.view.daily.DailyVerseFragment

class MainAdapter(fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var fragments = arrayListOf(BibleFragment(), FavoritesFragment(), DailyVerseFragment())

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}