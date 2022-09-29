package com.example.baseandroid.di

import com.example.baseandroid.service.MyApplicationFirebaseMessagingService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun firebaseMessagingService(): MyApplicationFirebaseMessagingService {
        return MyApplicationFirebaseMessagingService()
    }
}
