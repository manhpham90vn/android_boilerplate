package com.example.baseandroid.networking
import com.example.baseandroid.models.LoginResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AppApi {

    @FormUrlEncoded
    @POST("login")
    fun callLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

}