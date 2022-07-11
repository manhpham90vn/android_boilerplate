package com.example.baseandroid.data.remote

import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiClientRefreshable {
    @GET("user")
    fun getUserInfo(): Call<UserResponse>

    @GET("paging")
    fun getList(
        @Query("page") page: Int
    ): Call<PagingResponse>
}