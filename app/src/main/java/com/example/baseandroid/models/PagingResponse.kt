package com.example.baseandroid.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PagingResponse {

    @SerializedName("array")
    @Expose
    val array: List<PagingUserResponse>? = null
}

class PagingUserResponse {

    @SerializedName("id")
    @Expose
    val id: Int? = null

    @SerializedName("name")
    @Expose
    val name: String? = null

    @SerializedName("age")
    @Expose
    val age: Int? = null

    @SerializedName("website")
    @Expose
    val website: String? = null
}
