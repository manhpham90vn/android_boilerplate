package com.example.baseandroid.repository

import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.data.remote.ApiClientRefreshTokenable
import retrofit2.Call
import javax.inject.Inject

interface AppRemoteDataRefreshTokenableRepositoryInterface {
    fun getUserInfo(): Call<UserResponse>
    fun getList(page: Int): Call<PagingResponse>

}
class AppRemoteDataRefreshTokenableRepository @Inject constructor(private val apiClientRefreshTokenable: ApiClientRefreshTokenable): AppRemoteDataRefreshTokenableRepositoryInterface {

    override fun getUserInfo(): Call<UserResponse> {
        return apiClientRefreshTokenable.getUserInfo()
    }

    override fun getList(page: Int): Call<PagingResponse> {
        return apiClientRefreshTokenable.getList(page)
    }
}