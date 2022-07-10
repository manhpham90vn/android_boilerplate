package com.example.baseandroid.data.remote

import com.example.baseandroid.models.RefreshTokenResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiClient {
    @FormUrlEncoded
    @POST("refreshToken")
    fun refresh(
        @Field("token") token: String
    ): Call<RefreshTokenResponse>
}