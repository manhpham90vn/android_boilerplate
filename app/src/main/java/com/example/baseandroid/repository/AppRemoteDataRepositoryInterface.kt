package com.example.baseandroid.repository

import com.example.baseandroid.common.ConnectivityService
import com.example.baseandroid.common.SchedulerProvider
import com.example.baseandroid.data.remote.Api
import com.example.baseandroid.data.remote.ApiClient
import com.example.baseandroid.data.remote.ApiClientRefreshtor
import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.models.RefreshTokenResponse
import com.example.baseandroid.networking.makeRequest
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import javax.inject.Inject

interface AppRemoteDataRepositoryInterface {
    fun callLogin(email: String, password: String): Single<LoginResponse>
    fun refresh(token: String): Call<RefreshTokenResponse>
}

class AppRemoteDataRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val apiClientRefreshtor: ApiClientRefreshtor,
    private val schedulerProvider: SchedulerProvider,
    private val connectivityService: ConnectivityService,
    private val gson: Gson
) : AppRemoteDataRepositoryInterface {

    override fun callLogin(email: String, password: String): Single<LoginResponse> {
        return apiClient.callLogin(email, password)
            .makeRequest(schedulerProvider, connectivityService, gson, Api.Login)
    }

    override fun refresh(token: String): Call<RefreshTokenResponse> {
        return apiClientRefreshtor.refresh(token)
    }
}
