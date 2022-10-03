package com.example.baseandroid.ui.home

import androidx.lifecycle.LiveData
import com.example.baseandroid.extensions.checkCanLoadMore
import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.models.request.DataLoadMore
import com.example.baseandroid.models.request.DataLoadMoreInterface
import com.example.baseandroid.models.request.LoadMoreRequest
import com.example.baseandroid.models.request.PagingDataSortType
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.ui.base.BaseViewModel
import com.example.baseandroid.usecase.GetListUserUseCase
import com.example.baseandroid.usecase.GetUserInfoUseCase
import com.example.baseandroid.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.addTo
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val localDataRepositoryInterface: AppLocalDataRepositoryInterface,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getListUserUseCase: GetListUserUseCase,
    private val dataLoadMore: DataLoadMore<PagingUserResponse>
) : BaseViewModel() {

    private var filterType: PagingDataSortType = PagingDataSortType.ASCENDING

    private val _pagingResponse = SingleLiveEvent<PagingResponse>()
    val pagingResponse: LiveData<PagingResponse> = _pagingResponse

    init {
        Observables.combineLatest(getUserInfoUseCase.processing, getListUserUseCase.processing)
            .map {
                return@map it.first || it.second
            }
            .subscribe {
                isLoadingSingleLive.postValue(it)
            }
            .addTo(compositeDisposable)

        getUserInfoUseCase.apply {
                succeeded.subscribe {
            }.addTo(compositeDisposable)

            failed.subscribe {
                singleLiveError.postValue(it)
            }.addTo(compositeDisposable)
        }

        getListUserUseCase.apply {
            succeeded.subscribe {
                _pagingResponse.value = it
                dataLoadMore.handleDataWhenCallApiSuccess(it.array)
            }.addTo(compositeDisposable)

            failed.subscribe {
                singleLiveError.postValue(it)
            }.addTo(compositeDisposable)
        }
    }

    fun callApi() {
        getUserInfoUseCase.execute(Unit)
        dataLoadMore.clearDataLoadMore()
        checkExecuteGetListUser()
    }

    fun checkExecuteGetListUser() {
        if (!dataLoadMore.checkCallApiMore()) {
            return
        }
        val loadMoreRequest = LoadMoreRequest(dataLoadMore.pageLoadMore, dataLoadMore.perPageLoadMore, this.filterType)

        getListUserUseCase.execute(loadMoreRequest)
    }

    fun sort() {
        filterType = if (filterType == PagingDataSortType.ASCENDING) {
            PagingDataSortType.DESCENDING
        } else {
            PagingDataSortType.ASCENDING
        }
    }

    fun cleanData() {
        localDataRepositoryInterface.cleanRefreshToken()
        localDataRepositoryInterface.cleanToken()
    }

    override fun onCleared() {
        super.onCleared()
        getUserInfoUseCase.onCleared()
        getListUserUseCase.onCleared()
    }
}
