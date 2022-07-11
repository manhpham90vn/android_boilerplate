package com.example.baseandroid.repository

import com.example.baseandroid.data.remote.ApiClient
import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.models.RefreshTokenResponse
import retrofit2.Call
import javax.inject.Inject

interface AppRemoteDataRepositoryInterface {
    fun callLogin(email: String, password: String): Call<LoginResponse>
    fun refresh(token: String): Call<RefreshTokenResponse>
}

class AppRemoteDataRepository @Inject constructor(private val apiClient: ApiClient): AppRemoteDataRepositoryInterface {
    override fun callLogin(email: String, password: String): Call<LoginResponse> {
        return apiClient.callLogin(email, password)
    }

    override fun refresh(token: String): Call<RefreshTokenResponse> {
        return apiClient.refresh(token)
    }
}