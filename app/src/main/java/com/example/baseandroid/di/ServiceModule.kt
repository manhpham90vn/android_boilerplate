package com.example.baseandroid.di

import com.example.baseandroid.ui.application.MyApplicationFirebaseMessagingService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServiceModule {
    @ContributesAndroidInjector
    abstract fun contributeMyApplicationFirebaseMessagingService(): MyApplicationFirebaseMessagingService
}