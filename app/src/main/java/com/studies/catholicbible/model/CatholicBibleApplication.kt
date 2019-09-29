package com.studies.catholicbible.model

import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.studies.catholicbible.BuildConfig

class CatholicBibleApplication: Application() {

    companion object {
        lateinit var application: CatholicBibleApplication
    }

    override fun onCreate() {
        super.onCreate()
        application = this

        FirebaseAnalytics.getInstance(this).setAnalyticsCollectionEnabled(BuildConfig.ANALYTICS)
    }
}