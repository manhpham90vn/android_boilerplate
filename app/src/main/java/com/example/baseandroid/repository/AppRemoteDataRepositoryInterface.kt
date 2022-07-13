package com.example.baseandroid.repository

import com.example.baseandroid.data.remote.ApiClient
import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.models.RefreshTokenResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

interface AppRemoteDataRepositoryInterface {
    fun callLogin(email: String, password: String): Single<LoginResponse>
    fun refresh(token: String): Single<RefreshTokenResponse>
}

class AppRemoteDataRepository @Inject constructor(private val apiClient: ApiClient): AppRemoteDataRepositoryInterface {
    override fun callLogin(email: String, password: String): Single<LoginResponse> {
        return apiClient.callLogin(email, password).onErrorResumeNext { return@onErrorResumeNext Single.just(LoginResponse()) }
    }

    override fun refresh(token: String): Single<RefreshTokenResponse> {
        return apiClient.refresh(token).onErrorResumeNext { return@onErrorResumeNext Single.just(RefreshTokenResponse()) }
    }
}