package com.example.baseandroid.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ErrorResponse {

    @SerializedName("status")
    @Expose
    val status: String? = null

    @SerializedName("message")
    @Expose
    val message: String? = null

}