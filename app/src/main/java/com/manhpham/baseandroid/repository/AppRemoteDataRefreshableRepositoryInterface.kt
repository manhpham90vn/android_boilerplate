package com.manhpham.baseandroid.repository

import com.manhpham.baseandroid.data.remote.Api
import com.manhpham.baseandroid.data.remote.ApiClientRefreshable
import com.manhpham.baseandroid.models.PagingResponse
import com.manhpham.baseandroid.models.UserResponse
import com.manhpham.baseandroid.networking.AppError
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface AppRemoteDataRefreshableRepositoryInterface {
    fun getUserInfo(): Single<UserResponse>
    fun getList(page: Int, sort: String): Single<PagingResponse>
}

class AppRemoteDataRefreshableRepository @Inject constructor(
    private val apiClientRefreshable: ApiClientRefreshable
) : AppRemoteDataRefreshableRepositoryInterface {

    override fun getUserInfo(): Single<UserResponse> {
        return apiClientRefreshable.getUserInfo()
            .onErrorResumeNext {
                return@onErrorResumeNext Single.error(AppError(Api.GetUser, it))
            }
    }

    override fun getList(page: Int, sort: String): Single<PagingResponse> {
        return apiClientRefreshable.getList(page, sort)
            .onErrorResumeNext {
                return@onErrorResumeNext Single.error(AppError(Api.Paging, it))
            }
    }
}
