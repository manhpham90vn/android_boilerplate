package com.example.baseandroid

import android.app.Application
import com.example.baseandroid.data.SharedPreferencesStorage
import com.example.baseandroid.networking.NetworkModule
import com.example.baseandroid.repository.AppLocalDataRepository
import com.example.baseandroid.repository.AppRemoteDataRepository

class MyApplication: Application() {

    val appLocalData by lazy {
        AppLocalDataRepository(SharedPreferencesStorage(this))
    }

    val appRemoteData by lazy {
        AppRemoteDataRepository(NetworkModule(appLocalData).provideAppApi())
    }

}