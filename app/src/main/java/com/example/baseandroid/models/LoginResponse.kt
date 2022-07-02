package com.example.baseandroid.models
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("token")
    @Expose
    val token: String? = null

    @SerializedName("refreshToken")
    @Expose
    val refreshToken: String? = null

}