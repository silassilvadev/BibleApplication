package com.studies.bibleapplication.ui.main.daily

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.studies.bibleapplication.R
import com.studies.bibleapplication.entity.ReadingBook
import kotlinx.android.synthetic.main.item_daily_verses.view.*

class DailyVerseAdapter(var fragment: DailyVerseFragment,
                        var readingBooks: ArrayList<ReadingBook>): RecyclerView.Adapter<DailyVerseAdapter.BibleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BibleViewHolder {
        return BibleViewHolder(LayoutInflater.from(fragment.activity)
            .inflate(R.layout.item_daily_verses, parent, false))
    }

    override fun getItemCount(): Int = readingBooks.size

    override fun onBindViewHolder(holder: BibleViewHolder, position: Int) {
        with(readingBooks[position]) {
            fragment.activity?.let { activity ->

                holder.itemView.item_daily_verse_text.text = this.verses.first().text

                holder.itemView.item_daily_verse_book_details.text =
                    activity.getString(R.string.st_daily_verse_details,
                        this.book.abbrev,
                        this.chapter.number.toString(),
                        this.verses.first().number.toString())

                holder.itemView.item_daily_verse_date.text = this.date.toString()
            }
        }
    }

    internal fun updateFavorites(dailyVerses: ArrayList<ReadingBook>){
        this.readingBooks = dailyVerses
        notifyDataSetChanged()
        fragment.hideProgress()
    }

    class BibleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}