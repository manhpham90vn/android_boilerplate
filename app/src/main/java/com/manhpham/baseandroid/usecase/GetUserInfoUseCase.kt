package com.manhpham.baseandroid.usecase

import com.manhpham.baseandroid.models.UserResponse
import com.manhpham.baseandroid.repository.AppRemoteDataRefreshableRepositoryInterface
import com.manhpham.baseandroid.usecase.base.SingleUseCase
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(
    private val appRemoteDataRefreshableRepositoryInterface: AppRemoteDataRefreshableRepositoryInterface
) : SingleUseCase<Unit, UserResponse>() {
    override fun buildUseCase(params: Unit): Single<UserResponse> {
        return appRemoteDataRefreshableRepositoryInterface.getUserInfo()
    }
}
