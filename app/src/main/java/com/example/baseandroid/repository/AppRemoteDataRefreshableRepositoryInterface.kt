package com.example.baseandroid.repository

import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.UserResponse
import com.example.baseandroid.data.remote.ApiClientRefreshable
import retrofit2.Call
import javax.inject.Inject

interface AppRemoteDataRefreshableRepositoryInterface {
    fun getUserInfo(): Call<UserResponse>
    fun getList(page: Int): Call<PagingResponse>

}
class AppRemoteDataRefreshableRepository @Inject constructor(private val apiClientRefreshable: ApiClientRefreshable): AppRemoteDataRefreshableRepositoryInterface {

    override fun getUserInfo(): Call<UserResponse> {
        return apiClientRefreshable.getUserInfo()
    }

    override fun getList(page: Int): Call<PagingResponse> {
        return apiClientRefreshable.getList(page)
    }
}