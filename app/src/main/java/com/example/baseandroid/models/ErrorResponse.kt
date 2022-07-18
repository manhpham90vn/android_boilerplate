package com.example.baseandroid.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class ErrorResponse {

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}
