package com.example.baseandroid.di

import android.content.Context
import com.example.baseandroid.networking.ApiErrorHandler
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    @AppScope
    @Provides
    fun apiErrorHandler(context: Context, appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface): ApiErrorHandler {
        return ApiErrorHandler(context, appLocalDataRepositoryInterface)
    }
}
