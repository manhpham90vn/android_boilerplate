package com.example.baseandroid.ui.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.networking.RefreshTokenValidator
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import com.example.baseandroid.usecase.GetUserInfoUseCase
import com.example.baseandroid.usecase.PagingDataUseCaseAscending
import com.example.baseandroid.usecase.PagingDataUseCaseDescending
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

enum class HomeSortType {
    ASCENDING,
    DESCENDING
}

class HomeViewModel @Inject constructor(
    private val localDataRepositoryInterface: AppLocalDataRepositoryInterface,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val pagingDataUseCaseAscending: PagingDataUseCaseAscending,
    private val pagingDataUseCaseDescending: PagingDataUseCaseDescending
) : BaseViewModel() {

    private val listItemAscending = MutableLiveData<PagingData<PagingUserResponse>>()
    private val listItemDescending = MutableLiveData<PagingData<PagingUserResponse>>()
    private var filterType: HomeSortType = HomeSortType.ASCENDING
    val listItem = MediatorLiveData<PagingData<PagingUserResponse>>()

    init {
        listItem.addSource(listItemAscending) {
            listItem.value = it
        }

        listItem.addSource(listItemDescending) {
            listItem.value = it
        }

        Observables.combineLatest(getUserInfoUseCase.processing, pagingDataUseCaseAscending.processing, pagingDataUseCaseDescending.processing)
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
                }
                .addTo(compositeDisposable)

            failed
                .subscribe {
                    singleLiveError.postValue(it)
                }
                .addTo(compositeDisposable)
        }

        pagingDataUseCaseAscending.apply {
            succeeded
                .subscribe {
                    listItemAscending.value = it
                }
                .addTo(compositeDisposable)

            failed
                .subscribe {
                    listItemAscending.value = PagingData.empty()
                    singleLiveError.postValue(it)
                }
                .addTo(compositeDisposable)
        }

        pagingDataUseCaseDescending.apply {
            succeeded
                .subscribe {
                    listItemDescending.value = it
                }
                .addTo(compositeDisposable)

            failed
                .subscribe {
                    listItemDescending.value = PagingData.empty()
                    singleLiveError.postValue(it)
                }
                .addTo(compositeDisposable)
        }
    }

    fun callApi() {
        getUserInfoUseCase.execute(Unit)
        if (filterType == HomeSortType.ASCENDING) {
            pagingDataUseCaseAscending.execute(Unit)
        } else {
            pagingDataUseCaseDescending.execute(Unit)
        }
    }

    fun sort() {
        filterType = if (filterType == HomeSortType.ASCENDING) {
            HomeSortType.DESCENDING
        } else {
            HomeSortType.ASCENDING
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
        pagingDataUseCaseAscending.onCleared()
        pagingDataUseCaseDescending.onCleared()
    }
}
