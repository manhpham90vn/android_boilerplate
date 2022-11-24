package com.example.baseandroid.usecase

import com.example.baseandroid.repository.AppLocalDataRepositoryInterface
import com.example.baseandroid.repository.AppRemoteDataRepositoryInterface
import com.example.baseandroid.usecase.base.CompletableUseCase
import com.example.baseandroid.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

data class LoginUseCaseParams(val email: String, val password: String)

class LoginUseCase @Inject constructor(
    private val appRemoteDataRepositoryInterface: AppRemoteDataRepositoryInterface,
    private val localDataRepositoryInterface: AppLocalDataRepositoryInterface,
) : CompletableUseCase<LoginUseCaseParams>() {

    override fun buildUseCase(params: LoginUseCaseParams): Completable {
        return appRemoteDataRepositoryInterface.callLogin(params.email, params.password)
            .doAfterSuccess {
                if (!it.token.isNullOrEmpty() && !it.refreshToken.isNullOrEmpty()) {
                    localDataRepositoryInterface.setToken(it.token)
                    localDataRepositoryInterface.setRefreshToken(it.refreshToken)
                }
            }
            .ignoreElement()
    }
}
