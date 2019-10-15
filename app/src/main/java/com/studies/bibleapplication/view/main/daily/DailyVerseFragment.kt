package com.studies.bibleapplication.view.main.daily


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.studies.bibleapplication.R
import com.studies.bibleapplication.extensions.filterArray
import com.studies.bibleapplication.model.calls.IGenericProtocol
import com.studies.bibleapplication.model.calls.IGenericSearch
import com.studies.bibleapplication.model.entity.ReadingBook
import com.studies.bibleapplication.view.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.fragment_daily_verse.*

class DailyVerseFragment : Fragment(),
    IGenericSearch,
    IGenericProtocol,
    SwipeRefreshLayout.OnRefreshListener{

    private lateinit var viewModel: FirebaseViewModel
    private lateinit var readingBooks: ArrayList<ReadingBook>
    private lateinit var booksSelected: ArrayList<ReadingBook>

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_daily_verse, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)
            viewModel.setProtocol(this)

            daily_verse_swipe_refresh.setColorSchemeColors(ContextCompat.getColor(it, R.color.colorPrimary))
            daily_verse_swipe_refresh.setOnRefreshListener(this)

            showProgress(false)
            loadDailyVerses()
        }
    }

    override fun onRefresh() {
        showProgress(true)
        loadDailyVerses()
    }

    override fun search(query: String) {
        showProgress(false)
        daily_verse_recycler?.let { recycler ->
            (recycler.adapter as? DailyVerseAdapter)?.updateFavorites(this.readingBooks.filterArray(query))
        }
    }

    override fun onResponseError(message: String) {
        hideProgress()
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun loadDailyVerses(){
        viewModel.getDailyVerses().observe(this, Observer<ArrayList<ReadingBook>> { books ->
            this.booksSelected = books
            initRecyclerView()
            hideProgress()
        })
    }

    private fun initRecyclerView(){
        daily_verse_recycler.layoutManager = LinearLayoutManager(activity)
        daily_verse_recycler.adapter = DailyVerseAdapter(this, this.booksSelected)
    }

    private fun showProgress(isSwipe: Boolean){
        daily_verse_swipe_refresh.isRefreshing = isSwipe
        daily_verse_progress.isVisible = !isSwipe
        if (daily_verse_not_response_text.isVisible){
            daily_verse_not_response_text.isVisible = false
        }
    }


    internal fun hideProgress(){
        with(daily_verse_swipe_refresh) {
            if (this.isRefreshing) {
                this.isRefreshing = false
                this.clearAnimation()
            }
        }
        daily_verse_progress.visibility = View.GONE
    }
}
