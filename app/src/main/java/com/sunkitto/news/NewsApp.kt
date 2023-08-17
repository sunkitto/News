package com.sunkitto.news

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.fragment.app.strictmode.FragmentStrictMode
import com.sunkitto.news.all_news.di.AllNewsDependencyStore
import com.sunkitto.news.di.AppComponent
import com.sunkitto.news.di.DaggerAppComponent
import com.sunkitto.news.feature.settings.di.SettingsDependencyStore
import com.sunkitto.news.feature.top_headlines.di.TopHeadlinesDependencyStore

class NewsApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .applicationContext(this.applicationContext)
            .apiKey(BuildConfig.API_KEY)
            .build()
    }

    override fun onCreate() {
        TopHeadlinesDependencyStore.dependencies = appComponent
        AllNewsDependencyStore.dependencies = appComponent
        SettingsDependencyStore.dependencies = appComponent

        if (BuildConfig.DEVELOPER_MODE) {
            FragmentStrictMode.defaultPolicy =
                FragmentStrictMode.Policy.Builder()
                    .detectFragmentReuse()
                    .detectFragmentTagUsage()
                    .detectRetainInstanceUsage()
                    .detectSetUserVisibleHint()
                    .detectTargetFragmentUsage()
                    .detectWrongFragmentContainer()
                    .build()

            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .detectCustomSlowCalls()
                    .build(),
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build(),
            )
        }
        super.onCreate()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is NewsApp -> appComponent
        else -> applicationContext.appComponent
    }