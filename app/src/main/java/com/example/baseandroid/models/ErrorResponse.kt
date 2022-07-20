package com.example.baseandroid.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ErrorResponse {

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("code")
    @Expose
    var code: String? = null

    @SerializedName("message")
    @Expose
    var message: String? = null
}
