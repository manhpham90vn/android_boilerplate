package com.manhpham.baseandroid.data.remote

import com.manhpham.baseandroid.models.PagingResponse
import com.manhpham.baseandroid.models.UserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClientRefreshable {
    @GET("user")
    fun getUserInfo(): Single<UserResponse>

    @GET("paging")
    fun getList(
        @Query("page") page: Int,
        @Query("sort") sort: String
    ): Single<PagingResponse>
}
