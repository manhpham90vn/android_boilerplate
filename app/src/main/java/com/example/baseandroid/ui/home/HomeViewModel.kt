package com.example.baseandroid.ui.home

import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRefreshableRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import io.reactivex.rxjava3.core.Single.timer
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor(): BaseViewModel() {

    @Inject lateinit var appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface
    @Inject lateinit var appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface

    fun callApi() {
        timer(500, TimeUnit.MILLISECONDS)
            .flatMap { appRemoteDataRefreshableRepositoryInterface.getUserInfo() }
            .subscribe()
            .addTo(compositeDisposable)

        timer(1000, TimeUnit.MILLISECONDS)
            .flatMap { appRemoteDataRefreshableRepositoryInterface.getUserInfo() }
            .subscribe()
            .addTo(compositeDisposable)

        timer(1500, TimeUnit.MILLISECONDS)
            .flatMap { appRemoteDataRefreshableRepositoryInterface.getUserInfo() }
            .subscribe()
            .addTo(compositeDisposable)

        timer(2000, TimeUnit.MILLISECONDS)
            .flatMap { appRemoteDataRefreshableRepositoryInterface.getUserInfo() }
            .subscribe()
            .addTo(compositeDisposable)

        timer(2500, TimeUnit.MILLISECONDS)
            .flatMap { appRemoteDataRefreshableRepositoryInterface.getUserInfo() }
            .subscribe()
            .addTo(compositeDisposable)

        // call at the same time
        appRemoteDataRefreshableRepositoryInterface
            .getUserInfo()
            .subscribe()
            .addTo(compositeDisposable)

        appRemoteDataRefreshableRepositoryInterface
            .getUserInfo()
            .subscribe()
            .addTo(compositeDisposable)

        appRemoteDataRefreshableRepositoryInterface
            .getList(1)
            .subscribe()
            .addTo(compositeDisposable)

        appRemoteDataRefreshableRepositoryInterface
            .getList(1)
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun cleanData() {
        appLocalDataRepositoryInterface.cleanRefreshToken()
        appLocalDataRepositoryInterface.cleanToken()
    }

}