package com.sunkitto.news

import android.app.Application
import com.sunkitto.news.di.AppComponent
import com.sunkitto.news.di.DaggerAppComponent

class NewsApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .apiKey(BuildConfig.API_KEY)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}