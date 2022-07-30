package com.example.baseandroid.usecase

import androidx.paging.PagingData
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.repository.AppPagingDataRepositoryInterface
import com.example.baseandroid.service.ConnectivityService
import com.example.baseandroid.service.SchedulerProvider
import com.example.baseandroid.usecase.base.ObservableUseCase
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

enum class PagingDataSortType(val rawValue: String) {
    ASCENDING("ascending"),
    DESCENDING("descending")
}

class PagingDataUseCase @Inject constructor(
    private val appPagingDataRepositoryInterface: AppPagingDataRepositoryInterface,
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
) : ObservableUseCase<PagingDataSortType, PagingData<PagingUserResponse>>(schedulerProvider, connectivityService, gson) {
    override fun buildUseCase(params: PagingDataSortType): Observable<PagingData<PagingUserResponse>> {
        return appPagingDataRepositoryInterface.getPagingData(params)
    }
}
