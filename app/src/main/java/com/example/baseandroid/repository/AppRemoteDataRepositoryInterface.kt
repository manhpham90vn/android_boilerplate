package com.example.baseandroid.repository

import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.data.remote.ApiClientRefreshTokenable
import retrofit2.Call
import javax.inject.Inject

interface AppRemoteDataRepositoryInterface {
    fun callLogin(email: String, password: String): Call<LoginResponse>
    fun getUserInfo(): Call<UserResponse>
    fun getList(page: Int): Call<PagingResponse>

}
class AppRemoteDataRepository @Inject constructor(private val apiClientRefreshTokenable: ApiClientRefreshTokenable): AppRemoteDataRepositoryInterface {
    override fun callLogin(email: String, password: String): Call<LoginResponse> {
        return apiClientRefreshTokenable.callLogin(email, password)
    }

    override fun getUserInfo(): Call<UserResponse> {
        return apiClientRefreshTokenable.getUserInfo()
    }

    override fun getList(page: Int): Call<PagingResponse> {
        return apiClientRefreshTokenable.getList(page)
    }
}