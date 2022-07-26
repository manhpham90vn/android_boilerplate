package com.example.baseandroid.usecase

import androidx.paging.PagingData
import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.repository.AppPagingDataRepositoryInterface
import com.example.baseandroid.usecase.base.ObservableUseCase
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class PagingDataUseCaseAscending @Inject constructor(
    private val appPagingDataRepositoryInterface: AppPagingDataRepositoryInterface,
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
) : ObservableUseCase<Unit, PagingData<PagingUserResponse>>(schedulerProvider, connectivityService, gson) {
    override fun buildUseCase(params: Unit): Observable<PagingData<PagingUserResponse>> {
        return appPagingDataRepositoryInterface.getPagingDataAscending()
    }
}

class PagingDataUseCaseDescending @Inject constructor(
    private val appPagingDataRepositoryInterface: AppPagingDataRepositoryInterface,
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
) : ObservableUseCase<Unit, PagingData<PagingUserResponse>>(schedulerProvider, connectivityService, gson) {
    override fun buildUseCase(params: Unit): Observable<PagingData<PagingUserResponse>> {
        return appPagingDataRepositoryInterface.getPagingDataDescending()
    }
}