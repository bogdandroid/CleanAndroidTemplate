package com.example.highlevelarch

import android.app.Application
import com.example.highlevelarch.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HighLevelArchApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@HighLevelArchApp)
            modules(appModule)
        }
    }
}
