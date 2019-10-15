package com.studies.bibleapplication.view.chapter.verses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.studies.bibleapplication.R
import com.studies.bibleapplication.model.entity.Verse
import com.studies.bibleapplication.view.chapter.DynamicChaptersActivity
import kotlinx.android.synthetic.main.item_verse_chapter.view.*

class DynamicVerseAdapter(var activity: DynamicChaptersActivity,
                          var verses: ArrayList<Verse>): RecyclerView.Adapter<DynamicVerseAdapter.ChapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        return ChapterViewHolder(
            LayoutInflater.from(activity).inflate(R.layout.item_verse_chapter, parent, false))
    }

    override fun getItemCount(): Int = verses.size

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        verses[position].let { verse ->
            holder.itemView.let { view ->
                val colorIdStart = getColorSelected(verse.isSelected)
                view.chapter_verse_number_text.text = verse.number.toString()
                view.chapter_verse_description_text.text = verse.text
                view.chapter_verse_number_card.setCardBackgroundColor(colorIdStart)
                view.chapter_verse_description_card.setCardBackgroundColor(colorIdStart)

                view.setOnLongClickListener { item ->
                    if (!hasItemSelected()) {
                        setViewSelection(item, verse)
                        true
                    } else false
                }

                view.setOnClickListener { item ->
                    if (hasItemSelected()) {
                        setViewSelection(item, verse)
                    }
                }
            }
        }
    }

    private fun setViewSelection(view: View, verse: Verse){
        verse.isSelected = !verse.isSelected
        val colorId = getColorSelected(verse.isSelected)
        view.chapter_verse_number_card.setCardBackgroundColor(colorId)
        view.chapter_verse_description_card.setCardBackgroundColor(colorId)

        notifyItemChanged(verses.indexOf(verse))

        activity.refreshButtonsView(hasItemSelected())
    }

    private fun getColorSelected(isSelected: Boolean): Int {
        return if (isSelected) {
            ContextCompat.getColor(activity, R.color.colorLight)
        } else {
            ContextCompat.getColor(activity, android.R.color.white)
        }
    }

    internal fun hasItemSelected(): Boolean {
        for (verse in verses){
            if (verse.isSelected) return true
        }
        return false
    }

    internal fun updateVerses(updatedVerses: ArrayList<Verse>){
        this.verses = updatedVerses
        notifyDataSetChanged()
    }

    class ChapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}