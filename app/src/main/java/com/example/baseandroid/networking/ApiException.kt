package com.example.baseandroid.networking

sealed class ApiException(message: String) : Exception(message) {
    class NoInternetConnectionException : ApiException("no internet connection")
    class NotRespondingException : ApiException("not responding")
    class ParseJSONException : ApiException("parser json error")
    class ServerErrorException(val code: String, override val message: String) : ApiException(message)
    class UnknownException : ApiException("unknown exception")
}
