package com.studies.bibleapplication.view.main.bible

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.studies.bibleapplication.R
import com.studies.bibleapplication.extensions.filterArray
import com.studies.bibleapplication.model.calls.IGenericProtocol
import com.studies.bibleapplication.model.calls.IGenericSearch
import com.studies.bibleapplication.model.entity.Book
import com.studies.bibleapplication.view.chapter.DynamicChaptersActivity
import com.studies.bibleapplication.view.chapter.DynamicChaptersActivity.Companion.KEY_BOOK_SELECTED
import com.studies.bibleapplication.view.viewmodel.BibleViewModel
import kotlinx.android.synthetic.main.fragment_bible.*


class BibleFragment: Fragment(),
                     IGenericSearch,
                     IGenericProtocol,
                     SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: BibleViewModel
    private var listBooks = ArrayList<Book>()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_bible, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {

            viewModel = ViewModelProviders.of(this).get(BibleViewModel::class.java)
            viewModel.setProtocol(this)

            bible_swipe_refresh.setColorSchemeColors(ContextCompat.getColor(it, R.color.colorPrimary))
            bible_swipe_refresh.setOnRefreshListener(this)

            showProgress(false)
            loadBooks()
        }
    }

    override fun onResponseError(message: String) {
        showNotResponse()
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        showProgress(true)
        loadBooks()
    }

    override fun search(query: String) {
        showProgress(false)
        bible_books_recycler?.let { recycler ->
            (recycler.adapter as? BibleAdapter)?.updateBooks(listBooks.filterArray(query))
        }
    }

    private fun initRecyclerView(){
        bible_books_recycler.layoutManager = GridLayoutManager(activity, 2)
        bible_books_recycler.adapter = BibleAdapter(this, this.listBooks)
    }

    private fun loadBooks(){
        viewModel.getBooks().observe(this, Observer<ArrayList<Book>>{ books ->
            listBooks = books
            initRecyclerView()
            showBooksBible()
        })
    }

    internal fun goDetails(book: Book){
        val intent = Intent(activity, DynamicChaptersActivity::class.java)
        intent.putExtra(KEY_BOOK_SELECTED, book)
        startActivity(intent)
    }

    private fun showBooksBible(){
        bible_books_recycler.visibility = View.VISIBLE
        bible_not_response_text.visibility = View.GONE
        hideProgress()
    }

    private fun showNotResponse(){
        bible_books_recycler.visibility = View.GONE
        bible_not_response_text.visibility = View.VISIBLE
        hideProgress()
    }

    private fun showProgress(isSwipe: Boolean){
        bible_swipe_refresh.isRefreshing = isSwipe
        bible_progress.isVisible = !isSwipe
        if (bible_not_response_text.isVisible){
            bible_not_response_text.isVisible = false
        }
    }

    internal fun hideProgress(){
        with(bible_swipe_refresh) {
            if (this.isRefreshing) {
                this.isRefreshing = false
                this.clearAnimation()
            }
        }
        bible_progress.visibility = View.GONE
    }
}
