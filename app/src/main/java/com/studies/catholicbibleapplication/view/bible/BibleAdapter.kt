package com.studies.catholicbibleapplication.view.bible

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.studies.catholicbibleapplication.R
import com.studies.catholicbibleapplication.model.entity.Book
import kotlinx.android.synthetic.main.item_book_bible.view.*

class BibleAdapter(var fragment: BibleFragment,
                   var books: ArrayList<Book>): RecyclerView.Adapter<BibleAdapter.BibleViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BibleViewHolder {
        return BibleViewHolder(LayoutInflater.from(fragment.activity)
            .inflate(R.layout.item_book_bible, parent, false))
    }

    override fun getItemCount(): Int = books.size

    override fun onBindViewHolder(holder: BibleViewHolder, position: Int) {
        with(books[position]) {
            fragment.activity?.let { activity ->
                holder.itemView.item_book_abbrev.text = this.abbrev
                holder.itemView.item_book_group.text = activity.getString(R.string.st_bible_item_chapters, this.group)
                holder.itemView.item_book_author.text = activity.getString(R.string.st_bible_item_author, this.author)
                holder.itemView.item_book_chapters.text = activity.getString(R.string.st_bible_item_chapters, this.chapters)
            }

            holder.itemView.setOnClickListener {
                fragment.goDetails(this)
            }
        }
    }

    class BibleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}