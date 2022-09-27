package com.example.baseandroid.ui.application

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.baseandroid.BuildConfig
import com.example.baseandroid.service.MyApplicationDefaultLifecycleObserver
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeTimber()
        ProcessLifecycleOwner.get().lifecycle.addObserver(MyApplicationDefaultLifecycleObserver())
    }

    private fun initializeTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
