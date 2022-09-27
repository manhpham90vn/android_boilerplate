package com.example.baseandroid.di

import android.content.Context
import com.example.baseandroid.networking.ApiErrorHandler
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.service.MyApplicationFirebaseMessagingService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun apiErrorHandler(@ApplicationContext context: Context, gson: Gson, appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface): ApiErrorHandler {
        return ApiErrorHandler(context, gson, appLocalDataRepositoryInterface)
    }

    @Provides
    fun firebaseMessagingService(): MyApplicationFirebaseMessagingService {
        return MyApplicationFirebaseMessagingService()
    }
}
