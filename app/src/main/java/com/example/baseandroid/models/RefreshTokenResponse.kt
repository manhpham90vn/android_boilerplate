package com.example.baseandroid.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RefreshTokenResponse {

    @SerializedName("token")
    @Expose
    val token: String? = null
}
