package com.example.baseandroid.usecase

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.models.ErrorResponse
import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.repository.AppRemoteDataRefreshableRepositoryInterface
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PagingUseCase @Inject constructor(
    private val appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface,
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
) : SingleUseCase<Int, PagingResponse>(schedulerProvider, connectivityService, gson) {
    override fun buildUseCase(params: Int): Single<PagingResponse> {
        return appRemoteDataRefreshableRepositoryInterface.getList(params)
    }
}
