package com.example.baseandroid.data.local

object StorageConstants {
    const val token = "token"
    const val refreshToken = "refreshToken"
}

interface Storage {
    fun setString(key: String, value: String)
    fun getString(key: String): String
    fun clearString(key: String)
}