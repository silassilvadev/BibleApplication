package com.studies.catholicbibleapplication.view.chapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.studies.catholicbibleapplication.R
import com.studies.catholicbibleapplication.model.entity.Verse
import kotlinx.android.synthetic.main.item_chapter_book.view.*

class ChapterAdapter(var activity: ChapterActivity,
                     var verses: ArrayList<Verse>): RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        return ChapterViewHolder(
            LayoutInflater.from(activity)
                .inflate(R.layout.item_chapter_book, parent, false))
    }

    override fun getItemCount(): Int = verses.size

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        with(verses[position]){
            holder.itemView.chapter_verse_number_text.text = this.number.toString()
            holder.itemView.chapter_verse_description_text.text = this.text

            holder.itemView.setOnClickListener {
                activity.markVerse(this)
            }
        }
    }

    class ChapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}