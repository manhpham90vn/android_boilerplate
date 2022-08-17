package com.example.baseandroid.di

import com.example.baseandroid.ui.detail.DetailImageFragment
import com.example.baseandroid.ui.detail.DetailWebFragment
import com.example.baseandroid.ui.home.HomeFragment
import com.example.baseandroid.ui.login.LoginFragment
import com.example.baseandroid.ui.login.LoginSuccessFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun contributeLoginSuccessFragment(): LoginSuccessFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailWebFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailImageFragment(): DetailImageFragment
}
