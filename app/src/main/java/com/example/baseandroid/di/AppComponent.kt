package com.example.baseandroid.di

import android.content.Context
import com.example.baseandroid.ui.application.MyApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@AppScope
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ServiceModule::class,
    NetworkModule::class,
    ActivityModule::class,
    FragmentModule::class
])
interface AppComponent: AndroidInjector<MyApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}