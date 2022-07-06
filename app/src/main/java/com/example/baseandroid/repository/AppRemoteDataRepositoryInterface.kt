package com.example.baseandroid.repository

import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.RefreshTokenResponse
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.data.ApiClient
import retrofit2.Call

interface AppRemoteDataRepositoryInterface {
    fun callLogin(email: String, password: String): Call<LoginResponse>
    fun getUserInfo(): Call<UserResponse>
    fun getList(page: Int): Call<PagingResponse>
    fun refresh(token: String): Call<RefreshTokenResponse>
}
class AppRemoteDataRepository(private val apiClient: ApiClient): AppRemoteDataRepositoryInterface {
    override fun callLogin(email: String, password: String): Call<LoginResponse> {
        return apiClient.callLogin(email, password)
    }

    override fun getUserInfo(): Call<UserResponse> {
        return apiClient.getUserInfo()
    }

    override fun getList(page: Int): Call<PagingResponse> {
        return apiClient.getList(page)
    }

    override fun refresh(token: String): Call<RefreshTokenResponse> {
        return apiClient.refresh(token)
    }

}