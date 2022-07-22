package com.example.baseandroid.usecase

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.repository.AppRemoteDataRefreshableRepository
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class PagingUseCase @Inject constructor(
    private val refreshableRepository: AppRemoteDataRefreshableRepository,
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
) : SingleUseCase<Int, PagingResponse>(schedulerProvider, connectivityService, gson) {
    override fun buildUseCase(params: Int): Single<PagingResponse> {
        return refreshableRepository.getList(params)
    }
}
