package com.studies.catholicbible.view.main

import android.view.MenuItem
import com.studies.catholicbible.R

enum class MainMenuSelected(val position: Int,
                            val menuId: Int,
                            val title: Int){
    BIBLE(0,
        R.id.item_menu_bible,
        R.string.st_bible_title_toolbar),
    FAVORITES(1,
        R.id.item_menu_favorites,
        R.string.st_favorite_title_toolbar),
    DAILY_VERSE(2,
        R.id.item_menu_daily_verse,
        R.string.st_daily_verse_title_toolbar);

    companion object {
        fun getSelected(menuItem: MenuItem?, position: Int?): MainMenuSelected {
            var menuSelected = BIBLE
            for (item in values()) {
                if (menuItem != null && item.menuId == menuItem.itemId) {
                    menuSelected = item
                    break
                }
                if (position != null && item.position == position) {
                    menuSelected = item
                    break
                }
                continue
            }
            return menuSelected
        }
    }
}