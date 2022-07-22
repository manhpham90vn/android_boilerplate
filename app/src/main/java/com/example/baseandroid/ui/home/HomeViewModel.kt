package com.example.baseandroid.ui.home

import androidx.lifecycle.MediatorLiveData
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

    private val listItem1 = MutableLiveData<MutableList<PagingUserResponse>>()
    private val listItem2 = MutableLiveData<MutableList<PagingUserResponse>>()
    private var page = 1
    val list = MediatorLiveData<MutableList<PagingUserResponse>>()

    init {
        list.addSource(listItem1) {
            if (page == 1) {
                it.let { list.value = it }
            }
        }

        list.addSource(listItem2) {
            if (page == 2) {
                it.let { list.value = it }
            }
        }
    }

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
                if (!it.array.isNullOrEmpty()) {
                    listItem1.value = it.array.toMutableList()
                } else {
                    listItem1.value = mutableListOf()
                }
            }, {
                singleLiveError.postValue(it)
            })
            .addTo(compositeDisposable)

        appRemoteDataRefreshableRepositoryInterface
            .getList(2)
            .subscribe({
                if (!it.array.isNullOrEmpty()) {
                    listItem2.value = it.array.toMutableList()
                } else {
                    listItem2.value = mutableListOf()
                }
            }, {
                singleLiveError.postValue(it)
            })
            .addTo(compositeDisposable)
    }

    fun sort() {
        page = if (page == 1) {
            2
        } else {
            1
        }
        callApi()
    }

    fun cleanData() {
        appLocalDataRepositoryInterface.cleanRefreshToken()
        appLocalDataRepositoryInterface.cleanToken()
        RefreshTokenValidator.getInstance().lastFailedDate = null
    }
}
