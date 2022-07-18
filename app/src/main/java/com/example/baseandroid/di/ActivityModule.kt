package com.example.baseandroid.di

import com.example.baseandroid.ui.detail.DetailActivity
import com.example.baseandroid.ui.home.HomeActivity
import com.example.baseandroid.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeDetailActivity(): DetailActivity
}
