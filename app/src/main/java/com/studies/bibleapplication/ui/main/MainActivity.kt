package com.studies.bibleapplication.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.view.get
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.studies.bibleapplication.R
import com.studies.bibleapplication.utils.extensions.*
import com.studies.bibleapplication.ui.main.bible.BibleFragment
import com.studies.bibleapplication.ui.main.daily.DailyVerseFragment
import com.studies.bibleapplication.ui.main.favorites.FavoritesFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity(),
                    BottomNavigationView.OnNavigationItemSelectedListener,
                    SearchView.OnQueryTextListener,
                    SearchView.OnCloseListener {

    private lateinit var mainAdapter: MainAdapter
    private var backCount = 0
    private var menuSelected = MainMenuSelected.BIBLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        supportActionBar?.title = getString(menuSelected.title)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.menu_main_toolbar, menu)
            val itemSearch = it.findItem(R.id.item_search_toolbar)
            val searchView = itemSearch.actionView as SearchView

            searchView.setOnQueryTextListener(this)
            searchView.setOnCloseListener(this)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectPage(menuItem = item)
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            searchCurrentFragment(it)
        }
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        onQueryTextSubmit(query)
        return false
    }

    override fun onClose(): Boolean {
        searchCurrentFragment()
        return false
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size > 0 && backCount < 1 ) {
            selectPage(indexCount = 1)
            showToast(R.string.st_generic_message_click_exit)
        } else {
            super.onBackPressed()
        }
    }

    private fun initView() {
        this.mainAdapter = MainAdapter(
            arrayListOf(BibleFragment(), FavoritesFragment(), DailyVerseFragment()),
            supportFragmentManager)
        main_container_view_pager.adapter = this.mainAdapter

        main_bottom_navigation.setOnNavigationItemSelectedListener(this)
        main_container_view_pager.onPageChanged(onPageSelected = { position ->
            selectPage(position = position)
        })
    }

    private fun searchCurrentFragment(query: String = ""){
        when (val fragment = this.mainAdapter.getItem(main_container_view_pager.currentItem)){
            is BibleFragment -> fragment.search(query)
            is FavoritesFragment -> fragment.search(query)
            is DailyVerseFragment -> fragment.search(query)
            else -> showToast(R.string.st_error_searched)
        }
    }

    private fun selectPage(menuItem: MenuItem? = null, position: Int? = null, indexCount: Int = 0){
        backCount = indexCount
        menuSelected = MainMenuSelected.getSelected(menuItem, position)
        supportActionBar?.title = getString(menuSelected.title)
        main_container_view_pager.currentItem = menuSelected.position
        main_bottom_navigation.menu[menuSelected.position].isChecked = true
    }
}
