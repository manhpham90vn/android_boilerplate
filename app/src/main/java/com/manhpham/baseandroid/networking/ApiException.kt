package com.manhpham.baseandroid.networking

import com.manhpham.baseandroid.data.remote.Api
import java.io.IOException

sealed class ApiException(open val api: Api, override val message: String) : Exception(message) {
    object NoInternetConnectionException : ApiException(Api.None, "no internet connection")
    object ActionAlreadyPerformingException : ApiException(Api.None, "use case is running")
    object RefreshTokenException : IOException("refresh token error")
}

data class AppError(val api: Api, val throwable: Throwable) : RuntimeException(throwable)
