package com.studies.catholicbibleapplication.view.main

import android.view.MenuItem
import com.studies.catholicbibleapplication.R

enum class MainMenuSelected(val position: Int,
                            val menuId: Int){
    BIBLE(0,
        R.id.item_menu_bible),
    FAVORITES(1,
        R.id.item_menu_favorites),
    DAILY_VERSE(2,
        R.id.item_menu_daily_verse);

    companion object {
        var menuValues = values()
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