package com.manhpham.baseandroid.models

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

    @SerializedName("type")
    @Expose
    val type: String? = null

    @SerializedName("img")
    @Expose
    val img: String? = null
}
