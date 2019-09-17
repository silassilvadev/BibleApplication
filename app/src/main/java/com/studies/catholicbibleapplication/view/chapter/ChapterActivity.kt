package com.studies.catholicbibleapplication.view.chapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.studies.catholicbibleapplication.R
import com.studies.catholicbibleapplication.model.call.IGenericProtocol
import com.studies.catholicbibleapplication.model.entity.Book
import com.studies.catholicbibleapplication.model.entity.BookSelected
import com.studies.catholicbibleapplication.model.entity.Verse
import com.studies.catholicbibleapplication.view.viewmodel.BibleViewModel
import kotlinx.android.synthetic.main.activity_chapter.*
import kotlinx.android.synthetic.main.fragment_chapter.*
import kotlinx.android.synthetic.main.fragment_chapter.chapter_not_response_text
import kotlinx.android.synthetic.main.fragment_chapter.chapter_recycler
import kotlinx.android.synthetic.main.fragment_chapter.chapter_swipe_refresh
import kotlinx.android.synthetic.main.generic_toolbar.*

class ChapterActivity :
    AppCompatActivity(),
    IGenericProtocol,
    View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: BibleViewModel
    private lateinit var book: Book
    private lateinit var bookSelected: BookSelected
    private var chapter = 1

    companion object {
        const val KEY_BOOK_SELECTED = "bookSelected"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)
        receiveParams()

        viewModel = ViewModelProviders.of(this).get(BibleViewModel::class.java)
        viewModel.setProtocol(this)

        configureViews()
        initListeners()
        showProgress()
    }

    override fun onClick(view: View?) {
        view?.let {
            when (it){
                generic_back_button_toolbar -> onBackPressed()
            }
        }
    }

    override fun onRefresh() {
        loadChapters()
    }

    override fun onResponseError(message: String) {
        showNotResponse()
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun receiveParams(){
        intent.getSerializableExtra(KEY_BOOK_SELECTED)?.let {
            book = it as Book
        } ?: run {
            Toast.makeText(this, "Erro ao carregar versículos", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun configureViews() {
        chapter_swipe_refresh.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary))
        generic_title_toolbar.text = book.name
        generic_back_button_toolbar.visibility = View.VISIBLE
    }

    private fun initListeners(){
        generic_back_button_toolbar.setOnClickListener(this)
        chapter_swipe_refresh.setOnRefreshListener(this)
    }

    private fun showProgress(){
        chapter_swipe_refresh.isRefreshing = true
        onRefresh()
    }

    private fun hideProgress(){
        with(chapter_swipe_refresh) {
            if (this.isRefreshing) {
                this.isRefreshing = false
                this.clearAnimation()
            }
        }
    }

    private fun loadChapters(){
        viewModel.getChapter(book.abbrev, chapter)
            .observe(this, Observer<BookSelected> { response ->
                response?.let {
                    bookSelected = it
                    showChapter()
                    initRecyclerView()
                }
            })
    }

    private fun showChapter(){
        chapter_recycler.visibility = View.VISIBLE
        chapter_not_response_text.visibility = View.GONE
        hideProgress()
    }

    private fun showNotResponse(){
        chapter_recycler.visibility = View.GONE
        chapter_not_response_text.visibility = View.VISIBLE
        hideProgress()
    }

    private fun initRecyclerView(){
        chapter_recycler.layoutManager = LinearLayoutManager(this)
        chapter_recycler.adapter = ChapterAdapter(this, bookSelected.verses)
    }

    internal fun markVerse(verse: Verse) {
        Toast.makeText(this, "Cliquei no versículo ${verse.number}", Toast.LENGTH_SHORT).show()
    }
}