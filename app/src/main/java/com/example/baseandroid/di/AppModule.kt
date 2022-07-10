package com.example.baseandroid.di

import com.example.baseandroid.data.local.SharedPreferencesStorage
import com.example.baseandroid.data.local.Storage
import com.example.baseandroid.repository.*
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideStorage(instance: SharedPreferencesStorage): Storage

    @Binds
    abstract fun provideAppLocalDataRepositoryInterface(instance: AppLocalDataRepository): AppLocalDataRepositoryInterface

    @Binds
    abstract fun provideAppRemoteDataRepositoryInterface(instance: AppRemoteDataRepository): AppRemoteDataRepositoryInterface

    @Binds
    abstract fun provideRefreshTokenRepositoryInterface(instance: RefreshTokenRepository): RefreshTokenRepositoryInterface
}