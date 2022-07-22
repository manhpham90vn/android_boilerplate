package com.example.baseandroid.networking

import com.example.baseandroid.data.remote.Api
import java.io.IOException

sealed class ApiException(open val api: Api, override val message: String) : Exception(message) {
    class NoInternetConnectionException(override val api: Api) : ApiException(api, "no internet connection")
    class ActionAlreadyPerformingException(override val api: Api) : ApiException(api, "use case is running")

    class TimeOutException(override val api: Api) : ApiException(api, "api time out")
    class ParseJSONException(override val api: Api) : ApiException(api, "parser json error")
    class UnknownException(override val api: Api) : ApiException(api, "unknown exception")
    class ServerErrorException(val code: String, override val message: String, override val api: Api) : ApiException(api, message)

    object RefreshTokenException : IOException("refresh token error")
}
