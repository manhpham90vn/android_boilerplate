package com.example.baseandroid.ui

import android.app.Application
import com.example.baseandroid.BuildConfig
import com.example.baseandroid.di.AppComponent
import com.example.baseandroid.di.DaggerAppComponent
import timber.log.Timber

class MyApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}