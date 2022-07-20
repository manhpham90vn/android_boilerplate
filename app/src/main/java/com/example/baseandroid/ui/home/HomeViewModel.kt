package com.example.baseandroid.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.networking.RefreshTokenValidator
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRefreshableRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import io.reactivex.rxjava3.core.Single.timer
import io.reactivex.rxjava3.kotlin.addTo
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    @Inject lateinit var appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface

    @Inject lateinit var appLocalDataRepositoryInterface: AppLocalDataRepositoryInterface

    val listItem = MutableLiveData<MutableList<PagingUserResponse>>()

    fun callApi() {
        isLoading.value = true
        timer(500, TimeUnit.MILLISECONDS)
            .flatMap { appRemoteDataRefreshableRepositoryInterface.getUserInfo() }
            .subscribe({
            }, {
                singleLiveError.postValue(it)
            })
            .addTo(compositeDisposable)

        timer(1000, TimeUnit.MILLISECONDS)
            .flatMap { appRemoteDataRefreshableRepositoryInterface.getUserInfo() }
            .subscribe({
            }, {
                singleLiveError.postValue(it)
            })
            .addTo(compositeDisposable)

        timer(1500, TimeUnit.MILLISECONDS)
            .flatMap { appRemoteDataRefreshableRepositoryInterface.getUserInfo() }
            .subscribe({
            }, {
                singleLiveError.postValue(it)
            })
            .addTo(compositeDisposable)

        timer(2000, TimeUnit.MILLISECONDS)
            .flatMap { appRemoteDataRefreshableRepositoryInterface.getUserInfo() }
            .subscribe({
            }, {
                singleLiveError.postValue(it)
            })
            .addTo(compositeDisposable)

        timer(2500, TimeUnit.MILLISECONDS)
            .flatMap { appRemoteDataRefreshableRepositoryInterface.getUserInfo() }
            .subscribe({
                isLoading.value = false
            }, {
                isLoading.value = false
                singleLiveError.postValue(it)
            })
            .addTo(compositeDisposable)

        // call at the same time
        appRemoteDataRefreshableRepositoryInterface
            .getUserInfo()
            .subscribe({
            }, {
                singleLiveError.postValue(it)
            })
            .addTo(compositeDisposable)

        appRemoteDataRefreshableRepositoryInterface
            .getUserInfo()
            .subscribe({
            }, {
                singleLiveError.postValue(it)
            })
            .addTo(compositeDisposable)

        appRemoteDataRefreshableRepositoryInterface
            .getList(1)
            .subscribe({
            }, {
                singleLiveError.postValue(it)
            })
            .addTo(compositeDisposable)

        appRemoteDataRefreshableRepositoryInterface
            .getList(1)
            .subscribe({
                if (!it.array.isNullOrEmpty()) {
                    listItem.value = it.array.toMutableList()
                } else {
                    listItem.value = mutableListOf()
                }
            }, {
                singleLiveError.postValue(it)
            })
            .addTo(compositeDisposable)
    }

    fun cleanData() {
        appLocalDataRepositoryInterface.cleanRefreshToken()
        appLocalDataRepositoryInterface.cleanToken()
        RefreshTokenValidator.getInstance().lastFailedDate = null
    }
}
