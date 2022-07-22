package com.example.baseandroid.networking

import com.example.baseandroid.data.remote.Api
import java.io.IOException

sealed class ApiException(open val api: Api, override val message: String) : Exception(message) {
    object NoInternetConnectionException : ApiException(Api.None, "no internet connection")
    object ActionAlreadyPerformingException : ApiException(Api.None, "use case is running")

    class TimeOutException(override val api: Api) : ApiException(api, "api time out")
    class ParseJSONException(override val api: Api) : ApiException(api, "parser json error")
    class UnknownException(override val api: Api) : ApiException(api, "unknown exception")
    class ServerErrorException(val code: String, override val message: String, override val api: Api) : ApiException(api, message)

    object RefreshTokenException : IOException("refresh token error")
}

data class AppError(val api: Api, val throwable: Throwable) : RuntimeException(throwable)
