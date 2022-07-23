package com.example.baseandroid.usecase

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.models.ErrorResponse
import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.repository.AppRemoteDataRepositoryInterface
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

data class LoginUseCaseParams(val email: String, val password: String)

class LoginUseCase @Inject constructor(
    private val appRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface,
    schedulerProvider: SchedulerProvider,
    connectivityService: ConnectivityService,
    gson: Gson
) : SingleUseCase<LoginUseCaseParams, LoginResponse>(schedulerProvider, connectivityService, gson) {
    override fun buildUseCase(params: LoginUseCaseParams): Single<LoginResponse> {
        return appRemoteDataRepositoryInterface.callLogin(params.email, params.password)
    }
}
