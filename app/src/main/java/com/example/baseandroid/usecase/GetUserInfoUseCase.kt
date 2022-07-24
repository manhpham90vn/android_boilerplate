package com.example.baseandroid.usecase

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.repository.AppRemoteDataRefreshableRepositoryInterface
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface,
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
) : SingleUseCase<Unit, UserResponse>(schedulerProvider, connectivityService, gson) {
    override fun buildUseCase(params: Unit): Single<UserResponse> {
        return appRemoteDataRefreshableRepositoryInterface.getUserInfo()
    }
}
