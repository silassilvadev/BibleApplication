package com.studies.bibleapplication.model

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.studies.bibleapplication.BuildConfig
import com.studies.bibleapplication.model.client.ClientService

class BibleApplication: Application() {

    companion object {
        lateinit var application: BibleApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        ClientService().startServices()
        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(BuildConfig.ANALYTICS)
    }
}