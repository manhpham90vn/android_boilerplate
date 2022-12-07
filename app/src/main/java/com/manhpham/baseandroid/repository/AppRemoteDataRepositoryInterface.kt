package com.manhpham.baseandroid.repository

import com.manhpham.baseandroid.data.remote.Api
import com.manhpham.baseandroid.data.remote.ApiClient
import com.manhpham.baseandroid.data.remote.ApiClientRefreshtor
import com.manhpham.baseandroid.models.LoginResponse
import com.manhpham.baseandroid.models.RefreshTokenResponse
import com.manhpham.baseandroid.networking.AppError
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
            .onErrorResumeNext {
                return@onErrorResumeNext Single.error(AppError(Api.Login, it))
            }
    }

    override fun refresh(token: String): Call<RefreshTokenResponse> {
        return apiClientRefreshtor.refresh(token)
    }
}
