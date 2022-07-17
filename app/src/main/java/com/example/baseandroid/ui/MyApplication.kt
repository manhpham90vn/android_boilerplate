package com.example.baseandroid.ui

import androidx.lifecycle.ProcessLifecycleOwner
import com.example.baseandroid.BuildConfig
import com.example.baseandroid.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class MyApplication: DaggerApplication() {

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

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

}
