package com.studies.catholicbibleapplication.model

import android.app.Application

class CatholicBibleApplication: Application() {

    companion object {
        lateinit var application: CatholicBibleApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }
}