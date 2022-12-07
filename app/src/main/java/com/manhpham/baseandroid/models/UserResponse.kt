package com.manhpham.baseandroid.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {

    @SerializedName("email")
    @Expose
    val email: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null
}
