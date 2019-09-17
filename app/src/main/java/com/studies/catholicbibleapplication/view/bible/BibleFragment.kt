package com.studies.catholicbibleapplication.view.bible

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.studies.catholicbibleapplication.R
import com.studies.catholicbibleapplication.model.call.IGenericProtocol
import com.studies.catholicbibleapplication.model.entity.Book
import com.studies.catholicbibleapplication.view.chapter.ChapterActivity
import com.studies.catholicbibleapplication.view.chapter.ChapterActivity.Companion.KEY_BOOK_SELECTED
import com.studies.catholicbibleapplication.view.viewmodel.BibleViewModel
import kotlinx.android.synthetic.main.fragment_bible.*
import kotlinx.android.synthetic.main.generic_toolbar.*


class BibleFragment: Fragment(),
    IGenericProtocol,
    View.OnFocusChangeListener,
    SearchView.OnQueryTextListener,
    SearchView.OnCloseListener,
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

            initListeners()
            showProgress()
            onRefresh()
        }
    }

    override fun onResponseError(message: String) {
        showNotResponse()
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        view?.let {
            when (it){
                generic_search_button -> {
                    generic_title_toolbar.visibility = if (hasFocus) View.GONE else View.VISIBLE
                }
            }
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            showProgress()
            searchBooks(query)
        }
        return false
    }

    override fun onClose(): Boolean {
        showProgress()
        onRefresh()
        return false
    }

    override fun onRefresh() {
        loadBooks()
    }

    private fun initListeners(){
        generic_search_button.setOnQueryTextListener(this)
        generic_search_button.setOnCloseListener(this)
        generic_search_button.setOnQueryTextFocusChangeListener(this)
        bible_swipe_refresh.setOnRefreshListener(this)
    }

    private fun initRecyclerView(){
        bible_books_recycler.layoutManager = GridLayoutManager(activity, 2)
        bible_books_recycler.adapter = BibleAdapter(this, listBooks)
    }

    private fun loadBooks(){
        viewModel.getBooks().observe(this, Observer<ArrayList<Book>>{ books ->
            listBooks = books
            showBooksBible()
            initRecyclerView()
        })
    }

    private fun searchBooks(query: String){
        val booksFound = ArrayList<Book>()
        for (item in listBooks){
            if (item.abbrev.contains(query, true)
                || item.author.contains(query, true)
                || item.chapters.contains(query, true)
                || item.group.contains(query, true)
                || item.name.contains(query, true)
                || item.testament.contains(query, true)){
                booksFound.add(item)
            }
        }
        with(bible_books_recycler.adapter as BibleAdapter){
            this.books = booksFound
            this.notifyDataSetChanged()
            hideProgress()
        }
    }

    internal fun goDetails(book: Book){
        val intent = Intent(activity, ChapterActivity::class.java)
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

    private fun showProgress(){
        bible_swipe_refresh.isRefreshing = true
    }

    private fun hideProgress(){
        if (bible_swipe_refresh.isRefreshing) {
            bible_swipe_refresh.isRefreshing = false
            bible_swipe_refresh.clearAnimation()
        }
    }
}
