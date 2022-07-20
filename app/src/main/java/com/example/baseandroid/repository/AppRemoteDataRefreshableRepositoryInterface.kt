package com.example.baseandroid.repository

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.data.remote.Api
import com.example.baseandroid.data.remote.ApiClientRefreshable
import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.networking.makeRequest
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface AppRemoteDataRefreshableRepositoryInterface {
    fun getUserInfo(): Single<UserResponse>
    fun getList(page: Int): Single<PagingResponse>
}
class AppRemoteDataRefreshableRepository @Inject constructor(
    private val apiClientRefreshable: ApiClientRefreshable,
    private val connectivityService: ConnectivityService,
    private val schedulerProvider: SchedulerProvider,
    private val gson: Gson
) : AppRemoteDataRefreshableRepositoryInterface {

    override fun getUserInfo(): Single<UserResponse> {
        return apiClientRefreshable.getUserInfo()
            .makeRequest(schedulerProvider, connectivityService, gson, Api.GetUser)
    }

    override fun getList(page: Int): Single<PagingResponse> {
        return apiClientRefreshable.getList(page)
            .makeRequest(schedulerProvider, connectivityService, gson, Api.Paging)
    }
}
