package com.example.baseandroid.usecase

import com.example.baseandroid.service.ConnectivityService
import com.example.baseandroid.service.SchedulerProvider
import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRepositoryInterface
import com.example.baseandroid.usecase.base.SingleUseCase
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

data class LoginUseCaseParams(val email: String, val password: String)

class LoginUseCase @Inject constructor(
    private val appRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface,
    private val localDataRepositoryInterface: AppLocalDataRepositoryInterface,
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
) : SingleUseCase<LoginUseCaseParams, Boolean>(schedulerProvider, connectivityService, gson) {

    override fun buildUseCase(params: LoginUseCaseParams): Single<Boolean> {
        return appRemoteDataRepositoryInterface.callLogin(params.email, params.password)
            .map {
                if (!it.token.isNullOrEmpty() && !it.refreshToken.isNullOrEmpty()) {
                    localDataRepositoryInterface.setToken(it.token)
                    localDataRepositoryInterface.setRefreshToken(it.refreshToken)
                    return@map true
                } else {
                    return@map false
                }
            }
    }
}
