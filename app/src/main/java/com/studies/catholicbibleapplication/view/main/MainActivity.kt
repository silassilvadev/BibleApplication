package com.studies.catholicbibleapplication.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.get
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.studies.catholicbibleapplication.R
import com.studies.catholicbibleapplication.extensions.onPageSelected
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var backCount = 0
    private var menuSelected = MainMenuSelected.BIBLE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        selectPage(menuItem = item)
        return true
    }

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.size > 0 && backCount < 1 ) {
            selectPage(indexCount = 1)

            Toast.makeText(this,
                getString(R.string.st_generic_message_click_exit),
                Toast.LENGTH_SHORT).show()

        } else {
            super.onBackPressed()
        }
    }

    private fun initViews() {
        main_container_view_pager.adapter = MainAdapter(supportFragmentManager)
        main_bottom_navigation.setOnNavigationItemSelectedListener(this)
        main_container_view_pager.onPageSelected {
            selectPage(position = it)
        }
    }

    private fun selectPage(menuItem: MenuItem? = null, position: Int? = null, indexCount: Int = 0){
        backCount = indexCount
        menuSelected = MainMenuSelected.getSelected(menuItem, position)
        main_container_view_pager.currentItem = menuSelected.position
        main_bottom_navigation.menu[menuSelected.position].isChecked = true
    }
}
