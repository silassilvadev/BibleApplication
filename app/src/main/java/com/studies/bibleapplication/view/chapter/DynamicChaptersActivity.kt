package com.studies.bibleapplication.view.chapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.studies.bibleapplication.model.entity.Book
import android.widget.ArrayAdapter
import com.studies.bibleapplication.R
import com.studies.bibleapplication.extensions.*
import com.studies.bibleapplication.view.chapter.verses.DynamicVerseFragment
import kotlinx.android.synthetic.main.activity_chapter_dynamic.*
import java.util.ArrayList

class DynamicChaptersActivity: AppCompatActivity(),
                       SearchView.OnQueryTextListener,
                       SearchView.OnCloseListener {

    private var titleChapters = ArrayList<String>()
    private var listFragments = ArrayList<DynamicVerseFragment>()
    private lateinit var selectedFragment: DynamicVerseFragment
    private lateinit var itemFavorite: MenuItem
    private lateinit var itemShare: MenuItem
    private lateinit var book: Book

    companion object {
        const val KEY_BOOK_SELECTED = "readingBooks"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActivity(R.layout.activity_chapter_dynamic)
        setSupportActionBar(dynamic_chapter_toolbar)
        receiveParams()
        initToolbar()
        initSpinner()
        initFragments()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.menu_chapters_toolbar, menu)

            this.itemShare = it.findItem(R.id.item_share_toolbar)
            this.itemFavorite = it.findItem(R.id.item_favorite_toolbar)

            (it.findItem(R.id.item_search_toolbar).actionView as SearchView).apply{
                this.setOnQueryTextListener(this@DynamicChaptersActivity)
                this.setOnCloseListener(this@DynamicChaptersActivity)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            itemFavorite.itemId -> selectedFragment.initFavorite()
            itemShare.itemId -> selectedFragment.initSharing()
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            selectedFragment.search(it)
        }
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        onQueryTextSubmit(query)
        return false
    }

    override fun onClose(): Boolean {
        selectedFragment.search()
        return false
    }

    override fun onBackPressed() {
        if (selectedFragment.hasItemSelected()) {
            refreshButtonsView(false)
            selectedFragment.clearSelected()
        } else {
            super.onBackPressed()
            animateTransition(false)
        }
    }

    private fun receiveParams(){
        intent.getSerializableExtra(KEY_BOOK_SELECTED)?.let {
            book = it as Book
            titleChapters = book.chapters.iteratorChapters()
            listFragments = createDynamicFragments(book, titleChapters.size)
        } ?: run {
            showAlertInformation(message = getString(R.string.st_error_not_receive_chapters)) {
                finish()
            }
        }
    }

    private fun initToolbar(){
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
            it.title = book.name
        }
    }
    private fun initSpinner() {
        dynamic_chapter_spinner.adapter = ArrayAdapter(this,
            android.R.layout.simple_dropdown_item_1line,
            titleChapters)
        dynamic_chapter_spinner.onItemSelected { adapterView, _, _, _ ->
            adapterView?.let {
                selectPage(it.selectedItemPosition)
            }
        }
    }

    private fun initFragments(){
        dynamic_chapter_verses_container.adapter = DynamicChaptersAdapter(this.listFragments, supportFragmentManager)
        dynamic_chapter_verses_container.onPageSelected { position -> selectPage(position) }
    }

    internal fun refreshButtonsView(isItemSelected: Boolean){
        itemFavorite.isVisible = isItemSelected
        itemShare.isVisible = isItemSelected
        refreshToolbarColor(isItemSelected)
    }

    private fun refreshToolbarColor(isItemSelected: Boolean) {
        val colorId = if (isItemSelected){
            ContextCompat.getColor(this, R.color.colorLight)
        } else {
            ContextCompat.getColor(this, R.color.colorGray)
        }
        dynamic_chapter_toolbar.setBackgroundColor(colorId)
    }

    private fun selectPage(position: Int = 1){
        (dynamic_chapter_verses_container.adapter as? DynamicChaptersAdapter)?.let {
            dynamic_chapter_verses_container.currentItem = position
            selectedFragment = it.getItem(position)
            dynamic_chapter_spinner.setSelection(position)
        }
    }
}