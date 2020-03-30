package com.studies.bibleapplication.ui.main.favorites

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
import com.studies.bibleapplication.utils.extensions.filterArray
import com.studies.bibleapplication.utils.extensions.showToast
import com.studies.bibleapplication.ui.call.IProtocolResponse
import com.studies.bibleapplication.ui.call.ISearchResponse
import com.studies.bibleapplication.entity.ReadingBook
import com.studies.bibleapplication.ui.viewmodel.FirebaseViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(),
    ISearchResponse,
    IProtocolResponse,
    SwipeRefreshLayout.OnRefreshListener{

    private lateinit var itemSelected: ReadingBook
    private lateinit var readingBooks: ArrayList<ReadingBook>
    private lateinit var viewModel: FirebaseViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            viewModel = ViewModelProviders.of(this).get(FirebaseViewModel::class.java)
            viewModel.setProtocol(this)

            favorites_swipe_refresh.setColorSchemeColors(ContextCompat.getColor(it, R.color.colorPrimary))
            favorites_swipe_refresh.setOnRefreshListener(this)

            showProgress(false)
            loadFavorites()
        }
    }

    override fun onRefresh() {
        showProgress(true)
        loadFavorites()
    }

    override fun search(query: String) {
        showProgress(false)
        favorites_books_recycler?.let { recycler ->
            (recycler.adapter as? FavoritesAdapter)?.updateFavorites(this.readingBooks.filterArray(query))
        }
    }

    override fun onResponseError(message: String) {
        hideProgress()
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun loadFavorites(){
        viewModel.getFavorites().observe(this, Observer<ArrayList<ReadingBook>>{ itFavorites ->
            this.readingBooks = itFavorites
            initRecyclerView()
            hideProgress()
        })
    }

    private fun initRecyclerView(){
        favorites_books_recycler.layoutManager = LinearLayoutManager(activity)
        favorites_books_recycler.adapter = FavoritesAdapter(this, this.readingBooks)
    }

    internal fun selectedConfigure(favorite: ReadingBook) {
        this.itemSelected = favorite
        showToast("Cliquei no ${favorite.book.name}")
    }

    private fun showProgress(isSwipe: Boolean){
        favorites_swipe_refresh.isRefreshing = isSwipe
        favorites_progress.isVisible = !isSwipe
        if (favorites_not_response_text.isVisible){
            favorites_not_response_text.isVisible = false
        }
    }

    internal fun hideProgress(){
        with(favorites_swipe_refresh) {
            if (this.isRefreshing) {
                this.isRefreshing = false
                this.clearAnimation()
            }
        }
        favorites_progress.visibility = View.GONE
    }
}
