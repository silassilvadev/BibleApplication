package com.studies.bibleapplication.ui.main.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.studies.bibleapplication.R
import com.studies.bibleapplication.entity.ReadingBook
import kotlinx.android.synthetic.main.item_favorites.view.*

class FavoritesAdapter(var fragment: FavoritesFragment,
                       var readingBooks: ArrayList<ReadingBook>): RecyclerView.Adapter<FavoritesAdapter.BibleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BibleViewHolder {
        return BibleViewHolder(LayoutInflater.from(fragment.activity)
            .inflate(R.layout.item_favorites, parent, false))
    }

    override fun getItemCount(): Int = readingBooks.size

    override fun onBindViewHolder(holder: BibleViewHolder, position: Int) {
        with(readingBooks[position]) {
            fragment.activity?.let { activity ->
                holder.itemView.item_favorites_abbrev.text = this.abbrev
                holder.itemView.item_favorites_name.text = this.name
                holder.itemView.item_favorites_group.text = this.group
                holder.itemView.item_favorites_author.text = this.author
                holder.itemView.item_favorites_chapters.text =
                    activity.getString(R.string.st_favorites_number_chapters, this.chapter.number.toString())

                holder.itemView.setOnLongClickListener {
                    it.isSelected = !it.isSelected
                    fragment.selectedConfigure(this)
                    true
                }

            }
        }
    }

    internal fun updateFavorites(favorites: ArrayList<ReadingBook>){
        this.readingBooks = favorites
        notifyDataSetChanged()
        fragment.hideProgress()
    }

    class BibleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}