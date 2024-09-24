package com.techtest.daffa_github_user

import android.app.Application
import com.techtest.daffa_github_user.di.databaseModule
import com.techtest.daffa_github_user.di.networkModule
import com.techtest.daffa_github_user.di.repositoryModule
import com.techtest.daffa_github_user.di.utilModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    utilModule
                )
            )
        }
    }
}