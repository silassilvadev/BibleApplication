package com.studies.catholicbibleapplication.model

import android.app.Application

class BibleApplication: Application() {

    companion object {
        lateinit var application: BibleApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}