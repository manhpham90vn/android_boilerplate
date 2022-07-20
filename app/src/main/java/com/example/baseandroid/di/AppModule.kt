package com.example.baseandroid.di

import com.example.baseandroid.common.ApplicationSchedulerProvider
import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.ConnectivityServiceImpl
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.data.local.SharedPreferencesStorage
import com.example.baseandroid.data.local.Storage
import com.example.baseandroid.repository.* // ktlint-disable no-wildcard-imports
import com.example.baseandroid.ui.application.MyApplicationFirebaseMessagingService
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppModule {

    @Binds
    abstract fun provideConnectivityService(instance: ConnectivityServiceImpl): ConnectivityService

    @Binds
    abstract fun provideSchedulerProvider(instance: ApplicationSchedulerProvider): SchedulerProvider

    @Binds
    abstract fun provideStorage(instance: SharedPreferencesStorage): Storage

    @Binds
    abstract fun provideAppLocalDataRepositoryInterface(instance: AppLocalDataRepository): AppLocalDataRepositoryInterface

    @Binds
    abstract fun provideAppRemoteDataRepositoryInterface(instance: AppRemoteDataRepository): AppRemoteDataRepositoryInterface

    @Binds
    abstract fun provideAppRemoteDataRefreshableRepositoryInterface(instance: AppRemoteDataRefreshableRepository): AppRemoteDataRefreshableRepositoryInterface

    @ContributesAndroidInjector
    abstract fun contributeMyApplicationFirebaseMessagingService(): MyApplicationFirebaseMessagingService
}
