package com.techtest.daffa_github_user.app

import com.techtest.daffa_github_user.BuildConfig
import android.app.Application
import com.techtest.daffa_github_user.app.di.databaseModule
import com.techtest.daffa_github_user.app.di.networkModule
import com.techtest.daffa_github_user.app.di.repositoryModule
import com.techtest.daffa_github_user.app.di.useCaseModule
import com.techtest.daffa_github_user.app.di.utilModule
import com.techtest.daffa_github_user.app.di.viewModelModule
import com.techtest.daffa_github_user.app.logging.ReleaseTree
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,
                    utilModule
                )
            )
        }
    }
}