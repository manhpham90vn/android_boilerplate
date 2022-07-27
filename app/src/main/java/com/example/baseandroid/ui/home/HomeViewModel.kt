package com.example.baseandroid.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.networking.RefreshTokenValidator
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import com.example.baseandroid.usecase.GetUserInfoUseCase
import com.example.baseandroid.usecase.PagingDataSortType
import com.example.baseandroid.usecase.PagingDataUseCase
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val localDataRepositoryInterface: AppLocalDataRepositoryInterface,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val pagingDataUseCase: PagingDataUseCase
) : BaseViewModel() {

    val listItem = MutableLiveData<PagingData<PagingUserResponse>>()
    private var filterType: PagingDataSortType = PagingDataSortType.ASCENDING

    init {
        Observables.combineLatest(getUserInfoUseCase.processing, pagingDataUseCase.processing)
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

        pagingDataUseCase.apply {
            succeeded
                .subscribe {
                    listItem.value = it
                }
                .addTo(compositeDisposable)

            failed
                .subscribe {
                    singleLiveError.postValue(it)
                    listItem.value = PagingData.empty()
                }
                .addTo(compositeDisposable)
        }
    }

    fun callApi() {
        getUserInfoUseCase.execute(Unit)
        pagingDataUseCase.execute(filterType)
    }

    fun sort() {
        filterType = if (filterType == PagingDataSortType.ASCENDING) {
            PagingDataSortType.DESCENDING
        } else {
            PagingDataSortType.ASCENDING
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
        pagingDataUseCase.onCleared()
    }
}
