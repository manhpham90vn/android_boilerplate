package com.example.baseandroid.usecase

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.data.remote.Api
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.repository.AppRemoteDataRefreshableRepository
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val remoteDataRefreshableRepository: AppRemoteDataRefreshableRepository,
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
) : SingleUseCase<Unit, UserResponse>(schedulerProvider, connectivityService, gson) {
    override fun buildUseCase(params: Unit): Single<UserResponse> {
        return remoteDataRefreshableRepository.getUserInfo()
    }

    override fun getApiName(): Api = Api.GetUser
}
