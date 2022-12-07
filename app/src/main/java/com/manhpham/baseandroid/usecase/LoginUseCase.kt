package com.manhpham.baseandroid.usecase

import com.manhpham.baseandroid.repository.AppLocalDataRepositoryInterface
import com.manhpham.baseandroid.repository.AppRemoteDataRepositoryInterface
import com.manhpham.baseandroid.usecase.base.CompletableUseCase
import com.manhpham.baseandroid.usecase.base.SingleUseCase
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
