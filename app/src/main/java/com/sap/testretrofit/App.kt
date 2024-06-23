package com.sap.testretrofit

import android.app.Application
import android.util.Log
import com.sap.testretrofit.di.appModule
import com.sap.testretrofit.di.databaseModule
import com.sap.testretrofit.di.gson
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class App : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            Log.d("KoinInit", "Starting Koin")
            modules(databaseModule, gson, appModule)
        }
    }
}