package com.sap.testretrofit

import android.app.Application
import android.util.Log
import com.sap.testretrofit.di.appModule
import com.sap.testretrofit.presentation.screen.TokenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module


class App : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.DEBUG)
            Log.d("KoinInit", "Starting Koin")
            modules(appModule)
        }
    }
}