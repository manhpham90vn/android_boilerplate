package com.example.baseandroid.data.remote

import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.UserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ApiClientRefreshable {
    @GET("user")
    fun getUserInfo(): Single<UserResponse>

    @GET("paging")
    fun getList(
        @Query("page") page: Int
    ): Single<PagingResponse>
}