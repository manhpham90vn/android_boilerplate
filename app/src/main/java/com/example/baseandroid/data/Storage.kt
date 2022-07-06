package com.example.baseandroid.data

object StorageConstants {
    const val token = "token"
    const val refreshToken = "refreshToken"
}

interface Storage {
    fun setString(key: String, value: String)
    fun getString(key: String): String
    fun clearString(key: String)
}