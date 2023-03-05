package com.manhpham.baseandroid.viewmodel

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.manhpham.baseandroid.models.PagingUserResponse
import com.manhpham.baseandroid.repository.AppLocalDataRepositoryInterface
import com.manhpham.baseandroid.ui.base.BaseViewModel
import com.manhpham.baseandroid.usecase.GetUserInfoUseCase
import com.manhpham.baseandroid.usecase.PagingDataSortType
import com.manhpham.baseandroid.usecase.PagingDataUseCase
import com.manhpham.baseandroid.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localDataRepositoryInterface: AppLocalDataRepositoryInterface,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val pagingDataUseCase: PagingDataUseCase,
) : BaseViewModel() {

    private val _listItem = SingleLiveEvent<PagingData<PagingUserResponse>>()
    val listItem: LiveData<PagingData<PagingUserResponse>> = _listItem
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
                    _listItem.postValue(it)
                }
                .addTo(compositeDisposable)

            failed
                .subscribe {
                    singleLiveError.postValue(it)
                    _listItem.postValue(PagingData.empty())
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
    }

    override fun onCleared() {
        super.onCleared()
        getUserInfoUseCase.onCleared()
        pagingDataUseCase.onCleared()
    }
}
