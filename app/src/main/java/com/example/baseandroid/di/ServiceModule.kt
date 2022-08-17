package com.example.baseandroid.di

import android.content.Context
import com.example.baseandroid.networking.ApiErrorHandler
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    @AppScope
    @Provides
    fun apiErrorHandler(context: Context, gson: Gson, appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface): ApiErrorHandler {
        return ApiErrorHandler(context, gson, appLocalDataRepositoryInterface)
    }
}
