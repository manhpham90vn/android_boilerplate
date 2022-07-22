package com.example.baseandroid.repository

import com.example.baseandroid.data.remote.ApiClient
import com.example.baseandroid.data.remote.ApiClientRefreshtor
import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.models.RefreshTokenResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import javax.inject.Inject

interface AppRemoteDataRepositoryInterface {
    fun callLogin(email: String, password: String): Single<LoginResponse>
    fun refresh(token: String): Call<RefreshTokenResponse>
}

class AppRemoteDataRepository @Inject constructor(
    private val apiClient: ApiClient,
    private val apiClientRefreshtor: ApiClientRefreshtor
) : AppRemoteDataRepositoryInterface {

    override fun callLogin(email: String, password: String): Single<LoginResponse> {
        return apiClient.callLogin(email, password)
    }

    override fun refresh(token: String): Call<RefreshTokenResponse> {
        return apiClientRefreshtor.refresh(token)
    }
}
