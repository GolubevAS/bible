package com.gg.project.bible.analytics

import android.app.Application
import com.google.firebase.FirebaseApp

class MyAnalytics : Application() {

    // connecting analytics
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this@MyAnalytics)
    }


}