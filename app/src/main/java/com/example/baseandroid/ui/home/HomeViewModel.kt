package com.example.baseandroid.ui.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.networking.RefreshTokenValidator
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import com.example.baseandroid.usecase.GetUserInfoUseCase
import com.example.baseandroid.usecase.PagingUseCase
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.addTo
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor() : BaseViewModel() {

    @Inject lateinit var getUserInfoUseCase: GetUserInfoUseCase

    @Inject lateinit var pagingUseCase: PagingUseCase

    @Inject lateinit var localDataRepositoryInterface: AppLocalDataRepositoryInterface

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
        getUserInfoUseCase.execute(Unit)
        pagingUseCase.execute(page)

        Observables.combineLatest(getUserInfoUseCase.processing, pagingUseCase.processing)
            .map {
                return@map it.first || it.second
            }
            .subscribe {
                isLoadingSingleLive.postValue(it)
            }
            .addTo(compositeDisposable)

        getUserInfoUseCase.apply {
            succeeded
                .subscribe {
                    Timber.d(it.email)
                }
                .addTo(compositeDisposable)

            failed
                .subscribe {
                    singleLiveError.postValue(it)
                }
                .addTo(compositeDisposable)
        }

        pagingUseCase.apply {
            succeeded
                .subscribe {
                    if (page == 1) {
                        if (!it.array.isNullOrEmpty()) {
                            listItem1.value = it.array.toMutableList()
                        } else {
                            listItem1.value = mutableListOf()
                        }
                    } else if (page == 2) {
                        if (!it.array.isNullOrEmpty()) {
                            listItem2.value = it.array.toMutableList()
                        } else {
                            listItem2.value = mutableListOf()
                        }
                    }
                }
                .addTo(compositeDisposable)

            failed
                .subscribe {
                    singleLiveError.postValue(it)
                }
                .addTo(compositeDisposable)
        }
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
        localDataRepositoryInterface.cleanRefreshToken()
        localDataRepositoryInterface.cleanToken()
        RefreshTokenValidator.getInstance().lastFailedDate = null
    }

    override fun onCleared() {
        super.onCleared()
        getUserInfoUseCase.onCleared()
        pagingUseCase.onCleared()
    }
}
