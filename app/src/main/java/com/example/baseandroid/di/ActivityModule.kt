package com.example.baseandroid.di

import com.example.baseandroid.ui.proxy.ProxyActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): ProxyActivity
}
