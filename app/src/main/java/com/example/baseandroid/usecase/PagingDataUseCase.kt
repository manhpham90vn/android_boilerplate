package com.example.baseandroid.usecase

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.models.PagingUserResponse
import com.example.baseandroid.repository.AppRemoteDataRefreshableRepositoryInterface
import com.example.baseandroid.usecase.base.SingleUseCase
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PagingDataUseCase @Inject constructor(
    private val appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface,
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
) : SingleUseCase<Int, List<PagingUserResponse>>(schedulerProvider, connectivityService, gson) {
    override fun buildUseCase(params: Int): Single<List<PagingUserResponse>> {
        return appRemoteDataRefreshableRepositoryInterface.getList(params).map { it.array.orEmpty() }
    }
}
