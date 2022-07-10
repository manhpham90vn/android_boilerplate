package com.example.baseandroid.di

import android.content.Context
import com.example.baseandroid.ui.home.HomeActivity
import com.example.baseandroid.ui.login.LoginActivity
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AppModule::class, NetworkModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: HomeActivity)
    fun inject(activity: LoginActivity)
}