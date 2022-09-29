package com.example.baseandroid.usecase

import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.repository.AppRemoteDataRefreshableRepositoryInterface
import com.example.baseandroid.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface,
) : SingleUseCase<Unit, UserResponse>() {
    override fun buildUseCase(params: Unit): Single<UserResponse> {
        return appRemoteDataRefreshableRepositoryInterface.getUserInfo()
    }
}
