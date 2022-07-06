package com.example.baseandroid.data

import com.example.baseandroid.models.LoginResponse
import com.example.baseandroid.models.PagingResponse
import com.example.baseandroid.models.RefreshTokenResponse
import com.example.baseandroid.models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiClient {

    @FormUrlEncoded
    @POST("login")
    fun callLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("user")
    fun getUserInfo(): Call<UserResponse>

    @GET("paging")
    fun getList(
        @Query("page") page: Int
    ): Call<PagingResponse>

    @FormUrlEncoded
    @POST("refreshToken")
    fun refresh(
        @Field("token") token: String
    ): Call<RefreshTokenResponse>

}