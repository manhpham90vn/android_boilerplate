package com.example.baseandroid.ui

import android.app.Application
import com.example.baseandroid.di.AppComponent
import com.example.baseandroid.di.DaggerAppComponent

class MyApplication: Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }

}