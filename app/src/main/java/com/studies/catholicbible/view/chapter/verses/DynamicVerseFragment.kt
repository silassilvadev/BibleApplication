package com.studies.catholicbible.view.chapter.verses


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.studies.catholicbible.R
import com.studies.catholicbible.extensions.filterArray
import com.studies.catholicbible.extensions.makeToast
import com.studies.catholicbible.model.calls.IGenericProtocol
import com.studies.catholicbible.model.calls.IGenericSearch
import com.studies.catholicbible.model.entity.Book
import com.studies.catholicbible.model.entity.ReadingBook
import com.studies.catholicbible.view.chapter.DynamicChaptersActivity
import com.studies.catholicbible.view.viewmodel.BibleViewModel
import kotlinx.android.synthetic.main.activity_chapter_dynamic.*
import kotlinx.android.synthetic.main.fragment_verse_dynamic.*

class DynamicVerseFragment(
    var book: Book,
    var numberChapter: Int = 1)
    : Fragment(), IGenericProtocol, IGenericSearch, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mainActivity: DynamicChaptersActivity
    private lateinit var viewModel: BibleViewModel
    private lateinit var readingBook: ReadingBook
    private lateinit var adapter: DynamicVerseAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_verse_dynamic, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            mainActivity = it as DynamicChaptersActivity
            viewModel = ViewModelProviders.of(this).get(BibleViewModel::class.java)
            viewModel.setProtocol(this)

            dynamic_verse_swipe_refresh.setOnRefreshListener(this)
            dynamic_verse_swipe_refresh.setColorSchemeColors(ContextCompat.getColor(mainActivity, R.color.colorPrimaryDark))

            showProgress(false)
            loadVerses()
        }
    }

    override fun onRefresh() {
        showProgress(true)
        loadVerses()
    }

    override fun onResponseError(message: String) {
        showNotResponse()
        mainActivity.makeToast(message)
    }

    override fun search(query: String) {
        adapter.updateVerses(readingBook.verses.filterArray(query))
    }

    private fun isSharingLimit(): Boolean {
        var countSelected = 0
        for (verse in readingBook.verses){
            if(verse.isSelected) countSelected++
        }
        return countSelected <= 5
    }

    internal fun initSharing(){
        if (isSharingLimit()) {
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, readingBook.setUpSelectedShare())
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(
                sendIntent,
                getString(R.string.st_title_share_verses)
            )
            startActivity(shareIntent)
        } else {
            mainActivity.makeToast(getString(R.string.st_error_limit_selected_share))
        }
    }

    internal fun initFavorite(){
        mainActivity.makeToast(getString(R.string.st_favorite_title_toolbar))
    }

    internal fun clearSelected() {
        for (verse in readingBook.verses){
            verse.isSelected = false
        }
        adapter.updateVerses(readingBook.verses)
    }

    internal fun hasItemSelected() = adapter.hasItemSelected()

    private fun loadVerses(){
        viewModel.getChapter(book.abbrev, numberChapter)
            .observe(this, Observer<ReadingBook> { response ->
                response?.let {
                    readingBook = it
                    initRecyclerView()
                    showVerses()
                }
            })
    }

    private fun initRecyclerView(){
        this.adapter = DynamicVerseAdapter(mainActivity, readingBook.verses)
        dynamic_verse_recycler.layoutManager = LinearLayoutManager(mainActivity)
        dynamic_verse_recycler.adapter = this.adapter
    }

    private fun showVerses() {
        mainActivity.dynamic_chapter_spinner.visibility = View.VISIBLE
        dynamic_verse_recycler.visibility = View.VISIBLE
        dynamic_verse_not_response_text.visibility = View.GONE
        hideProgress()
    }

    private fun showNotResponse(){
        mainActivity.dynamic_chapter_spinner.visibility = View.GONE
        dynamic_verse_recycler.visibility = View.GONE
        dynamic_verse_not_response_text.visibility = View.VISIBLE
        hideProgress()
    }

    private fun showProgress(isSwipe: Boolean = false){
        dynamic_verse_swipe_refresh.isRefreshing = isSwipe
        dynamic_verse_progress.isVisible = !isSwipe
        if (dynamic_verse_not_response_text.isVisible){
            dynamic_verse_not_response_text.isVisible = false
        }
    }

    private fun hideProgress(){
        with(dynamic_verse_swipe_refresh){
            if (this.isRefreshing){
                this.isRefreshing = false
                this.clearAnimation()
            }
        }
        dynamic_verse_progress.isVisible = false
    }

}
