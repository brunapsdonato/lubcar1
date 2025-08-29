package com.example.lubcar1.koin

import android.app.Application
import com.example.lubcar1.viewmodels.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class LubcarApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@LubcarApp)
            modules(databaseModule, viewModelModule)
        }
    }
}