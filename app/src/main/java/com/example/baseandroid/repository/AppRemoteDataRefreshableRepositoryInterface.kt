package com.example.baseandroid.repository

import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.data.remote.ApiClientRefreshable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface AppRemoteDataRefreshableRepositoryInterface {
    fun getUserInfo(): Single<UserResponse>
    fun getList(page: Int): Single<PagingResponse>

}
class AppRemoteDataRefreshableRepository @Inject constructor(private val apiClientRefreshable: ApiClientRefreshable): AppRemoteDataRefreshableRepositoryInterface {

    override fun getUserInfo(): Single<UserResponse> {
        return apiClientRefreshable.getUserInfo().onErrorResumeNext { return@onErrorResumeNext Single.just(UserResponse()) }
    }

    override fun getList(page: Int): Single<PagingResponse> {
        return apiClientRefreshable.getList(page).onErrorResumeNext { return@onErrorResumeNext Single.just(PagingResponse()) }
    }
}