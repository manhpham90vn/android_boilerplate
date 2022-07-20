package com.example.baseandroid.di

import android.content.Context
import com.example.baseandroid.networking.ApiErrorHandler
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {
    @Provides
    fun apiErrorHandler(context: Context): ApiErrorHandler {
        return ApiErrorHandler(context)
    }
}
