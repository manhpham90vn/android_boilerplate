package com.manhpham.baseandroid.di

import com.manhpham.baseandroid.service.ConnectivityService
import com.manhpham.baseandroid.service.ConnectivityServiceImpl
import com.manhpham.baseandroid.data.local.SharedPreferencesStorage
import com.manhpham.baseandroid.data.local.Storage
import com.manhpham.baseandroid.repository.*
import com.manhpham.baseandroid.service.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
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

    @Binds
    abstract fun provideAppPagingDataRepositoryInterface(instance: AppPagingDataRepository): AppPagingDataRepositoryInterface
}
